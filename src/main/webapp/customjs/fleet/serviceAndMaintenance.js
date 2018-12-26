var formTemplate;

$(function () {
    formTemplate = $(".serviceAndMaintenanceForm").html();
    $(".select").selectpicker();
    $(".datepicker").datepicker({dateFormat: 'yy-mm-dd'});
    $(".datetimepicker").datetimepicker({
        dateFormat: 'yy-mm-dd',
        timeFormat: 'hh:mm TT',
        controlType: 'select',
        maxDate: new Date()
    });
    $(".ajax-select").selectpicker().ajaxSelectPicker();
    $("#stakeholderSeq").trigger("change");
});

function load_vehicle_list_by_transporter(element) {
    var formName = $(element).closest('form').attr("id");
    var transporterSeq = $("#" + formName + " .stakeholderSeq").closest('select').val();
    if (transporterSeq !== '') {
        loadObjectData('fleet/serviceAndMaintenance/getVehicleListByTransporterSeq/', transporterSeq, "GET").done(function (vehicleList) {
            populate_dropdown_by_class(formName + ' .vehicleSeq', vehicleList, 'vehicleSeq', 'vehicleNo');
            populate_dropdown('search_vehicleSeq', vehicleList, 'vehicleSeq', 'vehicleNo');
            add_empty_option_to_dropdown('search_vehicleSeq', 'ALL');
            $("#search_vehicleSeq").val('');
            $("#search_vehicleSeq").selectpicker('refresh');
        });
        loadObjectData('fleet/serviceAndMaintenance/getDriverListByTransporterSeq/', transporterSeq, "GET").done(function (driverList) {
            populate_dropdown_by_class(formName + ' .employeeSeq', driverList, 'employeeSeq', 'employeeName');
            set_default_driver(element);
        });
    }
}

function load_vehicle_list_by_transporter_search() {
    var transporterSeq = $("#search_stakeholderSeq").val();
    if (transporterSeq !== '') {
        loadObjectData('fleet/serviceAndMaintenance/getVehicleListByTransporterSeq/', transporterSeq, "GET").done(function (vehicleList) {
            populate_dropdown('search_vehicleSeq', vehicleList, 'vehicleSeq', 'vehicleNo');
            add_empty_option_to_dropdown('search_vehicleSeq', 'ALL');
            $("#search_vehicleSeq").val('');
        });
    }
}

function set_default_driver(element) {
    var formName = $(element).closest('form').attr("id");
    var vehicleSeq = $("#" + formName + " .vehicleSeq").closest('select').val();
    if (vehicleSeq !== '') {
        loadObjectData('fleet/serviceAndMaintenance/getVehicle/', vehicleSeq, "GET").done(function (vehicle) {
            $("#" + formName + " .employeeSeq").val(vehicle.defaultDriverSeq);
            $("#" + formName + " .employeeSeq").selectpicker('refresh');
        });
    }
}

function calculate_amount(element) {
    var formName = $(element).closest('form').attr("id");
    var unitPrice, quantity;
    unitPrice = $("#" + formName + " .unitPrice").val();
    quantity = $("#" + formName + " .quantity").val();
    var amount = (unitPrice * quantity );
    $("#" + formName + " .amount").val(amount.toFixed(2));
}

function create_serviceAndMaintenance() {
    if (form_validate("create")) {
        saveFormData('fleet/serviceAndMaintenance/saveServiceAndMaintenance', 'create', 'reset').done(function (responseObject) {
            if (responseObject.status === true) {
                loadObjectData('fleet/serviceAndMaintenance/findByServiceAndMaintenanceSeq/', responseObject.object.serviceAndMaintenanceSeq, 'GET').done(function (serviceAndMaintenance) {
                    transform_form(formTemplate, 'create', 'update', {serviceAndMaintenance: serviceAndMaintenance});
                });
            }
        });
    }
}

function update_serviceAndMaintenance() {
    if (form_validate("update")) {
        saveFormData('fleet/serviceAndMaintenance/updateServiceAndMaintenance', 'update').done(function (responseObject) {
            if (responseObject.status === true) {
                loadObjectData('fleet/serviceAndMaintenance/findByServiceAndMaintenanceSeq/', responseObject.object.serviceAndMaintenanceSeq, 'GET').done(function (serviceAndMaintenance) {
                    transform_form(formTemplate, 'update', 'update', {serviceAndMaintenance: serviceAndMaintenance});
                });
            }
        });
    }
}

