package com.tmj.tms.master.businesslayer.managerImpl;

import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.master.businesslayer.manager.CurrencyManagementControllerManager;
import com.tmj.tms.master.datalayer.modal.Currency;
import com.tmj.tms.master.datalayer.service.CurrencyService;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyManagementControllerManagerImpl implements CurrencyManagementControllerManager {

    private final CurrencyService currencyService;

    @Autowired
    public CurrencyManagementControllerManagerImpl(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }
    
    @Override
    public ResponseObject saveCurrency(Currency currency) {
        this.currencyService.save(currency);
        ResponseObject responseObject = new ResponseObject("Currency Saved Successfully", true);
        responseObject.setObject(currency);
        return responseObject;
    }

    @Override
    public ResponseObject updateCurrency(Currency currency) {
        ResponseObject responseObject;
        Currency dbCurrency = this.currencyService.findOne(currency.getCurrencySeq());
        if (dbCurrency != null) {
            if (!dbCurrency.equals(currency)) {
                currency = this.currencyService.save(currency);
                responseObject = new ResponseObject("Currency Updated Successfully", true);
                responseObject.setObject(currency);
            } else {
                responseObject = new ResponseObject("No Amendments Found !!", false);
            }
        } else {
            responseObject = new ResponseObject("Currency Not Found!!", false);
        }
        return responseObject;
    }

    @Override
    public ResponseObject deleteCurrency(Integer currencySeq) {
        Currency dbCurrency = this.currencyService.findOne(currencySeq);
        dbCurrency.setStatus(0);
        dbCurrency = this.currencyService.save(dbCurrency);
        ResponseObject responseObject = new ResponseObject("Currency Deleted Successfully", true);
        responseObject.setObject(dbCurrency);
        return responseObject;
    }
    
    @Override
    public List<Currency> searchCurrencyByCurrencyCodeAndCurrencyName(String currencyCode, String currencyName) {
        List<Currency> currencyList = null;
        try {
            List<Integer> statusSeqList = MasterDataStatus.getStatusListForViewing("ROLE_master@currencyManagement_VIEW-DELETE");
            if (currencyCode.equals("") && currencyName.equals("")) {
                currencyList = this.currencyService.findByStatusIn(statusSeqList);
            } else {
                currencyList = this.currencyService.findByCurrencyCodeContainingIgnoreCaseAndCurrencyNameContainingIgnoreCase(currencyCode, currencyName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return currencyList;
    }
}
