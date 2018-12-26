<%@ include file="/WEB-INF/jsp/include.jsp" %>
<script type="text/javascript" src="../../../cargodrivejs/finance/localCreditNoteManagement.js"></script>
<link href="../../../theme/css/custom.css" rel="stylesheet" type="text/css">
<div class="page-title">
    <h3><span class="fa fa-user"></span>Local Credit Note Details</h3>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default tabs">
            <ul class="nav nav-tabs" role="tablist">
                <li class="active"><a href="#tab-first" role="tab" data-toggle="tab">Local Credit Note Creation</a></li>
            </ul>
            <div class="panel-body tab-content">

                <div class="tab-pane active" id="tab-first">
                    <div class="col-md-12">
                        <div class="panel panel-danger push-down-10">
                            <div class="panel-heading collapsed" data-toggle="collapse" href="#booking_info">
                                <h3 class="panel-title">Search Details</h3>
                                <ul class="panel-controls">
                                    <li><a href="#booking_info"><span
                                            class="fa fa-angle-down"></span></a></li>
                                </ul>
                            </div>
                            <div class="panel-body">
                                <form class="form-horizontal" method="post" id="search">
                                    <input type="hidden" id="search_localCreditNoteHeaderSeq" value="${localCreditNote.localCreditNoteHeaderSeq}">

                                    <div class="col-md-12">
                                        <div class="col-md-3">
                                            <div class="form-group">
                                                <label class="col-md-4 col-xs-12 control-label">Module</label>
                                                <div class="col-md-8 col-xs-12">
                                                    <select class="form-control select" name="moduleSeq"
                                                            id="search_moduleSeq"
                                                            data-live-search="true"
                                                            placeholder="Module"
                                                            onchange="load_invoice_no_list()">
                                                        <c:forEach items="${moduleList}" var="module">
                                                            <option value="${module.moduleSeq}" ${module.moduleSeq eq localCreditNote.moduleSeq ? 'selected' : ''}>${module.moduleName}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <label class="col-md-4 col-xs-12 control-label">Invoice
                                                    Type</label>
                                                <div class="col-md-8 col-xs-12">
                                                    <select name="invoiceTypeSeq"
                                                            id="search_invoiceTypeSeq"
                                                            class="form-control select"
                                                            data-live-search="true"
                                                            title="Select Invoice Type"
                                                            onchange="load_invoice_no_list()">
                                                        <c:forEach items="${invoiceTypeList}" var="invoiceType">
                                                            <option value="${invoiceType.invoiceTypeSeq}" ${invoiceType.invoiceTypeSeq eq localCreditNote.invoiceTypeSeq ? 'selected' : ''}>${invoiceType.invoiceType}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="form-group" id="localInvoiceNo">
                                                <label class="col-md-4 col-xs-12 control-label">Invoice
                                                    No</label>
                                                <div class="col-md-8 col-xs-12">
                                                    <select name="localInvoiceSeq"
                                                            id="search_localInvoiceSeq"
                                                            rv-setselected="localInvoiceSeq"
                                                            class="form-control select"
                                                            data-validate="true"
                                                            data-abs-ajax-type="get"
                                                            data-abs-request-delay="500"
                                                            data-key="localInvoiceSeq"
                                                            data-value="localInvoiceNo"
                                                            title="Select and begin typing"
                                                            data-abs-ajax-url="finance/localCreditNoteManagement/findInvoiceList"
                                                            data-live-search="true">
                                                        <option value="${localCreditNote.localInvoiceSeq}" ${localCreditNote.localInvoiceSeq ne null ? 'selected' : ''}>${localCreditNote.localInvoice.localInvoiceNo}</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group" id="expenseVoucherNo">
                                                <label class="col-md-4 col-xs-12 control-label">Expense Voucher
                                                    No</label>
                                                <div class="col-md-8 col-xs-12">
                                                    <select name="expenseVoucherSeq"
                                                            id="search_expenseVoucherSeq"
                                                            rv-setselected="expenseVoucherSeq"
                                                            class="form-control select"
                                                            data-validate="true"
                                                            data-abs-ajax-type="get"
                                                            data-abs-request-delay="500"
                                                            data-key="expenseVoucherSeq"
                                                            data-value="expenseVoucherNo"
                                                            title="Select and begin typing"
                                                            data-abs-ajax-url="finance/localCreditNoteManagement/findExpenseVoucherList"
                                                            data-live-search="true">
                                                        <option value="${localCreditNote.expenseVoucherSeq}" ${localCreditNote.expenseVoucherSeq eq localCreditNote.expenseVoucherSeq ? 'selected' : ''}>${localCreditNote.expenseVoucher.expenseVoucherNo}</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-1">
                                            <div class="form-group">
                                                <div class="col-sm-offset-2 col-sm-10">
                                                    <button type="button"
                                                            class="btn btn-primary pull-right createOperation"
                                                            value="create Job Details"
                                                            <sec:authorize
                                                                    access="!hasRole('ROLE_finance@localCreditNoteManagement_VIEW')">
                                                                disabled="disabled"
                                                            </sec:authorize>
                                                            onclick="search_local_invoice_Detail()">Search
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>

                        <form class="form-horizontal loc" method="post" id="localCreditNote">
                            <div id="load_basic_details"></div>

                            <div id="load_financial_charge_details"></div>

                            <div class="panel panel-warning  updateOperation" style="display: none">
                                <div class="panel-heading collapsed" data-toggle="collapse" href="#statusRef">
                                    <h3 class="panel-title">Status</h3>
                                </div>
                                <div class="panel-body">
                                    <div class="col-md-12">
                                        <div class="col-md-6">
                                            <div class="form-group">
                                                <label class="col-md-1 col-xs-12 control-label">Status</label>
                                                <div class="col-md-8 col-xs-12" id="statusDiv">
                                                    <select name="status"
                                                            id="status"
                                                            rv-value="lcnResObject.status"
                                                            class="form-control select"
                                                            data-validate="true"
                                                            data-live-search="true">
                                                        <c:forEach items="${statusList}" var="status">
                                                            <option value="${status.statusSeq}"${status.statusSeq eq localCreditNote.status ? 'selected' : ''}>${status.status}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <button type="button" class="btn btn-success alwaysEnabled"
                                                    <sec:authorize
                                                            access="!hasRole('ROLE_finance@localCreditNoteManagement_CREATE')">
                                                        disabled="disabled"
                                                    </sec:authorize>
                                                    onclick="load_local_credit_note_page()">
                                                New
                                            </button>
                                            <button type="button" class="btn btn-danger"
                                                    id="deleteLocalCreditNote"
                                                    <sec:authorize
                                                            access="!hasRole('ROLE_finance@localCreditNoteManagement_DELETE')">
                                                        disabled="disabled"
                                                    </sec:authorize>
                                                    data-toggle=confirmation
                                                    data-btn-ok-label="Continue"
                                                    data-btn-ok-icon="glyphicon glyphicon-share-alt"
                                                    data-btn-ok-class="btn-success"
                                                    data-btn-cancel-label="Cancel"
                                                    data-btn-cancel-icon="glyphicon glyphicon-ban-circle"
                                                    data-btn-cancel-class="btn-danger"
                                                    data-title="Deleting Local Credit Note"
                                                    data-content="Are you sure you want to Delete this Credit Note ? "
                                                    data-id="create"
                                                    data-on-confirm="delete_local_credit_note">
                                                Delete
                                            </button>
                                            <button type="button" class="btn btn-info alwaysEnabled"
                                                    value="Print LCN"
                                                    id="printReport"
                                                    <sec:authorize
                                                            access="!hasRole('ROLE_finance@localCreditNoteManagement_PRINT')">
                                                        disabled="disabled"
                                                    </sec:authorize>
                                                    onclick="load_report()">Report
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-sm-offset-3 col-sm-8">
                                    <button type="button"
                                            class="btn btn-primary pull-right createOperation"
                                            value="create Local Credit Note"
                                            <sec:authorize
                                                    access="!hasRole('ROLE_finance@localCreditNoteManagement_CREATE')">
                                                disabled="disabled"
                                            </sec:authorize>
                                            onclick="create_local_credit_note()">Create
                                    </button>
                                </div>
                            </div>

                            <div class="form-group updateOperation" style="display: none">
                                <div class="col-sm-offset-2 col-sm-10 removeFromModal">
                                    <button type="button"
                                            class="btn btn-primary pull-right updateOperation alwaysEnabled"
                                            value="Update Credit Note"
                                            id="update_local_Credit_note_button"
                                            <sec:authorize
                                                    access="!hasRole('ROLE_finance@localCreditNoteManagement_UPDATE')">
                                                disabled="disabled"
                                            </sec:authorize>
                                            onclick="update_local_credit_note()">Update
                                    </button>
                                </div>
                                <div class="col-md-12 push-down-25" id="basicDetails">
                                    <div class="col-md-6">
                                        <div class="col-md-12">
                                            <div class="form-group">
                                                <label class="col-md-4 text-right">Created By :</label>
                                                <label class="col-md-8" rv-text="lcnResObject.createdBy">${localCreditNote.createdBy}</label>
                                            </div>
                                        </div>
                                        <div class="col-md-12">
                                            <div class="form-group">
                                                <label class="col-md-4 text-right">Created Date :</label>
                                                <label class="col-md-8" rv-text="lcnResObject.createdDate">${localCreditNote.createdDate}</label>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="col-md-12">
                                            <div class="form-group">
                                                <label class="col-md-4 text-right">Last Modified By :</label>
                                                <label class="col-md-8" rv-text="lcnResObject.lastModifiedBy">${localCreditNote.lastModifiedBy}</label>
                                            </div>
                                        </div>
                                        <div class="col-md-12">
                                            <div class="form-group">
                                                <label class="col-md-4 text-right">Last Modified Date :</label>
                                                <label class="col-md-8" rv-text="lcnResObject.lastModifiedDate">${localCreditNote.lastModifiedDate}</label>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>

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
                <div class="modal-body form-group">
                    <div class="form-group">
                        <label class="col-md-3 col-xs-12 control-label">Report Name</label>
                        <div class="col-md-8 col-xs-12">
                            <select name="reportName"
                                    id="reportModel_reportName"
                                    data-live-search="true"
                                    class="form-control select"
                                    data-validate="true" required
                                    data-error="Please Select Report Name"
                                    data-live-search="true" placeholder="Report">
                                <option value="">Select</option>
                                <c:forEach items="${reportList}" var="report">
                                    <option value="${report.reportName}">${report.displayName}</option>
                                </c:forEach>
                            </select>
                            <span class="help-block">Required, Select Report Name</span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 col-xs-12 control-label">Format</label>
                        <div class="col-md-6 col-xs-12">
                            <label><input type="radio" class="radio-button" name="reportType"
                                          id="reportModel_pdf" checked value="1"/> PDF</label>
                            <sec:authorize access="hasRole('ROLE_finance@localCreditNoteManagement_RTF')">
                                <label><input type="radio" class="radio-button" name="reportType"
                                              id="reportModel_rtf"
                                              value="1"/> RTF</label>
                            </sec:authorize>
                                                       <%--<label><input type="radio" class="radio-button" name="reportType"--%>
                                          <%--id="reportModel_xls"--%>
                                          <%--value="1"/> EXEL</label>--%>
                        </div>

                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary pull-right" value="generateReport"
                            onclick="view_house_bl_report()">Generate
                    </button>
                </div>

            </form>
        </div>
    </div>
</div>