/*
 * Copyright (c) Axway Academy, 2016. All Rights Reserved.
 */

package com.axway.academy.addressbook.core;


import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.axway.academy.addressbook.api.exceptions.AccountConstraintException;
import com.axway.academy.addressbook.beans.AccountBean;

/**
 * Utility class to validate constraints on application, subscription and transfer objects.
 * Methods throw relevant constraint exceptions. The exception may contain one or more flags indicating violations.
 * Checks are made prior to making the change to avoid interpreting constraint violation exceptions.
 */
final class ConstraintValidationUtil {

    /**
     * Private constructor to avoid creating instances of util class.
     */
    private ConstraintValidationUtil() {

    }


    /**
     * Check if the loginname is unique, the home folder is valid and
     * the html template is one of the installed html templates.
     * @param session - an open session.
     * @param account - the account to be checked.
     * @throws AccountConstraintException if the account is incorrect.
     */
    static void validateNewAccount(Session session, AccountBean account) throws AccountConstraintException {
        @SuppressWarnings("serial")
        CriterionBase criterion = new CriterionBase(AccountBean.class) {};
        criterion.andExpression(Restrictions.eq("loginname", account.getLoginname()));
        boolean uniqueAccountName = !criterion.exists(session);

        if(!uniqueAccountName ) {
            throw new AccountConstraintException( uniqueAccountName);
        }

    }

    /**
     * Check whether the loginname is unique,
     * the associated business unit and the home folder are valid and
     * the html template is one of the installed html templates.
     * @param session - an open session.
     * @param account - the account to check.
     * @throws AccountConstraintException if the account is incorrect.
     * or the home folder is not valid.
     */
    static void validateUpdateAccount(Session session, AccountBean account)
        throws AccountConstraintException {

        @SuppressWarnings("serial")
        CriterionBase criterion = new CriterionBase(AccountBean.class) {};
        criterion.andExpression(Restrictions.ne("id", account.getId().toString()));
        criterion.andExpression(Restrictions.eq("loginname", account.getLoginname()));
        boolean uniqueAccountName = !criterion.exists(session);

        if(!uniqueAccountName ) {
            throw new AccountConstraintException( uniqueAccountName);
        }
    }

    
    
}
