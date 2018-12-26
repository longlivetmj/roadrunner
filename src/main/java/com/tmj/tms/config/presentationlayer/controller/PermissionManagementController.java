package com.tmj.tms.config.presentationlayer.controller;

import com.tmj.tms.config.businesslayer.manager.PermissionManagementManager;
import com.tmj.tms.config.datalayer.modal.*;
import com.tmj.tms.config.datalayer.service.*;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping(value = "/config/permissionManagement")
public class PermissionManagementController {
    private final UserService userService;
    private final ModuleService moduleService;
    private final PermissionManagementManager permissionManagementManager;
    private final GroupService groupService;
    private final DocumentLinkService documentLinkService;
    private final CompanyProfileService companyProfileService;

    @Autowired
    public PermissionManagementController(UserService userService,
                                          ModuleService moduleService,
                                          PermissionManagementManager permissionManagementManager,
                                          GroupService groupService,
                                          DocumentLinkService documentLinkService,
                                          CompanyProfileService companyProfileService) {
        this.userService = userService;
        this.moduleService = moduleService;
        this.permissionManagementManager = permissionManagementManager;
        this.groupService = groupService;
        this.documentLinkService = documentLinkService;
        this.companyProfileService = companyProfileService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_config@permissionManagement_VIEW')")
    public String getPage(Model model, Principal principal) {
        this.pageLoad(model, principal);
        return "config/permissionManagement";
    }

    @RequestMapping(value = "/loadModuleWiseGroupList", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_config@permissionManagement_VIEW')")
    public String loadModuleWiseGroupList(@RequestParam(value = "moduleSeq") Integer moduleSeq,
                                          Model model) {
        model.addAttribute("moduleWiseGroupList", this.groupService.findByModuleSeq(moduleSeq));
        return "config/content/moduleWiseGroupData";
    }

    @RequestMapping(value = "/loadAssignedGroupList", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_config@permissionManagement_VIEW')")
    public
    @ResponseBody
    List<Group> loadAssignedGroupList(@RequestParam(value = "userSeq") Integer userSeq,
                                      @RequestParam(value = "companyProfileSeq") Integer companyProfileSeq,
                                      @RequestParam(value = "moduleSeq") Integer moduleSeq) {
        return this.permissionManagementManager.getAssignedGroupList(userSeq, companyProfileSeq, moduleSeq);
    }

    @RequestMapping(value = "/submitUserGroups", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_config@permissionManagement_CREATE')")
    public
    @ResponseBody
    ResponseObject submitUserGroups(@RequestParam(value = "userSeq") Integer userSeq,
                                    @RequestParam(value = "companyProfileSeq") Integer companyProfileSeq,
                                    @RequestParam(value = "moduleSeq") Integer moduleSeq,
                                    @RequestParam(value = "groupSeq[]") List<Integer> groupSeqList,
                                    Principal principal) {
        return this.permissionManagementManager.saveUserGroups(userSeq, companyProfileSeq, moduleSeq, groupSeqList, principal.getName());
    }

    @RequestMapping(value = "/submitGroupAuthorities", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_config@permissionManagement_CREATE')")
    public
    @ResponseBody
    ResponseObject submitGroupAuthorities(@RequestParam(value = "groupSeq") Integer groupSeq,
                                          @RequestParam(value = "moduleSeq") Integer moduleSeq,
                                          @RequestParam(value = "authoritySeq[]") List<Integer> authoritySeqList,
                                          Principal principal) {
        return this.permissionManagementManager.saveGroupAuthorities(groupSeq, moduleSeq, authoritySeqList, principal.getName());
    }

    @RequestMapping(value = "/loadModuleWiseDocumentLinkList", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_config@permissionManagement_VIEW')")
    public String loadModuleWiseDocumentLinkList(@RequestParam(value = "moduleSeq") Integer moduleSeq,
                                                 Model model) {
        List<DocumentLink> documentLinks = this.documentLinkService.findByModuleSeq(moduleSeq);
        model.addAttribute("moduleWiseDocumentLinkList", documentLinks);
        return "config/content/groupWiseDocumentLinkData";
    }

    @RequestMapping(value = "/loadAssignedAuthorityList", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_config@permissionManagement_VIEW')")
    public
    @ResponseBody
    List<Authority> loadAssignedAuthorityList(@RequestParam(value = "groupSeq") Integer groupSeq,
                                              @RequestParam(value = "moduleSeq") Integer moduleSeq) {
        return this.permissionManagementManager.getAssignedAuthorityList(groupSeq, moduleSeq);
    }

    @RequestMapping(value = "/getModuleListByUserSeqAndCompanySeq/{userSeq}/{companySeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_config@permissionManagement_VIEW')")
    public
    @ResponseBody
    List<Module> getModuleListByUserSeqAndCompanySeq(@PathVariable("userSeq") Integer userSeq,
                                                     @PathVariable("companySeq") Integer companySeq) {
        return this.moduleService.getModuleListByCompanySeqAndUserSeq(companySeq, userSeq);
    }

    @RequestMapping(value = "/getCompanyListByUserSeq/{userSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_config@permissionManagement_VIEW')")
    public
    @ResponseBody
    List<CompanyProfile> getCompanyListByUserSeq(@PathVariable("userSeq") Integer userSeq) {
        return this.companyProfileService.getActiveCompanyListByUserSeq(userSeq);
    }

    @RequestMapping(value = "/getGroupListByModuleSeq/{moduleSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_config@permissionManagement_VIEW')")
    public
    @ResponseBody
    List<Group> getGroupListByModuleSeq(@PathVariable("moduleSeq") Integer moduleSeq) {
        return this.groupService.findByModuleSeq(moduleSeq);
    }

    private void pageLoad(Model model, Principal principal) {
        model.addAttribute("username", principal.getName());
        model.addAttribute("userList", this.userService.findAll());
        model.addAttribute("moduleList", this.moduleService.findAll());
        model.addAttribute("groupList", this.groupService.findAll());
    }
}
