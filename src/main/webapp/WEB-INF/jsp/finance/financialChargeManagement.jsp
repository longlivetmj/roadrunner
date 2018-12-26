<%@ include file="/WEB-INF/jsp/include.jsp" %>
<script type="text/javascript" src="../../../customjs/finance/financialChargeManagement.js"></script>
<div class="page-title">
    <h3><span class="fa fa-road"></span>Financial Charge Management</h3>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default tabs">
            <ul class="nav nav-tabs tablist">
                <li class="active"><a href="#tab-first" class="tab" role="tab" data-toggle="tab">Financial Charge Management</a></li>
            </ul>
            <div class="panel-body tab-content">
                <div class="tab-pane active" id="tab-first">
                    <div class="col-md-12">
                        <form class="form-horizontal financeChargeForm" method="post" name="create" id="create">
                            <input type="hidden"
                                   name="companyProfileSeq"
                                   id="create_companyProfileSeq"
                                   value="${financialCharge.companyProfileSeq}"
                                   rv-value="financialCharge.companyProfileSeq"/>
                            <input type="hidden"
                                   name="moduleSeq"
                                   id="create_moduleSeq"
                                   value="${financialCharge.moduleSeq}"
                                   rv-value="financialCharge.moduleSeq"/>
                            <input type="hidden"
                                   name="targetType"
                                   id="create_targetType"
                                   value="${financialCharge.targetType}"
                                   rv-value="financialCharge.targetType"/>
                            <input type="hidden"
                                   name="referenceType"
                                   id="create_referenceType"
                                   value="${financialCharge.referenceType}"
                                   rv-value="financialCharge.referenceType"/>
                            <input type="hidden"
                                   name="referenceNo"
                                   id="create_referenceNo"
                                   value="${financialCharge.referenceNo}"
                                   rv-value="financialCharge.referenceNo"/>
                            <input type="hidden"
                                   name="referenceSeq"
                                   id="create_referenceSeq"
                                   value="${financialCharge.referenceSeq}"
                                   rv-value="financialCharge.referenceSeq"/>
                            <input type="hidden"
                                   name="financialChargeSeq"
                                   id="create_financialChargeSeq"
                                   value="${financialCharge.financialChargeSeq}"
                                   rv-value="financialCharge.financialChargeSeq"/>
                            <input type="hidden"
                                   name="status"
                                   id="create_status"
                                   value="${financialCharge.status}"
                                   rv-value="financialCharge.status"/>

                            <div class="col-md-12">
                                <div class="col-md-4">
                                    <div>
                                        <label class="col-md-4 col-xs-12 control-label">Request Id</label>
                                        <div class="col-md-7 col-xs-12">
                                            <h5 class="h5-top-margin-7">
                                                <label>${booking.bookingNo}</label></h5>
                                        </div>
                                    </div>
                                    <div>
                                        <label class="col-md-4 col-xs-12 control-label">Vehicle Type</label>
                                        <div class="col-md-7 col-xs-12">
                                            <h5 class="h5-top-margin-7">
                                                <label>${booking.vehicleType.vehicleTypeName}</label></h5>
                                        </div>
                                    </div>
                                    <div>
                                        <label class="col-md-4 col-xs-12 control-label">Transporter</label>
                                        <div class="col-md-7 col-xs-12">
                                            <h5 class="h5-top-margin-7">
                                                <label>${booking.transportBookingVehicleList[0].transportCompany.stakeholderName}</label></h5>
                                        </div>
                                    </div>
                                    <div>
                                        <label class="col-md-4 col-xs-12 control-label">Customer</label>
                                        <div class="col-md-7 col-xs-12">
                                            <h5 class="h5-top-margin-7">
                                                <label>${booking.customer.stakeholderName}</label></h5>
                                        </div>
                                    </div>
                                    <div>
                                        <label class="col-md-4 col-xs-12 control-label">Chargeable Km</label>
                                        <div class="col-md-7 col-xs-12">
                                            <h5 class="h5-top-margin-7">
                                                <label>${booking.transportBookingFeedback.chargeableKm}</label></h5>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <div>
                                        <label class="col-md-4 col-xs-12 control-label">Job No</label>
                                        <div class="col-md-7 col-xs-12">
                                            <h5 class="h5-top-margin-7">
                                                <label>${booking.job.jobNo}</label></h5>
                                        </div>
                                    </div>
                                    <div>
                                        <label class="col-md-4 col-xs-12 control-label">Shipper</label>
                                        <div class="col-md-7 col-xs-12">
                                            <h5 class="h5-top-margin-7">
                                                <label>${booking.shipper.stakeholderName}</label></h5>
                                        </div>
                                    </div>
                                    <div >
                                        <label class="col-md-4 col-xs-12 control-label">Vehicle</label>
                                        <div class="col-md-7 col-xs-12">
                                            <h5 class="h5-top-margin-7">
                                                <label>${booking.transportBookingVehicleList[0].vehicle.vehicleNo}</label></h5>
                                        </div>
                                    </div>
                                    <div>
                                        <label class="col-md-4 col-xs-12 control-label">Pickup</label>
                                        <div class="col-md-7 col-xs-12">
                                            <h5 class="h5-top-margin-7">
                                                <label>${booking.pickupLocation.destination}</label></h5>
                                        </div>
                                    </div>
                                    <div>
                                        <label class="col-md-4 col-xs-12 control-label">Pickup</label>
                                        <div class="col-md-7 col-xs-12">
                                            <h5 class="h5-top-margin-7">
                                                <label><fmt:formatDate value="${booking.transportBookingFeedback.arrivedAtPickup}"
                                                                       pattern="yyyy-MM-dd hh:mm a"/></label></h5>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <div>
                                        <label class="col-md-4 col-xs-12 control-label">Customer Ref</label>
                                        <div class="col-md-7 col-xs-12">
                                            <h5 class="h5-top-margin-7">
                                                <label>${booking.customerReferenceNo}</label></h5>
                                        </div>
                                    </div>
                                    <div>
                                        <label class="col-md-4 col-xs-12 control-label">Delivery</label>
                                        <div class="col-md-7 col-xs-12">
                                            <h5 class="h5-top-margin-7">
                                                <label>${booking.deliveryLocation.destination}</label></h5>
                                        </div>
                                    </div>
                                    <div>
                                        <label class="col-md-4 col-xs-12 control-label">Delivery</label>
                                        <div class="col-md-7 col-xs-12">
                                            <h5 class="h5-top-margin-7">
                                                <label><fmt:formatDate value="${booking.transportBookingFeedback.arrivedAtDelivery}"
                                                                       pattern="yyyy-MM-dd hh:mm a"/></label></h5>
                                        </div>
                                    </div>
                                    <div>
                                        <label class="col-md-4 col-xs-12 control-label">Via Locations</label>
                                        <div class="col-md-7 col-xs-12">
                                            <h5 class="h5-top-margin-7">
                                                <label>${booking.viaLocationString}</label></h5>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="panel panel-success">
                                <div class="panel-heading">
                                    <h3 class="panel-title">Charges</h3>
                                    <div style="float: right">
                                        <lable>No of Rows :</lable>
                                        <input type="text" id="noOfRows" value="1" style="width: 30px;"/>
                                        <button type="button"
                                                class="transparent-btn fa fa-plus-square fa-2x"
                                                onclick="add_table_row('financialChargeDetailOperator',this, 'noOfRows')"></button>
                                        <button type="button"
                                                class="transparent-btn fa fa-minus-square fa-2x"
                                                onclick="delete_table_row('financialChargeDetailOperator',this)"></button>
                                    </div>
                                </div>
                                <div class="panel-body">
                                    <div class="able-striped" style="font-size: 7px">
                                        <table class="financialChargeDetailOperator auto-grow-table table table-bordered table-striped table-actions"
                                               border="1" width="100%">
                                            <thead>
                                            <tr>
                                                <th width="3%"></th>
                                                <th width="10%">Pay.Type</th>
                                                <th width="30%">Charge Type</th>
                                                <th width="10%">Value</th>
                                                <th width="10%">Currency</th>
                                                <th width="10%">Qty</th>
                                                <th width="15%">Amount</th>
                                                <th width="10%">Unit</th>
                                                <th width="2%" class="hidden"></th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:set var="usedFc" value="${0}"/>
                                            <c:if test="${(fn:length(financialCharge.financialChargeDetails)) > 0}">
                                                <c:set var="index" value="${0}"/>
                                                <c:forEach items="${financialCharge.financialChargeDetails}"
                                                           var="financialChargeDetail">
                                                    <tr rv-each-tmp="financialCharge.financialChargeDetails | isNotEmpty">
                                                        <td>
                                                            <input type="checkbox" name="chk"
                                                                    <c:if test="${financialChargeDetail.evStatus ne null and financialChargeDetail.liStatus ne null}">
                                                                        <c:if test="${financialChargeDetail.evStatus ne 1 or financialChargeDetail.liStatus ne 1 }">
                                                                            disabled="disabled"
                                                                            <c:set var="usedFc" value="${1}"/>
                                                                        </c:if>
                                                                    </c:if>
                                                            />
                                                        </td>
                                                        <td class="form-group">
                                                            <select name="financialChargeDetails[${index}].chargeType"
                                                                    rv-value="tmp.chargeType"
                                                                    class="form-control select"
                                                                    id="chargeType${index}"
                                                                    data-live-search="true">
                                                                <c:forEach items="${chargeTypeList}"
                                                                           var="chargeType">
                                                                    <option value="${chargeType.chargeType}"
                                                                        ${financialChargeDetail.chargeType eq chargeType.chargeType ? 'selected':''}>${chargeType.chargeTypeDiscription}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </td>
                                                        <td class="form-group">
                                                            <select name="financialChargeDetails[${index}].chargeSeq"
                                                                    id="chargeSeq${index}"
                                                                    class="form-control ajax-select"
                                                                    data-validate="true"
                                                                    data-abs-ajax-type="get"
                                                                    data-abs-request-delay="500"
                                                                    title="Required, Charge Type"
                                                                    data-key="chargeSeq"
                                                                    data-value="chargeName"
                                                                    data-abs-ajax-url="finance/financialChargeManagement/findCharge"
                                                                    data-live-search="true"
                                                                    required>
                                                                <option rv-value="tmp.chargeSeq"
                                                                        rv-text="tmp.charge.chargeName"
                                                                        rv-setselectedattr="tmp.chargeSeq"
                                                                        value="${financialChargeDetail.chargeSeq}"
                                                                    ${financialChargeDetail.chargeSeq eq financialChargeDetail.charge.chargeSeq ? 'selected':''}>${financialChargeDetail.charge.chargeName}</option>
                                                            </select>
                                                        </td>
                                                        <td class="form-group">
                                                            <input type="text" class="form-control"
                                                                   id="chargeValue${index}"
                                                                   rv-value="tmp.chargeValue"
                                                                   required
                                                                   name="financialChargeDetails[${index}].chargeValue"
                                                                   value="${financialChargeDetail.chargeValue}"
                                                                   onchange="validateFloatKeyPress(this,4)"
                                                                   onkeyup="calculate_charge_amount('financialChargeDetails',this)"
                                                                   maxlength="10"/>
                                                        </td>
                                                        <td class="form-group">
                                                            <select name="financialChargeDetails[${index}].currencySeq"
                                                                    id="currencySeq${index}"
                                                                    class="form-control ajax-select"
                                                                    data-validate="true"
                                                                    data-abs-ajax-type="get"
                                                                    data-abs-request-delay="500"
                                                                    required
                                                                    data-key="currencySeq"
                                                                    data-value="currencyCode"
                                                                    data-abs-ajax-url="finance/financialChargeManagement/findCurrency"
                                                                    data-live-search="true"
                                                                    title="Required, Currency">
                                                                <option rv-value="tmp.currencySeq"
                                                                        rv-text="tmp.currency.currencyCode"
                                                                        rv-setselectedattr="tmp.currencySeq"
                                                                        value="${financialChargeDetail.currency.currencySeq}"
                                                                    ${financialChargeDetail.currencySeq eq financialChargeDetail.currency.currencySeq ? 'selected':''}>${financialChargeDetail.currency.currencyCode}</option>
                                                            </select>
                                                        </td>
                                                        <td class="form-group">
                                                            <input type="text" class="form-control"
                                                                   id="quantity${index}"
                                                                   rv-value="tmp.quantity"
                                                                   required
                                                                   name="financialChargeDetails[${index}].quantity"
                                                                   value="${financialChargeDetail.quantity}"
                                                                   onchange="validateFloatKeyPress(this,4)"
                                                                   style="min-width: 40px"
                                                                   onkeyup="calculate_charge_amount('financialChargeDetails',this)"
                                                                   maxlength="10"/>
                                                        </td>
                                                        <td class="form-group">
                                                            <input type="text" class="form-control"
                                                                   id="amount${index}"
                                                                   rv-value="tmp.amount"
                                                                   required
                                                                   name="financialChargeDetails[${index}].amount"
                                                                   value="${financialChargeDetail.amount}"
                                                                   style="min-width: 40px"
                                                                   maxlength="10"/>
                                                        </td>
                                                        <td class="form-group">
                                                            <select name="financialChargeDetails[${index}].unitSeq"
                                                                    id="unitSeq${index}"
                                                                    class="form-control ajax-select"
                                                                    data-validate="true"
                                                                    data-abs-ajax-type="get"
                                                                    data-abs-request-delay="500"
                                                                    data-key="unitSeq"
                                                                    data-value="unitName"
                                                                    data-abs-ajax-url="finance/financialChargeManagement/findUnit"
                                                                    data-live-search="true"
                                                                    title="Required, Unit"
                                                                    required>
                                                                <option rv-value="tmp.unitSeq"
                                                                        rv-text="tmp.unit.unitName"
                                                                        rv-setselectedattr="tmp.unitSeq"
                                                                        value="${financialChargeDetail.unitSeq}"
                                                                    ${financialChargeDetail.unitSeq eq financialChargeDetail.unit.unitSeq ? 'selected':''}>${financialChargeDetail.unit.unitName}</option>
                                                            </select>
                                                        </td>
                                                        <td class="hidden"><input type="hidden"
                                                                                  rv-value="tmp.financialChargeDetailSeq"
                                                                                  name="financialChargeDetails[${index}].financialChargeDetailSeq"
                                                                                  value="${financialChargeDetail.financialChargeDetailSeq}"/>
                                                            <input type="hidden"
                                                                   rv-value="tmp.financialChargeSeq"
                                                                   name="financialChargeDetails[${index}].financialChargeSeq"
                                                                   value="${financialChargeDetail.financialChargeSeq}"/>
                                                            <input type="hidden"
                                                                   rv-value="tmp.status"
                                                                   name="financialChargeDetails[${index}].status"
                                                                   value="${financialChargeDetail.status}"/>
                                                        </td>
                                                    </tr>
                                                    <c:set var="index" value="${index+1}"/>
                                                </c:forEach>
                                            </c:if>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>

                                <div class="form-group updateOperation" style="display: none">
                                    <div class="col-md-12">
                                        <div class="col-md-6">
                                            <div class="col-md-12">
                                                <div class="form-group">
                                                    <label class="col-md-4 text-right">Created By :</label>
                                                    <label class="col-md-8"
                                                           rv-text="financialCharge.createdBy">${financialCharge.createdBy}</label>
                                                </div>
                                            </div>
                                            <div class="col-md-12">
                                                <div class="form-group">
                                                    <label class="col-md-4 text-right">Created Date :</label>
                                                    <label class="col-md-8"
                                                           rv-text="financialCharge.createdDate"><fmt:formatDate value="${financialCharge.createdDate}"
                                                                                                                 pattern="yyyy-MM-dd hh:mm a"/></label>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="col-md-6">
                                            <div class="col-md-12">
                                                <div class="form-group">
                                                    <label class="col-md-4 text-right">Last Modified By
                                                        :</label>
                                                    <label class="col-md-8"
                                                           rv-text="financialCharge.lastModifiedBy">${financialCharge.lastModifiedBy}</label>
                                                </div>
                                            </div>

                                            <div class="col-md-12">
                                                <div class="form-group">
                                                    <label class="col-md-4 text-right">Last Modified Date
                                                        :</label>
                                                    <label class="col-md-8"
                                                           rv-text="financialCharge.lastModifiedDate"><fmt:formatDate value="${financialCharge.lastModifiedDate}"
                                                                                                                      pattern="yyyy-MM-dd hh:mm a"/></label>
                                                </div>
                                            </div>
                                        </div>

                                    </div>
                                </div>
                                <div class="panel-footer">
                                    <div class="form-group createOperation removeFromModal">
                                        <div class="col-sm-18 col-sm-18">
                                            <button type="button" class="btn btn-primary pull-right"
                                                    value="create Financial Charge"
                                                    <sec:authorize
                                                            access="!hasRole('ROLE_finance@financialChargeManagement_CREATE')">
                                                        disabled="disabled"
                                                    </sec:authorize>
                                                    onclick="create_financial_charge()">Create
                                            </button>
                                        </div>

                                    </div>

                                    <div class="form-group updateOperation" style="display: none">
                                        <div class="col-sm-2">
                                            <button type="button" class="btn btn-success pull-left"
                                                    value="To Invoice"
                                                    <sec:authorize
                                                            access="!hasRole('ROLE_finance@localInvoice_VIEW')">
                                                        disabled="disabled"
                                                    </sec:authorize>
                                                    onclick="create_new_local_invoice()">CREATE INVOICE
                                            </button>
                                        </div>

                                        <div class="col-sm-2">
                                            <button type="button" class="btn btn-success pull-left"
                                                    value="To EV"
                                                    <sec:authorize
                                                            access="!hasRole('ROLE_finance@expenseVoucher_VIEW')">
                                                        disabled="disabled"
                                                    </sec:authorize>
                                                    onclick="create_new_expense_voucher()"
                                            >CREATE EV
                                            </button>
                                        </div>
                                        <div class="col-sm-2 removeFromModal">
                                            <sec:authorize
                                                    access="hasRole('ROLE_finance@financialChargeManagement_DELETE')">
                                                <button type="button" class="btn btn-danger pull-right"
                                                        value="Update Finance Charge"
                                                        <c:if test="${usedFc eq 1}">
                                                            disabled="disabled"
                                                        </c:if>
                                                        onclick="delete_all_finance_charge()">Delete All
                                                </button>
                                            </sec:authorize>
                                        </div>
                                        <div class="col-sm-2 removeFromModal">
                                        </div>
                                        <div class="col-sm-2 removeFromModal">
                                            <button type="button" class="btn btn-primary pull-right"
                                                    value="Update Finance Charge"
                                                    <sec:authorize
                                                            access="!hasRole('ROLE_finance@financialChargeManagement_UPDATE')">
                                                        disabled="disabled"
                                                    </sec:authorize>
                                                    <c:if test="${financialChargeDetail.evStatus ne null and financialChargeDetail.liStatus ne null}">
                                                        <c:if test="${financialChargeDetail.evStatus ne 1 or financialChargeDetail.liStatus ne 1 }">
                                                            disabled="disabled"
                                                        </c:if>
                                                    </c:if>
                                                    onclick="update_finance_charge()">Update
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>