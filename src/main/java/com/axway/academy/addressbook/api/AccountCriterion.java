/*
 * Copyright (c) Axway Academy, 2016. All Rights Reserved.
 */
package com.axway.academy.addressbook.api;

/**
 * An interface to specify search and order criteria for account lists. The search criteria is created by adding the
 * desired criterion one at a time. Each criterion is added with an {@code and} operator to the previously added
 * criteria.
 * 
 * @see AccountManager#getAccountCriterion()
 */
public interface AccountCriterion {

    /**
     * Add a criterion to filter by an account id of any type.
     * 
     * @param accountId the id of an account.
     * @param equal {@code true} to filter for the account with an id equal to the supplied account id, otherwise,
     *            filter for accounts without this id.
     * @return The same object with the specified criterion added.
     */
    AccountCriterion id(Account.Id accountId, boolean equal);

    /**
     * Add a criterion to filter enabled or disabled accounts. If not set, both enabled and disabled accounts are
     * included in the results.
     * 
     * @param enabled {@code true} to include only enabled accounts, {@code false} to include only disabled accounts.
     * 
     * @return The same object with the specified criterion added.
     */
    AccountCriterion enabled(boolean enabled);
    
    
    /**
     * Add a criterion to filter by the name of the account. The default behavior is to exclude template accounts.
     * 
     * @param name the name of the account. Account names are case insensitive.
     * 
     * @return The same object with the specified criterion added.
     */
    AccountCriterion named(String name);

    /**
     * Add a criterion to filter by the name of the account ignoring upper or lower case. The default behavior is to
     * exclude template accounts.
     * 
     * @param name the name of the account. Account names are case insensitive.
     * 
     * @return The same object with the specified criterion added.
     */
    AccountCriterion namedIgnoringCase(String name);

    /**
     * Add a criterion to filter by the name of the account; with wildcard matching. Wildcard matching is done in
     * several ways as determined by the matchMode parameter.
     * 
     * @param matchString a string of characters used for matching accont names.
     * @param matchMode is one of:
     *            <ul>
     *            <li>"START": accounts starting with {@code matchString} in its account name are included.
     *            <li>"END": accounts ending with {@code matchString} in its account name are included.
     *            <li>"ANYWHERE": accounts with {@code matchString} anywhere in its account name are included.
     *            <li>"EXACT": accounts with a name matching {@code matchString} are included. The {@code matchString}
     *            parameter may contain the wildcard character '{@code %}', which will be expanded.
     *            </ul>
     * 
     * @return The same object with the specified criterion added.
     */
    AccountCriterion namedLike(String matchString, String matchMode);

    /**
     * Add a criterion to sort the results by the name of the account. If not set, the result is unordered.
     * 
     * @param ascending {@code true} for ascending order, {@code false} for descending.
     * 
     * @return The same object with the specified criterion added.
     */
    AccountCriterion orderByName(boolean ascending);

    /**
     * Add a criterion to sort the results by the email of the account. If not set, the result is unordered.
     * 
     * @param ascending {@code true} for ascending order, {@code false} for descending.
     * 
     * @return The same object with the specified criterion added.
     */
    AccountCriterion orderByEmail(boolean ascending);

    /**
     * Add a criterion to filter by the e-mail address.
     * 
     * @param email the e-mail address. Case insensitive. Cannot be null.
     * 
     * @return The same object with the specified criterion added.
     */
    AccountCriterion email(String email);
    
    /**
     * Add a criterion to filter by accounts which email address is not null.
     * 
     * @return The same object with the specified criterion added.
     */
    AccountCriterion emailNotNull();

}
