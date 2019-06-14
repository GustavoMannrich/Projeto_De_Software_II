package com.ggl.gerenciadorestudantil.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EnvioEmail {
	@Autowired
    private JavaMailSender mailSender;
 
	@Value("${spring.mail.username}")
	private String emailRemetente;
	
    public void enviar(String emailDestino, String assunto, String mensagem) {
        SimpleMailMessage email = new SimpleMailMessage();
        
        email.setTo(emailDestino);
        email.setSubject(assunto);
        email.setText(mensagem);
       
        mailSender.send(email);
    }
}
