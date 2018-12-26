package com.tmj.tms.config.utility;

import java.util.Arrays;

public enum DocumentType {

    JOB(1, "Job"),
    TRANSPORT_BOOKING(2, "Transport Booking");

    private Integer documentTypeSeq;
    private String documentType;

    public static DocumentType findOne(Integer documentTypeSeq) {
        return Arrays.stream(DocumentType.values())
                .filter(x -> x.documentTypeSeq.equals(documentTypeSeq))
                .findFirst()
                .orElse(null);
    }

    DocumentType(Integer documentTypeSeq, String documentType) {
        this.documentTypeSeq = documentTypeSeq;
        this.documentType = documentType;
    }

    public Integer getDocumentTypeSeq() {
        return documentTypeSeq;
    }

    public String getDocumentType() {
        return documentType;
    }
}
