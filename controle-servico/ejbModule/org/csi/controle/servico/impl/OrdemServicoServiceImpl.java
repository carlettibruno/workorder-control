package org.csi.controle.servico.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.io.IOUtils;
import org.csi.controle.core.entidade.ChaveConfiguracao;
import org.csi.controle.core.entidade.ClienteAcesso;
import org.csi.controle.core.entidade.Configuracao;
import org.csi.controle.core.entidade.DetalheNota;
import org.csi.controle.core.entidade.Endereco;
import org.csi.controle.core.entidade.EnderecoEntrega;
import org.csi.controle.core.entidade.EnvioFoto;
import org.csi.controle.core.entidade.Etapa;
import org.csi.controle.core.entidade.Foto;
import org.csi.controle.core.entidade.Funcionario;
import org.csi.controle.core.entidade.Historico;
import org.csi.controle.core.entidade.NotaFiscal;
import org.csi.controle.core.entidade.OrdemServico;
import org.csi.controle.core.entidade.ReferenciaEntrega;
import org.csi.controle.core.entidade.Sessao;
import org.csi.controle.core.entidade.Status;
import org.csi.controle.core.entidade.TipoEntrega;
import org.csi.controle.core.util.Codigo;
import org.csi.controle.core.util.DadosUtil;
import org.csi.controle.core.util.RetornoServico;
import org.csi.controle.servico.ConfiguracaoService;
import org.csi.controle.servico.EtapaService;
import org.csi.controle.servico.FilaImpressoraService;
import org.csi.controle.servico.OrdemServicoService;
import org.csi.controle.servico.util.ConversorTxt;
import org.csi.controle.servico.util.FileUtil;
import org.csi.rastreamento.correios.entidade.Evento;
import org.csi.rastreamento.correios.manager.Rastreio;
import org.csi.rastreamento.correios.manager.RastreioFactory;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

@Stateless
public class OrdemServicoServiceImpl implements OrdemServicoService {

	@PersistenceContext(name="controlePU")
	private EntityManager em;

	@EJB
	private ConfiguracaoService configuracaoService;

	@EJB
	private EtapaService etapaService;

	@EJB
	private FilaImpressoraService filaImpressoraService;

