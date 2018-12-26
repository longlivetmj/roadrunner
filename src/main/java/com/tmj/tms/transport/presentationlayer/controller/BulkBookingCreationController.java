package com.tmj.tms.transport.presentationlayer.controller;

import com.tmj.tms.transport.bussinesslayer.manager.BulkBookingCreationControllerManager;
import com.tmj.tms.transport.datalayer.modal.TransportBulkBooking;
import com.tmj.tms.transport.datalayer.modal.auxiliary.BulkBookingSearch;
import com.tmj.tms.transport.utility.TransportBookingUtils;
import com.tmj.tms.utility.MultipleDateEditor;
import com.tmj.tms.utility.NDaysBefore;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Controller
@RequestMapping("/transport/bulkBookingCreation")
public class BulkBookingCreationController {

    private final BulkBookingCreationControllerManager bulkBookingCreationControllerManager;
    private final TransportBookingUtils transportBookingUtils;

    @Autowired
    public BulkBookingCreationController(BulkBookingCreationControllerManager bulkBookingCreationControllerManager,
                                         TransportBookingUtils transportBookingUtils) {
        this.bulkBookingCreationControllerManager = bulkBookingCreationControllerManager;
        this.transportBookingUtils = transportBookingUtils;
    }

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_transport@bulkBookingCreation_VIEW')")
    public String getPage(Model model) {
        this.pageLoad(model);
        return "transport/bulkBookingCreation";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_transport@bulkBookingCreation_UPLOAD')")
    public
    @ResponseBody
    ResponseObject bulkUpload(@RequestPart(value = "data") TransportBulkBooking transportBulkBooking,
                              @RequestPart(value = "uploadingFile", required = false) MultipartFile multipartFile) {
        return this.bulkBookingCreationControllerManager.upload(transportBulkBooking, multipartFile);
    }

    @RequestMapping(value = "/findData", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_transport@bulkBookingCreation_VIEW')")
    public String findData(@ModelAttribute BulkBookingSearch bulkBookingSearch,
                           Model model) {
        model.addAttribute("bulkBookingData", this.bulkBookingCreationControllerManager.search(bulkBookingSearch));
        return "transport/content/bulkBookingData";
    }

    private void pageLoad(Model model) {
        model = this.transportBookingUtils.loadFormData(model);
        model.addAttribute("booking", this.transportBookingUtils.getDefaultBooking());
        model.addAttribute("startDate", new Date());
        model.addAttribute("endDate", new NDaysBefore().getDateNDaysAfterCurrentDate(5));
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new MultipleDateEditor());
    }
}
