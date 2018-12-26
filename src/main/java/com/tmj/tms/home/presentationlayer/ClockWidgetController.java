package com.tmj.tms.home.presentationlayer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/home/clockWidget")
public class ClockWidgetController {

    @RequestMapping(value = "/clockWidget", method = RequestMethod.GET)
    public String getPage() {
        return "home/widget/clockWidget";
    }
}
