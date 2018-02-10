package org.csi.controle.core.exception;

public class ServiceExpcetion extends Exception {
	
	private static final long serialVersionUID = 256493875224584745L;

	public ServiceExpcetion(Throwable e) {
		super(e);
	}
	
	public ServiceExpcetion(String str) {
		super(str);
	}	

}