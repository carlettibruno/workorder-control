package org.csi.controle.servico.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.mail.Session;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.csi.controle.core.entidade.ChaveConfiguracao;
import org.csi.controle.core.entidade.Configuracao;
import org.csi.controle.core.entidade.Usuario;
import org.csi.controle.core.util.Codigo;
import org.csi.controle.core.util.DadosUtil;
import org.csi.controle.core.util.RetornoServico;
import org.csi.controle.servico.ConfiguracaoService;
import org.csi.controle.servico.UsuarioService;
import org.csi.controle.servico.util.MailUtil;

public class UsuarioServiceImpl implements UsuarioService {

	@PersistenceContext(name="controlePU")
	protected EntityManager em;	
	
	@Resource(name="java:/Mail")
	protected Session mailSession;	
	
	@EJB
	protected ConfiguracaoService configService;	
	
	@Override
	public RetornoServico<String> manterSenhaUsuario(Usuario usuario, Boolean enviarEmail) {
		try {
			String senha = DadosUtil.gerarSenha();
			String senhaCriptografada = DadosUtil.criptografar(usuario.getLogin(), senha);
			usuario.setSenha(senhaCriptografada);
			if(usuario.getId() == null) {				
				em.persist(usuario);
			} else {
				em.merge(usuario);
			}
			if(enviarEmail != null && enviarEmail) {
				RetornoServico<Configuracao> config = configService.obterConfiguracao(ChaveConfiguracao.LINK_CLIENTE.ordinal());
				if(config.getCodigo().intValue() != Codigo.SUCESSO) {
					throw new Exception(config.getMensagem());
				}
				RetornoServico<Configuracao> caminhoBase = configService.obterConfiguracao(ChaveConfiguracao.CAMINHO_BASE.ordinal());
				MailUtil.enviarEmailSenha(usuario, config.getData().getValor(), senha, caminhoBase.getData().getValor(), mailSession);
			}
			return new RetornoServico<String>(Codigo.SUCESSO);
		} catch (Exception e) {
			e.printStackTrace();
			return new RetornoServico<String>(Codigo.ERRO, e.getMessage(), null);
		}		
	}


	@SuppressWarnings("unchecked")
	protected RetornoServico<Usuario> existeLogin(String login) {
		Query query = em.createQuery("SELECT u FROM Usuario u WHERE UPPER(u.login) = :login AND u.ativo = :ativo");
		query.setParameter("login", login.toUpperCase());
		query.setParameter("ativo", true);
		query.setMaxResults(1);
		List<Usuario> usuarios = query.getResultList();
		Usuario usuarioRetorno = null;
		if(!usuarios.isEmpty()) {
			usuarioRetorno = usuarios.get(0);
		}
		return new RetornoServico<Usuario>(Codigo.SUCESSO, usuarioRetorno); 
	}
	
	
}
