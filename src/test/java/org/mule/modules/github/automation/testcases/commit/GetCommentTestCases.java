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

package org.mule.modules.github.automation.testcases.commit;

import java.util.List;

import org.eclipse.egit.github.core.CommitComment;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.github.automation.testcases.GutHubTestParent;
import org.mule.modules.github.automation.testcases.RegressionTests;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class GetCommentTestCases extends GutHubTestParent
{
    @Before
    public void setUp() throws Exception
    {
        if (repository==null)
            createTestRepository(true);

        initializeTestRunMessage("commits");
        List<RepositoryCommit> commits = runFlowAndGetPayload("getCommitsBySha");
        upsertOnTestRunMessage("sha", commits.get(0).getSha());
        CommitComment comment = runFlowAndGetPayload("addComment");
        upsertOnTestRunMessage("commentId", comment.getId());

    }

    @After
    public void tearDown() throws Exception
    {
        runFlowAndGetPayload("deleteCommitComment");
    }

    @Test
    @Category({RegressionTests.class})
    public void getComment()
    {

        try
        {
            CommitComment comment = runFlowAndGetPayload("getComment");
            assertEquals(getTestRunMessageValue("commentId"), comment.getId());
            assertEquals(getTestRunMessageValue("body"), comment.getBody());
        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @Test
    @Category({RegressionTests.class})
    public void getComments()
    {

        try
        {
            List<CommitComment> comments = runFlowAndGetPayload("getCommmitComments");
            assertTrue(comments.size()>0);

            boolean found = false;
            for (CommitComment com : comments)
            {
                if (getTestRunMessageValue("commentId").equals(com.getId()))
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
