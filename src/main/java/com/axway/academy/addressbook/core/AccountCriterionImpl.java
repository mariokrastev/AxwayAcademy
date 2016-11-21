/*
 Copyright (c) 1993-2010 Axway Inc. All Rights Reserved.
 */

package com.axway.academy.addressbook.core;

import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

import com.axway.academy.addressbook.api.Account;
import com.axway.academy.addressbook.api.AccountCriterion;
import com.axway.academy.addressbook.beans.AccountBean;

/**
 * Class implementing the {@link AccountCriterion} interface.
 */
public class AccountCriterionImpl extends CriterionBase implements AccountCriterion {

    /** Serial. */
    private static final long serialVersionUID = 1L;
    
    /**
     * Default constructor.
     */
    public AccountCriterionImpl() {
        super(AccountBean.class, "_account");
    }

    /**
     * {@inheritDoc}
     */
    public AccountCriterion id(Account.Id accountId, boolean equal) {

        if (equal) {
            andExpression(Restrictions.eq("id", accountId.toString()));
        } else {
            andExpression(Restrictions.ne("id", accountId.toString()));
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public AccountCriterion enabled(boolean enabled) {

        andExpression(Restrictions.eq("disabled", Boolean.valueOf(!enabled)));
        return this;
    }
    
    /**
     * {@inheritDoc}
     */
    public AccountCriterion named(String loginname) {
        final SimpleExpression expr = Restrictions.eq("loginname", loginname);
        andExpression(expr);
        return this;
    }
    
    /**
     * {@inheritDoc}
     */
    public AccountCriterion namedNot(String loginname) {
        final SimpleExpression expr = Restrictions.ne("loginname", loginname);
        andExpression(expr);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public AccountCriterion namedIgnoringCase(String loginname) {
        final SimpleExpression expr = Restrictions.eq("loginname", loginname).ignoreCase();
        andExpression(expr);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public AccountCriterion namedLike(String loginname, String matchMode) {
        final SimpleExpression expr = Restrictions.like("loginname", loginname, getMatchMode(matchMode));
        andExpression(expr);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public AccountCriterion orderByName(boolean ascending) {

        addOrder("loginname", ascending);
        return this;
    }

    @Override
    public AccountCriterion orderByEmail(boolean ascending) {
        addOrder("email", ascending);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccountCriterion email(String email) {
        andExpression(Restrictions.eq("email", email).ignoreCase());
        return this;
    }

    @Override
    public AccountCriterion emailNotNull() {
        andExpression(Restrictions.isNotNull("email"));
        return this;
    }

}