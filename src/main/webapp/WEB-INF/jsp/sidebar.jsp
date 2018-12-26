<%@ include file="/WEB-INF/jsp/include.jsp" %>
<li class="xn-title" id="subModuleDefinition">${module.moduleName} - ${subModule.subModuleName}</li>
<c:forEach items="${subModule.actionGroupList}" var="actionGroup">
    <c:if test="${(actionGroup.actionGroupName eq 'General') || (fn:length(actionGroup.documentLinkList) eq 1)}">
        <c:forEach items="${actionGroup.documentLinkList}" var="documentLink">
            <c:if test="${documentLink.status eq 1}">
                <sec:authorize access="hasRole('ROLE_${module.urlPattern}@${documentLink.linkName}_VIEW')">
                    <c:if test="${documentLink.externalLink eq null}">
                        <li id="${module.urlPattern}-${documentLink.linkName}">
                            <a href="#/${module.urlPattern}/${documentLink.linkName}" data-toggle="tooltip"
                               title="${documentLink.pageName}"><span
                                    class="fa fa-${documentLink.icon}"></span> <span
                                    class="xn-text">${documentLink.pageName}</span></a>
                            <input type="hidden" id="${documentLink.linkName}" value="${documentLink.pageName}"/>
                        </li>
                    </c:if>
                    <c:if test="${documentLink.externalLink eq 1}">
                        <li id="${module.urlPattern}-${documentLink.linkName}">
                            <a target="_blank" href="${documentLink.externalPage}" data-toggle="tooltip"
                               title="${documentLink.pageName}"><span
                                    class="fa fa-${documentLink.icon}"></span> <span
                                    class="xn-text">${documentLink.pageName}</span></a>
                            <input type="hidden" id="${documentLink.linkName}" value="${documentLink.pageName}"/>
                        </li>
                    </c:if>
                </sec:authorize>
            </c:if>
        </c:forEach>
    </c:if>

    <c:if test="${actionGroup.actionGroupName ne 'General' && (fn:length(actionGroup.documentLinkList) gt 1)}">
        <li class="xn-openable">
            <a href="#"><span class="fa fa-${actionGroup.icon}"></span> <span
                    class="xn-text">${actionGroup.actionGroupName}</span></a>
            <ul>
                <c:forEach items="${actionGroup.documentLinkList}" var="documentLink">
                    <c:if test="${documentLink.status eq 1}">
                        <sec:authorize access="hasRole('ROLE_${module.urlPattern}@${documentLink.linkName}_VIEW')">
                            <c:if test="${documentLink.externalLink eq null}">
                                <li id="${module.urlPattern}-${documentLink.linkName}">
                                    <a href="#/${module.urlPattern}/${documentLink.linkName}" data-toggle="tooltip"
                                       title="${documentLink.pageName}"><span
                                            class="fa fa-${documentLink.icon}"></span> ${documentLink.pageName}</a>
                                    <input type="hidden" id="${documentLink.linkName}"
                                           value="${documentLink.pageName}"/>
                                </li>
                            </c:if>
                            <c:if test="${documentLink.externalLink eq 1}">
                                <li id="${module.urlPattern}-${documentLink.linkName}">
                                    <a target="_blank" href="${documentLink.externalPage}" data-toggle="tooltip"
                                       title="${documentLink.pageName}"><span
                                            class="fa fa-${documentLink.icon}"></span> ${documentLink.pageName}</a>
                                    <input type="hidden" id="${documentLink.linkName}"
                                           value="${documentLink.pageName}"/>
                                </li>
                            </c:if>
                        </sec:authorize>
                    </c:if>
                </c:forEach>
            </ul>
        </li>
    </c:if>
</c:forEach>
<script type="text/javascript" src="../../customjs/sidebar.js"></script>