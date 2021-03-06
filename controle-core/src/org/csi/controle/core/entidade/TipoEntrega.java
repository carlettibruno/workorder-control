package org.csi.controle.core.entidade;

import org.csi.rastreamento.correios.manager.CorreiosRastreio;
import org.csi.rastreamento.correios.manager.DhlRastreio;
import org.csi.rastreamento.correios.manager.JadlogRastreio;
import org.csi.rastreamento.correios.manager.Rastreio;
import org.csi.rastreamento.correios.manager.RastreioFactory;

public enum TipoEntrega {
	
	PARTICULAR, CORREIOS, JADLOG, DHL;
	
	public static TipoEntrega obterTipoEntrega(String codigoReferencia) {
		Rastreio r = RastreioFactory.getInstance(codigoReferencia);
		if(r == null) {
			return TipoEntrega.PARTICULAR;
		} else if(r instanceof CorreiosRastreio) {
			return TipoEntrega.CORREIOS;
		} else if(r instanceof JadlogRastreio) {
			return TipoEntrega.JADLOG;
		} else if(r instanceof DhlRastreio) {
			return TipoEntrega.DHL;			
		} else {
			return TipoEntrega.PARTICULAR; 
		}
	}
}