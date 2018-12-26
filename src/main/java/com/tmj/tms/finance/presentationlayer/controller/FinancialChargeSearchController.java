package com.tmj.tms.finance.presentationlayer.controller;

import com.tmj.tms.config.datalayer.modal.Department;
import com.tmj.tms.config.datalayer.service.ModuleService;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.finance.bussinesslayer.manager.FinancialChargeSearchControllerManager;
import com.tmj.tms.finance.utility.FinanceStatus;
import com.tmj.tms.finance.utility.FinancialChargeSearch;
import com.tmj.tms.finance.utility.ReferenceType;
import com.tmj.tms.finance.utility.TargetType;
import com.tmj.tms.fleet.datalayer.modal.Vehicle;
import com.tmj.tms.fleet.datalayer.service.VehicleService;
import com.tmj.tms.master.datalayer.modal.Stakeholder;
import com.tmj.tms.master.datalayer.modal.VehicleType;
import com.tmj.tms.master.datalayer.service.StakeholderService;
import com.tmj.tms.master.datalayer.service.StakeholderTypeService;
import com.tmj.tms.master.datalayer.service.VehicleTypeService;
import com.tmj.tms.transport.datalayer.service.TransportBookingStatusService;
import com.tmj.tms.transport.utility.DateFilterType;
import com.tmj.tms.utility.NDaysBefore;
import com.tmj.tms.utility.YesOrNo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/finance/financialChargeSearch")
public class FinancialChargeSearchController {

    private final ModuleService moduleService;
    private final HttpSession httpSession;
    private final TransportBookingStatusService transportBookingStatusService;
    private final StakeholderService stakeholderService;
    private final StakeholderTypeService stakeholderTypeService;
    private final VehicleTypeService vehicleTypeService;
    private final VehicleService vehicleService;
    private final FinancialChargeSearchControllerManager financialChargeSearchControllerManager;

    @Autowired
    public FinancialChargeSearchController(ModuleService moduleService,
                                           HttpSession httpSession,
                                           TransportBookingStatusService transportBookingStatusService,
                                           StakeholderService stakeholderService,
                                           StakeholderTypeService stakeholderTypeService,
                                           VehicleTypeService vehicleTypeService,
                                           VehicleService vehicleService,
                                           FinancialChargeSearchControllerManager financialChargeSearchControllerManager) {
        this.moduleService = moduleService;
        this.httpSession = httpSession;
        this.transportBookingStatusService = transportBookingStatusService;
        this.stakeholderService = stakeholderService;
        this.stakeholderTypeService = stakeholderTypeService;
        this.vehicleTypeService = vehicleTypeService;
        this.vehicleService = vehicleService;
        this.financialChargeSearchControllerManager = financialChargeSearchControllerManager;
    }

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_finance@financialChargeSearch_VIEW')")
    public String getPage(Model model, Principal principal) {
        this.pageLoad(model, principal);
        return "finance/financialChargeSearch";
    }

    @RequestMapping(value = "/findBookings", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_finance@financialChargeSearch_VIEW')")
    public String findBookings(@ModelAttribute FinancialChargeSearch financialChargeSearch,
                               Model model) {
        model.addAttribute("bookingList", this.financialChargeSearchControllerManager.searchBooking(financialChargeSearch));
        model.addAttribute("targetType", TargetType.TRANSPORT_JOB.getTargetType());
        model.addAttribute("referenceType", ReferenceType.TRANSPORT_BOOKING.getReferenceType());
        return "finance/content/financialChargeSearchData";
    }

    @RequestMapping(value = "/findCustomer", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_finance@financialChargeSearch_VIEW')")
    public
    @ResponseBody
    List<Stakeholder> findCustomer(@RequestParam("q") String searchParam) {
        return this.stakeholderService.getStakeholderDetails(searchParam,
                this.stakeholderTypeService.findByStakeholderTypeCodeAndStatus("CUSTOMER", MasterDataStatus.APPROVED.getStatusSeq()).getStakeholderTypeSeq(),
                Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString()),
                MasterDataStatus.APPROVED.getStatusSeq());
    }

    @RequestMapping(value = "/findTransporter", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_finance@financialChargeSearch_VIEW')")
    public
    @ResponseBody
    List<Stakeholder> findTransporter(@RequestParam("q") String searchParam, HttpServletRequest httpServletRequest) {
        return this.stakeholderService.getStakeholderDetails(searchParam,
                this.stakeholderTypeService.findByStakeholderTypeCodeAndStatus("TRANSPORT_COMPANY", MasterDataStatus.APPROVED.getStatusSeq()).getStakeholderTypeSeq(),
                Integer.parseInt(httpServletRequest.getSession().getAttribute("companyProfileSeq").toString()),
                MasterDataStatus.APPROVED.getStatusSeq());
    }

    @RequestMapping(value = "/getVehicleListByTransporterSeq/{transporterSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_finance@financialChargeSearch_VIEW')")
    public
    @ResponseBody
    List<Vehicle> getVehicleListByTransporterSeq(@PathVariable("transporterSeq") Integer transporterSeq) {
        return this.vehicleService.findByStakeholderSeqAndStatus(transporterSeq, MasterDataStatus.APPROVED.getStatusSeq());
    }

    @RequestMapping(value = "/findVehicleType", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_finance@financialChargeSearch_VIEW')")
    public
    @ResponseBody
    List<VehicleType> findVehicleType(@RequestParam("q") String searchParam) {
        return this.vehicleTypeService.findByVehicleTypeNameStartsWithIgnoreCaseAndStatus(searchParam, MasterDataStatus.APPROVED.getStatusSeq());
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/findModuleDepartments/{moduleSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_finance@financialChargeSearch_VIEW')")
    public
    @ResponseBody
    List<Department> getDepartment(@PathVariable("moduleSeq") Integer moduleSeq, HttpServletRequest request) {
        return (List<Department>) request.getSession().getAttribute("userDepartmentList" + moduleSeq);
    }

    private void pageLoad(Model model, Principal principal) {
        Integer companyProfileSeq = Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString());
        model.addAttribute("moduleList", this.moduleService.getModuleListByCompanySeqAndUsernameAndFinanceEnabled(companyProfileSeq, principal.getName(), YesOrNo.Yes.getYesOrNoSeq()));
        model.addAttribute("statusList", this.transportBookingStatusService.findAllByStatusOrderByCurrentStatus(MasterDataStatus.APPROVED.getStatusSeq()));
        model.addAttribute("companyProfileSeq", companyProfileSeq);
        model.addAttribute("endDate", new Date());
        model.addAttribute("startDate", new NDaysBefore().getDateNDaysBeforeCurrentDate(10));
        model.addAttribute("dateFilterTypeList", DateFilterType.values());
        model.addAttribute("financeStatusList", FinanceStatus.values());
        model.addAttribute("defaultFilterType", DateFilterType.ARRIVING_DATE.getDateFilterType());
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}
