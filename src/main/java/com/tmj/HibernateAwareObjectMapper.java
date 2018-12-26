package com.tmj;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

public class HibernateAwareObjectMapper extends ObjectMapper {

    public HibernateAwareObjectMapper() {
        Hibernate4Module module = new Hibernate4Module();
        module.disable(Hibernate4Module.Feature.USE_TRANSIENT_ANNOTATION);
        registerModule(module);
    }
}
