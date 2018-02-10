package br.com.carlettisolucoes.thermalprinter;


public enum TipoLetra {

	COMPRIMIDO(Constantes.FONTE_TIPO_LETRA_COMPRIMIDO),
	NORMAL(Constantes.FONTE_TIPO_LETRA_NORMAL);

	private int numeroTipoLetra;
	
	private TipoLetra(int numeroTipoLetra) {
		this.numeroTipoLetra = numeroTipoLetra;
	}
	
	public int getNumeroTipoLetra() {
		return numeroTipoLetra;
	}
	
}