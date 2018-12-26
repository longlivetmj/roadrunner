package com.tmj.tms.config.datalayer.service;

import com.tmj.tms.config.datalayer.modal.ReportUpload;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Map;

public interface ReportUploadService {

    ResponseEntity<byte[]> findAndDownload(Integer reportUploadSeq);

    ReportUpload findReportUploadByReportUploadSeq(Integer uploadedDocumentSeq);

    Integer saveReport(Map<String, Object> param, String moduleName, Integer reportSeq,
                       String localInvoiceNo, String s, String s1, HttpServletRequest httpServletRequest) throws SQLException;
}
