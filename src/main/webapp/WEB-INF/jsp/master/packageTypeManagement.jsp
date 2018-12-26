<%@ include file="/WEB-INF/jsp/include.jsp" %>
<script type="text/javascript" src="../../../customjs/master/packageTypeManagement.js"></script>
<div class="page-title">
    <h3><span class="fa fa-user"></span>Package Type Management</h3>
</div>

<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default tabs">
            <ul class="nav nav-tabs" role="tablist">
                <li class="active"><a href="#tab-first" role="tab" data-toggle="tab">Package Type Creation</a></li>
                <li><a href="#tab-second" role="tab" data-toggle="tab">Package Type Search</a></li>
            </ul>
            <div class="panel-body tab-content">
                <div class="tab-pane active" id="tab-first">
                    <div class="col-md-8">
                        <form class="form-horizontal packageTypeForm" method="post" id="create">
                            <div class="form-group removeFromModal">
                                <div class="col-md-7">
                                    <h4 class="subTitle">Package Type Details</h4>
                                </div>
                                <div class="col-md-5 operations" style="display: none">
                                    <button type="button" class="btn btn-success" onclick="new_form(formTemplate, 'update','create')">
                                        New
                                    </button>
                                    <button type="button" class="btn btn-danger"
                                            <sec:authorize
                                                    access="!hasRole('ROLE_master@packageTypeManagement_DELETE')">
                                                disabled="disabled"
                                            </sec:authorize>
                                            data-toggle=confirmation
                                            data-btn-ok-label="Continue"
                                            data-btn-ok-icon="glyphicon glyphicon-share-alt"
                                            data-btn-ok-class="btn-success"
                                            data-btn-cancel-label="Cancel"
                                            data-btn-cancel-icon="glyphicon glyphicon-ban-circle"
                                            data-btn-cancel-class="btn-danger"
                                            data-title="Deleting Package Type"
                                            data-content="Are you sure you want to delete this Package Type ? "
                                            data-id="modal"
                                            data-on-confirm="delete_package_type">
                                        Delete
                                    </button>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Package Type Name</label>
                                <div class="col-md-6 col-xs-12">
                                    <input type="text" class="form-control text-uppercase" id="packageTypeName"
                                           name="packageTypeName" data-error="Please Provide a Valid Package Type Name"
                                           rv-value="packageType.packageTypeName" maxlength="50"
                                           pattern="^[a-zA-Z0-9\s]+$" required/>
                                    <span class="help-block">Required, Package Type Name</span>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Package Type Code</label>
                                <div class="col-md-6 col-xs-12">
                                    <input type="text" class="form-control text-uppercase" id="packageTypeCode"
                                           name="packageTypeCode" data-error="Please Provide a Valid Package Type Code"
                                           rv-value="packageType.packageTypeCode" maxlength="15"
                                           pattern="^[a-zA-Z0-9\s]+$" required/>
                                    <span class="help-block">Required, Package Type Code</span>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Asycuda Code</label>
                                <div class="col-md-6 col-xs-12">
                                    <input type="text" class="form-control text-uppercase" id="asycudaCode"
                                           data-error="Please Provide a Valid Asycuda Code" maxlength="2"
                                           rv-value="packageType.asycudaCode" pattern="^[a-zA-Z0-9\s]+$"
                                           name="asycudaCode" required/>
                                    <span class="help-block">Required, Asycuda code</span>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Description</label>
                                <div class="col-md-6 col-xs-12">
                                    <input type="text" class="form-control text-uppercase" id="description"
                                           rv-value="packageType.description" maxlength="150"
                                           name="description"/>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Status</label>
                                <div class="col-md-6 col-xs-12">
                                    <select name="status" id="status"
                                            class="form-control select" data-validate="true" required
                                            rv-value="packageType.status"
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
                                    <button type="button" class="btn btn-primary pull-right" value="createPackageType"
                                            onclick="create_new_packageType()"
                                            <sec:authorize
                                                    access="!hasRole('ROLE_master@packageTypeManagement_CREATE')">
                                                disabled="disabled"
                                            </sec:authorize>>Create
                                    </button>
                                </div>
                            </div>
                            <div class="form-group updateOperation" style="display: none">
                                <div class="form-group">
                                    <label class="col-md-3 text-right">Created By</label>
                                    <label class="col-md-8" rv-text="packageType.createdBy"></label>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 text-right">Created Date</label>
                                    <label class="col-md-8" rv-text="packageType.createdDate"></label>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 text-right">Last Modified By</label>
                                    <label class="col-md-8" rv-text="packageType.lastModifiedBy"></label>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 text-right">Last Modified Date</label>
                                    <label class="col-md-8" rv-text="packageType.lastModifiedDate"></label>
                                </div>
                                <div class="col-sm-offset-2 col-sm-7 removeFromModal">
                                    <button type="button" class="btn btn-primary pull-right"
                                            value="Update Package Type"
                                            <sec:authorize
                                                    access="!hasRole('ROLE_master@packageTypeManagement_UPDATE')">
                                                disabled="disabled"
                                            </sec:authorize>
                                            onclick="update_package_type()">Update
                                    </button>
                                </div>
                            </div>
                            <input type="hidden" name="packageTypeSeq" id="packageTypeSeq" value="" rv-value="packageType.packageTypeSeq"/>
                        </form>
                    </div>
                </div>
                <div class="tab-pane" id="tab-second">
                    <div class="row">
                        <div class="col-md-6">
                            <form class="form-horizontal" method="post" name="search" id="search">
                                <div class="form-group">
                                    <h4 class="subTitle">Search Package Type</h4>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Package Type Name</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" class="form-control"
                                               rv-value="packageType.packageTypeName"
                                               id="search_packageTypeName"
                                               name="packageTypeName"/>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Package Type Code</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" class="form-control"
                                               rv-value="packageType.packageTypeCode"
                                               id="search_packageTypeCode"
                                               name="packageTypeCode"/>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Description</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" class="form-control" id="search_description"
                                               rv-value="packageType.description" maxlength="150"
                                               name="description"/>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <div class="col-sm-offset-2 col-sm-7">
                                        <button type="button" class="btn btn-primary pull-right"
                                                value="searchPackageType"
                                                onclick="search_package_type()"
                                                <sec:authorize
                                                        access="!hasRole('ROLE_master@packageTypeManagement_VIEW')">
                                                    disabled="disabled"
                                                </sec:authorize>>Search
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="form-group" id="loadPackageTypeSearchResult"></div>
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
                <h4 class="modal-title">Package Type Details</h4>
            </div>
            <form class="form-horizontal" method="post" name="modal" id="modal">
                <div class="modal-body panel">

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-danger"
                            <sec:authorize
                                    access="!hasRole('ROLE_master@packageTypeManagement_DELETE')">
                                disabled="disabled"
                            </sec:authorize>
                            data-toggle=confirmation
                            data-btn-ok-label="Continue"
                            data-btn-ok-icon="glyphicon glyphicon-share-alt"
                            data-btn-ok-class="btn-success"
                            data-btn-cancel-label="Cancel"
                            data-btn-cancel-icon="glyphicon glyphicon-ban-circle"
                            data-btn-cancel-class="btn-danger"
                            data-title="Deleting Package Type"
                            data-content="Are you sure you want to delete this Package Type ? "
                            data-id="modal"
                            data-on-confirm="delete_package_type_popup">
                        Delete
                    </button>
                    <button type="button" class="btn btn-primary"
                            <sec:authorize access="!hasRole('ROLE_master@packageTypeManagement_UPDATE')">
                                disabled="disabled"
                            </sec:authorize>
                            onclick="update_package_type_modal()">Save Changes
                    </button>
                </div>
            </form>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>






