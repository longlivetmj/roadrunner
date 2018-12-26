<%@ include file="/WEB-INF/jsp/include.jsp" %>
<script type="text/javascript" src="../../../customjs/finance/financialChargeSearch.js"></script>

<div class="page-title">
    <h3><span class="fa fa-book"></span>Financial Charge Search</h3>
</div>

<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default tabs">
            <ul class="nav nav-tabs" role="tablist">
                <li class="active"><a href="#tab-first" role="tab" data-toggle="tab">Search for Jobs</a></li>
            </ul>
            <div class="panel-body tab-content">
                <div class="tab-pane active" id="tab-first">
                    <form class="form-horizontal searchForm" id="searchForm">
                        <input type="hidden" name="companyProfileSeq" value="${companyProfileSeq}"/>
                        <div class="col-md-4">
                            <div class="form-group">
                                <label class="col-md-4 col-xs-12 control-label">Module</label>
                                <div class="col-md-7 col-xs-12">
                                    <select name="moduleSeq" id="moduleSeq"
                                            class="form-control select"
                                            data-live-search="true"
                                            data-validate="true" onchange="load_module_departments()">
                                        <c:forEach items="${moduleList}" var="module">
                                            <option value="${module.moduleSeq}">${module.moduleName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-4 col-xs-12 control-label">Department</label>
                                <div class="col-md-7 col-xs-12">
                                    <select name="departmentSeq"
                                            id="departmentSeq"
                                            class="form-control select"
                                            data-live-search="true"
                                            data-validate="true"
                                            data-search-placeholder="Select Department"
                                            aria-required="true"
                                            title="Select Department">
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
                                            data-abs-ajax-url="finance/financialChargeSearch/findCustomer"
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
                                            onchange="load_vehicle_list_by_transporter()"
                                            data-abs-ajax-url="finance/financialChargeSearch/findTransporter">
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Vehicle</label>
                                <div class="col-md-8 col-xs-12">
                                    <select name="vehicleSeq"
                                            id="vehicleSeq"
                                            data-live-search="true"
                                            class="form-control select"
                                            data-live-search="false"
                                            data-validate="true">

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
                                            data-abs-ajax-url="finance/financialChargeSearch/findVehicleType"
                                            data-live-search="true"
                                            placeholder="Vehicle Type" >
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Finance Status</label>
                                <div class="col-md-8 col-xs-12">
                                    <select name="financeStatus"
                                            id="financeStatus"
                                            data-live-search="true"
                                            class="form-control select"
                                            data-validate="true">
                                        <option value="-1" selected>All</option>
                                        <c:forEach items="${financeStatusList}" var="financeStatus">
                                            <option value="${financeStatus.financeStatus}">${financeStatus.financeStatusDescription}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <label class="col-md-4 col-xs-12 control-label">Reference No</label>
                                <div class="col-md-7 col-xs-12">
                                    <input type="text" class="form-control"
                                           id="transportBookingSeq"
                                           onchange="validateIntegerKeyPress(this)"
                                           name="transportBookingSeq" title="Enter Booking No"/>
                                </div>
                            </div>
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
                                                    access="!hasRole('ROLE_finance@financialChargeSearch_VIEW')">
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
