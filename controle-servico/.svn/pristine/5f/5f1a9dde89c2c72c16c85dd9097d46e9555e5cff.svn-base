package org.csi.controle.servico.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.Session;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.type.TypeReference;
import org.csi.controle.core.entidade.ChaveConfiguracao;
import org.csi.controle.core.entidade.ClienteAcesso;
import org.csi.controle.core.entidade.Configuracao;
import org.csi.controle.core.entidade.Funcionario;
import org.csi.controle.core.entidade.Modulo;
import org.csi.controle.core.entidade.Permissao;
import org.csi.controle.core.entidade.Sessao;
import org.csi.controle.core.entidade.Usuario;
import org.csi.controle.core.to.Login;
import org.csi.controle.core.util.Codigo;
import org.csi.controle.core.util.CodigoFuncionalidade;
import org.csi.controle.core.util.DadosUtil;
import org.csi.controle.core.util.RetornoServico;
import org.csi.controle.servico.ConfiguracaoService;
import org.csi.controle.servico.LoginService;
import org.csi.controle.servico.util.MailUtil;

@Stateless
public class LoginServiceImpl implements LoginService {

	@PersistenceContext(name="controlePU") //TODO COLOCAR EM CONSTANTES
	private EntityManager em;
	
	@Resource(name="java:/Mail")
	private Session mailSession;	
	
	@EJB
	private ConfiguracaoService configuracaoService;
	
	@SuppressWarnings("unchecked")
	@Override
	public RetornoServico<String> gerarToken(Login login) {
		RetornoServico<String> retorno = null;
		try {
			if(login == null || login == null 
					|| login.getUsuario() == null || login.getSenha() == null) {
				throw new IllegalArgumentException("Favor preencher os dados de login corretamente.");
			}
			
			String senhaCriptografada = DadosUtil.criptografar(login.getUsuario(), login.getSenha());
			Query query = em.createQuery("SELECT u FROM Usuario u WHERE u.login = :usuario AND u.senha = :senha AND u.ativo = :ativo");
			query.setParameter("usuario", login.getUsuario());
			query.setParameter("senha", senhaCriptografada);
			query.setParameter("ativo", true);
			Usuario usuario = (Usuario) query.getSingleResult();
			
			Configuracao configuracao = configuracaoService.obterConfiguracao(ChaveConfiguracao.TEMPO_SESSAO.ordinal()).getData();
			Integer tempoDefault = Integer.parseInt(configuracao.getValor());
			
			String token = DadosUtil.criptografar(login.getUsuario(), usuario.getSenha(), System.currentTimeMillis());
			Sessao sessao = new Sessao();
			sessao.setCodigo(token);
			sessao.setDataCriacao(new Date());
			sessao.setDataAtualizacao(new Date());
			sessao.setDuracao(login.isManterConectado() ? 999999999 : tempoDefault);
			sessao.setUsuario(usuario);
			usuario.setDataUltimoAcesso(new Date());
			sessao.setValidarPermissao(false);
			
			if(usuario instanceof Funcionario) {
				Query queryPermissao = em.createQuery("SELECT p FROM Permissao p JOIN p.grupo gp, Funcionario f JOIN f.grupos gf WHERE gf.idGrupo = gp.idGrupo AND f.idUsuario = :idUsuario GROUP BY p");
				queryPermissao.setParameter("idUsuario", usuario.getIdUsuario());
				List<Permissao> permissoes = queryPermissao.getResultList();
				ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
				String json = ow.writeValueAsString(permissoes);
				sessao.setPermissoes(json);
				sessao.setValidarPermissao(true);
			} else {
				List<Permissao> permissoes = new ArrayList<Permissao>();
				Permissao permissao = new Permissao();
				permissao.setFuncionalidade(CodigoFuncionalidade.CONSULTAR);
				permissao.setModulo(Modulo.ORDEM_SERVICO);
				permissoes.add(permissao);
				ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
				String json = ow.writeValueAsString(permissoes);
				sessao.setPermissoes(json);
			}
			em.merge(usuario);
			em.persist(sessao);
			retorno = new RetornoServico<String>(Codigo.SUCESSO, null, token);
		} catch (NoResultException e) {
			retorno = new RetornoServico<String>(Codigo.AUTENTICAR, "Usuário inválido.", null);
		} catch (Exception e) {
			retorno = new RetornoServico<String>(Codigo.ERRO, e.getMessage(), null);
		}
		return retorno;
	}

	@SuppressWarnings("unchecked")
	@Override
	public RetornoServico<List<Permissao>> obterPermissoes(String token) {
		RetornoServico<List<Permissao>> retorno = null;
		try {			
			Sessao sessao = em.find(Sessao.class, token);
			if(sessao == null) {
				throw new NoResultException();
			}
			Query querySessao = em.createQuery("SELECT s FROM Sessao s WHERE s.usuario.idUsuario = :idUsuario");
			querySessao.setParameter("idUsuario", sessao.getUsuario().getIdUsuario());
			List<Sessao> sessoes = querySessao.getResultList();
			for (Sessao sessaoAux : sessoes) {
				Calendar calendario = Calendar.getInstance();
				calendario.setTime(new Date());
				calendario.set(Calendar.SECOND, sessaoAux.getDuracao()*-1);
				Date dataExpirar = calendario.getTime();
				if(sessaoAux.getDataAtualizacao().before(dataExpirar)) {
					em.remove(sessaoAux);	
				}
			}			
			
			sessao.setDataAtualizacao(new Date());
			String jsonPermissao = sessao.getPermissoes();
			ObjectMapper mapper = new ObjectMapper();
			List<Permissao> permissoes = mapper.readValue(jsonPermissao, new TypeReference<List<Permissao>>() { });
			retorno = new RetornoServico<List<Permissao>>(Codigo.SUCESSO, permissoes);
		} catch (NoResultException e) {
			retorno = new RetornoServico<List<Permissao>>(Codigo.AUTENTICAR, "Usuário não autenticado.");
		} catch (Exception e) {
			retorno = new RetornoServico<List<Permissao>>(Codigo.ERRO, e.getMessage());
		}
		return retorno;
	}

