<%@ include file="/WEB-INF/jsp/include.jsp" %>
<form class="form-horizontal" method="post" name="createSalaryAdvance" id="createSalaryAdvance">
<div class="form-group">
    <div class="panel panel-info">
        <div class="panel-heading" style="padding: 5px">
            <h5 class="panel-title">Employee Details</h5>
        </div>
        <div class="panel-body padding-bottom-0">
            <div class="col-md-12">
                <div class="form-group">
                    <div class="col-md-4 col-xs-12">
                        <div class="form-group">
                            <label class="col-md-4 col-xs-12">Employee</label>
                            <label class="col-md-8 col-xs-12">${salary.employee.employeeName}</label>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 col-xs-12">Designation</label>
                            <label class="col-md-8 col-xs-12">${salary.employee.employeeDesignation.designation}</label>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 col-xs-12">Basic Salary</label>
                            <label class="col-md-8 col-xs-12"><fmt:formatNumber type="currency" currencySymbol=" " value="${salary.basicSalary}" /></label>
                        </div>
                    </div>
                    <div class="col-md-4 col-xs-12">
                        <div class="form-group">
                            <label class="col-md-4 col-xs-12">Year/Month</label>
                            <label class="col-md-8 col-xs-12">${salary.salaryYear}-${utils:getMonth(salary.salaryMonth)}</label>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 col-xs-12">Start Date</label>
                            <label class="col-md-8 col-xs-12"><fmt:formatDate value='${salary.startDate}'
                                                                              pattern='yyyy-MM-dd'/></label>
                        </div>
                        <div class="form-group">
                            <label class="col-md-4 col-xs-12">End Date</label>
                            <label class="col-md-8 col-xs-12"><fmt:formatDate value='${salary.endDate}'
                                                                              pattern='yyyy-MM-dd'/></label>
                        </div>
                    </div>
                    <div class="col-md-4 col-xs-12">
                        <table class="table data-table-specific" id="salaryAdvanceHistory" style="font-size: 12px">
                            <thead>
                            <tr>
                                <th>Date</th>
                                <th>Advance</th>
                                <th>Remarks</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${salary.salaryAdvanceList}" var="salaryAdvance">
                                <tr>
                                    <td><fmt:formatDate value='${salaryAdvance.createdDate}' pattern='yyyy-MM-dd hh:mm a'/></td>
                                    <td><fmt:formatNumber type="currency" currencySymbol=" " value="${salaryAdvance.salaryAdvance}" /></td>
                                    <td>${salaryAdvance.remarks}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="panel panel-danger">
        <div class="panel-heading" style="padding: 5px">
            <h5 class="panel-title">Salary Advance</h5>
        </div>
        <div class="panel-body" style="font-size: 14px">
            <div class="col-md-4">
                <div class="form-group">
                    <label class="col-md-4 col-xs-12">Basic Salary</label>
                    <label class="col-md-7 col-xs-12"><fmt:formatNumber type="currency" currencySymbol=" " value="${salary.basicSalary}" /></label>
                </div>
                <div class="form-group">
                    <label class="col-md-4 col-xs-12">Total Advance</label>
                    <label class="col-md-7 col-xs-12"><fmt:formatNumber type="currency" currencySymbol=" " value="${salary.totalSalaryAdvance}" /></label>
                </div>
                <div class="form-group">
                    <label class="col-md-4 col-xs-12">Net Pay</label>
                    <label class="col-md-7 col-xs-12"><fmt:formatNumber type="currency" currencySymbol=" " value="${salary.netPay}" /></label>
                    <input type="hidden" id="netPay" value="${salary.netPay}"/>
                </div>
            </div>
            <div class="col-md-4">
                <div class="form-group">
                    <label class="col-md-4 col-xs-12">Commission</label>
                    <label class="col-md-7 col-xs-12"><fmt:formatNumber type="currency" currencySymbol=" " value="${salary.totalCommission}" /></label>
                </div>
                <div class="form-group">
                    <label class="col-md-4 col-xs-12">Allowance</label>
                    <label class="col-md-7 col-xs-12"><fmt:formatNumber type="currency" currencySymbol=" " value="${salary.totalAllowance}" /></label>
                </div>
            </div>
            <div class="col-md-4">
                <div class="form-group">
                    <label class="col-md-4 col-xs-12">Deduction</label>
                    <label class="col-md-7 col-xs-12"><fmt:formatNumber type="currency" currencySymbol=" " value="${salary.totalDeduction}" /></label>
                </div>
                <div class="form-group">
                    <label class="col-md-4 col-xs-12">Company Contribution</label>
                    <label class="col-md-7 col-xs-12"><fmt:formatNumber type="currency" currencySymbol=" " value="${salary.totalCompanyContribution}" /></label>
                </div>
            </div>
            <div class="panel-heading">
                <div class="col-md-3">
                    <div class="form-group">
                        <label class="col-md-3 col-xs-12">Advance</label>
                        <div class="col-md-6 col-xs-12"><input required type="text" class="form-control" id="salaryAdvance" name="salaryAdvance"  onchange="validateIntegerKeyPress(this)"/></div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="form-group">
                        <label class="col-md-3 col-xs-12">Transaction Date</label>
                        <div class="col-md-6 col-xs-12"><input required type="text" class="form-control datepicker" id="transactionDate" name="transactionDate"  /></div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="form-group">
                        <label class="col-md-2 col-xs-12">Remarks</label>
                        <div class="col-md-8 col-xs-12"><textarea required id="remarks" class="form-control" name="remarks"></textarea></div>
                    </div>
                </div>
                <div class="col-md-2">
                    <div class="form-group" style="padding-top: 10px">
                        <button type="button" class="btn btn-primary pull-left" onclick="create_salary_advance()">Submit</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="panel panel-success">
        <div class="panel-heading" style="padding: 5px">
            <h5 class="panel-title">Allowance</h5>
        </div>
        <div class="panel-body padding-0">
            <div class="col-md-12">
                <table class="table data-table-specific" id="allowanceDetails">
                    <thead>
                    <tr>
                        <th>Charge</th>
                        <th>Calculation Type</th>
                        <th>Rate</th>
                        <th>Multiply</th>
                        <th>Allowance</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:set var="allowanceIndex" value="${0}"/>
                    <c:forEach items="${salary.salaryAllowanceList}" var="salaryAllowance">
                        <tr>
                            <td>${salaryAllowance.charge.chargeName}</td>
                            <td>${salaryAllowance.calculationTypeDescription}</td>
                            <td>${salaryAllowance.chargeValue}</td>
                            <td>${salaryAllowance.multiplyValue}</td>
                            <td>${salaryAllowance.allowance}</td>
                            <td></td>
                        </tr>
                        <c:set var="allowanceIndex" value="${allowanceIndex+1}"/>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <div class="panel panel-info">
        <div class="panel-heading" style="padding: 5px">
            <h5 class="panel-title">Commission</h5>
        </div>
        <div class="panel-body padding-0">
            <div class="col-md-12">
                <table class="table data-table-specific" id="commissionDetails">
                    <thead>
                    <tr>
                        <th>Charge</th>
                        <th>Calculation Type</th>
                        <th>Rate</th>
                        <th>Breakdown</th>
                        <th>Commission</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:set var="commissionIndex" value="${0}"/>
                    <c:forEach items="${salary.salaryCommissionList}" var="salaryCommission">
                        <tr>
                            <td>${salaryCommission.charge.chargeName}</td>
                            <td>${salaryCommission.calculationTypeDescription}</td>
                            <td>${salaryCommission.chargeValue}</td>
                            <td>
                                <button type="button" class="btn btn-primary" onclick="load_commission_breakdown(${salaryCommission.chargeSeq})">View
                                </button>
                            </td>
                            <td>${salaryCommission.commission}</td>
                            <td></td>
                        </tr>
                        <c:set var="commissionIndex" value="${commissionIndex+1}"/>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <div class="panel panel-danger">
        <div class="panel-heading" style="padding: 5px">
            <h5 class="panel-title">Deduction</h5>
        </div>
        <div class="panel-body padding-0">
            <div class="col-md-12">
                <table class="table data-table-specific" id="deductionDetails">
                    <thead>
                    <tr>
                        <th>Charge</th>
                        <th>Calculation Type</th>
                        <th>Rate</th>
                        <th>Multiply</th>
                        <th>Deduction</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:set var="deductionIndex" value="${0}"/>
                    <c:forEach items="${salary.salaryDeductionList}" var="salaryDeduction">
                        <tr>
                            <td>${salaryDeduction.charge.chargeName}</td>
                            <td>${salaryDeduction.calculationTypeDescription}</td>
                            <td>${salaryDeduction.chargeValue}</td>
                            <td>${salaryDeduction.multiplyValue}</td>
                            <td>${salaryDeduction.deduction}</td>
                            <td></td>
                        </tr>
                        <c:set var="deductionIndex" value="${deductionIndex+1}"/>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <div class="panel panel-primary">
        <div class="panel-heading" style="padding: 5px">
            <h5 class="panel-title">Company Contribution</h5>
        </div>
        <div class="panel-body padding-0">
            <div class="col-md-12">
                <table class="table data-table-specific" id="contributionDetails">
                    <thead>
                    <tr>
                        <th>Charge</th>
                        <th>Calculation Type</th>
                        <th>Rate</th>
                        <th>Multiply</th>
                        <th>Contribution</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:set var="contributionIndex" value="${0}"/>
                    <c:forEach items="${salary.salaryCompanyContributionList}" var="salaryCompanyContribution">
                        <tr>
                            <td>${salaryCompanyContribution.charge.chargeName}</td>
                            <td>${salaryCompanyContribution.calculationTypeDescription}</td>
                            <td>${salaryCompanyContribution.chargeValue}</td>
                            <td>${salaryCompanyContribution.multiplyValue}</td>
                            <td>${salaryCompanyContribution.companyContribution}</td>
                            <td></td>
                        </tr>
                        <c:set var="contributionIndex" value="${contributionIndex+1}"/>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
    <input type="hidden" name="employeeSeq" value="${salary.employeeSeq}"/>
    <input type="hidden" name="salaryYear" value="${salary.salaryYear}"/>
    <input type="hidden" name="salaryMonth" value="${salary.salaryMonth}"/>
    <input type="hidden" name="startDate" value="<fmt:formatDate value='${salary.startDate}' pattern='yyyy-MM-dd'/>"/>
    <input type="hidden" name="endDate" value="<fmt:formatDate value='${salary.endDate}' pattern='yyyy-MM-dd'/>"/>
