package com.tmj.tms.home.presentationlayer;

import com.tmj.tms.home.businesslayer.manager.DonutWidgetControllerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/home/donutWidget")
public class DonutWidgetController {

    private final DonutWidgetControllerManager donutWidgetControllerManager;

    @Autowired
    public DonutWidgetController(DonutWidgetControllerManager donutWidgetControllerManager) {
        this.donutWidgetControllerManager = donutWidgetControllerManager;
    }

    @RequestMapping(value = "/transportBookingStatusSummary", method = RequestMethod.GET)
    public String transportBookingStatusSummary(@RequestParam(value = "widgetSeq") Integer widgetSeq, Model model) {
        model.addAttribute("widget", this.donutWidgetControllerManager.transportBookingStatusSummary(widgetSeq));
        return "home/widget/donutWidget";
    }
}
