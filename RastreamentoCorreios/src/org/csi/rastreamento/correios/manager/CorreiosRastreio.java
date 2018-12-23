package org.csi.rastreamento.correios.manager;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.csi.rastreamento.correios.entidade.Evento;

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

	private List<Evento> obterEventosWsdl(String codigo, String user, String password, String wsdl) throws IOException {
		wsdl = wsdl.replace("#{CODE}", codigo);
//		wsdl = "https://websro.com.br/";
		URL url = new URL(wsdl);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setConnectTimeout(2000);
		con.setReadTimeout(2000);	
		con.setRequestMethod("GET");
		con.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		con.setRequestProperty("Host", "www.websro.com.br");
		con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_2) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/12.0.2 Safari/605.1.15");
		con.setRequestProperty("Accept-Language", "en-us");
		con.setRequestProperty("Accept-Encoding", "br, gzip, deflate");
		con.setRequestProperty("Connection", "keep-alive");
//		con.setRequestProperty("Cookie", "_ga=GA1.3.627365304.1545400010; _gat=1; _gid=GA1.3.115962214.1545400014; PHPSESSID=1q5vj79sr5goh6f7ipua803ht4");
		con.setDoOutput(true);
		DataOutputStream out = new DataOutputStream(con.getOutputStream());
		out.flush();
		out.close();

		int status = con.getResponseCode();
		List<Evento> eventosRet = new ArrayList<>();
		if(status == 200) {
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			in.close();
//			System.out.println(content);
			String tbody = content.substring(content.indexOf("<tbody>"), content.indexOf("</tbody>"));
			String[] linhas = tbody.split("<tr>");
			Pattern pattern = Pattern.compile("<td.*?>(.*)<\\/td>");
			for (int i = 1; i < linhas.length; i++) {
				String[] colunas = linhas[i].split("<td>");
				String coltitulo = colunas[0].replaceAll("\t", "").replaceAll("\r", "").replaceAll("\n", "");
				Matcher mt = pattern.matcher(coltitulo);
				String titulo = "";
				if(mt.find()) {
					titulo = mt.group(1);
					titulo = titulo.replaceAll("<br/?>", " - ").replaceAll("</?label>", "").replaceAll("</?strong>", "");
				}
				
				String coldescricao = "<td>"+colunas[1].replaceAll("\t", "").replaceAll("\r", "").replaceAll("\n", "");
				mt = pattern.matcher(coldescricao);
				String descricao = "";
				if(mt.find()) {
					descricao = mt.group(1);
					descricao = descricao.replaceAll("<br/?>", " - ").replaceAll("</?label>", "").replaceAll("</?strong>", "");
				}
				eventosRet.add(new Evento(titulo, descricao));
			}
		} else {
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			in.close();
			System.out.println(content);
		}
		
		return eventosRet;
	}
	
	public static void main(String[] args) {
		try {
			new CorreiosRastreio().obterEventosWsdl("AA123456789BR", null, null, "https://websro.com.br/detalhes.php?P_COD_UNI=#{CODE}");
		} catch (IOException e) {
			e.printStackTrace();
		}
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
