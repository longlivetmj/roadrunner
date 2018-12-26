<%@ include file="/WEB-INF/jsp/include.jsp" %>
<script type="text/javascript" src="../../../customjs/master/taxRegistrationManagement.js"></script>
<div class="page-title">
    <h3><span class="fa fa-user"></span>Tax Registration Management</h3>
</div>
<!-- END PAGE TITLE -->

<div class="row">
    <div class="col-md-12">

        <div class="panel panel-default tabs">
            <ul class="nav nav-tabs" role="tablist">
                <li class="active"><a href="#tab-first" role="tab" data-toggle="tab">Tax Registration Creation</a></li>
                <li><a href="#tab-second" role="tab" data-toggle="tab">Tax Registration Search</a></li>
            </ul>
            <div class="panel-body tab-content">
                <div class="tab-pane active" id="tab-first">
                    <div class="col-md-8">
                        <form class="form-horizontal taxRegistrationForm" method="post" id="create">
                            <div class="form-group removeFromModal">
                                <div class="col-md-7">
                                    <h4 class="subTitle">Tax Registration Details</h4>
                                </div>
                                <div class="col-md-5 operations" style="display: none">
                                    <button type="button" class="btn btn-success" onclick="new_form(formTemplate, 'update','create')">
                                        New
                                    </button>
                                    <button type="button" class="btn btn-danger"
                                            <sec:authorize
                                                    access="!hasRole('ROLE_master@taxRegistrationManagement_DELETE')">
                                                disabled="disabled"
                                            </sec:authorize>
                                            data-toggle=confirmation
                                            data-btn-ok-label="Continue" data-btn-ok-icon="glyphicon glyphicon-share-alt"
                                            data-btn-ok-class="btn-success"
                                            data-btn-cancel-label="Cancel" data-btn-cancel-icon="glyphicon glyphicon-ban-circle"
                                            data-btn-cancel-class="btn-danger"
                                            data-title="Deleting Tax Registration" data-content="Are you sure you want to Delete this Tax Registration? "
                                            data-id="update"
                                            data-on-confirm="delete_tax_registration">
                                        Delete
                                    </button>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Country</label>
                                <div class="col-md-6 col-xs-12">
                                    <select name="countrySeq" id="countrySeq"
                                            class="form-control ajax-select"
                                            data-validate="true" required
                                            data-abs-ajax-type="get"
                                            data-error="Please Select Country"
                                            data-abs-request-delay="500"
                                            title="Select and begin typing"
                                            data-key="countrySeq"
                                            data-value="countryName"
                                            data-subText="countryCode"
                                            rv-setselectedattr="taxRegistration.countrySeq"
                                            data-abs-ajax-url="master/taxRegistrationManagement/findCountry"
                                            data-live-search="true" placeholder="Country">
                                        <option rv-value="taxRegistration.countrySeq" rv-text="taxRegistration.country.countryName" rv-setselectedattr="taxRegistration.countrySeq"></option>
                                    </select>
                                    <span class="help-block">Required</span>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Tax Name</label>
                                <div class="col-md-6 col-xs-12">
                                    <input type="text" class="form-control text-uppercase" id="taxName"
                                           pattern="^[a-zA-Z0-9\s]+$"
                                           name="taxName" maxlength="50" rv-value="taxRegistration.taxName"
                                           data-error="Please Provide a Valid Tax Name"required/>
                                    <span class="help-block">Required, Tax Name</span>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Tax Rate</label>
                                <div class="col-md-6 col-xs-12">
                                    <input type="text" class="form-control text-uppercase" id="taxRate"
                                           pattern="^\d*\.?\d*$" maxlength="5"
                                           name="taxRate" rv-value="taxRegistration.taxRate"
                                           data-error="Please Provide a Valid Tax Rate"required/>
                                    <span class="help-block">Required, Tax Rate</span>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Display Rate</label>
                                <div class="col-md-6 col-xs-12">
                                    <input type="text" class="form-control text-uppercase" id="taxRateDisplay"
                                           maxlength="5"
                                           name="taxRateDisplay" rv-value="taxRegistration.taxRateDisplay"
                                           data-error="Please Provide a Display Tax Rate"required/>
                                    <span class="help-block">Required, Tax Rate</span>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Remarks</label>
                                <div class="col-md-6 col-xs-12">
                                    <textarea class="form-control text-uppercase" rows="3" name="remarks"
                                              rv-value="taxRegistration.remarks"
                                              id="create_remarks" maxlength="150"></textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Tax Type</label>
                                <div class="col-md-6 col-xs-12">
                                    <select name="taxTypeSeq" id="taxTypeSeq"
                                            class="form-control select"
                                            data-live-search="true"
                                            rv-value="taxRegistration.taxTypeSeq"
                                            data-validate="true"
                                            placeholder="Tax Type">
                                        <option value="">Select</option>
                                        <c:forEach items="${taxTypeList}" var="taxType">
                                            <option value="${taxType.taxTypeSeq}">${taxType.taxTypeName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Status</label>
                                <div class="col-md-6 col-xs-12">
                                    <select name="status" id="status"
                                            class="form-control select" data-validate="true"
                                            rv-value="taxRegistration.status"
                                            data-live-search="true" placeholder="Status">
                                        <c:forEach items="${statusList}" var="status">
                                            <option value="${status.statusSeq}">${status.status}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group createOperation removeFromModal">
                                <div class="col-sm-offset-2 col-sm-7">
                                    <button type="button" class="btn btn-primary pull-right" value="create Tax"
                                            <sec:authorize
                                                    access="!hasRole('ROLE_master@taxRegistrationManagement_CREATE')">
                                                disabled="disabled"
                                            </sec:authorize>
                                            onclick="create_tax_registration()">Create
                                    </button>
                                </div>
                            </div>
                            <div class="form-group updateOperation" style="display: none">
                                <div class="form-group">
                                    <label class="col-md-3 text-right">Created By</label>
                                    <label class="col-md-8" rv-text="taxRegistration.createdBy"></label>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 text-right">Created Date</label>
                                    <label class="col-md-8" rv-text="taxRegistration.createdDate"></label>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 text-right">Last Modified By</label>
                                    <label class="col-md-8" rv-text="taxRegistration.lastModifiedBy"></label>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 text-right">Last Modified Date</label>
                                    <label class="col-md-8" rv-text="taxRegistration.lastModifiedDate"></label>
                                </div>
                                <div class="col-sm-offset-2 col-sm-7 removeFromModal">
                                    <button type="button" class="btn btn-primary pull-right" value="Update Tax"
                                            <sec:authorize
                                                    access="!hasRole('ROLE_master@taxRegistrationManagement_UPDATE')">
                                                disabled="disabled"
                                            </sec:authorize>
                                            onclick="update_tax_registration()">Update
                                    </button>
                                </div>
                            </div>
                            <input type="hidden" name="taxRegistrationSeq" id="taxRegistrationSeq" value="" rv-value="taxRegistration.taxRegistrationSeq"/>
                            <input type="hidden" name="companyProfileSeq" value="${companyProfileSeq}" rv-value="taxRegistration.companyProfileSeq"/>
                        </form>
                    </div>
                </div>
                <div class="tab-pane" id="tab-second">
                    <div class="row">
                        <div class="col-md-6">
                            <form class="form-horizontal" method="post" name="search" id="search">
                                <div class="form-group">
                                    <h4 class="subTitle">Search Tax Registration</h4>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Country</label>
                                    <div class="col-md-6 col-xs-12">
                                        <select name="countrySeq" id="search_countrySeq"
                                                class="form-control ajax-select"
                                                data-validate="true"
                                                data-abs-ajax-type="get"
                                                data-abs-request-delay="500"
                                                data-key="countrySeq"
                                                data-value="countryName"
                                                data-subText="countryCode"
                                                data-abs-ajax-url="master/taxRegistrationManagement/findCountry"
                                                data-live-search="true" placeholder="Country">
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Tax Name</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" class="form-control text-uppercase" id="search_taxName"
                                               name="taxName"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Remarks</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" class="form-control text-uppercase" id="search_remarks"
                                               name="remarks"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-sm-offset-2 col-sm-7">
                                        <button type="button" class="btn btn-primary pull-right" value="search Tax"
                                                onclick="search_tax_registration()">Search
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="form-group" id="loadTaxRegistrationData"></div>
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>

<div class="modal fade" id="myModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Tax Registration Details</h4>
            </div>
            <form class="form-horizontal" method="post" name="modal" id="modal">
                <div class="modal-body panel">

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-danger"
                            <sec:authorize
                                    access="!hasRole('ROLE_master@taxRegistrationManagement_DELETE')">
                                disabled="disabled"
                            </sec:authorize>
                            data-toggle=confirmation
                            data-btn-ok-label="Continue" data-btn-ok-icon="glyphicon glyphicon-share-alt"
                            data-btn-ok-class="btn-success"
                            data-btn-cancel-label="Cancel" data-btn-cancel-icon="glyphicon glyphicon-ban-circle"
                            data-btn-cancel-class="btn-danger"
                            data-title="Deleting Tax Registration" data-content="Are you sure you want to Delete this Tax Registration? "
                            data-id="modal"
                            data-on-confirm="delete_tax_registration">
                        Delete
                    </button>
                    <button type="button" class="btn btn-primary"
                            <sec:authorize access="!hasRole('ROLE_master@taxRegistrationManagement_UPDATE')">
                                disabled="disabled"
                            </sec:authorize>
                            onclick="update_tax_registration_modal()">Save Changes
                    </button>
                </div>
            </form>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>

