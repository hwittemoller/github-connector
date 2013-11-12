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
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class CreatePullRequestCommentTestCases extends BasePullRequestTestCases
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

    }

    @Category({RegressionTests.class, PullRequestTests.class})
    @Test
    public void createPullRequestComment()
    {
        try
        {
            CommitComment comment = runFlowAndGetPayload("createPullRequestComment");
            assertNotNull(comment);
            assertEquals(getTestRunMessageValue("comment"), comment.getBody());
        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }


}
