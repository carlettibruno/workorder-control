package org.csi.controle.core.entidade;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.csi.controle.core.serialize.StatusDeserialize;
import org.csi.controle.core.serialize.StatusSerialize;

@Entity
@Table(name="ORDEM_SERVICO")
public class OrdemServico implements EntidadeControlada {

	private static final long serialVersionUID = 4529067495775640613L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_ORDEM_SERVICO")
	private Long idOrdemServico;
	
	@Column(name="NUMERO")
	private String numero;
	
	@Column(name="DESCRICAO", nullable=false)
	private String descricao;
	
	@Column(name="DATA_ENTREGA_PREVISAO")
	private Date dataEntregaPrevisao;
	
	@Column(name="EMAIL")
	private String email;
	
	@Column(name="DATA_CRIACAO", nullable=false)
	private Date dataCriacao;
	
	@Column(name="DATA_MODIFICACAO")
	private Date dataModificacao;
	
	@Column(name="ATIVO", nullable=false)
	private Boolean ativo;
	
	@JsonIgnore
	@OneToMany(mappedBy="ordemServico", fetch=FetchType.LAZY, cascade=CascadeType.REMOVE)
	private List<Foto> fotos;
	
	@JsonIgnore
	@OneToMany(mappedBy="ordemServico", fetch=FetchType.LAZY, cascade=CascadeType.REMOVE)
	private List<Historico> historicos;	
	
	@JsonIgnore
	@OneToMany(mappedBy="ordemServico", fetch=FetchType.LAZY, cascade=CascadeType.REMOVE)
	private List<EnderecoEntrega> enderecosEntrega;
	
	@ManyToOne
	@JoinColumn(name="ID_CLIENTE", referencedColumnName="ID_CLIENTE")
	private Cliente cliente;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="ID_NOTA_FISCAL", referencedColumnName="ID_NOTA_FISCAL")
	private NotaFiscal notaFiscal;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="ID_FUNCIONARIO", referencedColumnName="ID_USUARIO")
	private Funcionario funcionarioCriacao;

	@JsonIgnore
	@ManyToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="ID_HISTORICO_ATUAL", referencedColumnName="ID_HISTORICO")
	private Historico historicoAtual;
	
	@JsonSerialize(using=StatusSerialize.class)
	@JsonDeserialize(using=StatusDeserialize.class)
	@Column(name="STATUS", nullable=false)
	private Status status;
	
	@ManyToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="ID_FOTO", referencedColumnName="ID_FOTO")
	private Foto foto;
	
	@Column(name="PREVISAO_ENTREGA")
	private Date previsaoEntrega;
	
	@Transient
	private String diferencaDatas;
	
	@Transient
	private String nomeEtapaAtual;	
	
	public Long getIdOrdemServico() {
		return idOrdemServico;
	}

	public void setIdOrdemServico(Long idOrdemServico) {
		this.idOrdemServico = idOrdemServico;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDataEntregaPrevisao() {
		return dataEntregaPrevisao;
	}

	public void setDataEntregaPrevisao(Date dataEntregaPrevisao) {
		this.dataEntregaPrevisao = dataEntregaPrevisao;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idOrdemServico == null) ? 0 : idOrdemServico.hashCode());
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
		OrdemServico other = (OrdemServico) obj;
		if (idOrdemServico == null) {
			if (other.idOrdemServico != null)
				return false;
		} else if (!idOrdemServico.equals(other.idOrdemServico))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OrdemServico [idOrdemServico=" + idOrdemServico + ", numero="
				+ numero + ", descricao=" + descricao
				+ ", dataEntregaPrevisao=" + dataEntregaPrevisao + ", email="
				+ email + ", dataCriacao=" + dataCriacao + ", dataModificacao="
				+ dataModificacao + ", ativo=" + ativo + "]";
	}

	@Override
	public Long getId() {
		return idOrdemServico;
	}

	@Override
	public void setId(Long id) {
		this.idOrdemServico = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public NotaFiscal getNotaFiscal() {
		return notaFiscal;
	}

	public void setNotaFiscal(NotaFiscal notaFiscal) {
		this.notaFiscal = notaFiscal;
	}

	public Funcionario getFuncionarioCriacao() {
		return funcionarioCriacao;
	}

	public void setFuncionarioCriacao(Funcionario funcionarioCriacao) {
		this.funcionarioCriacao = funcionarioCriacao;
	}

	public Historico getHistoricoAtual() {
		return historicoAtual;
	}

	public void setHistoricoAtual(Historico historicoAtual) {
		this.historicoAtual = historicoAtual;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Foto getFoto() {
		return foto;
	}

	public void setFoto(Foto foto) {
		this.foto = foto;
	}

	public String getDiferencaDatas() {
		return diferencaDatas;
	}

	public void setDiferencaDatas(String diferencaDatas) {
		this.diferencaDatas = diferencaDatas;
	}

	public List<Foto> getFotos() {
		return fotos;
	}

	public void setFotos(List<Foto> fotos) {
		this.fotos = fotos;
	}

	public List<EnderecoEntrega> getEnderecosEntrega() {
		return enderecosEntrega;
	}

	public void setEnderecosEntrega(List<EnderecoEntrega> enderecosEntrega) {
		this.enderecosEntrega = enderecosEntrega;
	}

	public List<Historico> getHistoricos() {
		return historicos;
	}

	public void setHistoricos(List<Historico> historicos) {
		this.historicos = historicos;
	}

	public String getNomeEtapaAtual() {
		return nomeEtapaAtual;
	}

	public void setNomeEtapaAtual(String nomeEtapaAtual) {
		this.nomeEtapaAtual = nomeEtapaAtual;
	}

	public Date getPrevisaoEntrega() {
		return previsaoEntrega;
	}

	public void setPrevisaoEntrega(Date previsaoEntrega) {
		this.previsaoEntrega = previsaoEntrega;
	}
	
}