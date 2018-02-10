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

@Entity
@Table(name="LOG")
public class Log implements Serializable {

	private static final long serialVersionUID = 2229606866191378575L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_LOG")
	private Long idLog;
	
	@Column(name="DESCRICAO", nullable=false)
	private String descricao;
	
	@Column(name="MODULO", nullable=false)
	private Integer modulo;
	
	@Column(name="FUNCIONALIDADE", nullable=false)
	private Integer funcionalidade;
	
	@Column(name="DATA", nullable=false)
	private Date data;
	
	@ManyToOne
	@JoinColumn(name="ID_USUARIO", referencedColumnName="ID_USUARIO")	
	private Usuario usuario;

	public Long getIdLog() {
		return idLog;
	}

	public void setIdLog(Long idLog) {
		this.idLog = idLog;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getModulo() {
		return modulo;
	}

	public void setModulo(Integer modulo) {
		this.modulo = modulo;
	}

	public Integer getFuncionalidade() {
		return funcionalidade;
	}

	public void setFuncionalidade(Integer funcionalidade) {
		this.funcionalidade = funcionalidade;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idLog == null) ? 0 : idLog.hashCode());
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
		Log other = (Log) obj;
		if (idLog == null) {
			if (other.idLog != null)
				return false;
		} else if (!idLog.equals(other.idLog))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Log [idLog=" + idLog + ", descricao=" + descricao + ", modulo="
				+ modulo + ", funcionalidade=" + funcionalidade + ", data="
				+ data + "]";
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}