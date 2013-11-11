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

package org.mule.modules.github.automation;

import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.mule.modules.github.automation.testcases.AddTeamMemberTestCases;
import org.mule.modules.github.automation.testcases.AddTeamRepositoryTestCases;
import org.mule.modules.github.automation.testcases.CreateTeamTestCases;
import org.mule.modules.github.automation.testcases.DeleteTeamTestCases;
import org.mule.modules.github.automation.testcases.EditTeamTestCases;
import org.mule.modules.github.automation.testcases.GetTeamMembersTestCases;
import org.mule.modules.github.automation.testcases.GetTeamRepositoriesTestCases;
import org.mule.modules.github.automation.testcases.GetTeamTestCases;
import org.mule.modules.github.automation.testcases.GetTeamsForOrgTestCases;
import org.mule.modules.github.automation.testcases.IsTeamMemberTestCases;
import org.mule.modules.github.automation.testcases.RemoveTeamMemberTestCases;
import org.mule.modules.github.automation.testcases.RemoveTeamRepositoryTestCases;
import org.mule.modules.github.automation.testcases.TeamTests;

@RunWith(Categories.class)
@Categories.IncludeCategory(TeamTests.class)
@Suite.SuiteClasses({AddTeamMemberTestCases.class, AddTeamRepositoryTestCases.class, CreateTeamTestCases.class, DeleteTeamTestCases.class,
        EditTeamTestCases.class, GetTeamMembersTestCases.class, GetTeamRepositoriesTestCases.class, GetTeamsForOrgTestCases.class,
        GetTeamTestCases.class, IsTeamMemberTestCases.class, RemoveTeamMemberTestCases.class, RemoveTeamRepositoryTestCases.class})
public class TeamTestSuite
{
}
