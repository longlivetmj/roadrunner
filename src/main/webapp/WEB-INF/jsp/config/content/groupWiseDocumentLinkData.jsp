<%@ include file="/WEB-INF/jsp/include.jsp" %>
<div class="col-md-12">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">Page Permissions</h3>
        </div>
        <div class="panel-body">
            <table class="table datatable" id="groupAuthorities">
                <thead>
                <tr>
                    <th>Page Description</th>
                    <th>Available Facilities</th>
                </tr>
                </thead>
                <tbody>
                <jsp:useBean id="moduleWiseDocumentLinkList" scope="request" type="java.util.List"/>
                <c:forEach items="${moduleWiseDocumentLinkList}" var="documentLink">
                    <tr>
                        <td>${documentLink.pageName}</td>
                        <td>
                            <c:forEach items="${documentLink.authorities}" var="authority">
                                <c:set var="authorityParts"
                                       value="${fn:split(authority.authority, '_')}"/>
                                <input type="checkbox" id="${authority.authoritySeq}"
                                       name="authoritySeq[]"
                                       value="${authority.authoritySeq}"/>${authorityParts[2]} &nbsp;
                            </c:forEach>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
