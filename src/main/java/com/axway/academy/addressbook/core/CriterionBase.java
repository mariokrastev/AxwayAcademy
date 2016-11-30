/*
 * Copyright (c) Axway Academy, 2016. All Rights Reserved.
 */

//!!!When changing the package name change the pointcut in the conf/jboss-aop.xml as well!!!
package com.axway.academy.addressbook.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

/**
 * Subclass this class when implementing a class to construct a set of criteria for filtering and sorting a list of
 * results from a Manager.
 */
//!!!When changing the class name change the pointcut in the conf/jboss-aop.xml as well!!!
public abstract class CriterionBase implements Serializable {

    /**
     * 
     */

    /**
     * 
     */
    private static final long serialVersionUID = 4732767178559486082L;

    /** Ensure consistency during deserialization. */
    private Class<?> mBeanClass;

    private String mAlias;

    private Criterion mCriteria;

    private Collection<Order> mOrder = new ArrayList<Order>();

    private Map<String, String> mAliases = new HashMap<String, String>();

    private List<Criterion>  mSubqueries = new ArrayList<Criterion>();

    private Map<String,FetchMode> mFetchModes = new HashMap<String,FetchMode>();

    protected static final HashMap<String, MatchMode> sMatchModes = new HashMap<String, MatchMode>();

    static {
        sMatchModes.put(MatchMode.ANYWHERE.toString(), MatchMode.ANYWHERE);
        sMatchModes.put(MatchMode.EXACT.toString(), MatchMode.EXACT);
        sMatchModes.put(MatchMode.END.toString(), MatchMode.END);
        sMatchModes.put(MatchMode.START.toString(), MatchMode.START);
    }

    /**
     * Create a new <tt>CriterionBase</tt> instance, for the given entity class.
     *
     * @param beanClass the class on which this criteria is applied.
     */
    public CriterionBase(Class<?> beanClass) {
        mBeanClass = beanClass;
    }

    /**
     * Create a new <tt>CriterionBase</tt> instance, for the given entity class, with the given alias.
     *
     * @param beanClass the class on which this criteria is applied.
     * @param alias a short name for use in the queries.
     */
    public CriterionBase(Class<?> beanClass, String alias) {
        mBeanClass = beanClass;
        mAlias = alias;
    }

    /**
     * Get a MatchMode object by name. A MatchMode is used by the LIKE excpressions to determine the type of wildcard
     * matching to do on a string.
     *
     * @param name the name of the MatchMode. ("START", "END", "ANYWHERE" or "EXACT")
     * @return the requested MatchMode object.
     */
    public MatchMode getMatchMode(String name) {
        return sMatchModes.get(name);
    }

    /**
     * Get the number of object matching the criteria specified by this object.
     *
     * @param session an object representing a connection to the database.
     * @return the number of objects matching the criteria.
     */
    public int getCount(Session session) {
        // Get the criteria without the order clause. It is not needed and can affect performance if included.
        Criteria criteria = getCriteria(session, false).setProjection(Projections.rowCount());
        Long count = (Long) criteria.uniqueResult();
        return count.intValue();
    }

    /**
     * Creates Criteria for existence of at least one matching object.
     * @param session an object representing a connection to the database.
     * @return a new critera object
     */
    public Criteria getExistsCriteria(Session session) {
        // select id from Account where exists (SELECT id FROM table) limit 1
        DetachedCriteria dc = DetachedCriteria.forClass(mBeanClass);
        dc.setProjection(Projections.id());
        finishCriterion();
        dc.add(mCriteria);
        Criteria criteria = session.createCriteria(mBeanClass).add(Subqueries.exists(dc));
        criteria.setProjection(Projections.id()).setMaxResults(1);

        return criteria;
    }

    /**
     * Test for the existence of at least one matching object.
     *
     * @param session an object representing a connection to the database.
     * @return true if at least one matching object exists.
     */
    public boolean exists(Session session) {
        Criteria criteria = getExistsCriteria(session);
        return criteria.uniqueResult() != null;
    }

