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

import org.eclipse.egit.github.core.Team;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class GetTeamTestCases extends GitHubTestParent
{
    @Before
    public void setUp() throws Exception
    {
        initializeTestRunMessage("team");
        Team team = runFlowAndGetPayload("createTeam");
        upsertOnTestRunMessage("teamId", team.getId());
    }

    @After
    public void tearDown() throws Exception
    {
        runFlowAndGetPayload("deleteTeam");
    }

    @Category({RegressionTests.class, TeamTests.class})
    @Test
    public void getTeam()
    {
        try
        {
            Team team = runFlowAndGetPayload("getTeam");
            assertNotNull(team);            
            assertEquals(getTestRunMessageValue("teamName"), team.getName());            

        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @Category({RegressionTests.class, TeamTests.class})
    @Test
    public void getTeams()
    {
        try
        {
        	List<Team> teams = runFlowAndGetPayload("getTeamsForOrg");
            assertTrue(teams.size() > 0);
            boolean found = false;
            for (Team t : teams)
            {
                if (getTestRunMessageValue("teamId").equals(t.getId()))
                {
                    found = true;
                    break;
                }
            }
            assertTrue(found);

        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }


}
