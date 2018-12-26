package com.tmj.tms.home.datalayer.service;

import com.tmj.tms.home.datalayer.model.TempRevenueAndCost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Component
@Repository
public interface TempRevenueAndCostService extends JpaRepository<TempRevenueAndCost, Integer> {

    @Query("SELECT " +
            "   SUM(rc.amount) " +
            "FROM " +
            "   TempRevenueAndCost rc " +
            "WHERE " +
            "   rc.transactionDate BETWEEN :START_DATE AND :END_DATE " +
            "   AND rc.companyProfileSeq = :COMPANY_PROFILE_SEQ" +
            "   AND rc.moduleSeq = :MODULE_SEQ" +
            "   AND rc.chargeType = :CHARGE_TYPE")
    Double findRevenueAndCostChargeDetailSum(@Param("COMPANY_PROFILE_SEQ") Integer companyProfileSeq,
                                             @Param("START_DATE") Date startDate,
                                             @Param("END_DATE") Date endDate,
                                             @Param("MODULE_SEQ") Integer moduleSeq,
                                             @Param("CHARGE_TYPE") Integer chargeTyp);

    @Query(value = "SELECT \n" +
            "  REVENUE.key,\n" +
            "  trim(to_char(REVENUE.firstValue,'99999999999999999D99')) firstValue,\n" +
            "  trim(to_char(COST.secondValue,'99999999999999999D99')) secondValue\n" +
            "FROM\n" +
            "(SELECT\n" +
            "  extract(YEAR FROM transaction_date) || '-' ||to_char(transaction_date,'Mon') AS key,\n" +
            "  extract(YEAR FROM transaction_date) || to_char(transaction_date,'MM') AS orderKey,\n" +
            "  sum(amount) firstValue\n" +
            "FROM\n" +
            "  tms.temp_revenue_and_cost\n" +
            "WHERE\n" +
            "  company_profile_seq = :COMPANY_PROFILE_SEQ\n" +
            "  AND module_seq = :MODULE_SEQ\n" +
            "  AND charge_type = 1\n" +
            "GROUP BY\n" +
            "  extract(YEAR FROM transaction_date) || '-' ||to_char(transaction_date,'Mon'), " +
            "  extract(YEAR FROM transaction_date) || to_char(transaction_date,'MM') ) REVENUE,\n" +
            "(SELECT\n" +
            "  extract(YEAR FROM transaction_date) || '-' ||to_char(transaction_date,'Mon') AS key,\n" +
            "  sum(amount) secondValue\n" +
            "FROM\n" +
            "  tms.temp_revenue_and_cost\n" +
            "WHERE\n" +
            "  company_profile_seq = :COMPANY_PROFILE_SEQ\n" +
            "  AND module_seq = :MODULE_SEQ\n" +
            "  AND charge_type = 2\n" +
            "GROUP BY\n" +
            "  extract(YEAR FROM transaction_date) || '-' ||to_char(transaction_date,'Mon')) COST\n" +
            "WHERE\n" +
            "  REVENUE.key = COST.key\n" +
            "ORDER BY\n" +
            "  REVENUE.orderKey ", nativeQuery = true, name = "revenueProfitMapping")
    List<Object[]> findMonthWiseRevenueAndCost(@Param("COMPANY_PROFILE_SEQ") Integer companyProfileSeq,
                                               @Param("MODULE_SEQ") Integer moduleSeq);

    @Query("SELECT " +
            "   SUM(rc.financeDocumentCount) " +
            "FROM " +
            "   TempRevenueAndCost rc " +
            "WHERE " +
            "   rc.transactionDate BETWEEN :START_DATE AND :END_DATE " +
            "   AND rc.companyProfileSeq = :COMPANY_PROFILE_SEQ" +
            "   AND rc.moduleSeq = :MODULE_SEQ" +
            "   AND rc.chargeType = :CHARGE_TYPE")
    Double findFinancialDocumentSum(@Param("COMPANY_PROFILE_SEQ") Integer companyProfileSeq,
                                    @Param("START_DATE") Date startDate,
                                    @Param("END_DATE") Date endDate,
                                    @Param("MODULE_SEQ") Integer moduleSeq,
                                    @Param("CHARGE_TYPE") Integer chargeTyp);

    TempRevenueAndCost findByReferenceSeqAndModuleSeqAndCompanyProfileSeqAndChargeType(Integer transportBookingSeq, Integer moduleSeq, Integer companyProfileSeq, Integer chargeType);

    long countByCompanyProfileSeqAndModuleSeqAndChargeTypeAndFinanceDocumentCount(Integer companyProfileSeq, Integer moduleSeq, Integer chargeType, Integer financeDocumentCount);

    long countByCompanyProfileSeqAndModuleSeqAndChargeTypeAndFinanceDocumentCountAndStakeholderSeqNot(Integer companyProfileSeq, Integer moduleSeq, Integer chargeType, Integer financeDocumentCount, Integer defaultTransporterSeq);

