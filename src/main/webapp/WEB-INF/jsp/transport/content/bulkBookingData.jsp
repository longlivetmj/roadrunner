<%@ include file="/WEB-INF/jsp/include.jsp" %>
<div class="col-md-12">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">Bulk Booking Upload Status</h3>
        </div>
        <div class="panel-body">
            <table class="table datatable" id="bulkBookingData">
                <thead>
                <tr>
                    <th>Uploaded Date</th>
                    <th>Customer</th>
                    <th>Shipper</th>
                    <th>Status</th>
                    <th>Out Put</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${bulkBookingData}" var="bulkBooking">
                    <tr>
                        <td><fmt:formatDate value="${bulkBooking.createdDate}" pattern="yyyy-MM-dd hh:mm a"/></td>
                        <td>${bulkBooking.customer.stakeholderName}</td>
                        <td>${bulkBooking.shipper.stakeholderName}</td>
                        <td class="<c:if test="${bulkBooking.currentStatus eq 0}">error-status-colour</c:if>
                                   <c:if test="${bulkBooking.currentStatus eq 1}">success-status-colour</c:if>">
                            <c:if test="${bulkBooking.currentStatus eq 0}">FAILED</c:if>
                            <c:if test="${bulkBooking.currentStatus eq 1}">SUCCESS</c:if></td>
                        <td>${bulkBooking.processOutPut}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<!-- END DEFAULT DATATABLE -->