package com.tmj.tms.finance.zoho.schedule;

import com.tmj.tms.config.datalayer.modal.CompanyProfile;
import com.tmj.tms.config.datalayer.service.CompanyProfileService;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.finance.datalayer.modal.ZohoIntegration;
import com.tmj.tms.finance.zoho.api.ContactsApi;
import com.tmj.tms.finance.zoho.model.Contact;
import com.tmj.tms.finance.zoho.util.ZohoAccessTokenGenerator;
import com.tmj.tms.fleet.datalayer.modal.Vehicle;
import com.tmj.tms.fleet.datalayer.service.VehicleService;
import com.tmj.tms.master.datalayer.modal.Currency;
import com.tmj.tms.master.datalayer.service.CurrencyService;
import com.tmj.tms.utility.YesOrNo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Profile(value = "dev")
public class VehicleIntegration {

    private final CompanyProfileService companyProfileService;
    private final ZohoAccessTokenGenerator zohoAccessTokenGenerator;
    private final ContactsApi contactsApi;
    private final CurrencyService currencyService;
    private final VehicleService vehicleService;

    @Autowired
    public VehicleIntegration(CompanyProfileService companyProfileService,
                              ZohoAccessTokenGenerator zohoAccessTokenGenerator,
                              ContactsApi contactsApi,
                              CurrencyService currencyService,
                              VehicleService vehicleService) {
        this.companyProfileService = companyProfileService;
        this.zohoAccessTokenGenerator = zohoAccessTokenGenerator;
        this.contactsApi = contactsApi;
        this.currencyService = currencyService;
        this.vehicleService = vehicleService;
    }

    @Scheduled(fixedRate = 60000 * 30)
    @Transactional(timeout = 500000000)
    public void insertOrUpdateItem() {
        List<CompanyProfile> companyProfileList = this.companyProfileService.findByStatusNot(MasterDataStatus.DELETED.getStatusSeq());
        for (CompanyProfile companyProfile : companyProfileList) {
            try {
                ZohoIntegration zohoIntegration = this.zohoAccessTokenGenerator.tokenUpdater(companyProfile.getCompanyProfileSeq());
                List<Vehicle> vehicleList = this.vehicleService.findByCompanyProfileSeqAndFinanceIntegrationIsNullAndStakeholderSeqAndStatus(companyProfile.getCompanyProfileSeq(), companyProfile.getDefaultTransporterSeq(),MasterDataStatus.APPROVED.getStatusSeq());
                for (Vehicle vehicle : vehicleList) {
                    try {
                        Contact contact = this.initVehicle(vehicle, companyProfile);
                        this.saveOrUpdate(zohoIntegration, vehicle, contact);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(">>>>>>>>>>Vehicle>>>>>>>>>" + vehicle.getVehicleNo());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Contact initVehicle(Vehicle vehicle, CompanyProfile companyProfile) throws Exception {
        Contact contact = new Contact();
        contact.setContact_name(vehicle.getVehicleNo());
        contact.setCompany_name(companyProfile.getCompanyName());
        contact.setContact_type("customer");
        contact.setLanguage_code("en");
        Currency currency = this.currencyService.findOne(companyProfile.getLocalCurrencySeq());
        contact.setCurrency_code(currency.getCurrencyCode());
        contact.setStatus("active");
        return contact;
    }

    private void saveOrUpdate(ZohoIntegration zohoIntegration, Vehicle vehicle, Contact contact) throws Exception {
        if (vehicle.getFinanceIntegrationKey() == null) {
            contact = this.contactsApi.create(contact, zohoIntegration);
            if (contact != null) {
                vehicle.setFinanceIntegrationKey(contact.getContact_id());
                vehicle.setFinanceIntegration(YesOrNo.Yes.getYesOrNoSeq());
                this.vehicleService.save(vehicle);
            }
        } else {
            this.contactsApi.update(contact, zohoIntegration, vehicle.getFinanceIntegrationKey());
        }
    }

}
