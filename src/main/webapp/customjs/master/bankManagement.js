var formTemplate;
$(function () {
    formTemplate = $(".bankForm").html();
    $('.select').selectpicker();
});

function create_bank() {
    if (form_validate("create")) {
        saveFormData('master/bankManagement/createBank', 'create').done(function (responseObject) {
            if (responseObject.status === true) {
                loadObjectData('master/bankManagement/getBankDetails/', responseObject.object.bankSeq, 'GET').done(function (bank) {
                    transform_form(formTemplate, 'create', 'update', {bank: bank});
                });
            }
        });
    }
}

function update_bank() {
    if (form_validate("update")) {
        saveFormData('master/bankManagement/updateBank', 'update').done(function (responseObject) {
            if (responseObject.status === true) {
                loadObjectData('master/bankManagement/getBankDetails/', responseObject.object.bankSeq, 'GET').done(function (bank) {
                    transform_form(formTemplate, 'update', 'update', {bank: bank});
                });
            }
        });
    }
}

function delete_bank() {
    var formId = $(this)[0].getAttribute('data-id');
    var bankSeq = $('#' + formId + ' :input[name=bankSeq]').val();
    var data = {'bankSeq': bankSeq};
    loadDataPost('master/bankManagement/deleteByBankSeq', data).done(function (responseObject) {
        if (responseObject.status === true) {
            if (formId === 'modal') {
                $('#myModal').modal('toggle');
                new_notification(responseObject.message, 'success');
                $("#tr_" + bankSeq).remove();
                search_bank();
            } else {
                new_form(formTemplate, 'update', 'create');
                new_notification(responseObject.message, 'success');
            }
        } else {
            new_notification(responseObject.message, 'error');
        }
    });
}

function search_bank() {
    var bankName = $('input#search_bankName').val();
    var bankCode = $('input#search_bankCode').val();
    var data = {'bankName': bankName, 'bankCode': bankCode};
    loadPageData('master/bankManagement/searchBank', data, "POST").done(function (responseObject) {
        $("#loadBankList").html(responseObject);
        $('.datatable').DataTable();
    });
}

function update_bank_modal() {
    if (form_validate("modal")) {
        saveFormData('master/bankManagement/updateBank', 'modal').done(function (responseObject) {
            if (responseObject.status === true) {
                load_bank_data_to_modal(responseObject.object.bankSeq);
            }
        });
    }
}

function load_bank_data_to_modal(bankSeq) {
    loadObjectData('master/bankManagement/getBankDetails/', bankSeq, 'GET').done(function (bank) {
        display_update_modal(formTemplate, 'myModal', 'modal', {bank: bank});
    });
}

