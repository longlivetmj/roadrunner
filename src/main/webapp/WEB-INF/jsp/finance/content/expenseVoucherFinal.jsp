<%@ include file="/WEB-INF/jsp/include.jsp" %>
<div class="col-md-12 panel">
    <div class="col-md-4 col-xs-12">
        <div>
            <label class="col-md-4 col-xs-12 control-label">Invoice No</label>
            <div class="col-md-7 col-xs-12">
                <h5 class="h5-top-margin-7">
                    <label>${expenseVoucher.expenseVoucherNo}</label></h5>
            </div>
        </div>
        <div>
            <label class="col-md-4 col-xs-12 control-label">Request Id</label>
            <div class="col-md-7 col-xs-12">
                <h5 class="h5-top-margin-7">
                    <label>${expenseVoucher.transportBooking.bookingNo}</label></h5>
            </div>
        </div>
        <div>
            <label class="col-md-4 col-xs-12 control-label">Job No</label>
            <div class="col-md-7 col-xs-12">
                <h5 class="h5-top-margin-7">
                    <label>${expenseVoucher.transportBooking.job.jobNo}</label></h5>
            </div>
        </div>
        <div>
            <label class="col-md-4 col-xs-12 control-label">Invoice Party</label>
            <div class="col-md-7 col-xs-12">
                <h5 class="h5-top-margin-7">
                    <label>${expenseVoucher.stakeholder.stakeholderName}</label></h5>
            </div>
        </div>
        <div>
            <label class="col-md-4 col-xs-12 control-label">Module</label>
            <div class="col-md-7 col-xs-12">
                <h5 class="h5-top-margin-7">
                    <label>${expenseVoucher.module.moduleName}</label></h5>
            </div>
        </div>
        <div>
            <label class="col-md-4 col-xs-12 control-label">Department</label>
            <div class="col-md-7 col-xs-12">
                <h5 class="h5-top-margin-7">
                    <label>${expenseVoucher.department.departmentName}</label></h5>
            </div>
        </div>
    </div>
    <div class="col-md-4 col-xs-12">
        <div>
            <label class="col-md-4 col-xs-12 control-label">Vehicle</label>
            <div class="col-md-7 col-xs-12">
                <h5 class="h5-top-margin-7">
                    <label>${expenseVoucher.transportBooking.transportBookingVehicleList[0].vehicle.vehicleNo}</label></h5>
            </div>
        </div>
        <div>
            <label class="col-md-4 col-xs-12 control-label">Customer</label>
            <div class="col-md-7 col-xs-12">
                <h5 class="h5-top-margin-7">
                    <label>${expenseVoucher.transportBooking.customer.stakeholderName}</label></h5>
            </div>
        </div>
        <div>
            <label class="col-md-4 col-xs-12 control-label">Cha. KM</label>
            <div class="col-md-7 col-xs-12">
                <h5 class="h5-top-margin-7">
                    <label>${expenseVoucher.transportBooking.transportBookingFeedback.chargeableKm}</label></h5>
            </div>
        </div>
        <div>
            <label class="col-md-4 col-xs-12 control-label">Pickup</label>
            <div class="col-md-7 col-xs-12">
                <h5 class="h5-top-margin-7">
                    <label>${expenseVoucher.transportBooking.pickupLocation.destination}</label></h5>
            </div>
        </div>
        <div>
            <label class="col-md-4 col-xs-12 control-label">Delivery</label>
            <div class="col-md-7 col-xs-12">
                <h5 class="h5-top-margin-7">
                    <label>${expenseVoucher.transportBooking.deliveryLocation.destination}</label></h5>
            </div>
        </div>
        <div>
            <label class="col-md-4 col-xs-12 control-label">Via Locations</label>
            <div class="col-md-7 col-xs-12">
                <h5 class="h5-top-margin-7">
                    <label>${expenseVoucher.transportBooking.viaLocationString}</label></h5>
            </div>
        </div>
    </div>
    <div class="col-md-4 col-xs-12">
        <div>
            <label class="col-md-4 col-xs-12 control-label">Currency</label>
            <div class="col-md-7 col-xs-12">
                <h5 class="h5-top-margin-7">
                    <label>${expenseVoucher.currency.currencyCode}</label></h5>
            </div>
        </div>
        <div>
            <label class="col-md-4 col-xs-12 control-label">Total Amount</label>
            <div class="col-md-7 col-xs-12">
                <h5 class="h5-top-margin-7">
                    <label><fmt:formatNumber
                            value="${expenseVoucher.finalTotalAmount}" type="currency"
                            pattern="#,##0.00;(#,##0.00)"/></label></h5>
            </div>
        </div>
        <div>
            <label class="col-md-4 col-xs-12 control-label">Without Tax</label>
            <div class="col-md-7 col-xs-12">
                <h5 class="h5-top-margin-7">
                    <label><fmt:formatNumber
                            value="${expenseVoucher.finalWithoutTaxAmount}" type="currency"
                            pattern="#,##0.00;(#,##0.00)"/></label></h5>
            </div>
        </div>
        <div>
            <label class="col-md-4 col-xs-12 control-label">Total VAT</label>
            <div class="col-md-7 col-xs-12">
                <h5 class="h5-top-margin-7">
                    <label><fmt:formatNumber
                            value="${expenseVoucher.finalVatAmount}" type="currency"
                            pattern="#,##0.00;(#,##0.00)"/></label></h5>
            </div>
        </div>
        <div>
            <label class="col-md-4 col-xs-12 control-label">Total Other</label>
            <div class="col-md-7 col-xs-12">
                <h5 class="h5-top-margin-7">
                    <label><fmt:formatNumber
                            value="${expenseVoucher.finalOtherTaxAmount}" type="currency"
                            pattern="#,##0.00;(#,##0.00)"/></label></h5>
            </div>
        </div>
        <div>
            <label class="col-md-4 col-xs-12 control-label">Remarks</label>
            <div class="col-md-7 col-xs-12">
                <h5 class="h5-top-margin-7">
                    <label>${expenseVoucher.remark}</label></h5>
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
        <c:forEach items="${expenseVoucher.expenseVoucherChargeDetailList}" var="financialChargeDetail">
            <tr>
                <td>${financialChargeDetail.charge.chargeName}</td>
                <td>${financialChargeDetail.chargeValue}</td>
                <td>${financialChargeDetail.quantity}</td>
                <td><fmt:formatNumber
                        value="${financialChargeDetail.amount}"
                        type="currency"
                        pattern="#,##0.00#;(#,##0.00#-)"/></td>
                <td>
                    <c:forEach items="${financialChargeDetail.expenseVoucherChargeDetailTaxList}" var="expenseVoucherChargeDetailTax">
                        ${expenseVoucherChargeDetailTax.taxRegistration.taxName}(${expenseVoucherChargeDetailTax.taxRegistration.taxRateDisplay}) = <fmt:formatNumber value="${expenseVoucherChargeDetailTax.taxAmount}" type="currency" pattern="#,##0.00;(#,##0.00)"/> &nbsp;&nbsp;&nbsp;
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
        <label class="col-md-8">${expenseVoucher.createdBy}</label>
    </div>
    <div class="form-group">
        <label class="col-md-3 text-right">Created Date</label>
        <label class="col-md-8"><fmt:formatDate value="${expenseVoucher.createdDate}" pattern="yyyy-MM-dd hh:mm a"/></label>
    </div>
</div>
<div class="col-md-6">
    <div class="form-group">
        <label class="col-md-3 text-right">Last Modified By</label>
        <label class="col-md-8">${expenseVoucher.lastModifiedBy}</label>
    </div>
    <div class="form-group">
        <label class="col-md-3 text-right">Last Modified Date</label>
        <label class="col-md-8"><fmt:formatDate value="${expenseVoucher.lastModifiedDate}" pattern="yyyy-MM-dd hh:mm a"/></label>
    </div>
</div>

<input type="hidden" id="finalInvoice_expenseVoucherSeq" name="expenseVoucherSeq" value="${expenseVoucher.expenseVoucherSeq}"/>
<input type="hidden" id="finalInvoice_moduleSeq" name="moduleSeq" value="${expenseVoucher.moduleSeq}"/>