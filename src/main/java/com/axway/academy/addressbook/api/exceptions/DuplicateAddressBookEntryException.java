/*
 * Copyright (c) Axway Academy, 2016. All Rights Reserved.
 */
package com.axway.academy.addressbook.api.exceptions;

/**
 * This exception is thrown when attempting to create a address book contact which already exists.
 * 
 */
public class DuplicateAddressBookEntryException extends Exception {

    /** serialVersionUID. */
    private static final long serialVersionUID = 8874481540811654588L;

    /**
     * Construct the exception object.
     * 
     * @param message the exception's message.
     */
    public DuplicateAddressBookEntryException(String message) {
        super(message);
    }

    /**
     * Construct the exception object.
     * 
     * @param cause the cause of the exception.
     */
    public DuplicateAddressBookEntryException(Throwable cause) {
        super(cause);
    }

    /**
     * Construct the exception object specifying the cause.
     * 
     * @param message the exception's message.
     * @param cause the cause of the exception.
     */
    public DuplicateAddressBookEntryException(String message, Throwable cause) {
        super(message, cause);
    }
}
