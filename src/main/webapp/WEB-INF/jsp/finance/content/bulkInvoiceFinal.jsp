<%@ include file="/WEB-INF/jsp/include.jsp" %>
<div class="col-md-12 panel">
    <div class="col-md-4 col-xs-12">
        <div>
            <label class="col-md-4 col-xs-12 control-label">Invoice No</label>
            <div class="col-md-7 col-xs-12">
                <h5 class="h5-top-margin-7">
                    <label>${bulkInvoice.bulkInvoiceNo}</label></h5>
            </div>
        </div>
        <div>
            <label class="col-md-4 col-xs-12 control-label">Start Date</label>
            <div class="col-md-7 col-xs-12">
                <h5 class="h5-top-margin-7">
                    <label><fmt:formatDate value="${bulkInvoice.startDate}" pattern="yyyy-MM-dd"/></label></h5>
            </div>
        </div>
        <div>
            <label class="col-md-4 col-xs-12 control-label">End Date</label>
            <div class="col-md-7 col-xs-12">
                <h5 class="h5-top-margin-7">
                    <label><fmt:formatDate value="${bulkInvoice.endDate}" pattern="yyyy-MM-dd"/></label></h5>
            </div>
        </div>
        <div>
            <label class="col-md-4 col-xs-12 control-label">Filter Type</label>
            <div class="col-md-7 col-xs-12">
                <h5 class="h5-top-margin-7">
                    <label>${bulkInvoice.dateFilterTypeDescription}</label></h5>
            </div>
        </div>
        <div>
            <label class="col-md-4 col-xs-12 control-label">Department</label>
            <div class="col-md-7 col-xs-12">
                <h5 class="h5-top-margin-7">
                    <label>${bulkInvoice.department.departmentName}</label></h5>
            </div>
        </div>
    </div>
    <div class="col-md-4 col-xs-12">
        <div>
            <label class="col-md-4 col-xs-12 control-label">Vehicle No</label>
            <div class="col-md-7 col-xs-12">
                <h5 class="h5-top-margin-7">
                    <label>${bulkInvoice.vehicle.vehicleNo}</label></h5>
            </div>
        </div>
        <div>
            <label class="col-md-4 col-xs-12 control-label">Invoice Party</label>
            <div class="col-md-7 col-xs-12">
                <h5 class="h5-top-margin-7">
                    <label>${bulkInvoice.stakeholder.stakeholderName}</label></h5>
            </div>
        </div>
        <div>
            <label class="col-md-4 col-xs-12 control-label">Shipper</label>
            <div class="col-md-7 col-xs-12">
                <h5 class="h5-top-margin-7">
                    <label>${bulkInvoice.shipper.stakeholderName}</label></h5>
            </div>
        </div>
        <div>
            <label class="col-md-4 col-xs-12 control-label">Module</label>
            <div class="col-md-7 col-xs-12">
                <h5 class="h5-top-margin-7">
                    <label>${bulkInvoice.module.moduleName}</label></h5>
            </div>
        </div>
        <div>
            <label class="col-md-4 col-xs-12 control-label">Currency</label>
            <div class="col-md-7 col-xs-12">
                <h5 class="h5-top-margin-7">
                    <label>${bulkInvoice.currency.currencyCode}</label></h5>
            </div>
        </div>
    </div>
    <div class="col-md-4 col-xs-12">
        <div>
            <label class="col-md-4 col-xs-12 control-label">Total Amount</label>
            <div class="col-md-7 col-xs-12">
                <h5 class="h5-top-margin-7">
                    <label><fmt:formatNumber
                            value="${bulkInvoice.finalTotalAmount}" type="currency"
                            pattern="#,##0.00;(#,##0.00)"/></label></h5>
            </div>
        </div>
        <div>
            <label class="col-md-4 col-xs-12 control-label">Without Tax</label>
            <div class="col-md-7 col-xs-12">
                <h5 class="h5-top-margin-7">
                    <label><fmt:formatNumber
                            value="${bulkInvoice.finalWithoutTaxAmount}" type="currency"
                            pattern="#,##0.00;(#,##0.00)"/></label></h5>
            </div>
        </div>
        <div>
            <label class="col-md-4 col-xs-12 control-label">Total VAT</label>
            <div class="col-md-7 col-xs-12">
                <h5 class="h5-top-margin-7">
                    <label><fmt:formatNumber
                            value="${bulkInvoice.finalVatAmount}" type="currency"
                            pattern="#,##0.00;(#,##0.00)"/></label></h5>
            </div>
        </div>
        <div>
            <label class="col-md-4 col-xs-12 control-label">Total Other</label>
            <div class="col-md-7 col-xs-12">
                <h5 class="h5-top-margin-7">
                    <label><fmt:formatNumber
                            value="${bulkInvoice.finalOtherTaxAmount}" type="currency"
                            pattern="#,##0.00;(#,##0.00)"/></label></h5>
            </div>
        </div>
        <div>
            <label class="col-md-4 col-xs-12 control-label">Remarks</label>
            <div class="col-md-7 col-xs-12">
                <h5 class="h5-top-margin-7">
                    <label>${bulkInvoice.remark}</label></h5>
            </div>
        </div>
    </div>
