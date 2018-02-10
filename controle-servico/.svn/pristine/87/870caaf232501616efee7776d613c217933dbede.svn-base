package org.csi.controle.servico.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class CheckProtocolo {

	private static int cont = 0;
	
	private static int total = 0;
	
	public static void main(String[] args) {
		File raiz = new File("C:\\Users\\Bruno\\Desktop\\Grafstock\\teste_protocolos\\");
		File[] imagens = raiz.listFiles();
		for (int z = 0; z < imagens.length; z++) {
			Double percento = new CheckProtocolo().percentualProtocolo(imagens[z]);
			System.out.println("Arquivo: "+imagens[z].getName()+" Total: "+total+" | Branco: "+ cont  +" %: "+ percento);
		}
	}
	
	public Double percentualProtocolo(File imagem) {
		try {
			BufferedImage img = ImageIO.read(imagem);
			
			total = img.getHeight() * img.getWidth();
			cont = 0;
			
			for(int i = 0; i < img.getHeight(); i++){
				for(int j = 0; j < img.getWidth(); j++){
					try {						
						readPixelData(img, j, i);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}	
		} catch (IOException e) {
			e.printStackTrace();
		}

		double percento = (double)cont/(double)total * 100;
		return percento;
	}
	
	private static void readPixelData(BufferedImage img, int x, int y) {
		int argb = img.getRGB(x, y);

		int rgb[] = new int[] {
				(argb >> 16) & 0xff, //red
				(argb >>  8) & 0xff, //green
				(argb      ) & 0xff  //blue
		};

		if(rgb[0] > 150 && rgb[1] > 140 && rgb[2] > 150 ) {
			cont++;
		}
	}		

}