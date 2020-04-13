package com.datatools.notificar.util;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
public class DServicioEmail{
	private final Properties properties = new Properties();	
	//private String password; 
	private Session session;
 
	private void init() { 
		properties.put("mail.smtp.host", "smtp.gmail.com");//mail.gmail.com
		properties.put("mail.smtp.port",587);
		properties.put("mail.smtp.auth", "true");
		//properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        
        final String username = "jorgealejaramillo@gmail.com";
        final String password = "1013614015";
		session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
	}
 
	public void sendEmail(){ 
		init();
		try{
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress("jorgealejaramillo@gmail.com"));
			message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse("antoniojaramillo2020@gmail.com"));
			message.setSubject("Prueba");
			message.setText("Texto");
			Transport.send(message);

            System.out.println("Done");
            
//            
//			Transport t = session.getTransport("smtp");
//			t.connect((String)properties.get("mail.smtp.user"), "1013614015");
//			t.sendMessage(message, message.getAllRecipients());
//			t.close();
		}catch (MessagingException me){
            me.printStackTrace();
			return;
		}		
	}
 
}