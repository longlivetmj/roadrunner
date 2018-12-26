package com.tmj.tms.config.datalayer.service;

import com.tmj.tms.config.datalayer.modal.GroupAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupAuthorityService extends JpaRepository<GroupAuthority, Integer> {

    GroupAuthority findByGroupSeqAndAuthoritySeq(Integer groupSeq, Integer authoritySeq);

    List<GroupAuthority> findAllByGroupSeq(Integer groupSeq);
}
