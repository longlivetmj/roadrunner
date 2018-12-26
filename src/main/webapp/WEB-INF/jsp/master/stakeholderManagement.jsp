<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<script type="text/javascript" src="../../../customjs/master/stakeholderManagement.js"></script>
<div class="page-title">
    <h3><span class="fa fa-users"></span>Stakeholder Management</h3>
</div>
<!-- END PAGE TITLE -->
<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default tabs">
            <ul class="nav nav-tabs tablist">
                <li class="active"><a href="#tab-first" class="tab" role="tab" data-toggle="tab">Stakeholder
                    Creation</a></li>
                <li><a href="#tab-second" class="tab" role="tab" data-toggle="tab">Stakeholder Search</a></li>
            </ul>
            <div class="panel-body tab-content">
                <div class="tab-pane active" id="tab-first">
                    <div class="col-md-12">
                        <form class="form-horizontal stakeholderManagementForm" method="post" id="create">
                            <input type="hidden"
                                   id="companyProfileSeq"
                                   name="companyProfileSeq"
                                    rv-value="stakeholder.companyProfileSeq">
                            <div class="form-group removeFromModal">
                                <div class="col-md-10 push-down-20">
                                    <h4 class="subTitle">Stakeholder Details</h4>
                                </div>
                                <div class="col-md-2 operations" style="display: none">
                                    <button type="button" class="btn btn-success" onclick="new_form(formTemplate,'update','create')">
                                        New
                                    </button>
                                    <button type="button" class="btn btn-danger"
                                            <sec:authorize
                                                    access="!hasRole('ROLE_master@stakeholderManagement_DELETE')">
                                                disabled="disabled"
                                            </sec:authorize>
                                            data-toggle=confirmation
                                            data-btn-ok-label="Continue"
                                            data-btn-ok-icon="glyphicon glyphicon-share-alt"
                                            data-btn-ok-class="btn-success"
                                            data-btn-cancel-label="Cancel"
                                            data-btn-cancel-icon="glyphicon glyphicon-ban-circle"
                                            data-btn-cancel-class="btn-danger"
                                            data-title="Deleting Stakeholder"
                                            data-content="Are you sure you want to delete this stakeholder ? "
                                            data-id="modal"
                                            data-on-confirm="delete_stakeholder">
                                        Delete
                                    </button>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="panel panel-danger">
                                    <div class="panel-heading">
                                        <h3 class="panel-title">Stakeholder Type</h3>
                                    </div>
                                    <div class="panel-body">
                                        <div class="form-group">
                                            <div class="col-md-12">
                                                <div class="form-group stakeholderType" style="font-size: xx-small">
                                                    <c:set var="counter" value="0"/>
                                                    <c:forEach items="${stakeholderTypeList}" var="stakeholderType">
                                                        <div class="col-md-4">
                                                            <label>
                                                                <input type="checkbox" class="CB${stakeholderType.stakeholderTypeSeq}"
                                                                        <sec:authorize
                                                                                access="!hasRole('ROLE_master@stakeholderManagement_${stakeholderType.stakeholderTypeCode}')">
                                                                            readonly
                                                                        </sec:authorize>
                                                                       name="stakeholderTypeMappings[${counter}].stakeholderTypeSeq"
                                                                       id="stakeholderTypeMappings${stakeholderType.stakeholderTypeSeq}"
                                                                       value="${stakeholderType.stakeholderTypeSeq}"/>
                                                                ${stakeholderType.stakeholderTypeName}
                                                            </label>
                                                        </div>
                                                        <c:set var="counter" value="${counter +1}"/>
                                                    </c:forEach>
                                                    <div class="col-md-4">
                                                    <span class="help-block">Required, Stakeholder Type</span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="panel panel-success">
                                    <div class="panel-heading">
                                        <h3 class="panel-title">Stakeholder Basic Details</h3>
                                    </div>
                                    <div class="panel-body">
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Stakeholder ID</label>
                                            <div class="col-md-8 col-xs-12">
                                                <input type="text"
                                                       class="form-control"
                                                       readonly
                                                       id="create_stakeholderSeq"
                                                       rv-value="stakeholder.stakeholderSeq"/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Stakeholder Name</label>
                                            <div class="col-md-8 col-xs-12">
                                                <input type="text" class="form-control " id="stakeholderName"  rv-value="stakeholder.stakeholderName"
                                                       name="stakeholderName" required maxlength="100"/>
                                                <span class="help-block">Required, Stakeholder Name</span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Stakeholder Code</label>
                                            <div class="col-md-8 col-xs-12">
                                                <input type="text" class="form-control " id="stakeholderCode"  rv-value="stakeholder.stakeholderCode"
                                                       name="stakeholderCode" required maxlength="25">
                                                <span class="help-block">Required, Stakeholder Code</span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Stakeholder Group</label>
                                            <div class="col-md-8 col-xs-12">
                                                <select name="stakeholderGroupSeq"
                                                        id="stakeholderGroupSeq"
                                                        rv-setselected="stakeholder.stakeholderGroupSeq"
                                                        class="form-control ajax-select"
                                                        data-validate="true"
                                                        data-abs-ajax-type="get"
                                                        data-abs-request-delay="500"
                                                        data-key="customerGroupSeq"
                                                        data-value="customerGroupName"
                                                        data-subText="customerGroupCode"
                                                        title="Select and begin typing"
                                                        data-abs-ajax-url="master/stakeholderManagement/findStakeHolderGroup"
                                                        data-live-search="true">
                                                    <option rv-value="stakeholder.stakeholderGroupSeq"
                                                            rv-text="stakeholder.customerGroup.customerGroupName"
                                                            rv-setselectedattr="stakeholder.stakeholderGroupSeq"></option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Agent Network</label>
                                            <div class="col-md-8 col-xs-12">
                                                <select data-live-search="true"
                                                        name="agentNetworkSeq" id="agentNetworkSeq"
                                                        data-validate="true"
                                                        aria-required="true"
                                                        rv-setselected="stakeholder.agentNetworkSeq"
                                                        class="form-control ajax-select"
                                                        data-abs-ajax-type="get"
                                                        data-abs-request-delay="500"
                                                        data-key="agentNetworkSeq"
                                                        title="Select and begin typing"
                                                        data-value="agentNetworkName"
                                                        data-abs-ajax-url="master/stakeholderManagement/findAgentNetwork">
                                                    <option rv-value="stakeholder.agentNetworkSeq"
                                                            rv-text="stakeholder.agentNetwork.agentNetworkName"
                                                            rv-setselectedattr="stakeholder.agentNetworkSeq"></option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Global Stakeholder Code</label>
                                            <div class="col-md-8 col-xs-12">
                                                <input type="text" class="form-control text-uppercase" id="globalStakeholderCode"
                                                       name="globalStakeholderCode" maxlength="25" rv-value="stakeholder.globalStakeholderCode"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="panel panel-success">
                                    <div class="panel-heading">
                                        <h3 class="panel-title">Contact Details</h3>
                                    </div>
                                    <div class="panel-body">
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Address 1</label>
                                            <div class="col-md-8 col-xs-12">
                                                <input type="text"
                                                       class="form-control"
                                                       name="addressBook.address1"
                                                       required
                                                       id="address1"
                                                       rv-value="stakeholder.addressBook.address1"
                                                       maxlength="50"/>
                                                <span class="help-block">Required, Address1</span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Address 2</label>
                                            <div class="col-md-8 col-xs-12">
                                                <input type="text"
                                                       class="form-control"
                                                       name="addressBook.address2"
                                                       rv-value="stakeholder.addressBook.address2"
                                                       maxlength="100"
                                                       id="address2"/>
                                            </div>

                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">City</label>
                                            <div class="col-md-8 col-xs-12">
                                                <input type="text"
                                                       class="form-control"
                                                       name="addressBook.city"
                                                       rv-value="stakeholder.addressBook.city"
                                                       maxlength="50"
                                                       id="city"/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">State</label>
                                            <div class="col-md-8 col-xs-12">
                                                <input type="text"
                                                       class="form-control"
                                                       name="addressBook.state"
                                                       rv-value="stakeholder.addressBook.state"
                                                       maxlength="50"
                                                       id="state"/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Zip Code</label>
                                            <div class="col-md-8 col-xs-12">
                                                <input type="text"
                                                       class="form-control"
                                                       name="addressBook.zip"
                                                       maxlength="10"
                                                       rv-value="stakeholder.addressBook.zip"
                                                       required
                                                       id="zip"/>
                                                <span class="help-block">Required, Zip</span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 control-label">Country</label>
                                            <div class="col-md-8 col-xs-12">
                                                <select data-live-search="true"
                                                        name="addressBook.countrySeq"
                                                        id="countrySeq"
                                                        data-validate="true"
                                                        required
                                                        aria-required="true"
                                                        rv-setselected="stakeholder.addressBook.countrySeq"
                                                        class="form-control ajax-select"
                                                        data-abs-ajax-type="get"
                                                        data-abs-request-delay="500"
                                                        data-key="countrySeq"
                                                        data-value="countryName"
                                                        title="Required, Country"
                                                        data-abs-ajax-url="master/stakeholderManagement/findCountry">
                                                    <option rv-value="stakeholder.addressBook.countrySeq"
                                                            rv-text="stakeholder.addressBook.country.countryName"
                                                            rv-setselectedattr="stakeholder.addressBook.countrySeq"></option>
                                                </select>
                                                <span class="help-block"></span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Telephone</label>
                                            <div class="col-md-8 col-xs-12">
                                                <input type="text"
                                                       class="form-control"
                                                       name="addressBook.telephone"
                                                       rv-value="stakeholder.addressBook.telephone"
                                                       <%--pattern="[0-9]{10}|[0-9]{12}|[0-9]{15}"--%>
                                                       id="telephone"/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Fax</label>
                                            <div class="col-md-8 col-xs-12">
                                                <input type="text"
                                                       class="form-control"
                                                       name="addressBook.fax"
                                                       rv-value="stakeholder.addressBook.fax"
                                                       <%--pattern="[0-9]{10}|[0-9]{12}|[0-9]{15}"--%>
                                                       id="fax"/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Email</label>
                                            <div class="col-md-8 col-xs-12">
                                                <input type="text"
                                                       name="addressBook.email"
                                                       class="form-control"
                                                       rv-value="stakeholder.addressBook.email"
                                                       maxlength="512"
                                                       id="email"/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Web Site</label>
                                            <div class="col-md-8 col-xs-12">
                                                <input type="text"
                                                       name="addressBook.website"
                                                       class="form-control"
                                                       placeholder="http://"
                                                       rv-value="stakeholder.addressBook.website"
                                                       maxlength="512"
                                                       id="website"/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Air EMail</label>
                                            <div class="col-md-8 col-xs-12">
                                                <input type="text"
                                                       name="airEmail"
                                                       class="form-control"
                                                       rv-value="stakeholder.airEmail"
                                                       maxlength="512"
                                                       id="airEmail"/>
                                            </div>
                                        </div>
                                        <div class="form-group updateOperation" style="display: none">
                                            <div class="form-group">
                                                <label class="col-md-3 text-right">Created By</label>
                                                <label class="col-md-8" rv-text="stakeholder.createdBy"></label>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 text-right">Created Date</label>
                                                <label class="col-md-8" rv-text="stakeholder.createdDate"></label>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 text-right">Last Modified By</label>
                                                <label class="col-md-8" rv-text="stakeholder.lastModifiedBy"></label>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-md-3 text-right">Last Modified Date</label>
                                                <label class="col-md-8" rv-text="stakeholder.lastModifiedDate"></label>
                                            </div>
                                            <div class="col-sm-offset-2 col-sm-7 removeFromModal">
                                                <button type="button" class="btn btn-primary pull-right"
                                                        value="Update Stakeholder"
                                                        <sec:authorize
                                                                access="!hasRole('ROLE_master@stakeholderManagement_UPDATE')">
                                                            disabled="disabled"
                                                        </sec:authorize>
                                                        onclick="update_stakeholder()">Update
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="panel panel-success">
                                    <div class="panel-heading">
                                        <h3 class="panel-title">Financial Information</h3>
                                    </div>
                                    <div class="panel-body">
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Stakeholder Cash Type</label>
                                            <div class="col-md-8 col-xs-12">
                                               <select name="stakeholderCashTypeSeq"
                                                        id="stakeholderCashTypeSeq"
                                                        class="form-control select"
                                                        rv-value="stakeholder.stakeholderCashTypeSeq"
                                                        data-live-search="false"
                                                        data-validate="true">
                                                    <option value="">Select</option>
                                                    <c:forEach items="${stakeholderCashTypeList}" var="stakeholderCashType">
                                                        <option value="${stakeholderCashType.stakeholderCashTypeSeq}">${stakeholderCashType.stakeholderCashType}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Actual Outstanding Amount (Rs)</label>
                                            <div class="col-md-8 col-xs-12">
                                                <div class="input-group"></div>
                                                <input type="text" class="form-control "
                                                       id="actualOutStandingAmount"
                                                       rv-value="stakeholder.actualOutStandingAmount"
                                                       pattern="^[0-9]*(.[0-9]*)?([eE][-+][0-9]*)?$"
                                                       onchange="validateFloatKeyPress(this)"
                                                       name="actualOutStandingAmount">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Actual Credit Amount (Rs)</label>
                                            <div class="col-md-8 col-xs-12">
                                                <div class="input-group"></div>
                                                <input type="text" class="form-control "
                                                       id="actualCreditAmount"
                                                       rv-value="stakeholder.actualCreditAmount"
                                                       pattern="^[0-9]*(.[0-9]*)?([eE][-+][0-9]*)?$"
                                                       onchange="validateFloatKeyPress(this)"
                                                       maxlength="25"
                                                       name="actualCreditAmount">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Sales Person</label>
                                            <div class="col-md-8 col-xs-12">
                                                <select data-live-search="true"
                                                        name="salesPersonSeq"
                                                        id="salesPersonSeq"
                                                        data-validate="true"
                                                        rv-setselected="stakeholder.salesPersonSeq"
                                                        class="form-control ajax-select"
                                                        data-abs-ajax-type="get"
                                                        data-abs-request-delay="500"
                                                        title="Select and begin typing"
                                                        data-key="salesPersonSeq"
                                                        data-value="salesPersonName"
                                                        data-abs-ajax-url="master/stakeholderManagement/findSalesPerson">
                                                    <option rv-value="stakeholder.salesPersonSeq"
                                                            rv-text="stakeholder.salesPerson.salesPersonName"
                                                            rv-setselectedattr="stakeholder.salesPersonSeq"></option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Credit Type</label>
                                            <div class="col-md-8 col-xs-12">
                                                <div class="input-group"></div>
                                                 <select name="creditTypeSeq"
                                                        id="creditTypeSeq"
                                                        class="form-control select"
                                                        rv-value="stakeholder.creditTypeSeq"
                                                        data-live-search="false"
                                                        data-validate="true">
                                                    <option value="">Select</option>
                                                    <c:forEach items="${stakeholderCreditTypeList}" var="stakeholderCreditType">
                                                        <option value="${stakeholderCreditType.creditTypeSeq}">${stakeholderCreditType.creditTypeDescription}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Credit Amount (Rs)</label>
                                            <div class="col-md-8 col-xs-12">
                                                <div class="input-group"></div>
                                                <input type="text"
                                                       class="form-control "
                                                       id="creditAmount"
                                                       rv-value="stakeholder.creditAmount"
                                                       pattern="^[0-9]*(.[0-9]*)?([eE][-+][0-9]*)?$"
                                                       onchange="validateFloatKeyPress(this)"
                                                       name="creditAmount">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Tax (Tax Code)</label>
                                            <div class="col-md-8 col-xs-12">
                                                <select name="taxTypeSeq"
                                                        id="taxTypeSeq"
                                                        class="form-control select"
                                                        rv-value="stakeholder.taxTypeSeq"
                                                        data-live-search="false"
                                                        data-validate="true">
                                                    <option value="">Select</option>
                                                    <c:forEach items="${TaxTypeList}" var="TaxType">
                                                        <option value="${TaxType.taxTypeSeq}">${TaxType.taxTypeName}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Tax Reg. No</label>
                                            <div class="col-md-8 col-xs-12">
                                                <div class="input-group"></div>
                                                <input type="text"
                                                       class="form-control "
                                                       id="taxRegNo"
                                                       rv-value="stakeholder.taxRegNo"
                                                       name="taxRegNo"
                                                       maxlength="55"/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Company Reg. No</label>
                                            <div class="col-md-8 col-xs-12">
                                                <div class="input-group"></div>
                                                <input type="text" class="form-control " id="companyRegNo"
                                                       rv-value="stakeholder.companyRegNo"
                                                       name="companyRegNo" maxlength="50"/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">SVAT Stakeholder Type</label>
                                            <div class="col-md-8 col-xs-12">
                                                <select name="stakeholderSvatTypeSeq"
                                                        id="stakeholderSvatTypeSeq"
                                                        class="form-control select"
                                                        rv-value="stakeholder.stakeholderSvatTypeSeq"
                                                        data-live-search="false"
                                                        data-validate="true">
                                                    <option value="">Select</option>
                                                    <c:forEach items="${svalStakeholderTypeList}" var="svalStakeholderType">
                                                        <option value="${svalStakeholderType.stakeholderSvatTypeSeq}">${svalStakeholderType.stakeholderSvatType}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Suspended Tax Reg. No</label>
                                            <div class="col-md-8 col-xs-12">
                                                <div class="input-group"></div>
                                                <input type="text" class="form-control " id="suspendedTaxRegNo"
                                                       rv-value="stakeholder.suspendedTaxRegNo"
                                                       name="suspendedTaxRegNo" maxlength="50"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="panel panel-success">
                                    <div class="panel-heading">
                                        <h3 class="panel-title">Other Details</h3>
                                    </div>
                                    <div class="panel-body">
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Currency</label>
                                            <div class="col-md-8 col-xs-12">
                                                <select data-live-search="true"
                                                        name="currencySeq"
                                                        id="currencySeq"
                                                        rv-setselected="stakeholder.currencySeq"
                                                        data-validate="true"
                                                        required
                                                        aria-required="true"
                                                        class="form-control ajax-select"
                                                        data-abs-ajax-type="get"
                                                        data-abs-request-delay="500"
                                                        title="Required, Currency"
                                                        data-key="currencySeq"
                                                        data-value="currencyName"
                                                        data-abs-ajax-url="master/stakeholderManagement/findCurrency">
                                                    <option rv-value="stakeholder.currencySeq"
                                                            rv-text="stakeholder.currency.currencyName"
                                                            rv-setselectedattr="stakeholder.currencySeq"></option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">EU Number</label>
                                            <div class="col-md-8 col-xs-12">
                                                <div class="input-group"></div>
                                                <input type="text" class="form-control "
                                                       id="euNumber"
                                                       rv-value="stakeholder.euNumber"
                                                       name="euNumber"
                                                       maxlength="55">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Asycuda Code</label>
                                            <div class="col-md-8 col-xs-12">
                                                <div class="input-group col-md-12">
                                                    <input type="text" class="form-control "
                                                           id="asycudaCode"
                                                           rv-value="stakeholder.asycudaCode"
                                                           pattern="^[a-zA-Z0-9\s]+$"
                                                           name="asycudaCode"
                                                           maxlength="3"
                                                           data-error="Please Provide a Valid Asycuda Code"/>
                                                </div>
                                            </div>
                                            <div class="help-block with-errors"></div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Date Joined</label>
                                            <div class="col-md-8 col-xs-12">
                                                <div class="input-group col-md-12">
                                                    <input type="text" class="form-control datepicker" id="dateJoined"
                                                           rv-value="stakeholder.dateJoined" name="dateJoined"/>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Credit Limit</label>
                                            <div class="col-md-8 col-xs-12">
                                                <div class="input-group col-md-12">
                                                    <input type="text"
                                                           class="form-control "
                                                           id="creditLimit"
                                                           rv-value="stakeholder.creditLimit"
                                                           pattern="^[0-9]*(.[0-9]*)?([eE][-+][0-9]*)?$"
                                                           onchange="validateFloatKeyPress(this)"
                                                           name="creditLimit"
                                                           maxlength="10"
                                                           data-error="Please Provide a Valid Credit Limit"/>
                                                </div>
                                                <div class="help-block with-errors"></div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Credit Period</label>
                                            <div class="col-md-8 col-xs-12">
                                                <div class="input-group col-md-12">
                                                    <input type="text"
                                                           class="form-control "
                                                           id="creditPeriod"
                                                           rv-value="stakeholder.creditPeriod"
                                                           pattern="^[0-9]*(.[0-9]*)?([eE][-+][0-9]*)?$"
                                                           name="creditPeriod"
                                                           maxlength="10"
                                                           data-error="Please Provide a Valid Credit Period"/>
                                                </div>
                                                <div class="help-block with-errors"></div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Export Licence No</label>
                                            <div class="col-md-8 col-xs-12">
                                                <div class="input-group col-md-12">
                                                    <input type="text"
                                                           class="form-control "
                                                           id="exportLicenceNo"
                                                           rv-value="stakeholder.exportLicenceNo"
                                                           maxlength="50"
                                                           name="exportLicenceNo"/>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Nominee</label>
                                            <div class="col-md-8 col-xs-12">
                                                <div class="input-group col-md-12">
                                                    <input type="text"
                                                           class="form-control "
                                                           id="nominee"
                                                           rv-value="stakeholder.nominee"
                                                           name="nominee"
                                                           maxlength="50"/>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">TQB Code</label>
                                            <div class="col-md-8 col-xs-12">
                                                <div class="input-group col-md-12">
                                                    <input type="text"
                                                           class="form-control "
                                                           id="tqbCode"
                                                           rv-value="stakeholder.tqbCode"
                                                           name="tqbCode"
                                                           maxlength="25"/>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">IATA Code</label>
                                            <div class="col-md-8 col-xs-12">
                                                <div class="input-group col-md-12">
                                                    <input type="text" class="form-control" id="iataCode"
                                                           rv-value="stakeholder.iataCode"
                                                           name="iataCode"/>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Bank</label>
                                            <div class="col-md-8 col-xs-12">
                                                <div class="input-group col-md-12">
                                                    <select data-live-search="true"
                                                            name="bankSeq"
                                                            id="bankSeq"
                                                            rv-setselected="stakeholder.bankSeq"
                                                            aria-required="true"
                                                            class="form-control ajax-select"
                                                            data-abs-ajax-type="get"
                                                            data-abs-request-delay="500"
                                                            data-key="bankSeq"
                                                            data-value="bankName"
                                                            title="Select and begin typing"
                                                            data-abs-ajax-url="master/stakeholderManagement/findBank">
                                                        <option rv-value="stakeholder.bankSeq"
                                                                rv-text="stakeholder.bank.bankName"
                                                                rv-setselectedattr="stakeholder.bankSeq"
                                                        ></option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Bank Account No</label>
                                            <div class="col-md-8 col-xs-12">
                                                <div class="input-group col-md-12">
                                                    <input type="text"
                                                           class="form-control"
                                                           id="bankAccountNo"
                                                           rv-value="stakeholder.bankAccountNo"
                                                           maxlength="50"
                                                           name="bankAccountNo"/>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Beneficiary Name</label>
                                            <div class="col-md-8 col-xs-12">
                                                <div class="input-group col-md-12">
                                                    <input type="text"
                                                           class="form-control"
                                                           id="beneficiaryName"
                                                           rv-value="stakeholder.beneficiaryName"
                                                           maxlength="50"
                                                           name="beneficiaryName"/>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Beneficiary Branch Name</label>
                                            <div class="col-md-8 col-xs-12">
                                                <div class="input-group col-md-12">
                                                    <input type="text"
                                                           class="form-control"
                                                           id="beneficiaryBranchName"
                                                           rv-value="stakeholder.beneficiaryBranchName"
                                                           maxlength="50"
                                                           name="beneficiaryBranchName"/>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-md-3 col-xs-12 control-label">Status</label>
                                            <div class="col-md-8 col-xs-12">
                                                <select name="status" id="status" rv-value="stakeholder.status"
                                                        class="form-control select" data-validate="true"
                                                        onchange="get_status_value()"
                                                        data-live-search="false">
                                                    <c:forEach items="${statusList}" var="status">
                                                        <option value="${status.statusSeq}">${status.status}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group createOperation removeFromModal">
                                    <div class="col-md-offset-4 col-md-7">
                                        <button type="button" class="btn btn-primary pull-right"
                                                value="create Stakeholder"
                                                <sec:authorize
                                                        access="!hasRole('ROLE_master@stakeholderManagement_CREATE')">
                                                    disabled="disabled"
                                                </sec:authorize>
                                                onclick="create_stakeholder()">Create
                                        </button>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group hidden">
                                <input type="hidden" name="stakeholderSeq" id="stakeholderSeq" value="" rv-value="stakeholder.stakeholderSeq"/>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="tab-pane" id="tab-second">
                    <div class="row">
                        <div class="col-md-6">
                            <form class="form-horizontal" method="post" name="search" id="search">
                                <div class="form-group">
                                    <h4 class="subTitle">Search Stakeholder</h4>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Stakeholder ID</label>
                                    <div class="col-md-8 col-xs-12">
                                        <input type="text"
                                               class="form-control"
                                               id="search_stakeholderSeq"
                                               name="stakeholderSeq"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Stakeholder Type</label>
                                    <div class="col-md-8 col-xs-12">
                                        <select name="stakeholderTypeSeq"
                                                id="search_stakeholderTypeSeq"
                                                class="form-control select"
                                                data-live-search="true">
                                            <option value="-1">Select</option>
                                            <c:forEach items="${stakeholderTypeList}" var="stakeholderType">
                                                <option value="${stakeholderType.stakeholderTypeSeq}">${stakeholderType.stakeholderTypeName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Stakeholder Name</label>
                                    <div class="col-md-8 col-xs-12">
                                        <input type="text"
                                               class="form-control"
                                               id="search_stakeholderName"
                                               name="stakeholderName"
                                               maxlength="50"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 control-label">Country</label>
                                    <div class="col-md-8 col-xs-12">
                                        <select data-live-search="true"
                                                name="addressBook.countrySeq"
                                                id="search_countrySeq"
                                                data-validate="true"
                                                aria-required="true"
                                                class="form-control ajax-select"
                                                data-abs-ajax-type="get"
                                                data-abs-request-delay="500"
                                                data-key="countrySeq"
                                                data-value="countryName"
                                                data-subText="countryCode"
                                                title="Select and begin typing"
                                                data-abs-ajax-url="master/stakeholderManagement/findCountry">
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Status</label>
                                    <div class="col-md-8 col-xs-12">
                                        <select name="status" id="search_status"
                                                class="form-control select" data-validate="true"
                                                data-live-search="false">
                                            <option value="-1">Select</option>
                                            <c:forEach items="${statusList}" var="status">
                                                <option value="${status.statusSeq}">${status.status}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Stakeholder Group</label>
                                    <div class="col-md-8 col-xs-12">
                                        <select name="stakeholderGroupSeq" id="search_stakeholderGroupSeq"
                                                class="form-control select"
                                                data-live-search="true"
                                                data-validate="true">
                                            <option value="-1">Select</option>
                                            <c:forEach items="${customerGroupList}" var="customerGroup">
                                                <option value="${customerGroup.customerGroupSeq}">${customerGroup.customerGroupName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Created Date</label>
                                    <div class="col-md-8 col-xs-12" id="sandbox-container">
                                        <div class="input-daterange input-group" id="datepicker">
                                            <input type="text" class="input-sm form-control datepicker" id="search_startDate" name="startDate" value='<fmt:formatDate pattern="yyyy-MM-dd" value="${fromDate}"/>'/>
                                            <span class="input-group-addon">to</span>
                                            <input type="text" class="input-sm form-control datepicker" id="search_endDate" name="endDate" value='<fmt:formatDate pattern="yyyy-MM-dd" value="${toDate}" />'/>
                                        </div>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="col-sm-offset-2 col-sm-7">
                                        <button type="button" class="btn btn-primary pull-right" value="Search"
                                                onclick="search_stakeholder()">Search
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="form-group" id="loadStakeholderData"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="myModal">
    <div class="modal-dialog cs-modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Stakeholder Details</h4>
            </div>
            <form class="form-horizontal" method="post" name="modal" id="modal">
                <div class="modal-body panel">
                </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-danger"
                        <sec:authorize
                                access="!hasRole('ROLE_master@stakeholderManagement_DELETE')">
                            disabled="disabled"
                        </sec:authorize>
                        data-toggle=confirmation
                        data-btn-ok-label="Continue"
                        data-btn-ok-icon="glyphicon glyphicon-share-alt"
                        data-btn-ok-class="btn-success"
                        data-btn-cancel-label="Cancel"
                        data-btn-cancel-icon="glyphicon glyphicon-ban-circle"
                        data-btn-cancel-class="btn-danger"
                        data-title="Deleting Stakeholder"
                        data-content="Are you sure you want to delete this stakeholder ? "
                        data-id="modal"
                        data-on-confirm="delete_stakeholder_popup">
                    Delete
                </button>
                <button type="button" class="btn btn-primary"
                        <sec:authorize access="!hasRole('ROLE_master@stakeholderManagement_UPDATE')">
                            disabled="disabled"
                        </sec:authorize>
                        id="updateStakeHolder"
                        onclick="update_stakeholder_modal()">Save Changes
                </button>
                <button type="button" class="btn btn-danger"
                        <sec:authorize access="!hasRole('ROLE_finance@rateMasterManagement_VIEW')">
                            disabled="disabled"
                        </sec:authorize>
                        id="rateMaster"
                        onclick="view_rate_master()">Rate Master
                </button>
            </div>
            </form>
        </div>
    </div>
</div>


