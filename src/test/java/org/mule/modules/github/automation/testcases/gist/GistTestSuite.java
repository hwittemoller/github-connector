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

package org.mule.modules.github.automation.testcases.gist;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.mule.modules.github.automation.testcases.download.CreateDownloadTestCases;
import org.mule.modules.github.automation.testcases.download.GetDownloadTestCases;
import org.mule.modules.github.automation.testcases.download.ListDownloadsTestCases;

@RunWith(Suite.class)
@Suite.SuiteClasses({CreateCommentTestCases.class,CreateGistTestCases.class,DeleteGistTestCases.class,ForkGistTestCases.class,
        GetCommentTestCases.class,GetGistTestCases.class,StarGistTestCases.class})
public class GistTestSuite
{
}
