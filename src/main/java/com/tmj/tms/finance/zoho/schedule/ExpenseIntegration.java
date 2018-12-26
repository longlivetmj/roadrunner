package com.tmj.tms.finance.zoho.schedule;

import com.tmj.tms.config.datalayer.modal.CompanyProfile;
import com.tmj.tms.config.datalayer.service.CompanyProfileService;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.finance.datalayer.modal.ExpenseVoucher;
import com.tmj.tms.finance.datalayer.modal.ExpenseVoucherChargeDetail;
import com.tmj.tms.finance.datalayer.modal.ZohoIntegration;
import com.tmj.tms.finance.datalayer.service.ExpenseVoucherService;
import com.tmj.tms.finance.zoho.api.BillApi;
import com.tmj.tms.finance.zoho.model.Bill;
import com.tmj.tms.finance.zoho.model.BillLineItem;
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
public class ExpenseIntegration {

    private final CompanyProfileService companyProfileService;
    private final ZohoAccessTokenGenerator zohoAccessTokenGenerator;
    private final ExpenseVoucherService expenseVoucherService;
    private final BillApi billApi;

    @Autowired
    public ExpenseIntegration(CompanyProfileService companyProfileService,
                              ZohoAccessTokenGenerator zohoAccessTokenGenerator,
                              ExpenseVoucherService expenseVoucherService,
                              BillApi billApi) {
        this.companyProfileService = companyProfileService;
        this.zohoAccessTokenGenerator = zohoAccessTokenGenerator;
        this.expenseVoucherService = expenseVoucherService;
        this.billApi = billApi;
    }

    @Scheduled(fixedRate = 60000 * 15)
    @Transactional
    public void insertOrUpdateInvoice() {
        DateFormatter dateFormatter = new DateFormatter();
        List<CompanyProfile> companyProfileList = this.companyProfileService.findByStatusNot(MasterDataStatus.DELETED.getStatusSeq());
        for (CompanyProfile companyProfile : companyProfileList) {
            try {
                ZohoIntegration zohoIntegration = this.zohoAccessTokenGenerator.tokenUpdater(companyProfile.getCompanyProfileSeq());
                List<ExpenseVoucher> expenseVoucherList = this.expenseVoucherService.findFirst100ByCompanyProfileSeqAndFinanceIntegrationIsNullAndStatusOrderByExpenseVoucherNo(companyProfile.getCompanyProfileSeq(), MasterDataStatus.APPROVED.getStatusSeq());
                for (ExpenseVoucher expenseVoucher : expenseVoucherList) {
                    try {
                        Bill bill = this.initFromExpenseVoucher(expenseVoucher, dateFormatter);
                        if (expenseVoucher.getFinanceIntegrationKey() == null) {
                            bill = this.billApi.create(bill, zohoIntegration);
                            if (bill != null && bill.getBillNumber().equals(expenseVoucher.getExpenseVoucherNo())) {
                                expenseVoucher.setFinanceIntegrationKey(bill.getBillId());
                                expenseVoucher.setFinanceIntegration(YesOrNo.Yes.getYesOrNoSeq());
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(">>>>>>>>>ExpenseVoucher>>>>>>>>" + expenseVoucher.getExpenseVoucherNo());
                    }
                }
                this.expenseVoucherService.save(expenseVoucherList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Bill initFromExpenseVoucher(ExpenseVoucher expenseVoucher, DateFormatter dateFormatter) throws Exception {
        Bill bill = new Bill();
        bill.setBillNumber(expenseVoucher.getExpenseVoucherNo());
        bill.setVendorId(expenseVoucher.getStakeholder().getFinanceIntegrationKey());
        bill.setDate(dateFormatter.returnSortFormattedDate(expenseVoucher.getCreatedDate()));
        bill.setDueDate(dateFormatter.returnSortFormattedDate(dateFormatter.addMonthsToADate(1, expenseVoucher.getCreatedDate())));
        bill.setReferenceNumber(String.valueOf(expenseVoucher.getReferenceSeq()));
        bill.setTaxTotal(expenseVoucher.getFinalOtherTaxAmount() + expenseVoucher.getFinalVatAmount());
        bill.setSubTotal(expenseVoucher.getFinalWithoutTaxAmount());
        bill.setTotal(expenseVoucher.getFinalTotalAmount());
        bill.setCurrencyCode(expenseVoucher.getCurrency().getCurrencyCode());
        bill.setStatus("draft");
        bill.setPaymentTerms(15);
        bill.setExchangeRate(1);
        List<Tax> taxList = new ArrayList<>();
        if (expenseVoucher.getFinalOtherTaxAmount() > 0) {
            Tax other = new Tax();
            other.setTaxName("OTHER");
            other.setTaxAmount(expenseVoucher.getFinalOtherTaxAmount());
            taxList.add(other);
        }
        if (expenseVoucher.getFinalVatAmount() > 0) {
            Tax vat = new Tax();
            vat.setTaxName("VAT");
            vat.setTaxAmount(expenseVoucher.getFinalOtherTaxAmount());
            taxList.add(vat);
        }
        if (taxList.size() > 0) {
            bill.setTaxes(taxList);
        }
        List<BillLineItem> lineItemList = new ArrayList<>();
        for (ExpenseVoucherChargeDetail expenseVoucherChargeDetail : expenseVoucher.getExpenseVoucherChargeDetailList()) {
            BillLineItem lineItem = new BillLineItem();
            lineItem.setItemId(expenseVoucherChargeDetail.getCharge().getFinanceIntegrationKey());
            lineItem.setQuantity(expenseVoucherChargeDetail.getQuantity());
            lineItem.setRate(expenseVoucherChargeDetail.getAmount() / expenseVoucherChargeDetail.getQuantity());
            lineItem.setItemTotal(expenseVoucherChargeDetail.getAmount());
            lineItemList.add(lineItem);
        }
        bill.setLineItems(lineItemList);
        return bill;
    }

}
