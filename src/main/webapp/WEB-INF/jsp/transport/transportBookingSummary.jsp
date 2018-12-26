<%@ include file="/WEB-INF/jsp/include.jsp" %>
<script type="text/javascript" src="../../../customjs/transport/transportBookingSummary.js"></script>
<div class="page-title">
    <div class="col-md-7"><h3><span class="fa fa-book"></span>Transport Booking Summary <span
            style="color: red">${booking.bookingNo} (${booking.job.jobNo})</span></h3></div>
    <div class="col-md-4">
        <div class="form-group">
            <label class="col-md-3 col-xs-12 control-label">Find</label>
            <div class="col-md-6 col-xs-12">
                <input type="text" id="searchString"/>
            </div>
            <div class="col-md-3">
                <button type="button" class="btn btn-primary pull-right"
                        onclick="search_booking()">Search
                </button>
            </div>
        </div>
    </div>
</div>
<div class="row col-md-12">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title"><span class="fa fa-book"></span> General Information</h3>
            <ul class="panel-controls">
                <li><a onclick="view_hide('createBookingPanel')" class="panel-collapse">
                    <span class="fa fa-angle-down"></span></a></li>
            </ul>
        </div>
        <div class="panel-body" id="createBookingPanel">
            <form class="form-horizontal createBooking" id="createBooking" method="post">
                <div class="panel panel-warning">
                    <div class="panel-body" id="booking_info">
                        <div class="hidden" id="hidden_ids">
                            <input class="hidden" name="transportBookingSeq"
                                   value="${booking.transportBookingSeq}"
                                   id="transportBookingSeq"/>
                            <input class="hidden" name="companyProfileSeq" id="companyProfileSeq"
                                   value="${booking.companyProfileSeq}"/>
                            <input class="hidden" name="moduleSeq" id="moduleSeq"
                                   value="${booking.moduleSeq}"/>
                            <input type="hidden" id="currentStatus" name="currentStatus"
                                   value="${booking.currentStatus}"/>
                            <input type="hidden" id="currentStatusDescription"
                                   value="${booking.transportBookingStatus.currentStatusDescription}"/>
                            <input type="hidden" id="arrivalConfirmationData"
                                   value="${arrivalConfirmationData}"/>
                        </div>
                        <div id="general_info">
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label class="col-md-4 col-xs-12 control-label">Customer</label>
                                    <div class="col-md-7 col-xs-12">
                                        <h5 class="h5-top-margin-7">
                                            <label>${booking.customer.stakeholderName}</label></h5>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-4 col-xs-12 control-label">Shipper</label>
                                    <div class="col-md-7 col-xs-12">
                                        <h5 class="h5-top-margin-7">
                                            <label>${booking.shipper.stakeholderName}</label></h5>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-4 col-xs-12 control-label">Vehicle Type</label>
                                    <div class="col-md-7 col-xs-12">
                                        <h5 class="h5-top-margin-7">
                                            <label>${booking.vehicleType.vehicleTypeName}</label></h5>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-4 col-xs-12 control-label">Pickup Location</label>
                                    <div class="col-md-7 col-xs-12">
                                        <h5 class="h5-top-margin-7">
                                            <label>${booking.pickupLocation.destination} ${booking.pickupLocationAddress}</label>
                                        </h5>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-4 col-xs-12 control-label">Delivery Location</label>
                                    <div class="col-md-7 col-xs-12">
                                        <h5 class="h5-top-margin-7">
                                            <label>${booking.deliveryLocation.destination} ${booking.deliveryLocationAddress}</label>
                                        </h5>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-4 col-xs-12 control-label">Packages</label>
                                    <div class="col-md-7 col-xs-12">
                                        <h5 class="h5-top-margin-7">
                                            <label>${booking.pieces} ${booking.packageType.packageTypeName}</label>
                                        </h5>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-4 col-xs-12 control-label">Estimated Km</label>
                                    <div class="col-md-7 col-xs-12">
                                        <h5 class="h5-top-margin-7"><label>${booking.estimatedKm}</label></h5>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-4 col-xs-12 control-label">Payment Mode</label>
                                    <div class="col-md-7 col-xs-12">
                                        <h5 class="h5-top-margin-7"><label>${booking.paymentModeDescription}</label>
                                        </h5>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label class="col-md-4 col-xs-12 control-label">Consignee</label>
                                    <div class="col-md-7 col-xs-12">
                                        <h5 class="h5-top-margin-7">
                                            <label>${booking.consignee.stakeholderName}</label></h5>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-4 col-xs-12 control-label">Customer Ref.</label>
                                    <div class="col-md-7 col-xs-12">
                                        <h5 class="h5-top-margin-7"><label>${booking.customerReferenceNo}</label>
                                        </h5>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-4 col-xs-12 control-label">Cash or Credit</label>
                                    <div class="col-md-7 col-xs-12">
                                        <h5 class="h5-top-margin-7">
                                            <label>${booking.cashOrCreditDescription}</label></h5>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-4 col-xs-12 control-label">Pickup Time</label>
                                    <div class="col-md-7 col-xs-12">
                                        <h5 class="h5-top-margin-7"><label><fmt:formatDate
                                                value="${booking.requestedArrivalTime}"
                                                pattern="yyyy-MM-dd hh:mm a"/></label></h5>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-4 col-xs-12 control-label">Delivery Time</label>
                                    <div class="col-md-7 col-xs-12">
                                        <h5 class="h5-top-margin-7"><label><fmt:formatDate
                                                value="${booking.requestedDeliveryTime}"
                                                pattern="yyyy-MM-dd hh:mm a"/></label></h5>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-4 col-xs-12 control-label">CBM</label>
                                    <div class="col-md-7 col-xs-12">
                                        <h5 class="h5-top-margin-7"><label>${booking.cbm}</label></h5>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-4 col-xs-12 control-label">Proposed Rate</label>
                                    <div class="col-md-7 col-xs-12">
                                        <h5 class="h5-top-margin-7">
                                            <label>${booking.proposedTransportCharge}</label></h5>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-4 col-xs-12 control-label">Remarks</label>
                                    <div class="col-md-7 col-xs-12">
                                        <h5 class="h5-top-margin-7"><label>${booking.remarks}</label></h5>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label class="col-md-4 col-xs-12 control-label">Department</label>
                                    <div class="col-md-7 col-xs-12">
                                        <h5 class="h5-top-margin-7">
                                            <label>${booking.department.departmentName}</label></h5>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-4 col-xs-12 control-label">Invoice To</label>
                                    <div class="col-md-7 col-xs-12">
                                        <h5 class="h5-top-margin-7">
                                            <label>${booking.invoiceCustomer.stakeholderName}</label></h5>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-4 col-xs-12 control-label">Container Type</label>
                                    <div class="col-md-7 col-xs-12">
                                        <h5 class="h5-top-margin-7">
                                            <label>${booking.containerType.containerTypeName}</label></h5>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-4 col-xs-12 control-label">Invoice Status</label>
                                    <div class="col-md-7 col-xs-12">
                                        <h5 class="h5-top-margin-7">
                                            <label>${booking.invoiceStatusDescription}</label></h5>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-4 col-xs-12 control-label">Pickup Contact</label>
                                    <div class="col-md-7 col-xs-12">
                                        <h5 class="h5-top-margin-7">
                                            <label>${booking.pickupContactPerson} ${booking.pickupContactNo}</label>
                                        </h5>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-4 col-xs-12 control-label">Delivery Contact</label>
                                    <div class="col-md-7 col-xs-12">
                                        <h5 class="h5-top-margin-7">
                                            <label>${booking.deliveryContactPerson} ${booking.deliveryContactNo}</label>
                                        </h5>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-4 col-xs-12 control-label">Volume</label>
                                    <div class="col-md-7 col-xs-12">
                                        <h5 class="h5-top-margin-7">
                                            <label>${booking.volume} ${booking.uov.unitName}</label></h5>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-4 col-xs-12 control-label">Weight</label>
                                    <div class="col-md-7 col-xs-12">
                                        <h5 class="h5-top-margin-7">
                                            <label>${booking.weight} ${booking.uow.unitName}</label></h5>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title"><span class="fa fa-database"></span> Via Locations</h3>
            <ul class="panel-controls">
                <li><a onclick="view_hide('createViaLocationPanel')" class="panel-collapse">
                    <span class="fa fa-angle-down"></span></a></li>
            </ul>
        </div>
        <div id="createViaLocationPanel" class="panel-body">
            <div class="panel panel-warning">
                <div class="panel-body">
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th class="text-center">Location</th>
                            <th class="text-center">Requested Arrival Time</th>
                            <th class="text-center">Actual Arrival Time</th>
                            <th class="text-center">Contact Details</th>
                            <th class="text-center">Drop Pick</th>
                            <th class="text-center">Pieces</th>
                            <th class="text-center">CBM</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${booking.transportBookingViaLocationList}" var="transportBookingViaLocation">
                            <tr id="${transportBookingViaLocation.transportBookingViaLocationSeq}">
                                <td><h5 class="h5-top-margin-7">
                                    <label>${transportBookingViaLocation.viaLocation.destination} ${transportBookingViaLocation.viaLocationAddress}</label>
                                </h5></td>
                                <td><h5 class="h5-top-margin-7"><label><fmt:formatDate
                                        value="${transportBookingViaLocation.requestedArrivalTime}"
                                        pattern="yyyy-MM-dd hh:mm a"/></label></h5></td>
                                <td><h5 class="h5-top-margin-7"><label><fmt:formatDate
                                        value="${transportBookingViaLocation.requestedArrivalTime}"
                                        pattern="yyyy-MM-dd hh:mm a"/></label></h5></td>
                                <td><h5 class="h5-top-margin-7">
                                    <label>${transportBookingViaLocation.contactPerson} ${transportBookingViaLocation.contactNo}</label>
                                </h5></td>
                                <td><h5 class="h5-top-margin-7">
                                    <label>${transportBookingViaLocation.dropPickStatusDescription}</label></h5></td>
                                <td><h5 class="h5-top-margin-7">
                                    <label>${transportBookingViaLocation.pieces} ${transportBookingViaLocation.packageType.packageTypeName}</label>
                                </h5></td>
                                <td><h5 class="h5-top-margin-7"><label>${transportBookingViaLocation.cbm}</label></h5>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-12">
        <div class="col-md-4"></div>
        <div class="col-md-4"></div>
        <div class="col-md-4">
            <button type="button" class="btn btn-warning pull-right"
                    <sec:authorize
                            access="!hasRole('ROLE_finance@financialChargeManagement_VIEW')">
                        disabled="disabled"
                    </sec:authorize>
                    onclick="load_finance_charge('${booking.transportBookingSeq}','${booking.moduleSeq}','${targetType}','${referenceType}')">
                GO TO CHARGES
            </button>
        </div>
    </div>
    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title"><span class="fa fa-recycle"></span> Tracking Details</h3>
            <ul class="panel-controls">
                <li><a onclick="view_hide('trackingDataPanel')" class="panel-collapse">
                    <span class="fa fa-angle-down"></span></a></li>
            </ul>
            <input class="hidden" name="transportBookingSeq"
                   value="${booking.transportBookingSeq}"/>
        </div>
        <div class="panel-body" id="trackingDataPanel">
            <form class="form-horizontal trackingDataForm" id="trackingDataForm" method="post">
                <div class="panel panel-warning">
                    <div class="panel-body" id="trackingData">
                        <div class="col-md-4">
                            <div class="form-group">
                                <label class="col-md-4 col-xs-12 control-label">Vehicle</label>
                                <div class="col-md-7 col-xs-12">
                                    <h5 class="h5-top-margin-7">
                                        <label>${booking.transportBookingVehicleList[0].vehicle.vehicleNo}</label></h5>
                                </div>
                                <input class="hidden" name="transportBookingSeq"
                                       value="${booking.transportBookingSeq}"/>
                            </div>
                            <div class="form-group">
                                <label class="col-md-4 col-xs-12 control-label">Arrival Confirmation</label>
                                <div class="col-md-7 col-xs-12">
                                    <h5 class="h5-top-margin-7"><label
                                            <c:if test="${booking.transportBookingStatus.timeUpdate == 1}">
                                                class="arrivalConfirmation"
                                            </c:if>>${booking.transportBookingFeedback.arrivalConfirmationDescription}</label>
                                    </h5>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-4 col-xs-12 control-label">Arrived at Pickup</label>
                                <div class="col-md-7 col-xs-12">
                                    <h5 class="h5-top-margin-7"><label class="AAP"><fmt:formatDate
                                            value="${booking.transportBookingFeedback.arrivedAtPickup}"
                                            pattern="yyyy-MM-dd hh:mm a"/></label></h5>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-4 col-xs-12 control-label">Departed from Pickup</label>
                                <div class="col-md-7 col-xs-12">
                                    <h5 class="h5-top-margin-7"><label
                                            <c:if test="${booking.transportBookingStatus.timeUpdate == 1}">
                                                class="DFP"
                                            </c:if>><fmt:formatDate
                                            value="${booking.transportBookingFeedback.departedFromPickup}"
                                            pattern="yyyy-MM-dd hh:mm a"/></label></h5>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-4 col-xs-12 control-label">Arrived at Delivery</label>
                                <div class="col-md-7 col-xs-12">
                                    <h5 class="h5-top-margin-7"><label
                                            <c:if test="${booking.transportBookingStatus.timeUpdate == 1}">class="AAD"
                                    </c:if>><fmt:formatDate
                                            value="${booking.transportBookingFeedback.arrivedAtDelivery}"
                                            pattern="yyyy-MM-dd hh:mm a"/></label></h5>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-4 col-xs-12 control-label">Departed from Delivery</label>
                                <div class="col-md-7 col-xs-12">
                                    <h5 class="h5-top-margin-7"><label
                                            <c:if test="${booking.transportBookingStatus.timeUpdate == 1}">
                                                class="DFD" </c:if>><fmt:formatDate
                                            value="${booking.transportBookingFeedback.departedFromDelivery}"
                                            pattern="yyyy-MM-dd hh:mm a"/></label></h5>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <label class="col-md-4 col-xs-12 control-label">Driver</label>
                                <div class="col-md-7 col-xs-12">
                                    <h5 class="h5-top-margin-7">
                                        <label>${booking.transportBookingVehicleList[0].driver.employeeName} ${booking.transportBookingVehicleList[0].driver.addressBook.mobile}</label>
                                    </h5>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-4 col-xs-12 control-label">Helper</label>
                                <div class="col-md-7 col-xs-12">
                                    <h5 class="h5-top-margin-7"><label>${booking.transportBookingVehicleList[0].helper.employeeName}</label></h5>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-4 col-xs-12 control-label">Connecting Job</label>
                                <div class="col-md-7 col-xs-12">
                                    <select name="connectingTransportBookingSeq"
                                            id="connectingTransportBookingSeq"
                                            data-live-search="true"
                                            class="form-control select"
                                            data-validate="true">
                                        <option value="">None</option>
                                        <c:forEach items="${connectingBookingList}" var="connectingBooking">
                                            <option value="${connectingBooking.transportBookingSeq}"
                                                    <c:if test="${booking.transportBookingFeedback.connectingTransportBookingSeq eq connectingBooking.transportBookingSeq}"> selected </c:if>
                                            >${connectingBooking.bookingNo}
                                                - ${connectingBooking.pickupLocation.destination}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-4 col-xs-12 control-label">Cargo in Hand km</label>
                                <div class="col-md-7 col-xs-12">
                                    <input type="text"
                                           class="form-control"
                                           id="cargoInHandKm"
                                           name="cargoInHandKm"
                                           pattern="^[0-9]*(.[0-9]*)?([eE][-+][0-9]*)?$"
                                           maxlength="7"
                                           value="${booking.transportBookingFeedback.cargoInHandKm == null ? booking.estimatedKm: booking.transportBookingFeedback.cargoInHandKm}"
                                           onchange="validateFloatKeyPress(this,2)"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-4 col-xs-12 control-label">Chargeable km</label>
                                <div class="col-md-7 col-xs-12">
                                    <h5 class="h5-top-margin-7">
                                        <label rv-text="transportBookingFeedback.chargeableKm">${booking.transportBookingFeedback.chargeableKm}</label>
                                    </h5>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <label class="col-md-4 col-xs-12 control-label">Transporter</label>
                                <div class="col-md-7 col-xs-12">
                                    <h5 class="h5-top-margin-7">
                                        <label>${booking.transportBookingVehicleList[0].transportCompany.stakeholderName}</label>
                                    </h5>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-4 col-xs-12 control-label">Start Meter Reading</label>
                                <div class="col-md-7 col-xs-12">
                                    <input type="text"
                                           class="form-control"
                                           id="startMeterReading"
                                           name="startMeterReading"
                                           pattern="^[0-9]*(.[0-9]*)?([eE][-+][0-9]*)?$"
                                           maxlength="7"
                                           value="${booking.transportBookingFeedback.startMeterReading}"
                                           onchange="validateIntegerKeyPress(this)"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-4 col-xs-12 control-label">End Meter Reading</label>
                                <div class="col-md-7 col-xs-12">
                                    <input type="text"
                                           class="form-control"
                                           id="endMeterReading"
                                           name="endMeterReading"
                                           pattern="^[0-9]*(.[0-9]*)?([eE][-+][0-9]*)?$"
                                           maxlength="7"
                                           value="${booking.transportBookingFeedback.endMeterReading}"
                                           onchange="validateIntegerKeyPress(this)"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-4 col-xs-12 control-label">Placement Km</label>
                                <div class="col-md-7 col-xs-12">
                                    <input type="text"
                                           class="form-control"
                                           id="placementKm"
                                           name="placementKm"
                                           pattern="^[0-9]*(.[0-9]*)?([eE][-+][0-9]*)?$"
                                           maxlength="7"
                                           value="${booking.transportBookingFeedback.placementKm}"
                                           onchange="validateFloatKeyPress(this,2)"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-4 col-xs-12 control-label">Remarks</label>
                                <div class="col-md-7 col-xs-12">
                                    <textarea class="form-control vertical-resize"
                                              rows="3"
                                              id="operationRemarks"
                                              name="operationRemarks"
                                              maxlength="500">${booking.transportBookingFeedback.operationRemarks}</textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <button type="button" class="btn btn-primary pull-right"
                                        <sec:authorize
                                                access="!hasRole('ROLE_transport@transportBookingSummary_VIEW')">
                                            disabled="disabled"
                                        </sec:authorize>
                                        onclick="save_tracking_data()">Submit
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>

</div>
