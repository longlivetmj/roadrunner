<%--
  Created by IntelliJ IDEA.
  User: Udaya-Ehl
  Date: 7/1/2017
  Time: 10:54 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/jsp/include.jsp" %>

<input type="hidden"
       name="reportSeq"
       id="reportSeq"
       rv-value="reportDetail.reportSeq"
       value="${reportDetail.reportSeq}"/>
<input type="hidden"
       name="documentLinkSeq"
       id="documentLinkSeq"
       rv-value="reportDetail.documentLinkSeq"
       value="${documentLinkSeq}"/>
<input type="hidden"
       name="companyProfileSeq"
       id="companyProfileSeq"
       rv-value="reportDetail.companyProfileSeq"
       value="${reportDetail.companyProfileSeq}"/>
<input type="hidden"
       name="status"
       id="status"
       rv-value="reportDetail.status"
       value="${reportDetail.status}"/>

<div class="panel panel-success">
    <div class="panel-heading">
        <h3 class="panel-title">Basic Details</h3>
    </div>
    <div class="panel-body" id="jobDetails">
        <div class="row push-down-10">
            <div class="col-md-12">
                <div class="col-md-6"></div>
                <div class="col-md-6 operations" style="display: none">
                    <div class="col-md-4"></div>
                    <div class="col-md-4">
                        <button type="button" class="btn btn-success alwaysEnabled pull-right"
                                <sec:authorize
                                        access="!hasRole('ROLE_config@reportConfigManagement_VIEW')">
                                    disabled="disabled"
                                </sec:authorize>
                                onclick="load_new_report_format_page()">
                            New
                        </button>
                    </div>
                    <div class="col-md-4">
                        <button type="button" class="btn btn-danger"
                                id="deleteReportFormat"
                                <sec:authorize
                                        access="!hasRole('ROLE_config@reportConfigManagement_DELETE')">
                                    disabled="disabled"
                                </sec:authorize>
                                <c:if test="${reportDetail.isDefaultReport ne null ||   reportDetail.reportCategoryTypeSeq eq 1}">
                                    <c:if test="${adminUser ne true}">
                                        disabled="disabled"
                                    </c:if>
                                </c:if>
                                <c:if test="${adminUser eq true}">
                                    <c:if test="${reportDetail.isDefaultReport ne null}">
                                        disabled="disabled"
                                    </c:if>
                                </c:if>
                                data-toggle=confirmation
                                data-btn-ok-label="Continue"
                                data-btn-ok-icon="glyphicon glyphicon-share-alt"
                                data-btn-ok-class="btn-success"
                                data-btn-cancel-label="Cancel"
                                data-btn-cancel-icon="glyphicon glyphicon-ban-circle"
                                data-btn-cancel-class="btn-danger"
                                data-title="Deleting Report Format"
                                data-content="Are you sure you want to Delete this Report Format ? "
                                data-id="create"
                                data-on-confirm="delete_report_format">
                            Delete
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <div class="row push-down-10">
            <div class="col-md-12">
                <div class="col-md-6">
                    <div class="form-group">
                        <label class="col-md-4 col-xs-12 control-label">Report
                            Name</label>
                        <div class="col-md-8 col-xs-12">
                            <input type="text"
                                   rv-value="reportDetail.reportName"
                                   class="form-control"
                                   id="reportName"
                                   name="reportName"
                                   value="${reportDetail.reportName ne null ? reportDetail.reportName : ''}"/>
                        </div>
                    </div>
                </div>
                <div class="col-md-6"></div>
            </div>
        </div>
        <div class="row push-down-10">
            <div class="col-md-12">
                <div class="col-md-6">
                    <div class="form-group">
                        <label class="col-md-4 col-xs-12 control-label">Report Category</label>
                        <div class="col-md-8 col-xs-12">
                            <select name="reportCategoryTypeSeq"
                                    id="reportCategoryTypeSeq"
                                    rv-value="reportDetail.reportCategoryTypeSeq"
                                    class="form-control select"
                                    data-live-search="true">
                                <c:forEach items="${reportTypeCategoryList}" var="reportTypeCategory">
                                    <option value="${reportTypeCategory.reportCategoryTypeSeq}" ${reportTypeCategory.reportCategoryTypeSeq eq reportDetail.reportCategoryTypeSeq ?'selected' : ''}>${reportTypeCategory.reportCategoryName}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="col-md-6"></div>
            </div>
        </div>
        <div class="row">
            <div></div>
            <div class="col-md-12">
                <div class="form-group">
                    <div class="col-md-12">
                        <label class="col-md-2 col-xs-12 control-label">Column Name</label>
                        <div class="col-md-8 col-xs-12" style="font-size: 13px">
                            <c:set var="counter" value="0"/>
                            <c:forEach items="${dynamicJasperInfoList}" var="dynamicJasperInfo">
                                <div class="col-md-4">
                                    <label>
                                        <input type="checkbox"
                                               class="CB${dynamicJasperInfo.dynamicJasperInfoSeq}"
                                               name="reportDjInfoMappingList[${counter}].dynamicJasperInfoSeq"
                                               id="dynamicJasperInfo${dynamicJasperInfo.dynamicJasperInfoSeq}"
                                                <c:forEach items="${dynamicJasperInfoColumnList}"
                                                           var="dynamicJasperInfoColumn">
                                                    <c:if test="${dynamicJasperInfo.dynamicJasperInfoSeq eq dynamicJasperInfoColumn.dynamicJasperInfoSeq}">
                                                        checked
                                                    </c:if>
                                                </c:forEach>
                                               value="${dynamicJasperInfo.dynamicJasperInfoSeq}"/>
                                            ${dynamicJasperInfo.displayName}
                                    </label>
                                </div>
                                <c:set var="counter" value="${counter +1}"/>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row"></div>
    </div>
