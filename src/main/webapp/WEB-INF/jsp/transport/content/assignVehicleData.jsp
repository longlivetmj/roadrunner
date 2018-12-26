<%@ include file="/WEB-INF/jsp/include.jsp" %>
<div class="col-md-4">
    <input type="hidden" id="transportBookingSeq" name="transportBookingSeq" value="${booking.transportBookingSeq}"/>
    <input type="hidden" id="transportBookingVehicleSeq" value="${booking.transportBookingVehicleList[0].transportBookingVehicleSeq}"/>
    <div class="form-group">
        <label class="col-md-3 col-xs-12 control-label">Transporter</label>
        <div class="col-md-8 col-xs-12">
            <select data-live-search="true"
                    name="transportCompanySeq" id="transportCompanySeq"
                    data-validate="true"
                    aria-required="true"
                    class="form-control ajax-select"
                    data-abs-ajax-type="get"
                    data-abs-request-delay="500"
                    data-key="stakeholderSeq"
                    data-value="stakeholderName"
                    data-subText="stakeholderCode"
                    title="Select and begin typing"
                    required
                    onchange="load_vehicle_list_by_transporter()"
                    data-abs-ajax-url="transport/vehicleAssignment/findTransporter">
                <c:if test="${booking.transportBookingVehicleList ne null}">
                    <option value="${booking.transportBookingVehicleList[0].transportCompanySeq}" selected>${booking.transportBookingVehicleList[0].transportCompany.stakeholderName}</option>
                </c:if>
                <c:if test="${defaultTransporter ne null && booking.transportBookingVehicleList[0] eq null}">
                    <option value="${defaultTransporter.stakeholderSeq}" selected>${defaultTransporter.stakeholderName}</option>
                </c:if>
            </select>
            <span class="help-block">Required, Transporter</span>
        </div>
    </div>
    <div class="form-group">
        <label class="col-md-3 col-xs-12 control-label">Vehicle</label>
        <div class="col-md-8 col-xs-12">
            <select name="vehicleSeq"
                    id="vehicleSeq"
                    data-live-search="true"
                    class="form-control select"
                    required
                    data-live-search="false"
                    data-validate="true">
                <c:if test="${booking.transportBookingVehicleList ne null}">
                    <option value="${booking.transportBookingVehicleList[0].vehicleSeq}" selected>${booking.transportBookingVehicleList[0].vehicle.vehicleNo}</option>
                </c:if>
            </select>
            <span class="help-block">Required, Vehicle</span>
        </div>
    </div>
    <div class="form-group">
        <label class="col-md-3 col-xs-12 control-label">Driver</label>
        <div class="col-md-8 col-xs-12">
            <select name="driverSeq"
                    id="driverSeq"
                    data-live-search="true"
                    class="form-control select"
                    required
                    data-live-search="false"
                    data-validate="true">
                <c:if test="${booking.transportBookingVehicleList ne null}">
                    <option value="${booking.transportBookingVehicleList[0].driverSeq}" selected>${booking.transportBookingVehicleList[0].driver.employeeName}</option>
                </c:if>
            </select>
            <span class="help-block">Required, Driver</span>
        </div>
    </div>
    <div class="form-group">
        <label class="col-md-3 col-xs-12 control-label">Helper</label>
        <div class="col-md-8 col-xs-12">
            <select name="helperSeq"
                    id="helperSeq"
                    data-live-search="true"
                    class="form-control select"
                    data-live-search="false"
                    data-validate="true">
                <c:if test="${booking.transportBookingVehicleList ne null}">
                    <option value="${booking.transportBookingVehicleList[0].helperSeq}" selected>${booking.transportBookingVehicleList[0].helper.employeeName}</option>
                </c:if>
            </select>
        </div>
    </div>
    <div class="form-group updatePanel">
        <label class="col-md-3 col-xs-12 control-label" for="remarks">Remarks</label>
        <div class="col-md-8 col-xs-12"><textarea class="form-control vertical-resize"
                                                  rows="3"
                                                  id="remarks"
                                                  name="remarks"
                                                  maxlength="500">${booking.transportBookingVehicleList[0].remarks}</textarea>
        </div>
    </div>
    <div class="form-group updateOperation">
        <label class="col-md-4 text-right">Created By</label>
        <label class="col-md-8"
               title="${booking.transportBookingVehicleList[0].createdBy}">${booking.transportBookingVehicleList[0].createdBy}</label>
    </div>
    <div class="form-group updateOperation">
        <label class="col-md-4 text-right">Created Date</label>
        <label class="col-md-8">
            <fmt:formatDate value="${booking.transportBookingVehicleList[0].createdDate}"
                            pattern="yyyy-MM-dd hh:mm a"/>
        </label>
    </div>
    <div class="form-group updateOperation">
        <label class="col-md-4 text-right">Last Modified By</label>
        <label class="col-md-8"
               title="${booking.transportBookingVehicleList[0].lastModifiedBy}">${booking.transportBookingVehicleList[0].lastModifiedBy}</label>
    </div>
    <div class="form-group updateOperation">
        <label class="col-md-4 text-right">Last Modified
            Date</label>
        <label class="col-md-8">
            <fmt:formatDate value="${booking.transportBookingVehicleList[0].lastModifiedDate}"
                            pattern="yyyy-MM-dd hh:mm a"/>
        </label>
    </div>
