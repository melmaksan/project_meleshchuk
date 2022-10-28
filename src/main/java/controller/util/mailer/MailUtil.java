package controller.util.mailer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailUtil {

    private static final String FROM = "beauty_salon@beauty.com";  // sender email;
    private static final String USERNAME = "3d87a8cd215766"; //Mailtrap's username
    private static final String PASSWORD = "4da69804c88df1"; //Mailtrap's password
    private static final String HOST = "smtp.mailtrap.io";
    //Mailtrap's host address
    private static final Logger logger = LogManager.getLogger(MailUtil.class);

    public static void sendMessage(String recipient, String header, String body) throws MessagingException {
        //configure Mailtrap's SMTP server details
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.port", "587");
        //create the Session object
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(USERNAME, PASSWORD);
                    }
                });

        logger.info("session ==> " + session);

        //create a MimeMessage object
        Message message = new MimeMessage(session);
        //set From email field
        message.setFrom(new InternetAddress(FROM));
        //set To email field
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(recipient));
        //set email subject field
        message.setSubject(header);
        //set the content of the email message
        message.setText(body);
        //send the email message
        Transport.send(message);
        logger.info("Email Message Sent Successfully");

    }
}
