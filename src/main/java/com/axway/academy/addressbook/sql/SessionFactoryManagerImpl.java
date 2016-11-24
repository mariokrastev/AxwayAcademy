/*
 * Copyright (c) Axway Academy, 2016. All Rights Reserved.
 */

package com.axway.academy.addressbook.sql;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Provides an implementation of methods to manage a hibernate SessionFactory object.
 */
class SessionFactoryManagerImpl implements SessionFactoryManager {

    /** aggregated hibernate session factory. */
    private SessionFactory mSessionFactory;

    /** logger for this class. */
    private static final Logger sLogger = Logger.getLogger(SessionFactoryManagerImpl.class);

    /** Used to synchronize access to the session factory. */
    private static final ReadWriteLock mLock = new ReentrantReadWriteLock();
    
    /**
     * Configure SessionFactory based on a class name configuration only.
     * 
     * @param cfgFile Hibernate configuration document representation
     * @param componentType component type
     */
    public SessionFactoryManagerImpl(String cfgFile) {
        super();
        try {
            mSessionFactory = new Configuration().configure(cfgFile ).buildSessionFactory();
        } catch (HibernateException e) {
            System.err.println("Failed to create sessionFactory object." + e);
        } catch (Exception ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex); 
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionFactory setSessionFactory(SessionFactory newSessionFactory) {
        mLock.writeLock().lock();
        try {
            SessionFactory result = mSessionFactory;
            mSessionFactory = newSessionFactory;
            return result;
        } finally {
            mLock.writeLock().unlock();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionFactory getSessionFactory() {
       mLock.readLock().lock();
       try {         
            return mSessionFactory;
        } finally {
            mLock.readLock().unlock();
        }
        
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void closeSessionFactory() {
        if (mSessionFactory != null && !mSessionFactory.isClosed()) {
            try {
                mSessionFactory.close();
            } catch (HibernateException he) {
                sLogger.warn("Exception occurred while closing the old session factory.", he);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void closeSession( final Session session ) {
        SessionManagerFactory.closeSession( session );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Session openSession() {
        return mSessionFactory.openSession();
    }

}
