package org.csi.controle.core.entidade;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="ENVIO_FOTO")
public class EnvioFoto extends Envio {

	private static final long serialVersionUID = 7143266187242598846L;
	
	@ManyToOne
	@JoinColumn(name="ID_ORDEM_SERVICO", referencedColumnName="ID_ORDEM_SERVICO")	
	private OrdemServico ordemServico;
	
	@ManyToOne
	@JoinColumn(name="ID_FUNCIONARIO", referencedColumnName="ID_USUARIO")	
	private Funcionario funcionarioCriacao;

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

	@Override
	public String toString() {
		return "EnvioFoto [ordemServico=" + ordemServico
				+ ", getIdEnvio()=" + getIdEnvio() + ", getTotal()="
				+ getTotal() + ", getQtdeCarregada()=" + getQtdeCarregada()
				+ ", getEntidade()=" + getEntidade() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString()
				+ ", getErros()=" + getErros() + ", getDataCriacao()="
				+ getDataCriacao() + ", getClass()=" + getClass() + "]";
	}

}