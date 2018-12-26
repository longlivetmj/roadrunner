$(function () {
    $('.select').selectpicker();
    load_module_wise_groups_for_document_link_assign();
});

function loadModuleWiseGroupList() {
    var data = {
        'moduleSeq': $("#groupAssignModuleSeq").val(),
        'companyProfileSeq': $("#groupAssignCompanyProfileSeq").val()
    };
    loadPageData('config/permissionManagement/loadModuleWiseGroupList/', data, "POST").done(function (pageData) {
        $("#loadGroupList").html(pageData);
        loadAssignedGroupsToUser('config/permissionManagement/loadAssignedGroupList');
    });
}

function saveAssignedGroups() {
    saveFormData('config/permissionManagement/submitUserGroups', 'groupAssignForm');
}

function loadAssignedGroupsToUser(url) {
    var userSeq = $("#groupAssignUserSeq").val();
    var moduleSeq = $("#groupAssignModuleSeq").val();
    var companyProfileSeq = $("#groupAssignCompanyProfileSeq").val();
    var data = {'userSeq': userSeq, 'moduleSeq': moduleSeq, 'companyProfileSeq': companyProfileSeq};
    loadDataPost(url, data).done(function (responseData) {
        $('input:checkbox').removeAttr('checked');
        for (var i = 0; i < responseData.length; i++) {
            document.getElementById(responseData[i].groupSeq).checked = 'checked';
        }
    });
}

function loadModuleWiseDocumentLinkList() {
    var moduleSeq = $("#documentLinkAssignForm :input[name='moduleSeq']").val();
    var data = {'moduleSeq': moduleSeq};
    loadPageData('config/permissionManagement/loadModuleWiseDocumentLinkList', data, "POST").done(function (pageData) {
        $("#loadDocumentLinkList").html(pageData);
        $('.datatable').DataTable();
        loadAssignedAuthoritiesToGroup('config/permissionManagement/loadAssignedAuthorityList');
    });

}

function load_module_wise_groups_for_document_link_assign() {
    var moduleSeq = $('select#documentLinkAssignModuleSeq').val();
    if (moduleSeq !== null) {
        loadObjectData('config/permissionManagement/getGroupListByModuleSeq/', moduleSeq, "GET").done(function (groupList) {
            populate_dropdown('documentLinkAssignGroupSeq', groupList, 'groupSeq', 'groupName');
            loadModuleWiseDocumentLinkList();
        });
    }
}

function saveAssignedAuthoritiesToGroup() {
    dataTableSubmit('documentLinkAssignForm', 'groupAuthorities');
    saveFormData('config/permissionManagement/submitGroupAuthorities', 'documentLinkAssignForm');
}

function loadAssignedAuthoritiesToGroup(url) {
    var groupSeq = $("#documentLinkAssignForm :input[name='groupSeq']").val();
    var moduleSeq = $("#documentLinkAssignForm :input[name='moduleSeq']").val();
    var data = {'groupSeq': groupSeq, 'moduleSeq': moduleSeq};
    loadDataPost(url, data).done(function (responseData) {
        $('input:checkbox').removeAttr('checked');
        $('.dataTable').dataTable().fnDestroy();
        for (var i = 0; i < responseData.length; i++) {
            document.getElementById(responseData[i].authoritySeq).checked = 'checked';
        }
        $('.dataTable').dataTable();
    });
}

function load_company_list_by_user() {
    var userSeq = $('#groupAssignUserSeq').val();
    if (userSeq !== 0) {
        loadObjectData('config/permissionManagement/getCompanyListByUserSeq/', userSeq, "GET").done(function (companyList) {
            populate_dropdown('groupAssignCompanyProfileSeq', companyList, 'companyProfileSeq', 'companyName');
            if (companyList.length > 0) {
                load_assigned_module_list_by_company();
            }
        });
    }
}

function load_assigned_module_list_by_company() {
    var userSeq = $('#groupAssignUserSeq').val();
    var companySeq = $('#groupAssignCompanyProfileSeq').val();
    var params = userSeq + '/' + companySeq;
    loadObjectData('config/permissionManagement/getModuleListByUserSeqAndCompanySeq/', params, "GET").done(function (moduleList) {
        populate_dropdown('groupAssignModuleSeq', moduleList, 'moduleSeq', 'moduleName');
        if (moduleList.length > 0) {
            loadModuleWiseGroupList();
        }
    });
}