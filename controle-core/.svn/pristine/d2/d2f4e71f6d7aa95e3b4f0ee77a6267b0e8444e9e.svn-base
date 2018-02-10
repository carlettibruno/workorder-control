package org.csi.controle.core.entidade;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="CLIENTE_ACESSO")
@PrimaryKeyJoinColumn(name="ID_CLIENTE_ACESSO", referencedColumnName="ID_USUARIO")
public class ClienteAcesso extends Usuario {

	private static final long serialVersionUID = -3231739562657640430L;
	
	public ClienteAcesso() {
	}

	public ClienteAcesso(Cliente cliente) {
		this.setEmail(cliente.getEmail());
		this.setNome(cliente.getNome());
		this.setLogin(cliente.getEmail());
		this.setAtivo(true);
		this.setDataCriacao(new Date());
		this.setDataModificacao(new Date());		
	}
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="CLIENTE_ACESSO_CLIENTE",
		joinColumns=@JoinColumn(name="ID_CLIENTE_ACESSO", referencedColumnName="ID_USUARIO"),
		inverseJoinColumns=@JoinColumn(name="ID_CLIENTE", referencedColumnName="ID_CLIENTE"))	
	private List<Cliente> clientes;

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}
	
}