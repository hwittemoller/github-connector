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

import org.eclipse.egit.github.core.CommitComment;
import org.eclipse.egit.github.core.PullRequest;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.client.PageIterator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class GetPullRequestCommentTestCases extends BasePullRequestTestCases
{
    @Before
    public void setUp() throws Exception
    {
        prepareBranch();
        PullRequest pullRequest = runFlowAndGetPayload("createPullRequest");
        upsertOnTestRunMessage("id", pullRequest.getNumber());
        List<RepositoryCommit> commits = runFlowAndGetPayload("getPullRequestCommits");
        RepositoryCommit aCommit = commits.get(0);
        upsertOnTestRunMessage("commitId", aCommit.getSha());
        CommitComment comment = runFlowAndGetPayload("createPullRequestComment");
        upsertOnTestRunMessage("commentId", comment.getId());

    }

    @After
    public void tearDown() throws Exception
    {
        cleanUpRepo();
    }

    @Category({RegressionTests.class, PullRequestTests.class})
    @Test
    public void testGetPullRequestComment()
    {
        try
        {
            CommitComment comment = runFlowAndGetPayload("getPullRequestComment");
            assertNotNull(comment);
            assertEquals(getTestRunMessageValue("comment"), comment.getBody());
        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @Category({RegressionTests.class, PullRequestTests.class})
    @Test
    public void testGetPullRequestComments()
    {
        try
        {
            List<CommitComment> comments = runFlowAndGetPayload("getPullRequestComments");
            assertTrue(comments.size() > 0);
        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @Category({RegressionTests.class, PullRequestTests.class})
    @Test
    public void testPagePullRequestComments()
    {
        try
        {
            PageIterator<CommitComment> pagedComments = runFlowAndGetPayload("pagePullRequestComments");
            assertTrue(pagedComments.hasNext());
        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @Category({RegressionTests.class, PullRequestTests.class})
    @Test
    public void testEditPullRequestComment()
    {
        try
        {
            CommitComment comment = runFlowAndGetPayload("editPullRequestComment");
            assertEquals(getTestRunMessageValue("updatedComment"), comment.getBody());
        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @Category({RegressionTests.class, PullRequestTests.class})
    @Test
    public void testDeletePullRequestComment()
    {
        try
        {
            runFlowAndGetPayload("deletePullRequestComment");
        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

}
