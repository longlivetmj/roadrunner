<%@ include file="/WEB-INF/jsp/include.jsp" %>
<script type="text/javascript" src="../../../customjs/config/permissionManagement.js"></script>

<div class="page-title">
    <h2><span class="fa fa-arrow-circle-o-left"></span>Permission Management</h2>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default tabs">
            <ul class="nav nav-tabs" role="tablist">
                <li class="active"><a href="#assign-group-to-user" role="tab" data-toggle="tab">Assign Group to
                    User</a></li>
                <li><a href="#assign-permission-to-group" role="tab" data-toggle="tab">Assign Permission to
                    Group</a></li>
            </ul>
            <div class="panel-body tab-content col-md-6">
                <div class="tab-pane active" id="assign-group-to-user">
                    <div class="col-md-6 push-down-30">
                        <form class="form-horizontal" method="get" name="groupAssignForm" id="groupAssignForm">
                            <div class="form-group">
                                <h4 class="subTitle">Assign Group to User</h4>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">User</label>

                                <div class="col-md-6 col-xs-12">
                                    <select class="form-control select" name="userSeq" id="groupAssignUserSeq"
                                            data-live-search="true" placeholder="User"
                                            onchange="load_company_list_by_user()">
                                        <option value="0">None</option>
                                        <c:forEach items="${userList}" var="user">
                                            <option value="${user.userSeq}">${user.username}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Company</label>
                                <div class="col-md-6 col-xs-12">
                                    <select name="companyProfileSeq" id="groupAssignCompanyProfileSeq"
                                            class="form-control select" data-validate="true" required
                                            data-live-search="true" placeholder="Company Profile"
                                            onchange="load_assigned_module_list_by_company()">
                                    </select>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Module</label>

                                <div class="col-md-6 col-xs-12">
                                    <select class="form-control select" name="moduleSeq" id="groupAssignModuleSeq"
                                            data-live-search="true" placeholder="Module"
                                            onchange="loadModuleWiseGroupList()">
                                        <c:forEach items="${moduleList}" var="module">
                                            <option value="${module.moduleSeq}">${module.moduleName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <!-- loadModuleWiseGroupList -->
                            <div class="form-group" id="loadGroupList">
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-7">
                                    <button type="button" class="btn btn-primary pull-right"
                                            value="groupAssignForm"
                                            onclick="saveAssignedGroups()">Save
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="tab-pane" id="assign-permission-to-group">
                    <form class="form-horizontal" name="documentLinkAssignForm" id="documentLinkAssignForm">
                        <div class="form-group">
                            <h4 class="subTitle">Assign Permission to Group</h4>
                        </div>
                        <div class="form-group">
                            <label class="col-md-2 col-xs-12 control-label">Module</label>

                            <div class="col-md-5 col-xs-12">
                                <select class="form-control select" name="moduleSeq" id="documentLinkAssignModuleSeq"
                                        data-live-search="true" placeholder="Module"
                                        onchange="load_module_wise_groups_for_document_link_assign()">
                                    <c:forEach items="${moduleList}" var="module">
                                        <option value="${module.moduleSeq}">${module.moduleName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-2 col-xs-12 control-label">Group</label>

                            <div class="col-md-5 col-xs-12">
                                <select class="form-control select" name="groupSeq" id="documentLinkAssignGroupSeq"
                                        data-live-search="true" placeholder="Group"
                                        onchange="loadModuleWiseDocumentLinkList();">

                                </select>
                            </div>
                        </div>

                        <div class="form-group" id="loadDocumentLinkList">
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10">
                                <button type="button" class="btn btn-primary pull-right"
                                        value="groupAssignForm"
                                        onclick="saveAssignedAuthoritiesToGroup()">Save
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
