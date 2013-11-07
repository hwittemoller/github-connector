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

import org.eclipse.egit.github.core.service.WatcherService;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class GetWatchedTestCases extends GitHubTestParent
{

    @Before
    public void setUp() throws Exception
    {
        initializeTestRunMessage("watchers");
    }


    @Category({RegressionTests.class, WatcherTests.class})
    @Test
    public void getWatched()
    {
        try
        {
            List<WatcherService> watchers = runFlowAndGetPayload("getWatched");
            assertNotNull(watchers);

        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

}
