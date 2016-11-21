/*
 * Copyright (c) Axway Academy, 2016. All Rights Reserved.
 */
package com.axway.academy.addressbook.api.exceptions;

/**
 * This exception is thrown when attempting to change a user password and the password does not meet the
 * minimum password policy requirements.
 */
public class PasswordPolicyException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Construct the exception object.
     *
     * @param message the exception's message.
     */
    public PasswordPolicyException(String message) {
        super(message);
    }


    /**
     * Construct the exception object.
     *
     * @param cause the cause of the exception.
     */
    public PasswordPolicyException(Throwable cause) {
        super(cause);
    }

    /**
     * Construct the exception object specifying the cause.
     *
     * @param message the exception's message.
     * @param cause the cause of the exception.
     */
    public PasswordPolicyException(String message, Throwable cause) {
        super(message, cause);
    }
}
