package com.tmj.tms.config.businesslayer.manager;

import com.tmj.tms.config.datalayer.modal.Authority;
import com.tmj.tms.config.datalayer.modal.Group;
import com.tmj.tms.utility.ResponseObject;

import java.util.List;

public interface PermissionManagementManager {

    List<Group> getAssignedGroupList(Integer userSeq, Integer companyProfileSeq, Integer moduleSeq);

    List<Authority> getAssignedAuthorityList(Integer groupSeq, Integer moduleSeq);

    ResponseObject saveUserGroups(Integer userSeq, Integer companyProfileSeq, Integer moduleSeq, List<Integer> groupSeqList, String username);

    ResponseObject saveGroupAuthorities(Integer groupSeq, Integer moduleSeq, List<Integer> authoritySeqList, String username);
}
