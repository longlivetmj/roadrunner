package com.tmj.tms.home.presentationlayer;

import com.tmj.tms.home.businesslayer.manager.BarChartWidgetControllerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/home/barChartWidget")
public class BarChartWidgetController {

    private final BarChartWidgetControllerManager barChartWidgetControllerManager;

    @Autowired
    public BarChartWidgetController(BarChartWidgetControllerManager barChartWidgetControllerManager) {
        this.barChartWidgetControllerManager = barChartWidgetControllerManager;
    }

    @RequestMapping(value = "/transportMonthlyCostAndRevenue", method = RequestMethod.GET)
    public String transportMonthlyCostAndRevenue(@RequestParam(value = "widgetSeq") Integer widgetSeq, Model model) {
        model.addAttribute("widget", this.barChartWidgetControllerManager.transportMonthlyCostAndRevenue(widgetSeq));
        return "home/widget/barChartWidget";
    }
}
