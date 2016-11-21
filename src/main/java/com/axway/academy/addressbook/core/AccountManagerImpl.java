/*
 * Copyright (c) Axway Academy, 2016. All Rights Reserved.
 */
package com.axway.academy.addressbook.core;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.ReplicationMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.UnresolvableObjectException;
import org.hibernate.exception.ConstraintViolationException;

import com.axway.academy.addressbook.api.Account;
import com.axway.academy.addressbook.api.Account.Id;
import com.axway.academy.addressbook.api.AccountCriterion;
import com.axway.academy.addressbook.api.AccountManager;
import com.axway.academy.addressbook.api.AddressBookEntry;
import com.axway.academy.addressbook.api.AddressBookEntryCriterion;
import com.axway.academy.addressbook.api.exceptions.AccountConstraintException;
import com.axway.academy.addressbook.api.exceptions.DuplicateAccountException;
import com.axway.academy.addressbook.api.exceptions.DuplicateAddressBookEntryException;
import com.axway.academy.addressbook.api.exceptions.NoSuchAccountException;
import com.axway.academy.addressbook.api.exceptions.NoSuchAddressBookEntryException;
import com.axway.academy.addressbook.api.exceptions.PasswordPolicyException;
import com.axway.academy.addressbook.beans.AccountBean;
import com.axway.academy.addressbook.beans.AddressBookEntryBean;
import com.axway.academy.addressbook.sql.SessionFactoryManager;
import com.axway.academy.addressbook.sql.SessionManagerFactory;

public class AccountManagerImpl implements AccountManager {

    /** The logger. */
    private static final Logger sLogger = Logger.getLogger(AccountManager.class);

    /** The single instance of the account manager. */
    private static final AccountManagerImpl sAccountManager = new AccountManagerImpl();

    private SessionFactoryManager mSessionFactoryManager;


    public AccountManagerImpl() {
        super();
        mSessionFactoryManager = SessionManagerFactory.getInstance().getSessionFactoryManager();
    }
    
    @Override
    public Account getAccount(Id accountId) throws NoSuchAccountException {
        Session session = mSessionFactoryManager.getSessionFactory().openSession();

        try {
            return getAccount(session, accountId);
        } finally {
            SessionManagerFactory.closeSession(session);
        }
    }

    @Override
    public Account getAccount(String name) throws NoSuchAccountException {
        Session session = mSessionFactoryManager.getSessionFactory().openSession();

        try {
            AccountCriterionImpl criterion = (AccountCriterionImpl) getAccountCriterion().named(name);
            Criteria criteria = criterion.getCriteria(session);
            Account account = (Account) criteria.uniqueResult();
            if (account == null) {
                throw new NoSuchAccountException(name);
            }
            return account;

        } finally {
            SessionManagerFactory.closeSession(session);
        }
    }

    @Override
    public Id newAccountId(String uuid) {
        return new AccountBean.Id(uuid);
    }

    @Override
    public Account createAccount(Account account)
            throws DuplicateAccountException, PasswordPolicyException, AccountConstraintException {
        return persistAccount(account);
    }

    /**
     * Persist a new account and a user associated with the account.
     *
     * @param account the new {@code Account} object that holds the account's attributes.
     * @param user the new {@code User} object that holds the user's attributes to be created along with the account.
     *            May be set to {@code null} to create an account without a user.
     * @return An array of the newly persisted {@code Account} and {@code User} objects.
     * @throws DuplicateAccountException if the specified account already exists.
     * @throws DuplicateUserException if the specified user already exists.
     * @throws PasswordPolicyException if the passphrase set for the user does not fulfill the password policy
     *             requirements.
     * @throws AccountConstraintException if the account home folder is not valid.
     */
    private Account persistAccount(Account account) throws DuplicateAccountException,
            PasswordPolicyException, AccountConstraintException {

        if (account.getId() != null ) {
            throw new IllegalArgumentException("Instantiated from Template");
        }

        Session session = mSessionFactoryManager.getSessionFactory().openSession();

        try {

            ConstraintValidationUtil.validateNewAccount(session, (AccountBean) account);

            Transaction tx = session.beginTransaction();

            Account persistedAccount = account;
            try {
                if (account.getId() == null) {
                    // If object does not have an id, persist a copy so if exception is thrown original object is
                    // unchanged.
                    persistedAccount = ((AccountBean) account).copy();
                    session.save(persistedAccount);
                } else {
                    session.replicate(persistedAccount, ReplicationMode.OVERWRITE);
                }
            } catch (ConstraintViolationException ex) {
                // Assume this is a uniqueness violation on the name since that is the only constraint in this bean.
                throw new DuplicateAccountExceptionImpl(account, "Duplicate account: " + account.getLoginname(), ex);
            } catch (HibernateException e) {
                final String message = "Database error creating account";
                throw new RuntimeException(message, e);
            }
            session.flush();

            tx.commit();

            if (sLogger.isDebugEnabled()) {
                sLogger.debug("76568:createAccount : " + account);
            }

            return persistedAccount;
        } finally {
            SessionManagerFactory.closeSession(session);
        }
    }

