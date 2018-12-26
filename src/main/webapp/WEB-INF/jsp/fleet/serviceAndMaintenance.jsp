<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<script type="text/javascript" src="../../../customjs/fleet/serviceAndMaintenance.js"></script>
<div class="page-title">
    <h3><span class="fa fa-cogs"></span>Service & Maintenance</h3>
</div>
<!-- END PAGE TITLE -->
<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default tabs">
            <ul class="nav nav-tabs tablist">
                <li class="active"><a href="#tab-first" class="tab" role="tab" data-toggle="tab">Create Service &
                    Maintenance</a></li>
                <li><a href="#tab-second" class="tab" role="tab" data-toggle="tab">Search Create Service & Maintenance
                    Records</a></li>
            </ul>
            <div class="panel-body tab-content">
                <div class="tab-pane active" id="tab-first">
                    <form class="form-horizontal serviceAndMaintenanceForm" method="post" id="create">
                        <input type="hidden"
                               id="companyProfileSeq"
                               name="companyProfileSeq"
                               rv-value="serviceAndMaintenance.companyProfileSeq">
                        <input type="hidden" name="serviceAndMaintenanceSeq" id="serviceAndMaintenanceSeq"
                               rv-value="serviceAndMaintenance.serviceAndMaintenanceSeq"
                               class="serviceAndMaintenanceSeq"/>
                        <div class="form-group removeFromModal">
                            <div class="col-md-2 operations" style="display: none">
                                <button type="button" class="btn btn-success"
                                        onclick="new_form(formTemplate,'update','create')">
                                    New
                                </button>
                                <button type="button" class="btn btn-danger"
                                        <sec:authorize
                                                access="!hasRole('ROLE_fleet@serviceAndMaintenance_DELETE')">
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
                                        <label class="col-md-3 col-xs-12 control-label">Action</label>
                                        <div class="col-md-8 col-xs-12">
                                            <select name="actionType"
                                                    id="actionType"
                                                    class="form-control select"
                                                    rv-value="serviceAndMaintenance.actionType"
                                                    data-live-search="false"
                                                    required
                                                    data-validate="true">
                                                <c:forEach items="${actionTypeList}" var="actionType">
                                                    <option value="${actionType.actionType}">${actionType.actionTypeDescription}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-3 col-xs-12 control-label">Transporter</label>
                                        <div class="col-md-8 col-xs-12">
                                            <select data-live-search="true"
                                                    name="stakeholderSeq" id="stakeholderSeq"
                                                    data-validate="true"
                                                    aria-required="true"
                                                    rv-setselected="serviceAndMaintenance.stakeholderSeq"
                                                    class="form-control ajax-select stakeholderSeq"
                                                    data-abs-ajax-type="get"
                                                    data-abs-request-delay="500"
                                                    data-key="stakeholderSeq"
                                                    data-value="stakeholderName"
                                                    data-subText="stakeholderCode"
                                                    title="Select and begin typing"
                                                    required
                                                    onchange="load_vehicle_list_by_transporter(this)"
                                                    data-abs-ajax-url="fleet/serviceAndMaintenance/findTransporter">
                                                <c:if test="${serviceAndMaintenance.stakeholder ne null}">
                                                    <option value="${serviceAndMaintenance.stakeholder.stakeholderSeq}"
                                                            selected>${serviceAndMaintenance.stakeholder.stakeholderName}</option>
                                                </c:if>
                                                <option rv-value="serviceAndMaintenance.stakeholderSeq"
                                                        rv-text="serviceAndMaintenance.stakeholder.stakeholderName"
                                                        rv-setselectedattr="serviceAndMaintenance.stakeholderSeq"></option>
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
                                                <option rv-value="serviceAndMaintenance.vehicleSeq"
                                                        rv-text="serviceAndMaintenance.vehicle.vehicleNo"
                                                        rv-setselectedattr="serviceAndMaintenance.vehicleSeq"></option>
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
                                                <option rv-value="serviceAndMaintenance.employeeSeq"
                                                        rv-text="serviceAndMaintenance.employee.employeeName"
                                                        rv-setselectedattr="serviceAndMaintenance.employeeSeq"></option>
                                            </select>
                                            <span class="help-block">Required, Driver</span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-3 col-xs-12 control-label">Cash/Credit</label>
                                        <div class="col-md-8 col-xs-12">
                                            <select name="stakeholderCashTypeSeq"
                                                    id="stakeholderCashTypeSeq"
                                                    class="form-control select"
                                                    rv-value="serviceAndMaintenance.stakeholderCashTypeSeq"
                                                    data-live-search="false"
                                                    required
                                                    data-validate="true">
                                                <c:forEach items="${stakeholderCashTypeList}" var="stakeholderCashType">
                                                    <option value="${stakeholderCashType.stakeholderCashTypeSeq}">${stakeholderCashType.stakeholderCashType}</option>
                                                </c:forEach>
                                            </select>
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
                                                    rv-setselected="serviceAndMaintenance.supplierSeq"
                                                    class="form-control ajax-select supplierSeq"
                                                    data-abs-ajax-type="get"
                                                    data-abs-request-delay="500"
                                                    data-key="stakeholderSeq"
                                                    data-value="stakeholderName"
                                                    data-subText="stakeholderCode"
                                                    title="Select and begin typing"
                                                    required
                                                    data-abs-ajax-url="fleet/serviceAndMaintenance/findSupplier">
                                                <option rv-value="serviceAndMaintenance.supplierSeq"
                                                        rv-text="serviceAndMaintenance.supplier.stakeholderName"
                                                        rv-setselectedattr="serviceAndMaintenance.supplierSeq"></option>
                                            </select>
                                            <span class="help-block">Required, Supplier</span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-3 col-xs-12 control-label">Transaction Date</label>
                                        <div class="col-md-8 col-xs-12">
                                            <input type="text"
                                                   class="form-control datetimepicker"
                                                   name="transactionDate"
                                                   required
                                                   rv-value="serviceAndMaintenance.transactionDate"
                                                   maxlength="100"
                                                   id="transactionDate"/>
                                            <span class="help-block">Required, Transaction Date</span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-3 col-xs-12 control-label">Meter Reading</label>
                                        <div class="col-md-8 col-xs-12">
                                            <input type="text" class="form-control " id="meterReading"
                                                   rv-value="serviceAndMaintenance.meterReading"
                                                   onchange="validateIntegerKeyPress(this)"
                                                   name="meterReading" required>
                                            <span class="help-block">Required, Meter Reading</span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-3 col-xs-12 control-label">Remarks</label>
                                        <div class="col-md-8 col-xs-12">
                                                <textarea class="form-control text-uppercase" rows="3" name="remarks"
                                                          rv-value="serviceAndMaintenance.remarks"
                                                          id="remarks" maxlength="150"></textarea>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div class="panel-danger">
                                <div class="panel-heading">
                                    <h3 class="panel-title">Line Items</h3>
                                    <div style="float: right">
                                        <lable>No of Rows :</lable>
                                        <input type="text" id="noOfRows" value="1"
                                               style="width: 30px;"/>
                                        <button type="button"
                                                class="transparent-btn fa fa-plus-square fa-2x"
                                                onclick="add_table_row('lineItemTable',this, 'noOfRows')"></button>
                                        <button type="button"
                                                class="transparent-btn fa fa-minus-square fa-2x"
                                                onclick="delete_table_row('lineItemTable',this)"></button>
                                    </div>
                                </div>
                                <div class="panel-body">
                                    <table class="lineItemTable auto-grow-table table table-bordered table-striped table-actions"
                                           border="1" width="100%">
                                        <thead>
                                        <tr>
                                            <th width="3%"></th>
                                            <th width="10%">Supplier</th>
                                            <th width="10%">Item</th>
                                            <th width="10%">Quantity</th>
                                            <th width="10%">Unit Price</th>
                                            <th width="10%">Currency</th>
                                            <th width="10%">Unit</th>
                                            <th width="10%">Amount</th>
                                            <th width="10%">Expire(KM)</th>
                                            <th width="10%">Expire(Duration)</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${serviceAndMaintenance.serviceAndMaintenanceLines}"
                                                   var="serviceAndMaintenanceLine">
                                            <tr rv-each-tmp="serviceAndMaintenance.serviceAndMaintenanceLines | isNotEmpty">
                                                <td><input type="checkbox" name="chk"/>
                                                    <input type="hidden"
                                                           name="serviceAndMaintenanceLines[0].serviceAndMaintenanceLineSeq"
                                                           rv-value="tmp.serviceAndMaintenanceLineSeq"></td>
                                                <td><select data-live-search="true"
                                                            name="serviceAndMaintenanceLines[0].supplierSeq"
                                                            id="supplierSeq0"
                                                            data-validate="true"
                                                            aria-required="true"
                                                            class="form-control ajax-select supplierSeq"
                                                            rv-setselected="tmp.supplierSeq"
                                                            data-abs-ajax-type="get"
                                                            data-abs-request-delay="500"
                                                            data-key="stakeholderSeq"
                                                            data-value="stakeholderName"
                                                            data-subText="stakeholderCode"
                                                            title="Select and begin typing"
                                                            required
                                                            data-abs-ajax-url="fleet/serviceAndMaintenance/findSupplier">
                                                    <option rv-value="tmp.supplierSeq"
                                                            rv-text="tmp.supplier.stakeholderName"
                                                            rv-setselectedattr="tmp.supplierSeq"></option>
                                                </select></td>
                                                <td><select data-live-search="true"
                                                            name="serviceAndMaintenanceLines[0].itemSeq" id="itemSeq0"
                                                            data-validate="true"
                                                            aria-required="true"
                                                            class="form-control ajax-select itemSeq"
                                                            data-abs-ajax-type="get"
                                                            data-abs-request-delay="500"
                                                            data-key="itemSeq"
                                                            onchange="load_item_detail(this,'serviceAndMaintenanceLines')"
                                                            data-value="itemName"
                                                            data-subText="itemCode"
                                                            rv-setselected="tmp.itemSeq"
                                                            title="Select and begin typing"
                                                            required
                                                            data-abs-ajax-url="fleet/serviceAndMaintenance/findItem">
                                                    <option rv-value="tmp.itemSeq"
                                                            rv-text="tmp.item.itemName"
                                                            rv-setselectedattr="tmp.itemSeq"></option>
                                                </select></td>
                                                <td><input type="text" class="form-control"
                                                           id="quantity0"
                                                           rv-value="tmp.quantity"
                                                           required
                                                           name="serviceAndMaintenanceLines[0].quantity"
                                                           onchange="validateFloatKeyPress(this,4)"
                                                           style="min-width: 40px"
                                                           onkeyup="calculate_charge_amount('serviceAndMaintenanceLines',this)"
                                                           maxlength="10"/></td>
                                                <td><input type="text" class="form-control"
                                                           id="unitPrice0"
                                                           rv-value="tmp.unitPrice"
                                                           required
                                                           name="serviceAndMaintenanceLines[0].unitPrice"
                                                           onchange="validateFloatKeyPress(this,4)"
                                                           style="min-width: 40px"
                                                           onkeyup="calculate_charge_amount('serviceAndMaintenanceLines',this)"
                                                           maxlength="10"/></td>
                                                <td><select name="serviceAndMaintenanceLines[0].currencySeq"
                                                            id="currencySeq0"
                                                            class="form-control ajax-select"
                                                            data-validate="true"
                                                            data-abs-ajax-type="get"
                                                            data-abs-request-delay="500"
                                                            required
                                                            rv-setselected="tmp.currencySeq"
                                                            data-key="currencySeq"
                                                            data-value="currencyCode"
                                                            data-abs-ajax-url="fleet/serviceAndMaintenance/findCurrency"
                                                            data-live-search="true"
                                                            title="Required, Currency">
                                                    <option value="${serviceAndMaintenanceLine.currency.currencySeq}"
                                                            selected>${serviceAndMaintenanceLine.currency.currencyCode}</option>
                                                    <option rv-value="tmp.currencySeq"
                                                            rv-text="tmp.currency.currencyCode"
                                                            rv-setselectedattr="tmp.currencySeq"></option>
                                                </select></td>
                                                <td><select name="serviceAndMaintenanceLines[0].unitSeq"
                                                            id="unitSeq0"
                                                            class="form-control ajax-select"
                                                            rv-setselected="tmp.unitSeq"
                                                            data-validate="true"
                                                            data-abs-ajax-type="get"
                                                            data-abs-request-delay="500"
                                                            data-key="unitSeq"
                                                            data-value="unitName"
                                                            data-abs-ajax-url="fleet/serviceAndMaintenance/findUnit"
                                                            data-live-search="true"
                                                            title="Required, Unit"
                                                            required>
                                                    <option value="${serviceAndMaintenanceLine.unit.unitSeq}"
                                                            selected>${serviceAndMaintenanceLine.unit.unitName}</option>
                                                    <option rv-value="tmp.unitSeq"
                                                            rv-text="tmp.unit.unitName"
                                                            rv-setselectedattr="tmp.unitSeq"></option>
                                                </select></td>
                                                <td><input type="text" class="form-control amount"
                                                           id="amount0"
                                                           rv-value="tmp.amount"
                                                           required
                                                           name="serviceAndMaintenanceLines[0].amount"
                                                           style="min-width: 40px"
                                                           onkeyup="calculate_charge_amount('serviceAndMaintenanceLines',this)"
                                                           maxlength="10"/></td>
                                                <td><input type="text" class="form-control"
                                                           id="kmExpiration0"
                                                           rv-value="tmp.kmExpiration"
                                                           name="serviceAndMaintenanceLines[0].kmExpiration"
                                                           style="min-width: 40px"
                                                           maxlength="10"/></td>
                                                <td><select name="serviceAndMaintenanceLines[0].durationExpiration"
                                                            id="durationExpiration0"
                                                            class="form-control select"
                                                            rv-value="tmp.durationExpiration"
                                                            data-live-search="false"
                                                            required
                                                            data-validate="true">
                                                    <c:forEach items="${durationTypeList}" var="durationType">
                                                        <option value="${durationType.durationExpiration}">${durationType.durationExpirationDescription}</option>
                                                    </c:forEach>
                                                </select></td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div class="panel-success">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label class="col-md-3 col-xs-12 control-label">Total Amount</label>
                                        <div class="col-md-8 col-xs-12">
                                            <input type="text" class="form-control totalAmount" id="amount"
                                                   rv-value="serviceAndMaintenance.amount"
                                                   value="0.00"
                                                   name="amount">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-3 col-xs-12 control-label">Tax Amount</label>
                                        <div class="col-md-8 col-xs-12">
                                            <input type="text" class="form-control taxAmount" id="taxAmount"
                                                   rv-value="serviceAndMaintenance.taxAmount"
                                                   value="0.00"
                                                   onchange="validateFloatKeyPress(this,2)"
                                                   onkeyup="calculate_total(this)"
                                                   name="taxAmount" required>
                                            <span class="help-block">Required, Tax Amount</span>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label class="col-md-3 col-xs-12 control-label">Final Amount</label>
                                        <div class="col-md-8 col-xs-12">
                                            <input type="text" class="form-control finalAmount" id="totalAmount"
                                                   rv-value="serviceAndMaintenance.totalAmount"
                                                   value="0.00"
                                                   name="totalAmount">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="panel-body">
                                <div class="form-group updateOperation" style="display: none">
                                    <div class="form-group">
                                        <label class="col-md-3 text-right">Created By</label>
                                        <label class="col-md-8" rv-text="serviceAndMaintenance.createdBy"></label>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-3 text-right">Created Date</label>
                                        <label class="col-md-8" rv-text="serviceAndMaintenance.createdDate"></label>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-3 text-right">Last Modified By</label>
                                        <label class="col-md-8" rv-text="serviceAndMaintenance.lastModifiedBy"></label>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-3 text-right">Last Modified Date</label>
                                        <label class="col-md-8"
                                               rv-text="serviceAndMaintenance.lastModifiedDate"></label>
                                    </div>
                                    <div class="col-sm-offset-2 col-sm-7 removeFromModal">
                                        <button type="button" class="btn btn-primary pull-right"
                                                value="Update Vehicle"
                                                <sec:authorize
                                                        access="!hasRole('ROLE_fleet@serviceAndMaintenance_UPDATE')">
                                                    disabled="disabled"
                                                </sec:authorize>
                                                onclick="update_serviceAndMaintenance()">Update
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group createOperation removeFromModal">
                                <div class="col-md-offset-4 col-md-7">
                                    <button type="button" class="btn btn-primary pull-right"
                                            value="create Vehicle"
                                            <sec:authorize
                                                    access="!hasRole('ROLE_fleet@serviceAndMaintenance_CREATE')">
                                                disabled="disabled"
                                            </sec:authorize>
                                            onclick="create_serviceAndMaintenance()">Submit
                                    </button>
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
                                                data-abs-ajax-url="fleet/serviceAndMaintenance/findTransporter">
                                            <c:if test="${serviceAndMaintenance.stakeholder ne null}">
                                                <option value="${serviceAndMaintenance.stakeholder.stakeholderSeq}"
                                                        selected>${serviceAndMaintenance.stakeholder.stakeholderName}</option>
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
                                    <label class="col-md-3 col-xs-12 control-label">Transaction Date</label>
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
                                    <label class="col-md-3 col-xs-12 control-label">Action</label>
                                    <div class="col-md-8 col-xs-12">
                                        <select name="actionType"
                                                id="search_actionType"
                                                class="form-control select"
                                                data-live-search="false"
                                                required
                                                data-validate="true">
                                            <option value="">ALL</option>
                                            <c:forEach items="${actionTypeList}" var="actionType">
                                                <option value="${actionType.actionType}">${actionType.actionTypeDescription}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Supplier</label>
                                    <div class="col-md-8 col-xs-12">
                                        <select data-live-search="true"
                                                name="supplierSeq" id="search_supplierSeq"
                                                data-validate="true"
                                                aria-required="true"
                                                rv-setselected="serviceAndMaintenance.supplierSeq"
                                                class="form-control ajax-select supplierSeq"
                                                data-abs-ajax-type="get"
                                                data-abs-request-delay="500"
                                                data-key="stakeholderSeq"
                                                data-value="stakeholderName"
                                                data-subText="stakeholderCode"
                                                title="Select and begin typing"
                                                data-abs-ajax-url="fleet/serviceAndMaintenance/findSupplier">
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Cash/Credit</label>
                                    <div class="col-md-8 col-xs-12">
                                        <select name="stakeholderCashTypeSeq"
                                                id="search_stakeholderCashTypeSeq"
                                                class="form-control select"
                                                rv-value="serviceAndMaintenance.stakeholderCashTypeSeq"
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
                                                onclick="search_serviceAndMaintenance()">Search
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="row">
                        <div class="form-group" id="loadServiceAndMaintenanceData"></div>
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
                                    access="!hasRole('ROLE_fleet@serviceAndMaintenance_DELETE')">
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
                            <sec:authorize access="!hasRole('ROLE_fleet@serviceAndMaintenance_UPDATE')">
                                disabled="disabled"
                            </sec:authorize>
                            id="updateVehicle"
                            onclick="update_serviceAndMaintenance_modal()">Save Changes
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>


