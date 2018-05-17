package send.mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by plesha on 16-May-18.
 */
public class Email {

    private static final String username = "shows.management1@gmail.com";
    private static final String password = "Management1";

    public static void sendEmail( String recieverName, String emailTo, String showName ){

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("shows.management1@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo)); // address to send

            message.setSubject("New Show uploaded !");
            message.setText("Dear " + recieverName + ","
                    + "\n\nOne of your shows that you are interested in has been uploaded on our application. \n"+
                    "We are looking forward to see you log in and watch it ! \n"+
                    "Show name: "+ showName +" \n" +
                    "Status: AVAILABLE !\n\n" +
                    "Best regards, ShoWS Team" );

            Transport.send(message);

            System.out.println("E-mail sent successfully!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