    /**
     * Get a criteria object with or without the order clause.
     *
     * @param session an object representing a connection to the database.
     * @param doOrder flag whether the order clause is to be included.
     * @return a new critera object.
     */
    public Criteria getCriteria(Session session, boolean doOrder) {
        //!!!When changing the signature change the pointcut in the CacheabelQueryAspect
        Criteria criteria;
        if (mAlias != null) {
            criteria = session.createCriteria(mBeanClass, mAlias);
        } else {
            criteria = session.createCriteria(mBeanClass);
        }
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        finishCriterion();
        if (mCriteria != null) {
            criteria.add(mCriteria);
        }
        if (doOrder) {
            Iterator<Order> i = mOrder.iterator();
            while (i.hasNext()) {
                criteria.addOrder(i.next());
            }
        }


        for (Entry<String, String> aliasEntry : mAliases.entrySet()) {
            criteria.createAlias(aliasEntry.getKey(), aliasEntry.getValue());
        }

        for(Criterion subquery : mSubqueries){
            criteria.add(subquery);
        }

        for(Entry<String,FetchMode> fetchMode : mFetchModes.entrySet()){
            criteria.setFetchMode(fetchMode.getKey(),fetchMode.getValue());
        }


        return criteria;
    }

    /**
     * Get a criteria object which may be used to execute a query to retrieve the results.
     *
     * @param session an object representing a connection to the database.
     * @return a new critera object.
     */
    public Criteria getCriteria(Session session) {
        return getCriteria(session, true);
    }

    protected void finishCriterion() {
    }

    /**
     * Add a criterion to the set of restrictions by which the results is filtered. Multiple criteria may be added
     * resulting in a sequence of "and" expressions.
     *
     * @param criterion the criterion to "and" with the existing criteria expression.
     */
    public void andExpression(Criterion criterion) {
        if (mCriteria != null) {
            mCriteria = Restrictions.and(mCriteria, criterion);
        } else {
            mCriteria = criterion;

        }
    }


    /**
     * Add a criterion to the set of restrictions by which the results is filtered. Multiple criteria may be added
     * resulting in a sequence of "or" expressions.
     *
     * @param criterion the criterion to "or" with the existing criteria expression.
     */
    public void orExpression(Criterion criterion) {
        if (mCriteria != null) {
            mCriteria = Restrictions.or(mCriteria, criterion);
        } else {
            mCriteria = criterion;
        }
    }

    /**
     * Add a property to the order criteria. If multiple properties are added, the results are ordered with regards to
     * the order in which the properties are added.
     *
     * @param property the name of the bean propery to order by.
     * @param ascending true if the results should be ordered in ascending order, false if decending order is desired.
     */
    public void addOrder(String property, boolean ascending) {
        if (ascending) {
           addOrder(Order.asc(property));
        } else {
            addOrder(Order.desc(property));
        }
    }

    /**
     * Add a property to the order criteria. If multiple properties are added, the results are ordered with regards to
     * the order in which the properties are added.
     *
     * @param order the order to add
     */
    public void addOrder(Order order) {
        mOrder.add(order);
    }

    /**
     * Returns current criterion for the specified criteria.
     *
     * @return Returns current criterion.
     */
    protected Criterion getCriterion() {
        return mCriteria;
    }


    /**
     * Creates and alias to be used in criteria. Gives the possibility to join tables.
     *
     * @param property the property
     * @param alias the alias
     */
    protected void addAlias(String property, String alias) {
        mAliases.put(property, alias);
    }


    /**
     * Add subquery to current criteria
     *
     * @param subquery - subquery criterion
     */

    protected void addSubquery(Criterion subquery){
        mSubqueries.add(subquery);
    }

    public void setFetchMode(String property,FetchMode mode){
        mFetchModes.put(property, mode) ;
    }


}
