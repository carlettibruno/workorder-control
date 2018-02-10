package org.csi.controle.servico.util;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.ws.rs.core.MultivaluedMap;

public class FileUtil {

	public static int TAMANHO_PEQUENO = 256;
	public static int TAMANHO_NORMAL = 768;
	
	public static synchronized String obterPathFoto(String raiz, String nomeArquivo) {
		Date dataAtual = new Date();
		SimpleDateFormat ano = new SimpleDateFormat("yyyy");
		SimpleDateFormat mes = new SimpleDateFormat("MM");
		SimpleDateFormat dia = new SimpleDateFormat("dd");
		StringBuilder sb = new StringBuilder();
		sb.append(File.separator);
		sb.append(ano.format(dataAtual));
		sb.append(File.separator);
		sb.append(mes.format(dataAtual));
		sb.append(File.separator);
		sb.append(dia.format(dataAtual));
		sb.append(File.separator);
		File pasta = new File(raiz + sb.toString());
		if(!pasta.exists()) {
			pasta.mkdirs();
		}		
		String extensao = nomeArquivo.substring(nomeArquivo.lastIndexOf("."), nomeArquivo.length());
		sb.append(System.currentTimeMillis());
		sb.append("_");
		sb.append(Math.round(Math.random() * 10000));
		sb.append(extensao);
		return sb.toString();
	}
	
	public static String getFileName(MultivaluedMap<String, String> header) {		 
		String[] contentDisposition = header.getFirst("Content-Disposition").split(";");
		for (String filename : contentDisposition) {
			if ((filename.trim().startsWith("filename"))) {
				String[] name = filename.split("=");
				String finalFileName = name[1].trim().replaceAll("\"", "");
				return finalFileName;
			}
		}
		return "unknown";
	}
	
	public static void redimensionarImagem(String caminhoOriginal, String caminhoDestino, Integer tamanhoMaximo) throws IOException {
		BufferedImage imagem = ImageIO.read(new File(caminhoOriginal));
		Integer proporcao = 100;
		Integer maior = 0;
		if(imagem.getWidth() > imagem.getHeight()) {
			maior = imagem.getWidth(); 
		} else {
			maior = imagem.getHeight();
		}
		if(maior > tamanhoMaximo) {
			proporcao = (tamanhoMaximo * 100) / maior;
		}
		
		Double novaImgLargura = (double) imagem.getWidth() * ((double)proporcao / (double)100);  
		Double novaImgAltura = (double) imagem.getHeight() * ((double)proporcao / (double)100);
		BufferedImage novaImagem = new BufferedImage(novaImgLargura.intValue(), novaImgAltura.intValue(), BufferedImage.TYPE_INT_RGB);
		Graphics g = novaImagem.getGraphics();  
        g.drawImage(imagem.getScaledInstance(novaImgLargura.intValue(), novaImgAltura.intValue(), 10000), 0, 0, null);  
        g.dispose();
		ImageIO.write(novaImagem, "JPG", new File(caminhoDestino));
	}
	
	public static String obterCaminhoThumb(String caminhoOriginal, Integer tamanho) {
		return caminhoOriginal.substring(0, caminhoOriginal.lastIndexOf(".")) + "_thumbs_" + tamanho + caminhoOriginal.substring(caminhoOriginal.lastIndexOf("."));
	}
	
	public static String obterCaminhoThumbWeb(String caminhoOriginal, Integer tamanho) {
		return obterCaminhoThumb(caminhoOriginal, tamanho).replace("\\", "/");
	}	

	public static String readString(InputStream is) throws IOException {
		char[] buf = new char[2048];
		Reader r = new InputStreamReader(is, "UTF-8");
		StringBuilder s = new StringBuilder();
		while (true) {
			int n = r.read(buf);
			if (n < 0)
				break;
			s.append(buf, 0, n);
		}
		return s.toString();
	}
	
}