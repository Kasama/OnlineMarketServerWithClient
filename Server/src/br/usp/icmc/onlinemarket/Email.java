package br.usp.icmc.onlinemarket;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Email {

	Message msg;
	Session session;
	Properties props;

	public Email(String subject, String message, InternetAddress email) {
		props = new Properties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "587");
		props.put(
			"mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"
		);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "587");

		session = Session.getDefaultInstance(
			props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(
						"athenewson@gmail.com", "cilindrodeozonio"
					);
				}
			}
		);
//		session.setDebug(true);

		msg = new MimeMessage(session);
		try {
			msg.setFrom(new InternetAddress("athenewson@gmail.com"));
			msg.addRecipient(Message.RecipientType.TO, email);
			msg.setSubject(subject);
			msg.setText(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

	public void send() {
		try {
			Transport.send(msg);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}
