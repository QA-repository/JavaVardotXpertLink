package src.test.tests;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class SendMail {

    public static void Sendthereport() {
        // Recipient's email ID needs to be mentioned.
        String to = "h.aljundi@vardot.com";
        // Sender's email ID needs to be mentioned
        String from = "";

        // Assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.socketFactory.fallback", "true");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        properties.put("mail.smtp.ssl.enable", false);
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication("", "");

            }

        });
        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));

            message.addRecipient(Message.RecipientType.CC, new InternetAddress(to));

            message.setSubject("Duplication test report");

            Multipart multipart = new MimeMultipart();

            MimeBodyPart attachmentPart = new MimeBodyPart();

            MimeBodyPart textPart = new MimeBodyPart();

            try {

                File Emailfile =new File(System.getProperty("user.dir")+"/test-output"+"/Duplication-List"+".txt");

                attachmentPart.attachFile(Emailfile);
                textPart.setText("Duplication test has been completed, for more information,refer to the attached report.");
                multipart.addBodyPart(textPart);
                multipart.addBodyPart(attachmentPart);

            } catch (IOException e) {

                e.printStackTrace();

            }

            message.setContent(multipart);

            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }

    }

}