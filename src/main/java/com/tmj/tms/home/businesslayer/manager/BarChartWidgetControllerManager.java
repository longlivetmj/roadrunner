package com.tmj.tms.home.businesslayer.manager;

import com.tmj.tms.home.datalayer.model.auxilary.BarChartWidget;
import org.springframework.stereotype.Component;

@Component
public interface BarChartWidgetControllerManager {
    BarChartWidget transportMonthlyCostAndRevenue(Integer widgetSeq);
}
