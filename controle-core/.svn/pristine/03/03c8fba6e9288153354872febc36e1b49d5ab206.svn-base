package org.csi.controle.core.entidade;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name="DETALHE_NOTA")
public class DetalheNota implements Serializable {

	private static final long serialVersionUID = -2588522750415583065L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_DETALHE_NOTA")
	private Long idDetalheNota;	
	
	@Column(name="DATA_VENCIMENTO", nullable=false)
	private Date dataVencimento;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="ID_NOTA_FISCAL", referencedColumnName="ID_NOTA_FISCAL")	
	private NotaFiscal notaFiscal;

	public DetalheNota() {
		super();
	}

	public DetalheNota(Date dataVencimento, NotaFiscal notaFiscal) {
		super();
		this.dataVencimento = dataVencimento;
		this.notaFiscal = notaFiscal;
	}

	public Long getIdDetalheNota() {
		return idDetalheNota;
	}

	public void setIdDetalheNota(Long idDetalheNota) {
		this.idDetalheNota = idDetalheNota;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public NotaFiscal getNotaFiscal() {
		return notaFiscal;
	}

	public void setNotaFiscal(NotaFiscal notaFiscal) {
		this.notaFiscal = notaFiscal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idDetalheNota == null) ? 0 : idDetalheNota.hashCode());
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
		DetalheNota other = (DetalheNota) obj;
		if (idDetalheNota == null) {
			if (other.idDetalheNota != null)
				return false;
		} else if (!idDetalheNota.equals(other.idDetalheNota))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DetalheNota [idDetalheNota=" + idDetalheNota
				+ ", dataVencimento=" + dataVencimento + "]";
	}

}