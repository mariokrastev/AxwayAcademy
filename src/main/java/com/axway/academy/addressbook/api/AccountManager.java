/*
 * Copyright (c) Axway Academy, 2016. All Rights Reserved.
 */
package com.axway.academy.addressbook.api;

import java.util.List;

import com.axway.academy.addressbook.api.exceptions.AccountConstraintException;
import com.axway.academy.addressbook.api.exceptions.DuplicateAccountException;
import com.axway.academy.addressbook.api.exceptions.DuplicateAddressBookEntryException;
import com.axway.academy.addressbook.api.exceptions.NoSuchAccountException;
import com.axway.academy.addressbook.api.exceptions.NoSuchAddressBookEntryException;
import com.axway.academy.addressbook.api.exceptions.PasswordPolicyException;
import com.axway.academy.addressbook.beans.AddressBookEntryBean;

public interface AccountManager {

    /**
     * Get the account for the given {@code Account.Id}.
     * 
     * @param accountId the identifier of the desired account.
     * @return The account with the specified identifier.
     * @throws NoSuchAccountException if the account does not exist.
     */
    Account getAccount(Account.Id accountId) throws NoSuchAccountException;

    /**
     * Get the account with the given name. An account of type {@link Account#ACCOUNT_TEMPLATE} is NOT returned by this
     * method.
     * 
     * @param name the name of the account to find.
     * @return The account with the given name.
     * @throws NoSuchAccountException if an account by this name does not exist.
     * @see AccountCriterion
     */
    Account getAccount(String name) throws NoSuchAccountException;
    
    /**
     * Create a new {@code Account.Id} from a UUID. The UUID must be the {@code String} returned by a call to the
     * {@code toString} method of {@code Account.Id}.
     * @param uuid string representation of an account identifier produced by the {@code toString} method of
     * {@code Account.Id}.
     * @return A newly created {@code Account.Id} with the given UUID.
     * @throws IllegalArgumentException if {@code uuid} is null or malformed.
     */
    Account.Id newAccountId(String uuid);
    
    /**
     * Persist a new account and a user associated with the account.
     * 
     * @param account the new {@code Account} object that holds the account's attributes.
     * @return The newly persisted {@code Account} object.
     * @throws DuplicateAccountException if the specified account already exists.
     * @throws DuplicateUserException if the specified user already exists.
     * @throws PasswordPolicyException if the passphrase set for the user does not fulfill the password policy
     *             requirements.
     * @throws AccountConstraintException if the account home folder is not valid.
     */
    Account createAccount(Account account) throws DuplicateAccountException,
            PasswordPolicyException, AccountConstraintException;

    /**
     * Persist changes to an existing accounts's attributes.
     * 
     * @param account the {@code Account} object with the new attributes to persist.
     * @param user a {@code User} object for a user associated with the account. May be null if there are no changes to
     *            the user's attributes.
     * @throws NoSuchAccountException if the specified account does not exist.
     * @throws DuplicateAccountException if the account name cannot be changed because there is another existing account
     *             that has the same name.
     * @throws NoSuchUserException if the specified user does not exist.
     * @throws DuplicateUserException if a user with the same login name already exists.
     * @throws PasswordPolicyException if a new user passphrase is specified and does not fulfill the password policy
     *             requirements.
     * @throws AccountConstraintException if the business unit associated with the account is not valid.
     */
    void updateAccount(Account account) throws NoSuchAccountException, DuplicateAccountException,
            PasswordPolicyException, AccountConstraintException;

    /**
     * Delete the account with the given identifier and all objects associated with the account.
     * 
     * @param accountId the identifier of the account to delete.
     * @throws NoSuchAccountException if the specified account does not exist.
     * @throws ObjectInUseException if the account object is referenced and a foreign key constraint applies.
     */
    void deleteAccount(Account.Id accountId) throws NoSuchAccountException;


    /**
     * Get a newly instantiated implementation of an {@code AccountCriterion} for use in filtering and sorting a set of
     * accounts matching some criteria.
     * 
     * @return A newly instantiated class implementing {@code AccountCriterion}.
     * @see #getAccounts(AccountCriterion)
     * @see #getAccountCount(AccountCriterion)
     * @see #getAccounts(AccountCriterion, int, int)
     */
    AccountCriterion getAccountCriterion();

    /**
     * Get a collection of accounts with a specified criteria.
     * 
     * @param criterion specifies the criteria by which the account objects are to be filtered and ordered. If the
     *            criterion is {@code null}, an unordered collection of accounts with no filtering is returned.
     * @param first the index of the first account to return.
     * @param count the maximum number of accounts to return.
     * @return A collection of account objects matching the criteria or an empty list if no accounts matched the
     *         criteria.
     */
    List<Account> getAccounts(AccountCriterion criterion, int first, int count);

