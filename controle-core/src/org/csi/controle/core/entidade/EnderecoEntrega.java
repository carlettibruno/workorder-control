package org.csi.controle.core.entidade;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.csi.controle.core.serialize.StatusDeserialize;
import org.csi.controle.core.serialize.StatusSerialize;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name="ENDERECO_ENTREGA")
public class EnderecoEntrega extends Endereco {

	private static final long serialVersionUID = 4890931785946058649L;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="ID_ORDEM_SERVICO", referencedColumnName="ID_ORDEM_SERVICO")
	private OrdemServico ordemServico;
	
	@OneToMany(mappedBy="enderecoEntrega", fetch=FetchType.EAGER, cascade=CascadeType.REMOVE)
	private List<ReferenciaEntrega> referenciasEntrega;
	
	@JsonSerialize(using=StatusSerialize.class)
	@JsonDeserialize(using=StatusDeserialize.class)
	@Column(name="STATUS")	
	private Status status;
	
	public EnderecoEntrega() {
		super();
	}

	public EnderecoEntrega(Endereco endereco) {
		super(null, 
				endereco.getEndereco(),
				endereco.getCep(), 
				endereco.getComplemento(), 
				endereco.getBairro(), 
				endereco.getCidade(), 
				endereco.getEstado(),
				endereco.getTitulo());
	}

	public OrdemServico getOrdemServico() {
		return ordemServico;
	}

	public void setOrdemServico(OrdemServico ordemServico) {
		this.ordemServico = ordemServico;
	}

	public List<ReferenciaEntrega> getReferenciasEntrega() {
		return referenciasEntrega;
	}

	public void setReferenciasEntrega(List<ReferenciaEntrega> referenciasEntrega) {
		this.referenciasEntrega = referenciasEntrega;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}