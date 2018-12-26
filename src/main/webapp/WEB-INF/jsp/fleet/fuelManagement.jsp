<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<script type="text/javascript" src="../../../customjs/fleet/fuelManagement.js"></script>
<div class="page-title">
    <h3><span class="fa fa-puzzle-piece"></span>Fuel Management</h3>
</div>
<!-- END PAGE TITLE -->
<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default tabs">
            <ul class="nav nav-tabs tablist">
                <li class="active"><a href="#tab-first" class="tab" role="tab" data-toggle="tab">Fuel Record</a></li>
                <li><a href="#tab-second" class="tab" role="tab" data-toggle="tab">Search Fuel Records</a></li>
            </ul>
            <div class="panel-body tab-content">
                <div class="tab-pane active" id="tab-first">
                    <form class="form-horizontal fuelManagementForm" method="post" id="create">
                        <input type="hidden"
                               id="companyProfileSeq"
                               name="companyProfileSeq"
                               rv-value="vehicleFuel.companyProfileSeq">
                        <input type="hidden" name="vehicleFuelSeq" id="vehicleFuelSeq"
                               rv-value="vehicleFuel.vehicleFuelSeq" class="vehicleFuelSeq"/>
                        <div class="form-group removeFromModal">
                            <div class="col-md-2 operations" style="display: none">
                                <button type="button" class="btn btn-success"
                                        onclick="new_form(formTemplate,'update','create')">
                                    New
                                </button>
                                <button type="button" class="btn btn-danger"
                                        <sec:authorize
                                                access="!hasRole('ROLE_fleet@fuelManagement_DELETE')">
                                            disabled="disabled"
                                        </sec:authorize>
                                        data-toggle=confirmation
                                        data-btn-ok-label="Continue"
                                        data-btn-ok-icon="glyphicon glyphicon-share-alt"
                                        data-btn-ok-class="btn-success"
                                        data-btn-cancel-label="Cancel"
                                        data-btn-cancel-icon="glyphicon glyphicon-ban-circle"
                                        data-btn-cancel-class="btn-danger"
                                        data-title="Deleting Fuel Record"
                                        data-content="Are you sure you want to delete this Fuel Record ? "
                                        data-id="update"
                                        data-on-confirm="delete_fuel">
                                    Delete
                                </button>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="panel panel-danger">
                                <div class="panel-body">
                                    <div class="form-group">
                                        <label class="col-md-3 col-xs-12 control-label">Transporter</label>
                                        <div class="col-md-8 col-xs-12">
                                            <select data-live-search="true"
                                                    name="stakeholderSeq" id="stakeholderSeq"
                                                    data-validate="true"
                                                    aria-required="true"
                                                    rv-setselected="vehicleFuel.stakeholderSeq"
                                                    class="form-control ajax-select stakeholderSeq"
                                                    data-abs-ajax-type="get"
                                                    data-abs-request-delay="500"
                                                    data-key="stakeholderSeq"
                                                    data-value="stakeholderName"
                                                    data-subText="stakeholderCode"
                                                    title="Select and begin typing"
                                                    required
                                                    onchange="load_vehicle_list_by_transporter(this)"
                                                    data-abs-ajax-url="fleet/fuelManagement/findTransporter">
                                                <c:if test="${vehicleFuel.stakeholder ne null}">
                                                    <option value="${vehicleFuel.stakeholder.stakeholderSeq}"
                                                            selected>${vehicleFuel.stakeholder.stakeholderName}</option>
                                                </c:if>
                                                <option rv-value="vehicleFuel.stakeholderSeq"
                                                        rv-text="vehicleFuel.stakeholder.stakeholderName"
                                                        rv-setselectedattr="vehicleFuel.stakeholderSeq"></option>
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
                                                    class="form-control select vehicleSeq"
                                                    required
                                                    onchange="set_default_driver(this)"
                                                    data-validate="true">
                                                <option rv-value="vehicleFuel.vehicleSeq"
                                                        rv-text="vehicleFuel.vehicle.vehicleNo"
                                                        rv-setselectedattr="vehicleFuel.vehicleSeq"></option>
                                            </select>
                                            <span class="help-block">Required, Vehicle</span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-3 col-xs-12 control-label">Driver</label>
                                        <div class="col-md-8 col-xs-12">
                                            <select name="employeeSeq"
                                                    id="employeeSeq"
                                                    data-live-search="true"
                                                    class="form-control select employeeSeq"
                                                    required
                                                    data-live-search="false"
                                                    data-validate="true">
                                                <option rv-value="vehicleFuel.employeeSeq"
                                                        rv-text="vehicleFuel.employee.employeeName"
                                                        rv-setselectedattr="vehicleFuel.employeeSeq"></option>
                                            </select>
                                            <span class="help-block">Required, Driver</span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-3 col-xs-12 control-label">Meter Reading</label>
                                        <div class="col-md-8 col-xs-12">
                                            <input type="text" class="form-control " id="meterReading"
                                                   rv-value="vehicleFuel.meterReading"
                                                   onchange="validateIntegerKeyPress(this)"
                                                   name="meterReading" required>
                                            <span class="help-block">Required, Meter Reading</span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-3 col-xs-12 control-label">Remarks</label>
                                        <div class="col-md-8 col-xs-12">
                                                <textarea class="form-control text-uppercase" rows="3" name="remarks"
                                                          rv-value="vehicleFuel.remarks"
                                                          id="remarks" maxlength="150"></textarea>
                                        </div>
                                    </div>
                                    <div class="panel-body">
                                        <div class="form-group updateOperation" style="display: none">
                                            <div class="form-group">
                                                <label class="col-md-3 text-right">Created By</label>
                                                <label class="col-md-8" rv-text="vehicleFuel.createdBy"></label>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 text-right">Created Date</label>
                                                <label class="col-md-8" rv-text="vehicleFuel.createdDate"></label>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 text-right">Last Modified By</label>
                                                <label class="col-md-8" rv-text="vehicleFuel.lastModifiedBy"></label>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 text-right">Last Modified Date</label>
                                                <label class="col-md-8" rv-text="vehicleFuel.lastModifiedDate"></label>
                                            </div>
                                            <div class="col-sm-offset-2 col-sm-7 removeFromModal">
                                                <button type="button" class="btn btn-primary pull-right"
                                                        value="Update Vehicle"
                                                        <sec:authorize
                                                                access="!hasRole('ROLE_fleet@fuelManagement_UPDATE')">
                                                            disabled="disabled"
                                                        </sec:authorize>
                                                        onclick="update_fuel()">Update
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="panel panel-success">
                                <div class="panel-body">
                                    <div class="form-group">
                                        <label class="col-md-3 col-xs-12 control-label">Supplier</label>
                                        <div class="col-md-8 col-xs-12">
                                            <select data-live-search="true"
                                                    name="supplierSeq" id="supplierSeq"
                                                    data-validate="true"
                                                    aria-required="true"
                                                    rv-setselected="vehicleFuel.supplierSeq"
                                                    class="form-control ajax-select supplierSeq"
                                                    data-abs-ajax-type="get"
                                                    data-abs-request-delay="500"
                                                    data-key="stakeholderSeq"
                                                    data-value="stakeholderName"
                                                    data-subText="stakeholderCode"
                                                    title="Select and begin typing"
                                                    required
                                                    data-abs-ajax-url="fleet/fuelManagement/findSupplier">
                                                <option rv-value="vehicleFuel.supplierSeq"
                                                        rv-text="vehicleFuel.supplier.stakeholderName"
                                                        rv-setselectedattr="vehicleFuel.supplierSeq"></option>
                                            </select>
                                            <span class="help-block">Required, Supplier</span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-3 col-xs-12 control-label">Fuel Variant</label>
                                        <div class="col-md-8 col-xs-12">
                                            <select name="fuelTypeVariantSeq"
                                                    id="fuelTypeVariantSeq"
                                                    data-live-search="true"
                                                    class="form-control select fuelTypeVariantSeq"
                                                    required
                                                    data-live-search="false"
                                                    data-validate="true">
                                                <option rv-value="vehicleFuel.fuelTypeVariantSeq"
                                                        rv-text="vehicleFuel.fuelTypeVariant.variantName"
                                                        rv-setselectedattr="vehicleFuel.fuelTypeVariantSeq"></option>
                                            </select>
                                            <span class="help-block">Required, Fuel Variant</span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-3 col-xs-12 control-label">Unit Price</label>
                                        <div class="col-md-8 col-xs-12">
                                            <input type="text"
                                                   class="form-control unitPrice"
                                                   id="unitPrice"
                                                   onchange="validateFloatKeyPress(this,2); calculate_amount(this)"
                                                   rv-value="vehicleFuel.unitPrice"
                                                   required
                                                   name="unitPrice"
                                                   maxlength="10"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-3 col-xs-12 control-label">quantity</label>
                                        <div class="col-md-8 col-xs-12">
                                            <input type="text"
                                                   class="form-control quantity"
                                                   id="quantity"
                                                   required
                                                   onchange="validateFloatKeyPress(this,2); calculate_amount(this)"
                                                   rv-value="vehicleFuel.quantity"
                                                   name="quantity"
                                                   maxlength="10"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-3 col-xs-12 control-label">Amount</label>
                                        <div class="col-md-8 col-xs-12">
                                            <input type="text"
                                                   class="form-control amount"
                                                   id="amount" `
                                                   onchange="validateFloatKeyPress(this,2)"
                                                   rv-value="vehicleFuel.amount"
                                                   name="amount"
                                                   maxlength="10"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-3 col-xs-12 control-label">Transaction Date</label>
                                        <div class="col-md-8 col-xs-12">
                                            <input type="text"
                                                   class="form-control datetimepicker"
                                                   name="transactionDate"
                                                   required
                                                   rv-value="vehicleFuel.transactionDate"
                                                   maxlength="100"
                                                   id="transactionDate"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-3 col-xs-12 control-label">Cash/Credit</label>
                                        <div class="col-md-8 col-xs-12">
                                            <select name="stakeholderCashTypeSeq"
                                                    id="stakeholderCashTypeSeq"
                                                    class="form-control select"
                                                    rv-value="vehicleFuel.stakeholderCashTypeSeq"
                                                    data-live-search="false"
                                                    required
                                                    data-validate="true">
                                                <c:forEach items="${stakeholderCashTypeList}" var="stakeholderCashType">
                                                    <option value="${stakeholderCashType.stakeholderCashTypeSeq}">${stakeholderCashType.stakeholderCashType}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group createOperation removeFromModal">
                                        <div class="col-md-offset-4 col-md-7">
                                            <button type="button" class="btn btn-primary pull-right"
                                                    value="create Vehicle"
                                                    <sec:authorize
                                                            access="!hasRole('ROLE_fleet@fuelManagement_CREATE')">
                                                        disabled="disabled"
                                                    </sec:authorize>
                                                    onclick="create_fuel()">Submit
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="tab-pane" id="tab-second">
                    <div class="row">
                        <form class="form-horizontal" method="post" name="search" id="search">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Transporter</label>
                                    <div class="col-md-8 col-xs-12">
                                        <select data-live-search="true"
                                                name="stakeholderSeq" id="search_stakeholderSeq"
                                                data-validate="true"
                                                aria-required="true"
                                                class="form-control ajax-select stakeholderSeq"
                                                data-abs-ajax-type="get"
                                                data-abs-request-delay="500"
                                                data-key="stakeholderSeq"
                                                data-value="stakeholderName"
                                                data-subText="stakeholderCode"
                                                title="Select and begin typing"
                                                onchange="load_vehicle_list_by_transporter_search()"
                                                data-abs-ajax-url="fleet/fuelManagement/findTransporter">
                                            <c:if test="${vehicleFuel.stakeholder ne null}">
                                                <option value="${vehicleFuel.stakeholder.stakeholderSeq}"
                                                        selected>${vehicleFuel.stakeholder.stakeholderName}</option>
                                            </c:if>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Vehicle</label>
                                    <div class="col-md-8 col-xs-12">
                                        <select name="vehicleSeq"
                                                id="search_vehicleSeq"
                                                data-live-search="true"
                                                class="form-control select vehicleSeq"
                                                data-validate="true">
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Status</label>
                                    <div class="col-md-8 col-xs-12">
                                        <select name="status" id="search_status"
                                                class="form-control select" data-validate="true"
                                                data-live-search="false">
                                            <option value="">Select</option>
                                            <c:forEach items="${statusList}" var="status">
                                                <option value="${status.statusSeq}">${status.status}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Created Date</label>
                                    <div class="col-md-8 col-xs-12" id="sandbox-container">
                                        <div class="input-daterange input-group" id="datepicker">
                                            <input type="text" class="input-sm form-control datepicker"
                                                   id="search_startDate"
                                                   name="startDate"
                                                   value='<fmt:formatDate pattern="yyyy-MM-dd" value="${fromDate}"/>'/>
                                            <span class="input-group-addon">to</span>
                                            <input type="text" class="input-sm form-control datepicker"
                                                   id="search_endDate"
                                                   name="endDate"
                                                   value='<fmt:formatDate pattern="yyyy-MM-dd" value="${toDate}" />'/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Supplier</label>
                                    <div class="col-md-8 col-xs-12">
                                        <select data-live-search="true"
                                                name="supplierSeq" id="search_supplierSeq"
                                                data-validate="true"
                                                aria-required="true"
                                                rv-setselected="vehicleFuel.supplierSeq"
                                                class="form-control ajax-select supplierSeq"
                                                data-abs-ajax-type="get"
                                                data-abs-request-delay="500"
                                                data-key="stakeholderSeq"
                                                data-value="stakeholderName"
                                                data-subText="stakeholderCode"
                                                title="Select and begin typing"
                                                data-abs-ajax-url="fleet/fuelManagement/findSupplier">
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Fuel Type</label>
                                    <div class="col-md-8 col-xs-12">
                                        <select name="fuelType"
                                                id="search_fuelType"
                                                data-live-search="true"
                                                class="form-control select"
                                                data-live-search="false"
                                                data-validate="true">
                                            <option value="">Select</option>
                                            <c:forEach items="${fuelTypeList}" var="fuelType">
                                                <option value="${fuelType.fuelType}">${fuelType.fuelTypeDescription}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Fuel Variant</label>
                                    <div class="col-md-8 col-xs-12">
                                        <select name="fuelTypeVariantSeq"
                                                id="search_fuelTypeVariantSeq"
                                                data-live-search="true"
                                                class="form-control select fuelTypeVariantSeq"
                                                data-live-search="false"
                                                data-validate="true">
                                            <option value="">Select</option>
                                            <c:forEach items="${fuelTypeVariantList}" var="fuelTypeVariant">
                                                <option value="${fuelTypeVariant.fuelTypeVariantSeq}">${fuelTypeVariant.variantName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Cash/Credit</label>
                                    <div class="col-md-8 col-xs-12">
                                        <select name="stakeholderCashTypeSeq"
                                                id="search_stakeholderCashTypeSeq"
                                                class="form-control select"
                                                rv-value="vehicleFuel.stakeholderCashTypeSeq"
                                                data-live-search="false"
                                                required
                                                data-validate="true">
                                            <option value="">Select</option>
                                            <c:forEach items="${stakeholderCashTypeList}" var="stakeholderCashType">
                                                <option value="${stakeholderCashType.stakeholderCashTypeSeq}">${stakeholderCashType.stakeholderCashType}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-sm-offset-2 col-sm-9">
                                        <button type="button" class="btn btn-primary pull-right" value="Search"
                                                onclick="search_fuel()">Search
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="row">
                        <div class="form-group" id="loadFuelData"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="myModal">
    <div class="modal-dialog cs-modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Fuel Management Panel</h4>
            </div>
            <form class="form-horizontal" method="post" name="modal" id="modal">
                <div class="modal-body panel">
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-danger"
                            <sec:authorize
                                    access="!hasRole('ROLE_fleet@fuelManagement_DELETE')">
                                disabled="disabled"
                            </sec:authorize>
                            data-toggle=confirmation
                            data-btn-ok-label="Continue"
                            data-btn-ok-icon="glyphicon glyphicon-share-alt"
                            data-btn-ok-class="btn-success"
                            data-btn-cancel-label="Cancel"
                            data-btn-cancel-icon="glyphicon glyphicon-ban-circle"
                            data-btn-cancel-class="btn-danger"
                            data-title="Deleting Fuel Record"
                            data-content="Are you sure you want to delete this Fuel Record ? "
                            data-id="modal"
                            data-on-confirm="delete_fuel">
                        Delete
                    </button>
                    <button type="button" class="btn btn-primary"
                            <sec:authorize access="!hasRole('ROLE_fleet@fuelManagement_UPDATE')">
                                disabled="disabled"
                            </sec:authorize>
                            id="updateVehicle"
                            onclick="update_fuel_modal()">Save Changes
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>


