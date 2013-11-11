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

import org.eclipse.egit.github.core.Label;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public class GetLabelTestCases extends GitHubTestParent
{
    @Before
    public void setUp() throws Exception
    {
        createTestRepository();
        initializeTestRunMessage("getLabelTestData");
        runFlowAndGetPayload("createLabel");
    }

    @After
    public void tearDown() throws Exception
    {
        runFlowAndGetPayload("deleteLabel");
    }

    @Category({RegressionTests.class, LabelTests.class})
    @Test
    public void getLabel()
    {
        try
        {
            Label lbl = runFlowAndGetPayload("getLabel");
            assertEquals(getTestRunMessageValue("labelName"), lbl.getName());
            assertEquals(getTestRunMessageValue("color"), lbl.getColor());

        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

}
