package com.tmj;

import com.tmj.tms.config.businesslayer.manager.UserManagementControllerManager;
import com.tmj.tms.config.datalayer.modal.ActionGroup;
import com.tmj.tms.config.datalayer.modal.DocumentLink;
import com.tmj.tms.config.datalayer.modal.SubModule;
import com.tmj.tms.config.datalayer.modal.User;
import com.tmj.tms.config.datalayer.service.ModuleService;
import com.tmj.tms.config.datalayer.service.SubModuleService;
import com.tmj.tms.config.datalayer.service.UploadedDocumentService;
import com.tmj.tms.config.datalayer.service.UserService;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.cache.annotation.CacheResult;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Comparator;
import java.util.Date;

@Controller
public class IndexController {

    private static final String PATH = "/error";
    private final ModuleService moduleService;
    private final SubModuleService subModuleService;
    private final UserService userService;
    private final UploadedDocumentService uploadedDocumentService;
    private final UserManagementControllerManager userManagementControllerManager;

    @Autowired
    public IndexController(ModuleService moduleService,
                           SubModuleService subModuleService,
                           UserService userService,
                           UploadedDocumentService uploadedDocumentService,
                           UserManagementControllerManager userManagementControllerManager) {
        this.moduleService = moduleService;
        this.subModuleService = subModuleService;
        this.userService = userService;
        this.uploadedDocumentService = uploadedDocumentService;
        this.userManagementControllerManager = userManagementControllerManager;
    }

    @PreAuthorize("hasRole('ROLE_Home@index_VIEW')")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String viewPage(Model model, HttpServletRequest request, Principal principal) {
        if (principal == null) {
            return "login";
        }
        try {
            this.pageLoad(model, principal, request);
            return "index";
        } catch (Exception e) {
            return "login";
        }
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_Home@index_VIEW')")
    public
    @ResponseBody
    ResponseObject onSubmitChangePassword(@RequestParam(value = "userSeq") Integer userSeq,
                                          @RequestParam(value = "oldPassword") String oldPassword,
                                          @RequestParam(value = "newPassword") String newPassword,
                                          Principal principal) {
        ResponseObject responseObject;
        User user = this.userService.getUserByUserSeq(userSeq);
        if (user != null) {
            if (user.getPassword().equals(this.userManagementControllerManager.SHA1(oldPassword))) {
                //Password Encryption
                String encryptedPassword = this.userManagementControllerManager.SHA1(newPassword);
                user.setPassword(encryptedPassword);
                //Password Encryption
                user.setModifiedBy(principal.getName());
                user.setModifiedDate(new Date());
                this.userService.save(user);
                responseObject = new ResponseObject("Password Changed Successfully...", true);
            } else {
                responseObject = new ResponseObject("Incorrect Current Password...", false);
            }
        } else {
            responseObject = new ResponseObject("Password Changed Successfully...", false);
        }
        return responseObject;
    }

    @CacheResult
    @RequestMapping(value = "/loadLeftNavi/{moduleName}/{subModuleName}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_Home@index_VIEW')")
    public String loadLeftNavi(@PathVariable String moduleName,
                               @PathVariable String subModuleName,
                               Model model) {
        SubModule subModule = this.subModuleService.findBySubModuleNameAndModuleName(subModuleName, moduleName);
        subModule.getActionGroupList().sort(Comparator.comparing(ActionGroup::getOrderBy));
        subModule.getActionGroupList().forEach(i -> i.getDocumentLinkList().sort(Comparator.comparing(DocumentLink::getDocumentLinkSeq)));
        model.addAttribute("module", this.moduleService.findOne(subModule.getModuleSeq()));
        model.addAttribute("subModule", subModule);
        return "/sidebar";
    }

    @CacheResult
    @RequestMapping(value = "/loadLeftNaviByURL/{moduleUrlPattern}/{documentLinkName}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_Home@index_VIEW')")
    public String loadLeftNaviByURL(@PathVariable String moduleUrlPattern,
                                    @PathVariable String documentLinkName,
                                    Model model) {
        SubModule subModule = this.subModuleService.findByDocumentLinkNameAndModuleUrlPattern(documentLinkName, moduleUrlPattern);
        if (subModule != null) {
            model.addAttribute("module", this.moduleService.findOne(subModule.getModuleSeq()));
        }
        model.addAttribute("subModule", subModule);
        return "/sidebar";
    }

    private Model pageLoad(Model model, Principal principal, HttpServletRequest request) {
        model.addAttribute("username", principal.getName());
        Integer companyProfileSeq = Integer.parseInt(request.getSession().getAttribute("companyProfileSeq").toString());
        model.addAttribute("companyProfileSeq", companyProfileSeq);
        model.addAttribute("moduleList", this.moduleService.getModuleListByCompanySeqAndUsername(companyProfileSeq, principal.getName()));
        model.addAttribute("user", this.userService.findByUsername(principal.getName()));
        return model;
    }

    @RequestMapping(value = "/renewCSRF", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_Home@index_VIEW')")
    public
    @ResponseBody
    CsrfToken renewCSRF(HttpServletRequest request) {
        CsrfToken token = (CsrfToken) request.getAttribute("HttpSessionCsrfTokenRepository.CSRF_TOKEN");
        System.out.println(token.getHeaderName());
        System.out.println(token.getToken());
        return token;
    }

    @CacheResult
    @RequestMapping(value = "/getLogo/{uploadDocumentSeq}", method = RequestMethod.GET)
    public void getLogo(@PathVariable Integer uploadDocumentSeq, HttpServletResponse httpServletResponse) throws IOException {
        this.uploadedDocumentService.loadImage(uploadDocumentSeq, httpServletResponse);
    }
}
