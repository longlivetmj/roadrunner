<%@ include file="/WEB-INF/jsp/include.jsp" %>
<script type="text/javascript" src="../../../customjs/master/employeeManagement.js"></script>
<div class="page-title">
    <h3><span class="fa fa-road"></span>Employee Management</h3>
</div>

<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default tabs">
            <ul class="nav nav-tabs" role="tablist">
                <li class="active"><a href="#tab-first" role="tab" data-toggle="tab">Employee Creation</a></li>
                <li><a href="#tab-second" role="tab" data-toggle="tab">Employee Search</a></li>
            </ul>
            <div class="panel-body tab-content">
                <div class="tab-pane active" id="tab-first">
                    <div class="col-md-12">
                        <form class="form-horizontal employeeForm" method="post" name="create" id="create">
                            <div class="form-group removeFromModal">
                                <div class="col-md-7">
                                    <h4 class="subTitle">Employee Details</h4>
                                </div>

                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <div class="col-md-5 operations" style="display: none">
                                        <button type="button" class="btn btn-success"
                                                onclick="new_form(formTemplate, 'update','create')">
                                            New
                                        </button>
                                        <button type="button" class="btn btn-danger"
                                                <sec:authorize
                                                        access="!hasRole('ROLE_master@employeeManagement_DELETE')">
                                                    disabled="disabled"
                                                </sec:authorize>
                                                data-toggle=confirmation
                                                data-btn-ok-label="Continue"
                                                data-btn-ok-icon="glyphicon glyphicon-share-alt"
                                                data-btn-ok-class="btn-success"
                                                data-btn-cancel-label="Cancel"
                                                data-btn-cancel-icon="glyphicon glyphicon-ban-circle"
                                                data-btn-cancel-class="btn-danger"
                                                data-title="Deleting Employee"
                                                data-content="Are you sure you want to  Delete this Employee ? "
                                                data-id="update"
                                                data-on-confirm="delete_employee">
                                            Delete
                                        </button>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Employee Name</label>
                                    <div class="col-md-8 col-xs-12">
                                        <input type="text" maxlength="100" class="form-control text-uppercase"
                                               name="employeeName"
                                               rv-value="employee.employeeName"
                                               id="employeeName" pattern="^[a-zA-Z0-9\s]+$"
                                               data-error="Please Provide a Valid Employee Name"
                                               required/>
                                        <span class="help-block">Required, Employee Name</span>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 control-label">Designation</label>
                                    <div class="col-md-8 col-xs-12">
                                        <select class="form-control select" data-live-search="true"
                                                name="employeeDesignationSeq" id="employeeDesignationSeq"
                                                rv-value="employee.employeeDesignationSeq"
                                                data-error="Please Select Designation"
                                                data-validate="true" required aria-required="true"
                                                placeholder="Designation">
                                            <option value="">Select</option>
                                            <c:forEach items="${designationList}" var="employeeDesignation">
                                                <option value="${employeeDesignation.employeeDesignationSeq}">${employeeDesignation.designation}</option>
                                            </c:forEach>
                                        </select>
                                        <span class="help-block">Required</span>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Stakeholder</label>
                                    <div class="col-md-8 col-xs-12">
                                        <select data-live-search="true"
                                                name="stakeholderSeq" id="stakeholderSeq"
                                                data-validate="true"
                                                aria-required="true"
                                                class="form-control ajax-select"
                                                data-abs-ajax-type="get"
                                                data-abs-request-delay="500"
                                                data-key="stakeholderSeq"
                                                data-value="stakeholderName"
                                                data-subText="stakeholderCode"
                                                title="Select and begin typing"
                                                rv-setselectedattr="employee.stakeholderSeq"
                                                required
                                                data-abs-ajax-url="master/employeeManagement/findTransporter">
                                            <option rv-value="employee.stakeholderSeq"
                                                    rv-text="employee.stakeholder.stakeholderName"
                                                    rv-setselectedattr="employee.stakeholderSeq"></option>
                                        </select>
                                        <span class="help-block">Required, Stakeholder</span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">NIC</label>
                                    <div class="col-md-8 col-xs-12">
                                        <input type="text" class="form-control" name="nicNo" maxlength="50"
                                               rv-value="employee.nicNo"
                                               id="nicNo"/>
                                    </div>

                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">License No</label>
                                    <div class="col-md-8 col-xs-12">
                                        <input type="text" class="form-control" name="licenseNo"
                                               maxlength="50"
                                               pattern="^[a-zA-Z0-9\s]+$"
                                               rv-value="employee.licenseNo"
                                               id="licenseNo"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">License Expiry Date</label>
                                    <div class="col-md-8 col-xs-12">
                                        <input type="text" class="form-control datepicker" name="licenseExpiryDate"
                                               maxlength="50"
                                               rv-value="employee.licenseExpiryDate"
                                               id="licenseExpiryDate"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Status</label>
                                    <div class="col-md-8 col-xs-12">
                                        <select name="status" id="status"
                                                class="form-control select" data-validate="true" required
                                                rv-value="employee.status"
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
                                        <button type="button" class="btn btn-primary pull-right" value="addDestination"
                                                <sec:authorize
                                                        access="!hasRole('ROLE_master@employeeManagement_CREATE')">
                                                    disabled="disabled"
                                                </sec:authorize>
                                                onclick="create_employee()">Create
                                        </button>
                                    </div>
                                </div>
                                <div class="form-group updateOperation" style="display: none">
                                    <div class="form-group">
                                        <label class="col-md-3 text-right">Created By</label>
                                        <label class="col-md-8" rv-text="employee.createdBy"></label>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-3 text-right">Created Date</label>
                                        <label class="col-md-8" rv-text="employee.createdDate"></label>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-3 text-right">Last Modified By</label>
                                        <label class="col-md-8" rv-text="employee.lastModifiedBy"></label>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-3 text-right">Last Modified Date</label>
                                        <label class="col-md-8" rv-text="employee.lastModifiedDate"></label>
                                    </div>
                                    <div class="col-sm-offset-2 col-sm-7 removeFromModal">
                                        <button type="button" class="btn btn-primary pull-right" value="Update Employee"
                                                <sec:authorize
                                                        access="!hasRole('ROLE_master@employeeManagement_UPDATE')">
                                                    disabled="disabled"
                                                </sec:authorize>
                                                onclick="update_employee()">Update
                                        </button>
                                    </div>
                                </div>
                                <input type="hidden" name="employeeSeq" id="employeeSeq" value=""
                                       rv-value="employee.employeeSeq"/>
                                <input type="hidden" name="companyProfileSeq" value="${companyProfileSeq}"
                                       rv-value="employee.companyProfileSeq"/>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <h6 class="subTitle">Contact Details</h6>
                                </div>

                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Address 1</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" class="form-control" name="addressBook.address1"
                                               rv-value="employee.addressBook.address1" maxlength="100"
                                               id="address1"/>
                                    </div>

                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Address 2</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" class="form-control" name="addressBook.address2"
                                               rv-value="employee.addressBook.address2" maxlength="100"
                                               id="address2"/>
                                    </div>

                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">City</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" class="form-control" name="addressBook.city"
                                               rv-value="employee.addressBook.city" maxlength="50"
                                               id="city"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">State</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" class="form-control" name="addressBook.state"
                                               rv-value="employee.addressBook.state" maxlength="50"
                                               id="state"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Zip</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" class="form-control" name="addressBook.zip" maxlength="10"
                                               rv-value="employee.addressBook.zip"
                                               id="zip"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Country</label>
                                    <div class="col-md-6 col-xs-12">
                                        <select name="addressBook.countrySeq"
                                                class="form-control ajax-select"
                                                data-validate="true" required
                                                data-abs-ajax-type="get"
                                                data-error="Please Select Country"
                                                data-abs-request-delay="500"
                                                title="Select and begin typing"
                                                data-key="countrySeq"
                                                data-value="countryName"
                                                data-subText="countryCode"
                                                rv-setselectedattr="employee.addressBook.countrySeq"
                                                data-abs-ajax-url="master/employeeManagement/findCountry"
                                                data-live-search="true" placeholder="Country">
                                            <option rv-value="employee.addressBook.countrySeq"
                                                    rv-text="employee.addressBook.country.countryName"
                                                    rv-setselectedattr="employee.addressBook.countrySeq"></option>
                                        </select>
                                        <span class="help-block">Required</span>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Telephone</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="tel" class="form-control" name="addressBook.telephone"
                                               rv-value="employee.addressBook.telephone"
                                               pattern="[0-9]{10}|[0-9]{12}|[0-9]{15}"
                                               data-error="Please Provide a Valid Telephone Number" maxlength="15"
                                               id="telephone"/>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Telephone Extension</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" class="form-control" name="addressBook.telephoneExtension"
                                               rv-value="employee.addressBook.telephoneExtension"
                                               data-error="Please Provide a Numeric Ext Code" maxlength="5"
                                               pattern="^([0-9]*\s+)*[0-9]*$"
                                               id="telephoneExtension"/>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Mobile</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="tel" class="form-control" name="addressBook.mobile"
                                               rv-value="employee.addressBook.mobile"
                                               pattern="[0-9]{10}|[0-9]{12}|[0-9]{15}"
                                               data-error="Please Provide a Valid Mobile Number" maxlength="15"
                                               id="create_mobile"/>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Fax</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="tel" class="form-control" name="addressBook.fax"
                                               rv-value="employee.addressBook.fax"
                                               pattern="[0-9]{10}|[0-9]{12}|[0-9]{15}"
                                               data-error="Please Provide a Valid Fax Number" maxlength="15"
                                               id="create_fax"/>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Email</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="email" name="addressBook.email" class="form-control"
                                               rv-value="employee.addressBook.email"
                                               data-error="Please Provide a Valid E-mail Address"
                                               placeholder="s@f.c" maxlength="50"
                                               pattern="^[^\s@]+@[^\s@]+\.[^\s@]+$"
                                               id="create_email"/>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Web Site</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="url" name="addressBook.website" class="form-control"
                                               placeholder="http://" maxlength="100"
                                               pattern="(http:\/\/|https:\/\/).+([a-z]+)"
                                               rv-value="employee.addressBook.website"
                                               data-error="Please Provide a Valid Website Url"
                                               id="create_website"/>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                            </div>
                            <input type="hidden" name="addressBook.addressBookSeq"
                                   rv-value="employee.addressBook.addressBookSeq"/>
                            <input type="hidden" name="addressBookSeq" rv-value="employee.addressBookSeq"/>
                        </form>

                    </div>
                </div>

                <div class="tab-pane" id="tab-second">
                    <div class="row">
                        <form class="form-horizontal" method="post" name="search" id="search">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <h4 class="subTitle">Search Employee</h4>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Stakeholder</label>
                                    <div class="col-md-8 col-xs-12">
                                        <select data-live-search="true"
                                                name="stakeholderSeq" id="searchStakeholderSeq"
                                                data-validate="true"
                                                aria-required="true"
                                                class="form-control ajax-select"
                                                data-abs-ajax-type="get"
                                                data-abs-request-delay="500"
                                                data-key="stakeholderSeq"
                                                data-value="stakeholderName"
                                                data-subText="stakeholderCode"
                                                title="Select and begin typing"
                                                data-abs-ajax-url="master/employeeManagement/findTransporter">
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Employee Name</label>
                                    <div class="col-md-8 col-xs-12">
                                        <input type="text" maxlength="30" class="form-control" name="employeeName"
                                               id="search_employeeName"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Status</label>
                                    <div class="col-md-8 col-xs-12">
                                        <select name="status" id="search_status"
                                                class="form-control select"
                                                data-live-search="true" placeholder="Status">
                                            <option value="">All</option>
                                            <c:forEach items="${statusList}" var="status">
                                                <option value="${status.statusSeq}">${status.status}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 control-label">Designation</label>
                                    <div class="col-md-8 col-xs-12">
                                        <select class="form-control select" data-live-search="true"
                                                name="employeeDesignationSeq" id="search_employeeDesignationSeq"
                                                placeholder="Designation">
                                            <option value="">All</option>
                                            <c:forEach items="${designationList}" var="employeeDesignation">
                                                <option value="${employeeDesignation.employeeDesignationSeq}">${employeeDesignation.designation}</option>
                                            </c:forEach>
                                        </select>

                                    </div>

                                </div>
                                <div class="form-group">
                                    <div class="col-sm-offset-2 col-sm-9">
                                        <button type="button" class="btn btn-primary pull-right"
                                                value="loadEmployeeData"
                                                <sec:authorize
                                                        access="!hasRole('ROLE_master@employeeManagement_VIEW')">
                                                    disabled="disabled"
                                                </sec:authorize>
                                                onclick="load_employee_table_data()">Search
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="row">
                        <div class="form-group" id="loadEmployeeListData"></div>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="myModal">
    <div class="modal-dialog cs-modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Employee Details</h4>
            </div>
            <form class="form-horizontal" method="post" name="modal" id="modal">
                <div class="modal-body panel">

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-danger"
                            <sec:authorize
                                    access="!hasRole('ROLE_master@employeeManagement_DELETE')">
                                disabled="disabled"
                            </sec:authorize>
                            data-toggle=confirmation
                            data-btn-ok-label="Continue" data-btn-ok-icon="glyphicon glyphicon-share-alt"
                            data-btn-ok-class="btn-success"
                            data-btn-cancel-label="Cancel" data-btn-cancel-icon="glyphicon glyphicon-ban-circle"
                            data-btn-cancel-class="btn-danger"
                            data-title="Deleting Employee"
                            data-content="Are you sure you want to Delete this Employee ? "
                            data-id="modal"
                            data-on-confirm="delete_employee">
                        Delete
                    </button>
                    <button type="button" class="btn btn-primary"
                            <sec:authorize
                                    access="!hasRole('ROLE_master@employeeManagement_UPDATE')">
                                disabled="disabled"
                            </sec:authorize>
                            onclick="update_employee_modal()">Save Changes
                    </button>
                </div>
            </form>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
