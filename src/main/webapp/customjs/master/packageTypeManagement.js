var formTemplate;
$(function () {
    formTemplate = $(".packageTypeForm").html();
    $(".select").selectpicker();
});

function create_new_packageType() {
    if (form_validate("create")) {
        saveFormData('master/packageTypeManagement/createPackageType', 'create', 'reset').done(function (responseObject) {
            if (responseObject.status === true) {
                loadObjectData('master/packageTypeManagement/findByPackageTypeSeq/', responseObject.object.packageTypeSeq, 'GET').done(function (packageType) {
                    transform_form(formTemplate, 'create', 'update', {packageType: packageType});
                });
            }
        });
    }
}

function update_package_type() {
    if (form_validate("update")) {
        saveFormData('master/packageTypeManagement/updatePackageType', 'update').done(function (responseObject) {
            if (responseObject.status === true) {
                loadObjectData('master/packageTypeManagement/findByPackageTypeSeq/', responseObject.object.packageTypeSeq, 'GET').done(function (packageType) {
                    transform_form(formTemplate, 'update', 'update', {packageType: packageType});
                });
            }
        });
    }
}

function search_package_type() {
    var packageTypeName = $('input#search_packageTypeName').val();
    var packageTypeCode = $('input#search_packageTypeCode').val();
    var description = $('input#search_description').val();
    var data = {
        'packageTypeName': packageTypeName,
        'packageTypeCode': packageTypeCode,
        'description': description
    };
    if (form_validate("search")) {
        loadPageData('master/packageTypeManagement/searchPackageType', data, "POST").done(function (responseObject) {
            $("#loadPackageTypeSearchResult").html(responseObject);
            $('.datatable').DataTable();
        });
    }
}

function update_package_type_modal() {
    if (form_validate("modal")) {
        saveFormData('master/packageTypeManagement/updatePackageType', 'modal').done(function (responseObject) {
            if (responseObject.status === true) {
                load_package_type_data_to_modal(responseObject.object.packageTypeSeq);
            }
        });
    }
}

function load_package_type_data_to_modal(packageTypeSeq) {
    loadObjectData('master/packageTypeManagement/findByPackageTypeSeq/', packageTypeSeq, 'GET').done(function (packageType) {
        display_update_modal(formTemplate, 'myModal', 'modal', {packageType: packageType});
    });
}

function delete_package_type_popup() {
    var formId = $(this)[0].getAttribute('data-id');
    var packageTypeSeq = $('#' + formId + ' :input[name=packageTypeSeq]').val();
    var data = {'packageTypeSeq': packageTypeSeq};
    loadDataPost('master/packageTypeManagement/deleteByPackageTypeSeq', data).done(function (responseObject) {
        if (responseObject.status === true) {
            $('#myModal').modal('toggle');
            new_notification(responseObject.message, 'success');
            $("#tr_" + packageTypeSeq).remove();
        } else {
            new_notification(responseObject.message, 'error');
        }
    });
}

function delete_package_type() {
    var packageTypeSeq = $('#packageTypeSeq').val();
    var data = {'packageTypeSeq': packageTypeSeq};
    loadDataPost('master/packageTypeManagement/deleteByPackageTypeSeq', data).done(function (packageType) {
        if (packageType.status === true) {
            new_notification(packageType.message, 'success');
            new_form(formTemplate, 'update', 'create')
        } else {
            new_notification(packageType.message, 'error');
        }
    });
}