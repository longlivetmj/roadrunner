package com.tmj.tms.master.businesslayer.manager;

import com.tmj.tms.master.datalayer.modal.Unit;
import com.tmj.tms.utility.ResponseObject;

import java.security.Principal;
import java.util.List;

public interface UnitManagementControllerManager {

    ResponseObject createUnit(Unit unit, Principal principal);

    ResponseObject updateDeliveryType(Unit unit, Principal principal);

    List<Unit> searchUnit(String unitName, String unitCode, String usedFor);

    ResponseObject deleteUnit(Integer bankSeq, String username);
}
