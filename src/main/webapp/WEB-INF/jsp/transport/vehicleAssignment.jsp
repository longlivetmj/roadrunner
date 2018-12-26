<%@ include file="/WEB-INF/jsp/include.jsp" %>
<script type="text/javascript" src="../../../customjs/transport/vehicleAssignment.js"></script>

<div class="page-title">
    <h3><span class="fa fa-truck"></span>Vehicle Assignment</h3>
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
                                            data-abs-ajax-url="transport/vehicleAssignment/findCustomer"
                                            data-live-search="true">
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-md-4 col-xs-12">
                                    <select name="dateFilterType"
                                            id="dateFilterType"
                                            class="form-control select"
                                            data-validate="true"
                                            data-live-search="true">
                                        <c:forEach items="${dateFilterTypeList}" var="dateFilterType">
                                            <option value="${dateFilterType.dateFilterType}"
                                                ${(dateFilterType.dateFilterType eq defaultFilterType) ? 'selected' : ''}
                                            >${dateFilterType.dateFilterTypeDescription}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-md-7 col-xs-12" id="sandbox-container">
                                    <div class="input-daterange input-group" id="datepicker">
                                        <input type="text" class="input-sm form-control datepicker"
                                               id="search_startDate"
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
                                <label class="col-md-3 col-xs-12 control-label" for="pickupLocationSeq">Pickup
                                    location</label>
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
                                            data-abs-ajax-url="transport/vehicleAssignment/findFinalDestination"
                                            data-live-search="true">
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label" for="deliveryLocationSeq">Delivery
                                    location</label>
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
                                            data-abs-ajax-url="transport/vehicleAssignment/findFinalDestination"
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
                                            data-abs-ajax-url="transport/vehicleAssignment/findVehicleType"
                                            data-live-search="true"
                                            placeholder="Vehicle Type">
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
                                                    access="!hasRole('ROLE_transport@vehicleAssignment_VIEW')">
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

<div class="modal fade" id="myModal">
    <div class="modal-dialog cs-modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Vehicle Assignment Panel</h4>
            </div>
            <form class="form-horizontal" method="post" name="modal" id="modal">
                <div class="modal-body panel" id="assignVehicleData">
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-danger"
                            <sec:authorize
                                    access="!hasRole('ROLE_transport@vehicleAssignment_DELETE')">
                                disabled="disabled"
                            </sec:authorize>
                            onclick="remove_vehicle()">
                        Remove Vehicle
                    </button>
                    <button type="button" class="btn btn-primary"
                            <sec:authorize access="!hasRole('ROLE_transport@vehicleAssignment_CREATE')">
                                disabled="disabled"
                            </sec:authorize>
                            id="updateVehicle"
                            onclick="assign_vehicle()">Assign Vehicle
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>

<div class="modal fade" id="driverModal">
    <div class="modal-dialog cs-modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Driver Scorecard</h4>
            </div>
            <div class="modal-body panel" id="driverData">
                <div class="form-group font-10">
                    <table class="datatable stripe row-border order-column table">
                        <thead>
                        <tr>
                            <th>Employee</th>
                            <th>No of Jobs</th>
                            <th>Commission</th>
                            <th>Attendance</th>
                            <th>Total Km</th>
                            <th>Z-Score</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${driverList}" var="employee">
                            <tr>
                                <td>${employee.employee.employeeName}</td>
                                <td>${employee.noOfJobs}</td>
                                <td><fmt:formatNumber type="currency" currencySymbol=""
                                                      value="${employee.totalCommission}"/></td>
                                <td>${employee.attendance}</td>
                                <td>${employee.totalKm}</td>
                                <td><fmt:formatNumber type="number" maxFractionDigits="2"
                                                      value="${employee.zScore}"/></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="helperModal">
    <div class="modal-dialog cs-modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Helper Scorecard</h4>
            </div>
            <div class="modal-body panel" id="helperData">
                <div class="form-group font-10">
                    <table class="datatable stripe row-border order-column table">
                        <thead>
                        <tr>
                            <th>Employee</th>
                            <th>No of Jobs</th>
                            <th>Commission</th>
                            <th>Attendance</th>
                            <th>Total Km</th>
                            <th>Z-Score</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${helperList}" var="employee">
                            <tr>
                                <td>${employee.employee.employeeName}</td>
                                <td>${employee.noOfJobs}</td>
                                <td><fmt:formatNumber type="currency" currencySymbol=""
                                                      value="${employee.totalCommission}"/></td>
                                <td>${employee.attendance}</td>
                                <td>${employee.totalKm}</td>
                                <td><fmt:formatNumber type="number" maxFractionDigits="2"
                                                      value="${employee.zScore}"/></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>