var createBooking;
var addViaLocations;

$(function () {
    createBooking = $(".createBooking").html();
    addViaLocations = $(".addViaLocations").html();
    change_tab_status();

    $(".ajax-select").selectpicker().ajaxSelectPicker();
    $(".select").selectpicker();
    $(".datepicker").datepicker({dateFormat: 'yy-mm-dd'});
    $(".datetimepicker").datetimepicker({
        dateFormat: 'yy-mm-dd',
        timeFormat: 'hh:mm TT',
        controlType: 'select'
    });
    $('[data-toggle=confirmation]').confirmation({
        rootSelector: '[data-toggle=confirmation]'
    });
    load_via_locations($("#transportBookingSeq").val());
});

function create_booking() {
    if (form_validate("createBooking")) {
        saveFormData('transport/transportBookingManagement/saveTransportBooking', 'createBooking').done(function (responseObject) {
            if (responseObject.status === true) {
                loadObjectData('transport/transportBookingManagement/findByTransportBookingSeq/', responseObject.object.transportBookingSeq, 'GET').done(function (transportBooking) {
                    transform_form(createBooking, 'createBooking', 'createBooking', {transportBooking: transportBooking});
                    change_tab_status();
                    $('#viaLocations_transportBookingSeq').val(transportBooking.transportBookingSeq);
                });
            }
        });
    }
}

function update_booking() {
    if (form_validate("createBooking")) {
        var responseObject = saveFormData('transport/transportBookingManagement/updateTransportBooking', 'createBooking');
        if (responseObject.status === true) {
            loadObjectData('transport/transportBookingManagement/findBookingByBookingSeq/', responseObject.object.bookingSeq, 'GET').done(function (transportBooking) {
                transform_form(createBooking, 'createBooking', 'createBooking', {transportBooking: transportBooking});
                change_tab_status();
                $('#viaLocations_transportBookingSeq').val(transportBooking.transportBookingSeq);
            });
        }
    }
}

function set_shipper_invoice_customer() {
    var customerSeq = $('select#customerSeq').val();
    if (customerSeq !== '') {
        loadObjectData('transport/transportBookingManagement/findStakeholderSeqSeq/', customerSeq, 'GET').done(function (stakeholder) {
            populate_dropdown_single_element('shipperSeq', stakeholder, 'stakeholderSeq', 'stakeholderName');
            populate_dropdown_single_element('invoiceCustomerSeq', stakeholder, 'stakeholderSeq', 'stakeholderName');
        });
    }
    load_vehicle_types();
}

function load_pick_address_data() {
    var pickupLocationSeq = $('select#pickupLocationSeq').val();
    if (pickupLocationSeq !== '') {
        loadObjectData('transport/transportBookingManagement/findAddressBookByFinalDestinationSeq/', pickupLocationSeq, 'GET').done(function (addressBook) {
            $("#pickupLocationAddress").val(cleanAddressBook(addressBook));
        });
        loadObjectData('transport/transportBookingManagement/findFinalDestinationSeq/', pickupLocationSeq, 'GET').done(function (finalDestination) {
            populate_dropdown_single_element('actualStartLocationSeq', finalDestination, 'finalDestinationSeq', 'destination');
        });
    }
}

function load_delivery_address_data() {
    var deliveryLocationSeq = $('select#deliveryLocationSeq').val();
    if (deliveryLocationSeq !== '') {
        loadObjectData('transport/transportBookingManagement/findAddressBookByFinalDestinationSeq/', deliveryLocationSeq, 'GET').done(function (addressBook) {
            $("#deliveryLocationAddress").val(cleanAddressBook(addressBook));
        });
        loadObjectData('transport/transportBookingManagement/findFinalDestinationSeq/', deliveryLocationSeq, 'GET').done(function (finalDestination) {
            populate_dropdown_single_element('actualEndLocationSeq', finalDestination, 'finalDestinationSeq', 'destination');
        });
    }
}

function load_vehicle_types() {
    var customerSeq = $('select#customerSeq').val();
    if (customerSeq !== '') {
        loadObjectData('transport/transportBookingManagement/findVehicleTypeList/', customerSeq, 'GET').done(function (vehicleTypeList) {
            populate_dropdown('vehicleTypeSeq', vehicleTypeList, 'vehicleTypeSeq', 'vehicleTypeName');
        });
    }
}

function change_tab_status() {
    var bookingSeq = $('#transportBookingSeq').val();
    if (bookingSeq === '') {
        $("#viaLocations").hide();
    } else {
        $("#viaLocations").show();
        $('#viaLocations_transportBookingSeq').val(bookingSeq);
        showUpdateOptions("createBooking");
    }
}

function delete_booking() {
    var bookingSeq = $("#transportBookingSeq").val();
    var data = {'bookingSeq': bookingSeq};
    loadDataPost('transport/transportBookingManagement/deleteBooking', data).done(function (responseObject) {
        if (responseObject.status === true) {
            new_notification(responseObject.message, 'warning', 4000);
            load_booking_page();
        } else {
            new_notification(responseObject.message, 'error');
        }
    });
}

function create_via_locations() {
    if (form_validate("addViaLocations")) {
        saveFormData('transport/transportBookingManagement/addViaLocations', 'addViaLocations').done(function (responseObject) {
            if (responseObject.status === true) {
                load_via_locations($('#viaLocations_transportBookingSeq').val());
            }
        });
    }
}

function update_via_locations() {
    if (form_validate("addViaLocations")) {
        saveFormData('transport/transportBookingManagement/updateViaLocations', 'addViaLocations').done(function (responseObject) {
            if (responseObject.status === true) {
                load_via_locations($('#viaLocations_transportBookingSeq').val());
            }
        });
    }
}

function load_via_locations(bookingSeq) {
    if (bookingSeq !== '' && bookingSeq !== null) {
        loadObjectData('transport/transportBookingManagement/findViaLocationsByBookingSeq/', bookingSeq, 'GET').done(function (viaLocations) {
            if (viaLocations !== null && viaLocations.length > 0) {
                transform_form(addViaLocations, 'addViaLocations', 'addViaLocations', {bookingViaLocations: viaLocations});
            }
            $("#viaLocations_transportBookingSeq").val(bookingSeq);
        });
    }
}

function load_via_address_data(element) {
    var locationSeq = $(element).val();
    if (locationSeq !== '') {
        loadObjectData('transport/transportBookingManagement/findAddressBookByFinalDestinationSeq/', locationSeq, 'GET').done(function (addressBook) {
            var elementId = $(element).attr('id');
            elementId = elementId.replace('viaLocationSeq', 'viaLocationAddress');
            $("#" + elementId).val(cleanAddressBook(addressBook));
        });
    }
}

function load_booking_page() {
    loadPageContent("transport/transportBookingManagement");
    window.location.href = "#/transport/transportBookingManagement";
}

function add_table_row_custom(tableClass, object) {
    add_table_row(tableClass, object);
    $('.' + tableClass + ' tr:last .dimensionsVal').each(function () {
        $(this).val(0);
    });
}

function copy_booking() {
    var bookingSeq = $('#transportBookingSeq').val();
    window.open('#/transport/transportBookingManagement/copyBooking/' + bookingSeq, '_blank');
}