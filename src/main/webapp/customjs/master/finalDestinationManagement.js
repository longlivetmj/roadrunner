var formTemplate;
$(function () {
    formTemplate = $(".finalDestinationForm").html();
    $(".select").selectpicker();
    $(".ajax-select").selectpicker().ajaxSelectPicker();
});

function create_final_destination() {
    if (form_validate("create")) {
        saveFormData('master/finalDestinationManagement/addDestination', 'create').done(function (responseObject) {
            if (responseObject.status === true) {
                loadObjectData('master/finalDestinationManagement/findByFinalDestinationSeq/', responseObject.object.finalDestinationSeq, 'GET').done(function (finalDestination) {
                    transform_form(formTemplate, 'create', 'update', {finalDestination: finalDestination});
                });
            }
        });
    }
}

function update_final_destination() {
    if (form_validate("update")) {
        saveFormData('master/finalDestinationManagement/updateDestination', 'update').done(function (responseObject) {
            if (responseObject.status === true) {
                loadObjectData('master/finalDestinationManagement/findByFinalDestinationSeq/', responseObject.object.finalDestinationSeq, 'GET').done(function (finalDestination) {
                    transform_form(formTemplate, 'update', 'update', {finalDestination: finalDestination});
                });
            }
        });
    }
}

function update_final_destination_modal() {
    if (form_validate("modal")) {
        saveFormData('master/finalDestinationManagement/updateDestination', 'modal').done(function (responseObject) {
            if (responseObject.status === true) {
                load_final_destination_data_to_modal(responseObject.object.finalDestinationSeq);
            }
        });
    }
}

function delete_final_destination() {
    var formId = $(this)[0].getAttribute('data-id');
    var finalDestinationSeq = $('#' + formId + ' :input[name=finalDestinationSeq]').val();
    var data = {'finalDestinationSeq': finalDestinationSeq};
    loadDataPost('master/finalDestinationManagement/deleteByFinalDestinationSeq', data).done(function (responseObject) {
        if (responseObject.status === true) {
            if (formId === 'modal') {
                $('#myModal').modal('toggle');
                new_notification(responseObject.message, 'success');
                $("#tr_" + finalDestinationSeq).remove();
            } else {
                new_form(formTemplate, 'update', 'create');
                new_notification(responseObject.message, 'success');
            }
        } else {
            new_notification(responseObject.message, 'error');
        }
    });
}

function load_final_destination_table_data() {
    var finalDestinationCode = $('input#search_finalDestinationCode').val();
    var countrySeq = $('select#search_countrySeq').val();
    var city = $('input#search_city').val();
    var state = $('input#search_state').val();
    var zip = $('input#search_zip').val();
    var data = {
        'finalDestinationCode': finalDestinationCode,
        'countrySeq': countrySeq,
        'city': city,
        'state': state,
        'zip': zip
    };
    loadPageData('master/finalDestinationManagement/searchDestinationData', data, "POST").done(function (pageData) {
        $("#loadDestinationListData").html(pageData);
        $('.datatable').DataTable();
    });
}

function load_final_destination_data_to_modal(finalDestinationSeq) {
    loadObjectData('master/finalDestinationManagement/findByFinalDestinationSeq/', finalDestinationSeq, 'GET').done(function (finalDestination) {
        display_update_modal(formTemplate, 'myModal', 'modal', {finalDestination: finalDestination});
    });

}
