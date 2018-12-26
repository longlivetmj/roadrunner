<%@ include file="/WEB-INF/jsp/include.jsp" %>
<div class="col-md-12">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">Bank Details</h3>
            <div class="btn-group pull-right">
                <button class="btn btn-danger dropdown-toggle" data-toggle="dropdown"><i class="fa fa-bars"></i> Export
                    Data
                </button>
                <ul class="dropdown-menu">

                    <li><a href="#" onClick="$('#bankDataSearch').tableExport({type:'excel',escape:'false'});"><img
                            src='../../../../theme/img/icons/xls.png' width="24"/> XLS</a></li>
                    <li><a href="#" onClick="$('#bankDataSearch').tableExport({type:'doc',escape:'false'});"><img
                            src='../../../../theme/img/icons/word.png' width="24"/> Word</a></li>
                    <li><a href="#" onClick="$('#bankDataSearch').tableExport({type:'pdf',escape:'false'});"><img
                            src='../../../../theme/img/icons/pdf.png' width="24"/> PDF</a></li>
                </ul>
            </div>
        </div>
        <div class="panel-body">
            <table id="bankDataSearch" class="table datatable">
                <thead>
                <tr>
                    <th>Bank Code</th>
                    <th>Bank Name</th>
                    <th>SLIPS Code</th>
                    <th>Status</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${bankList}" var="bank">
                    <tr onclick="load_bank_data_to_modal(${bank.bankSeq})" class="clickable">
                        <td>${bank.bankCode}</td>
                        <td>${bank.bankName}</td>
                        <td>${bank.slipsCode}</td>
                        <td>${bank.statusDescription}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
