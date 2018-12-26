package com.tmj.tms.master.businesslayer.managerImpl;

import com.querydsl.core.BooleanBuilder;
import com.tmj.tms.master.businesslayer.manager.FinalDestinationManagementControllerManager;
import com.tmj.tms.master.datalayer.modal.FinalDestination;
import com.tmj.tms.master.datalayer.modal.QFinalDestination;
import com.tmj.tms.master.datalayer.service.FinalDestinationService;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class FinalDestinationManagementControllerManagerImpl implements FinalDestinationManagementControllerManager {

    private final FinalDestinationService finalDestinationService;
    private final HttpSession httpSession;

    @Autowired
    public FinalDestinationManagementControllerManagerImpl(FinalDestinationService finalDestinationService,
                                                           HttpSession httpSession) {
        this.finalDestinationService = finalDestinationService;
        this.httpSession = httpSession;
    }

    @Override
    public ResponseObject saveDestination(FinalDestination finalDestination) {
        Integer companyProfileSeq = Integer.parseInt(httpSession.getAttribute("companyProfileSeq").toString());
        finalDestination.setCompanyProfileSeq(companyProfileSeq);
        finalDestination = this.finalDestinationService.save(finalDestination);
        ResponseObject responseObject = new ResponseObject("Final Destination Saved Successfully", true);
        responseObject.setObject(finalDestination);
        return responseObject;
    }

    @Override
    public ResponseObject updateDestination(FinalDestination finalDestination) {
        ResponseObject responseObject;
        Integer companyProfileSeq = Integer.parseInt(httpSession.getAttribute("companyProfileSeq").toString());
        finalDestination.setCompanyProfileSeq(companyProfileSeq);
        FinalDestination dbFinalDestination = this.finalDestinationService.findOne(finalDestination.getFinalDestinationSeq());
        if (dbFinalDestination != null) {
            if (!dbFinalDestination.equals(finalDestination)) {
                finalDestination = this.finalDestinationService.save(finalDestination);
                responseObject = new ResponseObject("Final Destination Updated Successfully", true);
                responseObject.setObject(finalDestination);
            } else {
                responseObject = new ResponseObject("No Amendments Found!!", false);
            }
        } else {
            responseObject = new ResponseObject("Final Destination Not Found!!", false);
        }
        return responseObject;
    }

    @Override
    public ResponseObject deleteDestination(Integer finalDestinationSeq) {
        FinalDestination dbFinalDestination = this.finalDestinationService.findOne(finalDestinationSeq);
        dbFinalDestination.setStatus(0);
        dbFinalDestination = this.finalDestinationService.save(dbFinalDestination);
        ResponseObject responseObject = new ResponseObject("Final Destination Deleted Successfully", true);
        responseObject.setObject(dbFinalDestination);
        return responseObject;
    }

    @Override
    public List<FinalDestination> searchDestination(String finalDestinationCode, Integer countrySeq, String city, String state, String zip) {
        List<FinalDestination> finalDestinationList = null;
        try {
            QFinalDestination finalDestination = QFinalDestination.finalDestination;
            BooleanBuilder builder = new BooleanBuilder();
            Integer companyProfileSeq = Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString());
            builder.and(finalDestination.companyProfileSeq.eq(companyProfileSeq));
            if (finalDestinationCode != null) {
                builder.and(finalDestination.destination.containsIgnoreCase(finalDestinationCode));
            }
            finalDestinationList = (List<FinalDestination>) this.finalDestinationService.findAll(builder);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return finalDestinationList;
    }
}
