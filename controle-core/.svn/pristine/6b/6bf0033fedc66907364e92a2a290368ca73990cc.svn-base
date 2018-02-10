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
@Table(name="REFERENCIA_ENTREGA")
public class ReferenciaEntrega implements Serializable, EntidadeControlada {

	private static final long serialVersionUID = -4310985198337643495L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_REFERENCIA_ENTREGA")
	private Long idReferenciaEntrega;
	
	@Column(name="CODIGO_REFERENCIA", nullable=false)
	private String codigoReferencia;
	
	@Column(name="TIPO_ENTREGA", nullable=false)
	private TipoEntrega tipoEntrega;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="ID_ENDERECO_ENTREGA", referencedColumnName="ID_ENDERECO")	
	private EnderecoEntrega enderecoEntrega;
	
	@Column(name="DATA_CRIACAO", nullable=false)
	private Date dataCriacao;
	
	@Column(name="STATUS")
	private Status status;

	public Long getIdReferenciaEntrega() {
		return idReferenciaEntrega;
	}

	public void setIdReferenciaEntrega(Long idReferenciaEntrega) {
		this.idReferenciaEntrega = idReferenciaEntrega;
	}

	public String getCodigoReferencia() {
		return codigoReferencia;
	}

	public void setCodigoReferencia(String codigoReferencia) {
		this.codigoReferencia = codigoReferencia;
	}

	public TipoEntrega getTipoEntrega() {
		return tipoEntrega;
	}

	public void setTipoEntrega(TipoEntrega tipoEntrega) {
		this.tipoEntrega = tipoEntrega;
	}

	public EnderecoEntrega getEnderecoEntrega() {
		return enderecoEntrega;
	}

	public void setEnderecoEntrega(EnderecoEntrega enderecoEntrega) {
		this.enderecoEntrega = enderecoEntrega;
	}

	@Override
	public Long getId() {
		return this.idReferenciaEntrega;
	}

	@Override
	public void setId(Long id) {
		this.idReferenciaEntrega = id;
	}

	@Override
	public Date getDataCriacao() {
		return this.dataCriacao;
	}

	@Override
	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	@Override
	public Date getDataModificacao() {
		return null;
	}

	@Override
	public void setDataModificacao(Date dataModificacao) {
	}

	@Override
	public Boolean getAtivo() {
		return true;
	}

	@Override
	public void setAtivo(Boolean ativo) {
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}