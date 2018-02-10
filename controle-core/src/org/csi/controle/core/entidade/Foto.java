package org.csi.controle.core.entidade;

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

@Entity
@Table(name="FOTO")
public class Foto implements EntidadeControlada {

	private static final long serialVersionUID = -1142770133642846867L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_FOTO")
	private Long idFoto;
	
	@Column(name="CAMINHO", nullable=false)
	private String caminho;
	
	@Column(name="TAMANHO", nullable=false)
	private Integer tamanho;
	
	@Column(name="ATIVO", nullable=false)
	private Boolean ativo;
	
	@Column(name="NOME")
	private String nome;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="ID_ENVIO_FOTO", referencedColumnName="ID_ENVIO")
	private EnvioFoto envioFoto;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="ID_ORDEM_SERVICO", referencedColumnName="ID_ORDEM_SERVICO")
	private OrdemServico ordemServico;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="ID_FUNCIONARIO", referencedColumnName="ID_USUARIO")
	private Funcionario funcionarioCriacao;

	@Column(name="DATA_CRIACAO", nullable=false)
	private Date dataCriacao;
	
	@Column(name="DATA_MODIFICACAO")
	private Date dataModificacao;
	
	@Column(name="APROVADA")
	private Boolean aprovada;	
	
	@Transient
	private String caminhoCompleto;
	
	@Transient
	private String caminhoCompletoThumb;	
	
	public Long getIdFoto() {
		return idFoto;
	}

	public void setIdFoto(Long idFoto) {
		this.idFoto = idFoto;
	}

	public String getCaminho() {
		return caminho;
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public EnvioFoto getEnvioFoto() {
		return envioFoto;
	}

	public void setEnvioFoto(EnvioFoto envioFoto) {
		this.envioFoto = envioFoto;
	}

	public OrdemServico getOrdemServico() {
		return ordemServico;
	}

	public void setOrdemServico(OrdemServico ordemServico) {
		this.ordemServico = ordemServico;
	}

	public Funcionario getFuncionarioCriacao() {
		return funcionarioCriacao;
	}

	public void setFuncionarioCriacao(Funcionario funcionarioCriacao) {
		this.funcionarioCriacao = funcionarioCriacao;
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
		return idFoto;
	}

	@Override
	public void setId(Long id) {
		this.idFoto = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idFoto == null) ? 0 : idFoto.hashCode());
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
		Foto other = (Foto) obj;
		if (idFoto == null) {
			if (other.idFoto != null)
				return false;
		} else if (!idFoto.equals(other.idFoto))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Foto [idFoto=" + idFoto + ", caminho=" + caminho + ", tamanho="
				+ tamanho + ", nome=" + nome + "]";
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getTamanho() {
		return tamanho;
	}

	public void setTamanho(Integer tamanho) {
		this.tamanho = tamanho;
	}

	public String getCaminhoCompleto() {
		return caminhoCompleto;
	}

	public void setCaminhoCompleto(String caminhoCompleto) {
		this.caminhoCompleto = caminhoCompleto;
	}

	public String getCaminhoCompletoThumb() {
		return caminhoCompletoThumb;
	}

	public void setCaminhoCompletoThumb(String caminhoCompletoThumb) {
		this.caminhoCompletoThumb = caminhoCompletoThumb;
	}

	public Boolean getAprovada() {
		return aprovada;
	}

	public void setAprovada(Boolean aprovada) {
		this.aprovada = aprovada;
	}

}