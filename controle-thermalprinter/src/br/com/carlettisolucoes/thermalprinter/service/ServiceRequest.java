package br.com.carlettisolucoes.thermalprinter.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.csi.controle.core.entidade.FilaImpressora;
import org.csi.controle.core.util.RetornoServico;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import br.com.carlettisolucoes.thermalprinter.util.Util;

public class ServiceRequest {

	private static ServiceRequest instance;

	private String token;

	private String urlInfoPrint;

//	private String urlLogin;

//	private ServiceRequest() {
//	}

//	private ServiceRequest(String user, String password) {
	private ServiceRequest() {
		try {
			urlInfoPrint = Util.getValor("service.getinfoprint.url");
//			urlLogin = Util.getValor("service.url");
			//token = gerarToken(user, password);
			token = "bcc8088ae11d78ac9b96a67b0423a07c";
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ServiceRequest getInstance() {
		if(instance == null) {
//			try {
//				String user = Util.getValor("service.user");
//				String password = Util.getValor("service.password");
//				instance = new ServiceRequest(user, password);
				instance = new ServiceRequest();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
		}
		return instance;
	}

//	@SuppressWarnings("unchecked")
//	private String gerarToken(String usuario, String senha) throws IOException {
//		Login login = new Login();
//		login.setUsuario(usuario);
//		login.setSenha(senha);
//		login.setManterConectado(true);
//		Gson gson = new Gson();
//		String jsonRequest = gson.toJson(login);
//		String json = request(urlLogin, "POST", "", jsonRequest);
//		RetornoServico<String> retorno = gson.fromJson(json, RetornoServico.class);
//		return retorno.getData();
//	}

	@SuppressWarnings("unchecked")
	public List<FilaImpressora> getListInfoPrint() {
		try {
			String json = request(urlInfoPrint, "GET", token, null);
			Gson gson = new Gson();
			Type type = new TypeToken<RetornoServico<List<FilaImpressora>>>() {}.getType();
			RetornoServico<List<FilaImpressora>> retorno = (RetornoServico<List<FilaImpressora>>) gson.fromJson(json, type);
			List<FilaImpressora> lista = retorno.getData();
			for (FilaImpressora filaImpressora : lista) {
				request(urlInfoPrint + "?fiid="+filaImpressora.getIdFilaImpressora(), "DELETE", token, null);
			}
			return lista;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ArrayList<FilaImpressora>();
	}

	public void removeFilaImpressora(FilaImpressora filaImpressora) {
		try {
			request(urlInfoPrint + "?fiid="+filaImpressora.getIdFilaImpressora(), "DELETE", token, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String request(String urlStr, String method, String token, String jsonPost) throws IOException {
		URL url = new URL(urlStr);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestProperty("token", token);
		connection.setRequestMethod(method);
		connection.setUseCaches(false);
		connection.setDoInput(true);
		if(!method.equals("DELETE")) {
			connection.setDoOutput(true);
		}

		if(jsonPost != null) {
		    connection.setRequestProperty("Content-Type", "application/json");
			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			wr.writeBytes(jsonPost.toString());
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
