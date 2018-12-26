package com.tmj.tms.config.datalayer.service;

import com.tmj.tms.config.datalayer.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface UserService extends JpaRepository<User, Integer> {

    User getUserByUserSeq(Integer userSeq);

    User findByUserSeq(Integer userSeq);

    User findByUsername(String username);

    List<User> findByUsernameStartsWithIgnoreCase(String searchParam);

    @Query("SELECT\n" +
            "  us \n" +
            "FROM\n" +
            "  User us,\n" +
            "  GroupAuthority ga,\n" +
            "  Authority au,\n" +
            "  Group gr,\n" +
            "  UserGroup ug\n" +
            "WHERE\n" +
            "  au.authority = :ROLE\n" +
            "  AND ga.authoritySeq = au.authoritySeq\n" +
            "  AND gr.groupSeq = ga.groupSeq\n" +
            "  AND ug.groupSeq = ga.groupSeq\n" +
            "  AND ug.userSeq = us.userSeq\n")
    List<User> getUserByRole(@Param("ROLE") String role);

    List<User> findByEnabled(Integer enabled);

    List<User> findByCreatedDateBetween(Date startDate, Date endDate);
}