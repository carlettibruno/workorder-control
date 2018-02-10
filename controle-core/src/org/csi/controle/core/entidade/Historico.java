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
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.csi.controle.core.serialize.StatusDeserialize;
import org.csi.controle.core.serialize.StatusSerialize;

@Entity
@Table(name="HISTORICO")
public class Historico implements Serializable {

	private static final long serialVersionUID = 644952456825496224L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_HISTORICO")	
	private Long idHistorico;
	
	@Column(name="DATA_INICIO", nullable=false)
	private Date dataInicio;
	
	@Column(name="DATA_FIM")
	private Date dataFim;
	
	@JsonSerialize(using=StatusSerialize.class)
	@JsonDeserialize(using=StatusDeserialize.class)
	@Column(name="STATUS", nullable=false)
	private Status status;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="ID_ORDEM_SERVICO", referencedColumnName="ID_ORDEM_SERVICO")
	private OrdemServico ordemServico;
	
	@ManyToOne
	@JoinColumn(name="ID_ETAPA", referencedColumnName="ID_ETAPA")
	private Etapa etapa;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="ID_HISTORICO_PROXIMO", referencedColumnName="ID_HISTORICO")
	private Historico proximo;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="ID_HISTORICO_ANTERIOR", referencedColumnName="ID_HISTORICO")
	private Historico anterior;
	
	@Transient
	private String diferencaDatas;

	public Long getIdHistorico() {
		return idHistorico;
	}

	public void setIdHistorico(Long idHistorico) {
		this.idHistorico = idHistorico;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idHistorico == null) ? 0 : idHistorico.hashCode());
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
		Historico other = (Historico) obj;
		if (idHistorico == null) {
			if (other.idHistorico != null)
				return false;
		} else if (!idHistorico.equals(other.idHistorico))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Historico [idHistorico=" + idHistorico + ", dataInicio="
				+ dataInicio + ", dataFim=" + dataFim + "]";
	}

	public OrdemServico getOrdemServico() {
		return ordemServico;
	}

	public void setOrdemServico(OrdemServico ordemServico) {
		this.ordemServico = ordemServico;
	}

	public Etapa getEtapa() {
		return etapa;
	}

	public void setEtapa(Etapa etapa) {
		this.etapa = etapa;
	}

	public Historico getProximo() {
		return proximo;
	}

	public void setProximo(Historico proximo) {
		this.proximo = proximo;
	}

	public Historico getAnterior() {
		return anterior;
	}

	public void setAnterior(Historico anterior) {
		this.anterior = anterior;
	}

	public String getDiferencaDatas() {
		return diferencaDatas;
	}

	public void setDiferencaDatas(String diferencaDatas) {
		this.diferencaDatas = diferencaDatas;
	}

}