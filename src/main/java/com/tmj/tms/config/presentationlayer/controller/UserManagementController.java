package com.tmj.tms.config.presentationlayer.controller;

import com.tmj.tms.config.businesslayer.manager.UserManagementControllerManager;
import com.tmj.tms.config.datalayer.modal.*;
import com.tmj.tms.config.datalayer.service.*;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/config/userManagement")
public class UserManagementController {

    private final UserService userService;
    private final ModuleService moduleService;
    private final CompanyProfileService companyProfileService;
    private final UserManagementControllerManager userManagementControllerManager;
    private final DepartmentService departmentService;
    private final CompanyModuleService companyModuleService;

    @Autowired
    public UserManagementController(UserService userService,
                                    ModuleService moduleService,
                                    CompanyProfileService companyProfileService,
                                    UserManagementControllerManager userManagementControllerManager,
                                    DepartmentService departmentService,
                                    CompanyModuleService companyModuleService) {
        this.userService = userService;
        this.moduleService = moduleService;
        this.companyProfileService = companyProfileService;
        this.userManagementControllerManager = userManagementControllerManager;
        this.departmentService = departmentService;
        this.companyModuleService = companyModuleService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_config@userManagement_VIEW')")
    public String getPage(Model model, HttpServletRequest request) {
        this.pageLoad(model, request);
        return "config/userManagement";
    }

    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_config@userManagement_CREATE')")
    public
    @ResponseBody
    ResponseObject createUser(@RequestPart(value = "data") User user,
                              @RequestPart(value = "userPhoto", required = false) MultipartFile multipartFile,
                              Principal principal) {
        return this.userManagementControllerManager.createUser(user, multipartFile, principal.getName());
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_config@userManagement_UPDATE')")
    public
    @ResponseBody
    ResponseObject updateUser(@Valid @RequestPart(value = "data") User user,
                              @RequestPart(value = "userPhoto", required = false) MultipartFile multipartFile,
                              Principal principal) {
        return this.userManagementControllerManager.updateUser(user, multipartFile, principal.getName());
    }

    @RequestMapping(value = "/assignModuleToUser", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_config@userManagement_CREATE')")
    public
    @ResponseBody
    ResponseObject assignModuleToUser(@RequestParam(value = "userSeq") Integer userSeq,
                                      @RequestParam(value = "companyProfileSeq") Integer companyProfileSeq,
                                      @RequestParam(value = "moduleSeq[]") List<Integer> moduleSeqList,
                                      Principal principal) {
        return this.userManagementControllerManager.assignUserModuleList(userSeq, companyProfileSeq, moduleSeqList, principal.getName());
    }

    @RequestMapping(value = "/assignDepartmentsToUser", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_config@userManagement_CREATE')")
    public
    @ResponseBody
    ResponseObject assignDepartmentsToUser(@RequestParam(value = "userSeq") Integer userSeq,
                                           @RequestParam(value = "moduleSeq") Integer moduleSeq,
                                           @RequestParam(value = "departmentSeq[]") List<Integer> departmentSeqList,
                                           @RequestParam(value = "status") Integer status,
                                           Principal principal) {
        return this.userManagementControllerManager.assignUserDepartmentList(userSeq, moduleSeq, departmentSeqList, status, principal.getName());
    }

    @RequestMapping(value = "/removeDepartmentsFromUser", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_config@userManagement_CREATE')")
    public
    @ResponseBody
    ResponseObject removeDepartmentsFromUser(@RequestParam(value = "userSeq") Integer userSeq,
                                             @RequestParam(value = "moduleSeq") Integer moduleSeq,
                                             @RequestParam(value = "departmentSeq[]") List<Integer> departmentSeqList,
                                             Principal principal) {
        return this.userManagementControllerManager.removeDepartmentsFromUser(userSeq, moduleSeq, departmentSeqList, principal.getName());
    }

    @RequestMapping(value = "/removeModuleFromUser", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_config@userManagement_UPDATE')")
    public
    @ResponseBody
    ResponseObject removeModuleFromUser(@RequestParam(value = "userSeq") Integer userSeq,
                                        @RequestParam(value = "companyProfileSeq") Integer companyProfileSeq,
                                        @RequestParam(value = "moduleSeq[]") List<Integer> moduleSeqList,
                                        Principal principal) {
        return this.userManagementControllerManager.removeUserModuleList(userSeq, companyProfileSeq, moduleSeqList, principal.getName());
    }

    @RequestMapping(value = "/getModuleListByUserSeqAndCompanySeq/{userSeq}/{companySeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_config@userManagement_VIEW')")
    public
    @ResponseBody
    List<Module> getModuleListByUserSeqAndCompanySeq(@PathVariable("userSeq") Integer userSeq,
                                                     @PathVariable("companySeq") Integer companySeq) {
        return this.moduleService.getModuleListByCompanySeqAndUserSeq(companySeq, userSeq);
    }

    @RequestMapping(value = "/getDepartmentListByUserSeqAndModuleSeq/{userSeq}/{moduleSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_config@userManagement_VIEW')")
    public
    @ResponseBody
    List<Department> getDepartmentListByUserSeqAndModuleSeq(@PathVariable("userSeq") Integer userSeq,
                                                            @PathVariable("moduleSeq") Integer moduleSeq,
                                                            HttpServletRequest request) {
        Integer companyProfileSeq = Integer.parseInt(request.getSession().getAttribute("companyProfileSeq").toString());
        return this.departmentService.findByCompanyProfileSeqAndModuleSeqAndStatusByUserSeq(companyProfileSeq, moduleSeq, MasterDataStatus.APPROVED.getStatusSeq(), userSeq, MasterDataStatus.OPEN.getStatusSeq());
    }

    @RequestMapping(value = "/getModuleListByCompanySeq/{companySeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_config@userManagement_VIEW')")
    public
    @ResponseBody
    List<Module> getModuleListByCompanySeq(@PathVariable("companySeq") Integer companySeq) {
        return this.moduleService.getModuleListByCompanySeq(companySeq);
    }

    @RequestMapping(value = "/getDepartmentListByModuleSeq/{moduleSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_config@userManagement_VIEW')")
    public
    @ResponseBody
    List<Department> getDepartmentListByModuleSeq(@PathVariable("moduleSeq") Integer moduleSeq,
                                                  HttpServletRequest request) {
        return this.departmentService.findByCompanyProfileSeqAndModuleSeqAndStatus(Integer.parseInt(request.getSession().getAttribute("companyProfileSeq").toString()),
                moduleSeq, MasterDataStatus.APPROVED.getStatusSeq());
    }

    @RequestMapping(value = "/getCompanyListByUserSeq/{userSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_config@userManagement_VIEW')")
    public
    @ResponseBody
    List<CompanyProfile> getCompanyListByUserSeq(@PathVariable("userSeq") Integer userSeq) {
        return this.companyProfileService.getActiveCompanyListByUserSeq(userSeq);
    }

    @RequestMapping(value = "/getModuleListByUserSeq/{userSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_config@userManagement_VIEW')")
    public
    @ResponseBody
    List<Module> getModuleListByUserSeq(@PathVariable("userSeq") Integer userSeq,
                                        HttpServletRequest request) {
        return this.moduleService.getActiveModuleListByUserSeqAndCompanyProfileSeq(userSeq,
                Integer.parseInt(request.getSession().getAttribute("companyProfileSeq").toString()));
    }

    @RequestMapping(value = "/findUserByUserSeq/{userSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_config@userManagement_VIEW')")
    public
    @ResponseBody
    User findUserByUserSeq(@PathVariable("userSeq") Integer userId) {
        return this.userService.findOne(userId);
    }

    private Model pageLoad(Model model, HttpServletRequest request) {
        Integer companyProfileSeq = Integer.parseInt(request.getSession().getAttribute("companyProfileSeq").toString());
        model.addAttribute("userList", this.userService.findAll());
        model.addAttribute("companyProfileList", this.companyProfileService.findAll());
        List<CompanyModule> companyModuleList = this.companyModuleService.findByCompanyProfileSeq(companyProfileSeq);
        model.addAttribute("moduleList", companyModuleList.stream().map(CompanyModule::getModule).collect(Collectors.toList()));
        model.addAttribute("statusList", MasterDataStatus.getStatusListForTransactions("ROLE_config@userManagement_APPROVE"));
        return model;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}
