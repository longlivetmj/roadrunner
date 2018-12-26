<%@ include file="/WEB-INF/jsp/include.jsp" %>
<script type="text/javascript" src="../../../customjs/master/salesPersonManagement.js"></script>
<div class="page-title">
    <h3><span class="fa fa-road"></span>Sales Person Management</h3>
</div>

<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default tabs">
            <ul class="nav nav-tabs" role="tablist">
                <li class="active"><a href="#tab-first" role="tab" data-toggle="tab">Sales Person Creation</a></li>
                <li><a href="#tab-second" role="tab" data-toggle="tab">Sales Person Search</a></li>
            </ul>
            <div class="panel-body tab-content">
                <div class="tab-pane active" id="tab-first">
                    <div class="col-md-12">
                        <form class="form-horizontal salesPersonForm" method="post" id="create">
                            <div class="form-group removeFromModal">
                                <div class="col-md-7">
                                    <h4 class="subTitle">Sales Person Details</h4>
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
                                                        access="!hasRole('ROLE_master@salesPersonManagement_DELETE')">
                                                    disabled="disabled"
                                                </sec:authorize>
                                                data-toggle=confirmation
                                                data-btn-ok-label="Continue"
                                                data-btn-ok-icon="glyphicon glyphicon-share-alt"
                                                data-btn-ok-class="btn-success"
                                                data-btn-cancel-label="Cancel"
                                                data-btn-cancel-icon="glyphicon glyphicon-ban-circle"
                                                data-btn-cancel-class="btn-danger"
                                                data-title="Deleting Sales Person"
                                                data-content="Are you sure you want to Delete this Sales Person ? "
                                                data-id="update"
                                                data-on-confirm="delete_sales_person">
                                            Delete
                                        </button>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Sales Person Code</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" class="form-control" name="salesPersonCode"
                                               maxlength="15"
                                               rv-value="salesPerson.salesPersonCode"
                                               id="salesPersonCode"
                                               data-error="Please Provide a Valid Sales Person Code"
                                               required/>
                                        <span class="help-block">Required, Sales Person Code</span>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Sales Person Name</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" class="form-control" name="salesPersonName"
                                               maxlength="50"
                                               rv-value="salesPerson.salesPersonName"
                                               id="salesPersonName"
                                               data-error="Please Provide a Valid Sales Person Name"
                                               required/>
                                        <span class="help-block">Required, Sales Person Name</span>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 control-label">Stakeholder</label>
                                    <div class="col-md-6 col-xs-12">
                                        <select name="stakeholderSeq" id="stakeholderSeq"
                                                class="form-control ajax-select"
                                                data-validate="true"
                                                data-abs-ajax-type="get"
                                                data-error="Please Select Stakeholder"
                                                data-abs-request-delay="500"
                                                data-key="stakeholderSeq"
                                                data-value="stakeholderName"
                                                data-subText="stakeholderCode"
                                                title="Select and begin typing"
                                                data-abs-ajax-url="master/salesPersonManagement/findStakeholder"
                                                data-live-search="true"
                                                placeholder="Stakeholder"
                                                required>
                                            <option rv-value="salesPerson.stakeholderSeq"
                                                    rv-text="salesPerson.stakeholder.stakeholderName"
                                                    rv-setselectedattr="salesPerson.stakeholderSeq"></option>
                                            <span class="help-block">Required, Stakeholder</span>
                                        </select>
                                        <span class="help-block">Required</span>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Status</label>
                                    <div class="col-md-6 col-xs-12">
                                        <select name="status" id="status" rv-value="salesPerson.status"
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
                                        <button type="button" class="btn btn-primary pull-right"
                                                value="addWarehouse"
                                                <sec:authorize
                                                        access="!hasRole('ROLE_master@salesPersonManagement_CREATE')">
                                                    disabled="disabled"
                                                </sec:authorize>
                                                onclick="create_sales_person()">Create
                                        </button>
                                    </div>
                                </div>


                                <div class="form-group updateOperation" style="display: none">
                                    <div class="form-group">
                                        <label class="col-md-3 text-right">Created By</label>
                                        <label class="col-md-8" rv-text="salesPerson.createdBy"></label>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-3 text-right">Created Date</label>
                                        <label class="col-md-8" rv-text="salesPerson.createdDate"></label>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-3 text-right">Last Modified By</label>
                                        <label class="col-md-8 " rv-text="salesPerson.lastModifiedBy"></label>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-3 text-right">Last Modified Date</label>
                                        <label class="col-md-8 " rv-text="salesPerson.lastModifiedDate"></label>
                                    </div>
                                    <div class="col-sm-offset-2 col-sm-7 removeFromModal">
                                        <button type="button" class="btn btn-primary pull-right"
                                                value="Update Warehouse"
                                                <sec:authorize
                                                        access="!hasRole('ROLE_master@salesPersonManagement_UPDATE')">
                                                    disabled="disabled"
                                                </sec:authorize>
                                                onclick="update_sales_person()">Update
                                        </button>
                                    </div>
                                </div>
                                <input type="hidden" name="salesPersonSeq" id="salesPersonSeq" value=""
                                       rv-value="salesPerson.salesPersonSeq"/>
                                <input type="hidden" name="companyProfileSeq" value="${companyProfileSeq}" rv-value="salesPerson.companyProfileSeq"/>
                            </div>

                            <div class="col-md-6">
                                <div class="form-group">
                                    <h6 class="subTitle">Contact Details</h6>
                                </div>

                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Address 1</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" class="form-control" name="addressBook.address1"
                                               rv-value="salesPerson.addressBook.address1" maxlength="100"
                                               id="address1"/>
                                    </div>

                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Address 2</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" class="form-control" name="addressBook.address2"
                                               rv-value="salesPerson.addressBook.address2" maxlength="100"
                                               id="address2"/>
                                    </div>

                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">City</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" class="form-control" name="addressBook.city"
                                               rv-value="salesPerson.addressBook.city" maxlength="50"
                                               id="city"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">State</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" class="form-control" name="addressBook.state"
                                               rv-value="salesPerson.addressBook.state" maxlength="50"
                                               id="state"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Zip Code</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" class="form-control" name="addressBook.zip"
                                               rv-value="salesPerson.addressBook.zip" maxlength="10"
                                               id="zip"/>
                                    </div>
                                </div>
                                <div class="form-group">

                                    <label class="col-md-3 col-xs-12 control-label">Country</label>
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
                                                data-abs-ajax-url="master/salesPersonManagement/findCountry"
                                                data-live-search="true" placeholder="Country">
                                            <option data-hidden="true" rv-value="salesPerson.addressBook.countrySeq"
                                                    rv-text="salesPerson.addressBook.country.countryName"
                                                    rv-setselectedattr="salesPerson.addressBook.countrySeq"></option>
                                        </select>
                                        <span class="help-block">Required</span>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>


                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Telephone</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="tel" class="form-control" name="addressBook.telephone"
                                               rv-value="salesPerson.addressBook.telephone"
                                               pattern="[0-9]{10}|[0-9]{12}|[0-9]{15}"
                                               data-error="Please Provide a Valid Telephone Number" maxlength="15"
                                               id="telephone"/>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Telephone Extension</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" class="form-control"
                                               name="addressBook.telephoneExtension"
                                               rv-value="salesPerson.addressBook.telephoneExtension"
                                               data-error="Please Provide a Numeric Ext Code "
                                               pattern="^([0-9]*\s+)*[0-9]*$"
                                               maxlength="5"
                                               id="telephoneExtension"/>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Mobile</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="tel" class="form-control" name="addressBook.mobile"
                                               rv-value="salesPerson.addressBook.mobile"
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
                                               rv-value="salesPerson.addressBook.fax"
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
                                               rv-value="salesPerson.addressBook.email" maxlength="50"
                                               data-error="Please Provide a Valid E-mail Address"
                                               pattern="^[^\s@]+@[^\s@]+\.[^\s@]+$"
                                               id="email"/>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Web Site</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="url" name="addressBook.website" class="form-control"
                                               placeholder="http://" maxlength="100"
                                               rv-value="salesPerson.addressBook.website"
                                               pattern="(http:\/\/|https:\/\/).+([a-z]+)"

                                               data-error="Please Provide a Valid Website Url"
                                               id="website"/>
                                    </div>
                                    <div class="help-block with-errors"></div>
                                </div>
                            </div>
                            <input type="hidden" name="addressBook.addressBookSeq" rv-value="salesPerson.addressBook.addressBookSeq"/>
                            <input type="hidden" name="addressBookSeq" rv-value="salesPerson.addressBookSeq"/>
                        </form>
                    </div>
                </div>


                <div class="tab-pane" id="tab-second">
                    <div class="row">
                        <div class="col-md-6">
                            <form class="form-horizontal" method="post" name="search" id="search">
                                <div class="form-group">
                                    <h4 class="subTitle">Search Sales Person</h4>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Sales Person Code</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" class="form-control" name="salesPersonCode"
                                               id="search_salesPersonCode"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 col-xs-12 control-label">Sales Person Name</label>
                                    <div class="col-md-6 col-xs-12">
                                        <input type="text" class="form-control" name="salesPersonName"
                                               id="search_salesPersonName"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-3 control-label">Stakeholder</label>
                                    <div class="col-md-6 col-xs-12">
                                        <select name="stakeholderSeq" id="search_stakeholderSeq"
                                                class="form-control ajax-select"
                                                data-validate="true"
                                                data-abs-ajax-type="get"
                                                data-error="Please Select Consortium"
                                                data-abs-request-delay="500"
                                                data-key="stakeholderSeq"
                                                data-value="stakeholderName"
                                                data-subText="stakeholderCode"
                                                title="Select and begin typing"
                                                data-abs-ajax-url="master/salesPersonManagement/findStakeholder"
                                                data-live-search="true"
                                                placeholder="Stakeholder">
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-sm-offset-2 col-sm-7">
                                        <button type="button" class="btn btn-primary pull-right"
                                                value="loadSalesPersonData"
                                                <sec:authorize
                                                        access="!hasRole('ROLE_master@salesPersonManagement_VIEW')">
                                                    disabled="disabled"
                                                </sec:authorize>
                                                onclick="load_sales_person_table_data()">Search
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="form-group" id="loadSalesPersonData"></div>
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
                <h4 class="modal-title">Sales Person Details</h4>
            </div>
            <form class="form-horizontal" method="post" name="modal" id="modal">
                <div class="form-group">
                    <div class="modal-body panel">

                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-danger"
                            <sec:authorize
                                    access="!hasRole('ROLE_master@salesPersonManagement_DELETE')">
                                disabled="disabled"
                            </sec:authorize>
                            data-toggle=confirmation
                            data-btn-ok-label="Continue" data-btn-ok-icon="glyphicon glyphicon-share-alt"
                            data-btn-ok-class="btn-success"
                            data-btn-cancel-label="Cancel" data-btn-cancel-icon="glyphicon glyphicon-ban-circle"
                            data-btn-cancel-class="btn-danger"
                            data-title="Deleting Sales Person"
                            data-content="Are you sure you want to Delete this Sales Person ? "
                            data-id="modal"
                            data-on-confirm="delete_sales_person">
                        Delete
                    </button>
                    <button type="button" class="btn btn-primary"
                            <sec:authorize access="!hasRole('ROLE_master@salesPersonManagement_UPDATE')">
                                disabled="disabled"
                            </sec:authorize>
                            onclick="update_sales_person_popup()">Save Changes
                    </button>

                </div>
            </form>
        </div><!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
