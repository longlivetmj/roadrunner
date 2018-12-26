package com.tmj.tms.home.businesslayer.manager;

import com.tmj.tms.home.datalayer.model.auxilary.DonutWidget;
import org.springframework.stereotype.Component;

@Component
public interface DonutWidgetControllerManager {

    DonutWidget transportBookingStatusSummary(Integer widgetSeq);
}
