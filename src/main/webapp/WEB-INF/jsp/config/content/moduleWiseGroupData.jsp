<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="panel-body">
        <c:forEach items="${moduleWiseGroupList}" var="group">
            <div class="col-md-5">
                <label class="check"><input type="checkbox" id="${group.groupSeq}"
                                            name="groupSeq[]"
                                            value="${group.groupSeq}" class="icheckbox"/>
                        ${group.groupName}</label>
            </div>
        </c:forEach>
</div>
