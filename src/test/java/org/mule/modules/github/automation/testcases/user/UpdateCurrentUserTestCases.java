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

package org.mule.modules.github.automation.testcases.user;

import org.eclipse.egit.github.core.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.github.automation.testcases.GitHubTestParent;
import org.mule.modules.github.automation.testcases.RegressionTests;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;


public class UpdateCurrentUserTestCases extends GitHubTestParent
{
    @Before
    public void setUp() throws Exception
    {
        initializeTestRunMessage("updateCurrentUser");
    }

    @Category({RegressionTests.class})
    @Test
    public void updateCurrentUser()
    {
        try
        {
            User user = runFlowAndGetPayload("updateCurrentUser");
            assertEquals(getTestRunMessageValue("blog"), user.getBlog());
            assertEquals(getTestRunMessageValue("location"), user.getLocation());
            assertEquals(getTestRunMessageValue("company"), user.getCompany());

        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

}
