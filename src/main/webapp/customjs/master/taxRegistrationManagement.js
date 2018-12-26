var formTemplate;
$(function () {
    formTemplate = $(".taxRegistrationForm").html();
    $(".select").selectpicker();
    $(".ajax-select").selectpicker().ajaxSelectPicker();
});

function create_tax_registration() {
    if (form_validate("create")) {
        saveFormData('master/taxRegistrationManagement/createTaxRegistration', 'create').done(function (responseObject) {
            if (responseObject.status === true) {
                loadObjectData('master/taxRegistrationManagement/findByTaxRegistrationSeq/', responseObject.object.taxRegistrationSeq, 'GET').done(function (taxRegistration) {
                    transform_form(formTemplate, 'create', 'update', {taxRegistration: taxRegistration});
                });
            }
        });
    }
}

function update_tax_registration() {
    if (form_validate("update")) {
        saveFormData('master/taxRegistrationManagement/updateTaxRegistration', 'update').done(function (responseObject) {
            if (responseObject.status === true) {
                loadObjectData('master/taxRegistrationManagement/findByTaxRegistrationSeq/', responseObject.object.taxRegistrationSeq, 'GET').done(function (taxRegistration) {
                    transform_form(formTemplate, 'update', 'update', {taxRegistration: taxRegistration});
                });
            }
        });
    }
}

function delete_tax_registration() {
    var formId = $(this)[0].getAttribute('data-id');
    var taxRegistrationSeq = $('#' + formId + ' :input[name=taxRegistrationSeq]').val();
    var data = {'taxRegistrationSeq': taxRegistrationSeq};
    loadDataPost('master/taxRegistrationManagement/deleteByTaxRegistrationSeq', data).done(function (responseObject) {
        if (responseObject.status === true) {
            if (formId === 'modal') {
                $('#myModal').modal('toggle');
                new_notification(responseObject.message, 'success');
                $("#tr_" + taxRegistrationSeq).remove();
            } else {
                new_form(formTemplate, 'update', 'create');
                new_notification(responseObject.message, 'success');
            }
        } else {
            new_notification(responseObject.message, 'error');
        }
    });
}

function search_tax_registration() {
    var taxName = $('input#search_taxName').val();
    var countrySeq = $('select#search_countrySeq').val();
    var remarks = $('input#search_remarks').val();
    var data = {'taxName': taxName, 'countrySeq': countrySeq, 'remarks': remarks};
    loadPageData('master/taxRegistrationManagement/searchTaxRegistration', data, "POST").done(function (responseObject) {
        $("#loadTaxRegistrationData").html(responseObject);
        $('.datatable').DataTable();
    });
}

function update_tax_registration_modal() {
    if (form_validate("modal")) {
        saveFormData('master/taxRegistrationManagement/updateTaxRegistration', 'modal').done(function (responseObject) {
            if (responseObject.status === true) {
                load_tax_registration_data_to_modal(responseObject.object.taxRegistrationSeq);
            }
        });
    }
}

function load_tax_registration_data_to_modal(taxRegistrationSeq) {
    loadObjectData('master/taxRegistrationManagement/findByTaxRegistrationSeq/', taxRegistrationSeq, 'GET').done(function (taxRegistration) {
        display_update_modal(formTemplate, 'myModal', 'modal', {taxRegistration: taxRegistration});
    });
}
