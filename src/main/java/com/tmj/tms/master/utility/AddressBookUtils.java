package com.tmj.tms.master.utility;


import com.tmj.tms.master.datalayer.modal.AddressBook;

public class AddressBookUtils {

    public static String cleanAddressBook(AddressBook addressBook) {
        String address = "";
        if (addressBook.getAddress1() != null) {
            address += addressBook.getAddress1() + "\n";
        }
        if (addressBook.getAddress2() != null) {
            address += addressBook.getAddress2() + "\n";
        }
        if (addressBook.getCity() != null) {
            address += addressBook.getCity() + "\n";
        }
        if (addressBook.getState() != null) {
            address += addressBook.getState();
        }
        if (addressBook.getZip() != null) {
            address += addressBook.getZip() + "\n";
        }
        if (addressBook.getCountry() != null && addressBook.getCountry().getCountryName() != null) {
            address += addressBook.getCountry().getCountryName() + "\n";
        }
        if (addressBook.getTelephone() != null) {
            address += addressBook.getTelephone() + "\n";
        }
        if (addressBook.getFax() != null) {
            address += addressBook.getFax() + "\n";
        }
        return address;
    }

    public static String addressBookForInvoice(AddressBook addressBook) {
        String address = "";
        if (addressBook.getAddress1() != null) {
            address += addressBook.getAddress1() + "\n";
        }
        if (addressBook.getAddress2() != null) {
            address += addressBook.getAddress2() + "\n";
        }
        if (addressBook.getCity() != null) {
            address += addressBook.getCity() + "\n";
        }
        if (addressBook.getState() != null) {
            address += addressBook.getState();
        }
        if (addressBook.getZip() != null) {
            address += addressBook.getZip() + "\n";
        }
        if (addressBook.getCountry() != null && addressBook.getCountry().getCountryName() != null) {
            address += addressBook.getCountry().getCountryName() + "\n";
        }
        return address;
    }
}
