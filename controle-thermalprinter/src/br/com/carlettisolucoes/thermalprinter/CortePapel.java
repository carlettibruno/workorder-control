package br.com.carlettisolucoes.thermalprinter;


public enum CortePapel {

	CORTE_PARCIAL(Constantes.GUILHOTINA_PARCIAL),
	CORTE_TOTAL(Constantes.GUILHOTINA_TOTAL);
	
	private int numeroCortePapel;
	
	private CortePapel(int numeroCortePapel) {
		this.numeroCortePapel = numeroCortePapel;
	}

	public int getNumeroCortePapel() {
		return numeroCortePapel;
	}
	
}
