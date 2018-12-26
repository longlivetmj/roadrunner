package com.tmj.tms.finance.bussinesslayer.manager;

import com.tmj.tms.finance.datalayer.modal.RateMaster;
import com.tmj.tms.utility.ResponseObject;

import javax.servlet.http.HttpServletRequest;

public interface RateMasterManagementControllerManager {

    ResponseObject saveRateMaster(RateMaster rateMaster, HttpServletRequest httpServletRequest);

    ResponseObject updateRateMaster(RateMaster rateMaster, HttpServletRequest httpServletRequest);

}
