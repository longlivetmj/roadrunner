package com.tmj.tms.home.businesslayer.manager;

import com.tmj.tms.home.datalayer.model.auxilary.ProgressWidget;
import org.springframework.stereotype.Component;

@Component
public interface ProgressWidgetControllerManager {

    ProgressWidget transportCustomerWiseJobs(Integer widgetSeq);
}
