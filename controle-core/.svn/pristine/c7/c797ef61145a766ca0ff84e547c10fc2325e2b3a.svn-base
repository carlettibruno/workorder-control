package org.csi.controle.core.entidade;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum TipoEntrega {
	
	PARTICULAR, CORREIOS, JADLOG;
	
	public static TipoEntrega obterTipoEntrega(String codigoReferencia) {
		Pattern pattern = Pattern.compile("[A-Za-z]{2}[0-9]{9}[A-Za-z]{2}");
		Matcher matcher = pattern.matcher(codigoReferencia);

		TipoEntrega tipoEntrega = TipoEntrega.PARTICULAR;
		while (matcher.find()) {
			tipoEntrega = TipoEntrega.CORREIOS;
		}
		
		return tipoEntrega;
	}
}