var formTemplate;
$(function () {
    formTemplate = $(".countryForm").html();
    $('.select').selectpicker();
    $(".ajax-select").selectpicker().ajaxSelectPicker();
});

function create_country() {
    if (form_validate("create")) {
        saveFormData('master/countryManagement/createCountry', 'create').done(function (responseObject) {
            if (responseObject.status === true) {
                loadObjectData('master/countryManagement/getCountryDetails/', responseObject.object.countrySeq, 'GET').done(function (country) {
                    transform_form(formTemplate, 'create', 'update', {country: country});
                });
            }
        });
    }
}

function update_country() {
    if (form_validate("update")) {
        saveFormData('master/countryManagement/updateCountry', 'update').done(function (responseObject) {
            if (responseObject.status === true) {
                loadObjectData('master/countryManagement/getCountryDetails/', responseObject.object.countrySeq, 'GET').done(function (country) {
                    transform_form(formTemplate, 'update', 'update', {country: country});
                });
            }
        });
    }
}

function delete_country() {
    var formId = $(this)[0].getAttribute('data-id');
    var countrySeq = $('#' + formId + ' :input[name=countrySeq]').val();
    var data = {'countrySeq': countrySeq};
    loadDataPost('master/countryManagement/deleteByCountrySeq', data).done(function (responseObject) {
        if (responseObject.status === true) {
            if (formId === 'modal') {
                $('#myModal').modal('toggle');
                new_notification(responseObject.message, 'success');
                $("#tr_" + countrySeq).remove();
            } else {
                new_notification(responseObject.message, 'success');
                new_form(formTemplate, 'update', 'create');
            }
        } else {
            new_notification(responseObject.message, 'error');
        }
    });
}

function search_country() {
    var countryName = $('input#search_countryName').val();
    var countryCode = $('input#search_countryCode').val();
    var data = {'countryName': countryName, 'countryCode': countryCode};
    loadPageData('master/countryManagement/searchCountry', data, "POST").done(function (responseObject) {
        $("#loadCountryList").html(responseObject);
        $('.datatable').DataTable();
    });
}

function update_country_modal() {
    if (form_validate("modal")) {
        saveFormData('master/countryManagement/updateCountry', 'modal').done(function (responseObject) {
            if (responseObject.status === true) {
                load_country_data_to_modal(responseObject.object.countrySeq);
            }
        });
    }
}

function load_country_data_to_modal(countrySeq) {
    loadObjectData('master/countryManagement/getCountryDetails/', countrySeq, 'GET').done(function (country) {
        display_update_modal(formTemplate, 'myModal', 'modal', {country: country});
    });
}
