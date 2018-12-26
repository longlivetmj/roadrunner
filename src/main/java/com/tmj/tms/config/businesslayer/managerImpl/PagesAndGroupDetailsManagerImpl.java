package com.tmj.tms.config.businesslayer.managerImpl;

import com.tmj.tms.config.businesslayer.manager.PagesAndGroupDetailsManager;
import com.tmj.tms.config.datalayer.modal.*;
import com.tmj.tms.config.datalayer.service.*;
import com.tmj.tms.utility.CheckSpecialCharacters;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PagesAndGroupDetailsManagerImpl implements PagesAndGroupDetailsManager {

    private final ModuleService moduleService;
    private final SubModuleService subModuleService;
    private final ActionGroupService actionGroupService;
    private final GroupService groupService;
    private final DocumentLinkService documentLinkService;

    @Autowired
    public PagesAndGroupDetailsManagerImpl(ModuleService moduleService,
                                           SubModuleService subModuleService,
                                           ActionGroupService actionGroupService,
                                           GroupService groupService,
                                           DocumentLinkService documentLinkService) {
        this.moduleService = moduleService;
        this.subModuleService = subModuleService;
        this.actionGroupService = actionGroupService;
        this.groupService = groupService;
        this.documentLinkService = documentLinkService;
    }

    @Override
    public ResponseObject saveModule(Module module, Principal principal) {
        ResponseObject responseObject = new ResponseObject("Module Save Successfully", true);
        try {
            module.setCreatedBy(principal.getName());
            module.setCreatedDate(new Date());
            module.setStatus(1);
            module.setModifiedBy(principal.getName());
            module.setModifiedDate(new Date());
            module = this.moduleService.save(module);
            responseObject.setObject(module);
        } catch (Exception e) {
            e.printStackTrace();
            responseObject.setMessage(e.getMessage());
            responseObject.setStatus(false);
        }
        return responseObject;
    }

    @Override
    public ResponseObject saveSubModule(SubModule subModule, Principal principal) {
        ResponseObject responseObject = new ResponseObject("Sub Module Save Successfully", true);
        try {
            subModule.setCreatedBy(principal.getName());
            subModule.setCreatedDate(new Date());
            subModule.setStatus(1);
            subModule.setModifiedBy(principal.getName());
            subModule.setModifiedDate(new Date());
            subModule = this.subModuleService.save(subModule);
            responseObject.setObject(subModule);
        } catch (Exception e) {
            e.printStackTrace();
            responseObject.setMessage("Error Saving Sub Module");
            responseObject.setStatus(false);
        }
        return responseObject;
    }

    @Override
    public ResponseObject saveActionGroup(ActionGroup actionGroup, Principal principal) {
        ResponseObject responseObject = new ResponseObject("Action Group Save Successfully", true);
        try {
            actionGroup.setCreatedBy(principal.getName());
            actionGroup.setCreatedDate(new Date());
            actionGroup.setStatus(1);
            actionGroup.setModifiedBy(principal.getName());
            actionGroup.setModifiedDate(new Date());
            actionGroup = this.actionGroupService.save(actionGroup);
            responseObject.setObject(actionGroup);
        } catch (Exception e) {
            e.printStackTrace();
            responseObject.setMessage("Error Saving Action Group");
            responseObject.setStatus(false);
        }
        return responseObject;
    }

    @Override
    public ResponseObject saveGroup(Group group, Principal principal) {
        ResponseObject responseObject = new ResponseObject("Group Save Successfully", true);
        try {
            group.setCreatedBy(principal.getName());
            group.setCreatedDate(new Date());
            group.setStatus(1);
            group.setModifiedBy(principal.getName());
            group.setModifiedDate(new Date());
            group = this.groupService.save(group);
            responseObject.setObject(group);
        } catch (Exception e) {
            e.printStackTrace();
            responseObject.setMessage("Error Saving Group");
            responseObject.setStatus(false);
        }
        return responseObject;
    }

    @Override
    public ResponseObject saveDocumentLink(DocumentLink documentLink, Integer create, Integer view, Integer update, Integer remove, Integer approve, Integer viewDelete, Principal principal) {
        ResponseObject responseObject = new ResponseObject("Document Link Save Successfully", true);
        try {
            CheckSpecialCharacters checkSpecialCharacters = new CheckSpecialCharacters();
            Module moduleDetails = this.moduleService.findOne(documentLink.getModuleSeq());
            if (!checkSpecialCharacters.isSpecialCharacterInclude(documentLink.getLinkName())) {
                List<Authority> authorities = new ArrayList<>();
                if (create != null && create == 1) {
                    Authority authority = new Authority();
                    authority.setAuthority("ROLE_" + moduleDetails.getUrlPattern() + "@" + documentLink.getLinkName() + "_CREATE");
                    authority.setCreatedBy(principal.getName());
                    authority.setCreatedDate(new Date());
                    authorities.add(authority);
                }
                if (view != null && view == 1) {
                    Authority authority = new Authority();
                    authority.setAuthority("ROLE_" + moduleDetails.getUrlPattern() + "@" + documentLink.getLinkName() + "_VIEW");
                    authority.setCreatedBy(principal.getName());
                    authority.setCreatedDate(new Date());
                    authorities.add(authority);
                }
                if (update != null && update == 1) {
                    Authority authority = new Authority();
                    authority.setAuthority("ROLE_" + moduleDetails.getUrlPattern() + "@" + documentLink.getLinkName() + "_UPDATE");
                    authority.setCreatedBy(principal.getName());
                    authority.setCreatedDate(new Date());
                    authorities.add(authority);
                }
                if (remove != null && remove == 1) {
                    Authority authority = new Authority();
                    authority.setAuthority("ROLE_" + moduleDetails.getUrlPattern() + "@" + documentLink.getLinkName() + "_DELETE");
                    authority.setCreatedBy(principal.getName());
                    authority.setCreatedDate(new Date());
                    authorities.add(authority);
                }
                if (approve != null && approve == 1) {
                    Authority authority = new Authority();
                    authority.setAuthority("ROLE_" + moduleDetails.getUrlPattern() + "@" + documentLink.getLinkName() + "_APPROVE");
                    authority.setCreatedBy(principal.getName());
                    authority.setCreatedDate(new Date());
                    authorities.add(authority);
                }
                if (viewDelete != null && viewDelete == 1) {
                    Authority authority = new Authority();
                    authority.setAuthority("ROLE_" + moduleDetails.getUrlPattern() + "@" + documentLink.getLinkName() + "_VIEW-DELETE");
                    authority.setCreatedBy(principal.getName());
                    authority.setCreatedDate(new Date());
                    authorities.add(authority);
                }

                documentLink.setCreatedBy(principal.getName());
                documentLink.setCreatedDate(new Date());
                documentLink.setStatus(1);
                documentLink.setModifiedBy(principal.getName());
                documentLink.setModifiedDate(new Date());
                documentLink.setAuthorities(authorities);
                documentLink = this.documentLinkService.save(documentLink);
                responseObject.setObject(documentLink);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseObject.setMessage("Error Saving Document Link");
            responseObject.setStatus(false);
        }
        return responseObject;
    }

    @Override
    public ResponseObject updateModule(Module module, Principal principal) {
        ResponseObject responseObject = new ResponseObject("Module Update Successfully", true);
        try {
            Module moduleDetails = this.moduleService.findOne(module.getModuleSeq());
            if (moduleDetails != null) {
                moduleDetails.setModuleName(module.getModuleName());
                moduleDetails.setDescription(module.getDescription());
                moduleDetails.setModifiedBy(principal.getName());
                moduleDetails.setModifiedDate(new Date());
                moduleDetails.setUrlPattern(module.getUrlPattern());
                if (module.getStatus() != null) {
                    moduleDetails.setStatus(module.getStatus());
                } else {
                    moduleDetails.setStatus(1);
                }
                moduleDetails = this.moduleService.save(moduleDetails);
                responseObject.setObject(moduleDetails);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseObject.setMessage("Error Update Module");
            responseObject.setStatus(false);
        }
        return responseObject;
    }

    @Override
    public ResponseObject updateSubModule(SubModule subModule, Principal principal) {
        ResponseObject responseObject = new ResponseObject("Sub Module Update Successfully", true);
        try {
            SubModule subModuleDetails = this.subModuleService.findOne(subModule.getSubModuleSeq());
            if (subModuleDetails != null) {
                subModuleDetails.setSubModuleName(subModule.getSubModuleName());
                subModuleDetails.setDescription(subModule.getDescription());
                subModuleDetails.setModuleSeq(subModule.getModuleSeq());
                subModuleDetails.setModifiedBy(principal.getName());
                subModuleDetails.setModifiedDate(new Date());

                if (subModule.getStatus() != null) {
                    subModuleDetails.setStatus(subModule.getStatus());
                } else {
                    subModuleDetails.setStatus(1);
                }
                subModuleDetails = this.subModuleService.save(subModuleDetails);
                responseObject.setObject(subModuleDetails);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseObject.setMessage("Error Update Sub Module");
            responseObject.setStatus(false);
        }
        return responseObject;
    }

    @Override
    public ResponseObject updateActionGroup(ActionGroup actionGroup, Principal principal) {
        ResponseObject responseObject = new ResponseObject("Action Group Update Successfully", true);

        try {
            ActionGroup actionGroupDetails = this.actionGroupService.findOne(actionGroup.getActionGroupSeq());

            if (actionGroupDetails != null) {
                actionGroupDetails.setActionGroupName(actionGroup.getActionGroupName());
                actionGroupDetails.setDescription(actionGroup.getDescription());
                actionGroupDetails.setSubModuleSeq(actionGroup.getSubModuleSeq());
                actionGroupDetails.setModifiedBy(principal.getName());
                actionGroupDetails.setModifiedDate(new Date());

                if (actionGroup.getStatus() != null) {
                    actionGroupDetails.setStatus(actionGroup.getStatus());
                } else {
                    actionGroupDetails.setStatus(1);
                }
                actionGroupDetails = this.actionGroupService.save(actionGroupDetails);
                responseObject.setObject(actionGroupDetails);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseObject.setMessage("Error Update Action Group");
            responseObject.setStatus(false);
        }
        return responseObject;
    }

    @Override
    public ResponseObject updateGroup(Group group, Principal principal) {
        ResponseObject responseObject = new ResponseObject("Group Update Successfully", true);

        try {
            Group groupDetails = this.groupService.findOne(group.getGroupSeq());
            if (groupDetails != null) {
                groupDetails.setGroupName(group.getGroupName());
                groupDetails.setDescription(group.getDescription());
                groupDetails.setModuleSeq(group.getModuleSeq());
                groupDetails.setModifiedBy(principal.getName());
                groupDetails.setModifiedDate(new Date());

                if (group.getStatus() != null) {
                    groupDetails.setStatus(group.getStatus());
                } else {
                    groupDetails.setStatus(1);
                }
                groupDetails = this.groupService.save(groupDetails);
                responseObject.setObject(groupDetails);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseObject.setMessage("Error Update Group");
            responseObject.setStatus(false);
        }
        return responseObject;
    }

    @Override
    public ResponseObject saveCustomRole(Integer documentLinkSeq, String authority, Principal principal) {
        ResponseObject responseObject = new ResponseObject("Custom Role Save Successfully", true);
        try {
            DocumentLink documentLink = this.documentLinkService.findOne(documentLinkSeq);
            Module module = this.moduleService.findOne(documentLink.getModuleSeq());

            Authority authorityRole = new Authority();
            authorityRole.setCreatedBy(principal.getName());
            authorityRole.setDocumentLinkSeq(documentLink.getDocumentLinkSeq());
            authorityRole.setCreatedDate(new Date());
            authorityRole.setAuthority("ROLE_" + module.getUrlPattern() + "@" + documentLink.getLinkName() + "_" + authority);
            List<Authority> authorityList = documentLink.getAuthorities();
            authorityList.add(authorityRole);
            documentLink.setAuthorities(authorityList);
            documentLink = this.documentLinkService.save(documentLink);
            responseObject.setObject(documentLink);

        } catch (Exception e) {
            e.printStackTrace();
            responseObject.setMessage("Error Saving Custom Role");
            responseObject.setStatus(false);
        }
        return responseObject;
    }
}
