<%@ include file="/WEB-INF/jsp/include.jsp" %>
<div class="col-md-12">
    <table class="table data-table-specific" id="invoiceableChargeDetail">
        <thead>
        <tr>
            <th>Charge</th>
            <th>Rate</th>
            <th>Quantity</th>
            <th>Currency</th>
            <th>Amount</th>
            <th>VAT</th>
            <th>Other Tax</th>
            <th>Select</th>
        </tr>
        </thead>
        <tbody>
        <c:set var="index" value="${0}"/>
        <c:forEach items="${financialChargeDetailList}" var="financialChargeDetail">
            <tr>
                <td>${financialChargeDetail.charge.chargeName}</td>
                <td>${financialChargeDetail.chargeValue}</td>
                <td>${financialChargeDetail.quantity}</td>
                <td>${financialChargeDetail.tempCurrencyCode}</td>
                <td><fmt:formatNumber
                        value="${financialChargeDetail.tempAmount}"
                        type="currency"
                        pattern="#,##0.00#;(#,##0.00#-)"/></td>
                <td>
                    <c:forEach items="${taxRegistrationVatList}" var="taxRegistration">
                        <label class="choice-box">
                            <input type="checkbox" class="icheckbox"
                                   id="taxRegistrationSeq_${financialChargeDetail.financialChargeDetailSeq}_${taxRegistration.taxRegistrationSeq}"
                                   name="taxRegistrationSeq_${financialChargeDetail.financialChargeDetailSeq}_${taxRegistration.taxRegistrationSeq}"
                                   onchange="calculate()"
                                   value="${taxRegistration.taxRegistrationSeq}" ${financialChargeDetail.checkedStatus}/>
                                ${taxRegistration.taxName} - ${taxRegistration.taxRateDisplay}</label>
                    </c:forEach>

                </td>
                <td>
                    <c:forEach items="${taxRegistrationOtherList}" var="taxRegistration">
                        <label class="choice-box">
                            <input type="checkbox" class="icheckbox"
                                   id="taxRegistrationSeq_${financialChargeDetail.financialChargeDetailSeq}_${taxRegistration.taxRegistrationSeq}"
                                   name="taxRegistrationSeq_${financialChargeDetail.financialChargeDetailSeq}_${taxRegistration.taxRegistrationSeq}"
                                   onchange="calculate()"
                                   value="${taxRegistration.taxRegistrationSeq}" ${financialChargeDetail.checkedStatus}/>
                                ${taxRegistration.taxName} - ${taxRegistration.taxRateDisplay}</label>
                    </c:forEach>
                </td>
                <td>
                    <input type="checkbox" class="icheckbox" id="financialChargeDetailSeq"
                           name="localInvoiceChargeDetailList[${index}].financialChargeDetailSeq"
                           onchange="calculate()"
                           value="${financialChargeDetail.financialChargeDetailSeq}" ${financialChargeDetail.checkedStatus}/>
                </td>
            </tr>
            <c:set var="index" value="${index+1}"/>
        </c:forEach>

        </tbody>
    </table>
</div>
