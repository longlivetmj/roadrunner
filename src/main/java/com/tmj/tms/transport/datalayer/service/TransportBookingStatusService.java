package com.tmj.tms.transport.datalayer.service;

import com.tmj.tms.transport.datalayer.modal.TransportBookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.jdo.annotations.Cacheable;
import java.util.List;

@Repository
public interface TransportBookingStatusService extends JpaRepository<TransportBookingStatus, Integer> {

    @Cacheable
    List<TransportBookingStatus> findByUpdatable(Integer value);

    @Cacheable
    List<TransportBookingStatus> findByVehicleAssign(Integer value);

    @Cacheable
    List<TransportBookingStatus> findByInvoiceable(Integer value);

    @Cacheable
    List<TransportBookingStatus> findByKmUpdate(Integer value);

    @Cacheable
    List<TransportBookingStatus> findAllByStatusOrderByCurrentStatus(Integer value);

    @Cacheable
    List<TransportBookingStatus> findAllByStatusAndVehicleAssignOrderByCurrentStatus(Integer status, Integer vehicleAssign);

    @Cacheable
    List<TransportBookingStatus> findAllByStatusAndTimeUpdateOrderByCurrentStatus(Integer status, Integer timeUpdate);
}
