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

import org.eclipse.egit.github.core.Key;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

public class DeleteKeyTestCases extends GitHubTestParent
{
    @Before
    public void setUp() throws Exception
    {        
        initializeTestRunMessage("deleteKeyTestData");
        Key key = runFlowAndGetPayload("createKey");
        upsertOnTestRunMessage("id", key.getId());
    }

    @Test
    @Category({RegressionTests.class, UserTests.class})
    public void testDeleteKey()
    {
        try
        {
            runFlowAndGetPayload("deleteKey");

            List<Key> keys = runFlowAndGetPayload("getKeys");
            boolean found = false;
            for (Key k : keys)
                if (getTestRunMessageValue("id").equals(k.getId()))
                {
                    found = true;
                    break;
                }

            assertFalse(found);

        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

}
