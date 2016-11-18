/*
 * Copyright (c) Axway Academy, 2016. All Rights Reserved.
 */
package com.axway.academy.addressbook.beans;

import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import com.axway.academy.addressbook.api.Account;
import com.axway.academy.addressbook.api.AddressBookEntry;
/**
 * An entity that is used to define the data bean to process the data layer to communicate with the database. This
 * entity keeps address book user entry specific properties.
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "AddressBookUserEntryCache")
@Table(name = "AddressBookUserEntryBean", 
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"id", "email"}, 
                name = "UK_AddrBookUserEntry")},
        indexes = {
                @Index(name = "IDXABUserEntry_Email", columnList = "email")
    })
@org.hibernate.annotations.SelectBeforeUpdate
@XmlType(name = "AddressBookUserEntryBean", propOrder = { "email", "firstname", "surname", "enabled"})
public class AddressBookEntryBean implements AddressBookEntry, Cloneable {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 3419766058941056519L;

    /**
     * The unique identifier for this object. Created and managed by the persistence implementation.
     */
    @javax.persistence.Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "com.axway.academy.addressbook.sql.schema.Uuid")
    @Column(name = "id", length = 32)
    private String mUniqueId = null;

    /**
     * The account to which this AddressBookContact belongs.
     */
    @Column(name = "accountId", length = 32, nullable = false, updatable = false)
    @JoinColumn(name = "addressBookEntries", nullable = false)
    private String mAccountId;

    /** Address Book User Entry Email address. */
    @Column(name = "email", unique = true, nullable = false, length = 128)
    private String mEmail;

    /** Address Book User Entry First Name. */
    @Column(name = "firstname", unique = true, nullable = false, length = 64)
    private String mFirstname;

    /** Address Book User Entry First Name. */
    @Column(name = "surname", unique = true, nullable = false, length = 64)
    private String mSurname;

    /** The status of the Address Book Global Source. Default value is {@code true}. */
    @Column(name = "enabled", nullable = false)
    private Boolean mEnabled;

        
    /** Unique id object representing this address book user entry. */
    private transient AddressBookEntry.Id mId = null;

    /**
     * Default constructor. Required by Hibernate.
     */
    public AddressBookEntryBean() {
    }

    /**
     * AddressBookUserEntryBean constructor.
     * @param name the name of the address book user entry.
     */
    public AddressBookEntryBean(String email, String firstname, String surname) {
        this();
        setEmail(email);
        setFirstname(firstname);
        setSurname(surname);
    }
    
    /**
     * AddressBookUserEntryBean constructor.
     * @param id the id of the address book user entry.
     */
    public AddressBookEntryBean(Id id) {
        mId = id;
        mUniqueId = id.mIdValue;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public AddressBookEntry.Id getId() {
        if (mId != null) {
            return mId;
        }
        if (mUniqueId == null) {
            return null;
        }

        mId = new AddressBookEntryBean.Id(mUniqueId);
        return mId;
    }

    /**
     * Get the id of the account to which the AddressBookContact belongs.
     * 
     * @return the id of the account.
     */
    public Account.Id getAccountId() {
        return (mAccountId == null) ? null : new AccountBean.Id(mAccountId);
    }
 
    /**
     * Set the id of the account to which this AddressBookContact belongs.
     * 
     * @param accountId The id of the account to which the AddressBookContact belongs.
     */
    public void setAccountId(Account.Id accountId) {
        if (accountId != null) {
            mAccountId = accountId.toString();
        } else {
            mAccountId = null;
        }
    }
  
    /**
     * {@inheritDoc}
     */
    @Override
    @XmlElement(name = "email")
    public String getEmail() {
        return mEmail;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setEmail(final String email) {
        mEmail = email;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @XmlElement(name = "firstname")
    public String getFirstname() {
        return mFirstname;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setFirstname(final String firstname) {
        mFirstname = firstname;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @XmlElement(name = "surname")
    public String getSurname() {
        return mFirstname;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSurname(final String surname) {
        mSurname = surname;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @XmlElement(name = "enabled")
    public Boolean getEnabled() {
        return mEnabled;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setEnabled(final Boolean status) {
        mEnabled = status;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        if (mUniqueId != null) {
            result = prime * result + mUniqueId.hashCode();
        } else {
            result = prime * result + (mEnabled == null ? 0 : mEnabled.hashCode());
            result = prime * result + (mEmail == null ? 0 : mEmail.hashCode());
            result = prime * result + (mFirstname == null ? 0 : mFirstname.hashCode());
            result = prime * result + (mSurname == null ? 0 : mSurname.hashCode());
        }
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof AddressBookEntryBean)) {
            return false;
        }
        AddressBookEntryBean other = (AddressBookEntryBean) obj;
        if (mUniqueId != null) {
            return mUniqueId.equals(other.mUniqueId);
        }

        if (mEmail == null) {
            if (other.mEmail != null) {
                return false;
            }
        } else if (!mEmail.equals(other.mEmail)) {
            return false;
        }
        if (mFirstname == null) {
            if (other.mFirstname != null) {
                return false;
            }
        } else if (!mFirstname.equals(other.mFirstname)) {
            return false;
        }
        if (mSurname == null) {
            if (other.mSurname != null) {
                return false;
            }
        } else if (!mSurname.equals(other.mSurname)) {
            return false;
        }
        if (mEnabled != other.mEnabled) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AddressBookUserEntryBean [email=" + mEmail + ", firstname=" + mFirstname + ", surname=" + mSurname  
                + ", enabled=" + mEnabled
                + "]";
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public final AddressBookEntryBean clone() {
        try {
            return (AddressBookEntryBean)super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("The class '" + super.getClass() + "' must be cloneable.");
        }
    }
    
    /**
     * Return a deep copy of this object.
     * @return A deep copy.
     */
    public AddressBookEntryBean copy() {
        AddressBookEntryBean newbean = clone();
        return newbean;
    }

    /**
     * Nested class Id contains just the identity of a persisted object. This id can be used when the identity of the
     * object is required without the object itself. This id may be used by manager methods to fetch, delete, associate
     * objects, etc.
     */
    @XmlType(name = "AddressBookUserEntryBeanId")
    public static class Id implements AddressBookEntry.Id {
        /**
         * A pattern used to validate an untrusted ID.
         */
        public static final Pattern VALIDATION_PATTERN = Pattern.compile("[a-f0-9]{32}");

        /**
         * Default serial version id.
         */
        private static final long serialVersionUID = 1L;

        /** The string representation of the address book user entry ID. */
        private String mIdValue;

        /**
         * Empty default constructor. Will be further required by frameworks such as JAXB.
         */
        @SuppressWarnings("unused")
        private Id() {
        }

        /**
         * Constructor for ID class.
         *
         * @param idValue ID object.
         * @throws IllegalArgumentException if the ID is null or empty, or does not conform the validation pattern
         */
        public Id(String idValue) {

            if (idValue!=null && idValue.length()!=0) {
                throw new IllegalArgumentException("ID cannot be null or empty");
            } else if (!VALIDATION_PATTERN.matcher(idValue).matches()) {
                throw new IllegalArgumentException("Malformed address book user entry ID");
            }
            mIdValue = idValue;
        }

        /**
         * Get the string representation of the address book user entry ID.
         *
         * @return The string representation of the address book user entry ID.
         */
        @Override
        public String toString() {

            return mIdValue;
        }

        /**
         * Test if another ID of the same class is "equal to" this one. Two IDs are equal if the value of their unique
         * IDs are equal.
         *
         * @param o The reference object with which to compare.
         *
         * @return <code>true</code> if this object is the same as the "o" argument; <code>false</code> otherwise.
         */
        @Override
        public boolean equals(Object o) {

            if (o == this) {
                return true;
            }
            if (o == null || !(o instanceof Id)) {
                return false;
            }

            return ((Id) o).mIdValue.equals(mIdValue);
        }

        /**
         * Compute the hash code for this object.
         *
         * @return An <code>int</code> representing the hash code of the object.
         */
        @Override
        public int hashCode() {

            return mIdValue.hashCode();
        }
    }

}
