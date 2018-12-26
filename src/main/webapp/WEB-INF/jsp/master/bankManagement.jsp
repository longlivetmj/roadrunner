<%@ include file="/WEB-INF/jsp/include.jsp" %>
<script type="text/javascript" src="../../../customjs/master/bankManagement.js"></script>
<div class="page-title">
    <h3><span class="fa fa-user"></span>Bank Management</h3>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default tabs">
            <ul class="nav nav-tabs" role="tablist">
                <li class="active"><a href="#tab-first" role="tab" data-toggle="tab">Bank Creation</a></li>
                <li><a href="#tab-second" role="tab" data-toggle="tab">Bank Search</a></li>
            </ul>
            <div class="panel-body tab-content">
                <div class="tab-pane active" id="tab-first">
                    <div class="col-md-6">
                        <form class="form-horizontal bankForm" method="post" name="create" id="create">
                            <div class="form-group removeFromModal">
                                <div class="col-md-7">
                                    <h4 class="subTitle">Bank Details</h4>
                                </div>
                                <div class="col-md-5 operations" style="display: none">
                                    <button type="button" class="btn btn-success" onclick="new_form(formTemplate, 'update','create')">
                                        New
                                    </button>
                                    <button type="button" class="btn btn-danger"
                                            <sec:authorize
                                                    access="!hasRole('ROLE_master@bankManagement_DELETE')">
                                                disabled="disabled"
                                            </sec:authorize>
                                            data-toggle=confirmation
                                            data-btn-ok-label="Continue"
                                            data-btn-ok-icon="glyphicon glyphicon-share-alt"
                                            data-btn-ok-class="btn-success"
                                            data-btn-cancel-label="Cancel"
                                            data-btn-cancel-icon="glyphicon glyphicon-ban-circle"
                                            data-btn-cancel-class="btn-danger"
                                            data-title="Deleting Bank"
                                            data-content="Are you sure you want to Delete this Bank? "
                                            data-id="update"
                                            data-on-confirm="delete_bank">
                                        Delete
                                    </button>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Bank Code</label>
                                <div class="col-md-6 col-xs-12">
                                    <input type="text" maxlength="10" class="form-control" id="bankCode"
                                           name="bankCode"
                                           rv-value="bank.bankCode"
                                           data-error="Please Provide a Valid Bank Code"
                                           pattern="^[a-zA-Z0-9\s]+$"
                                           required/>
                                    <span class="help-block">Required, Bank Code</span>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Bank Name</label>

                                <div class="col-md-6 col-xs-12">
                                    <input type="text" maxlength="100" class="form-control" id="bankName"
                                           name="bankName"
                                           rv-value="bank.bankName"
                                           data-error="Please Provide a Valid Bank Name"
                                           pattern="^[a-zA-Z\s]+$"
                                           required/>
                                    <span class="help-block">Required, Bank Name</span>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">SLIPS Code</label>

                                <div class="col-md-6 col-xs-12">
                                    <input type="text" class="form-control" id="slipsCode" name="slipsCode"
                                           rv-value="bank.slipsCode" maxlength="150"
                                           data-error="Please Provide a Valid SLIPS Code"
                                           pattern="^[a-zA-Z0-9\s]+$"
                                           required/>
                                    <span class="help-block">Required, SLIPS Code</span>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Status</label>

                                <div class="col-md-6 col-xs-12">
                                    <select name="status" id="status" class="form-control select"
                                            rv-value="bank.status"
                                            data-live-search="true"
                                            data-error="Please Select Status" data-validate="true" required
                                            aria-required="true"
                                            placeholder="Status">
                                        <c:forEach items="${statusList}" var="status">
                                            <option value="${status.statusSeq}">${status.status}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group createOperation removeFromModal">
                                <div class="col-sm-offset-2 col-sm-7">
                                    <button type="button" class="btn btn-primary pull-right" value="create Bank"
                                            <sec:authorize
                                                    access="!hasRole('ROLE_master@bankManagement_CREATE')">
                                                disabled="disabled"
                                            </sec:authorize>
                                            onclick="create_bank()">Create
                                    </button>
                                </div>
                            </div>
                            <div class="form-group updateOperation" style="display: none">
                                <div class="form-group">
                                    <label class="col-md-3 text-right">Created By</label>
                                    <label class="col-md-8" rv-text="bank.createdBy"></label>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 text-right">Created Date</label>
                                    <label class="col-md-8" rv-text="bank.createdDate"></label>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 text-right">Last Modified By</label>
                                    <label class="col-md-8" rv-text="bank.lastModifiedBy"></label>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 text-right">Last Modified Date</label>
                                    <label class="col-md-8" rv-text="bank.lastModifiedDate"></label>
                                </div>
                                <div class="col-sm-offset-2 col-sm-7 removeFromModal">
                                    <button type="button" class="btn btn-primary pull-right" value="update Bank"
                                            <sec:authorize
                                                    access="!hasRole('ROLE_master@bankManagement_UPDATE')">
                                                disabled="disabled"
                                            </sec:authorize>
                                            onclick="update_bank()">Update
                                    </button>
                                </div>
                            </div>
                            <input type="hidden" name="bankSeq" id="bankSeq" value="" rv-value="bank.bankSeq"/>
                        </form>
                    </div>
                </div>
                <div class="tab-pane" id="tab-second">
                    <div class="row">
                        <div class="col-md-6">
                            <form class="form-horizontal" method="post" name="search" id="search">
                                <div class="form-group">
                                    <h4 class="subTitle">Search Bank</h4>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Bank Name</label>

                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" class="form-control" id="search_bankName"
                                               name="bankName" required/>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Bank Code</label>

                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" class="form-control" id="search_bankCode"
                                               name="bankCode"
                                               data-error="Please Provide a Valid Bank Code"
                                               pattern="^[a-zA-Z0-9\s]+$"
                                               required/>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <div class="col-sm-offset-2 col-sm-7">
                                        <button type="button" class="btn btn-primary pull-right" value="searchBank"
                                                <sec:authorize
                                                        access="!hasRole('ROLE_master@bankManagement_VIEW')">
                                                    disabled="disabled"
                                                </sec:authorize>
                                                onclick="search_bank()">Search
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="form-group" id="loadBankList">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="myModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Bank Details</h4>
            </div>
            <form class="form-horizontal" method="post" name="modal" id="modal">
                <div class="modal-body panel">

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-danger"
                            <sec:authorize
                                    access="!hasRole('ROLE_master@bankManagement_DELETE')">
                                disabled="disabled"
                            </sec:authorize>
                            data-toggle=confirmation
                            data-btn-ok-label="Continue" data-btn-ok-icon="glyphicon glyphicon-share-alt"
                            data-btn-ok-class="btn-success"
                            data-btn-cancel-label="Cancel" data-btn-cancel-icon="glyphicon glyphicon-ban-circle"
                            data-btn-cancel-class="btn-danger"
                            data-title="Deleting Bank" data-content="Are you sure you want to Delete this Bank? "
                            data-id="modal"
                            data-on-confirm="delete_bank">
                        Delete
                    </button>
                    <button type="button" class="btn btn-primary"
                            <sec:authorize
                                    access="!hasRole('ROLE_master@bankManagement_UPDATE')">
                                disabled="disabled"
                            </sec:authorize>
                            onclick="update_bank_modal()">Save Changes
                    </button>
                </div>
            </form>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>

