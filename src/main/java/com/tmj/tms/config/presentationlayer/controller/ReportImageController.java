package com.tmj.tms.config.presentationlayer.controller;

import com.tmj.tms.config.businesslayer.manager.ReportImageControllerManager;
import com.tmj.tms.config.datalayer.modal.ReportImage;
import com.tmj.tms.config.datalayer.service.ReportImageService;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/config/reportImage")
public class ReportImageController {

    private final HttpSession httpSession;
    private final ReportImageControllerManager reportImageControllerManager;
    private final ReportImageService reportImageService;

    @Autowired
    public ReportImageController(HttpSession httpSession,
                                 ReportImageControllerManager reportImageControllerManager,
                                 ReportImageService reportImageService) {
        this.httpSession = httpSession;
        this.reportImageControllerManager = reportImageControllerManager;
        this.reportImageService = reportImageService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_config@reportImage_VIEW')")
    public String getPage(Model model) {
        this.pageLoad(model);
        return "config/reportImage";
    }

    @RequestMapping(value = "/saveReportImage", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_config@reportImage_CREATE')")
    public
    @ResponseBody
    ResponseObject saveReportImage(@RequestPart(value = "data") ReportImage reportImage,
                                   @RequestPart(value = "reportImage", required = false) MultipartFile multipartFile) {
        return this.reportImageControllerManager.saveReportImage(reportImage, multipartFile);
    }

    @RequestMapping(value = "/updateReportImage", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_config@reportImage_CREATE')")
    public
    @ResponseBody
    ResponseObject updateReportImage(@RequestPart(value = "data") ReportImage reportImage,
                                     @RequestPart(value = "reportImage", required = false) MultipartFile multipartFile) {
        return this.reportImageControllerManager.updateReportImage(reportImage, multipartFile);
    }

    private void pageLoad(Model model) {
        Integer companyProfileSeq = Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString());
        model.addAttribute("reportImageList", this.reportImageService.findByCompanyProfileSeq(companyProfileSeq));
    }

}
