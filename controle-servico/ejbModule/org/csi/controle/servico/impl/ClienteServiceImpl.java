package org.csi.controle.servico.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import org.csi.controle.core.entidade.Cliente;
import org.csi.controle.core.entidade.ClienteAcesso;
import org.csi.controle.core.entidade.Endereco;
import org.csi.controle.core.entidade.Usuario;
import org.csi.controle.core.util.Codigo;
import org.csi.controle.core.util.DadosUtil;
import org.csi.controle.core.util.RetornoServico;
import org.csi.controle.servico.ClienteService;

@Stateless
public class ClienteServiceImpl extends UsuarioServiceImpl implements ClienteService {
	
	@SuppressWarnings("unchecked")
	@Override
	public RetornoServico<List<Cliente>> obterClientes(Integer inicio, Integer qtdeRegistro, String nome) {
		try {
			String whereCliente = "";
			if(nome != null && !nome.isEmpty()) {
				whereCliente = " AND UPPER(c.nome) LIKE :nome ";
			}
			Query query = em.createQuery("SELECT c FROM Cliente c WHERE c.ativo = :ativo "+whereCliente+" ORDER BY c.nome ASC");
			query.setParameter("ativo", true);
			if(nome != null && !nome.isEmpty()) {
				query.setParameter("nome", "%"+nome.toUpperCase()+"%");
			}
			query.setFirstResult(inicio);
			query.setMaxResults(qtdeRegistro);
			List<Cliente> clientes = query.getResultList();
			return new RetornoServico<List<Cliente>>(Codigo.SUCESSO, clientes);
		} catch (Exception e) {
			e.printStackTrace();
			return new RetornoServico<List<Cliente>>(Codigo.ERRO, e.getMessage());
		}
	}

	@Override
	public RetornoServico<Cliente> inserirCliente(Cliente cliente) {
		try {
			cliente.setId(null);
			cliente.setAtivo(true);
			cliente.setDataCriacao(new Date());
			cliente.setDataModificacao(new Date());
			
			manterClienteAcesso(cliente);
			
			if(cliente.getAprovacaoFoto() == null) {
				cliente.setAprovacaoFoto(DadosUtil.DEFAULT_APPROVAL_PHOTOS);
			}
			em.persist(cliente);
			return new RetornoServico<Cliente>(Codigo.SUCESSO);
		} catch (Exception e) {
			e.printStackTrace();
			return new RetornoServico<Cliente>(Codigo.ERRO, e.getMessage());
		}
	}

	@Override
	public RetornoServico<Cliente> obterCliente(Integer idCliente) {
		try {			
			Cliente cliente = em.find(Cliente.class, idCliente);
			return new RetornoServico<Cliente>(Codigo.SUCESSO, cliente);
		} catch (Exception e) {
			e.printStackTrace();
			return new RetornoServico<Cliente>(Codigo.ERRO, e.getMessage());
		}
	}

	@Override
	public RetornoServico<Cliente> atualizarCliente(Integer idCliente, Cliente cliente) {
		try {			
			Cliente clienteBase = em.find(Cliente.class, idCliente);
			String msg = null;
			if(!clienteBase.getEmail().equalsIgnoreCase(cliente.getEmail())) {
				msg = "Atenção, o usuário teve o email alterado, uma nova senha foi gerada e enviada para o novo email.";
//				clienteBase.setLogin(cliente.getEmail());
				clienteBase.setEmail(cliente.getEmail());
			}
			clienteBase.setNome(cliente.getNome());
			clienteBase.setCpfCnpj(cliente.getCpfCnpj());
			clienteBase.setDataModificacao(new Date());
			if(cliente.getAprovacaoFoto() == null) {
				clienteBase.setAprovacaoFoto(DadosUtil.DEFAULT_APPROVAL_PHOTOS);
			} else {
				clienteBase.setAprovacaoFoto(cliente.getAprovacaoFoto());
			}
			if(clienteBase.getEndereco() == null || cliente.getEndereco() != null) {
				if(clienteBase.getEndereco() == null) {					
					clienteBase.setEndereco(new Endereco());
				}
				clienteBase.getEndereco().setBairro(cliente.getEndereco().getBairro());
				clienteBase.getEndereco().setCep(cliente.getEndereco().getCep());
				clienteBase.getEndereco().setCidade(cliente.getEndereco().getCidade());
				clienteBase.getEndereco().setComplemento(cliente.getEndereco().getComplemento());
				clienteBase.getEndereco().setEndereco(cliente.getEndereco().getEndereco());
				clienteBase.getEndereco().setEstado(cliente.getEndereco().getEstado());
				clienteBase.getEndereco().setNumero(cliente.getEndereco().getNumero());
			}
			manterClienteAcesso(clienteBase);
			
			return new RetornoServico<Cliente>(Codigo.SUCESSO, msg);
		} catch (Exception e) {
			e.printStackTrace();
			return new RetornoServico<Cliente>(Codigo.ERRO, e.getMessage());
		}
	}

	private void manterClienteAcesso(Cliente cliente) {
		if(cliente.getEmail() != null && !cliente.getEmail().trim().isEmpty()) {
			RetornoServico<Usuario> retornoUsuario = existeLogin(cliente.getEmail());
			ClienteAcesso clienteAcesso = null;
			if (retornoUsuario.getData() != null) {
				if(retornoUsuario.getData() instanceof ClienteAcesso) {
					clienteAcesso = (ClienteAcesso)retornoUsuario.getData();
				}
			} else {
				clienteAcesso = new ClienteAcesso(cliente);				
				clienteAcesso.setClientes(new ArrayList<Cliente>());
				manterSenhaUsuario(clienteAcesso, false);
			}
			if(!clienteAcesso.getClientes().contains(cliente)) {
				clienteAcesso.getClientes().add(cliente);				
			}
		}
	}
	
	@Override
	public RetornoServico<String> apagarCliente(Integer idCliente) {
		try {
			Cliente cliente = em.find(Cliente.class, idCliente);
			cliente.setAtivo(false);
			em.merge(cliente);
			return new RetornoServico<String>(Codigo.SUCESSO);			
		} catch (Exception e) {
			e.printStackTrace();
			return new RetornoServico<String>(Codigo.ERRO);
		}
	}

	@Override
	public Cliente obterCliente(String code) {
		try {			
			Query query = em.createQuery("SELECT c FROM Cliente c WHERE c.code = :code AND c.ativo = :ativo");
			query.setParameter("code", code);
			query.setParameter("ativo", true);
			Cliente cliente = (Cliente) query.getSingleResult();
			return cliente;
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public RetornoServico<String> gerarNovaSenha(Integer idCliente, Boolean enviarEmail) {
		try {
			Query query = em.createQuery("SELECT ca FROM ClienteAcesso ca JOIN ca.clientes c WHERE c.idCliente = :idCliente");
			query.setParameter("idCliente", idCliente);
			List<ClienteAcesso> clientesAcesso = query.getResultList();
			for (ClienteAcesso clienteAcesso : clientesAcesso) {				
				manterSenhaUsuario(clienteAcesso, enviarEmail);
			}
			return new RetornoServico<String>(Codigo.SUCESSO, "Senha alterada com sucesso!", null);
		} catch (Exception e) {
			e.printStackTrace();
			return new RetornoServico<String>(Codigo.ERRO, e.getMessage(), null);
		}
	}

}