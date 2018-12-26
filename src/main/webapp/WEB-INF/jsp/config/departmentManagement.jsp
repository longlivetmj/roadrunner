<%@ include file="/WEB-INF/jsp/include.jsp" %>
<script type="text/javascript" src="../../../customjs/config/departmentManagement.js"></script>
<div class="page-title">
    <h3><span class="fa fa-road"></span>Department Management</h3>
</div>

<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default tabs">
            <ul class="nav nav-tabs" role="tablist">
                <li><a href="#tab-first" role="tab" data-toggle="tab">Department Creation</a></li>
                <li><a href="#tab-second" role="tab" data-toggle="tab">Department Search</a></li>
                <li class="active"><a href="#tab-third" role="tab" data-toggle="tab"> Department Charges</a></li>
            </ul>
            <div class="panel-body tab-content">
                <div class="tab-pane" id="tab-first">
                    <div class="col-md-6">
                        <form class="form-horizontal departmentForm" method="post" id="create">
                            <div class="form-group removeFromModal">
                                <div class="col-md-7">
                                    <h4 class="subTitle">Department Details</h4>
                                </div>
                                <div class="col-md-5 operations" style="display: none">
                                    <button type="button" class="btn btn-success"
                                            onclick="new_form(formTemplate, 'update','create')">
                                        New
                                    </button>
                                    <button type="button" class="btn btn-danger"
                                            <sec:authorize
                                                    access="!hasRole('ROLE_config@departmentManagement_DELETE')">
                                                disabled="disabled"
                                            </sec:authorize>
                                            data-toggle=confirmation
                                            data-btn-ok-label="Continue"
                                            data-btn-ok-icon="glyphicon glyphicon-share-alt"
                                            data-btn-ok-class="btn-success"
                                            data-btn-cancel-label="Cancel"
                                            data-btn-cancel-icon="glyphicon glyphicon-ban-circle"
                                            data-btn-cancel-class="btn-danger"
                                            data-title="Deleting Department"
                                            data-content="Are you sure you want to Delete this Department ? "
                                            data-id="update"
                                            data-on-confirm="delete_department">
                                        Delete
                                    </button>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Department Code</label>
                                <div class="col-md-6 col-xs-12">
                                    <input type="text" maxlength="15"
                                           class="form-control"
                                           name="departmentCode"
                                           id="departmentCode" maxlength="15"
                                           pattern="^[a-zA-Z0-9\s]+$"
                                           rv-value="department.departmentCode"
                                           data-error="Please Provide a Valid Department Code"
                                           required/>
                                    <span class="help-block">Required, Department Code</span>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Department Name</label>
                                <div class="col-md-6 col-xs-12">
                                    <input type="text" maxlength="50"
                                           class="form-control"
                                           name="departmentName"
                                           id="departmentName"
                                           maxlength="50"
                                           pattern="^[a-zA-Z0-9\s]+$"
                                           rv-value="department.departmentName"
                                           data-error="Please Provide a Valid Department Name"
                                           required/>
                                    <span class="help-block">Required, Department Name</span>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Prefix</label>
                                <div class="col-md-6 col-xs-12">
                                    <input type="text" maxlength="50" class="form-control" name="prefix"
                                           id="prefix"
                                           rv-value="department.prefix"
                                           pattern="^[a-zA-Z0-9\s]+$"
                                           data-error="Please Provide a Valid Prefix"
                                           required/>
                                    <span class="help-block">Required, Prefix</span>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Module</label>
                                <div class="col-md-6 col-xs-12">
                                    <select name="moduleSeq" id="moduleSeq"
                                            rv-value="department.moduleSeq"
                                            class="form-control select"
                                            data-error="Please Select Module" data-validate="true"
                                            data-live-search="true" placeholder="Module" required>
                                        <option value="">Select</option>
                                        <c:forEach items="${moduleList}" var="module">
                                            <option value="${module.moduleSeq}">${module.moduleName}</option>
                                        </c:forEach>
                                    </select>
                                    <span class="help-block">Required</span>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Email (CC)</label>
                                <div class="col-md-6 col-xs-12">
                                    <input type="email" name="emailCc" class="form-control"
                                           rv-value="department.emailCc"
                                           maxlength="50"
                                           data-error="Please Provide a Valid E-mail Address"
                                           pattern="[a-zA-Z0-9_\.\+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-\.]+"
                                           id="emailCc" required>
                                    <span class="help-block">Required, Email (CC)</span>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Status</label>
                                <div class="col-md-6 col-xs-12">
                                    <select name="status" id="status"
                                            rv-value="department.status"
                                            class="form-control select" data-validate="true" required
                                            data-live-search="true" placeholder="Status" >
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
                                            value="create Department"
                                            <sec:authorize
                                                    access="!hasRole('ROLE_config@departmentManagement_CREATE')">
                                                disabled="disabled"
                                            </sec:authorize>
                                            onclick="create_department()">Create
                                    </button>
                                </div>
                            </div>
                            <div class="form-group updateOperation" style="display: none">
                                <div class="form-group">
                                    <label class="col-md-3 text-right">Created By</label>
                                    <label class="col-md-8" rv-text="department.createdBy"></label>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 text-right">Created Date</label>
                                    <label class="col-md-8" rv-text="department.createdDate"></label>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 text-right">Last Modified By</label>
                                    <label class="col-md-8" rv-text="department.lastModifiedBy"></label>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 text-right">Last Modified Date</label>
                                    <label class="col-md-8" rv-text="department.lastModifiedDate"></label>
                                </div>
                                <div class="col-sm-offset-2 col-sm-7 removeFromModal">
                                    <button type="button" class="btn btn-primary pull-right"
                                            value="Update Department"
                                            <sec:authorize
                                                    access="!hasRole('ROLE_config@departmentManagement_UPDATE')">
                                                disabled="disabled"
                                            </sec:authorize>
                                            onclick="update_department()">Update
                                    </button>
                                </div>
                            </div>
                            <input type="hidden" name="departmentSeq" id="departmentSeq" value=""
                                   rv-value="department.departmentSeq"/>
                        </form>
                    </div>
                </div>

                <div class="tab-pane" id="tab-second">
                    <div class="row">
                        <div class="col-md-6">
                            <form class="form-horizontal" method="post" name="search" id="search">
                                <div class="form-group">
                                    <h4 class="subTitle">Search Department</h4>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Department Code</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" class="form-control" id="search_departmentCode"
                                               name="departmentCode"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Department Name</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" class="form-control" id="search_departmentName"
                                               name="departmentNameEq"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Prifix</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" class="form-control" id="search_prifix"
                                               name="prifix"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-sm-offset-2 col-sm-7">
                                        <button type="button" class="btn btn-primary pull-right"
                                                value="search Department"
                                                <sec:authorize
                                                        access="!hasRole('ROLE_config@departmentManagement_VIEW')">
                                                    disabled="disabled"
                                                </sec:authorize>
                                                onclick="search_department()">Search
                                        </button>
                                    </div>
                                </div>
                            </form>

                        </div>
                        <div class="form-group" id="loadDepartmentData"></div>

                    </div>
                </div>

                <div class="tab-pane active" id="tab-third">
                    <div class="row">
                        <form class="form-horizontal departmentChargesForm" method="post" name="department_charges"
                              id="department_charges">
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Module</label>
                                <div class="col-md-6 col-xs-12">
                                    <select name="moduleSeq" id="charge_moduleSeq"
                                            rv-value="departmentCharge.moduleSeq"
                                            class="form-control select"
                                            data-live-search="true"
                                            data-validate="true" required
                                            data-error="Please Select Department"
                                            onchange="load_departments_for_module()"
                                            aria-required="true">
                                        <option value="">Select</option>
                                        <c:forEach items="${moduleList}" var="module">
                                            <option value="${module.moduleSeq}">${module.moduleName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Department Name</label>
                                <div class="col-md-6 col-xs-12">
                                    <select name="departmentSeq" id="charge_departmentSeq"
                                            class="form-control select"
                                            data-validate="true"
                                            data-error="Please Select Department Charge"
                                            aria-required="true"
                                            data-live-search="true"
                                            placeholder="Department Name"
                                            onchange="load_department_charges_data()">
                                        <option value="">Select</option>
                                    </select>
                                </div>
                            </div>

                            <div id="loadDepartmentChargesData"></div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="myModal">
    <div class="modal-dialog  cs-modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Department Details</h4>
            </div>
            <form class="form-horizontal" method="post" name="modal" id="modal">
                <div class="form-group">
                    <div class="modal-body panel">

                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-danger"
                            <sec:authorize
                                    access="!hasRole('ROLE_config@departmentManagement_DELETE')">
                                disabled="disabled"
                            </sec:authorize>
                            data-toggle=confirmation
                            data-btn-ok-label="Continue" data-btn-ok-icon="glyphicon glyphicon-share-alt"
                            data-btn-ok-class="btn-success"
                            data-btn-cancel-label="Cancel" data-btn-cancel-icon="glyphicon glyphicon-ban-circle"
                            data-btn-cancel-class="btn-danger"
                            data-title="Deleting Department"
                            data-content="Are you sure you want to Delete this Department? "
                            data-id="modal"
                            data-on-confirm="delete_department">
                        Delete
                    </button>
                    <button type="button" class="btn btn-primary"
                            <sec:authorize access="!hasRole('ROLE_config@departmentManagement_UPDATE')">
                                disabled="disabled"
                            </sec:authorize>
                            onclick="update_department_modal()">Save Changes
                    </button>

                </div>
            </form>
        </div><!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>