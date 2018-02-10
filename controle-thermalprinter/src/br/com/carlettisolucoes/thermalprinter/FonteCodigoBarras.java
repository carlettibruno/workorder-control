package br.com.carlettisolucoes.thermalprinter;


public enum FonteCodigoBarras {

	NORMAL(Constantes.CODIGO_BARRAS_FONTE_NORMAL),
	CONDENSADO(Constantes.CODIGO_BARRAS_FONTE_CONDENSADA);
	
	private int numeroFonte;
	
	private FonteCodigoBarras(int numeroFonte) {
		this.numeroFonte = numeroFonte;
	}

	public int getNumeroFonte() {
		return numeroFonte;
	}
	
}