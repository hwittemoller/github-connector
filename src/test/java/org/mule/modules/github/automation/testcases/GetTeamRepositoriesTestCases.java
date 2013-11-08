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

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.Team;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class GetTeamRepositoriesTestCases extends GitHubTestParent
{
	
    @Before
    public void setUp() throws Exception
    {
        initializeTestRunMessage("teamTestData");
        Team team = runFlowAndGetPayload("createTeam");
        upsertOnTestRunMessage("teamId", team.getId());
        runFlowAndGetPayload("forkRepositoryForOrg", "createRepositoryTestData");
        Thread.sleep(10000L);
        runFlowAndGetPayload("addTeamRepository");

    }
    
    @After
    public void tearDown() throws Exception
    {
        runFlowAndGetPayload("deleteTeam");
        deleteRepository((String)getTestRunMessageValue("organization"), (String)getTestRunMessageValue("repository"));
    }

	@Category({RegressionTests.class, TeamTests.class})
    @Test
    public void getTeamRepositories()
    {
        try
        {
            List<Repository> repositories = runFlowAndGetPayload("getTeamRepositories");
            assertTrue(repositories.size() == 1);

        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @Category({RegressionTests.class, TeamTests.class})
    @Test
    public void removeTeamRepository()
    {
        try
        {
            runFlowAndGetPayload("removeTeamRepository");
            List<Repository> repositories = runFlowAndGetPayload("getTeamRepositories");
            assertTrue(repositories.size() == 0);

        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

}
