/*
 * Copyright (c) Axway Academy, 2016. All Rights Reserved.
 */
package com.axway.academy.addressbook.sql;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

/**
 * Provides a SessionFactoryManageror factory object, locating existing named SessionFactoryManager if already existing
 * or create a new one and register it.
 * 
 */
public final class SessionManagerFactory {

    /** Hibernate configuration file name. */
    private static final String CONFIG_FILE = "/resources/configuration.xml";

    private static final SessionManagerFactory INSTANCE = new SessionManagerFactory();

    /**
     * Gets the singleton instance.
     * 
     * @return the sole instance of {@code SessionManagerFactory}
     */
    public static SessionManagerFactory getInstance() {
        return INSTANCE;
    }
    
    /**
     * Close this Hibernate Session, rolling back the transaction if not committed. 
     * @param session to close
     */
    public static void closeSession( final Session session ) {
        if (session != null  ) {
            final Transaction tx = session.getTransaction();
            if ( tx != null && tx.getStatus() == TransactionStatus.ACTIVE ) {
                tx.rollback();
            }
            session.close();
        }
    }

    /**
     * Hide constructor.
     */
    private SessionManagerFactory() {
        super();
    }

    /**
     * @return a default SessionFactoryManager for this component, returning an existing one or a new one if one doesn't
     *         already exist
     */
    public SessionFactoryManager getSessionFactoryManager() {
        SessionFactoryManagerImpl sessionFactoryManager =  new SessionFactoryManagerImpl(CONFIG_FILE);
        return sessionFactoryManager;
    }

}
