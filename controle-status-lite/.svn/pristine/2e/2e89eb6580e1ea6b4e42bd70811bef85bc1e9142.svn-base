package org.csi.controle.status.lite;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.nio.charset.Charset;

import org.csi.controle.core.to.EtapaTO;
import org.csi.controle.core.to.Login;
import org.csi.controle.core.util.RetornoServico;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Services {

	private static Services instancia;
	
	private String token;
	
	private String URL;
	
	private String usuario;
	
	private String senha;
	
	private Services() throws IOException {
		super();
	}
	
	public static Services getInstancia(String caminho, String usuario, String senha) throws IOException {
		if(instancia == null) {
			instancia = new Services();
			instancia.setSenha(senha);
			instancia.setUsuario(usuario);
			instancia.setURL(caminho);
			instancia.gerarToken();
		}
		return instancia;
	}

	@SuppressWarnings("unchecked")
	private void gerarToken() throws IOException {
		Login login = new Login();
		login.setUsuario(usuario);
		login.setSenha(senha);
		login.setManterConectado(true);
		Gson gson = new Gson();
		String jsonRequest = gson.toJson(login);
		String json = request(URL + "login", "POST", "", jsonRequest);
		RetornoServico<String> retorno = gson.fromJson(json, RetornoServico.class);
		token = retorno.getData();
	}
	
	public void notificar(Integer idEtapa) {
		try {
			EtapaTO etapaTo = new EtapaTO();
			etapaTo.setIdEtapa(idEtapa);
			etapaTo.setIpInterno(InetAddress.getLocalHost().getHostAddress());
			Gson gson = new Gson();
			String json = gson.toJson(etapaTo);
			request(URL + "etapa/notificar", "PUT", token, json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Long obterIdOriginal(Integer codigoOs) throws Exception {
		String json = request(URL + "depara/ordemservico/"+codigoOs, "GET", token, null);
		Gson gson = new Gson();
		Type listType = new TypeToken<RetornoServico<Long>>() {}.getType();
		RetornoServico<Long> retorno = gson.fromJson(json, listType);
		if(retorno.getCodigo() != 0) {
			throw new Exception(retorno.getMensagem());
		}
		return retorno.getData();
	}
	
	public void mudarServico(Integer codigoOs, Integer idEtapa) throws Exception {
		Long idOriginal = obterIdOriginal(codigoOs);
		request(URL + "ordemservico/"+idOriginal+"/historico?proximoIdEtapa="+idEtapa, "POST", token, null);
	}
	
	private String request(String urlStr, String method, String token, String jsonPost) throws IOException {
		URL url = new URL(urlStr);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestProperty("token", token);
		connection.setRequestMethod(method);
		connection.setUseCaches(false);
		connection.setDoInput(true);
		connection.setDoOutput(true);
		
		if(jsonPost != null) {
		    connection.setRequestProperty("Content-Type", "application/json");
			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			wr.writeBytes(jsonPost);
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

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}