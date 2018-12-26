package com.tmj;

import com.tmj.tms.config.datalayer.modal.UserLogInAudit;
import com.tmj.tms.config.datalayer.service.UserLogInAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;


@Service
public class SessionTimeoutHandler implements InvalidSessionStrategy {

    private static final String FACES_REQUEST_HEADER = "faces-request";
    private final UserLogInAuditService userLogInAuditService;

    private String invalidSessionUrl = "/login";

    @Autowired
    public SessionTimeoutHandler(UserLogInAuditService userLogInAuditService) {
        this.userLogInAuditService = userLogInAuditService;
    }

    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            boolean ajaxRedirect = "partial/ajax".equals(request.getHeader(FACES_REQUEST_HEADER));
            if (ajaxRedirect) {
                String contextPath = request.getContextPath();
                String redirectUrl = contextPath + invalidSessionUrl;
                try {
                    UserLogInAudit userLogInAudit = this.userLogInAuditService.lastLogInAudit(request.getRemoteUser());
                    userLogInAudit.setLogoutDate(new Date());
                    this.userLogInAuditService.save(userLogInAudit);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String ajaxRedirectXml = createAjaxRedirectXml(redirectUrl);
                response.setContentType("text/xml");
                response.getWriter().write(ajaxRedirectXml);
            } else {
                String requestURI = getRequestUrl(request);
                request.getSession(true);
                response.sendRedirect(requestURI);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getRequestUrl(HttpServletRequest request) {
        StringBuffer requestURL = request.getRequestURL();
        String queryString = request.getQueryString();
        if (StringUtils.hasText(queryString)) {
            requestURL.append("?").append(queryString);
        }
        return requestURL.toString();
    }

    private String createAjaxRedirectXml(String redirectUrl) {
        return new StringBuilder()
                .append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
                .append("<partial-response><redirect url=\"")
                .append(redirectUrl)
                .append("\"></redirect></partial-response>")
                .toString();
    }

}
