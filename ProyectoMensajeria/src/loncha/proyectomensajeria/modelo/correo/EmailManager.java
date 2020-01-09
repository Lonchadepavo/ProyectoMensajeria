package loncha.proyectomensajeria.modelo.correo;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailManager {
	Properties propiedadesMail = new Properties();
	Session session;
	
	String emailSender = "maildeconfirmacionprueba@gmail.com";
	String passwordSender = "Password123_";
	
	String sujetoMailConfirmacion = "Código de confirmación";
	String mensajeMailConfirmacion = "Copia el siguiente código y pégalo en la aplicación: ";
	
	public void setMailConfig() {
		propiedadesMail.put("mail.smtp.auth", "true");
		propiedadesMail.put("mail.smtp.starttls.enable", "true");
		propiedadesMail.put("mail.smtp.host", "smtp.gmail.com");
		propiedadesMail.put("mail.smtp.port",587);
		
		session = Session.getInstance(propiedadesMail, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(emailSender, passwordSender);
			}
		});
	}
	
	public boolean emailConfirmacion(String mailDestino, int codigo) {
		try {
			setMailConfig();
			Message mensaje = prepararMensaje(session, emailSender, mailDestino, codigo, sujetoMailConfirmacion, mensajeMailConfirmacion);
			Transport.send(mensaje);
			
			return true;
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private static Message prepararMensaje(Session session, String email, String recipient, int codigo, String sujeto, String texto) {		
		try {
			Message mensaje = new MimeMessage(session);
			mensaje.setFrom(new InternetAddress(email));
			mensaje.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
			mensaje.setSubject(sujeto);
			mensaje.setText(texto + codigo);
			
			return mensaje;
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return null;
	}
 

}
