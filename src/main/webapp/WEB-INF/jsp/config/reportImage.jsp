<%@ include file="/WEB-INF/jsp/include.jsp" %>
<script type="text/javascript" src="../../../customjs/config/reportImage.js"></script>
<div class="page-title">
    <h3><span class="fa fa-user"></span>Report Image Uploading</h3>
</div>
<!-- END PAGE TITLE -->

<div class="row">
    <div class="col-md-12">

        <div class="panel panel-default tabs">
            <ul class="nav nav-tabs" role="tablist">
                <li class="active"><a href="#tab-first" role="tab" data-toggle="tab">Report Image</a></li>
            </ul>
            <div class="panel-body tab-content">
                <div class="tab-pane active" id="tab-first">
                    <div class="col-md-6">
                        <form class="form-horizontal" method="post" name="create" id="create"
                              enctype="multipart/form-data">
                            <div class="form-group">
                                <h4 class="subTitle">Upload Image</h4>
                            </div>
                            <div class="form-group">
                                <div class="col-md-12">
                                    <label class="col-md-3 col-xs-12 control-label">Report Image</label>
                                    <input type="file" lass="form-control" id="create_reportImage" name="reportImage"/>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-7">
                                    <button type="button" class="btn btn-primary pull-right" value="createReportImage"
                                            onclick="create_report_image()">Save
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="col-md-6">
                        <form class="form-horizontal" method="post" name="update" id="update"
                              enctype="multipart/form-data">
                            <div class="form-group">
                                <h4 class="subTitle">Update User</h4>
                            </div>
                            <div class="form-group">
                                <label class="col-md-3 col-xs-12 control-label">Report Images</label>
                                <div class="col-md-6 col-xs-12">
                                    <select name="reportImageSeq" id="update_reportImageSeq" class="form-control select"
                                            data-live-search="true" onchange="load_report_image()">
                                        <option value="0">None</option>
                                        <c:forEach items="${reportImageList}" var="reportImage">
                                            <option value="${reportImage.reportImageSeq}">${reportImage.fileName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-md-12">
                                    <label class="col-md-3 col-xs-12 control-label">Report Image</label>
                                    <input type="file" lass="form-control" id="update_reportImage" name="reportImage"/>
                                </div>
                                <div class="help-block with-errors"></div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-7">
                                    <button type="button" class="btn btn-primary pull-right" value="updateUser"
                                            onclick="update_report_image()">update
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>

