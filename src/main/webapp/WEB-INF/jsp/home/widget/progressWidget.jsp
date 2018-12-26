<%@ include file="/WEB-INF/jsp/include.jsp" %>
<div class="panel panel-default">
    <div class="panel-heading">
        <div class="panel-title-box">
            <h3>${widget.heading}</h3>
            <span>${widget.subHeading}</span>
        </div>
        <ul class="panel-controls" style="margin-top: 2px;">
            <li><a href="#" class="panel-fullscreen"><span class="fa fa-expand"></span></a></li>
            <li><a href="#" class="panel-refresh"><span class="fa fa-refresh"></span></a></li>
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="fa fa-cog"></span></a>
                <ul class="dropdown-menu">
                    <li><a href="#" class="panel-collapse"><span class="fa fa-angle-down"></span> Collapse</a></li>
                    <li><a href="#" class="panel-remove"><span class="fa fa-times"></span> Remove</a></li>
                </ul>
            </li>
        </ul>
    </div>
    <div class="panel-body panel-body-table">
        <div class="table-responsive">
            <table class="table table-bordered table-striped">
                <tbody>
                <c:forEach items="${widget.keyValuePairCustomList}" var="entry">
                    <tr>
                        <td width="55%"><h6 class="font-12">${entry.key}</h6></td>
                        <td width="20%"><span class="label label-${entry.color}">${entry.value}</span></td>
                        <td width="25%">
                            <div class="progress progress-small progress-striped active">
                                <div class="progress-bar progress-bar-${entry.color}" role="progressbar"
                                     aria-valuenow="${entry.value}"
                                     aria-valuemin="0"
                                     aria-valuemax="${widget.maxCount}"
                                     style="width: ${entry.value eq widget.maxCount ? 100 : (entry.value / widget.maxCount * 100)}%;">
                                     ${entry.value eq widget.maxCount ? 100 : (entry.value / widget.maxCount * 100)}%</div>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</div>
<!-- END PROJECTS BLOCK -->
