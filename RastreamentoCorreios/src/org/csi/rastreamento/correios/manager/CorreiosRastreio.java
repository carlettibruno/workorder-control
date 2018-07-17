package org.csi.rastreamento.correios.manager;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.csi.rastreamento.correios.entidade.Evento;

import br.com.correios.webservice.resource.Eventos;
import br.com.correios.webservice.resource.Objeto;
import br.com.correios.webservice.resource.ServiceProxy;
import br.com.correios.webservice.resource.Sroxml;

public class CorreiosRastreio implements Rastreio {

	@Override
	public List<Evento> obterEventos(String codigoObjeto, String user, String password, String wsdl) {
		List<Evento> eventos = new ArrayList<Evento>();
		try {
			eventos = obterEventosWsdl(codigoObjeto, user, password, wsdl);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return eventos;
	}
	
	private List<Evento> obterEventosWsdl(String codigo, String user, String password, String wsdl) throws RemoteException {
		ServiceProxy service = new ServiceProxy(wsdl);
		Sroxml sroxml = service.buscaEventos(user, password, "L", "T", "101", codigo.toUpperCase());
		Objeto obj = sroxml.getObjeto(0);
		
		List<Evento> eventosRet = new ArrayList<>();
		Eventos[] eventos = obj.getEvento();
		if(eventos == null) {
			throw new IllegalArgumentException("FAILED TO GET EVENTS: "+obj.getErro());
		}
		for (Eventos evento : eventos) {
			Evento e = new Evento();
			e.setTitulo(evento.getLocal());
			e.setDescricao(evento.getDescricao());
			eventosRet.add(e);
		}
		return eventosRet;
	}

	@Override
	public String getTokenUsuario() {
		return "CORREIOS_USUARIO";
	}

	@Override
	public String getTokenSenha() {
		return "CORREIOS_SENHA";
	}

	@Override
	public String getTokenWsdl() {
		return "CORREIOS_WSDL";
	}	

}
