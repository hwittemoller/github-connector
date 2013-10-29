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

package org.mule.modules.github.automation.testcases.commit;

import java.util.List;

import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.github.automation.testcases.GutHubTestParent;
import org.mule.modules.github.automation.testcases.RegressionTests;
import org.mule.modules.github.automation.testcases.SmokeTests;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class GetCommitsTestCases extends GutHubTestParent
{
    @Before
    public void setUp() throws Exception
    {
        if (repository==null)
            createTestRepository(true);

        initializeTestRunMessage("commits");
    }

    @Test
    @Category({RegressionTests.class})
    public void getCommits()
    {

        try
        {
            List<RepositoryCommit> commits = runFlowAndGetPayload("getCommits");
            assertTrue(commits.size() > 0);
        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

    @Test
    @Category({SmokeTests.class, RegressionTests.class})
    public void getCommitsBySha()
    {

        try
        {
            List<RepositoryCommit> commits = runFlowAndGetPayload("getCommitsBySha");
            assertTrue(commits.size() > 0);
        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

}
