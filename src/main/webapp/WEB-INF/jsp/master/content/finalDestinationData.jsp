<%@ include file="/WEB-INF/jsp/include.jsp" %>
<div class="col-md-12">

    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">Final Destination Details</h3>
            <div class="btn-group pull-right">
                <button class="btn btn-danger dropdown-toggle" data-toggle="dropdown"><i class="fa fa-bars"></i> Export
                    Data
                </button>
                <ul class="dropdown-menu">
                    <li><a href="#" onClick="$('#destinationDataSearch').tableExport({type:'csv',escape:'false'});"><img
                            src='../../../../theme/img/icons/csv.png' width="24"/> CSV</a></li>
                    <li><a href="#" onClick="$('#destinationDataSearch').tableExport({type:'txt',escape:'false'});"><img
                            src='../../../../theme/img/icons/txt.png' width="24"/> TXT</a></li>
                    <li class="divider"></li>
                    <li><a href="#" onClick="$('#destinationDataSearch').tableExport({type:'excel',escape:'false'});"><img
                            src='../../../../theme/img/icons/xls.png' width="24"/> XLS</a></li>
                    <li><a href="#" onClick="$('#destinationDataSearch').tableExport({type:'doc',escape:'false'});"><img
                            src='../../../../theme/img/icons/word.png' width="24"/> Word</a></li>
                    <li><a href="#" onClick="$('#destinationDataSearch').tableExport({type:'pdf',escape:'false'});"><img
                            src='../../../../theme/img/icons/pdf.png' width="24"/> PDF</a></li>
                </ul>
            </div>
        </div>
        <div class="panel-body">
            <table id="destinationDataSearch" class="table datatable">
                <thead>
                <tr>
                    <th>Destination Code</th>
                    <th>Location</th>
                    <th>Latitude</th>
                    <th>Longitude</th>
                    <th>City</th>
                    <th>Status</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${destinationListDB}" var="finalDestination">
                    <tr onclick="load_final_destination_data_to_modal(${finalDestination.finalDestinationSeq})" class="clickable"  id="tr_${finalDestination.finalDestinationSeq}">
                        <td>${finalDestination.destination}</td>
                        <td>${finalDestination.location.locationName}</td>
                        <td>${finalDestination.latitude}</td>
                        <td>${finalDestination.longitude}</td>
                        <td>${finalDestination.addressBook.city}</td>
                        <td>${finalDestination.statusDescription}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

