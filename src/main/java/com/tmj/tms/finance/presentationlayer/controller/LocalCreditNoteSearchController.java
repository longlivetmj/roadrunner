package com.tmj.tms.finance.presentationlayer.controller;

import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.finance.bussinesslayer.manager.LocalCreditNoteSearchControllerManager;
import com.tmj.tms.finance.datalayer.modal.auxiliary.LocalCreditNoteSearchAux;
import com.tmj.tms.finance.utility.InvoiceType;
import com.tmj.tms.utility.NDaysBefore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@Controller
@RequestMapping("/finance/localCreditNoteSearch")
public class LocalCreditNoteSearchController {

    private final LocalCreditNoteSearchControllerManager localCreditNoteSearchControllerManager;

    @Autowired
    public LocalCreditNoteSearchController(LocalCreditNoteSearchControllerManager localCreditNoteSearchControllerManager) {
        this.localCreditNoteSearchControllerManager = localCreditNoteSearchControllerManager;
    }

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_finance@localCreditNoteSearch_VIEW')")
    public String getPage(Model model) {
        model.addAttribute("fromDate", new NDaysBefore().getDateNDaysBeforeCurrentDate(30));
        model.addAttribute("toDate", new NDaysBefore().getDateNDaysAfterCurrentDate(1));
        this.pageLoad(model);
        return "finance/localCreditNoteSearch";
    }

    @RequestMapping(value = "/searchLocalCreditNoteDetail", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_finance@localCreditNoteSearch_VIEW')")
    public String searchHouseBl(@ModelAttribute LocalCreditNoteSearchAux localCreditNoteSearchAux,
                                Model model,
                                HttpServletRequest request) {
        model.addAttribute("localCreditNoteDetailList", this.localCreditNoteSearchControllerManager.searchLocalCreditNoteDetail(localCreditNoteSearchAux, request));
        return "finance/content/localCreditNoteData";
    }

    public Model pageLoad(Model model) {
        String[] statusList = {"ROLE_finance@localCreditNoteSearch_APPROVE", "ROLE_finance@localCreditNoteSearch_DELETE"};
        model.addAttribute("invoiceTypeList", InvoiceType.values());
        model.addAttribute("statusList", MasterDataStatus.getStatusListForTransaction(Arrays.asList(statusList)));
        return model;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}
