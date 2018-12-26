package com.tmj.tms.master.businesslayer.manager;

import com.tmj.tms.master.datalayer.modal.Item;
import com.tmj.tms.master.datalayer.modal.auxiliary.ItemSearch;
import com.tmj.tms.utility.ResponseObject;

import java.util.List;

public interface ItemManagementControllerManager {

    ResponseObject createItem(Item item);

    ResponseObject updateItem(Item item);

    List<Item> searchItem(ItemSearch itemSearch);

    ResponseObject deleteItem(Integer itemSeq);

}
