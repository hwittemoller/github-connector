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

import org.eclipse.egit.github.core.Key;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class EditKeyTestCases extends GitHubTestParent
{
    @Before
    public void setUp() throws Exception
    {        
        initializeTestRunMessage("editKeyTestData");
        Key key = runFlowAndGetPayload("createKey");
        upsertOnTestRunMessage("id", key.getId());
    }

    @After
    public void tearDown() throws Exception
    {
        runFlowAndGetPayload("deleteKey");
    }

    @Test
    @Category({RegressionTests.class, UserTests.class})
    public void testEditKey()
    {
        try
        {
            Key key = runFlowAndGetPayload("editKey");
            assertEquals(getTestRunMessageValue("updatedTitle"), key.getTitle());

        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

}
