<%@ include file="/WEB-INF/jsp/include.jsp" %>
<script type="text/javascript" src="../../../customjs/master/locationManagement.js"></script>
<div class="page-title">
    <h3><span class="fa fa-user"></span>Location Management</h3>
</div>
<!-- END PAGE TITLE -->

<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default tabs">
            <ul class="nav nav-tabs" role="tablist">
                <li class="active"><a href="#tab-first" role="tab" data-toggle="tab">Location Creation</a></li>
                <li><a href="#tab-second" role="tab" data-toggle="tab">Location Search</a></li>
            </ul>
            <div class="panel-body tab-content">
                <div class="tab-pane active" id="tab-first">
                    <div class="col-md-12">
                        <form class="form-horizontal locationForm" method="post" name="create" id="create">
                            <div class="form-group removeFromModal">
                                <div class="col-md-7">
                                    <h4 class="subTitle">Location Details</h4>
                                </div>
                                <div class="col-md-5 operations" style="display: none">
                                    <button type="button" class="btn btn-success"
                                            onclick="new_form(formTemplate, 'update','create')">
                                        New
                                    </button>
                                    <button type="button" class="btn btn-danger"
                                            <sec:authorize
                                                    access="!hasRole('ROLE_master@locationManagement_DELETE')">
                                                disabled="disabled"
                                            </sec:authorize>
                                            data-toggle=confirmation
                                            data-btn-ok-label="Continue"
                                            data-btn-ok-icon="glyphicon glyphicon-share-alt"
                                            data-btn-ok-class="btn-success"
                                            data-btn-cancel-label="Cancel"
                                            data-btn-cancel-icon="glyphicon glyphicon-ban-circle"
                                            data-btn-cancel-class="btn-danger"
                                            data-title="Deleting Location"
                                            data-content="Are You Sure You Want To Delete This Location ? "
                                            data-id="update"
                                            data-on-confirm="delete_location">
                                        Delete
                                    </button>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Location Name</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" class="form-control" id="locationName"
                                               name="locationName"
                                               rv-value="location.locationName"
                                               maxlength="50"
                                               data-error="Please Provide a Valid Location Name"
                                               pattern="^[a-zA-Z0-9\s]+$"
                                               required/>
                                        <span class="help-block">Required, Location Name</span>
                                    </div>
                                    <div class="help-block with-errors"></div>
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
                                                rv-setselectedattr="location.countrySeq"
                                                data-abs-ajax-url="master/locationManagement/findCountry"
                                                data-live-search="true" placeholder="Country">
                                            <option rv-value="location.countrySeq" rv-text="location.country.countryName" rv-setselectedattr="location.countrySeq"></option>
                                        </select>
                                        <span class="help-block">Required</span>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Postal Code</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" class="form-control text-uppercase" id="postalCode"
                                               name="postalCode"
                                               rv-value="location.postalCode"
                                               maxlength="50"
                                               data-error="Please Provide a Valid Postal Code"
                                               onchange="validateIntegerKeyPress()"
                                               required/>
                                        <span class="help-block">Required, Postal Code</span>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">District Code</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" class="form-control text-uppercase" id="districtCode"
                                               name="districtCode"
                                               rv-value="location.districtCode"
                                               maxlength="50"
                                               data-error="Please Provide a Valid District Code"
                                               pattern="^[a-zA-Z0-9\s]+$"
                                               required/>
                                        <span class="help-block">Required, District Code</span>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Latitude</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" class="form-control text-uppercase" id="latitude"
                                               name="latitude"
                                               rv-value="location.latitude"
                                               maxlength="50"
                                               data-error="Please Provide Latitude"
                                               pattern="^[0-9]+(\.[0-9]+)+$"
                                               required/>
                                        <span class="help-block">Required, Latitude</span>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Longitude</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" class="form-control text-uppercase" id="longitude"
                                               name="longitude"
                                               rv-value="location.longitude"
                                               maxlength="50"
                                               data-error="Please Provide Longitude"
                                               pattern="^[0-9]+(\.[0-9]+)+$"
                                               required/>
                                        <span class="help-block">Required, Longitude</span>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Status</label>
                                    <div class="col-md-6 col-xs-12">
                                        <select name="status" id="status"
                                                class="form-control select" data-validate="true" required
                                                rv-value="location.status"
                                                data-live-search="true" placeholder="Status">
                                            <c:forEach items="${statusList}" var="status">
                                                <option value="${status.statusSeq}">${status.status}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                            </div>
                            <div class="form-group createOperation removeFromModal">
                                <div class="col-sm-offset-2 col-sm-7">
                                    <button type="button" class="btn btn-primary pull-right" value="createLocation"
                                            <sec:authorize
                                                    access="!hasRole('ROLE_master@locationManagement_UPDATE')">
                                                disabled="disabled"
                                            </sec:authorize>
                                            onclick="create_location()">Create
                                    </button>
                                </div>
                            </div>
                            <div class="form-group updateOperation" style="display: none">
                                <div class="form-group">
                                    <label class="col-md-3 text-right">Created By</label>
                                    <label class="col-md-8" rv-text="location.createdBy"></label>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 text-right">Created Date</label>
                                    <label class="col-md-8" rv-text="location.createdDate"></label>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 text-right">Last Modified By</label>
                                    <label class="col-md-8" rv-text="location.lastModifiedBy"></label>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 text-right">Last Modified Date</label>
                                    <label class="col-md-8" rv-text="location.lastModifiedDate"></label>
                                </div>
                                <div class="col-sm-offset-2 col-sm-7 removeFromModal">
                                    <button type="button" class="btn btn-primary pull-right"
                                            value="Update Location"
                                            <sec:authorize
                                                    access="!hasRole('ROLE_master@locationManagement_UPDATE')">
                                                disabled="disabled"
                                            </sec:authorize>
                                            onclick="update_location()">Update
                                    </button>
                                </div>
                            </div>
                            <input type="hidden" name="locationSeq" id="locationSeq" value=""
                                   rv-value="location.locationSeq"/>
                        </form>
                    </div>
                </div>
                <div class="tab-pane" id="tab-second">
                    <div class="row">
                        <div class="col-md-6">
                            <form class="form-horizontal" method="post" name="search" id="search">
                                <div class="form-group">
                                    <h4 class="subTitle">Search Location</h4>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Location Name</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" class="form-control"
                                               id="search_locationName"
                                               name="locationName" required/>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Country</label>
                                    <div class="col-md-6 col-xs-12">
                                        <select name="countrySeq" id="search_countrySeq"
                                                class="form-control ajax-select"
                                                data-validate="true" required
                                                data-abs-ajax-type="get"
                                                data-abs-request-delay="500"
                                                data-key="countrySeq"
                                                data-value="countryName"
                                                data-subText="countryCode"
                                                data-abs-ajax-url="config/locationManagement/findCountry"
                                                data-live-search="true" placeholder="Country">
                                        </select>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <div class="col-sm-offset-2 col-sm-7">
                                        <button type="button" class="btn btn-primary pull-right"
                                                value="searchLocation"
                                                <sec:authorize
                                                        access="!hasRole('ROLE_master@locationManagement_VIEW')">
                                                    disabled="disabled"
                                                </sec:authorize>
                                                onclick="search_location()">Search
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="form-group" id="loadLocationData"></div>
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
                <h4 class="modal-title">Location Details</h4>
            </div>
            <form class="form-horizontal" method="post" name="modal" id="modal">
                <div class="modal-body panel">

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-danger"
                            <sec:authorize
                                    access="!hasRole('ROLE_master@locationManagement_DELETE')">
                                disabled="disabled"
                            </sec:authorize>
                            data-toggle=confirmation
                            data-btn-ok-label="Continue" data-btn-ok-icon="glyphicon glyphicon-share-alt"
                            data-btn-ok-class="btn-success"
                            data-btn-cancel-label="Cancel" data-btn-cancel-icon="glyphicon glyphicon-ban-circle"
                            data-btn-cancel-class="btn-danger"
                            data-title="Deleting Location"
                            data-content="Are You Sure You Want To Delete This Location? "
                            data-id="modal"
                            data-on-confirm="delete_location">
                        Delete
                    </button>
                    <button type="button" class="btn btn-primary"
                            <sec:authorize access="!hasRole('ROLE_master@locationManagement_UPDATE')">
                                disabled="disabled"
                            </sec:authorize>
                            onclick="update_location_modal()">Save Changes
                    </button>

                </div>
            </form>
        </div>
    </div>
</div>

