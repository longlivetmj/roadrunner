<%@ include file="/WEB-INF/jsp/include.jsp" %>
<script type="text/javascript" src="../../../customjs/master/currencyManagement.js"></script>
<div class="page-title">
    <h3><span class="fa fa-user"></span>Currency Management</h3>
</div>
<!-- END PAGE TITLE -->

<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default tabs">
            <ul class="nav nav-tabs" role="tablist">
                <li class="active"><a href="#tab-first" role="tab" data-toggle="tab">Currency Creation</a></li>
                <li><a href="#tab-second" role="tab" data-toggle="tab">Currency Search</a></li>
            </ul>
            <div class="panel-body tab-content">
                <div class="tab-pane active" id="tab-first">
                    <div class="col-md-6">
                        <form class="form-horizontal currencyForm" method="post" name="create" id="create">
                            <div class="form-group removeFromModal">
                                <div class="col-md-7">
                                    <h4 class="subTitle">Currency Details</h4>
                                </div>
                                <div class="col-md-5 operations" style="display: none">
                                    <button type="button" class="btn btn-success"
                                            onclick="new_form(formTemplate, 'update','create')">
                                        New
                                    </button>
                                    <button type="button" class="btn btn-danger"
                                            <sec:authorize
                                                    access="!hasRole('ROLE_master@currencyManagement_DELETE')">
                                                disabled="disabled"
                                            </sec:authorize>
                                            data-toggle=confirmation
                                            data-btn-ok-label="Continue"
                                            data-btn-ok-icon="glyphicon glyphicon-share-alt"
                                            data-btn-ok-class="btn-success"
                                            data-btn-cancel-label="Cancel"
                                            data-btn-cancel-icon="glyphicon glyphicon-ban-circle"
                                            data-btn-cancel-class="btn-danger"
                                            data-title="Deleting Currency"
                                            data-content="Are You Sure You Want To Delete This Currency ? "
                                            data-id="update"
                                            data-on-confirm="delete_currency">
                                        Delete
                                    </button>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Currency Code</label>
                                <div class="col-md-6 col-xs-12">

                                    <input type="text" class="form-control text-uppercase" id="currencyCode"
                                           name="currencyCode"
                                           rv-value="currency.currencyCode"
                                           data-error="Please Provide a Valid Currency Code"
                                           required
                                           maxlength="15"/>
                                    <span class="help-block">Required, Currency Code</span>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Currency Name</label>
                                <div class="col-md-6 col-xs-12">
                                    <input type="text" class="form-control text-uppercase"
                                           id="currencyName"
                                           name="currencyName"
                                           rv-value="currency.currencyName"
                                           data-error="Please Provide a Valid Currency Name"
                                           pattern="^[a-zA-Z0-9\s]+$"
                                           required
                                           maxlength="50"/>
                                    <span class="help-block">Required, Currency Name</span>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Status</label>
                                <div class="col-md-6 col-xs-12">
                                    <select name="status" id="status"
                                            class="form-control select" data-validate="true" required
                                            data-live-search="true" placeholder="Status"
                                            rv-value="currency.status">
                                        <c:forEach items="${statusList}" var="status">
                                            <option value="${status.statusSeq}">${status.status}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>

                            <div class="form-group createOperation removeFromModal">
                                <div class="col-sm-offset-2 col-sm-7">
                                    <button type="button" class="btn btn-primary pull-right" value="createCurrency"
                                            <sec:authorize
                                                    access="!hasRole('ROLE_master@currencyManagement_UPDATE')">
                                                disabled="disabled"
                                            </sec:authorize>
                                            onclick="create_currency()">Create
                                    </button>
                                </div>
                            </div>
                            <div class="form-group updateOperation" style="display: none">
                                <div class="form-group">
                                    <label class="col-md-3 text-right">Created By</label>
                                    <label class="col-md-8" rv-text="currency.createdBy"></label>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 text-right">Created Date</label>
                                    <label class="col-md-8" rv-text="currency.createdDate"></label>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 text-right">Last Modified By</label>
                                    <label class="col-md-8" rv-text="currency.lastModifiedBy"></label>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 text-right">Last Modified Date</label>
                                    <label class="col-md-8" rv-text="currency.lastModifiedDate"></label>
                                </div>
                                <div class="col-sm-offset-2 col-sm-7 removeFromModal">
                                    <button type="button" class="btn btn-primary pull-right"
                                            value="Update Currency"
                                            <sec:authorize
                                                    access="!hasRole('ROLE_master@currencyManagement_UPDATE')">
                                                disabled="disabled"
                                            </sec:authorize>
                                            onclick="update_currency()">Update
                                    </button>
                                </div>
                            </div>
                            <input type="hidden" name="currencySeq" id="currencySeq" value=""
                                   rv-value="currency.currencySeq"/>
                        </form>
                    </div>
                </div>
                <div class="tab-pane" id="tab-second">
                    <div class="row">
                        <div class="col-md-6">
                            <form class="form-horizontal" method="post" name="search" id="search">
                                <div class="form-group">
                                    <h4 class="subTitle">Search Currency</h4>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Currency Code</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" class="form-control" id="search_currencyCode"
                                               name="currencyName"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Currency Name</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" class="form-control" id="search_currencyName"
                                               name="currencyName"/>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <div class="col-sm-offset-2 col-sm-7">
                                        <button type="button" class="btn btn-primary pull-right"
                                                value="searchCurrency"
                                                <sec:authorize
                                                        access="!hasRole('ROLE_master@currencyManagement_VIEW')">
                                                    disabled="disabled"
                                                </sec:authorize>
                                                onclick="search_currency()">Search
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="form-group" id="loadCurrencySearchResult"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Start modal -->

<div class="modal fade" id="myModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Currency Details</h4>
            </div>
            <form class="form-horizontal" method="post" name="modal" id="modal">
                <div class="modal-body panel">

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-danger"
                            <sec:authorize
                                    access="!hasRole('ROLE_master@currencyManagement_DELETE')">
                                disabled="disabled"
                            </sec:authorize>
                            data-toggle=confirmation
                            data-btn-ok-label="Continue" data-btn-ok-icon="glyphicon glyphicon-share-alt"
                            data-btn-ok-class="btn-success"
                            data-btn-cancel-label="Cancel" data-btn-cancel-icon="glyphicon glyphicon-ban-circle"
                            data-btn-cancel-class="btn-danger"
                            data-title="Deleting Currency"
                            data-content="Are You Sure You Want To Delete This Currency? "
                            data-id="modal"
                            data-on-confirm="delete_currency">
                        Delete
                    </button>
                    <button type="button" class="btn btn-primary"
                            <sec:authorize access="!hasRole('ROLE_master@currencyManagement_UPDATE')">
                                disabled="disabled"
                            </sec:authorize>
                            onclick="update_currency_modal_data()">Save Changes
                    </button>

                </div>
            </form>
        </div><!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- End modal -->




