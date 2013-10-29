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


package org.mule.modules.github.automation.testcases.watcher;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.github.automation.testcases.GutHubTestParent;
import org.mule.modules.github.automation.testcases.RegressionTests;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

public class UnwatchTestCases extends GutHubTestParent
{

    @Before
    public void setUp() throws Exception
    {
        initializeTestRunMessage("watchers");
    }

    @Category({RegressionTests.class})
    @Test
    public void unwatch()
    {
        try
        {
            runFlowAndGetPayload("unwatch");
            Boolean isWatching = runFlowAndGetPayload("isWatching");
            assertFalse(isWatching);

        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

}
