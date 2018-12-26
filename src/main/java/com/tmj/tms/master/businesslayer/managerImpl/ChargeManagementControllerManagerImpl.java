package com.tmj.tms.master.businesslayer.managerImpl;

import com.tmj.tms.config.datalayer.modal.CompanyModule;
import com.tmj.tms.config.datalayer.modal.Module;
import com.tmj.tms.config.datalayer.service.CompanyModuleService;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.master.businesslayer.manager.ChargeManagementControllerManager;
import com.tmj.tms.master.datalayer.modal.Charge;
import com.tmj.tms.master.datalayer.modal.ChargeMode;
import com.tmj.tms.master.datalayer.service.ChargeModeService;
import com.tmj.tms.master.datalayer.service.ChargeService;
import com.tmj.tms.utility.ResponseObject;
import com.tmj.tms.utility.YesOrNo;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ChargeManagementControllerManagerImpl implements ChargeManagementControllerManager {

    private final ChargeService chargeService;
    private final ChargeModeService chargeModeService;
    private final CompanyModuleService companyModuleService;
    private final HttpSession httpSession;

    @Autowired
    public ChargeManagementControllerManagerImpl(ChargeService chargeService,
                                                 ChargeModeService chargeModeService,
                                                 CompanyModuleService companyModuleService,
                                                 HttpSession httpSession) {
        this.chargeService = chargeService;
        this.chargeModeService = chargeModeService;
        this.companyModuleService = companyModuleService;
        this.httpSession = httpSession;
    }

    @Override
    @Transactional
    public ResponseObject createCharge(Charge charge, List<Integer> modeSeqList) {
        ResponseObject responseObject = new ResponseObject("Charge Saved Successfully", true);
        Set<ChargeMode> chargeModes = new HashSet<>();
        charge = this.chargeService.save(charge);
        if (modeSeqList != null) {
            for (Integer modeSeq : modeSeqList) {
                ChargeMode chargeMode = new ChargeMode();
                chargeMode.setStatus(YesOrNo.Yes.getYesOrNoSeq());
                chargeMode.setChargeSeq(charge.getChargeSeq());
                chargeMode.setCompanyModuleSeq(modeSeq);
                chargeModes.add(chargeMode);
            }
            chargeModeService.save(chargeModes);
            charge.setChargeModes(chargeModes);
        }
        responseObject.setObject(charge);
        return responseObject;
    }

    @Override
    @Transactional
    public ResponseObject updateCharge(Charge charge, List<Integer> modeSeqList) {
        ResponseObject responseObject = new ResponseObject("Charge Updated Successfully ", true);

        Charge dbCharge = this.chargeService.findOne(charge.getChargeSeq());
        if (dbCharge != null) {
            dbCharge.setChargeName(charge.getChargeName());
            dbCharge.setDescription(charge.getDescription());
            dbCharge.setStatus(charge.getStatus());
            dbCharge = this.chargeService.save(dbCharge);
            responseObject.setObject(dbCharge);

            List<ChargeMode> chargeModes = this.chargeModeService.findByChargeSeq(charge.getChargeSeq());
            List<Integer> newModeSeqList = new ArrayList<>();
            List<Integer> existingModeSeqList = new ArrayList<>();
            for (ChargeMode dbChargeMode : chargeModes) {
                dbChargeMode.setStatus(YesOrNo.NO.getYesOrNoSeq());
                if (modeSeqList != null) {
                    if (modeSeqList.contains(dbChargeMode.getCompanyModuleSeq())) {
                        dbChargeMode.setStatus(YesOrNo.Yes.getYesOrNoSeq());
                        existingModeSeqList.add(dbChargeMode.getCompanyModuleSeq());
                    }
                }
            }
            if (modeSeqList != null) {
                if (modeSeqList.size() > existingModeSeqList.size()) {
                    for (Integer modeSeq : modeSeqList) {
                        if (!existingModeSeqList.contains(modeSeq)) {
                            newModeSeqList.add(modeSeq);
                        }
                    }
                    Set<ChargeMode> newChargeModes = new HashSet<>();
                    for (Integer newModeSeq : newModeSeqList) {
                        ChargeMode chargeMode = new ChargeMode();
                        chargeMode.setChargeSeq(charge.getChargeSeq());
                        chargeMode.setCompanyModuleSeq(newModeSeq);
                        chargeMode.setStatus(YesOrNo.Yes.getYesOrNoSeq());
                        newChargeModes.add(chargeMode);
                    }
                    this.chargeModeService.save(newChargeModes);
                }
            }
        } else {
            responseObject.setMessage("Charge not found!!");
            responseObject.setStatus(false);
        }
        return responseObject;
    }

    @Override
    public ResponseObject deleteCharge(Integer chargeSeq) {
        ResponseObject responseObject = new ResponseObject("Charge Deleted Successfully", true);
        try {
            Charge dbCharge = this.chargeService.findOne(chargeSeq);
            dbCharge.setStatus(MasterDataStatus.DELETED.getStatusSeq());
            dbCharge = this.chargeService.save(dbCharge);
            responseObject.setObject(dbCharge);
        } catch (Exception e) {
            e.printStackTrace();
            responseObject.setMessage(ExceptionUtils.getRootCauseMessage(e));
            responseObject.setStatus(false);
        }
        return responseObject;
    }

    @Override
    public List<Charge> searchCharge(String chargeName, String description, Integer customerProfileSeq) {
        List<Charge> chargeList = new ArrayList<>();
        try {
            List<Integer> statusSeqList = MasterDataStatus.getStatusListForViewing("ROLE_master@taxRegistrationManagement_VIEW-DELETE");
            if (chargeName.equals("") && description.equals("")) {
                chargeList = this.chargeService.findByStatusIn(statusSeqList);
            } else {
                chargeList = this.chargeService.findByChargeNameContainingIgnoreCaseAndDescriptionContainingIgnoreCaseAndStatusIn(chargeName,
                        description, statusSeqList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chargeList;
    }

    @Override
    public List<CompanyModule> getFinanceEnabledCompanyModuleList() {
        List<CompanyModule> companyModuleList = new ArrayList<>();
        try {
            Integer customerProfileSeq = Integer.parseInt(httpSession.getAttribute("companyProfileSeq").toString());
            for (CompanyModule companyModule : this.companyModuleService.findByCompanyProfileSeq(customerProfileSeq)) {
                Module module = companyModule.getModule();
                if (module.getFinanceEnabled() != null) {
                    if (module.getFinanceEnabled() == YesOrNo.Yes.getYesOrNoSeq()) {
                        companyModuleList.add(companyModule);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return companyModuleList;
    }

    public List<Charge> getViewingChargeList() {
        List<Charge> chargeList = new ArrayList<>();
        try {
            List<Integer> statusSeqList = MasterDataStatus.getStatusListForViewing("ROLE_master@chargeManagement_VIEW-DELETE");
            chargeList = this.chargeService.findByStatusIn(statusSeqList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chargeList;
    }

}