package br.com.carlettisolucoes.thermalprinter.servlet;

import javax.servlet.http.HttpServlet;

import br.com.carlettisolucoes.thermalprinter.Main;

public class InitPrintServer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public InitPrintServer() {
		System.out.println("Iniciando Servlet...");
		Thread th = new Thread(new Main());
		th.start();
		System.out.println("Servlet iniciada.");
    }

}