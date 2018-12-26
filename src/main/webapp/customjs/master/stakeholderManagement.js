var formTemplate;

$(function () {
    formTemplate = $(".stakeholderManagementForm").html();
    $(".select").selectpicker();
    $(".datepicker").datepicker({dateFormat: 'yy-mm-dd'});
    $(".ajax-select").selectpicker().ajaxSelectPicker();
});

function create_stakeholder() {
    if (validate_stakeholder_type("create") && form_validate("create")) {
        saveFormData('master/stakeholderManagement/createStakeholder', 'create', 'reset').done(function (responseObject) {
            if (responseObject.status === true) {
                loadObjectData('master/stakeholderManagement/findByStakeholderSeq/', responseObject.object.stakeholderSeq, 'GET').done(function (stakeholder) {
                    transform_form(formTemplate, 'create', 'update', {stakeholder: stakeholder});
                    set_stakeholder_type(stakeholder.stakeholderTypeMappings, 'update');
                });
            }
        });
    }
}

function update_stakeholder() {
    if (form_validate("update")) {
        saveFormData('master/stakeholderManagement/updateStakeholder', 'update').done(function (responseObject) {
            if (responseObject.status === true) {
                loadObjectData('master/stakeholderManagement/findByStakeholderSeq/', responseObject.object.stakeholderSeq, 'GET').done(function (stakeholder) {
                    transform_form(formTemplate, 'update', 'update', {stakeholder: stakeholder});
                    set_stakeholder_type(stakeholder.stakeholderTypeMappings, 'update');
                });
            }
        });
    }
}

function search_stakeholder() {
    var stakeholderTypeSeq = $('select#search_stakeholderTypeSeq').val();
    var stakeholderSeq = $('input#search_stakeholderSeq').val();
    var stakeholderName = $('input#search_stakeholderName').val();
    var countrySeq = $('select#search_countrySeq').val();
    var status = $('select#search_status').val();
    var stakeholderGroupSeq = $('select#search_stakeholderGroupSeq').val();
    var startDate = $('input#search_startDate').val();
    var endDate = $('input#search_endDate').val();
    var data = {
        'stakeholderSeq': stakeholderSeq,
        'stakeholderTypeSeq': stakeholderTypeSeq,
        'stakeholderName': stakeholderName,
        'countrySeq': countrySeq,
        'status': status,
        'stakeholderGroupSeq': stakeholderGroupSeq,
        'startDate': startDate,
        'endDate': endDate
    };
    loadPageData('master/stakeholderManagement/searchStakeholder', data, "POST").done(function (responseObject) {
        $("#loadStakeholderData").html(responseObject);
        $('.datatable').DataTable();
    });
}

function update_stakeholder_modal() {
    if (form_validate("modal")) {
        saveFormData('master/stakeholderManagement/updateStakeholder', 'modal').done(function (responseObject) {
            if (responseObject.status === true) {
                load_stakeholder_data_to_modal(responseObject.object.stakeholderSeq, 'modal');
            }
        });
    }
}

function load_stakeholder_data_to_modal(stakeholderSeq) {
    loadObjectData('master/stakeholderManagement/findByStakeholderSeq/', stakeholderSeq, 'GET').done(function (stakeholder) {
        display_update_modal(formTemplate, 'myModal', 'modal', {stakeholder: stakeholder});
        set_stakeholder_type(stakeholder.stakeholderTypeMappings, 'modal');
        $('#companyProfileSeq').val(stakeholder.companyProfileSeq);
        if (stakeholder.status === 2) {
            document.getElementById('updateStakeHolder').style.visibility = 'hidden';
        } else {
            document.getElementById('updateStakeHolder').style.visibility = 'visible';
        }
    });
}

function delete_stakeholder() {
    var stakeholderSeq = $('#stakeholderSeq').val();
    var data = {'stakeholderSeq': stakeholderSeq};
    loadDataPost('master/stakeholderManagement/deleteByStakeholderSeq', data).done(function (responseObject) {
        if (responseObject.status === true) {
            new_notification(responseObject.message, 'success');
            new_form(formTemplate, 'update', 'create')
        } else {
            new_notification(responseObject.message, 'error');
        }
    });
}

function delete_stakeholder_popup() {
    var formId = $(this)[0].getAttribute('data-id');
    var stakeholderSeq = $('#' + formId + ' :input[name=stakeholderSeq]').val();
    var data = {'stakeholderSeq': stakeholderSeq};
    loadDataPost('master/stakeholderManagement/deleteByStakeholderSeq', data).done(function (responseObject) {
        if (responseObject.status === true) {
            $('#myModal').modal('toggle');
            new_notification(responseObject.message, 'success');
            $("#tr_" + stakeholderSeq).remove();
        } else {
            new_notification(responseObject.message, 'error');
        }
    });
}

function validateFloatKeyPress(el) {
    var v = parseFloat(el.value);
    el.value = (isNaN(v)) ? '' : v.toFixed(2);
}

function get_selected_stakeholder_type_list(param, formId) {
    loadObjectData('master/stakeholderManagement/findStakeholderTypeListByStakeholderSeq/', param, 'GET').done(function (responseObject) {
        $('input:checkbox').removeAttr('checked');
        for (var i = 0; i < responseObject.length; i++) {
            var stakeholderTypeValue = $("#" + formId + " .CB" + object[i].stakeholderTypeSeq).attr("value");
            if (responseObject[i].stakeholderTypeSeq === parseIntstakeholderTypeValue) {
                $("#" + formId + " .CB" + responseObject[i].stakeholderTypeSeq).attr("id", "updateStakeholderTypeSeq" + responseObject[i].stakeholderTypeSeq);
                document.getElementById("updateStakeholderTypeSeq" + responseObject[i].stakeholderTypeSeq).checked = true;
            }
        }
    });
}

function set_stakeholder_type(object, formId) {
    $('input:checkbox').removeAttr('checked');
    for (var i = 0; i < object.length; i++) {
        var stakeholderTypeValue = $("#" + formId + " .CB" + object[i].stakeholderTypeSeq).attr("value");
        if (object[i].stakeholderTypeSeq === parseInt(stakeholderTypeValue)) {
            $("#" + formId + " .CB" + object[i].stakeholderTypeSeq).attr("id", "updateStakeholderTypeSeq" + object[i].stakeholderTypeSeq);
            document.getElementById("updateStakeholderTypeSeq" + object[i].stakeholderTypeSeq).checked = true;
        }
    }
}

function validate_stakeholder_type(formId) {
    var counter = 0;
    $("#" + formId).find('input[type=checkbox]').each(function () {
        if ($(this).is(':checked') === true) {
            counter++;
        }
    });

    if (counter === 0) {
        alert("Please select at least one");
        return false;
    } else {
        return true;
    }
}

function get_status_value() {
    var status = $('#status').val();
    if (status === 2) {
        document.getElementById('updateStakeHolder').style.visibility = 'hidden';
    } else {
        document.getElementById('updateStakeHolder').style.visibility = 'visible';
    }
}

function view_rate_master() {
    var stakeholderSeq = $('#modal :input[name=stakeholderSeq]').val();
    if (stakeholderSeq !== '') {
        window.open('#/finance/rateMasterManagement?stakeholderSeq=' + stakeholderSeq, '_blank');
    }
}
function view_rate_master_grid(stakeholderSeq) {
    if (stakeholderSeq !== '') {
        window.open('#/finance/rateMasterManagement?stakeholderSeq=' + stakeholderSeq, '_blank');
    }
}


