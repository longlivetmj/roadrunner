var formTemplate;
$(function () {
    formTemplate = $(".locationForm").html();
    $(".select").selectpicker();
    $(".ajax-select").selectpicker().ajaxSelectPicker();
});

function create_location() {
    if (form_validate("create")) {
        saveFormData('master/locationManagement/createLocation', 'create').done(function (responseObject) {
            if (responseObject.status === true) {
                loadObjectData('master/locationManagement/findLocationByLocationSeq/', responseObject.object.locationSeq, 'GET').done(function (location) {
                    transform_form(formTemplate, 'create', 'update', {location: location});
                });
            }
        });
    }
}

function delete_location() {
    var formId = $(this)[0].getAttribute('data-id');
    var locationSeq = $('#' + formId + ' :input[name=locationSeq]').val();
    var data = {'locationSeq': locationSeq};
    loadDataPost('master/locationManagement/deleteByLocationSeq', data).done(function (responseObject) {
        if (responseObject.status === true) {
            if (formId === 'modal') {
                $('#myModal').modal('toggle');
                new_notification(responseObject.message, 'success');
                $("#tr_" + locationSeq).remove();
            } else {
                new_notification(responseObject.message, 'success');
                new_form(formTemplate, 'update', 'create');
            }
        } else {
            new_notification(responseObject.message, 'error');
        }
    });
}

function update_location() {
    if (form_validate("update")) {
        saveFormData('master/locationManagement/updateLocation', 'update').done(function (responseObject) {
            if (responseObject.status === true) {
                loadObjectData('master/locationManagement/findLocationByLocationSeq/', responseObject.object.locationSeq, 'GET').done(function (location) {
                    transform_form(formTemplate, 'update', 'update', {location: location});
                });
            }
        });
    }
}

function update_location_modal() {
    if (form_validate("modal")) {
        saveFormData('master/locationManagement/updateLocation', 'modal').done(function (responseObject) {
            if (responseObject.status === true) {
                load_location_data_to_modal(responseObject.object.locationSeq);
            }
        });
    }
}

function load_location_data_to_modal(locationSeq) {
    loadObjectData('master/locationManagement/findLocationByLocationSeq/', locationSeq, 'GET').done(function (location) {
        display_update_modal(formTemplate, 'myModal', 'modal', {location: location});
    });
}

function search_location() {
    var locationName = $('input#search_locationName').val();
    var countrySeq = $('select#search_countrySeq').val();
    var data = {'locationName': locationName, 'countrySeq': countrySeq};
    loadPageData('master/locationManagement/searchLocation', data, "POST").done(function (responseObject) {
        $("#loadLocationData").html(responseObject);
        $('.datatable').DataTable();
    });
}