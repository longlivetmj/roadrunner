package com.tmj.tms.finance.utility;

import javax.cache.annotation.CacheResult;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum ExchangeRateSourceType {

    BANK(1, "BANK");

    private final Integer exchangeRateSourceType;
    private final String exchangeRateSourceTypeDescription;

    ExchangeRateSourceType(Integer exchangeRateSourceType, String exchangeRateSourceTypeDescription) {
        this.exchangeRateSourceType = exchangeRateSourceType;
        this.exchangeRateSourceTypeDescription = exchangeRateSourceTypeDescription;
    }

    public static ExchangeRateSourceType findOne(Integer exchangeRateSourceType) {
        return Arrays.stream(ExchangeRateSourceType.values())
                .filter(x -> x.exchangeRateSourceType.equals(exchangeRateSourceType))
                .findFirst()
                .orElse(null);
    }


    @CacheResult
    public static List<ExchangeRateSourceType> getSourceTypeForBulkInvoice() {
        ExchangeRateSourceType[] exchangeRateSourceTypes = ExchangeRateSourceType.values();
        List<ExchangeRateSourceType> exchangeRateSourceTypeList = new ArrayList<>();
        for (ExchangeRateSourceType sourceType : exchangeRateSourceTypes) {
            if (!sourceType.getExchangeRateSourceType().equals(2) && !sourceType.getExchangeRateSourceType().equals(3) && !sourceType.getExchangeRateSourceType().equals(4)) {
                exchangeRateSourceTypeList.add(sourceType);
            }
        }
        return exchangeRateSourceTypeList;
    }

    @CacheResult
    public static List<ExchangeRateSourceType> getSourceTypeForBulExpenseVoucher() {
        ExchangeRateSourceType[] exchangeRateSourceTypes = ExchangeRateSourceType.values();
        List<ExchangeRateSourceType> exchangeRateSourceTypeList = new ArrayList<>();
        for (ExchangeRateSourceType sourceType : exchangeRateSourceTypes) {
            if (!sourceType.getExchangeRateSourceType().equals(1) && !sourceType.getExchangeRateSourceType().equals(2)) {
                exchangeRateSourceTypeList.add(sourceType);
            }
        }
        return exchangeRateSourceTypeList;
    }

    public Integer getExchangeRateSourceType() {
        return exchangeRateSourceType;
    }

    public String getExchangeRateSourceTypeDescription() {
        return exchangeRateSourceTypeDescription;
    }
}
