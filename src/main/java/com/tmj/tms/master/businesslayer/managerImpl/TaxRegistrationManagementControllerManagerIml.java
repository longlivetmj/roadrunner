package com.tmj.tms.master.businesslayer.managerImpl;

import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.master.businesslayer.manager.TaxRegistrationManagementControllerManager;
import com.tmj.tms.master.datalayer.modal.TaxRegistration;
import com.tmj.tms.master.datalayer.service.TaxRegistrationService;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaxRegistrationManagementControllerManagerIml implements TaxRegistrationManagementControllerManager {

    private final TaxRegistrationService taxRegistrationService;

    @Autowired
    public TaxRegistrationManagementControllerManagerIml(TaxRegistrationService taxRegistrationService) {
        this.taxRegistrationService = taxRegistrationService;
    }

    @Override
    public ResponseObject createTaxRegistration(TaxRegistration taxRegistration) {
        taxRegistration = this.taxRegistrationService.save(taxRegistration);
        ResponseObject responseObject = new ResponseObject("Tax Registration Saved Successfully", true);
        responseObject.setObject(taxRegistration);
        return responseObject;
    }

    @Override
    public ResponseObject updateTaxRegistration(TaxRegistration taxRegistration) {
        ResponseObject responseObject;
        TaxRegistration dbTaxRegistration = this.taxRegistrationService.findOne(taxRegistration.getTaxRegistrationSeq());
        if (dbTaxRegistration != null) {
            if (!dbTaxRegistration.equals(taxRegistration)) {
                taxRegistration = this.taxRegistrationService.save(taxRegistration);
                responseObject = new ResponseObject("Tax Registration Updated Successfully", true);
                responseObject.setObject(taxRegistration);
            } else {
                responseObject = new ResponseObject("No Amendments found!!", false);
            }
        } else {
            responseObject = new ResponseObject("Tax Registration Not found!!", false);
        }
        return responseObject;
    }

    @Override
    public List<TaxRegistration> searchTaxRegistration(String taxName, String remarks, Integer countrySeq, Integer customerProfileSeq) {
        List<TaxRegistration> taxRegistrationList = null;
        try {
            List<Integer> statusSeqList = MasterDataStatus.getStatusListForViewing("ROLE_master@taxRegistrationManagement_VIEW-DELETE");
            if (taxName.equals(" ") && remarks.equals(" ") && countrySeq == null) {
                taxRegistrationList = this.taxRegistrationService.findByStatusIn(statusSeqList);
            } else if (countrySeq == null) {
                taxRegistrationList = this.taxRegistrationService.findByTaxNameContainingIgnoreCaseAndRemarksContainingIgnoreCaseAndCompanyProfileSeqAndStatusIn(taxName, remarks, customerProfileSeq, statusSeqList);
            } else {
                taxRegistrationList = this.taxRegistrationService.findByTaxNameContainingIgnoreCaseAndRemarksContainingIgnoreCaseAndCompanyProfileSeqAndCountrySeqAndAndStatusIn(taxName, remarks, customerProfileSeq, countrySeq, statusSeqList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return taxRegistrationList;
    }

    @Override
    public ResponseObject deleteTaxRegistration(Integer taxRegistrationSeq) {
        TaxRegistration dbTaxRegistration = this.taxRegistrationService.findOne(taxRegistrationSeq);
        dbTaxRegistration.setStatus(0);
        dbTaxRegistration = this.taxRegistrationService.save(dbTaxRegistration);
        ResponseObject responseObject = new ResponseObject("Tax Registration Deleted Successfully", true);
        responseObject.setObject(dbTaxRegistration);
        return responseObject;
    }
}
