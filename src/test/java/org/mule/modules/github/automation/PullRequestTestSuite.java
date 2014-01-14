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
import org.mule.modules.github.automation.testcases.CreatePullRequestCommentTestCases;
import org.mule.modules.github.automation.testcases.CreatePullRequestTestCases;
import org.mule.modules.github.automation.testcases.GetPullRequestCommentTestCases;
import org.mule.modules.github.automation.testcases.GetPullRequestTestCases;
import org.mule.modules.github.automation.testcases.PullRequestTests;

@RunWith(Categories.class)
@Categories.IncludeCategory(PullRequestTests.class)
@Suite.SuiteClasses({CreatePullRequestCommentTestCases.class,CreatePullRequestTestCases.class,
        GetPullRequestCommentTestCases.class,GetPullRequestTestCases.class})
public class PullRequestTestSuite
{
}
