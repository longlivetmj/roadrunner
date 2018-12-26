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
                    <li><a href="#" onClick="$('#locationList').tableExport({type:'excel',escape:'false'});"><img
                            src='../../../../theme/img/icons/xls.png' width="24"/> XLS</a></li>
                    <li><a href="#" onClick="$('#locationList').tableExport({type:'doc',escape:'false'});"><img
                            src='../../../../theme/img/icons/word.png' width="24"/> Word</a></li>
                    <li><a href="#" onClick="$('#locationList').tableExport({type:'pdf',escape:'false'});"><img
                            src='../../../../theme/img/icons/pdf.png' width="24"/> PDF</a></li>
                </ul>
            </div>
        </div>
        <div class="panel-body">
            <table class="table datatable" id="locationList">
                <thead>
                <tr>
                    <th width="15%">Vehicle No</th>
                    <th width="10%">Vehicle Code</th>
                    <th width="10%">Vehicle Type</th>
                    <th width="15%">Transporter</th>
                    <th width="10%">Default Driver</th>
                    <th width="10%">Default Helper</th>
                    <th width="10%">Payment Mode</th>
                    <th width="10%">Fuel Type</th>
                    <th width="10%">Vehicle Activation</th>
                    <th width="10%">Status</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${vehicleList}" var="vehicle">
                <tr onclick="load_vehicle_data_to_modal(${vehicle.vehicleSeq})" class="clickable">
                    <td>${vehicle.vehicleNo}</td>
                    <td>${vehicle.vehicleCode}</td>
                    <td><c:forEach items="${vehicle.vehicleVehicleTypeList}" var="vehicleType">
                        ${vehicleType.vehicleType.vehicleTypeName}<br>
                    </c:forEach>
                    </td>
                    <td>${vehicle.stakeholder.stakeholderName}</td>
                    <td>${vehicle.defaultDriver.employeeName}</td>
                    <td>${vehicle.defaultHelper.employeeName}</td>
                    <td>${vehicle.paymentModeDescription}</td>
                    <td>${vehicle.fuelTypeDescription}</td>
                    <td>${vehicle.vehicleActivationDescription}</td>
                    <td class="<c:if test="${vehicle.status eq 0}">delete-status-colour</c:if>
                                   <c:if test="${vehicle.status eq 1}">open-status-colour</c:if>
                                   <c:if test="${vehicle.status eq 2}">approved-status-colour</c:if>
                                   <c:if test="${vehicle.status eq 3}">closed-status-colour</c:if>"
                        >${vehicle.statusDescription}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
