<%@ include file="/WEB-INF/jsp/include.jsp" %>
<div class="col-md-12">

    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">Branch Details</h3>
            <div class="btn-group pull-right">
                <button class="btn btn-danger dropdown-toggle" data-toggle="dropdown"><i class="fa fa-bars"></i> Export
                    Data
                </button>
                <ul class="dropdown-menu">
                    <li><a href="#" onClick="$('#branchDataSearch').tableExport({type:'excel',escape:'false'});"><img
                            src='../../../../theme/img/icons/xls.png' width="24"/> XLS</a></li>
                    <li><a href="#" onClick="$('#branchDataSearch').tableExport({type:'doc',escape:'false'});"><img
                            src='../../../../theme/img/icons/word.png' width="24"/> Word</a></li>
                    <li><a href="#" onClick="$('#branchDataSearch').tableExport({type:'pdf',escape:'false'});"><img
                            src='../../../../theme/img/icons/pdf.png' width="24"/> PDF</a></li>
                </ul>
            </div>
        </div>
        <div class="panel-body">
            <table id="branchDataSearch" class="table datatable">
                <thead>
                <tr>
                    <th>Bank Name</th>
                    <th>Branch Code</th>
                    <th>Branch Name</th>
                </tr>
                </thead>
                <tbody>
                <jsp:useBean id="branchListDB" scope="request" type="java.util.List<com.tmj.tms.master.datalayer.modal.Branch>"/>
                <c:forEach items="${branchListDB}" var="branch">
                    <tr onclick="load_branch_popup(${branch.branchSeq})" class="clickable" id="tr_${branch.branchSeq}">
                        <td>${branch.bank.bankName}</td>
                        <td>${branch.branchCode}</td>
                        <td>${branch.branchName}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

