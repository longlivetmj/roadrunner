<%@ include file="/WEB-INF/jsp/include.jsp" %>
<div class="panel panel-default">
    <div class="panel-heading">
        <div class="panel-title-box col-md-5">
            <h3>${widget.heading}</h3>
            <span>${widget.subHeading}</span>
        </div>
        <div class="panel-title-box col-md-5">
            <h3>${widget.mapHeading}</h3>
            <span>${widget.mapSubHeading}</span>
        </div>
        <ul class="panel-controls" style="margin-top: 2px;">
            <li><a href="#" class="panel-fullscreen"><span class="fa fa-expand"></span></a></li>
            <li><a onclick="refresh('${widget.widget.controllerUrl}','${widget.widget.widgetName}','${widget.widget.widgetSeq}')" class="panel-refresh"><span class="fa fa-refresh"></span></a></li>
        </ul>
    </div>
    <div class="panel-body">
        <div class="row stacked">
            <div class="col-md-5">
                <c:forEach items="${widget.keyValuePairCustomList}" var="keyValuePairCustom">
                    <div class="progress-list">
                        <div class="pull-left"><strong>${keyValuePairCustom.key}</strong></div>
                        <div class="pull-right">${keyValuePairCustom.label}</div>
                        <div class="progress progress-small progress-striped active">
                            <div class="progress-bar progress-bar-${keyValuePairCustom.color}" role="progressbar" aria-valuenow="50" aria-valuemin="0" aria-valuemax="100" style="width: ${keyValuePairCustom.value}%;">${keyValuePairCustom.value}%</div>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <div class="col-md-7">
                <div id="dashboard-country-list${widget.widgetSeq}" style="width: 100%; height: 300px"></div>
            </div>
        </div>
    </div>
</div>
<!-- END SALES BLOCK -->

<script type="application/javascript">
    var locations = ${widget.latLongData};

    var map = new google.maps.Map(document.getElementById('dashboard-country-list${widget.widgetSeq}'), {
        zoom: 10,
        center: new google.maps.LatLng(locations[0].latitude, locations[0].longitude),
        mapTypeId: google.maps.MapTypeId.ROADMAP
    });

    var infowindow = new google.maps.InfoWindow();
    var marker, i;
    var markers = new Array();
    for (i = 0; i < locations.length; i++) {
        var colorType;
        if (locations[i].speed > 0) {
            colorType = "green";
        } else {
            colorType = "red";
        }

        var arrowHead = {
            path:google.maps.SymbolPath.FORWARD_CLOSED_ARROW,
            fillColor:colorType,
            fillOpacity:0.7,
            scale:4,
            strokeColor:"black",
            strokeWeight:1,
            rotation:locations[i].direction};

          marker = new MarkerWithLabel({
            position: new google.maps.LatLng(locations[i].latitude, locations[i].longitude),
            labelContent:locations[i].location,
            labelAnchor:new google.maps.Point(-10, 0),
            labelClass:"labels", // the CSS class for the label
            labelStyle:{opacity:0.75},
            draggable: false,
            raiseOnDrag: false,
            visible: true,
            icon:arrowHead,
            map:map
        });
        markers.push(marker);
    }

    var bounds = new google.maps.LatLngBounds();
    for (var i = 0; i < markers.length; i++) {
        bounds.extend(markers[i].getPosition());
    }
    var markerCluster = new MarkerClusterer(map, markers,
        {imagePath: 'https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m'});
    map.fitBounds(bounds);
</script>
