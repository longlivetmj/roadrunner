package com.tmj.tms.config.datalayer.service;

import com.tmj.tms.config.datalayer.modal.ReportImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportImageService extends JpaRepository<ReportImage, Integer> {

    List<ReportImage> findByCompanyProfileSeq(Integer companyProfileSeq);

}
