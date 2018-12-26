<%@ include file="/WEB-INF/jsp/include.jsp" %>
<script type="text/javascript" src="../../../customjs/fleet/salaryAdvance.js"></script>
<div class="page-title">
    <h3><span class="fa fa-money"></span>Salary Advance</h3>
</div>

<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default tabs">
            <ul class="nav nav-tabs" role="tablist">
                <li class="active"><a href="#tab-first" role="tab" data-toggle="tab">Salary Advance</a></li>
                <li><a href="#tab-second" role="tab" data-toggle="tab">Search Salary Advance</a></li>
            </ul>
            <div class="panel-body tab-content">
                <div class="tab-pane active" id="tab-first">
                    <div class="col-md-12">
                        <form class="form-horizontal" method="post" name="searchSalaryForm" id="searchSalaryForm">
                            <div class="form-group removeFromModal">
                                <div class="col-md-7">
                                    <h4 class="subTitle">Calculations for Salary Advance</h4>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label class="col-md-3 control-label">Designation</label>
                                    <div class="col-md-8 col-xs-12">
                                        <select class="form-control select" data-live-search="true"
                                                name="employeeDesignationSeq" id="employeeDesignationSeq"
                                                data-error="Please Select Designation"
                                                data-validate="true" required aria-required="true"
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
                                                data-validate="true" required aria-required="true"
                                                placeholder="Employee">
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group createOperation removeFromModal">
                                    <div class="col-sm-offset-2 col-sm-9">
                                        <button type="button" class="btn btn-primary pull-right"
                                                onclick="search_salary()">Search
                                        </button>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-5">
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Year/Month</label>
                                    <div class="col-md-4 col-xs-12">
                                        <input type="text" class="form-control" name="salaryYear" maxlength="50" readonly
                                               id="salaryYear" value="${currentYear}"/>
                                    </div>
                                    <div class="col-md-4 col-xs-12">
                                        <select name="salaryMonth"
                                                id="salaryMonth"
                                                class="form-control select"
                                                data-live-search="true"
                                                title="Month"
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
                                        <input type="text" class="form-control datepicker" name="startDate" maxlength="50"
                                               value="<fmt:formatDate value='${startDate}' pattern='yyyy-MM-dd'/>"
                                               id="startDate"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">End Date</label>
                                    <div class="col-md-8 col-xs-12">
                                        <input type="text" class="form-control datepicker" name="endDate" maxlength="50"
                                               value="<fmt:formatDate value='${endDate}' pattern='yyyy-MM-dd'/>"
                                               id="endDate"/>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="col-md-12" id="salaryDetails" style="display: none; padding-top: 5px">
                    </div>
                </div>

                <div class="tab-pane" id="tab-second">
                    <div class="row">
                        <div class="col-md-12">
                            <form class="form-horizontal" method="post" name="searchForm" id="searchForm">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label class="col-md-3 control-label">Designation</label>
                                        <div class="col-md-8 col-xs-12">
                                            <select class="form-control select" data-live-search="true"
                                                    name="employeeDesignationSeq" id="employeeDesignationSeqSearch"
                                                    onchange="get_employee_list_search()"
                                                    placeholder="Designation">
                                                <option value="">ALL</option>
                                                <c:forEach items="${designationList}" var="employeeDesignation">
                                                    <option value="${employeeDesignation.employeeDesignationSeq}">${employeeDesignation.designation}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-3 col-xs-12 control-label">Employee</label>
                                        <div class="col-md-8 col-xs-12">
                                            <select class="form-control select" data-live-search="true"
                                                    name="employeeSeq" id="employeeSeqSearch"
                                                    placeholder="Employee">
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-5">
                                    <div class="form-group">
                                        <label class="col-md-3 col-xs-12 control-label">Year/Month</label>
                                        <div class="col-md-4 col-xs-12">
                                            <input type="text" class="form-control" name="salaryYear" maxlength="50"
                                                   id="salaryYearSearch" value="${currentYear}"/>
                                        </div>
                                        <div class="col-md-4 col-xs-12">
                                            <select name="salaryMonth"
                                                    id="salaryMonthSearch"
                                                    class="form-control select"
                                                    title="Month">
                                                <c:forEach items="${monthList}" var="month">
                                                    <option value="${month.value}" ${month.value eq currentMonth ? 'selected' : ''}>${month.name()}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-sm-offset-2 col-sm-9">
                                            <button type="button" class="btn btn-primary pull-right"
                                                    onclick="search_salary_advance()">Search
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="form-group" id="salaryAdvanceData"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="myModal">
    <div class="modal-dialog cs-modal-md">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Salary Advance Details</h4>
            </div>
            <form class="form-horizontal" method="post" name="modal" id="modal">
                <div class="modal-body panel" style="font-size: 14px">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label class="col-md-4 col-xs-12">Employee</label>
                            <label class="col-md-7 col-xs-12" rv-text="salaryAdvance.employee.employeeName"></label>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 col-xs-12">Designation</label>
                            <label class="col-md-7 col-xs-12" rv-text="salaryAdvance.employeeDesignation.designation"></label>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 col-xs-12">Created By</label>
                            <label class="col-md-7 col-xs-12" rv-text="salaryAdvance.createdBy"></label>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 col-xs-12">Created Date</label>
                            <label class="col-md-7 col-xs-12" rv-text="salaryAdvance.createdDate"></label>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <label class="col-md-4 col-xs-12">Year/Month</label>
                            <label class="col-md-7 col-xs-12" rv-text="salaryAdvance.yearMonth"></label>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 col-xs-12">Advance</label>
                            <label class="col-md-7 col-xs-12" rv-text="salaryAdvance.salaryAdvance | currency"></label>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 col-xs-12">Remarks</label>
                            <label class="col-md-7 col-xs-12" rv-text="salaryAdvance.remarks"></label>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 col-xs-12">NetPay At Advance</label>
                            <label class="col-md-7 col-xs-12" rv-text="salaryAdvance.netPay | currency"></label>
                        </div>
                        <input type="hidden" name="salaryAdvanceSeq" id="salaryAdvanceSeq" rv-value="salaryAdvance.salaryAdvanceSeq">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary" onclick="load_report()">Report</button>
                    <button type="button" class="btn btn-danger"
                            <sec:authorize
                                    access="!hasRole('ROLE_fleet@salaryAdvance_DELETE')">
                                disabled="disabled"
                            </sec:authorize>
                            data-toggle=confirmation
                            data-btn-ok-label="Continue" data-btn-ok-icon="glyphicon glyphicon-share-alt"
                            data-btn-ok-class="btn-success"
                            data-btn-cancel-label="Cancel" data-btn-cancel-icon="glyphicon glyphicon-ban-circle"
                            data-btn-cancel-class="btn-danger"
                            data-title="Deleting Salary Advance"
                            data-content="Are you sure you want to Delete this Salary Advance ? "
                            data-id="modal"
                            data-on-confirm="delete_salary_advance">
                        Delete
                    </button>
                </div>
            </form>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>

<div class="modal fade" id="reportOptionModel">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Report Selection</h4>
            </div>
            <form class="form-horizontal" method="post" name="reportModel" id="reportModel">
                <div class="modal-body panel">
                    <div class="form-group">
                        <label class="col-md-3 col-xs-12 control-label">Report Name</label>
                        <div class="col-md-8 col-xs-12">
                            <select name="reportSeq"
                                    id="reportModel_reportSeq"
                                    data-live-search="true"
                                    class="form-control select"
                                    data-validate="true" required
                                    data-error="Please Select Report Name"
                                    data-live-search="true" placeholder="Report">
                                <c:forEach items="${reportList}" var="report">
                                    <option value="${report.reportSeq}">${report.reportName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="help-block with-errors"></div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 col-xs-12 control-label">Format</label>
                        <div class="col-md-6 col-xs-12">
                            <label><input type="radio" class="radio-button" name="reportType"
                                          id="reportModel_pdf" checked value="1"/> PDF</label>
                        </div>

                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary pull-right" value="generateReport"
                            onclick="view_salary_advance_report()">Generate
                    </button>
                    <input type="hidden" id="report_salaryAdvanceSeq"/>
                </div>
            </form>
        </div>
    </div>
</div>
