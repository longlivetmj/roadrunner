package com.tmj.tms.home.businesslayer.manager;

import com.tmj.tms.home.datalayer.model.auxilary.ContentWidget;
import org.springframework.stereotype.Component;

@Component
public interface ContentWidgetControllerManager {

    ContentWidget getTransportPendingToInvoice(Integer widgetSeq);

    ContentWidget getTransportPendingToEV(Integer widgetSeq);

    ContentWidget initContentWidget(Integer widgetSeq);
}
