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

import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.RepositoryContents;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class GetCommitsTestCases extends GitHubTestParent
{
    @Before
    public void setUp() throws Exception
    {
        forkTestRepository();
        initializeTestRunMessage("getCommitsTestData");

        //update Readme in master
        List<RepositoryContents> retrievedContents = runFlowAndGetPayload("getReadme");
        RepositoryContents file = retrievedContents.get(0);
        upsertOnTestRunMessage("fileSha", file.getSha());
        runFlowAndGetPayload("updateReadme");
        Thread.sleep(5000);
    }

    @Test
    @Category({RegressionTests.class, CommitTests.class})
    public void testGetCommits()
    {

        try
        {
            List<RepositoryCommit> commits = runFlowAndGetPayload("getCommits");
            assertTrue(commits.size() > 0);
            boolean found  = false;
            for (RepositoryCommit commit: commits)
            {
                if (getTestRunMessageValue("owner").equals(commit.getCommit().getCommitter().getName()))
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

    @Test
    @Category({RegressionTests.class, CommitTests.class})
    public void testGetCommitsBySha()
    {

        try
        {
            List<RepositoryCommit> commits = runFlowAndGetPayload("getCommitsBySha");
            assertTrue(commits.size() > 0);
            boolean found  = false;
            for (RepositoryCommit commit: commits)
            {
                if (getTestRunMessageValue("owner").equals(commit.getCommit().getCommitter().getName()))
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
