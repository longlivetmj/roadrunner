<%@ include file="/WEB-INF/jsp/include.jsp" %>
<script type="text/javascript" src="../../../customjs/master/countryManagement.js"></script>
<div class="page-title">
    <h3><span class="fa fa-user"></span>Country Management</h3>
</div>

<div class="row">
    <div class="col-md-12">

        <div class="panel panel-default tabs">
            <ul class="nav nav-tabs" role="tablist">
                <li class="active"><a href="#tab-first" role="tab" data-toggle="tab">Country Creation
                </a></li>
                <li><a href="#tab-second" role="tab" data-toggle="tab">Country Search </a></li>
            </ul>
            <div class="panel-body tab-content">
                <div class="tab-pane active" id="tab-first">
                    <div class="col-md-6">
                        <form class="form-horizontal countryForm" method="post" name="create" id="create">
                            <div class="form-group removeFromModal">
                                <div class="col-md-7">
                                    <h4 class="subTitle">Country Details</h4>
                                </div>
                                <div class="col-md-5 operations" style="display: none">
                                    <button type="button" class="btn btn-success"
                                            onclick="new_form(formTemplate, 'update','create')">
                                        New
                                    </button>
                                    <button type="button" class="btn btn-danger"
                                            <sec:authorize
                                                    access="!hasRole('ROLE_master@countryManagement_DELETE')">
                                                disabled="disabled"
                                            </sec:authorize>
                                            data-toggle=confirmation
                                            data-btn-ok-label="Continue"
                                            data-btn-ok-icon="glyphicon glyphicon-share-alt"
                                            data-btn-ok-class="btn-success"
                                            data-btn-cancel-label="Cancel"
                                            data-btn-cancel-icon="glyphicon glyphicon-ban-circle"
                                            data-btn-cancel-class="btn-danger"
                                            data-title="Deleting Country"
                                            data-content="Are You Sure You Want To Delete This Country ? "
                                            data-id="update"
                                            data-on-confirm="delete_country">
                                        Delete
                                    </button>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Country Code</label>

                                <div class="col-md-6 col-xs-12">
                                    <input type="text" class="form-control text-uppercase"
                                           id="countryCode"
                                           name="countryCode"
                                           maxlength="2"
                                           rv-value="country.countryCode"
                                           data-error="Please Provide a Valid Country Code"
                                           pattern="[a-zA-Z0-9_\.\+-]+"
                                           required/>
                                    <span class="help-block">Required, Country Code</span>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Country Name</label>

                                <div class="col-md-6 col-xs-12">
                                    <input type="text" class="form-control text-uppercase" id="countryName"
                                           name="countryName"
                                           maxlength="50"
                                           rv-value="country.countryName"
                                           data-error="Please Provide a Valid Country Name"
                                           pattern="^[a-zA-Z0-9\s]+$"
                                           required/>
                                    <span class="help-block">Required, Country Name</span>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Region</label>
                                <div class="col-md-6 col-xs-12">
                                    <select name="regionSeq" id="regionSeq"
                                            class="form-control ajax-select"
                                            data-validate="true"
                                            data-abs-ajax-type="get"
                                            data-abs-request-delay="500"
                                            data-key="regionSeq"
                                            data-value="regionName"
                                            data-subText="regionCode"
                                            title="Select and begin typing"
                                            data-abs-ajax-url="master/countryManagement/findRegion"
                                            data-live-search="true" placeholder="Region">
                                        <option rv-value="country.regionSeq"
                                                rv-text="country.region.regionName" selected></option>
                                    </select>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Currency</label>

                                <div class="col-md-6 col-xs-12">
                                    <select name="currencySeq" id="currencySeq"
                                            class="form-control ajax-select"
                                            data-validate="true"
                                            data-abs-ajax-type="get"
                                            data-error="Please Select Currency"
                                            data-abs-request-delay="500"
                                            data-key="currencySeq"
                                            data-value="currencyName"
                                            data-subText="currencyCode"
                                            title="Select and begin typing"
                                            data-abs-ajax-url="master/countryManagement/findCurrency"
                                            data-live-search="true"
                                            placeholder="Currency"
                                            required>
                                        <option rv-value="country.currencySeq"
                                                rv-text="country.currency.currencyName" rv-setselectedattr="country.currencySeq"></option>
                                    </select>
                                    <span class="help-block">Required</span>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">IATA Code</label>
                                <div class="col-md-6 col-xs-12">
                                    <input type="text" class="form-control" id="iataCode" name="iataCode"
                                           rv-value="country.iataCode"
                                           maxlength="15">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Status</label>
                                <div class="col-md-6 col-xs-12">
                                    <select name="status" id="status" class="form-control select"
                                            data-live-search="true" rv-value="country.status"
                                            placeholder="Status">
                                        <c:forEach items="${statusList}" var="status">
                                            <option value="${status.statusSeq}">${status.status}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group createOperation removeFromModal">
                                <div class="col-sm-offset-2 col-sm-7">
                                    <button type="button" class="btn btn-primary pull-right" value="createUser"
                                            <sec:authorize
                                                    access="!hasRole('ROLE_master@countryManagement_CREATE')">
                                                disabled="disabled"
                                            </sec:authorize>
                                            onclick="create_country()">Create
                                    </button>
                                </div>
                            </div>
                            <div class="form-group updateOperation" style="display: none">
                                <div class="form-group">
                                    <label class="col-md-3 text-right">Created By</label>
                                    <label class="col-md-8" rv-text="country.createdBy"></label>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 text-right">Created Date</label>
                                    <label class="col-md-8" rv-text="country.createdDate"></label>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 text-right">Last Modified By</label>
                                    <label class="col-md-8" rv-text="country.lastModifiedBy"></label>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 text-right">Last Modified Date</label>
                                    <label class="col-md-8" rv-text="country.lastModifiedDate"></label>
                                </div>
                                <div class="col-sm-offset-2 col-sm-7 removeFromModal">
                                    <button type="button" class="btn btn-primary pull-right"
                                            value="Update Country"
                                            <sec:authorize
                                                    access="!hasRole('ROLE_master@countryManagement_UPDATE')">
                                                disabled="disabled"
                                            </sec:authorize>
                                            onclick="update_country()">Update
                                    </button>
                                </div>
                            </div>
                            <input type="hidden" name="countrySeq" id="countrySeq" value=""
                                   rv-value="country.countrySeq"/>
                        </form>
                    </div>
                </div>
                <div class="tab-pane" id="tab-second">
                    <div class="row">
                        <div class="col-md-6">
                            <form class="form-horizontal" method="post" name="search" id="search">
                                <div class="form-group">
                                    <h4 class="subTitle">Search Country</h4>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Country Name</label>

                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" class="form-control" id="search_countryName"
                                               name="countryName" required/>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Country Code</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" class="form-control" id="search_countryCode"
                                               name="countryCode" required/>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <div class="col-sm-offset-2 col-sm-7">
                                        <button type="button" class="btn btn-primary pull-right" value="searchCountry"
                                                <sec:authorize
                                                        access="!hasRole('ROLE_master@countryManagement_VIEW')">
                                                    disabled="disabled"
                                                </sec:authorize>
                                                onclick="search_country()">Search
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="form-group" id="loadCountryList">
                        </div>
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
                <h4 class="modal-title">Country Details</h4>
            </div>
            <form class="form-horizontal" method="post" name="modal" id="modal">
                <div class="modal-body panel">

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-danger"
                            <sec:authorize
                                    access="!hasRole('ROLE_master@countryManagement_DELETE')">
                                disabled="disabled"
                            </sec:authorize>
                            data-toggle=confirmation
                            data-btn-ok-label="Continue" data-btn-ok-icon="glyphicon glyphicon-share-alt"
                            data-btn-ok-class="btn-success"
                            data-btn-cancel-label="Cancel" data-btn-cancel-icon="glyphicon glyphicon-ban-circle"
                            data-btn-cancel-class="btn-danger"
                            data-title="Deleting Country"
                            data-content="Are You Sure You Want To Delete This Country? "
                            data-id="modal"
                            data-on-confirm="delete_country">
                        Delete
                    </button>
                    <button type="button" class="btn btn-primary"
                            <sec:authorize access="!hasRole('ROLE_master@countryManagement_UPDATE')">
                                disabled="disabled"
                            </sec:authorize>
                            onclick="update_country_modal()">Save Changes
                    </button>
                </div>
            </form>
        </div><!-- /.modal-dialog -->
    </div>
</div>
