package org.csi.controle.core.entidade;

import java.io.Serializable;
import java.util.Date;

public interface EntidadeControlada extends Serializable {

	Long getId();
	void setId(Long id);
	Date getDataCriacao();
	void setDataCriacao(Date dataCriacao);
	Date getDataModificacao();
	void setDataModificacao(Date dataModificacao);
	Boolean getAtivo();
	void setAtivo(Boolean ativo);
	
}