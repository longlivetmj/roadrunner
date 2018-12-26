<%@ include file="/WEB-INF/jsp/include.jsp" %>
<script type="text/javascript" src="../../../customjs/master/customerGroupManagement.js"></script>
<div class="page-title">
    <h3><span class="fa fa-road"></span>Customer Group Management</h3>
</div>
<!-- END PAGE TITLE -->
<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default tabs">
            <ul class="nav nav-tabs" role="tablist">
                <li class="active"><a href="#tab-first" role="tab" data-toggle="tab">Customer Group Creation</a></li>
                <li><a href="#tab-second" role="tab" data-toggle="tab">Customer Group Search</a></li>
            </ul>
            <div class="panel-body tab-content">
                <div class="tab-pane active" id="tab-first">
                    <div class="col-md-6">
                        <form class="form-horizontal customerGroupForm" method="post" id="create">
                            <div class="form-group removeFromModal">
                                <div class="col-md-7">
                                    <h4 class="subTitle">Customer Group Details</h4>
                                </div>
                                <div class="col-md-5 operations" style="display: none">
                                    <button type="button" class="btn btn-success" onclick="new_form(formTemplate, 'update','create')">
                                        New
                                    </button>
                                    <button type="button" class="btn btn-danger"
                                            <sec:authorize
                                                    access="!hasRole('ROLE_master@agentNetworkManagement_DELETE')">
                                                disabled="disabled"
                                            </sec:authorize>
                                            data-toggle=confirmation
                                            data-btn-ok-label="Continue"
                                            data-btn-ok-icon="glyphicon glyphicon-share-alt"
                                            data-btn-ok-class="btn-success"
                                            data-btn-cancel-label="Cancel"
                                            data-btn-cancel-icon="glyphicon glyphicon-ban-circle"
                                            data-btn-cancel-class="btn-danger"
                                            data-title="Deleting Customer Group"
                                            data-content="Are you sure you want to Delete this Customer Group ? "
                                            data-id="update"
                                            data-on-confirm="delete_customer_group">
                                        Delete
                                    </button>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Customer Group Code</label>
                                <div class="col-md-6 col-xs-12">
                                    <input type="text" class="form-control" name="customerGroupCode" maxlength="15"
                                           rv-value="customerGroup.customerGroupCode"
                                           pattern="^[a-zA-Z0-9\s]+$"
                                           id="customerGroupCode" data-error="Please Provide a Valid Customer Group Code"
                                           required/>
                                    <span class="help-block">Required, Customer Group Code</span>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Customer Group Name</label>
                                <div class="col-md-6 col-xs-12">
                                    <input type="text" class="form-control" name="customerGroupName" maxlength="50"
                                           rv-value="customerGroup.customerGroupName"
                                           id="customerGroupName"
                                           data-error="Please Provide a Valid Customer Group Name"
                                           pattern="^[a-zA-Z0-9\s]+$"
                                           required/>
                                    <span class="help-block">Required, Customer Group Name</span>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Status</label>
                                <div class="col-md-6 col-xs-12">
                                    <select name="status" id="status"  rv-value="customerGroup.status"
                                            class="form-control select" data-validate="true" required
                                            data-live-search="true" placeholder="Status">
                                        <c:forEach items="${statusList}" var="status">
                                            <option value="${status.statusSeq}">${status.status}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group createOperation removeFromModal">
                                <div class="col-sm-offset-2 col-sm-7">
                                    <button type="button" class="btn btn-primary pull-right"
                                            value="create Agent Network"
                                            <sec:authorize
                                                    access="!hasRole('ROLE_master@customerGroupManagement_CREATE')">
                                                disabled="disabled"
                                            </sec:authorize>
                                            onclick="create_customer_group()">Create
                                    </button>
                                </div>
                            </div>
                            <div class="form-group updateOperation" style="display: none">
                                <div class="form-group">
                                    <label class="col-md-3 text-right">Created By</label>
                                    <label class="col-md-8" rv-text="customerGroup.createdBy"></label>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 text-right">Created Date</label>
                                    <label class="col-md-8" rv-text="customerGroup.createdDate"></label>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 text-right">Last Modified By</label>
                                    <label class="col-md-8" rv-text="customerGroup.lastModifiedBy"></label>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 text-right">Last Modified Date</label>
                                    <label class="col-md-8" rv-text="customerGroup.lastModifiedDate"></label>
                                </div>
                                <div class="col-sm-offset-2 col-sm-7 removeFromModal">
                                    <button type="button" class="btn btn-primary pull-right"
                                            value="Update Customer Group"
                                            <sec:authorize
                                                    access="!hasRole('ROLE_master@customerGroupManagement_UPDATE')">
                                                disabled="disabled"
                                            </sec:authorize>
                                            onclick="update_customer_group()">Update
                                    </button>
                                </div>
                            </div>
                            <input type="hidden" name="customerGroupSeq" id="customerGroupSeq" value="" rv-value="customerGroup.customerGroupSeq"/>
                            <input type="hidden" name="companyProfileSeq" value="${companyProfileSeq}" rv-value="customerGroup.companyProfileSeq"/>
                        </form>
                    </div>
                </div>
                <div class="tab-pane" id="tab-second">
                    <div class="row">
                        <div class="col-md-6">
                            <form class="form-horizontal" method="post" name="search" id="search">
                                <div class="form-group">
                                    <h4 class="subTitle">Search Customer Group</h4>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Customer Group Code</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" class="form-control" name="customerGroupCode"
                                               id="search_customerGroupCode"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Customer Group Name</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" class="form-control" name="customerGroupName"
                                               id="search_customerGroupName"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-sm-offset-2 col-sm-7">
                                        <button type="button" class="btn btn-primary pull-right"
                                                value="loadCustomerGroupData"
                                                <sec:authorize
                                                        access="!hasRole('ROLE_master@customerGroupManagement_VIEW')">
                                                    disabled="disabled"
                                                </sec:authorize>
                                                onclick="load_customer_group_table_data()">Search
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="form-group" id="loadCustomerGroupListData"></div>
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
                <h4 class="modal-title">Customer Group Details</h4>
            </div>
            <form class="form-horizontal" method="post" name="modal" id="modal">
                <div class="modal-body">

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-danger"
                            <sec:authorize
                                    access="!hasRole('ROLE_master@agentNetworkManagement_DELETE')">
                                disabled="disabled"
                            </sec:authorize>
                            data-toggle=confirmation
                            data-btn-ok-label="Continue" data-btn-ok-icon="glyphicon glyphicon-share-alt"
                            data-btn-ok-class="btn-success"
                            data-btn-cancel-label="Cancel" data-btn-cancel-icon="glyphicon glyphicon-ban-circle"
                            data-btn-cancel-class="btn-danger"
                            data-title="Deleting Customer Group" data-content="Are you sure you want to Delete this Customer Group? "
                            data-id="modal"
                            data-on-confirm="delete_customer_group">
                        Delete
                    </button>
                    <button type="button" class="btn btn-primary"
                            <sec:authorize access="!hasRole('ROLE_master@customerGroupManagement_UPDATE')">
                                disabled="disabled"
                            </sec:authorize>
                            onclick="update_customer_group_popup()">Save Changes
                    </button>

                </div>
            </form>
        </div><!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
