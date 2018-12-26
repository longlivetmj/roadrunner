package com.tmj.tms.finance.utility;

import com.tmj.tms.config.datalayer.modal.Department;
import com.tmj.tms.config.datalayer.service.DepartmentService;
import com.tmj.tms.finance.datalayer.modal.LocalInvoiceNoConfig;
import com.tmj.tms.finance.datalayer.service.LocalInvoiceNoConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FinancialNumberGenerator {
    private final LocalInvoiceNoConfigService localInvoiceNoConfigService;
    private final DepartmentService departmentService;

    @Autowired
    public FinancialNumberGenerator(LocalInvoiceNoConfigService localInvoiceNoConfigService,
                                    DepartmentService departmentService) {
        this.localInvoiceNoConfigService = localInvoiceNoConfigService;
        this.departmentService = departmentService;
    }

    public String generateLocalInvoiceNo(Integer companyProfileSeq, Integer departmentSeq, String letterCode) {
        Department department = this.departmentService.findOne(departmentSeq);
        LocalInvoiceNoConfig invoiceNoConfig = this.localInvoiceNoConfigService.findByCompanyProfileSeqAndDepartmentSeqAndLetterCode(companyProfileSeq, departmentSeq, letterCode);
        if (invoiceNoConfig == null) {
            this.increaseLocalInvoiceNo(companyProfileSeq, departmentSeq, letterCode);
            invoiceNoConfig = this.localInvoiceNoConfigService.findByCompanyProfileSeqAndDepartmentSeqAndLetterCode(companyProfileSeq, departmentSeq, letterCode);
        }
        String digitFormatting = String.format("%06d", invoiceNoConfig.getNextLocalInvoiceNo());
        return department.getPrefix() + "-" + letterCode + "-" + digitFormatting;
    }

    public String generateExpenseVoucherNo(Integer companyProfileSeq, Integer departmentSeq, String letterCode) {
        Department department = this.departmentService.findOne(departmentSeq);
        LocalInvoiceNoConfig invoiceNoConfig = this.localInvoiceNoConfigService.findByCompanyProfileSeqAndDepartmentSeqAndLetterCode(companyProfileSeq, departmentSeq, letterCode);
        this.increaseExpenseVoucherNo(companyProfileSeq, departmentSeq, letterCode);
        if (invoiceNoConfig == null) {
            this.increaseExpenseVoucherNo(companyProfileSeq, departmentSeq, letterCode);
            invoiceNoConfig = this.localInvoiceNoConfigService.findByCompanyProfileSeqAndDepartmentSeqAndLetterCode(companyProfileSeq, departmentSeq, letterCode);
        }
        String digitFormatting = String.format("%06d", invoiceNoConfig.getNextExpenseVoucherNo());
        return department.getPrefix() + "-" + letterCode + "-" + digitFormatting;

    }

    public void increaseLocalInvoiceNo(Integer companyProfileSeq, Integer departmentSeq, String letterCode) {
        LocalInvoiceNoConfig localInvoiceNoConfig = this.localInvoiceNoConfigService.findByCompanyProfileSeqAndDepartmentSeqAndLetterCode(companyProfileSeq, departmentSeq, letterCode);
        if (localInvoiceNoConfig == null) {
            LocalInvoiceNoConfig newInvoiceNoConfig = new LocalInvoiceNoConfig();
            newInvoiceNoConfig.setCompanyProfileSeq(companyProfileSeq);
            newInvoiceNoConfig.setDepartmentSeq(departmentSeq);
            newInvoiceNoConfig.setNextLocalInvoiceNo(1);
            newInvoiceNoConfig.setLetterCode(letterCode);
            newInvoiceNoConfig.setStatus(1);
            this.localInvoiceNoConfigService.save(newInvoiceNoConfig);
        } else {
            localInvoiceNoConfig.setNextLocalInvoiceNo(localInvoiceNoConfig.getNextLocalInvoiceNo() + 1);
            this.localInvoiceNoConfigService.save(localInvoiceNoConfig);
        }
    }

    public void increaseExpenseVoucherNo(Integer companyProfileSeq, Integer departmentSeq, String letterCode) {
        LocalInvoiceNoConfig localInvoiceNoConfig = this.localInvoiceNoConfigService.findByCompanyProfileSeqAndDepartmentSeqAndLetterCode(companyProfileSeq, departmentSeq, letterCode);
        if (localInvoiceNoConfig == null) {
            LocalInvoiceNoConfig newInvoiceNoConfig = new LocalInvoiceNoConfig();
            newInvoiceNoConfig.setCompanyProfileSeq(companyProfileSeq);
            newInvoiceNoConfig.setDepartmentSeq(departmentSeq);
            newInvoiceNoConfig.setNextExpenseVoucherNo(1);
            newInvoiceNoConfig.setLetterCode(letterCode);
            newInvoiceNoConfig.setStatus(1);
            this.localInvoiceNoConfigService.save(newInvoiceNoConfig);
        } else {
            localInvoiceNoConfig.setNextExpenseVoucherNo(localInvoiceNoConfig.getNextExpenseVoucherNo() + 1);
            this.localInvoiceNoConfigService.save(localInvoiceNoConfig);
        }
    }

}
