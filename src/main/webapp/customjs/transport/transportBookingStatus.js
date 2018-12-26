var formTemplate;

$(function () {
    formTemplate = $(".searchForm").html();
    $(".select").selectpicker();
    $(".datepicker").datepicker({dateFormat: 'yy-mm-dd'});
    $(".ajax-select").selectpicker().ajaxSelectPicker();
    $(".x-navigation-minimize").trigger("click");
});

function search_booking() {
    if (form_validate("searchForm")) {
        searchFormData('transport/transportBookingStatus/findBookings', 'searchForm').done(function (responseObject) {
            $('#bookingData').html(responseObject);
            $("#bookingDetailsSummary").DataTable({
                fixedColumns: {
                    "leftColumns": 1
                },
                scrollX: true,
                iDisplayLength: 25,
                fnDrawCallback: function () {
                    editableInit();
                },
                aaSorting: [
                    [2, 'desc']
                ]
            });
            view_hide('bookingSearchPanel');
        });
    }
}

function load_selected_booking(bookingSeq) {
    window.open('#/transport/transportBookingManagement?bookingSeq=' + bookingSeq, '_blank');
}

function load_selected_booking_summary(bookingSeq) {
    window.open('#/transport/transportBookingSummary?bookingSeq=' + bookingSeq, '_blank');
}

function reset() {
    reset_form(formTemplate, 'searchForm');
}

