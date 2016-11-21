/*
 * Copyright (c) Axway Academy, 2016. All Rights Reserved.
 */

package com.axway.academy.addressbook.api;

import java.io.Serializable;
import java.util.Set;

import com.axway.academy.addressbook.beans.AddressBookEntryBean;

/**
 * Public Account Interface.
 * 
 */
public interface Account extends Serializable {
    /** Maximum length, in characters, of the loginname. */
    int MAX_NAME_LENGTH = 80;
    /** Maximum length, in characters, of the email address. */
    int MAX_EMAIL_LENGTH = 255;
    /** Maximum length, in characters, of the phone number. */
    int MAX_PHONE_LENGTH = 80;
    
    /**
     * Get the unique loginname of the account.
     * 
     * @return Unique loginname of this account instance.
     */
    String getLoginname();

    /**
     * Set the unique loginname of the account.
     * 
     * @param loginname unique name of the account.
     */
    void setLoginname(String loginname);

    /**
     * Get the email address of the account's contact.
     * 
     * @return The contact's email address.
     */
    String getEmail();

    /**
     * Set the email address of the account's contact.
     * 
     * @param email the contact's email address.
     * @throws IllegalArgumentException if the format of the email address is invalid.
     */
    void setEmail(String email);

    /**
     * Get the phone number of the account's contact.
     * 
     * @return The contact's phone number.
     */
    String getPhone();

    /**
     * Set the phone number of the account's contact.
     * 
     * @param phone the contact's phone number.
     */
    void setPhone(String phone);

    /**
     * Test if this account is disabled. If the account is disabled:
     * <ul>
     * <li> Subscriptions for the account will not trigger.
     * <li> Users associated with the account will not be able to login and perform any transfers.
     * </ul>
     * 
     * @return {@code true} if the account is disabled.
     */
    boolean isDisabled();

    /**
     * Get the object representing the unique identity of this Account.
     *
     * @return The object which uniquely identifies the persisted Account
     *         or {@code null} if this is not a persisted Account.
     */
    Account.Id getId();

    /**
     * Interface representing the unique identity of a persisted Account.
     * @see Account#getId()
     */
    interface Id extends Serializable {
        /* Interface has no methods defined. */
    }

    void setAddressBookEntries(Set<AddressBookEntryBean> addressBookEntries);

    Set<AddressBookEntryBean> getAddressBookEntries();

    String getFullname();

    void setFullname(String fullname);

    void setDisabled(boolean isDisabled);

    void addAddressBookContact(AddressBookEntryBean entry);

    void removeAddressBookContact(AddressBookEntryBean entry);
    
}
