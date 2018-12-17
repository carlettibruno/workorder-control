package org.csi.controle.core.entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.csi.controle.core.serialize.MoedaDeserialize;
import org.csi.controle.core.serialize.MoedaSerialize;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name="NOTA_FISCAL")
public class NotaFiscal implements Serializable {

	private static final long serialVersionUID = -4032766300221316248L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_NOTA_FISCAL")
	private Long idNotaFiscal;

	@JsonDeserialize(using=MoedaDeserialize.class)
	@JsonSerialize(using=MoedaSerialize.class)
	@Column(name="VALOR", nullable=false)
	private Double valor;
	
	@Column(name="NUMERO")
	private Long numero;
	
	@Column(name="PAGA")
	private Boolean paga;
	
	@OneToMany
	@JoinColumn(name="ID_NOTA_FISCAL")
	private List<DetalheNota> detalhesNota;

	public NotaFiscal() {
	}
	
	public NotaFiscal(Double valor, Long numero, Boolean paga) {
		super();
		this.valor = valor;
		this.numero = numero;
		this.paga = paga;
	}



	public NotaFiscal(Double valor, Date dataVencimento, Long numero, Boolean paga) {
		super();
		this.valor = valor;
		this.numero = numero;
		this.detalhesNota = new ArrayList<DetalheNota>();
		this.detalhesNota.add(new DetalheNota(dataVencimento, this));
		this.paga = paga;
	}

	public Long getIdNotaFiscal() {
		return idNotaFiscal;
	}

	public void setIdNotaFiscal(Long idNotaFiscal) {
		this.idNotaFiscal = idNotaFiscal;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idNotaFiscal == null) ? 0 : idNotaFiscal.hashCode());
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
		NotaFiscal other = (NotaFiscal) obj;
		if (idNotaFiscal == null) {
			if (other.idNotaFiscal != null)
				return false;
		} else if (!idNotaFiscal.equals(other.idNotaFiscal))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "NotaFiscal [idNotaFiscal=" + idNotaFiscal + ", valor=" + valor
				+ ", numero=" + numero + "]";
	}

	public List<DetalheNota> getDetalhesNota() {
		return detalhesNota;
	}

	public void setDetalhesNota(List<DetalheNota> detalhesNota) {
		this.detalhesNota = detalhesNota;
	}

	public Boolean getPaga() {
		return paga;
	}

	public void setPaga(Boolean paga) {
		this.paga = paga;
	}

	public void addDetalheNota(DetalheNota detalheNota) {
		if(this.detalhesNota == null) {
			this.detalhesNota = new ArrayList<DetalheNota>();
		}
		this.detalhesNota.add(detalheNota);
	}

}