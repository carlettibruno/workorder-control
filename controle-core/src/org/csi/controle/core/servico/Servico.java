package org.csi.controle.core.servico;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;

import org.csi.controle.core.entidade.EnvioFoto;
import org.csi.controle.core.entidade.OrdemServico;
import org.csi.controle.core.entidade.Permissao;
import org.csi.controle.core.serialize.JsonDateDeserializer;
import org.csi.controle.core.to.Login;
import org.csi.controle.core.util.RetornoServico;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Servico<T> {

	private String url;
	
	private String token;
	
	public Servico(String url, String token) {
		this.url = url;
		this.token = token;
	}
	
	public RetornoServico<T> gerarToken(String usuario, String senha) throws IOException {
		Login login = new Login();
		login.setUsuario(usuario);
		login.setSenha(senha);
		login.setManterConectado(true);
		Gson gson = new Gson();
		String jsonRequest = gson.toJson(login);
		String json = request(url + "login", "POST", "", jsonRequest);
		
		Type type = new TypeToken<RetornoServico<T>>() {}.getType();
		RetornoServico<T> retorno = gson.fromJson(json, type);
		return retorno;
	}
	
	public RetornoServico<T> obterPermissoes(String token) throws IOException {
		String json = request(url + "login/permissao?token="+token, "GET", "", null);
		Type type = new TypeToken<RetornoServico<List<Permissao>>>() {}.getType();
		Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new JsonDateDeserializer()).create();
		RetornoServico<T> retorno = gson.fromJson(json, type);
		return retorno;
	}	

	private String request(String urlStr, String method, String token, String jsonPost) throws IOException {
		URL url = new URL(urlStr);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestProperty("token", token);
		connection.setRequestMethod(method);
		connection.setUseCaches(false);
		connection.setDoInput(true);
//		connection.setDoOutput(true);
		
		if(jsonPost != null) {
		    connection.setRequestProperty("Content-Type", "application/json");
			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			wr.writeBytes(jsonPost);
			wr.flush();
			wr.close();
		}
		
		int respCode = connection.getResponseCode();
		if (respCode >= 400) {
		    if (respCode == 404 || respCode == 410) {
		        throw new FileNotFoundException(url.toString());
		    } else {
		        throw new java.io.IOException(
		            "Server returned HTTP"
		            + " response code: " + respCode
		            + " for URL: " + url.toString());
		    }
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

	public RetornoServico<List<EnvioFoto>> listarEnviosFoto() throws IOException {
		String json = request(url + "envio/foto?inicio=0&qtderegistro=20", "GET", token, null);
		Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new JsonDateDeserializer()).create();
		Type type = new TypeToken<RetornoServico<List<EnvioFoto>>>() {}.getType();
		RetornoServico<List<EnvioFoto>> retorno = gson.fromJson(json, type);
		return retorno;
	}
	
	public Integer registrarEnvio(Integer qtdeTotal, Long idOrdemServico) throws IOException {
		EnvioFoto envio = new EnvioFoto();
		envio.setTotal(qtdeTotal);
		OrdemServico os = new OrdemServico();
		os.setId(idOrdemServico);
		envio.setOrdemServico(os);
		Gson gson = new Gson();
		String jsonObjeto = gson.toJson(envio);
		String json = request(url + "envio/foto", "POST", token, jsonObjeto);
		Type type = new TypeToken<RetornoServico<Integer>>() {}.getType();
		RetornoServico<Integer> idEnvio = gson.fromJson(json, type);
		return idEnvio.getData();
	}
	
	public void enviarFoto(Integer idEnvioFoto, File uploadFile) throws Exception {
		MultipartUtility mu = new MultipartUtility(url + "envio/foto/"+idEnvioFoto, token, "UTF-8");
		mu.addFilePart("fileFoto", uploadFile);
		mu.finish();
	}
	
	public RetornoServico<Long> obterIdOriginal(String codigoOs) throws Exception {
		String json = request(url + "depara/ordemservico/"+codigoOs, "GET", token, null);
		Gson gson = new Gson();
		Type listType = new TypeToken<RetornoServico<Long>>() {}.getType();
		RetornoServico<Long> retorno = gson.fromJson(json, listType);
		if(retorno.getCodigo() != 0) {
			throw new Exception(retorno.getMensagem());
		}
		return retorno;
	}
	
}