    @Override
    public void updateAccount(Account account) throws NoSuchAccountException, DuplicateAccountException,
            PasswordPolicyException, AccountConstraintException {

        Session session = mSessionFactoryManager.getSessionFactory().openSession();

        try {

            ConstraintValidationUtil.validateUpdateAccount(session, (AccountBean) account);

            Transaction tx = session.beginTransaction();

            try {
                session.replicate(account, ReplicationMode.OVERWRITE);
                session.flush();

            } catch (UnresolvableObjectException ex) {
                throw new NoSuchAccountException(account.getLoginname(), ex);
            } catch (ConstraintViolationException ex) {
                throw new DuplicateAccountExceptionImpl(account, "Duplicate account: " + account.getLoginname(), ex);
            }

            tx.commit();

            if (sLogger.isDebugEnabled()) {
                sLogger.debug("76568: updateAccount updated: " + account, new Throwable("trace caller"));
            }

        } finally {
            SessionManagerFactory.closeSession(session);
        }
        
    }

    @Override
    public void deleteAccount(Id accountId) throws NoSuchAccountException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public AccountCriterion getAccountCriterion() {
        return new AccountCriterionImpl();
    }

    @Override
    public List<Account> getAccounts(AccountCriterion criterion, int first, int count) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Account> getAccounts(AccountCriterion criterion) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getAccountCount(AccountCriterion criterion) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void disableAccount(Id accountId) throws NoSuchAccountException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void enableAccount(Id accountId) throws NoSuchAccountException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public AddressBookEntry createAddressBookEntry(String accountName, AddressBookEntry addressBookEntry)
            throws NoSuchAccountException, DuplicateAddressBookEntryException {
        Account.Id accountId = getAccount(accountName).getId();
        Serializable[] result = persistAddressBookEntry(accountId, addressBookEntry);
        return (AddressBookEntry) result[1];
    }

    static void exists(Session session, Account.Id accountId) throws NoSuchAccountException {
        exists(session, accountId, null);
    }

    static void exists(Session session, Account.Id accountId, Boolean unlicensed) throws NoSuchAccountException {

        final AccountCriterion accountCriterion = sAccountManager.getAccountCriterion();
        if (!((CriterionBase) accountCriterion.id(accountId, true)).exists(session)) {
            throw new NoSuchAccountException(accountId.toString());
        }
    }

