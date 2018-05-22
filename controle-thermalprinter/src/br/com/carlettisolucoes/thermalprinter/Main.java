package br.com.carlettisolucoes.thermalprinter;

import java.io.IOException;
import java.util.List;

import org.csi.controle.core.entidade.FilaImpressora;

import com.sun.jna.Native;

import br.com.carlettisolucoes.thermalprinter.service.ServiceRequest;
import br.com.carlettisolucoes.thermalprinter.util.Util;

public class Main implements Runnable {

	public static void main(String[] args) throws IOException {
		new Main().initPrinter();
	}

	private void initPrinter() {
		while(true) {
			System.out.println("Iniciando impressora...");
			try {
				ImpressoraWindows impressora = iniciarPorta(Util.getValor("impressora.ip"));
				ServiceRequest sr = ServiceRequest.getInstance();
				while(true) {
					try {
						System.out.println("Buscando impressoes...");
						List<FilaImpressora> filaImpressora = sr.getListInfoPrint();
						System.out.println("Impressoes recuperadas: "+filaImpressora.size());
						for (FilaImpressora fi : filaImpressora) {
							int qtde = fi.getNumberOfCopies();
							for (int i = 0; i < qtde; i++) {
								impressora.imprimirTexto("Ordem de serviço impressa: "+fi.getNumberOs(), false, true, false, false, TipoLetra.NORMAL);
								impressora.imprimirCodigoBarras(fi.getNumberOs().toString(), 240, 2, 10, FonteCodigoBarras.NORMAL, true, false);
								if(i != qtde - 1) {
									impressora.cortarPapel(CortePapel.CORTE_PARCIAL);
								}
							}
							System.out.println("Impressão realizada: "+fi.getNumberOs());
							sr.removeFilaImpressora(fi);
							System.out.println("Impressão notificada: "+fi.getNumberOs());
						}
						impressora.cortarPapel(CortePapel.CORTE_TOTAL);
						Thread.sleep(Long.parseLong(Util.getValor("impressora.tempo.espera")));
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						try {
							impressora.getImpressora().FechaPorta();
							impressora = null;

							impressora = iniciarPorta(Util.getValor("impressora.ip"));
							sr = ServiceRequest.getInstance();
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Falha: "+e.getMessage());
			}
			try {
				Thread.sleep(Long.parseLong(Util.getValor("impressora.tempo.espera")));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static ImpressoraWindows iniciarPorta(String ip) {
		ImpressoraWindowsJNA impressoraWindows = (ImpressoraWindowsJNA)Native.loadLibrary("mp2032",ImpressoraWindowsJNA.class);
		int retornoModelo = impressoraWindows.ConfiguraModeloImpressora(5);
		if(retornoModelo == -2) {
			System.out.println("Falha no retorno do modelo da impressora");
		}
		int retornoIniciarPorta = impressoraWindows.IniciaPorta(ip);
		if(retornoIniciarPorta == Constantes.ERRO_COMUNICACAO) {
			System.out.println("Falha ao iniciar porta da impressora");
		}
		ImpressoraWindows impressora = new ImpressoraWindows(impressoraWindows);
		impressoraWindows.HabilitaEsperaImpressao(1);
		return impressora;
	}

	@Override
	public void run() {
		initPrinter();
	}

}
