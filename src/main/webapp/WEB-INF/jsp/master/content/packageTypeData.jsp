<%@ include file="/WEB-INF/jsp/include.jsp" %>
<!-- START DEFAULT DATATABLE -->
<div class="col-md-12">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">Package Type Details</h3>
            <div class="btn-group pull-right">
                <button class="btn btn-danger dropdown-toggle" data-toggle="dropdown"><i class="fa fa-bars"></i>
                    Export Data
                </button>
                <ul class="dropdown-menu">
                    <li class="divider"></li>
                    <li><a href="#" onClick="$('#packageTypeTable').tableExport({type:'excel',escape:'false'});"><img
                            src='../../../../theme/img/icons/xls.png' width="24"/> XLS</a></li>
                    <li><a href="#" onClick="$('#packageTypeTable').tableExport({type:'doc',escape:'false'});"><img
                            src='../../../../theme/img/icons/word.png' width="24"/> Word</a></li>
                    <li><a href="#" onClick="$('#packageTypeTable').tableExport({type:'pdf',escape:'false'});"><img
                            src='../../../../theme/img/icons/pdf.png' width="24"/> PDF</a></li>
                </ul>
            </div>
        </div>
        <div class="panel-body">
            <table class="table datatable" id="packageTypeTable">
                <thead>
                <tr>
                    <th>Package Type Name</th>
                    <th>Package Type Code</th>
                    <th>Asycuda Code</th>
                    <th>Description</th>
                    <th>Status</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${packageTypeManagementList}" var="packageType">
                    <tr onclick="load_package_type_data_to_modal(${packageType.packageTypeSeq})"  class="clickable" id="tr_${packageType.packageTypeSeq}">
                        <td>${packageType.packageTypeName}</td>
                        <td>${packageType.packageTypeCode}</td>
                        <td>${packageType.asycudaCode}</td>
                        <td>${packageType.description}</td>
                        <td>${packageType.statusDescription}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<!-- END DEFAULT DATATABLE -->