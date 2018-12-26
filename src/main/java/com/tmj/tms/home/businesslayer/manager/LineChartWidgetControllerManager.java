package com.tmj.tms.home.businesslayer.manager;

import com.tmj.tms.home.datalayer.model.auxilary.LineChartWidget;
import org.springframework.stereotype.Component;

@Component
public interface LineChartWidgetControllerManager {

    LineChartWidget transportInvoiceAndExpenseComparison(Integer widgetSeq);
}
