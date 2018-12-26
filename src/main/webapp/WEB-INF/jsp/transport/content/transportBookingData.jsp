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
                    <li><a href="#" onClick="$('#bookingData').tableExport({type:'excel',escape:'false'});"><img
                            src='../../../../theme/img/icons/xls.png' width="24"/> XLS</a></li>
                    <li><a href="#" onClick="$('#bookingData').tableExport({type:'doc',escape:'false'});"><img
                            src='../../../../theme/img/icons/word.png' width="24"/> Word</a></li>
                    <li><a href="#" onClick="$('#bookingData').tableExport({type:'pdf',escape:'false'});"><img
                            src='../../../../theme/img/icons/pdf.png' width="24"/> PDF</a></li>
                </ul>
            </div>
        </div>
        <div class="panel-body">
            <table class="table datatable" id="bookingData">
                <thead>
                <tr>
                    <th width="5%">Booking Id</th>
                    <th width="5%">Job No</th>
                    <th width="10%">Customer</th>
                    <th width="10%">Shipper</th>
                    <th width="10%">Vehicle Type</th>
                    <th width="10%">Current Status</th>
                    <th width="10%">Pickup</th>
                    <th width="10%">Arrival Time</th>
                    <th width="10%">Delivery</th>
                    <th width="10%">Delivery Time</th>
                    <th width="10%">Remarks</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${bookingList}" var="booking">
                    <tr onclick="load_selected_booking(${booking.transportBookingSeq})" class="clickable">
                        <td>${booking.bookingNo}</td>
                        <td>${booking.job.jobNo}</td>
                        <td>${booking.customer.stakeholderName}</td>
                        <td>${booking.shipper.stakeholderName}</td>
                        <td>${booking.vehicleType.vehicleTypeName}</td>
                        <td bgcolor="${booking.transportBookingStatus.colorCode}">${booking.transportBookingStatus.currentStatusDescription}</td>
                        <td>${booking.pickupLocation.destination}</td>
                        <td><fmt:formatDate value="${booking.requestedArrivalTime}" pattern="yyyy-MM-dd hh:mm a"/></td>
                        <td>${booking.deliveryLocation.destination}</td>
                        <td><fmt:formatDate value="${booking.requestedDeliveryTime}" pattern="yyyy-MM-dd hh:mm a"/></td>
                        <td>${booking.remarks}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>