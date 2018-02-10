package org.csi.controle.core.entidade;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="FUNCIONARIO")
@PrimaryKeyJoinColumn(name="ID_FUNCIONARIO", referencedColumnName="ID_USUARIO")
public class Funcionario extends Usuario {

	private static final long serialVersionUID = 2616775594019377054L;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="FUNCIONARIO_GRUPO",
		joinColumns=@JoinColumn(name="ID_FUNCIONARIO", referencedColumnName="ID_USUARIO"),
		inverseJoinColumns=@JoinColumn(name="ID_GRUPO", referencedColumnName="ID_GRUPO"))	
	private List<Grupo> grupos;

	public List<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}

}