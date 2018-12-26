package com.tmj.tms.master.businesslayer.managerImpl;

import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.master.businesslayer.manager.UnitManagementControllerManager;
import com.tmj.tms.master.datalayer.modal.Unit;
import com.tmj.tms.master.datalayer.service.UnitService;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class UnitManagementControllerManagerImpl implements UnitManagementControllerManager {

    private final UnitService unitService;

    @Autowired
    public UnitManagementControllerManagerImpl(UnitService unitService) {
        this.unitService = unitService;
    }

    @Override
    public ResponseObject createUnit(Unit unit, Principal principal) {
        unit = this.unitService.save(unit);
        ResponseObject responseObject = new ResponseObject("Unit Saved Successfully", true);
        responseObject.setObject(unit);
        return responseObject;
    }

    @Override
    public ResponseObject updateDeliveryType(Unit unit, Principal principal) {
        ResponseObject responseObject;
        Unit dbUnit = this.unitService.findOne(unit.getUnitSeq());
        if (dbUnit != null) {
            if (!dbUnit.equals(unit)) {
                unit = this.unitService.save(unit);
                responseObject = new ResponseObject("Unit Updated Successfully", true);
                responseObject.setObject(unit);
            } else {
                responseObject = new ResponseObject("No Amendments Found !!", false);
            }
        } else {
            responseObject = new ResponseObject("Unit not Found !!", false);
        }
        return responseObject;
    }

    @Override
    public List<Unit> searchUnit(String unitName, String unitCode, String usedFor) {
        List<Unit> unitList = new ArrayList<>();
        try {
            List<Integer> statusSeqList = MasterDataStatus.getStatusListForViewing("ROLE_master@unitManagement_VIEW-DELETE");
            if (unitName.equals("") && unitCode.equals("") && usedFor.equals("None")) {//null,null,none
                unitList = this.unitService.findByStatusIn(statusSeqList);
            } else if (unitName.equals("") && unitCode.equals("") && !usedFor.equals("None")) {//null,null,value
                unitList = this.unitService.findByUsedForContainingIgnoreCaseAndStatusIn(usedFor, statusSeqList);
            } else if (unitName.equals("") && !unitCode.equals("") && usedFor.equals("None")) {//null,value,none
                unitList = this.unitService.findByUnitCodeContainingIgnoreCaseAndStatusIn(unitCode, statusSeqList);
            } else if (unitName.equals("") && !unitCode.equals("") && !usedFor.equals("None")) {//null,value,value
                unitList = this.unitService.findByUnitCodeContainingIgnoreCaseAndUsedForContainingIgnoreCaseAndStatusIn(unitCode, usedFor, statusSeqList);
            } else if (!unitName.equals("") && unitCode.equals("") && usedFor.equals("None")) {//value,null,none
                unitList = this.unitService.findByUnitNameContainingIgnoreCaseAndStatusIn(unitName, statusSeqList);
            } else if (!unitName.equals("") && unitCode.equals("") && !usedFor.equals("None")) {//value,null,value
                unitList = this.unitService.findByUnitNameContainingIgnoreCaseAndUsedForContainingIgnoreCaseAndStatusIn(unitName, usedFor, statusSeqList);
            } else if (!unitName.equals("") && !unitCode.equals("") && usedFor.equals("None")) {//value,value,none
                unitList = this.unitService.findByUnitNameContainingIgnoreCaseAndUnitCodeContainingIgnoreCaseAndStatusIn(unitName, unitCode, statusSeqList);
            } else {//value,value,value
                unitList = this.unitService.findByUnitNameContainingIgnoreCaseAndUnitCodeContainingIgnoreCaseAndUsedForContainingIgnoreCaseAndStatusIn(unitName, unitCode, usedFor, statusSeqList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return unitList;
    }

    public ResponseObject deleteUnit(Integer unitSeq, String username) {
        Unit dbUnit = this.unitService.findOne(unitSeq);
        dbUnit.setStatus(0);
        dbUnit = this.unitService.save(dbUnit);
        ResponseObject responseObject = new ResponseObject("Unit Deleted Successfully", true);
        responseObject.setObject(dbUnit);
        return responseObject;
    }
}
