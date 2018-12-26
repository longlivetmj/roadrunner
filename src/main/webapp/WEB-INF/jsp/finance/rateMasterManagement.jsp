<%@ include file="/WEB-INF/jsp/include.jsp" %>
<script type="text/javascript" src="../../../customjs/finance/rateMasterManagement.js"></script>
<div class="page-title">
    <h3><span class="fa fa-user"></span>Rate Master Information</h3>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default tabs">
            <ul class="nav nav-tabs" role="tablist">
                <c:set var="tabIndex" value="1"/>
                <c:forEach items="${rateMasterList}" var="rateMaster">
                    <c:if test="${tabIndex eq 1}">
                        <li class="active"><a href="#tab-${tabIndex}" role="tab" data-toggle="tab">New</a></li>
                    </c:if>
                    <c:if test="${tabIndex ne 1}">
                        <li><a href="#tab-${tabIndex}" role="tab" data-toggle="tab">${rateMaster.charge.chargeName}</a>
                        </li>
                    </c:if>
                    <c:set var="tabIndex" value="${tabIndex + 1}"/>
                </c:forEach>
            </ul>
            <div class="panel-body tab-content">
                <c:set var="index" value="1"/>
                <c:forEach items="${rateMasterList}" var="rateMaster">
                    <div class="tab-pane <c:if test="${index eq 1}">active</c:if>" id="tab-${index}">
                        <div class="col-md-12">
                            <form class="form-horizontal rateMasterForm" method="post" id="rateMasterForm${index}">
                                <div class="form-group">
                                    <div class="col-md-7">
                                        <h4 class="subTitle">Rate Master for ${stakeholder.stakeholderName}
                                        </h4>
                                    </div>
                                </div>

                                <input type="hidden"
                                       id="companyProfileSeq${index}"
                                       rv-value="rateMaster.companyProfileSeq"
                                       name="companyProfileSeq"
                                       value="${rateMaster.companyProfileSeq}"/>

                                <input type="hidden"
                                       name="stakeholderSeq"
                                       rv-value="rateMaster.stakeholderSeq"
                                       id="stakeholderSeq${index}"
                                       value="${rateMaster.stakeholderSeq}"/>

                                <input type="hidden"
                                       name="rateMasterSeq"
                                       rv-value="rateMaster.rateMasterSeq"
                                       id="rateMasterSeq${index}"
                                       value="${rateMaster.rateMasterSeq}"/>

                                <div class="panel panel-warning">
                                    <div class="panel-heading collapsed">
                                        <h3 class="panel-title">Rate Information</h3>
                                    </div>
                                    <div class="panel-body">
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <label class="col-md-4 col-xs-12 control-label">Charge</label>
                                                <div class="col-md-7 col-xs-12">
                                                    <select name="chargeSeq"
                                                            id="chargeSeq${index}"
                                                            class="form-control ajax-select"
                                                            data-abs-ajax-type="get"
                                                            data-abs-request-delay="500"
                                                            data-key="chargeSeq"
                                                            data-value="chargeName"
                                                            data-abs-ajax-url="finance/rateMasterManagement/findCharge"
                                                            data-live-search="true"
                                                            title="Required, Charge"
                                                            required>
                                                        <option value="${rateMaster.chargeSeq}"
                                                            ${rateMaster.chargeSeq ne null ? 'selected' : ''}>
                                                                ${rateMaster.charge.chargeName}
                                                        </option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-4 col-xs-12 control-label">Charge Type</label>
                                                <div class="col-md-7 col-xs-12">
                                                    <select name="chargeType"
                                                            id="chargeType${index}"
                                                            class="form-control select"
                                                            aria-required="true"
                                                            placeholder="Charge Type"
                                                            title="Charge Type"
                                                            required>
                                                        <c:forEach items="${chargeTypeList}" var="chargeType">
                                                            <option value="${chargeType.chargeType}"
                                                                ${chargeType.chargeType eq rateMaster.chargeType ? 'selected' : ''}>
                                                                    ${chargeType.chargeTypeDiscription}
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-4 col-xs-12 control-label">Shippers</label>
                                                <div class="col-md-7 col-xs-12">
                                                    <select name="shipperSeq[]" id="shipperSeq${index}"
                                                            class="form-control ajax-select"
                                                            data-validate="true"
                                                            data-shipper-consignee
                                                            data-abs-ajax-type="get"
                                                            data-abs-request-delay="500"
                                                            data-key="stakeholderSeq"
                                                            data-value="stakeholderName"
                                                            data-subText="stakeholderCode"
                                                            data-abs-ajax-url="finance/rateMasterManagement/findShipper"
                                                            data-live-search="true" multiple>
                                                        <c:forEach items="${rateMaster.rateMasterStakeholderList}" var="rateMasterStakeholder">
                                                            <option value="${rateMasterStakeholder.stakeholderSeq}" selected >
                                                                    ${rateMasterStakeholder.stakeholder.stakeholderName}
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-4 col-xs-12 control-label">Threshold</label>
                                                <div class="col-md-4 col-xs-12">
                                                    <input type="text"
                                                           class="form-control"
                                                           name="thresholdValue"
                                                           id="thresholdValue${index}"
                                                           rv-value="rateMaster.thresholdValue"
                                                           value="${rateMaster.thresholdValue}"
                                                           maxlength="50"
                                                           title="Required, Threshold"
                                                           required/>
                                                </div>
                                                <div class="col-md-3 col-xs-12">
                                                    <select name="multiplyType"
                                                            id="multiplyType${index}"
                                                            class="form-control select"
                                                            aria-required="true"
                                                            rv-value="rateMaster.multiplyType"
                                                            title="Multiply Type"
                                                            required>
                                                        <c:forEach items="${multiplyTypeList}" var="multiplyType">
                                                            <option value="${multiplyType.multiplyType}"
                                                                ${multiplyType.multiplyType eq rateMaster.multiplyType ? 'selected' : ''}>
                                                                    ${multiplyType.multiplyTypeDescription}
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-4 col-xs-12 control-label">Minimum</label>
                                                <div class="col-md-4 col-xs-12">
                                                    <input type="text"
                                                           class="form-control"
                                                           name="minimumCharge"
                                                           id="minimumCharge${index}"
                                                           rv-value="rateMaster.minimumCharge"
                                                           value="${rateMaster.minimumCharge}"
                                                           maxlength="50"
                                                           title="Required, Minimum Charge"
                                                           required/>
                                                </div>
                                                <div class="col-md-3 col-xs-12">
                                                    <select name="currencySeq"
                                                            id="currencySeq${index}"
                                                            class="form-control ajax-select"
                                                            data-abs-ajax-type="get"
                                                            data-abs-request-delay="500"
                                                            data-key="currencySeq"
                                                            data-value="currencyCode"
                                                            data-abs-ajax-url="finance/rateMasterManagement/findCurrency"
                                                            data-live-search="true"
                                                            title="Required, Currency"
                                                            required>
                                                        <option rv-value="rateMaster.currencySeq"
                                                                rv-text="rateMaster.currency.currencyCode"
                                                                rv-setselectedattr="rateMaster.currencySeq"
                                                                value="${rateMaster.currencySeq}"
                                                            ${rateMaster.currencySeq ne null ? 'selected' : ''}>
                                                                ${rateMaster.currency.currencyCode}
                                                        </option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-4 col-xs-12 control-label">Status</label>
                                                <div class="col-md-7 col-xs-12">
                                                    <select name="status"
                                                            id="status${index}"
                                                            class="form-control select"
                                                            rv-value="rateMaster.status"
                                                            aria-required="true"
                                                            title="Status"
                                                            required>
                                                        <c:forEach items="${statusList}" var="status">
                                                            <option value="${status.statusSeq}"
                                                                ${status.statusSeq eq rateMaster.status ? 'selected' : ''}>
                                                                    ${status.status}
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-8">
                                            <div class="panel-heading">
                                                <h3 class="panel-title">Rate Breakdown</h3>
                                                <div style="float: right">
                                                    <lable>No of Rows :</lable>
                                                    <input type="text" id="noOfRows${index}" value="1"
                                                           style="width: 30px;"/>
                                                    <button type="button"
                                                            class="transparent-btn fa fa-plus-square fa-2x"
                                                            onclick="add_table_row('rateBreakdownTable${index}',this, 'noOfRows${index}')"></button>
                                                    <button type="button"
                                                            class="transparent-btn fa fa-minus-square fa-2x"
                                                            onclick="delete_table_row('rateBreakdownTable${index}',this)"></button>
                                                </div>
                                            </div>
                                            <div class="panel-body">
                                                <table class="rateBreakdownTable${index} auto-grow-table table table-bordered table-striped table-actions"
                                                       border="1" width="100%">
                                                    <thead>
                                                    <tr>
                                                        <th width="3%"></th>
                                                        <th width="10%">Rate Type</th>
                                                        <th width="20%">Type</th>
                                                        <th width="17%">Rate</th>
                                                        <th width="25%">Pick</th>
                                                        <th width="25%">Delivery</th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <c:set var="rateMasterDetailIndex" value="${0}"/>
                                                    <c:forEach items="${rateMaster.rateMasterDetailList}"
                                                               var="rateMasterDetail">
                                                        <tr>
                                                            <td>
                                                                <input type="checkbox" name="chk"/>
                                                                <input type="hidden"  name="rateMasterDetailList[${rateMasterDetailIndex}].rateMasterDetailSeq"
                                                                       value="${rateMasterDetail.rateMasterDetailSeq}"/>
                                                                <input type="hidden"  name="rateMasterDetailList[${rateMasterDetailIndex}].rateMasterSeq"
                                                                       value="${rateMasterDetail.rateMasterSeq}"/>
                                                            </td>
                                                            <td>
                                                                <select name="rateMasterDetailList[${rateMasterDetailIndex}].rateType"
                                                                        class="form-control select"
                                                                        id="rateType_${index}_${rateMasterDetailIndex}"
                                                                        data-live-search="true">
                                                                    <c:forEach items="${rateTypeList}"
                                                                               var="rateType">
                                                                        <option value="${rateType.rateType}"
                                                                            ${rateType.rateType eq rateMasterDetail.rateType ? "selected":""}
                                                                        >${rateType.rateTypeDescription}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </td>
                                                            <td>
                                                                <select name="rateMasterDetailList[${rateMasterDetailIndex}].typeSeq"
                                                                        class="form-control select"
                                                                        id="typeSeq_${index}_${rateMasterDetailIndex}"
                                                                        data-live-search="true">
                                                                    <c:forEach items="${vehicleTypeList}"
                                                                               var="vehicleType">
                                                                        <option value="${vehicleType.vehicleTypeSeq}"
                                                                            ${vehicleType.vehicleTypeSeq eq rateMasterDetail.typeSeq ? "selected":""}>
                                                                                ${vehicleType.vehicleTypeName}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </td>
                                                            <td>
                                                                <input type="text" class="form-control"
                                                                       id="vehicleRate_${index}_${rateMasterDetailIndex}"
                                                                       name="rateMasterDetailList[${rateMasterDetailIndex}].rateValue"
                                                                       onchange="validateFloatKeyPress(this,2)"
                                                                       value="${rateMasterDetail.rateValue}"
                                                                       required
                                                                       maxlength="10"/>
                                                            </td>
                                                            <td>
                                                                <select name="rateMasterDetailList[${rateMasterDetailIndex}].pickupLocationSeq"
                                                                        id="pickupLocationSeq_${index}_${rateMasterDetailIndex}"
                                                                        class="form-control ajax-select"
                                                                        data-validate="true"
                                                                        data-abs-ajax-type="get"
                                                                        data-abs-request-delay="500"
                                                                        data-key="finalDestinationSeq"
                                                                        data-value="destination"
                                                                        data-subText="location.locationName"
                                                                        data-abs-ajax-url="finance/rateMasterManagement/findFinalDestination"
                                                                        data-live-search="true"
                                                                        placeholder="Pickup"
                                                                        title="Pickup">
                                                                    <option value="">None</option>
                                                                    <c:if test="${rateMasterDetail != null && rateMasterDetail.pickupLocationSeq != null}">
                                                                        <option value="${rateMasterDetail.pickupLocationSeq}" selected >
                                                                                ${rateMasterDetail.pickupLocation.destination}
                                                                        </option>
                                                                    </c:if>
                                                                </select>
                                                            </td>
                                                            <td>
                                                                <select name="rateMasterDetailList[${rateMasterDetailIndex}].deliveryLocationSeq"
                                                                        id="deliveryLocationSeq_${index}_${rateMasterDetailIndex}"
                                                                        class="form-control ajax-select"
                                                                        data-validate="true"
                                                                        data-abs-ajax-type="get"
                                                                        data-abs-request-delay="500"
                                                                        data-key="finalDestinationSeq"
                                                                        data-value="destination"
                                                                        data-subText="location.locationName"
                                                                        data-abs-ajax-url="finance/rateMasterManagement/findFinalDestination"
                                                                        data-live-search="true"
                                                                        placeholder="Delivery"
                                                                        title="Delivery">
                                                                    <option value="">None</option>
                                                                    <c:if test="${rateMasterDetail != null && rateMasterDetail.deliveryLocationSeq != null}">
                                                                        <option value="${rateMasterDetail.deliveryLocationSeq}" selected >
                                                                                ${rateMasterDetail.deliveryLocation.destination}
                                                                        </option>
                                                                    </c:if>
                                                                </select>
                                                            </td>
                                                        </tr>
                                                        <c:set var="rateMasterDetailIndex" value="${rateMasterDetailIndex+1}"/>
                                                    </c:forEach>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <c:if test="${rateMaster.rateMasterSeq eq null}">
                                <div class="form-group">
                                    <div class="col-sm-offset-3 col-sm-8">
                                        <button type="button"
                                                class="btn btn-primary pull-right createOperation"
                                                value="create Rate Master"
                                                <sec:authorize
                                                        access="!hasRole('ROLE_finance@rateMasterManagement_CREATE')">
                                                    disabled="disabled"
                                                </sec:authorize>
                                                onclick="create_rate_master_information('rateMasterForm${index}')">Save
                                        </button>
                                    </div>
                                </div>
                                </c:if>
                                <c:if test="${rateMaster.rateMasterSeq ne null}">
                                <div class="form-group updateOperation">
                                    <div class="col-md-12 push-down-25">
                                        <div class="col-md-6">
                                            <div class="col-md-12">
                                                <div class="form-group">
                                                    <label class="col-md-4 text-right">Created By</label>
                                                    <label class="col-md-8"
                                                           rv-text="rateMaster.createdBy">${rateMaster.createdBy}</label>
                                                </div>
                                            </div>
                                            <div class="col-md-12">
                                                <div class="form-group">
                                                    <label class="col-md-4 text-right">Created Date</label>
                                                    <label class="col-md-8"
                                                           rv-text="rateMaster.createdDate"><fmt:formatDate value='${rateMaster.createdDate}' pattern='yyyy-MM-dd hh:mm a'/></label>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="col-md-12">
                                                <div class="form-group">
                                                    <label class="col-md-4 text-right">Last Modified By</label>
                                                    <label class="col-md-8"
                                                           rv-text="rateMaster.lastModifiedBy">${rateMaster.lastModifiedBy}</label>
                                                </div>
                                            </div>
                                            <div class="col-md-12">
                                                <div class="form-group">
                                                    <label class="col-md-4 text-right">Last Modified Date</label>
                                                    <label class="col-md-8"
                                                           rv-text="rateMaster.lastModifiedDate"><fmt:formatDate value='${rateMaster.lastModifiedDate}' pattern='yyyy-MM-dd hh:mm a'/></label>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-sm-offset-2 col-sm-7 removeFromModal">
                                        <button type="button" class="btn btn-primary pull-right"
                                                value="Update Rate Master"
                                                <sec:authorize
                                                        access="!hasRole('ROLE_finance@rateMasterManagement_UPDATE')">
                                                    disabled="disabled"
                                                </sec:authorize>
                                                onclick="update_rate_master('rateMasterForm${index}')">Update
                                        </button>
                                    </div>
                                </div>
                                </c:if>
                            </form>
                        </div>
                    </div>
                    <c:set var="index" value="${index +1}"/>
                </c:forEach>
            </div>
        </div>
    </div>
</div>