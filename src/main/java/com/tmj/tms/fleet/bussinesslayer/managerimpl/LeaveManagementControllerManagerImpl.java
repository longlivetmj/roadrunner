package com.tmj.tms.fleet.bussinesslayer.managerimpl;

import com.querydsl.core.BooleanBuilder;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.fleet.bussinesslayer.manager.LeaveManagementControllerManager;
import com.tmj.tms.fleet.datalayer.modal.Leave;
import com.tmj.tms.fleet.datalayer.modal.QLeave;
import com.tmj.tms.fleet.datalayer.modal.auxiliary.LeaveSearch;
import com.tmj.tms.fleet.datalayer.service.LeaveService;
import com.tmj.tms.utility.DateFormatter;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class LeaveManagementControllerManagerImpl implements LeaveManagementControllerManager {

    private final LeaveService leaveService;
    private final HttpSession httpSession;

    @Autowired
    public LeaveManagementControllerManagerImpl(LeaveService leaveService,
                                                HttpSession httpSession) {
        this.leaveService = leaveService;
        this.httpSession = httpSession;
    }

    @Override
    public ResponseObject saveLeave(Leave leave) {
        ResponseObject responseObject = this.validate(leave);
        if (responseObject.isStatus()) {
            long hours = DateFormatter.getDateDiff(leave.getStartDate(), DateFormatter.getEndOfTheDay(leave.getEndDate()), TimeUnit.HOURS);
            leave.setNoOfDays(Math.round(hours / 24) + 1);
            Integer companyProfileSeq = Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString());
            leave.setCompanyProfileSeq(companyProfileSeq);
            leave = this.leaveService.save(leave);
            responseObject = new ResponseObject("Leave Saved Successfully", true);
            responseObject.setObject(leave);
        }
        return responseObject;
    }

    @Override
    public ResponseObject updateLeave(Leave leave) {
        ResponseObject responseObject = this.validate(leave);
        if (responseObject.isStatus()) {
            Leave dbLeave = this.leaveService.findOne(leave.getLeaveSeq());
            if (dbLeave != null) {
                if (!dbLeave.equals(leave)) {
                    long hours = DateFormatter.getDateDiff(leave.getStartDate(), DateFormatter.getEndOfTheDay(leave.getEndDate()), TimeUnit.HOURS);
                    leave.setNoOfDays(Math.round(hours / 24));
                    Integer companyProfileSeq = Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString());
                    leave.setCompanyProfileSeq(companyProfileSeq);
                    leave = this.leaveService.save(leave);
                    responseObject = new ResponseObject("Leave Updated Successfully", true);
                    responseObject.setObject(leave);
                } else {
                    responseObject = new ResponseObject("No Amendments Found", false);
                }
            } else {
                responseObject = new ResponseObject("Leave Not Found!!", false);
            }
        }
        return responseObject;
    }

    @Override
    public List<Leave> searchLeaves(LeaveSearch leaveSearch) {
        List<Leave> leaveList = null;
        try {
            QLeave leave = QLeave.leave;
            BooleanBuilder builder = new BooleanBuilder();
            Integer companyProfileSeq = Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString());
            builder.and(leave.companyProfileSeq.eq(companyProfileSeq));
            if (leaveSearch.getEmployeeDesignationSeq() != null) {
                builder.and(leave.employeeDesignationSeq.eq(leaveSearch.getEmployeeDesignationSeq()));
            }
            if (leaveSearch.getEmployeeName() != null) {
                builder.and(leave.employee.employeeName.containsIgnoreCase(leaveSearch.getEmployeeName()));
            }
            if (leaveSearch.getEndDate() != null && leaveSearch.getStartDate() != null) {
                builder.and(leave.startDate.between(leaveSearch.getStartDate(), leaveSearch.getEndDate()));
            }
            if (leaveSearch.getLeaveTypeSeq() != null) {
                builder.and(leave.leaveTypeSeq.eq(leaveSearch.getLeaveTypeSeq()));
            }
            leaveList = (List<Leave>) this.leaveService.findAll(builder);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return leaveList;
    }

    @Override
    public ResponseObject validate(Leave leave) {
        ResponseObject responseObject = new ResponseObject(true, "No Error");
        if (leave.getStartDate().after(leave.getEndDate())) {
            responseObject.setStatus(false);
            responseObject.setMessage("End should be equal or Greater than Start Date");
        }
        List<Leave> leaveList = this.leaveService.findByEmployeeSeqAndStartDateBetweenAndStatusNot(leave.getEmployeeSeq(), leave.getStartDate(), leave.getEndDate(), MasterDataStatus.DELETED.getStatusSeq());
        if (leaveList != null && leaveList.size() > 0) {
            responseObject.setStatus(false);
            responseObject.setMessage("Leave Already Exists comparing Start Date!!");
        } else {
            leaveList = this.leaveService.findByEmployeeSeqAndEndDateBetweenAndStatusNot(leave.getEmployeeSeq(), leave.getStartDate(), leave.getEndDate(), MasterDataStatus.DELETED.getStatusSeq());
            if (leaveList != null && leaveList.size() > 0) {
                responseObject.setStatus(false);
                responseObject.setMessage("Leave Already Exists End Date!!");
            } else {
                leaveList = this.leaveService.findByEmployeeSeqAndStartDateLessThanEqualAndEndDateGreaterThanEqualAndStatusNot(leave.getEmployeeSeq(), leave.getStartDate(), leave.getEndDate(), MasterDataStatus.DELETED.getStatusSeq());
                if (leaveList != null && leaveList.size() > 0) {
                    responseObject.setStatus(false);
                    responseObject.setMessage("Leave Already Exists Start and End Dates!!");
                }
            }
        }

        Integer month = leave.getLeaveMonth() - 1;
        Integer startMonth = DateFormatter.getMonthOfDate(leave.getStartDate());
        Integer endMonth = DateFormatter.getMonthOfDate(leave.getEndDate());
        if (!Objects.equals(month, startMonth)) {
            responseObject.setStatus(false);
            responseObject.setMessage("Start date is does not match with the Month !!");
        }
        if (!Objects.equals(month, endMonth)) {
            responseObject.setStatus(false);
            responseObject.setMessage("End date is does not match with the Month !!");
        }
        return responseObject;
    }

    @Override
    public ResponseObject deleteLeave(Integer leaveSeq) {
        ResponseObject responseObject;
        Leave leave = this.leaveService.findOne(leaveSeq);
        if (leave != null) {
            leave.setStatus(MasterDataStatus.DELETED.getStatusSeq());
            this.leaveService.save(leave);
            responseObject = new ResponseObject("Leave Deleted Successfully", true);
        } else {
            responseObject = new ResponseObject("Cannot find Leave!!", false);
        }
        return responseObject;
    }
}
