package com.tmj;

import com.tmj.tms.config.datalayer.service.SystemPropertyService;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.config.ServerConfig;
import org.simplejavamail.mailer.config.TransportStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Properties;

/**
 * Created by Thilangaj on 4/10/2017 11:32 AM).
 */
@Service
public class EmailServiceNew {

    private final SystemPropertyService systemPropertyService;

    @Autowired
    public EmailServiceNew(SystemPropertyService systemPropertyService) {
        this.systemPropertyService = systemPropertyService;
    }

    @Bean
    public Mailer javaMailService() {
        Mailer mailer = null;
        try {
            String from = this.systemPropertyService.findByPropertyName("mail_sender").getPropertyValue();
            String emailHost = this.systemPropertyService.findByPropertyName("mail_host").getPropertyValue();
            String password = this.systemPropertyService.findByPropertyName("mail_password").getPropertyValue();
            String port = this.systemPropertyService.findByPropertyName("mail_port").getPropertyValue();
            ServerConfig serverConfigSMTP = new ServerConfig(emailHost, Integer.parseInt(port), from, password);
            mailer = new Mailer(serverConfigSMTP, TransportStrategy.SMTP_TLS);
//        mailer.getSession().setDebug(true);
//        mailer.setDebug(true);
            Properties props = new Properties();
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.auth", "true");
            props.setProperty("mail.smtp.timeout", String.valueOf(1000 * 30));
            props.setProperty("mail.smtp.connectiontimeout", String.valueOf(30 * 1000));
            mailer.applyProperties(props);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mailer;
    }

    public Mailer customMailService(String from, String emailHost, String username, String password, String port) {
        ServerConfig serverConfigSMTP = new ServerConfig(emailHost, Integer.parseInt(port), from, password);
        return new Mailer(serverConfigSMTP, TransportStrategy.SMTP_TLS);
    }

}
