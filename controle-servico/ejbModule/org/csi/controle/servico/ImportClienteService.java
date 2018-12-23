package org.csi.controle.servico;

import java.io.InputStream;

import javax.ejb.Local;

import org.csi.controle.core.entidade.Envio;

@Local
public interface ImportClienteService {

	void inserirClientes(InputStream is, Envio envio) throws Exception;

}