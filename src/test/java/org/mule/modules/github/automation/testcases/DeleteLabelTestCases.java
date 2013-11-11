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

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.junit.Assert.fail;

public class DeleteLabelTestCases extends GitHubTestParent
{

    @Before
    public void setUp() throws Exception
    {
        createTestRepository();
        initializeTestRunMessage("createLabelTestData");
        runFlowAndGetPayload("createLabel");
    }

    @Category({RegressionTests.class, LabelTests.class})
    @Test
    public void deleteLabel()
    {
        try
        {
            runFlowAndGetPayload("deleteLabel");

        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }


}
