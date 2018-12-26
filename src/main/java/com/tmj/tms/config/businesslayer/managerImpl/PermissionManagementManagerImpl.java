package com.tmj.tms.config.businesslayer.managerImpl;

import com.tmj.tms.config.businesslayer.manager.PermissionManagementManager;
import com.tmj.tms.config.datalayer.modal.*;
import com.tmj.tms.config.datalayer.service.*;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by shanakajay on 9/2/2016.
 */
@Service
public class PermissionManagementManagerImpl implements PermissionManagementManager {
    private final UserGroupService userGroupService;
    private final GroupService groupService;
    private final GroupAuthorityService groupAuthorityService;
    private final UserModuleService userModuleService;
    private final AuthorityService authorityService;

    @Autowired
    public PermissionManagementManagerImpl(UserGroupService userGroupService,
                                           GroupService groupService,
                                           GroupAuthorityService groupAuthorityService,
                                           UserModuleService userModuleService,
                                           AuthorityService authorityService) {
        this.userGroupService = userGroupService;
        this.groupService = groupService;
        this.groupAuthorityService = groupAuthorityService;
        this.userModuleService = userModuleService;
        this.authorityService = authorityService;
    }

    public List<Group> getAssignedGroupList(Integer userSeq, Integer companyProfileSeq, Integer moduleSeq) {
        List<Group> assignedGroupList = new ArrayList<Group>();
        try {
            List<Group> groupList = this.groupService.getGroupListByUserSeqModuleSeqAndCompanyProfileSeq(userSeq, moduleSeq, companyProfileSeq);
            for (Group group : groupList) {
                if (Objects.equals(group.getModuleSeq(), moduleSeq)) {
                    assignedGroupList.add(group);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return assignedGroupList;
    }

    public List<Authority> getAssignedAuthorityList(Integer groupSeq, Integer moduleSeq) {
        List<Authority> assignedAuthorityList = new ArrayList<Authority>();
        try {
            if (groupSeq != null && groupSeq != 0) {
                assignedAuthorityList = this.authorityService.findByGroupSeqAndModuleSeq(groupSeq, moduleSeq);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return assignedAuthorityList;
    }

    @Override
    public ResponseObject saveUserGroups(Integer userSeq, Integer companyProfileSeq, Integer moduleSeq, List<Integer> groupSeqList, String username) {
        ResponseObject responseObject = new ResponseObject("User Groups Successfully Saved", true);
        try {
            List<UserGroup> userGroupList = this.userGroupService.findAllByUserSeqCompanyProfileSeqAndModuleSeq(userSeq, companyProfileSeq, moduleSeq);
            this.userGroupService.delete(userGroupList);

            UserModule userModule = this.userModuleService.findByUserSeqCompanyProfileSeqAndModuleSeq(userSeq, companyProfileSeq, moduleSeq);
            for (Integer groupSeq : groupSeqList) {
                UserGroup userGroup = new UserGroup();
                userGroup.setUserSeq(userSeq);
                userGroup.setGroupSeq(groupSeq);
                userGroup.setUserModuleSeq(userModule.getUserModuleSeq());
                userGroup.setCreatedBy(username);
                userGroup.setCreatedDate(new Date());
                userGroup.setStatus(1);
                this.userGroupService.save(userGroup);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseObject.setMessage("Error saving user Groups");
            responseObject.setStatus(false);
        }
        return responseObject;
    }

    @Override
    public ResponseObject saveGroupAuthorities(Integer groupSeq, Integer moduleSeq, List<Integer> authoritySeqList, String username) {
        ResponseObject responseObject = new ResponseObject("Group Authorities were Successfully Saved", true);
        try {
            List<GroupAuthority> groupAuthorityList = this.groupAuthorityService.findAllByGroupSeq(groupSeq);
            this.groupAuthorityService.delete(groupAuthorityList);
            for (Integer authoritySeq : authoritySeqList) {
                GroupAuthority groupAuthority = new GroupAuthority();
                groupAuthority.setGroupSeq(groupSeq);
                groupAuthority.setAuthoritySeq(authoritySeq);
                groupAuthority.setCreatedBy(username);
                groupAuthority.setCreatedDate(new Date());
                groupAuthority.setStatus(1);
                this.groupAuthorityService.save(groupAuthority);
            }
        } catch (Exception e) {
            responseObject.setMessage("Group Authority Save Operation Failed");
            responseObject.setStatus(false);
            e.printStackTrace();
        }
        return responseObject;
    }
}
