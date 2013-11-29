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

import org.eclipse.egit.github.core.Issue;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CloseIssueTestCases extends GitHubTestParent
{
    @Before
    public void setUp() throws Exception
    {
        createTestRepository();
        initializeTestRunMessage("closeIssueTestData");
        Issue issue = runFlowAndGetPayload("createIssue");
        upsertOnTestRunMessage("issueNumber", issue.getNumber());
    }


    @Category({RegressionTests.class, IssueTests.class})
    @Test
    public void testCloseIssue()
    {
        try
        {
            Issue issue = runFlowAndGetPayload("closeIssue");
            assertEquals("closed", issue.getState());

        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }
}
