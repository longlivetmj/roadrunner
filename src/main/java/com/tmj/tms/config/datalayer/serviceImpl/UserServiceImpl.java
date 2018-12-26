package com.tmj.tms.config.datalayer.serviceImpl;

import com.tmj.tms.config.datalayer.modal.User;
import com.tmj.tms.config.datalayer.service.UserService;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public abstract class UserServiceImpl implements UserService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User getUserByUserSeq(Integer userSeq) {
        return this.entityManager.find(User.class, userSeq);
    }
}
