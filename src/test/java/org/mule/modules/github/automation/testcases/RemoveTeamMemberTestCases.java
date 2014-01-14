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
import org.eclipse.egit.github.core.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class RemoveTeamMemberTestCases extends GitHubTestParent
{
	
    @Before
    public void setUp() throws Exception
    {
        initializeTestRunMessage("removeTeamMemberTestData");
        Team team = runFlowAndGetPayload("createTeam");
        upsertOnTestRunMessage("id", team.getId());
        runFlowAndGetPayload("addTeamMember");
    }
    
    @After
    public void tearDown() throws Exception
    {
        runFlowAndGetPayload("deleteTeam");
    }

	@Category({RegressionTests.class, TeamTests.class})
    @Test
    public void testRemoveTeamMember()
    {
        try
        {          
        	runFlowAndGetPayload("removeTeamMember");
        	
        	List<User> members = runFlowAndGetPayload("getTeamMembers");        	
            assertTrue(members.size() == 0);         	 
            
        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

}
