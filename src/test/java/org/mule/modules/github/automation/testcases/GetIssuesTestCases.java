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

import org.eclipse.egit.github.core.Issue;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class GetIssuesTestCases extends GitHubTestParent
{
    @Before
    public void setUp() throws Exception
    {
        createTestRepository();
        initializeTestRunMessage("getIssuesTestData");
        Issue issue = runFlowAndGetPayload("createIssue");
        upsertOnTestRunMessage("issueNumber", issue.getNumber());
    }

    @Category({RegressionTests.class, IssueTests.class})
    @Test
    public void testGetIssues()
    {
        try
        {
            List<Issue> issues = runFlowAndGetPayload("getIssues");
            assertTrue(issues.size()>0);
            boolean found = false;
            for (Issue i : issues)
            {
                if (getTestRunMessageValue("issueNumber").equals(i.getNumber()))
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


    @Category({RegressionTests.class, IssueTests.class})
    @Test
    public void testGetIssuesCreatedAfter()
    {
        try
        {
            List<Issue> issues = runFlowAndGetPayload("getIssuesCreatedAfter");
            assertTrue(issues.size()>0);
            boolean found = false;
            for (Issue i : issues)
            {
                if (getTestRunMessageValue("issueNumber").equals(i.getNumber()))
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

    @Category({RegressionTests.class, IssueTests.class})
    @Test
    public void testGetIssuesSinceNumber()
    {
        try
        {
            List<Issue> issues = runFlowAndGetPayload("getIssuesSinceNumber");
            assertTrue(issues.size()>0);
            boolean found = false;
            for (Issue i : issues)
            {
                if (getTestRunMessageValue("issueNumber").equals(i.getNumber()))
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
}
