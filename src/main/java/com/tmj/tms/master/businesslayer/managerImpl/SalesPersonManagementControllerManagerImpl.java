package com.tmj.tms.master.businesslayer.managerImpl;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphUtils;
import com.querydsl.core.BooleanBuilder;
import com.tmj.tms.master.businesslayer.manager.SalesPersonManagementControllerManager;
import com.tmj.tms.master.datalayer.modal.QSalesPerson;
import com.tmj.tms.master.datalayer.modal.SalesPerson;
import com.tmj.tms.master.datalayer.service.SalesPersonService;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class SalesPersonManagementControllerManagerImpl implements SalesPersonManagementControllerManager {

    private final SalesPersonService salesPersonService;

    @Autowired
    public SalesPersonManagementControllerManagerImpl(SalesPersonService salesPersonService) {
        this.salesPersonService = salesPersonService;
    }

    @Override
    public ResponseObject saveSalesPerson(SalesPerson salesPerson) {
        salesPerson = this.salesPersonService.save(salesPerson);
        ResponseObject responseObject = new ResponseObject("Sales Person Saved Successfully", true);
        responseObject.setObject(salesPerson);
        return responseObject;
    }

    @Override
    public ResponseObject updateSalesPerson(SalesPerson salesPerson) {
        ResponseObject responseObject;
        SalesPerson dbSalesPerson = this.salesPersonService.findOne(salesPerson.getSalesPersonSeq());
        if (dbSalesPerson != null) {
            if (!dbSalesPerson.equals(salesPerson)) {
                salesPerson = this.salesPersonService.save(salesPerson);
                responseObject = new ResponseObject("Sales Person Updated Successfully ", true);
                responseObject.setObject(salesPerson);
            } else {
                responseObject = new ResponseObject("No Amendments Found!!", false);
            }
        } else {
            responseObject = new ResponseObject("Sales Person Not Found!!", false);
        }
        return responseObject;
    }

    @Override
    public ResponseObject deleteSalesPerson(Integer salesPersonSeq) {
        SalesPerson dbSalesPerson = this.salesPersonService.findOne(salesPersonSeq);
        dbSalesPerson.setStatus(0);
        dbSalesPerson = this.salesPersonService.save(dbSalesPerson);
        ResponseObject responseObject = new ResponseObject("Sales Person Deleted Successfully", true);
        responseObject.setObject(dbSalesPerson);
        return responseObject;
    }

    @Override
    public List<SalesPerson> searchSalesPerson(String salesPersonCode, String salesPersonName, Integer stakeholderSeq,HttpServletRequest request) {
        List<SalesPerson> salesPersons = null;
        try {
            Integer companyProfileSeq = Integer.parseInt( request.getSession().getAttribute("companyProfileSeq" ).toString());
            BooleanBuilder builder = new BooleanBuilder();
            QSalesPerson qSalesPerson = QSalesPerson.salesPerson;
            if (stakeholderSeq != null) {
                builder.and(qSalesPerson.stakeholderSeq.eq(stakeholderSeq));
            }
            if (!salesPersonCode.isEmpty()) {
                builder.and(qSalesPerson.salesPersonCode.eq(salesPersonCode));
            }
            if (!salesPersonName.isEmpty()) {
                builder.and(qSalesPerson.salesPersonName.eq(salesPersonName));
            }
            builder.and(qSalesPerson.companyProfileSeq.eq(companyProfileSeq));
            salesPersons = (List<SalesPerson>) this.salesPersonService.findAll(builder, EntityGraphUtils.fromName("SalesPerson.default"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return salesPersons;
    }
}
