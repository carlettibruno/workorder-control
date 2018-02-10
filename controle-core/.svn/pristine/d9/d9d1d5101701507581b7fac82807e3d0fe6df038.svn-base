package org.csi.controle.core.entidade;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="SESSAO")
public class Sessao implements Serializable {

	private static final long serialVersionUID = 580897497846055873L;
	
	@Id
	@Column(name="CODIGO")
	private String codigo;
	
	@Column(name="DATA_CRIACAO", nullable=false)
	private Date dataCriacao;
	
	@Column(name="DATA_ATUALIZACAO", nullable=false)
	private Date dataAtualizacao;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_USUARIO", referencedColumnName="ID_USUARIO")	
	private Usuario usuario;
	
	@Column(name="DURACAO", nullable=false)
	private Integer duracao;
	
	@Column(name="VALIDAR_PERMISSAO")
	private Boolean validarPermissao;	
	
	@Column(name="PERMISSOES", nullable=false)
	private String permissoes;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Integer getDuracao() {
		return duracao;
	}

	public void setDuracao(Integer duracao) {
		this.duracao = duracao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
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
		Sessao other = (Sessao) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Sessao [codigo=" + codigo + ", dataCriacao=" + dataCriacao
				+ ", usuario=" + usuario + ", duracao=" + duracao + "]";
	}

	public String getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(String permissoes) {
		this.permissoes = permissoes;
	}

	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public Boolean getValidarPermissao() {
		return validarPermissao;
	}

	public void setValidarPermissao(Boolean validarPermissao) {
		this.validarPermissao = validarPermissao;
	}

}