package com.tmj.tms.utility;

import com.tmj.tms.config.datalayer.modal.SystemProperty;
import com.tmj.tms.config.datalayer.modal.User;
import com.tmj.tms.config.datalayer.service.SystemPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendUserCreationEmail {

    private final SystemPropertyService systemPropertyService;
    private final SendEmailAndSMS sendEmailAndSMS;

    @Autowired
    public SendUserCreationEmail(SystemPropertyService systemPropertyService,
                                 SendEmailAndSMS sendEmailAndSMS) {
        this.systemPropertyService = systemPropertyService;
        this.sendEmailAndSMS = sendEmailAndSMS;
    }

    public void sendEmailForSingleUser(User user) {
        SystemProperty domainName = this.systemPropertyService.findByPropertyName("domain_name");
        String[] bccNames = {"thilangaj@itx360.com", "samanthal@itx360.com", "dinaliw@itx360.com"};
        String[] ccNames = null;
        try {
            String emailBody = this.createEmailBody(user, domainName);
            sendEmailAndSMS.sendEmails(
                    bccNames,
                    ccNames,
                    new String[]{user.getEmail()},
                    "System Permission For Road Runner",
                    emailBody);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(user.getUsername());
    }

    private String createEmailBody(User user, SystemProperty domainName) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<html>\n" +
                "<head>\n" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                "</head>\n" +
                "<body>\n" +
                "<p><strong>Welcome to New Road Runner </strong></p>\n" +
                "<table border=\"0\" cellpadding=\"5\" cellspacing=\"5\" >\n" +
                "  <tbody>\n" +
                "    <tr>\n" +
                "      <td align=\"left\" colspan=\"2\">Please find the following credentials for system access.</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td align=\"left\">URL: </td>\n" +
                "      <td align=\"left\"><a href='" + domainName.getPropertyValue() + "'>" + domainName.getPropertyValue() + "</a></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td align=\"left\">Username: </td>\n" +
                "      <td align=\"left\">" + user.getUsername() + "</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td align=\"left\">Password: </td>\n" +
                "      <td align=\"left\">changeme</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td align=\"left\" colspan=\"2\">Please change the above mentioned default password by accessing User Profile</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td align=\"left\" colspan=\"2\">For further clarifications and system assistance feel free to contact <a href='support@Road Runner.com'>support@Road Runner.com</a></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td align=\"left\" colspan=\"2\"><b>Note: Use Chrome or Firefox web browser for better performance.</b></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td align=\"left\" colspan=\"2\">Thanks</td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td align=\"left\" colspan=\"2\">CD Team | ITX360</td>\n" +
                "    </tr>\n" +
                "  </tbody>\n" +
                "</table>\n" +
                "</body>\n" +
                "</html>\n");
        return stringBuilder.toString();
    }

}
