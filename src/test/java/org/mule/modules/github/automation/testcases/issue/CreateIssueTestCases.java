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

package org.mule.modules.github.automation.testcases.issue;

import org.eclipse.egit.github.core.Issue;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.github.automation.testcases.GutHubTestParent;
import org.mule.modules.github.automation.testcases.RegressionTests;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class CreateIssueTestCases extends GutHubTestParent
{
    @Before
    public void setUp() throws Exception
    {
        createTestRepository(false);
        initializeTestRunMessage("issue");
    }

    @Category({RegressionTests.class})
    @Test
    public void createIssue()
    {
        try
        {
            Issue issue = runFlowAndGetPayload("createIssue");
            assertNotNull(issue);
            assertEquals(getTestRunMessageValue("title"), issue.getTitle());
            assertEquals(getTestRunMessageValue("body"), issue.getBody());

        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }


}
