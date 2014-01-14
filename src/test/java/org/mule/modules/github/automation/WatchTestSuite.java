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
import org.mule.modules.github.automation.testcases.GetWatchedTestCases;
import org.mule.modules.github.automation.testcases.GetWatchersTestCases;
import org.mule.modules.github.automation.testcases.IsWatchingTestCases;
import org.mule.modules.github.automation.testcases.UnwatchTestCases;
import org.mule.modules.github.automation.testcases.WatchTestCases;
import org.mule.modules.github.automation.testcases.WatcherTests;

@RunWith(Categories.class)
@Categories.IncludeCategory(WatcherTests.class)
@Suite.SuiteClasses({GetWatchedTestCases.class, GetWatchersTestCases.class, IsWatchingTestCases.class, WatchTestCases.class, UnwatchTestCases.class})
public class WatchTestSuite
{
}
