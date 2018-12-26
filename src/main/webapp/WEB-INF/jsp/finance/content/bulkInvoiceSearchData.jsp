<%@ include file="/WEB-INF/jsp/include.jsp" %>
<div class="col-md-12">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">Bulk Invoice Details</h3>
        </div>
        <div class="panel-body">
            <table class="table datatable data-table-specific">
                <thead>
                <tr>
                    <th>Bulk Invoice No</th>
                    <th>Currency</th>
                    <th>Invoice Amount</th>
                    <th>Vehicle</th>
                    <th>Invoice Party</th>
                    <th>Shipper</th>
                    <th>From</th>
                    <th>To</th>
                    <th>Status</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${bulkInvoiceSearchList}" var="bulkInvoice">
                    <tr onclick="load_bulk_invoice_data_to_modal('${bulkInvoice.bulkInvoiceSeq}')" class="clickable"
                        id="tr_${bulkInvoice.bulkInvoiceSeq}">
                        <td>${bulkInvoice.bulkInvoiceNo}</td>
                        <td>${bulkInvoice.currency.currencyCode}</td>
                        <td><fmt:formatNumber
                                value="${bulkInvoice.finalTotalAmount}" type="currency"
                                pattern="#,##0.00;(#,##0.00)"/></td>
                        <td>${bulkInvoice.vehicle.vehicleNo}</td>
                        <td>${bulkInvoice.stakeholder.stakeholderName}</td>
                        <td>${bulkInvoice.shipper.stakeholderName}</td>
                        <td><fmt:formatDate value="${bulkInvoice.startDate}" pattern="yyyy-MM-dd"/></td>
                        <td><fmt:formatDate value="${bulkInvoice.endDate}" pattern="yyyy-MM-dd"/></td>
                        <td class="<c:if test="${bulkInvoice.status eq 0}">delete-status-colour</c:if>
                                   <c:if test="${bulkInvoice.status eq 1}">open-status-colour</c:if>
                                   <c:if test="${bulkInvoice.status eq 2}">approved-status-colour</c:if>
                                   <c:if test="${bulkInvoice.status eq 3}">closed-status-colour</c:if>
                       ">${bulkInvoice.statusDescription != null ? bulkInvoice.statusDescription : '-'}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>