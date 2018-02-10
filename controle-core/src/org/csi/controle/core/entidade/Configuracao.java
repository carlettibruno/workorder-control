package org.csi.controle.core.entidade;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CONFIGURACAO")
public class Configuracao implements EntidadeControlada {

	private static final long serialVersionUID = 729352754001198396L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_CONFIGURACAO")	
	private Integer idConfiguracao;
	
	@Column(name="ATIVO", nullable=false)
	private Boolean ativo;
	
	@Column(name="VALOR", nullable=false)
	private String valor;
	
	@Column(name="CHAVE", nullable=false)
	private ChaveConfiguracao chave;
	
	@Column(name="DATA_CRIACAO", nullable=false)
	private Date dataCriacao;
	
	@Column(name="DATA_MODIFICACAO")
	private Date dataModificacao;

	public Integer getIdConfiguracao() {
		return idConfiguracao;
	}

	public void setIdConfiguracao(Integer idConfiguracao) {
		this.idConfiguracao = idConfiguracao;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public ChaveConfiguracao getChave() {
		return chave;
	}

	public void setChave(ChaveConfiguracao chave) {
		this.chave = chave;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Date getDataModificacao() {
		return dataModificacao;
	}

	public void setDataModificacao(Date dataModificacao) {
		this.dataModificacao = dataModificacao;
	}

	@Override
	public Long getId() {
		return idConfiguracao != null ? idConfiguracao.longValue() : null;
	}

	@Override
	public void setId(Long id) {
		this.idConfiguracao = id != null ? Integer.parseInt(id.toString()) : null;
	}

}
