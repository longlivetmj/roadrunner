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
                    <th>Stakeholder</th>
                    <th>Employee Name</th>
                    <th>Designation</th>
                    <th>NIC</th>
                    <th>Contacts</th>
                    <th>Status</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${employeeListDB}" var="employee">
                    <tr onclick="load_employee_data_to_modal(${employee.employeeSeq})" class="clickable" id="tr_${employee.employeeSeq}">
                        <td>${employee.stakeholder.stakeholderName}</td>
                        <td>${employee.employeeName}</td>
                        <td>${employee.employeeDesignation.designation}</td>
                        <td>${employee.nicNo}</td>
                        <td>${employee.addressBook.mobile} ${employee.addressBook.telephone}</td>
                        <td  class="<c:if test="${employee.status eq 0}">delete-status-colour</c:if>
                                   <c:if test="${employee.status eq 1}">open-status-colour</c:if>
                                   <c:if test="${employee.status eq 2}">approved-status-colour</c:if>
                                   <c:if test="${employee.status eq 3}">closed-status-colour</c:if>"
                        >${employee.statusDescription}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

