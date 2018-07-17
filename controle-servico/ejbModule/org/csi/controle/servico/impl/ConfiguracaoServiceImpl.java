package org.csi.controle.servico.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.csi.controle.core.entidade.ChaveConfiguracao;
import org.csi.controle.core.entidade.Configuracao;
import org.csi.controle.core.util.Codigo;
import org.csi.controle.core.util.RetornoServico;
import org.csi.controle.servico.ConfiguracaoService;

@Stateless
public class ConfiguracaoServiceImpl implements ConfiguracaoService {

	@PersistenceContext(name="controlePU")
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public RetornoServico<List<Configuracao>> obterConfiguracoes() {
		try {			
			Query query = em.createQuery("SELECT c FROM Configuracao c WHERE c.ativo = :ativo");
			query.setParameter("ativo", true);
			List<Configuracao> configuracoes = query.getResultList();
			return new RetornoServico<List<Configuracao>>(Codigo.SUCESSO, configuracoes);
		} catch (Exception e) {
			e.printStackTrace();
			return new RetornoServico<List<Configuracao>>(Codigo.ERRO, e.getMessage());
		}
	}

	@Override
	public RetornoServico<String> inserirConfiguracao(Configuracao configuracao) {
		try {
			configuracao.setIdConfiguracao(null);
			configuracao.setAtivo(true);
			configuracao.setDataCriacao(new Date());
			configuracao.setDataModificacao(new Date());
			return new RetornoServico<String>(Codigo.SUCESSO, "Inserido com sucesso", null);
		} catch (Exception e) {
			e.printStackTrace();
			return new RetornoServico<String>(Codigo.ERRO, e.getMessage(), null);
		}
	}

	@Override
	public RetornoServico<Configuracao> obterConfiguracao(String chave) {
		try {			
			Query query = em.createQuery("SELECT c FROM Configuracao c WHERE c.ativo = :ativo AND c.chave = :chave");
			query.setParameter("ativo", true);
			query.setParameter("chave", ChaveConfiguracao.valueOf(chave));
			Configuracao configuracao = (Configuracao) query.getSingleResult();
			return new RetornoServico<Configuracao>(Codigo.SUCESSO, configuracao);
		} catch (Exception e) {
			e.printStackTrace();
			return new RetornoServico<Configuracao>(Codigo.ERRO, e.getMessage());
		}
	}
	
	@Override
	public RetornoServico<Configuracao> obterConfiguracao(Integer chave) {
		return obterConfiguracao(ChaveConfiguracao.values()[chave].name());
	}

	@Override
	public RetornoServico<String> atualizarConfiguracao(Integer idConfiguracao, Configuracao configuracao) {
		try {			
			Configuracao configuracaoBase = em.find(Configuracao.class, idConfiguracao);
			configuracaoBase.setDataModificacao(new Date());
			configuracaoBase.setValor(configuracao.getValor());
			return new RetornoServico<String>(Codigo.SUCESSO);
		} catch (Exception e) {
			e.printStackTrace();
			return new RetornoServico<String>(Codigo.ERRO, e.getMessage(), null);
		}
	}

	@Override
	public RetornoServico<String> apagarConfiguracao(Integer idConfiguracao) {
		try {
			Configuracao configuracao = em.find(Configuracao.class, idConfiguracao);
			em.remove(configuracao);
			return new RetornoServico<String>(Codigo.SUCESSO);			
		} catch (Exception e) {
			e.printStackTrace();
			return new RetornoServico<String>(Codigo.ERRO);
		}
	}

}