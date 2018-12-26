<%@ include file="/WEB-INF/jsp/include.jsp" %>
<div class="col-md-12">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">Item Details</h3>
            <div class="btn-group pull-right">
                <button class="btn btn-danger dropdown-toggle" data-toggle="dropdown"><i class="fa fa-bars"></i> Export
                    Data
                </button>
                <ul class="dropdown-menu">
                    <li><a href="#" onClick="$('#itemList').tableExport({type:'excel',escape:'false'});"><img
                            src='../../../../theme/img/icons/xls.png' width="24"/> XLS</a></li>
                    <li><a href="#" onClick="$('#itemList').tableExport({type:'doc',escape:'false'});"><img
                            src='../../../../theme/img/icons/word.png' width="24"/> Word</a></li>
                    <li><a href="#" onClick="$('#itemList').tableExport({type:'pdf',escape:'false'});"><img
                            src='../../../../theme/img/icons/pdf.png' width="24"/> PDF</a></li>
                </ul>
            </div>
        </div>
        <div class="panel-body">
            <table class="table datatable" id="itemList">
                <thead>
                <tr>
                    <th>Item Code</th>
                    <th>Item Name</th>
                    <th>Item Type</th>
                    <th>Unit Price</th>
                    <th>Unit</th>
                    <th>Expire(KM)</th>
                    <th>Expire(Duration)</th>
                    <th>Description</th>
                    <th>Status</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${itemList}" var="item">
                    <tr onclick="load_item_data_to_modal(${item.itemSeq})" class="clickable">
                        <td>${item.itemCode}</td>
                        <td>${item.itemName}</td>
                        <td>${item.itemTypeDescription}</td>
                        <td>${item.unitPrice}</td>
                        <td>${item.unit.unitName}</td>
                        <td>${item.kmExpiration}</td>
                        <td>${item.kmExpiration}</td>
                        <td>${item.itemDescription}</td>
                        <td class="<c:if test="${item.status eq 0}">delete-status-colour</c:if>
                                   <c:if test="${item.status eq 1}">open-status-colour</c:if>
                                   <c:if test="${item.status eq 2}">approved-status-colour</c:if>
                                   <c:if test="${item.status eq 3}">closed-status-colour</c:if>"
                        >${item.statusDescription}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

