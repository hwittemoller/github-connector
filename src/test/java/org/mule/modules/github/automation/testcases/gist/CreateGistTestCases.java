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

package org.mule.modules.github.automation.testcases.gist;

import org.eclipse.egit.github.core.Gist;
import org.eclipse.egit.github.core.Label;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.github.automation.testcases.GutHubTestParent;
import org.mule.modules.github.automation.testcases.RegressionTests;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class CreateGistTestCases extends GutHubTestParent
{
    @Before
    public void setUp() throws Exception
    {
        initializeTestRunMessage("gist");
    }

    @After
    public void tearDown() throws Exception
    {
        runFlowAndGetPayload("deleteGist");
    }

    @Category({RegressionTests.class})
    @Test
    public void createGist()
    {
        try
        {
            Gist gist = runFlowAndGetPayload("createGist");
            assertNotNull(gist);
            assertEquals(getTestRunMessageValue("description"), gist.getDescription());
            assertTrue(gist.getFiles().containsKey((String) getTestRunMessageValue("filename")));
            upsertOnTestRunMessage("gistId",gist.getId());

        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }


}
