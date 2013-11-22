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
import org.eclipse.egit.github.core.RepositoryCommit;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

public class DeleteCommentTestCases extends GitHubTestParent
{
    @Before
    public void setUp() throws Exception
    {
        forkTestRepository();
        initializeTestRunMessage("getCommitsTestData");
        List<RepositoryCommit> commits = runFlowAndGetPayload("getCommitsBySha");
        upsertOnTestRunMessage("sha", commits.get(0).getSha());
        CommitComment comment = runFlowAndGetPayload("addComment");
        upsertOnTestRunMessage("commentId", comment.getId());

    }

    @Test
    @Category({RegressionTests.class, CommitTests.class})
    public void testDeleteComment()
    {

        try
        {
            runFlowAndGetPayload("deleteCommitComment");
            List<CommitComment> comments = runFlowAndGetPayload("getCommmitComments");
            boolean found = false;
            for (CommitComment comment: comments)
            {
                if (getTestRunMessageValue("commentId").equals(comment.getId()))
                {
                    found = true;
                }
            }
            assertFalse(found);
        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

}
