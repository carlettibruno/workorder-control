package org.csi.controle.servico;

import java.util.List;

import javax.ejb.Local;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.csi.controle.core.entidade.Envio;
import org.csi.controle.core.entidade.EnvioFoto;
import org.csi.controle.core.util.RetornoServico;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

@Local
@Path("/envio")
public interface EnvioService {

	@GET
	@Path("/foto")
	@Produces("application/json")
	RetornoServico<List<EnvioFoto>> obterEnviosFoto(@QueryParam("inicio") Integer inicio, @QueryParam("qtderegistro") Integer qtdeRegistro);
	
	@POST
	@Path("/foto")
	@Produces("application/json")
	@Consumes("application/json")
	RetornoServico<Integer> inserirEnvioFoto(EnvioFoto envioFoto, @HeaderParam("token") String token);
	
	@POST
	@Path("/foto/{id}")
	@Produces("application/json")
	@Consumes("multipart/form-data")
	RetornoServico<String> inserirFoto(@PathParam("id") Integer idEnvioFoto, MultipartFormDataInput foto);	

	@POST
	@Path("/{id}/infoos")
	@Produces("application/json")
	@Consumes("multipart/form-data")
	RetornoServico<Envio> inserirEnvioInfoOs(@PathParam("id") Integer idEnvio, MultipartFormDataInput data);
	
	@GET
	@Path("/{id}")
	@Produces("application/json")
	RetornoServico<Envio> obterEnvio(@PathParam("id") Integer idEnvio);

	@POST
	@Path("/")
	@Produces("application/json")
	@Consumes("application/json")
	RetornoServico<Envio> registrarEnvio(Envio envio);

	@POST
	@Path("/{id}/cliente")
	@Produces("application/json")
	@Consumes("multipart/form-data")
	RetornoServico<Envio> inserirClientes(@PathParam("id") Integer idEnvio, MultipartFormDataInput data);
	
}