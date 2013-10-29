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

package org.mule.modules.github.automation.testcases.repository;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.eclipse.egit.github.core.Contributor;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryBranch;
import org.eclipse.egit.github.core.RepositoryTag;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.github.GitHubModule;
import org.mule.modules.github.ServiceFactory;
import org.mule.modules.github.automation.testcases.GutHubTestParent;
import org.mule.modules.github.automation.testcases.RegressionTests;
import org.mule.modules.tests.ConnectorTestUtils;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class CreateRepositoryTestCases extends GutHubTestParent
{
    private String repoOwner = null;

    @Before
    public void setUp() throws Exception
    {
        initializeTestRunMessage("createRepository");
    }

    @After
    public void tearDown() throws Exception
    {
        if (repoOwner!=null){
            deleteRepository(repoOwner, (String)getTestRunMessageValue("repository"));
            repoOwner=null;
        }
    }

    @Category({RegressionTests.class})
    @Test
    public void createRepository()
    {
        try
        {
            Repository repository = runFlowAndGetPayload("createRepository");
            Thread.sleep(5000L);
            assertEquals(getTestRunMessageValue("repository"), repository.getName());
            assertEquals(getTestRunMessageValue("description"), repository.getDescription());
            repoOwner = getTestRunMessageValue("user");

        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @Category({RegressionTests.class})
    @Test
    public void createRepositoryForOrg()
    {
        try
        {
            Repository repository = runFlowAndGetPayload("createRepositoryForOrg");
            Thread.sleep(5000L);
            assertEquals(getTestRunMessageValue("repository"), repository.getName());
            assertEquals(getTestRunMessageValue("description"), repository.getDescription());
            repoOwner = getTestRunMessageValue("organization");

        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @Category({RegressionTests.class})
    @Test
    public void forkRepositoryForOrg()
    {
        try
        {
            Repository repository = runFlowAndGetPayload("forkRepositoryForOrg");
            Thread.sleep(10000L);
            assertEquals(getTestRunMessageValue("repository"), repository.getName());
            repoOwner = getTestRunMessageValue("organization");

        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }


    private void deleteRepository(String owner, String name) throws IOException
    {
        GitHubModule github = new GitHubModule();
        Properties props = getBeanFromContext("testProps");
        String user = props.getProperty("github.userName");
        String pass = props.getProperty("github.secret");
        String scope = props.getProperty("github.scope");
        github.setServiceFactory(new ServiceFactory(user, pass, scope));
        github.deleteRepository(owner, name);
    }

}
