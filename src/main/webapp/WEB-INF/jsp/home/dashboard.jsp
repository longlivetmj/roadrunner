<%@ include file="/WEB-INF/jsp/include.jsp" %>
<script type="text/javascript" src="../../../customjs/home/dashboard.js"></script>
<div id="firstRow" class="row">
    <c:forEach items="${firstRowWidgetList}" var="firstRowWidget">
        <input type="hidden" id="controllerUrl${firstRowWidget.widgetSeq}" value="${firstRowWidget.controllerUrl}"/>
        <input type="hidden" id="widgetName${firstRowWidget.widgetSeq}" value="${firstRowWidget.widgetName}"/>
        <div class="col-md-${firstRowWidget.widgetSize}" id="${firstRowWidget.widgetSeq}">

        </div>
    </c:forEach>
</div>
<div id="secondRow" class="row">
    <c:forEach items="${secondRowWidgetList}" var="secondRowWidget">
        <input type="hidden" id="controllerUrl${secondRowWidget.widgetSeq}" value="${secondRowWidget.controllerUrl}"/>
        <input type="hidden" id="widgetName${secondRowWidget.widgetSeq}" value="${secondRowWidget.widgetName}"/>
        <div class="col-md-${secondRowWidget.widgetSize}" id="${secondRowWidget.widgetSeq}">

        </div>
    </c:forEach>
</div>
<div id="thirdRow" class="row">
    <c:forEach items="${thirdRowWidgetList}" var="thirdRowWidget">
        <input type="hidden" id="controllerUrl${thirdRowWidget.widgetSeq}" value="${thirdRowWidget.controllerUrl}"/>
        <input type="hidden" id="widgetName${thirdRowWidget.widgetSeq}" value="${thirdRowWidget.widgetName}"/>
        <div class="col-md-${thirdRowWidget.widgetSize}" id="${thirdRowWidget.widgetSeq}">

        </div>
    </c:forEach>
</div>
