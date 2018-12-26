package com.tmj.tms.config.businesslayer.managerImpl;

import com.tmj.tms.config.businesslayer.manager.ReportImageControllerManager;
import com.tmj.tms.config.datalayer.modal.ReportImage;
import com.tmj.tms.config.datalayer.service.ReportImageService;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@Service
public class ReportImageControllerManagerImpl implements ReportImageControllerManager {

    private final ReportImageService reportImageService;
    private final HttpSession httpSession;

    @Autowired
    public ReportImageControllerManagerImpl(ReportImageService reportImageService, HttpSession httpSession) {
        this.reportImageService = reportImageService;
        this.httpSession = httpSession;
    }

    @Override
    public ResponseObject saveReportImage(ReportImage reportImage, MultipartFile multipartFile) {
        ResponseObject responseObject = new ResponseObject("Report Image uploaded successfully", true);
        try {
            Integer companyProfileSeq = Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString());
            reportImage.setCompanyProfileSeq(companyProfileSeq);
            if (multipartFile != null && !multipartFile.isEmpty()) {
                reportImage.setFileData(multipartFile.getBytes());
                reportImage.setFileName(multipartFile.getOriginalFilename().trim().replace(',', ' '));
                reportImage.setFileType(multipartFile.getContentType());
                this.reportImageService.save(reportImage);
            }

        } catch (Exception e) {
            e.printStackTrace();
            new ResponseObject("Error Uploading", false);
        }
        return responseObject;
    }

    @Override
    public ResponseObject updateReportImage(ReportImage reportImage, MultipartFile multipartFile) {
        ResponseObject responseObject = new ResponseObject("Report Image uploaded successfully", true);
        try {
            Integer companyProfileSeq = Integer.parseInt(this.httpSession.getAttribute("companyProfileSeq").toString());
            reportImage.setCompanyProfileSeq(companyProfileSeq);
            if (multipartFile != null && !multipartFile.isEmpty()) {
                reportImage.setFileData(multipartFile.getBytes());
                reportImage.setFileName(multipartFile.getOriginalFilename().trim().replace(',', ' '));
                reportImage.setFileType(multipartFile.getContentType());
                this.reportImageService.save(reportImage);
            }
        } catch (Exception e) {
            e.printStackTrace();
            new ResponseObject("Error Uploading", false);
        }
        return responseObject;
    }
}
