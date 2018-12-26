package com.tmj.tms.fleet.bussinesslayer.managerimpl;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphUtils;
import com.querydsl.core.BooleanBuilder;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.fleet.bussinesslayer.manager.VehicleManagementControllerManager;
import com.tmj.tms.fleet.datalayer.modal.QVehicle;
import com.tmj.tms.fleet.datalayer.modal.Vehicle;
import com.tmj.tms.fleet.datalayer.modal.VehicleVehicleType;
import com.tmj.tms.fleet.datalayer.modal.auxiliary.VehicleSearch;
import com.tmj.tms.fleet.datalayer.service.VehicleService;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class VehicleManagementControllerManagerImpl implements VehicleManagementControllerManager {

    private final VehicleService vehicleService;
    private final HttpSession httpSession;

    @Autowired
    public VehicleManagementControllerManagerImpl(VehicleService vehicleService,
                                                  HttpSession httpSession) {
        this.vehicleService = vehicleService;
        this.httpSession = httpSession;
    }

    @Override
    public ResponseObject saveVehicle(Vehicle vehicle, Principal principal) {
        Integer companyProfileSeq = Integer.parseInt(httpSession.getAttribute("companyProfileSeq").toString());
        vehicle.setCompanyProfileSeq(companyProfileSeq);
        vehicle.setVehicleActivationBy(principal.getName());
        vehicle.setVehicleActivationDate(new Date());
        List<VehicleVehicleType> vehicleVehicleTypeList = new ArrayList<>();
        for (Integer vehicleTypeSeq : vehicle.getVehicleTypeSeqList()) {
            VehicleVehicleType vehicleVehicleType = new VehicleVehicleType();
            vehicleVehicleType.setVehicleTypeSeq(vehicleTypeSeq);
            vehicleVehicleType.setStatus(MasterDataStatus.APPROVED.getStatusSeq());
            vehicleVehicleTypeList.add(vehicleVehicleType);
        }
        vehicle.setVehicleVehicleTypeList(vehicleVehicleTypeList);
        vehicle = this.vehicleService.save(vehicle);
        ResponseObject responseObject = new ResponseObject("Vehicle Saved Successfully", true);
        responseObject.setObject(vehicle);
        return responseObject;
    }

    @Override
    public ResponseObject updateVehicle(Vehicle vehicle) {
        ResponseObject responseObject;
        Integer companyProfileSeq = Integer.parseInt(httpSession.getAttribute("companyProfileSeq").toString());
        vehicle.setCompanyProfileSeq(companyProfileSeq);
        Vehicle dbVehicle = this.vehicleService.findOne(vehicle.getVehicleSeq());
        if (dbVehicle != null) {
            if (!dbVehicle.equals(vehicle)) {
                vehicle.setVehicleActivationBy(dbVehicle.getVehicleActivationBy());
                vehicle.setVehicleActivationDate(dbVehicle.getVehicleActivationDate());
                List<VehicleVehicleType> vehicleVehicleTypeList = new ArrayList<>();
                for (Integer vehicleTypeSeq : vehicle.getVehicleTypeSeqList()) {
                    VehicleVehicleType vehicleVehicleType = new VehicleVehicleType();
                    vehicleVehicleType.setVehicleTypeSeq(vehicleTypeSeq);
                    vehicleVehicleType.setStatus(MasterDataStatus.APPROVED.getStatusSeq());
                    vehicleVehicleType.setVehicleSeq(vehicle.getVehicleSeq());
                    vehicleVehicleTypeList.add(vehicleVehicleType);
                }
                vehicle.setVehicleVehicleTypeList(vehicleVehicleTypeList);
                vehicle = this.vehicleService.save(vehicle);
                responseObject = new ResponseObject("Vehicle Updated Successfully", true);
                responseObject.setObject(vehicle);
            } else {
                responseObject = new ResponseObject("No Amendments Found", false);
            }
        } else {
            responseObject = new ResponseObject("Vehicle Not Found!!", false);
        }
        return responseObject;
    }

    @Override
    public List<Vehicle> searchVehicle(VehicleSearch vehicleSearch) {
        List<Vehicle> vehicleList = null;
        try {
            Integer companyProfileSeq = Integer.parseInt(httpSession.getAttribute("companyProfileSeq").toString());
            QVehicle vehicle = QVehicle.vehicle;
            BooleanBuilder builder = new BooleanBuilder();
            builder.and(vehicle.companyProfileSeq.eq(companyProfileSeq));
            if (vehicleSearch.getVehicleNo() != null) {
                builder.and(vehicle.vehicleNo.containsIgnoreCase(vehicleSearch.getVehicleNo()));
            }
            if (vehicleSearch.getVehicleCode() != null) {
                builder.and(vehicle.vehicleCode.containsIgnoreCase(vehicleSearch.getVehicleCode()));
            }
            if (vehicleSearch.getVehicleTypeSeq() != null) {
                builder.and(vehicle.vehicleVehicleTypeList.any().vehicleTypeSeq.eq(vehicleSearch.getVehicleTypeSeq()));
            }
            if (vehicleSearch.getStatusSeq() != null) {
                builder.and(vehicle.status.eq(vehicleSearch.getStatusSeq()));
            }
            if (vehicleSearch.getStartDate() != null && vehicleSearch.getEndDate() != null) {
                builder.and(vehicle.createdDate.between(vehicleSearch.getStartDate(), vehicleSearch.getEndDate()));
            }
            vehicleList = (List<Vehicle>) this.vehicleService.findAll(builder, EntityGraphUtils.fromName("Vehicle.default"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vehicleList;
    }
}
