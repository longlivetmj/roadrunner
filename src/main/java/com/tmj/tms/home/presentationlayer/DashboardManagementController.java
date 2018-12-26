package com.tmj.tms.home.presentationlayer;

import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.home.datalayer.service.WidgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/home/dashboardManagement")
public class DashboardManagementController {

    private final WidgetService widgetService;

    @Autowired
    public DashboardManagementController(WidgetService widgetService) {
        this.widgetService = widgetService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_home@dashboardManagement_VIEW')")
    public String getPage(Model model) {
        model.addAttribute("firstRowWidgetList", this.widgetService.findByRowNoAndStatusNotOrderByDisplayOrderAsc(1, MasterDataStatus.DELETED.getStatusSeq()));
        model.addAttribute("secondRowWidgetList", this.widgetService.findByRowNoAndStatusNotOrderByDisplayOrderAsc(2, MasterDataStatus.DELETED.getStatusSeq()));
        model.addAttribute("thirdRowWidgetList", this.widgetService.findByRowNoAndStatusNotOrderByDisplayOrderAsc(3, MasterDataStatus.DELETED.getStatusSeq()));
        return "home/dashboard";
    }
}
