package org.csi.controle.core.to;

import java.io.Serializable;

public class Login implements Serializable {

	private static final long serialVersionUID = -4455207507054156538L;

	private String usuario;
	
	private String senha;
	
	private String senhaNova;
	
	private String senhaNovaConfirmacao;
	
	private Boolean manterConectado;

	public String getUsuario() {
		return usuario.toUpperCase();
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario.toUpperCase();
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Boolean getManterConectado() {
		return manterConectado;
	}
	
	public boolean isManterConectado() {
		return manterConectado == null ? false : manterConectado;
	}

	public void setManterConectado(Boolean manterConectado) {
		this.manterConectado = manterConectado;
	}

	@Override
	public String toString() {
		return "Login [usuario=" + usuario + ", senha=" + senha
				+ ", manterConectado=" + manterConectado + "]";
	}

	public String getSenhaNova() {
		return senhaNova;
	}

	public void setSenhaNova(String senhaNova) {
		this.senhaNova = senhaNova;
	}

	public String getSenhaNovaConfirmacao() {
		return senhaNovaConfirmacao;
	}

	public void setSenhaNovaConfirmacao(String senhaNovaConfirmacao) {
		this.senhaNovaConfirmacao = senhaNovaConfirmacao;
	}

	
}