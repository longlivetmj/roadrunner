<%@ include file="/WEB-INF/jsp/include.jsp" %>
<script type="text/javascript" src="../../../customjs/fleet/payrollConfig.js"></script>
<div class="page-title">
    <h3><span class="fa fa-cogs"></span>Payroll Configuration</h3>
</div>

<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default tabs">
            <ul class="nav nav-tabs" role="tablist">
                <li class="active"><a href="#tab-first" role="tab" data-toggle="tab">Payroll Configuration</a></li>
                <li><a href="#tab-second" role="tab" data-toggle="tab">Payroll Search</a></li>
            </ul>
            <div class="panel-body tab-content">
                <div class="tab-pane active" id="tab-first">
                    <div class="col-md-12">
                        <form class="form-horizontal employeeForm" method="post" name="create" id="create">
                            <div class="form-group removeFromModal">
                                <div class="col-md-7">
                                    <h4 class="subTitle">Payroll Details</h4>
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
                                                        access="!hasRole('ROLE_fleet@payrollConfig_DELETE')">
                                                    disabled="disabled"
                                                </sec:authorize>
                                                data-toggle=confirmation
                                                data-btn-ok-label="Continue"
                                                data-btn-ok-icon="glyphicon glyphicon-share-alt"
                                                data-btn-ok-class="btn-success"
                                                data-btn-cancel-label="Cancel"
                                                data-btn-cancel-icon="glyphicon glyphicon-ban-circle"
                                                data-btn-cancel-class="btn-danger"
                                                data-title="Deleting Payroll"
                                                data-content="Are you sure you want to  Delete this Payroll ? "
                                                data-id="update"
                                                data-on-confirm="delete_payroll">
                                            Delete
                                        </button>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 control-label">Designation</label>
                                    <div class="col-md-8 col-xs-12">
                                        <select class="form-control select" data-live-search="true"
                                                name="employeeDesignationSeq" id="employeeDesignationSeq"
                                                rv-value="payroll.employee.employeeDesignationSeq"
                                                data-error="Please Select Designation"
                                                data-validate="true" required aria-required="true"
                                                onchange="get_employee_list()"
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
                                    <label class="col-md-3 col-xs-12 control-label">Employee</label>
                                    <div class="col-md-8 col-xs-12">
                                        <select class="form-control select" data-live-search="true"
                                                name="employeeSeq" id="employeeSeq"
                                                data-error="Please Select Employee"
                                                data-validate="true" required aria-required="true"
                                                placeholder="Employee">
                                            <option rv-value="payroll.employeeSeq"
                                                    rv-text="payroll.employee.employeeName"
                                                    rv-setselectedattr="payroll.employeeSeq"></option>
                                        </select>
                                        <span class="help-block">Required, Employee</span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Basic Salary</label>
                                    <div class="col-md-8 col-xs-12">
                                        <input type="text" class="form-control" name="basicSalary" maxlength="50"
                                               rv-value="payroll.basicSalary"
                                               onchange="validateFloatKeyPress(this,2)"
                                               id="basicSalary"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Status</label>
                                    <div class="col-md-8 col-xs-12">
                                        <select name="status" id="status"
                                                class="form-control select" data-validate="true" required
                                                rv-value="payroll.status"
                                                data-live-search="true" placeholder="Status">
                                            <c:forEach items="${statusList}" var="status">
                                                <option value="${status.statusSeq}">${status.status}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                    <input type="hidden" name="companyProfileSeq" rv-value="payroll.companyProfileSeq"/>
                                    <input type="hidden" name="payrollSeq" rv-value="payroll.payrollSeq"/>
                                </div>
                                <div class="form-group createOperation removeFromModal">
                                    <div class="col-sm-offset-2 col-sm-7">
                                        <button type="button" class="btn btn-primary pull-right" value="addPayroll"
                                                <sec:authorize
                                                        access="!hasRole('ROLE_fleet@payrollConfig_CREATE')">
                                                    disabled="disabled"
                                                </sec:authorize>
                                                onclick="create_payroll_config()">Save
                                        </button>
                                    </div>
                                </div>
                                <span class="form-group updateOperation" style="display: none">
                                    <div class="form-group">
                                        <label class="col-md-3 text-right">Created By</label>
                                        <label class="col-md-8" rv-text="payroll.createdBy"></label>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-3 text-right">Created Date</label>
                                        <label class="col-md-8" rv-text="payroll.createdDate"></label>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-3 text-right">Last Modified By</label>
                                        <label class="col-md-8" rv-text="payroll.lastModifiedBy"></label>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-3 text-right">Last Modified Date</label>
                                        <label class="col-md-8" rv-text="payroll.lastModifiedDate"></label>
                                    </div>
                                    <div class="col-sm-offset-2 col-sm-7 removeFromModal">
                                        <button type="button" class="btn btn-primary pull-right" value="Update Payroll"
                                                <sec:authorize
                                                        access="!hasRole('ROLE_master@employeeManagement_UPDATE')">
                                                    disabled="disabled"
                                                </sec:authorize>
                                                onclick="update_payroll_config()">Update
                                        </button>
                                    </div>
                                </span>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Allowance</label>
                                    <div class="col-md-8 col-xs-12">
                                        <select class="form-control select allowanceSeqList" data-live-search="true"
                                                name="allowanceSeqList" id="allowanceSeqList" multiple
                                                data-error="Please Select Allowances"
                                                data-validate="true" aria-required="true"
                                                placeholder="Allowance">
                                            <c:forEach items="${allowanceList}" var="allowance">
                                                <option value="${allowance.charge.chargeSeq}">${allowance.charge.chargeName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Commission</label>
                                    <div class="col-md-8 col-xs-12">
                                        <select class="form-control select commissionSeqList" data-live-search="true"
                                                name="commissionSeqList" id="commissionSeqList" multiple
                                                data-error="Please Select Commission"
                                                data-validate="true" aria-required="true"
                                                placeholder="Commission">
                                            <c:forEach items="${commissionList}" var="commission">
                                                <option value="${commission.charge.chargeSeq}">${commission.charge.chargeName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Deductions</label>
                                    <div class="col-md-8 col-xs-12">
                                        <select class="form-control select deductionSeqList" data-live-search="true"
                                                name="deductionSeqList" id="deductionSeqList" multiple
                                                data-error="Please Select Deductions"
                                                data-validate="true" aria-required="true"
                                                placeholder="Deduction">
                                            <c:forEach items="${deductionList}" var="deduction">
                                                <option value="${deduction.charge.chargeSeq}">${deduction.charge.chargeName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Company Contribution</label>
                                    <div class="col-md-8 col-xs-12">
                                        <select class="form-control select companyContributionSeqList" data-live-search="true"
                                                name="companyContributionSeqList" id="companyContributionSeqList" multiple
                                                data-error="Please Select Company Contribution"
                                                data-validate="true" aria-required="true"
                                                placeholder="Contribution">
                                            <c:forEach items="${companyContributionList}" var="companyContribution">
                                                <option value="${companyContribution.charge.chargeSeq}">${companyContribution.charge.chargeName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>

                <div class="tab-pane" id="tab-second">
                    <div class="row">
                        <div class="col-md-6">
                            <form class="form-horizontal" method="post" name="search" id="search">
                                <div class="form-group">
                                    <h4 class="subTitle">Search Employee</h4>
                                </div>
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
                                            <option value="-1">All</option>
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
                                            <option value="-1">All</option>
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
                                                        access="!hasRole('ROLE_fleet@payrollConfig_VIEW')">
                                                    disabled="disabled"
                                                </sec:authorize>
                                                onclick="load_payroll_data()">Search
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="form-group" id="loadPayrollData"></div>
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
                <h4 class="modal-title">Payroll Details</h4>
            </div>
            <form class="form-horizontal" method="post" name="modal" id="modal">
                <div class="modal-body panel">

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-danger"
                            <sec:authorize
                                    access="!hasRole('ROLE_fleet@payrollConfig_DELETE')">
                                disabled="disabled"
                            </sec:authorize>
                            data-toggle=confirmation
                            data-btn-ok-label="Continue" data-btn-ok-icon="glyphicon glyphicon-share-alt"
                            data-btn-ok-class="btn-success"
                            data-btn-cancel-label="Cancel" data-btn-cancel-icon="glyphicon glyphicon-ban-circle"
                            data-btn-cancel-class="btn-danger"
                            data-title="Deleting Payroll"
                            data-content="Are you sure you want to Delete this Payroll ? "
                            data-id="modal"
                            data-on-confirm="delete_payroll">
                        Delete
                    </button>
                    <button type="button" class="btn btn-primary"
                            <sec:authorize
                                    access="!hasRole('ROLE_fleet@payrollConfig_UPDATE')">
                                disabled="disabled"
                            </sec:authorize>
                            onclick="update_payroll_config_modal()">Save Changes
                    </button>
                </div>
            </form>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
