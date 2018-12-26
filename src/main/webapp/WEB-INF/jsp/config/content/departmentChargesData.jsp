<%@ include file="/WEB-INF/jsp/include.jsp" %>
<div class="panel panel-default">
    <div class="panel-heading">
        <h3 class="panel-title">Department wise Charges</h3>
        <div style="float: right">
            <button type="button"
                    class="transparent-btn fa fa-plus-square fa-2x"
                    onclick="add_table_row('departmentWiseCharges',this)"></button>
            <button type="button"
                    class="transparent-btn fa fa-minus-square fa-2x"
                    onclick="delete_table_row('departmentWiseCharges',this)"></button>
        </div>
    </div>
    <div class="panel-body panel-body-table">
        <div class="able-striped">
            <table class="departmentWiseCharges auto-grow-table table table-bordered table-striped table-actions"
                   border="1" width="100%">
                <thead>
                <tr>
                    <th width="3%"></th>
                    <th width="17%">Charge Name</th>
                    <th width="12%">Charge Type</th>
                    <th width="12%">Reference Type</th>
                    <th width="15%">Currency</th>
                    <th width="10%">Unit</th>
                    <th width="5%">Quantity</th>
                    <th width="16">Amount</th>
                    <th width="5%">Order</th>
                </tr>
                </thead>
                <tbody>
                <c:set var="counter" value="0"/>
                <c:forEach items="${departmentCharges}" var="departmentCharge">
                    <tr>
                        <td>
                            <input type="checkbox" name="chk"/>
                            <input type="hidden"
                                   name="departmentCharges[${counter}].departmentChargeSeq"
                                   value="${departmentCharge.departmentChargeSeq != null ? departmentCharge.departmentChargeSeq : null}"/>
                        </td>
                        <td>
                            <select name="departmentCharges[${counter}].chargeSeq"
                                    class="form-control select"
                                    id="chargeName0"
                                    data-live-search="true"
                                    data-validate="true" required
                                    data-error="Please Select a Charge Name"
                                    aria-required="true" style="width: 90%">
                                <option value="">Select</option>
                                <c:forEach items="${chargeTypes}" var="chargeType">
                                    <option value="${chargeType.chargeSeq}" ${departmentCharge.chargeSeq == chargeType.chargeSeq ? 'selected': ''}>${chargeType.chargeName}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td>
                            <select name="departmentCharges[${counter}].chargeType"
                                    class="form-control select"
                                    id="chargeType0"
                                    data-live-search="true"
                                    data-validate="true" required
                                    data-error="Please Select a Charge Type"
                                    aria-required="true" style="width: 90%">
                                <c:forEach items="${chargeTypeList}"
                                           var="chargeType">
                                    <option value="${chargeType.chargeType}" ${departmentCharge.chargeType == chargeType.chargeType ? 'selected' : ''}>${chargeType.chargeTypeDiscription}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td>
                            <select name="departmentCharges[${counter}].referenceType"
                                    class="form-control select"
                                    id="referenceType0"
                                    data-live-search="true"
                                    data-validate="true" required
                                    data-error="Please Select a Reference Type"
                                    aria-required="true" style="width: 90%">
                                <c:forEach items="${referenceTypeList}"
                                           var="referenceType">
                                    <option value="${referenceType.referenceType}" ${departmentCharge.referenceType == referenceType.referenceType ? 'selected': ''}>${referenceType.referenceTypeDescription}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td>
                            <select name="departmentCharges[${counter}].currencySeq"
                                    class="form-control select"
                                    id="currencySeq0"
                                    data-live-search="true"
                                    data-validate="true" required
                                    data-error="Please Select Currency"
                                    aria-required="true" style="width: 90%">
                                <option value="">Select</option>
                                <c:forEach items="${currencyList}"
                                           var="currency">
                                    <option value="${currency.currencySeq}" ${departmentCharge.currencySeq == currency.currencySeq ? 'selected': ''}>${currency.currencyName}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td>
                            <select name="departmentCharges[${counter}].unitSeq"
                                    class="form-control select"
                                    id="unitSeq0"
                                    data-live-search="true"
                                    data-validate="true" required
                                    data-error="Please Select Container Operator"
                                    aria-required="true" style="width: 90%">
                                <option value="">Select</option>
                                <c:forEach items="${unitList}"
                                           var="unit">
                                    <option value="${unit.unitSeq}" ${departmentCharge.unitSeq == unit.unitSeq ? 'selected': ''}>${unit.unitName}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td>
                            <input name="departmentCharges[${counter}].quantity"
                                   class="form-control select"
                                   id="quantity0"
                                   data-live-search="true"
                                   data-validate="true" required
                                   data-error="Please enter a Quantity"
                                   aria-required="true"
                                   value="${departmentCharge.quantity}"
                                   type="text">
                        </td>
                        <td>
                            <input name="departmentCharges[${counter}].amount"
                                   class="form-control select"
                                   id="amount0"
                                   data-live-search="true"
                                   data-validate="true" required
                                   data-error="Please enter a Amount"
                                   aria-required="true"
                                   value="${departmentCharge.amount}"
                                   type="text">
                        </td>
                        <td>
                            <input name="departmentCharges[${counter}].defaultOrder"
                                   class="form-control select"
                                   id="defaultOrder0"
                                   data-live-search="true"
                                   data-validate="true"
                                   aria-required="true"
                                   value="${departmentCharge.defaultOrder}"
                                   type="text">
                        </td>
                    </tr>
                    <c:set var="counter" value="${counter + 1}"/>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <div class="panel-footer">
        <button type="button"
                id="saveData"
                class="button btn-primary pull-right"
                value="Submit Department Charges"
                <sec:authorize
                        access="!hasRole('ROLE_config@departmentManagement_UPDATE')">
                    disabled="disabled"
                </sec:authorize>
                onclick="submit_department_charges()">Save</button>
    </div>
</div>