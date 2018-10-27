package org.csi.controle.core.to;

import java.io.Serializable;

public class RequestHistorico implements Serializable {

	private static final long serialVersionUID = -7493239995891942016L;
	
	private String oscode;
	
	private String reference;

	public String getOscode() {
		return oscode;
	}

	public void setOscode(String oscode) {
		this.oscode = oscode;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	@Override
	public String toString() {
		return "RequestHistorico [oscode=" + oscode + ", reference=" + reference + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((reference == null) ? 0 : reference.hashCode());
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
		RequestHistorico other = (RequestHistorico) obj;
		if (reference == null) {
			if (other.reference != null)
				return false;
		} else if (!reference.equals(other.reference))
			return false;
		return true;
	}

}
