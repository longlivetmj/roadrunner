package com.tmj.tms.home.presentationlayer;

import com.tmj.tms.home.businesslayer.manager.ContentWidgetControllerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/home/contentWidget")
public class ContentWidgetController {

    private final ContentWidgetControllerManager contentWidgetControllerManager;

    @Autowired
    public ContentWidgetController(ContentWidgetControllerManager contentWidgetControllerManager) {
        this.contentWidgetControllerManager = contentWidgetControllerManager;
    }

    @RequestMapping(value = "/transportPendingToInvoice", method = RequestMethod.GET)
    public String transportPendingToInvoice(@RequestParam(value = "widgetSeq") Integer widgetSeq, Model model) {
        model.addAttribute("widget", this.contentWidgetControllerManager.getTransportPendingToInvoice(widgetSeq));
        return "home/widget/contentWidget";
    }

    @RequestMapping(value = "/transportPendingToEV", method = RequestMethod.GET)
    public String transportPendingToEV(@RequestParam(value = "widgetSeq") Integer widgetSeq, Model model) {
        model.addAttribute("widget", this.contentWidgetControllerManager.getTransportPendingToEV(widgetSeq));
        return "home/widget/contentWidget";
    }

}
