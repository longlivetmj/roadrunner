var formTemplate;
$(function () {
    formTemplate = $(".salesPersonForm").html();
    $(".select").selectpicker();
    $(".ajax-select").selectpicker().ajaxSelectPicker();
});

function create_sales_person() {
    if (form_validate("create")) {
        saveFormData('master/salesPersonManagement/createSalesPerson', 'create').done(function (responseObject) {
            if (responseObject.status === true) {
                loadObjectData('master/salesPersonManagement/getSalesPersonDetails/', responseObject.object.salesPersonSeq, 'GET').done(function (salesPerson) {
                    transform_form(formTemplate, 'create', 'update', {salesPerson: salesPerson});
                });
            }
        });
    }
}

function update_sales_person() {
    if (form_validate("update")) {
        saveFormData('master/salesPersonManagement/updateSalesPerson', 'update').done(function (responseObject) {
            if (responseObject.status === true) {
                loadObjectData('master/salesPersonManagement/getSalesPersonDetails/', responseObject.object.salesPersonSeq, 'GET').done(function (salesPerson) {
                    transform_form(formTemplate, 'update', 'update', {salesPerson: salesPerson});
                });
            }
        });
    }
}

function delete_sales_person() {
    var formId = $(this)[0].getAttribute('data-id');
    var salesPersonSeq = $('#' + formId + ' :input[name=salesPersonSeq]').val();
    var data = {'salesPersonSeq': salesPersonSeq};
    loadDataPost('master/salesPersonManagement/deleteBySalesPersonSeq', data).done(function (responseObject) {
        if (responseObject.status === true) {
            if (formId === 'modal') {
                $('#myModal').modal('toggle');
                new_notification(responseObject.message, 'success');
                $("#tr_" + salesPersonSeq).remove();
            } else {
                new_form(formTemplate, 'update', 'create');
                new_notification(responseObject.message, 'success');
            }
        } else {
            new_notification(responseObject.message, 'error');
        }
    });
}

function update_sales_person_popup() {
    if (form_validate("modal")) {
        saveFormData('master/salesPersonManagement/updateSalesPerson', 'modal').done(function (responseObject) {
            if (responseObject.status === true) {
                load__sales_person_popup(responseObject.object.salesPersonSeq);
            }
        });
    }
}

function load_sales_person_table_data() {
    var salesPersonCode = $('input#search_salesPersonCode').val();
    var salesPersonName = $('input#search_salesPersonName').val();
    var stakeholderSeq = $('select#search_stakeholderSeq').val();
    var data = {
        'salesPersonCode': salesPersonCode,
        'salesPersonName': salesPersonName,
        'stakeholderSeq': stakeholderSeq
    };
    loadPageData('master/salesPersonManagement/searchSalesPersonData', data, "POST").done(function (pageData) {
        $("#loadSalesPersonData").html(pageData);
        $('.datatable').DataTable();
    });
}

function load__sales_person_popup(salesPersonSeq) {
    loadObjectData('master/salesPersonManagement/getSalesPersonDetails/', salesPersonSeq, 'GET').done(function (salesPerson) {
        display_update_modal(formTemplate, 'myModal', 'modal', {salesPerson: salesPerson});
    });
}