<%@ include file="/WEB-INF/jsp/include.jsp" %>
<div class="col-md-12">
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">Date Range</h3>
            </div>
            <div class="panel-body">
                <table class="table table-striped" style="margin-bottom: 0px">
                    <c:forEach items="${exchangeRateBankList}" var="exchangeRate">
                        <tr>
                            <td width="2%" style="padding: 4px"><input type="RADIO" class="radio-button exchangeRateBankExpenseVoucher"
                                                                       name="exchangeRateSeq"
                                                                       id="${exchangeRate.exchangeRateSeq}"
                                                                       onchange="load_exchange_rate_bank('${exchangeRate.exchangeRateSeq}')"
                                                                       value="${exchangeRate.exchangeRateSeq}"/></td>
                            <td style="padding: 4px"><fmt:formatDate value="${exchangeRate.effectiveFrom}"
                                                                     pattern="dd/MM/yyyy"/>
                                - <fmt:formatDate value="${exchangeRate.effectiveTo}"
                                                  pattern="dd/MM/yyyy"/></td>
                            <td style="padding: 4px">
                                <table class="table" style="margin-bottom: 0px; background-color: transparent">
                                    <c:set var="index" value="${0}"/>
                                    <c:forEach items="${exchangeRate.exchangeRateDetails}" var="exchangeRateDetail">
                                        <tr style="background-color: transparent">
                                            <td style="padding: 4px; background-color: transparent">${exchangeRateDetail.currency.currencyCode}</td>
                                            <td style="padding: 4px; background-color: transparent"><fmt:formatNumber
                                                    value="${exchangeRateDetail.rate}" type="currency"
                                                    pattern="#,##0.00#;(#,##0.00#-)"/>

                                            </td>
                                        </tr>
                                        <c:set var="index" value="${index+1}"/>
                                    </c:forEach>
                                </table>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </div>
</div>
