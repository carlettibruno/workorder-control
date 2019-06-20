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

import org.csi.controle.core.entidade.Seller;
import org.csi.controle.core.util.RetornoServico;

@Local
@Path("/sellers")
public interface SellerService {

	@GET
	@Path("/")
	@Produces("application/json")
	RetornoServico<List<Seller>> findAll();

	@POST
	@Path("/")
	@Produces("application/json")
	@Consumes("application/json")
	RetornoServico<String> create(Seller seller);

	@GET
	@Path("/{id}")
	@Produces("application/json")
	RetornoServico<Seller> findById(@PathParam("id") Integer id);

	@PUT
	@Path("/{id}")
	@Produces("application/json")
	@Consumes("application/json")
	RetornoServico<String> update(@PathParam("id") Integer id, Seller seller);

	@DELETE
	@Path("/{id}")
	@Produces("application/json")
	RetornoServico<String> delete(@PathParam("id") Integer id);

}