    /**
     * Get a collection of accounts with a specified criteria.
     *
     * @param criterion specifies the criteria by which the account objects are to be filtered and ordered. If
     * {@code criterion} is {@code null}, an unordered collection of accounts with no filtering is returned.
     * @return A collection of {@code Account} objects matching the criteria; the collection may be empty.
     */
    List<Account> getAccounts(AccountCriterion criterion);

    /**
     * Get a count of the total number of accounts matching the specified criteria.
     * 
     * @param criterion specifies the criteria used to filter the accounts included in the count.
     * @return The total number of accounts matching the criteria.
     */
    int getAccountCount(AccountCriterion criterion);

    /**
     * Disable the account with the given identifier.
     * 
     * @param accountId the identifier of the account to be disabled.
     * @throws NoSuchAccountException if the specified account does not exist.
     */
    void disableAccount(Account.Id accountId) throws NoSuchAccountException;

    /**
     * Enable the account with the given identifier.
     * 
     * @param accountId the identifier of the account to be enabled.
     * @throws NoSuchAccountException if the specified account does not exist.
     */
    void enableAccount(Account.Id accountId) throws NoSuchAccountException;

    /**
     * Persist a new address book contact associated with an account.
     *
     * @param accountName the identifier of the account to which the address book contact will be added.
     * @param contact the contact to add to the account.
     * @return the new address book contact
     * @throws NoSuchAccountException the no such account exception
     * @throws DuplicateAddressBookEntryException the duplicate address book contact exception
     */
    AddressBookEntry createAddressBookEntry(String accountName, AddressBookEntry addressBookEntry)
            throws NoSuchAccountException, DuplicateAddressBookEntryException;

    /**
     * Get a newly instantiated implementation of an {@code AddressBookContactCriterion} for use in filtering and
     * sorting a set of AddressBookContact matching some criteria.
     * 
     * @param accountName the identifier of the account for which the address book contacts will be retrieved.
     * 
     * @return A newly instantiated class implementing {@code AddressBookContactCriterion}.
     * 
     * @throws NoSuchAccountException if the specified account does not exist.
     */
    AddressBookEntryCriterion getAddressBookContactCriterion(String accountName) throws NoSuchAccountException;

    /**
     * Get the collection of address book contacts which are associated with an account.
     * 
     * @param criterion The AddressBookContactCriterion.
     * @param first The first element to show.
     * @param count The max element to show.
     * @return A collection of {@code AddressBookContact} objects matching the criteria; the collection may be empty.
     * @throws NoSuchAccountException if the specified account does not exist.
     */
    List<AddressBookEntry> getAddressBookContacts(AddressBookEntryCriterion criterion, Integer first, Integer count)
            throws NoSuchAccountException;

    /**
     * Gets the address book contact by id.
     *
     * @param accountId the identifier of the account for which the address book contacts will be retrieved.
     * @param addressBookContactId The address book id
     * @return {@code AddressBookContact} objects matching the criteria;
     * @throws NoSuchAccountException if the specified account does not exist.
     * @throws NoSuchAddressBookEntryException if the specified address book contact does not exist.
     */
    AddressBookEntry getAddressBookEntryById(String accountId, String addressBookContactId)
            throws NoSuchAccountException, NoSuchAddressBookEntryException;

    /**
     * Persist changes to an existing AddressBookContact's attributes.
     * 
     * @param contact the {@code AddressBookContact} object with the new attributes to persist.
     * @throws NoSuchAddressBookEntryException if the specified AddressBookContact does not exist.
     * @throws DuplicateAddressBookEntryException if a AddressBookContact with the same full name already exists.
     */
    void updateAddressBookEntry(AddressBookEntryBean entry) throws NoSuchAddressBookEntryException,
            DuplicateAddressBookEntryException;

    /**
     * Delete the AddressBookContact with the given identifier from the account.
     * 
     * @param contactId the identifier of the AddressBookContact to remove.
     * @throws NoSuchAddressBookEntryException if the specified AddressBookContact does not exist.
     */
    void deleteAddressBookEntry(String deleteAddressBookEntry) throws NoSuchAddressBookEntryException;

    /**
     * Gets all the virtual users' accounts that have the specified e-mail address.
     * 
     * @param email the address to search with. Cannot be null.
     * @return the accounts found. Empty list will be returned if no account is found.
     */
    List<Account> getAccountsByEmail(String email);

    /**
     * Gets all the virtual users' accounts that have at least one of the specified e-mail addresses.
     * 
     * @param emails the addresses to search with. Cannot be null.
     * @return the accounts found. Empty list will be returned if no account is found.
     */
    List<Account> getAccountsByEmails(List<String> emails);

    Account getAccountByAccountName(String name) throws NoSuchAccountException;


}
