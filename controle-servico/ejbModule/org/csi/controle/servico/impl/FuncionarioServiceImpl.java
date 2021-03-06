package org.csi.controle.servico.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import org.csi.controle.core.entidade.Funcionario;
import org.csi.controle.core.entidade.Grupo;
import org.csi.controle.core.entidade.Usuario;
import org.csi.controle.core.util.Codigo;
import org.csi.controle.core.util.RetornoServico;
import org.csi.controle.servico.FuncionarioService;

@Stateless
public class FuncionarioServiceImpl extends UsuarioServiceImpl implements FuncionarioService {

	@SuppressWarnings("unchecked")
	@Override
	public RetornoServico<List<Funcionario>> obterFuncionarios(String filtro, Integer inicio, Integer qtdeRegistro) {
		try {
			String sql = "SELECT f FROM Funcionario f LEFT JOIN FETCH f.grupos g WHERE f.ativo = :ativo #WHERE# ORDER BY f.idUsuario DESC";
			if(filtro != null && !filtro.isEmpty()) {
				sql = sql.replace("#WHERE#", " AND ( UPPER(f.login) like :filtro OR UPPER(f.email) like :filtro OR UPPER(f.nome) like :filtro ) ");
			} else {
				sql = sql.replace("#WHERE#", " ");
			}
			Query query = em.createQuery(sql);
			query.setFirstResult(inicio);
			query.setMaxResults(qtdeRegistro);
			query.setParameter("ativo", true);
			if(filtro != null && !filtro.isEmpty()) {
				query.setParameter("filtro", "%" + filtro.toUpperCase() + "%");
			}			
			List<Funcionario> funcionarios = query.getResultList();
			return new RetornoServico<List<Funcionario>>(Codigo.SUCESSO, funcionarios);
		} catch (Exception e) {
			e.printStackTrace();
			return new RetornoServico<List<Funcionario>>(Codigo.ERRO, e.getMessage());
		}
	}

	@Override
	public RetornoServico<Funcionario> inserirFuncionario(Funcionario funcionario) {
		try {
			funcionario.setId(null);
			funcionario.setAtivo(true);
			funcionario.setDataCriacao(new Date());
			funcionario.setDataModificacao(new Date());
			funcionario.setLogin(funcionario.getEmail());
			RetornoServico<Usuario> retornoUsuario = existeLogin(funcionario.getLogin());
			if (retornoUsuario.getData() != null) {
				return new RetornoServico<Funcionario>(Codigo.ERRO, "Usuário já existe com o login: "+funcionario.getLogin());
			}
			manterSenhaUsuario(funcionario, true);
			return new RetornoServico<Funcionario>(Codigo.SUCESSO);
		} catch (Exception e) {
			e.printStackTrace();
			return new RetornoServico<Funcionario>(Codigo.ERRO, e.getMessage());
		}
	}

	@Override
	public RetornoServico<Funcionario> obterFuncionario(Integer idFuncionario) {
		try {			
			Funcionario funcionario = em.find(Funcionario.class, idFuncionario);
			return new RetornoServico<Funcionario>(Codigo.SUCESSO, funcionario);
		} catch (Exception e) {
			e.printStackTrace();
			return new RetornoServico<Funcionario>(Codigo.ERRO, e.getMessage());
		}
	}

	@Override
	public RetornoServico<Funcionario> atualizarFuncionario(Integer idFuncionario, Funcionario funcionario) {
		try {			
			Funcionario funcionarioBase = em.find(Funcionario.class, idFuncionario);
			String msg = null;
			if(!funcionarioBase.getEmail().equalsIgnoreCase(funcionario.getEmail())) {
				msg = "Atenção, o usuário teve o email alterado, uma nova senha foi gerada e enviada para o novo email.";
				funcionarioBase.setLogin(funcionario.getEmail());
				funcionarioBase.setEmail(funcionario.getEmail());
				manterSenhaUsuario(funcionarioBase, true);
			}
			funcionarioBase.setNome(funcionario.getNome());
			funcionarioBase.getGrupos().clear();
			funcionarioBase.getGrupos().addAll(funcionario.getGrupos());
			funcionarioBase.setDataModificacao(new Date());
			return new RetornoServico<Funcionario>(Codigo.SUCESSO, msg);
		} catch (Exception e) {
			e.printStackTrace();
			return new RetornoServico<Funcionario>(Codigo.ERRO, e.getMessage());
		}
	}

	@Override
	public RetornoServico<String> apagarFuncionario(Integer idFuncionario) {
		try {
			Funcionario funcionario = em.find(Funcionario.class, idFuncionario);
			funcionario.setAtivo(false);
			em.merge(funcionario);
			return new RetornoServico<String>(Codigo.SUCESSO);			
		} catch (Exception e) {
			e.printStackTrace();
			return new RetornoServico<String>(Codigo.ERRO);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public RetornoServico<List<Grupo>> obterGrupos() {
		try {			
			Query query = em.createQuery("SELECT g FROM Grupo g");
			List<Grupo> grupos = query.getResultList();
			return new RetornoServico<List<Grupo>>(Codigo.SUCESSO, grupos);
		} catch (Exception e) {
			return new RetornoServico<List<Grupo>>(Codigo.ERRO, e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean pertenceGrupo(Integer idFuncionario, Integer idGrupo) {
		try {			
			Query query = em.createQuery("SELECT f.idUsuario FROM Funcionario f JOIN f.grupos g WHERE g.idGrupo = :idGrupo AND f.idUsuario = :idUsuario");
			query.setParameter("idGrupo", idGrupo);
			query.setParameter("idUsuario", idFuncionario);
			query.setFirstResult(0);
			query.setMaxResults(1);
			List<Integer> ids = query.getResultList();
			return ids.size() > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}