package com.tmj.tms.finance.zoho.schedule;

import com.tmj.tms.config.datalayer.modal.CompanyProfile;
import com.tmj.tms.config.datalayer.service.CompanyProfileService;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.finance.datalayer.modal.LocalInvoice;
import com.tmj.tms.finance.datalayer.modal.LocalInvoiceChargeDetail;
import com.tmj.tms.finance.datalayer.modal.ZohoIntegration;
import com.tmj.tms.finance.datalayer.service.LocalInvoiceService;
import com.tmj.tms.finance.zoho.api.InvoiceApi;
import com.tmj.tms.finance.zoho.model.Invoice;
import com.tmj.tms.finance.zoho.model.LineItem;
import com.tmj.tms.finance.zoho.model.LineTag;
import com.tmj.tms.finance.zoho.model.Tax;
import com.tmj.tms.finance.zoho.util.ZohoAccessTokenGenerator;
import com.tmj.tms.fleet.datalayer.modal.Vehicle;
import com.tmj.tms.fleet.datalayer.service.VehicleService;
import com.tmj.tms.transport.datalayer.modal.TransportBooking;
import com.tmj.tms.transport.datalayer.service.TransportBookingService;
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
@Profile(value = "prod")
public class InvoiceIntegration {

    private final CompanyProfileService companyProfileService;
    private final ZohoAccessTokenGenerator zohoAccessTokenGenerator;
    private final LocalInvoiceService localInvoiceService;
    private final InvoiceApi invoiceApi;
    private final TransportBookingService transportBookingService;
    private final VehicleService vehicleService;

    @Autowired
    public InvoiceIntegration(CompanyProfileService companyProfileService,
                              ZohoAccessTokenGenerator zohoAccessTokenGenerator,
                              LocalInvoiceService localInvoiceService,
                              InvoiceApi invoiceApi,
                              TransportBookingService transportBookingService,
                              VehicleService vehicleService) {
        this.companyProfileService = companyProfileService;
        this.zohoAccessTokenGenerator = zohoAccessTokenGenerator;
        this.localInvoiceService = localInvoiceService;
        this.invoiceApi = invoiceApi;
        this.transportBookingService = transportBookingService;
        this.vehicleService = vehicleService;
    }

    @Scheduled(fixedRate = 60000 * 30)
    @Transactional(timeout = 500000000)
    public void insertOrUpdateInvoice() {
        DateFormatter dateFormatter = new DateFormatter();
        List<CompanyProfile> companyProfileList = this.companyProfileService.findByStatusNot(MasterDataStatus.DELETED.getStatusSeq());
        for (CompanyProfile companyProfile : companyProfileList) {
            try {
                ZohoIntegration zohoIntegration = this.zohoAccessTokenGenerator.tokenUpdater(companyProfile.getCompanyProfileSeq());
                List<LocalInvoice> localInvoiceList = this.localInvoiceService.findFirst100ByCompanyProfileSeqAndFinanceIntegrationIsNullAndStatusOrderByLocalInvoiceNo(companyProfile.getCompanyProfileSeq(), MasterDataStatus.APPROVED.getStatusSeq());
                for (LocalInvoice localInvoice : localInvoiceList) {
                    try {
                        Invoice invoice = this.initFromLocalInvoice(localInvoice, dateFormatter);
                        if (localInvoice.getFinanceIntegrationKey() == null) {
                            invoice = this.invoiceApi.create(invoice, zohoIntegration);
                            if (invoice != null && invoice.getInvoiceId() != null && invoice.getInvoiceNumber().equals(localInvoice.getLocalInvoiceNo())) {
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
                System.out.println(">>>>>>>>>LocalInvoice error>>>>>>>>");
            }
        }
    }

    private Invoice initFromLocalInvoice(LocalInvoice localInvoice, DateFormatter dateFormatter) throws Exception {
        TransportBooking transportBooking = this.transportBookingService.findOne(localInvoice.getReferenceSeq());
        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber(localInvoice.getLocalInvoiceNo() + " (" + transportBooking.getBookingNo() + ")");
        invoice.setCustomerId(localInvoice.getStakeholder().getFinanceIntegrationKey());
        invoice.setDate(dateFormatter.returnSortFormattedDate(localInvoice.getCreatedDate()));
        invoice.setDueDate(dateFormatter.returnSortFormattedDate(dateFormatter.addMonthsToADate(1, localInvoice.getCreatedDate())));
        Vehicle vehicle = this.vehicleService.findOne(transportBooking.getTransportBookingVehicleList().get(0).getVehicleSeq());
        invoice.setReferenceNumber(transportBooking.getCustomerReferenceNo() + " (" + vehicle.getVehicleNo() + ")");
        invoice.setTaxTotal(localInvoice.getFinalOtherTaxAmount() + localInvoice.getFinalVatAmount());
        invoice.setSubTotal(localInvoice.getFinalWithoutTaxAmount());
        invoice.setTotal(localInvoice.getFinalTotalAmount());
        invoice.setCurrencyCode(localInvoice.getCurrency().getCurrencyCode());
        invoice.setCustomerName(localInvoice.getStakeholder().getStakeholderName());
        invoice.setStatus("sent");
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
            if (vehicle.getFinanceIntegration() != null && vehicle.getFinanceIntegration().equals(1)) {
                if (localInvoiceChargeDetail.getCharge().getChargeName().equals("TRANSPORT CHARGE") ||
                        localInvoiceChargeDetail.getCharge().getChargeName().equals("DEMURRAGE") ||
                        localInvoiceChargeDetail.getCharge().getChargeName().equals("NIGHT PARK")) {
                    List<LineTag> lineTags = new ArrayList<>();
                    LineTag lineTag = new LineTag();
                    lineTag.setTagId("1281355000000000333");
                    lineTag.setTagOptionId(String.valueOf(vehicle.getFinanceIntegrationKey()));
                    lineTags.add(lineTag);
                    lineItem.setTags(lineTags);
                }
            }
            lineItemList.add(lineItem);
        }
        invoice.setLineItems(lineItemList);
        return invoice;
    }

}
