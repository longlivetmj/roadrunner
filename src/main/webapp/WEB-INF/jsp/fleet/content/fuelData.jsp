<%@ include file="/WEB-INF/jsp/include.jsp" %>
<!-- START DEFAULT DATATABLE -->
<div class="col-md-12">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">Vehicle Details</h3>
            <div class="btn-group pull-right">
                <button class="btn btn-danger dropdown-toggle" data-toggle="dropdown"><i class="fa fa-bars"></i> Export
                    Data
                </button>
                <ul class="dropdown-menu">
                    <li><a href="#" onClick="$('#fuelDataList').tableExport({type:'excel',escape:'false'});"><img
                            src='../../../../theme/img/icons/xls.png' width="24"/> XLS</a></li>
                    <li><a href="#" onClick="$('#fuelDataList').tableExport({type:'doc',escape:'false'});"><img
                            src='../../../../theme/img/icons/word.png' width="24"/> Word</a></li>
                    <li><a href="#" onClick="$('#fuelDataList').tableExport({type:'pdf',escape:'false'});"><img
                            src='../../../../theme/img/icons/pdf.png' width="24"/> PDF</a></li>
                </ul>
            </div>
        </div>
        <div class="panel-body">
            <table class="table datatable" id="fuelDataList">
                <thead>
                <tr>
                    <th width="15%">Supplier</th>
                    <th width="15%">Transaction Date</th>
                    <th width="10%">Vehicle No</th>
                    <th width="10%">Meter Reading</th>
                    <th width="5%">Fuel Type</th>
                    <th width="15%">Fuel Variant</th>
                    <th width="5%">Unit Price</th>
                    <th width="3%">Qty</th>
                    <th width="8%">Amount</th>
                    <th width="3%">Cash/Credit</th>
                    <th width="5%">Status</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${vehicleFuelList}" var="vehicleFuel">
                <tr onclick="load_fuel_data_to_modal(${vehicleFuel.vehicleFuelSeq})" class="clickable">
                    <td>${vehicleFuel.supplier.stakeholderName}</td>
                    <td><fmt:formatDate value='${vehicleFuel.transactionDate}' pattern='yyyy-MM-dd hh:mm a'/></td>
                    <td>${vehicleFuel.vehicle.vehicleNo}</td>
                    <td>${vehicleFuel.meterReading}</td>
                    <td>${vehicleFuel.fuelTypeDescription}</td>
                    <td>${vehicleFuel.fuelTypeVariant.variantName}</td>
                    <td>${vehicleFuel.unitPrice}</td>
                    <td>${vehicleFuel.quantity}</td>
                    <td><fmt:formatNumber type="currency" currencySymbol=" " value="${vehicleFuel.amount}" /></td>
                    <td>${vehicleFuel.stakeholderCashType}</td>
                    <td class="<c:if test="${vehicleFuel.status eq 0}">delete-status-colour</c:if>
                                   <c:if test="${vehicleFuel.status eq 1}">open-status-colour</c:if>
                                   <c:if test="${vehicleFuel.status eq 2}">approved-status-colour</c:if>
                                   <c:if test="${vehicleFuel.status eq 3}">closed-status-colour</c:if>"
                        >${vehicleFuel.statusDescription}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
