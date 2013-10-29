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

import java.io.IOException;
import java.util.Properties;

import org.eclipse.egit.github.core.Repository;
import org.junit.AfterClass;
import org.mule.modules.github.GitHubModule;
import org.mule.modules.github.ServiceFactory;
import org.mule.modules.tests.ConnectorTestCase;

public class GutHubTestParent extends ConnectorTestCase
{

    //test repository will be forked from mulesoft for some tests
    protected String MULE = "mulesoft";

    protected static Repository repository = null;

    protected void createTestRepository(boolean fork) throws Exception
    {
        GitHubModule github = new GitHubModule();
        Properties props = getBeanFromContext("testProps");
        String user = props.getProperty("github.userName");
        String pass = props.getProperty("github.secret");
        String repoName = props.getProperty("github.repository");
        String scope = props.getProperty("github.scope");
        github.setServiceFactory(new ServiceFactory(user, pass, scope));

        Repository repo = null;
        try
        {
            //delete test repo if it was not cleaned up after previous test
            repo = github.getRepository(user, repoName);
        } catch (IOException e)
        {
            //nothing. IOException happens when repository not found
        }
        if (repo != null)
        {
            deleteTestRepository();
            Thread.sleep(10000L); //repository takes some time to get ready
        }

        if (fork)
        {
            repository = github.forkRepository(MULE, repoName);
            Thread.sleep(15000L); //repository takes some time to get ready
        }
        else
        {
            repository = github.createRepository(repoName, "Repository for functional tests", false, true, true, true);
        }
    }

    @AfterClass
    public static void cleanUpRepo() throws Exception
    {
        if (repository != null)
        {
            new GutHubTestParent().deleteTestRepository();
        }
    }

    protected void deleteTestRepository() throws IOException
    {
        GitHubModule github = new GitHubModule();
        Properties props = getBeanFromContext("testProps");
        String user = props.getProperty("github.userName");
        String pass = props.getProperty("github.secret");
        String repo = props.getProperty("github.repository");
        String scope = props.getProperty("github.scope");
        github.setServiceFactory(new ServiceFactory(user, pass, scope));
        github.deleteRepository(user, repo);
        repository = null;
    }


}
