var formTemplate;

$(function () {
    formTemplate = $(".searchForm").html();
    $(".select").selectpicker();
    $(".datepicker").datepicker({dateFormat: 'yy-mm-dd'});
    $(".ajax-select").selectpicker().ajaxSelectPicker();
    $(".x-navigation-minimize").trigger("click");
});

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

    $('.driverName').each(function () {
        var $this = $(this);
        $this.editable('transport/vehicleAssignment/changeDriver', {
            type: "driver",
            tooltip: 'Click to edit...',
            loadtype: 'POST',
            event: 'click',
            cancel: '<button class="btn-xs btn-danger" type="cancel" ><i class="fa fa-close"></i></button>',
            submit: '<button class="btn-xs btn-success" type="submit"><i class="fa fa-check"></i></button>',
            submitdata: {id: $this.parent().attr('id')},
            callback: function (data, settings) {
                var responseData = $.parseJSON(data);
                if (responseData.status === true) {
                    $(this).text(responseData.object.employeeName);
                } else {
                    new_notification(responseData.message, 'error')
                }
            }
        });
    });

    $('.helperName').each(function () {
        var $this = $(this);
        $this.editable('transport/vehicleAssignment/changeHelper', {
            type: "helper",
            tooltip: 'Click to edit...',
            loadtype: 'POST',
            event: 'click',
            cancel: '<button class="btn-xs btn-danger" type="cancel" ><i class="fa fa-close"></i></button>',
            submit: '<button class="btn-xs btn-success" type="submit"><i class="fa fa-check"></i></button>',
            submitdata: {id: $this.parent().attr('id')},
            callback: function (data, settings) {
                var responseData = $.parseJSON(data);
                if (responseData.status === true) {
                    $(this).text(responseData.object.employeeName);
                } else {
                    new_notification(responseData.message, 'error')
                }
            }
        });
    });
}

function search_booking() {
    if (form_validate("searchForm")) {
        searchFormData('transport/vehicleAssignment/findBookings', 'searchForm').done(function (responseObject) {
            $('#bookingData').html(responseObject);
            $("#bookingData .datatable").DataTable({
                fixedColumns: {
                    "leftColumns": 1
                },
                scrollX: true,
                fnDrawCallback: function () {
                    editableInit();
                }
            });
        });
    }
}

function assign_vehicle() {
    if (form_validate("modal")) {
        saveFormData('transport/vehicleAssignment/assignVehicle', 'modal', 'reset').done(function (responseObject) {
            if (responseObject.status === true) {
                loadObjectData('transport/vehicleAssignment/findByTransportBookingVehicleSeq/', responseObject.object.transportBookingVehicleSeq, 'GET').done(function (transportBookingVehicle) {
                    $("#vehicleNo" + transportBookingVehicle.transportBookingSeq).html(transportBookingVehicle.vehicle.vehicleNo);
                    $("#transporterName" + transportBookingVehicle.transportBookingSeq).html(transportBookingVehicle.transportCompany.stakeholderName);
                    $("#driverName" + transportBookingVehicle.transportBookingSeq).html(transportBookingVehicle.driver.employeeName);
                    if(transportBookingVehicle.helper !== null && transportBookingVehicle.helper !== undefined){
                        $("#helperName" + transportBookingVehicle.transportBookingSeq).html(transportBookingVehicle.helper.employeeName);
                    }
                    $("#myModal").modal("hide");
                });
            }
        });
    }
}

function remove_vehicle() {
    saveFormData('transport/vehicleAssignment/removeVehicle', 'modal', 'reset').done(function (responseObject) {
        if (responseObject.status === true) {
            $("#vehicleNo" + responseObject.transportBookingSeq).html('');
            $("#transporterName" + responseObject.transportBookingSeq).html('');
            $("#driverName" + responseObject.transportBookingSeq).html('');
            $("#myModal").modal("hide");
        }
    });
}

function load_selected_booking(bookingSeq) {
    window.open('#/transport/transportBookingManagement?bookingSeq=' + bookingSeq, '_blank');
}

function reset() {
    reset_form(formTemplate, 'searchForm');
}

function load_assignment_panel(bookingSeq) {
    var data = {'bookingSeq': bookingSeq};
    loadPageData('transport/vehicleAssignment/loadAssignmentPanel', data, 'POST').done(function (responseObject) {
        $('#assignVehicleData').html(responseObject);
        load_vehicle_list_by_transporter();
        load_modal('myModal', 'modal', null);
        var transportBookingVehicleSeq = $("#transportBookingVehicleSeq").val();
        $("#driverScore").html($("#driverData").html());
        $("#helperScore").html($("#helperData").html());
        if (transportBookingVehicleSeq === '') {
            $(".updateOperation").hide();
        }else{
            $(".updateOperation").show();
        }
    });
}

function load_vehicle_list_by_transporter() {
    var transporterSeq = $("#transportCompanySeq").val();
    if (transporterSeq !== '') {
        loadObjectData('transport/vehicleAssignment/getVehicleListByTransporterSeq/', transporterSeq, "GET").done(function (vehicleList) {
            populate_dropdown('vehicleSeq', vehicleList, 'vehicleSeq', 'vehicleNo');
        });
        loadObjectData('transport/vehicleAssignment/getDriverListByTransporterSeq/', transporterSeq, "GET").done(function (driverList) {
            populate_dropdown('driverSeq', driverList, 'employeeSeq', 'employeeName');
        });
        loadObjectData('transport/vehicleAssignment/getHelperListByTransporterSeq/', transporterSeq, "GET").done(function (driverList) {
            populate_dropdown('helperSeq', driverList, 'employeeSeq', 'employeeName');
            add_empty_option_to_dropdown('helperSeq','NO HELPER');
        });
    }
}

function loadDriverScorecard() {
    $('#driverModal').modal("show");
}

function loadHelperScorecard() {
    $('#helperModal').modal("show");
}
