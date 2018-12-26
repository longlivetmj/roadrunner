<%@ include file="/WEB-INF/jsp/include.jsp" %>
<!-- START DEFAULT DATATABLE -->
<div class="col-md-12">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">Location Details</h3>
            <div class="btn-group pull-right">
                <button class="btn btn-danger dropdown-toggle" data-toggle="dropdown"><i class="fa fa-bars"></i> Export
                    Data
                </button>
                <ul class="dropdown-menu">
                    <li><a href="#" onClick="$('#locationList').tableExport({type:'excel',escape:'false'});"><img
                            src='../../../../theme/img/icons/xls.png' width="24"/> XLS</a></li>
                    <li><a href="#" onClick="$('#locationList').tableExport({type:'doc',escape:'false'});"><img
                            src='../../../../theme/img/icons/word.png' width="24"/> Word</a></li>
                    <li><a href="#" onClick="$('#locationList').tableExport({type:'pdf',escape:'false'});"><img
                            src='../../../../theme/img/icons/pdf.png' width="24"/> PDF</a></li>
                </ul>
            </div>
        </div>
        <div class="panel-body">
            <table class="table datatable" id="locationList">
                <thead>
                <tr>
                    <th width="30%">Location Name</th>
                    <th width="10%">Country</th>
                    <th width="10%">Postal Code</th>
                    <th width="10%">District Code</th>
                    <th width="10%">Latitude</th>
                    <th width="10%">Longitude</th>
                    <th width="10%">Status</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${locationList}" var="location">
                    <tr onclick="load_location_data_to_modal(${location.locationSeq})" class="clickable">
                        <td>${location.locationName}</td>
                        <td>${location.country.countryName}</td>
                        <td>${location.postalCode}</td>
                        <td>${location.districtCode}</td>
                        <td>${location.latitude}</td>
                        <td>${location.longitude}</td>
                        <td>${location.statusDescription}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
