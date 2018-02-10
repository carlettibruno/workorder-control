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

import org.csi.controle.core.entidade.Funcionario;
import org.csi.controle.core.entidade.Grupo;
import org.csi.controle.core.util.RetornoServico;

@Local
@Path("/funcionario")
public interface FuncionarioService {

	@GET
	@Path("/")
	@Produces("application/json")
	RetornoServico<List<Funcionario>> obterFuncionarios(@QueryParam("filtro") String filtro, @QueryParam("inicio") Integer inicio, @QueryParam("qtderegistro") Integer qtdeRegistro);

	@POST
	@Path("/")
	@Produces("application/json")
	@Consumes("application/json")
	RetornoServico<Funcionario> inserirFuncionario(Funcionario funcionario);

	@GET
	@Path("/{id}")
	@Produces("application/json")
	RetornoServico<Funcionario> obterFuncionario(@PathParam("id") Integer idFuncionario);
	
	@PUT
	@Path("/{id}")
	@Produces("application/json")
	@Consumes("application/json")
	RetornoServico<Funcionario> atualizarFuncionario(@PathParam("id") Integer idFuncionario, Funcionario funcionario);
	
	@DELETE
	@Path("/{id}")
	@Produces("application/json")
	RetornoServico<String> apagarFuncionario(@PathParam("id") Integer idFuncionario);
	
	@GET
	@Path("/grupos")
	@Produces("application/json")
	RetornoServico<List<Grupo>> obterGrupos();
	
	boolean pertenceGrupo(Integer idFuncionario, Integer idGrupo);
	
}