package com.tmj.tms.master.businesslayer.manager;

import com.tmj.tms.master.datalayer.modal.Branch;
import com.tmj.tms.utility.ResponseObject;

import java.util.List;

public interface BranchManagementControllerManager {

    ResponseObject saveBranch(Branch branch);

    ResponseObject updateBranch(Branch branch);

    ResponseObject deleteBranch(Integer branchSeq);

    List<Branch> searchBranch(String branchCode, String branchName, String bankName);

}
