package com.tmj.tms.transport.bussinesslayer.manager;

import com.tmj.tms.transport.datalayer.modal.TransportBulkBooking;
import com.tmj.tms.transport.datalayer.modal.auxiliary.BulkBookingSearch;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BulkBookingCreationControllerManager {

    ResponseObject upload(TransportBulkBooking transportBulkBooking, MultipartFile multipartFile);

    List<TransportBulkBooking> search(BulkBookingSearch bulkBookingSearch);
}
