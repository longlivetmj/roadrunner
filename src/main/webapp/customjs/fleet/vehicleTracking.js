$(function () {
    $(".select").selectpicker();
    $(".datepicker").datepicker({dateFormat: 'yy-mm-dd'});
    $(".ajax-select").selectpicker().ajaxSelectPicker();
    search_vehicle();
});

function search_vehicle() {
    searchFormData('fleet/vehicleTracking/searchVehicle', 'search').done(function (responseObject) {
        vehicles = responseObject;
        initMap();
    });
}

// counter for online cars...
var cars_count = 0;
var vehicles;
// markers array to store all the markers, so that we could remove marker when any car goes offline and its data will be remove from realtime database...
var markers = [];
var keys = [];
var map;
var markerCluster;

function initMap() {
    map = new google.maps.Map(document.getElementById('map'), {
        zoom: 10,
        center: new google.maps.LatLng(7.013187, 79.899018),
        mapTypeId: google.maps.MapTypeId.ROADMAP
    });
    markerCluster = new MarkerClusterer(map, markers,
        {imagePath: 'https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m'});
}

function findVehicle(gpsTerminalKey) {
    var foundVehicle = null;
    $.each(vehicles, function (i, vehicle) {
        if (vehicle.gpsTerminalKey == gpsTerminalKey) {
            foundVehicle = vehicle;
            return false;
        }
    });
    return foundVehicle;
}

// This Function will create a car icon with angle and add/display that marker on the map
function AddCar(data, vehicle) {
    var colorType;
    if (data.val().speed > 0) {
        colorType = "green";
    } else {
        colorType = "red";
    }

    var arrowHead = {
        path: google.maps.SymbolPath.FORWARD_CLOSED_ARROW,
        fillColor: colorType,
        fillOpacity: 0.7,
        scale: 4,
        strokeColor: "black",
        strokeWeight: 1,
        rotation: data.val().direction
    };

    var position = {lat: data.val().latitude, lng: data.val().longitude};

    var marker = new MarkerWithLabel({
        position: position,
        labelContent: data.val().vehicleNo,
        labelAnchor: new google.maps.Point(-10, 0),
        labelClass: "labels", // the CSS class for the label
        labelStyle: {opacity: 0.75},
        draggable: false,
        raiseOnDrag: false,
        visible: true,
        icon: arrowHead,
        map: map
    });

    google.maps.event.addListener(marker, 'click', function () {
        var infobox = new InfoBox({
            content: vehicle.message,
            disableAutoPan: false,
            maxWidth: 300,
            pixelOffset: new google.maps.Size(-140, 0),
            zIndex: null,
            boxStyle: {
                background: "url('theme/img/tipbox.gif') no-repeat",
                opacity: 0.75,
                width: "280px"
            },
            closeBoxMargin: "12px 4px 2px 2px",
            closeBoxURL: "theme/img/closeTip.gif",
            infoBoxClearance: new google.maps.Size(1, 1)
        });
        infobox.open(map, this);
        map.panTo(position);
    });

    markers[data.key] = marker; // add marker in the markers array...
    keys.push(data.key);
    markerCluster.clearMarkers();
    markerCluster.addMarkers(markers);
}

// get firebase database reference...
var cars_Ref = firebase.database().ref("/vehicles");

// this event will be triggered when a new object will be added in the database...
cars_Ref.on('child_added', function (data) {
    var vehicle = findVehicle(data.val().gpsTerminalKey);
    if (vehicle != null) {
        cars_count++;
        AddCar(data, vehicle);
    }
});

// this event will be triggered on location change of any car...
cars_Ref.on('child_changed', function (data) {
    var vehicle = findVehicle(data.val().gpsTerminalKey);
    if (vehicle != null) {
        markers[data.key].setMap(null);
        AddCar(data, vehicle);
    }
});

// If any car goes offline then this event will get triggered and we'll remove the marker of that car...
cars_Ref.on('child_removed', function (data) {
    var vehicle = findVehicle(data.val().gpsTerminalKey);
    if (vehicle != null) {
        markers[data.key].setMap(null);
        cars_count--;
    }
});
