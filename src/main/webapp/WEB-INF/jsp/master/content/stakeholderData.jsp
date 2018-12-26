<%@ include file="/WEB-INF/jsp/include.jsp" %>
<!-- START DEFAULT DATATABLE -->
<div class="col-md-12">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">Stakeholder Details</h3>
            <div class="btn-group pull-right">
                <button class="btn btn-danger dropdown-toggle" data-toggle="dropdown"><i class="fa fa-bars"></i>
                    Export Data
                </button>
                <ul class="dropdown-menu">
                    <li class="divider"></li>
                    <li><a href="#" onClick="$('#stakeholderTable').tableExport({type:'excel',escape:'false'});"><img
                            src='../../../../theme/img/icons/xls.png' width="24"/> XLS</a></li>
                    <li><a href="#" onClick="$('#stakeholderTable').tableExport({type:'doc',escape:'false'});"><img
                            src='../../../../theme/img/icons/word.png' width="24"/> Word</a></li>
                    <li><a href="#" onClick="$('#stakeholderTable').tableExport({type:'pdf',escape:'false'});"><img
                            src='../../../../theme/img/icons/pdf.png' width="24"/> PDF</a></li>
                </ul>
            </div>
        </div>
        <div class="panel-body">
            <table class="table datatable" id="stakeholderTable">
                <thead>
                <tr>
                    <th>Stakeholder ID</th>
                    <th>Stakeholder Name</th>
                    <th>Stakeholder Code</th>
                    <th>Stakeholder Group</th>
                    <th>Country</th>
                    <th>Status</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${stakeholderList}" var="stakeholder">
                    <tr id="tr_${stakeholder.stakeholderSeq}">
                        <td onclick="load_stakeholder_data_to_modal(${stakeholder.stakeholderSeq})" class="clickable">${stakeholder.stakeholderSeq ne null ? stakeholder.stakeholderSeq : ''}</td>
                        <td>${stakeholder.stakeholderName ne null ? stakeholder.stakeholderName : ''}</td>
                        <td>${stakeholder.stakeholderCode ne null ? stakeholder.stakeholderCode :''}</td>
                        <td>${stakeholder.customerGroup ne null ? stakeholder.customerGroup.customerGroupName : ''}</td>
                        <td>${stakeholder.addressBook.country.countryName ne null ? stakeholder.addressBook.country.countryName :''}</td>
                        <td class="<c:if test="${stakeholder.status eq 0}">delete-status-colour</c:if>
                                   <c:if test="${stakeholder.status eq 1}">open-status-colour</c:if>
                                   <c:if test="${stakeholder.status eq 2}">approved-status-colour</c:if>
                                   <c:if test="${stakeholder.status eq 3}">closed-status-colour</c:if>
                            ">${stakeholder.statusDescription != null ? stakeholder.statusDescription : '-'}</td>
                        <td> <button type="button" class="btn btn-danger"
                                <sec:authorize access="!hasRole('ROLE_finance@rateMasterManagement_VIEW')">
                                    disabled="disabled"
                                </sec:authorize>
                                     id="rateMaster"
                                     onclick="view_rate_master_grid(${stakeholder.stakeholderSeq})">Rate Master
                        </button></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<!-- END DEFAULT DATATABLE -->