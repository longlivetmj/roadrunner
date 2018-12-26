package com.tmj.tms.utility;

import com.tmj.tms.config.datalayer.modal.Report;
import com.tmj.tms.config.datalayer.modal.UploadedDocument;
import com.tmj.tms.config.datalayer.service.ReportService;
import com.tmj.tms.config.datalayer.service.UploadedDocumentService;
import com.tmj.tms.config.utility.ReportType;
import com.tmj.tms.utility.JasperReportGenerate;
import com.tmj.tms.utility.ReportDBCon;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

@Service
public class ReportGenerator {

    private final UploadedDocumentService uploadedDocumentService;
    private final JasperReportGenerate jasperReportGenerate;
    private final ReportDBCon reportDBCon;
    private final ReportService reportService;

    @Autowired
    public ReportGenerator(UploadedDocumentService uploadedDocumentService,
                           JasperReportGenerate jasperReportGenerate,
                           ReportDBCon reportDBCon,
                           ReportService reportService) {
        this.uploadedDocumentService = uploadedDocumentService;
        this.jasperReportGenerate = jasperReportGenerate;
        this.reportDBCon = reportDBCon;
        this.reportService = reportService;
    }

    public Integer saveReport(Map<String, Object> param,
                              String moduleName,
                              Integer reportSeq,
                              String fileName,
                              String fileExtension,
                              String fileType,
                              HttpServletRequest httpServletRequest) throws SQLException {
        Integer uploadedDocumentSeq = null;
        Connection connection = null;
        try {
            Report report = this.reportService.findOne(reportSeq);
            ServletContext sc = httpServletRequest.getServletContext();
            String actualReportPath = Paths.get(sc.getRealPath("reports"), moduleName, report.getReportName() + ".jasper").toString();
            param.put("REPORT_SEQ", reportSeq);
            connection = this.reportDBCon.getReportDBCon();
            JasperPrint jp = JasperFillManager.fillReport(actualReportPath, param, connection);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            if (fileType.equals(ReportType.PDF.getFileType())) {
                this.jasperReportGenerate.generatePdf(jp, out);
            } else if (fileType.equals(ReportType.RTF.getFileType())) {
                this.jasperReportGenerate.generateRtf(jp, out);
            } else if (fileType.equals(ReportType.EXCEL.getFileType())) {
                this.jasperReportGenerate.generateXlxs(jp, out);
            }

            byte[] bytes = out.toByteArray();
            uploadedDocumentSeq = this.saveToUploadDocument(fileName, fileExtension, fileType, bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return uploadedDocumentSeq;
    }

    public Integer saveReport(Connection connection,
                              Map<String, Object> param,
                              String moduleName,
                              String reportName,
                              String fileName,
                              String fileExtension,
                              String fileType,
                              HttpServletRequest httpServletRequest) {
        Integer uploadedDocumentSeq = null;

        try {
            ServletContext sc = httpServletRequest.getServletContext();
            String actualReportPath = Paths.get(sc.getRealPath("reports"), moduleName, reportName + ".jasper").toString();

            JasperPrint jp = JasperFillManager.fillReport(actualReportPath, param, connection);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            this.jasperReportGenerate.generatePdf(jp, out);
            byte[] bytes = out.toByteArray();
            uploadedDocumentSeq = this.saveToUploadDocument(fileName, fileExtension, fileType, bytes);
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uploadedDocumentSeq;
    }

    private Integer saveToUploadDocument(String fileName, String fileExtension, String fileType, byte[] bytes) {
        Integer uploadedDocumentSeq = null;
        try {
            UploadedDocument uploadedDocument = new UploadedDocument();
            uploadedDocument.setFileName(fileName + fileExtension);
            uploadedDocument.setFileType(fileType);
            uploadedDocument.setFileData(bytes);
            uploadedDocumentSeq = this.uploadedDocumentService.save(uploadedDocument);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uploadedDocumentSeq;
    }

    public Integer saveReport(JasperPrint jp, ByteArrayOutputStream out, String fileName, String fileExtension, String fileType) {
        Integer uploadedDocumentSeq = null;
        try {
            if (fileType.equals(ReportType.PDF.getFileType())) {
                this.jasperReportGenerate.generatePdf(jp, out);
            } else if (fileType.equals(ReportType.RTF.getFileType())) {
                this.jasperReportGenerate.generateRtf(jp, out);
            } else if (fileType.equals(ReportType.EXCEL.getFileType())) {
                this.jasperReportGenerate.generateXlxs(jp, out);
            }

            byte[] bytes = out.toByteArray();
            uploadedDocumentSeq = this.saveToUploadDocument(fileName, fileExtension, fileType, bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uploadedDocumentSeq;
    }
}
