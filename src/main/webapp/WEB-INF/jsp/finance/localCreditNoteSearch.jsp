<%@ include file="/WEB-INF/jsp/include.jsp" %>
<div class="page-title">
    <h3><span class="fa fa-users"></span>Search Local Credit Notes</h3>
</div>
<script type="text/javascript" src="../../../theme/js/plugins/datatables/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="../../../cargodrivejs/finance/localCreditNoteSearch.js"></script>
<link href="../../../theme/css/custom.css" rel="stylesheet" type="text/css">
<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default tabs">
            <ul class="nav nav-tabs tablist">
                <li class="active"><a href="#tab-first" class="tab" role="tab" data-toggle="tab">Local Credit Note
                    Search</a>
                </li>
            </ul>
            <div class="panel-body tab-content">
                <div class="tab-pane active" id="tab-first">
                    <div class="col-md-12">
                        <form class="form-horizontal" method="post" name="search" id="search">
                            <div class="form-group">
                                <h4 class="subTitle">Search Local Credit Note</h4>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Local Credit No.</label>
                                    <div class="col-md-8 col-xs-12">
                                        <input type="text"
                                               class="form-control"
                                               id="creditDebitNo"
                                               name="creditDebitNo"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Invoice
                                        Type</label>
                                    <div class="col-md-8 col-xs-12">
                                        <select name="invoiceTypeSeq"
                                                id="invoiceTypeSeq"
                                                class="form-control select"
                                                data-live-search="true"
                                                title="Select Invoice Type"
                                                onchange="load_invoice_no_list()">
                                            <c:forEach items="${invoiceTypeList}" var="invoiceType">
                                                <option value="${invoiceType.invoiceTypeSeq}">${invoiceType.invoiceType}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Effective Date</label>
                                    <div class="col-md-8 col-xs-12" id="sandbox-container">
                                        <div class="input-daterange input-group" id="datepicker">
                                            <input type="text" class="input-sm form-control" id="search_startDate"
                                                   name="startDate"
                                                   value='<fmt:formatDate pattern="yyyy-MM-dd" value="${fromDate}"/>'/>
                                            <span class="input-group-addon">to</span>
                                            <input type="text" class="input-sm form-control" id="search_endDate"
                                                   name="endDate"
                                                   value='<fmt:formatDate pattern="yyyy-MM-dd" value="${toDate}"/>'/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6 push-down-20">
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">local Invoice No</label>
                                    <div class="col-md-8 col-xs-12">
                                        <input type="text"
                                               class="form-control"
                                               id="localInvoiceNo"
                                               name="localInvoiceNo"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Expense Voucher No</label>
                                    <div class="col-md-8 col-xs-12">
                                        <input type="text"
                                               class="form-control"
                                               id="expenseVoucherNo"
                                               name="expenseVoucherNo"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Status</label>
                                    <div class="col-md-8 col-xs-12">
                                        <select name="status"
                                                id="status"
                                                class="form-control select"
                                                data-validate="true"
                                                data-live-search="true">
                                            <c:forEach items="${statusList}" var="status">
                                                <option value="${status.statusSeq}">${status.status}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-sm-offset-4 col-sm-7">
                                    <button type="button" class="btn btn-primary pull-right"
                                            value="search SOP"
                                            <sec:authorize
                                                    access="!hasRole('ROLE_finance@localCreditNoteSearch_VIEW')">
                                                disabled="disabled"
                                            </sec:authorize>
                                            onclick="search_local_Credit_notes_details()">Search
                                    </button>
                                </div>
                            </div>
                        </form>
                        <div class="col-md-12">
                            <div class="form-group" id="load_local_credit_note_detail"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
