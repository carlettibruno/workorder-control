//package org.csi.controle.servico.interceptor;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.lang.reflect.Method;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.ws.rs.DELETE;
//import javax.ws.rs.GET;
//import javax.ws.rs.POST;
//import javax.ws.rs.PUT;
//import javax.ws.rs.core.Context;
//import javax.ws.rs.core.Response;
//import javax.ws.rs.core.Response.Status;
//import javax.ws.rs.ext.Provider;
//
//import org.codehaus.jackson.map.ObjectMapper;
//import org.csi.controle.core.entidade.Modulo;
//import org.csi.controle.core.util.Codigo;
//import org.csi.controle.core.util.CodigoFuncionalidade;
//import org.csi.controle.core.util.RetornoServico;
//import org.jboss.resteasy.annotations.interception.ServerInterceptor;
//import org.jboss.resteasy.core.ResourceMethod;
//import org.jboss.resteasy.core.ServerResponse;
//import org.jboss.resteasy.plugins.providers.multipart.InputPart;
//import org.jboss.resteasy.spi.HttpRequest;
//import org.jboss.resteasy.spi.UnauthorizedException;
//import org.jboss.resteasy.spi.interception.AcceptedByMethod;
//import org.jboss.resteasy.spi.interception.PreProcessInterceptor;
//
//@Provider
//@ServerInterceptor
//public class RequestIntercept implements PreProcessInterceptor, AcceptedByMethod {
//
//	@Context 
//	private HttpServletRequest request;
//	
//	@SuppressWarnings("unchecked")
//	@Override
//	public ServerResponse preProcess(HttpRequest httpRequest, ResourceMethod resourceMethod) throws UnauthorizedException {
//		String headerValue = request.getHeader("token");
//		if(headerValue == null) {
//			return (ServerResponse)Response.status(Status.BAD_REQUEST).entity("Sessão inválida").build();
//		} else {
//
//			httpRequest.setAttribute(InputPart.DEFAULT_CONTENT_TYPE_PROPERTY, "*/*; charset=UTF-8");
//			
//			Modulo modulo = getModulo(resourceMethod.getResourceClass().getName());
//			
//			Integer funcionalidade = 0; 
//			funcionalidade = resourceMethod.getMethod().isAnnotationPresent(POST.class) ? CodigoFuncionalidade.INSERIR : funcionalidade;
//			funcionalidade = resourceMethod.getMethod().isAnnotationPresent(PUT.class) ? CodigoFuncionalidade.EDITAR : funcionalidade;
//			funcionalidade = resourceMethod.getMethod().isAnnotationPresent(DELETE.class) ? CodigoFuncionalidade.EXCLUIR : funcionalidade;
//			funcionalidade = resourceMethod.getMethod().isAnnotationPresent(GET.class) ? CodigoFuncionalidade.CONSULTAR : funcionalidade;
//			URL url;
//			HttpURLConnection conn;
//			BufferedReader rd;
//			String line;
//			String result = "";
//			try {
//				url = new URL("http://localhost:8080/controle-admin/services/login/permissao/"+modulo.ordinal()+"/"+funcionalidade+"?token="+headerValue+"&time="+System.currentTimeMillis());
//				conn = (HttpURLConnection) url.openConnection();
//				conn.setRequestMethod("GET");
//				rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//				while ((line = rd.readLine()) != null) {
//					result += line;
//				}
//				rd.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			ObjectMapper mapper = new ObjectMapper();
//			RetornoServico<Boolean> retorno;
//			try {
//				retorno = mapper.readValue(result, RetornoServico.class);
//			} catch (Exception e) {
//				return (ServerResponse)Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
//			}
//			if(retorno.getCodigo().intValue() == Codigo.ERRO) {
//				return (ServerResponse)Response.status(Status.INTERNAL_SERVER_ERROR).entity(retorno.getMensagem()).build();	
//			} else if(retorno.getCodigo().intValue() == Codigo.AUTENTICAR) {
//				return (ServerResponse)Response.status(Status.UNAUTHORIZED).entity(retorno.getMensagem()).build();
//			} else if(retorno.getCodigo().intValue() == Codigo.SEM_PERMISSAO) {
//				return (ServerResponse)Response.status(Status.BAD_REQUEST).entity("Sem permissão").build();	
//			}
//		}
//		return null;
//	}
//
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@Override
//	public boolean accept(Class clazz, Method method) {
//		return !clazz.isAnnotationPresent(NotSecure.class) && !method.isAnnotationPresent(NotSecure.class);
//	}
//	
//	private Modulo getModulo(String classe) {
//		if(classe.contains("Funcionario")) {
//			return Modulo.FUNCIONARIO;
//		} else if(classe.contains("Configuracao")) {
//			return Modulo.CONFIGURACAO;
//		} else if(classe.contains("Cliente")) {
//			return Modulo.CLIENTE;
//		} else if(classe.contains("Etapa")) {
//			return Modulo.ETAPA;
//		} else if(classe.contains("OrdemServico")) {
//			return Modulo.ORDEM_SERVICO;
//		} else if(classe.contains("Envio")) {
//			return Modulo.ENVIO;
//		}
//		return null;
//	}
//}