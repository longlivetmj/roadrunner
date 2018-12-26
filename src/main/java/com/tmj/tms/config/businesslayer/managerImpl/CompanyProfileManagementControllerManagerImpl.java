package com.tmj.tms.config.businesslayer.managerImpl;

import com.tmj.tms.config.businesslayer.manager.CompanyProfileManagementControllerManager;
import com.tmj.tms.config.datalayer.modal.CompanyModule;
import com.tmj.tms.config.datalayer.modal.CompanyProfile;
import com.tmj.tms.config.datalayer.modal.UploadedDocument;
import com.tmj.tms.config.datalayer.service.CompanyModuleService;
import com.tmj.tms.config.datalayer.service.CompanyProfileService;
import com.tmj.tms.config.datalayer.service.UploadedDocumentService;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CompanyProfileManagementControllerManagerImpl implements CompanyProfileManagementControllerManager {

    private final CompanyProfileService companyProfileService;
    private final CompanyModuleService companyModuleService;
    private final UploadedDocumentService uploadedDocumentService;

    @Autowired
    public CompanyProfileManagementControllerManagerImpl(CompanyProfileService companyProfileService,
                                                         CompanyModuleService companyModuleService,
                                                         UploadedDocumentService uploadedDocumentService) {
        this.companyProfileService = companyProfileService;
        this.uploadedDocumentService = uploadedDocumentService;
        this.companyModuleService = companyModuleService;
    }

    @Override
    public ResponseObject createCompany(CompanyProfile companyProfile, MultipartFile multipartFile, Principal principal) {
        ResponseObject responseObject;
        //save logo file
        if (multipartFile != null && !multipartFile.isEmpty()) {
            UploadedDocument file = this.uploadedDocumentService.save(multipartFile, principal.getName());
            companyProfile.setUploadDocumentSeq(file.getUploadedDocumentSeq());
        }
        companyProfile = this.companyProfileService.save(companyProfile);
        responseObject = new ResponseObject("Company Profile Saved Successfully", true, companyProfile);
        responseObject.setObject(companyProfile);
        return responseObject;
    }

    @Override
    public ResponseObject updateCompany(CompanyProfile companyProfile, MultipartFile multipartFile, Principal principal) {
        ResponseObject responseObject;
        CompanyProfile dbCompanyProfile = this.companyProfileService.findOne(companyProfile.getCompanyProfileSeq());
        if (dbCompanyProfile != null) {
            if (!dbCompanyProfile.equals(companyProfile)) {
                System.out.println(dbCompanyProfile.equals(companyProfile));
                //save logo file
                if (multipartFile != null && !multipartFile.isEmpty()) {
                    UploadedDocument file = this.uploadedDocumentService.save(multipartFile, principal.getName());
                    companyProfile.setUploadDocumentSeq(file.getUploadedDocumentSeq());
                }

                //update companyProfile data
                dbCompanyProfile.setCompanyName(companyProfile.getCompanyName());
                dbCompanyProfile.setDescription(companyProfile.getDescription());
                dbCompanyProfile.setUploadDocumentSeq(companyProfile.getUploadDocumentSeq());
                if (companyProfile.getStatus() != null) {
                    dbCompanyProfile.setStatus(companyProfile.getStatus());
                }
                // update address book data

                companyProfile = this.companyProfileService.save(dbCompanyProfile);
                responseObject = new ResponseObject("Company Profile Updated Successfully", true, companyProfile);
            } else {
                responseObject = new ResponseObject("No Updates found for Company Profile!!", false);
            }
        } else {
            responseObject = new ResponseObject("Company Profile Not Found !!", false);
        }
        return responseObject;
    }

    @Override
    public ResponseObject createCompanyModules(Integer companyProfileSeq, List<Integer> moduleSeqList, Principal principal) {
        ResponseObject responseObject;
        ArrayList<CompanyModule> companyModules = new ArrayList<>();
        if (moduleSeqList.size() > 0) {
            for (Integer moduleSeq : moduleSeqList) {
                if (this.companyModuleService.findByCompanyProfileSeqAndModuleSeq(companyProfileSeq, moduleSeq) == null) {
                    CompanyModule companyModule = new CompanyModule();
                    companyModule.setCompanyProfileSeq(companyProfileSeq);
                    companyModule.setModuleSeq(moduleSeq);
                    companyModule.setCreatedBy(principal.getName());
                    companyModule.setCreatedDate(new Date());
                    companyModule.setStatus(1);
                    companyModule = this.companyModuleService.save(companyModule);
                    companyModules.add(companyModule);
                }
            }
            responseObject = new ResponseObject("Company Modules Saved Successfully", true, companyModules);
        } else {
            responseObject = new ResponseObject("No Company Modules Received", false);
        }
        return responseObject;
    }

    @Override
    public ResponseObject removeCompanyModules(Integer companyProfileSeq, List<Integer> moduleSeqList, Principal principal) {
        ResponseObject responseObject;
        for (Integer moduleSeq : moduleSeqList) {
            CompanyModule companyModule = this.companyModuleService.findByCompanyProfileSeqAndModuleSeq(companyProfileSeq, moduleSeq);
            companyModule.setStatus(0);
        }
        responseObject = new ResponseObject("Company Modules removed succesfully", true);
        return responseObject;
    }

    @Override
    public ResponseObject deleteCompanyProfile(Integer companyProfileSeq, Principal principal) {
        ResponseObject responseObject;
        CompanyProfile dbCompanyProfile = this.companyProfileService.findByCompanyProfileSeq(companyProfileSeq);
        dbCompanyProfile.setStatus(0);
        dbCompanyProfile.setLastModifiedBy(principal.getName());
        dbCompanyProfile.setLastModifiedDate(new Date());
        dbCompanyProfile = this.companyProfileService.save(dbCompanyProfile);
        responseObject = new ResponseObject("Company Profile Deleted Successfully", true, dbCompanyProfile);
        return responseObject;
    }
}
