package org.csi.controle.core.entidade;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="CLIENTE")
public class Cliente implements EntidadeControlada {

	private static final long serialVersionUID = -8760559040904017969L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_CLIENTE")
	private Integer idCliente;

	@Column(name="CODE")
	private String code;
	
	@Column(name="CPF_CNPJ", nullable=false)
	private String cpfCnpj;
	
	@Column(name="NOME", nullable=false)
	private String nome;
	
	@Column(name="APROVACAO_FOTO")
	private Boolean aprovacaoFoto;	
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="ID_ENDERECO", referencedColumnName="ID_ENDERECO")
	private Endereco endereco;
	
	@Column(name="DATA_CRIACAO", nullable=false)
	private Date dataCriacao;
	
	@Column(name="EMAIL")
	private String email;	
	
	@Column(name="DATA_MODIFICACAO")
	private Date dataModificacao;
	
	@Column(name="ATIVO", nullable=false)
	private Boolean ativo;	

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Boolean getAprovacaoFoto() {
		return aprovacaoFoto;
	}

	public void setAprovacaoFoto(Boolean aprovacaoFoto) {
		this.aprovacaoFoto = aprovacaoFoto;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	@Override
	public Long getId() {
		return this.idCliente.longValue();
	}

	@Override
	public void setId(Long id) {
		if(id != null) {			
			this.idCliente = id.intValue();
		} else {
			this.idCliente = null;
		}
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Date getDataModificacao() {
		return dataModificacao;
	}

	public void setDataModificacao(Date dataModificacao) {
		this.dataModificacao = dataModificacao;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idCliente == null) ? 0 : idCliente.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (idCliente == null) {
			if (other.idCliente != null)
				return false;
		} else if (!idCliente.equals(other.idCliente))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Cliente [idCliente=" + idCliente + ", cpfCnpj=" + cpfCnpj
				+ ", nome=" + nome + ", aprovacaoFoto=" + aprovacaoFoto
				+ ", dataCriacao=" + dataCriacao + ", email=" + email
				+ ", dataModificacao=" + dataModificacao + ", ativo=" + ativo
				+ "]";
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}