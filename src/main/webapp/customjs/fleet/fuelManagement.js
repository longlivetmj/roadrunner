var formTemplate;

$(function () {
    formTemplate = $(".fuelManagementForm").html();
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
        loadObjectData('fleet/fuelManagement/getVehicleListByTransporterSeq/', transporterSeq, "GET").done(function (vehicleList) {
            populate_dropdown_by_class(formName + ' .vehicleSeq', vehicleList, 'vehicleSeq', 'vehicleNo');
            populate_dropdown('search_vehicleSeq', vehicleList, 'vehicleSeq', 'vehicleNo');
            add_empty_option_to_dropdown('search_vehicleSeq', 'ALL');
            $("#search_vehicleSeq").val('');
            $("#search_vehicleSeq").selectpicker('refresh');
        });
        loadObjectData('fleet/fuelManagement/getDriverListByTransporterSeq/', transporterSeq, "GET").done(function (driverList) {
            populate_dropdown_by_class(formName + ' .employeeSeq', driverList, 'employeeSeq', 'employeeName');
            set_default_driver(element);
            get_fuel_variants(element);
        });
    }
}

function load_vehicle_list_by_transporter_search() {
    var transporterSeq = $("#search_stakeholderSeq").val();
    if (transporterSeq !== '') {
        loadObjectData('fleet/fuelManagement/getVehicleListByTransporterSeq/', transporterSeq, "GET").done(function (vehicleList) {
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
        loadObjectData('fleet/fuelManagement/getVehicle/', vehicleSeq, "GET").done(function (vehicle) {
            $("#" + formName + " .employeeSeq").val(vehicle.defaultDriverSeq);
            $("#" + formName + " .employeeSeq").selectpicker('refresh');
            get_fuel_variants(element);
        });
    }
}

function get_fuel_variants(element) {
    var formName = $(element).closest('form').attr("id");
    var vehicleSeq = $("#" + formName + " .vehicleSeq").closest('select').val();
    if (vehicleSeq !== '') {
        loadObjectData('fleet/fuelManagement/getFuelVariantList/', vehicleSeq, "GET").done(function (fuelTypeVariantList) {
            populate_dropdown_by_class(formName + ' .fuelTypeVariantSeq', fuelTypeVariantList, 'fuelTypeVariantSeq', 'variantName');
            find_fuel_type_variant(element);
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

function find_fuel_type_variant(element) {
    var formName = $(element).closest('form').attr("id");
    var fuelTypeVariantSeq = $("#" + formName + " .fuelTypeVariantSeq").closest('select').val();
    if (fuelTypeVariantSeq !== '') {
        loadObjectData('fleet/fuelManagement/findFuelTypeVariant/', fuelTypeVariantSeq, "GET").done(function (fuelTypeVariant) {
            $("#" + formName + " .unitPrice").val(fuelTypeVariant.unitPrice);
        });
    }
}

function create_fuel() {
    if (form_validate("create")) {
        saveFormData('fleet/fuelManagement/saveFuel', 'create', 'reset').done(function (responseObject) {
            if (responseObject.status === true) {
                loadObjectData('fleet/fuelManagement/findByVehicleFuelSeq/', responseObject.object.vehicleFuelSeq, 'GET').done(function (vehicleFuel) {
                    transform_form(formTemplate, 'create', 'update', {vehicleFuel: vehicleFuel});
                });
            }
        });
    }
}

function update_fuel() {
    if (form_validate("update")) {
        saveFormData('fleet/fuelManagement/updateFuel', 'update').done(function (responseObject) {
            if (responseObject.status === true) {
                loadObjectData('fleet/fuelManagement/findByVehicleFuelSeq/', responseObject.object.vehicleFuelSeq, 'GET').done(function (vehicleFuel) {
                    transform_form(formTemplate, 'update', 'update', {vehicleFuel: vehicleFuel});
                });
            }
        });
    }
}

function search_fuel() {
    searchFormData('fleet/fuelManagement/searchFuel', 'search').done(function (responseObject) {
        $("#loadFuelData").html(responseObject);
        $('.datatable').DataTable();
    });
}

function update_fuel_modal() {
    if (form_validate("modal")) {
        saveFormData('fleet/fuelManagement/updateFuel', 'modal').done(function (responseObject) {
            if (responseObject.status === true) {
                load_fuel_data_to_modal(responseObject.object.fuelSeq, 'modal');
            }
        });
    }
}

function load_fuel_data_to_modal(vehicleFuelSeq) {
    loadObjectData('fleet/fuelManagement/findByVehicleFuelSeq/', vehicleFuelSeq, 'GET').done(function (vehicleFuel) {
        display_update_modal(formTemplate, 'myModal', 'modal', {vehicleFuel: vehicleFuel});;
    });
}

function delete_fuel() {
    var formId = $(this)[0].getAttribute('data-id');
    var vehicleFuelSeq = $('#' + formId + ' :input[name=vehicleFuelSeq]').val();
    var data = {'vehicleFuelSeq': vehicleFuelSeq};
    loadDataPost('fleet/fuelManagement/deleteByVehicleFuelSeq', data).done(function (responseObject) {
        if (responseObject.status === true) {
            if (formId === 'modal') {
                $('#myModal').modal('toggle');
                new_notification(responseObject.message, 'success');
                $("#tr_" + vehicleFuelSeq).remove();
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


function set_fuel_type(formName, fuel) {
    $("#" + formName + " .fuelTypeSeqList").selectpicker('destroy');
    $("#" + formName + " .fuelTypeSeqList option:selected").removeAttr("selected");
    $.each(fuel.fuelFuelTypeList, function (i, fuelType) {
        $("#" + formName + " .fuelTypeSeqList option[value='" + fuelType.fuelTypeSeq + "']").prop("selected", true);
    });
    $("#" + formName + " .fuelTypeSeqList").selectpicker('refresh');

}
