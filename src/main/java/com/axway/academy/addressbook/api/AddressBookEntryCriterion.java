/*
 * Copyright (c) Axway Academy, 2016. All Rights Reserved.
 */
package com.axway.academy.addressbook.api;

/**
 * An interface to specify search and order criteria for address book contact lists. The search criteria is created by
 * adding the desired criterion one at a time. Each criterion is added with an {@code and} operator to the previously
 * added criteria.
 * 
 * @see AccountManager#getAddressBookContactCriterion()
 */
public interface AddressBookEntryCriterion {

    /**
     * Add a clause to order by full name.
     * 
     * @param ascending If {@code true}, the order is ascending, otherwise, descending.
     * @return The same object with the specified criteria added.
     */
    public AddressBookEntryCriterion orderByFullName(boolean ascending);

    /**
     * Add a criterion to filter by the address book contact's by uniqueId.
     * 
     * @param uniqueId The unique id of the address book contact by which to filter.
     * @return The same object with the specified criteria added.
     */
    public AddressBookEntryCriterion uniqueId(String uniqueId);

    /**
     * Add a criterion to filter by the address book contact's fullName.
     * 
     * @param fullName The full name of the address book contact by which to filter.
     * @return The same object with the specified criteria added.
     */
    public AddressBookEntryCriterion fullName(String fullName);
    
    /**
     * Add a criterion to filter by the name of the address book contact's fullName;
     * with wildcard matching. Wildcard matching is done in anywhere in its fullName are included.
     * 
     * @param fullName
     * @return The same object with the specified criteria added.
     */
    public AddressBookEntryCriterion fullNameLike(String fullName);

    /**
     * Add a criterion to filter by the address book contact's primaryEmail.
     * 
     * @param primaryEmail The primaryEmail of the address book contact by which to filter.
     * @return The same object with the specified criteria added.
     */

    public AddressBookEntryCriterion primaryEmail(String primaryEmail);

    /**
     * Add a criterion to filter by the name of the address book contact's primaryEmail;
     * with wildcard matching. Wildcard matching is done in anywhere in its primaryEmail are included.
     * 
     * @param primaryEmail The primaryEmail of the address book contact by which to filter.
     * @return The same object with the specified criteria added.
     */
    public AddressBookEntryCriterion primaryEmailLike(String primaryEmail);
    
}
