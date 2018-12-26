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
import com.tmj.tms.master.datalayer.modal.Currency;
import com.tmj.tms.master.datalayer.modal.Employee;
import com.tmj.tms.master.datalayer.service.CurrencyService;
import com.tmj.tms.master.datalayer.service.EmployeeService;
import com.tmj.tms.utility.YesOrNo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Profile(value = "dev")
public class EmployeeIntegration {

    private final CompanyProfileService companyProfileService;
    private final EmployeeService employeeService;
    private final ZohoAccessTokenGenerator zohoAccessTokenGenerator;
    private final ContactsApi contactsApi;
    private final CurrencyService currencyService;

    @Autowired
    public EmployeeIntegration(CompanyProfileService companyProfileService,
                               EmployeeService employeeService,
                               ZohoAccessTokenGenerator zohoAccessTokenGenerator,
                               ContactsApi contactsApi,
                               CurrencyService currencyService) {
        this.companyProfileService = companyProfileService;
        this.employeeService = employeeService;
        this.zohoAccessTokenGenerator = zohoAccessTokenGenerator;
        this.contactsApi = contactsApi;
        this.currencyService = currencyService;
    }

    @Scheduled(fixedRate = 60000 * 30)
    @Transactional(timeout = 500000000)
    public void insertOrUpdateItem() {
        List<CompanyProfile> companyProfileList = this.companyProfileService.findByStatusNot(MasterDataStatus.DELETED.getStatusSeq());
        for (CompanyProfile companyProfile : companyProfileList) {
            try {
                ZohoIntegration zohoIntegration = this.zohoAccessTokenGenerator.tokenUpdater(companyProfile.getCompanyProfileSeq());
                List<Employee> employeeList = this.employeeService.findByCompanyProfileSeqAndFinanceIntegrationIsNullAndStakeholderSeqAndStatus(companyProfile.getCompanyProfileSeq(), companyProfile.getDefaultTransporterSeq(),MasterDataStatus.APPROVED.getStatusSeq());
                for (Employee employee : employeeList) {
                    try {
                        Contact employeeforIntegration = this.initEmployee(employee, companyProfile);
                        this.saveOrUpdate(zohoIntegration, employee, employeeforIntegration);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(">>>>>>>>>>Employee>>>>>>>>>" + employee.getEmployeeName());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Contact initEmployee(Employee employee, CompanyProfile companyProfile) throws Exception {
        Contact contact = new Contact();
        contact.setContact_name(employee.getEmployeeName());
        contact.setCompany_name(companyProfile.getCompanyName() + "-" + employee.getEmployeeDesignation().getDesignation());
        contact.setContact_type("vendor");
        contact.setLanguage_code("en");
        Currency currency = this.currencyService.findOne(companyProfile.getLocalCurrencySeq());
        contact.setCurrency_code(currency.getCurrencyCode());
        AddressBook addressBook = employee.getAddressBook();
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
        contact.setStatus("active");
        return contact;
    }

    private void saveOrUpdate(ZohoIntegration zohoIntegration, Employee employee, Contact employeeForIntegration) throws Exception {
        if (employee.getFinanceIntegrationKey() == null) {
            employeeForIntegration = this.contactsApi.create(employeeForIntegration, zohoIntegration);
            if (employeeForIntegration != null) {
                employee.setFinanceIntegrationKey(employeeForIntegration.getContact_id());
                employee.setFinanceIntegration(YesOrNo.Yes.getYesOrNoSeq());
                this.employeeService.save(employee);
            }
        } else {
            this.contactsApi.update(employeeForIntegration, zohoIntegration, employee.getFinanceIntegrationKey());
        }
    }

}
