<%@ include file="/WEB-INF/jsp/include.jsp" %>
<div class="panel panel-default">
    <div class="panel-heading">
        <div class="panel-title-box">
            <h3>${widget.heading}</h3>
            <span>${widget.subHeading}</span>
        </div>
        <ul class="panel-controls" style="margin-top: 2px;">
            <li><a href="#" class="panel-fullscreen"><span class="fa fa-expand"></span></a></li>
            <li><a href="#" class="panel-refresh"><span class="fa fa-refresh"></span></a></li>
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="fa fa-cog"></span></a>
                <ul class="dropdown-menu">
                    <li><a href="#" class="panel-collapse"><span class="fa fa-angle-down"></span> Collapse</a></li>
                    <li><a href="#" class="panel-remove"><span class="fa fa-times"></span> Remove</a></li>
                </ul>
            </li>
        </ul>
    </div>
    <div class="panel-body padding-0">
        <div class="chart-holder" id="dashboard-line-${widget.widgetSeq}" style="height: 200px;">

        </div>
    </div>
</div>
<!-- END SALES & EVENTS BLOCK -->

<script>
    /* Line dashboard chart */
    try {
        Morris.Line({
            element: 'dashboard-line-${widget.widgetSeq}',
            data: ${widget.data},
            xkey: ${widget.xKeys},
            ykeys: [${widget.yKeys}],
            labels: [${widget.yLabels}],
            resize: true,
            hideHover: true,
            xLabels: ${widget.xLabels},
            gridTextSize: '10px',
            lineColors: ['#1caf9a', '#33414E'],
            gridLineColor: '#E5E5E5'
        });
    } catch (e) {
    }
    /* EMD Line dashboard chart */
</script>
