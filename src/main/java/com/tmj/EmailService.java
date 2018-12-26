package com.tmj;

import com.tmj.tms.config.datalayer.service.SystemPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Properties;

/**
 * Created by Thilangaj on 4/10/2017 11:32 AM).
 */
@Service
public class EmailService {

    private final SystemPropertyService systemPropertyService;

    @Autowired
    public EmailService(SystemPropertyService systemPropertyService) {
        this.systemPropertyService = systemPropertyService;
    }

    @Bean
    public JavaMailSender javaMailService() {
        JavaMailSenderImpl mailSender = null;
        try {
            String from = this.systemPropertyService.findByPropertyName("mail_sender").getPropertyValue();
            String emailHost = this.systemPropertyService.findByPropertyName("mail_host").getPropertyValue();
            String username = this.systemPropertyService.findByPropertyName("mail_username").getPropertyValue();
            String password = this.systemPropertyService.findByPropertyName("mail_password").getPropertyValue();
            String port = this.systemPropertyService.findByPropertyName("mail_port").getPropertyValue();

            mailSender = new JavaMailSenderImpl();
            mailSender.setHost(emailHost);
            mailSender.setPort(Integer.parseInt(port));

            mailSender.setUsername(username);
            mailSender.setPassword(password);

            Properties props = mailSender.getJavaMailProperties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.debug", "false");
            props.put("mail.smtp.host", emailHost);
            props.put("mail.smtp.port", port);
            props.put("mail.smtp.timeout", 1000 * 10);
            props.put("mail.smtp.connectiontimeout", 1000 * 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mailSender;
    }

    public JavaMailSender customMailService(String emailHost, String username, String password, String port) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(emailHost);
        mailSender.setPort(Integer.parseInt(port));

        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "false");
        props.put("mail.smtp.host", emailHost);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.timeout", 1000 * 10);
        props.put("mail.smtp.connectiontimeout", 1000 * 10);
        return mailSender;
    }

}
