/*
 * Copyright (c) Axway Academy, 2016. All Rights Reserved.
 */
package com.axway.academy.addressbook.sql.schema;

import java.io.Serializable;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.id.Configurable;
import org.hibernate.id.UUIDHexGenerator;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.Type;

/**
 * An id generator which uses the current id if set, or creates a new UUID if not set.
 */
public class Uuid extends UUIDHexGenerator implements Configurable {

    private String entityName;

    /**
     * Generate an id if it is not already set.  If set, use the one already set.
     *
     * @param session a database session.
     * @param obj the object for which the id is being generated.
     * @return The unique identifier for this object.
     * @throws HibernateException if there is a problem fetching the id from {@code obj}.
     */
    public Serializable generate(SessionImplementor session, Object obj) throws HibernateException {

        final Serializable id = session.getEntityPersister( entityName, obj )
                .getIdentifier( obj, session );

        if (id != null) {
            return id;
        }

        return super.generate(session, obj);
    }

    public void configure(Type type, Properties params, Dialect d)
            throws MappingException {
        entityName = params.getProperty(ENTITY_NAME);
        if (entityName==null) {
            throw new MappingException("no entity name");
        }
    }
}






