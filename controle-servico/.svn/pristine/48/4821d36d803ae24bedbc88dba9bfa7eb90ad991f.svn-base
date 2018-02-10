package org.csi.controle.servico.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.csi.controle.core.entidade.OrdemServico;
import org.csi.controle.core.util.Codigo;
import org.csi.controle.core.util.RetornoServico;
import org.csi.controle.servico.DeParaService;
import org.csi.controle.servico.OrdemServicoService;

@Stateless
public class DeParaServiceImpl implements DeParaService {

	@EJB
	private OrdemServicoService ordemServicoService;
	
	@Override
	public RetornoServico<Long> obterOsOriginal(String codigoOs) {
		RetornoServico<OrdemServico> retorno = ordemServicoService.obterOrdemServico(codigoOs);
		if(retorno.getCodigo().intValue() == Codigo.SUCESSO) {
			return new RetornoServico<Long>(retorno.getCodigo(), retorno.getData().getIdOrdemServico());
		} else {
			return new RetornoServico<Long>(retorno.getCodigo(), retorno.getMensagem(), null);
		}
	}

}
