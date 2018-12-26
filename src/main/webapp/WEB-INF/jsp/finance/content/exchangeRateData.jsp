<%@ include file="/WEB-INF/jsp/include.jsp" %>
<div class="col-md-12">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">Exchange Rate Details</h3>
            <div class="btn-group pull-right">
                <button class="btn btn-danger dropdown-toggle" data-toggle="dropdown"><i class="fa fa-bars"></i> Export
                    Data
                </button>

                <ul class="dropdown-menu">
                    <li><a href="#"
                           onClick="$('#exchangeRateDataSearch').tableExport({type:'csv',escape:'false'});"><img
                            src='../../../../theme/img/icons/csv.png' width="24"/> CSV</a></li>
                    <li><a href="#"
                           onClick="$('#exchangeRateDataSearch').tableExport({type:'txt',escape:'false'});"><img
                            src='../../../../theme/img/icons/txt.png' width="24"/> TXT</a></li>
                    <li class="divider"></li>
                    <li><a href="#"
                           onClick="$('#exchangeRateDataSearch').tableExport({type:'excel',escape:'false'});"><img
                            src='../../../../theme/img/icons/xls.png' width="24"/> XLS</a></li>
                    <li><a href="#"
                           onClick="$('#exchangeRateDataSearch').tableExport({type:'doc',escape:'false'});"><img
                            src='../../../../theme/img/icons/word.png' width="24"/> Word</a></li>
                    <li><a href="#"
                           onClick="$('#exchangeRateDataSearch').tableExport({type:'pdf',escape:'false'});"><img
                            src='../../../../theme/img/icons/pdf.png' width="24"/> PDF</a></li>
                </ul>
            </div>
        </div>
        <div class="panel-body">
            <table id="exchangeRateDataSearch" class="table datatable">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Module</th>
                    <th>Base Currency</th>
                    <th>Bank</th>
                    <th>Source</th>
                    <th>Status</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${exchangeRateList}" var="exchangeRate">
                    <tr onclick="load_exchange_rate_data_to_modal('${exchangeRate.exchangeRateSeq}','${exchangeRate.moduleSeq}','${exchangeRate.exchangeRateSourceType}')"
                        class="clickable"
                        id="tr_${exchangeRate.exchangeRateSeq}">
                        <td>${exchangeRate.exchangeRateSeq}</td>
                        <td>${exchangeRate.module.moduleName}</td>
                        <td>${exchangeRate.currency.currencyCode}</td>
                        <td>${exchangeRate.sourceBank.bankName != null ? exchangeRate.sourceBank.bankName : '-'}</td>
                        <td>${exchangeRate.sourceDescription !=null ? exchangeRate.sourceDescription: '-'}</td>
                        <td class="<c:if test="${exchangeRate.status eq 0}">delete-status-colour</c:if>
                                   <c:if test="${exchangeRate.status eq 1}">open-status-colour</c:if>
                                   <c:if test="${exchangeRate.status eq 2}">approved-status-colour</c:if>
                                   <c:if test="${exchangeRate.status eq 3}">closed-status-colour</c:if>
                       ">${exchangeRate.statusDescription != null ? exchangeRate.statusDescription : '-'}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
