package com.tmj.tms.config.datalayer.serviceImpl;

import com.tmj.tms.config.datalayer.modal.Report;
import com.tmj.tms.config.datalayer.modal.ReportUpload;
import com.tmj.tms.config.datalayer.service.ReportService;
import com.tmj.tms.config.datalayer.service.ReportUploadService;
import com.tmj.tms.config.utility.ReportType;
import com.tmj.tms.utility.JasperReportGenerate;
import com.tmj.tms.utility.ReportDBCon;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

@Repository
public class ReportUploadServiceImpl implements ReportUploadService {

    private final ReportService reportService;
    private final ReportDBCon reportDBCon;
    private final JasperReportGenerate jasperReportGenerate;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public ReportUploadServiceImpl(ReportService reportService,
                                   ReportDBCon reportDBCon,
                                   JasperReportGenerate jasperReportGenerate) {
        this.reportService = reportService;
        this.reportDBCon = reportDBCon;
        this.jasperReportGenerate = jasperReportGenerate;
    }

    @Override
    public ReportUpload findReportUploadByReportUploadSeq(Integer reportUploadSeq) {
        ReportUpload reportUpload = null;
        try {
            reportUpload = this.entityManager.find(ReportUpload.class, reportUploadSeq);
        } catch (Exception e) {
            System.out.println("No file Found");
        }
        return reportUpload;
    }

    @Override
    @Transactional(timeout = 600)
    public Integer saveReport(Map<String, Object> param,
                              String moduleName,
                              Integer reportSeq,
                              String fileName,
                              String fileExtension,
                              String fileType,
                              HttpServletRequest httpServletRequest) throws SQLException {
        Integer reportUploadSeq = null;
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
            reportUploadSeq = this.saveToReportUpload(fileName, fileExtension, fileType, bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return reportUploadSeq;
    }

    private Integer saveToReportUpload(String fileName, String fileExtension, String fileType, byte[] bytes) {
        Integer reportUploadSeq = null;
        try {
            ReportUpload reportUpload = new ReportUpload();
            reportUpload.setFileName(fileName + fileExtension);
            reportUpload.setFileType(fileType);
            reportUpload.setFileData(bytes);
            this.entityManager.persist(reportUpload);
            reportUploadSeq = reportUpload.getReportUploadSeq();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reportUploadSeq;
    }

    @Override
    public ResponseEntity<byte[]> findAndDownload(Integer reportUploadSeq) {
        try {
            ReportUpload reportUpload = this.findReportUploadByReportUploadSeq(reportUploadSeq);
            if (reportUpload != null && reportUpload.getFileData() != null && reportUpload.getFileData().length != 0) {
                InputStream in = new ByteArrayInputStream(reportUpload.getFileData());
                final HttpHeaders headers = new HttpHeaders();
                headers.set("Content-disposition", " filename=" + reportUpload.getFileName());
                headers.setContentType(MediaType.valueOf(reportUpload.getFileType()));
                return new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<byte[]>(null, null, HttpStatus.CREATED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<byte[]>(null, null, HttpStatus.CREATED);
        }
    }

}
