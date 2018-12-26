package com.tmj.tms.master.businesslayer.manager;

import com.tmj.tms.master.datalayer.modal.TaxRegistration;
import com.tmj.tms.utility.ResponseObject;

import java.util.List;

public interface TaxRegistrationManagementControllerManager {

    ResponseObject createTaxRegistration(TaxRegistration taxRegistration);

    ResponseObject updateTaxRegistration(TaxRegistration taxRegistration);

    List<TaxRegistration> searchTaxRegistration(String taxName, String remarks, Integer countrySeq, Integer customerProfileSeq);

    ResponseObject deleteTaxRegistration(Integer taxRegistrationSeq);
}
