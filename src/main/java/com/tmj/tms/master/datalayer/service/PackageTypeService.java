package com.tmj.tms.master.datalayer.service;

import com.tmj.tms.master.datalayer.modal.PackageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PackageTypeService extends JpaRepository<PackageType, Integer> {

    PackageType findByPackageTypeCodeContainingIgnoreCase(String packageTypeCode);

    List<PackageType> findByStatusIn(Collection<Integer> statusSeqList);

    List<PackageType> findByPackageTypeNameContainingIgnoreCaseAndPackageTypeCodeContainingIgnoreCaseAndDescriptionContainingIgnoreCaseAndStatusIn(String packageTypeName,
                                                                                                                                                   String packageTypeCode,
                                                                                                                                                   String description,
                                                                                                                                                   Collection<Integer> statusSeqList);

    List<PackageType> findByPackageTypeNameStartsWithIgnoreCaseAndStatus(String packageTypeName, Integer status);

    List<PackageType> findByStatus(Integer status);

    List<PackageType> findByStatusOrderByPackageTypeNameAsc(Integer status);

    PackageType findByAsycudaCodeAndStatus(String packageTypeCode, Integer status);

    PackageType findByPackageTypeCodeAndStatus(String packageTypeCode, Integer status);
}
