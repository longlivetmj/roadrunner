<%@ include file="/WEB-INF/jsp/include.jsp" %>
<script type="text/javascript" src="../../../customjs/master/unitManagement.js"></script>
<div class="page-title">
    <h3><span class="fa fa-user"></span>Unit Management</h3>
</div>

<div class="row">
    <div class="col-md-12">

        <div class="panel panel-default tabs">
            <ul class="nav nav-tabs" role="tablist">
                <li class="active"><a href="#tab-first" role="tab" data-toggle="tab">Unit Creation</a></li>
                <li><a href="#tab-second" role="tab" data-toggle="tab">Unit Search</a></li>
            </ul>
            <div class="panel-body tab-content">
                <div class="tab-pane active" id="tab-first">
                    <div class="col-md-6">
                        <form class="form-horizontal unitForm" method="post" name="create" id="create">
                            <div class="form-group removeFromModal">
                                <div class="col-md-7">
                                    <h4 class="subTitle">Unit Details</h4>
                                </div>
                                <div class="col-md-5 operations" style="display: none">
                                    <button type="button" class="btn btn-success" onclick="new_form(formTemplate, 'update','create')">
                                        New
                                    </button>
                                    <button type="button" class="btn btn-danger"
                                            <sec:authorize
                                                    access="!hasRole('ROLE_master@unitManagement_DELETE')">
                                                disabled="disabled"
                                            </sec:authorize>
                                            data-toggle=confirmation
                                            data-btn-ok-label="Continue"
                                            data-btn-ok-icon="glyphicon glyphicon-share-alt"
                                            data-btn-ok-class="btn-success"
                                            data-btn-cancel-label="Cancel"
                                            data-btn-cancel-icon="glyphicon glyphicon-ban-circle"
                                            data-btn-cancel-class="btn-danger"
                                            data-title="Deleting Unit"
                                            data-content="Are you sure you want to Delete this Unit? "
                                            data-id="update"
                                            data-on-confirm="delete_unit">
                                        Delete
                                    </button>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Unit Code</label>

                                <div class="col-md-6 col-xs-12">
                                    <input type="text" maxlength="15" class="form-control" id="unitCode"
                                           name="unitCode"
                                           rv-value="unit.unitCode"
                                           pattern="^[a-zA-Z0-9\s]+$"
                                           data-error="Please Provide a Valid Unit Code"
                                           required/>
                                    <span class="help-block">Required, Unit Code</span>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Unit Name</label>

                                <div class="col-md-6 col-xs-12">
                                    <input type="text" maxlength="50" class="form-control" id="unitName"
                                           name="unitName"
                                           rv-value="unit.unitName"
                                           pattern="^[a-zA-Z0-9\s]+$"
                                           data-error="Please Provide a Valid Unit Name"
                                           required/>
                                    <span class="help-block">Required, Unit Name</span>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Used For</label>

                                <div class="col-md-6 col-xs-12">
                                    <select name="usedFor" id="usedFor"
                                            rv-value="unit.usedFor"
                                            class="form-control select"
                                            data-live-search="true"
                                            placeholder="Status"
                                            data-error="Please Select Used for"
                                            required>
                                        <option value="">Select</option>
                                        <c:forEach items="${usedForList}" var="usedFor">
                                            <option value="${usedFor.unitCategory}">${usedFor.unitCategory}</option>
                                        </c:forEach>
                                    </select>
                                    <span class="help-block">Required</span>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Description</label>

                                <div class="col-md-6 col-xs-12">
                                    <input type="text" maxlength="150" class="form-control" id="description"
                                           name="description"
                                           rv-value="unit.description">
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Status</label>

                                <div class="col-md-6 col-xs-12">
                                    <select name="status" id="status"
                                            rv-value="unit.status"
                                            class="form-control select"
                                            data-live-search="true"
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
                                    <button type="button" class="btn btn-primary pull-right" value="create Unit"
                                            <sec:authorize
                                                    access="!hasRole('ROLE_master@unitManagement_CREATE')">
                                                disabled="disabled"
                                            </sec:authorize>
                                            onclick="create_unit()">Create
                                    </button>
                                </div>
                            </div>
                            <div class="form-group updateOperation" style="display: none">
                                <div class="form-group">
                                    <label class="col-md-3 text-right">Created By</label>
                                    <label class="col-md-8" rv-text="unit.createdBy"></label>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 text-right">Created Date</label>
                                    <label class="col-md-8" rv-text="unit.createdDate"></label>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 text-right">Last Modified By</label>
                                    <label class="col-md-8" rv-text="unit.lastModifiedBy"></label>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 text-right">Last Modified Date</label>
                                    <label class="col-md-8" rv-text="unit.lastModifiedDate"></label>
                                </div>
                                <div class="col-sm-offset-2 col-sm-7 removeFromModal">
                                    <button type="button" class="btn btn-primary pull-right"
                                            value="Update Delivery Type"
                                            <sec:authorize
                                                    access="!hasRole('ROLE_master@unitManagement_UPDATE')">
                                                disabled="disabled"
                                            </sec:authorize>
                                            onclick="update_unit()">Update
                                    </button>
                                </div>
                            </div>
                            <input type="hidden" name="unitSeq" id="unitSeq" value="" rv-value="unit.unitSeq"/>
                        </form>
                    </div>
                </div>
                <div class="tab-pane" id="tab-second">
                    <div class="row">
                        <div class="col-md-6">
                            <form class="form-horizontal" method="post" name="search" id="search">
                                <div class="form-group">
                                    <h4 class="subTitle">Search Unit</h4>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Unit Code</label>

                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" class="form-control" id="search_unitCode"
                                               name="unitCode" required/>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Used For</label>

                                    <div class="col-md-6 col-xs-12">
                                        <select name="usedFor" id="search_usedFor" class="form-control select"
                                                data-live-search="true"
                                                placeholder="Status">
                                            <option value="None">Select</option>
                                            <c:forEach items="${usedForList}" var="usedFor">
                                                <option value="${usedFor.unitCategory}">${usedFor.unitCategory}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Unit Name</label>

                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" class="form-control" id="search_unitName"
                                               name="unitName" required/>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <div class="col-sm-offset-2 col-sm-7">
                                        <button type="button" class="btn btn-primary pull-right" value="searchUnit"
                                                <sec:authorize
                                                        access="!hasRole('ROLE_master@unitManagement_VIEW')">
                                                    disabled="disabled"
                                                </sec:authorize>
                                                onclick="search_unit()">Search
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="form-group" id="loadUnitList">
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
                <h4 class="modal-title">Unit Details</h4>
            </div>
            <form class="form-horizontal" method="post" name="modal" id="modal">
                <div class="modal-body panel">

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-danger"
                            <sec:authorize
                                    access="!hasRole('ROLE_master@unitManagement_DELETE')">
                                disabled="disabled"
                            </sec:authorize>
                            data-toggle=confirmation
                            data-btn-ok-label="Continue" data-btn-ok-icon="glyphicon glyphicon-share-alt"
                            data-btn-ok-class="btn-success"
                            data-btn-cancel-label="Cancel" data-btn-cancel-icon="glyphicon glyphicon-ban-circle"
                            data-btn-cancel-class="btn-danger"
                            data-title="Deleting Unit" data-content="Are you sure you want to Delete this Unit? "
                            data-id="modal"
                            data-on-confirm="delete_unit">
                        Delete
                    </button>
                    <button type="button" class="btn btn-primary"
                            <sec:authorize access="!hasRole('ROLE_master@unitManagement_UPDATE')">
                                disabled="disabled"
                            </sec:authorize>
                            onclick="update_unit_modal()">Save Changes
                    </button>
                </div>
            </form>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>



