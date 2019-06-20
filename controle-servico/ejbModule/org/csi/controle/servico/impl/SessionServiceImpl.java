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
		Query query = this.em.createQuery("SELECT s FROM Sessao s WHERE s.codigo = :token");
		query.setParameter("token", token);
		Sessao sessao = (Sessao) query.getSingleResult();
		return sessao;
	}

}