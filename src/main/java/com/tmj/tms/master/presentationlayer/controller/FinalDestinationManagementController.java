package com.tmj.tms.master.presentationlayer.controller;

import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.master.businesslayer.manager.FinalDestinationManagementControllerManager;
import com.tmj.tms.master.datalayer.modal.Country;
import com.tmj.tms.master.datalayer.modal.FinalDestination;
import com.tmj.tms.master.datalayer.modal.Location;
import com.tmj.tms.master.datalayer.service.CountryService;
import com.tmj.tms.master.datalayer.service.FinalDestinationService;
import com.tmj.tms.master.datalayer.service.LocationService;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/master/finalDestinationManagement")
public class FinalDestinationManagementController {

    private final FinalDestinationService finalDestinationService;
    private final LocationService locationService;
    private final CountryService countryService;
    private final FinalDestinationManagementControllerManager finalDestinationManagementControllerManager;

    @Autowired
    public FinalDestinationManagementController(FinalDestinationService finalDestinationService,
                                                LocationService locationService,
                                                CountryService countryService,
                                                FinalDestinationManagementControllerManager finalDestinationManagementControllerManager) {
        this.finalDestinationService = finalDestinationService;
        this.locationService = locationService;
        this.countryService = countryService;
        this.finalDestinationManagementControllerManager = finalDestinationManagementControllerManager;
    }

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_master@finalDestinationManagement_VIEW')")
    public String getPage(Model model) {
        this.pageLoad(model);
        return "master/finalDestinationManagement";
    }

    @RequestMapping(value = "/addDestination", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_master@finalDestinationManagement_CREATE')")
    public
    @ResponseBody
    ResponseObject addDestination(@ModelAttribute FinalDestination finalDestination) {
        return this.finalDestinationManagementControllerManager.saveDestination(finalDestination);
    }

    @RequestMapping(value = "/updateDestination", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_master@finalDestinationManagement_UPDATE')")
    public
    @ResponseBody
    ResponseObject updateDestination(@Valid @ModelAttribute FinalDestination finalDestination) {
        return this.finalDestinationManagementControllerManager.updateDestination(finalDestination);
    }

    @RequestMapping(value = "/searchDestinationData", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_master@finalDestinationManagement_VIEW')")
    public String searchDestinationData(@RequestParam(value = "finalDestinationCode", required = false) String finalDestinationCode,
                                        @RequestParam(value = "countrySeq", required = false) Integer countrySeq,
                                        @RequestParam(value = "city", required = false) String city,
                                        @RequestParam(value = "state", required = false) String state,
                                        @RequestParam(value = "zip", required = false) String zip,
                                        Model model) {
        model.addAttribute("destinationListDB", this.finalDestinationManagementControllerManager.searchDestination(finalDestinationCode, countrySeq, city, state, zip));
        return "master/content/finalDestinationData";
    }

    @RequestMapping(value = "/findByFinalDestinationSeq/{finalDestinationSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_master@finalDestinationManagement_VIEW')")
    public
    @ResponseBody
    FinalDestination findByFinalDestinationSeq(@PathVariable("finalDestinationSeq") Integer finalDestinationSeq) {
        return this.finalDestinationService.findOne(finalDestinationSeq);
    }

    @RequestMapping(value = "/deleteByFinalDestinationSeq", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_master@finalDestinationManagement_DELETE')")
    public
    @ResponseBody
    ResponseObject deleteByFinalDestinationByFinalDestinationSeq(@RequestParam("finalDestinationSeq") Integer finalDestinationSeq) {
        return this.finalDestinationManagementControllerManager.deleteDestination(finalDestinationSeq);
    }

    @RequestMapping(value = "/findLocation", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_master@finalDestinationManagement_VIEW')")
    public
    @ResponseBody
    List<Location> findLocation(@RequestParam("q") String searchParam) {
        return this.locationService.findByLocationNameStartsWithIgnoreCaseAndStatus(searchParam, MasterDataStatus.APPROVED.getStatusSeq());
    }

    @RequestMapping(value = "/findCountry", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_master@finalDestinationManagement_VIEW')")
    public
    @ResponseBody
    List<Country> findCountry(@RequestParam("q") String searchParam) {
        return this.countryService.findByCountryNameStartsWithIgnoreCaseAndStatus(searchParam, MasterDataStatus.APPROVED.getStatusSeq());
    }

    private void pageLoad(Model model) {
        model.addAttribute("statusList", MasterDataStatus.getStatusListForTransactions("ROLE_master@finalDestinationManagement_APPROVE"));
    }
}
