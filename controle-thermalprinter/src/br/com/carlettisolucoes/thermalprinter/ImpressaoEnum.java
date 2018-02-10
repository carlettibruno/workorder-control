package br.com.carlettisolucoes.thermalprinter;

public enum ImpressaoEnum {

	QTDE(0, 3),
	DESCRICAO(3, 33),
	PRECO_UNIT(33 ,39),
	PRECO(39 ,46);
	
	private int inicio;
	
	private int fim;

	private ImpressaoEnum(int inicio, int fim) {
		this.inicio = inicio;
		this.fim = fim;
	}

	public int getInicio() {
		return inicio;
	}

	public void setInicio(int inicio) {
		this.inicio = inicio;
	}

	public int getFim() {
		return fim;
	}

	public void setFim(int fim) {
		this.fim = fim;
	}

}