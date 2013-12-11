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

import org.eclipse.egit.github.core.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public class GetFollowingTestCases extends GitHubTestParent
{

    @Before
    public void setUp() throws Exception
    {
        initializeTestRunMessage("getFollowingTestData");
        runFlowAndGetPayload("follow");
    }

    @After
    public void tearDown() throws Exception
    {
        runFlowAndGetPayload("unfollow");
    }

    @Category({RegressionTests.class, UserTests.class})
    @Test
    public void testGetFollowing()
    {
        try
        {
        	List<User> following = runFlowAndGetPayload("getFollowing");
            assertNotNull(following);

            boolean found = false;
            for (User user: following)
            {
                if (getTestRunMessageValue("user").equals(user.getLogin()))
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
