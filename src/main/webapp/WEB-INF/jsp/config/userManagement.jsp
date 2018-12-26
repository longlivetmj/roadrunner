<%@ include file="/WEB-INF/jsp/include.jsp" %>
<script type="text/javascript" src="../../../customjs/config/userManagement.js"></script>
<div class="page-title">
    <h3><span class="fa fa-user"></span>User Creation and Configuration</h3>
</div>
<!-- END PAGE TITLE -->

<div class="row">
    <div class="col-md-12">

        <div class="panel panel-default tabs">
            <ul class="nav nav-tabs" role="tablist">
                <li class="active"><a href="#tab-first" role="tab" data-toggle="tab">User Creation</a></li>
                <li><a href="#tab-second" role="tab" data-toggle="tab">Assign Module's to User</a></li>
                <%--<li><a href="#tab-third" role="tab" data-toggle="tab">User Company Configuration</a></li>--%>
                <%--<li><a href="#tab-fourth" role="tab" data-toggle="tab">Other Settings</a></li>--%>
                <li><a href="#tab-fifth" role="tab" data-toggle="tab">Assign User's to Department</a></li>
            </ul>
            <div class="panel-body tab-content">
                <div class="tab-pane active" id="tab-first">
                    <div class="col-md-6">
                        <form class="form-horizontal" method="post" name="create" id="create"
                              enctype="multipart/form-data">
                            <div class="form-group">
                                <h4 class="subTitle">Create User</h4>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">First Name</label>
                                <div class="col-md-6 col-xs-12">
                                    <input type="text" class="form-control" id="create_firstName" name="firstName"
                                           required/>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Second Name</label>
                                <div class="col-md-6 col-xs-12">
                                    <input type="text" class="form-control" id="create_secondName" name="secondName"
                                           required/>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Date of Birth</label>
                                <div class="col-md-6 col-xs-12">
                                    <input type="text" class="form-control" id="create_dateOfBirth" name="dateOfBirth"
                                           required
                                           data-date="2014-06-06" data-date-format="yyyy-mm-dd"
                                           data-date-viewmode="years"/>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Email</label>
                                <div class="col-md-6 col-xs-12">
                                    <input type="email" class="form-control" id="create_email" name="email" required/>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Contact No</label>
                                <div class="col-md-6 col-xs-12">
                                    <input type="tel" class="form-control" id="create_contactNo" name="contactNo"
                                           pattern="^[0-9]{1,}$"/>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Username</label>
                                <div class="col-md-6 col-xs-12">
                                    <input type="text" class="form-control" id="create_username" name="username"
                                           required
                                           maxlength="25"/>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <div class="col-md-12">
                                    <label class="col-md-3 col-xs-12 control-label">User Photo</label>
                                    <input type="file" lass="form-control" id="create_userPhoto" name="userPhoto"/>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-7">
                                    <button type="button" class="btn btn-primary pull-right" value="createUser"
                                            onclick="create_user()">Create
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="col-md-6">
                        <form class="form-horizontal" method="post" name="update" id="update"
                              enctype="multipart/form-data">
                            <div class="form-group">
                                <h4 class="subTitle">Update User</h4>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Username</label>
                                <div class="col-md-6 col-xs-12">
                                    <select name="userSeq" id="update_userSeq" class="form-control select"
                                            data-live-search="true"
                                            placeholder="Username" onchange="load_user_by_user_seq_to_update()">
                                        <option value="0">None</option>
                                        <c:forEach items="${userList}" var="user">
                                            <option value="${user.userSeq}">${user.username}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">First Name</label>
                                <div class="col-md-6 col-xs-12">
                                    <input type="text" class="form-control" id="update_firstName" name="firstName"
                                           required/>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Second Name</label>
                                <div class="col-md-6 col-xs-12">
                                    <input type="text" class="form-control" id="update_secondName" name="secondName"
                                           required/>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Date of Birth</label>
                                <div class="col-md-6 col-xs-12">
                                    <input type="text" class="form-control" id="update_dateOfBirth" name="dateOfBirth"
                                           required
                                           data-date="2014-06-06" data-date-format="yyyy-mm-dd"
                                           data-date-viewmode="years"/>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Email</label>
                                <div class="col-md-6 col-xs-12">
                                    <input type="email" class="form-control" id="update_email" name="email" required/>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Contact No</label>
                                <div class="col-md-6 col-xs-12">
                                    <input type="tel" class="form-control" id="update_contactNo" name="contactNo"
                                           pattern="^[0-9]{1,}$"/>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>

                            <div class="form-group">
                                <div class="col-md-12">
                                    <label class="col-md-3 col-xs-12 control-label">User Photo</label>
                                    <input type="file" lass="form-control" id="update_userPhoto" name="userPhoto"/>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <div class="col-md-12">
                                    <label class="col-md-3 col-xs-12 control-label">Enabled</label>
                                    <input type='checkbox' value=1 name="enabled" id="update_enabled">
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-7">
                                    <button type="button" class="btn btn-primary pull-right" value="updateUser"
                                            onclick="update_user()">update
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="tab-pane" id="tab-second">
                    <div class="col-md-6">
                        <form class="form-horizontal" method="post" name="assignModule" id="assignModule">
                            <div class="form-group">
                                <h4 class="subTitle">Assign Modules to User</h4>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Username</label>
                                <div class="col-md-6 col-xs-12">
                                    <select name="userSeq" id="assignModule_userSeq" class="form-control select"
                                            data-live-search="true" data-validate="true" required
                                            placeholder="Select User">
                                        <option value="">Select User</option>
                                        <c:forEach items="${userList}" var="user">
                                            <option value="${user.userSeq}">${user.username}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Company</label>
                                <div class="col-md-6 col-xs-12">
                                    <select name="companyProfileSeq" id="assignModule_companyProfileSeq"
                                            class="form-control select" data-validate="true" required
                                            data-live-search="true" placeholder="Company Profile"
                                            onchange="load_module_list_by_company()">
                                        <option value="">None</option>
                                        <c:forEach items="${companyProfileList}" var="companyProfile">
                                            <option value="${companyProfile.companyProfileSeq}">${companyProfile.companyName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Module</label>
                                <div class="col-md-6 col-xs-12">
                                    <select name="moduleSeq[]" id="assignModule_moduleSeq" class="form-control select"
                                            data-live-search="true" multiple data-validate="true" required
                                            placeholder="Company Module">

                                    </select>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-7">
                                    <button type="button" class="btn btn-primary pull-right" value="assignUserModule"
                                            onclick="assign_modules_to_user()">Submit
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="col-md-6">
                        <form class="form-horizontal" method="post" name="removeModule"
                              id="removeModuleFromUser">
                            <div class="form-group">
                                <h4 class="subTitle">Remove Module From User</h4>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Username</label>
                                <div class="col-md-6 col-xs-12">
                                    <select name="userSeq" id="removeModule_userSeq" class="form-control select"
                                            data-live-search="true" data-validate="true" required
                                            placeholder="Username" onchange="load_assigned_company_list_to_user()">
                                        <option value="">None</option>
                                        <c:forEach items="${userList}" var="user">
                                            <option value="${user.userSeq}">${user.username}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Company</label>
                                <div class="col-md-6 col-xs-12">
                                    <select name="companyProfileSeq" id="removeModule_companyProfileSeq"
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
                                    <select name="moduleSeq[]" id="removeModule_moduleSeq" class="form-control select"
                                            data-live-search="true" multiple data-validate="true" required
                                            placeholder="Company Profile">

                                    </select>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-7">
                                    <button type="button" class="btn btn-primary pull-right" value="removeUserModule"
                                            onclick="remove_modules_from_user()">Submit
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <%--<div class="tab-pane" id="tab-third">
                    <p>Donec tristique eu sem et aliquam. Proin sodales elementum urna et euismod. Quisque nisl
                        nisl, venenatis eget dignissim et, adipiscing eu tellus. Sed nulla massa, luctus id orci
                        sed, elementum consequat est. Proin dictum odio quis diam gravida facilisis. Sed pharetra
                        dolor a tempor tristique. Sed semper sed urna ac dignissim. Aenean fermentum leo at posuere
                        mattis. Etiam vitae quam in magna viverra dictum. Curabitur feugiat ligula in dui luctus,
                        sed aliquet neque posuere.</p>

                    <div class="form-group">
                        <label class="col-md-2 col-xs-12 control-label">Payment type</label>
                        <div class="col-md-2">
                            <select class="form-control select">
                                <option>PayPal</option>
                                <option selected="selected">Payoneer</option>
                                <option>Skrill</option>
                                <option>SWIFT</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-md-2"></div>
                        <div class="col-md-10">Fusce enim dui, pulvinar a augue nec, dapibus hendrerit mauris.
                            Praesent efficitur, elit non convallis faucibus.
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-2 col-xs-12 control-label">E-mail</label>
                        <div class="col-md-6 col-xs-12">
                            <input type="text" class="form-control" value="johndoe@domain.com"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-2 col-xs-12 control-label"></label>
                        <div class="col-md-6 col-xs-12">
                            <label class="check"><input type="checkbox" class="icheckbox" checked="checked"/> I
                                agree</label>
                            <span class="help-block">Read <a href="#">terms</a> and confirm that you agree</span>
                        </div>
                    </div>
                </div>--%>
                <%-- <div class="tab-pane" id="tab-fourth">
                     <p>This is non libero bibendum, scelerisque arcu id, placerat nunc. Integer ullamcorper rutrum
                         dui eget porta. Fusce enim dui, pulvinar a augue nec, dapibus hendrerit mauris. Praesent
                         efficitur, elit non convallis faucibus, enim sapien suscipit mi, sit amet fringilla felis
                         arcu id sem. Phasellus semper felis in odio convallis, et venenatis nisl posuere. Morbi non
                         aliquet magna, a consectetur risus. Vivamus quis tellus eros. Nulla sagittis nisi sit amet
                         orci consectetur laoreet.</p>

                     <div class="form-group">
                         <label class="col-md-3 col-xs-12 control-label">E-mail</label>
                         <div class="col-md-6 col-xs-12">
                             <input type="text" class="form-control" value="johndoe@domain.com"/>
                         </div>
                     </div>

                     <div class="form-group">
                         <label class="col-md-3 col-xs-12 control-label">Password</label>
                         <div class="col-md-6 col-xs-12">
                             <input type="password" class="form-control" value="password"/>
                         </div>
                     </div>

                     <div class="form-group">
                         <label class="col-md-3 col-xs-12 control-label">POP3</label>
                         <div class="col-md-6 col-xs-12">
                             <input type="text" class="form-control" value="pop3.domain.com"/>
                         </div>
                     </div>

                     <div class="form-group push-up-30">
                         <label class="col-md-3 col-xs-12 control-label">Copy to</label>
                         <div class="col-md-6 col-xs-12">
                             <input type="text" class="form-control" value="doejohn@domain.com"/>
                         </div>
                     </div>

                 </div>--%>

                <%----%>
                <div class="tab-pane" id="tab-fifth">
                    <div class="col-md-6">
                        <form class="form-horizontal" method="post" name="userDepartment" id="userDepartment">
                            <div class="form-group">
                                <h4 class="subTitle">Assign User Departments</h4>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Username</label>
                                <div class="col-md-6 col-xs-12">
                                    <select name="userSeq" id="assignUserDepartment_userSeq" class="form-control select"
                                            data-live-search="true" data-validate="true"
                                            required
                                            onchange="load_assigned_module_list_to_user('assign')"
                                            placeholder="Select User">
                                        <option value="">Select User</option>
                                        <c:forEach items="${userList}" var="user">
                                            <option value="${user.userSeq}">${user.username}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Module</label>
                                <div class="col-md-6 col-xs-12">
                                    <select name="moduleSeq" id="assignUserDepartment_moduleSeq"
                                            class="form-control select" data-validate="true" required
                                            data-live-search="true" placeholder="Module"
                                            onchange="load_assigned_Department_list_by_module('assign')">
                                        <option value="">None</option>
                                        <c:forEach items="${moduleList}" var="module">
                                            <option value="${module.moduleSeq}">${module.moduleName}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">

                                <label class="col-md-3 col-xs-12 control-label">Department</label>
                                <div class="col-md-6 col-xs-12">
                                    <select name="departmentSeq[]" id="assignUserDepartment_DepartmentSeq"
                                            class="form-control select"
                                            data-live-search="true" multiple data-validate="true" required
                                            placeholder="Department">

                                    </select>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Status</label>
                                <div class="col-md-6 col-xs-12">
                                    <select name="status" id="status" class="form-control select"
                                            data-live-search="true"
                                            placeholder="Status">
                                        <c:forEach items="${statusList}" var="status">
                                            <option value="${status.statusSeq}">${status.status}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-7">
                                    <button type="button" class="btn btn-primary pull-right" value="assignUserModule"
                                            onclick="assign_department_to_user()">Submit
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="col-md-6">
                        <form class="form-horizontal" method="post" name="removeUserDepartment"
                              id="removeUserDepartment">
                            <div class="form-group">
                                <h4 class="subTitle">Remove User Departments</h4>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Username</label>
                                <div class="col-md-6 col-xs-12">
                                    <select name="userSeq" id="removeUserDepartment_userSeq" class="form-control select"
                                            data-live-search="true" data-validate="true" required
                                            onchange="load_assigned_module_list_to_user('remove')"
                                            placeholder="Select User">
                                        <option value="">Select User</option>
                                        <c:forEach items="${userList}" var="user">
                                            <option value="${user.userSeq}">${user.username}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Module</label>
                                <div class="col-md-6 col-xs-12">
                                    <select name="moduleSeq" id="removeUserDepartment_moduleSeq"
                                            class="form-control select" data-validate="true" required
                                            data-live-search="true" placeholder="Module"
                                            onchange="load_assigned_department_list_by_module()">

                                    </select>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Department</label>
                                <div class="col-md-6 col-xs-12">

                                    <select name="departmentSeq[]" id="removeUserDepartment_DepartmentSeq"
                                            class="form-control select"
                                            data-live-search="true" multiple data-validate="true" required
                                            placeholder="Department">

                                    </select>

                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-7">
                                    <button type="button" class="btn btn-primary pull-right" value="removeUserModule"
                                            onclick="remove_deparment_from_user()">Remove
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <%----%>
            </div>
        </div>

    </div>
</div>

