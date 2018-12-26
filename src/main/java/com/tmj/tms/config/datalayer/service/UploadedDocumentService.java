package com.tmj.tms.config.datalayer.service;

import com.tmj.tms.config.datalayer.modal.UploadedDocument;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface UploadedDocumentService {

    UploadedDocument save(MultipartFile multipartFile, String username);

    Integer save(UploadedDocument uploadedDocument);

    UploadedDocument findUploadedDocumentByUploadedDocumentSeq(Integer uploadedDocumentSeq);

    List<UploadedDocument> findUploadedDocumentUploadedSeq(List<Integer> uploadedDocumentSeq);

    UploadedDocument findAndWriteToResponse(Integer uploadedDocumentSeq, HttpServletResponse httpServletResponse);

    UploadedDocument loadImage(Integer uploadedDocumentSeq, HttpServletResponse httpServletResponse);

    ResponseEntity<byte[]> findAndDownload(Integer uploadedDocumentSeq);

}
