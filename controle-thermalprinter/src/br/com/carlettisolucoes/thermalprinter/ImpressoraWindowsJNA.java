package br.com.carlettisolucoes.thermalprinter;

import com.sun.jna.Library;

public interface ImpressoraWindowsJNA extends Library {
	
	void ConfiguraTaxaSerial(int i);  

	int IniciaPorta(String PORTA);  

	int BematechTX(String texto);  

	int ConfiguraModeloImpressora(int i);  

	int FormataTX(String texto, int i, int j, int k, int l, int m);  

	int FechaPorta();  

	int PrinterReset();  

	int AjustaLarguraPapel(int i);  

	int HabilitaEsperaImpressao(int i);
	
	void EsperaImpressao();

	int Le_Status();
	
	int AcionaGuilhotina(int i);
	
	int ImprimeCodigoBarrasCODE128(String codigo);
	
	int ImprimeCodigoBarrasCODABAR(String codigo);

	int ImprimeCodigoBarrasCODE93(String codigo);
	
	int ImprimeCodigoBarrasITF(String codigo);
	
	int ConfiguraCodigoBarras(int altura, int largura, int posicaoCaracteres, int fonte, int margem);
	
	int ImprimeBitmap(String caminho, int posicao);

}  