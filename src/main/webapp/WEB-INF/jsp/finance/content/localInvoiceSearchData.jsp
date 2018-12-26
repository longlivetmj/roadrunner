<%@ include file="/WEB-INF/jsp/include.jsp" %>
<div class="col-md-12">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">Local Invoice Details</h3>
        </div>
        <div class="panel-body">
            <table class="table datatable data-table-specific">
                <thead>
                <tr>
                    <th>Local Invoice No</th>
                    <th>Request Id</th>
                    <th>Module</th>
                    <th>Department</th>
                    <th>Currency</th>
                    <th>Invoice Amount</th>
                    <th>Stakeholder</th>
                    <th>Status</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${localInvoiceSearchList}" var="localInvoice">
                    <tr onclick="load_local_invoice_data_to_modal('${localInvoice.localInvoiceSeq}')" class="clickable"
                        id="tr_${localInvoice.localInvoiceSeq}">
                        <td>${localInvoice.localInvoiceNo}</td>
                        <td>${localInvoice.financialCharge.referenceNo}</td>
                        <td>${localInvoice.module.moduleName}</td>
                        <td>${localInvoice.department.departmentName}</td>
                        <td>${localInvoice.currency.currencyCode}</td>
                        <td><fmt:formatNumber
                                value="${localInvoice.finalTotalAmount}" type="currency"
                                pattern="#,##0.00;(#,##0.00)"/></td>
                        <td>${localInvoice.stakeholder.stakeholderName}</td>
                        <td class="<c:if test="${localInvoice.status eq 0}">delete-status-colour</c:if>
                                   <c:if test="${localInvoice.status eq 1}">open-status-colour</c:if>
                                   <c:if test="${localInvoice.status eq 2}">approved-status-colour</c:if>
                                   <c:if test="${localInvoice.status eq 3}">closed-status-colour</c:if>
                       ">${localInvoice.statusDescription != null ? localInvoice.statusDescription : '-'}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>