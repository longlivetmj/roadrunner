package com.tmj.tms.master.businesslayer.managerImpl;

import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.master.businesslayer.manager.BranchManagementControllerManager;
import com.tmj.tms.master.datalayer.modal.Branch;
import com.tmj.tms.master.datalayer.service.BranchService;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchManagementControllerManagerImpl implements BranchManagementControllerManager {

    private final BranchService branchService;

    @Autowired
    public BranchManagementControllerManagerImpl(BranchService branchService) {
        this.branchService = branchService;
    }

    @Override
    public ResponseObject saveBranch(Branch branch) {
        branch = this.branchService.save(branch);
        ResponseObject responseObject = new ResponseObject("Branch Saved Successfully", true);
        responseObject.setObject(branch);
        return responseObject;
    }

    @Override
    public ResponseObject updateBranch(Branch branch) {
        ResponseObject responseObject;
        Branch dbBranch = this.branchService.findOne(branch.getBranchSeq());
        if (dbBranch != null) {
            if (!dbBranch.equals(branch)) {
                branch = this.branchService.save(branch);
                responseObject = new ResponseObject("Branch Updated Successfully", true);
                responseObject.setObject(branch);
            } else {
                responseObject = new ResponseObject("No Amendments Found!!", false);
            }
        } else {
            responseObject = new ResponseObject("Branch Not Found!!", false);
        }
        return responseObject;
    }

    @Override
    public ResponseObject deleteBranch(Integer branchSeq) {
        Branch dbBranch = this.branchService.findOne(branchSeq);
        dbBranch.setStatus(0);
        dbBranch = this.branchService.save(dbBranch);
        ResponseObject responseObject = new ResponseObject("Branch Deleted Successfully", true);
        responseObject.setObject(dbBranch);
        return responseObject;
    }

    @Override
    public List<Branch> searchBranch(String branchCode, String branchName, String bankName) {
        List<Branch> branchList = null;
        try {
            List<Integer> statusSeqList = MasterDataStatus.getStatusListForViewing("ROLE_master@branchManagement_VIEW-DELETE");
            if (branchCode.equals("") && branchName.equals("") && bankName.equals("")) {
                branchList = branchService.findByStatusIn(statusSeqList);
            } else if (!branchCode.equals("") && branchName.equals("") && bankName.equals("")) {
                branchList = branchService.findByBranchCodeContainingIgnoreCaseAndStatusIn(branchCode, statusSeqList);
            } else if (branchCode.equals("") && !branchName.equals("") && bankName.equals("")) {
                branchList = branchService.findByBranchNameContainingIgnoreCaseAndStatusIn(branchName, statusSeqList);
            } else if (branchCode.equals("") && branchName.equals("") && !bankName.equals("")) {
                branchList = branchService.findByBankBankNameContainingIgnoreCaseAndStatusIn(bankName, statusSeqList);
            } else if (!branchCode.equals("") && !branchName.equals("") && bankName.equals("")) {
                branchList = branchService.findByBranchCodeContainingIgnoreCaseAndBranchNameContainingIgnoreCaseAndStatusIn(branchCode, branchName, statusSeqList);
            } else if (!branchCode.equals("") && branchName.equals("") && !bankName.equals("")) {
                branchList = branchService.findByBranchCodeContainingIgnoreCaseAndBankBankNameContainingIgnoreCaseAndStatusIn(branchCode, bankName, statusSeqList);
            } else if (branchCode.equals("") && !branchName.equals("") && !bankName.equals("")) {
                branchList = branchService.findByBranchNameContainingIgnoreCaseAndBankBankNameContainingIgnoreCaseAndStatusIn(branchName, bankName, statusSeqList);
            } else if (!branchCode.equals("") && !branchName.equals("") && !bankName.equals("")) {
                branchList = branchService.findByBranchCodeContainingIgnoreCaseAndBranchNameContainingIgnoreCaseAndBankBankNameContainingIgnoreCaseAndStatusIn(branchCode, branchName, bankName, statusSeqList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return branchList;
    }
}
