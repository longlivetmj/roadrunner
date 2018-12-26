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
                <c:forEach items="${expenseVoucherSearchList}" var="expenseVoucher">
                    <tr onclick="load_expense_voucher_data_to_modal('${expenseVoucher.expenseVoucherSeq}')" class="clickable"
                        id="tr_${expenseVoucher.expenseVoucherSeq}">
                        <td>${expenseVoucher.expenseVoucherNo}</td>
                        <td>${expenseVoucher.financialCharge.referenceNo}</td>
                        <td>${expenseVoucher.module.moduleName}</td>
                        <td>${expenseVoucher.department.departmentName}</td>
                        <td>${expenseVoucher.currency.currencyCode}</td>
                        <td><fmt:formatNumber
                                value="${expenseVoucher.finalTotalAmount}" type="currency"
                                pattern="#,##0.00;(#,##0.00)"/></td>
                        <td>${expenseVoucher.stakeholder.stakeholderName}</td>
                        <td class="<c:if test="${expenseVoucher.status eq 0}">delete-status-colour</c:if>
                                   <c:if test="${expenseVoucher.status eq 1}">open-status-colour</c:if>
                                   <c:if test="${expenseVoucher.status eq 2}">approved-status-colour</c:if>
                                   <c:if test="${expenseVoucher.status eq 3}">closed-status-colour</c:if>
                       ">${expenseVoucher.statusDescription != null ? expenseVoucher.statusDescription : '-'}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>