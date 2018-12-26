package com.tmj.tms.home.datalayer.service;


import com.tmj.tms.home.datalayer.model.TempOtherCost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
@Repository
public interface TempOtherCostService extends JpaRepository<TempOtherCost, Integer> {

    TempOtherCost findByCostTypeAndReferenceSeq(Integer costType, Integer referenceSeq);

    @Query(value = "" +
            "   SELECT\n" +
            "     extract(YEAR FROM transaction_date) || '-' || to_char(transaction_date,'Mon') AS key,\n" +
            "     to_char(sum(amount),'99999999999999999D99') amount,\n" +
            "     extract(YEAR FROM transaction_date) || '-' || to_char(transaction_date,'MM') AS otherKey\n" +
            "   FROM\n" +
            "     tms.temp_other_cost\n" +
            "   WHERE\n" +
            "     company_profile_seq = :COMPANY_PROFILE_SEQ\n" +
            "   GROUP BY\n" +
            "     extract(YEAR FROM transaction_date) || '-' || to_char(transaction_date,'Mon'),\n" +
            "     extract(YEAR FROM transaction_date) || '-' || to_char(transaction_date,'MM')", nativeQuery = true, name = "otherCostMapping")
    List<Object[]> findMonthWiseRevenueAndCost(@Param("COMPANY_PROFILE_SEQ") Integer companyProfileSeq);
}
