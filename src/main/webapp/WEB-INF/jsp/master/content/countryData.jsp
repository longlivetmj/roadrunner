<%--
  Created by IntelliJ IDEA.
  User: shanakajay
  Date: 9/20/2016
  Time: 11:24 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<div class="col-md-12">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">Country Details</h3>
            <div class="btn-group pull-right">
                <button class="btn btn-danger dropdown-toggle" data-toggle="dropdown"><i class="fa fa-bars"></i>
                    Export Data
                </button>
                <ul class="dropdown-menu">
                    <li class="divider"></li>
                    <li><a href="#" onClick="$('#countryTable').tableExport({type:'excel',escape:'false'});"><img
                            src='../../../../theme/img/icons/xls.png' width="24"/> XLS</a></li>
                    <li><a href="#" onClick="$('#countryTable').tableExport({type:'doc',escape:'false'});"><img
                            src='../../../../theme/img/icons/word.png' width="24"/> Word</a></li>
                    <li><a href="#" onClick="$('#countryTable').tableExport({type:'pdf',escape:'false'});"><img
                            src='../../../../theme/img/icons/pdf.png' width="24"/> PDF</a></li>
                </ul>
            </div>
        </div>
        <div class="panel-body">
            <table class="table datatable" id="countryTable">
                <thead>
                <tr>
                    <th>Country Code</th>
                    <th>Country Name</th>
                    <th>Region</th>
                    <th>Currency</th>
                    <th>IATA Code</th>
                    <th>Status</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${countryList}" var="country">
                    <tr onclick="load_country_data_to_modal('${country.countrySeq}')" class="clickable" id="tr_${country.countrySeq}">
                        <td>${country.countryCode}</td>
                        <td>${country.countryName}</td>
                        <td>${country.region.regionName}</td>
                        <td>${country.currency.currencyCode}</td>
                        <td>${country.iataCode}</td>
                        <td>${country.statusDescription}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

