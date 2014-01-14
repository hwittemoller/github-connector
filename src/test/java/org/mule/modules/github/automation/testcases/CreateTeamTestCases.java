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

import org.eclipse.egit.github.core.Team;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class CreateTeamTestCases extends GitHubTestParent
{
	
    @Before
    public void setUp() throws Exception
    {
        initializeTestRunMessage("createTeamTestData");
    }
    
    @After
    public void tearDown() throws Exception
    {
        runFlowAndGetPayload("deleteTeam");
    }

	@Category({RegressionTests.class, TeamTests.class, SmokeTests.class})
    @Test
    public void testCreateTeam()
    {
        try
        {          
        	Team team = runFlowAndGetPayload("createTeam");
        	assertNotNull(team);
            assertEquals(getTestRunMessageValue("team"), team.getName());
            upsertOnTestRunMessage("id", team.getId());
            
        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

}
