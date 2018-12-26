package com.tmj.tms.home.presentationlayer;

import com.tmj.tms.home.businesslayer.manager.ProgressWithMapWidgetControllerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/home/progressWithMapWidget")
public class ProgressWithMapWidgetController {

    private final ProgressWithMapWidgetControllerManager progressWithMapWidgetControllerManager;

    @Autowired
    public ProgressWithMapWidgetController(ProgressWithMapWidgetControllerManager progressWithMapWidgetControllerManager) {
        this.progressWithMapWidgetControllerManager = progressWithMapWidgetControllerManager;
    }

    @RequestMapping(value = "/transportCustomerWiseRevenue", method = RequestMethod.GET)
    public String transportCustomerWiseRevenue(@RequestParam(value = "widgetSeq") Integer widgetSeq, Model model) {
        model.addAttribute("widget", this.progressWithMapWidgetControllerManager.transportCustomerWiseRevenue(widgetSeq));
        return "home/widget/progressWithMapWidget";
    }
}
