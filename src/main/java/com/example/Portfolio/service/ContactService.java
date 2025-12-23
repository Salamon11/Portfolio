package com.example.Portfolio.service;

import com.example.Portfolio.entity.ContactMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${contact.email.recipient}")
    private String recipientEmail;

    public void sendContactEmail(ContactMessage message) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setTo(recipientEmail);
            mailMessage.setSubject("Portfolio Contact Form - Message from " + message.getName());
            mailMessage.setReplyTo(message.getEmail());

            String emailBody = String.format(
                    "You have received a new message from your portfolio contact form.\n\n" +
                            "Name: %s\n" +
                            "Email: %s\n\n" +
                            "Message:\n%s\n\n" +
                            "---\n" +
                            "You can reply directly to this email to respond to %s",
                    message.getName(),
                    message.getEmail(),
                    message.getMessage(),
                    message.getName()
            );

            mailMessage.setText(emailBody);

            mailSender.send(mailMessage);

        } catch (Exception e) {
            throw new RuntimeException("Failed to send email: " + e.getMessage(), e);
        }
    }
}