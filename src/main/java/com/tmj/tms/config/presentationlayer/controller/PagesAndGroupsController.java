package com.tmj.tms.config.presentationlayer.controller;

import com.tmj.tms.config.businesslayer.manager.PagesAndGroupDetailsManager;
import com.tmj.tms.config.datalayer.modal.*;
import com.tmj.tms.config.datalayer.service.*;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/config/pagesAndGroups")
public class PagesAndGroupsController {

    private final ModuleService moduleService;
    private final PagesAndGroupDetailsManager pagesAndGroupDetailsManager;
    private final SubModuleService subModuleService;
    private final ActionGroupService actionGroupService;
    private final GroupService groupService;
    private final DocumentLinkService documentLinkService;

    @Autowired
    public PagesAndGroupsController(ModuleService moduleService,
                                    PagesAndGroupDetailsManager pagesAndGroupDetailsManager,
                                    SubModuleService subModuleService,
                                    ActionGroupService actionGroupService,
                                    GroupService groupService,
                                    DocumentLinkService documentLinkService) {
        this.moduleService = moduleService;
        this.pagesAndGroupDetailsManager = pagesAndGroupDetailsManager;
        this.subModuleService = subModuleService;
        this.actionGroupService = actionGroupService;
        this.groupService = groupService;
        this.documentLinkService = documentLinkService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_config@pagesAndGroups_VIEW')")
    public String getPage(Model model, Principal principal) {
        this.pageLoad(model, principal);
        return "config/pagesAndGroups";
    }

    @RequestMapping(value = "/addModule", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_config@pagesAndGroups_CREATE')")
    public
    @ResponseBody
    ResponseObject addModule(@Valid @ModelAttribute Module module, Principal principal) {
        return this.pagesAndGroupDetailsManager.saveModule(module, principal);
    }

    @RequestMapping(value = "/addSubModule", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_config@pagesAndGroups_CREATE')")
    public
    @ResponseBody
    ResponseObject addSubModule(@Valid @ModelAttribute SubModule subModule, Principal principal) {
        return this.pagesAndGroupDetailsManager.saveSubModule(subModule, principal);
    }

    @RequestMapping(value = "/addGroup", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_config@pagesAndGroups_CREATE')")
    public
    @ResponseBody
    ResponseObject addGroup(@Valid @ModelAttribute Group group, Principal principal) {
        return this.pagesAndGroupDetailsManager.saveGroup(group, principal);
    }

    @RequestMapping(value = "/addActionGroup", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_config@pagesAndGroups_CREATE')")
    public
    @ResponseBody
    ResponseObject addActionGroup(@Valid @ModelAttribute ActionGroup actionGroup, Principal principal) {
        return this.pagesAndGroupDetailsManager.saveActionGroup(actionGroup, principal);
    }

    @PreAuthorize("hasRole('ROLE_config@pagesAndGroups_CREATE')")
    @RequestMapping(value = "/addDocumentLink", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseObject addDocumentLink(@Valid @ModelAttribute DocumentLink documentLink,
                                   @RequestParam(value = "create", required = false) Integer create,
                                   @RequestParam(value = "read", required = false) Integer view,
                                   @RequestParam(value = "update", required = false) Integer update,
                                   @RequestParam(value = "remove", required = false) Integer remove,
                                   @RequestParam(value = "approve", required = false) Integer approve,
                                   @RequestParam(value = "viewDelete", required = false) Integer viewDelete,
                                   Principal principal) {
        return this.pagesAndGroupDetailsManager.saveDocumentLink(documentLink, create, view, update, remove, approve, viewDelete, principal);
    }


    @RequestMapping(value = "/addCustomRole", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_config@pagesAndGroups_CREATE')")
    public
    @ResponseBody
    ResponseObject addCustomRole(@RequestParam(value = "documentLinkSeq") Integer documentLinkSeq,
                                 @RequestParam(value = "authority") String authority,
                                 Principal principal) {
        return this.pagesAndGroupDetailsManager.saveCustomRole(documentLinkSeq, authority, principal);
    }

    @RequestMapping(value = "/findModuleByModuleSeq/{moduleSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_config@pagesAndGroups_VIEW')")
    public
    @ResponseBody
    Module findModuleByModuleSeq(@PathVariable("moduleSeq") Integer moduelId) {
        return this.moduleService.findOne(moduelId);
    }


    @RequestMapping(value = "/findSubModuleBySubModuleSeq/{subModuleSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_config@pagesAndGroups_VIEW')")
    public
    @ResponseBody
    SubModule findSubModuleBySubModuleSeq(@PathVariable("subModuleSeq") Integer subModuelId) {
        return this.subModuleService.findOne(subModuelId);
    }

    @RequestMapping(value = "/findActionGroupByActionGroupSeq/{actionGroupSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_config@pagesAndGroups_VIEW')")
    public
    @ResponseBody
    ActionGroup findActionGroupByActionGroupSeq(@PathVariable("actionGroupSeq") Integer actionGroupId) {
        return this.actionGroupService.findOne(actionGroupId);
    }

    @RequestMapping(value = "/findGroupByGroupSeq/{groupSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_config@pagesAndGroups_VIEW')")
    public
    @ResponseBody
    Group findGroupByGroupSeq(@PathVariable("groupSeq") Integer groupId) {
        return this.groupService.findOne(groupId);
    }

    @RequestMapping(value = "/updateModule", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_config@pagesAndGroups_UPDATE')")
    public
    @ResponseBody
    ResponseObject updateModule(@Valid @ModelAttribute Module module, Principal principal) {
        return this.pagesAndGroupDetailsManager.updateModule(module, principal);
    }

    @RequestMapping(value = "/updateSubModule", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_config@pagesAndGroups_UPDATE')")
    public
    @ResponseBody
    ResponseObject updateSubModule(@Valid @ModelAttribute SubModule subModule, Principal principal) {
        return this.pagesAndGroupDetailsManager.updateSubModule(subModule, principal);
    }

    @RequestMapping(value = "/updateActionGroup", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_config@pagesAndGroups_UPDATE')")
    public
    @ResponseBody
    ResponseObject updateActionGroup(@Valid @ModelAttribute ActionGroup actionGroup, Principal principal) {
        return this.pagesAndGroupDetailsManager.updateActionGroup(actionGroup, principal);
    }

    @RequestMapping(value = "/updateGroup", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_config@pagesAndGroups_UPDATE')")
    public
    @ResponseBody
    ResponseObject updateGroup(@Valid @ModelAttribute Group group, Principal principal) {
        return this.pagesAndGroupDetailsManager.updateGroup(group, principal);
    }

    @RequestMapping(value = "/getSubModuleListByModuleSeq/{moduleSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_config@pagesAndGroups_VIEW')")
    public
    @ResponseBody
    List<SubModule> getGroupListByModuleSeq(@PathVariable("moduleSeq") Integer moduleSeq) {
        return this.subModuleService.findByModuleSeq(moduleSeq);
    }

    @RequestMapping(value = "/getActionGroupListBySubModuleSeq/{subModuleSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_config@pagesAndGroups_VIEW')")
    public
    @ResponseBody
    List<ActionGroup> getActionGroupListBySubModuleSeq(@PathVariable("subModuleSeq") Integer subModuleSeq) {
        return this.actionGroupService.findBySubModuleSeq(subModuleSeq);
    }

    public Model pageLoad(Model model, Principal principal) {
        model.addAttribute("username", principal.getName());
        model.addAttribute("moduleList", this.moduleService.findAll());
        model.addAttribute("groupList", this.groupService.findAll());
        model.addAttribute("documentLinkList", this.documentLinkService.findAll());
        return model;
    }
}
