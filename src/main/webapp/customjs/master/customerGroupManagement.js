var formTemplate;
$(function () {
    formTemplate = $(".customerGroupForm").html();
    $(".select").selectpicker();
});

function create_customer_group() {
    if (form_validate("create")) {
        saveFormData('master/customerGroupManagement/addCustomerGroup', 'create').done(function (responseObject) {
            if (responseObject.status === true) {
                loadObjectData('master/customerGroupManagement/getCustomerGroupDetails/', responseObject.object.customerGroupSeq, 'GET').done(function (customerGroup) {
                    transform_form(formTemplate, 'create', 'update', {customerGroup: customerGroup});
                });
            }
        });
    }
}

function update_customer_group() {
    if (form_validate("update")) {
        saveFormData('master/customerGroupManagement/updateCustomerGroup', 'update').done(function (responseObject) {
            if (responseObject.status === true) {
                loadObjectData('master/customerGroupManagement/getCustomerGroupDetails/', responseObject.object.customerGroupSeq, 'GET').done(function (customerGroup) {
                    transform_form(formTemplate, 'update', 'update', {customerGroup: customerGroup});
                });
            }
        });
    }
}

function update_customer_group_popup() {
    if (form_validate("modal")) {
        saveFormData('master/customerGroupManagement/updateCustomerGroup', 'modal').done(function (responseObject) {
            if (responseObject.status === true) {
                load__customer_group_popup(responseObject.object.customerGroupSeq);
            }
        });
    }
}

function load_customer_group_table_data() {
    var customerGroupCode = $('input#search_customerGroupCode').val();
    var customerGroupName = $('input#search_customerGroupName').val();
    var data = {'customerGroupCode': customerGroupCode, 'customerGroupName': customerGroupName};
    loadPageData('master/customerGroupManagement/searchCustomerGroupData', data, "POST").done(function (pageData) {
        $("#loadCustomerGroupListData").html(pageData);
        $('.datatable').DataTable();
    });
}

function delete_customer_group() {
    var formId = $(this)[0].getAttribute('data-id');
    var customerGroupSeq = $('#' + formId + ' :input[name=customerGroupSeq]').val();
    var data = {'customerGroupSeq': customerGroupSeq};
    loadDataPost('master/customerGroupManagement/deleteByCustomerGroupSeq', data).done(function (responseObject) {
        if (responseObject.status === true) {
            if (formId === 'modal') {
                $('#myModal').modal('toggle');
                new_notification(responseObject.message, 'success');
                $("#tr_" + customerGroupSeq).remove();
            } else {
                new_form(formTemplate, 'update', 'create');
                new_notification(responseObject.message, 'success');
            }
        } else {
            new_notification(responseObject.message, 'error');
        }
    });
}

function load__customer_group_popup(customerGroupSeq) {
    loadObjectData('master/customerGroupManagement/getCustomerGroupDetails/', customerGroupSeq, 'GET').done(function (customerGroup) {
        display_update_modal(formTemplate, 'myModal', 'modal', {customerGroup: customerGroup});
    });
}