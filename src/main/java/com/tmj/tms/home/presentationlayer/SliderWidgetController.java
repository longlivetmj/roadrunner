package com.tmj.tms.home.presentationlayer;

import com.tmj.tms.home.businesslayer.manager.SliderWidgetControllerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@RequestMapping("/home/sliderWidget")
public class SliderWidgetController {

    private final SliderWidgetControllerManager sliderWidgetControllerManager;

    @Autowired
    public SliderWidgetController(SliderWidgetControllerManager sliderWidgetControllerManager) {
        this.sliderWidgetControllerManager = sliderWidgetControllerManager;
    }

    @RequestMapping(value = "/transportStatistics", method = RequestMethod.GET)
    public String transportStatistics(@RequestParam(value = "widgetSeq") Integer widgetSeq, Model model) {
        model.addAttribute("widget", this.sliderWidgetControllerManager.transportStatistics(widgetSeq));
        return "home/widget/sliderWidget";
    }
}
