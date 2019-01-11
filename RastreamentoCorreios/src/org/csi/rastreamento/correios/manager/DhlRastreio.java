package org.csi.rastreamento.correios.manager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.csi.rastreamento.correios.entidade.Evento;
import org.csi.rastreamento.correios.util.Constantes;

public class DhlRastreio implements Rastreio {

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
	
	public static void main(String[] args) {
		new DhlRastreio().obterEventos("6481267225", "v62_UBLbvmydcY", "Zvnk7OzOcy", "https://xmlpitest-ea.dhl.com/XMLShippingServlet?isUTF8Support=true");
	}

	private List<Evento> obterEventosWsdl(String codigo, String user, String password, String wsdl) throws Exception {
		String clientRequestXML = Constantes.XML_DHL.replace("{SITEID}", user).replace("{PASSWORD}", password).replace("{AWBNUMBER}", codigo);
		boolean useUtf8 = wsdl.contains("isUTF8Support");
		
		URL servletURL = new URL(wsdl);
		HttpURLConnection conn = null;
		conn = (HttpURLConnection) servletURL.openConnection();
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setUseCaches(false); 
		conn.setRequestMethod("POST");

		conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");	
		if(useUtf8) {
			conn.setRequestProperty("Accept-Charset", "UTF-8");
		}
		String len = Integer.toString(clientRequestXML.getBytes().length);
		conn.setRequestProperty("Content-Length", len);
		conn.setRequestProperty("futureDate", "false");

		conn.connect();
		OutputStreamWriter wr = null;
		if(useUtf8) {
			wr = new OutputStreamWriter(conn.getOutputStream(), "UTF8");
		} else {
			wr = new OutputStreamWriter(conn.getOutputStream());
		}
		wr.write(clientRequestXML);
		wr.flush();
		wr.close();

		InputStreamReader isr = null;
		if(useUtf8) {
			isr = new InputStreamReader(conn.getInputStream(),"UTF8");
		} else {
			isr = new InputStreamReader(conn.getInputStream());
		}
		BufferedReader rd = new BufferedReader(isr);
		StringBuilder result = new StringBuilder();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line).append("\n");
		}

		List<Evento> events = new ArrayList<>();
		Pattern pattern = Pattern.compile("<ShipmentEvent>(.*?)<\\/ShipmentEvent>");
		Matcher matcher = pattern.matcher(result.toString().replace("\n", ""));
		Pattern patternDescription = Pattern.compile("(<Description>)(.*?)(<\\/Description>)");
		while(matcher.find()) {
			String description = "";
			String title = "";
			String eventStr = matcher.group();
			Matcher matcherDescription = patternDescription.matcher(eventStr);
			if(matcherDescription.find()) {
				description = matcherDescription.group(2).replaceAll("\\s+", " ");
				matcherDescription.find();
				title = matcherDescription.group(2);
			}
			events.add(new Evento(title, description));
		}
		rd.close();
		isr.close();
		return events;
	}

	@Override
	public String getTokenUsuario() {
		return "DHL_USUARIO";
	}

	@Override
	public String getTokenSenha() {
		return "DHL_SENHA";
	}

	@Override
	public String getTokenWsdl() {
		return "DHL_WSDL";
	}

}
