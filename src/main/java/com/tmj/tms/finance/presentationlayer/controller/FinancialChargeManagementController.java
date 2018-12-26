package com.tmj.tms.finance.presentationlayer.controller;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphUtils;
import com.tmj.tms.config.datalayer.service.ModuleService;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.finance.bussinesslayer.manager.FinancialChargeManagementControllerManager;
import com.tmj.tms.finance.datalayer.modal.FinancialCharge;
import com.tmj.tms.finance.datalayer.modal.FinancialChargeDetail;
import com.tmj.tms.finance.datalayer.service.FinancialChargeService;
import com.tmj.tms.finance.utility.ChargeType;
import com.tmj.tms.finance.utility.ReferenceType;
import com.tmj.tms.finance.utility.TargetType;
import com.tmj.tms.master.datalayer.modal.Charge;
import com.tmj.tms.master.datalayer.modal.Currency;
import com.tmj.tms.master.datalayer.modal.Unit;
import com.tmj.tms.master.datalayer.service.ChargeService;
import com.tmj.tms.master.datalayer.service.CurrencyService;
import com.tmj.tms.master.datalayer.service.UnitService;
import com.tmj.tms.transport.datalayer.modal.TransportBooking;
import com.tmj.tms.transport.datalayer.service.TransportBookingService;
import com.tmj.tms.transport.utility.ViaLocationFormatter;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/finance/financialChargeManagement")
public class FinancialChargeManagementController {

    private final FinancialChargeManagementControllerManager financialChargeManagementControllerManager;
    private final ChargeService chargeService;
    private final CurrencyService currencyService;
    private final UnitService unitService;
    private final ModuleService moduleService;
    private final FinancialChargeService financialChargeService;
    private final TransportBookingService transportBookingService;
    private final ViaLocationFormatter viaLocationFormatter;

    @Autowired
    public FinancialChargeManagementController(FinancialChargeManagementControllerManager financialChargeManagementControllerManager,
                                               ChargeService chargeService,
                                               CurrencyService currencyService,
                                               UnitService unitService,
                                               ModuleService moduleService,
                                               FinancialChargeService financialChargeService,
                                               TransportBookingService transportBookingService,
                                               ViaLocationFormatter viaLocationFormatter) {
        this.financialChargeManagementControllerManager = financialChargeManagementControllerManager;
        this.chargeService = chargeService;
        this.currencyService = currencyService;
        this.unitService = unitService;
        this.moduleService = moduleService;
        this.financialChargeService = financialChargeService;
        this.transportBookingService = transportBookingService;
        this.viaLocationFormatter = viaLocationFormatter;
    }

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_finance@financialChargeManagement_VIEW')")
    public String viewPage(@RequestParam(value = "referenceSeq") Integer referenceSeq,
                           @RequestParam(value = "targetType") Integer targetType,
                           @RequestParam(value = "moduleSeq") Integer moduleSeq,
                           @RequestParam(value = "referenceType") Integer referenceType,
                           Model model) {
        this.pageLoad(model);
        Integer transport = this.moduleService.findByModuleCode("TRANSPORT").getModuleSeq();
        model.addAttribute("targetType", targetType);
        model.addAttribute("moduleSeq", moduleSeq);
        FinancialCharge financialCharge = null;
        if (moduleSeq.equals(transport) && targetType.equals(TargetType.TRANSPORT_JOB.getTargetType()) && referenceType.equals(ReferenceType.TRANSPORT_BOOKING.getReferenceType())) {
            TransportBooking transportBooking = this.transportBookingService.findOne(referenceSeq);
            transportBooking.setViaLocationString(this.viaLocationFormatter.getViaLocationAsString(transportBooking.getTransportBookingViaLocationList()));
            model.addAttribute("booking", transportBooking);
            financialCharge = this.financialChargeService.findByTargetTypeAndReferenceTypeAndReferenceSeqAndModuleSeqAndStatusNot(targetType, referenceType, referenceSeq, transport, MasterDataStatus.DELETED.getStatusSeq());
            if (financialCharge == null) {
                financialCharge = new FinancialCharge();
                financialCharge.setModuleSeq(transportBooking.getModuleSeq());
                financialCharge.setCompanyProfileSeq(transportBooking.getCompanyProfileSeq());
                financialCharge.setTargetType(targetType);
                financialCharge.setReferenceType(referenceType);
                financialCharge.setReferenceNo(transportBooking.getBookingNo());
                financialCharge.setReferenceSeq(transportBooking.getTransportBookingSeq());
                List<FinancialChargeDetail> financialChargeDetailList = this.financialChargeManagementControllerManager.loadDefaultChargeListForModuleSeqAndDepartmentSeqAndReferenceTye(moduleSeq, transportBooking.getDepartmentSeq(), referenceType, transportBooking.getTransportBookingSeq());
                if (financialChargeDetailList == null || financialChargeDetailList.size() == 0) {
                    financialChargeDetailList = new ArrayList<>();
                    financialChargeDetailList.add(new FinancialChargeDetail());
                }
                financialCharge.setFinancialChargeDetails(financialChargeDetailList);
            }
        }
        model.addAttribute("financialCharge", financialCharge);
        return "finance/financialChargeManagement";
    }

