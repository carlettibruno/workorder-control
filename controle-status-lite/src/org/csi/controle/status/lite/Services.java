package org.csi.controle.status.lite;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

public class Services {
	
	private static Services instancia;

	private String token;

	private String baseUrl;

	private Services() throws IOException {
		super();
	}

	public static Services getInstancia(String baseUrl) throws IOException {
		if(instancia == null) {
			instancia = new Services();
			instancia.token = Constants.KEY;
			instancia.baseUrl = baseUrl;
		}
		return instancia;
	}

	public void notificar() {
		try {
			String macAddress = Util.getMacAddress();
			request(this.baseUrl + "/etapa/notificar", "PUT", token, macAddress, "text/plain");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void mudarServico(Integer codigoOs) {
		try {
			String dataPost = String.format("{\"reference\":\"%s\",\"oscode\":\"%s\"}", Util.getMacAddress(), codigoOs);
			request(this.baseUrl + "/historico", "POST", token, dataPost, "application/json");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String request(String urlStr, String method, String token, String data, String contentType) throws Exception {
		URL url = new URL(urlStr);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestProperty("token", token);
		connection.setRequestMethod(method);
		connection.setUseCaches(false);
		connection.setDoInput(true);
		connection.setDoOutput(true);

		if(data != null) {
			connection.setRequestProperty("Content-Type", contentType);
			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			wr.writeBytes(data);
			wr.flush ();
			wr.close ();
		}

		BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream(),Charset.forName("UTF-8")));
		String response = "";
		String line = "";
		while ((line = rd.readLine()) != null) {
			response += line;
		}
		rd.close();
		return response;
	}

}