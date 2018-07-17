package org.csi.rastreamento.correios.manager;

import java.util.List;

import org.csi.rastreamento.correios.entidade.Evento;

public interface Rastreio {

	public List<Evento> obterEventos(String codigoObjeto, String user, String password, String wsdl);
	
	String getTokenUsuario();
	
	String getTokenSenha();
	
	String getTokenWsdl();
	
}
