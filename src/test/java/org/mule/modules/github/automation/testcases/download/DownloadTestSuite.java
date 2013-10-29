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

package org.mule.modules.github.automation.testcases.download;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.mule.modules.github.automation.testcases.deploykey.CreateDeployKeyTestCases;
import org.mule.modules.github.automation.testcases.deploykey.DeleteDeployKeyTestCases;
import org.mule.modules.github.automation.testcases.deploykey.EditDeployKeyTestCases;
import org.mule.modules.github.automation.testcases.deploykey.GetDeployKeyTestCases;

@RunWith(Suite.class)
@Suite.SuiteClasses({CreateDownloadTestCases.class, GetDownloadTestCases.class, ListDownloadsTestCases.class})
public class DownloadTestSuite
{
}
