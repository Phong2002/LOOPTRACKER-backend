package com.looptracker.looptracker.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;



@Service
@AllArgsConstructor
public class EmailService implements IEmailService{
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender javaMailSender;
    private TemplateEngine templateEngine;

    @Override
    public void send(String to, String subject, String template, Map<String, Object> templateModel) {
        try{
            Context context = new Context();
            context.setVariables(templateModel);
            String htmlBody = templateEngine.process(template, context);
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,"utf-8");
            helper.setTo(to);
            helper.setText(htmlBody,true);
            helper.setSubject(subject);
            javaMailSender.send(mimeMessage);
        }catch (MessagingException e){
            logger.error("Failed to send email");
            throw new IllegalStateException("Failed to send email");
        }
    }

}