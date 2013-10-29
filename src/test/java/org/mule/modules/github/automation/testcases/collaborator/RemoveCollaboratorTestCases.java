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

package org.mule.modules.github.automation.testcases.collaborator;

import org.junit.Before;
import org.junit.Test;
import org.mule.modules.github.automation.testcases.GutHubTestParent;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

public class RemoveCollaboratorTestCases extends GutHubTestParent
{
    @Before
    public void setUp() throws Exception
    {
        createTestRepository(false);
        initializeTestRunMessage("collaborators");
        runFlowAndGetPayload("addCollaborator");
    }

    @Test
    public void removeCollaborator()
    {

        try
        {
            runFlowAndGetPayload("removeCollaborator");
            Boolean isCollaborator = runFlowAndGetPayload("isCollaborator");
            assertFalse(isCollaborator);

        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }
}