	@SuppressWarnings("unchecked")
	@Override
	public RetornoServico<List<OrdemServico>> obterOrdensServico(Integer inicio, Integer qtdeRegistro, String token, String filtro) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT o ");
			sql.append("FROM OrdemServico o, Sessao s ");
			sql.append("WHERE s.codigo = :token AND ");
			sql.append("( ");
			sql.append("(o.cliente.idCliente IN(SELECT c.idCliente FROM ClienteAcesso ca JOIN ca.clientes c, Sessao s WHERE s.codigo = :token AND s.usuario.idUsuario = ca.idUsuario) AND s.usuario.class = ClienteAcesso) OR ");
			sql.append("s.usuario.class = Funcionario ");
			sql.append(") ");
			if(filtro != null && !filtro.isEmpty()) {
				sql.append(" AND (o.numero like :filtro OR UPPER(o.descricao) like :filtro OR UPPER(o.cliente.nome) like :filtro ) ");
			}
			sql.append("ORDER BY o.idOrdemServico DESC ");
			Query query = em.createQuery(sql.toString());
			query.setParameter("token", token);
			if(filtro != null && !filtro.isEmpty()) {
				query.setParameter("filtro", "%" + filtro.toUpperCase() + "%");
			}
			query.setFirstResult(inicio);
			query.setMaxResults(qtdeRegistro);
			List<OrdemServico> ordensServico = query.getResultList();
			for (OrdemServico ordemServico : ordensServico) {
				em.detach(ordemServico);
				atualizarRetornoOrdemServico(ordemServico);
			}
			return new RetornoServico<List<OrdemServico>>(Codigo.SUCESSO, ordensServico);
		} catch (Exception e) {
			e.printStackTrace();
			return new RetornoServico<List<OrdemServico>>(Codigo.ERRO, e.getMessage());
		}
	}

	@Override
	public RetornoServico<Long> inserirOrdemServico(OrdemServico ordemServico, String token) {
		try {
			OrdemServico ordemServicoBase = obterOrdemServico(ordemServico.getNumero()).getData();
			if(ordemServicoBase != null) {
				return new RetornoServico<Long>(Codigo.ERRO, "Ordem de serviço existente: "+ordemServicoBase.getNumero());
			}
			ordemServico.setAtivo(true);
			ordemServico.setDataCriacao(new Date());
			ordemServico.setDataModificacao(new Date());
			ordemServico.setStatus(Status.ABERTO);
			Query query = em.createQuery("SELECT se FROM Sessao se JOIN FETCH se.usuario u WHERE se.codigo = :codigo");
			query.setParameter("codigo", token);
			Sessao sessao = (Sessao) query.getSingleResult();
			ordemServico.setFuncionarioCriacao((Funcionario) sessao.getUsuario());

			em.persist(ordemServico);
			RetornoServico<Etapa> etapa = etapaService.obterEtapaInicial();
			if(etapa.getData() != null) {
				Historico historico = new Historico();
				historico.setDataInicio(ordemServico.getDataCriacao());
				historico.setEtapa(etapa.getData());
				historico.setOrdemServico(ordemServico);
				historico.setStatus(Status.EM_ANDAMENTO);
				em.persist(historico);
				ordemServico.setHistoricoAtual(historico);
			}

			filaImpressoraService.create(ordemServico.getNumero(), 2);

			return new RetornoServico<Long>(Codigo.SUCESSO, ordemServico.getIdOrdemServico());
		} catch (Exception e) {
			e.printStackTrace();
			return new RetornoServico<Long>(Codigo.ERRO, e.getMessage());
		}
	}

	@Override
	public RetornoServico<OrdemServico> obterOrdemServico(Long idOrdemServico) {
		try {
			OrdemServico ordemServico = em.find(OrdemServico.class, idOrdemServico);
			return new RetornoServico<OrdemServico>(Codigo.SUCESSO, ordemServico);
		} catch (Exception e) {
			e.printStackTrace();
			return new RetornoServico<OrdemServico>(Codigo.ERRO, e.getMessage());
		}
	}

	@Override
	public RetornoServico<String> atualizarOrdemServico(Long idOrdemServico, OrdemServico ordemServico) {
		try {
			OrdemServico ordemServicoBase = em.find(OrdemServico.class, idOrdemServico);
			ordemServicoBase.setDataModificacao(new Date());
			ordemServicoBase.setDescricao(ordemServico.getDescricao());
			ordemServicoBase.setPrevisaoEntrega(ordemServico.getPrevisaoEntrega());

			atualizarNotaFiscal(idOrdemServico, ordemServico.getNotaFiscal());
			return new RetornoServico<String>(Codigo.SUCESSO);
		} catch (Exception e) {
			e.printStackTrace();
			return new RetornoServico<String>(Codigo.ERRO, e.getMessage(), null);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public RetornoServico<String> apagarOrdemServico(Long idOrdemServico) {
		try {
			OrdemServico ordemServicoBase = em.find(OrdemServico.class, idOrdemServico);
			List<Foto> fotos = ordemServicoBase.getFotos();
			List<Long> idsFoto = new ArrayList<Long>(fotos.size());
			for (Foto foto : fotos) {
				idsFoto.add(foto.getIdFoto());
			}
			for (Long idFoto : idsFoto) {
				apagarFoto(idOrdemServico, idFoto);
			}
			Query query = em.createQuery("SELECT ef FROM EnvioFoto ef WHERE ef.ordemServico.idOrdemServico = :idOs");
			query.setParameter("idOs", idOrdemServico);
			List<EnvioFoto> enviosFoto = query.getResultList();
			for (EnvioFoto envioFoto : enviosFoto) {
				em.remove(envioFoto);
			}
			em.remove(ordemServicoBase);
			return new RetornoServico<String>(Codigo.SUCESSO);
		} catch (Exception e) {
			e.printStackTrace();
			return new RetornoServico<String>(Codigo.ERRO, e.getMessage(), null);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public RetornoServico<List<Foto>> obterFotos(Long idOrdemServico, String token) {
		try {
			Query query = em.createQuery("SELECT se FROM Sessao se JOIN FETCH se.usuario u WHERE se.codigo = :codigo");
			query.setParameter("codigo", token);
			Sessao sessao = (Sessao) query.getSingleResult();
			String mostrarAprovada = "";
			if(sessao.getUsuario() instanceof ClienteAcesso) {
				mostrarAprovada = " AND f.aprovada = :aprovada ";
			}

			Query queryFoto = em.createQuery("SELECT f FROM Foto f WHERE f.ordemServico.idOrdemServico = :idOrdemServico "+mostrarAprovada+" ORDER BY f.idFoto");
			queryFoto.setParameter("idOrdemServico", idOrdemServico);
			if(!mostrarAprovada.isEmpty()) {
				queryFoto.setParameter("aprovada", true);
			}
			List<Foto> fotos = queryFoto.getResultList();
			for (Foto foto : fotos) {
				atualizarCaminhoCompleto(foto);
			}
			return new RetornoServico<List<Foto>>(Codigo.SUCESSO, fotos);
		} catch (Exception e) {
			e.printStackTrace();
			return new RetornoServico<List<Foto>>(Codigo.ERRO, e.getMessage());
		}
	}

	@Override
	public RetornoServico<Long> inserirFoto(Long idOrdemServico, MultipartFormDataInput data) {
		try {
			RetornoServico<OrdemServico> retornoOs = obterOrdemServico(idOrdemServico);
			if(retornoOs.getCodigo() != Codigo.SUCESSO) {
				throw new Exception(retornoOs.getMensagem());
			}
			OrdemServico ordemServico = retornoOs.getData();
			boolean fotoAprovada = ordemServico.getCliente().getAprovacaoFoto() != null && !ordemServico.getCliente().getAprovacaoFoto();

			Configuracao configRaiz = configuracaoService.obterConfiguracao(ChaveConfiguracao.RAIZ_FOTOS.ordinal()).getData();

			List<InputPart> parts = data.getParts();
			Foto foto = null;
			for (InputPart part : parts) {
				String nome = FileUtil.getFileName(part.getHeaders());
				foto = new Foto();
				foto.setAtivo(true);
				foto.setDataCriacao(new Date());
				foto.setDataModificacao(new Date());
				foto.setOrdemServico(ordemServico);
				foto.setNome(nome);
				foto.setAprovada(fotoAprovada);
				InputStream inputStream = part.getBody(InputStream.class,null);
				byte[] arquivo = IOUtils.toByteArray(inputStream);

				String path = FileUtil.obterPathFoto(configRaiz.getValor(), nome);

				foto.setCaminho(path);
				String caminhoCompleto = configRaiz.getValor() + path;
				File fileCompleto = new File(caminhoCompleto);
				FileOutputStream fos = new FileOutputStream(fileCompleto);
				fos.write(arquivo);
				fos.flush();
				fos.close();
				foto.setTamanho(Integer.parseInt(fileCompleto.length()+""));
				FileUtil.redimensionarImagem(caminhoCompleto, FileUtil.obterCaminhoThumb(caminhoCompleto,FileUtil.TAMANHO_PEQUENO), FileUtil.TAMANHO_PEQUENO);
				FileUtil.redimensionarImagem(caminhoCompleto, FileUtil.obterCaminhoThumb(caminhoCompleto,FileUtil.TAMANHO_NORMAL), FileUtil.TAMANHO_NORMAL);

				em.persist(foto);
				if(ordemServico.getFoto() == null) {
					ordemServico.setFoto(foto);
				}

//				RetornoServico<Configuracao> configuracao = configuracaoService.obterConfiguracao(ChaveConfiguracao.PERCENTO_PROTOCOLO.ordinal());
//				Integer percento = Integer.parseInt(configuracao.getData().getValor());
//				CheckProtocolo checkProtocolo = new CheckProtocolo();
//				Double percentualImagem = checkProtocolo.percentualProtocolo(new File(caminhoCompleto));
//				Double percentualImagem = checkProtocolo.percentualProtocolo(new File(caminhoCompleto));
//				if(percentualImagem > percento) {
//					encerrarPorEntrega(idOrdemServico, TipoEntrega.PARTICULAR.toString());
//				}
			}
			if(foto != null) {
				return new RetornoServico<Long>(Codigo.SUCESSO, foto.getId());
			} else {
				return new RetornoServico<Long>(Codigo.SUCESSO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new RetornoServico<Long>(Codigo.ERRO, e.getMessage());
		}
	}

	@Override
	public RetornoServico<String> apagarFoto(Long idOrdemServico, Long idFoto) {
		try {
			Foto foto = em.find(Foto.class, idFoto);
			if(foto.getOrdemServico().getFoto().equals(foto)) {
				foto.getOrdemServico().getFotos().remove(foto);
				List<Foto> fotos = foto.getOrdemServico().getFotos();
				if(fotos.isEmpty()) {
					foto.getOrdemServico().setFoto(null);
				} else {
					foto.getOrdemServico().setFoto(fotos.get(0));
				}
			}
			em.remove(foto);
			return new RetornoServico<String>(Codigo.SUCESSO);
		} catch (Exception e) {
			e.printStackTrace();
			return new RetornoServico<String>(Codigo.ERRO, e.getMessage(), null);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public RetornoServico<List<Historico>> obterHistoricos(Long idOrdemServico) {
		try {
			Query query = em.createQuery("SELECT h FROM Historico h JOIN FETCH h.etapa WHERE h.ordemServico.idOrdemServico = :idOrdemServico ORDER BY h.idHistorico DESC");
			query.setParameter("idOrdemServico", idOrdemServico);
			List<Historico> historicos = query.getResultList();
			for (Historico historico : historicos) {
				Date dataFinal = historico.getDataFim() != null ? historico.getDataFim() : new Date();
				historico.setDiferencaDatas(DadosUtil.formatarDiffData(historico.getDataInicio(), dataFinal));
			}
			return new RetornoServico<List<Historico>>(Codigo.SUCESSO, historicos);
		} catch (Exception e) {
			e.printStackTrace();
			return new RetornoServico<List<Historico>>(Codigo.ERRO, e.getMessage());
		}
	}

	@Override
	public RetornoServico<String> alterarStatus(Long idOrdemServico, Integer idEtapa) {
		try {
			OrdemServico ordemServico = em.find(OrdemServico.class, idOrdemServico);
			Historico historico = new Historico();
			if(ordemServico.getHistoricoAtual() != null) {
				if(idEtapa != null && ordemServico.getHistoricoAtual().getEtapa().getId().intValue() == idEtapa.intValue()) {
					return new RetornoServico<String>(Codigo.SUCESSO);
				}
				ordemServico.getHistoricoAtual().setDataFim(new Date());
				ordemServico.getHistoricoAtual().setStatus(Status.CONCLUIDO);
			}
			if(idEtapa != null) {
				ordemServico.getHistoricoAtual().setProximo(historico);
				Etapa etapa = em.find(Etapa.class, idEtapa);
				historico.setDataInicio(new Date());
				historico.setAnterior(ordemServico.getHistoricoAtual());
				historico.setOrdemServico(ordemServico);
				historico.setStatus(Status.EM_ANDAMENTO);
				historico.setEtapa(etapa);
				em.persist(historico);
				ordemServico.setHistoricoAtual(historico);
			}
			return new RetornoServico<String>(Codigo.SUCESSO);
		} catch (Exception e) {
			e.printStackTrace();
			return new RetornoServico<String>(Codigo.ERRO, e.getMessage(), null);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public RetornoServico<List<EnderecoEntrega>> obterEnderecos(Long idOrdemServico) {
		try {
			Query query = em.createQuery("SELECT ee FROM EnderecoEntrega ee WHERE ee.ordemServico.idOrdemServico = :idOrdemServico");
			query.setParameter("idOrdemServico", idOrdemServico);
			List<EnderecoEntrega> enderecosEntrega = query.getResultList();
			return new RetornoServico<List<EnderecoEntrega>>(Codigo.SUCESSO, enderecosEntrega);
		} catch (Exception e) {
			e.printStackTrace();
			return new RetornoServico<List<EnderecoEntrega>>(Codigo.ERRO, e.getMessage());
		}
	}

	@Override
	public RetornoServico<String> inserirEnderecoEntrega(Long idOrdemServico, Endereco endereco) {
		try {
			OrdemServico ordemServico = em.find(OrdemServico.class, idOrdemServico);
			EnderecoEntrega enderecoEntrega = new EnderecoEntrega(endereco);
			enderecoEntrega.setOrdemServico(ordemServico);
			enderecoEntrega.setStatus(Status.PENDENTE);
			em.persist(enderecoEntrega);
			return new RetornoServico<String>(Codigo.SUCESSO);
		} catch (Exception e) {
			e.printStackTrace();
			return new RetornoServico<String>(Codigo.ERRO, e.getMessage(), null);
		}
	}

	@Override
	public RetornoServico<String> apagarEnderecoEntrega(Long idOrdemServico, Long idEndereco) {
		try {
			EnderecoEntrega enderecoEntrega = em.find(EnderecoEntrega.class, idEndereco);
			em.remove(enderecoEntrega);
			return new RetornoServico<String>(Codigo.SUCESSO);
		} catch (Exception e) {
			e.printStackTrace();
			return new RetornoServico<String>(Codigo.ERRO, e.getMessage(), null);
		}
	}

	@Override
	public Foto atualizarCaminhoCompleto(Foto foto) {
		String caminhoBase = configuracaoService.obterConfiguracao(ChaveConfiguracao.CAMINHO_BASE.ordinal()).getData().getValor();
		if(foto == null || foto.getCaminho() == null) {
			String caminhoCompleto = caminhoBase + "img/semimagem.jpg";
			foto = new Foto();
			foto.setCaminhoCompletoThumb(caminhoCompleto);
			return foto;
		} else {
			String caminhoCompleto = caminhoBase + "foto/" + foto.getCaminho();
			foto.setCaminhoCompleto(FileUtil.obterCaminhoThumbWeb(caminhoCompleto,FileUtil.TAMANHO_NORMAL));
			foto.setCaminhoCompletoThumb(FileUtil.obterCaminhoThumbWeb(caminhoCompleto,FileUtil.TAMANHO_PEQUENO));
			return foto;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public RetornoServico<OrdemServico> obterOrdemServico(String codigoOs) {
		try {
			Query query = em.createQuery("SELECT os FROM OrdemServico os WHERE os.numero = :numero");
			query.setParameter("numero", codigoOs);
			List<OrdemServico> os = query.getResultList();
			if(os.isEmpty()) {
				return new RetornoServico<OrdemServico>(Codigo.SUCESSO);
			}
			return new RetornoServico<OrdemServico>(Codigo.SUCESSO, os.get(0));
		} catch (Exception e) {
			e.printStackTrace();
			return new RetornoServico<OrdemServico>(Codigo.ERRO, e.getMessage(), null);
		}
	}

	@Override
	public RetornoServico<String> inserirEnderecosEntrega(Long idOrdemServico, MultipartFormDataInput data) {
		try {
			OrdemServico ordemServico = em.find(OrdemServico.class, idOrdemServico);
			List<InputPart> parts = data.getParts();
			for (InputPart part : parts) {
				InputStream inputStream = part.getBody(InputStream.class,null);
				ConversorTxt conversor = new ConversorTxt();
				List<Endereco> enderecos = conversor.planilhaToEndereco(inputStream);
				for (Endereco endereco : enderecos) {
	    			EnderecoEntrega enderecoEntrega = new EnderecoEntrega(endereco);
	    			enderecoEntrega.setOrdemServico(ordemServico);
	    			enderecoEntrega.setStatus(Status.PENDENTE);
	    			em.persist(enderecoEntrega);
				}
			}

			return new RetornoServico<String>(Codigo.SUCESSO);
		} catch (Exception e) {
			e.printStackTrace();
			return new RetornoServico<String>(Codigo.ERRO, e.getMessage(), null);
		}
	}

	private void atualizarRetornoOrdemServico(OrdemServico ordemServico) {
		String diffData = "";
		if(ordemServico.getStatus().equals(Status.CONCLUIDO) && ordemServico.getHistoricoAtual() != null) {
			diffData = DadosUtil.formatarDiffData(ordemServico.getDataCriacao(), ordemServico.getHistoricoAtual().getDataFim());
		} else {
			diffData = DadosUtil.formatarDiffData(ordemServico.getDataCriacao(), new Date());
		}
		if(ordemServico.getHistoricoAtual() != null) {
			ordemServico.setNomeEtapaAtual(ordemServico.getHistoricoAtual().getEtapa().getNome());
		}
		ordemServico.setDiferencaDatas(diffData);

		if(ordemServico.getFoto() == null) {
			Foto foto = atualizarCaminhoCompleto(ordemServico.getFoto());
			ordemServico.setFoto(foto);
		} else {
			atualizarCaminhoCompleto(ordemServico.getFoto());
		}

		if(ordemServico.getNotaFiscal() != null) {
			ordemServico.getNotaFiscal().setDetalhesNota(null);
		}
	}

	@Override
	public RetornoServico<String> atualizarNotaFiscal(Long idOrdemServico, NotaFiscal notaFiscal) {
		if(notaFiscal == null) {
			return new RetornoServico<String>(Codigo.ALERTA, "notaFiscal == null", null);
		}
		OrdemServico ordemServicoBase = em.find(OrdemServico.class, idOrdemServico);
		if(ordemServicoBase == null) {
			return new RetornoServico<String>(Codigo.ALERTA, "ordemServicoBase == null", null);
		}
		ordemServicoBase.setDataModificacao(new Date());

		if(ordemServicoBase.getNotaFiscal() == null) {
			em.persist(notaFiscal);
			ordemServicoBase.setNotaFiscal(notaFiscal);
		} else {
			ordemServicoBase.getNotaFiscal().setNumero(notaFiscal.getNumero());
			ordemServicoBase.getNotaFiscal().setValor(notaFiscal.getValor());
			ordemServicoBase.getNotaFiscal().setPaga(notaFiscal.getPaga());
			em.merge(ordemServicoBase.getNotaFiscal());
			
			List<DetalheNota> detalhesNotaBase = ordemServicoBase.getNotaFiscal().getDetalhesNota();
			if(detalhesNotaBase == null) {
				detalhesNotaBase = new ArrayList<DetalheNota>();
			}
			
			for (DetalheNota detalheNotaBase : detalhesNotaBase) {
				boolean existsInvoice = false;
				for (DetalheNota detalheNota : notaFiscal.getDetalhesNota()) {
					if (detalheNotaBase.equals(detalheNota)) {
						existsInvoice = true;
						break;
					}
				}
				if (!existsInvoice) {
					em.remove(detalheNotaBase);
				}			
			}			

			for (DetalheNota detalheNota : notaFiscal.getDetalhesNota()) {
				detalheNota.setNotaFiscal(ordemServicoBase.getNotaFiscal());
				if(detalheNota.getIdDetalheNota() == null) {
					detalhesNotaBase.add(detalheNota);
					em.persist(detalheNota);
				} else {
					em.merge(detalheNota);
				}
			}
		}
		em.merge(ordemServicoBase);
		return new RetornoServico<String>(Codigo.SUCESSO);
	}

	@Override
	public RetornoServico<String> aprovarFoto(Long idOrdemServico, Long idFoto, Boolean aprova) {
		try {
			Foto foto = em.find(Foto.class, idFoto);
			foto.setAprovada(aprova);
			em.merge(foto);
			return new RetornoServico<String>(Codigo.SUCESSO);
		} catch (Exception e) {
			return new RetornoServico<String>(Codigo.ERRO, e.getMessage(), null);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public RetornoServico<List<DetalheNota>> obterDetalhesNota(Long idNotaFiscal) {
		try {
			Query query = em.createQuery("SELECT dn FROM DetalheNota dn WHERE dn.notaFiscal.idNotaFiscal = :idNotaFiscal");
			query.setParameter("idNotaFiscal", idNotaFiscal);
			List<DetalheNota> detalhesNota = query.getResultList();
			return new RetornoServico<List<DetalheNota>>(Codigo.SUCESSO, detalhesNota);
		} catch (Exception e) {
			return new RetornoServico<List<DetalheNota>>(Codigo.ERRO, e.getMessage());
		}
	}

	@Override
	public RetornoServico<String> inserirReferenciaEntrega(Long idEnderecoEntrega, String codigoReferencia) {
		EnderecoEntrega enderecoEntrega = em.find(EnderecoEntrega.class, idEnderecoEntrega);
		ReferenciaEntrega referencia = new ReferenciaEntrega();
		referencia.setAtivo(true);
		referencia.setCodigoReferencia(codigoReferencia);
		referencia.setDataCriacao(new Date());
		referencia.setEnderecoEntrega(enderecoEntrega);
		referencia.setTipoEntrega(TipoEntrega.obterTipoEntrega(codigoReferencia));
		enderecoEntrega.setStatus(Status.EM_ANDAMENTO);
		em.persist(referencia);
		return new RetornoServico<String>(Codigo.SUCESSO);
	}

	@SuppressWarnings("unchecked")
	@Override
	public RetornoServico<List<Evento>> obterEventosEntrega(String codigo) {
		try {
			Rastreio rastreio = RastreioFactory.getInstance(codigo);
			if(rastreio == null) {
				return new RetornoServico<>();
			}
			Configuracao configWsdl = configuracaoService.obterConfiguracao(rastreio.getTokenWsdl()).getData();
			Configuracao configUser = configuracaoService.obterConfiguracao(rastreio.getTokenUsuario()).getData();
			Configuracao configPassword = configuracaoService.obterConfiguracao(rastreio.getTokenSenha()).getData();
			List<Evento> eventos = rastreio.obterEventos(codigo, configUser.getValor(), configPassword.getValor(), configWsdl.getValor());
			for (Evento evento : eventos) {
				if(evento.getDescricao().toUpperCase().contains("ENTREGUE")) {
					Query query = em.createQuery("SELECT os FROM ReferenciaEntrega re JOIN re.enderecoEntrega ee JOIN ee.ordemServico os WHERE re.codigoReferencia = :codigoReferencia");
					query.setParameter("codigoReferencia", codigo);
					List<OrdemServico> ordensServico = query.getResultList();
					for (OrdemServico ordemServico : ordensServico) {
						encerrarPorEntrega(ordemServico.getIdOrdemServico(), codigo);
					}
				}
			}
			return new RetornoServico<List<Evento>>(Codigo.SUCESSO, eventos);
		} catch (Exception e) {
			return new RetornoServico<List<Evento>>(Codigo.ERRO, e.getMessage());
		}
	}

	public void encerrarPorEntrega(Long idOrdemServico, String codigoReferencia) {
		OrdemServico ordemServico = em.find(OrdemServico.class, idOrdemServico);

		if(ordemServico.getStatus().equals(Status.CONCLUIDO)) {
			return;
		}
		List<EnderecoEntrega> enderecosEntrega = ordemServico.getEnderecosEntrega();
		boolean concluida = true;
		for (EnderecoEntrega enderecoEntrega : enderecosEntrega) {
			List<ReferenciaEntrega> referenciasEntrega = enderecoEntrega.getReferenciasEntrega();
			concluida = !referenciasEntrega.isEmpty() && concluida;
			boolean enderecoConcluido = true;
			for (ReferenciaEntrega referenciaEntrega : referenciasEntrega) {
				if((referenciaEntrega.getStatus() == null || !referenciaEntrega.getStatus().equals(Status.CONCLUIDO)) && !referenciaEntrega.getCodigoReferencia().equalsIgnoreCase(codigoReferencia)) {
					concluida = false;
					enderecoConcluido = false;
				} else if(referenciaEntrega.getCodigoReferencia().equalsIgnoreCase(codigoReferencia)) {
					referenciaEntrega.setStatus(Status.CONCLUIDO);
				}
			}
			if(enderecoConcluido && !referenciasEntrega.isEmpty()) {
				enderecoEntrega.setStatus(Status.CONCLUIDO);
			}
		}

		if(concluida) {
			alterarStatus(ordemServico.getId(), null);
			ordemServico.setStatus(Status.CONCLUIDO);
		}
	}

	@SuppressWarnings("unchecked")
	@Schedule(minute="0", second="0")
	public void sincronizarReferenciaEntrega() {
		Query query = em.createQuery("SELECT re FROM ReferenciaEntrega re WHERE re.tipoEntrega IN(:tipoEntrega) AND re.status <> :status");
		List<TipoEntrega> tiposEntrega = new ArrayList<>();
		tiposEntrega.add(TipoEntrega.CORREIOS);
		tiposEntrega.add(TipoEntrega.JADLOG);
		query.setParameter("tipoEntrega", tiposEntrega);
		query.setParameter("status", Status.CONCLUIDO);
		List<ReferenciaEntrega> referencias = query.getResultList();
		for (ReferenciaEntrega referenciaEntrega : referencias) {
			obterEventosEntrega(referenciaEntrega.getCodigoReferencia());
		}
	}

}
