/**
 * Created by Harshaa on 9/9/2016.
 */
var formTemplate;

$(function () {
    formTemplate = $(".companyProfileManagementForm").html();

    $(".singleFile").fileinput({
        showUpload: false,
        showCaption: false,
        browseClass: "btn btn-danger",
        fileType: "any"
    });

    $(".select").selectpicker();

});

function create_company_profile() {
    if (form_validate("create")) {
        var responseObject = saveFormDataWithAttachments('config/companyProfileManagement/createCompanyProfile', 'create');
        if (responseObject.status == true) {
            transform_form(formTemplate, 'create', 'update', {companyProfile: responseObject.object});
        }
    }
}

function update_company_profile() {
    if (form_validate("update")) {
        var responseObject = saveFormDataWithAttachments('config/companyProfileManagement/updateCompanyProfile', 'update');
        if (responseObject.status == true) {
            BindJsonByForm(formTemplate, {companyProfile: responseObject.object}, 'update')
        }
    }
}

function update_company_profile_from_modal() {
    if (form_validate("modal")) {
        var responseObject = saveFormDataWithAttachments('config/companyProfileManagement/updateCompanyProfile', 'modal');
        if (responseObject.status == true) {
            BindJsonByForm(formTemplate, {companyProfile: responseObject.object}, 'modal');
        }
    }
}

function delete_company_profile() {
    var formId = $(this)[0].getAttribute('data-id');
    var companyProfileSeq = $('#' + formId + ' :input[name=companyProfileSeq]').val();
    var data = {'companyProfileSeq': companyProfileSeq};
    var responseObject = loadDataPost('config/companyProfileManagement/deleteCompanyProfile', data);
    if (responseObject.status == true) {
        if (formId == 'modal') {
            $('#myModal').modal('toggle');
            new_notification(responseObject.message, 'success');
            $("#tr_" + companyProfileSeq).remove();
        } else {
            new_form('update', 'create');
        }
    } else {
        new_notification(responseObject.message, 'error');
    }
}


function load_company_profile_data_to_modal(companyProfileSeq) {
    var companyProfile = loadObjectData('config/companyProfileManagement/findByCompanyProfileSeq/', companyProfileSeq, 'GET');
    display_update_modal(formTemplate, 'myModal', 'modal', {companyProfile: companyProfile});
}

function search_company_profiles() {
    var companyName = $('input#search_companyName').val();
    var data = {'companyName': companyName};
    var responseObject = loadPageData('config/companyProfileManagement/searchCompanyProfiles', data, "GET");
    $("#loadCompanyProfileData").html(responseObject);
    $('.datatable').DataTable();
}


function load_company_profiles_by_company_seq_to_update() {
    var companyProfileSeq = $("#updateCompanyProfileFromCompanySeq").val();
    if (companyProfileSeq != '-1') {
        var companyProfile = loadObjectData('config/companyProfileManagement/findCompanyProfileByCompanyProfileSeq/', companyProfileSeq, 'GET');
        BindJsonByForm(formTemplate, {companyProfile: companyProfile}, 'update');
    } else {
        $('#update').rest();
    }
}

function load_assigned_module_list_by_company() {
    var companyProfileSeq = $('select#companyToRemove').val();
    if (companyProfileSeq != null) {
        var moduleList = loadObjectData('config/companyProfileManagement/getModuleListByCompanyProfileSeq/', companyProfileSeq, "GET");
        populate_dropdown('companyModulesToRemove', moduleList, 'moduleSeq', 'moduleName');
    }
}

function assign_modules_to_company() {
    if (form_validate("assignModulesToCompanyProfile")) {
        saveFormData("/config/companyProfileManagement/assignModules", "assignModulesToCompanyProfile");
    }
}

function remove_modules_from_company() {
    if (form_validate("removeModulesFromCompanyProfile")) {
        saveFormData("/config/companyProfileManagement/removeModules", "removeModulesFromCompanyProfile");
    }
}