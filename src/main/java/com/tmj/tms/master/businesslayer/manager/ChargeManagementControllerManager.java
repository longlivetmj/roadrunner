package com.tmj.tms.master.businesslayer.manager;

import com.tmj.tms.config.datalayer.modal.CompanyModule;
import com.tmj.tms.master.datalayer.modal.Charge;
import com.tmj.tms.utility.ResponseObject;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

public interface ChargeManagementControllerManager {

    ResponseObject createCharge(Charge charge, List<Integer> modeSeqList);

    ResponseObject updateCharge(Charge charge, List<Integer> modeSeqList);

    ResponseObject deleteCharge(Integer currencySeq);

    List<Charge> searchCharge(String chargeName, String description, Integer customerProfileSeq);

    List<CompanyModule> getFinanceEnabledCompanyModuleList();

    List<Charge> getViewingChargeList();

}
