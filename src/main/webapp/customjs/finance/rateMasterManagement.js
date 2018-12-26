$(function () {
    $(".select").selectpicker();
    $(".ajax-select").selectpicker().ajaxSelectPicker();
});

function create_rate_master_information(formId) {
    if (form_validate(formId)) {
        saveFormData('finance/rateMasterManagement/saveRateMaster', formId).done(function (responseObject) {
            if (responseObject.status === true) {
                setTimeout(location.reload(), 3000);
            }
        });
    }
}

function update_rate_master(formId) {
    if (form_validate(formId)) {
        saveFormData('finance/rateMasterManagement/updateRateMaster', formId).done(function (responseObject) {
            if (responseObject.status === true) {
                setTimeout(location.reload(), 3000);
            }
        });
    }
}
