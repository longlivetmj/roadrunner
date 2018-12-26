var formTemplate;
$(function () {
    formTemplate = $(".employeeForm").html();
    $(".select").selectpicker();
    $(".ajax-select").selectpicker().ajaxSelectPicker();
    $(".datepicker").datepicker({dateFormat: 'yy-mm-dd'});
});

function create_employee() {
    if (form_validate("create")) {
        saveFormData('master/employeeManagement/addEmployee', 'create').done(function (responseObject) {
            if (responseObject.status === true) {
                loadObjectData('master/employeeManagement/findByEmployeeSeq/', responseObject.object.employeeSeq, 'GET').done(function (employee) {
                    transform_form(formTemplate, 'create', 'update', {employee: employee});
                });
            }
        });
    }
}

function update_employee() {
    if (form_validate("update")) {
        saveFormData('master/employeeManagement/updateEmployee', 'update').done(function (responseObject) {
            if (responseObject.status === true) {
                loadObjectData('master/employeeManagement/findByEmployeeSeq/', responseObject.object.employeeSeq, 'GET').done(function (employee) {
                    transform_form(formTemplate, 'update', 'update', {employee: employee});
                });
            }
        });
    }
}

function update_employee_modal() {
    if (form_validate("modal")) {
        saveFormData('master/employeeManagement/updateEmployee', 'modal').done(function (responseObject) {
            if (responseObject.status === true) {
                load_employee_data_to_modal(responseObject.object.employeeSeq);
            }
        });
    }
}

function delete_employee() {
    var formId = $(this)[0].getAttribute('data-id');
    var employeeSeq = $('#' + formId + ' :input[name=employeeSeq]').val();
    var data = {'employeeSeq': employeeSeq};
    loadDataPost('master/employeeManagement/deleteByEmployeeSeq', data).done(function (responseObject) {
        if (responseObject.status === true) {
            if (formId === 'modal') {
                $('#myModal').modal('toggle');
                new_notification(responseObject.message, 'success');
                $("#tr_" + employeeSeq).remove();
            } else {
                new_form(formTemplate, 'update', 'create');
                new_notification(responseObject.message, 'success');
            }
        } else {
            new_notification(responseObject.message, 'error');
        }
    });
}

function load_employee_table_data() {
    searchFormData('master/employeeManagement/searchEmployeeData', 'search').done(function (pageData) {
        $("#loadEmployeeListData").html(pageData);
        $('.datatable').DataTable();
    });
}

function load_employee_data_to_modal(employeeSeq) {
    loadObjectData('master/employeeManagement/findByEmployeeSeq/', employeeSeq, 'GET').done(function (employee) {
        display_update_modal(formTemplate, 'myModal', 'modal', {employee: employee});
    });
}
