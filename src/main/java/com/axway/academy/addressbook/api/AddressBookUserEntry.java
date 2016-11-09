/*
 * Copyright (c) Axway Academy, 2016. All Rights Reserved.
 */
package com.axway.academy.addressbook.api;

/**
 * Public Address Book Global Source interface.
 */
public interface AddressBookUserEntry extends AddressBookEntry {

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

}
