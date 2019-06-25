package org.csi.controle.servico.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.csi.controle.core.entidade.Sessao;
import org.csi.controle.servico.SessionService;

@Stateless
public class SessionServiceImpl implements SessionService {

	@PersistenceContext(name = "controlePU")
	private EntityManager em;

	@Override
	public Sessao findSession(String token) {
		Query query = em.createQuery("SELECT se FROM Sessao se JOIN FETCH se.usuario u WHERE se.codigo = :codigo");
		query.setParameter("codigo", token);
		Sessao sessao = (Sessao) query.getSingleResult();
		return sessao;
	}

}