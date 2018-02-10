package org.csi.controle.servico;

import java.util.List;

import javax.ejb.Local;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.csi.controle.core.entidade.Cliente;
import org.csi.controle.core.util.RetornoServico;

@Local
@Path("/cliente")
public interface ClienteService {

	@GET
	@Path("/")
	@Produces("application/json")
	RetornoServico<List<Cliente>> obterClientes(@QueryParam("inicio") Integer inicio, @QueryParam("qtderegistro") Integer qtdeRegistro, @QueryParam("nome") String nome);

	@POST
	@Path("/")
	@Produces("application/json")
	@Consumes("application/json")
	RetornoServico<Cliente> inserirCliente(Cliente cliente);

	@GET
	@Path("/{id}")
	@Produces("application/json")
	RetornoServico<Cliente> obterCliente(@PathParam("id") Integer idCliente);
	
	@PUT
	@Path("/{id}")
	@Produces("application/json")
	@Consumes("application/json")
	RetornoServico<Cliente> atualizarCliente(@PathParam("id") Integer idCliente, Cliente cliente);
	
	@DELETE
	@Path("/{id}")
	@Produces("application/json")
	RetornoServico<String> apagarCliente(@PathParam("id") Integer idCliente);
	
	Cliente obterCliente(String documento);

	@PUT
	@Path("/{id}/gerarsenha")
	@Produces("application/json")
	RetornoServico<String> gerarNovaSenha(@PathParam("id") Integer idCliente, @QueryParam("enviarEmail") Boolean enviarEmail);
	
}