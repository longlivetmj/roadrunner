<%@ include file="/WEB-INF/jsp/include.jsp" %>
<div class="widget widget-default widget-carousel">
    <div class="owl-carousel" id="owl-example">
        <c:forEach items="${widget.contentWidgetList}" var="contentWidget">
            <div>
                <div class="col-sm-1">
                    <span class="fa fa-${contentWidget.icon}"  style="font-size: 40px;text-align: right; vertical-align: middle; padding-top: 20px"></span>
                </div>
                <div class="col-sm-11">
                    <div class="widget-title">${contentWidget.title}</div>
                    <div class="widget-subtitle">${contentWidget.subTitle}</div>
                    <div class="widget-int">${contentWidget.message}</div>
                </div>
            </div>
        </c:forEach>
    </div>
    <div class="widget-controls">
        <a href="#" class="widget-control-right widget-remove" data-toggle="tooltip" data-placement="top" title="Remove Widget"><span class="fa fa-times"></span></a>
    </div>
</div>
<script>
    if ($(".owl-carousel").length > 0) {
        $(".owl-carousel").owlCarousel({
            mouseDrag: false,
            touchDrag: true,
            slideSpeed: 300,
            paginationSpeed: 400,
            singleItem: true,
            navigation: false,
            autoPlay: true
        });
    }
</script>