</form>

<c:forEach items="${salary.salaryCommissionList}" var="salaryCommission">
    <div id="salaryCommissionBreakDown${salaryCommission.chargeSeq}" style="display: none">
        <table class="table data-table-specific" style="font-size: 11px">
            <thead>
            <tr>
                <th>Request Id</th>
                <th>Shipper</th>
                <th>Start</th>
                <th>Pickup</th>
                <th>Via Location</th>
                <th>Delivery</th>
                <th>End</th>
                <th>Arrived at Pickup</th>
                <th>Vehicle No</th>
                <th>Total Km</th>
                <th>Per km Rate</th>
                <th>Commission</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${salaryCommission.salaryCommissionDetailList}" var="salaryCommissionDetail">
                <tr>
                    <td>${salaryCommissionDetail.transportBooking.bookingNo}</td>
                    <td>${salaryCommissionDetail.transportBooking.shipper.stakeholderName}</td>
                    <td>${salaryCommissionDetail.transportBooking.actualStartLocation.destination}</td>
                    <td>${salaryCommissionDetail.transportBooking.pickupLocation.destination}</td>
                    <td>${salaryCommissionDetail.transportBooking.viaLocationString}</td>
                    <td>${salaryCommissionDetail.transportBooking.deliveryLocation.destination}</td>
                    <td>${salaryCommissionDetail.transportBooking.actualEndLocation.destination}</td>
                    <td><fmt:formatDate value="${salaryCommissionDetail.transportBooking.transportBookingFeedback.arrivedAtPickup}" pattern="yyyy-MM-dd hh:mm a"/></td>
                    <td>${salaryCommissionDetail.transportBooking.transportBookingVehicleList[0].vehicle.vehicleNo}</td>
                    <td><fmt:formatNumber type="number" maxFractionDigits="2" value="${salaryCommissionDetail.totalKm}" /></td>
                    <td>${salaryCommissionDetail.rate}</td>
                    <td><fmt:formatNumber type="number" maxFractionDigits="2" value="${salaryCommissionDetail.amount}" /></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</c:forEach>

<div class="modal fade" id="salaryCommissionModal">
    <div class="modal-dialog cs-modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Salary Commission Details</h4>
            </div>
            <div class="modal-body panel" id="salaryCommissionModalBody">

            </div>
        </div>
    </div>
</div>
