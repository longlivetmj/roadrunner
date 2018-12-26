package com.tmj.tms.transport.utility;

import com.tmj.tms.config.datalayer.modal.CompanyProfile;
import com.tmj.tms.config.datalayer.modal.Department;
import com.tmj.tms.config.datalayer.service.CompanyProfileService;
import com.tmj.tms.config.datalayer.service.DepartmentService;
import com.tmj.tms.master.datalayer.modal.JobNoConfig;
import com.tmj.tms.master.datalayer.service.JobNoConfigService;
import com.tmj.tms.utility.NumberFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobNoGenerator {

    private final JobNoConfigService jobNoConfigService;
    private final CompanyProfileService companyProfileService;
    private final DepartmentService departmentService;

    @Autowired
    public JobNoGenerator(JobNoConfigService jobNoConfigService,
                          CompanyProfileService companyProfileService,
                          DepartmentService departmentService) {
        this.jobNoConfigService = jobNoConfigService;
        this.companyProfileService = companyProfileService;
        this.departmentService = departmentService;
    }

    public String generateJobNo(Integer companyProfileSeq, Integer departmentSeq) {
        Department department = this.departmentService.findOne(departmentSeq);
        CompanyProfile companyProfile = this.companyProfileService.findOne(companyProfileSeq);
        JobNoConfig jobNoConfig = increaseMaxJobNo(companyProfile, companyProfileSeq, departmentSeq);
        String strJobNo = NumberFormatter.convertToSixDigit(jobNoConfig.getNextJobNo());
        return department.getPrefix() + "-" + companyProfile.getFinancialYear() + "-" + strJobNo;
    }

    private JobNoConfig increaseMaxJobNo(CompanyProfile companyProfile, Integer companyProfileSeq, Integer departmentSeq) {
        JobNoConfig jobNoConfig = this.jobNoConfigService.findByCompanyProfileSeqAndDepartmentSeqAndFinancialYear(companyProfileSeq, departmentSeq, companyProfile.getFinancialYear());
        if (jobNoConfig == null) {
            jobNoConfig = new JobNoConfig();
            jobNoConfig.setCompanyProfileSeq(companyProfileSeq);
            jobNoConfig.setDepartmentSeq(departmentSeq);
            jobNoConfig.setFinancialYear(companyProfile.getFinancialYear());
            jobNoConfig.setNextJobNo(1);
            jobNoConfig.setStatus(1);
            this.jobNoConfigService.save(jobNoConfig);
        } else {
            jobNoConfig.setNextJobNo(jobNoConfig.getNextJobNo() + 1);
            jobNoConfig = this.jobNoConfigService.save(jobNoConfig);
        }
        return jobNoConfig;
    }
}
