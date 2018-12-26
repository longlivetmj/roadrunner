package com.tmj.tms.fleet.bussinesslayer.managerimpl;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphUtils;
import com.querydsl.core.BooleanBuilder;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.fleet.bussinesslayer.manager.ServiceAndMaintenanceControllerManager;
import com.tmj.tms.fleet.datalayer.modal.QServiceAndMaintenance;
import com.tmj.tms.fleet.datalayer.modal.ServiceAndMaintenance;
import com.tmj.tms.fleet.datalayer.modal.ServiceAndMaintenanceLine;
import com.tmj.tms.fleet.datalayer.modal.auxiliary.ServiceAndMaintenanceSearch;
import com.tmj.tms.fleet.datalayer.service.ServiceAndMaintenanceService;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class ServiceAndMaintenanceControllerManagerImpl implements ServiceAndMaintenanceControllerManager {

    private final HttpSession httpSession;
    private final ServiceAndMaintenanceService serviceAndMaintenanceService;

    @Autowired
    public ServiceAndMaintenanceControllerManagerImpl(HttpSession httpSession,
                                                      ServiceAndMaintenanceService serviceAndMaintenanceService) {
        this.httpSession = httpSession;
        this.serviceAndMaintenanceService = serviceAndMaintenanceService;
    }

    @Override
    public ResponseObject saveRecord(ServiceAndMaintenance serviceAndMaintenance) {
        Integer companyProfileSeq = Integer.parseInt(httpSession.getAttribute("companyProfileSeq").toString());
        serviceAndMaintenance.setCompanyProfileSeq(companyProfileSeq);
        serviceAndMaintenance.setStatus(MasterDataStatus.APPROVED.getStatusSeq());
        Double amount = 0.0;
        for (ServiceAndMaintenanceLine serviceAndMaintenanceLine : serviceAndMaintenance.getServiceAndMaintenanceLines()) {
            serviceAndMaintenanceLine.setStatus(MasterDataStatus.APPROVED.getStatusSeq());
            amount = amount + serviceAndMaintenanceLine.getAmount();
        }
        serviceAndMaintenance.setAmount(amount);
        serviceAndMaintenance.setTotalAmount(amount + serviceAndMaintenance.getTaxAmount());
        serviceAndMaintenance = this.serviceAndMaintenanceService.save(serviceAndMaintenance);
        ResponseObject responseObject = new ResponseObject("Record Saved Successfully", true);
        responseObject.setObject(serviceAndMaintenance);
        return responseObject;
    }

    @Override
    public ResponseObject updateRecord(ServiceAndMaintenance serviceAndMaintenance) {
        ResponseObject responseObject;
        Integer companyProfileSeq = Integer.parseInt(httpSession.getAttribute("companyProfileSeq").toString());
        serviceAndMaintenance.setCompanyProfileSeq(companyProfileSeq);
        serviceAndMaintenance.setStatus(MasterDataStatus.APPROVED.getStatusSeq());
        Double amount = 0.0;
        for (ServiceAndMaintenanceLine serviceAndMaintenanceLine : serviceAndMaintenance.getServiceAndMaintenanceLines()) {
            serviceAndMaintenanceLine.setServiceAndMaintenanceSeq(serviceAndMaintenance.getServiceAndMaintenanceSeq());
            serviceAndMaintenanceLine.setStatus(MasterDataStatus.APPROVED.getStatusSeq());
            amount = amount + serviceAndMaintenanceLine.getAmount();
        }
        serviceAndMaintenance.setAmount(amount);
        serviceAndMaintenance.setTotalAmount(amount + serviceAndMaintenance.getTaxAmount());
        ServiceAndMaintenance dbServiceAndMaintenance = this.serviceAndMaintenanceService.findOne(serviceAndMaintenance.getServiceAndMaintenanceSeq());
        if (dbServiceAndMaintenance != null) {
            if (!dbServiceAndMaintenance.equals(serviceAndMaintenance)) {
                serviceAndMaintenance = this.serviceAndMaintenanceService.save(serviceAndMaintenance);
                responseObject = new ResponseObject("Records Updated Successfully", true);
                responseObject.setObject(serviceAndMaintenance);
            } else {
                responseObject = new ResponseObject("No Amendments Found!!", false);
            }
        } else {
            responseObject = new ResponseObject("Record Not Found!!", false);
        }
        return responseObject;
    }

    @Override
    public List<ServiceAndMaintenance> searchRecord(ServiceAndMaintenanceSearch serviceAndMaintenanceSearch) {
        List<ServiceAndMaintenance> serviceAndMaintenanceList = null;
        try {
            Integer companyProfileSeq = Integer.parseInt(httpSession.getAttribute("companyProfileSeq").toString());
            QServiceAndMaintenance serviceAndMaintenance = QServiceAndMaintenance.serviceAndMaintenance;
            BooleanBuilder builder = new BooleanBuilder();
            builder.and(serviceAndMaintenance.companyProfileSeq.eq(companyProfileSeq));
            if (serviceAndMaintenanceSearch.getVehicleSeq() != null) {
                builder.and(serviceAndMaintenance.vehicleSeq.eq(serviceAndMaintenanceSearch.getVehicleSeq()));
            }
            if (serviceAndMaintenanceSearch.getStakeholderSeq() != null) {
                builder.and(serviceAndMaintenance.stakeholderSeq.eq(serviceAndMaintenanceSearch.getStakeholderSeq()));
            }
            if (serviceAndMaintenanceSearch.getSupplierSeq() != null) {
                builder.and(serviceAndMaintenance.supplierSeq.eq(serviceAndMaintenanceSearch.getSupplierSeq()));
            }
            if (serviceAndMaintenanceSearch.getStakeholderCashTypeSeq() != null) {
                builder.and(serviceAndMaintenance.stakeholderCashTypeSeq.eq(serviceAndMaintenanceSearch.getStakeholderCashTypeSeq()));
            }
            if (serviceAndMaintenanceSearch.getStatusSeq() != null) {
                builder.and(serviceAndMaintenance.status.eq(serviceAndMaintenanceSearch.getStatusSeq()));
            }
            if (serviceAndMaintenanceSearch.getActionType() != null) {
                builder.and(serviceAndMaintenance.actionType.eq(serviceAndMaintenanceSearch.getActionType()));
            }
            if (serviceAndMaintenanceSearch.getStartDate() != null && serviceAndMaintenanceSearch.getEndDate() != null) {
                builder.and(serviceAndMaintenance.transactionDate.between(serviceAndMaintenanceSearch.getStartDate(), serviceAndMaintenanceSearch.getEndDate()));
            }
            serviceAndMaintenanceList = (List<ServiceAndMaintenance>) this.serviceAndMaintenanceService.findAll(builder, EntityGraphUtils.fromName("ServiceAndMaintenance.default"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serviceAndMaintenanceList;
    }
}
