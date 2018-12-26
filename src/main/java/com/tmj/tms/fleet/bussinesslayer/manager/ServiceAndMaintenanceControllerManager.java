package com.tmj.tms.fleet.bussinesslayer.manager;

import com.tmj.tms.fleet.datalayer.modal.ServiceAndMaintenance;
import com.tmj.tms.fleet.datalayer.modal.auxiliary.ServiceAndMaintenanceSearch;
import com.tmj.tms.utility.ResponseObject;

import java.util.List;

public interface ServiceAndMaintenanceControllerManager {

    ResponseObject updateRecord(ServiceAndMaintenance serviceAndMaintenance);

    ResponseObject saveRecord(ServiceAndMaintenance serviceAndMaintenance);

    List<ServiceAndMaintenance> searchRecord(ServiceAndMaintenanceSearch serviceAndMaintenanceSearch);
}