</div>

<div class="col-md-12 panel">
    <table class="table data-table-specific" id="invoiceableChargeDetail">
        <thead>
        <tr>
            <th width="20%">Charge</th>
            <th width="10%">Rate</th>
            <th width="10%">Quantity</th>
            <th width="10%">Amount</th>
            <th width="30%">Tax</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${bulkInvoice.bulkInvoiceChargeDetailList}" var="financialChargeDetail">
            <tr>
                <td>${financialChargeDetail.charge.chargeName}</td>
                <td>${financialChargeDetail.chargeValue}</td>
                <td>${financialChargeDetail.quantity}</td>
                <td><fmt:formatNumber
                        value="${financialChargeDetail.amount}"
                        type="currency"
                        pattern="#,##0.00#;(#,##0.00#-)"/></td>
                <td>
                    <c:forEach items="${financialChargeDetail.bulkInvoiceChargeDetailTaxList}" var="bulkInvoiceChargeDetailTax">
                        ${bulkInvoiceChargeDetailTax.taxRegistration.taxName}(${bulkInvoiceChargeDetailTax.taxRegistration.taxRateDisplay}) = <fmt:formatNumber value="${bulkInvoiceChargeDetailTax.taxAmount}" type="currency" pattern="#,##0.00;(#,##0.00)"/> &nbsp;&nbsp;&nbsp;
                    </c:forEach>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<div class="col-md-6">
    <div class="form-group">
        <label class="col-md-3 text-right">Created By</label>
        <label class="col-md-8">${bulkInvoice.createdBy}</label>
    </div>
    <div class="form-group">
        <label class="col-md-3 text-right">Created Date</label>
        <label class="col-md-8"><fmt:formatDate value="${bulkInvoice.createdDate}" pattern="yyyy-MM-dd hh:mm a"/></label>
    </div>
</div>
<div class="col-md-6">
    <div class="form-group">
        <label class="col-md-3 text-right">Last Modified By</label>
        <label class="col-md-8">${bulkInvoice.lastModifiedBy}</label>
    </div>
    <div class="form-group">
        <label class="col-md-3 text-right">Last Modified Date</label>
        <label class="col-md-8"><fmt:formatDate value="${bulkInvoice.lastModifiedDate}" pattern="yyyy-MM-dd hh:mm a"/></label>
    </div>
</div>

<input type="hidden" id="finalInvoice_bulkInvoiceSeq" name="bulkInvoiceSeq" value="${bulkInvoice.bulkInvoiceSeq}"/>
<input type="hidden" id="finalInvoice_moduleSeq" name="moduleSeq" value="${bulkInvoice.moduleSeq}"/>