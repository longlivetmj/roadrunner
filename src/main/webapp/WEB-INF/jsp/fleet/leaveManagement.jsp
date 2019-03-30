<%@ include file="/WEB-INF/jsp/include.jsp" %>
<script type="text/javascript" src="../../../customjs/fleet/leaveManagement.js"></script>
<div class="page-title">
    <h3><span class="fa fa-user"></span>Leave Management</h3>
</div>

<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default tabs">
            <ul class="nav nav-tabs" role="tablist">
                <li class="active"><a href="#tab-first" role="tab" data-toggle="tab">Leave Management</a></li>
                <li><a href="#tab-second" role="tab" data-toggle="tab">Leave Search</a></li>
            </ul>
            <div class="panel-body tab-content">
                <div class="tab-pane active" id="tab-first">
                    <div class="col-md-12">
                        <form class="form-horizontal leaveForm" method="post" name="create" id="create">
                            <div class="form-group removeFromModal">
                                <div class="col-md-7">
                                    <h4 class="subTitle">Enter Employee Leaves</h4>
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
                                                        access="!hasRole('ROLE_fleet@leaveManagement_DELETE')">
                                                    disabled="disabled"
                                                </sec:authorize>
                                                data-toggle=confirmation
                                                data-btn-ok-label="Continue"
                                                data-btn-ok-icon="glyphicon glyphicon-share-alt"
                                                data-btn-ok-class="btn-success"
                                                data-btn-cancel-label="Cancel"
                                                data-btn-cancel-icon="glyphicon glyphicon-ban-circle"
                                                data-btn-cancel-class="btn-danger"
                                                data-title="Deleting Leave"
                                                data-content="Are you sure you want to  Delete this Leave ? "
                                                data-id="update"
                                                data-on-confirm="delete_leave">
                                            Delete
                                        </button>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 control-label">Designation</label>
                                    <div class="col-md-8 col-xs-12">
                                        <select class="form-control select" data-live-search="true"
                                                name="employeeDesignationSeq" id="employeeDesignationSeq"
                                                data-error="Please Select Designation" required
                                                data-validate="true" required aria-required="true"
                                                rv-value="leave.employeeDesignationSeq"
                                                onchange="get_employee_list()"
                                                placeholder="Designation">
                                            <option value="">Select</option>
                                            <c:forEach items="${designationList}" var="employeeDesignation">
                                                <option value="${employeeDesignation.employeeDesignationSeq}">${employeeDesignation.designation}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Employee</label>
                                    <div class="col-md-8 col-xs-12">
                                        <select class="form-control select" data-live-search="true"
                                                name="employeeSeq" id="employeeSeq"
                                                data-error="Please Select Employee"
                                                rv-value="leave.employeeSeq"
                                                data-validate="true" required aria-required="true"
                                                placeholder="Employee">
                                            <option rv-value="leave.employeeSeq"
                                                    rv-text="leave.employee.employeeName"
                                                    rv-setselectedattr="leave.employeeSeq"></option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 control-label">Leave Type</label>
                                    <div class="col-md-8 col-xs-12">
                                        <select class="form-control select" data-live-search="true"
                                                name="leaveTypeSeq" id="leaveTypeSeq"
                                                data-error="Please Select Leave Type" required
                                                data-validate="true" required aria-required="true"
                                                rv-value="leave.leaveTypeSeq"
                                                placeholder="Leave Type">
                                            <option value="">Select</option>
                                            <c:forEach items="${leaveTypeList}" var="leaveType">
                                                <option value="${leaveType.leaveTypeSeq}">${leaveType.leaveType}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Status</label>
                                    <div class="col-md-8 col-xs-12">
                                        <select name="status" id="status"
                                                class="form-control select"
                                                rv-value="leave.status"
                                                data-live-search="true" placeholder="Status">
                                            <c:forEach items="${statusList}" var="status">
                                                <option value="${status.statusSeq}" ${status.statusSeq eq 2 ? 'selected' : ''}>${status.status}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-sm-offset-2 col-sm-9 createOperation removeFromModal" >
                                        <button type="button" class="btn btn-primary pull-right"
                                                <sec:authorize
                                                        access="!hasRole('ROLE_fleet@leaveManagement_CREATE')">
                                                    disabled="disabled"
                                                </sec:authorize>
                                                onclick="create_leave()">Submit
                                        </button>
                                    </div>
                                    <div class="col-sm-offset-2 col-sm-9 removeFromModal updateOperation" style="display: none">
                                        <button type="button" class="btn btn-primary pull-right"
                                                <sec:authorize
                                                        access="!hasRole('ROLE_fleet@leaveManagement_UPDATE')">
                                                    disabled="disabled"
                                                </sec:authorize>
                                                onclick="update_leave()">Update
                                        </button>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-5">
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Year/Month</label>
                                    <div class="col-md-4 col-xs-12">
                                        <input type="text" class="form-control" name="leaveYear" maxlength="50"
                                               rv-value="leave.leaveYear"
                                               id="leaveYear" value="${currentYear}"/>
                                    </div>
                                    <div class="col-md-4 col-xs-12">
                                        <select name="leaveMonth"
                                                id="leaveMonth"
                                                class="form-control select"
                                                data-live-search="true"
                                                title="Month"
                                                rv-value="leave.leaveMonth"
                                                required>
                                            <c:forEach items="${monthList}" var="month">
                                                <option value="${month.value}" ${month.value eq currentMonth ? 'selected' : ''}>${month.name()}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Start Date</label>
                                    <div class="col-md-8 col-xs-12">
                                        <input type="text" class="form-control datepicker" name="startDate"
                                               maxlength="50"
                                               rv-value="leave.startDate"
                                               value="<fmt:formatDate value='${startDate}' pattern='yyyy-MM-dd'/>"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">End Date</label>
                                    <div class="col-md-8 col-xs-12">
                                        <input type="text" class="form-control datepicker" name="endDate" maxlength="50"
                                               rv-value="leave.endDate"
                                               value="<fmt:formatDate value='${startDate}' pattern='yyyy-MM-dd'/>"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Reason</label>
                                    <div class="col-md-8 col-xs-12">
                                        <input type="text" class="form-control" name="reason"
                                               maxlength="100"
                                               rv-value="leave.reason"
                                               id="reason"/>
                                    </div>
                                </div>
                                <div class="form-group updateOperation" style="display: none">
                                    <div class="form-group">
                                        <label class="col-md-3 text-right">Created By</label>
                                        <label class="col-md-8" rv-text="leave.createdBy"></label>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-3 text-right">Created Date</label>
                                        <label class="col-md-8" rv-text="leave.createdDate"></label>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-3 text-right">Last Modified By</label>
                                        <label class="col-md-8" rv-text="leave.lastModifiedBy"></label>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-3 text-right">Last Modified Date</label>
                                        <label class="col-md-8" rv-text="leave.lastModifiedDate"></label>
                                    </div>
                                </div>
                                <input type="hidden" name="leaveSeq" id="leaveSeq" rv-value="leave.leaveSeq"/>
                                <input type="hidden" name="companyProfileSeq" value="${companyProfileSeq}"
                                       rv-value="leave.companyProfileSeq"/>
                            </div>
                        </form>
                    </div>
                </div>

                <div class="tab-pane" id="tab-second">
                    <div class="row">
                        <form class="form-horizontal" method="post" name="search" id="search">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Employee Name</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" maxlength="30" class="form-control" name="employeeName"
                                               id="search_employeeName"/>
                                    </div>

                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Status</label>
                                    <div class="col-md-6 col-xs-12">
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
                                    <div class="col-md-6 col-xs-12">
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
                                    <div class="col-sm-offset-2 col-sm-7">
                                        <button type="button" class="btn btn-primary pull-right"
                                                value="loadEmployeeData"
                                                <sec:authorize
                                                        access="!hasRole('ROLE_fleet@leaveManagement_VIEW')">
                                                    disabled="disabled"
                                                </sec:authorize>
                                                onclick="search_leave_data()">Search
                                        </button>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Start Date</label>
                                    <div class="col-md-8 col-xs-12">
                                        <input type="text" class="form-control datepicker" name="startDate"
                                               maxlength="50"
                                               value="<fmt:formatDate value='${startDate}' pattern='yyyy-MM-dd'/>"
                                               id="search_startDate"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">End Date</label>
                                    <div class="col-md-8 col-xs-12">
                                        <input type="text" class="form-control datepicker" name="endDate" maxlength="50"
                                               value="<fmt:formatDate value='${endDate}' pattern='yyyy-MM-dd'/>"
                                               id="search_endDate"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 control-label">Leave Type</label>
                                    <div class="col-md-8 col-xs-12">
                                        <select class="form-control select" data-live-search="true"
                                                name="leaveTypeSeq" id="search_leaveTypeSeq">
                                            <option value="">ALL</option>
                                            <c:forEach items="${leaveTypeList}" var="leaveType">
                                                <option value="${leaveType.leaveTypeSeq}">${leaveType.leaveType}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                            </div>
                        </form>
                        <div class="form-group" id="loadLeaveData"></div>
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
                <h4 class="modal-title">Leave Details</h4>
            </div>
            <form class="form-horizontal" method="post" name="modal" id="modal">
                <div class="modal-body panel">

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-danger"
                            <sec:authorize
                                    access="!hasRole('ROLE_fleet@leaveManagement_DELETE')">
                                disabled="disabled"
                            </sec:authorize>
                            data-toggle=confirmation
                            data-btn-ok-label="Continue" data-btn-ok-icon="glyphicon glyphicon-share-alt"
                            data-btn-ok-class="btn-success"
                            data-btn-cancel-label="Cancel" data-btn-cancel-icon="glyphicon glyphicon-ban-circle"
                            data-btn-cancel-class="btn-danger"
                            data-title="Deleting Leave"
                            data-content="Are you sure you want to Delete this Leave ? "
                            data-id="modal"
                            data-on-confirm="delete_leave">
                        Delete
                    </button>
                    <button type="button" class="btn btn-primary"
                            <sec:authorize
                                    access="!hasRole('ROLE_fleet@leaveManagement_UPDATE')">
                                disabled="disabled"
                            </sec:authorize>
                            onclick="update_leave_data()">Save Changes
                    </button>
                </div>
            </form>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
