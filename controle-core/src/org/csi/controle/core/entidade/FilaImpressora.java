package org.csi.controle.core.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="FILA_IMPRESSORA")
public class FilaImpressora implements Serializable {

	private static final long serialVersionUID = -6255532173496309244L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_FILA_IMPRESSORA")
	private Long idFilaImpressora;
	
	@Column(name="NUMBER_OS", nullable=false)
	private String numberOs;
	
	@Column(name="NUMBER_OF_COPIES", nullable=false)
	private Integer numberOfCopies;

	public Long getIdFilaImpressora() {
		return idFilaImpressora;
	}

	public void setIdFilaImpressora(Long idFilaImpressora) {
		this.idFilaImpressora = idFilaImpressora;
	}

	public String getNumberOs() {
		return numberOs;
	}

	public void setNumberOs(String numberOs) {
		this.numberOs = numberOs;
	}

	public Integer getNumberOfCopies() {
		return numberOfCopies;
	}

	public void setNumberOfCopies(Integer numberOfCopies) {
		this.numberOfCopies = numberOfCopies;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idFilaImpressora == null) ? 0 : idFilaImpressora.hashCode());
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
		FilaImpressora other = (FilaImpressora) obj;
		if (idFilaImpressora == null) {
			if (other.idFilaImpressora != null)
				return false;
		} else if (!idFilaImpressora.equals(other.idFilaImpressora))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FilaImpressora [idFilaImpressora=" + idFilaImpressora + ", numberOs=" + numberOs + ", numberOfCopies="
				+ numberOfCopies + "]";
	}

}