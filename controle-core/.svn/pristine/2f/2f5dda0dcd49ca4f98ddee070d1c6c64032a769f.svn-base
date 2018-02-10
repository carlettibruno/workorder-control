package org.csi.controle.core.entidade;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class Usuario implements EntidadeControlada {
	
	private static final long serialVersionUID = 4317343201347226144L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name="ID_USUARIO")
	private Integer idUsuario;
	
	@Column(name="LOGIN", nullable=false)
	private String login;
	
	@Column(name="SENHA")
	private String senha;
	
	@Column(name="EMAIL")
	private String email;
	
	@Column(name="NOME", nullable=false)
	private String nome;

	@Column(name="DATA_ULTIMO_ACESSO")
	private Date dataUltimoAcesso;
	
	@Column(name="DATA_CRIACAO", nullable=false)
	private Date dataCriacao;
	
	@Column(name="DATA_MODIFICACAO")
	private Date dataModificacao;
	
	@Column(name="ATIVO", nullable=false)
	private Boolean ativo;

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getLogin() {
		return login.toUpperCase();
	}

	public void setLogin(String login) {
		this.login = login.toUpperCase();
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataUltimoAcesso() {
		return dataUltimoAcesso;
	}

	public void setDataUltimoAcesso(Date dataUltimoAcesso) {
		this.dataUltimoAcesso = dataUltimoAcesso;
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
				+ ((idUsuario == null) ? 0 : idUsuario.hashCode());
		return result;
	}

	@Override
	public Long getId() {
		return idUsuario != null ? idUsuario.longValue() : null;
	}

	@Override
	public void setId(Long id) {
		this.idUsuario = id != null ? Integer.parseInt(id.toString()) : null;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (idUsuario == null) {
			if (other.idUsuario != null)
				return false;
		} else if (!idUsuario.equals(other.idUsuario))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Usuario [idUsuario=" + idUsuario + ", login=" + login
				+ ", senha=" + senha + ", email=" + email + ", nome=" + nome
				+ ", dataUltimoAcesso=" + dataUltimoAcesso + ", dataCriacao="
				+ dataCriacao + ", dataModificacao=" + dataModificacao
				+ ", ativo=" + ativo + "]";
	}

	
}