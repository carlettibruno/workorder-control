package org.csi.controle.core.entidade;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="ENVIO")
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class Envio implements Serializable {

	private static final long serialVersionUID = -8653200810900781586L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name="ID_ENVIO")	
	private Integer idEnvio;
	
	@Column(name="TOTAL", nullable=false)
	private Integer total;
	
	@Column(name="QTDE_CARREGADA", nullable=false)
	private Integer qtdeCarregada;
	
	@Column(name="ENTIDADE", nullable=false)
	private Entidade entidade;

	@Column(name="DATA_CRIACAO")
	private Date dataCriacao;	
	
	@JsonIgnore
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinTable(name="ENVIO_ERRO", 
    	joinColumns={@JoinColumn(name="ID_ENVIO", referencedColumnName="ID_ENVIO")}, 
    	inverseJoinColumns={@JoinColumn(name="ID_ERRO", referencedColumnName="ID_ERRO")})
	private List<Erro> erros;

	public Integer getIdEnvio() {
		return idEnvio;
	}

	public void setIdEnvio(Integer idEnvio) {
		this.idEnvio = idEnvio;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getQtdeCarregada() {
		return qtdeCarregada;
	}

	public void setQtdeCarregada(Integer qtdeCarregada) {
		this.qtdeCarregada = qtdeCarregada;
	}

	public Entidade getEntidade() {
		return entidade;
	}

	public void setEntidade(Entidade entidade) {
		this.entidade = entidade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idEnvio == null) ? 0 : idEnvio.hashCode());
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
		Envio other = (Envio) obj;
		if (idEnvio == null) {
			if (other.idEnvio != null)
				return false;
		} else if (!idEnvio.equals(other.idEnvio))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Envio [idEnvio=" + idEnvio + ", total=" + total
				+ ", qtdeCarregada=" + qtdeCarregada + ", entidade=" + entidade
				+ "]";
	}

	public List<Erro> getErros() {
		return erros;
	}

	public void setErros(List<Erro> erros) {
		this.erros = erros;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

}
