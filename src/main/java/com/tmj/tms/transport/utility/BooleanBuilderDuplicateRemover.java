package com.tmj.tms.transport.utility;

import com.querydsl.core.BooleanBuilder;
import com.tmj.tms.config.datalayer.modal.Department;
import com.tmj.tms.config.datalayer.modal.Module;
import com.tmj.tms.config.datalayer.service.ModuleService;
import com.tmj.tms.finance.datalayer.modal.QBulkInvoice;
import com.tmj.tms.finance.datalayer.modal.QExpenseVoucher;
import com.tmj.tms.finance.datalayer.modal.QLocalInvoice;
import com.tmj.tms.transport.datalayer.modal.QTransportBooking;
import com.tmj.tms.utility.YesOrNo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BooleanBuilderDuplicateRemover {

    private final HttpSession httpSession;
    private final ModuleService moduleService;

    @Autowired
    public BooleanBuilderDuplicateRemover(HttpSession httpSession,
                                          ModuleService moduleService) {
        this.httpSession = httpSession;
        this.moduleService = moduleService;
    }

    @SuppressWarnings("unchecked")
    public BooleanBuilder transportBooking(BooleanBuilder booleanBuilder, QTransportBooking transportBooking, Integer transportBookingSeq) {
        Integer moduleSeq = this.moduleService.findByModuleCode("TRANSPORT").getModuleSeq();
        Integer companyProfileSeq = Integer.parseInt(httpSession.getAttribute("companyProfileSeq").toString());
        booleanBuilder.and(transportBooking.companyProfileSeq.eq(companyProfileSeq));
        if (transportBookingSeq != null) {
            booleanBuilder.and(transportBooking.transportBookingSeq.eq(transportBookingSeq));
        } else {
            List<Department> departmentList = (List<Department>) this.httpSession.getAttribute("userDepartmentList" + moduleSeq);
            List<Integer> departmentSeqList = new ArrayList<>();
            for (Department department : departmentList) {
                departmentSeqList.add(department.getDepartmentSeq());
            }
            booleanBuilder.and(transportBooking.departmentSeq.in(departmentSeqList));
        }
        return booleanBuilder;
    }

    public BooleanBuilder dateFilterType(BooleanBuilder builder, QTransportBooking transportBooking, Integer dateFilterType, Date start, Date end) {
        if (dateFilterType.equals(DateFilterType.CREATED_DATE.getDateFilterType())) {
            builder.and(transportBooking.createdDate.between(start, end));
        } else if (dateFilterType.equals(DateFilterType.ARRIVING_DATE.getDateFilterType())) {
            builder.and(transportBooking.requestedArrivalTime.between(start, end));
        } else if (dateFilterType.equals(DateFilterType.DELIVERING_DATE.getDateFilterType())) {
            builder.and(transportBooking.requestedDeliveryTime.between(start, end));
        } else if (dateFilterType.equals(DateFilterType.DELIVERED_DATE.getDateFilterType())) {
            builder.and(transportBooking.transportBookingFeedback.departedFromDelivery.between(start, end));
        } else if (dateFilterType.equals(DateFilterType.ARRIVED_DATE.getDateFilterType())) {
            builder.and(transportBooking.transportBookingFeedback.arrivedAtPickup.between(start, end));
        } else if (dateFilterType.equals(DateFilterType.DOCUMENT_COLLECTED_DATE.getDateFilterType())) {
            builder.and(transportBooking.transportBookingFeedback.documentsCollectedDate.between(start, end));
        }
        return builder;
    }

    public BooleanBuilder financialDocumentSearch(BooleanBuilder builder, Integer moduleSeq, Integer departmentSeq, Integer companyProfileSeq,
                                                  Principal principal, QLocalInvoice localInvoice, QExpenseVoucher expenseVoucher, QBulkInvoice bulkInvoice) {
        if (moduleSeq != null) {
            if (localInvoice != null) {
                builder.and(localInvoice.moduleSeq.eq(moduleSeq));
            } else if (bulkInvoice != null) {
                builder.and(bulkInvoice.moduleSeq.eq(moduleSeq));
            } else {
                builder.and(expenseVoucher.moduleSeq.eq(moduleSeq));
            }
            if (departmentSeq != null) {
                if (localInvoice != null) {
                    builder.and(localInvoice.departmentSeq.eq(departmentSeq));
                } else if (bulkInvoice != null) {
                    builder.and(bulkInvoice.departmentSeq.eq(departmentSeq));
                } else {
                    builder.and(expenseVoucher.departmentSeq.eq(departmentSeq));
                }
            } else {
                @SuppressWarnings("unchecked") List<Department> userDepartmentList = (List<Department>) this.httpSession.getAttribute("userDepartmentList" + moduleSeq);
                List<Integer> userDepartmentSeqList = userDepartmentList.stream().map(Department::getDepartmentSeq).collect(Collectors.toList());
                if (localInvoice != null) {
                    builder.and(localInvoice.departmentSeq.in(userDepartmentSeqList));
                } else if (bulkInvoice != null) {
                    builder.and(bulkInvoice.departmentSeq.in(userDepartmentSeqList));
                } else {
                    builder.and(expenseVoucher.departmentSeq.in(userDepartmentSeqList));
                }
            }
        } else {
            List<Module> moduleList = this.moduleService.getModuleListByCompanySeqAndUsernameAndFinanceEnabled(companyProfileSeq, principal.getName(), YesOrNo.Yes.getYesOrNoSeq());
            List<Integer> moduleSeqList = moduleList.stream().map(Module::getModuleSeq).collect(Collectors.toList());
            if (localInvoice != null) {
                builder.and(localInvoice.moduleSeq.in(moduleSeqList));
            } else if (bulkInvoice != null) {
                builder.and(bulkInvoice.moduleSeq.in(moduleSeqList));
            } else {
                builder.and(expenseVoucher.moduleSeq.in(moduleSeqList));
            }
            List<Integer> departmentSeqList = new ArrayList<>();
            for (Integer userModuleSeq : moduleSeqList) {
                @SuppressWarnings("unchecked") List<Department> userDepartmentList = (List<Department>) this.httpSession.getAttribute("userDepartmentList" + userModuleSeq);
                if (userDepartmentList != null && userDepartmentList.size() > 0) {
                    List<Integer> userDepartmentSeqList = userDepartmentList.stream().map(Department::getDepartmentSeq).collect(Collectors.toList());
                    departmentSeqList.addAll(userDepartmentSeqList);
                }
            }
            if (localInvoice != null) {
                builder.and(localInvoice.departmentSeq.in(departmentSeqList));
            } else if (bulkInvoice != null) {
                builder.and(bulkInvoice.departmentSeq.in(departmentSeqList));
            } else {
                builder.and(expenseVoucher.departmentSeq.in(departmentSeqList));
            }
        }
        return builder;
    }
}