function editableInit() {
    $('.AAP').each(function () {
        var $this = $(this);
        $this.editable('transport/transportBookingStatus/changeAAP', {
            type: 'datetimepicker',
            tooltip: 'Click to edit...',
            loadtype: 'POST',
            event: 'click',
            width: '100px',
            height: '20px',
            cancel: '<button class="btn-xs btn-danger" type="cancel" ><i class="fa fa-close"></i></button>',
            submit: '<button class="btn-xs btn-success" type="submit"><i class="fa fa-check"></i></button>',
            submitdata: {id: $this.parent().attr('id')},
            intercept: function (jsondata) {
                var responseData = $.parseJSON(data);
                if (responseData.status === true) {
                    return (responseData.message);
                } else {
                    return '';
                }
            },
            callback: function (data, settings) {
                var transportBookingSeq = $this.parent().attr('id');
                var responseData = $.parseJSON(data);
                if (responseData.status === true) {
                    $(this).text(responseData.message);
                    $("#status" + transportBookingSeq).text(responseData.object.currentStatusDescription);
                    $("#status" + transportBookingSeq).attr('bgcolor', responseData.object.colorCode);
                } else {
                    new_notification(responseData.message, 'error')
                }
            }
        });
    });

    $('.DFP').each(function () {
        var $this = $(this);
        $this.editable('transport/transportBookingStatus/changeDFP', {
            type: 'datetimepicker',
            tooltip: 'Click to edit...',
            loadtype: 'POST',
            event: 'click',
            width: '100px',
            height: '20px',
            cancel: '<button class="btn-xs btn-danger" type="cancel" ><i class="fa fa-close"></i></button>',
            submit: '<button class="btn-xs btn-success" type="submit"><i class="fa fa-check"></i></button>',
            submitdata: {id: $this.parent().attr('id')},
            callback: function (data, settings) {
                var transportBookingSeq = $this.parent().attr('id');
                var responseData = $.parseJSON(data);
                if (responseData.status === true) {
                    $(this).text(responseData.message);
                    $("#status" + transportBookingSeq).text(responseData.object.currentStatusDescription);
                    $("#status" + transportBookingSeq).attr('bgcolor', responseData.object.colorCode);
                } else {
                    new_notification(responseData.message, 'error')
                }
            }
        });
    });

    $('.AAD').each(function () {
        var $this = $(this);
        $this.editable('transport/transportBookingStatus/changeAAD', {
            type: 'datetimepicker',
            tooltip: 'Click to edit...',
            loadtype: 'POST',
            event: 'click',
            width: '100px',
            height: '20px',
            cancel: '<button class="btn-xs btn-danger" type="cancel" ><i class="fa fa-close"></i></button>',
            submit: '<button class="btn-xs btn-success" type="submit"><i class="fa fa-check"></i></button>',
            submitdata: {id: $this.parent().attr('id')},
            callback: function (data, settings) {
                var transportBookingSeq = $this.parent().attr('id');
                var responseData = $.parseJSON(data);
                if (responseData.status === true) {
                    $(this).text(responseData.message);
                    $("#status" + transportBookingSeq).text(responseData.object.currentStatusDescription);
                    $("#status" + transportBookingSeq).attr('bgcolor', responseData.object.colorCode);
                } else {
                    new_notification(responseData.message, 'error')
                }
            }
        });
    });

    $('.DFD').each(function () {
        var $this = $(this);
        $this.editable('transport/transportBookingStatus/changeDFD', {
            type: 'datetimepicker',
            tooltip: 'Click to edit...',
            loadtype: 'POST',
            event: 'click',
            width: '100px',
            height: '20px',
            cancel: '<button class="btn-xs btn-danger" type="cancel" ><i class="fa fa-close"></i></button>',
            submit: '<button class="btn-xs btn-success" type="submit"><i class="fa fa-check"></i></button>',
            submitdata: {id: $this.parent().attr('id')},
            callback: function (data, settings) {
                var transportBookingSeq = $this.parent().attr('id');
                var responseData = $.parseJSON(data);
                if (responseData.status === true) {
                    $(this).text(responseData.message);
                    $("#status" + transportBookingSeq).text(responseData.object.currentStatusDescription);
                    $("#status" + transportBookingSeq).attr('bgcolor', responseData.object.colorCode);
                } else {
                    new_notification(responseData.message, 'error')
                }
            }
        });
    });

    $('.actualEndLocation').each(function () {
        var $this = $(this);
        $this.editable('transport/transportBookingStatus/changeActualEndLocation', {
            type: "finalDestination",
            tooltip: 'Click to edit...',
            loadtype: 'POST',
            event: 'click',
            cancel: '<button class="btn-xs btn-danger" type="cancel" ><i class="fa fa-close"></i></button>',
            submit: '<button class="btn-xs btn-success" type="submit"><i class="fa fa-check"></i></button>',
            submitdata: {id: $this.parent().attr('id')},
            callback: function (data, settings) {
                var responseData = $.parseJSON(data);
                if (responseData.status === true) {
                    $(this).text(responseData.object.destination);
                } else {
                    new_notification(responseData.message, 'error')
                }
            }
        });
    });

    $('.actualStartLocation').each(function () {
        var $this = $(this);
        $this.editable('transport/transportBookingStatus/changeActualStartLocation', {
            type: "finalDestination",
            tooltip: 'Click to edit...',
            loadtype: 'POST',
            event: 'click',
            cancel: '<button class="btn-xs btn-danger" type="cancel" ><i class="fa fa-close"></i></button>',
            submit: '<button class="btn-xs btn-success" type="submit"><i class="fa fa-check"></i></button>',
            submitdata: {id: $this.parent().attr('id')},
            callback: function (data, settings) {
                var responseData = $.parseJSON(data);
                if (responseData.status === true) {
                    $(this).text(responseData.object.destination);
                } else {
                    new_notification(responseData.message, 'error')
                }
            }
        });
    });

    $('.documentsCollectedDate').each(function () {
        var $this = $(this);
        $this.editable('transport/transportBookingStatus/changeDocumentsCollectedDate', {
            type: 'datepicker',
            tooltip: 'Click to edit...',
            loadtype: 'POST',
            event: 'click',
            width: '100px',
            height: '20px',
            cancel: '<button class="btn-xs btn-danger" type="cancel" ><i class="fa fa-close"></i></button>',
            submit: '<button class="btn-xs btn-success" type="submit"><i class="fa fa-check"></i></button>',
            submitdata: {id: $this.parent().attr('id')},
            callback: function (data, settings) {
                var transportBookingSeq = $this.parent().attr('id');
                var responseData = $.parseJSON(data);
                if (responseData.status === true) {
                    $(this).text(responseData.message);
                } else {
                    new_notification(responseData.message, 'error')
                }
            }
        });
    });

    $('.customerReferenceNo').each(function () {
        var $this = $(this);
        $this.editable('transport/transportBookingStatus/changeCustomerReferenceNo', {
            type: 'text',
            tooltip: 'Click to edit...',
            loadtype: 'POST',
            event: 'click',
            width: '100px',
            height: '20px',
            cancel: '<button class="btn-xs btn-danger" type="cancel" ><i class="fa fa-close"></i></button>',
            submit: '<button class="btn-xs btn-success" type="submit"><i class="fa fa-check"></i></button>',
            submitdata: {id: $this.parent().attr('id')},
            callback: function (data, settings) {
                var transportBookingSeq = $this.parent().attr('id');
                var responseData = $.parseJSON(data);
                if (responseData.status === true) {
                    $(this).text(responseData.message);
                } else {
                    new_notification(responseData.message, 'error')
                }
            }
        });
    });

}

