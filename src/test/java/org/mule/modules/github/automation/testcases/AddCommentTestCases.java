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
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class AddCommentTestCases extends GitHubTestParent
{
    @Before
    public void setUp() throws Exception
    {
        forkTestRepository();
        initializeTestRunMessage("getCommitsTestData");
        List<RepositoryCommit> commits = runFlowAndGetPayload("getCommitsBySha");
        upsertOnTestRunMessage("sha", commits.get(0).getSha());
    }

    @After
    public void tearDown() throws Exception
    {
        runFlowAndGetPayload("deleteCommitComment");
    }

    @Test
    @Category({RegressionTests.class, CommitTests.class})
    public void testAddComment()
    {

        try
        {
            CommitComment comment = runFlowAndGetPayload("addComment");
            assertEquals(getTestRunMessageValue("body"), comment.getBody());
            upsertOnTestRunMessage("commentId", comment.getId());
        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

}
