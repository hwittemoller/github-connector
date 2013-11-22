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

import org.eclipse.egit.github.core.Milestone;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.junit.Assert.fail;

public class DeleteMilestoneTestCases extends GitHubTestParent
{
    @Before
    public void setUp() throws Exception
    {
        createTestRepository();
        initializeTestRunMessage("deleteMilestoneTestData");
        Milestone milestone = runFlowAndGetPayload("createMilestone");
        upsertOnTestRunMessage("number", milestone.getNumber());
    }

    @Category({RegressionTests.class, MilestoneTests.class})
    @Test
    public void testDeleteMilestone()
    {
        try
        {
            runFlowAndGetPayload("deleteMilestone");

        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }


}
