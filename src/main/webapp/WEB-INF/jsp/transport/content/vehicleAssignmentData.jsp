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
                    <li><a href="#" onClick="$('#vehicleAssignmentData').tableExport({type:'excel',escape:'false'});"><img
                            src='../../../../theme/img/icons/xls.png' width="24"/> XLS</a></li>
                    <li><a href="#" onClick="$('#vehicleAssignmentData').tableExport({type:'doc',escape:'false'});"><img
                            src='../../../../theme/img/icons/word.png' width="24"/> Word</a></li>
                    <li><a href="#" onClick="$('#vehicleAssignmentData').tableExport({type:'pdf',escape:'false'});"><img
                            src='../../../../theme/img/icons/pdf.png' width="24"/> PDF</a></li>
                </ul>
            </div>
        </div>
        <div class="panel-body">
            <table class="datatable stripe row-border order-column table" id="vehicleAssignmentData" style="min-width: 3000px">
                <thead>
                <tr>
                    <th width="4%">Id</th>
                    <th width="4%">Job No</th>
                    <th width="5%">Customer</th>
                    <th width="4%">Vehicle Type</th>
                    <th width="5%">Current Status</th>
                    <%--22--%>

                    <th width="5%">Transporter</th>
                    <th width="4%">Vehicle</th>
                    <th width="5%">Driver</th>
                    <th width="5%">Helper</th>
                    <th width="5%">Payment Mode</th>
                    <th width="5%">Supplier Negotiations</th>
                    <%--24--%>

                    <th width="5%">Arrived at Pickup</th>
                    <th width="5%">Departed From Pickup</th>
                    <th width="5%">Shipper</th>
                    <th width="5%">Actual Start</th>
                    <th width="5%">Pickup</th>
                    <%--25--%>

                    <th width="4%">Arrival Time</th>
                    <th width="7%">Via Location</th>
                    <th width="5%">Delivery</th>
                    <th width="5%">Actual End</th>
                    <th width="4%">Delivery Time</th>
                    <th width="4%">Remarks</th>
                    <%--29--%>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${bookingList}" var="booking">
                    <tr id="${booking.transportBookingSeq}">
                        <td onclick="load_selected_booking(${booking.transportBookingSeq})">${booking.bookingNo}</td>
                        <td>${booking.job.jobNo}</td>
                        <td>${booking.customer.stakeholderName}</td>
                        <td>${booking.vehicleType.vehicleTypeName}</td>
                        <td id="status${booking.transportBookingSeq}" bgcolor="${booking.transportBookingStatus.colorCode}">${booking.transportBookingStatus.currentStatusDescription}</td>
                        <td onclick="load_assignment_panel('${booking.transportBookingSeq}')" id="transporterName${booking.transportBookingSeq}">${booking.transportBookingVehicleList[0].transportCompany.stakeholderName}</td>
                        <td onclick="load_assignment_panel('${booking.transportBookingSeq}')" id="vehicleNo${booking.transportBookingSeq}">${booking.transportBookingVehicleList[0].vehicle.vehicleNo}</td>
                        <td class="driverName" id="driverName${booking.transportBookingSeq}">${booking.transportBookingVehicleList[0].driver.employeeName} ${booking.transportBookingVehicleList[0].driver.addressBook.mobile}</td>
                        <td class="helperName" id="helperName${booking.transportBookingSeq}">${booking.transportBookingVehicleList[0].helper.employeeName} ${booking.transportBookingVehicleList[0].helper.addressBook.mobile}</td>
                        <td>${booking.paymentModeDescription}</td>
                        <td bgcolor="#FF9999" class="supplierNegotiation"></td>
                        <td bgcolor="#FF9999" class="AAP"><fmt:formatDate value="${booking.transportBookingFeedback.arrivedAtPickup}" pattern="yyyy-MM-dd hh:mm a"/></td>
                        <td bgcolor="#FF9999" class="DFP"><fmt:formatDate value="${booking.transportBookingFeedback.departedFromPickup}" pattern="yyyy-MM-dd hh:mm a"/></td>
                        <td>${booking.shipper.stakeholderName}</td>
                        <td bgcolor="#98FB98" class="actualStartLocation">${booking.actualStartLocation.destination}</td>
                        <td bgcolor="#6699FF">${booking.pickupLocation.destination}</td>
                        <td bgcolor="#6699FF"><fmt:formatDate value="${booking.requestedArrivalTime}" pattern="yyyy-MM-dd hh:mm a"/></td>
                        <td>${booking.viaLocationString}</td>
                        <td bgcolor="#6699FF">${booking.deliveryLocation.destination}</td>
                        <td bgcolor="#6699FF"><fmt:formatDate value="${booking.requestedDeliveryTime}" pattern="yyyy-MM-dd hh:mm a"/></td>
                        <td bgcolor="#98FB98" class="actualEndLocation" >${booking.actualEndLocation.destination}</td>
                        <td>${booking.remarks}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>