    Long countByCompanyProfileSeqAndModuleSeqAndChargeTypeAndTransactionDateBetween(Integer companyProfileSeq, Integer moduleSeq, Integer chargeType, Date start, Date end);

    long countByCompanyProfileSeqAndModuleSeqAndChargeTypeAndCurrentStatusIn(Integer companyProfileSeq, Integer moduleSeq, Integer chargeType, List<Integer> statusList);

    @Query("SELECT " +
            "   rc.stakeholderSeq, " +
            "   count(rc.referenceSeq) " +
            "FROM " +
            "   TempRevenueAndCost rc " +
            "WHERE " +
            "   rc.companyProfileSeq = :COMPANY_PROFILE_SEQ" +
            "   AND rc.moduleSeq = :MODULE_SEQ" +
            "   AND rc.chargeType = 1 " +
            "GROUP BY " +
            "   rc.stakeholderSeq " +
            "ORDER BY " +
            "  count(rc.referenceSeq) desc ")
    List<Object[]> findCustomerWiseJobCount(@Param("COMPANY_PROFILE_SEQ") Integer companyProfileSeq,
                                            @Param("MODULE_SEQ") Integer moduleSeq);

    @Query("SELECT " +
            "   rc.stakeholderSeq, " +
            "   sum (rc.amount) " +
            "FROM " +
            "   TempRevenueAndCost rc " +
            "WHERE " +
            "   rc.companyProfileSeq = :COMPANY_PROFILE_SEQ" +
            "   AND rc.moduleSeq = :MODULE_SEQ" +
            "   AND rc.chargeType = 1 " +
            "GROUP BY " +
            "   rc.stakeholderSeq " +
            "ORDER BY " +
            "  sum(rc.amount) desc ")
    List<Object[]> findCustomerWiseRevenue(@Param("COMPANY_PROFILE_SEQ") Integer companyProfileSeq,
                                           @Param("MODULE_SEQ") Integer moduleSeq);

    @Query("SELECT " +
            "   rc.pickupLocationSeq, " +
            "   count(rc.pickupLocationSeq) " +
            "FROM " +
            "   TempRevenueAndCost rc " +
            "WHERE " +
            "   rc.companyProfileSeq = :COMPANY_PROFILE_SEQ" +
            "   AND rc.moduleSeq = :MODULE_SEQ" +
            "   AND rc.chargeType = 1 " +
            "GROUP BY " +
            "   rc.pickupLocationSeq " +
            "ORDER BY " +
            "  count(rc.pickupLocationSeq) desc ")
    List<Object[]> findJobCountByPickup(@Param("COMPANY_PROFILE_SEQ") Integer companyProfileSeq,
                                        @Param("MODULE_SEQ") Integer moduleSeq);

    @Query(value = "SELECT \n" +
            "  REVENUE.orderKey,\n" +
            "  trim(to_char(REVENUE.firstValue,'99999999999999999D99')) firstValue,\n" +
            "  trim(to_char(COST.secondValue,'99999999999999999D99')) secondValue\n" +
            "FROM\n" +
            "(SELECT\n" +
            "  extract(YEAR FROM transaction_date) || '-' ||to_char(transaction_date,'Mon') AS key,\n" +
            "  extract(YEAR FROM transaction_date) || '-' ||to_char(transaction_date,'MM') AS orderKey,\n" +
            "  sum(finance_document_value) firstValue\n" +
            "FROM\n" +
            "  tms.temp_revenue_and_cost\n" +
            "WHERE\n" +
            "  company_profile_seq = :COMPANY_PROFILE_SEQ\n" +
            "  AND module_seq = :MODULE_SEQ\n" +
            "  AND charge_type = 1\n" +
            "GROUP BY\n" +
            "  extract(YEAR FROM transaction_date) || '-' ||to_char(transaction_date,'Mon'), " +
            "  extract(YEAR FROM transaction_date) || '-' || to_char(transaction_date,'MM') ) REVENUE,\n" +
            "(SELECT\n" +
            "  extract(YEAR FROM transaction_date) || '-' ||to_char(transaction_date,'Mon') AS key,\n" +
            "  sum(finance_document_value) secondValue\n" +
            "FROM\n" +
            "  tms.temp_revenue_and_cost\n" +
            "WHERE\n" +
            "  company_profile_seq = :COMPANY_PROFILE_SEQ\n" +
            "  AND module_seq = :MODULE_SEQ\n" +
            "  AND charge_type = 2\n" +
            "GROUP BY\n" +
            "  extract(YEAR FROM transaction_date) || '-' ||to_char(transaction_date,'Mon')) COST\n" +
            "WHERE\n" +
            "  REVENUE.key = COST.key\n" +
            "ORDER BY\n" +
            "  REVENUE.orderKey ", nativeQuery = true)
    List<Object[]> findMonthWiseInvoiceAndExpense(@Param("COMPANY_PROFILE_SEQ") Integer companyProfileSeq,
                                                  @Param("MODULE_SEQ") Integer moduleSeq);
}
