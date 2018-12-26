package com.tmj.tms.master.businesslayer.manager;


import com.tmj.tms.master.datalayer.modal.CustomerGroup;
import com.tmj.tms.utility.ResponseObject;

import java.util.List;

public interface CustomerGroupManagementControllerManager {

    ResponseObject saveCustomerGroup(CustomerGroup customerGroupt);

    ResponseObject updateCustomerGroup(CustomerGroup customerGroup);

    ResponseObject deleteCustomerGroup(Integer customerGroupSeq);

    List<CustomerGroup> searchCustomerGroup(String customerGroupCode, String customerGroupName);

}
