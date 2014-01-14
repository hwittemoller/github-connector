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

import org.eclipse.egit.github.core.Gist;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class StarGistTestCases extends GitHubTestParent
{
    @Before
    public void setUp() throws Exception
    {
        initializeTestRunMessage("starGistTestData");
        Gist gist = runFlowAndGetPayload("createGist");
        upsertOnTestRunMessage("gistId", gist.getId());
    }

    @After
    public void tearDown() throws Exception
    {
        runFlowAndGetPayload("deleteGist");
    }

    @Category({RegressionTests.class, GistTests.class})
    @Test
    public void testStarGist()
    {
        try
        {
            runFlowAndGetPayload("starGist");
            Boolean starred = runFlowAndGetPayload("isStarred");
            assertTrue(starred);

        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

}
