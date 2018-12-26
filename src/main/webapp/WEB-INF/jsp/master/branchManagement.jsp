<%@ include file="/WEB-INF/jsp/include.jsp" %>
<script type="text/javascript" src="../../../customjs/master/branchManagement.js"></script>
<div class="page-title">
    <h3><span class="fa fa-road"></span>Branch Management</h3>
</div>

<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default tabs">
            <ul class="nav nav-tabs" role="tablist">
                <li class="active"><a href="#tab-first" role="tab" data-toggle="tab">Branch Creation</a></li>
                <li><a href="#tab-second" role="tab" data-toggle="tab">Branch Search</a></li>
            </ul>
            <div class="panel-body tab-content">
                <div class="tab-pane active" id="tab-first">

                    <div class="col-md-12">
                        <form class="form-horizontal branchForm" method="post" id="create">
                            <div class="form-group removeFromModal">
                                <div class="col-md-7">
                                    <h4 class="subTitle">Branch Details</h4>
                                </div>

                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <div class="col-md-5 operations" style="display: none">
                                        <button type="button" class="btn btn-success"
                                                onclick="new_form(formTemplate, 'update','create')">
                                            New
                                        </button>
                                        <button type="button" class="btn btn-danger"
                                                <sec:authorize
                                                        access="!hasRole('ROLE_master@branchManagement_DELETE')">
                                                    disabled="disabled"
                                                </sec:authorize>
                                                data-toggle=confirmation
                                                data-btn-ok-label="Continue"
                                                data-btn-ok-icon="glyphicon glyphicon-share-alt"
                                                data-btn-ok-class="btn-success"
                                                data-btn-cancel-label="Cancel"
                                                data-btn-cancel-icon="glyphicon glyphicon-ban-circle"
                                                data-btn-cancel-class="btn-danger"
                                                data-title="Deleting Branch"
                                                data-content="Are you sure you want to Delete this Branch? "
                                                data-id="update"
                                                data-on-confirm="delete_branch">
                                            Delete
                                        </button>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 control-label">Bank Name</label>
                                    <div class="col-md-6 col-xs-12">
                                        <select name="bankSeq"
                                                id="bankSeq"
                                                class="form-control ajax-select"
                                                data-validate="true"
                                                data-abs-ajax-type="get"
                                                data-abs-request-delay="500"
                                                data-key="bankSeq"
                                                data-value="bankName"
                                                data-error="Please Select Bank Name"
                                                data-subText="bankCode"
                                                title="Select and begin typing"
                                                data-abs-ajax-url="master/branchManagement/findBank"
                                                data-live-search="true" placeholder="Bank" required>
                                            <option rv-value="branch.bankSeq" rv-text="branch.bank.bankName"  rv-setselectedattr="branch.bankSeq"></option>
                                        </select>
                                        <span class="help-block">Required</span>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Branch Code</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" maxlength="10" class="form-control" name="branchCode"
                                               rv-value="branch.branchCode"
                                               id="branchCode" data-error="Please Provide a Valid Branch Code"
                                               pattern="^([0-9]*\s+)*[0-9]*$"
                                               required/>
                                        <span class="help-block">Required, Branch Code</span>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Branch Name</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" maxlength="30" class="form-control" name="branchName"
                                               rv-value="branch.branchName"
                                               id="branchName" pattern="^[a-zA-Z0-9\s]+$"
                                               data-error="Please Provide a Valid Branch Name"
                                               required/>
                                        <span class="help-block">Required, Branch Name</span>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Status</label>
                                    <div class="col-md-6 col-xs-12">
                                        <select name="status" id="status" rv-value="branch.status"
                                                class="form-control select" data-validate="true" required
                                                data-live-search="true" placeholder="Status">
                                            <c:forEach items="${statusList}" var="status">
                                                <option value="${status.statusSeq}">${status.status}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group createOperation removeFromModal">
                                    <div class="col-sm-offset-2 col-sm-7">
                                        <button type="button" class="btn btn-primary pull-right" value="addBranch"
                                                <sec:authorize
                                                        access="!hasRole('ROLE_master@branchManagement_CREATE')">
                                                    disabled="disabled"
                                                </sec:authorize>
                                                onclick="create_branch()">Create
                                        </button>
                                    </div>
                                </div>
                                <div class="form-group updateOperation" style="display: none">
                                    <div class="form-group">
                                        <label class="col-md-3 text-right">Created By</label>
                                        <label class="col-md-8" rv-text="branch.createdBy"></label>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-3 text-right">Created Date</label>
                                        <label class="col-md-8" rv-text="branch.createdDate"></label>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-3 text-right">Last Modified By</label>
                                        <label class="col-md-8" rv-text="branch.lastModifiedBy"></label>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-3 text-right">Last Modified Date</label>
                                        <label class="col-md-8" rv-text="branch.lastModifiedDate"></label>
                                    </div>
                                    <div class="col-sm-offset-2 col-sm-7 removeFromModal">
                                        <button type="button" class="btn btn-primary pull-right"
                                                value="Update Branch"
                                                <sec:authorize
                                                        access="!hasRole('ROLE_master@branchManagement_UPDATE')">
                                                    disabled="disabled"
                                                </sec:authorize>
                                                onclick="update_branch()">Update
                                        </button>
                                    </div>
                                </div>
                                <input type="hidden" name="branchSeq" id="branchSeq" value=""
                                       rv-value="branch.branchSeq"/>
                                <input type="hidden" name="companyProfileSeq" value="${companyProfileSeq}" rv-value="branch.companyProfileSeq"/>

                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <h6 class="subTitle">Contact Details</h6>
                                </div>

                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Address 1</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" class="form-control"
                                               name="addressBook.address1"
                                               rv-value="branch.addressBook.address1"
                                               maxlength="100"
                                               id="address1"/>
                                    </div>

                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Address 2</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" class="form-control"
                                               name="addressBook.address2"
                                               rv-value="branch.addressBook.address2"
                                               maxlength="100"
                                               id="address2"/>
                                    </div>

                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">City</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" class="form-control" name="addressBook.city"
                                               rv-value="branch.addressBook.city" maxlength="50"
                                               id="city"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">State</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" class="form-control" name="addressBook.state"
                                               rv-value="branch.addressBook.state" maxlength="50"
                                               id="state"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Zip Code</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" class="form-control" name="addressBook.zip" maxlength="10"
                                               rv-value="branch.addressBook.zip"
                                               id="zip"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 control-label">Country</label>
                                    <div class="col-md-6 col-xs-12">
                                        <select name="addressBook.countrySeq" id="addressBook.countrySeq"
                                                class="form-control ajax-select"
                                                data-validate="true" required
                                                data-abs-ajax-type="get"
                                                data-error="Please Select Country"
                                                data-abs-request-delay="500"
                                                title="Select and begin typing"
                                                data-key="countrySeq"
                                                data-value="countryName"
                                                data-subText="countryCode"
                                                data-abs-ajax-url="master/branchManagement/findCountry"
                                                data-live-search="true" placeholder="Country">
                                            <option rv-value="branch.addressBook.countrySeq" rv-text="branch.addressBook.country.countryName"  rv-setselectedattr="branch.addressBook.countrySeq"></option>
                                        </select>
                                        <span class="help-block">Required</span>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Telephone</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="tel" class="form-control" name="addressBook.telephone"
                                               rv-value="branch.addressBook.telephone"
                                               pattern="[0-9]{10}|[0-9]{12}|[0-9]{15}"
                                               data-error="Please Provide a Valid Telephone Number" maxlength="15"
                                               id="telephone"/>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Telephone Extension</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" class="form-control" name="addressBook.telephoneExtension"
                                               rv-value="branch.addressBook.telephoneExtension"
                                               data-error="Please Provide a Numeric Ext Code" maxlength="5"
                                               pattern="^([0-9]*\s+)*[0-9]*$"
                                               id="telephoneExtension"/>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Mobile</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="tel" class="form-control" name="addressBook.mobile"
                                               rv-value="branch.addressBook.mobile"
                                               pattern="[0-9]{10}|[0-9]{12}|[0-9]{15}"
                                               data-error="Please Provide a Valid Mobile Number" maxlength="15"
                                               id="mobile"/>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Fax</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="tel" class="form-control" name="addressBook.fax"
                                               rv-value="branch.addressBook.fax"
                                               pattern="[0-9]{10}|[0-9]{12}|[0-9]{15}"
                                               data-error="Please Provide a Valid Fax Number" maxlength="15"
                                               id="fax"/>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Email</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="email" name="addressBook.email" class="form-control"
                                               rv-value="branch.addressBook.email"
                                               data-error="Please Provide a Valid E-mail Address"
                                               pattern="/^[^\s@]+@[^\s@]+\.[^\s@]+$/"
                                               id="email"/>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Web Site</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="url" name="addressBook.website" class="form-control"
                                               placeholder="http://"
                                               rv-value="branch.addressBook.website" maxlength="100"
                                               pattern="(http:\/\/|https:\/\/).+([a-z]+)"
                                               data-error="Please Provide a Valid Website Url"
                                               id="website"/>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <input type="hidden" name="addressBook.addressBookSeq" rv-value="branch.addressBook.addressBookSeq"/>
                                <input type="hidden" name="addressBookSeq" rv-value="branch.addressBookSeq"/>

                            </div>
                        </form>
                    </div>

                </div>
                <div class="tab-pane" id="tab-second">
                    <div class="row">
                        <div class="col-md-6">
                            <form class="form-horizontal" method="post" name="search" id="search">
                                <div class="form-group">
                                    <h4 class="subTitle">Search Branch</h4>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Branch Code</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" class="form-control" name="branchCode"
                                               id="search_branchCode"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Bank Name</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" class="form-control" name="bankName"
                                               id="search_bankName"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Branch Name</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" class="form-control" name="branchName"
                                               id="search_branchName"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-sm-offset-2 col-sm-7">
                                        <button type="button" class="btn btn-primary pull-right"
                                                value="loadBranchData"
                                                <sec:authorize
                                                        access="!hasRole('ROLE_master@branchManagement_VIEW')">
                                                    disabled="disabled"
                                                </sec:authorize>
                                                onclick="load_branch_table_data()">Search
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="form-group" id="loadBranchListData"></div>
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
                <h4 class="modal-title">Branch Details</h4>
            </div>
            <form class="form-horizontal" method="post" name="modal" id="modal">
                <div class="modal-body panel">

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-danger"
                            <sec:authorize
                                    access="!hasRole('ROLE_master@branchManagement_DELETE')">
                                disabled="disabled"
                            </sec:authorize>
                            data-toggle=confirmation
                            data-btn-ok-label="Continue" data-btn-ok-icon="glyphicon glyphicon-share-alt"
                            data-btn-ok-class="btn-success"
                            data-btn-cancel-label="Cancel" data-btn-cancel-icon="glyphicon glyphicon-ban-circle"
                            data-btn-cancel-class="btn-danger"
                            data-title="Deleting Branch" data-content="Are you sure you want to Delete this Branch? "
                            data-id="modal"
                            data-on-confirm="delete_branch">
                        Delete
                    </button>
                    <button type="button" class="btn btn-primary"
                            <sec:authorize access="!hasRole('ROLE_master@branchManagement_UPDATE')">
                                disabled="disabled"
                            </sec:authorize>
                            onclick="update_branch_popup()">Save Changes
                    </button>

                </div>
            </form>
        </div><!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
