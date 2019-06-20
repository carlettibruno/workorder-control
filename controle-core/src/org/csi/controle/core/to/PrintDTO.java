package org.csi.controle.core.to;

import java.io.Serializable;

public class PrintDTO implements Serializable {

	private static final long serialVersionUID = -8633847122988692091L;

	private Long idOrdemServico;

	private Integer qty;

	public PrintDTO() {
	}

	public PrintDTO(Long idOrdemServico, Integer qty) {
		this.idOrdemServico = idOrdemServico;
		this.qty = qty;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idOrdemServico == null) ? 0 : idOrdemServico.hashCode());
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
		PrintDTO other = (PrintDTO) obj;
		if (idOrdemServico == null) {
			if (other.idOrdemServico != null)
				return false;
		} else if (!idOrdemServico.equals(other.idOrdemServico))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PrintDTO [idOrdemServico=" + idOrdemServico + ", qty=" + qty + "]";
	}

	public Long getIdOrdemServico() {
		return idOrdemServico;
	}

	public void setIdOrdemServico(Long idOrdemServico) {
		this.idOrdemServico = idOrdemServico;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

}
