<%@ include file="/WEB-INF/jsp/include.jsp" %>
<script type="text/javascript" src="../../../customjs/master/finalDestinationManagement.js"></script>
<div class="page-title">
    <h3><span class="fa fa-road"></span>Final Destination Management</h3>
</div>

<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default tabs">
            <ul class="nav nav-tabs" role="tablist">
                <li class="active"><a href="#tab-first" role="tab" data-toggle="tab">Final Destination Creation</a></li>
                <li><a href="#tab-second" role="tab" data-toggle="tab">Final Destination Search</a></li>
            </ul>
            <div class="panel-body tab-content">
                <div class="tab-pane active" id="tab-first">
                    <div class="col-md-12">
                        <form class="form-horizontal finalDestinationForm" method="post" name="create" id="create">
                            <div class="form-group removeFromModal">
                                <div class="col-md-7">
                                    <h4 class="subTitle">Final Destination Details</h4>
                                </div>
                                <div class="col-md-5 operations" style="display: none">
                                    <button type="button" class="btn btn-success" onclick="new_form(formTemplate, 'update','create')">
                                        New
                                    </button>
                                    <button type="button" class="btn btn-danger"
                                            <sec:authorize
                                                    access="!hasRole('ROLE_master@finalDestinationManagement_DELETE')">
                                                disabled="disabled"
                                            </sec:authorize>
                                            data-toggle=confirmation
                                            data-btn-ok-label="Continue"
                                            data-btn-ok-icon="glyphicon glyphicon-share-alt"
                                            data-btn-ok-class="btn-success"
                                            data-btn-cancel-label="Cancel"
                                            data-btn-cancel-icon="glyphicon glyphicon-ban-circle"
                                            data-btn-cancel-class="btn-danger"
                                            data-title="Deleting Final Destination"
                                            data-content="Are you sure you want to Delete this Final Destination? "
                                            data-id="update"
                                            data-on-confirm="delete_final_destination">
                                        Delete
                                    </button>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Destination</label>
                                    <div class="col-md-8 col-xs-12">
                                        <input type="text" maxlength="200" class="form-control" name="destination"
                                               id="finalDestination"  rv-value="finalDestination.destination"
                                               required/>
                                        <span class="help-block">Required, Destination</span>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">City</label>
                                    <div class="col-md-8 col-xs-12">
                                        <input type="text" maxlength="30" class="form-control" name="addressBook.city"
                                               id="city" rv-value="finalDestination.addressBook.city"
                                               data-error="Please Provide a City"
                                               required/>
                                        <span class="help-block">Required, City</span>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">State</label>
                                    <div class="col-md-8 col-xs-12">
                                        <input type="text" maxlength="50" class="form-control" name="addressBook.state"
                                               rv-value="finalDestination.addressBook.state"
                                               id="state"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Zip</label>
                                    <div class="col-md-8 col-xs-12">
                                        <input type="text" maxlength="10" class="form-control" name="addressBook.zip"
                                               rv-value="finalDestination.addressBook.zip"
                                               id="zip"/>
                                    </div>

                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Location</label>
                                    <div class="col-md-8 col-xs-12">
                                        <select name="locationSeq" id="locationSeq"
                                                class="form-control ajax-select"
                                                data-validate="true" required
                                                data-abs-ajax-type="get"
                                                data-error="Please Select Location"
                                                data-abs-request-delay="500"
                                                title="Select and begin typing"
                                                data-key="locationSeq"
                                                data-value="locationName"
                                                data-subText="postalCode"
                                                rv-setselectedattr="finalDestination.locationSeq"
                                                data-abs-ajax-url="master/finalDestinationManagement/findLocation"
                                                data-live-search="true" placeholder="Country">
                                            <option rv-value="finalDestination.locationSeq"
                                                    rv-text="finalDestination.location.locationName"
                                                    rv-setselectedattr="finalDestination.locationSeq"></option>
                                        </select>
                                        <span class="help-block">Required</span>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Address 1</label>
                                    <div class="col-md-8 col-xs-12">
                                        <input type="text"
                                               class="form-control"
                                               name="addressBook.address1"
                                               required
                                               id="address1"
                                               rv-value="finalDestination.addressBook.address1"
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
                                               rv-value="finalDestination.addressBook.address2"
                                               maxlength="100"
                                               id="address2"/>
                                    </div>

                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Latitude</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" class="form-control text-uppercase" id="latitude"
                                               name="latitude"
                                               rv-value="finalDestination.latitude"
                                               maxlength="50"
                                               data-error="Please Provide Latitude"/>
                                        <span class="help-block">Required, Latitude</span>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Longitude</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" class="form-control text-uppercase" id="longitude"
                                               name="longitude"
                                               rv-value="finalDestination.longitude"
                                               maxlength="50"
                                               data-error="Please Provide Longitude"/>
                                        <span class="help-block">Required, Longitude</span>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Status</label>
                                    <div class="col-md-6 col-xs-12">
                                        <select name="status" id="status"  rv-value="finalDestination.status"
                                                class="form-control select" data-validate="true" required
                                                data-live-search="true" placeholder="Status">
                                            <c:forEach items="${statusList}" var="status">
                                                <option value="${status.statusSeq}">${status.status}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <span class="updateOperation" style="display: none">
                                    <div class="form-group">
                                        <label class="col-md-3 text-right">Created By</label>
                                        <label class="col-md-8" rv-text="finalDestination.createdBy"></label>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-3 text-right">Created Date</label>
                                        <label class="col-md-8" rv-text="finalDestination.createdDate"></label>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-3 text-right">Last Modified By</label>
                                        <label class="col-md-8" rv-text="finalDestination.lastModifiedBy"></label>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-3 text-right">Last Modified Date</label>
                                        <label class="col-md-8" rv-text="finalDestination.lastModifiedDate"></label>
                                    </div>
                                    <div class="col-sm-offset-2 col-sm-7 removeFromModal">
                                        <button type="button" class="btn btn-primary pull-right"
                                                value="Update Final Destination"
                                                <sec:authorize
                                                        access="!hasRole('ROLE_master@finalDestinationManagement_UPDATE')">
                                                    disabled="disabled"
                                                </sec:authorize>
                                                onclick="update_final_destination()">Update
                                        </button>
                                    </div>
                                </span>
                            </div>
                            <div class="form-group createOperation removeFromModal">
                                <div class="col-sm-offset-2 col-sm-7">
                                    <button type="button" class="btn btn-primary pull-right" value="addDestination"
                                            <sec:authorize
                                                    access="!hasRole('ROLE_master@finalDestinationManagement_CREATE')">
                                                disabled="disabled"
                                            </sec:authorize>
                                            onclick="create_final_destination()">Create
                                    </button>
                                </div>
                            </div>
                            <input type="hidden" name="finalDestinationSeq" rv-value="finalDestination.finalDestinationSeq"/>
                            <input type="hidden" name="addressBook.addressBookSeq" rv-value="finalDestination.addressBook.addressBookSeq"/>
                            <input type="hidden" name="addressBookSeq" rv-value="finalDestination.addressBookSeq"/>
                        </form>
                    </div>
                </div>

                <div class="tab-pane" id="tab-second">
                    <div class="row">
                        <div class="col-md-6">
                            <form class="form-horizontal" method="post" name="search" id="search">
                                <div class="form-group">
                                    <h4 class="subTitle">Search Final Destination</h4>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Destination</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" maxlength="30" class="form-control"
                                               name="finalDestinationCode"
                                               id="search_finalDestinationCode"/>
                                    </div>
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
                                                data-abs-ajax-url="master/finalDestinationManagement/findCountry"
                                                data-live-search="true" placeholder="Country">
                                        </select>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">City</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" maxlength="30" class="form-control" name="city"
                                               id="search_city"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">State</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" maxlength="30" class="form-control" name="state"
                                               id="search_state"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Zip</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" maxlength="30" class="form-control" name="zip"
                                               id="search_zip"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-sm-offset-2 col-sm-7">
                                        <button type="button" class="btn btn-primary pull-right"
                                                value="loadDestinationData"
                                                <sec:authorize
                                                        access="!hasRole('ROLE_master@finalDestinationManagement_VIEW')">
                                                    disabled="disabled"
                                                </sec:authorize>
                                                onclick="load_final_destination_table_data()">Search
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="form-group" id="loadDestinationListData"></div>
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
                <h4 class="modal-title">Final Destination Details</h4>
            </div>
            <form class="form-horizontal" method="post" name="modal" id="modal">
                <div class="modal-body panel">

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-danger"
                            <sec:authorize
                                    access="!hasRole('ROLE_master@finalDestinationManagement_DELETE')">
                                disabled="disabled"
                            </sec:authorize>
                            data-toggle=confirmation
                            data-btn-ok-label="Continue" data-btn-ok-icon="glyphicon glyphicon-share-alt"
                            data-btn-ok-class="btn-success"
                            data-btn-cancel-label="Cancel" data-btn-cancel-icon="glyphicon glyphicon-ban-circle"
                            data-btn-cancel-class="btn-danger"
                            data-title="Deleting Final Destination"
                            data-content="Are you sure you want to Delete this Final Destination? "
                            data-id="modal"
                            data-on-confirm="delete_final_destination">
                        Delete
                    </button>
                    <button type="button" class="btn btn-primary"
                            <sec:authorize
                                    access="!hasRole('ROLE_master@finalDestinationManagement_UPDATE')">
                                disabled="disabled"
                            </sec:authorize>
                            onclick="update_final_destination_modal()">Save Changes
                    </button>
                </div>
            </form>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
