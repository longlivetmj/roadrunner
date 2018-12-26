package com.tmj.tms.home.businesslayer.manager;

import com.tmj.tms.home.datalayer.model.auxilary.SliderWidget;
import org.springframework.stereotype.Component;

@Component
public interface SliderWidgetControllerManager {
    SliderWidget transportStatistics(Integer widgetSeq);
}
