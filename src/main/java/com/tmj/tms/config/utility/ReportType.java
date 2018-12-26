package com.tmj.tms.config.utility;

import java.util.Arrays;

public enum ReportType {
    PDF(1, "PDF", ".pdf", "application/pdf"),
    RTF(2, "RTF", ".rtf", "application/rtf"),
    EXCEL(3, "EXCEL", ".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

    private Integer reportTypeSeq;
    private String reportType;
    private String extension;
    private String fileType;

    ReportType(Integer reportTypeSeq, String reportType, String extension, String fileType) {
        this.reportTypeSeq = reportTypeSeq;
        this.reportType = reportType;
        this.extension = extension;
        this.fileType = fileType;
    }

    public static ReportType findOne(Integer reportTypeSeq) {
        return Arrays.stream(ReportType.values())
                .filter(x -> x.reportTypeSeq.equals(reportTypeSeq))
                .findFirst()
                .orElse(null);
    }

    public Integer getReportTypeSeq() {
        return reportTypeSeq;
    }

    public String getReportType() {
        return reportType;
    }

    public String getExtension() {
        return extension;
    }

    public String getFileType() {
        return fileType;
    }
}
