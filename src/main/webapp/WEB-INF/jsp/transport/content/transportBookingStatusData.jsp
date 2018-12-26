<%@ include file="/WEB-INF/jsp/include.jsp" %>
<div class="col-md-12">
    <div class="panel panel-default">
        <div class="panel-heading">
            <div class="btn-group pull-right">
                <button class="btn btn-danger dropdown-toggle" data-toggle="dropdown"><i class="fa fa-bars"></i>
                    Export Data
                </button>
                <ul class="dropdown-menu">
                    <li class="divider"></li>
                    <li><a href="#"
                           onClick="$('#bookingDetailsSummary').tableExport({type:'excel',escape:'false'});"><img
                            src='../../../../theme/img/icons/xls.png' width="24"/> XLS</a></li>
                    <li><a href="#" onClick="$('#bookingDetailsSummary').tableExport({type:'doc',escape:'false'});"><img
                            src='../../../../theme/img/icons/word.png' width="24"/> Word</a></li>
                    <li><a href="#" onClick="$('#bookingDetailsSummary').tableExport({type:'pdf',escape:'false'});"><img
                            src='../../../../theme/img/icons/pdf.png' width="24"/> PDF</a></li>
                </ul>
            </div>
        </div>
        <div class="panel-body">
            <table class="datatable stripe row-border order-column table" id="bookingDetailsSummary"
                   style="min-width: 3000px;">
                <thead>
                <tr>
                    <th width="4%">Id</th>
                    <th width="4%">Job No</th>
                    <th width="4%">Arrival Time</th>
                    <th width="4%">Customer</th>
                    <th width="4%">Shipper</th>
                    <th width="4%">Current Status</th>
                    <th width="4%">Vehicle</th>
                    <%--20--%>

                    <th width="5%">Driver</th>
                    <th width="4%">Helper</th>
                    <th width="5%">Arrived at Pickup</th>
                    <th width="5%">Departed from Pickup</th>
                    <th width="5%">Arrived at Delivery</th>
                    <th width="5%">Departed from Delivery</th>
                    <%--25--%>

                    <th width="5%">Actual Start</th>
                    <th width="4%">Pickup</th>
                    <th width="5%">Via Location</th>
                    <%--22--%>

                    <th width="4%">Delivery</th>
                    <th width="4%">Delivery Time</th>
                    <th width="5%">Actual End</th>
                    <th width="4%">Vehicle Type</th>
                    <th width="4%">Char. Km</th>
                    <th width="4%">Total Km</th>
                    <th width="4%">Customer Ref. No</th>
                    <th width="4%">Document Collected Date</th>
                    <th width="4%">Remarks</th>
                    <%--25--%>

                </tr>
                </thead>
                <tbody>
                <c:forEach items="${bookingList}" var="booking">
                    <tr id="${booking.transportBookingSeq}">
                        <td onclick="load_selected_booking(${booking.transportBookingSeq})"
                            class="clickable">${booking.bookingNo}</td>
                        <td onclick="load_selected_booking_summary(${booking.transportBookingSeq})"
                            class="clickable">${booking.job.jobNo}</td>
                        <td bgcolor="#6699FF"><fmt:formatDate value="${booking.requestedArrivalTime}"
                                                              pattern="yyyy-MM-dd hh:mm a"/></td>
                        <td>${booking.customer.stakeholderName}</td>
                        <td>${booking.shipper.stakeholderName}</td>
                        <td id="status${booking.transportBookingSeq}"
                            bgcolor="${booking.transportBookingStatus.colorCode}">${booking.transportBookingStatus.currentStatusDescription}</td>
                        <td class="clickable"
                            onclick="launch_map('${booking.transportBookingSeq}')">${booking.transportBookingVehicleList[0].vehicle.vehicleNo}</td>
                        <td>${booking.transportBookingVehicleList[0].driver.employeeName} ${booking.transportBookingVehicleList[0].driver.addressBook.mobile}</td>
                        <td>${booking.transportBookingVehicleList[0].helper.employeeName} ${booking.transportBookingVehicleList[0].helper.addressBook.mobile}</td>
                        <td bgcolor="#FF9999"
                                <c:if test="${booking.transportBookingStatus.timeUpdate == 1}">
                                    class="AAP"
                                </c:if>
                        ><fmt:formatDate value="${booking.transportBookingFeedback.arrivedAtPickup}"
                                         pattern="yyyy-MM-dd hh:mm a"/></td>
                        <td bgcolor="#FF9999"
                                <c:if test="${booking.transportBookingStatus.timeUpdate == 1}">
                                    class="DFP"
                                </c:if>
                        ><fmt:formatDate value="${booking.transportBookingFeedback.departedFromPickup}"
                                         pattern="yyyy-MM-dd hh:mm a"/></td>
                        <td bgcolor="#FF9999"
                                <c:if test="${booking.transportBookingStatus.timeUpdate == 1}">
                                    class="AAD"
                                </c:if>
                        ><fmt:formatDate value="${booking.transportBookingFeedback.arrivedAtDelivery}"
                                         pattern="yyyy-MM-dd hh:mm a"/></td>
                        <td bgcolor="#FF9999"
                                <c:if test="${booking.transportBookingStatus.timeUpdate == 1}">
                                    class="DFD"
                                </c:if>
                        ><fmt:formatDate value="${booking.transportBookingFeedback.departedFromDelivery}"
                                         pattern="yyyy-MM-dd hh:mm a"/></td>
                        <td bgcolor="#98FB98"
                            class="actualStartLocation">${booking.actualStartLocation.destination}</td>
                        <td bgcolor="#6699FF">${booking.pickupLocation.destination}</td>
                        <td>${booking.viaLocationString}</td>
                        <td bgcolor="#6699FF">${booking.deliveryLocation.destination}</td>
                        <td bgcolor="#6699FF"><fmt:formatDate value="${booking.requestedDeliveryTime}"
                                                              pattern="yyyy-MM-dd hh:mm a"/></td>
                        <td bgcolor="#98FB98" class="actualEndLocation">${booking.actualEndLocation.destination}</td>
                        <td>${booking.vehicleType.vehicleTypeName}</td>
                        <td>${booking.transportBookingFeedback.chargeableKm}</td>
                        <td>${(booking.transportBookingFeedback.cargoInHandKm eq null ? booking.estimatedKm:booking.transportBookingFeedback.cargoInHandKm) + booking.transportBookingFeedback.placementKm}</td>
                        <td class="customerReferenceNo">${booking.customerReferenceNo}</td>
                        <td class="documentsCollectedDate"><fmt:formatDate value="${booking.transportBookingFeedback.documentsCollectedDate}" pattern="yyyy-MM-dd"/></td>
                        <td>${booking.remarks}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>