/*
 * Copyright (c) Axway Academy, 2016. All Rights Reserved.
 */
package com.axway.academy.addressbook.core;

import org.apache.log4j.BasicConfigurator;
import org.hsqldb.Server;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.axway.academy.addressbook.api.AddressBookEntry;
/**
 * @author ttotev
 *
 */
public class AddressBookManagerImplTest {

    /** Hsql Server. */
    private static Server sHsqlServer;

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
        sHsqlServer.setSilent(true);
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

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testAll() {
    }

}
