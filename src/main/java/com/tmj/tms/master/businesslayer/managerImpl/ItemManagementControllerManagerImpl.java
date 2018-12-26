package com.tmj.tms.master.businesslayer.managerImpl;

import com.querydsl.core.BooleanBuilder;
import com.tmj.tms.master.businesslayer.manager.ItemManagementControllerManager;
import com.tmj.tms.master.datalayer.modal.Item;
import com.tmj.tms.master.datalayer.modal.QItem;
import com.tmj.tms.master.datalayer.modal.auxiliary.ItemSearch;
import com.tmj.tms.master.datalayer.service.ItemService;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class ItemManagementControllerManagerImpl implements ItemManagementControllerManager {

    private final HttpSession httpSession;
    private final ItemService itemService;

    @Autowired
    public ItemManagementControllerManagerImpl(HttpSession httpSession,
                                               ItemService itemService) {
        this.httpSession = httpSession;
        this.itemService = itemService;
    }

    @Override
    public ResponseObject createItem(Item item) {
        Integer companyProfileSeq = Integer.parseInt(httpSession.getAttribute("companyProfileSeq").toString());
        item.setCompanyProfileSeq(companyProfileSeq);
        item = this.itemService.save(item);
        ResponseObject responseObject = new ResponseObject("Item Saved Successfully", true);
        responseObject.setObject(item);
        return responseObject;
    }

    @Override
    public ResponseObject updateItem(Item item) {
        Integer companyProfileSeq = Integer.parseInt(httpSession.getAttribute("companyProfileSeq").toString());
        item.setCompanyProfileSeq(companyProfileSeq);
        ResponseObject responseObject;
        Item dbItem = this.itemService.findOne(item.getItemSeq());
        if (dbItem != null) {
            if (!dbItem.equals(item)) {
                item = this.itemService.save(item);
                responseObject = new ResponseObject("Item Updated Successfully", true);
                responseObject.setObject(item);
            } else {
                responseObject = new ResponseObject("No Amendments Found !!", false);
            }
        } else {
            responseObject = new ResponseObject("Item not Found !!", false);
        }
        return responseObject;
    }

    @Override
    public List<Item> searchItem(ItemSearch itemSearch) {
        List<Item> itemList = null;
        try {
            QItem item = QItem.item;
            BooleanBuilder builder = new BooleanBuilder();
            Integer companyProfileSeq = Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString());
            builder.and(item.companyProfileSeq.eq(companyProfileSeq));
            if (itemSearch.getItemCode() != null) {
                builder.and(item.itemCode.containsIgnoreCase(itemSearch.getItemCode()));
            }
            if (itemSearch.getItemName() != null) {
                builder.and(item.itemName.containsIgnoreCase(itemSearch.getItemName()));
            }
            if (itemSearch.getItemType() != null) {
                builder.and(item.itemType.eq(itemSearch.getItemType()));
            }
            if (itemSearch.getStatus() != null) {
                builder.and(item.status.eq(itemSearch.getStatus()));
            }
            itemList = (List<Item>) this.itemService.findAll(builder);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemList;
    }

    @Override
    public ResponseObject deleteItem(Integer itemSeq) {
        Item dbItem = this.itemService.findOne(itemSeq);
        dbItem.setStatus(0);
        dbItem = this.itemService.save(dbItem);
        ResponseObject responseObject = new ResponseObject("Item Deleted Successfully", true);
        responseObject.setObject(dbItem);
        return responseObject;
    }
}
