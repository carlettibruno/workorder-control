package org.csi.controle.servico;

import javax.ejb.Local;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.csi.controle.core.util.RetornoServico;

@Local
@Path("/depara")
public interface DeParaService {

	@GET
	@Path("/ordemservico/{codigo}")
	@Produces("application/json")
	RetornoServico<Long> obterOsOriginal(@PathParam("codigo") String codigoOs);	
	
}