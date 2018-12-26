package com.tmj.tms.config.datalayer.serviceImpl;

import com.tmj.tms.config.datalayer.modal.UserModule;
import com.tmj.tms.config.datalayer.service.UserModuleService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class UserModuleServiceImpl implements UserModuleService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public UserModule getUserModuleByUserModuleSeq(Integer userModuleSeq) {
        UserModule userModule = null;
        try {
            userModule = this.entityManager.find(UserModule.class, userModuleSeq);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userModule;
    }
}