    @RequestMapping(value = "/createFinancialCharge", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_finance@financialChargeManagement_CREATE')")
    public
    @ResponseBody
    ResponseObject createFinancialCharge(@Valid @ModelAttribute FinancialCharge financialCharge) {
        return this.financialChargeManagementControllerManager.saveFinancialCharge(financialCharge);
    }

    @RequestMapping(value = "/updateFinancialCharge", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_finance@financialChargeManagement_UPDATE')")
    public
    @ResponseBody
    ResponseObject updateFinancialCharge(@Valid @ModelAttribute FinancialCharge financialCharge) {
        return this.financialChargeManagementControllerManager.updateFinancialCharge(financialCharge);
    }

    @RequestMapping(value = "/deleteAllFinancialCharge", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_finance@financialChargeManagement_DELETE')")
    public
    @ResponseBody
    ResponseObject deleteAllFinancialCharge(@Valid @ModelAttribute FinancialCharge financialCharge) {
        return this.financialChargeManagementControllerManager.deleteAllFinancialCharge(financialCharge);
    }

    @RequestMapping(value = "/findCurrency", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_finance@financialChargeManagement_VIEW')")
    public
    @ResponseBody
    List<Currency> findCurrency(@RequestParam("q") String searchParam) {
        return this.currencyService.findByCurrencyCodeStartsWithIgnoreCaseAndStatus(searchParam, MasterDataStatus.APPROVED.getStatusSeq());
    }

    @RequestMapping(value = "/findUnit", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_finance@financialChargeManagement_VIEW')")
    @ResponseBody
    public List<Unit> findUnit(@RequestParam("q") String searchParam) {
        return this.unitService.findByUnitNameStartsWithIgnoreCaseAndStatus(searchParam, MasterDataStatus.APPROVED.getStatusSeq());
    }

    @RequestMapping(value = "/findCharge", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_finance@financialChargeManagement_VIEW')")
    public
    @ResponseBody
    List<Charge> findCharge(@RequestParam("q") String searchParam) {
        return this.chargeService.findByChargeNameStartsWithIgnoreCaseAndStatus(searchParam,
                MasterDataStatus.APPROVED.getStatusSeq());
    }

    @RequestMapping(value = "/findByFinancialChargeSeq/{financialChargeSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_finance@financialChargeManagement_VIEW')")
    public
    @ResponseBody
    FinancialCharge findByFinancialChargeSeq(@PathVariable("financialChargeSeq") Integer financialChargeSeq) {
        return this.financialChargeService.findOne(financialChargeSeq, EntityGraphUtils.fromName("FinancialCharge.create"));
    }

    private void pageLoad(Model model) {
        model.addAttribute("chargeTypeList", ChargeType.values());
        model.addAttribute("chargeList", chargeService.findByStatus(MasterDataStatus.APPROVED.getStatusSeq()));
        model.addAttribute("currencyList", currencyService.findByStatus(MasterDataStatus.APPROVED.getStatusSeq()));
        model.addAttribute("unitList", unitService.findByStatus(MasterDataStatus.APPROVED.getStatusSeq()));
        model.addAttribute("statusList", MasterDataStatus.getStatusListForTransactions("ROLE_finance@financialChargeManagement_APPROVE"));
    }

}
