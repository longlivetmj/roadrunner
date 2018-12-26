<%@ include file="/WEB-INF/jsp/include.jsp" %>
<script type="text/javascript" src="../../../customjs/config/pagesAndGroups.js"></script>
<div class="page-title">
    <h3><span class="fa fa-group"></span>Pages And Groups</h3>
</div>
<!-- END PAGE TITLE -->
<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default tabs">
            <ul class="nav nav-tabs" role="tablist">
                <li class="active"><a href="#tab-first" role="tab" data-toggle="tab">Document Link</a></li>
                <li><a href="#tab-second" role="tab" data-toggle="tab">Module</a></li>
                <li><a href="#tab-third" role="tab" data-toggle="tab">Sub Module</a></li>
                <li><a href="#tab-fourth" role="tab" data-toggle="tab">Action Group</a></li>
                <li><a href="#tab-fifth" role="tab" data-toggle="tab">Group</a></li>
            </ul>
            <div class="panel-body tab-content">

                <div class="tab-pane active" id="tab-first">
                    <div class="col-md-6">
                        <form class="form-horizontal" method="post" name="documentLinkAdd" id="documentLinkAdd">
                            <div class="form-group">
                                <h4 class="subTitle">Create Document Link</h4>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Module</label>
                                <div class="col-md-6 col-xs-12">
                                    <select data-error="Please Select Module" data-validate="true" required
                                            aria-required="true"
                                            name="moduleSeq" id="createDocumentLinkModuleSeq"
                                            class="form-control select"
                                            onchange="load_submodule_by_module_seq()"
                                            data-live-search="true" placeholder="Module">
                                        <option value="">Choose</option>
                                        <c:forEach items="${moduleList}" var="module">
                                            <option value="${module.moduleSeq}">${module.moduleName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Sub Module</label>
                                <div class="col-md-6 col-xs-12">
                                    <select data-error="Please Select Sub Module" data-validate="true" required
                                            aria-required="true"
                                            name="subModuleSeq" id="createDocumentLinkSubModuleSeq"
                                            class="form-control select" data-live-search="true"
                                            onchange="load_action_group_by_sub_module_seq()"
                                            placeholder="Sub Module">
                                    </select>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Action Group</label>
                                <div class="col-md-6 col-xs-12">
                                    <select data-error="Please Select Action Group" data-validate="true" required
                                            aria-require="true"
                                            name="actionGroupSeq" id="createDocumentLinkActionGroupSeq"
                                            class="form-control select" data-live-search="true"
                                            placeholder="Action Group">
                                    </select>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Link Name</label>
                                <div class="col-md-6 col-xs-12">
                                    <input type="text" class="form-control" name="linkName"
                                           id="createDocumentLinkName" data-error="Please Provide Link Name" required/>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Page Name</label>
                                <div class="col-md-6 col-xs-12">
                                    <input type="text" class="form-control" name="pageName"
                                           id="createDocumentLinkPageName"  data-error="Please Provide Page Name" required/>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Icon</label>
                                <div class="col-md-6 col-xs-12">
                                    <input type="text" class="form-control" name="icon"
                                           id="createDocumentLinkIcon" data-error="Please Provide Icon Name" required/>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Permissions</label>
                                <div class="col-md-6 col-xs-12">
                                    <div class="form-group" style="font-size: xx-small">
                                        <div class="col-md-3">
                                            <label class="check"><input type="checkbox" class="icheckbox" name="create"
                                                                        id="createPageCreate" value="1"/> CREATE</label>
                                        </div>
                                        <div class="col-md-3">
                                            <label class="check"><input type="checkbox" class="icheckbox" name="read"
                                                                        id="createPageRead" value="1"/> VIEW</label>
                                        </div>
                                        <div class="col-md-3">
                                            <label class="check"><input type="checkbox" class="icheckbox" name="update"
                                                                        id="createPageUpdate"
                                                                        value="1"/> UPDATE</label>
                                        </div>
                                        <div class="col-md-3">
                                            <label class="check"><input type="checkbox" class="icheckbox" name="remove"
                                                                        id="createPageRemove"
                                                                        value="1"/> DELETE</label>
                                        </div>
                                        <div class="col-md-3">
                                            <label class="check"><input type="checkbox" class="icheckbox" name="approve"
                                                                        id="createPageApprove"
                                                                        value="1"/> APPROVE</label>
                                        </div>
                                        <div class="col-md-3">
                                            <label class="check"><input type="checkbox" class="icheckbox" name="viewDelete"
                                                                        id="createPageViewDelete"
                                                                        value="1"/> VIEW-DELETE</label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-7">
                                    <button type="button" class="btn btn-primary pull-right" value="addDocumentLink"
                                            <sec:authorize
                                            access="!hasRole('ROLE_config@pagesAndGroups_CREATE')">
                                        disabled="disabled"
                                    </sec:authorize>
                                            onclick="create_document_Link()">Create
                                    </button>
                                </div>
                            </div>

                        </form>
                    </div>

                    <div class="col-md-6">
                        <form class="form-horizontal" method="post" name="customRoleAdd" id="customRoleAdd">
                            <div class="form-group">
                                <h4 class="subTitle">Create Custom Role</h4>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Document Link</label>
                                <div class="col-md-6 col-xs-12">
                                    <select data-error="Please Select Document Link" data-validate="true" required aria-required="true"
                                            name="documentLinkSeq" id="createCustomRoleDocumentLinkSeq"
                                            class="form-control select"
                                            data-live-search="true" placeholder="Document Link">
                                        <option value="">Choose</option>
                                        <c:forEach items="${documentLinkList}" var="documentLink">
                                            <option value="${documentLink.documentLinkSeq}">${documentLink.linkName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Role</label>
                                <div class="col-md-6 col-xs-12">
                                    <input type="text" class="form-control" name="authority"
                                           id="createAuthority" data-error="Please Provide Role" required/>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-7">
                                    <button type="button" class="btn btn-primary pull-right" value="addCustomRole"
                                            <sec:authorize
                                            access="!hasRole('ROLE_config@pagesAndGroups_CREATE')">
                                        disabled="disabled"
                                    </sec:authorize>
                                            onclick="create_custom_role()">Create
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>

                </div>

                <div class="tab-pane" id="tab-second">
                    <div class="col-md-6">
                        <form class="form-horizontal" method="post" name="moduleAdd" id="moduleAdd">
                            <div class="form-group">
                                <h4 class="subTitle">Create Module</h4>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Module</label>
                                <div class="col-md-6 col-xs-12">
                                    <input type="text" class="form-control" name="moduleName"
                                           id="createModuleName" data-error="Please Provide Module Name" required/>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Url Pattern</label>
                                <div class="col-md-6 col-xs-12">
                                    <input type="text" class="form-control" name="urlPattern"
                                           id="createUrlPattern" data-error="Please Provide Url Pattern" required/>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Description</label>
                                <div class="col-md-6 col-xs-12">
                                    <input type="text" class="form-control" name="description"
                                           id="createModuleDescription"/>

                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-7">
                                    <button type="button" class="btn btn-primary pull-right" value="addModule"
                                            <sec:authorize
                                            access="!hasRole('ROLE_config@pagesAndGroups_CREATE')">
                                        disabled="disabled"
                                    </sec:authorize>
                                            onclick="create_module()">Create
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="col-md-6">
                        <form class="form-horizontal" method="post" name="updateModule" id="updateModule">
                            <div class="form-group">
                                <h4 class="subTitle">Update Module</h4>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Module</label>
                                <div class="col-md-6 col-xs-12">
                                    <select data-error="Please Select Module" data-validate="true" required aria-required="true"
                                            name="moduleSeq" id="updateModule_moduleSeq" class="form-control select"
                                            data-live-search="true"
                                            placeholder="Module" onchange="load_module_by_module_seq_to_update()">
                                        <option value="">Choose</option>
                                        <c:forEach items="${moduleList}" var="module">
                                            <option value="${module.moduleSeq}">${module.moduleName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Module Name</label>
                                <div class="col-md-6 col-xs-12">
                                    <input type="text" class="form-control" id="updateModule_moduleName" name="moduleName"
                                           data-error="Please Provide Module Name" required/>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Url Pattern</label>
                                <div class="col-md-6 col-xs-12">
                                    <input type="text" class="form-control" name="urlPattern"
                                           id="updateModule_urlPattern" data-error="Please Provide Url Pattern" required/>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Description</label>
                                <div class="col-md-6 col-xs-12">
                                    <input type="text" class="form-control" name="description"
                                           id="updateModule_description"/>

                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Status</label>
                                <div class="col-md-6 col-xs-12">
                                    <label class="check"><input type="RADIO" class="radio-button" value="1"
                                                                id="updateModuleActiveStatus"
                                                                name="status"/>Active</label>
                                    <label class="check"><input type="RADIO" class="radio-button" value="0"
                                                                id="updateModuleInActiveStatus" name="status"/>In-Active</label>
                                </div>

                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-7">
                                    <button type="button" class="btn btn-primary pull-right" value="updateModule"
                                            <sec:authorize
                                                    access="!hasRole('ROLE_config@pagesAndGroups_UPDATE')">
                                                disabled="disabled"
                                            </sec:authorize>
                                            onclick="update_module()">update
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="tab-pane" id="tab-third">
                    <div class="col-md-6">
                        <form class="form-horizontal" method="post" name="subModuleAdd" id="subModuleAdd"
                              enctype="multipart/form-data">
                            <div class="form-group">
                                <h4 class="subTitle">Create Sub Module</h4>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Module</label>
                                <div class="col-md-6 col-xs-12">
                                    <select data-error="Please Select Module" data-validate="true" required aria-required="true"
                                            name="moduleSeq" id="moduleSeq" class="form-control select"
                                            data-live-search="true" placeholder="Module">
                                        <option value="">Choose</option>
                                        <c:forEach items="${moduleList}" var="module">
                                            <option value="${module.moduleSeq}">${module.moduleName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Sub Module</label>
                                <div class="col-md-6 col-xs-12">
                                    <input type="text" class="form-control"
                                           name="subModuleName" id="createSubModuleName"
                                           data-error="Please Provide Sub Module Name" required/>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Description</label>
                                <div class="col-md-6 col-xs-12">
                                    <input type="text" class="form-control" name="description"
                                           id="createSubModuleDescription"/>

                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-7">
                                    <button type="button" class="btn btn-primary pull-right" value="addSubModule"   <sec:authorize
                                            access="!hasRole('ROLE_config@pagesAndGroups_CREATE')">
                                        disabled="disabled"
                                    </sec:authorize>
                                            onclick="create_submodule()">Create
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>

                    <div class="col-md-6">
                        <form class="form-horizontal" method="post" name="updateSubModule" id="updateSubModule">
                            <div class="form-group">
                                <h4 class="subTitle">Update Sub Module</h4>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Sub Module</label>
                                <div class="col-md-6 col-xs-12">
                                    <select data-error="Please Select Sub Module" data-validate="true" required aria-required="true"
                                            name="subModuleSeq" id="updateSubModule_subModuleSeq"
                                            class="form-control select" data-live-search="true"
                                            onchange="load_submodule_by_submodule_seq_to_update()"
                                            placeholder="Sub Module">
                                        <option value="">Choose</option>
                                        <c:forEach items="${subModuleList}" var="subModule">
                                            <option value="${subModule.subModuleSeq}">${subModule.subModuleName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Module</label>
                                <div class="col-md-6 col-xs-12">
                                    <select data-error="Please Select Module" data-validate="true" required aria-required="true"
                                            name="moduleSeq" id="updateSubModule_moduleSeq"
                                            class="form-control select"
                                            data-live-search="true" placeholder="Module">
                                        <option value="">Choose</option>
                                        <c:forEach items="${moduleList}" var="module">
                                            <option value="${module.moduleSeq}">${module.moduleName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Sub Module Name</label>
                                <div class="col-md-6 col-xs-12">
                                    <input type="text" class="form-control" id="updateSubModule_subModuleName"
                                           name="subModuleName"
                                           data-error="Please Provide Sub Module Name" required/>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Description</label>
                                <div class="col-md-6 col-xs-12">
                                    <input type="text" class="form-control" name="description"
                                           id="updateSubModule_description"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Status</label>
                                <div class="col-md-6 col-xs-12">
                                    <label class="check"><input type="RADIO" class="radio-button" value="1"
                                                                id="updateSubModuleActiveStatus"
                                                                name="status"/>Active</label>
                                    <label class="check"><input type="RADIO" class="radio-button" value="0"
                                                                id="updateSubModuleInActiveStatus" name="status"/>In-Active</label>
                                </div>

                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-7">
                                    <button type="button" class="btn btn-primary pull-right" value="updateSubModule"
                                            <sec:authorize
                                                    access="!hasRole('ROLE_config@pagesAndGroups_UPDATE')">
                                                disabled="disabled"
                                            </sec:authorize>
                                            onclick="update_sub_module()">update
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="tab-pane" id="tab-fourth">
                    <div class="col-md-6">
                        <form class="form-horizontal" method="post" name="actionGroupAdd" id="actionGroupAdd"
                              enctype="multipart/form-data">
                            <div class="form-group">
                                <h4 class="subTitle">Create Action Group</h4>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Module</label>
                                <div class="col-md-6 col-xs-12">
                                    <select data-error="Please Select Module" data-validate="true" required
                                            aria-required="true"
                                            name="moduleSeq" id="actionGroupModuleSeq"
                                            class="form-control select"
                                            onchange="load_submodule_by_module_seq_for_action_group()"
                                            data-live-search="true" placeholder="Module">
                                        <option value="">Choose</option>
                                        <c:forEach items="${moduleList}" var="module">
                                            <option value="${module.moduleSeq}">${module.moduleName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Sub Module</label>
                                <div class="col-md-6 col-xs-12">
                                    <select data-error="Please Select Sub Module" data-validate="true" required aria-required="true"
                                            name="subModuleSeq" id="actionGroupSubModuleSeq" class="form-control select"
                                            data-live-search="true" placeholder="Sub Module">
                                    </select>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Action Group</label>
                                <div class="col-md-6 col-xs-12">
                                    <input type="text" class="form-control"
                                           name="actionGroupName" id="createActionGroupName"
                                           data-error="Please Provide Action Group Name" required/>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Description</label>
                                <div class="col-md-6 col-xs-12">
                                    <input type="text" class="form-control" name="description"
                                           id="createActionGroupDescription"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-7">
                                    <button type="button" class="btn btn-primary pull-right" value="addActionGroup"
                                            <sec:authorize
                                            access="!hasRole('ROLE_config@pagesAndGroups_CREATE')">
                                        disabled="disabled"
                                    </sec:authorize>
                                            onclick="create_actiongroup()">Create
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>

                    <div class="col-md-6">
                        <form class="form-horizontal" method="post" name="updateActionGroup" id="updateActionGroup">
                            <div class="form-group">
                                <h4 class="subTitle">Update Action Group</h4>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Action Group</label>
                                <div class="col-md-6 col-xs-12">
                                    <select data-error="Please Select Action Group" data-validate="true" required aria-required="true"
                                            name="actionGroupSeq" id="updateActionGroup_actionGroupSeq"
                                            class="form-control select" data-live-search="true"
                                            placeholder="Action Group"
                                            onchange="load_action_group_by_action_group_seq_to_update()">
                                        <option value="">Choose</option>
                                        <c:forEach items="${actionGroupList}" var="actionGroup">
                                            <option value="${actionGroup.actionGroupSeq}">${actionGroup.actionGroupName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Sub Module</label>
                                <div class="col-md-6 col-xs-12">
                                    <select data-error="Please Select Sub Module" data-validate="true" required aria-required="true"
                                            name="subModuleSeq" id="updateActionGroup_subModuleSeq"
                                            class="form-control select"
                                            data-live-search="true" placeholder="Sub Module">
                                        <option value="">Choose</option>
                                        <c:forEach items="${subModuleList}" var="subModule">
                                            <option value="${subModule.subModuleSeq}">${subModule.subModuleName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Action Group Name</label>
                                <div class="col-md-6 col-xs-12">
                                    <input type="text" class="form-control"
                                           name="actionGroupName" id="updateActionGroup_actionGroupName"
                                           data-error="Please Provide Action Group Name" required/>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Description</label>
                                <div class="col-md-6 col-xs-12">
                                    <input type="text" class="form-control" name="description"
                                           id="updateActionGroup_description"/>

                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Status</label>
                                <div class="col-md-6 col-xs-12">
                                    <label class="check"><input type="RADIO" class="radio-button" value="1"
                                                                id="updateActionGroupActiveStatus" name="status"/>Active</label>
                                    <label class="check"><input type="RADIO" class="radio-button" value="0"
                                                                id="updateActionGroupInActiveStatus" name="status"/>In-Active</label>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-7">
                                    <button type="button" class="btn btn-primary pull-right" value="updateActionGroup"
                                            <sec:authorize
                                                    access="!hasRole('ROLE_config@pagesAndGroups_UPDATE')">
                                                disabled="disabled"
                                            </sec:authorize>
                                            onclick="update_action_group()">update
                                    </button>
                                </div>

                            </div>
                        </form>
                    </div>
                </div>
                <div class="tab-pane" id="tab-fifth">
                    <div class="col-md-6">
                        <form class="form-horizontal" method="post" name="groupAdd" id="groupAdd"
                              enctype="multipart/form-data">
                            <div class="form-group">
                                <h4 class="subTitle">Create Group</h4>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Module</label>
                                <div class="col-md-6 col-xs-12">
                                    <select data-error="Please Select Module" data-validate="true" required aria-required="true"
                                            name="moduleSeq" id="groupModuleSeq" class="form-control select"
                                            data-live-search="true" placeholder="Module">
                                        <option value="">Choose</option>
                                        <c:forEach items="${moduleList}" var="module">
                                            <option value="${module.moduleSeq}">${module.moduleName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Group</label>
                                <div class="col-md-6 col-xs-12">
                                    <input type="text" class="form-control" name="groupName"
                                           id="createGroupName" data-error="Please Provide Group Name" required/>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Description</label>
                                <div class="col-md-6 col-xs-12">
                                    <input type="text" class="form-control" name="description"
                                           id="createGroupDescription"/>

                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-7">
                                    <button type="button" class="btn btn-primary pull-right" value="addGroup"
                                            <sec:authorize
                                            access="!hasRole('ROLE_config@pagesAndGroups_CREATE')">
                                        disabled="disabled"
                                    </sec:authorize>
                                            onclick="create_group()">Create
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="col-md-6">
                        <form class="form-horizontal" method="post" name="updateGroup" id="updateGroup">
                            <div class="form-group">
                                <h4 class="subTitle">Update Group</h4>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Group</label>
                                <div class="col-md-6 col-xs-12">
                                    <select data-error="Please Select Group" data-validate="true" required aria-required="true"
                                            name="groupSeq" id="updateGroup_groupSeq"
                                            class="form-control select" data-live-search="true"
                                            placeholder="Group" onchange="load_group_by_group_seq_to_update()">
                                        <option value="">Choose</option>
                                        <c:forEach items="${groupList}" var="group">
                                            <option value="${group.groupSeq}">${group.groupName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Module</label>
                                <div class="col-md-6 col-xs-12">
                                    <select data-error="Please Select Module" data-validate="true" required aria-required="true"
                                            name="moduleSeq" id="updateGroup_moduleSeq" class="form-control select"
                                            data-live-search="true" placeholder="Module">
                                        <option value="">Choose</option>
                                        <c:forEach items="${moduleList}" var="module">
                                            <option value="${module.moduleSeq}">${module.moduleName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Group Name</label>
                                <div class="col-md-6 col-xs-12">
                                    <input type="text" class="form-control"
                                           name="groupName" id="updateGroup_groupName"
                                           data-error="Please Provide Group Name" required/>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Description</label>
                                <div class="col-md-6 col-xs-12">
                                    <input type="text" class="form-control" name="description"
                                           id="updateGroup_description"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Status</label>
                                <div class="col-md-6 col-xs-12">
                                    <label class="check"><input type="RADIO" class="radio-button" value="1"
                                                                id="updateGroupActiveStatus"
                                                                name="status"/>Active</label>
                                    <label class="check"><input type="RADIO" class="radio-button" value="0"
                                                                id="updateGroupInActiveStatus" name="status"/>In-Active</label>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-7">
                                    <button type="button" class="btn btn-primary pull-right" value="updateGroup"
                                            <sec:authorize
                                                    access="!hasRole('ROLE_config@pagesAndGroups_UPDATE')">
                                                disabled="disabled"
                                            </sec:authorize>
                                            onclick="update_group()">update
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
</div>



