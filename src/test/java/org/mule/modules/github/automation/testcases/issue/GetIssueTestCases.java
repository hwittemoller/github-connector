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

import java.util.List;

import org.eclipse.egit.github.core.Issue;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.github.automation.testcases.GitHubTestParent;
import org.mule.modules.github.automation.testcases.RegressionTests;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class GetIssueTestCases extends GitHubTestParent
{
    @Before
    public void setUp() throws Exception
    {
        createTestRepository(false);
        initializeTestRunMessage("issue");
        Issue issue = runFlowAndGetPayload("createIssue");
        upsertOnTestRunMessage("issueId", issue.getNumber());
    }

    @Category({RegressionTests.class})
    @Test
    public void getIssue()
    {
        try
        {
            Issue issue = runFlowAndGetPayload("getIssue");
            assertNotNull(issue);
            assertEquals(getTestRunMessageValue("title"), issue.getTitle());
            assertEquals(getTestRunMessageValue("body"), issue.getBody());

        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @Category({RegressionTests.class})
    @Test
    public void getIssues()
    {
        try
        {
            List<Issue> issues = runFlowAndGetPayload("getIssues");
            assertTrue(issues.size()>0);
            boolean found = false;
            for (Issue i : issues)
            {
                if (getTestRunMessageValue("issueId").equals(i.getNumber()))
                {
                    found = true;
                    break;
                }
            }
            assertTrue(found);

        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }


    @Category({RegressionTests.class})
    @Test
    public void getIssuesCretedAfter()
    {
        try
        {
            List<Issue> issues = runFlowAndGetPayload("getIssuesCretedAfter");
            assertTrue(issues.size()>0);
            boolean found = false;
            for (Issue i : issues)
            {
                if (getTestRunMessageValue("issueId").equals(i.getNumber()))
                {
                    found = true;
                    break;
                }
            }
            assertTrue(found);

        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @Category({RegressionTests.class})
    @Test
    public void getIssuesSinceNumber()
    {
        try
        {
            List<Issue> issues = runFlowAndGetPayload("getIssuesSinceNumber");
            assertTrue(issues.size()>0);
            boolean found = false;
            for (Issue i : issues)
            {
                if (getTestRunMessageValue("issueId").equals(i.getNumber()))
                {
                    found = true;
                    break;
                }
            }
            assertTrue(found);

        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @Category({RegressionTests.class})
    @Test
    public void closeIssue()
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
