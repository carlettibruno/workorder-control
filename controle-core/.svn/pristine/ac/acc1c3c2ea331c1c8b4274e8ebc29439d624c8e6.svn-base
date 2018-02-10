package org.csi.controle.core.entidade;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="GRUPO")
public class Grupo implements EntidadeControlada {

	private static final long serialVersionUID = -8526906416707486914L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_GRUPO")
	private Integer idGrupo;
	
	@Column(name="NOME", nullable=false)
	private String nome;
	
	@Column(name="DATA_CRIACAO", nullable=false)
	private Date dataCriacao;
	
	@Column(name="DATA_MODIFICACAO")
	private Date dataModificacao;
	
	@Column(name="ATIVO", nullable=false)
	private Boolean ativo;

	public Integer getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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
		result = prime * result + ((idGrupo == null) ? 0 : idGrupo.hashCode());
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
		Grupo other = (Grupo) obj;
		if (idGrupo == null) {
			if (other.idGrupo != null)
				return false;
		} else if (!idGrupo.equals(other.idGrupo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Grupo [idGrupo=" + idGrupo + ", nome=" + nome
				+ ", dataCriacao=" + dataCriacao + ", dataModificacao="
				+ dataModificacao + ", ativo=" + ativo + "]";
	}

	@Override
	public Long getId() {
		return idGrupo != null ? idGrupo.longValue() : null;
	}

	@Override
	public void setId(Long id) {
		this.idGrupo = id != null ? Integer.parseInt(id.toString()) : null;
	}

}