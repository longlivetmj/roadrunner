package com.tmj.tms.master.businesslayer.manager;

import com.tmj.tms.master.datalayer.modal.Country;
import com.tmj.tms.utility.ResponseObject;

import java.util.List;

public interface CountryManagementControllerManager {

    ResponseObject createCountry(Country country);

    ResponseObject updateCountry(Country country);

    ResponseObject deleteCountry(Integer countrySeq);

    List<Country> searchCountry(String countryName, String countryCode);

}
