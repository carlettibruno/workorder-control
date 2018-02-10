package org.csi.controle.status.lite;

public class Notificador implements Runnable {

	private Services services;
	
	private Integer idEtapa;
	
	public Notificador(Services services, Integer idEtapa) {
		this.services = services;
		this.idEtapa = idEtapa;
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				services.notificar(idEtapa);
				long minutos = 5 * 60 * 1000;
				Thread.sleep(minutos);				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}

}
