<%@ include file="/WEB-INF/jsp/include.jsp" %>
<div class="col-md-12">
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">Rates</h3>
            </div>
            <div class="panel-body">
                <table class="collaptable table table-striped">
                    <thead>
                    <tr>
                        <th>Currency</th>
                        <th>Rate</th>

                    </tr>
                    </thead>
                    <tbody>
                    <c:set var="index" value="${0}"/>
                    <c:forEach items="${exchangeRateDetailList}" var="exchangeRateDetail">
                        <tr>
                            <td>${exchangeRateDetail.currency.currencyCode}</td>
                            <td><fmt:formatNumber value="${exchangeRateDetail.rate}" type="currency"
                                                  pattern="#,##0.00#;(#,##0.00#-)"/>

                            </td>
                        </tr>
                        <c:set var="index" value="${index+1}"/>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
