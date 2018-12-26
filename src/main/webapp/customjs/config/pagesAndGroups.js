$(function () {
    $(".select").selectpicker();
});

function update_module() {
    if (form_validate("updateModule")) {
        saveFormData('config/pagesAndGroups/updateModule', 'updateModule', 'reset');
    }
}

function create_module() {
    if (form_validate("moduleAdd")) {
        saveFormData('config/pagesAndGroups/addModule', 'moduleAdd', 'reset').done(function (responseObject) {
            if (responseObject.status === true) {
                add_option_to_dropdown('updateModule_moduleSeq', responseObject.object, 'moduleSeq', 'moduleName');
            }
        });
    }
}

function create_submodule() {
    if (form_validate("subModuleAdd")) {
        saveFormData('config/pagesAndGroups/addSubModule', 'subModuleAdd', 'reset').done(function (responseObject) {
            if (responseObject.status === true) {
                add_option_to_dropdown('updateSubModule_subModuleSeq', responseObject.object, 'subModuleSeq', 'subModuleName');
            }
        });
    }
}

function update_sub_module() {
    if (form_validate("updateSubModule")) {
        saveFormData('config/pagesAndGroups/updateSubModule', 'updateSubModule', 'reset');
    }
}

function create_actiongroup() {
    if (form_validate("actionGroupAdd")) {
        saveFormData('config/pagesAndGroups/addActionGroup', 'actionGroupAdd', 'reset').done(function (responseObject) {
            if (responseObject.status === true) {
                add_option_to_dropdown('updateActionGroup_actionGroupSeq', responseObject.object, 'actionGroupSeq', 'actionGroupName');
            }
        });
    }
}

function update_action_group() {
    if (form_validate("updateActionGroup")) {
        saveFormData('config/pagesAndGroups/updateActionGroup', 'updateActionGroup', 'reset');
    }
}

function create_group() {
    if (form_validate("groupAdd")) {
        saveFormData('config/pagesAndGroups/addGroup', 'groupAdd', 'reset').done(function (responseObject) {
            if (responseObject.status === true) {
                add_option_to_dropdown('updateGroup_groupSeq', responseObject.object, 'groupSeq', 'groupName');
            }
        });
    }
}

function update_group() {
    if (form_validate("updateGroup")) {
        saveFormData('config/pagesAndGroups/updateGroup', 'updateGroup', 'reset');

    }
}

function create_document_Link() {
    if (form_validate("documentLinkAdd")) {
        saveFormData('config/pagesAndGroups/addDocumentLink', 'documentLinkAdd', 'reset');
    }
}

function create_custom_role() {
    if (form_validate("customRoleAdd")) {
        saveFormData('config/pagesAndGroups/addCustomRole', 'customRoleAdd', 'reset');
    }
}

function load_module_by_module_seq_to_update() {
    var moduleSeq = $("#updateModule_moduleSeq").val();
    if (moduleSeq !== '-1') {
        loadObjectData('config/pagesAndGroups/findModuleByModuleSeq/', moduleSeq, 'GET').done(function (module) {
            $('#updateModule').BindJson(module, 'updateModule');
        });
    } else {
        $('#updateModule').trigger("reset");
    }

}

function load_submodule_by_submodule_seq_to_update() {
    var subModuleSeq = $("#updateSubModule_subModuleSeq").val();
    if (subModuleSeq !== '-1') {
        loadObjectData('config/pagesAndGroups/findSubModuleBySubModuleSeq/', subModuleSeq, 'GET').done(function (submodule) {
            $('#updateSubModule').BindJson(submodule, 'updateSubModule');
        });
    } else {
        $('#updateSubModule').trigger("reset");
    }

}

function load_action_group_by_action_group_seq_to_update() {
    var actionGroupSeq = $("#updateActionGroup_actionGroupSeq").val();
    if (actionGroupSeq !== '-1') {
        loadObjectData('config/pagesAndGroups/findActionGroupByActionGroupSeq/', actionGroupSeq, 'GET').done(function (actiongroup) {
            $('#updateActionGroup').BindJson(actiongroup, 'updateActionGroup');
        });
    } else {
        $('#updateActionGroup').trigger("reset");
    }

}

function load_group_by_group_seq_to_update() {
    var groupSeq = $("#updateGroup_groupSeq").val();
    if (groupSeq !== '-1') {
        loadObjectData('config/pagesAndGroups/findGroupByGroupSeq/', groupSeq, 'GET').done(function (group) {
            $('#updateGroup').BindJson(group, 'updateGroup');
        });
    } else {
        $('#updateGroup').trigger("reset");
    }

}

function load_submodule_by_module_seq() {
    var moduleSeq = $("#createDocumentLinkModuleSeq").val();
    if (moduleSeq !== null) {
        loadObjectData('config/pagesAndGroups/getSubModuleListByModuleSeq/', moduleSeq, "GET").done(function (subModuleList) {
            populate_dropdown('createDocumentLinkSubModuleSeq', subModuleList, 'subModuleSeq', 'subModuleName');
            load_action_group_by_sub_module_seq();
        });
    }
}

function load_action_group_by_sub_module_seq() {
    var subModuleSeq = $("#createDocumentLinkSubModuleSeq").val();
    if (subModuleSeq !== null) {
        loadObjectData('config/pagesAndGroups/getActionGroupListBySubModuleSeq/', subModuleSeq, "GET").done(function (actionGroupList) {
            populate_dropdown('createDocumentLinkActionGroupSeq', actionGroupList, 'actionGroupSeq', 'actionGroupName');
        });
    }
}

function load_submodule_by_module_seq_for_action_group() {
    var moduleSeq = $("#actionGroupModuleSeq").val();
    if (moduleSeq !== null) {
        loadObjectData('config/pagesAndGroups/getSubModuleListByModuleSeq/', moduleSeq, "GET").done(function (subModuleList) {
            populate_dropdown('actionGroupSubModuleSeq', subModuleList, 'subModuleSeq', 'subModuleName');
        });
    }
}
