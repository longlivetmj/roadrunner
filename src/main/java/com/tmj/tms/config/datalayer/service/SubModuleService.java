package com.tmj.tms.config.datalayer.service;

import com.tmj.tms.config.datalayer.modal.SubModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubModuleService extends JpaRepository<SubModule, Integer> {

    SubModule findBySubModuleName(String subModuleName);

    @Query("select " +
            "   sm " +
            "from " +
            "   SubModule sm, " +
            "   Module m " +
            "where " +
            "   sm.moduleSeq = m.moduleSeq " +
            "   and sm.subModuleName = :SUB_MODULE " +
            "   and m.moduleName = :MODULE ")
    SubModule findBySubModuleNameAndModuleName(@Param("SUB_MODULE") String subModuleName,
                                               @Param("MODULE") String moduleName);

    @Query("select " +
            "   sm " +
            "from " +
            "   SubModule sm, " +
            "   Module m," +
            "   DocumentLink dl " +
            "where " +
            "   sm.moduleSeq = m.moduleSeq " +
            "   and sm.subModuleSeq = dl.subModuleSeq " +
            "   and dl.linkName = :LINK_NAME " +
            "   and m.urlPattern = :URL_PATTERN " +
            "   ")
    SubModule findByDocumentLinkNameAndModuleUrlPattern(@Param("LINK_NAME") String documentLinkName,
                                                        @Param("URL_PATTERN") String moduleUrlPattern);

    List<SubModule> findByModuleSeq(Integer moduleSeq);
}
