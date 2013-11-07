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

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({AddTeamMemberTestCases.class, AddTeamRepositoryTestCases.class, CreateTeamTestCases.class, DeleteTeamTestCases.class,
        EditTeamTestCases.class,GetTeamMembersTestCases.class, GetTeamRepositoriesTestCases.class,GetTeamTestCases.class, RemoveTeamMemberTestCases.class})
public class TeamTestSuite
{
}
