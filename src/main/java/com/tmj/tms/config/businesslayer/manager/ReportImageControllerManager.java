package com.tmj.tms.config.businesslayer.manager;

import com.tmj.tms.config.datalayer.modal.ReportImage;
import com.tmj.tms.utility.ResponseObject;
import org.springframework.web.multipart.MultipartFile;

public interface ReportImageControllerManager {

    ResponseObject saveReportImage(ReportImage reportImage, MultipartFile multipartFile);

    ResponseObject updateReportImage(ReportImage reportImage, MultipartFile multipartFile);
}
