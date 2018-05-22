package br.com.carlettisolucoes.thermalprinter.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Util {

	public static String getValor(String chave) throws IOException{
	    String value = null;
	    Properties properties = new Properties();
	    String path = "./config.properties";
//	    String path = "/config.properties";
	    InputStream is = Util.class.getClassLoader().getResourceAsStream(path);
//	    FileInputStream file = new FileInputStream(path);
	    properties.load(is);
//	    file.close();
	    is.close();
	    value = properties.getProperty(chave);
	    return value;
	}	
	
}