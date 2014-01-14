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
import org.mule.modules.github.automation.testcases.CreateLabelTestCases;
import org.mule.modules.github.automation.testcases.DeleteLabelTestCases;
import org.mule.modules.github.automation.testcases.GetLabelTestCases;
import org.mule.modules.github.automation.testcases.GetLabelsTestCases;
import org.mule.modules.github.automation.testcases.LabelTests;

@RunWith(Categories.class)
@Categories.IncludeCategory(LabelTests.class)
@Suite.SuiteClasses({CreateLabelTestCases.class, DeleteLabelTestCases.class, GetLabelTestCases.class, GetLabelsTestCases.class })
public class LabelTestSuite
{
}
