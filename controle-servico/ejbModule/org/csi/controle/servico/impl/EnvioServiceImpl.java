package org.csi.controle.servico.impl;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.Session;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.fileupload.util.Streams;
import org.csi.controle.core.entidade.ChaveConfiguracao;
import org.csi.controle.core.entidade.Configuracao;
import org.csi.controle.core.entidade.Entidade;
import org.csi.controle.core.entidade.Envio;
import org.csi.controle.core.entidade.EnvioFoto;
import org.csi.controle.core.entidade.Foto;
import org.csi.controle.core.entidade.Funcionario;
import org.csi.controle.core.entidade.OrdemServico;
import org.csi.controle.core.entidade.Usuario;
import org.csi.controle.core.util.Codigo;
import org.csi.controle.core.util.IdGrupo;
import org.csi.controle.core.util.RetornoServico;
import org.csi.controle.servico.ClienteService;
import org.csi.controle.servico.ConfiguracaoService;
import org.csi.controle.servico.EnvioService;
import org.csi.controle.servico.FuncionarioService;
import org.csi.controle.servico.ImportClienteService;
import org.csi.controle.servico.OrdemServicoService;
import org.csi.controle.servico.util.ConversorTxt;
import org.csi.controle.servico.util.MailUtil;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

@Stateless
public class EnvioServiceImpl implements EnvioService {

	@PersistenceContext(name="controlePU")
	private EntityManager em;
	
	@EJB
	private OrdemServicoService ordemServicoService;
	
	@EJB
	private ImportClienteService importClientService;	
	
	@EJB
	private ClienteService clienteService;
	
	@EJB
	private FuncionarioService funcionarioService;
	
	@EJB
	private ConfiguracaoService configuracaoService;
	
	@Resource(name="java:/Mail")
	private Session mailSession;	
	
	@SuppressWarnings("unchecked")
	@Override
	public RetornoServico<List<EnvioFoto>> obterEnviosFoto(Integer inicio, Integer qtdeRegistro) {
		try {			
			Query query = em.createQuery("SELECT e FROM EnvioFoto e ORDER BY e.idEnvio DESC");
			query.setFirstResult(inicio);
			query.setMaxResults(qtdeRegistro);
			List<EnvioFoto> enviosFoto = query.getResultList();
			for (EnvioFoto envioFoto : enviosFoto) {
				em.detach(envioFoto);
				envioFoto.setFuncionarioCriacao(null);
			}
			return new RetornoServico<List<EnvioFoto>>(Codigo.SUCESSO, enviosFoto);
		} catch (Exception e) {
			e.printStackTrace();
			return new RetornoServico<List<EnvioFoto>>(Codigo.ERRO, e.getMessage());
		}
	}

	@Override
	public RetornoServico<Integer> inserirEnvioFoto(EnvioFoto envioFoto, String token) {
		try {
			if(envioFoto.getOrdemServico() == null || envioFoto.getOrdemServico().getId() == null) {
				throw new Exception("Ordem de serviço não pode ser vazia.");
			}
			Query query = em.createQuery("SELECT s.usuario FROM Sessao s WHERE s.codigo = :token");
			query.setParameter("token", token);
			query.setMaxResults(1);
			Usuario usuario = (Usuario) query.getSingleResult();
			envioFoto.setFuncionarioCriacao((Funcionario) usuario);
			envioFoto.setEntidade(Entidade.FOTO);
			envioFoto.setQtdeCarregada(0);
			OrdemServico os = em.find(OrdemServico.class, envioFoto.getOrdemServico().getId());
			envioFoto.setOrdemServico(os);
			em.persist(envioFoto);
			return new RetornoServico<Integer>(Codigo.SUCESSO, envioFoto.getIdEnvio());
		} catch (Exception e) {
			e.printStackTrace();
			return new RetornoServico<Integer>(Codigo.ERRO, e.getMessage());
		}
	}

