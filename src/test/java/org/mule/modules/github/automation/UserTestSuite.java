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

package org.mule.modules.github.automation;

import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.mule.modules.github.automation.testcases.AddEmailsTestCases;
import org.mule.modules.github.automation.testcases.CreateKeyTestCases;
import org.mule.modules.github.automation.testcases.DeleteKeyTestCases;
import org.mule.modules.github.automation.testcases.FollowTestCases;
import org.mule.modules.github.automation.testcases.GetCurrentUserTestCases;
import org.mule.modules.github.automation.testcases.GetEmailsTestCases;
import org.mule.modules.github.automation.testcases.GetFollowersTestCases;
import org.mule.modules.github.automation.testcases.GetFollowingTestCases;
import org.mule.modules.github.automation.testcases.GetKeyTestCases;
import org.mule.modules.github.automation.testcases.GetUserByLoginNameTestCases;
import org.mule.modules.github.automation.testcases.IsFollowingTestCases;
import org.mule.modules.github.automation.testcases.RemoveEmailsTestCases;
import org.mule.modules.github.automation.testcases.UpdateCurrentUserTestCases;
import org.mule.modules.github.automation.testcases.UserTests;

@RunWith(Categories.class)
@Categories.IncludeCategory(UserTests.class)
@Suite.SuiteClasses({GetCurrentUserTestCases.class, GetFollowersTestCases.class, GetFollowingTestCases.class,
        GetUserByLoginNameTestCases.class, IsFollowingTestCases.class, UpdateCurrentUserTestCases.class, FollowTestCases.class,
        CreateKeyTestCases.class, GetKeyTestCases.class, DeleteKeyTestCases.class,
        GetEmailsTestCases.class, AddEmailsTestCases.class, RemoveEmailsTestCases.class
})
public class UserTestSuite
{
}
