package org.csi.controle.servico;

import javax.ejb.Local;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.csi.controle.core.entidade.Historico;
import org.csi.controle.core.to.RequestHistorico;
import org.csi.controle.core.util.RetornoServico;

@Local
@Path("/historico")
public interface HistoricoService {

	@POST
	@Path("/")
	@Consumes("application/json")
	@Produces("application/json")
	RetornoServico<String> create(RequestHistorico request);
	
}