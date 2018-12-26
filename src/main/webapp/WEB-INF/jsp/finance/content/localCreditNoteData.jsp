<%--
  Created by IntelliJ IDEA.
  User: Udaya-Ehl
  Date: 4/28/2017
  Time: 1:42 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<div class="col-md-12">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">Local Credit Note</h3>
            <div class="btn-group pull-right">
                <button class="btn btn-danger dropdown-toggle" data-toggle="dropdown"><i class="fa fa-bars"></i>
                    Export Data
                </button>
                <ul class="dropdown-menu">
                    <li class="divider"></li>
                    <li><a href="#" onClick="$('#localCreditNote').tableExport({type:'excel',escape:'false'});"><img
                            src='../../../../theme/img/icons/xls.png' width="24"/> XLS</a></li>
                    <li><a href="#" onClick="$('#localCreditNote').tableExport({type:'doc',escape:'false'});"><img
                            src='../../../../theme/img/icons/word.png' width="24"/> Word</a></li>
                    <li><a href="#" onClick="$('#localCreditNote').tableExport({type:'pdf',escape:'false'});"><img
                            src='../../../../theme/img/icons/pdf.png' width="24"/> PDF</a></li>
                </ul>
            </div>
        </div>
        <div class="panel-body" id="localCreditNote">
            <table id="masterBLDataSearch" class="table datatable">
                <thead>
                <tr>
                    <th>Credit/Debit No.</th>
                    <th>LocalInvoice No.</th>
                    <th>Expense Voucher No.</th>
                    <th>Date</th>
                    <th>Status</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${localCreditNoteDetailList}" var="localCreditNoteDetail">
                    <tr onclick="load_selected_local_Credit_note('${localCreditNoteDetail.localCreditNoteHeaderSeq}')" class="clickable">
                        <td>${localCreditNoteDetail.localCreditNoteNo}</td>
                        <td>${localCreditNoteDetail.localInvoice.localInvoiceNo}</td>
                        <td>${localCreditNoteDetail.expenseVoucher.expenseVoucherNo}</td>
                        <td>
                            <fmt:formatDate
                                    value="${localCreditNoteDetail.createdDate ne null ? localCreditNoteDetail.createdDate : ''}"
                                    pattern="yyyy-MM-dd"/>
                        </td>
                        <td class="<c:if test="${localCreditNoteDetail.status eq 0}">delete-status-colour</c:if>
                                   <c:if test="${localCreditNoteDetail.status eq 1}">open-status-colour</c:if>
                                   <c:if test="${localCreditNoteDetail.status eq 2}">approved-status-colour</c:if>
                                   <c:if test="${localCreditNoteDetail.status eq 3}">closed-status-colour</c:if>
                            ">${localCreditNoteDetail.statusDescription != null ? localCreditNoteDetail.statusDescription : '-'}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

