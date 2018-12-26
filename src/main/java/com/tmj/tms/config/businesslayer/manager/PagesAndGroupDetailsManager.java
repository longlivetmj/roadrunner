package com.tmj.tms.config.businesslayer.manager;

import com.tmj.tms.config.datalayer.modal.*;
import com.tmj.tms.utility.ResponseObject;

import java.security.Principal;

public interface PagesAndGroupDetailsManager {

    ResponseObject saveModule(Module module, Principal principal);

    ResponseObject saveSubModule(SubModule subModule, Principal principal);

    ResponseObject saveActionGroup(ActionGroup actionGroup, Principal principal);

    ResponseObject saveGroup(Group group, Principal principal);

    ResponseObject saveDocumentLink(DocumentLink documentLink, Integer create, Integer view, Integer update, Integer remove, Integer approve, Integer viewDelete, Principal principal);

    ResponseObject updateModule(Module module, Principal principal);

    ResponseObject updateSubModule(SubModule subModule, Principal principal);

    ResponseObject updateActionGroup(ActionGroup actionGroup, Principal principal);

    ResponseObject updateGroup(Group group, Principal principal);

    ResponseObject saveCustomRole(Integer documentLinkSeq, String authority, Principal principal);
}
