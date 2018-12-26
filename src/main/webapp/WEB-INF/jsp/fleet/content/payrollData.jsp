<%@ include file="/WEB-INF/jsp/include.jsp" %>
<div class="col-md-12">

    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">Employee Details</h3>
            <div class="btn-group pull-right">
                <button class="btn btn-danger dropdown-toggle" data-toggle="dropdown"><i class="fa fa-bars"></i> Export
                    Data
                </button>
                <ul class="dropdown-menu">
                    <li><a href="#" onClick="$('#employeeDataSearch').tableExport({type:'excel',escape:'false'});"><img
                            src='../../../../theme/img/icons/xls.png' width="24"/> XLS</a></li>
                    <li><a href="#" onClick="$('#employeeDataSearch').tableExport({type:'doc',escape:'false'});"><img
                            src='../../../../theme/img/icons/word.png' width="24"/> Word</a></li>
                    <li><a href="#" onClick="$('#employeeDataSearch').tableExport({type:'pdf',escape:'false'});"><img
                            src='../../../../theme/img/icons/pdf.png' width="24"/> PDF</a></li>
                </ul>
            </div>
        </div>
        <div class="panel-body">
            <table id="employeeDataSearch" class="table datatable">
                <thead>
                <tr>
                    <th>Employee Name</th>
                    <th>Designation</th>
                    <th>Basic</th>
                    <th>Allowance</th>
                    <th>Deduction</th>
                    <th>Commission</th>
                    <th>Status</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${payrollList}" var="payroll">
                    <tr onclick="load_payroll_data_to_modal(${payroll.payrollSeq})" class="clickable" id="tr_${payroll.payrollSeq}">
                        <td>${payroll.employee.employeeName}</td>
                        <td>${payroll.employee.employeeDesignation.designation}</td>
                        <td>${payroll.basicSalary}</td>
                        <td>
                            <c:forEach items="${payroll.payrollAllowanceList}" var="payrollAllowance">
                                ${payrollAllowance.charge.chargeName} &nbsp;&nbsp;
                            </c:forEach>
                        </td>
                        <td>
                            <c:forEach items="${payroll.payrollDeductionList}" var="payrollDeduction">
                                ${payrollDeduction.charge.chargeName} &nbsp;&nbsp;
                            </c:forEach>
                        </td>
                        <td>
                            <c:forEach items="${payroll.payrollCommissionList}" var="payrollCommission">
                                ${payrollCommission.charge.chargeName} &nbsp;&nbsp;
                            </c:forEach>
                        </td>
                        <td>${payroll.statusDescription}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

