package com.tmj.tms.config.datalayer.service;

import com.tmj.tms.config.datalayer.modal.DocumentLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentLinkService extends JpaRepository<DocumentLink, Integer> {

    List<DocumentLink> findByModuleSeq(Integer moduleSeq);

    DocumentLink findByLinkName(String pageName);
}
