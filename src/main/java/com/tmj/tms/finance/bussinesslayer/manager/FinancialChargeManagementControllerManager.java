package com.tmj.tms.finance.bussinesslayer.manager;

import com.tmj.tms.finance.datalayer.modal.FinancialCharge;
import com.tmj.tms.finance.datalayer.modal.FinancialChargeDetail;
import com.tmj.tms.utility.ResponseObject;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface FinancialChargeManagementControllerManager {

    List<FinancialChargeDetail> loadDefaultChargeListForModuleSeqAndDepartmentSeqAndReferenceTye(Integer moduleSeq, Integer departmentSeq,
                                                                                                 Integer referenceType, Integer referenceSeq);

    ResponseObject saveFinancialCharge(FinancialCharge financialCharge);

    ResponseObject updateFinancialCharge(FinancialCharge financialCharge);

    ResponseObject deleteAllFinancialCharge(FinancialCharge financialCharge);
}
