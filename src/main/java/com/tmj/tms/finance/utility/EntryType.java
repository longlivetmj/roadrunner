package com.tmj.tms.finance.utility;


public enum EntryType {

    CREDIT(1, "CREDIT"),
    DEBIT(2, "DEBIT");

    private final Integer entryTypeSeq;
    private final String entryType;

    EntryType(Integer entryTypeSeq, String entryType) {
        this.entryTypeSeq = entryTypeSeq;
        this.entryType = entryType;
    }

    public Integer getEntryTypeSeq() {
        return entryTypeSeq;
    }

    public String getEntryType() {
        return entryType;
    }
}
