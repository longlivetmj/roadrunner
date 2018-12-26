package com.tmj.tms.finance.zoho.schedule;

import com.tmj.tms.config.datalayer.modal.CompanyProfile;
import com.tmj.tms.config.datalayer.service.CompanyProfileService;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.finance.datalayer.modal.*;
import com.tmj.tms.finance.datalayer.service.BulkInvoiceService;
import com.tmj.tms.finance.datalayer.service.LocalInvoiceService;
import com.tmj.tms.finance.zoho.api.InvoiceApi;
import com.tmj.tms.finance.zoho.model.Invoice;
import com.tmj.tms.finance.zoho.model.LineItem;
import com.tmj.tms.finance.zoho.model.Tax;
import com.tmj.tms.finance.zoho.util.ZohoAccessTokenGenerator;
import com.tmj.tms.utility.DateFormatter;
import com.tmj.tms.utility.YesOrNo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Profile(value = "disabled")
public class InvoiceIntegration {

    private final CompanyProfileService companyProfileService;
    private final ZohoAccessTokenGenerator zohoAccessTokenGenerator;
    private final LocalInvoiceService localInvoiceService;
    private final BulkInvoiceService bulkInvoiceService;
    private final InvoiceApi invoiceApi;

    @Autowired
    public InvoiceIntegration(CompanyProfileService companyProfileService,
                              ZohoAccessTokenGenerator zohoAccessTokenGenerator,
                              LocalInvoiceService localInvoiceService,
                              BulkInvoiceService bulkInvoiceService,
                              InvoiceApi invoiceApi) {
        this.companyProfileService = companyProfileService;
        this.zohoAccessTokenGenerator = zohoAccessTokenGenerator;
        this.localInvoiceService = localInvoiceService;
        this.bulkInvoiceService = bulkInvoiceService;
        this.invoiceApi = invoiceApi;
    }