</div>

<div class="form-group">
    <div class="col-sm-offset-2 col-sm-10">
        <button type="button"
                class="btn btn-primary pull-right createOperation"
                value="create Container Details"
                <sec:authorize
                        access="!hasRole('ROLE_config@reportConfigManagement_CREATE')">
                    disabled="disabled"
                </sec:authorize>
                onclick="create_new_report_format()">Create
        </button>
    </div>
</div>

<div class="form-group updateOperation" style="display: none">
    <div class="col-sm-offset-2 col-sm-10 removeFromModal">
        <button type="button" class="btn btn-primary pull-right updateOperation"
                value="Update Container Details"
                <sec:authorize
                        access="!hasRole('ROLE_config@reportConfigManagement_UPDATE')">
                    disabled="disabled"
                </sec:authorize>
                <c:if test="${reportDetail.isDefaultReport ne null || reportDetail.reportCategoryTypeSeq eq 1}">
                    <c:if test="${adminUser ne true}">
                        disabled="disabled"
                    </c:if>
                </c:if>
                <c:if test="${adminUser eq true}">
                    <c:if test="${reportDetail.isDefaultReport ne null}">
                        disabled="disabled"
                    </c:if>
                </c:if>
                onclick="update_new_report_format()">Update
        </button>
    </div>
    <div class="col-md-12 push-down-25">
        <div class="col-md-6">
            <div class="col-md-12">
                <div class="form-group">
                    <label class="col-md-4 text-right">Created By :</label>
                    <label class="col-md-8"
                           rv-text="reportDetail.createdBy">${reportDetail.createdBy}</label>
                </div>
            </div>
            <div class="col-md-12">
                <div class="form-group">
                    <label class="col-md-4 text-right">Created Date :</label>
                    <label class="col-md-8"
                           rv-text="reportDetail.createdDate">${reportDetail.createdDate}</label>
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <div class="col-md-12">
                <div class="form-group">
                    <label class="col-md-4 text-right">Last Modified By :</label>
                    <label class="col-md-8"
                           rv-text="reportDetail.lastModifiedBy">${reportDetail.lastModifiedBy}</label>
                </div>
            </div>
            <div class="col-md-12">
                <div class="form-group">
                    <label class="col-md-4 text-right">Last Modified Date :</label>
                    <label class="col-md-8"
                           rv-text="reportDetail.lastModifiedDate">${reportDetail.lastModifiedDate}</label>
                </div>
            </div>
        </div>
    </div>
</div>