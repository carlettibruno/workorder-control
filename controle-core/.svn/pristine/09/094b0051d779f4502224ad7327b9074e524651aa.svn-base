package org.csi.controle.core.entidade;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ETAPA")
public class Etapa implements EntidadeControlada {
	
	private static final long serialVersionUID = -8673182792218544316L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_ETAPA")	
	private Integer idEtapa;
	
	@Column(name="NOME", nullable=false)
	private String nome;
	
	@Column(name="IP")
	private String ip;
	
	@Column(name="DISPONIVEL")
	private Boolean disponivel;
	
	@Column(name="AUTOMATICA", nullable=false)
	private Boolean automatica;
	
	@Column(name="ETAPA_ENTREGA")
	private Boolean etapaEntrega;
	
	@Column(name="ETAPA_INICIAL")
	private Boolean etapaInicial;
	
	@Column(name="DATA_CRIACAO", nullable=false)
	private Date dataCriacao;
	
	@Column(name="DATA_MODIFICACAO")
	private Date dataModificacao;
	
	@Column(name="IP_EXTERNO")
	private String ipExterno;
	
	private Boolean ativo;
	
	@Column(name="DATA_ULTIMA_ATUALIZACAO")
	private Date dataUltimaAtualizacao;

	public Integer getIdEtapa() {
		return idEtapa;
	}

	public void setIdEtapa(Integer idEtapa) {
		this.idEtapa = idEtapa;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Boolean getDisponivel() {
		return disponivel;
	}

	public void setDisponivel(Boolean disponivel) {
		this.disponivel = disponivel;
	}

	public Boolean getAutomatica() {
		return automatica;
	}

	public void setAutomatica(Boolean automatica) {
		this.automatica = automatica;
	}

	public Boolean getEtapaEntrega() {
		return etapaEntrega;
	}

	public void setEtapaEntrega(Boolean etapaEntrega) {
		this.etapaEntrega = etapaEntrega;
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

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	
	@Override
	public Long getId() {
		return idEtapa != null ? idEtapa.longValue() : null;
	}

	@Override
	public void setId(Long id) {
		this.idEtapa = id != null ? Integer.parseInt(id.toString()) : null;
	}

	public Boolean getEtapaInicial() {
		return etapaInicial;
	}

	public void setEtapaInicial(Boolean etapaInicial) {
		this.etapaInicial = etapaInicial;
	}

	public Date getDataUltimaAtualizacao() {
		return dataUltimaAtualizacao;
	}

	public void setDataUltimaAtualizacao(Date dataUltimaAtualizacao) {
		this.dataUltimaAtualizacao = dataUltimaAtualizacao;
	}

	public String getIpExterno() {
		return ipExterno;
	}

	public void setIpExterno(String ipExterno) {
		this.ipExterno = ipExterno;
	}

}
