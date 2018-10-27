package org.csi.controle.core.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.Date;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class DadosUtil {
	
	public static final boolean DEFAULT_APPROVAL_PHOTOS = false;

	public static void main(String[] args) {
		try {
			String senha = criptografar("bruno.carletti", "123123");
			System.out.println(senha);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String criptografar(String usuario, String senha) throws Exception {
		usuario = usuario.toLowerCase();
		String assinatura = usuario + senha;   
		assinatura = criptografar(assinatura);
		return assinatura;   
	}
	
	public static String criptografar(String usuario, String senha, Long tempo) throws Exception {
		usuario = usuario.toLowerCase();
		String assinatura = usuario + senha + tempo;   
		assinatura = criptografar(assinatura);
		return assinatura;   
	}
	
	public static String criptografar(String str) throws Exception {
		String retorno = "";
		try {   
			MessageDigest md = MessageDigest.getInstance("MD5");   
			md.update(str.getBytes());   
			byte[] hash = md.digest();   
			StringBuffer hexString = new StringBuffer();   
			for (int i = 0; i < hash.length; i++) {   
				if ((0xff & hash[i]) < 0x10) {   
					hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
				} else {   
					hexString.append(Integer.toHexString(0xFF & hash[i]));
				}
			}   
			retorno = hexString.toString();   
		} catch (Exception e) {   
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}   
		return retorno;
	}
	
	public String criptografarAES(String valor, String chave) throws Exception {
	    byte[] raw = chave.getBytes(Charset.forName("US-ASCII"));
	    if (raw.length != 16) {
	      throw new IllegalArgumentException("Invalid key size.");
	    }
	    SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
	    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	    cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(new byte[16]));
	    return new String(cipher.doFinal(valor.getBytes(Charset.forName("US-ASCII"))));		
	}
	
	public String descriptografarAES(String valor, String chave) throws Exception {
	    byte[] raw = chave.getBytes(Charset.forName("US-ASCII"));
	    if (raw.length != 16) {
	      throw new IllegalArgumentException("Invalid key size.");
	    }
	    SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
	    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	    cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(new byte[16]));
	    byte[] original = cipher.doFinal(valor.getBytes());
	    return new String(original, Charset.forName("US-ASCII"));		
	}
	
	public static String gerarSenha() throws Exception {
		return DadosUtil.criptografar("", "", System.currentTimeMillis()).substring(0, 6);
	}
	
	public static boolean ping(String ip) {
		boolean pingOk = false;
		try {
			pingOk = InetAddress.getByName(ip).isReachable(6000);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pingOk;
	}
	
	public static String formatarDiffData(Date dataInicial, Date dataFinal) {
		if(dataFinal == null || dataInicial == null) {
			return "";
		}
		long segundos = (dataFinal.getTime() - dataInicial.getTime()) / 1000;
		int dias = (int)Math.floor(segundos / 86400);
		segundos -= dias * 86400;
		int horas = (int)Math.floor(segundos / 3600);
		segundos -= horas * 3600;
		int minutos = (int)Math.floor(segundos / 60);
		segundos -= minutos * 60;
		String retorno = "";
		if(dias == 1) {
			retorno = dias + " dia, ";
		} else if(dias > 1) {
			retorno = dias + " dias, ";
		}
		if(horas > 0) {
			retorno += horas + "h, ";
		}
		if(minutos > 0) {
			retorno += minutos + "min e ";
		}		
		retorno += segundos + " seg";
		return retorno;
	}

	public static String getValor(String chave) throws IOException{

	    String versionString = null;

	    //to load application's properties, we use this class
	    Properties mainProperties = new Properties();

	    FileInputStream file;

	    //the base folder is ./, the root of the main.properties file  
	    String path = "./config.properties";

	    //load the file handle for main.properties
	    file = new FileInputStream(path);

	    //load all the properties from this file
	    mainProperties.load(file);

	    //we have loaded the properties, so close the file handle
	    file.close();

	    //retrieve the property we are intrested, the app.version
	    versionString = mainProperties.getProperty(chave);

	    return versionString;
	}	
	
}