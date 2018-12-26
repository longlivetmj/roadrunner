package com.tmj.tms.master.datalayer.service;

import com.tmj.tms.master.datalayer.modal.ChargeMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChargeModeService extends JpaRepository<ChargeMode, Integer> {

    List<ChargeMode> findByChargeSeq(Integer chargeSeq);

}
