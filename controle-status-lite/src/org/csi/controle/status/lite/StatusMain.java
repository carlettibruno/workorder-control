package org.csi.controle.status.lite;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StatusMain {

	public static void main(String[] args) throws IOException {
		String url = args[0];
		Services services = Services.getInstancia(url);
		Thread thread = new Thread(new Notificador(services));
		thread.start();
		while(true) {
			try {
				BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
				String codigoOs = bufferRead.readLine();
				services.mudarServico(Integer.parseInt(codigoOs));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}