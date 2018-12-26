package com.tmj.tms.master.businesslayer.managerImpl;

import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.master.businesslayer.manager.PackageTypeManagementControllerManager;
import com.tmj.tms.master.datalayer.modal.PackageType;
import com.tmj.tms.master.datalayer.service.PackageTypeService;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class PackageTypeManagementControllerManagerImpl implements PackageTypeManagementControllerManager {

    private final PackageTypeService packageTypeService;

    @Autowired
    public PackageTypeManagementControllerManagerImpl(PackageTypeService packageTypeService) {
        this.packageTypeService = packageTypeService;
    }

    @Override
    public ResponseObject savePackageType(PackageType packageType, Principal principal) {
        this.packageTypeService.save(packageType);
        ResponseObject responseObject = new ResponseObject("Package Type Saved Successfully", true);
        responseObject.setObject(packageType);
        return responseObject;
    }

    @Override
    public ResponseObject updatePackageType(PackageType packageType, Principal principal) {
        ResponseObject responseObject;
        PackageType dbPackageType = this.packageTypeService.findOne(packageType.getPackageTypeSeq());
        if (dbPackageType != null) {
            if (!dbPackageType.equals(packageType)) {
                this.packageTypeService.save(packageType);
                responseObject = new ResponseObject("Package Type Updated Successfully", true);
                responseObject.setObject(dbPackageType);
            } else {
                responseObject = new ResponseObject("No amendments Found for Package Type !!", false);
            }
        } else {
            responseObject = new ResponseObject("Package Type Not Found !!", false);
        }
        return responseObject;
    }

    @Override
    public ResponseObject deletePackageType(Integer packageTypeSeq, Principal principal) {
        PackageType dbPackageType = this.packageTypeService.findOne(packageTypeSeq);
        dbPackageType.setStatus(0);
        dbPackageType = this.packageTypeService.save(dbPackageType);
        ResponseObject responseObject = new ResponseObject("Package Type Deleted Successfully", true);
        responseObject.setObject(dbPackageType);
        return responseObject;
    }

    @Override
    public List<PackageType> searchPackageType(String packageTypeName, String packageTypeCode, String description) {
        List<PackageType> packageTypeList;
        List<Integer> statusSeqList = MasterDataStatus.getStatusListForViewing("ROLE_master@packageTypeManagement_VIEW-DELETE");
        if (packageTypeName.equals("") && packageTypeCode.equals("") && description.equals("")) {
            packageTypeList = this.packageTypeService.findByStatusIn(statusSeqList);
        } else {
            packageTypeList = this.packageTypeService.findByPackageTypeNameContainingIgnoreCaseAndPackageTypeCodeContainingIgnoreCaseAndDescriptionContainingIgnoreCaseAndStatusIn(packageTypeName,
                    packageTypeCode,
                    description,
                    statusSeqList);
        }
        return packageTypeList;
    }

}
