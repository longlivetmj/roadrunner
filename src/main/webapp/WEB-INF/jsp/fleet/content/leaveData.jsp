<%@ include file="/WEB-INF/jsp/include.jsp" %>
<div class="col-md-12">

    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">Leave Details</h3>
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
                    <th>Year/Month</th>
                    <th>Leave Type</th>
                    <th>Start Date</th>
                    <th>End Date</th>
                    <th>No Days</th>
                    <th>Reason</th>
                    <th>Status</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${leaveList}" var="leave">
                    <tr onclick="load_leave_data_to_modal(${leave.leaveSeq})" class="clickable"
                        id="tr_${leave.leaveSeq}">
                        <td>${leave.employee.employeeName}</td>
                        <td>${leave.employeeDesignation.designation}</td>
                        <td>${leave.leaveYear}-${utils:getMonth(leave.leaveMonth)}</td>
                        <td>${leave.leaveType.leaveType}</td>
                        <td><fmt:formatDate value="${leave.startDate}" pattern="yyyy-MM-dd"/></td>
                        <td><fmt:formatDate value="${leave.endDate}" pattern="yyyy-MM-dd"/></td>
                        <td>${leave.noOfDays}</td>
                        <td>${leave.reason}</td>
                        <td class="<c:if test="${leave.status eq 0}">delete-status-colour</c:if>
                                   <c:if test="${leave.status eq 1}">open-status-colour</c:if>
                                   <c:if test="${leave.status eq 2}">approved-status-colour</c:if>
                                   <c:if test="${leave.status eq 3}">closed-status-colour</c:if>"
                        >${leave.statusDescription}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

