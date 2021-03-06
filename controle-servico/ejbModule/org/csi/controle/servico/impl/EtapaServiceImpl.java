package org.csi.controle.servico.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import org.csi.controle.core.entidade.Etapa;
import org.csi.controle.core.entidade.TipoEntrega;
import org.csi.controle.core.util.Codigo;
import org.csi.controle.core.util.DadosUtil;
import org.csi.controle.core.util.RetornoServico;
import org.csi.controle.servico.EtapaService;

@Stateless
public class EtapaServiceImpl implements EtapaService {

	@PersistenceContext(name="controlePU")
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public RetornoServico<List<Etapa>> obterEtapas() {
		try {			
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MINUTE, -10);
			Date limitNotifyAlive = calendar.getTime();
			Query query = em.createQuery("SELECT e FROM Etapa e ORDER BY e.nome");
			List<Etapa> etapas = query.getResultList();
			for (Etapa etapa : etapas) {
				boolean unavailable = etapa.getDataUltimaAtualizacao() == null || limitNotifyAlive.after(etapa.getDataUltimaAtualizacao());
				etapa.setDisponivel(!unavailable);
			}
			return new RetornoServico<List<Etapa>>(Codigo.SUCESSO, etapas);
		} catch (Exception e) {
			e.printStackTrace();
			return new RetornoServico<List<Etapa>>(Codigo.ERRO, e.getMessage());
		}
	}

	@Override
	public RetornoServico<String> inserirEtapa(Etapa etapa) {
		try {
			etapa.setAtivo(true);
			etapa.setId(null);
			etapa.setAtivo(true);
			etapa.setDataCriacao(new Date());
			etapa.setDataModificacao(new Date());
			if(etapa.getAutomatica() == null) {
				etapa.setAutomatica(false);
			}
			if(etapa.getIp() != null) {
				boolean pingOk = DadosUtil.ping(etapa.getIp());
				etapa.setDisponivel(pingOk);				
			}
			if(etapa.getEtapaInicial() != null && etapa.getEtapaInicial()) {
				limparEtapaInicial();
			}			
			em.persist(etapa);
			return new RetornoServico<String>(Codigo.SUCESSO);
		} catch (Exception e) {
			e.printStackTrace();
			return new RetornoServico<String>(Codigo.ERRO, e.getMessage(), null);
		}
	}

	@Override
	public RetornoServico<Etapa> obterEtapa(Integer idEtapa) {
		try {			
			Etapa etapa = em.find(Etapa.class, idEtapa);
			return new RetornoServico<Etapa>(Codigo.SUCESSO, etapa);
		} catch (Exception e) {
			e.printStackTrace();
			return new RetornoServico<Etapa>(Codigo.ERRO, e.getMessage());
		}
	}

	@Override
	public RetornoServico<String> atualizarEtapa(Integer idEtapa, Etapa etapa) {
		try {			
			Etapa etapaBase = em.find(Etapa.class, idEtapa);
			etapaBase.setNome(etapa.getNome());
			etapaBase.setAutomatica(etapa.getAutomatica());
			etapaBase.setEtapaEntrega(etapa.getEtapaEntrega());
			etapaBase.setIp(etapa.getIp());
			etapaBase.setDataModificacao(new Date());
			if(etapaBase.getIp() != null) {
//				boolean pingOk = DadosUtil.ping(etapaBase.getIp());
				boolean pingOk = true;
				etapaBase.setDisponivel(pingOk);				
			}			
			if(etapa.getEtapaInicial() != null && etapa.getEtapaInicial()) {
				limparEtapaInicial();
			}
			etapaBase.setEtapaInicial(etapa.getEtapaInicial());
			em.merge(etapaBase);
			return new RetornoServico<String>(Codigo.SUCESSO);
		} catch (Exception e) {
			e.printStackTrace();
			return new RetornoServico<String>(Codigo.ERRO, e.getMessage(), null);
		}
	}

	@Override
	public RetornoServico<String> apagarEtapa(Integer idEtapa) {
		try {
			Etapa etapa = em.find(Etapa.class, idEtapa);
			em.remove(etapa);
			return new RetornoServico<String>(Codigo.SUCESSO);			
		} catch (Exception e) {
			e.printStackTrace();
			return new RetornoServico<String>(Codigo.ERRO);
		}
	}

	@Override
	public RetornoServico<TipoEntrega[]> obterTiposEntrega() {
		return new RetornoServico<TipoEntrega[]>(Codigo.SUCESSO, TipoEntrega.values());
	}
	
//	@SuppressWarnings("unchecked")
//	@Schedule(hour="*", minute="*", second="*/50", persistent=false)
//	public void verificarDisponibilidade() {
//		Query query = em.createQuery("SELECT e FROM Etapa e");
//		List<Etapa> etapas = query.getResultList();
//		for (Etapa etapa : etapas) {
//			if (etapa.getIp() != null) {
//				boolean pingOk = DadosUtil.ping(etapa.getIp());
//				etapa.setDisponivel(pingOk);
//				etapa.setDataModificacao(new Date());
//				em.merge(etapa);
//			}
//		}
//	}

	@Override
	public RetornoServico<Etapa> obterEtapaInicial() {
		try {
			Query query = em.createQuery("SELECT e FROM Etapa e WHERE e.etapaInicial = :etapaInicial");
			query.setParameter("etapaInicial", true);
			Etapa etapa = (Etapa) query.getSingleResult();
			return new RetornoServico<Etapa>(Codigo.SUCESSO, etapa);			
		} catch (Exception e) {
			e.printStackTrace();
			return new RetornoServico<Etapa>(Codigo.ERRO);
		}
	}

	@Override
	public void limparEtapaInicial() {
		try {
			Query query = em.createQuery("UPDATE Etapa e SET e.etapaInicial = :etapaInicial");
			query.setParameter("etapaInicial", false);
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	@Override
	public void notificarEtapa(String reference, HttpServletRequest request) {
		Etapa etapa = findEtapaByReference(reference).getData();
		if(etapa == null) {
			etapa = new Etapa();
			etapa.setReference(reference);
			etapa.setDisponivel(true);
			etapa.setIpExterno(request.getRemoteAddr());
			etapa.setNome(reference + " - Mudar o nome");
			this.inserirEtapa(etapa);
		} else {
			String ip = request.getRemoteAddr();
			etapa.setIpExterno(ip);
			etapa.setDataUltimaAtualizacao(new Date());
			etapa.setDisponivel(true);
			this.em.merge(etapa);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public RetornoServico<Etapa> findEtapaByReference(String reference) {
		Query query = em.createQuery("SELECT e FROM Etapa e WHERE e.reference = :ref");
		query.setParameter("ref", reference);
		List<Etapa> etapas = query.getResultList();
		if(etapas.isEmpty()) {
			return new RetornoServico<Etapa>(Codigo.SUCESSO);
		}
		return new RetornoServico<Etapa>(Codigo.SUCESSO, etapas.get(0));
	}

}