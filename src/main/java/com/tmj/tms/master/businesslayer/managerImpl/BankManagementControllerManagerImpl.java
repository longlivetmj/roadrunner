package com.tmj.tms.master.businesslayer.managerImpl;

import com.tmj.tms.master.businesslayer.manager.BankManagementControllerManager;
import com.tmj.tms.master.datalayer.modal.Bank;
import com.tmj.tms.master.datalayer.service.BankService;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BankManagementControllerManagerImpl implements BankManagementControllerManager {

    private final BankService bankService;

    @Autowired
    public BankManagementControllerManagerImpl(BankService bankService) {
        this.bankService = bankService;
    }

    @Override
    public ResponseObject createBank(Bank bank) {
        bank = this.bankService.save(bank);
        ResponseObject responseObject = new ResponseObject("Bank Saved Successfully", true);
        responseObject.setObject(bank);
        return responseObject;
    }

    @Override
    public ResponseObject updateBank(Bank bank) {
        ResponseObject responseObject;
        Bank dbBank = this.bankService.findOne(bank.getBankSeq());
        if (dbBank != null) {
            if (!dbBank.equals(bank)) {
                bank = this.bankService.save(bank);
                responseObject = new ResponseObject("Bank Updated Successfully", true);
                responseObject.setObject(bank);
            } else {
                responseObject = new ResponseObject("No Amendments Found", false);
            }
        } else {
            responseObject = new ResponseObject("Bank Not Found!!", false);
        }
        return responseObject;
    }

    @Override
    public List<Bank> searchBank(String bankName, String bankCode) {
        List<Bank> bankList = new ArrayList<>();
        try {
            List<Integer> statusSeqList = MasterDataStatus.getStatusListForViewing("ROLE_master@bankManagement_VIEW-DELETE");
            if (bankName.equals("") && bankCode.equals("")) {
                bankList = bankService.findByStatusIn(statusSeqList);
            } else if (!bankName.equals("") && bankCode.equals("")) {
                bankList = bankService.findByBankNameContainingIgnoreCaseAndStatusIn(bankName, statusSeqList);
            } else if (bankName.equals("") && !bankCode.equals("")) {
                bankList = bankService.findByBankCodeContainingIgnoreCaseAndStatusIn(bankCode, statusSeqList);
            } else if (!bankName.equals("") && !bankCode.equals("")) {
                bankList = bankService.findByBankNameContainingIgnoreCaseAndBankCodeContainingIgnoreCaseAndStatusIn(bankName, bankCode, statusSeqList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bankList;
    }

    public ResponseObject deleteBank(Integer bankSeq) {
        Bank dbBank = this.bankService.findOne(bankSeq);
        dbBank.setStatus(0);
        ResponseObject responseObject = new ResponseObject("Bank Deleted Successfully", true);
        dbBank = this.bankService.save(dbBank);
        responseObject.setObject(dbBank);

        return responseObject;
    }

}
