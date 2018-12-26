package com.tmj.tms.master.presentationlayer.controller;

import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.master.businesslayer.manager.LocationManagementControllerManager;
import com.tmj.tms.master.datalayer.modal.Country;
import com.tmj.tms.master.datalayer.modal.Location;
import com.tmj.tms.master.datalayer.service.CountryService;
import com.tmj.tms.master.datalayer.service.LocationService;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityListeners;
import java.util.List;

@Controller
@EntityListeners(AuditingEntityListener.class)
@RequestMapping("/master/locationManagement")
public class LocationManagementController {

    private final LocationManagementControllerManager locationManagementControllerManager;
    private final LocationService locationService;
    private final CountryService countryService;

    @Autowired
    public LocationManagementController(LocationManagementControllerManager locationManagementControllerManager,
                                        LocationService locationService,
                                        CountryService countryService) {
        this.locationManagementControllerManager = locationManagementControllerManager;
        this.locationService = locationService;
        this.countryService = countryService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_master@locationManagement_VIEW')")
    public String getPage(Model model) {
        this.pageLoad(model);
        return "master/locationManagement";
    }

    @RequestMapping(value = "/createLocation", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_master@locationManagement_CREATE')")
    public
    @ResponseBody
    ResponseObject createLocation(@ModelAttribute Location location) {
        return this.locationManagementControllerManager.createLocation(location);
    }

    @RequestMapping(value = "/updateLocation", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_master@locationManagement_UPDATE')")
    public
    @ResponseBody
    ResponseObject updateLocation(@ModelAttribute Location location) {
        return this.locationManagementControllerManager.updateLocation(location);
    }

    @RequestMapping(value = "/searchLocation", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_master@locationManagement_VIEW')")
    public String searchLocation(@RequestParam(value = "locationName", required = false) String locationName,
                                 @RequestParam(value = "countrySeq") Integer countrySeq,
                                 Model model) {
        model.addAttribute("locationList", this.locationManagementControllerManager.searchLocation(locationName, countrySeq));
        return "master/content/locationData";
    }

    @RequestMapping(value = "/deleteByLocationSeq", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_master@locationManagement_DELETE')")
    public
    @ResponseBody
    ResponseObject deleteLocationByLocationSeq(@RequestParam("locationSeq") Integer locationSeq) {
        return this.locationManagementControllerManager.deleteLocation(locationSeq);
    }

    @RequestMapping(value = "/findLocationByLocationSeq/{locationSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_master@locationManagement_VIEW')")
    public
    @ResponseBody
    Location findLocationByLocationSeq(@PathVariable("locationSeq") Integer locationSeq) {
        return this.locationService.findOne(locationSeq);
    }

    @RequestMapping(value = "/findCountry", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_master@locationManagement_VIEW')")
    public
    @ResponseBody
    List<Country> findCountry(@RequestParam("q") String searchParam) {
        return this.countryService.findByCountryNameStartsWithIgnoreCaseAndStatus(searchParam, 2);
    }

    private void pageLoad(Model model) {
        model.addAttribute("statusList", MasterDataStatus.getStatusListForTransactions("ROLE_master@locationManagement_APPROVE"));
    }
}
