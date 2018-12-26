<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<script type="text/javascript" src="../../../customjs/fleet/vehicleTracking.js"></script>
<div class="page-title">
    <h3><span class="fa fa-location-arrow"></span>Vehicle Tracking</h3>
</div>
<!-- END PAGE TITLE -->
<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-body">
                <div class="row">
                    <form class="form-horizontal" method="post" name="search" id="search">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Vehicle No</label>
                                <div class="col-md-8 col-xs-12">
                                    <input type="text"
                                           class="form-control"
                                           id="search_vehicleNo"
                                           name="vehicleNo"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Vehicle Type</label>
                                <div class="col-md-8 col-xs-12">
                                    <select name="vehicleTypeSeq"
                                            id="search_vehicleTypeSeq"
                                            class="form-control ajax-select"
                                            data-validate="true"
                                            data-abs-ajax-type="get"
                                            data-abs-request-delay="500"
                                            data-key="vehicleTypeSeq"
                                            data-value="vehicleTypeName"
                                            title="Select and begin typing"
                                            data-abs-ajax-url="fleet/vehicleTracking/findVehicleType"
                                            data-live-search="true">
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label" for="customerSeq">Customer</label>
                                <div class="col-md-8 col-xs-12">
                                    <select name="customerSeq" id="customerSeq"
                                            class="form-control ajax-select"
                                            data-validate="true"
                                            data-shipper-consignee
                                            data-abs-ajax-type="get"
                                            data-abs-request-delay="500"
                                            data-key="stakeholderSeq"
                                            data-value="stakeholderName"
                                            data-subText="stakeholderCode"
                                            data-abs-ajax-url="transport/vehicleAssignment/findCustomer"
                                            data-live-search="true">
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="col-md-3 control-label">Transporter</label>
                                <div class="col-md-8 col-xs-12">
                                    <select data-live-search="true"
                                            name="stakeholderSeq" id="search_stakeholderSeq"
                                            data-validate="true"
                                            aria-required="true"
                                            class="form-control ajax-select stakeholderSeq"
                                            data-abs-ajax-type="get"
                                            data-abs-request-delay="500"
                                            data-key="stakeholderSeq"
                                            data-value="stakeholderName"
                                            data-subText="stakeholderCode"
                                            title="Select and begin typing"
                                            data-abs-ajax-url="fleet/vehicleTracking/findTransporter">
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Status</label>
                                <div class="col-md-8 col-xs-12">
                                    <select name="status" id="search_status"
                                            class="form-control select" data-validate="true"
                                            data-live-search="false">
                                        <c:forEach items="${statusList}" var="status">
                                            <option value="${status.trackingType}">${status.statusDescription}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-7">
                                    <button type="button" class="btn btn-primary pull-right" value="Search"
                                            onclick="search_vehicle()">Search
                                    </button>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class="panel panel-default" class="row">
            <div id="map" class="col-md-12" style="min-height: 500px"></div>
        </div>
    </div>
</div>


