<%@ include file="/WEB-INF/jsp/include.jsp" %>
<script type="text/javascript" src="../../../customjs/transport/transportBookingSearch.js"></script>

<div class="page-title">
    <h3><span class="fa fa-book"></span>Booking Management</h3>
</div>

<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default tabs">
            <ul class="nav nav-tabs" role="tablist">
                <li class="active"><a href="#tab-first" role="tab" data-toggle="tab">Booking Search</a></li>
            </ul>
            <div class="panel-body tab-content">
                <div class="tab-pane active" id="tab-first">
                    <form class="form-horizontal searchForm" id="searchForm">
                        <input type="hidden" name="moduleSeq" value="${moduleSeq}"/>
                        <input type="hidden" name="companyProfileSeq" value="${companyProfileSeq}"/>
                        <div class="col-md-4">
                            <div class="form-group">
                                <label class="col-md-4 col-xs-12 control-label">Booking Id</label>
                                <div class="col-md-7 col-xs-12">
                                    <input type="text" class="form-control"
                                           id="search_bookingSeq"
                                           onchange="validateIntegerKeyPress(this)"
                                           name="transportBookingSeq" title="Enter Booking No"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-4 col-xs-12 control-label">Department</label>
                                <div class="col-md-7 col-xs-12">
                                    <select name="departmentSeq"
                                            id="search_departmentSeq"
                                            class="form-control select"
                                            data-live-search="true"
                                            data-validate="true"
                                            data-search-placeholder="Select Department"
                                            aria-required="true"
                                            title="Select Department">
                                        <option value="" selected>All</option>
                                        <c:forEach items="${departmentList}" var="department">
                                            <option value="${department.departmentSeq}">${department.departmentName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-4 col-xs-12 control-label" for="customerSeq">Customer</label>
                                <div class="col-md-7 col-xs-12">
                                    <select name="customerSeq" id="customerSeq"
                                            class="form-control ajax-select"
                                            data-validate="true"
                                            data-shipper-consignee
                                            data-abs-ajax-type="get"
                                            data-abs-request-delay="500"
                                            data-key="stakeholderSeq"
                                            data-value="stakeholderName"
                                            data-subText="stakeholderCode"
                                            data-abs-ajax-url="transport/transportBookingSearch/findCustomer"
                                            data-live-search="true">
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-4 col-xs-12 control-label">Created Date</label>
                                <div class="col-md-7 col-xs-12" id="sandbox-container">
                                    <div class="input-daterange input-group" id="datepicker">
                                        <input type="text" class="input-sm form-control datepicker" id="search_startDate"
                                               name="startDate"
                                               value='<fmt:formatDate pattern="yyyy-MM-dd" value="${startDate}"/>'/>
                                        <span class="input-group-addon">to</span>
                                        <input type="text" class="input-sm form-control datepicker" id="search_endDate"
                                               name="endDate"
                                               value='<fmt:formatDate pattern="yyyy-MM-dd" value="${endDate}" />'/>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4">
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
                                            data-abs-ajax-url="transport/transportBookingSearch/findFinalDestination"
                                            data-live-search="true">
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
                                            data-abs-ajax-url="transport/transportBookingSearch/findFinalDestination"
                                            data-live-search="true">
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Vehicle Type</label>
                                <div class="col-md-8 col-xs-12">
                                    <select name="vehicleTypeSeq"
                                            id="vehicleTypeSeq"
                                            class="form-control ajax-select"
                                            data-validate="true"
                                            data-abs-ajax-type="get"
                                            data-abs-request-delay="500"
                                            data-key="vehicleTypeSeq"
                                            data-value="vehicleTypeName"
                                            data-abs-ajax-url="transport/transportBookingSearch/findVehicleType"
                                            data-live-search="true"
                                            placeholder="Vehicle Type" >
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <label class="col-md-4 col-xs-12 control-label">Job No</label>
                                <div class="col-md-7 col-xs-12">
                                    <input type="text" class="form-control"
                                           id="jobNo"
                                           name="jobNo" title="Enter Job No"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-4 col-xs-12 control-label">Customer Ref No</label>
                                <div class="col-md-7 col-xs-12">
                                    <input type="text" class="form-control"
                                           id="search_customerRefNo"
                                           name="customerRefNo" title="Enter Customer Ref No"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-4 col-xs-12 control-label">Current Status</label>
                                <div class="col-md-7 col-xs-12">
                                    <select name="currentStatus"
                                            id="currentStatus"
                                            class="form-control select"
                                            data-validate="true"
                                            data-live-search="true"
                                            placeholder="Status"
                                            title="Select Status">
                                        <option value="-1" selected>All</option>
                                        <c:forEach items="${statusList}" var="status">
                                            <option value="${status.currentStatus}">${status.currentStatusDescription}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-4 col-xs-12 control-label">&nbsp;</label>
                                <div class="col-md-4 col-xs-12">
                                    <button type="button" class="btn btn-default pull-left"
                                            onclick="reset()">Reset
                                    </button>
                                </div>
                                <div class="col-md-4 col-xs-12">
                                    <button type="button" class="btn btn-primary pull-right"
                                            <sec:authorize
                                                    access="!hasRole('ROLE_transport@transportBookingSearch_VIEW')">
                                                disabled="disabled"
                                            </sec:authorize>
                                            onclick="search_booking()">Search
                                    </button>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div id="bookingData"></div>
        </div>
    </div>
</div>
