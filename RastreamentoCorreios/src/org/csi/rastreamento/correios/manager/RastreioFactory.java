package org.csi.rastreamento.correios.manager;

public class RastreioFactory {

	public static Rastreio getInstance(String codigoRastreio) {
		String regexCorreios = "[A-Z]{2}[0-9]{9}[A-Z]{2}";
		String regexJadlog = "[0-9]{14}";
		
		if(codigoRastreio.matches(regexCorreios)) {
			return new CorreiosRastreio();
		} else if (codigoRastreio.matches(regexJadlog)) {
			return new JadlogRastreio();
		} else {
//			throw new IllegalArgumentException(codigoRastreio);
			return null;
		}
	}
	
}
