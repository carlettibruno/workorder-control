package org.csi.controle.servico.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.csi.controle.core.entidade.Etapa;
import org.csi.controle.core.entidade.OrdemServico;
import org.csi.controle.core.to.RequestHistorico;
import org.csi.controle.core.util.Codigo;
import org.csi.controle.core.util.RetornoServico;
import org.csi.controle.servico.EtapaService;
import org.csi.controle.servico.HistoricoService;
import org.csi.controle.servico.OrdemServicoService;

@Stateless
public class HistoricoServiceImpl implements HistoricoService {
	
	@EJB
	protected OrdemServicoService osService;
	
	@EJB
	protected EtapaService etapaService;	

	@Override
	public RetornoServico<String> create(RequestHistorico request) {
		RetornoServico<OrdemServico> os = osService.obterOrdemServico(request.getOscode());
		RetornoServico<Etapa> etapa = etapaService.findEtapaByReference(request.getReference());
		osService.alterarStatus(os.getData().getId(), etapa.getData().getIdEtapa());
		return new RetornoServico<String>(Codigo.SUCESSO);
	}

}