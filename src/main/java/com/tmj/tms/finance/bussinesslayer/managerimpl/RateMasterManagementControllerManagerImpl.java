package com.tmj.tms.finance.bussinesslayer.managerimpl;

import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.finance.bussinesslayer.manager.RateMasterManagementControllerManager;
import com.tmj.tms.finance.datalayer.modal.RateMaster;
import com.tmj.tms.finance.datalayer.modal.RateMasterDetail;
import com.tmj.tms.finance.datalayer.modal.RateMasterStakeholder;
import com.tmj.tms.finance.datalayer.service.RateMasterService;
import com.tmj.tms.master.datalayer.modal.StakeholderType;
import com.tmj.tms.master.datalayer.service.StakeholderTypeService;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class RateMasterManagementControllerManagerImpl implements RateMasterManagementControllerManager {

    private final StakeholderTypeService stakeholderTypeService;
    private final RateMasterService rateMasterService;

    @Autowired
    public RateMasterManagementControllerManagerImpl(StakeholderTypeService stakeholderTypeService,
                                                     RateMasterService rateMasterService) {
        this.stakeholderTypeService = stakeholderTypeService;
        this.rateMasterService = rateMasterService;
    }

    @Override
    public ResponseObject saveRateMaster(RateMaster rateMaster, HttpServletRequest httpServletRequest) {
        ResponseObject responseObject = new ResponseObject();
        String[] shipperList = httpServletRequest.getParameterValues("shipperSeq[]");
        rateMaster = this.setRateMasterStakeholders(shipperList, rateMaster);
        for (RateMasterDetail rateMasterDetail : rateMaster.getRateMasterDetailList()) {
            rateMasterDetail.setStatus(MasterDataStatus.APPROVED.getStatusSeq());
        }
        rateMaster = this.rateMasterService.save(rateMaster);
        responseObject.setStatus(true);
        responseObject.setMessage("Rate Master Data successfully saved");
        responseObject.setObject(rateMaster);
        return responseObject;
    }

    @Override
    public ResponseObject updateRateMaster(RateMaster rateMaster, HttpServletRequest httpServletRequest) {
        ResponseObject responseObject = new ResponseObject();
        RateMaster rateMasterDB = this.rateMasterService.findOne(rateMaster.getRateMasterSeq());
        if (rateMasterDB != null) {
            String[] shipperList = httpServletRequest.getParameterValues("shipperSeq[]");
            rateMaster = this.setRateMasterStakeholders(shipperList, rateMaster);
            for (RateMasterDetail rateMasterDetail : rateMaster.getRateMasterDetailList()) {
                rateMasterDetail.setStatus(MasterDataStatus.APPROVED.getStatusSeq());
            }
            if (!rateMasterDB.equals(rateMaster)) {
                rateMaster = this.rateMasterService.save(rateMaster);
                responseObject.setStatus(true);
                responseObject.setMessage("Rate Master Data successfully saved");
                responseObject.setObject(rateMaster);
            } else {
                responseObject.setStatus(false);
                responseObject.setMessage("No amendments found");
            }
        } else {
            responseObject.setStatus(false);
            responseObject.setMessage("Cannot find Rate Master");
        }
        return responseObject;
    }

    private RateMaster setRateMasterStakeholders(String[] shipperList, RateMaster rateMaster) {
        List<RateMasterStakeholder> rateMasterStakeholderList = new ArrayList<>();
        if (shipperList != null && shipperList.length > 0) {
            StakeholderType stakeholderType = this.stakeholderTypeService.findByStakeholderTypeCodeAndStatus("SHIPPER", MasterDataStatus.APPROVED.getStatusSeq());
            for (String shipper : shipperList) {
                RateMasterStakeholder rateMasterStakeholder = new RateMasterStakeholder();
                rateMasterStakeholder.setStakeholderTypeSeq(stakeholderType.getStakeholderTypeSeq());
                rateMasterStakeholder.setStakeholderSeq(Integer.parseInt(shipper));
                rateMasterStakeholder.setStatus(MasterDataStatus.APPROVED.getStatusSeq());
                rateMasterStakeholderList.add(rateMasterStakeholder);
            }
        }
        rateMaster.setRateMasterStakeholderList(rateMasterStakeholderList);
        return rateMaster;
    }

}
