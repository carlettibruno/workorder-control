package br.com.carlettisolucoes.thermalprinter;


public enum StatusImpressora {

	ERRO_COMUNICACAO,
	OK_COMUNICACAO,
	POUCO_PAPEL,
	TAMPA_ABERTA,
	ONLINE,
	SEM_PAPEL;	
	
	public static StatusImpressora obterStatusImpressora(int numeroStatusImpressora) {
		StatusImpressora statusRetorno = null;
		switch (numeroStatusImpressora) {
		case Constantes.ERRO_COMUNICACAO:
			statusRetorno = ERRO_COMUNICACAO;
			break;
			
		case Constantes.IMPRESSORA_ONLINE:
			statusRetorno = ONLINE;
			break;
			
		case Constantes.POUCO_PAPEL:
			statusRetorno = POUCO_PAPEL;
			break;
			
		case Constantes.IMPRESSORA_SEM_PAPEL:
			statusRetorno = SEM_PAPEL;
			break;
			
		case Constantes.TAMPA_ABERTA:
			statusRetorno = TAMPA_ABERTA;
			break;			

		default:
			statusRetorno = OK_COMUNICACAO;
			break;
		}
		return statusRetorno;
	}
	
}
