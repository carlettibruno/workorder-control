package org.csi.rastreamento.correios.manager;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.StringEscapeUtils;
import org.csi.rastreamento.correios.entidade.Evento;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class RastreioManager {

	public static void main(String[] args) {
		new RastreioManager().obterEventos("RC582273316CN");
	}
	
	public List<Evento> obterEventos(String codigoObjeto) {
		List<Evento> eventos = new ArrayList<Evento>();
		try {
			String html = obterHtml(codigoObjeto);
			InputStream is = new ByteArrayInputStream(html.getBytes());
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(is);
			//get the root element
			Element docEle = doc.getDocumentElement();

			NodeList trs = docEle.getElementsByTagName("tr");
			if(trs != null && trs.getLength() > 0) {
				for(int i = 0 ; i < trs.getLength();i++) {
					Element el = (Element)trs.item(i);
					NodeList tds = el.getElementsByTagName("td");
					if(tds != null && tds.getLength() > 0) {
						eventos.add(new Evento(StringEscapeUtils.unescapeHtml4(tds.item(0).getTextContent()), StringEscapeUtils.unescapeHtml4(tds.item(1).getTextContent())));
					}
				}
			}
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return eventos;
	}
	
	private String obterHtml(String codigoObjeto) {		
		StringBuilder html = new StringBuilder("");
//		StringBuilder html = new StringBuilder("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
	    try {
	    	
	    	String urlStr = "http://www2.correios.com.br/sistemas/rastreamento/resultado.cfm";
	    	String urlParameters = "objetos=" + URLEncoder.encode(codigoObjeto, "UTF-8");
	    	
	    	URL url = new URL(urlStr);
	    	HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    	conn.setRequestMethod("POST");
	    	conn.setDoOutput(true);
	    	
	    	OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
	    	
	    	writer.write(urlParameters);
	    	writer.flush();
	    	String line;
	    	BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	    	boolean iniciou = false;
	    	while ((line = reader.readLine()) != null) {
	    		boolean finalizou = false;
	    		if(iniciou) {
	    			finalizou = line.contains("</table>");
	    		} else {
	    			iniciou = line.contains("<table class=\"listEvent sro\">");
	    		}
	    		if(iniciou && !finalizou) {
	    			html.append(line+"\r\n");
	    		} else if(finalizou) {
	    			html.append(line);
	    			break;
	    		}
	    	}
	    	writer.close();
			reader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}			
		
		return html.toString().replace("&", "&amp;");		
	}
	
}