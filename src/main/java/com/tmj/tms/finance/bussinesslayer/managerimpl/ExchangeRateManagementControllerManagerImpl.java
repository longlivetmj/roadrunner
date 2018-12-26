package com.tmj.tms.finance.bussinesslayer.managerimpl;

import com.querydsl.core.BooleanBuilder;
import com.tmj.tms.config.datalayer.service.ModuleService;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.finance.bussinesslayer.manager.ExchangeRateManagementControllerManager;
import com.tmj.tms.finance.datalayer.modal.ExchangeRate;
import com.tmj.tms.finance.datalayer.modal.ExchangeRateDetail;
import com.tmj.tms.finance.datalayer.modal.QExchangeRate;
import com.tmj.tms.finance.datalayer.modal.auxiliary.ExchangeRateSearchAux;
import com.tmj.tms.finance.datalayer.service.ExchangeRateDetailService;
import com.tmj.tms.finance.datalayer.service.ExchangeRateService;
import com.tmj.tms.finance.utility.ExchangeRateSourceType;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExchangeRateManagementControllerManagerImpl implements ExchangeRateManagementControllerManager {

    private final HttpSession httpSession;
    private final ModuleService moduleService;
    private final ExchangeRateService exchangeRateService;
    private final ExchangeRateDetailService exchangeRateDetailService;

    @Autowired
    public ExchangeRateManagementControllerManagerImpl(HttpSession httpSession,
                                                       ModuleService moduleService,
                                                       ExchangeRateService exchangeRateService,
                                                       ExchangeRateDetailService exchangeRateDetailService) {
        this.httpSession = httpSession;
        this.moduleService = moduleService;
        this.exchangeRateService = exchangeRateService;
        this.exchangeRateDetailService = exchangeRateDetailService;
    }

    @Override
    public ResponseObject saveExchangeRate(ExchangeRate exchangeRate) {
        ResponseObject responseObject = new ResponseObject();
        Integer companyProfileSeq = Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString());
        List<ExchangeRateDetail> tempRateDetailList = exchangeRate.getExchangeRateDetails();
        Integer transport = this.moduleService.findByModuleCode("TRANSPORT").getModuleSeq();
        List<Integer> currencySeqList = tempRateDetailList.stream().map(ExchangeRateDetail::getCurrencySeq).collect(Collectors.toList());
        Set<Integer> duplicatedCurrencyRemovedSet = new HashSet<>();
        Set<Integer> duplicatedCurrencySet = currencySeqList.stream().filter(n -> !duplicatedCurrencyRemovedSet.add(n)).collect(Collectors.toSet());
        if (currencySeqList.contains(exchangeRate.getBaseCurrencySeq())) {
            responseObject = new ResponseObject("Can not Duplicate Base Currency & Exchange Rate Detail Currency", false);
        } else if (duplicatedCurrencySet.size() > 0) {
            responseObject = new ResponseObject("Can not Duplicate in Exchange Rate Detail Currency", false);
        } else {
            exchangeRate.setCompanyProfileSeq(companyProfileSeq);
            for (ExchangeRateDetail exchangeRateDetail : tempRateDetailList) {
                exchangeRateDetail.setStatus(exchangeRate.getStatus());
            }
            if (exchangeRate.getModuleSeq().equals(transport)) {
                if (exchangeRate.getExchangeRateSourceType() != null && exchangeRate.getExchangeRateSourceType().equals(ExchangeRateSourceType.BANK.getExchangeRateSourceType())) {
                    exchangeRate.setBankSeq(exchangeRate.getBankSeq());
                }
                exchangeRate = this.exchangeRateService.save(exchangeRate);
                responseObject = new ResponseObject("Exchange Rate Saved Successfully", true);
                responseObject.setObject(exchangeRate);
            }
        }
        return responseObject;
    }

    @Override
    public ResponseObject updateExchangeRate(ExchangeRate exchangeRate) {
        ResponseObject responseObject = new ResponseObject();
        List<ExchangeRateDetail> frontEndExchangeRateDetailList = exchangeRate.getExchangeRateDetails();
        List<ExchangeRateDetail> newExchangeRateDetailSet = new ArrayList<>();
        List<Integer> currencySeqList = frontEndExchangeRateDetailList.stream().map(ExchangeRateDetail::getCurrencySeq).collect(Collectors.toList());
        Integer transport = this.moduleService.findByModuleCode("TRANSPORT").getModuleSeq();
        Integer companyProfileSeq = Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString());

        Set<Integer> duplicatedCurrencyRemovedSet = new HashSet<>();
        Set<Integer> duplicatedCurrencySet = currencySeqList.stream().filter(n -> !duplicatedCurrencyRemovedSet.add(n)).collect(Collectors.toSet());
        ExchangeRate dbExchangeRate = this.exchangeRateService.findOne(exchangeRate.getExchangeRateSeq());
        List<ExchangeRateDetail> dbExchangeRateDetailList = this.exchangeRateDetailService.findByExchangeRateSeq(dbExchangeRate.getExchangeRateSeq());

        if (currencySeqList.contains(exchangeRate.getBaseCurrencySeq())) {
            responseObject = new ResponseObject("Can not Duplicate Base Currency & Exchange Rate Detail Currency", false);
        } else if (duplicatedCurrencySet.size() > 0) {
            responseObject = new ResponseObject("Can not Duplicate in Exchange Rate Detail Currency", false);
        } else {
            dbExchangeRate.setBaseCurrencySeq(exchangeRate.getBaseCurrencySeq());
            dbExchangeRate.setCompanyProfileSeq(companyProfileSeq);
            dbExchangeRate.setStatus(exchangeRate.getStatus());

            for (ExchangeRateDetail exchangeRateDetail : frontEndExchangeRateDetailList) {
                for (ExchangeRateDetail dbRateDetail : dbExchangeRateDetailList) {
                    if (Objects.equals(exchangeRateDetail.getExchangeRateDetailSeq(), dbRateDetail.getExchangeRateDetailSeq())) {
                        exchangeRateDetail.setStatus(exchangeRate.getStatus());
                        newExchangeRateDetailSet.add(exchangeRateDetail);
                        dbExchangeRate.setExchangeRateDetails(newExchangeRateDetailSet);
                    } else {
                        exchangeRateDetail.setStatus(exchangeRate.getStatus());
                        newExchangeRateDetailSet.add(exchangeRateDetail);
                        dbExchangeRate.setExchangeRateDetails(newExchangeRateDetailSet);
                    }
                }
            }
            if (dbExchangeRate.getModuleSeq().equals(transport)) {
                if (dbExchangeRate.getExchangeRateSourceType() != null && dbExchangeRate.getExchangeRateSourceType().equals(ExchangeRateSourceType.BANK.getExchangeRateSourceType())) {
                    dbExchangeRate.setBankSeq(exchangeRate.getBankSeq());
                }
                dbExchangeRate.setEffectiveFrom(exchangeRate.getEffectiveFrom());
                dbExchangeRate.setEffectiveTo(exchangeRate.getEffectiveTo());
                dbExchangeRate = this.exchangeRateService.save(dbExchangeRate);
                responseObject = new ResponseObject("Exchange Rate Updated Successfully", true);
                responseObject.setObject(dbExchangeRate);

            }
        }
        return responseObject;
    }

    @Override
    public ResponseObject deleteExchangeRate(Integer exchangeRateSeq) {
        ResponseObject responseObject;
        ExchangeRate dbExchangeRate = this.exchangeRateService.findOne(exchangeRateSeq);
        dbExchangeRate.setStatus(MasterDataStatus.DELETED.getStatusSeq());
        dbExchangeRate = this.exchangeRateService.save(dbExchangeRate);
        responseObject = new ResponseObject("Exchange Rate Deleted Successfully", true);
        responseObject.setObject(dbExchangeRate);
        return responseObject;
    }

    @Override
    public List<ExchangeRate> searchExchangeRate(ExchangeRateSearchAux exchangeRateSearchAux) {
        List<ExchangeRate> exchangeRateList = new ArrayList<>();
        QExchangeRate qExchangeRate = QExchangeRate.exchangeRate;
        BooleanBuilder builder = new BooleanBuilder();
        Integer companyProfileSeq = Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString());
        try {
            List<Integer> statusSeqList = MasterDataStatus.getStatusListForViewing("ROLE_finance@exchangeRateManagement_VIEW-DELETE");
            builder.and(qExchangeRate.companyProfileSeq.eq(companyProfileSeq));
            if (exchangeRateSearchAux.getBaseCurrencySeq() != null) {
                builder.and(qExchangeRate.baseCurrencySeq.eq(exchangeRateSearchAux.getBaseCurrencySeq()));
            }
            if (exchangeRateSearchAux.getStatus() != null) {
                builder.and(qExchangeRate.status.eq(exchangeRateSearchAux.getStatus()));
            } else {
                builder.and(qExchangeRate.status.in(statusSeqList));
            }
            if (exchangeRateSearchAux.getEffectiveFrom() != null) {
                builder.and(qExchangeRate.effectiveFrom.eq(exchangeRateSearchAux.getEffectiveFrom()));
            }
            if (exchangeRateSearchAux.getExchangeRateSeq() != null) {
                builder.and(qExchangeRate.exchangeRateSeq.eq(exchangeRateSearchAux.getExchangeRateSeq()));
            }
            if (exchangeRateSearchAux.getModuleSeq() != null) {
                builder.and(qExchangeRate.moduleSeq.eq(exchangeRateSearchAux.getModuleSeq()));
            }

            exchangeRateList = (List<ExchangeRate>) this.exchangeRateService.findAll(builder);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exchangeRateList;
    }
}
