/*
 Copyright (c) 1993-2012 Axway Inc. All Rights Reserved.
 */
package com.axway.academy.addressbook.core;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.axway.academy.addressbook.api.Account;
import com.axway.academy.addressbook.api.AddressBookEntryCriterion;
import com.axway.academy.addressbook.beans.AddressBookEntryBean;

/**
 * Search criterion for the AddressBookContact.
 * 
 * @author zrusinov
 * 
 */
public class AddressBookEntryCriterionImpl extends CriterionBase implements AddressBookEntryCriterion {

    /** Serial. */
    private static final long serialVersionUID = 1L;
    
    /**
     * Default constructor.
     */
    public AddressBookEntryCriterionImpl(Account.Id accountId) {
        super(AddressBookEntryBean.class, "_addressBookContact");
        andExpression(Restrictions.eq("mAccountId", accountId.toString()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AddressBookEntryCriterion orderByFullName(boolean ascending) {

        addOrder("mFullName", ascending);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AddressBookEntryCriterion uniqueId(String uniqueId) {
        andExpression(Restrictions.eq("mUniqueId", uniqueId));
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AddressBookEntryCriterion fullName(String fullName) {
        if (fullName != null) {
            andExpression(Restrictions.eq("mFullName", fullName));
        }
        return this;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public AddressBookEntryCriterion fullNameLike(String fullName) {
        if (fullName != null) {
            andExpression(Restrictions.like("mFullName", fullName, MatchMode.ANYWHERE));
        }
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AddressBookEntryCriterion primaryEmail(String primaryEmail) {
        if (primaryEmail != null) {
            andExpression(Restrictions.eq("mPrimaryEmail", primaryEmail));
        }
        return this;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public AddressBookEntryCriterion primaryEmailLike(String primaryEmail) {
        if (primaryEmail != null) {
            andExpression(Restrictions.like("mPrimaryEmail", primaryEmail, MatchMode.ANYWHERE));
        }
        return this;
    }
    
}
