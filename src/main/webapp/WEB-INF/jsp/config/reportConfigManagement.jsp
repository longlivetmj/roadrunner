<%--
  Created by IntelliJ IDEA.
  User: Udaya-Ehl
  Date: 6/27/2017
  Time: 12:59 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<div class="page-title">
    <h3><span class="fa fa-users"></span>Report Config</h3>
</div>
<script type="text/javascript" src="../../../customjs/config/reportConfigManagement.js"></script>
<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default tabs">
            <ul class="nav nav-tabs tablist">
                <li class="active"><a href="#tab-first" class="tab" role="tab" data-toggle="tab">Report Planning</a>
                </li>
            </ul>
            <div class="panel-body tab-content">
                <div class="tab-pane active" id="tab-first">
                    <div class="col-md-12">
                        <div class=" form-group removeFromModal">
                            <div class="col-md-8">
                                <h4 class="subTitle">Report Config Details</h4>
                            </div>
                        </div>
                        <div class="col-md-12 push-down-30">
                            <form class="form-horizontal searchForm" method="post" id="search">
                                <div class="panel panel-warning">
                                    <div class="panel-heading collapsed" data-toggle="collapse" href="#sop_info">
                                        <h3 class="panel-title">Search Report Format Details</h3>
                                        <ul class="panel-controls">
                                            <li>
                                                <a href="#sop_info" class="panel-collapse">
                                            <span class="fa fa-angle-down"></span>
                                                </a>
                                            </li>
                                        </ul>
                                    </div>
                                    <div class="panel-body">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <div class="col-md-4">
                                                    <div class="form-group">
                                                        <label class="col-md-3 col-xs-12 control-label">Report
                                                            Name</label>
                                                        <div class="col-md-9 col-xs-12">
                                                            <select name="reportSeq"
                                                                    id="search_reportSeq"
                                                                    data-live-search="true"
                                                                    class="form-control select"
                                                                    data-validate="true"
                                                                    placeholder="Report"
                                                            <%--onchange="search_report_format_Detail()">--%>>
                                                            <c:forEach items="${reportList}" var="report">
                                                                <option value="${report.reportSeq}">${report.reportName}</option>
                                                            </c:forEach>
                                                            </select>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-md-1">
                                                    <div class="form-group ">
                                                        <button type="button" class="btn btn-primary pull-right"
                                                                onclick="search_report_format_Detail()">Search
                                                        </button>
                                                    </div>
                                                </div>
                                                <div class="col-md-7"></div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </form>

                            <form class="form-horizontal reportFormatCreationForm" method="post" id="create" name="create">
                                <div id="reportConfigFormatDetail"></div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

