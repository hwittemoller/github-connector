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

import org.eclipse.egit.github.core.Repository;
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

public class AddTeamRepositoryTestCases extends GutHubTestParent
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

	@Category({RegressionTests.class})
    @Test
    public void addTeamRepository()
    {
        try
        {  
        	Team team = runFlowAndGetPayload("getTeam");
        	
        	System.out.println("\n\n\n\n\n\n\n!!!");
            System.out.println("Team: " + team.getName());
            System.out.println("\n\n\n\n\n\n\n!!!");
        	
            //Repository repo = runFlowAndGetPayload("forkRepositoryForOrg");
            
        	runFlowAndGetPayload("addTeamRepository");
        	Thread.sleep(5000L);
            //assertNotNull(repo);
            
            //System.out.println("\n\n\n\n\n\n\n!!!");
            //for(int i=0; i<repo.size(); i++) System.out.println("Size: " + repo.get(i));
            //System.out.println("\n\n\n\n\n\n\n!!!");
            
        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

}
