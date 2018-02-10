package org.csi.controle.servico.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.csi.controle.core.entidade.FilaImpressora;
import org.csi.controle.core.util.Codigo;
import org.csi.controle.core.util.RetornoServico;
import org.csi.controle.servico.FilaImpressoraService;


public class FilaImpressoraServiceImpl implements FilaImpressoraService {

	@PersistenceContext(name="controlePU")
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public RetornoServico<List<FilaImpressora>> findAll() {
		Query query = em.createQuery("SELECT fi FROM FilaImpressora fi ORDER BY fi.idFilaImpressora");
		List<FilaImpressora> list = query.getResultList();
		return new RetornoServico<List<FilaImpressora>>(Codigo.SUCESSO, list);
	}

	@Override
	public void remove(Long id) {
		Query query = em.createQuery("DELETE FROM FilaImpressora fi WHERE fi.idFilaImpressora = :id");
		query.setParameter("id", id);
		query.executeUpdate();
	}

	@Override
	public RetornoServico<FilaImpressora> create(Long osNumber, Integer numberOfCopies) {
		FilaImpressora fi = new FilaImpressora();
		fi.setNumberOfCopies(numberOfCopies);
		fi.setNumberOs(osNumber);
		em.persist(fi);
		return new RetornoServico<FilaImpressora>(Codigo.SUCESSO, fi);
	}

}
