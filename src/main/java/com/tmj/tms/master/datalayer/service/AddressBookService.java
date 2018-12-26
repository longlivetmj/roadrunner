package com.tmj.tms.master.datalayer.service;

import com.tmj.tms.master.datalayer.modal.AddressBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressBookService extends JpaRepository<AddressBook, Integer> {

    List<AddressBook> findByEmailContaining(String email);

    List<AddressBook> findByAddressBookSeqIn(List<Integer> addressBookSequenceList);

    @Query("SELECT\n" +
            "  AB\n" +
            "FROM\n" +
            "  AddressBook AB,\n" +
            "  Stakeholder  SH,\n" +
            "  StakeholderTypeMapping SHTM\n" +
            "WHERE\n" +
            "  SHTM.stakeholderTypeSeq = :STAKEHOLDER_TYPE_SEQ\n" +
            "  AND SHTM.stakeholderSeq = SH.stakeholderSeq\n" +
            "  AND SH.addressBookSeq = AB.addressBookSeq\n" +
            "  AND SH.companyProfileSeq = :COMPANY_PROFILE_SEQ \n" +
            "  AND SH.status = :STATUS \n" +
            "  AND UPPER(AB.email) LIKE UPPER('%' || :E_MAIL || '%')")
    List<AddressBook> getStakeholderConsigneeDetails(@Param("COMPANY_PROFILE_SEQ") Integer companyProfileSeq,
                                                     @Param("STAKEHOLDER_TYPE_SEQ") Integer stakeholderTypeSeq,
                                                     @Param("STATUS") Integer status,
                                                     @Param("E_MAIL") String email);
}
