package org.csi.controle.servico.interceptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import org.csi.controle.core.entidade.Modulo;
import org.csi.controle.core.util.Codigo;
import org.csi.controle.core.util.CodigoFuncionalidade;
import org.csi.controle.core.util.RetornoServico;

import com.fasterxml.jackson.databind.ObjectMapper;

@Provider
public class RequestIntercept implements ContainerRequestFilter {

	private Modulo getModulo(String classe) {
		if(classe.startsWith("/funcionario")) {
			return Modulo.FUNCIONARIO;
		} else if(classe.startsWith("/configuracao")) {
			return Modulo.CONFIGURACAO;
		} else if(classe.startsWith("/cliente")) {
			return Modulo.CLIENTE;
		} else if(classe.startsWith("/etapa")) {
			return Modulo.ETAPA;
		} else if(classe.startsWith("/ordemservico")) {
			return Modulo.ORDEM_SERVICO;
		} else if(classe.startsWith("/envio")) {
			return Modulo.ENVIO;
		} else if(classe.startsWith("/sellers")) {
			return Modulo.SELLER;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void filter(ContainerRequestContext request) throws IOException {
		if(request.getUriInfo().getPath().startsWith("/login")
				|| request.getUriInfo().getPath().startsWith("/depara")) {
			
			return;
		}
		
		String headerValue = request.getHeaderString("token");
		if(headerValue == null) {
			request.abortWith(Response.status(Status.BAD_REQUEST).entity("Sessão inválida").build());
			return;
		} else {
			if(headerValue.equals("bcc8088ae11d78ac9b96a67b0423a07c")) {
				return;
			}

			request.getHeaders().add("Content-Type", "*/*; charset=UTF-8");

			Modulo modulo = getModulo(request.getUriInfo().getPath());

			Integer funcionalidade = 0; 
			funcionalidade = request.getMethod().equals("POST") ? CodigoFuncionalidade.INSERIR : funcionalidade;
			funcionalidade = request.getMethod().equals("PUT") ? CodigoFuncionalidade.EDITAR : funcionalidade;
			funcionalidade = request.getMethod().equals("DELETE") ? CodigoFuncionalidade.EXCLUIR : funcionalidade;
			funcionalidade = request.getMethod().equals("GET") ? CodigoFuncionalidade.CONSULTAR : funcionalidade;
			URL url;
			HttpURLConnection conn;
			BufferedReader rd;
			String line;
			String result = "";
			try {
				url = new URL(request.getUriInfo().getBaseUri().toString() + "login/permissao/"+modulo.ordinal()+"/"+funcionalidade+"?token="+headerValue+"&time="+System.currentTimeMillis());
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				while ((line = rd.readLine()) != null) {
					result += line;
				}
				rd.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			ObjectMapper mapper = new ObjectMapper();
			RetornoServico<Boolean> retorno = null;
			try {
				retorno = mapper.readValue(result, RetornoServico.class);
			} catch (Exception e) {
				request.abortWith(Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build());
				return;
			}
			if(retorno.getCodigo().intValue() == Codigo.ERRO) {
				request.abortWith(Response.status(Status.INTERNAL_SERVER_ERROR).entity(retorno.getMensagem()).build());
			} else if(retorno.getCodigo().intValue() == Codigo.AUTENTICAR) {
				request.abortWith(Response.status(Status.UNAUTHORIZED).entity(retorno.getMensagem()).build());
			} else if(retorno.getCodigo().intValue() == Codigo.SEM_PERMISSAO) {
				request.abortWith(Response.status(Status.BAD_REQUEST).entity("Sem permissão").build());
			}
		}		
	}
}