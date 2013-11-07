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
import org.mule.modules.github.automation.testcases.CreateDeployKeyTestCases;
import org.mule.modules.github.automation.testcases.DeleteDeployKeyTestCases;
import org.mule.modules.github.automation.testcases.DeployKeyTests;
import org.mule.modules.github.automation.testcases.EditDeployKeyTestCases;
import org.mule.modules.github.automation.testcases.GetDeployKeyTestCases;

@RunWith(Categories.class)
@Categories.IncludeCategory(DeployKeyTests.class)
@Suite.SuiteClasses({CreateDeployKeyTestCases.class, DeleteDeployKeyTestCases.class, EditDeployKeyTestCases.class, GetDeployKeyTestCases.class})
public class DeployKeyTestSuite
{
}
