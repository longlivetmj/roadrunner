package com.tmj;

import com.tmj.tms.config.datalayer.modal.UserLogInAudit;
import com.tmj.tms.config.datalayer.service.UserLogInAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Service
public class ApplicationAuthenticationLogoutHandler implements LogoutSuccessHandler {

    private final UserLogInAuditService userLogInAuditService;

    @Autowired
    public ApplicationAuthenticationLogoutHandler(UserLogInAuditService userLogInAuditService) {
        this.userLogInAuditService = userLogInAuditService;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        try {
            UserLogInAudit userLogInAudit = this.userLogInAuditService.lastLogInAudit(authentication.getName());
            userLogInAudit.setLogoutDate(new Date());
            this.userLogInAuditService.save(userLogInAudit);
        } catch (NullPointerException e) {
            System.out.println("authentication is null");
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect("/login");
    }
}
