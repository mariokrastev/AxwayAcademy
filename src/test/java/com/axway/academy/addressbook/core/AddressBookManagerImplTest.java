/*
 * Copyright (c) Axway Academy, 2016. All Rights Reserved.
 */
package com.axway.academy.addressbook.core;

import org.apache.log4j.BasicConfigurator;
import org.hsqldb.Server;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.axway.academy.addressbook.api.Account;
import com.axway.academy.addressbook.api.AccountManager;
import com.axway.academy.addressbook.api.AddressBookEntry;
import com.axway.academy.addressbook.api.exceptions.AccountConstraintException;
import com.axway.academy.addressbook.api.exceptions.DuplicateAccountException;
import com.axway.academy.addressbook.api.exceptions.NoSuchAccountException;
import com.axway.academy.addressbook.api.exceptions.PasswordPolicyException;
import com.axway.academy.addressbook.beans.AccountBean;
/**
 * @author ttotev
 *
 */
public class AddressBookManagerImplTest {

    /** Hsql Server. */
    private static Server sHsqlServer;

    /** Address Book Global Source. */
    private static Account sAccount;
    
    /** Address Book Global Source. */
    private static AddressBookEntry sAddressBookUserEntry;
    
    /** Address Book Account Source. */
    private static AddressBookEntry sAddressBookUserEntry1;
    private static AddressBookEntry sAddressBookUserEntry2;
    
    /**
     * @throws java.lang.Exception if exception occurs
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        BasicConfigurator.configure();

        sHsqlServer = new Server();
        sHsqlServer.setSilent(false);
        sHsqlServer.start();
        
    }

    /**
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        sHsqlServer.shutdown();
        sHsqlServer = null;
    }

    private AccountManager sAccountManager;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        sAccountManager = new AccountManagerImpl();
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testAll() {
        createAccount();
        createAddressBookEntries();
        
    }

    @Test
    public void createAccount() {
        Account retrievedAccount = null;
        Account account = new AccountBean("stavri1999", "stavri1999@gmail.com");
        account.setFullname("Bai Stavri");
        try {
            Account persistedAccount = sAccountManager.createAccount(account);
            retrievedAccount = sAccountManager.getAccountByAccountName(persistedAccount.getLoginname());
        } catch (DuplicateAccountException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (PasswordPolicyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (AccountConstraintException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchAccountException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Assert.assertEquals(account.getLoginname(), retrievedAccount.getLoginname());    
    }

    @Test
    public void createAddressBookEntries() {
        // TODO Auto-generated method stub
        
    }

}
