package com.tmj.tms.finance.utility;

import java.util.Arrays;

public enum FinanceStatus {

    INVOICED(1, "Invoiced"),
    PENDING_TO_INVOICE(2, "Pending to Invoice"),
    PENDING_TO_EV(3, "Pending to EV");

    private final Integer financeStatus;
    private final String financeStatusDescription;

    FinanceStatus(Integer financeStatus, String financeStatusDescription) {
        this.financeStatus = financeStatus;
        this.financeStatusDescription = financeStatusDescription;
    }

    public static FinanceStatus findOne(Integer financeStatus) {
        return Arrays.stream(FinanceStatus.values())
                .filter(x -> x.financeStatus.equals(financeStatus))
                .findFirst()
                .orElse(null);
    }

    public Integer getFinanceStatus() {
        return financeStatus;
    }

    public String getFinanceStatusDescription() {
        return financeStatusDescription;
    }
}
