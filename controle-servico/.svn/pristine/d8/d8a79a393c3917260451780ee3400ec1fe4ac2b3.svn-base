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

import org.csi.controle.core.entidade.DetalheNota;
import org.csi.controle.core.entidade.Endereco;
import org.csi.controle.core.entidade.EnderecoEntrega;
import org.csi.controle.core.entidade.Foto;
import org.csi.controle.core.entidade.Historico;
import org.csi.controle.core.entidade.NotaFiscal;
import org.csi.controle.core.entidade.OrdemServico;
import org.csi.controle.core.util.RetornoServico;
import org.csi.rastreamento.correios.entidade.Evento;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

@Local
@Path("/ordemservico")
public interface OrdemServicoService {

	@GET
	@Path("/")
	@Produces("application/json")
	RetornoServico<List<OrdemServico>> obterOrdensServico(@QueryParam("inicio") Integer inicio, @QueryParam("qtderegistro") Integer qtdeRegistro, @HeaderParam("token") String token, @QueryParam("filtro") String filtro);

	@POST
	@Path("/")
	@Produces("application/json")
	@Consumes("application/json")
	RetornoServico<Long> inserirOrdemServico(OrdemServico ordemServico, @HeaderParam("token") String token);

	@GET
	@Path("/{id}")
	@Produces("application/json")
	RetornoServico<OrdemServico> obterOrdemServico(@PathParam("id") Long idOrdemServico);
	
	@PUT
	@Path("/{id}")
	@Produces("application/json")
	@Consumes("application/json")
	RetornoServico<String> atualizarOrdemServico(@PathParam("id") Long idOrdemServico, OrdemServico ordemServico);
	
	@DELETE
	@Path("/{id}")
	@Produces("application/json")
	RetornoServico<String> apagarOrdemServico(@PathParam("id") Long idOrdemServico);

	@GET
	@Path("/{id}/foto")
	@Produces("application/json")
	RetornoServico<List<Foto>> obterFotos(@PathParam("id") Long idOrdemServico, @HeaderParam("token") String token);
	
	@PUT
	@Path("/{id}/foto/{idFoto}")
	@Produces("application/json")
	RetornoServico<String> aprovarFoto(@PathParam("id") Long idOrdemServico, @PathParam("idFoto") Long idFoto, @QueryParam("aprova") Boolean aprova);	

	@POST
	@Path("/{id}/foto")
	@Produces("application/json")
	@Consumes("multipart/form-data")
	RetornoServico<Long> inserirFoto(@PathParam("id") Long idOrdemServico, MultipartFormDataInput data);

	@DELETE
	@Path("/{id}/foto/{idFoto}")
	@Produces("application/json")
	RetornoServico<String> apagarFoto(@PathParam("id") Long idOrdemServico, @PathParam("idFoto") Long idFoto);	
	
	@GET
	@Path("/{id}/historico")
	@Produces("application/json")
	RetornoServico<List<Historico>> obterHistoricos(@PathParam("id") Long idOrdemServico);
	
	@POST
	@Path("/{id}/historico")
	@Produces("application/json")
	RetornoServico<String> alterarStatus(@PathParam("id") Long idOrdemServico, @QueryParam("proximoIdEtapa") Integer idEtapa);	

	@GET
	@Path("/{id}/endereco")
	@Produces("application/json")
	RetornoServico<List<EnderecoEntrega>> obterEnderecos(@PathParam("id") Long idOrdemServico);
	
	@GET
	@Path("/endereco/{codigo}")
	@Produces("application/json")
	RetornoServico<List<Evento>> obterEventosEntrega(@PathParam("codigo") String codigo);	
	
	@POST
	@Path("/{id}/endereco")
	@Produces("application/json")
	@Consumes("application/json")
	RetornoServico<String> inserirEnderecoEntrega(@PathParam("id") Long idOrdemServico, Endereco endereco);
	
	@POST
	@Path("/{id}/enderecos")
	@Produces("application/json")
	@Consumes("multipart/form-data")
	RetornoServico<String> inserirEnderecosEntrega(@PathParam("id") Long idOrdemServico, MultipartFormDataInput data);

	@POST
	@Path("/endereco/{idEnderecoEntrega}/referencia")
	@Produces("application/json")
//	@Consumes("multipart/form-data")
	RetornoServico<String> inserirReferenciaEntrega(@PathParam("idEnderecoEntrega") Long idEnderecoEntrega, String codigoReferencia);

	@DELETE
	@Path("/{id}/endereco/{idEndereco}")
	@Produces("application/json")
	RetornoServico<String> apagarEnderecoEntrega(@PathParam("id") Long idOrdemServico, @PathParam("idEndereco") Long idEndereco);
	
	Foto atualizarCaminhoCompleto(Foto foto);
	
	RetornoServico<OrdemServico> obterOrdemServico(String codigoOs);

	@PUT
	@Path("/{id}/notafiscal/")
	RetornoServico<String> atualizarNotaFiscal(@PathParam("id") Long idOrdemServico, NotaFiscal notaFiscal);

	@GET
	@Path("/{id}/notafiscal/{idNotaFiscal}")
	RetornoServico<List<DetalheNota>> obterDetalhesNota(@PathParam("idNotaFiscal") Long idNotaFiscal);

	void encerrarPorEntrega(Long idOrdemServico, String codigoReferencia);
	
	void sincronizarReferenciaEntrega();
	
}