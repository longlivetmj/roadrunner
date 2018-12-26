var formTemplate;
$(function () {
    formTemplate = $(".itemForm").html();
    $(".select").selectpicker();
    $(".ajax-select").selectpicker().ajaxSelectPicker();
});

function create_item() {
    if (form_validate("create")) {
        saveFormData('master/itemManagement/createItem', 'create').done(function (responseObject) {
            if (responseObject.status === true) {
                loadObjectData('master/itemManagement/findByItemSeq/', responseObject.object.itemSeq, 'GET').done(function (item) {
                    transform_form(formTemplate, 'create', 'update', {item: item});
                });
            }
        });
    }
}

function update_item() {
    if (form_validate("update")) {
        saveFormData('master/itemManagement/updateItem', 'update').done(function (responseObject) {
            if (responseObject.status === true) {
                loadObjectData('master/itemManagement/findByItemSeq/', responseObject.object.itemSeq, 'GET').done(function (item) {
                    transform_form(formTemplate, 'update', 'update', {item: item});
                });
            }
        });
    }
}

function delete_item() {
    var formId = $(this)[0].getAttribute('data-id');
    var itemSeq = $('#' + formId + ' :input[name=itemSeq]').val();
    var data = {'itemSeq': itemSeq};
    loadDataPost('master/itemManagement/deleteByItemSeq', data).done(function (responseObject) {
        if (responseObject.status === true) {
            if (formId === 'modal') {
                $('#myModal').modal('toggle');
                new_notification(responseObject.message, 'success');
                $("#tr_" + itemSeq).remove();
                search_item();
            } else {
                new_form(formTemplate, 'update', 'create');
                new_notification(responseObject.message, 'success');
            }
        } else {
            new_notification(responseObject.message, 'error');
        }
    });
}

function search_item() {
    var itemName = $('input#search_itemName').val();
    var itemCode = $('input#search_itemCode').val();
    var usedFor = $('select#search_usedFor').val();
    var data = {'itemName': itemName, 'itemCode': itemCode, 'usedFor': usedFor};
    loadPageData('master/itemManagement/searchItem', data, "POST").done(function (responseObject) {
        $("#loadItemList").html(responseObject);
        $('.datatable').DataTable();
    });
}

function update_item_modal() {
    if (form_validate("modal")) {
        saveFormData('master/itemManagement/updateItem', 'modal').done(function (responseObject) {
            if (responseObject.status === true) {
                load_item_data_to_modal(responseObject.object.itemSeq);
            }
        });
    }
}

function load_item_data_to_modal(itemSeq) {
    loadObjectData('master/itemManagement/findByItemSeq/', itemSeq, 'GET').done(function (item) {
        display_update_modal(formTemplate, 'myModal', 'modal', {item: item});
    });
}


