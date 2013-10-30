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

package org.mule.modules.github.automation.testcases.team;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.egit.github.core.Team;
import org.eclipse.egit.github.core.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.github.automation.testcases.GutHubTestParent;
import org.mule.modules.github.automation.testcases.RegressionTests;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class CreateTeamTestCases extends GutHubTestParent
{
	
    @Before
    public void setUp() throws Exception
    {
        initializeTestRunMessage("team");
    }
    
    @After
    public void tearDown() throws Exception
    {
        runFlowAndGetPayload("deleteTeam");
    }

	@Category({RegressionTests.class})
    @Test
    public void CreateTeam()
    {
        try
        {          
        	Team team = runFlowAndGetPayload("createTeam");
        	assertNotNull(team);
            assertEquals(getTestRunMessageValue("teamName"), team.getName());
            upsertOnTestRunMessage("teamId", team.getId()); 
            
        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

}
