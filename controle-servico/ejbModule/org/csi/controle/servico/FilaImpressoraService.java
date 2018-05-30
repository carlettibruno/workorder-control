package org.csi.controle.servico;

import java.util.List;

import javax.ejb.Local;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.csi.controle.core.entidade.FilaImpressora;
import org.csi.controle.core.util.RetornoServico;

@Local
@Path("filaimpressora")
public interface FilaImpressoraService {

	@GET
	@Path("/")
	@Produces("application/json")
	RetornoServico<List<FilaImpressora>> findAll();
	
	@DELETE
	@Path("/")
	void remove(@QueryParam("fiid") Long id);
	
	@POST
	@Path("/")
	RetornoServico<FilaImpressora> create(String osNumber, Integer numberOfCopies);
	
}
