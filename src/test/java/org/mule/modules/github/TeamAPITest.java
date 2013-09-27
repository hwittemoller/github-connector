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

public class TeamAPITest extends BaseAPITest {

    @Test
    public void testTeamAPI() throws Exception {

        Team team = runMuleFlow("createTeam", Team.class);
        assertNotNull(team);
        assertEquals("github-connector-testing-team", team.getName());
        int teamId = team.getId();

        List<Team> teams = runMuleFlow("getTeamsForOrg", List.class);
        assertNotNull(teams);
        assertTrue(teams.size() > 0);
        boolean found = false;
        for (Team t: teams){
            if (t.getId()==teamId){
                found = true;
                break;
            }
        }
        assertTrue(found);

        Map<String, Integer> teamIdParameter = Collections.singletonMap("teamId", teamId);
        team = runMuleFlow("getTeam", Team.class, teamIdParameter);
        assertNotNull(team);
        assertEquals("github-connector-testing-team", team.getName());

        team = runMuleFlow("editTeam", Team.class, teamIdParameter);
        assertNotNull(team);
        assertEquals("new-team-name", team.getName());

        runMuleFlow("addTeamMember", null, teamIdParameter);

        List<User> members = runMuleFlow("getTeamMembers", List.class, teamIdParameter);
        assertNotNull(members);
        assertTrue(members.size() == 1);

        boolean isTeamMember = runMuleFlow("isTeamMember", Boolean.class, teamIdParameter);
        assertTrue(isTeamMember);

        runMuleFlow("removeTeamMember", null, teamIdParameter);
        members = runMuleFlow("getTeamMembers", List.class, teamIdParameter);
        assertTrue(members.size() == 0);

        Repository repo = runMuleFlow("forkRepositoryForOrg", Repository.class);
        assertNotNull(repo);

        runMuleFlow("addTeamRepository", null, teamIdParameter);

        List<Repository> repositories = runMuleFlow("getTeamRepositories", List.class, teamIdParameter);
        assertNotNull(repositories);
        assertTrue(repositories.size() == 1);

        runMuleFlow("removeTeamRepository", null, teamIdParameter);
        repositories = runMuleFlow("getTeamRepositories", List.class, teamIdParameter);
        assertTrue(repositories.size() == 0);

        runMuleFlow("deleteTeam", null, teamIdParameter);

        github.deleteRepository("mule-testers-coalition", REPO); //cleanup

    }
}
