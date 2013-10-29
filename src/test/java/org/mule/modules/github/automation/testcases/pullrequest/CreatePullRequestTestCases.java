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

package org.mule.modules.github.automation.testcases.pullrequest;

import org.eclipse.egit.github.core.PullRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.github.automation.testcases.RegressionTests;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class CreatePullRequestTestCases extends BasePullRequestTestCases
{
    @Before
    public void setUp() throws Exception
    {
        prepareBranch();
    }

    @Category({RegressionTests.class})
    @Test
    public void createPullRequest()
    {
        try
        {
            PullRequest pullRequest = runFlowAndGetPayload("createPullRequest");
            assertNotNull(pullRequest);
            assertEquals(getTestRunMessageValue("title"), pullRequest.getTitle());
            assertEquals(getTestRunMessageValue("body"), pullRequest.getBody());
        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }


}
