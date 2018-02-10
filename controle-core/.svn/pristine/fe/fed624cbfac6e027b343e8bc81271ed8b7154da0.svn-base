package org.csi.controle.core.to;

import java.io.Serializable;

public class EtapaTO implements Serializable {
	
	private static final long serialVersionUID = 5383372717618712526L;

	private Integer idEtapa;
	
	private String ipInterno;

	public Integer getIdEtapa() {
		return idEtapa;
	}

	public void setIdEtapa(Integer idEtapa) {
		this.idEtapa = idEtapa;
	}

	public String getIpInterno() {
		return ipInterno;
	}

	public void setIpInterno(String ipInterno) {
		this.ipInterno = ipInterno;
	}

	@Override
	public String toString() {
		return "EtapaTO [idEtapa=" + idEtapa + ", ipInterno=" + ipInterno + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idEtapa == null) ? 0 : idEtapa.hashCode());
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
		EtapaTO other = (EtapaTO) obj;
		if (idEtapa == null) {
			if (other.idEtapa != null)
				return false;
		} else if (!idEtapa.equals(other.idEtapa))
			return false;
		return true;
	}

}
