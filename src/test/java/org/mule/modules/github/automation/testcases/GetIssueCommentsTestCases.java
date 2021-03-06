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

import org.eclipse.egit.github.core.Comment;
import org.eclipse.egit.github.core.Issue;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class GetIssueCommentsTestCases extends GitHubTestParent
{
    @Before
    public void setUp() throws Exception
    {
        createTestRepository();
        initializeTestRunMessage("getCommentsTestData");
        Issue issue = runFlowAndGetPayload("createIssue");
        upsertOnTestRunMessage("issueNumber", issue.getNumber());
        Comment comment = runFlowAndGetPayload("createComment");
        upsertOnTestRunMessage("commentId", comment.getId());
    }

    @Category({RegressionTests.class, IssueTests.class})
    @Test
    public void testGetIssueComments()
    {
        try
        {
            List<Comment> comments = runFlowAndGetPayload("getComments");
            assertTrue(comments.size()>0);
            boolean found = false;
            for (Comment c : comments)
            {
                if (getTestRunMessageValue("commentId").equals(c.getId()))
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