    private Serializable[] persistAddressBookEntry(Account.Id accountId, AddressBookEntry entry)
            throws NoSuchAccountException, DuplicateAddressBookEntryException {

        Session session = mSessionFactoryManager.getSessionFactory().openSession();

        try {
            Transaction tx = session.beginTransaction();
            exists(session, accountId);
            entry = addAddressBookEntry(session, accountId, entry);
            tx.commit();

            if (sLogger.isDebugEnabled()) {
                sLogger.debug("76568:createAddressBookEntry created: " + entry, new Throwable("trace caller"));
            }

            return new Serializable[] { accountId, entry };
        } finally {
            SessionManagerFactory.closeSession(session);
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Account getAccountByAccountName(String name) throws NoSuchAccountException {

        Session session = mSessionFactoryManager.getSessionFactory().openSession();
        try {
            AccountCriterionImpl criterion = (AccountCriterionImpl) getAccountCriterion().named(name);

            Criteria criteria = criterion.getCriteria(session);
            Account account = (Account) criteria.uniqueResult();
            if (account == null) {
                throw new NoSuchAccountException(name);
            }
            return account;

        } finally {
            SessionManagerFactory.closeSession(session);
        }
    }

    @Override
    public AddressBookEntryCriterion getAddressBookContactCriterion(String accountName) throws NoSuchAccountException {
        Account.Id accountId = getAccountByAccountName(accountName).getId();
        return new AddressBookEntryCriterionImpl(accountId);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AddressBookEntry> getAddressBookContacts(AddressBookEntryCriterion criterion, Integer first,
            Integer count) throws NoSuchAccountException {
        Session session = mSessionFactoryManager.getSessionFactory().openSession();

        try {
            if (criterion == null) {
                throw new NoSuchAccountException("AddressBookEntryCriterion not valid");
            }

            Criteria criteria = ((AddressBookEntryCriterionImpl) criterion).getCriteria(session);

            if (first != null) {
                criteria.setFirstResult(first);
            }

            if (count != null) {
                criteria.setMaxResults(count);
            }

            return criteria.list();

        } finally {
            SessionManagerFactory.closeSession(session);
        }
    }

    @Override
    public AddressBookEntry getAddressBookEntryById(String accountId, String addressBookContactId)
            throws NoSuchAccountException, NoSuchAddressBookEntryException {
        Session session = mSessionFactoryManager.getSessionFactory().openSession();

        try {

            AddressBookEntryCriterionImpl criterion =
                    (AddressBookEntryCriterionImpl)(new AddressBookEntryCriterionImpl (newAccountId(accountId)))
                    .uniqueId(addressBookContactId);
            Criteria criteria = criterion.getCriteria(session);
            AddressBookEntry contact = (AddressBookEntry) criteria.uniqueResult();
            if (contact == null) {
                throw new NoSuchAddressBookEntryException((MessageFormat.format(
                        "Address book with Id - {0} does not exist", addressBookContactId)));
            }

            return contact;

        } finally {
            SessionManagerFactory.closeSession(session);
        }

    }

    @Override
    public void updateAddressBookEntry(AddressBookEntryBean entry) throws NoSuchAddressBookEntryException,
    DuplicateAddressBookEntryException {
        Session session = mSessionFactoryManager.getSessionFactory().openSession();
    
        try {
            Transaction tx = session.beginTransaction();
            updategetAddressBookEntry(session, entry);
            tx.commit();
        } finally {
            SessionManagerFactory.closeSession(session);
        }
    }

    private void updategetAddressBookEntry(Session session, AddressBookEntryBean entry)
            throws NoSuchAddressBookEntryException, DuplicateAddressBookEntryException {
    
        try {
            AddressBookEntryBean addressBookEntryBean = (AddressBookEntryBean) entry;
            AccountBean account = (AccountBean) getAccount(session, addressBookEntryBean.getAccountId());
    
            if (addressBookEntryBean.getId() == null) {
                throw new NoSuchAddressBookEntryException("AddressBookContact does not exist yet");
            }
    
            for (AddressBookEntryBean existingContact : account.getAddressBookEntries()) {
                if (((AddressBookEntryBean) entry).getId().equals(existingContact.getId())) {
                    account.removeAddressBookContact(existingContact);
                    session.delete(existingContact);
                    session.flush();
                    break;
                }
            }
            addAddressBookEntry(session, addressBookEntryBean.getAccountId(), addressBookEntryBean);
    
        } catch (ConstraintViolationException ex) {
            throw new DuplicateAddressBookEntryException("Duplicate address book contact: " + entry.getEmail(),
                    ex);
        } catch (UnresolvableObjectException ex) {
            throw new NoSuchAddressBookEntryException("Id: " + entry.getId().toString());
        } catch (NoSuchAccountException e) {
            throw new NoSuchAddressBookEntryException("Assigned account does not exist", e);
        }
    
        if (sLogger.isDebugEnabled()) {
            sLogger.debug("76568: updateAddressBookContact updated: " + entry, new Throwable("trace caller"));
        }
    }

    private AddressBookEntry addAddressBookEntry(Session session, Account.Id accountId, AddressBookEntry entry)
            throws DuplicateAddressBookEntryException, NoSuchAccountException {

        // Create the association between the contact and account.
        AccountBean account = (AccountBean) getAccount(session, accountId);
        ((AddressBookEntryBean) entry).setAccountId(accountId);
        

        // If object does not have an id, persist a clone so if exception is thrown original object is unchanged.
        AddressBookEntry persistedContact = (entry.getId() == null) ? ((AddressBookEntryBean) entry).clone()
                : entry;
        account.addAddressBookContact((AddressBookEntryBean) persistedContact);

        try {
            session.save(persistedContact);
            session.flush();
        } catch (ConstraintViolationException ex) {
            throw new DuplicateAddressBookEntryException("Duplicate address book entry: " + entry.getEmail(),
                    ex);
        }

        return persistedContact;
    }

    @Override
    public void deleteAddressBookEntry(String addressBookEntryId) throws NoSuchAddressBookEntryException {

        Session session = mSessionFactoryManager.getSessionFactory().openSession();

        try {
            Transaction tx = session.beginTransaction();
            AddressBookEntryBean addressBookEntry = (AddressBookEntryBean) session.get(AddressBookEntryBean.class,
                    addressBookEntryId);
            if (addressBookEntry == null) {
                throw new NoSuchAddressBookEntryException("Id: " + addressBookEntryId.toString());
            }

            AccountBean account = null;
            try {
                account = (AccountBean) getAccount(session, addressBookEntry.getAccountId());
            } catch (NoSuchAccountException e) {
                // ignore, no account associated to this address book contact.
            }
            if (account != null) {
                account.removeAddressBookContact(addressBookEntry);
            }

            session.delete(addressBookEntry);
            session.flush();
            tx.commit();

            if (sLogger.isDebugEnabled()) {
                sLogger.debug("76568: deleteAddressBookContact deleted: " + addressBookEntry, new Throwable("trace caller"));
            }

        } finally {
            SessionManagerFactory.closeSession(session);
        }
        
    }

    @Override
    public List<Account> getAccountsByEmail(String email) {
        return getAccountsByEmails(Arrays.asList(email));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Account> getAccountsByEmails(List<String> emails) {
        if (emails.isEmpty()) {
            return Collections.emptyList();
        }

        Session session = mSessionFactoryManager.getSessionFactory().openSession();

        try {
            final AccountCriterion accountCriterion = getAccountCriterion();
            for (final String email : emails) {
                if (email == null) {
                    throw new IllegalArgumentException("E-mail address cannot be null.");
                }
                accountCriterion.email(email);
            }
            return ((AccountCriterionImpl) accountCriterion).getCriteria(session).list();
        } finally {
            SessionManagerFactory.closeSession(session);
        }
    }
    
    private Account getAccount(Session session, Account.Id accountId) throws NoSuchAccountException {

        Account account = (Account) session.get(AccountBean.class, accountId.toString());
        if (account == null) {
            throw new NoSuchAccountException(accountId.toString());
        }
        return account;
    }

    /**
     * Subclass of the DuplicateAccountException implementing getDuplicate method.
     */
    private class DuplicateAccountExceptionImpl extends DuplicateAccountException {

        /** The serial version UID */
        private static final long serialVersionUID = 1L;

        private Account mAccount;

        private Account mDupAccount;

        private DuplicateAccountExceptionImpl(Account a, String message, Throwable cause) {
            super(message, cause);
            mAccount = a;
        }

        /**
         * Get an existing Account object which may have caused this duplicate exception to be thrown.
         *
         * @return the Account object already persisted or null if a duplicate was not found.
         */
        @Override
        public Account getDuplicate() {
            if (mDupAccount != null) {
                return mDupAccount;
            }
            Session session = mSessionFactoryManager.getSessionFactory().openSession();
            try {
                AccountCriterionImpl criterion = (AccountCriterionImpl) getAccountCriterion();
                if (mAccount.getId() != null) {
                    criterion.id(mAccount.getId(), false);
                }
                mDupAccount = (Account) criterion.getCriteria(session).setMaxResults(1).uniqueResult();
                session.flush();
                return mDupAccount;
            } catch (HibernateException ex) {
                return null;
            } finally {
                SessionManagerFactory.closeSession(session);
            }
        }

        /**
         * Get the new Account object which failed to be persisted because this exception was thrown.
         *
         * @return an Account object.
         */
        @Override
        public Account getObject() {
            return mAccount;
        }
    }

}
