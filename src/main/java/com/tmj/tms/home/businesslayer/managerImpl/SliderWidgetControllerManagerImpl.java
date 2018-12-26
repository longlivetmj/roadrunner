package com.tmj.tms.home.businesslayer.managerImpl;

import com.tmj.tms.config.datalayer.modal.Module;
import com.tmj.tms.config.datalayer.service.ModuleService;
import com.tmj.tms.finance.utility.ChargeType;
import com.tmj.tms.home.businesslayer.manager.SliderWidgetControllerManager;
import com.tmj.tms.home.datalayer.model.Widget;
import com.tmj.tms.home.datalayer.model.auxilary.ContentWidget;
import com.tmj.tms.home.datalayer.model.auxilary.SliderWidget;
import com.tmj.tms.home.datalayer.service.TempRevenueAndCostService;
import com.tmj.tms.home.datalayer.service.WidgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class SliderWidgetControllerManagerImpl implements SliderWidgetControllerManager {

    private final HttpSession httpSession;
    private final ModuleService moduleService;
    private final WidgetService widgetService;
    private final TempRevenueAndCostService tempRevenueAndCostService;

    @Autowired
    public SliderWidgetControllerManagerImpl(HttpSession httpSession,
                                             ModuleService moduleService,
                                             WidgetService widgetService,
                                             TempRevenueAndCostService tempRevenueAndCostService) {
        this.httpSession = httpSession;
        this.moduleService = moduleService;
        this.widgetService = widgetService;
        this.tempRevenueAndCostService = tempRevenueAndCostService;
    }

    @Override
    public SliderWidget transportStatistics(Integer widgetSeq) {
        Module module = this.moduleService.findByModuleCode("TRANSPORT");
        Integer companyProfileSeq = Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString());
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        SliderWidget sliderWidget = new SliderWidget();
        List<ContentWidget> contentWidgetList = new ArrayList<>();
        Widget widget = this.widgetService.findOne(widgetSeq);
        //Local Invoices
        ContentWidget localInvoice = new ContentWidget();
        localInvoice.setIcon(widget.getIcon1());
        localInvoice.setTitle(widget.getWidgetText1());
        localInvoice.setSubTitle(widget.getWidgetText2());
        Double invoiceCount = this.tempRevenueAndCostService.findFinancialDocumentSum(companyProfileSeq, calendar.getTime(), new Date(), module.getModuleSeq(), ChargeType.REVENUE.getChargeType());
        if (invoiceCount != null) {
            localInvoice.setMessage(String.valueOf(invoiceCount.intValue()));
        } else {
            localInvoice.setMessage("N/A");
        }
        contentWidgetList.add(localInvoice);
        //Local Invoices

        //Expense Vouchers
        ContentWidget expenseVouchers = new ContentWidget();
        expenseVouchers.setIcon(widget.getIcon2());
        expenseVouchers.setTitle(widget.getWidgetText3());
        expenseVouchers.setSubTitle(widget.getWidgetText4());
        Double expenseCount = this.tempRevenueAndCostService.findFinancialDocumentSum(companyProfileSeq, calendar.getTime(), new Date(), module.getModuleSeq(), ChargeType.COST.getChargeType());
        if (expenseCount != null) {
            expenseVouchers.setMessage(String.valueOf(expenseCount.intValue()));
        } else {
            expenseVouchers.setMessage("N/A");
        }
        contentWidgetList.add(expenseVouchers);
        //Expense Vouchers

        //Total Jobs
        ContentWidget totalJobs = new ContentWidget();
        totalJobs.setIcon(widget.getIcon3());
        totalJobs.setTitle(widget.getWidgetText5());
        totalJobs.setSubTitle(widget.getWidgetText6());
        Long totalJobCount = this.tempRevenueAndCostService.countByCompanyProfileSeqAndModuleSeqAndChargeTypeAndTransactionDateBetween(companyProfileSeq, module.getModuleSeq(), ChargeType.REVENUE.getChargeType(), calendar.getTime(), new Date());
        if (totalJobCount != null) {
            totalJobs.setMessage(String.valueOf(totalJobCount));
        } else {
            totalJobs.setMessage("N/A");
        }
        contentWidgetList.add(totalJobs);
        //Local Invoices

        sliderWidget.setContentWidgetList(contentWidgetList);
        return sliderWidget;
    }

}
