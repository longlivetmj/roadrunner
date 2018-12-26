package com.tmj.tms.master.businesslayer.managerImpl;

import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.master.businesslayer.manager.CustomerGroupManagementControllerManager;
import com.tmj.tms.master.datalayer.modal.CustomerGroup;
import com.tmj.tms.master.datalayer.service.CustomerGroupService;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerGroupManagementControllerManagerImpl implements CustomerGroupManagementControllerManager {

    private final CustomerGroupService customerGroupService;

    @Autowired
    public CustomerGroupManagementControllerManagerImpl(CustomerGroupService customerGroupService) {
        this.customerGroupService = customerGroupService;
    }

    @Override
    public ResponseObject saveCustomerGroup(CustomerGroup customerGroup) {
        customerGroup = this.customerGroupService.save(customerGroup);
        ResponseObject responseObject = new ResponseObject("Customer Group Saved Successfully", true);
        responseObject.setObject(customerGroup);
        return responseObject;
    }

    @Override
    public ResponseObject updateCustomerGroup(CustomerGroup customerGroup) {
        ResponseObject responseObject;
        CustomerGroup dbCustomerGroup = this.customerGroupService.findOne(customerGroup.getCustomerGroupSeq());
        if (dbCustomerGroup != null) {
            if (!dbCustomerGroup.equals(customerGroup)) {
                customerGroup = this.customerGroupService.save(customerGroup);
                responseObject = new ResponseObject("Customer Group Updated Successfully", true);
                responseObject.setObject(customerGroup);
            } else {
                responseObject = new ResponseObject("No Amendments Found!!", false);
            }
        } else {
            responseObject = new ResponseObject("Customer Group Not Found!!", true);
        }
        return responseObject;
    }

    @Override
    public ResponseObject deleteCustomerGroup(Integer customerGroupSeq) {
        CustomerGroup dbCustomerGroup = this.customerGroupService.findOne(customerGroupSeq);
        dbCustomerGroup.setStatus(0);
        dbCustomerGroup = this.customerGroupService.save(dbCustomerGroup);
        ResponseObject responseObject = new ResponseObject("Customer Group Deleted Successfully", true);
        responseObject.setObject(dbCustomerGroup);
        return responseObject;
    }

    @Override
    public List<CustomerGroup> searchCustomerGroup(String customerGroupCode, String customerGroupName) {
        List<CustomerGroup> customerGroupList = null;
        try {
            List<Integer> statusSeqList = MasterDataStatus.getStatusListForViewing("ROLE_master@customerGroupManagement_VIEW-DELETE");
            if (customerGroupCode.equals("") && customerGroupName.equals("")) {
                customerGroupList = customerGroupService.findByStatusIn(statusSeqList);
            } else if (!customerGroupCode.equals("") && customerGroupName.equals("")) {
                customerGroupList = customerGroupService.findByCustomerGroupCodeContainingIgnoreCaseAndStatusIn(customerGroupCode, statusSeqList);
            } else if (customerGroupCode.equals("") && !customerGroupName.equals("")) {
                customerGroupList = customerGroupService.findByCustomerGroupNameContainingIgnoreCaseAndStatusIn(customerGroupName, statusSeqList);
            } else if (!customerGroupCode.equals("") && !customerGroupName.equals("")) {
                customerGroupList = customerGroupService.findByCustomerGroupCodeContainingIgnoreCaseAndCustomerGroupNameContainingIgnoreCaseAndStatusIn(customerGroupCode, customerGroupName, statusSeqList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customerGroupList;
    }
}