function search_serviceAndMaintenance() {
    searchFormData('fleet/serviceAndMaintenance/searchServiceAndMaintenance', 'search').done(function (responseObject) {
        $("#loadServiceAndMaintenanceData").html(responseObject);
        $('.datatable').DataTable();
    });
}

function update_serviceAndMaintenance_modal() {
    if (form_validate("modal")) {
        saveFormData('fleet/serviceAndMaintenance/updateServiceAndMaintenance', 'modal').done(function (responseObject) {
            if (responseObject.status === true) {
                load_serviceAndMaintenance_data_to_modal(responseObject.object.serviceAndMaintenanceSeq, 'modal');
            }
        });
    }
}

function load_serviceAndMaintenance_data_to_modal(serviceAndMaintenanceSeq) {
    loadObjectData('fleet/serviceAndMaintenance/findByServiceAndMaintenanceSeq/', serviceAndMaintenanceSeq, 'GET').done(function (serviceAndMaintenance) {
        display_update_modal(formTemplate, 'myModal', 'modal', {serviceAndMaintenance: serviceAndMaintenance});
    });
}

function load_item_detail(element, fieldName) {
    var formName = $(element).closest('form').attr("id");
    var attrId = $(element).attr("id");
    var itemSeq = $(element).val();
    var param = attrId.match(/\d+/);
    if (itemSeq !== '') {
        loadObjectData('fleet/serviceAndMaintenance/findByItemSeq/', itemSeq, 'GET').done(function (item) {
            $('input[name="' + fieldName + '[' + param + '].unitPrice"]').val(item.unitPrice);
            $('input[name="' + fieldName + '[' + param + '].unitSeq"]').val(item.unitSeq);
            populate_ajax_dropdown_single_element($('input[name="' + fieldName + '[' + param + '].unitSeq"]'), item.unitSeq, item.unit.unitCode);
            $('input[name="' + fieldName + '[' + param + '].kmExpiration"]').val(item.kmExpiration);
            $('input[name="' + fieldName + '[' + param + '].durationExpiration"]').val(item.durationExpiration);
        });
    }
}

function calculate_charge_amount(fieldName, obj) {
    var formName = $(obj).closest('form').attr("id");
    var attrId = $(obj).attr("id");
    var chargeValue, qty, amount;
    var param = attrId.match(/\d+/);
    chargeValue = $('input[name="' + fieldName + '[' + param + '].unitPrice"]').val();
    qty = $('input[name="' + fieldName + '[' + param + '].quantity"]').val();
    amount = (chargeValue * qty );
    $('input[name="' + fieldName + '[' + param + '].amount"]').val(amount.toFixed(2));
    calculate_total(obj)
}

function calculate_total(obj) {
    var formName = $(obj).closest('form').attr("id");
    var totalAmount = 0.0;
    $("#" + formName + " .amount").each(function () {
        totalAmount = Number(totalAmount) + Number($(this).val());
    });

    var taxAmount = 0.0;
    $("#" + formName + " .taxAmount").each(function () {
        taxAmount = Number(taxAmount) + Number($(this).val());
    });
    var finalAmount = totalAmount + taxAmount;
    $("#" + formName + " .totalAmount").val(totalAmount.toFixed(2));
    $("#" + formName + " .finalAmount").val(finalAmount.toFixed(2));
}


function delete_serviceAndMaintenance() {
    var formId = $(this)[0].getAttribute('data-id');
    var serviceAndMaintenanceSeq = $('#' + formId + ' :input[name=serviceAndMaintenanceSeq]').val();
    var data = {'serviceAndMaintenanceSeq': serviceAndMaintenanceSeq};
    loadDataPost('fleet/serviceAndMaintenance/deleteByServiceAndMaintenanceSeq', data).done(function (responseObject) {
        if (responseObject.status === true) {
            if (formId === 'modal') {
                $('#myModal').modal('toggle');
                new_notification(responseObject.message, 'success');
                $("#tr_" + serviceAndMaintenanceSeq).remove();
            } else {
                new_form(formTemplate, 'update', 'create');
                new_notification(responseObject.message, 'success');
            }
        } else {
            new_notification(responseObject.message, 'error');
        }
    });
}

function validateFloatKeyPress(el) {
    var v = parseFloat(el.value);
    el.value = (isNaN(v)) ? '' : v.toFixed(2);
}
