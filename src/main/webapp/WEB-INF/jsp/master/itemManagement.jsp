<%@ include file="/WEB-INF/jsp/include.jsp" %>
<script type="text/javascript" src="../../../customjs/master/itemManagement.js"></script>
<div class="page-title">
    <h3><span class="fa fa-anchor"></span>Item Management</h3>
</div>

<div class="row">
    <div class="col-md-12">

        <div class="panel panel-default tabs">
            <ul class="nav nav-tabs" role="tablist">
                <li class="active"><a href="#tab-first" role="tab" data-toggle="tab">Item Creation</a></li>
                <li><a href="#tab-second" role="tab" data-toggle="tab">Item Search</a></li>
            </ul>
            <div class="panel-body tab-content">
                <div class="tab-pane active" id="tab-first">
                    <div class="col-md-12">
                        <form class="form-horizontal itemForm" method="post" name="create" id="create">
                            <div class="form-group removeFromModal">
                                <div class="col-md-7">
                                    <h4 class="subTitle">Item Details</h4>
                                </div>
                                <div class="col-md-5 operations" style="display: none">
                                    <button type="button" class="btn btn-success" onclick="new_form(formTemplate, 'update','create')">
                                        New
                                    </button>
                                    <button type="button" class="btn btn-danger"
                                            <sec:authorize
                                                    access="!hasRole('ROLE_master@itemManagement_DELETE')">
                                                disabled="disabled"
                                            </sec:authorize>
                                            data-toggle=confirmation
                                            data-btn-ok-label="Continue"
                                            data-btn-ok-icon="glyphicon glyphicon-share-alt"
                                            data-btn-ok-class="btn-success"
                                            data-btn-cancel-label="Cancel"
                                            data-btn-cancel-icon="glyphicon glyphicon-ban-circle"
                                            data-btn-cancel-class="btn-danger"
                                            data-title="Deleting Item"
                                            data-content="Are you sure you want to Delete this Item? "
                                            data-id="update"
                                            data-on-confirm="delete_item">
                                        Delete
                                    </button>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Item Code</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" maxlength="15" class="form-control" id="itemCode"
                                               name="itemCode"
                                               rv-value="item.itemCode"
                                               pattern="^[a-zA-Z0-9\s]+$"
                                               data-error="Please Provide a Valid Item Code"
                                               required/>
                                        <span class="help-block">Required, Item Code</span>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Item Name</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" maxlength="50" class="form-control" id="itemName"
                                               name="itemName"
                                               rv-value="item.itemName"
                                               pattern="^[a-zA-Z0-9\s]+$"
                                               data-error="Please Provide a Valid Item Name"
                                               required/>
                                        <span class="help-block">Required, Item Name</span>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Item Type</label>

                                    <div class="col-md-6 col-xs-12">
                                        <select name="itemType" id="itemType"
                                                rv-value="item.itemType"
                                                class="form-control select"
                                                data-live-search="true"
                                                placeholder="Status"
                                                data-error="Please Select Item Type"
                                                required>
                                            <option value="">Select</option>
                                            <c:forEach items="${itemTypeList}" var="itemType">
                                                <option value="${itemType.itemType}">${itemType.itemTypeDescription}</option>
                                            </c:forEach>
                                        </select>
                                        <span class="help-block">Required</span>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Description</label>

                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" maxlength="150" class="form-control" id="itemDescription"
                                               name="itemDescription"
                                               rv-value="item.itemDescription">
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Status</label>

                                    <div class="col-md-6 col-xs-12">
                                        <select name="status" id="status"
                                                rv-value="item.status"
                                                class="form-control select"
                                                data-live-search="true"
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
                                        <button type="button" class="btn btn-primary pull-right" value="create Item"
                                                <sec:authorize
                                                        access="!hasRole('ROLE_master@itemManagement_CREATE')">
                                                    disabled="disabled"
                                                </sec:authorize>
                                                onclick="create_item()">Create
                                        </button>
                                    </div>
                                </div>
                                <div class="form-group updateOperation" style="display: none">
                                    <div class="form-group">
                                        <label class="col-md-3 text-right">Created By</label>
                                        <label class="col-md-8" rv-text="item.createdBy"></label>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-3 text-right">Created Date</label>
                                        <label class="col-md-8" rv-text="item.createdDate"></label>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-3 text-right">Last Modified By</label>
                                        <label class="col-md-8" rv-text="item.lastModifiedBy"></label>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-3 text-right">Last Modified Date</label>
                                        <label class="col-md-8" rv-text="item.lastModifiedDate"></label>
                                    </div>
                                    <div class="col-sm-offset-2 col-sm-7 removeFromModal">
                                        <button type="button" class="btn btn-primary pull-right"
                                                value="Update Delivery Type"
                                                <sec:authorize
                                                        access="!hasRole('ROLE_master@itemManagement_UPDATE')">
                                                    disabled="disabled"
                                                </sec:authorize>
                                                onclick="update_item()">Update
                                        </button>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Unit</label>
                                    <div class="col-md-6 col-xs-12">
                                        <select name="unitSeq"
                                                id="unitSeq"
                                                class="form-control ajax-select"
                                                data-validate="true"
                                                data-abs-ajax-type="get"
                                                data-abs-request-delay="500"
                                                data-key="unitSeq"
                                                rv-value="item.unitSeq"
                                                rv-setselected="item.unitSeq"
                                                data-value="unitName"
                                                data-abs-ajax-url="master/itemManagement/findUnit"
                                                data-live-search="true"
                                                title="Required, Unit"
                                                required>
                                            <option rv-value="item.unitSeq"
                                                    rv-text="item.unit.unitName"
                                                    rv-setselectedattr="item.unitSeq"></option>
                                        </select>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Unit Price</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" class="form-control"
                                               id="unitPrice"
                                               rv-value="item.unitPrice"
                                               required
                                               name="unitPrice"
                                               onchange="validateFloatKeyPress(this,2)"
                                               style="min-width: 40px"
                                               maxlength="10"/>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Expire(KM)</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" class="form-control"
                                               id="kmExpiration"
                                               rv-value="item.kmExpiration"
                                               required
                                               name="kmExpiration"
                                               onchange="validateIntegerKeyPress(this)"
                                               style="min-width: 40px"
                                               maxlength="10"/>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Expire(Duration)</label>
                                    <div class="col-md-6 col-xs-12">
                                        <select name="durationExpiration"
                                                id="durationExpiration0"
                                                class="form-control select"
                                                rv-value="item.durationExpiration"
                                                data-live-search="false"
                                                required
                                                data-validate="true">
                                            <c:forEach items="${durationTypeList}" var="durationType">
                                                <option value="${durationType.durationExpiration}">${durationType.durationExpirationDescription}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                            </div>
                            <input type="hidden" name="itemSeq" id="itemSeq" value="" rv-value="item.itemSeq"/>
                        </form>
                    </div>
                </div>
                <div class="tab-pane" id="tab-second">
                    <div class="row">
                        <div class="col-md-6">
                            <form class="form-horizontal" method="post" name="search" id="search">
                                <div class="form-group">
                                    <h4 class="subTitle">Search Item</h4>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Item Code</label>

                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" class="form-control" id="search_itemCode"
                                               name="itemCode" required/>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Used For</label>

                                    <div class="col-md-6 col-xs-12">
                                        <select name="usedFor" id="search_usedFor" class="form-control select"
                                                data-live-search="true"
                                                placeholder="Status">
                                            <option value="None">Select</option>
                                            <c:forEach items="${itemTypeList}" var="itemType">
                                                <option value="${itemType.itemType}">${itemType.itemTypeDescription}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Item Name</label>

                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" class="form-control" id="search_itemName"
                                               name="itemName" required/>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <div class="col-sm-offset-2 col-sm-7">
                                        <button type="button" class="btn btn-primary pull-right" value="searchItem"
                                                <sec:authorize
                                                        access="!hasRole('ROLE_master@itemManagement_VIEW')">
                                                    disabled="disabled"
                                                </sec:authorize>
                                                onclick="search_item()">Search
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="form-group" id="loadItemList">
                        </div>
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
                <h4 class="modal-title">Item Details</h4>
            </div>
            <form class="form-horizontal" method="post" name="modal" id="modal">
                <div class="modal-body panel">

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-danger"
                            <sec:authorize
                                    access="!hasRole('ROLE_master@itemManagement_DELETE')">
                                disabled="disabled"
                            </sec:authorize>
                            data-toggle=confirmation
                            data-btn-ok-label="Continue" data-btn-ok-icon="glyphicon glyphicon-share-alt"
                            data-btn-ok-class="btn-success"
                            data-btn-cancel-label="Cancel" data-btn-cancel-icon="glyphicon glyphicon-ban-circle"
                            data-btn-cancel-class="btn-danger"
                            data-title="Deleting Item" data-content="Are you sure you want to Delete this Item? "
                            data-id="modal"
                            data-on-confirm="delete_item">
                        Delete
                    </button>
                    <button type="button" class="btn btn-primary"
                            <sec:authorize access="!hasRole('ROLE_master@itemManagement_UPDATE')">
                                disabled="disabled"
                            </sec:authorize>
                            onclick="update_item_modal()">Save Changes
                    </button>
                </div>
            </form>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>



