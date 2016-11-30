/*
 * Copyright (c) Axway Academy, 2016. All Rights Reserved.
 */
package com.axway.academy.addressbook.utils;

/**
 * Utility class for AppFramework methods that need to be shared between the framework and the
 * framework agents.
 */
public final class AppFrameworkUtils {

    /**
     * Verify a string value for use in a field.  The string must not be null,  must not contain only spaces and must
     * be between 1 and maxLength characters long, inclusive.  The verified string with trailing spaces
     * removed is returned.
     *
     * @param value a string which is to be qualified for use as a name.
     * @param maxLength the maximum allowed length of the string.
     * @return the name argument with trailing spaces trimmed.
     */
    public static String verifyField(String value, int maxLength) {

        if (value == null) {
            throw new IllegalArgumentException("Name is null");
        }
        value = value.trim();
        if (value.length() == 0){
            throw new IllegalArgumentException("Name is empty");
        }
        if (value.length() > maxLength) {
            throw new IllegalArgumentException("Name exceeds maximum length: " + value);
        }
        return value;
    }

    /**
     * Determines if a {@link String} is <code>null</code> or empty.
     * 
     * @param value a {@link String} to test.
     * @return <code>true</code> if the string is <code>null</code> or empty.
     */
    public static boolean isNullOrEmpty(String value)
    {
        return (value == null) || (value.length() == 0);
    }
}
