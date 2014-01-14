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

import org.eclipse.egit.github.core.CommitFile;
import org.eclipse.egit.github.core.MergeStatus;
import org.eclipse.egit.github.core.PullRequest;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.client.PageIterator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class GetPullRequestTestCases extends BasePullRequestTestCases
{

    @Before
    public void setUp() throws Exception
    {
        prepareBranch();
        PullRequest pullRequest = runFlowAndGetPayload("createPullRequest");
        upsertOnTestRunMessage("id", pullRequest.getNumber());
    }

    @After
    public void tearDown() throws Exception
    {
        cleanUpRepo();
    }


    @Category({RegressionTests.class, PullRequestTests.class})
    @Test
    public void testGetPullRequest()
    {
        try
        {
            PullRequest pullRequest = runFlowAndGetPayload("getPullRequest");
            assertNotNull(pullRequest);
            assertEquals(getTestRunMessageValue("title"), pullRequest.getTitle());
            assertEquals(getTestRunMessageValue("body"), pullRequest.getBody());
        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @Category({RegressionTests.class, PullRequestTests.class})
    @Test
    public void testGetPullRequests()
    {
        try
        {
            List<PullRequest> pullRequests = runFlowAndGetPayload("getPullRequests");
            assertTrue(pullRequests.size()==1);
            assertTrue("open".equalsIgnoreCase(pullRequests.get(0).getState()));
        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @Category({RegressionTests.class, PullRequestTests.class})
    @Test
    public void testPagePullRequests()
    {
        try
        {
            PageIterator<PullRequest> pagedPullRequest = runFlowAndGetPayload("pagePullRequests");
            assertTrue(pagedPullRequest.hasNext());
        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @Category({RegressionTests.class, PullRequestTests.class})
    @Test
    public void testEditPullRequest()
    {
        try
        {
            PullRequest pullRequest = runFlowAndGetPayload("editPullRequest");
            assertEquals(getTestRunMessageValue("updatedTitle"), pullRequest.getTitle());
        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @Category({RegressionTests.class, PullRequestTests.class})
    @Test
    public void testGetPullRequestCommits()
    {
        try
        {
            List<RepositoryCommit> commits = runFlowAndGetPayload("getPullRequestCommits");
            assertTrue(commits.size() == 1);
            assertTrue(getTestRunMessageValue("owner").equals(commits.get(0).getCommit().getAuthor().getName()) );

        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @Category({RegressionTests.class, PullRequestTests.class})
    @Test
    public void testGetPullRequestFiles()
    {
        try
        {
            List<CommitFile> files = runFlowAndGetPayload("getPullRequestFiles");
            assertTrue(files.size() == 1);
            assertTrue("README.md".equalsIgnoreCase(files.get(0).getFilename()) );

        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @Category({RegressionTests.class, PullRequestTests.class})
    @Test
    public void testIsPullRequestMerged()
    {
        try
        {
            Boolean merged = runFlowAndGetPayload("isPullRequestMerged");
            assertFalse(merged);

        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @Category({RegressionTests.class, PullRequestTests.class})
    @Test
    public void testMergePullRequest()
    {
        try
        {
            MergeStatus mergeStatus = runFlowAndGetPayload("mergePullRequest");
            assertTrue(mergeStatus.isMerged());

        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

}
