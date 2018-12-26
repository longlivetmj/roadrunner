package com.tmj;

import com.tmj.tms.config.datalayer.modal.CompanyProfile;
import com.tmj.tms.config.datalayer.modal.Module;
import com.tmj.tms.config.datalayer.modal.UserLogInAudit;
import com.tmj.tms.config.datalayer.service.CompanyProfileService;
import com.tmj.tms.config.datalayer.service.DepartmentService;
import com.tmj.tms.config.datalayer.service.ModuleService;
import com.tmj.tms.config.datalayer.service.UserLogInAuditService;
import com.tmj.tms.config.utility.MasterDataStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class ApplicationAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final CompanyProfileService companyProfileService;
    private final UserLogInAuditService userLogInAuditService;
    private final ModuleService moduleService;
    private final DepartmentService departmentService;

    @Autowired
    public ApplicationAuthenticationSuccessHandler(CompanyProfileService companyProfileService,
                                        UserLogInAuditService userLogInAuditService,
                                        ModuleService moduleService,
                                        DepartmentService departmentService) {
        this.companyProfileService = companyProfileService;
        this.userLogInAuditService = userLogInAuditService;
        this.moduleService = moduleService;
        this.departmentService = departmentService;
    }

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        try {
            UserLogInAudit userLogInAudit = new UserLogInAudit();
            userLogInAudit.setUsername(authentication.getName());
            userLogInAudit.setLoginDate(new Date());
            userLogInAudit.setRemoteAddress(request.getRemoteAddr());
            this.userLogInAuditService.save(userLogInAudit);
            Integer SESSION_TIMEOUT_IN_SECONDS = 60 * 30 * 10;
            request.getSession().setMaxInactiveInterval(SESSION_TIMEOUT_IN_SECONDS);
            List<CompanyProfile> companyProfileList = this.companyProfileService.getActiveCompanyListByUsername(authentication.getName());
            if (companyProfileList.size() == 1) {
                request.getSession().setAttribute("companyProfileSeq", companyProfileList.get(0).getCompanyProfileSeq());
                List<Module> moduleList = this.moduleService.getModuleListByCompanySeqAndUsername(companyProfileList.get(0).getCompanyProfileSeq(), authentication.getName());
                request.getSession().setAttribute("userModuleList", moduleList);
                for (Module module : moduleList) {
                    request.getSession().setAttribute("userDepartmentList" + module.getModuleSeq(), this.departmentService.findByCompanyProfileSeqAndModuleSeqAndStatusByUsername(Integer.parseInt(request.getSession().getAttribute("companyProfileSeq").toString()),
                            module.getModuleSeq(), MasterDataStatus.APPROVED.getStatusSeq(), authentication.getName(), MasterDataStatus.DELETED.getStatusSeq()));
                }
                response.sendRedirect("/#/home/dashboardManagement");
            } else if (companyProfileList.size() > 1) {
                response.sendRedirect("/companySelection");
            } else {
                response.sendRedirect("/login?No Company");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
