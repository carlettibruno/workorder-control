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

import org.csi.controle.core.entidade.Configuracao;
import org.csi.controle.core.util.RetornoServico;

@Local
@Path("/configuracao")
public interface ConfiguracaoService {

	@GET
	@Path("/")
	@Produces("application/json")
	RetornoServico<List<Configuracao>> obterConfiguracoes();

	@POST
	@Path("/")
	@Produces("application/json")
	@Consumes("application/json")
	RetornoServico<String> inserirConfiguracao(Configuracao configuracao);

	@GET
	@Path("/{chave}")
	@Produces("application/json")
	RetornoServico<Configuracao> obterConfiguracao(@PathParam("chave") Integer chave);
	
	@PUT
	@Path("/{id}")
	@Produces("application/json")
	@Consumes("application/json")
	RetornoServico<String> atualizarConfiguracao(@PathParam("id") Integer idConfiguracao, Configuracao configuracao);
	
	@DELETE
	@Path("/{id}")
	@Produces("application/json")
	RetornoServico<String> apagarConfiguracao(@PathParam("id") Integer idConfiguracao);

	@GET
	@Path("/byname/{chave}")
	@Produces("application/json")
	RetornoServico<Configuracao> obterConfiguracao(String chave);	
	
}