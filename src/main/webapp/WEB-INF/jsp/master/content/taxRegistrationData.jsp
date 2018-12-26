<%@ include file="/WEB-INF/jsp/include.jsp" %>
<!-- START DEFAULT DATATABLE -->
<div class="col-md-12">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">Tax Registration Details</h3>
            <div class="btn-group pull-right">
                <button class="btn btn-danger dropdown-toggle" data-toggle="dropdown"><i class="fa fa-bars"></i> Export
                    Data
                </button>
                <ul class="dropdown-menu">
                    <li><a href="#" onClick="$('#taxRegistrationList').tableExport({type:'csv',escape:'false'});"><img
                            src='../../../../theme/img/icons/csv.png' width="24"/> CSV</a></li>
                    <li><a href="#" onClick="$('#taxRegistrationList').tableExport({type:'txt',escape:'false'});"><img
                            src='../../../../theme/img/icons/txt.png' width="24"/> TXT</a></li>
                    <li class="divider"></li>
                    <li><a href="#" onClick="$('#taxRegistrationList').tableExport({type:'excel',escape:'false'});"><img
                            src='../../../../theme/img/icons/xls.png' width="24"/> XLS</a></li>
                    <li><a href="#" onClick="$('#taxRegistrationList').tableExport({type:'doc',escape:'false'});"><img
                            src='../../../../theme/img/icons/word.png' width="24"/> Word</a></li>
                    <li><a href="#" onClick="$('#taxRegistrationList').tableExport({type:'pdf',escape:'false'});"><img
                            src='../../../../theme/img/icons/pdf.png' width="24"/> PDF</a></li>
                </ul>
            </div>
        </div>
        <div class="panel-body">
            <table class="table datatable" id="taxRegistrationList">
                <thead>
                <tr>
                    <th>Tax Name</th>
                    <th>Country</th>
                    <th>Tax Rate</th>
                    <th>Tax Type</th>
                    <th>Remarks</th>
                    <th>Status</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${taxRegistrationList}" var="taxRegistration">
                    <tr onclick="load_tax_registration_data_to_modal(${taxRegistration.taxRegistrationSeq})" class="clickable" id="tr_${taxRegistration.taxRegistrationSeq}">
                        <td>${taxRegistration.taxName}</td>
                        <td>${taxRegistration.country.countryName}</td>
                        <td>${taxRegistration.taxRate}</td>
                        <td>${taxRegistration.taxType.taxTypeName}</td>
                        <td>${taxRegistration.remarks}</td>
                        <td>${taxRegistration.statusDescription}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
