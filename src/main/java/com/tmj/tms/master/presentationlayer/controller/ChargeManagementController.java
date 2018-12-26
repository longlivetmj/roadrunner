package com.tmj.tms.master.presentationlayer.controller;

import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.master.businesslayer.manager.ChargeManagementControllerManager;
import com.tmj.tms.master.datalayer.modal.Charge;
import com.tmj.tms.master.datalayer.modal.ChargeMode;
import com.tmj.tms.master.datalayer.service.ChargeModeService;
import com.tmj.tms.master.datalayer.service.ChargeService;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping(value = "/master/chargeManagement")
public class ChargeManagementController {

    private final ChargeService chargeService;
    private final ChargeModeService chargeModeService;
    private final ChargeManagementControllerManager chargeManagementControllerManager;
    private final HttpSession httpSession;

    @Autowired
    public ChargeManagementController(ChargeService chargeService,
                                      ChargeModeService chargeModeService,
                                      ChargeManagementControllerManager chargeManagementControllerManager,
                                      HttpSession httpSession) {
        this.chargeService = chargeService;
        this.chargeModeService = chargeModeService;
        this.chargeManagementControllerManager = chargeManagementControllerManager;
        this.httpSession = httpSession;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getPage(Model model) {
        this.pageLoad(model);
        return "master/chargeManagement";
    }

    @RequestMapping(value = "/createCharge", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_master@chargeManagement_CREATE')")
    public
    @ResponseBody
    ResponseObject createCharge(@ModelAttribute Charge charge,
                                @RequestParam(value = "modeSeq[]") List<Integer> modeSeqList) {
        charge.setCompanyProfileSeq(Integer.parseInt(httpSession.getAttribute("companyProfileSeq").toString()));
        return this.chargeManagementControllerManager.createCharge(charge, modeSeqList);
    }

    @RequestMapping(value = "/updateCharge", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_master@chargeManagement_UPDATE')")
    public
    @ResponseBody
    ResponseObject updateCharge(@ModelAttribute Charge charge,
                                @RequestParam(value = "modeSeq[]", required = false) List<Integer> modeSeqList) {
        charge.setCompanyProfileSeq(Integer.parseInt(httpSession.getAttribute("companyProfileSeq").toString()));
        return this.chargeManagementControllerManager.updateCharge(charge, modeSeqList);
    }

    @RequestMapping(value = "/deleteByChargeSeq", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_master@chargeManagement_DELETE')")
    public
    @ResponseBody
    ResponseObject deleteChargeByByChargeSeq(@RequestParam(" chargeSeq") Integer chargeSeq) {
        return this.chargeManagementControllerManager.deleteCharge(chargeSeq);
    }


    @RequestMapping(value = "/searchCharge", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_master@chargeManagement_VIEW')")
    public String searchCharge(@RequestParam(value = "chargeName") String chargeName,
                               @RequestParam(value = "description") String description,
                               Model model) {
        Integer customerProfileSeq = Integer.parseInt(httpSession.getAttribute("companyProfileSeq").toString());
        model.addAttribute("chargeList", this.chargeManagementControllerManager.searchCharge(chargeName, description, customerProfileSeq));
        return "master/content/chargeData";
    }

    @RequestMapping(value = "/findChargeByChargeSeq/{chargeSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_master@chargeManagement_VIEW')")
    public
    @ResponseBody
    Charge findChargeByChargeSeq(@PathVariable("chargeSeq") Integer chargeSeq) {
        return this.chargeService.findOne(chargeSeq);
    }

    @RequestMapping(value = "/findChargeModeByChargeSeq/{chargeSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_master@chargeManagement_VIEW')")
    public
    @ResponseBody
    List<ChargeMode> findChargeModeByChargeSeq(@PathVariable("chargeSeq") Integer chargeSeq) {
        return this.chargeModeService.findByChargeSeq(chargeSeq);
    }

    @RequestMapping(value = "/getChargeDetails/{chargeSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_master@chargeManagement_VIEW')")
    @ResponseBody
    public Charge getChargeDetails(@PathVariable("chargeSeq") Integer chargeSeq) {
        return this.chargeService.findOne(chargeSeq);
    }

    private void pageLoad(Model model) {
        model.addAttribute("modeList", this.chargeManagementControllerManager.getFinanceEnabledCompanyModuleList());
        model.addAttribute("statusList", MasterDataStatus.getStatusListForTransactions("ROLE_master@chargeManagement_APPROVE"));
    }
}
