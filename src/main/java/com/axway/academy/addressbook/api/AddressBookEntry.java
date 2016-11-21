/*
 * Copyright (c) Axway Academy, 2016. All Rights Reserved.
 */
package com.axway.academy.addressbook.api;

import java.io.Serializable;

/**
 * Public Address Book Global Source interface.
 */
public interface AddressBookEntry extends Serializable {

    /**
     * Gets the email of this AB Entry.
     *
     * @return The email of this AB Entry.
     */
    String getEmail();

    /**
     * Sets the email of this AB Entry.
     *
     * @param email The Email to set to this AB Entry.
     */
    void setEmail(String email);

    /**
     * Check whether this AB entry is enabled/disabled.
     * 
     * @return - <b>true</b> if AB is enabled, <b>false</b> otherwise.
     */
    Boolean getEnabled();

    /**
     * Change status of this AB entry to enabled/disable.
     * 
     * @param isEnabled - <b>true</b> for enabling, <b>false</b> for disabling.
     */
    void setEnabled(Boolean isEnabled);
    
    /**
     * Gets the first name of this AB User Entry.
     *
     * @return the first name of this AB User Entry or {@code null} if none is set.
     */
    String getFirstname();

    /**
     * Sets the first name of this AB Entry.
     *
     * @param firstname The first name of AB User Entry
     */
    void setFirstname(String firstname);

    /**
     * Gets the Surname of this AB User Entry.
     *
     * @return The surname name of this AB User Entry or {@code null} if none is set.
     */
    String getSurname();

    /**
     * Sets the name of this AB Entry.
     *
     * @param surname The surname of AB UserEntry
     */
    void setSurname(String surname);
    
    /**
     * Gets the unique identifier for this Address Book Entry.
     *
     * @return The identifier which uniquely identifies the persisted Address Book Entry 
     *          or {@code null} if this is not a persisted Address Book Entry.
     */
    AddressBookEntry.Id getId();

    /**
     * Interface representing the unique identity of a persisted AddressBookEntry.
     *
     * @see AddressBookEntry#getId()
     */
    interface Id extends Serializable {
        /* Interface has no methods defined. */
    }


}
