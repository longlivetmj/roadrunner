package com.tmj.tms.home.businesslayer.managerImpl;

import com.tmj.tms.config.datalayer.modal.Module;
import com.tmj.tms.config.datalayer.service.ModuleService;
import com.tmj.tms.finance.utility.ChargeType;
import com.tmj.tms.home.businesslayer.manager.DonutWidgetControllerManager;
import com.tmj.tms.home.datalayer.model.Widget;
import com.tmj.tms.home.datalayer.model.auxilary.DonutWidget;
import com.tmj.tms.home.datalayer.service.TempRevenueAndCostService;
import com.tmj.tms.home.datalayer.service.WidgetService;
import com.tmj.tms.transport.utility.BookingStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class DonutWidgetControllerManagerImpl implements DonutWidgetControllerManager {

    private final HttpSession httpSession;
    private final WidgetService widgetService;
    private final TempRevenueAndCostService tempRevenueAndCostService;
    private final ModuleService moduleService;

    @Autowired
    public DonutWidgetControllerManagerImpl(HttpSession httpSession,
                                            WidgetService widgetService,
                                            TempRevenueAndCostService tempRevenueAndCostService,
                                            ModuleService moduleService) {
        this.httpSession = httpSession;
        this.widgetService = widgetService;
        this.tempRevenueAndCostService = tempRevenueAndCostService;
        this.moduleService = moduleService;
    }

    @Override
    public DonutWidget transportBookingStatusSummary(Integer widgetSeq) {
        DonutWidget donutWidget = this.initDonutWidget(widgetSeq);
        Integer companyProfileSeq = Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString());
        Module module = this.moduleService.findByModuleCode("TRANSPORT");
        List<Integer> statusList = new ArrayList<>();
        statusList.add(BookingStatus.JOB_COMPLETED.getCurrentStatus());
        statusList.add(BookingStatus.JOB_CLOSED.getCurrentStatus());
        statusList.add(BookingStatus.JOB_CONFIRMED.getCurrentStatus());
        statusList.add(BookingStatus.JOB_DISPUTE.getCurrentStatus());
        statusList.add(BookingStatus.KM_CONFIRMED.getCurrentStatus());
        statusList.add(BookingStatus.FINANCE_CONFIRMED.getCurrentStatus());
        statusList.add(BookingStatus.CHARGES_SUBMITTED.getCurrentStatus());
        statusList.add(BookingStatus.FINANCE_DISPUTE.getCurrentStatus());
        long completed = this.tempRevenueAndCostService.countByCompanyProfileSeqAndModuleSeqAndChargeTypeAndCurrentStatusIn(companyProfileSeq, module.getModuleSeq(), ChargeType.REVENUE.getChargeType(), statusList);

        statusList.clear();
        statusList.add(BookingStatus.ACCEPTED.getCurrentStatus());
        statusList.add(BookingStatus.APPROVED.getCurrentStatus());
        statusList.add(BookingStatus.PENDING_FOR_APPROVAL.getCurrentStatus());
        long pending = this.tempRevenueAndCostService.countByCompanyProfileSeqAndModuleSeqAndChargeTypeAndCurrentStatusIn(companyProfileSeq, module.getModuleSeq(), ChargeType.REVENUE.getChargeType(), statusList);

        statusList.clear();
        statusList.add(BookingStatus.ATTENDED.getCurrentStatus());
        statusList.add(BookingStatus.ARRIVED_AT_PICKUP.getCurrentStatus());
        statusList.add(BookingStatus.DEPARTED_FROM_PICKUP.getCurrentStatus());
        statusList.add(BookingStatus.ARRIVED_AT_DELIVERY.getCurrentStatus());
        long onGoing = this.tempRevenueAndCostService.countByCompanyProfileSeqAndModuleSeqAndChargeTypeAndCurrentStatusIn(companyProfileSeq, module.getModuleSeq(), ChargeType.REVENUE.getChargeType(), statusList);

        String jsonString =
                "{label: 'Completed', value: " + completed + "},\n" +
                        "{label: 'Pending', value: " + pending + "},\n" +
                        "{label: 'On Going', value: " + onGoing + "}";
        donutWidget.setData(jsonString);
        return donutWidget;
    }

    private DonutWidget initDonutWidget(Integer widgetSeq) {
        DonutWidget donutWidget = new DonutWidget();
        Widget widget = this.widgetService.findOne(widgetSeq);
        donutWidget.setHeading(widget.getHeading());
        donutWidget.setSubHeading(widget.getSubHeading());
        return donutWidget;
    }
}
