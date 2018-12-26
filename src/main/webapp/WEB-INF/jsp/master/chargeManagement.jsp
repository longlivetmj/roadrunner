<%@ include file="/WEB-INF/jsp/include.jsp" %>
<script type="text/javascript" src="../../../customjs/master/chargeManagement.js"></script>
<div class="page-title">
    <h3><span class="fa fa-user"></span>Charge Management</h3>
</div>

<div class="row">
    <div class="col-md-12">

        <div class="panel panel-default tabs">
            <ul class="nav nav-tabs" role="tablist">
                <li class="active"><a href="#tab-first" role="tab" data-toggle="tab">Charge Creation</a></li>
                <li><a href="#tab-second" role="tab" data-toggle="tab">Charge Search</a></li>
            </ul>
            <div class="panel-body tab-content">
                <div class="tab-pane active" id="tab-first">
                    <div class="col-md-6">
                        <form class="form-horizontal chargeForm" method="post" name="create" id="create">
                            <div class="form-group removeFromModal">
                                <div class="col-md-7">
                                    <h4 class="subTitle">Charge Details</h4>
                                </div>
                                <div class="col-md-5 operations" style="display: none">
                                    <button type="button" class="btn btn-success"
                                            onclick="new_form(formTemplate, 'update','create')">
                                        New
                                    </button>
                                    <button type="button" class="btn btn-danger"
                                            <sec:authorize
                                                    access="!hasRole('ROLE_master@chargeManagement_DELETE')">
                                                disabled="disabled"
                                            </sec:authorize>
                                            data-toggle=confirmation
                                            data-btn-ok-label="Continue"
                                            data-btn-ok-icon="glyphicon glyphicon-share-alt"
                                            data-btn-ok-class="btn-success"
                                            data-btn-cancel-label="Cancel"
                                            data-btn-cancel-icon="glyphicon glyphicon-ban-circle"
                                            data-btn-cancel-class="btn-danger"
                                            data-title="Deleting Charge"
                                            data-content="Are You Sure You Want To Delete This Charge ? "
                                            data-id="update"
                                            data-on-confirm="delete_charge">
                                        Delete
                                    </button>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Charge Name</label>
                                <div class="col-md-6 col-xs-12">
                                    <input type="text" class="form-control text-uppercase" id="chargeName"
                                           name="chargeName"
                                           data-error="Please Provide a Valid Charge Name"
                                           maxlength="50"
                                           rv-value="charge.chargeName"
                                           required/>
                                    <span class="help-block">Required, Charge Name</span>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Description</label>
                                <div class="col-md-6 col-xs-12">
                                    <input type="text"
                                           class="form-control text-uppercase"
                                           id="description"
                                           name="description"
                                           maxlength="150"
                                           rv-value="charge.description">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Charge Modes</label>
                                <div class="col-md-6 col-xs-12">
                                    <c:forEach items="${modeList}" var="mode" varStatus="status">
                                        <label class="check">
                                            <input type="checkbox"
                                                   id="create_${mode.companyModuleSeq}"
                                                   name="modeSeq[]"
                                                   value="${mode.companyModuleSeq}"
                                                   data-error="Please Select Charge Mode/s"/>
                                                ${mode.module.moduleName}</label>
                                    </c:forEach>
                                    <span class="help-block">Required, Charge Modes</span>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Status</label>
                                <div class="col-md-6 col-xs-12">
                                    <select name="status" id="status" class="form-control select"
                                            data-live-search="true"
                                            placeholder="Status"
                                            rv-value="charge.status">
                                        <c:forEach items="${statusList}" var="status">
                                            <option value="${status.statusSeq}">${status.status}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group createOperation removeFromModal">
                                <div class="col-sm-offset-2 col-sm-7">
                                    <button type="button" class="btn btn-primary pull-right" value="createCharge"
                                            <sec:authorize
                                                    access="!hasRole('ROLE_master@chargeManagement_CREATE')">
                                                disabled="disabled"
                                            </sec:authorize>
                                            onclick="create_charge()">Create
                                    </button>
                                </div>
                            </div>
                            <div class="form-group updateOperation" style="display: none">
                                <div class="form-group">
                                    <label class="col-md-3 text-right">Created By</label>
                                    <label class="col-md-8" rv-text="charge.createdBy"></label>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 text-right">Created Date</label>
                                    <label class="col-md-8" rv-text="charge.createdDate"></label>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 text-right">Last Modified By</label>
                                    <label class="col-md-8" rv-text="charge.lastModifiedBy"></label>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 text-right">Last Modified Date</label>
                                    <label class="col-md-8" rv-text="charge.lastModifiedDate"></label>
                                </div>
                                <div class="col-sm-offset-2 col-sm-7 removeFromModal">
                                    <button type="button" class="btn btn-primary pull-right"
                                            value="Update Charge"
                                            <sec:authorize
                                                    access="!hasRole('ROLE_master@chargeManagement_UPDATE')">
                                                disabled="disabled"
                                            </sec:authorize>
                                            onclick="update_charge()">Update
                                    </button>
                                </div>
                            </div>
                            <input type="hidden" name="chargeSeq" id="chargeSeq" value=""
                                   rv-value="charge.chargeSeq"/>

                        </form>
                    </div>
                </div>
                <div class="tab-pane" id="tab-second">
                    <div class="row">
                        <div class="col-md-6">
                            <form class="form-horizontal" method="post" name="search" id="search">
                                <div class="form-group">
                                    <h4 class="subTitle">Search Charge</h4>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Charge Name</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" class="form-control" id="search_chargeName"
                                               name="chargeName" required/>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Description</label>

                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" class="form-control text-uppercase" id="search_description"
                                               name="description" required/>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <div class="col-sm-offset-2 col-sm-7">
                                        <button type="button" class="btn btn-primary pull-right" value="searchCharge"
                                                <sec:authorize
                                                        access="!hasRole('ROLE_master@chargeManagement_VIEW')">
                                                    disabled="disabled"
                                                </sec:authorize>
                                                onclick="search_charge()">Search
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="form-group" id="loadChargeList">
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
                <h4 class="modal-title">Charge Details</h4>
            </div>
            <form class="form-horizontal" method="post" name="modal" id="modal">
                <div class="modal-body panel">

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-danger"
                            <sec:authorize
                                    access="!hasRole('ROLE_master@chargeManagement_DELETE')">
                                disabled="disabled"
                            </sec:authorize>
                            data-toggle=confirmation
                            data-btn-ok-label="Continue" data-btn-ok-icon="glyphicon glyphicon-share-alt"
                            data-btn-ok-class="btn-success"
                            data-btn-cancel-label="Cancel" data-btn-cancel-icon="glyphicon glyphicon-ban-circle"
                            data-btn-cancel-class="btn-danger"
                            data-title="Deleting Charge"
                            data-content="Are You Sure You Want To Delete This Charge? "
                            data-id="modal"
                            data-on-confirm="delete_charge">
                        Delete
                    </button>
                    <button type="button" class="btn btn-primary"
                            <sec:authorize access="!hasRole('ROLE_master@chargeManagement_UPDATE')">
                                disabled="disabled"
                            </sec:authorize>
                            onclick="update_charge_modal()">Save Changes
                    </button>
                </div>
            </form>
        </div><!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- End modal -->
