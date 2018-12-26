<%@ include file="/WEB-INF/jsp/include.jsp" %>
<div class="col-md-12">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">Booking Details</h3>
            <div class="btn-group pull-right">
                <button class="btn btn-danger dropdown-toggle" data-toggle="dropdown"><i class="fa fa-bars"></i>
                    Export Data
                </button>
                <ul class="dropdown-menu">
                    <li class="divider"></li>
                    <li><a href="#" onClick="$('#financeChargeData').tableExport({type:'excel',escape:'false'});"><img
                            src='../../../../theme/img/icons/xls.png' width="24"/> XLS</a></li>
                    <li><a href="#" onClick="$('#financeChargeData').tableExport({type:'doc',escape:'false'});"><img
                            src='../../../../theme/img/icons/word.png' width="24"/> Word</a></li>
                    <li><a href="#" onClick="$('#financeChargeData').tableExport({type:'pdf',escape:'false'});"><img
                            src='../../../../theme/img/icons/pdf.png' width="24"/> PDF</a></li>
                </ul>
            </div>
        </div>
        <div class="panel-body">
            <table class="table datatable" id="financeChargeData" style="min-width: 2000px;">
                <thead>
                <tr>
                    <th width="5%">Booking Id</th>
                    <th width="5%">Job No</th>
                    <th width="5%">Customer</th>
                    <th width="5%">Shipper</th>
                    <%--20--%>
                    <th width="5%">Vehicle Type</th>
                    <th width="5%">Current Status</th>
                    <th width="5%">Transporter</th>
                    <th width="5%">Vehicle No</th>
                    <%--20--%>
                    <th width="5%">Actual Start</th>
                    <th width="5%">Pickup</th>
                    <th width="6%">Via Location</th>
                    <th width="5%">Delivery</th>
                    <th width="5%">Actual End</th>
                    <th width="4%">Reference No</th>
                    <th width="4%">Document Collect</th>
                    <%--31--%>
                    <th width="3%">Total Km</th>
                    <th width="3%">Char. Km</th>
                    <th width="4%">Revenue</th>
                    <th width="4%">Cost</th>
                    <%--16--%>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${bookingList}" var="booking">
                    <tr onclick="load_finance_charge('${booking.transportBookingSeq}','${booking.moduleSeq}','${targetType}','${referenceType}')" class="clickable">
                        <td>${booking.bookingNo}</td>
                        <td>${booking.job.jobNo}</td>
                        <td>${booking.customer.stakeholderName}</td>
                        <td>${booking.shipper.stakeholderName}</td>
                        <td>${booking.vehicleType.vehicleTypeName}</td>
                        <td bgcolor="${booking.transportBookingStatus.colorCode}">${booking.transportBookingStatus.currentStatusDescription}</td>
                        <td>${booking.transportBookingVehicleList[0].transportCompany.stakeholderName}</td>
                        <td>${booking.transportBookingVehicleList[0].vehicle.vehicleNo}</td>

                        <td bgcolor="#6699FF">${booking.actualStartLocation.destination}</td>
                        <td bgcolor="#98FB98">${booking.pickupLocation.destination}</td>
                        <td bgcolor="#6699FF">${booking.viaLocationString}</td>
                        <td bgcolor="#6699FF">${booking.deliveryLocation.destination}</td>
                        <td bgcolor="#98FB98">${booking.actualEndLocation.destination}</td>
                        <td>${booking.customerReferenceNo}</td>
                        <td><fmt:formatDate value="${booking.transportBookingFeedback.documentsCollectedDate}" pattern="yyyy-MM-dd"/></td>

                        <td>${(booking.transportBookingFeedback.cargoInHandKm eq null ? booking.estimatedKm:booking.transportBookingFeedback.cargoInHandKm) + booking.transportBookingFeedback.placementKm}</td>
                        <td>${booking.transportBookingFeedback.chargeableKm}</td>
                        <td bgcolor="#FF9999"><fmt:formatNumber type="currency" currencySymbol=" " value="${booking.revenue}" /></td>
                        <td bgcolor="#FF9999"><fmt:formatNumber type="currency" currencySymbol=" " value="${booking.cost}" /></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>