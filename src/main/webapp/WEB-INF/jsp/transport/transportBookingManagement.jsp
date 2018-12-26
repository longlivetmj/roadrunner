<%@ include file="/WEB-INF/jsp/include.jsp" %>
<script type="text/javascript" src="../../../customjs/transport/transportBookingManagement.js"></script>
<div class="page-title">
    <h3><span class="fa fa-user"></span>Transport Booking</h3>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default tabs">
            <ul class="nav nav-tabs" role="tablist">
                <sec:authorize access="hasRole('ROLE_transport@transportBookingManagement_VIEW')">
                    <li class="active"><a href="#tab-first" role="tab" data-toggle="tab" id="generalInfo">Booking
                        Details</a></li>
                </sec:authorize>
                <sec:authorize access="hasRole('ROLE_transport@transportBookingManagement_CREATE')">
                    <li class="updateOperation"><a href="#tab-second" role="tab" data-toggle="tab" id="viaLocations"
                                                   style="display: none">Via Locations</a></li>
                </sec:authorize>
            </ul>
            <div class="panel-body tab-content">
                <div class="tab-pane active" id="tab-first">
                    <form class="form-horizontal createBooking" id="createBooking" method="post">
                        <div class="col-md-12">
                            <div class="form-group removeFromModal">
                                <div class="col-md-3 operations" style="display: none">
                                    <button type="button" class="btn btn-success"
                                            onclick="load_booking_page()">
                                        New
                                    </button>
                                    <button type="button" class="btn btn-primary"
                                            onclick="copy_booking()">
                                        Copy
                                    </button>
                                    <button type="button"
                                            id="deleteButton"
                                            class="btn btn-danger"
                                            <sec:authorize
                                                    access="!hasRole('ROLE_transport@transportBookingManagement_DELETE')">
                                                disabled="disabled"
                                            </sec:authorize>
                                            data-toggle=confirmation
                                            data-btn-ok-label="Continue"
                                            data-btn-ok-icon="glyphicon glyphicon-share-alt"
                                            data-btn-ok-class="btn-success"
                                            data-btn-cancel-label="Cancel"
                                            data-btn-cancel-icon="glyphicon glyphicon-ban-circle"
                                            data-btn-cancel-class="btn-danger"
                                            data-title="Deleting Booking"
                                            data-content="Are you sure you want to Delete this Booking ? "
                                            data-id="createBooking"
                                            data-on-confirm="delete_booking">
                                        Cancel
                                    </button>
                                </div>
                            </div>
                            <div class="panel panel-warning">
                                <div class="panel-body" id="booking_info">
                                    <div class="hidden" id="hidden_ids">
                                        <input class="hidden" name="transportBookingSeq"
                                               value="${booking.transportBookingSeq}"
                                               rv-value="transportBooking.transportBookingSeq"
                                               id="transportBookingSeq"/>
                                        <input class="hidden" name="companyProfileSeq" id="companyProfileSeq"
                                               value="${booking.companyProfileSeq}"
                                               rv-value="transportBooking.companyProfileSeq"/>
                                        <input class="hidden" name="moduleSeq" id="moduleSeq"
                                               value="${booking.moduleSeq}"
                                               rv-value="transportBooking.moduleSeq"/>
                                        <input type="hidden" id="currentStatus" name="currentStatus"
                                               value="${booking.currentStatus}"
                                               rv-value="transportBooking.currentStatus"/>
                                        <input type="hidden" id="currentStatusDescription"
                                               value="${booking.transportBookingStatus.currentStatusDescription}"
                                               rv-value="transportBooking.transportBookingStatus.currentStatusDescription"/>
                                    </div>

                                    <div class="updateOperation" style="display: none">
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label class="col-md-3 col-xs-12 control-label">Booking No</label>
                                                <div class="col-md-8 col-xs-12">
                                                    <h3 class="h3-top-margin-5"><label rv-text="transportBooking.bookingNo">${booking.bookingNo}</label></h3>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label class="col-md-3 col-xs-12 control-label">Job No</label>
                                                <div class="col-md-8 col-xs-12">
                                                    <h3 class="h3-top-margin-5"><label rv-text="transportBooking.job.jobNo">${booking.job.jobNo}</label></h3>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div id="general_info">
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label class="col-md-3 col-xs-12 control-label" for="departmentSeq">Department</label>
                                                <div class="col-md-8 col-xs-12">
                                                    <select name="departmentSeq"
                                                            id="departmentSeq"
                                                            rv-value="transportBooking.departmentSeq"
                                                            class="form-control select"
                                                            data-live-search="true"
                                                            data-validate="true"
                                                            data-search-placeholder="Required, Department"
                                                            aria-required="true"
                                                            title="Select Local Department">
                                                        <c:forEach items="${departmentList}" var="department">
                                                            <option value="${department.departmentSeq}" ${(booking.departmentSeq eq department.departmentSeq) ? 'selected' : ''}>${department.departmentName}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 col-xs-12 control-label" for="customerSeq">Customer</label>
                                                <div class="col-md-8 col-xs-12">
                                                    <select name="customerSeq" id="customerSeq"
                                                            class="form-control ajax-select"
                                                            data-validate="true"
                                                            required
                                                            data-shipper-consignee
                                                            data-abs-ajax-type="get"
                                                            data-abs-request-delay="500"
                                                            data-key="stakeholderSeq"
                                                            data-value="stakeholderName"
                                                            data-subText="stakeholderCode"
                                                            data-abs-ajax-url="transport/transportBookingManagement/findCustomer"
                                                            data-live-search="true"
                                                            onchange="set_shipper_invoice_customer()"
                                                            title="Required, Shipper">
                                                        <option rv-value="transportBooking.customerSeq"
                                                                rv-text="transportBooking.customer.stakeholderName"
                                                                data-subtext="${booking.customer.stakeholderCode}"
                                                                rv-setselectedattr="transportBooking.customerSeq"
                                                                value="${booking.customerSeq}"
                                                        ${booking.customerSeq ne null ? 'selected' : ''}>${booking.customer.stakeholderName}</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 col-xs-12 control-label" for="shipperSeq">Shipper</label>
                                                <div class="col-md-8 col-xs-12">
                                                    <select name="shipperSeq" id="shipperSeq"
                                                            class="form-control ajax-select"
                                                            data-validate="true"
                                                            required
                                                            data-shipper-consignee
                                                            data-abs-ajax-type="get"
                                                            data-abs-request-delay="500"
                                                            data-key="stakeholderSeq"
                                                            data-value="stakeholderName"
                                                            data-subText="stakeholderCode"
                                                            data-abs-ajax-url="transport/transportBookingManagement/findShipper"
                                                            data-live-search="true"
                                                            title="Required, Shipper">
                                                        <option rv-value="transportBooking.shipperSeq"
                                                                rv-text="transportBooking.shipper.stakeholderName"
                                                                data-subtext="${booking.shipper.stakeholderCode}"
                                                                rv-setselectedattr="transportBooking.shipperSeq"
                                                                value="${booking.shipperSeq}"
                                                        ${booking.shipperSeq ne null ? 'selected' : ''}>${booking.shipper.stakeholderName}</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 col-xs-12 control-label" for="customerReferenceNo">Customer Reference
                                                    No</label>
                                                <div class="col-md-8 col-xs-12">
                                                    <input type="text"
                                                           class="form-control"
                                                           id="customerReferenceNo"
                                                           name="customerReferenceNo"
                                                           rv-value="transportBooking.customerReferenceNo"
                                                           value="${booking.customerReferenceNo}"
                                                           title="Customer Reference No"
                                                           maxlength="25"/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 col-xs-12 control-label" for="pickupLocationSeq">Pickup location</label>
                                                <div class="col-md-8 col-xs-12">
                                                    <select name="pickupLocationSeq"
                                                            id="pickupLocationSeq"
                                                            class="form-control ajax-select"
                                                            data-validate="true"
                                                            data-abs-ajax-type="get"
                                                            data-abs-request-delay="500"
                                                            data-key="finalDestinationSeq"
                                                            data-value="destination"
                                                            data-subText="location.locationName"
                                                            data-abs-ajax-url="transport/transportBookingManagement/findFinalDestination"
                                                            data-live-search="true"
                                                            placeholder="Pickup"
                                                            title="Required, Pickup location"
                                                            onchange="load_pick_address_data()"
                                                            required>
                                                        <option rv-value="transportBooking.pickupLocationSeq"
                                                                rv-text="transportBooking.pickupLocation.destination"
                                                                data-subText="${booking.pickupLocation.location.locationName}"
                                                                rv-setselectedattr="transportBooking.pickupLocationSeq"
                                                                value="${booking.pickupLocationSeq}"
                                                        ${booking.pickupLocationSeq ne null ? 'selected' : ''}>${booking.pickupLocation.destination}</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 col-xs-12 control-label" for="pickupLocationAddress">Pickup Address</label>
                                                <div class="col-md-8 col-xs-12">
                                        <textarea class="form-control vertical-resize"
                                                  rows="3"
                                                  rv-value="transportBooking.pickupLocationAddress"
                                                  id="pickupLocationAddress"
                                                  name="pickupLocationAddress"
                                                  maxlength="256">${booking.pickupLocationAddress}</textarea>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label col-md-3 text-right" for="requestedArrivalTime">Requested Arrival
                                                    Date</label>
                                                <div class="col-md-8">
                                                    <input type="text" class="form-control datetimepicker"
                                                           id="requestedArrivalTime"
                                                           rv-value="transportBooking.requestedArrivalTime"
                                                           name="requestedArrivalTime"
                                                           value="<fmt:formatDate value='${booking.requestedArrivalTime}' pattern='yyyy-MM-dd hh:mm a'/>"/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 col-xs-12 control-label" for="pickupContactPerson">Pickup Contact
                                                    Person</label>
                                                <div class="col-md-8 col-xs-12">
                                                    <input type="text"
                                                           class="form-control"
                                                           id="pickupContactPerson"
                                                           name="pickupContactPerson"
                                                           rv-value="transportBooking.pickupContactPerson"
                                                           value="${booking.pickupContactPerson}"
                                                           title="Pickup Contact Person"
                                                           maxlength="200"/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 col-xs-12 control-label" for="pickupContactNo">Pickup Contact No</label>
                                                <div class="col-md-8 col-xs-12">
                                                    <input type="text"
                                                           class="form-control"
                                                           id="pickupContactNo"
                                                           name="pickupContactNo"
                                                           rv-value="transportBooking.pickupContactNo"
                                                           value="${booking.pickupContactNo}"
                                                           title="Pickup Contact No"
                                                           maxlength="20"/>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label class="col-md-3 col-xs-12 control-label" for="invoiceCustomerSeq">Invoice To</label>
                                                <div class="col-md-8 col-xs-12">
                                                    <select name="invoiceCustomerSeq" id="invoiceCustomerSeq"
                                                            class="form-control ajax-select"
                                                            data-validate="true"
                                                            required
                                                            data-shipper-consignee
                                                            data-abs-ajax-type="get"
                                                            data-abs-request-delay="500"
                                                            data-key="stakeholderSeq"
                                                            data-value="stakeholderName"
                                                            data-subText="stakeholderCode"
                                                            data-abs-ajax-url="transport/transportBookingManagement/findCustomer"
                                                            data-live-search="true"
                                                            title="Required, Shipper">
                                                        <option rv-value="transportBooking.invoiceCustomerSeq"
                                                                rv-text="transportBooking.invoiceCustomer.stakeholderName"
                                                                data-subtext="${booking.invoiceCustomer.stakeholderCode}"
                                                                rv-setselectedattr="transportBooking.invoiceCustomerSeq"
                                                                value="${booking.invoiceCustomerSeq}"
                                                        ${booking.invoiceCustomerSeq ne null ? 'selected' : ''}>${booking.invoiceCustomer.stakeholderName}</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 col-xs-12 control-label" for="consigneeSeq">Consignee</label>
                                                <div class="col-md-8 col-xs-12">
                                                    <select name="consigneeSeq" id="consigneeSeq"
                                                            class="form-control ajax-select"
                                                            data-validate="true"
                                                            data-abs-ajax-type="get"
                                                            data-abs-request-delay="500"
                                                            data-key="stakeholderSeq"
                                                            data-value="stakeholderName"
                                                            data-subtext="stakeholderCode"
                                                            data-abs-ajax-url="transport/transportBookingManagement/findConsignee"
                                                            data-live-search="true"
                                                            onchange="load_consignee_data()"
                                                            title="Consignee Name">
                                                        <option rv-value="transportBooking.consigneeSeq"
                                                                rv-text="transportBooking.consignee.stakeholderName"
                                                                data-subtext="${booking.consignee.stakeholderCode}"
                                                                rv-setselectedattr="transportBooking.consigneeSeq"
                                                                value="${booking.consigneeSeq}"
                                                        ${booking.consigneeSeq ne null ? 'selected' : ''}>${booking.consignee.stakeholderName}</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 col-xs-12 control-label" for="vehicleTypeSeq">Vehicle Type</label>
                                                <div class="col-md-8 col-xs-12">
                                                    <select name="vehicleTypeSeq"
                                                            id="vehicleTypeSeq"
                                                            class="form-control ajax-select"
                                                            data-validate="true"
                                                            data-live-search="true"
                                                            placeholder="Vehicle Type"
                                                            title="Required, Vehicle Type"
                                                            required>
                                                        <option rv-value="transportBooking.vehicleTypeSeq"
                                                                rv-text="transportBooking.vehicleType.vehicleTypeName"
                                                                data-subText="${booking.vehicleType.vehicleTypeName}"
                                                                rv-setselectedattr="transportBooking.vehicleTypeSeq"
                                                                value="${booking.vehicleTypeSeq}"
                                                        ${booking.vehicleTypeSeq ne null ? 'selected' : ''}>${booking.vehicleType.vehicleTypeName}</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 col-xs-12 control-label" for="containerTypeSeq">Container Type</label>
                                                <div class="col-md-8 col-xs-12">
                                                    <select name="containerTypeSeq"
                                                            id="containerTypeSeq"
                                                            class="form-control ajax-select"
                                                            data-validate="true"
                                                            data-abs-ajax-type="get"
                                                            data-abs-request-delay="500"
                                                            data-key="containerTypeSeq"
                                                            data-value="containerTypeName"
                                                            data-abs-ajax-url="transport/transportBookingManagement/findContainerType"
                                                            data-live-search="true"
                                                            placeholder="Container Type">
                                                        <option rv-value="transportBooking.containerTypeSeq"
                                                                rv-text="transportBooking.containerType.containerTypeName"
                                                                data-subText="${booking.containerType.containerTypeName}"
                                                                rv-setselectedattr="transportBooking.containerTypeSeq"
                                                                value="${booking.containerTypeSeq}"
                                                        ${booking.containerTypeSeq ne null ? 'selected' : ''}>${booking.containerType.containerTypeName}</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 col-xs-12 control-label" for="deliveryLocationSeq">Delivery location</label>
                                                <div class="col-md-8 col-xs-12">
                                                    <select name="deliveryLocationSeq"
                                                            id="deliveryLocationSeq"
                                                            class="form-control ajax-select"
                                                            data-validate="true"
                                                            data-abs-ajax-type="get"
                                                            data-abs-request-delay="500"
                                                            data-key="finalDestinationSeq"
                                                            data-value="destination"
                                                            data-subText="location.locationName"
                                                            data-abs-ajax-url="transport/transportBookingManagement/findFinalDestination"
                                                            data-live-search="true"
                                                            placeholder="delivery"
                                                            onchange="load_delivery_address_data()"
                                                            title="Required, delivery location"
                                                            required>
                                                        <option rv-value="transportBooking.deliveryLocationSeq"
                                                                rv-text="transportBooking.deliveryLocation.destination"
                                                                data-subText="${booking.deliveryLocation.location.locationName}"
                                                                rv-setselectedattr="transportBooking.deliveryLocationSeq"
                                                                value="${booking.deliveryLocationSeq}"
                                                        ${booking.deliveryLocationSeq ne null ? 'selected' : ''}>${booking.deliveryLocation.destination}</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 col-xs-12 control-label" for="deliveryLocationAddress">Delivery Address</label>
                                                <div class="col-md-8 col-xs-12">
                                        <textarea class="form-control vertical-resize"
                                                  rows="3"
                                                  rv-value="transportBooking.deliveryLocationAddress"
                                                  id="deliveryLocationAddress"
                                                  name="deliveryLocationAddress"
                                                  maxlength="256">${booking.deliveryLocationAddress}</textarea>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label col-md-3 text-right" for="requestedDeliveryTime">Requested Delivery
                                                    Date</label>
                                                <div class="col-md-8">
                                                    <input type="text" class="form-control datetimepicker"
                                                           id="requestedDeliveryTime"
                                                           rv-value="transportBooking.requestedDeliveryTime"
                                                           name="requestedDeliveryTime"
                                                           value="<fmt:formatDate value='${booking.requestedDeliveryTime}' pattern='yyyy-MM-dd hh:mm a'/>"/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 col-xs-12 control-label" for="deliveryContactPerson">Delivery Contact
                                                    Person</label>
                                                <div class="col-md-8 col-xs-12">
                                                    <input type="text"
                                                           class="form-control"
                                                           id="deliveryContactPerson"
                                                           name="deliveryContactPerson"
                                                           rv-value="transportBooking.deliveryContactPerson"
                                                           value="${booking.deliveryContactPerson}"
                                                           title="Pickup Contact Person"
                                                           maxlength="200"/>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 col-xs-12 control-label" for="deliveryContactNo">Delivery Contact No</label>
                                                <div class="col-md-8 col-xs-12">
                                                    <input type="text"
                                                           class="form-control"
                                                           id="deliveryContactNo"
                                                           name="deliveryContactNo"
                                                           rv-value="transportBooking.deliveryContactNo"
                                                           value="${booking.deliveryContactNo}"
                                                           title="Pickup Contact No"
                                                           maxlength="20"/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="panel panel-warning">
                                <div class="panel-body" id="basicDetails">
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label" for="uowSeq">Actual Start</label>
                                            <div class="col-md-8 col-xs-12">
                                                <select name="actualStartLocationSeq"
                                                        id="actualStartLocationSeq"
                                                        class="form-control ajax-select"
                                                        data-validate="true"
                                                        data-abs-ajax-type="get"
                                                        data-abs-request-delay="500"
                                                        data-key="finalDestinationSeq"
                                                        data-value="destination"
                                                        data-subText="location.locationName"
                                                        data-abs-ajax-url="transport/transportBookingManagement/findFinalDestination"
                                                        data-live-search="true"
                                                        placeholder="Actual Start">
                                                    <option rv-value="transportBooking.actualStartLocationSeq"
                                                            rv-text="transportBooking.actualStartLocation.destination"
                                                            data-subText="${booking.actualStartLocation.location.locationName}"
                                                            rv-setselectedattr="transportBooking.actualStartLocationSeq"
                                                            value="${booking.actualStartLocationSeq}"
                                                    ${booking.actualStartLocationSeq ne null ? 'selected' : ''}>${booking.actualStartLocation.destination}</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label" for="uowSeq">Unit of Weight</label>
                                            <div class="col-md-8 col-xs-12">
                                                <select class="form-control select"
                                                        name="uowSeq"
                                                        id="uowSeq"
                                                        rv-aftersave="transportBooking.uowSeq"
                                                        rv-value="transportBooking.uowSeq"
                                                        data-live-search="true">
                                                    <option value="">Select</option>
                                                    <c:forEach items="${weightUnitList}" var="weightUnit">
                                                        <option value="${weightUnit.unitSeq}" ${weightUnit.unitSeq eq booking.uowSeq ? 'selected' : ''}>${weightUnit.unitCode}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label" for="uovSeq">Unit of Volume</label>
                                            <div class="col-md-8 col-xs-12">
                                                <select class="form-control select"
                                                        name="uovSeq"
                                                        id="uovSeq"
                                                        rv-value="transportBooking.uovSeq"
                                                        data-live-search="true">
                                                    <c:forEach items="${volumeUnitList}" var="volumeUnit">
                                                        <option value="${volumeUnit.unitSeq}" ${volumeUnit.unitSeq eq booking.uovSeq ? 'selected' : ''}>${volumeUnit.unitCode}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label" for="packageTypeSeq">Package Type</label>
                                            <div class="col-md-8 col-xs-12">
                                                <select class="form-control select"
                                                        name="packageTypeSeq"
                                                        id="packageTypeSeq"
                                                        rv-value="transportBooking.packageTypeSeq"
                                                        data-live-search="true">
                                                    <c:forEach items="${packageTypeList}" var="packageType">
                                                        <option value="${packageType.packageTypeSeq}"
                                                                data-subText="${packageType.packageTypeCode}"
                                                            ${packageType.packageTypeSeq eq booking.packageTypeSeq ? 'selected' : ''}>${packageType.packageTypeName}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label" for="uowSeq">Actual End</label>
                                            <div class="col-md-8 col-xs-12">
                                                <select name="actualEndLocationSeq"
                                                        id="actualEndLocationSeq"
                                                        class="form-control ajax-select"
                                                        data-validate="true"
                                                        data-abs-ajax-type="get"
                                                        data-abs-request-delay="500"
                                                        data-key="finalDestinationSeq"
                                                        data-value="destination"
                                                        data-subText="location.locationName"
                                                        data-abs-ajax-url="transport/transportBookingManagement/findFinalDestination"
                                                        data-live-search="true"
                                                        placeholder="Acutal End">
                                                    <option rv-value="transportBooking.actualEndLocationSeq"
                                                            rv-text="transportBooking.actualEndLocation.destination"
                                                            data-subText="${booking.actualEndLocation.location.locationName}"
                                                            rv-setselectedattr="transportBooking.actualEndLocationSeq"
                                                            value="${booking.actualEndLocationSeq}"
                                                    ${booking.actualEndLocationSeq ne null ? 'selected' : ''}>${booking.actualEndLocation.destination}</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label" for="invoiceStatus">Invoicing State</label>
                                            <div class="col-md-8 col-xs-12">
                                                <select name="invoiceStatus"
                                                        id="invoiceStatus"
                                                        data-live-search="true"
                                                        class="form-control select"
                                                        rv-value="transportBooking.invoiceStatus"
                                                        rv-aftersave="transportBooking.invoiceStatus"
                                                        data-validate="true">
                                                    <c:forEach items="${trueOrFalse}" var="trueFalseCalue">
                                                        <option value="${trueFalseCalue.trueOrFalseSeq}" ${booking.invoiceStatus  eq trueFalseCalue.trueOrFalseSeq ? 'selected' : ''}>${trueFalseCalue.trueOrFalse}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label" for="cashOrCredit">Cash or Credit</label>
                                            <div class="col-md-8 col-xs-12">
                                                <select name="cashOrCredit"
                                                        id="cashOrCredit"
                                                        data-live-search="true"
                                                        class="form-control select"
                                                        rv-value="transportBooking.cashOrCredit"
                                                        rv-aftersave="transportBooking.cashOrCredit"
                                                        data-validate="true">
                                                    <c:forEach items="${cashCreditList}" var="cashCredit">
                                                        <option value="${cashCredit.stakeholderCashTypeSeq}" ${booking.cashOrCredit  eq cashCredit.stakeholderCashTypeSeq ? 'selected' : ''}>${cashCredit.stakeholderCashType}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label" for="paymentMode">Payment Mode</label>
                                            <div class="col-md-8 col-xs-12">
                                                <select name="paymentMode"
                                                        id="paymentMode"
                                                        data-live-search="true"
                                                        class="form-control select"
                                                        rv-value="transportBooking.paymentMode"
                                                        rv-aftersave="transportBooking.paymentMode"
                                                        data-validate="true">
                                                    <c:forEach items="${paymentModeList}" var="paymentMode">
                                                        <option value="${paymentMode.paymentMode}" ${booking.paymentMode  eq paymentMode.paymentMode ? 'selected' : ''}>${paymentMode.paymentModeDescription}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="panel panel-warning">
                                <div class="panel-body" id="weightsAndRates">
                                    <div class="col-md-12">
                                        <table class="table table-bordered">
                                            <thead>
                                            <tr>
                                                <th class="text-center">No Of Packages</th>
                                                <th class="text-center">Weight</th>
                                                <th class="text-center">Volume</th>
                                                <th class="text-center">CBM</th>
                                                <th class="text-center">Estimated Km</th>
                                                <th class="text-center">Proposed Rate</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <tr>
                                                <td><input type="text"
                                                           class="form-control"
                                                           id="pieces"
                                                           placeholder="0"
                                                           name="pieces"
                                                           rv-value="transportBooking.pieces"
                                                           maxlength="7"
                                                           value="${booking.pieces}"
                                                           onchange="validateIntegerKeyPress(this)"/>
                                                </td>
                                                <td><input type="text"
                                                           class="form-control"
                                                           id="weight"
                                                           placeholder="0.00"
                                                           maxlength="10"
                                                           onchange="validateFloatKeyPress(this,2)"
                                                           name="weight"
                                                           rv-value="transportBooking.weight"
                                                           value="${booking.weight}"/>
                                                </td>
                                                <td><input type="text"
                                                           class="form-control"
                                                           id="volume" name="volume"
                                                           placeholder="0.00"
                                                           maxlength="10"
                                                           onchange="validateFloatKeyPress(this,2)"
                                                           rv-value="transportBooking.volume"
                                                           value="${booking.volume}"/>
                                                </td>
                                                <td><input type="text"
                                                           class="form-control"
                                                           id="cbm"
                                                           placeholder="0.0000"
                                                           maxlength="10"
                                                           onchange="validateFloatKeyPress(this,4)"
                                                           name="cbm"
                                                           rv-value="transportBooking.cbm"
                                                           value="${booking.cbm}"/>
                                                </td>
                                                <td><input type="text"
                                                           class="form-control"
                                                           id="estimatedKm"
                                                           onchange="validateIntegerKeyPress(this)"
                                                           placeholder="0"
                                                           maxlength="10"
                                                           name="estimatedKm"
                                                           rv-value="transportBooking.estimatedKm"
                                                           value="${booking.estimatedKm}"/>
                                                </td>
                                                <td><input type="text"
                                                           class="form-control"
                                                           id="proposedTransportCharge"
                                                           placeholder="0"
                                                           maxlength="7"
                                                           name="proposedTransportCharge"
                                                           rv-value="transportBooking.proposedTransportCharge"
                                                           value="${booking.proposedTransportCharge}"
                                                           onchange="validateFloatKeyPress(this,2)"/>
                                                </td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                            <div class="panel panel-warning">
                                <div class="panel-body" id="other_info">
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label" for="remarks">Remarks</label>
                                            <div class="col-md-8 col-xs-12">
                                        <textarea class="form-control vertical-resize"
                                                  rows="3"
                                                  rv-value="transportBooking.remarks"
                                                  id="remarks"
                                                  name="remarks"
                                                  maxlength="500">${booking.remarks}</textarea>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <div class="col-md-6 col-xs-12 createOperation">
                                                <button type="button" class="btn btn-primary pull-left"
                                                        value="Create Transport Booking"
                                                        <sec:authorize
                                                                access="!hasRole('ROLE_transport@transportBookingManagement_CREATE')">
                                                            disabled="disabled"
                                                        </sec:authorize>
                                                        onclick="create_booking()">Submit
                                                </button>
                                            </div>
                                            <div class="col-md-6 updateOperation" style="display: none">
                                                <button type="button" class="btn btn-primary pull-left"
                                                        value="Update Transport Booking"
                                                        <sec:authorize
                                                                access="!hasRole('ROLE_transport@transportBookingManagement_UPDATE')">
                                                            disabled="disabled"
                                                        </sec:authorize>
                                                        onclick="update_booking()">Update
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="block">
                                    <div id="airex_status_info" class="push-up-30">
                                        <div class="form-group updateOperation" style="display: none">
                                            <div class="col-md-12 push-up-30">
                                                <div class="col-md-6">
                                                    <div class="col-md-12">
                                                        <div class="form-group">
                                                            <label class="col-md-4 text-right">Created By</label>
                                                            <label class="col-md-8"
                                                                   rv-text="transportBooking.createdBy"
                                                                   title="${booking.createdBy}">${booking.createdBy}</label>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-12">
                                                        <div class="form-group">
                                                            <label class="col-md-4 text-right">Created Date</label>
                                                            <label class="col-md-8"
                                                                   rv-text="transportBooking.createdDate">
                                                                <fmt:formatDate value="${booking.createdDate}"
                                                                                pattern="yyyy-MM-dd hh:mm a"/>
                                                            </label>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-md-6">
                                                    <div class="col-md-12">
                                                        <div class="form-group">
                                                            <label class="col-md-4 text-right">Last Modified By</label>
                                                            <label class="col-md-8"
                                                                   rv-text="transportBooking.lastModifiedBy"
                                                                   title="${booking.lastModifiedBy}">${booking.lastModifiedBy}</label>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-12">
                                                        <div class="form-group">
                                                            <label class="col-md-4 text-right">Last Modified
                                                                Date</label>
                                                            <label class="col-md-8"
                                                                   rv-text="transportBooking.lastModifiedDate">
                                                                <fmt:formatDate value="${booking.lastModifiedDate}"
                                                                                pattern="yyyy-MM-dd hh:mm a"/>
                                                            </label>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="tab-pane" id="tab-second">
                    <form class="form-horizontal addViaLocations" id="addViaLocations" method="post">
                        <input type="hidden"
                               name="transportBookingSeq"
                               id="viaLocations_transportBookingSeq"
                               value="${booking.transportBookingSeq}"/>
                        <div class="panel panel-warning">
                            <div class="panel-heading" data-toggle="collapse">
                                <h3 class="panel-title">Booking Via Locations</h3>
                                <div style="float: right">
                                    <button type="button"
                                            class="transparent-btn fa fa-plus-square fa-2x"
                                            onclick="add_table_row_custom('bookingViaLocationTable',this)"></button>
                                    <button type="button"
                                            class="transparent-btn fa fa-minus-square fa-2x"
                                            onclick="delete_table_row('bookingViaLocationTable',this)"></button>
                                </div>
                            </div>

                            <div class="panel-body">
                                <table class="bookingViaLocationTable auto-grow-table table font-12" id="bookingViaLocationTable" border="1" width="100%">
                                    <thead style="display: none">
                                    <tr>
                                        <td>&nbsp;</td>
                                        <td>&nbsp;</td>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr rv-each-tmp="bookingViaLocations | isNotEmpty">
                                        <td width="2%">
                                            <input type="checkbox" name="chk"/>
                                            <input type="hidden"
                                                   rv-value="tmp.transportBookingViaLocationSeq"
                                                   name="bookingViaLocations[0].transportBookingViaLocationSeq"/>
                                            <input type="hidden"
                                                   rv-value="tmp.transportBookingSeq"
                                                   name="bookingViaLocations[0].transportBookingSeq"/>
                                        </td>
                                        <td>
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <label class="col-md-4 col-xs-12 control-label">Location</label>
                                                    <div class="col-md-7 col-xs-12">
                                                        <select name="bookingViaLocations[0].viaLocationSeq"
                                                                id="viaLocation_viaLocationSeq0"
                                                                class="form-control ajax-select"
                                                                data-abs-ajax-type="get"
                                                                data-abs-request-delay="500"
                                                                data-key="finalDestinationSeq"
                                                                data-value="destination"
                                                                data-subText="location.locationName"
                                                                data-abs-ajax-url="transport/transportBookingManagement/findFinalDestination"
                                                                data-live-search="true"
                                                                required
                                                                onchange="load_via_address_data(this)"
                                                                title="Select and begin typing">
                                                            <option rv-value="tmp.viaLocationSeq"
                                                                    rv-text="tmp.viaLocation.destination"
                                                                    rv-setselectedattr="tmp.viaLocationSeq"></option>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-4 col-xs-12 control-label">Address</label>
                                                    <div class="col-md-7 col-xs-12">
                                                        <textarea class="form-control vertical-resize"
                                                                  rows="3"
                                                                  rv-value="tmp.viaLocationAddress"
                                                                  id="viaLocation_viaLocationAddress0"
                                                                  name="bookingViaLocations[0].viaLocationAddress"
                                                                  maxlength="256"></textarea>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-4 col-xs-12 control-label">Arrival Time</label>
                                                    <div class="col-md-7 col-xs-12">
                                                        <input type="text" class="form-control datetimepicker"
                                                               id="viaLocation_requestedArrivalTime0"
                                                               rv-value="tmp.requestedArrivalTime"
                                                               name="bookingViaLocations[0].requestedArrivalTime"
                                                               title="Required, Arrival Time"
                                                               required
                                                               maxlength="25"/>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <label class="col-md-4 col-xs-12 control-label">Contact Person</label>
                                                    <div class="col-md-7 col-xs-12">
                                                        <input type="text" class="form-control" id="viaLocation_contactPerson0"
                                                               rv-value="tmp.contactPerson"
                                                               name="bookingViaLocations[0].contactPerson"
                                                               title="Contact Person"
                                                               maxlength="100"/>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-4 col-xs-12 control-label">Contact No</label>
                                                    <div class="col-md-7 col-xs-12">
                                                        <input type="text" class="form-control" id="viaLocation_contactNo0"
                                                               rv-value="tmp.contactNo"
                                                               name="bookingViaLocations[0].contactNo"
                                                               title="Contact No"
                                                               maxlength="50"/>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-4 col-xs-12 control-label">Drop Pick</label>
                                                    <div class="col-md-7 col-xs-12">
                                                        <select class="form-control select"
                                                                rv-value="tmp.dropPickStatus"
                                                                data-live-search="true"
                                                                name="bookingViaLocations[0].dropPickStatus"
                                                                id="viaLocation_dropPickStatus0"
                                                                required>
                                                            <c:forEach items="${dropPickList}" var="dropPick">
                                                                <option value="${dropPick.dropPickStatus}">${dropPick.dropPickStatusDescription}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-4 col-xs-12 control-label">Pieces</label>
                                                    <div class="col-md-7 col-xs-12">
                                                        <input type="text"
                                                               class="form-control viaLocationsVal"
                                                               id="viaLocation_pieces0"
                                                               name="bookingViaLocations[0].pieces"
                                                               rv-value="tmp.pieces"
                                                               pattern="^[0-9]*(.[0-9]*)?([eE][-+][0-9]*)?$"
                                                               maxlength="7"
                                                               onchange="validateFloatKeyPress(this,2)"
                                                        />
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-4 col-xs-12 control-label">CBM</label>
                                                    <div class="col-md-7 col-xs-12">
                                                        <input type="text"
                                                               class="form-control viaLocationsVal"
                                                               id="viaLocation_cbm0"
                                                               name="bookingViaLocations[0].cbm"
                                                               rv-value="tmp.length"
                                                               pattern="^[0-9]*(.[0-9]*)?([eE][-+][0-9]*)?$"
                                                               maxlength="7"
                                                               onchange="validateFloatKeyPress(this,2)"
                                                        />
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <label class="col-md-4 col-xs-12 control-label">Volume</label>
                                                    <div class="col-md-7 col-xs-12">
                                                        <input type="text"
                                                               class="form-control viaLocationsVal"
                                                               id="viaLocation_volume0"
                                                               name="bookingViaLocations[0].volume"
                                                               rv-value="tmp.volume"
                                                               pattern="^[0-9]*(.[0-9]*)?([eE][-+][0-9]*)?$"
                                                               maxlength="7"
                                                               onchange="validateFloatKeyPress(this,2)"
                                                        />
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-4 col-xs-12 control-label">Pkg Type</label>
                                                    <div class="col-md-7 col-xs-12">
                                                        <select name="bookingViaLocations[0].packageTypeSeq"
                                                                rv-value="tmp.packageTypeSeq"
                                                                class="form-control select"
                                                                id="viaLocation_packageTypeSeq0"
                                                                data-live-search="true"
                                                                title="Required, Pkg Type">
                                                            <c:forEach items="${packageTypeList}" var="packageType">
                                                                <option value="${packageType.packageTypeSeq}" ${packageType.packageTypeSeq eq booking.packageTypeSeq ? 'selected' : ''}>
                                                                        ${packageType.packageTypeName}
                                                                </option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-4 col-xs-12 control-label">U.O.V</label>
                                                    <div class="col-md-7 col-xs-12">
                                                        <select class="form-control select"
                                                                rv-value="tmp.uovSeq"
                                                                data-live-search="true"
                                                                name="bookingViaLocations[0].uovSeq"
                                                                id="viaLocation_uovSeq0">
                                                            <c:forEach items="${volumeUnitList}" var="volumeUnit">
                                                                <option value="${volumeUnit.unitSeq}" ${volumeUnit.unitSeq eq booking.uovSeq ? 'selected' : ''}>${volumeUnit.unitCode}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-4 col-xs-12 control-label">U.O.W</label>
                                                    <div class="col-md-7 col-xs-12">
                                                        <select class="form-control select"
                                                                rv-value="tmp.uowSeq"
                                                                data-live-search="true"
                                                                name="bookingViaLocations[0].uowSeq"
                                                                id="viaLocation_uowSeq0">
                                                            <c:forEach items="${weightUnitList}" var="weightUnit">
                                                                <option value="${weightUnit.unitSeq}" ${weightUnit.unitSeq eq booking.uowSeq ? 'selected' : ''}>${weightUnit.unitCode}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-3 col-sm-8">
                                <button type="button" class="btn btn-primary pull-right createOperation"
                                        <sec:authorize
                                                access="!hasRole('ROLE_transport@transportBookingManagement_CREATE')">
                                            disabled="disabled"
                                        </sec:authorize>
                                        onclick="create_via_locations()">Submit
                                </button>
                            </div>
                        </div>

                        <div class="form-group updateOperation" style="display: none">
                            <div class="col-sm-offset-2 col-sm-10 removeFromModal">
                                <button type="button"
                                        id="update_dimension_button"
                                        class="btn btn-primary pull-right updateOperation"
                                        <sec:authorize
                                                access="!hasRole('ROLE_transport@transportBookingManagement_UPDATE')">
                                            disabled="disabled"
                                        </sec:authorize>
                                        onclick="update_via_locations()">Update
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<div id="data_modal"></div>
