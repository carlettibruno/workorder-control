package org.csi.rastreamento.correios.manager;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.csi.rastreamento.correios.entidade.Evento;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import jadlogEdiws.TrackingBeanProxy;

public class JadlogRastreio implements Rastreio {

	@Override
	public List<Evento> obterEventos(String codigoObjeto, String user, String password, String wsdl) {
		List<Evento> eventos = new ArrayList<Evento>();
		try {
			eventos = obterEventosWsdl(codigoObjeto, user, password, wsdl);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return eventos;
	}

	private List<Evento> obterEventosWsdl(String codigo, String user, String password, String wsdl) throws Exception {
		String xml = "";
		try {
			TrackingBeanProxy service = new TrackingBeanProxy(wsdl);
			xml = service.consultar(user, password, codigo);
		} catch (Exception e) {
			TrackingBeanProxy service = new TrackingBeanProxy(wsdl.replace(".com.br", ".com.br:8080"));
			xml = service.consultar(user, password, codigo);
		}
		InputStream is = new ByteArrayInputStream(xml.getBytes());
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(is);
		//get the root element
		Element docEle = doc.getDocumentElement();
		System.out.println(xml);

		List<Evento> eventos = new ArrayList<Evento>();
		NodeList trs = docEle.getElementsByTagName("Evento");
		if(trs != null && trs.getLength() > 0) {
			for(int i = 0 ; i < trs.getLength();i++) {
				Element el = (Element)trs.item(i);
				NodeList nodeLocal = el.getElementsByTagName("Observacao");
				NodeList nodeDescricao = el.getElementsByTagName("Descricao");
				eventos.add(new Evento(nodeLocal.item(0).getTextContent().trim(), nodeDescricao.item(0).getTextContent().trim()));
			}
		}

		return eventos;
	}

	@Override
	public String getTokenUsuario() {
		return "JADLOG_USUARIO";
	}

	@Override
	public String getTokenSenha() {
		return "JADLOG_SENHA";
	}

	@Override
	public String getTokenWsdl() {
		return "JADLOG_WSDL";
	}

}
