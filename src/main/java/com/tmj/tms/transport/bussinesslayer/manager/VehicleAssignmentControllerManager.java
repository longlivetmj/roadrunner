package com.tmj.tms.transport.bussinesslayer.manager;

import com.tmj.tms.master.datalayer.modal.Employee;
import com.tmj.tms.transport.datalayer.modal.TransportBooking;
import com.tmj.tms.transport.datalayer.modal.TransportBookingVehicle;
import com.tmj.tms.transport.datalayer.modal.auxiliary.VehicleAssignmentSearch;
import com.tmj.tms.utility.ResponseObject;

import java.util.List;

public interface VehicleAssignmentControllerManager {

    List<TransportBooking> searchBooking(VehicleAssignmentSearch vehicleAssignmentSearch);

    ResponseObject assignVehicle(TransportBookingVehicle transportBookingVehicle);

    ResponseObject validateVehicleAssignment(TransportBookingVehicle transportBookingVehicle);

    ResponseObject removeVehicle(TransportBookingVehicle transportBookingVehicle);

    ResponseObject validateRemoveVehicleAssignment(TransportBookingVehicle transportBookingVehicle);

    List<Employee> findHelper(Integer transportBookingSeq, String searchParam, Integer companyProfileSeq);

    List<Employee> findDriver(Integer transportBookingSeq, String searchParam, Integer companyProfileSeq);

    ResponseObject changeDriver(Integer transportBookingSeq, Integer employeeSeq);

    ResponseObject changeHelper(Integer transportBookingSeq, Integer employeeSeq);
}
