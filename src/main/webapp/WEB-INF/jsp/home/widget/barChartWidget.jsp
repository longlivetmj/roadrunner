<%@ include file="/WEB-INF/jsp/include.jsp" %>
<div class="panel panel-default">
    <div class="panel-heading">
        <div class="panel-title-box">
            <h3>${widget.heading}</h3>
            <span>${widget.subHeading}</span>
        </div>
        <ul class="panel-controls" style="margin-top: 2px;">
            <li><a href="#" class="panel-fullscreen"><span class="fa fa-expand"></span></a></li>
            <li><a onclick="refresh()" class="panel-refresh"><span class="fa fa-refresh"></span></a></li>
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
        <div class="chart-holder" id="dashboard-bar-${widget.widgetSeq}" style="height: 200px;">

        </div>
    </div>
</div>

<script>
    var morrisBar = Morris.Bar({
        element: 'dashboard-bar-${widget.widgetSeq}',
        data: ${widget.data},
        xkey: ${widget.xKeys},
        ykeys: [${widget.yKeys}],
        labels: [${widget.yLabels}],
        barColors: ['#33414E', '#1caf9a'],
        gridTextSize: '10px',
        hideHover: true,
        resize: true,
        gridLineColor: '#E5E5E5'
    });

    function refresh() {

    }

</script>
<!-- END USERS ACTIVITY BLOCK -->
