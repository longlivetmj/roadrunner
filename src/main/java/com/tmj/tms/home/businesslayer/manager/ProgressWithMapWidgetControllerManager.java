package com.tmj.tms.home.businesslayer.manager;

import com.tmj.tms.home.datalayer.model.auxilary.ProgressWidgetWithLocations;
import org.springframework.stereotype.Component;

@Component
public interface ProgressWithMapWidgetControllerManager {

    ProgressWidgetWithLocations transportCustomerWiseRevenue(Integer widgetSeq);
}
