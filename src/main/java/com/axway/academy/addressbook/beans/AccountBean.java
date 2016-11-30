/*
 * Copyright (c) Axway Academy, 2016. All Rights Reserved.
 */

package com.axway.academy.addressbook.beans;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import javax.mail.internet.AddressException;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.axway.academy.addressbook.api.Account;
import com.axway.academy.addressbook.utils.AppFrameworkUtils;

/**
 * An Account is used to define users and sites with which the ST system communicates. It has a unique name and ID.
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "AccountCache")

@Table(name = "Account", indexes = { @Index(name = "IDXAccount_Email", columnList = "email"), }, uniqueConstraints = {
        @javax.persistence.UniqueConstraint(columnNames = { "loginname" }, name = "UC_Account_loginname"), })
@org.hibernate.annotations.SelectBeforeUpdate
@XmlType(name = "Account", propOrder = { "loginname", "fullname", "email", "phone", "enabled" })
@NamedQueries({
        @NamedQuery(name = "findAccountByIdOrLoginname", query = "select a from AccountBean a where a.mUniqueId = :id or a.loginname = :loginname"),
        @NamedQuery(name = "findAccountById", query = "select a from AccountBean a where a.mUniqueId = :id"),
        @NamedQuery(name = "findAccountByLoginname", query = "select a from AccountBean a where a.loginname = :loginname") })
public class AccountBean implements Account, Cloneable {

    /** Unique ID used for serialization. */
    private static final long serialVersionUID = 5937117895768043550L;

    /**
     * The unique identifier for this object. Created and managed by the persistence implementation.
     */
    @XmlAttribute(name = "id", required = false)
    @javax.persistence.Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "com.axway.academy.addressbook.sql.schema.Uuid")
    @Column(name = "id", length = 32)
    private String mUniqueId = null;

    /**
     * Unique account Login name.
     */
    @Column(name = "loginname", nullable = false)
    private String loginname = null;

    /**
     * The user ID assigned to this account.
     */
    @Column(name = "fullname", nullable = true)
    private String fullname = null;

    /**
     * The email address of the account's contact.
     */
    @Column(name = "email")
    private String email = null;

    /**
     * The phone number of the account's contact.
     */
    @Column(name = "phone")
    private String phone = null;

    /**
     * The status of the account.
     */
    @XmlAttribute(name = "disabled")
    @Column(name = "disabled", nullable = false)
    private boolean disabled = false;

    /**
     * Definition needed by Hibernate to cascade deletes to the AddressBookContact child elements. This is not to be
     * referenced. Use the Transfer Manager to get/set the AddressBookContact objects.
     */
    @SuppressWarnings("deprecation")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, targetEntity = com.axway.academy.addressbook.beans.AddressBookEntryBean.class, mappedBy = "mAccountId")
    // @JoinColumn(name = "accountId", nullable = false)
    @org.hibernate.annotations.ForeignKey(name = "FKAddressBookEntry_Account")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "AccountCache")
    private Set<AddressBookEntryBean> addressBookEntries = new HashSet<AddressBookEntryBean>();

    /**
     * DO NOT CHANGE. Default constructor as required by Hibernate.
     */
    protected AccountBean() {
        super();
    }

    /**
     * Package level constructor used by AccountManager to create a new account instance.
     *
     * @param name the unique name of the account.
     * @param email the account email.
     */
    public AccountBean(String loginname, String email) {
        this();
        setLoginname(loginname);
        setEmail(email);
    }

    public AccountBean(Id id) {
        mId = id;
        mUniqueId = id.mIdValue;
    }

    /**
     * {@inheritDoc}
     */
    @XmlElement(name = "loginname", required = true)
    @Override
    public String getLoginname() {

        return loginname;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLoginname(String loginname) {

        this.loginname = AppFrameworkUtils.verifyField(loginname, MAX_NAME_LENGTH);
    }

    /**
     * {@inheritDoc}
     */
    @XmlElement(name = "fullname", required = true)
    @Override
    public String getFullname() {

        return fullname;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setFullname(String fullname) {

        this.fullname = fullname;
    }

    /**
     * {@inheritDoc}
     */
    @XmlElement(name = "email")
    @Override
    public String getEmail() {

        return email;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setEmail(String emailAddress) {

        if (AppFrameworkUtils.isNullOrEmpty(emailAddress)) {
            email = null;
        } else {
            emailAddress = AppFrameworkUtils.verifyField(emailAddress, MAX_EMAIL_LENGTH);
            try {
                new javax.mail.internet.InternetAddress(emailAddress, true);
            } catch (AddressException ex) {
                throw new IllegalArgumentException("Bad email address", ex);
            }
            email = emailAddress;
        }
    }

    /**
     * {@inheritDoc}
     */
    @XmlElement(name = "phone")
    @Override
    public String getPhone() {

        return phone;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPhone(String phoneNumber) {

        if (AppFrameworkUtils.isNullOrEmpty(phoneNumber)) {
            phone = null;
        } else {
            phone = AppFrameworkUtils.verifyField(phoneNumber, MAX_PHONE_LENGTH);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDisabled() {

        return disabled;
    }

    /**
     * Sets the disabled value.
     *
     * @param isDisabled the new disabled
     */
    @Override
    public void setDisabled(boolean isDisabled) {
        this.disabled = isDisabled;
    }

    /**
     * ID object constructed using mUniqueId member.
     */
    private transient AccountBean.Id mId;

    /**
     * {@inheritDoc}
     */
    @Override
    public Account.Id getId() {

        if (mId != null) {
            return mId;
        }
        if (mUniqueId == null) {
            return null;
        }

        mId = new AccountBean.Id(mUniqueId);
        return mId;
    }

    /**
     * Add mapping to AddressBookContactBean.
     *
     * @param contact AddressBookContactBean
     */
    @Override
    public void addAddressBookContact(AddressBookEntryBean entry) {
        addressBookEntries.add(entry);
    }

    /**
     * Remove mapping to AddressBookContactBean.
     *
     * @param contact AddressBookContactBean
     */
    @Override
    public void removeAddressBookContact(AddressBookEntryBean entry) {
        addressBookEntries.remove(entry);
    }


    @Override
    public Set<AddressBookEntryBean> getAddressBookEntries() {
        return addressBookEntries;
    }

    @Override
    public void setAddressBookEntries(Set<AddressBookEntryBean> addressBookEntries) {
        this.addressBookEntries = addressBookEntries;
    }

    /**
     * Test if an account is "equal to" this one. Two accounts are equal if their IDs are equal.
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
        if (o == null || !(o instanceof AccountBean)) {
            return false;
        }

        Account.Id id = ((AccountBean) o).getId();
        return id != null && id.equals(getId());
    }

    /**
     * Compute the hash code for this object.
     *
     * @return An <code>int</code> representing the hash code of the object.
     */
    @Override
    public int hashCode() {

        if (getId() == null) {
            return 0;
        }
        return getId().hashCode();
    }

    /**
     * Return a shallow copy of this object.
     *
     * @return A shallow copy.
     */
    @Override
    public final AccountBean clone() {
        try {
            return (AccountBean) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("The class '" + super.getClass() + "' must be cloneable.");
        }
    }

    /**
     * Return a deep copy of this object.
     *
     * @return A deep copy.
     */
    public AccountBean copy() {
        AccountBean newbean = clone();
        return newbean;
    }

    /**
     * Nested class Id contains just the identity of an object. This ID can be used when the identity of the object is
     * required without the object itself. This ID may be used by manager methods to fetch, delete, associate objects,
     * etc.
     */
    @XmlType(name = "AccountId")
    /* JAXB needs unique type name to generate schema */
    public static class Id implements Account.Id {

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

            if (idValue != null && idValue.length() != 0) {
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

    @Override
    public String toString() {
        StringBuilder accInfo = new StringBuilder();

        accInfo.append("Account name: " + this.getLoginname());
        accInfo.append(" AccountID: " + this.getId());
        accInfo.append(" Full name: " + this.getFullname());
        accInfo.append(" Email: " + this.getEmail());
        accInfo.append(" Phone: " + this.getPhone());

        return accInfo.toString();
    }

    /**
     * Package private method for setting an id.
     *
     * @param id the id to set
     */
    void setId(Id id) {
        mId = id;
        mUniqueId = mId.mIdValue;
    }

}