</div>
<div class="col-md-4">
    <div class="form-group">
        <label class="col-md-5 col-xs-12 control-label">Booking No</label>
        <div class="col-md-7 col-xs-12">
            <h5 class=".h5-top-margin-7"><label>${booking.bookingNo}</label></h5>
        </div>
    </div>
    <div class="form-group">
        <label class="col-md-5 col-xs-12 control-label">Job No</label>
        <div class="col-md-7 col-xs-12">
            <h5 class=".h5-top-margin-7"><label>${booking.job.jobNo}</label></h5>
        </div>
    </div>
    <div class="form-group">
        <label class="col-md-5 col-xs-12 control-label">Customer</label>
        <div class="col-md-7 col-xs-12">
            <h5 class=".h5-top-margin-7"><label>${booking.customer.stakeholderName}</label></h5>
        </div>
    </div>
    <div class="form-group">
        <label class="col-md-5 col-xs-12 control-label">Pickup Location</label>
        <div class="col-md-7 col-xs-12">
            <h5 class=".h5-top-margin-7"><label>${booking.pickupLocation.destination}</label></h5>
        </div>
    </div>
    <div class="form-group">
        <label class="col-md-5 col-xs-12 control-label">Arrival Time</label>
        <div class="col-md-7 col-xs-12">
            <h5 class=".h5-top-margin-7"><label><fmt:formatDate value="${booking.requestedArrivalTime}" pattern="yyyy-MM-dd hh:mm a"/></label></h5>
        </div>
    </div>
    <div class="form-group">
        <label class="col-md-5 col-xs-12 control-label">Estimated Km</label>
        <div class="col-md-7 col-xs-12">
            <h5 class=".h5-top-margin-7"><label>${booking.estimatedKm}</label></h5>
        </div>
    </div>
</div>
<div class="col-md-4">
    <div class="form-group">
        <label class="col-md-5 col-xs-12 control-label">Vehicle Type</label>
        <div class="col-md-7 col-xs-12">
            <h5 class=".h5-top-margin-7"><label>${booking.vehicleType.vehicleTypeName}</label></h5>
        </div>
    </div>
    <div class="form-group">
        <label class="col-md-5 col-xs-12 control-label">Delivery Location</label>
        <div class="col-md-7 col-xs-12">
            <h5 class=".h5-top-margin-7"><label>${booking.deliveryLocation.destination}</label></h5>
        </div>
    </div>
    <div class="form-group">
        <label class="col-md-5 col-xs-12 control-label">Delivery Time</label>
        <div class="col-md-7 col-xs-12">
            <h5 class=".h5-top-margin-7"><label><fmt:formatDate value="${booking.requestedDeliveryTime}" pattern="yyyy-MM-dd hh:mm a"/></label></h5>
        </div>
    </div>
    <div class="form-group">
        <label class="col-md-5 col-xs-12 control-label">Via Locations</label>
        <div class="col-md-7 col-xs-12">
            <h5 class=".h5-top-margin-7"><label>${booking.viaLocationString}</label></h5>
        </div>
    </div>
    <div class="form-group">
        <label class="col-md-5 col-xs-12 control-label">Customer Ref.</label>
        <div class="col-md-7 col-xs-12">
            <h5 class=".h5-top-margin-7"><label>${booking.customerReferenceNo}</label></h5>
        </div>
    </div>
    <div class="form-group">
        <button type="button" class="btn btn-success pull-right" onclick="loadDriverScorecard()">Driver Scorecard</button>
    </div>
    <div class="form-group">
        <button type="button" class="btn btn-primary pull-right" onclick="loadHelperScorecard()">Helper Scorecard</button>
    </div>
</div>
