var formTemplate;
$(function () {
    formTemplate = $(".chargeForm").html();
    $('.select').selectpicker();
});

function validate_multipleCheckBoxes() {
    $("#select_all").change(function () {
        $(".checkbox").prop('checked', $(this).prop("checked"));
    });

    $('.checkbox').change(function () {
        if (false === $(this).prop("checked")) {
            $("#select_all").prop('checked', false);
        }
        if ($('.checkbox:checked').length === $('.checkbox').length) {
            $("#select_all").prop('checked', true);
        }
    });
}

function create_charge() {
    validate_multipleCheckBoxes();
    if (form_validate("create")) {
        saveFormData('master/chargeManagement/createCharge', 'create').done(function (responseObject) {
            if (responseObject.status === true) {
                transform_form(formTemplate, 'create', 'update', {charge: responseObject.object});
                var chargeModes = responseObject.object['chargeModes'];
                $('input:checkbox').removeAttr('checked');
                for (var i = 0; i < chargeModes.length; i++) {
                    if (chargeModes[i].status === 1) {
                        document.getElementById("create_" + chargeModes[i].companyModuleSeq).checked = 'checked';
                    }
                }
            }
        });
    }
}

function update_charge() {
    if (form_validate("update")) {
        saveFormData('master/chargeManagement/updateCharge', 'update').done(function (responseObject) {
            if (responseObject.status === true) {
                BindJsonByForm(formTemplate, {charge: responseObject.object}, 'update');
            }
        });
    }
}


function delete_charge() {
    var formId = $(this)[0].getAttribute('data-id');
    var chargeSeq = $('#' + formId + ' :input[name= chargeSeq]').val();
    var data = {' chargeSeq': chargeSeq};
    loadDataPost('master/chargeManagement/deleteByChargeSeq', data).done(function (responseObject) {
        if (responseObject.status === true) {
            if (formId === 'modal') {
                $('#myModal').modal('toggle');
                new_notification(responseObject.message, 'success');
                $("#tr_" + chargeSeq).remove();
            } else {
                new_notification(responseObject.message, 'success');
                new_form(formTemplate, 'update', 'create');
            }
        } else {
            new_notification(responseObject.message, 'error');
        }
    });
}


function search_charge() {
    var chargeName = $('input#search_chargeName').val();
    var description = $('input#search_description').val();
    var data = {'chargeName': chargeName, 'description': description};
    loadPageData('master/chargeManagement/searchCharge', data, "POST").done(function (responseObject) {
        $("#loadChargeList").html(responseObject);
        $('.datatable').DataTable();
    });
}


function update_charge_modal() {
    if (form_validate("modal")) {
        saveFormData('master/chargeManagement/updateCharge', 'modal').done(function (responseObject) {
            if (responseObject.status === true) {
                load_charge_data_to_modal(responseObject.object.chargeSeq);
            }
        });
    }
}

function load_charge_data_to_modal(chargeSeq) {
    loadObjectData('master/chargeManagement/getChargeDetails/', chargeSeq, 'GET').done(function (charge) {
        display_update_modal(formTemplate, 'myModal', 'modal', {charge: charge});
        loadObjectData('master/chargeManagement/findChargeModeByChargeSeq/', chargeSeq, 'GET').done(function (chargeModes) {
            $('input:checkbox').removeAttr('checked');
            $("#myModal").find(":input").each(function () {
                try {
                    if ($(this).attr("id") !== null) {
                        $(this).attr("id", $(this).attr("id").replace('create', 'modal'));
                    }
                } catch (e) {
                    console.log(e);
                }
            });
            for (var i = 0; i < chargeModes.length; i++) {
                if (chargeModes[i].status === 1) {
                    document.getElementById("modal_" + chargeModes[i].companyModuleSeq).checked = 'checked';
                }
            }
            $("#myModal").modal("show");
        });
    });
}





