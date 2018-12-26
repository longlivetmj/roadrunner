<%@ include file="/WEB-INF/jsp/include.jsp" %>
<script type="text/javascript" src="../../../customjs/config/companyProfileManagement.js"></script>
<div class="page-title">
    <h2><span class="fa fa-arrow-circle-o-left"></span>Company Profile configuration</h2>
</div>
<!-- END PAGE TITLE -->

<div class="row">
    <div class="col-md-12">

        <div class="panel panel-default tabs">
            <ul class="nav nav-tabs" role="tablist" data-toggle="tablist">
                <li class="active"><a href="#tab-first" role="tab" data-toggle="tab">Company Profile Creation</a></li>
                <li><a href="#tab-second" role="tab" data-toggle="tab">Company Profile Search</a></li>
                <li><a href="#tab-third" role="tab" data-toggle="tab">Manage Company Modules</a></li>
            </ul>
            <div class="panel-body tab-content">
                <div class="tab-pane active" id="tab-first">
                    <div class="col-md-12">
                        <form class="form-horizontal companyProfileManagementForm" method="post" id="create"
                              enctype="multipart/form-data" data-toggle="form">
                            <div class="form-group removeFromModal">
                                <div class="col-md-10 push-down-20">
                                    <h4 class="subTitle">Create Company Profile</h4>
                                </div>
                                <div class="col-md-2 operations" style="display: none">
                                    <button type="button" class="btn btn-success"
                                            onclick="new_form(formTemplate,'update','create')">
                                        New
                                    </button>
                                    <button type="button" class="btn btn-danger"
                                            <sec:authorize
                                                    access="!hasRole('ROLE_config@companyProfileManagement_DELETE')">
                                                disabled="disabled"
                                            </sec:authorize>
                                            data-toggle=confirmation
                                            data-btn-ok-label="Continue"
                                            data-btn-ok-icon="glyphicon glyphicon-share-alt"
                                            data-btn-ok-class="btn-success"
                                            data-btn-cancel-label="Cancel"
                                            data-btn-cancel-icon="glyphicon glyphicon-ban-circle"
                                            data-btn-cancel-class="btn-danger"
                                            data-title="Deleting Company Profile"
                                            data-content="Are you sure you want to delete this company profile ? "
                                            data-id="update"
                                            data-on-confirm="delete_company_profile">
                                        Delete
                                    </button>
                                </div>
                            </div>
                            <div class="form-group hidden">
                                <input class="hidden" id="companyProfileSeq" name="companyProfileSeq" value=""
                                       rv-value="companyProfile.companyProfileSeq"/>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Company Name</label>
                                <div class="col-md-6 col-xs-12">
                                    <input type="text" class="form-control" id="companyName" name="companyName" required
                                           maxlength="255" minlength="10"
                                           data-error="Please Provide a Valid Company Profile Name"
                                           rv-value="companyProfile.companyName"/>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Short Code</label>
                                <div class="col-md-6 col-xs-12">
                                    <input type="text" class="form-control" id="shortCode" name="shortCode"
                                           required
                                           maxlength="3" minlength="3"
                                           rv-value="companyProfile.shortCode"
                                           data-error="Please Provide a Valid Company Code"/>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Description</label>
                                <div class="col-md-6 col-xs-12">
                                    <input type="text" class="form-control" id="description" name="description"
                                           maxlength="512" rv-value="companyProfile.description"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Email</label>
                                <div class="col-md-6 col-xs-12">
                                    <input type="email" class="form-control" id="email" name="addressBook.email"
                                           required
                                           data-error="Please Provide a Valid Email Address"
                                           rv-value="companyProfile.addressBook.email"/>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Telephone No</label>
                                <div class="col-md-6 col-xs-12">
                                    <input type="tel" class="form-control" id="telephoneNumber"
                                           name="addressBook.telephone"
                                           pattern="[0-9]{10}|[0-9]{12}|[0-9]{15}"
                                           data-error="Invalid telephone number format"
                                           required
                                           rv-value="companyProfile.addressBook.telephone"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Status</label>
                                <div class="col-md-6 col-xs-12">
                                    <select name="status" id="status"
                                            class="form-control select"
                                            data-live-search="true"
                                            data-validate="true" required
                                            data-error="Please Select Status"
                                            rv-value="companyProfile.status">
                                        <option value="">Select</option>
                                        <c:forEach items="${statusList}" var="status">
                                            <option value="${status.statusSeq}">${status.status}</option>
                                        </c:forEach>
                                    </select>
                                    <div class="help-block with-errors"></div>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-md-12">
                                    <label class="col-md-3 col-xs-12 control-label">Company Logo</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="file" lass="form-control" id="companyLogo"
                                               name="companyLogo" class="singleFile"/>
                                        <input type="hidden" name="uploadDocumentSeq"
                                               rv-value="companyProfile.uploadDocumentSeq">
                                    </div>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <div class="col-md-12">
                                    <label class="col-md-3 col-xs-12 control-label">Main Company Logo</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="file" lass="form-control" id="mainCompanyLogo"
                                               name="mainCompanyLogo" class="singleFile"/>
                                        <input type="hidden" name="uploadDocumentSeq"
                                               rv-value="companyProfile.uploadDocumentSeq">
                                    </div>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group createOperation removeFromModal">
                                <div class="col-sm-offset-2 col-sm-7">
                                    <button type="button" class="btn btn-primary pull-right" value="create"
                                            <sec:authorize
                                                    access="!hasRole('ROLE_config@companyProfileManagement_CREATE')">
                                                disabled="disabled"
                                            </sec:authorize>
                                            onclick="create_company_profile()">Create
                                    </button>
                                </div>
                            </div>
                            <div class="form-group updateOperation" style="display: none">
                                <div class="form-group">
                                    <label class="col-md-3 text-right">Created By</label>
                                    <label class="col-md-8" rv-text="companyProfile.createdBy"></label>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 text-right">Created Date</label>
                                    <label class="col-md-8" rv-text="companyProfile.createdDate"></label>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 text-right">Last Modified By</label>
                                    <label class="col-md-8" rv-text="companyProfile.lastModifiedBy"></label>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 text-right">Last Modified Date</label>
                                    <label class="col-md-8" rv-text="companyProfile.lastModifiedDate"></label>
                                </div>
                                <div class="col-sm-offset-2 col-sm-7 removeFromModal">
                                    <button type="button" class="btn btn-primary pull-right" value="Update Tax"
                                            <sec:authorize
                                                    access="!hasRole('ROLE_config@freightLineManagement_UPDATE')">
                                                disabled="disabled"
                                            </sec:authorize>
                                            onclick="update_company_profile()">Update
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="tab-pane" id="tab-second" data-toggle="tab">
                    <div class="row">
                        <div class="col-md-6">
                            <form class="form-horizontal" method="post" name="search" id="search">
                                <div class="form-group">
                                    <h4 class="subTitle">Search Company Profiles</h4>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Company Name</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" class="form-control" id="search_companyName"
                                               name="companyName" maxlength="50"
                                               rv-value="companyProfile.companyName"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-sm-offset-2 col-sm-7">
                                        <button type="button" class="btn btn-primary pull-right" value="Search"
                                                onclick="search_company_profiles()">Search
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="form-group" id="loadCompanyProfileData"></div>
                    </div>
                </div>
                <div class="tab-pane" id="tab-third">
                    <div class="col-md-6">
                        <form class="form-horizontal" method="post" name="assignModulesToCompanyProfile"
                              id="assignModulesToCompanyProfile"
                              enctype="multipart/form-data">
                            <div class="form-group">
                                <h4>Assign Modules</h4>
                            </div>
                            <div class="form-group">
                                <div>
                                    <label class="control-label col-md-3 col-xs-12">Company</label>
                                    <div class="col-md-9 col-xs-12">
                                        <select name="companyToAssign" id="companyToAssign" class="form-control select"
                                                data-live-search="true">
                                            <%--onchange="load_modules_for_company()">--%>
                                            <option value="0">None</option>
                                            <c:forEach items="${companyProfiles}" var="companyProfile">
                                                <option value="${companyProfile.companyProfileSeq}">${companyProfile.companyName}
                                                    - ${companyProfile.shortCode}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <div>
                                    <label class="control-label col-md-3">Module</label>
                                    <div class="col-md-9">
                                        <select class="col-md-6 form-control select" name="companyModules"
                                                id="companyModules"
                                                data-live-search="true" multiple>
                                            <option value="0">None</option>
                                            <c:forEach items="${modules}" var="module">
                                                <option value="${module.moduleSeq}">${module.moduleName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-10">
                                    <button type="button" class="btn btn-primary pull-right" value="assignModules"
                                            onclick="assign_modules_to_company()">Assign
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="col-md-6">
                        <form class="form-horizontal" method="post" name="removeModulesFromCompany"
                              id="removeModulesFromCompany"
                              enctype="multipart/form-data">
                            <div class="form-group">
                                <h4>Remove Modules</h4>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3">Company</label>
                                <div class="col-md-9 col-xs-12">
                                    <select name="companyToRemove" id="companyToRemove" class="form-control select"
                                            data-live-search="true" onchange="load_assigned_module_list_by_company()">
                                        <option value="0">None</option>
                                        <c:forEach items="${companyProfiles}" var="companyProfile">
                                            <option value="${companyProfile.companyProfileSeq}">${companyProfile.companyName}
                                                - ${companyProfile.shortCode}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <div>
                                    <label class="control-label col-md-3">Module</label>
                                    <div class="col-md-9">
                                        <select class="col-md-6 form-control select" name="companyModulesToRemove"
                                                id="companyModulesToRemove"
                                                data-live-search="true" multiple>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-10">
                                    <button type="button" class="btn btn-danger pull-right" value="assignModules"
                                            onclick="remove_modules_from_company()">Remove
                                    </button>
                                </div>
                            </div>
                        </form>
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
                <h4 class="modal-title">Company Profile Details</h4>
            </div>
            <form class="form-horizontal" method="post" name="modal" id="modal">
                <div class="modal-body panel">

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-danger"
                            <sec:authorize
                                    access="!hasRole('ROLE_config@companyProfileManagement_DELETE')">
                                disabled="disabled"
                            </sec:authorize>
                            data-toggle=confirmation
                            data-btn-ok-label="Continue" data-btn-ok-icon="glyphicon glyphicon-share-alt"
                            data-btn-ok-class="btn-success"
                            data-btn-cancel-label="Cancel" data-btn-cancel-icon="glyphicon glyphicon-ban-circle"
                            data-btn-cancel-class="btn-danger"
                            data-title="Deleting Company Profile"
                            data-content="Are you sure you want to delete this company profile? "
                            data-id="modal"
                            data-on-confirm="delete_company_profile">
                        Delete
                    </button>
                    <button type="button" class="btn btn-primary"
                            <sec:authorize access="!hasRole('ROLE_config@companyProfileManagement_UPDATE')">
                                disabled="disabled"
                            </sec:authorize>
                            onclick="update_company_profile_from_modal()">Save changes
                    </button>
                </div>
            </form>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>



