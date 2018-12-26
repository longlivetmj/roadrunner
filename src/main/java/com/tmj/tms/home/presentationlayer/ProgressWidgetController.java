package com.tmj.tms.home.presentationlayer;

import com.tmj.tms.home.businesslayer.manager.ProgressWidgetControllerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/home/progressWidget")
public class ProgressWidgetController {

    private final ProgressWidgetControllerManager progressWidgetControllerManager;

    @Autowired
    public ProgressWidgetController(ProgressWidgetControllerManager progressWidgetControllerManager) {
        this.progressWidgetControllerManager = progressWidgetControllerManager;
    }

    @RequestMapping(value = "/transportCustomerWiseJobs", method = RequestMethod.GET)
    public String transportCustomerWiseJobs(@RequestParam(value = "widgetSeq") Integer widgetSeq, Model model) {
        model.addAttribute("widget", this.progressWidgetControllerManager.transportCustomerWiseJobs(widgetSeq));
        return "home/widget/progressWidget";
    }
}