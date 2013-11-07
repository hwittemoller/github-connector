/**
 *
 * Mule GitHub Cloud Connector
 *
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 * <p/>
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.modules.github.automation.testcases;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public class GetEmailsTestCases extends GitHubTestParent
{
    @Before
    public void setUp() throws Exception
    {
        initializeTestRunMessage("email");
        runFlowAndGetPayload("addEmails");
    }
    
    @After
    public void tearDown() throws Exception
    {
        runFlowAndGetPayload("removeEmails");
    }
    
    @Category({RegressionTests.class, UserTests.class})
    @Test
    public void getEmails()
    {
        try
        {
        	List<String> emails = runFlowAndGetPayload("getEmails");
            assertTrue(emails.contains(getTestRunMessageValue("email1")));

        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

}
