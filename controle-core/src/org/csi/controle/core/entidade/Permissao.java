package org.csi.controle.core.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="PERMISSAO")
public class Permissao implements Serializable {

	private static final long serialVersionUID = -8271575394820661810L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_PERMISSAO")
	private Integer idPermissao;
	
	@ManyToOne
	@JoinColumn(name="ID_GRUPO", referencedColumnName="ID_GRUPO")
	private Grupo grupo;
	
	@Column(name="MODULO", nullable=false)
	private Modulo modulo;
	
	@Column(name="FUNCIONALIDADE", nullable=false)
	private Integer funcionalidade;

	public Integer getIdPermissao() {
		return idPermissao;
	}

	public void setIdPermissao(Integer idPermissao) {
		this.idPermissao = idPermissao;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Modulo getModulo() {
		return modulo;
	}

	public void setModulo(Modulo modulo) {
		this.modulo = modulo;
	}

	public Integer getFuncionalidade() {
		return funcionalidade;
	}

	public void setFuncionalidade(Integer funcionalidade) {
		this.funcionalidade = funcionalidade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idPermissao == null) ? 0 : idPermissao.hashCode());
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
		Permissao other = (Permissao) obj;
		if (idPermissao == null) {
			if (other.idPermissao != null)
				return false;
		} else if (!idPermissao.equals(other.idPermissao))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Permissao [idPermissao=" + idPermissao + ", modulo=" + modulo
				+ ", funcionalidade=" + funcionalidade + "]";
	}

}