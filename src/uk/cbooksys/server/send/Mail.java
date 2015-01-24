package uk.cbooksys.server.send;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
public class Mail {
	public static String FROM_EMAIL = "tarundhillon@gmail.com";
	public static String FROM_TEXT = "Booking System Name";

	public static void sendMail(String toEmail,String toText, String subject, String message) {
		sendMail(FROM_EMAIL,FROM_TEXT,toEmail,toText,subject,message);
	}	
	public static void sendMail(String fromEmail,String fromText, String toEmail,String toText, String subject, String message){

		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);


		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(fromEmail, fromText)); 
			msg.addRecipient(Message.RecipientType.TO,
					new InternetAddress(toEmail,toText));
			msg.setSubject(subject);
			msg.setText(message);
			Transport.send(msg);

		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}
}


