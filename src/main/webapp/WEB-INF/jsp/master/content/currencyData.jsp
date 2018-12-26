<%@ include file="/WEB-INF/jsp/include.jsp" %>
<!-- START DEFAULT DATATABLE -->
<div class="col-md-12">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">Currency Details</h3>
            <div class="btn-group pull-right">
                <button class="btn btn-danger dropdown-toggle" data-toggle="dropdown"><i class="fa fa-bars"></i>
                    Export Data
                </button>
                <ul class="dropdown-menu">
                    <li class="divider"></li>
                    <li><a href="#" onClick="$('#currencyTable').tableExport({type:'excel',escape:'false'});"><img
                            src='../../../../theme/img/icons/xls.png' width="24"/> XLS</a></li>
                    <li><a href="#" onClick="$('#currencyTable').tableExport({type:'doc',escape:'false'});"><img
                            src='../../../../theme/img/icons/word.png' width="24"/> Word</a></li>
                    <li><a href="#" onClick="$('#currencyTable').tableExport({type:'pdf',escape:'false'});"><img
                            src='../../../../theme/img/icons/pdf.png' width="24"/> PDF</a></li>
                </ul>
            </div>
        </div>
        <div class="panel-body">
            <table class="table datatable" id="currencyTable">
                <thead>
                <tr>
                    <th>Currency Code</th>
                    <th>Currency Name</th>
                    <th>Status</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${currencyManagementList}" var="currencyManagement">
                    <tr onclick="load_currency_data_to_modal('${currencyManagement.currencySeq}')">
                        <td>${currencyManagement.currencyCode}</td>
                        <td>${currencyManagement.currencyName}</td>
                        <td>${currencyManagement.statusDescription}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<!-- END DEFAULT DATATABLE -->