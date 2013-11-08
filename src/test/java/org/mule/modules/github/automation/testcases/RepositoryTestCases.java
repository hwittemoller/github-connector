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
import java.util.Map;

import org.eclipse.egit.github.core.Contributor;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryBranch;
import org.eclipse.egit.github.core.RepositoryTag;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class RepositoryTestCases extends GitHubTestParent
{
    @Before
    public void setUp() throws Exception
    {
        if (repository==null)
            createTestRepository(false);

        initializeTestRunMessage("repositoryTestData");
    }

    @Category({RegressionTests.class, RepositoryTests.class})
    @Test
    public void getRepositories()
    {
        try
        {
            List<Repository> repositories = runFlowAndGetPayload("getRepositories");
            assertTrue(repositories.size() > 0);

        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @Category({RegressionTests.class, RepositoryTests.class})
    @Test
    public void getRepositoriesForUser()
    {
        try
        {
            List<Repository> repositories = runFlowAndGetPayload("getRepositoriesForUser");
            assertTrue(repositories.size() > 0);

        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }


    @Category({RegressionTests.class, RepositoryTests.class})
    @Test
    public void getOrgRepositories()
    {
        try
        {
            List<Repository> repositories = runFlowAndGetPayload("getOrgRepositories");
            assertTrue(repositories.size() > 0);

        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @Category({RegressionTests.class, RepositoryTests.class})
    @Test
    public void getForks()
    {
        try
        {
            List<Repository> repositories = runFlowAndGetPayload("getForks");
            assertTrue(repositories.size() > 0);

        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @Category({RegressionTests.class, RepositoryTests.class})
    @Test
    public void getLanguages()
    {
        try
        {
            Map<String, Long> languages = runFlowAndGetPayload("getLanguages");
            assertTrue(languages.size() > 0);
            assertTrue(languages.containsKey("Java"));

        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @Category({RegressionTests.class, RepositoryTests.class})
    @Test
    public void getBranches()
    {
        try
        {
            List<RepositoryBranch> branches = runFlowAndGetPayload("getBranches");
            assertTrue(branches.size() > 0);

        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @Category({RegressionTests.class, RepositoryTests.class})
    @Test
    public void getTags()
    {
        try
        {
            List<RepositoryTag> tags = runFlowAndGetPayload("getTags");
            assertTrue(tags.size() > 0);

        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @Category({RegressionTests.class, RepositoryTests.class})
    @Test
    public void getContributors()
    {
        try
        {
            List<Contributor> contributors = runFlowAndGetPayload("getContributors");
            assertTrue(contributors.size() > 0);

        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }
}
