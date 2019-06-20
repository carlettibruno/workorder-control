package org.csi.controle.core.entidade;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SELLER")
public class Seller implements EntidadeControlada {

	private static final long serialVersionUID = -8760559040904017969L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_SELLER")
	private Integer idSeller;

	@Column(name = "NOME", nullable = false)
	private String nome;

	@Column(name = "DATA_CRIACAO", nullable = false)
	private Date dataCriacao;

	@Column(name = "DATA_MODIFICACAO")
	private Date dataModificacao;

	@Column(name = "ATIVO", nullable = false)
	private Boolean ativo;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public Long getId() {
		return this.idSeller.longValue();
	}

	@Override
	public void setId(Long id) {
		if (id != null) {
			this.idSeller = id.intValue();
		} else {
			this.idSeller = null;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idSeller == null) ? 0 : idSeller.hashCode());
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
		Seller other = (Seller) obj;
		if (idSeller == null) {
			if (other.idSeller != null)
				return false;
		} else if (!idSeller.equals(other.idSeller))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Seller [ativo=" + ativo + ", dataCriacao=" + dataCriacao + ", dataModificacao=" + dataModificacao
				+ ", idSeller=" + idSeller + ", nome=" + nome + "]";
	}

	public Integer getIdSeller() {
		return idSeller;
	}

	public void setIdSeller(Integer idSeller) {
		this.idSeller = idSeller;
	}

}