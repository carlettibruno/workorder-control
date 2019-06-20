package org.csi.controle.servico.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.csi.controle.core.entidade.Seller;
import org.csi.controle.core.util.Codigo;
import org.csi.controle.core.util.RetornoServico;
import org.csi.controle.servico.SellerService;

@Stateless
public class SellerServiceImpl implements SellerService {

	@PersistenceContext(name = "controlePU")
	private EntityManager em;

	@Override
	public RetornoServico<List<Seller>> findAll() {
		Query query = this.em.createQuery("SELECT s FROM Seller s WHERE s.ativo = :ativo ORDER BY s.nome");
		query.setParameter("ativo", true);
		List<Seller> sellers = query.getResultList();
		return new RetornoServico<>(Codigo.SUCESSO, sellers);
	}

	@Override
	public RetornoServico<String> create(Seller seller) {
		try {
			seller.setAtivo(true);
			seller.setId(null);
			seller.setAtivo(true);
			seller.setDataCriacao(new Date());
			seller.setDataModificacao(new Date());
			em.persist(seller);
			return new RetornoServico<String>(Codigo.SUCESSO);
		} catch (Exception e) {
			e.printStackTrace();
			return new RetornoServico<String>(Codigo.ERRO, e.getMessage(), null);
		}
	}

	@Override
	public RetornoServico<Seller> findById(Integer id) {
		try {
			Seller seller = em.find(Seller.class, id);
			return new RetornoServico<Seller>(Codigo.SUCESSO, seller);
		} catch (Exception e) {
			e.printStackTrace();
			return new RetornoServico<Seller>(Codigo.ERRO, e.getMessage());
		}
	}

	@Override
	public RetornoServico<String> update(Integer id, Seller seller) {
		try {
			Seller sellerBase = em.find(Seller.class, id);
			sellerBase.setNome(seller.getNome());
			sellerBase.setDataModificacao(new Date());
			em.merge(sellerBase);
			return new RetornoServico<String>(Codigo.SUCESSO);
		} catch (Exception e) {
			e.printStackTrace();
			return new RetornoServico<String>(Codigo.ERRO, e.getMessage(), null);
		}
	}

	@Override
	public RetornoServico<String> delete(Integer id) {
		try {
			Seller seller = em.find(Seller.class, id);
			seller.setAtivo(false);
			em.merge(seller);
			return new RetornoServico<String>(Codigo.SUCESSO);
		} catch (Exception e) {
			e.printStackTrace();
			return new RetornoServico<String>(Codigo.ERRO);
		}
	}

}