package com.tmj.tms.utility;

import com.tmj.EmailService;
import com.tmj.EmailServiceNew;
import com.tmj.tms.config.datalayer.modal.UploadedDocument;
import com.tmj.tms.config.datalayer.service.SystemPropertyService;
import com.tmj.tms.config.datalayer.service.UploadedDocumentService;
import org.simplejavamail.email.Email;
import org.simplejavamail.mailer.Mailer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import java.io.File;
import java.util.List;

@Service
public class SendEmailAndSMS {

    private final EmailService emailService;
    private final UploadedDocumentService uploadedDocumentService;
    private final EmailServiceNew emailServiceNew;
    private final SystemPropertyService systemPropertyService;

    @Autowired
    public SendEmailAndSMS(EmailService emailService,
                           UploadedDocumentService uploadedDocumentService,
                           EmailServiceNew emailServiceNew,
                           SystemPropertyService systemPropertyService) {
        this.emailService = emailService;
        this.uploadedDocumentService = uploadedDocumentService;
        this.emailServiceNew = emailServiceNew;
        this.systemPropertyService = systemPropertyService;
    }

    public void sendEmails(final String[] bcc, final String[] cc, final String to[], final String subject, final String body) throws Exception {
        String mailSender = this.systemPropertyService.findByPropertyName("mail_sender").getPropertyValue();
        Mailer mailer = this.emailServiceNew.javaMailService();
        final Email email = new Email();
        email.setFromAddress("Road Runner", mailSender);
        email.addRecipients(this.emailAddressCreator(to), MimeMessage.RecipientType.TO);
        if (cc != null) {
            email.addRecipients(this.emailAddressCreator(cc), MimeMessage.RecipientType.CC);
        }
        if (bcc != null) {
            email.addRecipients(this.emailAddressCreator(bcc), MimeMessage.RecipientType.BCC);
        }
        email.setTextHTML(body);
        email.setSubject(subject);
        mailer.sendMail(email);
    }

    public void sendEmailWithAttachment(final String[] bcc, final String[] cc, final String[] to, final String subject, final String body, final List<EmailAttachment> attachmentList) throws Exception {
        String mailSender = this.systemPropertyService.findByPropertyName("mail_sender").getPropertyValue();
        Mailer mailer = this.emailServiceNew.javaMailService();
        final Email email = new Email();
        email.setFromAddress("Road Runner", mailSender);
        email.addRecipients(this.emailAddressCreator(to), MimeMessage.RecipientType.TO);
        if (cc != null) {
            email.addRecipients(this.emailAddressCreator(cc), MimeMessage.RecipientType.CC);
        }
        if (bcc != null) {
            email.addRecipients(this.emailAddressCreator(bcc), MimeMessage.RecipientType.BCC);
        }
        email.setTextHTML(body);
        email.setSubject(subject);
        if (attachmentList.size() > 0) {
            for (EmailAttachment attachment : attachmentList) {
                email.addAttachment(attachment.getFileName(), attachment.getDataSource());
            }
        }
        mailer.sendMail(email);
    }

    public void sendEmailWithAttachmentAndImage(final String[] bcc, final String[] cc, final String[] to, final String subject, final String body, final List<EmailAttachment> attachmentList, final List<String> imagePathList) throws Exception {
        String mailSender = this.systemPropertyService.findByPropertyName("mail_sender").getPropertyValue();
        Mailer mailer = this.emailServiceNew.javaMailService();
        final Email email = new Email();
        email.setFromAddress("Road Runner", mailSender);
        email.addRecipients(this.emailAddressCreator(to), MimeMessage.RecipientType.TO);
        if (cc != null) {
            email.addRecipients(this.emailAddressCreator(cc), MimeMessage.RecipientType.CC);
        }
        if (bcc != null) {
            email.addRecipients(this.emailAddressCreator(bcc), MimeMessage.RecipientType.BCC);
        }
        email.setTextHTML(body);
        email.setSubject(subject);
        if (attachmentList.size() > 0) {
            for (EmailAttachment attachment : attachmentList) {
                email.addAttachment(attachment.getFileName(), attachment.getDataSource());
            }
        }
        if (imagePathList != null) {
            for (int i = 0; i < imagePathList.size(); i++) {
                email.addEmbeddedImage("inlineImage" + i, new FileDataSource(new File(imagePathList.get(i))));
            }
        }
        mailer.sendMail(email);
    }

    public void sendMASEmailWithAttachmentAndImage(final String[] bcc, final String[] cc, final String[] to,
                                                   final String subject, final String body, final List<EmailAttachment> attachmentList,
                                                   final List<String> imagePathList) throws Exception {
        String from = "mas@expofreight.com";
        String username = "mas@expofreight.com";
        String password = "ns#%ny64";

//        String from = "efl-cdr@Road Runner.com";
//        String username = "efl-cdr@Road Runner.com";
//        String password = "THY%$#@fv";


        String host = "smtp.office365.com";
        String port = "587";
        MimeMessagePreparator email = mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(from);
            if (bcc != null) {
                helper.setBcc(bcc);
            }
            if (cc != null) {
                helper.setCc(cc);
            }
            if (to != null) {
                helper.setTo(to);
            }
            helper.setSubject(subject);
            helper.setText(body, true);
            helper.setPriority(1);
            if (imagePathList != null) {
                for (int i = 0; i < imagePathList.size(); i++) {
                    helper.addInline("inlineImage" + i, new File(imagePathList.get(i)));
                }
            }
            if (attachmentList.size() > 0) {
                for (EmailAttachment attachment : attachmentList) {
                    helper.addAttachment(attachment.getFileName(), attachment.getDataSource());
                }
            }
        };
        JavaMailSender sender = this.emailService.customMailService(host, username, password, port);
        sender.send(email);
    }

    public EmailAttachment createFileAttachment(Integer uploadedDocumentSeq) {
        UploadedDocument uploadedDocument = this.uploadedDocumentService.findUploadedDocumentByUploadedDocumentSeq(uploadedDocumentSeq);
        DataSource source = new ByteArrayDataSource(uploadedDocument.getFileData(), uploadedDocument.getFileType());
        EmailAttachment attachment = new EmailAttachment();
        attachment.setDataSource(source);
        attachment.setFileName(uploadedDocument.getFileName());
        return attachment;
    }

    private String emailAddressCreator(String[] addressList) {
        if (addressList.length > 0) {
            StringBuilder nameBuilder = new StringBuilder();

            for (String address : addressList) {
                nameBuilder.append(address).append(",");
            }
            nameBuilder.deleteCharAt(nameBuilder.length() - 1);
            return nameBuilder.toString();
        } else {
            return "";
        }
    }
}
