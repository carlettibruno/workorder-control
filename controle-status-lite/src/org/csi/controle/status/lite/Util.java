package org.csi.controle.status.lite;

import java.net.NetworkInterface;
import java.util.Enumeration;

public class Util {

	public static String getMacAddress() throws Exception {
		String macStr = null;
		Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
		while (nis.hasMoreElements()) {
			NetworkInterface networkInterface = (NetworkInterface) nis.nextElement();
			byte[] mac = networkInterface.getHardwareAddress();
			if(mac != null && networkInterface.isUp()) {
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < mac.length; i++) {
					sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));		
				}
				macStr = sb.toString();
			}
		}
		System.out.println(macStr);
		return macStr;
	}
	
}
