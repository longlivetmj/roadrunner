<%@ include file="/WEB-INF/jsp/include.jsp" %>
<div class="col-md-12">

    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">Customer Group Details</h3>
            <div class="btn-group pull-right">
                <button class="btn btn-danger dropdown-toggle" data-toggle="dropdown"><i class="fa fa-bars"></i> Export
                    Data
                </button>
                <ul class="dropdown-menu">
                    <li><a href="#"
                           onClick="$('#customerGroupDataSearch').tableExport({type:'csv',escape:'false'});"><img
                            src='../../../theme/img/icons/csv.png' width="24"/> CSV</a></li>
                    <li><a href="#"
                           onClick="$('#customerGroupDataSearch').tableExport({type:'txt',escape:'false'});"><img
                            src='../../../theme/img/icons/txt.png' width="24"/> TXT</a></li>
                    <li class="divider"></li>
                    <li><a href="#" onClick="$('#customerGroupDataSearch').tableExport({type:'excel',escape:'false'});"><img
                            src='../../../theme/img/icons/xls.png' width="24"/> XLS</a></li>
                    <li><a href="#"
                           onClick="$('#customerGroupDataSearch').tableExport({type:'doc',escape:'false'});"><img
                            src='../../../theme/img/icons/word.png' width="24"/> Word</a></li>
                    <li><a href="#"
                           onClick="$('#customerGroupDataSearch').tableExport({type:'pdf',escape:'false'});"><img
                            src='../../../theme/img/icons/pdf.png' width="24"/> PDF</a></li>
                </ul>
            </div>
        </div>
        <div class="panel-body">
            <table id="customerGroupDataSearch" class="table datatable">
                <thead>
                <tr>
                    <th>Customer Group Code</th>
                    <th>Customer Group Name</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${customerGroupListDB}" var="customerGroup">
                    <tr onclick="load__customer_group_popup(${customerGroup.customerGroupSeq})" class="clickable"  id="tr_${customerGroup.customerGroupSeq}">
                        <td>${customerGroup.customerGroupCode}</td>
                        <td>${customerGroup.customerGroupName}</td>

                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

