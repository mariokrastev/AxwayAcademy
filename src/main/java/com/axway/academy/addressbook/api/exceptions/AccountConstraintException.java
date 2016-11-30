/*
 * Copyright (c) Axway Academy, 2016. All Rights Reserved.
 */
package com.axway.academy.addressbook.api.exceptions;

/**
 * This exception is thrown when attempt to update account with incorrect business unit assigned.
 */
public class AccountConstraintException extends Exception {


    /** Serial. */
    private static final long serialVersionUID = 1L;

    /**
     * Initialize class members and construct the exception message based on them.
     * @param uniqueAccountName - a flag to indicate if the account name is unique.
     */
    public AccountConstraintException(boolean uniqueAccountName) {
        super(constructExceptionMessage(uniqueAccountName));
        mUniqueAccountName = uniqueAccountName;
    }

    /**
     * Flag to indicate if the account name is unique. Defaults to true.
     */
    private boolean mUniqueAccountName = true;


    /**
     * Indicate if the account name is unique.
     * @return {@code true} if the account name is unique.
     */
    public boolean isUniqueAccountName() {
        return mUniqueAccountName;
    }

    /**
     * Returns a message based on the those of the class members: mUniqueAccountName,
     * and mValidHomeFolder which value is {@code false}.
     * @param uniqueAccountName - a flag to indicate if the account name is unique.
     */
    private static String constructExceptionMessage(boolean uniqueAccountName){
        String message = "";
        if(!uniqueAccountName) {
            message += "The account name is not unique. ";
        }
        return message;
    }

}
