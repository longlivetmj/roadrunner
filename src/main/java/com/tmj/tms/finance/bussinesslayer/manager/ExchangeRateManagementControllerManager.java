package com.tmj.tms.finance.bussinesslayer.manager;

import com.tmj.tms.finance.datalayer.modal.ExchangeRate;
import com.tmj.tms.finance.datalayer.modal.auxiliary.ExchangeRateSearchAux;
import com.tmj.tms.utility.ResponseObject;

import java.util.List;

public interface ExchangeRateManagementControllerManager {

    ResponseObject saveExchangeRate(ExchangeRate exchangeRate);

    ResponseObject updateExchangeRate(ExchangeRate exchangeRate);

    ResponseObject deleteExchangeRate(Integer exchangeRateSeq);

    List<ExchangeRate> searchExchangeRate(ExchangeRateSearchAux exchangeRateSearchAux);
}
