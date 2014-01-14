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
import org.eclipse.egit.github.core.Gist;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class GetGistCommentsTestCases extends GitHubTestParent
{
    @Before
    public void setUp() throws Exception
    {
        initializeTestRunMessage("getGistCommentsTestData");
        Gist gist = runFlowAndGetPayload("createGist");
        upsertOnTestRunMessage("gistId", gist.getId());
        Comment comment = runFlowAndGetPayload("createGistComment");
        upsertOnTestRunMessage("commentId", comment.getId());
    }

    @After
    public void tearDown() throws Exception
    {
        runFlowAndGetPayload("deleteGist");
    }

    @Category({RegressionTests.class, GistTests.class})
    @Test
    public void testGetGistComments()
    {
        try
        {
            List<Comment> comments = runFlowAndGetPayload("getGistComments");
            assertTrue(comments.size() > 0);
            boolean found = false;
            for (Comment comment: comments)
            {
                if (getTestRunMessageValue("commentId").equals(comment.getId()))
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
