package org.mule.modules.github;

import java.util.List;
import java.util.Map;

import org.eclipse.egit.github.core.Contributor;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryBranch;
import org.eclipse.egit.github.core.RepositoryTag;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 * <p/>
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

@SuppressWarnings("unchecked")
public class RepositoryAPITest extends BaseAPITest
{
    @Test
    public void testRepositoryAPI() throws Exception
    {
        Repository repository = createTestRepository(true);
        assertNotNull(repository);

        List<Repository> repositories = runMuleFlow("getRepositories", List.class);
        assertNotNull(repositories);
        assertTrue(repositories.size() > 0);

        repositories = runMuleFlow("getRepositoriesForUser", List.class);
        assertNotNull(repositories);
        assertTrue(repositories.size() > 0);

        repositories = runMuleFlow("getOrgRepositories", List.class);
        assertNotNull(repositories);
        assertTrue(repositories.size() > 0);

        repositories = runMuleFlow("getForks", List.class);
        assertNotNull(repositories);
        assertTrue(repositories.size() > 0);

        repository = runMuleFlow("createRepository", Repository.class);
        assertNotNull(repository);
        assertEquals("cool-repo", repository.getName());
        assertEquals("this is a cool repo", repository.getDescription());
        github.deleteRepository(USER, "cool-repo");

        repository = runMuleFlow("createRepositoryForOrg", Repository.class);
        assertNotNull(repository);
        assertEquals("cool-repo-for-org", repository.getName());
        assertEquals("this is a cool repo", repository.getDescription());
        github.deleteRepository("mule-testers-coalition", "cool-repo-for-org");

        repository = runMuleFlow("forkRepositoryForOrg", Repository.class);
        assertNotNull(repository);
        assertEquals("github-connector", repository.getName());
        github.deleteRepository("mule-testers-coalition", "github-connector");

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

        deleteTestRepository();

    }
}