	@Override
	public RetornoServico<String> inserirFoto(Integer idEnvioFoto, MultipartFormDataInput data) {
		try {
			EnvioFoto envioFoto = em.find(EnvioFoto.class, idEnvioFoto);
			RetornoServico<Long> retornoIdFoto = ordemServicoService.inserirFoto(envioFoto.getOrdemServico().getIdOrdemServico(), data);
			if(retornoIdFoto.getCodigo() != Codigo.SUCESSO) {
				throw new Exception(retornoIdFoto.getMensagem());
			}
			Foto foto = em.find(Foto.class, retornoIdFoto.getData());
			boolean isEntregador = funcionarioService.pertenceGrupo(envioFoto.getFuncionarioCriacao().getIdUsuario(), IdGrupo.ID_ENTREGADOR);
			if(isEntregador) {
				RetornoServico<Configuracao> configLink = configuracaoService.obterConfiguracao(ChaveConfiguracao.LINK_CLIENTE.ordinal());
				RetornoServico<Configuracao> raizFotos = configuracaoService.obterConfiguracao(ChaveConfiguracao.RAIZ_FOTOS.ordinal());
				RetornoServico<Configuracao> caminhoBase = configuracaoService.obterConfiguracao(ChaveConfiguracao.CAMINHO_BASE.ordinal());
				MailUtil.enviarEmailProtocolo(envioFoto.getOrdemServico().getCliente(), envioFoto.getOrdemServico().getNumero(), configLink.getData().getValor(), new File(raizFotos.getData().getValor() + foto.getCaminho()), caminhoBase.getData().getValor(), mailSession);
				foto.setAprovada(true);
			}
			foto.setEnvioFoto(envioFoto);
			envioFoto.setQtdeCarregada(envioFoto.getQtdeCarregada() + 1);
			return new RetornoServico<String>(Codigo.SUCESSO);
		} catch (Exception e) {
			e.printStackTrace();
			return new RetornoServico<String>(Codigo.ERRO, e.getMessage(), null);
		}
	}

	public RetornoServico<Envio> registrarEnvio(Envio envio) {
		try {
			envio.setTotal(envio.getTotal() == null ? 0 : envio.getTotal());
			envio.setQtdeCarregada(envio.getQtdeCarregada() == null ? 0 : envio.getQtdeCarregada());
			envio.setDataCriacao(new Date());
			em.persist(envio);
			return new RetornoServico<Envio>(Codigo.SUCESSO, envio);
		} catch (Exception e) {
			return new RetornoServico<Envio>(Codigo.ERRO, e.getMessage());
		}
	}
	
	@Override
	public RetornoServico<Envio> inserirEnvioInfoOs(Integer idEnvio, MultipartFormDataInput data) {
		try {
			RetornoServico<Envio> retornoEnvio = obterEnvio(idEnvio);
			if(retornoEnvio.getCodigo().intValue() == Codigo.ERRO) {
				return new RetornoServico<Envio>(Codigo.ERRO, retornoEnvio.getMensagem(), null);
			}
			Envio envio = retornoEnvio.getData();
			List<InputPart> parts = data.getParts();
			ConversorTxt conversor = new ConversorTxt();
			for (InputPart part : parts) {
				InputStream inputStream = part.getBody(InputStream.class,null);
				String arquivoStr = Streams.asString(inputStream, "ISO-8859-1");
				StringTokenizer st = new StringTokenizer(arquivoStr, "\n");
	            envio.setTotal(0);
	            
	            st.nextElement(); //pula cabeçalho
	            while (st.hasMoreElements()) {	
	            	String linha = (String) st.nextElement();
	            	try {
		            	List<String> codigosOs = conversor.txtToIdOrdemServicoNotaFiscal(linha);
		            	envio.setTotal(codigosOs.size() + envio.getTotal());
		            	for (String codigoOs : codigosOs) {
			            	RetornoServico<OrdemServico> retornoOs = ordemServicoService.obterOrdemServico(codigoOs);
			            	if(retornoOs.getData() != null) {	            		
			            		ordemServicoService.atualizarNotaFiscal(retornoOs.getData().getId(), conversor.txtToNotaFiscal(linha));
			            	}
			            	envio.setQtdeCarregada(envio.getQtdeCarregada() + 1);
		            	}
	            	} catch (Exception e) {
	            		e.printStackTrace();
					}
				}
			}
			return new RetornoServico<Envio>(Codigo.SUCESSO, envio);
		} catch (Exception e) {
			return new RetornoServico<Envio>(Codigo.ERRO, e.getMessage(), null);
		}
		
	}
	
	@Override
	public RetornoServico<Envio> inserirClientes(Integer idEnvio, MultipartFormDataInput data) {
		try {
			RetornoServico<Envio> retornoEnvio = obterEnvio(idEnvio);
			if(retornoEnvio.getCodigo().intValue() == Codigo.ERRO) {
				return new RetornoServico<Envio>(Codigo.ERRO, retornoEnvio.getMensagem(), null);
			}
			
			Envio envio = retornoEnvio.getData();
			List<InputPart> parts = data.getParts();
			for (InputPart part : parts) {
				InputStream inputStream = part.getBody(InputStream.class,null);
				importClientService.inserirClientes(inputStream, envio);
			}
			return new RetornoServico<Envio>(Codigo.SUCESSO, envio);
		} catch (Exception e) {
			return new RetornoServico<Envio>(Codigo.ERRO, e.getMessage(), null);
		}
		
	}	

	@Override
	public RetornoServico<Envio> obterEnvio(Integer idEnvio) {
		try {
			Envio envio = em.find(Envio.class, idEnvio);
			return new RetornoServico<Envio>(Codigo.SUCESSO, envio);
		} catch (Exception e) {
			e.printStackTrace();
			return new RetornoServico<Envio>(Codigo.ERRO, e.getMessage());
		}
	}

}	