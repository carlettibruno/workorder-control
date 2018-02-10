package org.csi.controle.core.entidade;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ERRO")
public class Erro implements Serializable {

	private static final long serialVersionUID = -1966295065557927135L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_ERRO")
	private Long idErro;
	
	@Column(name="DESCRICAO", nullable=false)
	private String descricao;
	
	@Column(name="DATA_HORA", nullable=false)
	private Date dataHora;

	public Long getIdErro() {
		return idErro;
	}

	public void setIdErro(Long idErro) {
		this.idErro = idErro;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDataHora() {
		return dataHora;
	}

	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idErro == null) ? 0 : idErro.hashCode());
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
		Erro other = (Erro) obj;
		if (idErro == null) {
			if (other.idErro != null)
				return false;
		} else if (!idErro.equals(other.idErro))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Erro [idErro=" + idErro + ", descricao=" + descricao + "]";
	}

}