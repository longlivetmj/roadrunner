package com.tmj.tms.home.presentationlayer;

import com.tmj.tms.home.businesslayer.manager.LineChartWidgetControllerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/home/lineChartWidget")
public class LineChartWidgetController {

    private final LineChartWidgetControllerManager lineChartWidgetControllerManager;

    @Autowired
    public LineChartWidgetController(LineChartWidgetControllerManager lineChartWidgetControllerManager) {
        this.lineChartWidgetControllerManager = lineChartWidgetControllerManager;
    }

    @RequestMapping(value = "/transportInvoiceAndExpenseComparison", method = RequestMethod.GET)
    public String transportInvoiceAndExpenseComparison(@RequestParam(value = "widgetSeq") Integer widgetSeq, Model model) {
        model.addAttribute("widget", this.lineChartWidgetControllerManager.transportInvoiceAndExpenseComparison(widgetSeq));
        return "home/widget/lineChartWidget";
    }

}
