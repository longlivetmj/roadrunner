package com.tmj.tms.finance.bussinesslayer.manager;

import com.tmj.tms.finance.utility.FinancialChargeSearch;
import com.tmj.tms.transport.datalayer.modal.TransportBooking;

import java.util.List;

public interface FinancialChargeSearchControllerManager {

    List<TransportBooking> searchBooking(FinancialChargeSearch financialChargeSearch);

}
