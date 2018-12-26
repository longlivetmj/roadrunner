
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<script type="text/javascript" src="../../../customjs/finance/exchangeRateManagement.js"></script>
<div class="page-title">
    <h3><span class="fa fa-road"></span>Exchange Rate Management</h3>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default tabs">
            <ul class="nav nav-tabs" role="tablist">
                <li class="active"><a href="#tab-first" role="tab" data-toggle="tab">Exchange Rate Creation</a></li>
                <li><a href="#tab-second" role="tab" data-toggle="tab">Exchange Rate Search</a></li>
            </ul>
            <div class="panel-body tab-content">
                <div class="tab-pane active" id="tab-first">

                    <form class="form-horizontal exchangeRateForm" method="post" name="create" id="create">
                        <div class="form-group removeFromModal">
                            <div class="col-md-5 operations" style="display: none">
                                <button type="button" class="btn btn-success"
                                        onclick="new_form(formTemplate,'update','create')">
                                    New
                                </button>
                                <button type="button" class="btn btn-danger"
                                        <sec:authorize
                                                access="!hasRole('ROLE_finance@exchangeRateManagement_DELETE')">
                                            disabled="disabled"
                                        </sec:authorize>
                                        data-toggle=confirmation
                                        data-btn-ok-label="Continue"
                                        data-btn-ok-icon="glyphicon glyphicon-share-alt"
                                        data-btn-ok-class="btn-success"
                                        data-btn-cancel-label="Cancel"
                                        data-btn-cancel-icon="glyphicon glyphicon-ban-circle"
                                        data-btn-cancel-class="btn-danger"
                                        data-title="Deleting Exchange Rate"
                                        data-content="Are you sure you want to delete this Exchange Rate ?"
                                        data-id="update"
                                        data-on-confirm="delete_exchange_rate">
                                    Delete
                                </button>
                            </div>
                        </div>
                        <div class="col-md-12">

                        </div>
                        <div class="col-md-12">
                            <div class="panel panel-success">
                                <div class="panel-body">
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Module</label>
                                            <div class="col-md-6 col-xs-12">
                                                <select name="moduleSeq" id="moduleSeq"
                                                        rv-value="exchangeRate.moduleSeq"
                                                        class="form-control select"
                                                        data-live-search="true"
                                                        required
                                                        data-error="Please Select Module"
                                                        data-validate="true">
                                                    <option value="">Select</option>
                                                    <c:forEach items="${moduleList}" var="module">
                                                        <option value="${module.moduleSeq}">${module.moduleName}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <div class="help-block with-errors"></div>
                                        </div>
                                        <div class="form-group sourceType">
                                            <label class="col-md-3 col-xs-12 control-label">Source</label>
                                            <div class="col-md-6 col-xs-12">
                                                <select name="exchangeRateSourceType" id="exchangeRateSourceType"
                                                        rv-value="exchangeRate.exchangeRateSourceType"
                                                        class="form-control select"
                                                        data-validate="true"
                                                        data-live-search="true"
                                                        placeholder="exchangeRateSourceType">
                                                    <option value="">Select</option>
                                                    <c:forEach items="${sourceTypeList}" var="exchangeSourceType">
                                                        <option value="${exchangeSourceType.exchangeRateSourceType}">${exchangeSourceType.exchangeRateSourceTypeDescription}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>

                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Currency</label>
                                            <div class="col-md-6 col-xs-12">
                                                <select name="baseCurrencySeq" id="baseCurrencySeq"
                                                        rv-value="exchangeRate.baseCurrencySeq"
                                                        class="form-control select" data-validate="true"
                                                        required
                                                        data-error="Please Select Base Currency"
                                                        data-live-search="true"
                                                        placeholder="baseCurrencySeq">
                                                    <option value="">Select</option>
                                                    <c:forEach items="${currencyList}" var="currency">
                                                        <option value="${currency.currencySeq}" ${defaultCurrencySeq eq currency.currencySeq ? "selected":""}>${currency.currencyCode}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <div class="help-block with-errors"></div>
                                        </div>

                                        <div class="form-group sourceBank">
                                            <label class="col-md-3 col-xs-12 control-label">Source(Bank)</label>
                                            <div class="col-md-6 col-xs-12">
                                                <select name="bankSeq" id="bankSeq"
                                                        rv-value="exchangeRate.bankSeq"
                                                        class="form-control select" data-validate="true"
                                                        data-live-search="true"
                                                        placeholder="sourceBankSeq">
                                                    <option value="">Select</option>
                                                    <c:forEach items="${bankList}" var="bank">
                                                        <option value="${bank.bankSeq}">${bank.bankName}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>

                                        </div>
                                        <div class="form-group effectiveFromDate">
                                            <label class="col-md-3 col-xs-12 control-label">Effective From</label>
                                            <div class="col-md-6 col-xs-12">
                                                    <input type="text" class="form-control datepicker"
                                                           id="effectiveFrom"
                                                           name="effectiveFrom"
                                                           rv-value="exchangeRate.effectiveFrom"/>
                                            </div>

                                        </div>
                                        <div class="form-group effectiveToDate">
                                            <label class="col-md-3 col-xs-12 control-label">Effective To</label>
                                            <div class="col-md-6 col-xs-12">
                                                    <input type="text" class="form-control datepicker"
                                                           id="effectiveTo"
                                                           name="effectiveTo"
                                                           rv-value="exchangeRate.effectiveTo"/>
                                            </div>

                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Status</label>
                                            <div class="col-md-6 col-xs-12">
                                                <select name="status" id="status"
                                                        rv-value="exchangeRate.status"
                                                        class="form-control select"
                                                        data-validate="true"
                                                        data-live-search="true">
                                                    <c:forEach items="${statusList}" var="status">
                                                        <option value="${status.statusSeq}">${status.status}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group updateOperation" style="display: none">
                                            <div class="form-group">
                                                <label class="col-md-3 text-right">Created By</label>
                                                <label class="col-md-8" rv-text="exchangeRate.createdBy"></label>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 text-right">Created Date</label>
                                                <label class="col-md-8" rv-text="exchangeRate.createdDate"></label>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 text-right">Last Modified By</label>
                                                <label class="col-md-8" rv-text="exchangeRate.lastModifiedBy"></label>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 text-right">Last Modified Date</label>
                                                <label class="col-md-8" rv-text="exchangeRate.lastModifiedDate"></label>
                                            </div>

                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <div class="panel panel-default">
                                                    <div class="panel-heading">
                                                        <h3 class="panel-title">Currency Exchange Rates</h3>
                                                        <div style="float: right">
                                                            <button type="button"
                                                                    class="transparent-btn fa fa-plus-square fa-2x"
                                                                    onclick="add_table_row('exchangeRateDetailOperator',this)"></button>
                                                            <button type="button"
                                                                    class="transparent-btn fa fa-minus-square fa-2x"
                                                                    onclick="delete_table_row('exchangeRateDetailOperator',this)"></button>
                                                        </div>
                                                    </div>
                                                    <div class="panel-body panel-body-table">
                                                        <div class="able-striped">
                                                            <table class="exchangeRateDetailOperator auto-grow-table table table-bordered table-striped table-actions"
                                                                   border="1" width="100%">
                                                                <thead>
                                                                <tr>
                                                                    <th></th>
                                                                    <th>Currency</th>
                                                                    <th>Rate</th>
                                                                    <th class="hidden"></th>
                                                                </tr>

                                                                </thead>
                                                                <tbody>
                                                                <tr rv-each-tmp="exchangeRate.exchangeRateDetails | isNotEmpty">
                                                                    <td width="3%"><input type="checkbox" name="chk"/>
                                                                    </td>
                                                                    <td width="46%">
                                                                        <select name="exchangeRateDetails[0].currencySeq"
                                                                                rv-value="tmp.currencySeq"
                                                                                class="form-control select"
                                                                                id="currencySeq0"
                                                                                data-live-search="true"
                                                                                data-validate="true"
                                                                                aria-required="true" style="width: 90%">
                                                                            <option value="">Select</option>
                                                                            <c:forEach items="${currencyList}"
                                                                                       var="currency">
                                                                                <option value="${currency.currencySeq}">${currency.currencyCode}</option>
                                                                            </c:forEach>
                                                                        </select>
                                                                    </td>
                                                                    <td width="46%">
                                                                        <input type="text" class="form-control"
                                                                               id="rate0"
                                                                               rv-value="tmp.rate"
                                                                               name="exchangeRateDetails[0].rate"
                                                                               maxlength="10"/>
                                                                    </td>
                                                                    <td class="hidden"><input type="hidden"
                                                                                              rv-value="tmp.exchangeRateDetailSeq"
                                                                                              name="exchangeRateDetails[0].exchangeRateDetailSeq"/>
                                                                    </td>

                                                                </tr>
                                                                </tbody>
                                                            </table>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-footer">
                                <div class="form-group createOperation removeFromModal">
                                    <div class="col-sm-offset-2 col-sm-10">
                                        <button type="button" class="btn btn-primary pull-right"
                                                value="create Exchange Rate"
                                                <sec:authorize
                                                        access="!hasRole('ROLE_finance@exchangeRateManagement_CREATE')">
                                                    disabled="disabled"
                                                </sec:authorize>
                                                onclick="create_exchange_rate()">Create
                                        </button>
                                    </div>
                                </div>
                                <div class="form-group updateOperation" style="display: none">
                                    <div class="col-sm-offset-2 col-sm-10 removeFromModal">
                                        <button type="button" class="btn btn-primary pull-right"
                                                value="Update Exchange Rate"
                                                <sec:authorize
                                                        access="!hasRole('ROLE_finance@exchangeRateManagement_UPDATE')">
                                                    disabled="disabled"
                                                </sec:authorize>
                                                onclick="update_exchange_rate()">Update
                                        </button>
                                    </div>
                                    <input type="hidden" name="exchangeRateSeq" id="exchangeRateSeq" value=""
                                           rv-value="exchangeRate.exchangeRateSeq"/>
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
                                    <h4 class="subTitle">Search Exchange Rate</h4>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Module</label>
                                    <div class="col-md-6 col-xs-12">
                                        <select name="moduleSeq"
                                                id="search_moduleSeq"
                                                class="form-control select"
                                                data-live-search="true"
                                                data-validate="true">
                                            <option value="">Select</option>
                                            <c:forEach items="${moduleList}" var="module">
                                                <option value="${module.moduleSeq}">${module.moduleName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Exchange Rate ID</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" class="form-control" id="search_exchangeRateSeq"
                                               name="exchangeRateSeq"/>
                                    </div>
                                </div>
                                <div class="form-group" id="searchEffectiveOn">
                                    <label class="col-md-3 col-xs-12 control-label">Effective On</label>
                                    <div class="col-md-8 col-xs-12">
                                        <div class="input-group col-md-9">
                                            <input type="text" class="form-control datepicker"
                                                   id="search_effectiveFrom"
                                                   name="effectiveFrom"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Base Currency</label>
                                    <div class="col-md-6 col-xs-12">
                                        <select name="baseCurrencySeq" id="search_baseCurrencySeq"
                                                class="form-control select" data-validate="true"
                                                data-live-search="true"
                                                placeholder="baseCurrencySeq">
                                            <option value="">Select</option>
                                            <c:forEach items="${currencyList}" var="currency">
                                                <option value="${currency.currencySeq}">${currency.currencyCode}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Status</label>
                                    <div class="col-md-6 col-xs-12">
                                        <select name="status" id="search_status"
                                                class="form-control select"
                                                data-validate="true"
                                                data-live-search="true">
                                            <option value="">Select</option>
                                            <c:forEach items="${statusList}" var="status">
                                                <option value="${status.statusSeq}">${status.status}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-sm-offset-2 col-sm-7">
                                        <button type="button" class="btn btn-primary pull-right"
                                                value="search Exchange Rate"
                                                <sec:authorize
                                                        access="!hasRole('ROLE_finance@exchangeRateManagement_VIEW')">
                                                    disabled="disabled"
                                                </sec:authorize>
                                                onclick="search_exchange_rate()">Search
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="form-group" id="loadExchangeRateData"></div>
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
                <h4 class="modal-title">Exchange Rate Detail</h4>
            </div>
            <form class="form-horizontal" method="post" name="modal" id="modal">
                <div class="modal-body panel">

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-danger"
                            <sec:authorize
                                    access="!hasRole('ROLE_finance@exchangeRateManagement_DELETE')">
                                disabled="disabled"
                            </sec:authorize>
                            data-toggle=confirmation
                            data-btn-ok-label="Continue" data-btn-ok-icon="glyphicon glyphicon-share-alt"
                            data-btn-ok-class="btn-success"
                            data-btn-cancel-label="Cancel" data-btn-cancel-icon="glyphicon glyphicon-ban-circle"
                            data-btn-cancel-class="btn-danger"
                            data-title="Deleting Exchange Rate"
                            data-content="Are you sure you want to Delete this Exchange Rate? "
                            data-id="modal"
                            data-on-confirm="delete_exchange_rate">
                        Delete
                    </button>
                    <button type="button" class="btn btn-primary"
                            <sec:authorize access="!hasRole('ROLE_finance@exchangeRateManagement_UPDATE')">
                                disabled="disabled"
                            </sec:authorize>
                            onclick="update_exchange_rate_model()">Save Changes
                    </button>

                </div>
            </form>
        </div><!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
