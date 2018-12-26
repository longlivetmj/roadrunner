package com.tmj.tms.fleet.bussinesslayer.manager;

import com.tmj.tms.fleet.datalayer.modal.Leave;
import com.tmj.tms.fleet.datalayer.modal.auxiliary.LeaveSearch;
import com.tmj.tms.utility.ResponseObject;

import java.util.List;

public interface LeaveManagementControllerManager {

    ResponseObject saveLeave(Leave leave);

    ResponseObject updateLeave(Leave leave);

    List<Leave> searchLeaves(LeaveSearch leaveSearch);

    ResponseObject validate(Leave leave);

    ResponseObject deleteLeave(Integer leaveSeq);
}
