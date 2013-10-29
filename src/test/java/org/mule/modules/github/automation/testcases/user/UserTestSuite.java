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

package org.mule.modules.github.automation.testcases.user;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.mule.modules.github.automation.testcases.label.CreateLabelTestCases;
import org.mule.modules.github.automation.testcases.label.DeleteLabelTestCases;
import org.mule.modules.github.automation.testcases.label.GetLabelTestCases;

@RunWith(Suite.class)
@Suite.SuiteClasses({GetCurrentUserTestCases.class})
public class UserTestSuite
{
}
