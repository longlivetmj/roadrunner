package com.tmj.tms.master.businesslayer.manager;

import com.tmj.tms.master.datalayer.modal.SalesPerson;
import com.tmj.tms.utility.ResponseObject;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface SalesPersonManagementControllerManager {

    ResponseObject saveSalesPerson(SalesPerson salesPerson);

    ResponseObject updateSalesPerson(SalesPerson salesPerson);

    ResponseObject deleteSalesPerson(Integer salesPersonSeq);

    List<SalesPerson> searchSalesPerson(String salesPersonCode, String salesPersonName, Integer stakeholderSeq, HttpServletRequest request);
}
