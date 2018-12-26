package com.tmj.tms.finance.bussinesslayer.managerimpl;

import com.tmj.tms.config.datalayer.modal.DepartmentCharge;
import com.tmj.tms.config.datalayer.service.DepartmentChargeService;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.finance.bussinesslayer.manager.FinancialChargeManagementControllerManager;
import com.tmj.tms.finance.datalayer.modal.FinancialCharge;
import com.tmj.tms.finance.datalayer.modal.FinancialChargeDetail;
import com.tmj.tms.finance.datalayer.service.FinancialChargeService;
import com.tmj.tms.finance.utility.ReferenceType;
import com.tmj.tms.transport.datalayer.modal.Job;
import com.tmj.tms.transport.datalayer.modal.TransportBooking;
import com.tmj.tms.transport.datalayer.service.JobService;
import com.tmj.tms.transport.datalayer.service.TransportBookingService;
import com.tmj.tms.transport.utility.BookingStatus;
import com.tmj.tms.utility.NumberFormatter;
import com.tmj.tms.utility.ResponseObject;
import com.tmj.tms.utility.YesOrNo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class FinancialChargeManagementControllerManagerImpl implements FinancialChargeManagementControllerManager {

    private final DepartmentChargeService departmentChargeService;
    private final TransportBookingService transportBookingService;
    private final FinancialChargeService financialChargeService;
    private final JobService jobService;

    @Autowired
    public FinancialChargeManagementControllerManagerImpl(DepartmentChargeService departmentChargeService,
                                                          TransportBookingService transportBookingService,
                                                          FinancialChargeService financialChargeService,
                                                          JobService jobService) {
        this.departmentChargeService = departmentChargeService;
        this.transportBookingService = transportBookingService;
        this.financialChargeService = financialChargeService;
        this.jobService = jobService;
    }

    @Override
    public List<FinancialChargeDetail> loadDefaultChargeListForModuleSeqAndDepartmentSeqAndReferenceTye(Integer moduleSeq, Integer departmentSeq, Integer referenceType, Integer referenceSeq) {
        List<FinancialChargeDetail> financialChargeDetailList = new ArrayList<>();
        List<DepartmentCharge> departmentChargeList = this.departmentChargeService.findByModuleSeqAndDepartmentSeqAndReferenceTypeOrderByDefaultOrderAsc(moduleSeq, departmentSeq, referenceType);
        if (referenceType.equals(ReferenceType.TRANSPORT_BOOKING.getReferenceType())) {
            TransportBooking transportBooking = this.transportBookingService.findOne(referenceSeq);
            for (DepartmentCharge departmentCharge : departmentChargeList) {
                FinancialChargeDetail financialChargeDetail = new FinancialChargeDetail();
                financialChargeDetail.setChargeType(departmentCharge.getChargeType());
                financialChargeDetail.setChargeSeq(departmentCharge.getChargeSeq());
                financialChargeDetail.setCharge(departmentCharge.getCharge());
                financialChargeDetail.setCurrencySeq(departmentCharge.getCurrencySeq());
                financialChargeDetail.setCurrency(departmentCharge.getCurrency());
                financialChargeDetail.setUnit(departmentCharge.getUnit());
                financialChargeDetail.setUnitSeq(departmentCharge.getUnitSeq());

                if (departmentCharge.getQuantity() != null) {
                    if (departmentCharge.getQuantity().equals(-1)) {
                        financialChargeDetail.setQuantity(transportBooking.getTransportBookingFeedback().getChargeableKm());
                    } else {
                        financialChargeDetail.setQuantity(financialChargeDetail.getQuantity());
                    }
                }
                if (departmentCharge.getAmount() != null) {
                    if (departmentCharge.getAmount() == -1) {
                        financialChargeDetail.setChargeValue(transportBooking.getProposedTransportCharge());
                    } else {
                        financialChargeDetail.setChargeValue(departmentCharge.getAmount());
                    }
                }
                if (financialChargeDetail.getQuantity() != null && financialChargeDetail.getChargeValue() != null) {
                    financialChargeDetail.setAmount(NumberFormatter.round(financialChargeDetail.getQuantity() * financialChargeDetail.getChargeValue(), 2));
                }
                financialChargeDetailList.add(financialChargeDetail);
            }
        }
        return financialChargeDetailList;
    }

    @Override
    public ResponseObject saveFinancialCharge(FinancialCharge financialCharge) {
        ResponseObject responseObject;
        boolean status = false;
        financialCharge.setStatus(MasterDataStatus.APPROVED.getStatusSeq());
        List<FinancialChargeDetail> financialChargeDetailList = new ArrayList<>();
        for (FinancialChargeDetail financialChargeDetail : financialCharge.getFinancialChargeDetails()) {
            if (financialChargeDetail.getChargeSeq() != null && financialChargeDetail.getChargeValue() != null &&
                    financialChargeDetail.getCurrencySeq() != null && financialChargeDetail.getQuantity() != null &&
                    financialChargeDetail.getUnitSeq() != null) {
                status = true;
                if (financialChargeDetail.getChargeType() != null) {
                    financialChargeDetail.setLiStatus(MasterDataStatus.OPEN.getStatusSeq());
                    financialChargeDetail.setEvStatus(MasterDataStatus.OPEN.getStatusSeq());
                    financialChargeDetail.setStatus(MasterDataStatus.OPEN.getStatusSeq());
                    financialChargeDetailList.add(financialChargeDetail);
                }
            } else {
                status = false;
            }
        }
        if (status) {
            if (financialChargeDetailList.size() > 0) {
                financialCharge.setFinancialChargeDetails(financialChargeDetailList);
                financialCharge = this.financialChargeService.save(financialCharge);
                responseObject = new ResponseObject("Financial Charge Saved Successfully", true);
                responseObject.setObject(financialCharge);
                Job job = this.jobService.findByModuleSeqAndCompanyProfileSeqAndReferenceSeq(financialCharge.getModuleSeq(), financialCharge.getCompanyProfileSeq(), financialCharge.getReferenceSeq());
                job.setUpdateFlag(YesOrNo.NO.getYesOrNoSeq());
                this.jobService.save(job);
            } else {
                responseObject = new ResponseObject("No data found", false);
            }
        } else {
            responseObject = new ResponseObject("Please enter required fields", false);
        }
        return responseObject;
    }

    @Override
    public ResponseObject updateFinancialCharge(FinancialCharge financialCharge) {
        ResponseObject responseObject = new ResponseObject();
        FinancialCharge dbFinancialCharge = this.financialChargeService.findOne(financialCharge.getFinancialChargeSeq());
        boolean okToUpdate = true;
        if (dbFinancialCharge.getReferenceType().equals(ReferenceType.TRANSPORT_BOOKING.getReferenceType())) {
            TransportBooking transportBooking = this.transportBookingService.findOne(dbFinancialCharge.getReferenceSeq());
            if (transportBooking.getCurrentStatus().equals(BookingStatus.JOB_CLOSED.getCurrentStatus())) {
                okToUpdate = false;
                responseObject.setStatus(false);
                responseObject.setMessage("Job is Closed. Cannot Updated Charges!!");
            }
        }
        if (okToUpdate) {
            try {
                List<FinancialChargeDetail> dbFinanceChargeDetailList = dbFinancialCharge.getFinancialChargeDetails();
                List<FinancialChargeDetail> frontFinanceChargeDetailList = financialCharge.getFinancialChargeDetails();
                List<FinancialChargeDetail> savingList = new ArrayList<>();
                if (dbFinanceChargeDetailList.size() > 0) {
                    //new Record
                    for (FinancialChargeDetail financialChargeDetail : frontFinanceChargeDetailList) {
                        if (financialChargeDetail.getFinancialChargeDetailSeq() == null) {
                            financialChargeDetail.setLiStatus(MasterDataStatus.OPEN.getStatusSeq());
                            financialChargeDetail.setEvStatus(MasterDataStatus.OPEN.getStatusSeq());
                            financialChargeDetail.setStatus(MasterDataStatus.OPEN.getStatusSeq());
                            financialChargeDetail.setFinancialChargeSeq(financialCharge.getFinancialChargeSeq());
                            savingList.add(financialChargeDetail);
                        }
                    }

                    //Existing Record Update
                    for (FinancialChargeDetail frontFinancialChargeDetail : frontFinanceChargeDetailList) {
                        for (FinancialChargeDetail dbFinancialChargeDetail : dbFinanceChargeDetailList) {
                            if (dbFinancialChargeDetail.getFinancialChargeDetailSeq().equals(frontFinancialChargeDetail.getFinancialChargeDetailSeq())) {
                                frontFinancialChargeDetail.setLiStatus(dbFinancialChargeDetail.getLiStatus());
                                frontFinancialChargeDetail.setEvStatus(dbFinancialChargeDetail.getEvStatus());
                                frontFinancialChargeDetail.setFinancialChargeSeq(dbFinancialChargeDetail.getFinancialChargeSeq());
                                frontFinancialChargeDetail.setStatus(MasterDataStatus.OPEN.getStatusSeq());
                                savingList.add(frontFinancialChargeDetail);
                                break;
                            }
                        }
                    }

                    //Existing Records Delete
                    for (FinancialChargeDetail dbFinanceChargeDetail : dbFinanceChargeDetailList) {
                        boolean recordExists = false;
                        for (FinancialChargeDetail frontFinanceChargeDetail : frontFinanceChargeDetailList) {
                            if (dbFinanceChargeDetail.getFinancialChargeDetailSeq().equals(frontFinanceChargeDetail.getFinancialChargeDetailSeq())) {
                                recordExists = true;
                                break;
                            }
                        }
                        if (!recordExists) {
                            dbFinanceChargeDetail.setStatus(0);
                            savingList.add(dbFinanceChargeDetail);
                        }

                    }
                    if (savingList.size() > 0) {
                        responseObject.setMessage("Finance Charges Updated Successfully");
                        responseObject.setStatus(true);
                    } else {
                        responseObject.setMessage("Not found any Amendments to Update");
                        responseObject.setStatus(false);
                    }
                    dbFinancialCharge.setCompanyProfileSeq(financialCharge.getCompanyProfileSeq());
                    dbFinancialCharge.setFinancialChargeDetails(savingList);
                    dbFinancialCharge = this.financialChargeService.save(dbFinancialCharge);
                    responseObject.setObject(dbFinancialCharge);

                    Job job = this.jobService.findByModuleSeqAndCompanyProfileSeqAndReferenceSeq(financialCharge.getModuleSeq(), financialCharge.getCompanyProfileSeq(), financialCharge.getReferenceSeq());
                    job.setUpdateFlag(YesOrNo.NO.getYesOrNoSeq());
                    this.jobService.save(job);
                } else {
                    responseObject.setMessage("Not found any Amendments to Update");
                    responseObject.setStatus(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return responseObject;
    }

    @Override
    public ResponseObject deleteAllFinancialCharge(FinancialCharge financialCharge) {
        ResponseObject responseObject = null;
        try {
            FinancialCharge dbFinancialCharge = this.financialChargeService.findOne(financialCharge.getFinancialChargeSeq());
            List<FinancialChargeDetail> financialChargeDetailList = dbFinancialCharge.getFinancialChargeDetails().stream().filter(i -> i.getEvStatus().equals(YesOrNo.Yes.getYesOrNoSeq())
                    || i.getLiStatus().equals(YesOrNo.Yes.getYesOrNoSeq())).collect(Collectors.toList());
            if (financialChargeDetailList.size() == 0) {
                dbFinancialCharge.setStatus(MasterDataStatus.DELETED.getStatusSeq());
                dbFinancialCharge.getFinancialChargeDetails().forEach(i -> i.setStatus(MasterDataStatus.DELETED.getStatusSeq()));
                this.financialChargeService.save(dbFinancialCharge);
                responseObject = new ResponseObject("All Charges have been deleted", true);
                Job job = this.jobService.findByModuleSeqAndCompanyProfileSeqAndReferenceSeq(financialCharge.getModuleSeq(), financialCharge.getCompanyProfileSeq(), financialCharge.getReferenceSeq());
                job.setUpdateFlag(YesOrNo.NO.getYesOrNoSeq());
                this.jobService.save(job);
            } else {
                responseObject = new ResponseObject("Cannot Delete as charges have been affected with a Transaction", false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseObject;
    }

}
