/*
 * Copyright (c) Axway Academy, 2016. All Rights Reserved.
 */
package com.axway.academy.addressbook.api.exceptions;

/**
 * This exception is thrown when the requested operation failed because the specified AddressBookContact does not exist.
 * 
 */
public class NoSuchAddressBookEntryException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Construct the exception object.
     * 
     * @param message the exception's message.
     */
    public NoSuchAddressBookEntryException(String message) {
        super(message);
    }

    /**
     * Construct the exception object.
     * 
     * @param cause the cause of the exception.
     */
    public NoSuchAddressBookEntryException(Throwable cause) {
        super(cause);
    }

    /**
     * Construct the exception object specifying the cause.
     * 
     * @param message the exception's message.
     * @param cause the cause of the exception.
     */
    public NoSuchAddressBookEntryException(String message, Throwable cause) {
        super(message, cause);
    }
}