function launch_map(transportBookingSeq) {
    initialize(transportBookingSeq);
}

var map = null;
var gmarkers = [];
var directionsService;
var directionsDisplay;
function initialize(transportBookingSeq) {
    loadObjectData('transport/transportBookingStatus/findGoogleMapJobRoute/', transportBookingSeq, 'GET').done(function (GoogleMapJobRoute) {
        $("#bookingNo").html(GoogleMapJobRoute.bookingNo);
        $("#estimatedKm").html(GoogleMapJobRoute.distance);
        $("#chargeableKm").html(GoogleMapJobRoute.chargeableKm);
        $("#eta").html(GoogleMapJobRoute.eta);
        var center = new google.maps.LatLng(GoogleMapJobRoute.center.lat, GoogleMapJobRoute.center.lng);
        if(map === null){
            directionsService = new google.maps.DirectionsService();
            directionsDisplay = new google.maps.DirectionsRenderer();
            map = new google.maps.Map(document.getElementById('map-canvas'), {
                zoom: 10,
                center: center,
                mapTypeId: google.maps.MapTypeId.ROADMAP
            });
        }else{
            directionsDisplay.setMap(null);
            for (var i = 0; i < gmarkers.length; i++) {
                gmarkers[i].setMap(null);
            }
            map = new google.maps.Map(document.getElementById('map-canvas'), {
                zoom: 10,
                center: center,
                mapTypeId: google.maps.MapTypeId.ROADMAP
            });
        }
        directionsDisplay.setMap(map);
        var infowindow = new google.maps.InfoWindow();
        var request = {
            travelMode: google.maps.TravelMode.DRIVING,
            optimizeWaypoints: true,
            avoidHighways: true
        };

        $.each(GoogleMapJobRoute.finalDestinationList, function (i, finalDestination) {
            if(finalDestination !== null){
                var  marker = new MarkerWithLabel({
                    position:new google.maps.LatLng(finalDestination.latitude, finalDestination.longitude),
                    labelContent:finalDestination.destination,
                    labelAnchor:new google.maps.Point(-10, 0),
                    labelClass:"googleLabel", // the CSS class for the label
                    labelStyle:{opacity:0.75},
                    draggable: false,
                    raiseOnDrag: false,
                    visible: true,
                    map:map
                });

                gmarkers.push(marker);
                google.maps.event.addListener(marker, 'click', (function (marker, i) {
                    return function () {
                        infowindow.setContent(finalDestination.destination);
                        infowindow.open(map, marker);
                    }
                })(marker, i));
                if (i === 0) request.origin = marker.getPosition();
                else if (i === GoogleMapJobRoute.finalDestinationList.length - 1) request.destination = marker.getPosition();
                else {
                    if (!request.waypoints) request.waypoints = [];
                    request.waypoints.push({
                        location: marker.getPosition(),
                        stopover: true
                    });
                }
            }
        });
        directionsService.route(request, function (result, status) {
            if (status === google.maps.DirectionsStatus.OK) {
                directionsDisplay.setDirections(result);
            }
        });
        $('#myModal').modal("show");
    });
}

