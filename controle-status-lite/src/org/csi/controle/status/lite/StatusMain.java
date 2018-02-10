package org.csi.controle.status.lite;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StatusMain {

	public static void main(String[] args) throws IOException {
		String url = args[0];
		String usuario = args[1];
		String senha = args[2];
		Integer idEtapa = Integer.parseInt(args[3]);
		Services services = Services.getInstancia(url, usuario, senha);
		Thread thread = new Thread(new Notificador(services, idEtapa));
		thread.start();
		while(true) {
			try {
				BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
				String codigoOs = bufferRead.readLine();
				services.mudarServico(Integer.parseInt(codigoOs), idEtapa);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}