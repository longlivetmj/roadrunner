var formTemplate;
$(function () {
    formTemplate = $(".leaveForm").html();
    $(".select").selectpicker();
    $(".ajax-select").selectpicker().ajaxSelectPicker();
    $(".datepicker").datepicker({dateFormat: 'yy-mm-dd'});
});

function get_employee_list() {
    var designationSeq = $("#employeeDesignationSeq").val();
    loadObjectData('fleet/leaveManagement/findByDesignationSeq/', designationSeq, "GET").done(function (employeeList) {
        populate_dropdown('employeeSeq', employeeList, 'employeeSeq', 'employeeName');
    });
}

function create_leave() {
    if (form_validate("create")) {
        saveFormData('fleet/leaveManagement/saveLeave', 'create').done(function (responseObject) {
            if (responseObject.status === true) {
                loadObjectData('fleet/leaveManagement/findByLeaveSeq/', responseObject.object.leaveSeq, 'GET').done(function (leave) {
                    transform_form(formTemplate, 'create', 'update', {leave: leave});
                });
            }
        });
    }
}

function update_leave() {
    if (form_validate("update")) {
        saveFormData('fleet/leaveManagement/updateLeave', 'update').done(function (responseObject) {
            if (responseObject.status === true) {
                loadObjectData('fleet/leaveManagement/findByLeaveSeq/', responseObject.object.leaveSeq, 'GET').done(function (leave) {
                    transform_form(formTemplate, 'update', 'update', {leave: leave});
                });
            }
        });
    }
}

function update_leave_data() {
    if (form_validate("modal")) {
        saveFormData('fleet/leaveManagement/updateLeave', 'modal').done(function (responseObject) {
            if (responseObject.status === true) {
                load_leave_data_to_modal(responseObject.object.leaveSeq);
            }
        });
    }
}

function delete_leave() {
    var formId = $(this)[0].getAttribute('data-id');
    var leaveSeq = $('#' + formId + ' :input[name=leaveSeq]').val();
    var data = {'leaveSeq': leaveSeq};
    loadDataPost('fleet/leaveManagement/deleteByLeaveSeq', data).done(function (responseObject) {
        if (responseObject.status === true) {
            if (formId === 'modal') {
                $('#myModal').modal('toggle');
                new_notification(responseObject.message, 'success');
                $("#tr_" + leaveSeq).remove();
            } else {
                new_form(formTemplate, 'update', 'create');
                new_notification(responseObject.message, 'success');
            }
        } else {
            new_notification(responseObject.message, 'error');
        }
    });
}

function search_leave_data() {
    searchFormData('fleet/leaveManagement/searchLeaveData', 'search').done(function (pageData) {
        $("#loadLeaveData").html(pageData);
        $('.datatable').DataTable();
    });
}

function load_leave_data_to_modal(leaveSeq) {
    loadObjectData('fleet/leaveManagement/findByLeaveSeq/', leaveSeq, 'GET').done(function (leave) {
        display_update_modal(formTemplate, 'myModal', 'modal', {leave: leave});
    });
}