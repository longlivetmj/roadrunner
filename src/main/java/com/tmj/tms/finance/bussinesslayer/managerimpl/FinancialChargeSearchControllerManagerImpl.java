package com.tmj.tms.finance.bussinesslayer.managerimpl;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphUtils;
import com.querydsl.core.BooleanBuilder;
import com.tmj.tms.config.datalayer.modal.CompanyProfile;
import com.tmj.tms.config.datalayer.modal.Module;
import com.tmj.tms.config.datalayer.service.CompanyProfileService;
import com.tmj.tms.config.datalayer.service.ModuleService;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.finance.bussinesslayer.manager.FinancialChargeSearchControllerManager;
import com.tmj.tms.finance.datalayer.modal.FinancialCharge;
import com.tmj.tms.finance.datalayer.modal.FinancialChargeDetail;
import com.tmj.tms.finance.datalayer.service.FinancialChargeService;
import com.tmj.tms.finance.utility.*;
import com.tmj.tms.transport.datalayer.modal.QTransportBooking;
import com.tmj.tms.transport.datalayer.modal.TransportBooking;
import com.tmj.tms.transport.datalayer.service.TransportBookingService;
import com.tmj.tms.transport.utility.BooleanBuilderDuplicateRemover;
import com.tmj.tms.transport.utility.ViaLocationFormatter;
import com.tmj.tms.utility.DateFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class FinancialChargeSearchControllerManagerImpl implements FinancialChargeSearchControllerManager {

    private final BooleanBuilderDuplicateRemover booleanBuilderDuplicateRemover;
    private final TransportBookingService transportBookingService;
    private final ModuleService moduleService;
    private final FinancialChargeService financialChargeService;
    private final ViaLocationFormatter viaLocationFormatter;
    private final CompanyProfileService companyProfileService;
    private final HttpSession httpSession;

    @Autowired
    public FinancialChargeSearchControllerManagerImpl(BooleanBuilderDuplicateRemover booleanBuilderDuplicateRemover,
                                                      TransportBookingService transportBookingService,
                                                      ModuleService moduleService,
                                                      FinancialChargeService financialChargeService,
                                                      ViaLocationFormatter viaLocationFormatter,
                                                      CompanyProfileService companyProfileService,
                                                      HttpSession httpSession) {
        this.booleanBuilderDuplicateRemover = booleanBuilderDuplicateRemover;
        this.transportBookingService = transportBookingService;
        this.moduleService = moduleService;
        this.financialChargeService = financialChargeService;
        this.viaLocationFormatter = viaLocationFormatter;
        this.companyProfileService = companyProfileService;
        this.httpSession = httpSession;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TransportBooking> searchBooking(FinancialChargeSearch financialChargeSearch) {
        List<TransportBooking> transportBookingList = null;
        try {
            QTransportBooking transportBooking = QTransportBooking.transportBooking;
            BooleanBuilder builder = new BooleanBuilder();
            builder = this.booleanBuilderDuplicateRemover.transportBooking(builder, transportBooking, financialChargeSearch.getTransportBookingSeq());
            Module transport = this.moduleService.findByModuleCode("TRANSPORT");
            if (financialChargeSearch.getModuleSeq().equals(transport.getModuleSeq())) {
                if (financialChargeSearch.getTransportBookingSeq() == null) {
                    if (financialChargeSearch.getCustomerSeq() != null) {
                        builder.and(transportBooking.customerSeq.eq(financialChargeSearch.getCustomerSeq()));
                    }
                    if (!financialChargeSearch.getCurrentStatus().equals(-1)) {
                        builder.and(transportBooking.currentStatus.eq(financialChargeSearch.getCurrentStatus()));
                    }
                    if (financialChargeSearch.getTransportCompanySeq() != null) {
                        builder.and(transportBooking.transportBookingVehicleList.get(0).transportCompanySeq.eq(financialChargeSearch.getTransportCompanySeq()));
                    }
                    if (financialChargeSearch.getVehicleSeq() != null) {
                        builder.and(transportBooking.transportBookingVehicleList.get(0).vehicleSeq.eq(financialChargeSearch.getVehicleSeq()));
                    }
                    if (financialChargeSearch.getVehicleTypeSeq() != null) {
                        builder.and(transportBooking.vehicleTypeSeq.eq(financialChargeSearch.getVehicleTypeSeq()));
                    }
                    if (financialChargeSearch.getJobNo() != null) {
                        builder.and(transportBooking.job.jobNo.containsIgnoreCase(financialChargeSearch.getJobNo()));
                    }

                    if (financialChargeSearch.getFinanceStatus().equals(FinanceStatus.INVOICED.getFinanceStatus())) {
                        builder.and(transportBooking.localInvoiceList.isNotEmpty());
                    }
                    if (financialChargeSearch.getFinanceStatus().equals(FinanceStatus.PENDING_TO_INVOICE.getFinanceStatus())) {
                        builder.and(transportBooking.localInvoiceList.isEmpty());
                    }
                    if (financialChargeSearch.getFinanceStatus().equals(FinanceStatus.PENDING_TO_EV.getFinanceStatus())) {
                        Integer companyProfileSeq = Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString());
                        CompanyProfile companyProfile = this.companyProfileService.findByCompanyProfileSeq(companyProfileSeq);
                        builder.and(transportBooking.transportBookingVehicleList.any().transportCompanySeq.ne(companyProfile.getDefaultTransporterSeq()));
                        builder.and(transportBooking.expenseVoucherList.isEmpty());
                    }
                    if (financialChargeSearch.getCustomerRefNo() != null && financialChargeSearch.getCustomerRefNo().trim().length() > 0) {
                        builder.and(transportBooking.customerReferenceNo.containsIgnoreCase(financialChargeSearch.getCustomerRefNo()));
                    }
                    if (financialChargeSearch.getStartDate() != null && financialChargeSearch.getEndDate() != null) {
                        builder = this.booleanBuilderDuplicateRemover.dateFilterType(builder, transportBooking, financialChargeSearch.getDateFilterType(),
                                financialChargeSearch.getStartDate(), DateFormatter.getEndOfTheDay(financialChargeSearch.getEndDate()));
                    }
                    transportBookingList = (List<TransportBooking>) this.transportBookingService.findAll(builder, EntityGraphUtils.fromName("TransportBooking.default"));
                } else {
                    transportBookingList = (List<TransportBooking>) this.transportBookingService.findAll(builder, EntityGraphUtils.fromName("TransportBooking.default"));
                }
                for (TransportBooking booking : transportBookingList) {
                    FinancialCharge financialCharge = this.financialChargeService.findByTargetTypeAndReferenceTypeAndReferenceSeqAndModuleSeqAndStatusNot(TargetType.TRANSPORT_JOB.getTargetType(),
                            ReferenceType.TRANSPORT_BOOKING.getReferenceType(), booking.getTransportBookingSeq(), booking.getModuleSeq(), MasterDataStatus.DELETED.getStatusSeq());
                    if (financialCharge != null) {
                        List<FinancialChargeDetail> financialChargeDetailList = financialCharge.getFinancialChargeDetails();
                        booking.setRevenue(financialChargeDetailList.stream().filter(x -> x.getChargeType().equals(ChargeType.REVENUE.getChargeType())).map(FinancialChargeDetail::getAmount).mapToDouble(Double::doubleValue).sum());
                        booking.setCost(financialChargeDetailList.stream().filter(x -> x.getChargeType().equals(ChargeType.COST.getChargeType())).map(FinancialChargeDetail::getAmount).mapToDouble(Double::doubleValue).sum());
                        booking.setViaLocationString(this.viaLocationFormatter.getViaLocationAsString(booking.getTransportBookingViaLocationList()));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transportBookingList;
    }
}
