package com.tmj.tms.home.businesslayer.managerImpl;

import com.tmj.tms.config.datalayer.modal.Module;
import com.tmj.tms.config.datalayer.service.ModuleService;
import com.tmj.tms.home.businesslayer.manager.ProgressWidgetControllerManager;
import com.tmj.tms.home.datalayer.model.Widget;
import com.tmj.tms.home.datalayer.model.auxilary.KeyValuePairCustom;
import com.tmj.tms.home.datalayer.model.auxilary.ProgressWidget;
import com.tmj.tms.home.datalayer.service.TempRevenueAndCostService;
import com.tmj.tms.home.datalayer.service.WidgetService;
import com.tmj.tms.master.datalayer.service.StakeholderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProgressWidgetControllerManagerImpl implements ProgressWidgetControllerManager {

    private final HttpSession httpSession;
    private final WidgetService widgetService;
    private final TempRevenueAndCostService tempRevenueAndCostService;
    private final ModuleService moduleService;
    private final StakeholderService stakeholderService;

    @Autowired
    public ProgressWidgetControllerManagerImpl(HttpSession httpSession,
                                               WidgetService widgetService,
                                               TempRevenueAndCostService tempRevenueAndCostService,
                                               ModuleService moduleService,
                                               StakeholderService stakeholderService) {
        this.httpSession = httpSession;
        this.widgetService = widgetService;
        this.tempRevenueAndCostService = tempRevenueAndCostService;
        this.moduleService = moduleService;
        this.stakeholderService = stakeholderService;
    }

    @Override
    public ProgressWidget transportCustomerWiseJobs(Integer widgetSeq) {
        Integer companyProfileSeq = Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString());
        Module module = this.moduleService.findByModuleCode("TRANSPORT");
        String[] colorList = {"danger", "success", "warning", "info","default"};
        ProgressWidget progressWidget = this.initProgressWidget(widgetSeq);
        List<KeyValuePairCustom> keyValuePairCustomList = new ArrayList<>();
        Integer maxCount = 0;
        List<Object[]> valueList = this.tempRevenueAndCostService.findCustomerWiseJobCount(companyProfileSeq, module.getModuleSeq());
        for (Object[] objects : valueList) {
            Integer count = Integer.parseInt(objects[1].toString());
            if (count > maxCount) {
                maxCount = count;
            }
        }
        progressWidget.setMaxCount(maxCount);
        if (valueList.size() > 4) {
            for (int x = 0; x < 4; x++) {
                keyValuePairCustomList.add(this.populateKeyValuePairCustom(valueList, x, colorList));
            }
            int others = 0;
            for (int x = 4; x < valueList.size(); x++) {
                others = others + Integer.parseInt(valueList.get(x)[1].toString());
            }
            KeyValuePairCustom keyValuePairCustom = new KeyValuePairCustom();
            keyValuePairCustom.setKey("Others");
            keyValuePairCustom.setValue(String.valueOf(others));
            keyValuePairCustom.setColor(colorList[4]);
            keyValuePairCustomList.add(keyValuePairCustom);
        } else {
            for (int x = 0; x < valueList.size(); x++) {
                keyValuePairCustomList.add(this.populateKeyValuePairCustom(valueList, x, colorList));
            }
        }
        progressWidget.setKeyValuePairCustomList(keyValuePairCustomList);
        return progressWidget;
    }

    private KeyValuePairCustom populateKeyValuePairCustom(List<Object[]> valueList, int x, String[] colorList) {
        Integer stakeholderSeq = Integer.parseInt(valueList.get(x)[0].toString());
        KeyValuePairCustom keyValuePairCustom = new KeyValuePairCustom();
        keyValuePairCustom.setKey(this.stakeholderService.findOne(stakeholderSeq).getStakeholderName());
        keyValuePairCustom.setValue(valueList.get(x)[1].toString());
        keyValuePairCustom.setColor(colorList[x]);
        return keyValuePairCustom;
    }

    private ProgressWidget initProgressWidget(Integer widgetSeq) {
        Widget widget = this.widgetService.findOne(widgetSeq);
        ProgressWidget progressWidget = new ProgressWidget();
        progressWidget.setHeading(widget.getHeading());
        progressWidget.setSubHeading(widget.getSubHeading());
        progressWidget.setDescription(widget.getWidgetText1());
        progressWidget.setCount(widget.getWidgetText2());
        progressWidget.setComparison(widget.getWidgetText3());
        return progressWidget;
    }
}
