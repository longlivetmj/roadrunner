package com.tmj.tms.finance.zoho.schedule;

import com.tmj.tms.config.datalayer.modal.CompanyProfile;
import com.tmj.tms.config.datalayer.service.CompanyProfileService;
import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.finance.datalayer.modal.ZohoIntegration;
import com.tmj.tms.finance.zoho.api.ItemApi;
import com.tmj.tms.finance.zoho.model.Item;
import com.tmj.tms.finance.zoho.util.ZohoAccessTokenGenerator;
import com.tmj.tms.master.datalayer.modal.Charge;
import com.tmj.tms.master.datalayer.service.ChargeService;
import com.tmj.tms.utility.YesOrNo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Profile(value = "dev")
public class ChargeIntegration {

    private final CompanyProfileService companyProfileService;
    private final ChargeService chargeService;
    private final ZohoAccessTokenGenerator zohoAccessTokenGenerator;
    private final ItemApi itemApi;

    @Autowired
    public ChargeIntegration(CompanyProfileService companyProfileService,
                             ChargeService chargeService,
                             ZohoAccessTokenGenerator zohoAccessTokenGenerator,
                             ItemApi itemApi) {
        this.companyProfileService = companyProfileService;
        this.chargeService = chargeService;
        this.zohoAccessTokenGenerator = zohoAccessTokenGenerator;
        this.itemApi = itemApi;
    }

    @Scheduled(fixedRate = 60000 * 30)
    @Transactional(timeout = 500000000)
    public void insertOrUpdateItem() {
        List<CompanyProfile> companyProfileList = this.companyProfileService.findByStatusNot(MasterDataStatus.DELETED.getStatusSeq());
        for (CompanyProfile companyProfile : companyProfileList) {
            try {
                ZohoIntegration zohoIntegration = this.zohoAccessTokenGenerator.tokenUpdater(companyProfile.getCompanyProfileSeq());
                List<Charge> chargeList = this.chargeService.findByCompanyProfileSeqAndFinanceIntegrationIsNull(companyProfile.getCompanyProfileSeq());
                for (Charge charge : chargeList) {
                    try {
                        Item item = this.initItem(charge);
                        this.saveOrUpdate(zohoIntegration, charge, item);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(">>>>>>>>>>charge>>>>>>>>>" + charge.getChargeName());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Item initItem(Charge charge) throws Exception {
        Item item = new Item();
        item.setName(charge.getChargeName());
        item.setDescription(charge.getDescription());
        item.setRate(0);
        return item;
    }

    private void saveOrUpdate(ZohoIntegration zohoIntegration, Charge charge, Item item) throws Exception {
        if (charge.getFinanceIntegrationKey() == null) {
            item = this.itemApi.create(item, zohoIntegration);
            if (item != null) {
                charge.setFinanceIntegrationKey(item.getItemId());
                charge.setFinanceIntegration(YesOrNo.Yes.getYesOrNoSeq());
                this.chargeService.save(charge);
            }
        } else {
            itemApi.update(item, zohoIntegration, charge.getFinanceIntegrationKey());
        }
    }

}
