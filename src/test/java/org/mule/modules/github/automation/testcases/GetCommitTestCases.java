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

import org.eclipse.egit.github.core.RepositoryCommit;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class GetCommitTestCases extends GitHubTestParent
{
    @Before
    public void setUp() throws Exception
    {
        forkTestRepository();
        initializeTestRunMessage("getCommitTestData");
        List<RepositoryCommit> commits = runFlowAndGetPayload("getCommitsBySha");
        upsertOnTestRunMessage("sha", commits.get(0).getSha());

    }


    @Test
    @Category({RegressionTests.class, CommitTests.class})
    public void testGetCommit()
    {

        try
        {
            RepositoryCommit commit = runFlowAndGetPayload("getCommit");
            assertEquals(commit.getSha(), getTestRunMessageValue("sha"));
        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

}
