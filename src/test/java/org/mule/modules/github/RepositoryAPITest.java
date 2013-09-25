package org.mule.modules.github;

import org.eclipse.egit.github.core.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.refEq;
import static org.mockito.Mockito.when;

/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

public class RepositoryAPITest extends BaseAPITest
{
    @Test
    public void testRepositoryAPI()
            throws Exception
    {
        String MULE = "mulesoft";
        String REPO = "github-connector";
        String ORG  = "my-org";
        String DESC = "test-description";

        List<Repository> repositories = runMuleFlow("getRepositories", List.class);
        assertNotNull(repositories);
        assertTrue(repositories.size()>0);

        repositories = runMuleFlow("getRepositoriesForUser", List.class);
        assertNotNull(repositories);
        assertTrue(repositories.size() > 0);

        repositories = runMuleFlow("getOrgRepositories", List.class);
        assertNotNull(repositories);
        assertTrue(repositories.size()>0);

        repositories = runMuleFlow("getForks", List.class);
        assertNotNull(repositories);
        assertTrue(repositories.size()>0);

        Repository repo = new Repository();
        repo.setName(REPO);
        repo.setDescription(DESC);

        //mocked test for createRepository because API does not have method for deleting created repository
        //change mockedGithub to github if you want to run real test but remember that you'll have to manually delete created repo
        when(repositoryService.createRepository(refEq(repo, "parent","source","owner"))).thenReturn(repo);
        Repository repository = mockedGithub.createRepository(REPO, DESC, false, false, false, false);
        assertEquals(repo, repository);

        //TODO: create repository for org
        when(repositoryService.createRepository(eq(ORG), refEq(repo, "parent","source","owner"))).thenReturn(repo);
        repository = mockedGithub.createRepositoryForOrg(ORG, REPO, DESC, false, false, false, false);
        assertEquals(repo, repository);

        //TODO: forkRepositoryFor
        when(repositoryService.forkRepository(eq(new RepositoryId(MULE, REPO)))).thenReturn(repo);
        repository = mockedGithub.forkRepository(MULE, REPO);
        assertEquals(repo, repository);

        //TODO: forkRepositoryForOrg
        when(repositoryService.forkRepository(eq(new RepositoryId(MULE, REPO)),eq(ORG))).thenReturn(repo);
        repository = mockedGithub.forkRepositoryForOrg(ORG, MULE, REPO);
        assertEquals(repo, repository);

        Map<String, Long> languages = runMuleFlow("getLanguages", Map.class);
        assertNotNull(languages);
        assertTrue(languages.containsKey("Java"));

        List<RepositoryBranch> branches = runMuleFlow("getBranches", List.class);
        assertNotNull(branches);
        assertTrue(branches.size() > 0);

        List<RepositoryTag> tags = runMuleFlow("getTags", List.class);
        assertNotNull(tags);
        assertTrue(tags.size() > 0);

        List<Contributor> contributors = runMuleFlow("getContributors", List.class);
        assertNotNull(contributors);
        assertTrue(contributors.size() > 0);

    }
}
