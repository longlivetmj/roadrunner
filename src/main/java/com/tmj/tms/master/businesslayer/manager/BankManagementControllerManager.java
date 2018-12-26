package com.tmj.tms.master.businesslayer.manager;

import com.tmj.tms.master.datalayer.modal.Bank;
import com.tmj.tms.utility.ResponseObject;

import java.util.List;

public interface BankManagementControllerManager {

    ResponseObject createBank(Bank bank);

    ResponseObject updateBank(Bank bank);

    List<Bank> searchBank(String bankName, String bankCode);

    ResponseObject deleteBank(Integer bankSeq);
}
