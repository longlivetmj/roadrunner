package com.tmj.tms.fleet.bussinesslayer.managerimpl;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphUtils;
import com.querydsl.core.BooleanBuilder;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.fleet.bussinesslayer.manager.FuelManagementControllerManager;
import com.tmj.tms.fleet.datalayer.modal.QVehicleFuel;
import com.tmj.tms.fleet.datalayer.modal.Vehicle;
import com.tmj.tms.fleet.datalayer.modal.VehicleFuel;
import com.tmj.tms.fleet.datalayer.modal.auxiliary.FuelSearch;
import com.tmj.tms.fleet.datalayer.service.VehicleFuelService;
import com.tmj.tms.fleet.datalayer.service.VehicleService;
import com.tmj.tms.master.utility.StakeholderCashType;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class FuelManagementControllerManagerImpl implements FuelManagementControllerManager {

    private final HttpSession httpSession;
    private final VehicleFuelService vehicleFuelService;
    private final VehicleService vehicleService;

    @Autowired
    public FuelManagementControllerManagerImpl(HttpSession httpSession,
                                               VehicleFuelService vehicleFuelService,
                                               VehicleService vehicleService) {
        this.httpSession = httpSession;
        this.vehicleFuelService = vehicleFuelService;
        this.vehicleService = vehicleService;
    }

    @Override
    public ResponseObject saveVehicleFuel(VehicleFuel vehicleFuel) {
        Integer companyProfileSeq = Integer.parseInt(httpSession.getAttribute("companyProfileSeq").toString());
        Vehicle vehicle = this.vehicleService.findOne(vehicleFuel.getVehicleSeq());
        vehicleFuel.setFuelType(vehicle.getFuelType());
        vehicleFuel.setCompanyProfileSeq(companyProfileSeq);
        if(vehicleFuel.getStakeholderCashTypeSeq().equals(StakeholderCashType.CASH.getStakeholderCashTypeSeq())){
            vehicleFuel.setStatus(MasterDataStatus.APPROVED.getStatusSeq());
        }else{
            vehicleFuel.setStatus(MasterDataStatus.OPEN.getStatusSeq());
        }
        vehicleFuel = this.vehicleFuelService.save(vehicleFuel);
        ResponseObject responseObject = new ResponseObject("Fuel Saved Successfully", true);
        responseObject.setObject(vehicleFuel);
        return responseObject;
    }

    @Override
    public ResponseObject updateVehicleFuel(VehicleFuel vehicleFuel) {
        ResponseObject responseObject;
        Integer companyProfileSeq = Integer.parseInt(httpSession.getAttribute("companyProfileSeq").toString());
        if(vehicleFuel.getStakeholderCashTypeSeq().equals(StakeholderCashType.CASH.getStakeholderCashTypeSeq())){
            vehicleFuel.setStatus(MasterDataStatus.APPROVED.getStatusSeq());
        }else{
            vehicleFuel.setStatus(MasterDataStatus.OPEN.getStatusSeq());
        }
        vehicleFuel.setCompanyProfileSeq(companyProfileSeq);
        VehicleFuel dbVehicleFuel = this.vehicleFuelService.findOne(vehicleFuel.getVehicleFuelSeq());
        if (dbVehicleFuel != null) {
            vehicleFuel.setFuelType(dbVehicleFuel.getFuelType());
            if (!dbVehicleFuel.equals(vehicleFuel)) {
                vehicleFuel = this.vehicleFuelService.save(vehicleFuel);
                responseObject = new ResponseObject("Fuel Updated Successfully", true);
                responseObject.setObject(vehicleFuel);
            } else {
                responseObject = new ResponseObject("No Amendments Found", false);
            }
        } else {
            responseObject = new ResponseObject("Fuel Record Not Found!!", false);
        }
        return responseObject;
    }

    @Override
    public List<VehicleFuel> searchFuelData(FuelSearch fuelSearch) {
        List<VehicleFuel> vehicleFuelList = null;
        try {
            Integer companyProfileSeq = Integer.parseInt(httpSession.getAttribute("companyProfileSeq").toString());
            QVehicleFuel vehicleFuel = QVehicleFuel.vehicleFuel;
            BooleanBuilder builder = new BooleanBuilder();
            builder.and(vehicleFuel.companyProfileSeq.eq(companyProfileSeq));
            if (fuelSearch.getVehicleSeq() != null) {
                builder.and(vehicleFuel.vehicleSeq.eq(fuelSearch.getVehicleSeq()));
            }
            if (fuelSearch.getStakeholderSeq() != null) {
                builder.and(vehicleFuel.stakeholderSeq.eq(fuelSearch.getStakeholderSeq()));
            }
            if (fuelSearch.getSupplierSeq() != null) {
                builder.and(vehicleFuel.supplierSeq.eq(fuelSearch.getSupplierSeq()));
            }
            if (fuelSearch.getStakeholderCashTypeSeq() != null) {
                builder.and(vehicleFuel.stakeholderCashTypeSeq.eq(fuelSearch.getStakeholderCashTypeSeq()));
            }
            if (fuelSearch.getFuelType() != null) {
                builder.and(vehicleFuel.fuelType.eq(fuelSearch.getFuelType()));
            }
            if (fuelSearch.getFuelTypeVariantSeq() != null) {
                builder.and(vehicleFuel.fuelTypeVariantSeq.eq(fuelSearch.getFuelTypeVariantSeq()));
            }
            if (fuelSearch.getStatusSeq() != null) {
                builder.and(vehicleFuel.status.eq(fuelSearch.getStatusSeq()));
            }
            if (fuelSearch.getStartDate() != null && fuelSearch.getEndDate() != null) {
                builder.and(vehicleFuel.transactionDate.between(fuelSearch.getStartDate(), fuelSearch.getEndDate()));
            }
            vehicleFuelList = (List<VehicleFuel>) this.vehicleFuelService.findAll(builder, EntityGraphUtils.fromName("VehicleFuel.default"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vehicleFuelList;
    }

    @Override
    public ResponseObject delete(Integer vehicleFuelSeq) {
        ResponseObject responseObject;
        VehicleFuel dbVehicleFuel = this.vehicleFuelService.findOne(vehicleFuelSeq);
        if (dbVehicleFuel != null) {
            dbVehicleFuel.setStatus(MasterDataStatus.DELETED.getStatusSeq());
            dbVehicleFuel = this.vehicleFuelService.save(dbVehicleFuel);
            responseObject = new ResponseObject("Fuel Record Successfully Deleted", true);
            responseObject.setObject(dbVehicleFuel);
        } else {
            responseObject = new ResponseObject("Fuel Record Not Found!!", false);
        }
        return responseObject;
    }

}
