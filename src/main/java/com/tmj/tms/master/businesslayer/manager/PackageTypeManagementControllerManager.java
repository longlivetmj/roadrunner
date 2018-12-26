package com.tmj.tms.master.businesslayer.manager;

import com.tmj.tms.master.datalayer.modal.PackageType;
import com.tmj.tms.utility.ResponseObject;

import java.security.Principal;
import java.util.List;

public interface PackageTypeManagementControllerManager {

    ResponseObject savePackageType(PackageType packageType, Principal principal);

    ResponseObject updatePackageType(PackageType packageType, Principal principal);

    ResponseObject deletePackageType(Integer packageTypeSeq, Principal principal);

    List<PackageType> searchPackageType(String packageTypeName, String packageTypeCode, String description);
}
