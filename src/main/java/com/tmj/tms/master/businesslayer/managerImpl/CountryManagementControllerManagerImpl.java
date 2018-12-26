package com.tmj.tms.master.businesslayer.managerImpl;

import com.tmj.tms.config.utility.MasterDataStatus;
import com.tmj.tms.master.businesslayer.manager.CountryManagementControllerManager;
import com.tmj.tms.master.datalayer.modal.Country;
import com.tmj.tms.master.datalayer.service.CountryService;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CountryManagementControllerManagerImpl implements CountryManagementControllerManager {

    private final CountryService countryService;

    @Autowired
    public CountryManagementControllerManagerImpl(CountryService countryService) {
        this.countryService = countryService;
    }

    @Override
    public ResponseObject createCountry(Country country) {
        country = this.countryService.save(country);
        ResponseObject responseObject = new ResponseObject("Country Saved Successfully", true);
        responseObject.setObject(country);
        return responseObject;
    }

    @Override
    public ResponseObject deleteCountry(Integer countrySeq) {
        Country dbCountry = this.countryService.findOne(countrySeq);
        dbCountry.setStatus(0);
        dbCountry = this.countryService.save(dbCountry);
        ResponseObject responseObject = new ResponseObject("Country Deleted Successfully", true);
        responseObject.setObject(dbCountry);
        return responseObject;
    }

    @Override
    public ResponseObject updateCountry(Country country) {
        ResponseObject responseObject = new ResponseObject("Country Updated Successfully", true);
        Country dbCountry = this.countryService.findOne(country.getCountrySeq());
        if (dbCountry != null) {
            if(!dbCountry.equals(country)){
                this.countryService.save(country);
                responseObject.setObject(country);
            }else {
                responseObject.setMessage("No Amendments Found!!");
                responseObject.setStatus(false);
            }
        } else {
            responseObject.setMessage("Country not found!!");
            responseObject.setStatus(false);
        }
        return responseObject;
    }

    @Override
    public List<Country> searchCountry(String countryName, String countryCode) {
        List<Country> countryList = new ArrayList<>();
        try {
            List<Integer> statusSeqList = MasterDataStatus.getStatusListForViewing("ROLE_master@countryManagement_VIEW-DELETE");
            if (countryName.equals("") && countryCode.equals("")) {
                countryList = this.countryService.findByStatusIn(statusSeqList);
            } else {
                countryList = this.countryService.findByCountryNameContainingIgnoreCaseAndCountryCodeContainingIgnoreCaseAndStatusIn(countryName, countryCode, statusSeqList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return countryList;
    }

}
