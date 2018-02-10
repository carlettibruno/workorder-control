package br.com.carlettisolucoes.thermalprinter;

public class ImpressoraUtil {

	public static int obterNumeroCaracterCodigoBarras(boolean abaixo, boolean acima) {
		if(abaixo && acima) {
			return Constantes.CODIGO_BARRAS_CARACTER_AMBOS;
		} else if(abaixo) {
			return Constantes.CODIGO_BARRAS_CARACTER_ABAIXO;
		} else if(acima) {
			return Constantes.CODIGO_BARRAS_CARACTER_ACIMA;
		} else {
			return Constantes.CODIGO_BARRAS_SEM_CARACTER;
		}
	}
	
	public static boolean validarAlturaCodigoBarras(int altura) {
		if(!(altura >= 1 && altura <= 255)) {
			throw new IllegalArgumentException("Altura deve ser entre 1 e 255.");
		}
		return true;
	}
	
	public static boolean validarLarguraCodigoBarras(int largura) {
		if(!(largura >= 0 && largura <= 2)) {
			throw new IllegalArgumentException("Largura deve ser entre 0 e 2.");
		}
		return true;
	}
	
	public static boolean validarMargemCodigoBarras(int margem) {
		if(!(margem >= 0 && margem <= 575)) {
			throw new IllegalArgumentException("Margem deve ser entre 0 e 575.");
		}
		return true;
	}	
	
}