
var formTemplate;

$(function () {
    formTemplate = $(".currencyForm").html();
    $(".select").selectpicker();
});

function load_currency_data_to_modal(currencySeq) {
    loadObjectData('master/currencyManagement/getCurrencyDetails/', currencySeq, 'GET').done(function (currency) {
        display_update_modal(formTemplate, 'myModal', 'modal', {currency: currency});
    });
}

function create_currency() {
    if (form_validate("create")) {
        saveFormData('/master/currencyManagement/createCurrency', 'create').done(function (responseObject) {
            if (responseObject.status === true) {
                transform_form(formTemplate, 'create', 'update', {currency: responseObject.object});
            }
        });
    }
}

function update_currency() {
    if (form_validate("update")) {
        saveFormData('master/currencyManagement/updateCurrency', 'update').done(function (responseObject) {
            if (responseObject.status === true) {
                BindJsonByForm(formTemplate, {currency: responseObject.object}, 'update');
            }
        });
    }
}

function update_currency_modal_data() {
    if (form_validate("modal")) {
        saveFormData('master/currencyManagement/updateCurrency', 'modal').done(function (responseObject) {
            if (responseObject.status === true) {
                load_currency_data_to_modal(responseObject.object.currencySeq);
            }
        });
    }
}

function search_currency() {
    var currencyCode = $('input#search_currencyCode').val();
    var currencyName = $('input#search_currencyName').val();
    var data = {
        'currencyCode': currencyCode,
        'currencyName': currencyName
    };
    if (form_validate("search")) {
        loadPageData('master/currencyManagement/searchCurrency', data, "POST").done(function (responseObject) {
            $("#loadCurrencySearchResult").html(responseObject);
            $('.datatable').DataTable();
        });
    }
}

function delete_currency() {
    var formId = $(this)[0].getAttribute('data-id');
    var currencySeq = $('#' + formId + ' :input[name=currencySeq]').val();
    var data = {'currencySeq': currencySeq};
    loadDataPost('master/currencyManagement/deleteByCurrencySeq', data).done(function (responseObject) {
        if (responseObject.status === true) {
            if (formId === 'modal') {
                $('#myModal').modal('toggle');
                new_notification(responseObject.message, 'success');
                $("#tr_" + currencySeq).remove();
            } else {
                new_notification(responseObject.message, 'success');
                new_form(formTemplate, 'update', 'create');
            }
        } else {
            new_notification(responseObject.message, 'error');
        }
    });
}