package com.tmj.tms.finance.zoho.schedule;

import com.tmj.tms.config.datalayer.modal.CompanyProfile;
import com.tmj.tms.config.datalayer.service.CompanyProfileService;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.finance.datalayer.modal.ZohoIntegration;
import com.tmj.tms.finance.zoho.api.ContactsApi;
import com.tmj.tms.finance.zoho.model.Address;
import com.tmj.tms.finance.zoho.model.Contact;
import com.tmj.tms.finance.zoho.util.ZohoAccessTokenGenerator;
import com.tmj.tms.master.datalayer.modal.AddressBook;
import com.tmj.tms.master.datalayer.modal.Stakeholder;
import com.tmj.tms.master.datalayer.modal.StakeholderType;
import com.tmj.tms.master.datalayer.service.StakeholderService;
import com.tmj.tms.master.datalayer.service.StakeholderTypeService;
import com.tmj.tms.utility.YesOrNo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Profile(value = "dev")
public class StakeholderIntegration {

    private final CompanyProfileService companyProfileService;
    private final StakeholderService stakeholderService;
    private final StakeholderTypeService stakeholderTypeService;
    private final ZohoAccessTokenGenerator zohoAccessTokenGenerator;
    private final ContactsApi contactsApi;

    @Autowired
    public StakeholderIntegration(CompanyProfileService companyProfileService,
                                  StakeholderService stakeholderService,
                                  StakeholderTypeService stakeholderTypeService,
                                  ZohoAccessTokenGenerator zohoAccessTokenGenerator,
                                  ContactsApi contactsApi) {
        this.companyProfileService = companyProfileService;
        this.stakeholderService = stakeholderService;
        this.stakeholderTypeService = stakeholderTypeService;
        this.zohoAccessTokenGenerator = zohoAccessTokenGenerator;
        this.contactsApi = contactsApi;
    }

    @Scheduled(fixedRate = 60000 * 30)
    @Transactional(timeout = 500000000)
    public void insertOrUpdateStakeholder() {
        List<CompanyProfile> companyProfileList = this.companyProfileService.findByStatusNot(MasterDataStatus.DELETED.getStatusSeq());
        StakeholderType customer = this.stakeholderTypeService.findByStakeholderTypeCodeAndStatus("CUSTOMER", MasterDataStatus.APPROVED.getStatusSeq());
        StakeholderType transporter = this.stakeholderTypeService.findByStakeholderTypeCodeAndStatus("TRANSPORT_COMPANY", MasterDataStatus.APPROVED.getStatusSeq());
        StakeholderType supplier = this.stakeholderTypeService.findByStakeholderTypeCodeAndStatus("SUPPLIER", MasterDataStatus.APPROVED.getStatusSeq());
        for (CompanyProfile companyProfile : companyProfileList) {
            try {
                ZohoIntegration zohoIntegration = this.zohoAccessTokenGenerator.tokenUpdater(companyProfile.getCompanyProfileSeq());
                List<Stakeholder> customerList = this.stakeholderService.getStakeholdersForFinanceIntegration(customer.getStakeholderTypeSeq(), companyProfile.getCompanyProfileSeq(), MasterDataStatus.APPROVED.getStatusSeq());
                for (Stakeholder stakeholder : customerList) {
                    try {
                        Contact contact = this.initContact(stakeholder, "customer");
                        this.saveOrUpdate(zohoIntegration, stakeholder, contact);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(">>>>Customer>>>>>" + stakeholder.getStakeholderSeq());
                    }
                }

                List<Stakeholder> supplierList = this.stakeholderService.getStakeholdersForFinanceIntegration(supplier.getStakeholderTypeSeq(), companyProfile.getCompanyProfileSeq(), MasterDataStatus.APPROVED.getStatusSeq());
                for (Stakeholder stakeholder : supplierList) {
                    try {
                        Contact contact = this.initContact(stakeholder, "vendor");
                        this.saveOrUpdate(zohoIntegration, stakeholder, contact);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(">>>>Supplier>>>>>" + stakeholder.getStakeholderSeq());
                    }
                }

                List<Stakeholder> transporterList = this.stakeholderService.getStakeholdersForFinanceIntegration(transporter.getStakeholderTypeSeq(), companyProfile.getCompanyProfileSeq(), MasterDataStatus.APPROVED.getStatusSeq());
                for (Stakeholder stakeholder : transporterList) {
                    try {
                        Contact contact = this.initContact(stakeholder, "vendor");
                        this.saveOrUpdate(zohoIntegration, stakeholder, contact);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(">>>>transporterList>>>>>" + stakeholder.getStakeholderSeq());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Contact initContact(Stakeholder stakeholder, String contactType) throws Exception {
        Contact contact = new Contact();
        contact.setContact_name(stakeholder.getStakeholderName());
        contact.setCompany_name(stakeholder.getStakeholderName());
        contact.setContact_type(contactType);
        contact.setLanguage_code("en");
        contact.setCurrency_code(stakeholder.getCurrency().getCurrencyCode());
        AddressBook addressBook = stakeholder.getAddressBook();
        if (addressBook != null) {
            Address address = new Address();
            if (addressBook.getCity() != null && addressBook.getCity().trim().length() > 0) {
                address.setCity(addressBook.getCity());
            }
            if (addressBook.getAddress1() != null && addressBook.getAddress1().trim().length() > 0) {
                address.setAddress(addressBook.getAddress1());
            }
            address.setCountry(addressBook.getCountry().getCountryName());
            contact.setBilling_address(address);
            contact.setShipping_address(address);
            if (addressBook.getEmail() != null && addressBook.getEmail().trim().length() > 0) {
                contact.setEmail(addressBook.getEmail());
            }
            if (addressBook.getTelephone() != null && addressBook.getTelephone().trim().length() > 0) {
                contact.setPhone(addressBook.getTelephone());
            }
            if (addressBook.getMobile() != null && addressBook.getMobile().trim().length() > 0) {
                contact.setMobile(addressBook.getMobile());
            }
        }
        if (stakeholder.getBeneficiaryName() != null && stakeholder.getBeneficiaryName().trim().length() > 0) {
            contact.setFirst_name(stakeholder.getBeneficiaryName());
        }
        if (stakeholder.getTaxRegNo() != null && stakeholder.getTaxRegNo().trim().length() > 0) {
            contact.setTax_id(stakeholder.getTaxRegNo());
        }
        contact.setStatus("active");
        return contact;
    }

    private void saveOrUpdate(ZohoIntegration zohoIntegration, Stakeholder stakeholder, Contact contact) throws Exception {
        if (stakeholder.getFinanceIntegrationKey() == null) {
            contact = this.contactsApi.create(contact, zohoIntegration);
            if (contact != null) {
                stakeholder = this.stakeholderService.findOne(stakeholder.getStakeholderSeq());
                stakeholder.setFinanceIntegrationKey(contact.getContact_id());
                stakeholder.setFinanceIntegration(YesOrNo.Yes.getYesOrNoSeq());
                this.stakeholderService.save(stakeholder);
            }
        } else {
            contactsApi.update(contact, zohoIntegration, stakeholder.getFinanceIntegrationKey());
        }
    }

}
