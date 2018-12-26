package com.tmj.tms.config.businesslayer.managerImpl;

import com.tmj.tms.config.businesslayer.manager.DepartmentManagementControllerManager;
import com.tmj.tms.config.datalayer.modal.Department;
import com.tmj.tms.config.datalayer.modal.DepartmentCharge;
import com.tmj.tms.config.datalayer.modal.DepartmentChargeAux;
import com.tmj.tms.config.datalayer.service.DepartmentChargeService;
import com.tmj.tms.config.datalayer.service.DepartmentService;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Service
public class DepartmentManagementControllerManagerImpl implements DepartmentManagementControllerManager {

    private final DepartmentService departmentService;
    private final DepartmentChargeService departmentChargeService;

    @Autowired
    public DepartmentManagementControllerManagerImpl(DepartmentService departmentService,
                                                     DepartmentChargeService departmentChargeService) {
        this.departmentService = departmentService;
        this.departmentChargeService = departmentChargeService;
    }

    @Override
    public ResponseObject saveDepartment(Department department, Principal principal, HttpServletRequest request) {
        Integer companyProfileSeq = Integer.parseInt(request.getSession().getAttribute("companyProfileSeq").toString());
        department.setCompanyProfileSeq(companyProfileSeq);
        department = this.departmentService.save(department);
        ResponseObject responseObject = new ResponseObject("Department Saved Successfully", true);
        responseObject.setObject(department);
        return responseObject;
    }

    @Override
    public ResponseObject updateDepartment(Department department, Principal principal) {
        ResponseObject responseObject;
        Department dbDepartment = this.departmentService.findOne(department.getDepartmentSeq());
        if (dbDepartment != null) {
            if (!dbDepartment.equals(department)) {
                department = this.departmentService.save(department);
                responseObject = new ResponseObject("Department Updated Successfully", true);
                responseObject.setObject(department);
            } else {
                responseObject = new ResponseObject("No Amendments Found !!!", false);
            }
        } else {
            responseObject = new ResponseObject("Department Not Found!!", true);
            responseObject.setStatus(false);
        }
        return responseObject;
    }

    @Override
    public ResponseObject deleteDepartment(Integer departmentSeq, Principal principal) {
        ResponseObject responseObject;
        Department dbDepartment = this.departmentService.findOne(departmentSeq);
        dbDepartment.setStatus(0);
        dbDepartment = this.departmentService.save(dbDepartment);
        responseObject = new ResponseObject("Department Deleted Successfully", true);
        responseObject.setObject(dbDepartment);
        return responseObject;
    }

    @Override
    public List<Department> searchDepartment(String departmentCode, String departmentName, String prifix) {
        List<Department> departmentList = null;
        try {
            List<Integer> statusSeqList = MasterDataStatus.getStatusListForViewing("ROLE_config@departmentManagement_VIEW-DELETE");
            if (departmentCode.equals("") && departmentCode.equals("") && prifix.equals("")) {
                departmentList = this.departmentService.findByStatusIn(statusSeqList);
            } else {
                departmentList = this.departmentService.findByDepartmentCodeContainingIgnoreCaseAndDepartmentNameContainingIgnoreCaseAndPrefixContainingIgnoreCaseAndStatusIn(departmentCode, departmentName, prifix, statusSeqList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return departmentList;
    }

    @Override
    public ResponseObject saveDepartmentCharges(DepartmentChargeAux departmentChargesAux, Principal principal) {
        ResponseObject responseObject;
        Integer moduleSeq = departmentChargesAux.getModuleSeq();
        Integer departmentSeq = departmentChargesAux.getDepartmentSeq();
        List<DepartmentCharge> departmentChargeFromModel = departmentChargesAux.getDepartmentCharges();

        List<DepartmentCharge> dbDepartmentCharges = this.departmentChargeService.findByModuleSeqAndDepartmentSeq(moduleSeq, departmentSeq);
        this.departmentChargeService.delete(dbDepartmentCharges);

        for (DepartmentCharge departmentCharge : departmentChargeFromModel) {
            departmentCharge.setModuleSeq(moduleSeq);
            departmentCharge.setDepartmentSeq(departmentSeq);
            departmentCharge.setStatus(1);
        }
        departmentChargeFromModel = departmentChargeService.save(departmentChargeFromModel);
        responseObject = new ResponseObject("Department Charges created Successfully", true, departmentChargeFromModel);
        return responseObject;
    }

}