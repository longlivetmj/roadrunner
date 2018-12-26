var formTemplate;
$(function () {
    formTemplate = $(".branchForm").html();
    $(".select").selectpicker();
    $(".ajax-select").selectpicker().ajaxSelectPicker();
});

function create_branch() {
    if (form_validate("create")) {
        saveFormData('master/branchManagement/addBranch', 'create').done(function (responseObject) {
            if (responseObject.status === true) {
                loadObjectData('master/branchManagement/getBranchDetails/', responseObject.object.branchSeq, 'GET').done(function (branch) {
                    transform_form(formTemplate,'create', 'update', {branch: branch});
                });
            }
        });
    }
}

function update_branch() {
    if (form_validate("update")) {
        saveFormData('master/branchManagement/updateBranch', 'update').done(function (responseObject) {
            if (responseObject.status === true) {
                loadObjectData('master/branchManagement/getBranchDetails/', responseObject.object.branchSeq, 'GET').done(function (branch) {
                    transform_form(formTemplate,'update', 'update', {branch: branch});
                });
            }
        });
    }
}

function update_branch_popup() {
    if (form_validate("modal")) {
        saveFormData('master/branchManagement/updateBranch', 'modal').done(function (responseObject) {
            if (responseObject.status === true) {
                load__branch_popup(responseObject.object.branchSeq);
            }
        });
    }
}

function delete_branch() {
    var formId = $(this)[0].getAttribute('data-id');
    var branchSeq = $('#' + formId + ' :input[name=branchSeq]').val();
    var data = {'branchSeq': branchSeq};
    loadDataPost('master/branchManagement/deleteByBranchSeq', data).done(function (responseObject) {
        if (responseObject.status === true) {
            if (formId === 'modal') {
                $('#myModal').modal('toggle');
                new_notification(responseObject.message, 'success');
                $("#tr_" + branchSeq).remove();
            } else {
                new_form(formTemplate, 'update', 'create');
                new_notification(responseObject.message, 'success');
            }
        } else {
            new_notification(responseObject.message, 'error');
        }
    });
}

function load_branch_table_data() {
    var branchName = $('input#search_branchName').val();
    var branchCode = $('input#search_branchCode').val();
    var bankName = $('input#search_bankName').val();
    var data = {'branchCode': branchCode, 'branchName': branchName, 'bankName': bankName};
    loadPageData('master/branchManagement/searchBranchData', data, "POST").done(function (pageData) {
        $("#loadBranchListData").html(pageData);
        $('.datatable').DataTable();
    });
}

function load_branch_popup(branchSeq) {
    loadObjectData('master/branchManagement/getBranchDetails/', branchSeq, 'GET').done(function (branch) {
        display_update_modal(formTemplate, 'myModal', 'modal', {branch: branch});
    });
}
