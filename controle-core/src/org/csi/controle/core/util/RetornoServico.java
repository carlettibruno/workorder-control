package org.csi.controle.core.util;

import java.io.Serializable;

public class RetornoServico<T> implements Serializable {

	private static final long serialVersionUID = 7280687457528577942L;

	private Integer codigo;
	
	private String mensagem;
	
	private T data;

	public RetornoServico() {
		super();
	}

	public RetornoServico(Integer codigo) {
		super();
		this.codigo = codigo;
	}

	public RetornoServico(Integer codigo, String mensagem, T data) {
		super();
		this.codigo = codigo;
		this.mensagem = mensagem;
		this.data = data;
	}

	public RetornoServico(Integer codigo, String mensagem) {
		super();
		this.codigo = codigo;
		this.mensagem = mensagem;
	}

	public RetornoServico(Integer codigo, T data) {
		super();
		this.codigo = codigo;
		this.data = data;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}