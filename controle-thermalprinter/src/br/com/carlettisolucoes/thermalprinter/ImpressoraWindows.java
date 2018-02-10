package br.com.carlettisolucoes.thermalprinter;

public class ImpressoraWindows {

	private ImpressoraWindowsJNA impressora;
	
	public ImpressoraWindows(ImpressoraWindowsJNA impressora) {
		this.impressora = impressora;
	}
	
	public StatusImpressora obterStatusImpressora() {
		int status = impressora.Le_Status();
		return StatusImpressora.obterStatusImpressora(status);
	}

	public int imprimirTexto(String texto, boolean italico, boolean negrito, boolean expandido, boolean sublinhado, TipoLetra tipoLetra) {
		
		int italicoInt = italico ? Constantes.FONTE_ATIVA_MODO : Constantes.FONTE_DESATIVA_MODO;
		int negritoInt = negrito ? Constantes.FONTE_ATIVA_MODO : Constantes.FONTE_DESATIVA_MODO;
		int expandidoInt = expandido ? Constantes.FONTE_ATIVA_MODO : Constantes.FONTE_DESATIVA_MODO;
		int sublinhadoInt = sublinhado ? Constantes.FONTE_ATIVA_MODO : Constantes.FONTE_DESATIVA_MODO;
		
		int retorno = impressora.FormataTX(texto, tipoLetra.getNumeroTipoLetra(), italicoInt, sublinhadoInt, expandidoInt, negritoInt);
		return retorno;
	}
	
	public int imprimirBitmap(String caminho) {
		return impressora.ImprimeBitmap(caminho, 0);
	}

	public int imprimirCodigoBarras(String codigo, int altura, int largura, int margem, FonteCodigoBarras fonteCodigoBarras, boolean textoAbaixo, boolean textoAcima) {
		ImpressoraUtil.validarAlturaCodigoBarras(altura);
		ImpressoraUtil.validarLarguraCodigoBarras(largura);
		ImpressoraUtil.validarMargemCodigoBarras(margem);
		int posicaoCaracteres = ImpressoraUtil.obterNumeroCaracterCodigoBarras(textoAbaixo, textoAcima);
		
		impressora.ConfiguraCodigoBarras(altura, largura, posicaoCaracteres, fonteCodigoBarras.getNumeroFonte(), margem);
		int retorno = impressora.ImprimeCodigoBarrasCODE93(codigo);
		return retorno;
	}

	public int cortarPapel(CortePapel cortePapel) {
		int retorno = impressora.AcionaGuilhotina(cortePapel.getNumeroCortePapel());
		return retorno;
	}

	public void imprimirProduto(String qtde, String descricao, String precoUnit, String precoTotal, boolean negrito) {
		String linha = "";
		linha += String.format("%-"+(ImpressaoEnum.QTDE.getFim() - ImpressaoEnum.QTDE.getInicio())+"s", qtde);
		int tamanhoDescricao = ImpressaoEnum.DESCRICAO.getFim() - ImpressaoEnum.DESCRICAO.getInicio();
		linha += String.format("%-"+(tamanhoDescricao)+"s", descricao).substring(0, tamanhoDescricao);
		linha += String.format("%"+(ImpressaoEnum.PRECO_UNIT.getFim() - ImpressaoEnum.PRECO_UNIT.getInicio())+"s", precoUnit);
		linha += String.format("%"+(ImpressaoEnum.PRECO.getFim() - ImpressaoEnum.PRECO.getInicio())+"s", precoTotal);
		linha += "\n";
		imprimirTexto(linha, false, negrito, false, false, TipoLetra.NORMAL);		
	}
	
	public void imprimirPizzaCaixa(String descricao, String precoUnit, String precoTotal, boolean negrito) {
		String linha = "";
		linha += String.format("%-"+(33)+"s", descricao).substring(0, 33);
		linha += String.format("%"+(ImpressaoEnum.PRECO_UNIT.getFim() - ImpressaoEnum.PRECO_UNIT.getInicio())+"s", precoUnit);
		linha += String.format("%"+(ImpressaoEnum.PRECO.getFim() - ImpressaoEnum.PRECO.getInicio())+"s", precoTotal);
		linha += "\n";
		imprimirTexto(linha, false, negrito, false, false, TipoLetra.NORMAL);		
	}	

	public ImpressoraWindowsJNA getImpressora() {
		return impressora;
	}

	public void setImpressora(ImpressoraWindowsJNA impressora) {
		this.impressora = impressora;
	}

}