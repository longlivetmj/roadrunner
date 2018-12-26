<%@ include file="/WEB-INF/jsp/include.jsp" %>
<div class="col-md-12">

    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">Sales Person Details</h3>
            <div class="btn-group pull-right">
                <button class="btn btn-danger dropdown-toggle" data-toggle="dropdown"><i class="fa fa-bars"></i> Export
                    Data
                </button>
                <ul class="dropdown-menu">

                    <li><a href="#" onClick="$('#salesPersonDataSearch').tableExport({type:'excel',escape:'false'});"><img
                            src='../../../../theme/img/icons/xls.png' width="24"/> XLS</a></li>
                    <li><a href="#" onClick="$('#salesPersonDataSearch').tableExport({type:'doc',escape:'false'});"><img
                            src='../../../../theme/img/icons/word.png' width="24"/> Word</a></li>
                    <li><a href="#" onClick="$('#salesPersonDataSearch').tableExport({type:'pdf',escape:'false'});"><img
                            src='../../../../theme/img/icons/pdf.png' width="24"/> PDF</a></li>
                </ul>
            </div>
        </div>
        <div class="panel-body">
            <table id="salesPersonDataSearch" class="table datatable">
                <thead>
                <tr>
                    <th>Sales Person Code</th>
                    <th>Sales Person Name</th>
                    <th>Stakeholder</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${salesPersonListDB}" var="salesPerson">
                    <tr onclick="load__sales_person_popup(${salesPerson.salesPersonSeq})" class="clickable"  id="tr_${salesPerson.salesPersonSeq}">
                        <td>${salesPerson.salesPersonCode}</td>
                        <td>${salesPerson.salesPersonName}</td>
                        <td>${salesPerson.stakeholder.stakeholderName}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

