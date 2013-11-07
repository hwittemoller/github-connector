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
import org.mule.modules.github.automation.testcases.CreateDownloadTestCases;
import org.mule.modules.github.automation.testcases.DownloadTests;
import org.mule.modules.github.automation.testcases.GetDownloadTestCases;
import org.mule.modules.github.automation.testcases.ListDownloadsTestCases;

@RunWith(Categories.class)
@Categories.IncludeCategory(DownloadTests.class)
@Suite.SuiteClasses({CreateDownloadTestCases.class, GetDownloadTestCases.class, ListDownloadsTestCases.class})
public class DownloadTestSuite
{
}
