package com.tmj.tms.master.presentationlayer.controller;

import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.fleet.utility.DurationType;
import com.tmj.tms.master.businesslayer.manager.ItemManagementControllerManager;
import com.tmj.tms.master.datalayer.modal.Item;
import com.tmj.tms.master.datalayer.modal.Unit;
import com.tmj.tms.master.datalayer.modal.auxiliary.ItemSearch;
import com.tmj.tms.master.datalayer.service.ItemService;
import com.tmj.tms.master.datalayer.service.UnitService;
import com.tmj.tms.master.utility.ItemType;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/master/itemManagement")
public class ItemManagementController {

    private final ItemManagementControllerManager itemManagementControllerManager;
    private final ItemService itemService;
    private final UnitService unitService;

    @Autowired
    public ItemManagementController(ItemManagementControllerManager itemManagementControllerManager,
                                    ItemService itemService,
                                    UnitService unitService) {
        this.itemManagementControllerManager = itemManagementControllerManager;
        this.itemService = itemService;
        this.unitService = unitService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getPage(Model model) {
        this.pageLoad(model);
        return "master/itemManagement";
    }

    @RequestMapping(value = "/createItem", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_master@itemManagement_CREATE')")
    public
    @ResponseBody
    ResponseObject createItem(@ModelAttribute Item item) {
        return this.itemManagementControllerManager.createItem(item);
    }

    @RequestMapping(value = "/updateItem", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_master@itemManagement_UPDATE')")
    public
    @ResponseBody
    ResponseObject updateItem(@ModelAttribute Item item) {
        return this.itemManagementControllerManager.updateItem(item);
    }

    @RequestMapping(value = "/searchItem", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_master@itemManagement_VIEW')")
    public String searchItem(@ModelAttribute ItemSearch itemSearch,
                             Model model) {
        model.addAttribute("itemList", this.itemManagementControllerManager.searchItem(itemSearch));
        return "master/content/itemData";
    }

    @RequestMapping(value = "/findByItemSeq/{itemSeq}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_master@itemManagement_VIEW')")
    public
    @ResponseBody
    Item findByDeliveryTypeSeq(@PathVariable("itemSeq") Integer itemSeq) {
        return this.itemService.findOne(itemSeq);
    }

    @RequestMapping(value = "/deleteByItemSeq", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_master@itemManagement_DELETE')")
    public
    @ResponseBody
    ResponseObject deleteItemByItemSeq(@RequestParam("itemSeq") Integer itemSeq) {
        return this.itemManagementControllerManager.deleteItem(itemSeq);
    }

    @RequestMapping(value = "/findUnit", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_master@itemManagement_DELETE')")
    @ResponseBody
    public List<Unit> findUnit(@RequestParam("q") String searchParam) {
        return this.unitService.findByUnitNameStartsWithIgnoreCaseAndStatus(searchParam, MasterDataStatus.APPROVED.getStatusSeq());
    }

    private void pageLoad(Model model) {
        model.addAttribute("itemTypeList", ItemType.values());
        model.addAttribute("durationTypeList", DurationType.values());
        model.addAttribute("statusList", MasterDataStatus.getStatusListForTransactions("ROLE_master@itemManagement_APPROVE"));
    }

}
