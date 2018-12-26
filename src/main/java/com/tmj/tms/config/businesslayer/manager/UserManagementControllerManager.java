package com.tmj.tms.config.businesslayer.manager;

import com.tmj.tms.config.datalayer.modal.User;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserManagementControllerManager {

    ResponseObject createUser(User user, MultipartFile multipartFile, String username);

    ResponseObject updateUser(User user, MultipartFile multipartFile, String username);

    ResponseObject assignUserModuleList(Integer userSeq, Integer companyProfileSeq, List<Integer> moduleSeqList, String username);

    ResponseObject assignUserDepartmentList(Integer userSeq, Integer moduleSeq, List<Integer> departmentSeqList, Integer status, String username);

    ResponseObject removeUserModuleList(Integer userSeq, Integer companyProfileSeq, List<Integer> moduleSeqList, String username);

    ResponseObject removeDepartmentsFromUser(Integer userSeq, Integer moduleSeq, List<Integer> departmentSeqList, String name);

    String SHA256(String username);

    String SHA1(String username);
}
