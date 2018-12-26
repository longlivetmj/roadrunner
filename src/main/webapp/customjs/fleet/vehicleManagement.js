var formTemplate;

$(function () {
    formTemplate = $(".vehicleManagementForm").html();
    $(".select").selectpicker();
    $(".datepicker").datepicker({dateFormat: 'yy-mm-dd'});
    $(".ajax-select").selectpicker().ajaxSelectPicker();
    init('create');
});

function init(formName) {
    initializeCustomAjaxByClass(formName, 'vehicleModal', '/fleet/vehicleManagement/findVehicleModal', 'vehicleManufacturerSeq');
    initializeCustomAjaxByClass(formName, 'defaultDriver', '/fleet/vehicleManagement/findDriver', 'stakeholderSeq');
    initializeCustomAjaxByClass(formName, 'secondaryDriver', '/fleet/vehicleManagement/findDriver', 'stakeholderSeq');
    initializeCustomAjaxByClass(formName, 'defaultHelper', '/fleet/vehicleManagement/findHelper', 'stakeholderSeq');
    initializeCustomAjaxByClass(formName, 'secondaryHelper', '/fleet/vehicleManagement/findHelper', 'stakeholderSeq');
}

function create_vehicle() {
    if (form_validate("create")) {
        saveFormData('fleet/vehicleManagement/saveVehicle', 'create', 'reset').done(function (responseObject) {
            if (responseObject.status === true) {
                loadObjectData('fleet/vehicleManagement/findByVehicleSeq/', responseObject.object.vehicleSeq, 'GET').done(function (vehicle) {
                    transform_form(formTemplate, 'create', 'update', {vehicle: vehicle});
                    init('update');
                });
            }
        });
    }
}

function update_vehicle() {
    if (form_validate("update")) {
        saveFormData('fleet/vehicleManagement/updateVehicle', 'update').done(function (responseObject) {
            if (responseObject.status === true) {
                loadObjectData('fleet/vehicleManagement/findByVehicleSeq/', responseObject.object.vehicleSeq, 'GET').done(function (vehicle) {
                    transform_form(formTemplate, 'update', 'update', {vehicle: vehicle});
                    init('update');
                    set_vehicle_type('update', vehicle);
                });
            }
        });
    }
}

function search_vehicle() {
    searchFormData('fleet/vehicleManagement/searchVehicle', 'search').done(function (responseObject) {
        $("#loadVehicleData").html(responseObject);
        $('.datatable').DataTable();
    });
}

function update_vehicle_modal() {
    if (form_validate("modal")) {
        saveFormData('fleet/vehicleManagement/updateVehicle', 'modal').done(function (responseObject) {
            if (responseObject.status === true) {
                load_vehicle_data_to_modal(responseObject.object.vehicleSeq, 'modal');
            }
        });
    }
}

function load_vehicle_data_to_modal(vehicleSeq) {
    loadObjectData('fleet/vehicleManagement/findByVehicleSeq/', vehicleSeq, 'GET').done(function (vehicle) {
        display_update_modal(formTemplate, 'myModal', 'modal', {vehicle: vehicle});
        $('#companyProfileSeq').val(vehicle.companyProfileSeq);
        init('modal');
        set_vehicle_type('modal', vehicle);
    });
}

function delete_vehicle() {
    var vehicleSeq = $('#vehicleSeq').val();
    var data = {'vehicleSeq': vehicleSeq};
    loadDataPost('fleet/vehicleManagement/deleteByVehicleSeq', data).done(function (responseObject) {
        if (responseObject.status === true) {
            new_notification(responseObject.message, 'success');
            new_form(formTemplate, 'update', 'create')
        } else {
            new_notification(responseObject.message, 'error');
        }
    });
}

function validateFloatKeyPress(el) {
    var v = parseFloat(el.value);
    el.value = (isNaN(v)) ? '' : v.toFixed(2);
}


function set_vehicle_type(formName, vehicle) {
    $("#" + formName + " .vehicleTypeSeqList").selectpicker('destroy');
    $("#" + formName + " .vehicleTypeSeqList option:selected").removeAttr("selected");
    $.each(vehicle.vehicleVehicleTypeList, function (i, vehicleType) {
        $("#" + formName + " .vehicleTypeSeqList option[value='" + vehicleType.vehicleTypeSeq + "']").prop("selected", true);
    });
    $("#" + formName + " .vehicleTypeSeqList").selectpicker('refresh');

}
