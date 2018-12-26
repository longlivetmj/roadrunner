var formTemplate;
$(function () {
    formTemplate = $(".departmentForm").html();
    $(".select").selectpicker();
});

function create_department() {
    if (form_validate("create")) {
        saveFormData('config/departmentManagement/createDepartment', 'create').done(function (responseObject) {
            if (responseObject.status === true) {
                transform_form(formTemplate, 'create', 'update', {department: responseObject.object});
            }
        });
    }
}

function update_department() {
    if (form_validate("update")) {
        saveFormData('config/departmentManagement/updateDepartment', 'update').done(function (responseObject) {
            if (responseObject.status === true) {
                transform_form(formTemplate, 'update', 'update', {department: responseObject.object});
            }
        });
    }
}

function search_department() {
    var departmentCode = $('input#search_departmentCode').val();
    var departmentName = $('input#search_departmentName').val();
    var prifix = $('input#search_prifix').val();
    var data = {'departmentCode': departmentCode, 'departmentName': departmentName, 'prifix': prifix};
    loadPageData('config/departmentManagement/searchDepartmentData', data, "POST").done(function (department) {
        $("#loadDepartmentData").html(department);
        $('.datatable').DataTable();
    });
}

function update_department_modal() {
    if (form_validate("modal")) {
        saveFormData('config/departmentManagement/updateDepartment', 'modal').done(function (responseObject) {
            load_department_data_to_modal(responseObject.object.departmentSeq);
        });
    }
}

function delete_department() {
    var formId = $(this)[0].getAttribute('data-id');
    var departmentSeq = $('#' + formId + ' :input[name=departmentSeq]').val();
    var data = {'departmentSeq': departmentSeq};
    loadDataPost('config/departmentManagement/deleteByDepartmentSeq', data).done(function (responseObject) {
        if (responseObject.status === true) {
            if (formId === 'modal') {
                $('#myModal').modal('toggle');
                new_notification(responseObject.message, 'success');
                $("#tr_" + departmentSeq).remove();
            } else {
                new_form(formTemplate, 'update', 'create');
                new_notification(responseObject.message, 'success');
            }
        } else {
            new_notification(responseObject.message, 'error');
        }
    });
}

function load_department_data_to_modal(departmentSeq) {
    loadObjectData('config/departmentManagement/findByDepartmentSeq/', departmentSeq, 'GET').done(function (department) {
        display_update_modal(formTemplate, 'myModal', 'modal', {department: department});
    });
}

function load_departments_for_module() {
    var moduleSeq = $('select#charge_moduleSeq').val();
    loadObjectData('config/departmentManagement/findDepartmentsByModule/', moduleSeq, "GET").done(function (departments) {
        if (departments !== null) {
            populate_dropdown('charge_departmentSeq', departments, 'departmentSeq', 'departmentName');
            load_department_charges_data();
        }
    });
}

function load_department_charges_data() {
    var moduleSeq = $('select#charge_moduleSeq').val();
    var departmentSeq = $('select#charge_departmentSeq').val();
    var data = {"moduleSeq": moduleSeq, "departmentSeq": departmentSeq};
    loadPageData("/config/departmentManagement/getDepartmentChargesData", data, "POST").done(function (departmentChargesData) {
        $("#loadDepartmentChargesData").html(departmentChargesData);
        $(".select").selectpicker();
    });
}

function submit_department_charges() {
    saveFormData('/config/departmentManagement/saveDepartmentCharges', 'department_charges').done(function (departmentCharges) {

    });
}
