package com.tmj.tms.config.businesslayer.managerImpl;

import com.tmj.tms.config.businesslayer.manager.UserManagementControllerManager;
import com.tmj.tms.config.datalayer.modal.*;
import com.tmj.tms.config.datalayer.service.*;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.utility.DateFormatter;
import com.tmj.tms.utility.ResponseObject;
import com.tmj.tms.utility.SendUserCreationEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.security.MessageDigest;
import java.util.Date;
import java.util.List;

@Service
public class UserManagementControllerManagerImpl implements UserManagementControllerManager {

    private final UserService userService;
    private final CompanyModuleService companyModuleService;
    private final UploadedDocumentService uploadedDocumentService;
    private final UserModuleService userModuleService;
    private final UserDepartmentService userDepartmentService;
    private final SendUserCreationEmail sendUserCreationEmail;

    @Autowired
    public UserManagementControllerManagerImpl(UserService userService,
                                               CompanyModuleService companyModuleService,
                                               UploadedDocumentService uploadedDocumentService,
                                               UserModuleService userModuleService,
                                               UserDepartmentService userDepartmentService,
                                               SendUserCreationEmail sendUserCreationEmail) {
        this.userService = userService;
        this.companyModuleService = companyModuleService;
        this.uploadedDocumentService = uploadedDocumentService;
        this.userModuleService = userModuleService;
        this.userDepartmentService = userDepartmentService;
        this.sendUserCreationEmail = sendUserCreationEmail;
    }

