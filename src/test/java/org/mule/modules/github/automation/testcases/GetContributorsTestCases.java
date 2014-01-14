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

import org.eclipse.egit.github.core.Contributor;
import org.eclipse.egit.github.core.RepositoryContents;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class GetContributorsTestCases extends GitHubTestParent
{
    @Before
    public void setUp() throws Exception
    {
        forkTestRepository();
        initializeTestRunMessage("getContributorsTestData");

        //update Readme in master
        List<RepositoryContents> retrievedContents = runFlowAndGetPayload("getReadme");
        RepositoryContents file = retrievedContents.get(0);
        upsertOnTestRunMessage("fileSha", file.getSha());
        runFlowAndGetPayload("updateReadme");
        Thread.sleep(5000);

    }

    @Category({RegressionTests.class, RepositoryTests.class})
    @Test
    public void testGetContributors()
    {
        try
        {
            List<Contributor> contributors = runFlowAndGetPayload("getContributors");
            assertTrue(contributors.size() > 0);

            boolean found = false;
            for (Contributor contributor: contributors)
            {
                if (getTestRunMessageValue("owner").equals(contributor.getLogin()))
                {
                    found = true;
                    break;
                }
            }
            assertTrue(found);

        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }
}
