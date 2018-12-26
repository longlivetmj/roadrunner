$(function () {
    $("#create_userPhoto").fileinput({
        showUpload: false,
        showCaption: false,
        browseClass: "btn btn-danger",
        fileType: "any"
    });

    $("#update_userPhoto").fileinput({
        showUpload: false,
        showCaption: false,
        browseClass: "btn btn-danger",
        fileType: "any"
    });

    $("#create_dateOfBirth,#update_dateOfBirth").mask('9999-99-99');
    $("#create_dateOfBirth,#update_dateOfBirth").datepicker({dateFormat:'yy-mm-dd'});
    $(".select").selectpicker();
    $(".ajax-select").selectpicker().ajaxSelectPicker();
});

function create_user() {
    if (form_validate("create")) {
        saveFormDataWithAttachments('config/userManagement/createUser', 'create').done(function (responseObject) {
            if (responseObject.status === true) {
                add_option_to_dropdown('update_userSeq', responseObject.object, 'userSeq', 'username');
                add_option_to_dropdown('assignModule_userSeq', responseObject.object, 'userSeq', 'username');
                add_option_to_dropdown('removeModule_userSeq', responseObject.object, 'userSeq', 'username');
            }
        });
    }
}

function update_user() {
    if (form_validate("update")) {
        saveFormDataWithAttachments('config/userManagement/updateUser', 'update');
    }
}

function load_user_by_user_seq_to_update() {
    var userSeq = $("select#update_userSeq").val();
    if (userSeq !== '-1') {
        loadObjectData('config/userManagement/findUserByUserSeq/', userSeq, 'GET').done(function (user) {
            $('form#update').BindJson(user, 'update');
        });
    } else {
        $('form#update').trigger("reset");
    }
}

function load_module_list_by_company() {
    var companySeq = $('select#assignModule_companyProfileSeq').val();
    loadObjectData('config/userManagement/getModuleListByCompanySeq/', companySeq, "GET").done(function (moduleList) {
        populate_dropdown('assignModule_moduleSeq', moduleList, 'moduleSeq', 'moduleName');
    });
}

function load_assigned_Department_list_by_module(param) {
    var moduleSeq = $('select#' + param + 'UserDepartment_moduleSeq').val();
    loadObjectData('config/userManagement/getDepartmentListByModuleSeq/', moduleSeq, "GET").done(function (departmentList) {
        populate_dropdown(param + 'UserDepartment_DepartmentSeq', departmentList, 'departmentSeq', 'departmentName');
    });
}

function assign_modules_to_user() {
    if (form_validate('assignModule')) {
        saveFormData('config/userManagement/assignModuleToUser', 'assignModule')
    }
}

function assign_department_to_user() {
    if (form_validate("userDepartment")) {
        saveFormData('config/userManagement/assignDepartmentsToUser', 'userDepartment');
    }
}

function load_assigned_module_list_by_company() {
    var userSeq = $('select#removeModule_userSeq').val();
    var companySeq = $('select#removeModule_companyProfileSeq').val();
    var params = userSeq + '/' + companySeq;
    if (userSeq !== null && companySeq !== null) {
        loadObjectData('config/userManagement/getModuleListByUserSeqAndCompanySeq/', params, "GET").done(function (moduleList) {
            populate_dropdown('removeModule_moduleSeq', moduleList, 'moduleSeq', 'moduleName');
        });
    }
}

function load_assigned_department_list_by_module() {
    var userSeq = $('select#removeUserDepartment_userSeq').val();
    var moduleSeq = $('select#removeUserDepartment_moduleSeq').val();
    var params = userSeq + '/' + moduleSeq;
    if (userSeq !== null && moduleSeq !== null) {
        loadObjectData('config/userManagement/getDepartmentListByUserSeqAndModuleSeq/', params, "GET").done(function (departmentList) {
            populate_dropdown('removeUserDepartment_DepartmentSeq', departmentList, 'departmentSeq', 'departmentName');
        });
    }
}

function load_assigned_departmentList() {
    var userSeq = $('select#userDepartment_userSeq').val();
}

function remove_modules_from_user() {
    if (form_validate('removeModule')) {
        saveFormData('config/userManagement/removeModuleFromUser', 'removeModule')
    }
}

function load_assigned_company_list_to_user() {
    var userSeq = $('select#removeModule_userSeq').val();
    loadObjectData('config/userManagement/getCompanyListByUserSeq/', userSeq, "GET").done(function (companyList) {
        populate_dropdown('removeModule_companyProfileSeq', companyList, 'companyProfileSeq', 'companyName');
        if (companyList.length > 0) {
            load_assigned_module_list_by_company();
        }
    });
}

function load_assigned_module_list_to_user(param) {
    var userSeq = $('select#' + param + 'UserDepartment_userSeq').val();
    loadObjectData('config/userManagement/getModuleListByUserSeq/', userSeq, "GET").done(function (moduleList) {
        populate_dropdown(param + 'UserDepartment_moduleSeq', moduleList, 'moduleSeq', 'moduleName');
        if (moduleList.length > 0) {
            load_assigned_department_list_by_module();
        }
    });
}

function load_assigned_department_list_to_user() {
    load_assigned_departmentList();
}

function remove_deparment_from_user() {
    if (form_validate("removeUserDepartment")) {
        saveFormData('config/userManagement/removeDepartmentsFromUser', 'removeUserDepartment');
    }
}