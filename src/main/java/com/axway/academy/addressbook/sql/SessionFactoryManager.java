/*
 * Copyright (c) Axway Academy, 2016. All Rights Reserved.
 */

package com.axway.academy.addressbook.sql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Defines a set of methods to manage hibernate SessionFcatory objects.
 */
public interface SessionFactoryManager {

    
    /**
     * Sets a new hibernate SessionFactory object, returning the previous SessionFactory object or null
     * 
     * @param newSessionFactory
     * @return
     */
    SessionFactory setSessionFactory(SessionFactory newSession);

    /**
     * @return hibernate SessionFactory object for this implementation
     */
    SessionFactory getSessionFactory();

    
    /**
     * Close this Hibernate Session, rolling back the transaction if not committed.
     * Calls SessionManagerFactory.closeSession() 
     * @param session to close
     * @see SessionManagerFactory.closeSession()
     */
    void closeSession( Session session );
    
    /**
     * Close the hibernate SessionFactory object associated with this implementation if sessionFactory is set and is
     * connected
     */
    void closeSessionFactory();

    /**
     * Open a session to the database.
     * @return {@link Session} instance
     */
    Session openSession();

}
