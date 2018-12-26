var formTemplate;
$(function () {
    formTemplate = $(".unitForm").html();
    $(".select").selectpicker();
});

function create_unit() {
    if (form_validate("create")) {
        saveFormData('master/unitManagement/createUnit', 'create').done(function (responseObject) {
            if (responseObject.status === true) {
                loadObjectData('master/unitManagement/findByUnitSeq/', responseObject.object.unitSeq, 'GET').done(function (unit) {
                    transform_form(formTemplate, 'create', 'update', {unit: unit});
                });
            }
        });
    }
}

function update_unit() {
    if (form_validate("update")) {
        saveFormData('master/unitManagement/updateUnit', 'update').done(function (responseObject) {
            if (responseObject.status === true) {
                loadObjectData('master/unitManagement/findByUnitSeq/', responseObject.object.unitSeq, 'GET').done(function (unit) {
                    transform_form(formTemplate, 'update', 'update', {unit: unit});
                });
            }
        });
    }
}

function delete_unit() {
    var formId = $(this)[0].getAttribute('data-id');
    var unitSeq = $('#' + formId + ' :input[name=unitSeq]').val();
    var data = {'unitSeq': unitSeq};
    loadDataPost('master/unitManagement/deleteByUnitSeq', data).done(function (responseObject) {
        if (responseObject.status === true) {
            if (formId === 'modal') {
                $('#myModal').modal('toggle');
                new_notification(responseObject.message, 'success');
                $("#tr_" + unitSeq).remove();
                search_unit();
            } else {
                new_form(formTemplate, 'update', 'create');
                new_notification(responseObject.message, 'success');
            }
        } else {
            new_notification(responseObject.message, 'error');
        }
    });
}

function search_unit() {
    var unitName = $('input#search_unitName').val();
    var unitCode = $('input#search_unitCode').val();
    var usedFor = $('select#search_usedFor').val();
    var data = {'unitName': unitName, 'unitCode': unitCode, 'usedFor': usedFor};
    loadPageData('master/unitManagement/searchUnit', data, "POST").done(function (responseObject) {
        $("#loadUnitList").html(responseObject);
        $('.datatable').DataTable();
    });
}

function update_unit_modal() {
    if (form_validate("modal")) {
        saveFormData('master/unitManagement/updateUnit', 'modal').done(function (responseObject) {
            if (responseObject.status === true) {
                load_unit_data_to_modal(responseObject.object.unitSeq);
            }
        });
    }
}

function load_unit_data_to_modal(unitSeq) {
    loadObjectData('master/unitManagement/findByUnitSeq/', unitSeq, 'GET').done(function (unit) {
        display_update_modal(formTemplate, 'myModal', 'modal', {unit: unit});
    });
}

