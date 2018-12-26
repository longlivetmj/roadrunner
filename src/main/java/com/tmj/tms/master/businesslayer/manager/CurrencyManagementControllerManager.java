package com.tmj.tms.master.businesslayer.manager;

import com.tmj.tms.master.datalayer.modal.Currency;
import com.tmj.tms.utility.ResponseObject;

import java.util.List;

public interface CurrencyManagementControllerManager {

    ResponseObject saveCurrency(Currency currency);

    ResponseObject deleteCurrency(Integer currencySeq);

    ResponseObject updateCurrency(Currency currency);

    List<Currency> searchCurrencyByCurrencyCodeAndCurrencyName(String currencyCode, String currencyName);

}
