package com.tmj.tms.finance.presentationlayer.controller;

import com.tmj.tms.config.datalayer.modal.CompanyProfile;
import com.tmj.tms.config.datalayer.service.CompanyProfileService;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.finance.bussinesslayer.manager.RateMasterManagementControllerManager;
import com.tmj.tms.finance.datalayer.modal.RateMaster;
import com.tmj.tms.finance.datalayer.modal.RateMasterDetail;
import com.tmj.tms.finance.datalayer.service.RateMasterService;
import com.tmj.tms.finance.utility.ChargeType;
import com.tmj.tms.finance.utility.MultiplyType;
import com.tmj.tms.finance.utility.RateType;
import com.tmj.tms.master.datalayer.modal.Charge;
import com.tmj.tms.master.datalayer.modal.FinalDestination;
import com.tmj.tms.master.datalayer.modal.Stakeholder;
import com.tmj.tms.master.datalayer.service.*;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/finance/rateMasterManagement")
public class RateMasterManagementController {

    private final ChargeService chargeService;
    private final CurrencyService currencyService;
    private final StakeholderService stakeholderService;
    private final HttpSession httpSession;
    private final RateMasterService rateMasterService;
    private final CompanyProfileService companyProfileService;
    private final StakeholderTypeService stakeholderTypeService;
    private final VehicleTypeService vehicleTypeService;
    private final FinalDestinationService finalDestinationService;
    private final RateMasterManagementControllerManager rateMasterManagementControllerManager;

    @Autowired
    public RateMasterManagementController(ChargeService chargeService,
                                          CurrencyService currencyService,
                                          StakeholderService stakeholderService,
                                          HttpSession httpSession,
                                          RateMasterService rateMasterService,
                                          CompanyProfileService companyProfileService,
                                          StakeholderTypeService stakeholderTypeService,
                                          VehicleTypeService vehicleTypeService,
                                          FinalDestinationService finalDestinationService,
                                          RateMasterManagementControllerManager rateMasterManagementControllerManager) {
        this.chargeService = chargeService;
        this.currencyService = currencyService;
        this.stakeholderService = stakeholderService;
        this.httpSession = httpSession;
        this.rateMasterService = rateMasterService;
        this.companyProfileService = companyProfileService;
        this.stakeholderTypeService = stakeholderTypeService;
        this.vehicleTypeService = vehicleTypeService;
        this.finalDestinationService = finalDestinationService;
        this.rateMasterManagementControllerManager = rateMasterManagementControllerManager;
    }

    @RequestMapping(params = "stakeholderSeq", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_finance@rateMasterManagement_VIEW')")
    public String getPage(@RequestParam("stakeholderSeq") Integer stakeholderSeq,
                          Model model) {
        this.pageLoad(model, stakeholderSeq);
        return "finance/rateMasterManagement";
    }

    @RequestMapping(value = "/saveRateMaster", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_finance@rateMasterManagement_CREATE')")
    public
    @ResponseBody
    ResponseObject saveRateMaster(@ModelAttribute RateMaster rateMaster, HttpServletRequest httpServletRequest) {
        return this.rateMasterManagementControllerManager.saveRateMaster(rateMaster,httpServletRequest);
    }

    @RequestMapping(value = "/updateRateMaster", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_finance@rateMasterManagement_UPDATE')")
    public
    @ResponseBody
    ResponseObject updateRateMaster(@ModelAttribute RateMaster rateMaster, HttpServletRequest httpServletRequest) {
        return this.rateMasterManagementControllerManager.updateRateMaster(rateMaster,httpServletRequest);
    }

    @RequestMapping(value = "/findCharge", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_finance@rateMasterManagement_VIEW')")
    public
    @ResponseBody
    List<Charge> findCharge(@RequestParam("q") String searchParam) {
        return this.chargeService.findByChargeNameStartsWithIgnoreCaseAndStatus(searchParam, MasterDataStatus.APPROVED.getStatusSeq());
    }

    @RequestMapping(value = "/findShipper", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_finance@rateMasterManagement_VIEW')")
    public
    @ResponseBody
    List<Stakeholder> findShipper(@RequestParam("q") String searchParam) {
        return this.stakeholderService.getStakeholderDetails(searchParam,
                this.stakeholderTypeService.findByStakeholderTypeCodeAndStatus("SHIPPER", MasterDataStatus.APPROVED.getStatusSeq()).getStakeholderTypeSeq(),
                Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString()),
                MasterDataStatus.APPROVED.getStatusSeq());
    }

    @RequestMapping(value = "/findFinalDestination", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_finance@rateMasterManagement_VIEW')")
    public
    @ResponseBody
    List<FinalDestination> findFinalDestination(@RequestParam("q") String searchParam) {
        Integer companyProfileSeq = Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString());
        return this.finalDestinationService.findByDestinationContainingIgnoreCaseAndStatusAndCompanyProfileSeq(searchParam,
                MasterDataStatus.APPROVED.getStatusSeq(),
                companyProfileSeq);
    }

    public Model pageLoad(Model model, Integer stakeholderSeq) {
        model.addAttribute("chargeTypeList", ChargeType.values());
        model.addAttribute("multiplyTypeList", MultiplyType.values());
        model.addAttribute("vehicleTypeList", this.vehicleTypeService.findByStatus(MasterDataStatus.APPROVED.getStatusSeq()));
        model.addAttribute("rateTypeList", RateType.values());
        model.addAttribute("stakeholder", this.stakeholderService.findOne(stakeholderSeq));
        String[] statusList = {"ROLE_finance@rateMasterManagement_APPROVE"};
        model.addAttribute("statusList", MasterDataStatus.getStatusListForTransaction(Arrays.asList(statusList)));
        List<RateMaster> rateMasterList = new ArrayList<>();
        Integer companyProfileSeq = Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString());
        RateMaster rateMaster = new RateMaster();
        rateMaster.setCompanyProfileSeq(companyProfileSeq);
        rateMaster.setStakeholderSeq(stakeholderSeq);
        rateMaster.setStakeholder(this.stakeholderService.findOne(stakeholderSeq));
        CompanyProfile companyProfile = this.companyProfileService.findByCompanyProfileSeq(companyProfileSeq);
        rateMaster.setCurrencySeq(companyProfile.getLocalCurrencySeq());
        rateMaster.setCurrency(this.currencyService.findOne(companyProfile.getLocalCurrencySeq()));
        List<RateMasterDetail> rateMasterDetailList = new ArrayList<>();
        RateMasterDetail rateMasterDetail = new RateMasterDetail();
        rateMasterDetailList.add(rateMasterDetail);
        rateMaster.setRateMasterDetailList(rateMasterDetailList);
        rateMasterList.add(rateMaster);
        rateMasterList.addAll(this.rateMasterService.findByStakeholderSeqAndStatus(stakeholderSeq, MasterDataStatus.APPROVED.getStatusSeq()));
        model.addAttribute("rateMasterList", rateMasterList);
        return model;
    }

}
