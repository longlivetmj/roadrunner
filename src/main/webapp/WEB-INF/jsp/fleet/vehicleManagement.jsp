<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<script type="text/javascript" src="../../../customjs/fleet/vehicleManagement.js"></script>
<div class="page-title">
    <h3><span class="fa fa-users"></span>Vehicle Management</h3>
</div>
<!-- END PAGE TITLE -->
<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default tabs">
            <ul class="nav nav-tabs tablist">
                <li class="active"><a href="#tab-first" class="tab" role="tab" data-toggle="tab">Vehicle
                    Creation</a></li>
                <li><a href="#tab-second" class="tab" role="tab" data-toggle="tab">Vehicle Search</a></li>
            </ul>
            <div class="panel-body tab-content">
                <div class="tab-pane active" id="tab-first">
                    <div class="col-md-12">
                        <form class="form-horizontal vehicleManagementForm" method="post" id="create">
                            <input type="hidden"
                                   id="companyProfileSeq"
                                   name="companyProfileSeq"
                                   rv-value="vehicle.companyProfileSeq">
                            <div class="form-group removeFromModal">
                                <div class="col-md-10 push-down-20">
                                    <h4 class="subTitle">Vehicle Details</h4>
                                </div>
                                <div class="col-md-2 operations" style="display: none">
                                    <button type="button" class="btn btn-success"
                                            onclick="new_form(formTemplate,'update','create')">
                                        New
                                    </button>
                                    <button type="button" class="btn btn-danger"
                                            <sec:authorize
                                                    access="!hasRole('ROLE_fleet@vehicleManagement_DELETE')">
                                                disabled="disabled"
                                            </sec:authorize>
                                            data-toggle=confirmation
                                            data-btn-ok-label="Continue"
                                            data-btn-ok-icon="glyphicon glyphicon-share-alt"
                                            data-btn-ok-class="btn-success"
                                            data-btn-cancel-label="Cancel"
                                            data-btn-cancel-icon="glyphicon glyphicon-ban-circle"
                                            data-btn-cancel-class="btn-danger"
                                            data-title="Deleting Vehicle"
                                            data-content="Are you sure you want to delete this vehicle ? "
                                            data-id="modal"
                                            data-on-confirm="delete_vehicle">
                                        Delete
                                    </button>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="panel panel-success">
                                    <div class="panel-heading">
                                        <h3 class="panel-title">Vehicle Details</h3>
                                    </div>
                                    <div class="panel-body">
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Vehicle No</label>
                                            <div class="col-md-8 col-xs-12">
                                                <input type="text" class="form-control " id="vehicleNo"
                                                       rv-value="vehicle.vehicleNo"
                                                       name="vehicleNo" required maxlength="100"/>
                                                <span class="help-block">Required, Vehicle No</span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Vehicle Code</label>
                                            <div class="col-md-8 col-xs-12">
                                                <input type="text" class="form-control " id="vehicleCode"
                                                       rv-value="vehicle.vehicleCode"
                                                       name="vehicleCode" required maxlength="25">
                                                <span class="help-block">Required, Vehicle Code</span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Vehicle Type</label>
                                            <div class="col-md-8 col-xs-12">
                                                <select name="vehicleTypeSeqList" multiple
                                                        id="vehicleTypeSeq"
                                                        class="form-control select vehicleTypeSeqList"
                                                        data-validate="true"
                                                        title="Select and begin typing"
                                                        data-live-search="true"
                                                        required>
                                                    <c:forEach items="${vehicleTypeList}" var="vehicleType">
                                                        <option value="${vehicleType.vehicleTypeSeq}">${vehicleType.vehicleTypeName}</option>
                                                    </c:forEach>
                                                </select>
                                                <span class="help-block">Required, Vehicle Type</span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Transporter</label>
                                            <div class="col-md-8 col-xs-12">
                                                <select data-live-search="true"
                                                        name="stakeholderSeq" id="stakeholderSeq"
                                                        data-validate="true"
                                                        aria-required="true"
                                                        rv-setselected="vehicle.stakeholderSeq"
                                                        class="form-control ajax-select stakeholderSeq"
                                                        data-abs-ajax-type="get"
                                                        data-abs-request-delay="500"
                                                        data-key="stakeholderSeq"
                                                        data-value="stakeholderName"
                                                        data-subText="stakeholderCode"
                                                        title="Select and begin typing"
                                                        required
                                                        data-abs-ajax-url="fleet/vehicleManagement/findTransporter">
                                                    <option rv-value="vehicle.stakeholderSeq"
                                                            rv-text="vehicle.stakeholder.stakeholderName"
                                                            rv-setselectedattr="vehicle.stakeholderSeq"></option>
                                                </select>
                                                <span class="help-block">Required, Transporter</span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Engine No</label>
                                            <div class="col-md-8 col-xs-12">
                                                <input type="text" class="form-control " id="engineNo"
                                                       rv-value="vehicle.engineNo"
                                                       name="engineNo" required maxlength="50">
                                                <span class="help-block">Required, Engine No</span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Chassis No</label>
                                            <div class="col-md-8 col-xs-12">
                                                <input type="text" class="form-control " id="chassisNo"
                                                       rv-value="vehicle.chassisNo"
                                                       name="chassisNo" required maxlength="50">
                                                <span class="help-block">Required, Chassis No</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="panel panel-success">
                                    <div class="panel-heading">
                                        <h3 class="panel-title">Generic Information</h3>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-3 col-xs-12 control-label">Manufacturer</label>
                                        <div class="col-md-8 col-xs-12">
                                            <select name="vehicleManufacturerSeq"
                                                    id="vehicleManufacturerSeq"
                                                    rv-setselected="vehicle.vehicleManufacturerSeq"
                                                    class="form-control ajax-select vehicleManufacturerSeq"
                                                    data-validate="true"
                                                    data-abs-ajax-type="get"
                                                    data-abs-request-delay="500"
                                                    data-key="vehicleManufacturerSeq"
                                                    data-value="manufacturerName"
                                                    title="Select and begin typing"
                                                    data-abs-ajax-url="fleet/vehicleManagement/findVehicleManufacturer"
                                                    data-live-search="true"
                                                    required>
                                                <option rv-value="vehicle.vehicleManufacturerSeq"
                                                        rv-text="vehicle.vehicleManufacturer.manufacturerName"
                                                        rv-setselectedattr="vehicle.vehicleManufacturerSeq"></option>
                                            </select>
                                            <span class="help-block">Required, Vehicle Manufacturer</span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-3 col-xs-12 control-label">Modal</label>
                                        <div class="col-md-8 col-xs-12">
                                            <select name="vehicleModalSeq"
                                                    id="vehicleModalSeq"
                                                    rv-setselected="vehicle.vehicleModalSeq"
                                                    class="form-control vehicleModal"
                                                    data-validate="true"
                                                    data-abs-ajax-type="get"
                                                    data-abs-request-delay="500"
                                                    data-key="vehicleModalSeq"
                                                    data-value="modalName"
                                                    title="Select and begin typing"
                                                    data-abs-ajax-url="fleet/vehicleManagement/findVehicleModal"
                                                    data-live-search="true"
                                                    required>
                                                <option rv-value="vehicle.vehicleModalSeq"
                                                        rv-text="vehicle.vehicleModal.modalName"
                                                        rv-setselectedattr="vehicle.vehicleModalSeq"></option>
                                            </select>
                                            <span class="help-block">Required, Vehicle Modal</span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-3 control-label">Manufacture Country</label>
                                        <div class="col-md-8 col-xs-12">
                                            <select data-live-search="true"
                                                    name="countrySeq"
                                                    id="countrySeq"
                                                    data-validate="true"
                                                    required
                                                    aria-required="true"
                                                    rv-setselected="vehicle.countrySeq"
                                                    class="form-control ajax-select"
                                                    data-abs-ajax-type="get"
                                                    data-abs-request-delay="500"
                                                    data-key="countrySeq"
                                                    data-value="countryName"
                                                    title="Required, Country"
                                                    data-abs-ajax-url="fleet/vehicleManagement/findCountry">
                                                <option rv-value="vehicle.countrySeq"
                                                        rv-text="vehicle.country.countryName"
                                                        rv-setselectedattr="vehicle.countrySeq"></option>
                                            </select>
                                            <span class="help-block"></span>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-3 col-xs-12 control-label">Manufactuer Year</label>
                                        <div class="col-md-8 col-xs-12">
                                            <input type="text"
                                                   class="form-control "
                                                   id="yearOfManufacture"
                                                   rv-value="vehicle.yearOfManufacture"
                                                   name="yearOfManufacture"
                                                   maxlength="10"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-3 col-xs-12 control-label">Fuel Type</label>
                                        <div class="col-md-8 col-xs-12">
                                            <select name="fuelType"
                                                    id="fuelType"
                                                    class="form-control select"
                                                    rv-value="vehicle.fuelType"
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
                                        <label class="col-md-3 col-xs-12 control-label">Fuel Consumption (Km Per Liter)</label>
                                        <div class="col-md-8 col-xs-12">
                                            <input type="text"
                                                   class="form-control"
                                                   name="fuelConsumption"
                                                   rv-value="vehicle.fuelConsumption"
                                                   maxlength="100"
                                                   id="fuelConsumption"/>
                                        </div>
                                    </div>
                                    <div class="panel-body">
                                        <div class="form-group updateOperation" style="display: none">
                                            <div class="form-group">
                                                <label class="col-md-3 text-right">Created By</label>
                                                <label class="col-md-8" rv-text="vehicle.createdBy"></label>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 text-right">Created Date</label>
                                                <label class="col-md-8" rv-text="vehicle.createdDate"></label>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 text-right">Last Modified By</label>
                                                <label class="col-md-8" rv-text="vehicle.lastModifiedBy"></label>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 text-right">Last Modified Date</label>
                                                <label class="col-md-8" rv-text="vehicle.lastModifiedDate"></label>
                                            </div>
                                            <div class="col-sm-offset-2 col-sm-7 removeFromModal">
                                                <button type="button" class="btn btn-primary pull-right"
                                                        value="Update Vehicle"
                                                        <sec:authorize
                                                                access="!hasRole('ROLE_fleet@vehicleManagement_UPDATE')">
                                                            disabled="disabled"
                                                        </sec:authorize>
                                                        onclick="update_vehicle()">Update
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="panel panel-success">
                                    <div class="panel-heading">
                                        <h3 class="panel-title">Operational Details</h3>
                                    </div>
                                    <div class="panel-body">
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Default Driver</label>
                                            <div class="col-md-8 col-xs-12">
                                                <select name="defaultDriverSeq"
                                                        id="defaultDriverSeq"
                                                        rv-setselected="vehicle.defaultDriverSeq"
                                                        class="form-control defaultDriver"
                                                        data-validate="true"
                                                        data-abs-ajax-type="get"
                                                        data-abs-request-delay="500"
                                                        data-key="employeeSeq"
                                                        data-value="employeeName"
                                                        title="Select and begin typing"
                                                        data-abs-ajax-url="fleet/vehicleManagement/findDriver"
                                                        data-live-search="true">
                                                    <option rv-value="vehicle.defaultDriverSeq"
                                                            rv-text="vehicle.defaultDriver.employeeName"
                                                            rv-setselectedattr="vehicle.defaultDriverSeq"></option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Secondary Driver</label>
                                            <div class="col-md-8 col-xs-12">
                                                <select name="secondaryDriverSeq"
                                                        id="secondaryDriverSeq"
                                                        rv-setselected="vehicle.secondaryDriverSeq"
                                                        class="form-control secondaryDriver"
                                                        data-validate="true"
                                                        data-abs-ajax-type="get"
                                                        data-abs-request-delay="500"
                                                        data-key="employeeSeq"
                                                        data-value="employeeName"
                                                        title="Select and begin typing"
                                                        data-abs-ajax-url="fleet/vehicleManagement/findDriver"
                                                        data-live-search="true">
                                                    <option rv-value="vehicle.secondaryDriverSeq"
                                                            rv-text="vehicle.secondaryDriver.employeeName"
                                                            rv-setselectedattr="vehicle.secondaryDriverSeq"></option>
                                                </select>
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Default Helper</label>
                                            <div class="col-md-8 col-xs-12">
                                                <select name="defaultHelperSeq"
                                                        id="defaultHelperSeq"
                                                        rv-setselected="vehicle.defaultHelperSeq"
                                                        class="form-control defaultHelper"
                                                        data-validate="true"
                                                        data-abs-ajax-type="get"
                                                        data-abs-request-delay="500"
                                                        data-key="employeeSeq"
                                                        data-value="employeeName"
                                                        title="Select and begin typing"
                                                        data-abs-ajax-url="fleet/vehicleManagement/findHelper"
                                                        data-live-search="true">
                                                    <option rv-value="vehicle.defaultHelperSeq"
                                                            rv-text="vehicle.defaultHelper.employeeName"
                                                            rv-setselectedattr="vehicle.defaultHelper"></option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Secondary Helper</label>
                                            <div class="col-md-8 col-xs-12">
                                                <select name="secondaryHelperSeq"
                                                        id="secondaryHelperSeq"
                                                        rv-setselected="vehicle.secondaryHelperSeq"
                                                        class="form-control secondaryHelper"
                                                        data-validate="true"
                                                        data-abs-ajax-type="get"
                                                        data-abs-request-delay="500"
                                                        data-key="employeeSeq"
                                                        data-value="employeeName"
                                                        title="Select and begin typing"
                                                        data-abs-ajax-url="fleet/vehicleManagement/findHelper"
                                                        data-live-search="true">
                                                    <option rv-value="vehicle.secondaryHelperSeq"
                                                            rv-text="vehicle.secondaryDriver.employeeName"
                                                            rv-setselectedattr="vehicle.secondaryHelperSeq"></option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">GPS Service Provider</label>
                                            <div class="col-md-8 col-xs-12">
                                                <select name="gpsServiceProviderSeq"
                                                        id="gpsServiceProviderSeq"
                                                        class="form-control select"
                                                        rv-value="vehicle.gpsServiceProviderSeq"
                                                        data-live-search="false"
                                                        data-validate="true">
                                                    <c:forEach items="${gpsServiceProviderList}" var="gpsServiceProvider">
                                                        <option value="${gpsServiceProvider.gpsServiceProviderSeq}" selected>${gpsServiceProvider.serviceProvider}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">GPS Terminal Key</label>
                                            <div class="col-md-8 col-xs-12">
                                                <input type="text"
                                                       class="form-control "
                                                       id="gpsTerminalKey"
                                                       rv-value="vehicle.gpsTerminalKey"
                                                       name="gpsTerminalKey"
                                                       maxlength="50"/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Based Location</label>
                                            <div class="col-md-8 col-xs-12">
                                                <select data-live-search="true"
                                                        name="basedLocationSeq"
                                                        id="basedLocationSeq"
                                                        rv-setselected="vehicle.basedLocationSeq"
                                                        data-validate="true"
                                                        aria-required="true"
                                                        class="form-control ajax-select"
                                                        data-abs-ajax-type="get"
                                                        data-abs-request-delay="500"
                                                        data-key="finalDestinationSeq"
                                                        data-value="destination"
                                                        data-abs-ajax-url="fleet/vehicleManagement/findFinalDestination">
                                                    <option rv-value="vehicle.basedLocationSeq"
                                                            rv-text="vehicle.basedLocation.destination"
                                                            rv-setselectedattr="vehicle.basedLocationSeq"></option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Current Mileage (km)</label>
                                            <div class="col-md-8 col-xs-12">
                                                <div class="input-group col-md-12">
                                                    <input type="text"
                                                           class="form-control "
                                                           id="currentMileage"
                                                           rv-value="vehicle.currentMileage"
                                                           pattern="^[0-9]*(.[0-9]*)?([eE][-+][0-9]*)?$"
                                                           name="currentMileage"
                                                           maxlength="10"
                                                           data-error="Please Provide a Value"/>
                                                </div>
                                                <div class="help-block with-errors"></div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Last Synced Date</label>
                                            <div class="col-md-8 col-xs-12">
                                                <input type="text" class="form-control datepicker" name="mileageSyncedDate"
                                                       maxlength="50"
                                                       rv-value="vehicle.mileageSyncedDate"
                                                       id="mileageSyncedDate"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="panel panel-success">
                                    <div class="panel-heading">
                                        <h3 class="panel-title">Other Details</h3>
                                    </div>
                                    <div class="panel-body">
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Tyre Size</label>
                                            <div class="col-md-8 col-xs-12">
                                                <select name="tyreSizeSeq"
                                                        id="tyreSizeSeq"
                                                        class="form-control select"
                                                        rv-value="vehicle.tyreSizeSeq"
                                                        data-live-search="false"
                                                        data-validate="true">
                                                    <option value="">Select</option>
                                                    <c:forEach items="${tyreSizeList}" var="tyreSize">
                                                        <option value="${tyreSize.tyreSizeSeq}">${tyreSize.sizeName}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">No of Tyres</label>
                                            <div class="col-md-8 col-xs-12">
                                                <input type="text"
                                                       class="form-control"
                                                       name="noOfTyres"
                                                       required
                                                       id="noOfTyres"
                                                       rv-value="vehicle.noOfTyres"
                                                       onchange="validateIntegerKeyPress()"
                                                       maxlength="50"/>
                                                <span class="help-block">Required, No of Tyres</span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Engine Capacity</label>
                                            <div class="col-md-8 col-xs-12">
                                                <input type="text"
                                                       class="form-control"
                                                       name="engineCapacity"
                                                       required
                                                       id="engineCapacity"
                                                       rv-value="vehicle.engineCapacity"
                                                       onchange="validateIntegerKeyPress()"
                                                       maxlength="50"/>
                                                <span class="help-block">Required, Engine Capacity</span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Payment Mode</label>
                                            <div class="col-md-8 col-xs-12">
                                                <select name="paymentMode"
                                                         id="paymentMode"
                                                         class="form-control select"
                                                         rv-value="vehicle.paymentMode"
                                                         data-live-search="false"
                                                         data-validate="true">
                                                <c:forEach items="${paymentModeList}" var="paymentMode">
                                                    <option value="${paymentMode.paymentMode}">${paymentMode.paymentModeDescription}</option>
                                                </c:forEach>
                                            </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Agreed Value</label>
                                            <div class="col-md-8 col-xs-12">
                                                <div class="input-group col-md-12">
                                                    <input type="text"
                                                           class="form-control "
                                                           id="paymentModeValue"
                                                           rv-value="vehicle.paymentModeValue"
                                                           pattern="^[0-9]*(.[0-9]*)?([eE][-+][0-9]*)?$"
                                                           onchange="validateFloatKeyPress(this)"
                                                           name="paymentModeValue"
                                                           maxlength="10"
                                                           data-error="Please Provide a Value"/>
                                                </div>
                                                <div class="help-block with-errors"></div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Vehicle Activation</label>
                                            <div class="col-md-8 col-xs-12">
                                                <select name="vehicleActivation"
                                                         id="vehicleActivation"
                                                         class="form-control select"
                                                         rv-value="vehicle.vehicleActivation"
                                                         data-live-search="false"
                                                         data-validate="true">
                                                <c:forEach items="${vehicleActivationList}" var="vehicleActivation">
                                                    <option value="${vehicleActivation.vehicleActivation}">${vehicleActivation.vehicleActivationDescription}</option>
                                                </c:forEach>
                                            </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Status</label>
                                            <div class="col-md-8 col-xs-12">
                                                <select name="status" id="status" rv-value="vehicle.status"
                                                        class="form-control select" data-validate="true"
                                                        onchange="get_status_value()"
                                                        data-live-search="false">
                                                    <c:forEach items="${statusList}" var="status">
                                                        <option value="${status.statusSeq}">${status.status}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group createOperation removeFromModal">
                                    <div class="col-md-offset-4 col-md-7">
                                        <button type="button" class="btn btn-primary pull-right"
                                                value="create Vehicle"
                                                <sec:authorize
                                                        access="!hasRole('ROLE_fleet@vehicleManagement_CREATE')">
                                                    disabled="disabled"
                                                </sec:authorize>
                                                onclick="create_vehicle()">Create
                                        </button>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group hidden">
                                <input type="hidden" name="vehicleSeq" id="vehicleSeq" value=""
                                       rv-value="vehicle.vehicleSeq"/>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="tab-pane" id="tab-second">
                    <div class="row">
                        <div class="col-md-6">
                            <form class="form-horizontal" method="post" name="search" id="search">
                                <div class="form-group">
                                    <h4 class="subTitle">Search Vehicle</h4>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Vehicle No</label>
                                    <div class="col-md-8 col-xs-12">
                                        <input type="text"
                                               class="form-control"
                                               id="search_vehicleNo"
                                               name="vehicleNo"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Vehicle Type</label>
                                    <div class="col-md-8 col-xs-12">
                                        <select name="vehicleTypeSeq"
                                                id="search_vehicleTypeSeq"
                                                class="form-control ajax-select"
                                                data-validate="true"
                                                data-abs-ajax-type="get"
                                                data-abs-request-delay="500"
                                                data-key="vehicleTypeSeq"
                                                data-value="vehicleTypeName"
                                                title="Select and begin typing"
                                                data-abs-ajax-url="fleet/vehicleManagement/findVehicleType"
                                                data-live-search="true">
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Vehicle Code</label>
                                    <div class="col-md-8 col-xs-12">
                                        <input type="text"
                                               class="form-control"
                                               id="search_vehicleCode"
                                               name="vehicleCode"
                                               maxlength="50"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 control-label">Transporter</label>
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
                                                data-abs-ajax-url="fleet/vehicleManagement/findTransporter">
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Status</label>
                                    <div class="col-md-8 col-xs-12">
                                        <select name="status" id="search_status"
                                                class="form-control select" data-validate="true"
                                                data-live-search="false">
                                            <option value="-1">Select</option>
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
                                            <input type="text" class="input-sm form-control datepicker" id="search_startDate"
                                                   name="startDate"
                                                   value='<fmt:formatDate pattern="yyyy-MM-dd" value="${fromDate}"/>'/>
                                            <span class="input-group-addon">to</span>
                                            <input type="text" class="input-sm form-control datepicker" id="search_endDate"
                                                   name="endDate"
                                                   value='<fmt:formatDate pattern="yyyy-MM-dd" value="${toDate}" />'/>
                                        </div>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="col-sm-offset-2 col-sm-7">
                                        <button type="button" class="btn btn-primary pull-right" value="Search"
                                                onclick="search_vehicle()">Search
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="form-group" id="loadVehicleData"></div>
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
                <h4 class="modal-title">Vehicle Management Panel</h4>
            </div>
            <form class="form-horizontal" method="post" name="modal" id="modal">
                <div class="modal-body panel">
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-danger"
                            <sec:authorize
                                    access="!hasRole('ROLE_fleet@vehicleManagement_DELETE')">
                                disabled="disabled"
                            </sec:authorize>
                            data-toggle=confirmation
                            data-btn-ok-label="Continue"
                            data-btn-ok-icon="glyphicon glyphicon-share-alt"
                            data-btn-ok-class="btn-success"
                            data-btn-cancel-label="Cancel"
                            data-btn-cancel-icon="glyphicon glyphicon-ban-circle"
                            data-btn-cancel-class="btn-danger"
                            data-title="Deleting Vehicle"
                            data-content="Are you sure you want to delete this vehicle ? "
                            data-id="modal"
                            data-on-confirm="delete_vehicle_popup">
                        Delete
                    </button>
                    <button type="button" class="btn btn-primary"
                            <sec:authorize access="!hasRole('ROLE_fleet@vehicleManagement_UPDATE')">
                                disabled="disabled"
                            </sec:authorize>
                            id="updateVehicle"
                            onclick="update_vehicle_modal()">Save Changes
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>


