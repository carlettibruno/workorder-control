//package org.csi.rastreamento.correios.manager;
//
//import java.io.IOException;
//import java.rmi.RemoteException;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.csi.rastreamento.correios.entidade.Evento;
//
//import br.com.correios.webservice.resource.Eventos;
//import br.com.correios.webservice.resource.Objeto;
//import br.com.correios.webservice.resource.ServiceProxy;
//import br.com.correios.webservice.resource.Sroxml;
//
//public class RastreioManager {
//
//	public List<Evento> obterEventos(String codigoObjeto, String user, String password) {
//		List<Evento> eventos = new ArrayList<Evento>();
//		try {
//			eventos = obterEventosWsdl(codigoObjeto, user, password);
////		} catch (SAXException e) {
////			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
////		} catch (ParserConfigurationException e) {
////			e.printStackTrace();
//		}
//		return eventos;
//	}
//
//	private List<Evento> obterEventosWsdl(String codigo, String user, String password) throws RemoteException {
//		ServiceProxy service = new ServiceProxy();
//		Sroxml sroxml = service.buscaEventos(user, password, "L", "T", "101", codigo.toUpperCase());
//		Objeto obj = sroxml.getObjeto(0);
//
//		List<Evento> eventosRet = new ArrayList<>();
//		Eventos[] eventos = obj.getEvento();
//		if(eventos == null) {
//			throw new IllegalArgumentException("FAILED TO GET EVENTS: "+obj.getErro());
//		}
//		for (Eventos evento : eventos) {
//			Evento e = new Evento();
//			e.setTitulo(evento.getLocal());
//			e.setDescricao(evento.getDescricao());
//			eventosRet.add(e);
//		}
//		return eventosRet;
//	}
//
//}
