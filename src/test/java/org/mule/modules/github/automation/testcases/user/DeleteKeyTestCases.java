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

package org.mule.modules.github.automation.testcases.user;

import org.eclipse.egit.github.core.Key;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.github.automation.testcases.GitHubTestParent;
import org.mule.modules.github.automation.testcases.RegressionTests;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.junit.Assert.fail;

public class DeleteKeyTestCases extends GitHubTestParent
{
    @Before
    public void setUp() throws Exception
    {        
        initializeTestRunMessage("key");
        Key key = runFlowAndGetPayload("createKey");
        upsertOnTestRunMessage("keyId", key.getId());
    }

    @Test
    @Category({RegressionTests.class})
    public void deleteKey()
    {
        try
        {
            runFlowAndGetPayload("deleteKey");

        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

}
