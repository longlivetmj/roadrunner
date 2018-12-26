<%@ include file="/WEB-INF/jsp/include.jsp" %>
<div class="panel panel-default">
    <div class="panel-heading">
        <h3 class="panel-title">Salary Details</h3>
        <div class="btn-group pull-right">
            <button class="btn btn-danger dropdown-toggle" data-toggle="dropdown"><i class="fa fa-bars"></i> Export
                Data
            </button>
            <ul class="dropdown-menu">
                <li><a href="#" onClick="$('#salaryDataSearch').tableExport({type:'excel',escape:'false'});"><img
                        src='../../../../theme/img/icons/xls.png' width="24"/> XLS</a></li>
                <li><a href="#" onClick="$('#salaryDataSearch').tableExport({type:'doc',escape:'false'});"><img
                        src='../../../../theme/img/icons/word.png' width="24"/> Word</a></li>
                <li><a href="#" onClick="$('#salaryDataSearch').tableExport({type:'pdf',escape:'false'});"><img
                        src='../../../../theme/img/icons/pdf.png' width="24"/> PDF</a></li>
            </ul>
        </div>
    </div>
    <div class="panel-body">
        <table id="salaryDataSearch" class="table dataTable data-table-specific">
            <thead>
            <tr>
                <th>Employee</th>
                <th>Designation</th>
                <th>Year/Month</th>
                <th>Basic</th>
                <th>Allowance</th>
                <th>Deduction</th>
                <th>Commission</th>
                <th>Contributions</th>
                <th>Advance</th>
                <th>NetPay</th>
                <th>Status</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${salaryList}" var="salary">
                <tr onclick="load_salary_data_to_modal('${salary.salarySeq}')" class="clickable"
                    id="tr_${salary.salarySeq}">
                    <td>${salary.employee.employeeName}</td>
                    <td>${salary.employee.employeeDesignation.designation}</td>
                    <td>${salary.salaryYear}-${utils:getMonth(salary.salaryMonth)}</td>
                    <td><fmt:formatNumber type="currency" currencySymbol=" " value="${salary.basicSalary}"/></td>
                    <td><fmt:formatNumber type="currency" currencySymbol=" " value="${salary.totalAllowance}"/></td>
                    <td><fmt:formatNumber type="currency" currencySymbol=" " value="${salary.totalDeduction}"/></td>
                    <td><fmt:formatNumber type="currency" currencySymbol=" " value="${salary.totalCommission}"/></td>
                    <td><fmt:formatNumber type="currency" currencySymbol=" " value="${salary.totalCompanyContribution}"/></td>
                    <td><fmt:formatNumber type="currency" currencySymbol=" " value="${salary.totalSalaryAdvance}"/></td>
                    <td><fmt:formatNumber type="currency" currencySymbol=" " value="${salary.netPay}"/></td>
                    <td class="<c:if test="${salary.status eq 0}">delete-status-colour</c:if>
                                   <c:if test="${salary.status eq 1}">open-status-colour</c:if>
                                   <c:if test="${salary.status eq 2}">approved-status-colour</c:if>
                                   <c:if test="${salary.status eq 3}">closed-status-colour</c:if>
                            ">${salary.statusDescription}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

