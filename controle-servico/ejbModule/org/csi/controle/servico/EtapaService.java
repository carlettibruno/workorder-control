package org.csi.controle.servico;

import java.util.List;

import javax.ejb.Local;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.csi.controle.core.entidade.Etapa;
import org.csi.controle.core.entidade.TipoEntrega;
import org.csi.controle.core.util.RetornoServico;

@Local
@Path("/etapa")
public interface EtapaService {

	@GET
	@Path("/")
	@Produces("application/json")
	RetornoServico<List<Etapa>> obterEtapas();

	@POST
	@Path("/")
	@Produces("application/json")
	@Consumes("application/json")
	RetornoServico<String> inserirEtapa(Etapa etapa);

	@GET
	@Path("/{id}")
	@Produces("application/json")
	RetornoServico<Etapa> obterEtapa(@PathParam("id") Integer idEtapa);
	
	@PUT
	@Path("/{id}")
	@Produces("application/json")
	@Consumes("application/json")
	RetornoServico<String> atualizarEtapa(@PathParam("id") Integer idEtapa, Etapa etapa);
	
	@DELETE
	@Path("/{id}")
	@Produces("application/json")
	RetornoServico<String> apagarEtapa(@PathParam("id") Integer idEtapa);
	
	@GET
	@Path("/tipoentrega")
	@Produces("application/json")
	RetornoServico<TipoEntrega[]> obterTiposEntrega();	

	RetornoServico<Etapa> obterEtapaInicial();
	
	@GET
	@Path("/reference/{ref}")
	@Produces("application/json")
	RetornoServico<Etapa> findEtapaByReference(@PathParam("ref") String reference);
	
	@PUT
	@Path("/notificar")
	@Consumes("text/plain")
	void notificarEtapa(String reference, @Context HttpServletRequest req);
	
	void limparEtapaInicial();
	
}