    @Scheduled(fixedRate = 60000 * 15)
    @Transactional
    public void insertOrUpdateInvoice() {
        DateFormatter dateFormatter = new DateFormatter();
        List<CompanyProfile> companyProfileList = this.companyProfileService.findByStatusNot(MasterDataStatus.DELETED.getStatusSeq());
        for (CompanyProfile companyProfile : companyProfileList) {
            try {
                ZohoIntegration zohoIntegration = this.zohoAccessTokenGenerator.tokenUpdater(companyProfile.getCompanyProfileSeq());
                List<BulkInvoice> bulkInvoiceList = this.bulkInvoiceService.findByCompanyProfileSeqAndFinanceIntegrationIsNullAndStatus(companyProfile.getCompanyProfileSeq(), MasterDataStatus.APPROVED.getStatusSeq());
                for (BulkInvoice bulkInvoice : bulkInvoiceList) {
                    try {
                        Invoice invoice = this.initFromBulkInvoice(bulkInvoice, dateFormatter);
                        if (bulkInvoice.getFinanceIntegrationKey() == null) {
                            invoice = this.invoiceApi.create(invoice, zohoIntegration);
                            if (invoice != null) {
                                bulkInvoice.setFinanceIntegrationKey(invoice.getInvoiceId());
                                bulkInvoice.setFinanceIntegration(YesOrNo.Yes.getYesOrNoSeq());
                                List<BulkInvoiceDetail> localInvoiceList = bulkInvoice.getBulkInvoiceDetailList();
                                for (BulkInvoiceDetail bulkInvoiceDetail : localInvoiceList) {
                                    LocalInvoice localInvoice = this.localInvoiceService.findOne(bulkInvoiceDetail.getLocalInvoiceSeq());
                                    localInvoice.setFinanceIntegrationKey(invoice.getInvoiceId());
                                    localInvoice.setFinanceIntegration(YesOrNo.Yes.getYesOrNoSeq());
                                    this.localInvoiceService.save(localInvoice);
                                }
                                this.bulkInvoiceService.save(bulkInvoice);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(">>>>>>>>BulkInvoice>>>>>>>>" + bulkInvoice.getBulkInvoiceNo());
                    }
                }

                List<LocalInvoice> localInvoiceList = this.localInvoiceService.findFirst100ByCompanyProfileSeqAndFinanceIntegrationIsNullAndStatusOrderByLocalInvoiceNo(companyProfile.getCompanyProfileSeq(), MasterDataStatus.APPROVED.getStatusSeq());
                for (LocalInvoice localInvoice : localInvoiceList) {
                    try {
                        Invoice invoice = this.initFromLocalInvoice(localInvoice, dateFormatter);
                        if (localInvoice.getFinanceIntegrationKey() == null) {
                            invoice = this.invoiceApi.create(invoice, zohoIntegration);
                            if (invoice != null && invoice.getInvoiceNumber().equals(localInvoice.getLocalInvoiceNo())) {
                                localInvoice.setFinanceIntegrationKey(invoice.getInvoiceId());
                                localInvoice.setFinanceIntegration(YesOrNo.Yes.getYesOrNoSeq());
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(">>>>>>>>>LocalInvoice>>>>>>>>" + localInvoice.getLocalInvoiceNo());
                    }
                }
                this.localInvoiceService.save(localInvoiceList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Invoice initFromLocalInvoice(LocalInvoice localInvoice, DateFormatter dateFormatter) throws Exception {
        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber(localInvoice.getLocalInvoiceNo());
        invoice.setCustomerId(localInvoice.getStakeholder().getFinanceIntegrationKey());
        invoice.setDate(dateFormatter.returnSortFormattedDate(localInvoice.getCreatedDate()));
        invoice.setDueDate(dateFormatter.returnSortFormattedDate(dateFormatter.addMonthsToADate(1, localInvoice.getCreatedDate())));
        invoice.setReferenceNumber(String.valueOf(localInvoice.getReferenceSeq()));
        invoice.setTaxTotal(localInvoice.getFinalOtherTaxAmount() + localInvoice.getFinalVatAmount());
        invoice.setSubTotal(localInvoice.getFinalWithoutTaxAmount());
        invoice.setTotal(localInvoice.getFinalTotalAmount());
        invoice.setCurrencyCode(localInvoice.getCurrency().getCurrencyCode());
        invoice.setCustomerName(localInvoice.getStakeholder().getStakeholderName());
        invoice.setStatus("draft");
        invoice.setPaymentTerms(15);
        invoice.setExchangeRate(1);
        List<Tax> taxList = new ArrayList<>();
        if (localInvoice.getFinalOtherTaxAmount() > 0) {
            Tax other = new Tax();
            other.setTaxName("OTHER");
            other.setTaxAmount(localInvoice.getFinalOtherTaxAmount());
            taxList.add(other);
        }
        if (localInvoice.getFinalVatAmount() > 0) {
            Tax vat = new Tax();
            vat.setTaxName("VAT");
            vat.setTaxAmount(localInvoice.getFinalOtherTaxAmount());
            taxList.add(vat);
        }
        if (taxList.size() > 0) {
            invoice.setTaxes(taxList);
        }
        List<LineItem> lineItemList = new ArrayList<>();
        for (LocalInvoiceChargeDetail localInvoiceChargeDetail : localInvoice.getLocalInvoiceChargeDetailList()) {
            LineItem lineItem = new LineItem();
            lineItem.setItemId(localInvoiceChargeDetail.getCharge().getFinanceIntegrationKey());
            lineItem.setName(localInvoiceChargeDetail.getCharge().getChargeName());
            lineItem.setQuantity(localInvoiceChargeDetail.getQuantity());
            lineItem.setRate(localInvoiceChargeDetail.getAmount() / localInvoiceChargeDetail.getQuantity());
            lineItem.setItemTotal(localInvoiceChargeDetail.getAmount());
            lineItemList.add(lineItem);
        }
        invoice.setLineItems(lineItemList);
        return invoice;
    }


    private Invoice initFromBulkInvoice(BulkInvoice bulkInvoice, DateFormatter dateFormatter) throws Exception {
        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber(bulkInvoice.getBulkInvoiceNo());
        invoice.setCustomerId(bulkInvoice.getStakeholder().getFinanceIntegrationKey());
        invoice.setDate(dateFormatter.returnSortFormattedDate(bulkInvoice.getCreatedDate()));
        invoice.setDueDate(dateFormatter.returnSortFormattedDate(dateFormatter.addMonthsToADate(1, bulkInvoice.getCreatedDate())));
        invoice.setReferenceNumber(String.valueOf(bulkInvoice.getVehicle().getVehicleNo()));
        invoice.setTaxTotal(bulkInvoice.getFinalOtherTaxAmount() + bulkInvoice.getFinalVatAmount());
        invoice.setSubTotal(bulkInvoice.getFinalWithoutTaxAmount());
        invoice.setTotal(bulkInvoice.getFinalTotalAmount());
        invoice.setCurrencyCode(bulkInvoice.getCurrency().getCurrencyCode());
        invoice.setCustomerName(bulkInvoice.getStakeholder().getStakeholderName());
        invoice.setStatus("draft");
        invoice.setPaymentTerms(15);
        invoice.setExchangeRate(1);
        List<Tax> taxList = new ArrayList<>();
        if (bulkInvoice.getFinalOtherTaxAmount() > 0) {
            Tax other = new Tax();
            other.setTaxName("OTHER");
            other.setTaxAmount(bulkInvoice.getFinalOtherTaxAmount());
            taxList.add(other);
        }
        if (bulkInvoice.getFinalVatAmount() > 0) {
            Tax vat = new Tax();
            vat.setTaxName("VAT");
            vat.setTaxAmount(bulkInvoice.getFinalOtherTaxAmount());
            taxList.add(vat);
        }
        if (taxList.size() > 0) {
            invoice.setTaxes(taxList);
        }
        List<LineItem> lineItemList = new ArrayList<>();
        for (BulkInvoiceChargeDetail bulkInvoiceChargeDetail : bulkInvoice.getBulkInvoiceChargeDetailList()) {
            LineItem lineItem = new LineItem();
            lineItem.setItemId(bulkInvoiceChargeDetail.getCharge().getFinanceIntegrationKey());
            lineItem.setName(bulkInvoiceChargeDetail.getCharge().getChargeName());
            lineItem.setQuantity(bulkInvoiceChargeDetail.getQuantity());
            lineItem.setRate(bulkInvoiceChargeDetail.getAmount() / bulkInvoiceChargeDetail.getQuantity());
            lineItem.setItemTotal(bulkInvoiceChargeDetail.getAmount());
            lineItemList.add(lineItem);
        }
        invoice.setLineItems(lineItemList);
        return invoice;
    }

}
