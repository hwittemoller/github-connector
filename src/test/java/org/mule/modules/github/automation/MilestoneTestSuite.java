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
import org.mule.modules.github.automation.testcases.CreateMilestoneTestCases;
import org.mule.modules.github.automation.testcases.DeleteMilestoneTestCases;
import org.mule.modules.github.automation.testcases.GetMilestoneTestCases;
import org.mule.modules.github.automation.testcases.GetMilestonesTestCases;
import org.mule.modules.github.automation.testcases.MilestoneTests;

@RunWith(Categories.class)
@Categories.IncludeCategory(MilestoneTests.class)
@Suite.SuiteClasses({CreateMilestoneTestCases.class, DeleteMilestoneTestCases.class, GetMilestoneTestCases.class, GetMilestonesTestCases.class})
public class MilestoneTestSuite
{
}
