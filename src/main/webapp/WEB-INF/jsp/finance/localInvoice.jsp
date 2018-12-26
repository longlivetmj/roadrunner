<%@ include file="/WEB-INF/jsp/include.jsp" %>
<script type="text/javascript" src="../../../customjs/finance/localInvoice.js"></script>
<div class="page-title">
    <h3><span class="fa fa-road"></span>Local Invoice Management</h3>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default tabs">
            <ul class="nav nav-tabs" role="tablist">
                <li class="active"><a href="#tab-first" role="tab" data-toggle="tab">Local Invoice Creation</a></li>
                <li><a href="#tab-second" role="tab" data-toggle="tab">Local Invoice Search</a></li>
            </ul>
            <div class="panel-body tab-content">
                <div class="tab-pane active" id="tab-first">
                    <form class="form-horizontal localInvoiceForm" method="post" name="create" id="create">
                        <div class="col-md-12">
                            <div class="form-group createOperation">
                                <div class="panel panel-info">
                                    <div class="panel-body" id="filteringInfo">
                                        <div class="col-md-5">
                                            <div class="form-group">
                                                <label class="col-md-4 col-xs-12 control-label">Module</label>
                                                <div class="col-md-7 col-xs-12">
                                                    <select name="moduleSeq" id="moduleSeq"
                                                            class="form-control select"
                                                            data-live-search="true"
                                                            data-validate="true">
                                                        <option value="">Select</option>
                                                        <c:forEach items="${moduleList}" var="module">
                                                            <option value="${module.moduleSeq}"
                                                                ${localInvoiceSearchAux.moduleSeq eq module.moduleSeq ? "selected":""}>
                                                                    ${module.moduleName}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                                <div class="help-block with-errors"></div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-4 col-xs-12 control-label">Document Type</label>
                                                <div class="col-md-7 col-xs-12">
                                                    <select name="targetType" id="targetType"
                                                            class="form-control select"
                                                            data-live-search="true"
                                                            data-validate="true">
                                                        <option value="">Select</option>
                                                        <c:forEach items="${targetTypeList}" var="financialTarget">
                                                            <option value="${financialTarget.targetType}"
                                                                ${localInvoiceSearchAux.targetType eq financialTarget.targetType ? "selected":""}>
                                                                    ${financialTarget.targetTypeDescription}
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group" id="sourceType">
                                                <label class="col-md-4 col-xs-12 control-label">Source</label>
                                                <div class="col-md-7 col-xs-12">
                                                    <select name="exchangeRateSourceType" id="exchangeRateSourceType"
                                                            class="form-control select"
                                                            data-validate="true"
                                                            data-live-search="true"
                                                            placeholder="exchangeRateSourceType">
                                                        <option value="">Select</option>
                                                        <c:forEach items="${sourceTypeList}" var="exchangeSourceType">
                                                            <option value="${exchangeSourceType.exchangeRateSourceType}"
                                                                ${localInvoiceSearchAux.exchangeRateSourceType eq exchangeSourceType.exchangeRateSourceType ? "selected":""}>
                                                                    ${exchangeSourceType.exchangeRateSourceTypeDescription}
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group" id="sourceBank">
                                                <label class="col-md-4 col-xs-12 control-label">Source(Bank)</label>
                                                <div class="col-md-7 col-xs-12">
                                                    <select name="sourceBankSeq" id="sourceBankSeq"
                                                            class="form-control select" data-validate="true"
                                                            data-live-search="true"
                                                            onchange="search_exchange_rate_bank()"
                                                            placeholder="sourceBankSeq">
                                                        <option value="">Select</option>
                                                        <c:forEach items="${bankList}" var="bank">
                                                            <option value="${bank.bankSeq}"
                                                                ${localInvoiceSearchAux.sourceBankSeq eq bank.bankSeq ? "selected":""}>
                                                                    ${bank.bankName}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group" id="referenceNo">
                                                <label class="col-md-4 col-xs-12 control-label">Reference No</label>
                                                <div class="col-md-7 col-xs-12">
                                                    <select name="referenceSeq"
                                                            id="transportBookingSeq"
                                                            rv-setselected="transportBookingSeq"
                                                            class="form-control"
                                                            data-validate="true"
                                                            data-abs-ajax-type="get"
                                                            data-abs-request-delay="500"
                                                            data-key="transportBookingSeq"
                                                            data-value="bookingNo"
                                                            data-subText="job.jobNo"
                                                            title="Select and begin typing"
                                                            data-abs-ajax-url="finance/localInvoice/findTransportBooking"
                                                            data-live-search="true">
                                                        <option rv-value="transportBookingSeq"
                                                                rv-text="transportBookingSeq"></option>
                                                        <c:if test="${localInvoiceSearchAux.transportBooking ne null}">
                                                            <option value="${localInvoiceSearchAux.transportBooking.transportBookingSeq}"
                                                                    selected>
                                                                    ${localInvoiceSearchAux.transportBooking.bookingNo}
                                                                (${localInvoiceSearchAux.transportBooking.job.jobNo})
                                                            </option>
                                                        </c:if>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-4 col-xs-12 control-label">Currency</label>
                                                <div class="col-md-7 col-xs-12">
                                                    <select name="currencySeq" id="currencySeq"
                                                            class="form-control select" data-validate="true"
                                                            onchange="load_transport_booking_charge()"
                                                            data-live-search="true">
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group col-md-12">
                                                <button type="button" class="btn btn-info pull-right"
                                                        value="Filter Charge"
                                                        <sec:authorize
                                                                access="!hasRole('ROLE_finance@localInvoice_VIEW')">
                                                            disabled="disabled"
                                                        </sec:authorize>
                                                        onclick="load_transport_booking_charge()">Search
                                                </button>
                                            </div>
                                        </div>
                                        <div class="col-md-7">
                                            <div class="form-group" id="loadExchangeRateBankData"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div class="form-group createOperation">
                                <div class="panel panel-info">
                                    <div class="panel-heading collapsed" data-toggle="collapse" data-parent="#accordion"
                                         href="#bookingInformation">
                                        <h3 class="panel-title">Transport Booking Details</h3>
                                        <ul class="panel-controls">
                                            <li><a href="#bookingInformation" class="panel-collapse"><span
                                                    class="fa fa-angle-down"></span></a></li>
                                        </ul>
                                    </div>
                                    <div class="panel-body collapse" id="bookingInformation">
                                        <div class="col-md-12">
                                            <div class="form-group" id="loadFinancialChargeTransportBooking"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div class="form-group createOperation">
                                <div class="panel panel-success">
                                    <div class="panel-heading collapsed" data-toggle="collapse" data-parent="#accordion"
                                         href="#chargeInfo">
                                        <h3 class="panel-title">Charges</h3>
                                        <ul class="panel-controls">
                                            <li><a href="#chargeInfo" class="panel-collapse"><span
                                                    class="fa fa-angle-down"></span></a></li>
                                        </ul>
                                    </div>
                                    <div class="panel-body collapse" id="chargeInfo">
                                        <div class="form-group" id="loadFinancialCharge"></div>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group createOperation" id="calculations">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label class="col-md-5 col-xs-12 control-label">Without Tax</label>
                                        <div class="col-md-6 col-xs-12">
                                            <input type="text" class="form-control text-danger"
                                                   id="rvWithoutTax"
                                                   rv-value="localInvoice.finalWithoutTaxAmount | currency"
                                                   readonly/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-5 col-xs-12 control-label">Total Amount </label>
                                        <div class="col-md-6 col-xs-12">
                                            <input type="text" class="form-control text-danger"
                                                   id="rvTotalAmount"
                                                   rv-value="localInvoice.finalTotalAmount | currency"
                                                   readonly/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-5 col-xs-12 control-label">Invoice Party</label>
                                        <div class="col-md-6 col-xs-12">
                                            <select name="stakeholderSeq"
                                                    id="stakeholderSeq"
                                                    class="form-control ajax-select"
                                                    data-validate="true"
                                                    data-abs-ajax-type="get"
                                                    data-abs-request-delay="500"
                                                    data-key="stakeholderSeq"
                                                    data-value="stakeholderName"
                                                    data-subtext="stakeholderCode"
                                                    title="Select and begin typing"
                                                    data-abs-ajax-url="finance/localInvoice/findStakeholder"
                                                    data-live-search="true"
                                                    onchange="set_stakeholder_Data()">
                                                <option rv-value="stakeholder.stakeholderSeq"
                                                        rv-text="stakeholder.stakeholderName"></option>
                                                <c:if test="${localInvoiceSearchAux.invoiceParty ne null}">
                                                    <option value="${localInvoiceSearchAux.invoiceParty.stakeholderSeq}"
                                                            selected
                                                            data-subText="${localInvoiceSearchAux.invoiceParty.stakeholderCode}">
                                                            ${localInvoiceSearchAux.invoiceParty.stakeholderName}
                                                    </option>
                                                </c:if>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-5 text-right">Invoice Party Address</label>
                                        <span id="stakeholderAddress"
                                              class="col-md-6">${localInvoiceSearchAux.stakeholderAddress}</span>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label class="col-md-5 col-xs-12 control-label">VAT</label>
                                        <div class="col-md-6 col-xs-12">
                                            <input type="text" class="form-control text-danger" id="rvVat"
                                                   rv-value="localInvoice.finalVatAmount | currency"
                                                   readonly/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-5 col-xs-12 control-label">OTHER</label>
                                        <div class="col-md-6 col-xs-12">
                                            <input type="text" class="form-control text-danger" id="rvNbt"
                                                   rv-value="localInvoice.finalOtherTaxAmount | currency"
                                                   readonly/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-5 col-xs-12 control-label">Remarks</label>
                                        <div class="col-md-6 col-xs-12">
                                            <textarea class="form-control" rows="3" id="remark" maxlength="512"
                                                      rv-value="localInvoice.remark"
                                                      name="remark"></textarea>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </div>
                        <div class="form-group createOperation">
                            <div class="col-sm-push-5 col-sm-6">
                                <span class="help-block"></span>
                                <button type="button" class="btn btn-primary pull-right"
                                        value="create Local Invoice"
                                        <sec:authorize
                                                access="!hasRole('ROLE_finance@localInvoice_CREATE')">
                                            disabled="disabled"
                                        </sec:authorize>
                                        onclick="create_local_invoice()">Create
                                </button>
                            </div>
                        </div>
                        <input type="hidden" name="moduleSeq" id="create_moduleSeq" value=""
                               rv-value="localInvoice.moduleSeq"/>
                        <input type="hidden" class="form-control text-danger"
                               name="localInvoiceSeq"
                               id="localInvoiceSeq" rv-value="localInvoice.localInvoiceSeq"
                               readonly/>

                    </form>
                </div>
                <div class="tab-pane" id="tab-second">
                    <div class="row">
                        <div class="col-md-6">
                            <form class="form-horizontal" method="post" name="search" id="search">
                                <div class="form-group">
                                    <h4 class="subTitle">Search Local Invoice</h4>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Local Invoice No</label>
                                    <div class="col-md-8 col-xs-12">
                                        <input type="text" class="form-control" id="search_localInvoiceNo"
                                               name="localInvoiceNo"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Document Type</label>
                                    <div class="col-md-8 col-xs-12">
                                        <select name="targetType" id="search_targetTypeSeq"
                                                class="form-control select"
                                                data-live-search="true"
                                                data-validate="true"
                                                onchange="target_type_dynamic()">
                                            <option>Select</option>
                                            <c:forEach items="${targetTypeList}" var="financialTarget">
                                                <option value="${financialTarget.targetType}">${financialTarget.targetTypeDescription}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Date</label>
                                    <div class="col-md-8 col-xs-12" id="sandbox-container">
                                        <div class="input-daterange input-group" id="datepicker">
                                            <input type="text" class="input-sm form-control datepicker"
                                                   id="search_startDate"
                                                   name="startDate"
                                                   value='<fmt:formatDate pattern="yyyy-MM-dd" value="${startDate}"/>'/>
                                            <span class="input-group-addon">to</span>
                                            <input type="text" class="input-sm form-control datepicker"
                                                   id="search_endDate"
                                                   name="endDate"
                                                   value='<fmt:formatDate pattern="yyyy-MM-dd" value="${endDate}"/>'/>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Module</label>
                                    <div class="col-md-8 col-xs-12">
                                        <select name="moduleSeq" id="search_moduleSeq"
                                                class="form-control select"
                                                data-live-search="true"
                                                data-validate="true"
                                                onchange="load_module_departments()">
                                            <option>Select</option>
                                            <c:forEach items="${moduleList}" var="module">
                                                <option value="${module.moduleSeq}">${module.moduleName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Department</label>
                                    <div class="col-md-8 col-xs-12">
                                        <select name="departmentSeq"
                                                id="search_departmentSeq"
                                                class="form-control select"
                                                data-live-search="true"
                                                data-validate="true">
                                            <option>Select</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Request Id</label>
                                    <div class="col-md-8 col-xs-12">
                                        <input type="text" class="form-control" id="search_requestIdjobNo"
                                               name="requestId"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Invoice Party</label>
                                    <div class="col-md-8 col-xs-12">
                                        <select id="search_stakeholderSeq"
                                                rv-setselected="stakeholder.stakeholderSeq"
                                                class="form-control ajax-select"
                                                data-validate="true"
                                                data-abs-ajax-type="get"
                                                data-abs-request-delay="500"
                                                data-key="stakeholderSeq"
                                                data-value="stakeholderName"
                                                data-subtext="stakeholderCode"
                                                title="Select and begin typing"
                                                data-abs-ajax-url="finance/localInvoice/findStakeholder"
                                                data-live-search="true">
                                            <option rv-value="stakeholder.stakeholderSeq"
                                                    rv-text="stakeholder.stakeholderName"></option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Status</label>
                                    <div class="col-md-8 col-xs-12">
                                        <select name="status"
                                                id="search_status"
                                                class="form-control select"
                                                data-live-search="true">
                                            <option>Select</option>
                                            <c:forEach items="${statusList}" var="status">
                                                <option value="${status.statusSeq}">${status.status}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-sm-offset-4 col-sm-7">
                                        <button type="button" class="btn btn-primary pull-right"
                                                value="search Local Invoice"
                                                <sec:authorize
                                                        access="!hasRole('ROLE_finance@localInvoice_VIEW')">
                                                    disabled="disabled"
                                                </sec:authorize>
                                                onclick="search_local_invoice()">Search
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="form-group" id="loadFinancialChargeData"></div>
                    </div>
                </div>
            </div>

        </div>

    </div>
</div>

<div class="modal fade" id="myModal">
    <div class="col-md-12">
        <div class="modal-dialog cs-modal-lg">
            <div class="modal-content">
                <form class="form-horizontal" method="post" name="modal" id="modal">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">Local Invoice Details</h4>
                    </div>
                    <div class="modal-body panel" id="localInvoiceData">
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-info"
                                value="Update Local Invoice"
                                <sec:authorize
                                        access="!hasRole('ROLE_finance@localInvoice_UPDATE')">
                                    disabled="disabled"
                                </sec:authorize>
                                onclick="update_local_invoice_modal()">Save Changes
                        </button>
                        <button type="button" class="btn btn-success"
                                value="Print Local Invoice"
                                <sec:authorize
                                        access="!hasRole('ROLE_finance@localInvoice_PRINT')">
                                    disabled="disabled"
                                </sec:authorize>
                                onclick="load_model_report()">Report
                        </button>
                        <button type="button" class="btn btn-danger"
                                <sec:authorize
                                        access="!hasRole('ROLE_finance@localInvoice_DELETE')">
                                    disabled="disabled"
                                </sec:authorize>
                                data-toggle=confirmation
                                data-btn-ok-label="Continue" data-btn-ok-icon="glyphicon glyphicon-share-alt"
                                data-btn-ok-class="btn-success"
                                data-btn-cancel-label="Cancel" data-btn-cancel-icon="glyphicon glyphicon-ban-circle"
                                data-btn-cancel-class="btn-danger"
                                data-title="Deleting Local Invoice"
                                data-content="Are you sure you want to Delete this Local Invoice? "
                                data-id="modal"
                                data-on-confirm="delete_local_invoice">
                            Delete
                        </button>
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="reportOptionModel">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Report Selection</h4>
            </div>
            <form class="form-horizontal" method="post" name="reportModel" id="reportModel">
                <div class="modal-body panel">
                    <div class="form-group">
                        <label class="col-md-3 col-xs-12 control-label">Report Name</label>
                        <div class="col-md-8 col-xs-12">
                            <select name="reportSeq"
                                    id="reportModel_reportSeq"
                                    data-live-search="true"
                                    class="form-control select"
                                    data-validate="true" required
                                    data-error="Please Select Report Name"
                                    data-live-search="true" placeholder="Report">
                                <option value="">Select</option>
                            </select>
                        </div>
                        <div class="help-block with-errors"></div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 col-xs-12 control-label">Format</label>
                        <div class="col-md-6 col-xs-12">
                            <label><input type="radio" class="radio-button" name="reportType"
                                          id="reportModel_pdf" checked value="1"/> PDF</label>
                            <sec:authorize access="hasRole('ROLE_finance@localInvoice_RTF')">
                                <label><input type="radio" class="radio-button" name="reportType"
                                              id="reportModel_rtf"
                                              value="1"/> RTF</label>
                            </sec:authorize>
                        </div>

                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary pull-right" value="generateReport"
                            onclick="view_local_invoice_report()">Generate
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>