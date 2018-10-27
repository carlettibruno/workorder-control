package org.csi.controle.servico;

import java.util.List;

import javax.ejb.Local;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.csi.controle.core.entidade.Permissao;
import org.csi.controle.core.to.Login;
import org.csi.controle.core.util.RetornoServico;

@Local
@Path("/login")
public interface LoginService {

	@POST
	@Path("/")
	@Produces("application/json")
	@Consumes("application/json")
	RetornoServico<String> gerarToken(Login login);

	@DELETE
	@Path("/logout")
	@Produces("application/json")
	RetornoServico<String> invalidarToken(@QueryParam("token") String token);
	
	@GET
	@Path("/permissao")
	@Produces("application/json")
	RetornoServico<List<Permissao>> obterPermissoes(@QueryParam("token") String token);
	
	@POST
	@Path("/recuperarsenha")
	@Produces("application/json")
	RetornoServico<String> recuperarSenha(@QueryParam("email") String email);
	
	@PUT
	@Path("/trocarsenha")
	@Produces("application/json")
	@Consumes("application/json")
	RetornoServico<String> trocarSenha(Login novaSenha, @HeaderParam("token") String token);

	@GET
	@Path("/permissao/{modulo}/{funcionalidade}")
	@Produces("application/json")
	RetornoServico<Boolean> temPermissao(@QueryParam("token") String token, @PathParam("modulo") Integer modulo, @PathParam("funcionalidade") Integer funcionalidade);
	
}