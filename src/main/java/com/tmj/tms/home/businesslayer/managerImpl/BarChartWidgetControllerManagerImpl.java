package com.tmj.tms.home.businesslayer.managerImpl;

import com.tmj.tms.config.datalayer.modal.Module;
import com.tmj.tms.config.datalayer.service.ModuleService;
import com.tmj.tms.home.businesslayer.manager.BarChartWidgetControllerManager;
import com.tmj.tms.home.datalayer.model.Widget;
import com.tmj.tms.home.datalayer.model.auxilary.BarChartWidget;
import com.tmj.tms.home.datalayer.model.auxilary.KeyAndTwoValue;
import com.tmj.tms.home.datalayer.service.TempOtherCostService;
import com.tmj.tms.home.datalayer.service.TempRevenueAndCostService;
import com.tmj.tms.home.datalayer.service.WidgetService;
import com.tmj.tms.utility.ConvertToJSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class BarChartWidgetControllerManagerImpl implements BarChartWidgetControllerManager {

    private final HttpSession httpSession;
    private final ModuleService moduleService;
    private final TempRevenueAndCostService tempRevenueAndCostService;
    private final WidgetService widgetService;
    private final TempOtherCostService tempOtherCostService;

    @Autowired
    public BarChartWidgetControllerManagerImpl(HttpSession httpSession,
                                               ModuleService moduleService,
                                               TempRevenueAndCostService tempRevenueAndCostService,
                                               WidgetService widgetService,
                                               TempOtherCostService tempOtherCostService) {
        this.httpSession = httpSession;
        this.moduleService = moduleService;
        this.tempRevenueAndCostService = tempRevenueAndCostService;
        this.widgetService = widgetService;
        this.tempOtherCostService = tempOtherCostService;
    }

    @Override
    public BarChartWidget transportMonthlyCostAndRevenue(Integer widgetSeq) {
        ConvertToJSON convertToJSON = new ConvertToJSON();
        Integer companyProfileSeq = Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString());
        Module module = this.moduleService.findByModuleCode("TRANSPORT");
        BarChartWidget barChartWidget = this.initBarChartWidget(widgetSeq);
        List<Object[]> stringValueList = this.tempRevenueAndCostService.findMonthWiseRevenueAndCost(companyProfileSeq, module.getModuleSeq());
        List<Object[]> otherCostList = this.tempOtherCostService.findMonthWiseRevenueAndCost(companyProfileSeq);
        List<KeyAndTwoValue> keyAndTwoValueList = new ArrayList<>();
        for (Object[] strings : stringValueList) {
            KeyAndTwoValue keyAndTwoValue = new KeyAndTwoValue(strings[0].toString(),
                    Double.parseDouble(strings[1].toString()),
                    Double.parseDouble(strings[2].toString()));
            keyAndTwoValueList.add(keyAndTwoValue);
        }
        for (Object[] strings : otherCostList) {
            for (KeyAndTwoValue keyAndTwoValue : keyAndTwoValueList) {
                if (strings[0].toString().equals(keyAndTwoValue.getKey())) {
//                    keyAndTwoValue.setSecondValue(keyAndTwoValue.getSecondValue() + Double.parseDouble(strings[1].toString()));
                    keyAndTwoValue.setSecondValue(Double.parseDouble(strings[1].toString()));
                    break;
                }
            }
        }
        String data = convertToJSON.convertToBasicJson(keyAndTwoValueList);
        barChartWidget.setData(data);
        return barChartWidget;
    }

    private BarChartWidget initBarChartWidget(Integer widgetSeq) {
        BarChartWidget barChartWidget = new BarChartWidget();
        Widget widget = this.widgetService.findOne(widgetSeq);
        barChartWidget.setWidgetSeq(widgetSeq);
        barChartWidget.setHeading(widget.getHeading());
        barChartWidget.setSubHeading(widget.getSubHeading());
        barChartWidget.setxLabels(widget.getWidgetText1());
        barChartWidget.setxKeys(widget.getWidgetText2());
        barChartWidget.setyLabels(widget.getWidgetText3());
        barChartWidget.setyKeys(widget.getWidgetText4());
        return barChartWidget;
    }
}
