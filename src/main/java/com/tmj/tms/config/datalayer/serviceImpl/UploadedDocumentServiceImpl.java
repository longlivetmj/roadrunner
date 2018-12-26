package com.tmj.tms.config.datalayer.serviceImpl;

import com.tmj.tms.config.datalayer.modal.UploadedDocument;
import com.tmj.tms.config.datalayer.service.UploadedDocumentService;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

@Repository
public class UploadedDocumentServiceImpl implements UploadedDocumentService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(timeout = 600)
    public UploadedDocument save(MultipartFile multipartFile, String username) {
        UploadedDocument file = new UploadedDocument();
        try {
            if (multipartFile != null && !multipartFile.isEmpty()) {
                file.setFileData(multipartFile.getBytes());
                file.setFileName(multipartFile.getOriginalFilename().trim().replace(',', ' '));
                file.setFileType(multipartFile.getContentType());
                this.entityManager.persist(file);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return file;
    }


    @Override
    public UploadedDocument findUploadedDocumentByUploadedDocumentSeq(Integer uploadedDocumentSeq) {
        UploadedDocument uploadedDocument = null;
        try {
            uploadedDocument = this.entityManager.find(UploadedDocument.class, uploadedDocumentSeq);
        } catch (Exception e) {
            System.out.println("No file Found");
        }
        return uploadedDocument;
    }

    @Override
    public List<UploadedDocument> findUploadedDocumentUploadedSeq(List<Integer> uploadedDocumentSeq) {
        List<UploadedDocument> uploadedDocumentList = null;
        try {
            Query query = this.entityManager.createQuery("select UD from UploadedDocument UD where UD.uploadedDocumentSeq IN :UPLOADED_DOCUMENT_SEQ");
            query.setParameter("UPLOADED_DOCUMENT_SEQ", uploadedDocumentSeq);
            uploadedDocumentList = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uploadedDocumentList;
    }

    @Override
    public UploadedDocument findAndWriteToResponse(Integer uploadedDocumentSeq, HttpServletResponse httpServletResponse) {
        UploadedDocument uploadedDocument = this.findUploadedDocumentByUploadedDocumentSeq(uploadedDocumentSeq);
        try {
            byte[] data = uploadedDocument.getFileData();
            InputStream in = new ByteArrayInputStream(data);
            httpServletResponse.setContentLength(data.length);
            httpServletResponse.setContentType(uploadedDocument.getFileType());
            httpServletResponse.setHeader("Content-Disposition", "attachment; filename=" + uploadedDocument.getFileName());
            IOUtils.copy(in, httpServletResponse.getOutputStream());
            httpServletResponse.getOutputStream().flush();
            httpServletResponse.getOutputStream().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uploadedDocument;
    }

    @Override
    public UploadedDocument loadImage(Integer uploadedDocumentSeq, HttpServletResponse httpServletResponse) {
        UploadedDocument uploadedDocument = this.findUploadedDocumentByUploadedDocumentSeq(uploadedDocumentSeq);
        try {
            byte[] data = uploadedDocument.getFileData();
            InputStream in = new ByteArrayInputStream(data);
            httpServletResponse.setContentLength(data.length);
            httpServletResponse.setContentType(uploadedDocument.getFileType());
            httpServletResponse.setHeader("Cache-Control", "max-age=86400");
            httpServletResponse.setHeader("Content-Disposition", "attachment; filename=" + uploadedDocument.getFileName());
            IOUtils.copy(in, httpServletResponse.getOutputStream());
            httpServletResponse.getOutputStream().flush();
            httpServletResponse.getOutputStream().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uploadedDocument;
    }

    @Override
    public ResponseEntity<byte[]> findAndDownload(Integer uploadedDocumentSeq) {
        try {
            UploadedDocument uploadedDocument = this.findUploadedDocumentByUploadedDocumentSeq(uploadedDocumentSeq);
            if (uploadedDocument.getFileData() != null && uploadedDocument.getFileData().length != 0) {
                InputStream in = new ByteArrayInputStream(uploadedDocument.getFileData());
                final HttpHeaders headers = new HttpHeaders();

                headers.set("Content-disposition", " filename=" + uploadedDocument.getFileName());
                headers.setContentType(MediaType.valueOf(uploadedDocument.getFileType()));
                return new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<byte[]>(null, null, HttpStatus.CREATED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<byte[]>(null, null, HttpStatus.CREATED);
        }
    }


    @Override
    @Transactional(timeout = 600)
    public Integer save(UploadedDocument uploadedDocument) {
        Integer uploadedDocumentSeq = null;
        try {
            this.entityManager.persist(uploadedDocument);
            uploadedDocumentSeq = uploadedDocument.getUploadedDocumentSeq();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uploadedDocumentSeq;
    }

}
