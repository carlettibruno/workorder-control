package org.csi.controle.status.lite;

public class Notificador implements Runnable {

	private Services services;
	
	public Notificador(Services services) {
		this.services = services;
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				services.notificar();
				Thread.sleep(60000 * 5);				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
