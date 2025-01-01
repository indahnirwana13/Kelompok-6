/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import config.EmailConfig;
/**
 *
 * @author ahmad
 */
public class EmaiilService {
    
         public static void sendEmail(String recipientEmail, String subject, String body) {
         try {
            // Dapatkan sesi dari EmailConfig
            Session session = EmailConfig.getSession();
            
            // Buat pesan email
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EmailConfig.getSenderEmail()));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            message.setText(body);

            // Kirim email
            Transport.send(message);
            System.out.println("Email berhasil dikirim!");

        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Gagal mengirim email!");
        }
    }
    
}
