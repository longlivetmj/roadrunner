<%@ include file="/WEB-INF/jsp/include.jsp" %>
<div class="col-md-12">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">Department Details</h3>
            <div class="btn-group pull-right">
                <button class="btn btn-danger dropdown-toggle" data-toggle="dropdown"><i class="fa fa-bars"></i> Export
                    Data
                </button>
                <ul class="dropdown-menu">

                    <li><a href="#"
                           onClick="$('#departmentDataSearch').tableExport({type:'excel',escape:'false'});"><img
                            src='../../../../theme/img/icons/xls.png' width="24"/> XLS</a></li>
                    <li><a href="#"
                           onClick="$('#departmentDataSearch').tableExport({type:'doc',escape:'false'});"><img
                            src='../../../../theme/img/icons/word.png' width="24"/> Word</a></li>
                    <li><a href="#"
                           onClick="$('#departmentDataSearch').tableExport({type:'pdf',escape:'false'});"><img
                            src='../../../../theme/img/icons/pdf.png' width="24"/> PDF</a></li>
                </ul>
            </div>
        </div>
        <div class="panel-body">
            <table id="departmentDataSearch" class="table datatable">
                <thead>
                <tr>
                    <th>Department Code</th>
                    <th>Department Name</th>
                    <th>Module</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${departmentListDB}" var="department">
                    <tr onclick="load_department_data_to_modal(${department.departmentSeq})" class="clickable" id="tr_${department.departmentSeq}">
                        <td>${department.departmentCode}</td>
                        <td>${department.departmentName}</td>
                        <td>${department.module.moduleName}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

