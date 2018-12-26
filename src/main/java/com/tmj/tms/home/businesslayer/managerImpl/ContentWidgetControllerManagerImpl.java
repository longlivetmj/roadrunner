package com.tmj.tms.home.businesslayer.managerImpl;

import com.tmj.tms.config.datalayer.modal.CompanyProfile;
import com.tmj.tms.config.datalayer.modal.Module;
import com.tmj.tms.config.datalayer.service.CompanyProfileService;
import com.tmj.tms.config.datalayer.service.ModuleService;
import com.tmj.tms.finance.utility.ChargeType;
import com.tmj.tms.home.businesslayer.manager.ContentWidgetControllerManager;
import com.tmj.tms.home.datalayer.model.Widget;
import com.tmj.tms.home.datalayer.model.auxilary.ContentWidget;
import com.tmj.tms.home.datalayer.service.TempRevenueAndCostService;
import com.tmj.tms.home.datalayer.service.WidgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class ContentWidgetControllerManagerImpl implements ContentWidgetControllerManager {

    private final TempRevenueAndCostService tempRevenueAndCostService;
    private final WidgetService widgetService;
    private final ModuleService moduleService;
    private final HttpSession httpSession;
    private final CompanyProfileService companyProfileService;

    @Autowired
    public ContentWidgetControllerManagerImpl(TempRevenueAndCostService tempRevenueAndCostService,
                                              WidgetService widgetService,
                                              ModuleService moduleService,
                                              HttpSession httpSession,
                                              CompanyProfileService companyProfileService) {
        this.tempRevenueAndCostService = tempRevenueAndCostService;
        this.widgetService = widgetService;
        this.moduleService = moduleService;
        this.httpSession = httpSession;
        this.companyProfileService = companyProfileService;
    }

    @Override
    public ContentWidget getTransportPendingToInvoice(Integer widgetSeq) {
        ContentWidget contentWidget = this.initContentWidget(widgetSeq);
        Integer companyProfileSeq = Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString());
        Module module = this.moduleService.findByModuleCode("TRANSPORT");
        long pendingToInvoice = this.tempRevenueAndCostService.countByCompanyProfileSeqAndModuleSeqAndChargeTypeAndFinanceDocumentCount(companyProfileSeq,
                module.getModuleSeq(), ChargeType.REVENUE.getChargeType(), 0);
        contentWidget.setMessage(String.valueOf(pendingToInvoice));
        return contentWidget;
    }

    @Override
    public ContentWidget getTransportPendingToEV(Integer widgetSeq) {
        ContentWidget contentWidget = this.initContentWidget(widgetSeq);
        Integer companyProfileSeq = Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString());
        Module module = this.moduleService.findByModuleCode("TRANSPORT");
        CompanyProfile companyProfile = this.companyProfileService.findByCompanyProfileSeq(companyProfileSeq);
        long pendingEV = this.tempRevenueAndCostService.countByCompanyProfileSeqAndModuleSeqAndChargeTypeAndFinanceDocumentCountAndStakeholderSeqNot(companyProfileSeq,
                module.getModuleSeq(),
                ChargeType.COST.getChargeType(), 0, companyProfile.getDefaultTransporterSeq());
        contentWidget.setMessage(String.valueOf(pendingEV));
        return contentWidget;
    }

    @Override
    public ContentWidget initContentWidget(Integer widgetSeq) {
        ContentWidget contentWidget = new ContentWidget();
        Widget widget = this.widgetService.findOne(widgetSeq);
        contentWidget.setTitle(widget.getHeading());
        contentWidget.setSubTitle(widget.getSubHeading());
        contentWidget.setIcon(widget.getIcon1());
        return contentWidget;
    }
}
