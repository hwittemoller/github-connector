package org.mule.modules.github;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.egit.github.core.service.RepositoryService;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mule.api.MuleEvent;
import org.mule.api.processor.MessageProcessor;
import org.mule.tck.junit4.FunctionalTestCase;

import static org.junit.Assert.assertNotNull;

/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

public class BaseAPITest extends FunctionalTestCase {

    //The username of currently authenticated user
    protected String USER = "mule-tester";

    //the password of currently authenticated user
    protected String PASS = "mule2013";

    //The repository name. In most tests it will be forked from Mule and then deleted. Please make sure it does not exist before test.
    protected String REPO = "github-connector";

    //github authentication scope
    protected String SCOPE = "user,repo,public_repo,delete_repo,gist";
    protected GitHubModule github = new GitHubModule();
    protected GitHubModule mockedGithub = new GitHubModule();

    @Mock
    protected ExtendedRepositoryService repositoryService;


    @Override
    protected String getConfigResources() {
        return "mule-config.xml";
    }

    @Override
    protected void doSetUp() throws Exception {
        github.setServiceFactory(new ServiceFactory(USER, PASS, SCOPE));

        MockitoAnnotations.initMocks(this);
        ServiceFactory serviceFactory = new ServiceFactory(USER, PASS, SCOPE);
        serviceFactory.setDefaultRepositoryService(repositoryService);
        mockedGithub.setServiceFactory(serviceFactory);
    }

    protected <T> T runMuleFlow(String flowName, Class<T> type)
            throws Exception
    {
        return runMuleFlow(flowName, type, null);
    }
    
    protected <T> T runMuleFlow(String flowName, Class<T> type, Object data)
            throws Exception
    {
        MuleEvent response = lookupFlow(flowName).process(getTestEvent(data));
        assertNotNull( response );

        Object payload = response.getMessage().getPayload();
        assertNotNull( payload );
        return type==null ? null : type.cast(payload);
    }


    protected MessageProcessor lookupFlow(final String flowName)
    {
        return (MessageProcessor) muleContext.getRegistry().lookupFlowConstruct(flowName);
    }

}
