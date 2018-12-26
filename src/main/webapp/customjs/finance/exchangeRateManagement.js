
var formTemplate;
$(function () {
    formTemplate = $(".exchangeRateForm").html();
    $(".select").selectpicker();
    $(".ajax-select").selectpicker().ajaxSelectPicker();
    $(".datepicker").datepicker({dateFormat: 'yy-mm-dd'});
    var exchangeRateSeq = getUrlParameter('exchangeRateSeq');
    if (exchangeRateSeq !== '' && exchangeRateSeq !== undefined) {
        load_exchange_rate_data_to_modal(exchangeRateSeq)
    }
});

function create_exchange_rate() {
    if (form_validate("create")) {
        saveFormData('finance/exchangeRateManagement/createExchangeRate', 'create').done(function (responseObject) {
            if (responseObject.status === true) {
                loadObjectData('finance/exchangeRateManagement/findByExchangeRateSeq/', responseObject.object.exchangeRateSeq, 'GET').done(function (exchangeRate) {
                    transform_form(formTemplate, 'create', 'update', {exchangeRate: exchangeRate});
                });
            }
        });
    }
}

function update_exchange_rate() {
    if (form_validate("update")) {
        saveFormData('finance/exchangeRateManagement/updateExchangeRate', 'update').done(function (responseObject) {
            if (responseObject.status === true) {
                BindJsonByForm({exchangeRate: responseObject.object}, 'update');
            }
        });
    }
}

function update_exchange_rate_model() {
    if (form_validate("modal")) {
        saveFormData('finance/exchangeRateManagement/updateExchangeRate', 'modal').done(function (responseObject) {
            if (responseObject.status === true) {
                BindJsonByForm({exchangeRate: responseObject.object}, 'modal');
            }
        });
    }
}

function delete_exchange_rate() {
    var formId = $(this)[0].getAttribute('data-id');
    var exchangeRateSeq = $('#' + formId + ' :input[name=exchangeRateSeq]').val();
    var data = {'exchangeRateSeq': exchangeRateSeq};
    loadDataPost('finance/exchangeRateManagement/deleteExchangeRate', data).done(function (responseObject) {
        if (responseObject.status === true) {
            if (formId === 'modal') {
                $('#myModal').modal('toggle');
                new_notification(responseObject.message, 'success');
                $("#tr_" + exchangeRateSeq).remove();
            } else {
                new_form(formTemplate, 'update', 'create');
                new_notification(responseObject.message, 'success');
            }
        } else {
            new_notification(responseObject.message, 'error');
        }
    });

}

function search_exchange_rate() {
    searchFormData('finance/exchangeRateManagement/searchExchangeRateData', 'searchForm').done(function (exchangeRate) {
        $("#loadExchangeRateData").html(exchangeRate);
        $('.datatable').DataTable();
    });
}

function load_exchange_rate_data_to_modal(exchangeRateSeq) {
    loadObjectData('finance/exchangeRateManagement/findByExchangeRateSeq/', exchangeRateSeq, 'GET').done(function (exchangeRate) {
        display_update_modal(formTemplate, 'myModal', 'modal', {exchangeRate: exchangeRate});
    });
}
