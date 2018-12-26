<%@ include file="/WEB-INF/jsp/include.jsp" %>
<!-- START DEFAULT DATATABLE -->
<div class="col-md-12">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">Service and Maintenance Data</h3>
            <div class="btn-group pull-right">
                <button class="btn btn-danger dropdown-toggle" data-toggle="dropdown"><i class="fa fa-bars"></i> Export
                    Data
                </button>
                <ul class="dropdown-menu">
                    <li><a href="#" onClick="$('#serviceAndMaintenanceDataList').tableExport({type:'excel',escape:'false'});"><img
                            src='../../../../theme/img/icons/xls.png' width="24"/> XLS</a></li>
                    <li><a href="#" onClick="$('#serviceAndMaintenanceDataList').tableExport({type:'doc',escape:'false'});"><img
                            src='../../../../theme/img/icons/word.png' width="24"/> Word</a></li>
                    <li><a href="#" onClick="$('#serviceAndMaintenanceDataList').tableExport({type:'pdf',escape:'false'});"><img
                            src='../../../../theme/img/icons/pdf.png' width="24"/> PDF</a></li>
                </ul>
            </div>
        </div>
        <div class="panel-body">
            <table class="table datatable" id="serviceAndMaintenanceDataList">
                <thead>
                <tr>
                    <th width="5%">Action Type</th>
                    <th width="15%">Supplier</th>
                    <th width="15%">Transaction Date</th>
                    <th width="10%">Vehicle No</th>
                    <th width="10%">Meter Reading</th>
                    <th width="10%">Final Amount</th>
                    <th width="3%">Cash/Credit</th>
                    <th width="10%">Remarks</th>
                    <th width="5%">Status</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${serviceAndMaintenanceList}" var="serviceAndMaintenance">
                <tr onclick="load_serviceAndMaintenance_data_to_modal(${serviceAndMaintenance.serviceAndMaintenanceSeq})" class="clickable">
                    <td>${serviceAndMaintenance.actionTypeDescription}</td>
                    <td>${serviceAndMaintenance.supplier.stakeholderName}</td>
                    <td><fmt:formatDate value='${serviceAndMaintenance.transactionDate}' pattern='yyyy-MM-dd hh:mm a'/></td>
                    <td>${serviceAndMaintenance.vehicle.vehicleNo}</td>
                    <td>${serviceAndMaintenance.meterReading}</td>
                    <td><fmt:formatNumber type="currency" currencySymbol=" " value="${serviceAndMaintenance.totalAmount}" /></td>
                    <td>${serviceAndMaintenance.stakeholderCashType}</td>
                    <td>${serviceAndMaintenance.remarks}</td>
                    <td class="<c:if test="${serviceAndMaintenance.status eq 0}">delete-status-colour</c:if>
                                   <c:if test="${serviceAndMaintenance.status eq 1}">open-status-colour</c:if>
                                   <c:if test="${serviceAndMaintenance.status eq 2}">approved-status-colour</c:if>
                                   <c:if test="${serviceAndMaintenance.status eq 3}">closed-status-colour</c:if>"
                        >${serviceAndMaintenance.statusDescription}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