    private static String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte aData : data) {
            int halfbyte = (aData >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9))
                    buf.append((char) ('0' + halfbyte));
                else
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = aData & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    @Override
    @Transactional
    public ResponseObject createUser(User user, MultipartFile multipartFile, String username) {
        ResponseObject responseObject = new ResponseObject("User Successfully Saved", true);
        try {
            if (this.userService.findByUsername(user.getUsername()) == null) {
                UploadedDocument file = this.uploadedDocumentService.save(multipartFile, username);
                user.setUploadDocumentSeq(file.getUploadedDocumentSeq());
                //Date was coming with time from the front end
                user.setDateOfBirth(DateFormatter.getStartOfTheDay(user.getDateOfBirth()));
                //Password Encryption
                String encryptedPassword = this.SHA1("changeme");
                user.setPassword(encryptedPassword);
                //Password Encryption
                user.setEnabled(1);
                user.setCreatedBy(username);
                user.setCreatedDate(new Date());
                user = this.userService.save(user);
                this.sendUserCreationEmail.sendEmailForSingleUser(user);
                responseObject.setObject(user);
            } else {
                responseObject.setMessage("Username already exist!!");
                responseObject.setStatus(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseObject.setMessage(e.getMessage());
            responseObject.setStatus(false);
        }
        return responseObject;
    }

    @Override
    @Transactional
    public ResponseObject updateUser(User user, MultipartFile multipartFile, String username) {
        ResponseObject responseObject = new ResponseObject("User Successfully Updated", true);
        try {
            User dbUser = this.userService.findOne(user.getUserSeq());
            if (dbUser != null) {
                dbUser.setFirstName(user.getFirstName());
                dbUser.setSecondName(user.getSecondName());
                dbUser.setEnabled(user.getEnabled() == null ? 0 : 1);
                dbUser.setDateOfBirth(DateFormatter.getStartOfTheDay(user.getDateOfBirth()));
                dbUser.setContactNo(user.getContactNo());
                dbUser.setEmail(user.getEmail());
                dbUser.setModifiedBy(username);
                dbUser.setModifiedDate(new Date());
                UploadedDocument file = this.uploadedDocumentService.save(multipartFile, username);
                if (file.getUploadedDocumentSeq() != null) {
                    dbUser.setUploadDocumentSeq(file.getUploadedDocumentSeq());
                }
                this.userService.save(dbUser);
            } else {
                responseObject.setMessage("User not found!!");
                responseObject.setStatus(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseObject.setMessage("Error Updating user");
            responseObject.setStatus(false);
        }
        return responseObject;
    }

    @Override
    public ResponseObject assignUserModuleList(Integer userSeq, Integer companyProfileSeq, List<Integer> moduleSeqList, String username) {
        ResponseObject responseObject = new ResponseObject("User Module Successfully Saved", true);
        try {
            if (moduleSeqList.size() > 0) {
                for (Integer moduleSeq : moduleSeqList) {
                    UserModule dbUserModule = this.userModuleService.findByUserSeqCompanyProfileSeqAndModuleSeq(userSeq, companyProfileSeq, moduleSeq);
                    if (dbUserModule == null) {
                        UserModule userModule = new UserModule();
                        userModule.setUserSeq(userSeq);
                        CompanyModule companyModule = this.companyModuleService.findByCompanyProfileSeqAndModuleSeq(companyProfileSeq, moduleSeq);
                        userModule.setCompanyModuleSeq(companyModule.getCompanyModuleSeq());
                        userModule.setCreatedBy(username);
                        userModule.setCreatedDate(new Date());
                        userModule.setStatus(1);
                        this.userModuleService.save(userModule);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseObject.setMessage("User Module Save failed");
            responseObject.setStatus(false);
        }
        return responseObject;
    }

    @Override
    public ResponseObject assignUserDepartmentList(Integer userSeq, Integer moduleSeq, List<Integer> departmentSeqList, Integer status, String username) {
        ResponseObject responseObject = new ResponseObject("User Department Successfully Saved", true);
        try {
            if (departmentSeqList.size() > 0) {
                List<UserDepartment> dbUserDepartmentList = this.userDepartmentService.findByUserSeqAndStatusAndDepartment_ModuleSeq(userSeq, 1, moduleSeq);
                for (Integer departmentSeq : departmentSeqList) {
                    UserDepartment userDepartment = new UserDepartment();
                    userDepartment.setUserSeq(userSeq);
                    userDepartment.setDepartmentSeq(departmentSeq);
                    userDepartment.setStatus(status);
                    if (!dbUserDepartmentList.contains(userDepartment)) {
                        this.userDepartmentService.save(userDepartment);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseObject.setMessage("User Department Save failed");
            responseObject.setStatus(false);
        }
        return responseObject;
    }

    @Override
    public ResponseObject removeUserModuleList(Integer userSeq, Integer companyProfileSeq, List<Integer> moduleSeqList, String username) {
        ResponseObject responseObject = new ResponseObject("User Module Successfully Removed", true);
        try {
            for (Integer moduleSeq : moduleSeqList) {
                CompanyModule companyModule = this.companyModuleService.findByCompanyProfileSeqAndModuleSeq(companyProfileSeq, moduleSeq);
                UserModule userModule = this.userModuleService.findByUserSeqAndCompanyModuleSeq(userSeq, companyModule.getCompanyModuleSeq());
                userModule.setStatus(0);
                userModule.setModifiedDate(new Date());
                userModule.setModifiedBy(username);
                this.userModuleService.save(userModule);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseObject.setMessage("User Module Save failed");
            responseObject.setStatus(false);
        }
        return responseObject;
    }

    @Override
    public ResponseObject removeDepartmentsFromUser(Integer userSeq, Integer moduleSeq, List<Integer> departmentSeqList, String name) {
        ResponseObject responseObject = new ResponseObject("User Department Successfully Removed", true);
        try {
            if (departmentSeqList.size() > 0) {
                for (Integer departmentSeq : departmentSeqList) {
                    UserDepartment userDepartment = this.userDepartmentService.findByUserSeqAndDepartmentSeqAndStatusAndDepartment_ModuleSeq(userSeq, departmentSeq, MasterDataStatus.OPEN.getStatusSeq(), moduleSeq);
                    userDepartment.setStatus(MasterDataStatus.DELETED.getStatusSeq());
                    this.userDepartmentService.save(userDepartment);
                }
            }
            responseObject.setMessage("User Department Remove Successful");
            responseObject.setStatus(true);
        } catch (Exception e) {
            e.printStackTrace();
            responseObject.setMessage("User Department Remove failed");
            responseObject.setStatus(false);
        }
        return responseObject;
    }


    public String SHA256(String username) {
        try {
            MessageDigest md;
            md = MessageDigest.getInstance("SHA-256");
            byte[] sha1hash;
            md.update(username.getBytes("iso-8859-1"), 0, username.length());
            sha1hash = md.digest();
            return convertToHex(sha1hash);
        } catch (Exception e) {
            return "";
        }
    }

    public String SHA1(String username) {
        try {
            MessageDigest md;
            md = MessageDigest.getInstance("SHA-1");
            byte[] sha1hash;
            md.update(username.getBytes("iso-8859-1"), 0, username.length());
            sha1hash = md.digest();
            return convertToHex(sha1hash);
        } catch (Exception e) {
            return "";
        }
    }
}
