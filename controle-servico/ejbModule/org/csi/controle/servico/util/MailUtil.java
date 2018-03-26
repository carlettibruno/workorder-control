package org.csi.controle.servico.util;

import java.io.File;
import java.io.InputStream;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.csi.controle.core.entidade.Cliente;
import org.csi.controle.core.entidade.Usuario;

public class MailUtil {
	
	public static void enviarEmailSenha(Usuario usuario, String linkCliente, String novaSenha, String caminhoBase, Session mailSession) throws Exception {
        ClassLoader loader = MailUtil.class.getClassLoader(); 
        InputStream in = loader.getResourceAsStream("layout_email.html");
        String conteudo = FileUtil.readString(in);
		MimeMessage m = new MimeMessage(mailSession);
		Address[] to = new InternetAddress[] {new InternetAddress(usuario.getEmail()) };
		m.setRecipients(Message.RecipientType.TO, to);
		m.setSubject("Lembrete de senha [Grafstock Festas]");
		m.setSentDate(new java.util.Date());
		conteudo = conteudo.replace("#USUARIO#", usuario.getNome().toUpperCase()).replace("#LINK_CLIENTE#", linkCliente).replace("#SENHA#", novaSenha).replace("#LOGIN#", usuario.getLogin()).replace("#CAMINHO_BASE#", caminhoBase);
		m.setContent(conteudo,"text/html; charset=utf-8");
		Transport.send(m);
	}
	
	public static void enviarEmailProtocolo(Cliente cliente, String numeroOrdemServico, String linkCliente, File protocolo, String caminhoBase, Session mailSession) throws Exception {
        ClassLoader loader = MailUtil.class.getClassLoader(); 
        InputStream in = loader.getResourceAsStream("layout_email_protocolo.html");
        String conteudo = FileUtil.readString(in);
		MimeMessage m = new MimeMessage(mailSession);
		Address[] to = new InternetAddress[] {new InternetAddress(cliente.getEmail()) };
		m.setRecipients(Message.RecipientType.TO, to);
		m.setSubject("Protocolo de entrega [Grafstock Festas]");
		m.setSentDate(new java.util.Date());
		conteudo = conteudo.replace("#USUARIO#", cliente.getNome().toUpperCase()).replace("#LINK_CLIENTE#", linkCliente).replace("#ORDEM_SERVICO#", numeroOrdemServico).replace("#CAMINHO_BASE#", caminhoBase);
		
		Multipart multipart = new MimeMultipart();
		
		BodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(conteudo,"text/html; charset=utf-8");
		multipart.addBodyPart(messageBodyPart);
		
		addAttachment(multipart, protocolo.getPath());
		
		m.setContent(multipart);
		
		Transport.send(m);
	}
	
	private static void addAttachment(Multipart multipart, String filename) throws MessagingException {
	    DataSource source = new FileDataSource(filename);
	    BodyPart messageBodyPart = new MimeBodyPart();        
	    messageBodyPart.setDataHandler(new DataHandler(source));
	    messageBodyPart.setFileName(filename);
	    multipart.addBodyPart(messageBodyPart);
	}	
	
}