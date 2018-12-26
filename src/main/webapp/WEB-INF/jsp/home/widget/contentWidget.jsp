<%@ include file="/WEB-INF/jsp/include.jsp" %>
<div style="cursor: pointer" class="widget widget-default widget-item-icon">
    <div class="widget-item-left">
        <span class="fa fa-${widget.icon}"></span>
    </div>
    <div class="widget-data">
        <div class="widget-int num-count">${widget.message}</div>
        <div class="widget-title">${widget.title}</div>
        <div class="widget-subtitle">${widget.subTitle}</div>
    </div>
    <div class="widget-controls">
        <a href="#" class="widget-control-right widget-remove" data-toggle="tooltip" data-placement="top" title="Remove Widget"><span class="fa fa-times"></span></a>
    </div>
</div>
