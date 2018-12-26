package com.tmj.tms.master.businesslayer.manager;

import com.tmj.tms.master.datalayer.modal.Stakeholder;
import com.tmj.tms.utility.ResponseObject;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Date;
import java.util.List;

public interface StakeholderManagementControllerManager {

    ResponseObject saveStakeholder(Stakeholder stakeholder, HttpServletRequest request, Principal principal);

    ResponseObject updateStakeholder(Stakeholder stakeholder, Principal principal);

    ResponseObject deleteStakeholder(Integer stakeholderSeq, Principal principal);

    List<Stakeholder> searchStakeholder(String stakeholderSeq,
                                        Integer stakeholderTypeSeq,
                                        String stakeholderName,
                                        Integer countrySeq,
                                        Integer status,
                                        Integer stakeholderGroupSeq,
                                        Date startDate,
                                        Date endDate);

    ResponseObject validateStakeholder(Stakeholder stakeholder);
}
