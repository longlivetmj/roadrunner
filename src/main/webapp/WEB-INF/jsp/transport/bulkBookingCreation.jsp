<%@ include file="/WEB-INF/jsp/include.jsp" %>
<script type="text/javascript" src="../../../customjs/transport/bulkBookingCreation.js"></script>
<div class="page-title">
    <h3><span class="fa fa-user"></span>Bulk Transport Booking</h3>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default tabs">
            <ul class="nav nav-tabs" role="tablist">
                <li class="active"><a href="#tab-first" role="tab" data-toggle="tab">Upload Balance Stock</a></li>
                <li><a href="#tab-second" role="tab" data-toggle="tab">Balance Stock History</a></li>
            </ul>
            <div class="panel-body tab-content">
                <div class="tab-pane active" id="tab-first">
                    <form class="form-horizontal bulkBookingFrom" id="create" method="post" enctype="multipart/form-data">
                        <div class="col-md-12">
                            <div class="panel panel-warning">
                                <div class="panel-body" id="booking_info">
                                    <div class="hidden" id="hidden_ids">
                                        <input class="hidden" name="companyProfileSeq" id="companyProfileSeq"
                                               value="${booking.companyProfileSeq}"/>
                                        <input class="hidden" name="moduleSeq" id="moduleSeq"
                                               value="${booking.moduleSeq}"/>
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
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label class="col-md-3 col-xs-12 control-label"
                                                       for="shipperSeq">Shipper</label>
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
                                                <label class="col-md-3 col-xs-12 control-label"
                                                       for="invoiceCustomerSeq">Invoice To</label>
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
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="panel panel-warning">
                                <div class="panel-body" id="basicDetails">
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label" for="uowSeq">Unit of
                                                Weight</label>
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
                                            <label class="col-md-3 col-xs-12 control-label" for="uovSeq">Unit of
                                                Volume</label>
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
                                            <label class="col-md-3 col-xs-12 control-label" for="packageTypeSeq">Package
                                                Type</label>
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
                                            <label class="col-md-3 col-xs-12 control-label" for="invoiceStatus">Invoicing
                                                State</label>
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
                                            <label class="col-md-3 col-xs-12 control-label" for="cashOrCredit">Cash or
                                                Credit</label>
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
                                            <label class="col-md-3 col-xs-12 control-label" for="paymentMode">Payment
                                                Mode</label>
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
                                <div class="panel-body" id="other_info">
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Excel File</label>
                                            <input type="file" class="form-control fileUpload" id="uploadingFile" required
                                                   accept=".xlsx,.xls"
                                                   name="uploadingFile"/>
                                            <div class="help-block with-errors"></div>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label"
                                                   for="remarks">Remarks</label>
                                            <div class="col-md-8 col-xs-12">
                                        <textarea class="form-control vertical-resize"
                                                  rows="3"
                                                  rv-value="transportBooking.remarks"
                                                  id="remarks"
                                                  name="remarks"
                                                  maxlength="500">${booking.remarks}</textarea>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-md-11 col-xs-10 createOperation">
                                                <button type="button" class="btn btn-primary pull-right"
                                                        value="Create Transport Booking"
                                                        <sec:authorize
                                                                access="!hasRole('ROLE_transport@bulkBookingCreation_UPLOAD')">
                                                            disabled="disabled"
                                                        </sec:authorize>
                                                        onclick="upload()">UPLOAD
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
                                            </div>

                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="tab-pane" id="tab-second">
                    <div class="row">
                        <div class="col-md-6">
                            <form class="form-horizontal" method="post" name="searchForm" id="searchForm">
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label" for="customerSeq">Customer</label>
                                    <div class="col-md-8 col-xs-12">
                                        <select name="customerSeq" id="search_customerSeq"
                                                class="form-control ajax-select"
                                                data-validate="true"
                                                data-shipper-consignee
                                                data-abs-ajax-type="get"
                                                data-abs-request-delay="500"
                                                data-key="stakeholderSeq"
                                                data-value="stakeholderName"
                                                data-subText="stakeholderCode"
                                                data-abs-ajax-url="transport/transportBookingManagement/findCustomer"
                                                data-live-search="true">
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Created Date</label>
                                    <div class="col-md-8 col-xs-12" id="sandbox-container">
                                        <div class="input-daterange input-group" id="datepicker">
                                            <input type="text" class="input-sm form-control datepicker" id="search_startDate" name="startDate" value='<fmt:formatDate pattern="yyyy-MM-dd" value="${startDate}"/>'/>
                                            <span class="input-group-addon">to</span>
                                            <input type="text" class="input-sm form-control datepicker" id="search_endDate" name="endDate" value='<fmt:formatDate pattern="yyyy-MM-dd" value="${endDate}" />'/>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-sm-offset-2 col-sm-7">
                                        <button type="button" class="btn btn-primary pull-right"
                                                value="loadDestinationData"
                                                <sec:authorize
                                                        access="!hasRole('ROLE_transport@bulkBookingCreation_VIEW')">
                                                    disabled="disabled"
                                                </sec:authorize>
                                                onclick="view_bulk_booking_data()">Search
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="form-group" id="bulkUploadData"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>