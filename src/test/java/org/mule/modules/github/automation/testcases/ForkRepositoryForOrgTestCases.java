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

import org.eclipse.egit.github.core.Repository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ForkRepositoryForOrgTestCases extends GitHubTestParent
{
    @Before
    public void setUp() throws Exception
    {
        initializeTestRunMessage("forkRepositoryForOrgTestData");
    }

    @After
    public void tearDown() throws Exception
    {
        deleteRepository((String)getTestRunMessageValue("organization"), (String)getTestRunMessageValue("repository"));
        Thread.sleep(10000L);
    }

    @Category({RegressionTests.class, RepositoryTests.class})
    @Test
    public void testForkRepositoryForOrg()
    {
        try
        {
            Repository repository = runFlowAndGetPayload("forkRepositoryForOrg");
            Thread.sleep(10000L);
            assertEquals(getTestRunMessageValue("repository"), repository.getName());

        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

}