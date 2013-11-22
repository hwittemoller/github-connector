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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class IsWatchingTestCases extends GitHubTestParent
{

    @Before
    public void setUp() throws Exception
    {
        initializeTestRunMessage("isWatchingTestData");
        runFlowAndGetPayload("watch");
    }

    @After
    public void tearDown() throws Exception
    {
        runFlowAndGetPayload("unwatch");
    }


    @Category({RegressionTests.class, WatcherTests.class})
    @Test
    public void testIsWatching()
    {
        try
        {
            Boolean isWatching = runFlowAndGetPayload("isWatching");
            assertTrue(isWatching);

        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

}
