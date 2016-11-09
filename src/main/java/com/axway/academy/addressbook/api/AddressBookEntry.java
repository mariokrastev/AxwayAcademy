/*
 * Copyright (c) Axway Academy, 2016. All Rights Reserved.
 */
package com.axway.academy.addressbook.api;

import java.io.Serializable;

/**
 * Public Address Book Entry interface.
 */
public interface AddressBookEntry extends Serializable {

    /**
     * Gets the unique identifier for this Address Book Entry.
     *
     * @return The identifier which uniquely identifies the persisted Address Book Entry 
     *          or {@code null} if this is not a persisted Address Book Entry.
     */
    Id getId();

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
     * Interface representing the unique identity of a persisted AddressBookEntry.
     *
     * @see AddressBookUserEntry#getId()
     */
    interface Id extends Serializable {
        /* Interface has no methods defined. */
    }
}
