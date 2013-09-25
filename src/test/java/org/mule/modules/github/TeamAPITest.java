package org.mule.modules.github;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.Team;
import org.eclipse.egit.github.core.User;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.*;

/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 * <p/>
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

@SuppressWarnings("unchecked")
public class TeamAPITest extends BaseAPITest {
    @Test
    public void testTeamAPI()
            throws Exception {
        //values used in mule-config.xml
        String REPO = "github-connector";      //The repository name. Will be forked from Mule and then deleted. Please make sure it does not exist before test.
        String ORG = "mule-github-api-test";   //The organization name. Organization must exist and authenticated user must be it's admin
        String TEAM = "github-connector-team"; //The team name. Team will be created during test and then deleted. Please make sure it does not exist before test.


        Team team = runMuleFlow("createTeam", Team.class);
        assertNotNull(team);

        List<Team> teams = runMuleFlow("getTeamsForOrg", List.class);
        assertNotNull(teams);
        assertTrue(teams.size() > 0);

        int teamId = 0;
        for (Team t : teams) {
            if (TEAM.equals(t.getName()))
                teamId = t.getId();
        }
        assertTrue(teamId > 0);

        Map<String, Integer> params = Collections.singletonMap("teamId", teamId);
        team = runMuleFlow("getTeam", Team.class, params);
        assertNotNull(team);
        assertEquals(TEAM, team.getName());

        team = runMuleFlow("editTeam", Team.class, params);
        assertNotNull(team);
        assertEquals("new-team-name", team.getName());

        runMuleFlow("addTeamMember", null, params);

        List<User> members = runMuleFlow("getTeamMembers", List.class, params);
        assertNotNull(members);
        assertTrue(members.size() == 1);

        boolean isTeamMember = runMuleFlow("isTeamMember", Boolean.class, params);
        assertTrue(isTeamMember);

        runMuleFlow("removeTeamMember", null, params);
        members = runMuleFlow("getTeamMembers", List.class, params);
        assertTrue(members.size() == 0);

        Repository repo = runMuleFlow("forkRepositoryForOrg", Repository.class);
        assertNotNull(repo);

        runMuleFlow("addTeamRepository", null, params);

        List<Repository> repositories = runMuleFlow("getTeamRepositories", List.class, params);
        assertNotNull(repositories);
        assertTrue(repositories.size() == 1);

        runMuleFlow("removeTeamRepository", null, params);
        repositories = runMuleFlow("getTeamRepositories", List.class, params);
        assertTrue(repositories.size() == 0);

        runMuleFlow("deleteTeam", null, params);

        github.deleteRepository(ORG, REPO); //cleanup

    }
}
