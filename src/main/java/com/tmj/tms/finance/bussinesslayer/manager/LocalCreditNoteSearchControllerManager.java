package com.tmj.tms.finance.bussinesslayer.manager;


import com.tmj.tms.finance.datalayer.modal.LocalCreditNoteHeader;
import com.tmj.tms.finance.datalayer.modal.auxiliary.LocalCreditNoteSearchAux;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface LocalCreditNoteSearchControllerManager {
    List<LocalCreditNoteHeader> searchLocalCreditNoteDetail(LocalCreditNoteSearchAux localCreditNoteSearchAux,
                                                            HttpServletRequest request);
}
