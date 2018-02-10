package org.csi.controle.core.entidade;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="FILA_IMPRESSORA")
public class FilaImpressora implements Serializable {

	private static final long serialVersionUID = -6255532173496309244L;
	
	private Long idFilaImpressora;
	
	private Long numberOs;
	
	private Integer numberOfCopies;

	public Long getIdFilaImpressora() {
		return idFilaImpressora;
	}

	public void setIdFilaImpressora(Long idFilaImpressora) {
		this.idFilaImpressora = idFilaImpressora;
	}

	public Long getNumberOs() {
		return numberOs;
	}

	public void setNumberOs(Long numberOs) {
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