package com.tmj.tms.config.datalayer.service;

import com.tmj.tms.config.datalayer.modal.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface DepartmentService extends JpaRepository<Department, Integer> {

    List<Department> findByStatus(Integer status);

    List<Department> findByStatusIn(Collection<Integer> statusSeqList);

    List<Department> findByDepartmentCodeContainingIgnoreCaseAndDepartmentNameContainingIgnoreCaseAndPrefixContainingIgnoreCaseAndStatusIn(String departmentCode, String departmentName, String prifix, Collection<Integer> statusSeqList);

    List<Department> findByCompanyProfileSeqAndStatus(Integer companyProfileSeq, Integer status);

    List<Department> findByCompanyProfileSeqAndModuleSeqAndStatus(Integer companyProfileSeq, Integer ModuleSeq, Integer status);

    @Query("select\n" +
            "  distinct de \n" +
            "from\n" +
            "  Department de,\n" +
            "  UserDepartment ud,\n" +
            "  User u \n" +
            "where\n" +
            "  u.userSeq = ud.userSeq \n" +
            "  and de.departmentSeq = ud.departmentSeq\n" +
            "  and u.username = :USERNAME\n" +
            "  and de.companyProfileSeq = :COMPANY_PROFILE_SEQ\n" +
            "  and de.moduleSeq = :MODULE_SEQ\n" +
            "  and de.status = :STATUS\n " +
            "  and ud.status != :USER_DEPT_STATUS " +
            "order by  de.departmentName")
    List<Department> findByCompanyProfileSeqAndModuleSeqAndStatusByUsername(@Param("COMPANY_PROFILE_SEQ") Integer companyProfileSeq,
                                                                            @Param("MODULE_SEQ") Integer ModuleSeq,
                                                                            @Param("STATUS") Integer status,
                                                                            @Param("USERNAME") String username,
                                                                            @Param("USER_DEPT_STATUS") Integer userDepartmentStatus);

    @Query("select\n" +
            "  distinct de \n" +
            "from\n" +
            "  Department de,\n" +
            "  UserDepartment ud,\n" +
            "  User u \n" +
            "where\n" +
            "  u.userSeq = ud.userSeq \n" +
            "  and de.departmentSeq = ud.departmentSeq\n" +
            "  and u.userSeq = :USER_SEQ\n" +
            "  and de.companyProfileSeq = :COMPANY_PROFILE_SEQ\n" +
            "  and de.moduleSeq = :MODULE_SEQ\n" +
            "  and de.status = :STATUS" +
            "  and ud.status = :USER_DEPT_STATUS")
    List<Department> findByCompanyProfileSeqAndModuleSeqAndStatusByUserSeq(@Param("COMPANY_PROFILE_SEQ") Integer companyProfileSeq,
                                                                           @Param("MODULE_SEQ") Integer ModuleSeq,
                                                                           @Param("STATUS") Integer status,
                                                                           @Param("USER_SEQ") Integer userSeq,
                                                                           @Param("USER_DEPT_STATUS") Integer userDepartmentStatus);

    List<Department> findByModuleSeq(Integer moduleSeq);

    @Query("select " +
            "  distinct de \n" +
            "from\n" +
            "  Department de  ,\n" +
            "  UserDepartment ud \n" +
            "where\n" +
            "de.departmentSeq = ud.departmentSeq\n" +
            "and ud.userSeq = :USER_SEQ\n" +
            "and de.companyProfileSeq = :COMPANY_PROFILE_SEQ\n" +
            "and de.status = :STATUS")
    List<Department> findByCompanyProfileSeqAndStatusByUserSeq(@Param("COMPANY_PROFILE_SEQ") Integer companyProfileSeq,
                                                               @Param("STATUS") Integer status,
                                                               @Param("USER_SEQ") Integer userSeq);

    List<Department> findByModuleSeqInAndStatusNot(List<Integer> moduelSeqList, Integer status);
}
