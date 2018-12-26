<%@ include file="/WEB-INF/jsp/include.jsp" %>
<div class="col-md-12">

    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">Salary Advance Details</h3>
            <div class="btn-group pull-right">
                <button class="btn btn-danger dropdown-toggle" data-toggle="dropdown"><i class="fa fa-bars"></i> Export
                    Data
                </button>
                <ul class="dropdown-menu">
                    <li><a href="#" onClick="$('#salaryAdvanceDataSearch').tableExport({type:'excel',escape:'false'});"><img
                            src='../../../../theme/img/icons/xls.png' width="24"/> XLS</a></li>
                    <li><a href="#" onClick="$('#salaryAdvanceDataSearch').tableExport({type:'doc',escape:'false'});"><img
                            src='../../../../theme/img/icons/word.png' width="24"/> Word</a></li>
                    <li><a href="#" onClick="$('#salaryAdvanceDataSearch').tableExport({type:'pdf',escape:'false'});"><img
                            src='../../../../theme/img/icons/pdf.png' width="24"/> PDF</a></li>
                </ul>
            </div>
        </div>
        <div class="panel-body">
            <table id="salaryAdvanceDataSearch" class="table datatable">
                <thead>
                <tr>
                    <th>Employee</th>
                    <th>Designation</th>
                    <th>Year/Month</th>
                    <th>Advance</th>
                    <th>Remarks</th>
                    <th>Net Pay at Advance</th>
                    <th>Created Date</th>
                    <th>Status</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${salaryAdvanceList}" var="salaryAdvance">
                    <tr class="pointer" onclick="load_salary_advance('${salaryAdvance.salaryAdvanceSeq}')">
                        <td>${salaryAdvance.employee.employeeName}</td>
                        <td>${salaryAdvance.employeeDesignation.designation}</td>
                        <td>${salaryAdvance.salaryYear}-${utils:getMonth(salaryAdvance.salaryMonth)}</td>
                        <td><fmt:formatNumber type="currency" currencySymbol=" " value="${salaryAdvance.salaryAdvance}" /></td>
                        <td>${salaryAdvance.remarks}</td>
                        <td><fmt:formatNumber type="currency" currencySymbol=" " value="${salaryAdvance.netPay}" /></td>
                        <td><fmt:formatDate value='${salaryAdvance.createdDate}' pattern='yyyy-MM-dd hh:mm a'/></td>
                        <td class="<c:if test="${salaryAdvance.status eq 0}">delete-status-colour</c:if>
                                   <c:if test="${salaryAdvance.status eq 1}">open-status-colour</c:if>
                                   <c:if test="${salaryAdvance.status eq 2}">approved-status-colour</c:if>
                                   <c:if test="${salaryAdvance.status eq 3}">closed-status-colour</c:if>
                            ">${salaryAdvance.statusDescription}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

