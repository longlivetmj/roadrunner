package com.tmj.tms.home.businesslayer.managerImpl;

import com.tmj.tms.config.datalayer.modal.Module;
import com.tmj.tms.config.datalayer.service.ModuleService;
import com.tmj.tms.home.businesslayer.manager.LineChartWidgetControllerManager;
import com.tmj.tms.home.datalayer.model.Widget;
import com.tmj.tms.home.datalayer.model.auxilary.KeyAndTwoValue;
import com.tmj.tms.home.datalayer.model.auxilary.LineChartWidget;
import com.tmj.tms.home.datalayer.service.TempOtherCostService;
import com.tmj.tms.home.datalayer.service.TempRevenueAndCostService;
import com.tmj.tms.home.datalayer.service.WidgetService;
import com.tmj.tms.utility.ConvertToJSON;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class LineChartWidgetControllerManagerImpl implements LineChartWidgetControllerManager {

    private final HttpSession httpSession;
    private final WidgetService widgetService;
    private final TempRevenueAndCostService tempRevenueAndCostService;
    private final ModuleService moduleService;
    private final TempOtherCostService tempOtherCostService;

    public LineChartWidgetControllerManagerImpl(HttpSession httpSession,
                                                WidgetService widgetService,
                                                TempRevenueAndCostService tempRevenueAndCostService,
                                                ModuleService moduleService,
                                                TempOtherCostService tempOtherCostService) {
        this.httpSession = httpSession;
        this.widgetService = widgetService;
        this.tempRevenueAndCostService = tempRevenueAndCostService;
        this.moduleService = moduleService;
        this.tempOtherCostService = tempOtherCostService;
    }

    @Override
    public LineChartWidget transportInvoiceAndExpenseComparison(Integer widgetSeq) {
        ConvertToJSON convertToJSON = new ConvertToJSON();
        Integer companyProfileSeq = Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString());
        Module module = this.moduleService.findByModuleCode("TRANSPORT");
        LineChartWidget barChartWidget = this.initLineChartWidget(widgetSeq);
        List<Object[]> stringValueList = this.tempRevenueAndCostService.findMonthWiseInvoiceAndExpense(companyProfileSeq, module.getModuleSeq());
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
                if (strings[2].toString().equals(keyAndTwoValue.getKey())) {
//                    keyAndTwoValue.setSecondValue(keyAndTwoValue.getSecondValue() + Double.parseDouble(strings[1].toString()));
                    keyAndTwoValue.setSecondValue( Double.parseDouble(strings[1].toString()));
                    break;
                }
            }
        }
        String data = convertToJSON.convertToBasicJson(keyAndTwoValueList);
        barChartWidget.setData(data);
        return barChartWidget;
    }

    private LineChartWidget initLineChartWidget(Integer widgetSeq) {
        LineChartWidget lineChartWidget = new LineChartWidget();
        Widget widget = this.widgetService.findOne(widgetSeq);
        lineChartWidget.setWidgetSeq(widgetSeq);
        lineChartWidget.setHeading(widget.getHeading());
        lineChartWidget.setSubHeading(widget.getSubHeading());
        lineChartWidget.setxLabels(widget.getWidgetText1());
        lineChartWidget.setxKeys(widget.getWidgetText2());
        lineChartWidget.setyLabels(widget.getWidgetText3());
        lineChartWidget.setyKeys(widget.getWidgetText4());
        return lineChartWidget;
    }
}