	@SuppressWarnings("unchecked")
	@Override
	public RetornoServico<String> recuperarSenha(String email) {
		RetornoServico<String> retorno = null;
		try {
			if(email == null) {
				throw new IllegalArgumentException("Favor preencher corretamente o email.");
			}
			Query query = em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email");
			query.setParameter("email", email);
			List<Usuario> usuarios = query.getResultList();
			if(usuarios.isEmpty()) {
				throw new IllegalArgumentException("Nenhum usuário com email "+email);
			}
			for (Usuario usuario : usuarios) {
				String novaSenha = DadosUtil.gerarSenha();
				String senhaCriptografada = DadosUtil.criptografar(usuario.getLogin(), novaSenha);		
				usuario.setSenha(senhaCriptografada);
				RetornoServico<Configuracao> configLink = null;
				if(usuario instanceof Funcionario) {
					configLink = configuracaoService.obterConfiguracao(ChaveConfiguracao.CAMINHO_BASE.ordinal());
				} else if(usuario instanceof ClienteAcesso) {
					configLink = configuracaoService.obterConfiguracao(ChaveConfiguracao.LINK_CLIENTE.ordinal());
				}
				if(configLink.getCodigo().intValue() != Codigo.SUCESSO) {
					throw new Exception(configLink.getMensagem());
				}					
				MailUtil.enviarEmailSenha(usuario, configLink.getData().getValor(), novaSenha, mailSession);
			}
			retorno = new RetornoServico<String>(Codigo.SUCESSO, "Email enviado com sucesso para "+email, null);
		} catch (Exception e) {
			retorno = new RetornoServico<String>(Codigo.ERRO, e.getMessage(), null);
		}
		return retorno;
	}

	@Override
	public RetornoServico<String> trocarSenha(Login novaSenha, String token) {
		RetornoServico<String> retorno = null;
		try {
			if(novaSenha == null || token == null || novaSenha.getSenhaNova() == null || novaSenha.getSenhaNovaConfirmacao() == null) {
				throw new IllegalArgumentException("Favor preenchar a nova senha corretamente.");
			} 
			Sessao sessao = em.find(Sessao.class, token);
			String senhaAntiga = DadosUtil.criptografar(sessao.getUsuario().getLogin(), novaSenha.getSenha());
			if(!senhaAntiga.equals(sessao.getUsuario().getSenha())) {
				throw new IllegalArgumentException("A senha atual está inválida, favor tentar novamente.");
			}
			if(!novaSenha.getSenhaNova().equals(novaSenha.getSenhaNovaConfirmacao())) {
				throw new IllegalArgumentException("As senhas não batem, favor verificar a senha de confirmação.");
			}
			String senhaCriptografada = DadosUtil.criptografar(sessao.getUsuario().getLogin(), novaSenha.getSenhaNova());
			sessao.setDataAtualizacao(new Date());
			sessao.getUsuario().setSenha(senhaCriptografada);
			retorno = new RetornoServico<String>(Codigo.SUCESSO, "Senha alterada com sucesso.", null);
		} catch (Exception e) {
			retorno = new RetornoServico<String>(Codigo.ERRO, e.getMessage(), null);
		}
		return retorno;
	}

	@Override
	public RetornoServico<String> invalidarToken(String token) {
		RetornoServico<String> retorno = null;
		try {
			Sessao sessao = em.find(Sessao.class, token);
			em.remove(sessao);
			retorno = new RetornoServico<String>(Codigo.SUCESSO);
		} catch (Exception e) {
			retorno = new RetornoServico<String>(Codigo.ERRO, e.getMessage(), null);
		}
		return retorno;
	}

	@Override
	public RetornoServico<Boolean> temPermissao(String token, Integer modulo, Integer funcionalidade) {
		RetornoServico<Boolean> retorno = null;
		RetornoServico<List<Permissao>> retornoPermissao = obterPermissoes(token);
		if(retornoPermissao.getCodigo().equals(Codigo.SUCESSO)) {
			List<Permissao> permissoes = retornoPermissao.getData();
			for (Permissao permissao : permissoes) {
				if(permissao.getModulo().ordinal() == modulo.intValue() && (permissao.getFuncionalidade() & funcionalidade) == funcionalidade) {
					retorno = new RetornoServico<Boolean>(Codigo.SUCESSO, true);
					break;
				}			
			}
			if(retorno == null) {
				retorno = new RetornoServico<Boolean>(Codigo.SEM_PERMISSAO, false);	
			}
		} else {			
			retorno = new RetornoServico<Boolean>(retornoPermissao.getCodigo(), retornoPermissao.getMensagem(), false);
		}
		return retorno;
	}

}