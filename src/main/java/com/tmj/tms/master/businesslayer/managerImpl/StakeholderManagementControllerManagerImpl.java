package com.tmj.tms.master.businesslayer.managerImpl;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphUtils;
import com.querydsl.core.BooleanBuilder;
import com.tmj.tms.master.businesslayer.manager.StakeholderManagementControllerManager;
import com.tmj.tms.master.datalayer.modal.AddressBook;
import com.tmj.tms.master.datalayer.modal.QStakeholder;
import com.tmj.tms.master.datalayer.modal.Stakeholder;
import com.tmj.tms.master.datalayer.modal.StakeholderTypeMapping;
import com.tmj.tms.master.datalayer.service.StakeholderService;
import com.tmj.tms.utility.ResponseObject;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class StakeholderManagementControllerManagerImpl implements StakeholderManagementControllerManager {

    private final StakeholderService stakeholderService;

    @Autowired
    public StakeholderManagementControllerManagerImpl(StakeholderService stakeholderService) {
        this.stakeholderService = stakeholderService;
    }

    @Override
    public ResponseObject saveStakeholder(Stakeholder stakeholder,
                                          HttpServletRequest request,
                                          Principal principal) {
        ResponseObject responseObject;
        List<StakeholderTypeMapping> stakeholderTypeMappingList = new ArrayList<>();
        try {
            Integer companyProfileSeq = Integer.parseInt(request.getSession().getAttribute("companyProfileSeq").toString());
            stakeholder.setCompanyProfileSeq(companyProfileSeq);
            AddressBook dbAddressBook = stakeholder.getAddressBook();
            dbAddressBook.setCreatedBy(principal.getName());
            dbAddressBook.setCreatedDate(new Date());
            dbAddressBook.setLastModifiedBy(principal.getName());
            dbAddressBook.setLastModifiedDate(new Date());
            stakeholder.setAddressBook(dbAddressBook);
            if (stakeholder.getStakeholderTypeMappings() != null) {
                for (StakeholderTypeMapping stakeholderTypeMapping : stakeholder.getStakeholderTypeMappings()) {
                    if (stakeholderTypeMapping.getStakeholderTypeSeq() != null) {
                        stakeholderTypeMapping.setCreatedDate(new Date());
                        stakeholderTypeMapping.setCreatedBy(principal.getName());
                        stakeholderTypeMapping.setLastModifiedBy(principal.getName());
                        stakeholderTypeMapping.setLastModifiedDate(new Date());
                        stakeholderTypeMapping.setStatus(1);
                        stakeholderTypeMappingList.add(stakeholderTypeMapping);
                    }
                }
                stakeholder.setStakeholderTypeMappings(stakeholderTypeMappingList);
            }
            responseObject = this.validateStakeholder(stakeholder);
            if (responseObject.isStatus()) {
                stakeholder = this.stakeholderService.save(stakeholder);
                responseObject = new ResponseObject("Stakeholder Saved Successfully", true);
                responseObject.setObject(stakeholder);
            }
        } catch (Exception e) {
            Throwable throwable = ExceptionUtils.getRootCause(e);
            if (throwable instanceof SQLIntegrityConstraintViolationException) {
                responseObject = new ResponseObject("Stakeholder Already Exists !!", false);
            } else {
                e.printStackTrace();
                responseObject = new ResponseObject(ExceptionUtils.getRootCauseMessage(e), false);
            }
        }
        return responseObject;
    }

    @Override
    public ResponseObject validateStakeholder(Stakeholder stakeholder) {
        ResponseObject responseObject = new ResponseObject(true, "No Error");
        List<Stakeholder> dbStakeholderList = this.stakeholderService.findByStakeholderCode(stakeholder.getStakeholderCode());
        if (dbStakeholderList != null && dbStakeholderList.size() > 0) {
            responseObject.setStatus(false);
            responseObject.setMessage("Stakeholder Code Already Exists");
        }
        return responseObject;
    }

    @Override
    public ResponseObject updateStakeholder(Stakeholder stakeholder, Principal principal) {
        ResponseObject responseObject;
        List<StakeholderTypeMapping> frontStakeholderTypeMappingList = stakeholder.getStakeholderTypeMappings();
        List<StakeholderTypeMapping> filteredList = new ArrayList<>();
        for (StakeholderTypeMapping stakeholderTypeMapping : frontStakeholderTypeMappingList) {
            if (stakeholderTypeMapping.getStakeholderTypeSeq() != null) {
                stakeholderTypeMapping.setStatus(1);
                filteredList.add(stakeholderTypeMapping);
            }
        }
        Stakeholder dbStakeholder = this.stakeholderService.findOne(stakeholder.getStakeholderSeq());
        if (!dbStakeholder.equals(stakeholder)) {
            responseObject = this.validateStakeholderForUpdate(stakeholder);
            if (responseObject.isStatus()) {
                stakeholder.setStakeholderTypeMappings(filteredList);
                stakeholder = this.stakeholderService.save(stakeholder);
                responseObject = new ResponseObject("Stakeholder Updated Successfully", true);
                responseObject.setObject(stakeholder);
            }
        } else {
            responseObject = new ResponseObject("Not found any Amendments to Update", false);
        }
        return responseObject;
    }

    private ResponseObject validateStakeholderForUpdate(Stakeholder stakeholder) {
        ResponseObject responseObject = new ResponseObject(true, "No Error");

        List<Stakeholder> dbStakeholderList = this.stakeholderService.findByStakeholderCodeAndStakeholderSeqNot(stakeholder.getStakeholderCode(), stakeholder.getStakeholderSeq());
        if (dbStakeholderList != null && dbStakeholderList.size() > 0) {
            responseObject.setStatus(false);
            responseObject.setMessage("Stakeholder Code Already Exists");
        }
        return responseObject;
    }

    @Override
    public ResponseObject deleteStakeholder(Integer stakeholderSeq, Principal principal) {
        ResponseObject responseObject;
        try {
            Stakeholder dbStakeholder = this.stakeholderService.findOne(stakeholderSeq);
            dbStakeholder.setStatus(0);
            dbStakeholder.setLastModifiedBy(principal.getName());
            dbStakeholder.setLastModifiedDate(new Date());
            dbStakeholder = this.stakeholderService.save(dbStakeholder);
            responseObject = new ResponseObject("Stakeholder Deleted Successfully", true);
            responseObject.setObject(dbStakeholder);
        } catch (Exception e) {
            e.printStackTrace();
            responseObject = new ResponseObject(ExceptionUtils.getRootCauseMessage(e), false);
        }
        return responseObject;
    }

    @Override
    public List<Stakeholder> searchStakeholder(String stakeholderSeq,
                                               Integer stakeholderTypeSeq,
                                               String stakeholderName,
                                               Integer countrySeq,
                                               Integer status,
                                               Integer stakeholderGroupSeq,
                                               Date startDate,
                                               Date endDate) {
        List<Stakeholder> stakeholderList = new ArrayList<>();
        try {
            QStakeholder stakeholder = QStakeholder.stakeholder;
            BooleanBuilder builder = new BooleanBuilder();
            if (stakeholderTypeSeq == -1) {
                if (!stakeholderName.isEmpty()) {
                    builder.and(stakeholder.stakeholderName.startsWithIgnoreCase(stakeholderName));
                }
                if (countrySeq != -1) {
                    builder.and(stakeholder.addressBook.countrySeq.eq(countrySeq));
                }
                if (stakeholderSeq != null && !stakeholderSeq.isEmpty()) {
                    builder.and(stakeholder.stakeholderSeq.eq(Integer.parseInt(stakeholderSeq)));
                }
                if (stakeholderGroupSeq != -1) {
                    builder.and(stakeholder.stakeholderGroupSeq.eq(stakeholderGroupSeq));
                }
                if (status != -1) {
                    builder.and(stakeholder.status.eq(status));
                }
                if (startDate != null && endDate != null) {
                    builder.and(stakeholder.createdDate.between(startDate, endDate));
                }
                stakeholderList = (List<Stakeholder>) this.stakeholderService.findAll(builder, EntityGraphUtils.fromName("Stakeholder.search"));
            } else {
                stakeholderList = this.stakeholderService.getStakeholderDetails(stakeholderTypeSeq);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stakeholderList;
    }
}
