/*
 * Copyright (c) Axway Academy, 2016. All Rights Reserved.
 */
package com.axway.academy.addressbook.api.exceptions;

import com.axway.academy.addressbook.api.Account;

/**
 * This exception is thrown when attempting to create or update an account with a name already in use by another
 * account.
 */
public abstract class DuplicateAccountException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Construct the exception object.
     * 
     * @param message the exception's message.
     */
    public DuplicateAccountException(String message) {
        super(message);
    }
    
    
    /**
     * Construct the exception object.
     * 
     * @param cause the cause of the exception.
     */
    public DuplicateAccountException(Throwable cause) {
        super(cause);
    }

    /**
     * Construct the exception object specifying the cause.
     * 
     * @param message the exception's message.
     * @param cause the cause of the exception.
     */
    public DuplicateAccountException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Get an existing Account object which may have caused this duplicate exception to be thrown.
     * @return the Account object already persisted or null if a duplicate was not found.
     */
    public abstract Account getDuplicate();

    /**
     * Get the new Account object which failed to be persisted because this exception was thrown.
     * @return an Account object.
     */
    public abstract Account getObject();
}
