/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.service;

import main.exception.EmailFailureException;
import main.models.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@Service
public class EmailService {
    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    @Value("${app.frontend.url}")
    private String url;
    private JavaMailSender mailSender;
    @Value("${email.from}")
    private String fromAddress;
    
    private SimpleMailMessage simpleMailMessage(){
        var mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(fromAddress);
        return mailMessage;
    }
    
    public void sendVerificationToken(VerificationToken token) throws EmailFailureException{
        var message = new SimpleMailMessage();
        message.setTo(token.getUser().getEmail());
        message.setSubject("Verify Your Email to Verify Your Account");
        message.setText("Please Follow The Link Below To Verify Your Email To Activate Your Account\n" + url
        + "/auth/verify?token=" + token.getToken());
        try{
            mailSender.send(message);
        }catch(MailException e){
            throw new EmailFailureException();
        }
    }
    
}
