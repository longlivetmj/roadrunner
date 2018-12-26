package com.tmj.tms.config.businesslayer.manager;

import com.tmj.tms.config.datalayer.modal.CompanyProfile;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@Component
public interface CompanyProfileManagementControllerManager {

    ResponseObject createCompany(CompanyProfile companyProfile, MultipartFile multipartFile, Principal principal);

    ResponseObject updateCompany(CompanyProfile companyProfile, MultipartFile multipartFile, Principal principal);

    ResponseObject createCompanyModules(Integer companyProfileSeq, List<Integer> moduleSeqList, Principal principal);

    ResponseObject removeCompanyModules(Integer companyProfileSeq, List<Integer> moduleSeqList, Principal principal);

    ResponseObject deleteCompanyProfile(Integer companyProfileSeq, Principal principal);
}
