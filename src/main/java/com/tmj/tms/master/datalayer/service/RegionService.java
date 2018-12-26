package com.tmj.tms.master.datalayer.service;

import com.tmj.tms.master.datalayer.modal.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegionService extends JpaRepository<Region, Integer> {
    List<Region> findByStatus(Integer status);

    List<Region>findByRegionNameStartsWithIgnoreCaseAndStatus(String searchParam, Integer status);

}
