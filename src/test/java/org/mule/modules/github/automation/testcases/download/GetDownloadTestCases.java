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

import org.eclipse.egit.github.core.Download;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mule.modules.github.automation.testcases.GutHubTestParent;
import org.mule.modules.github.automation.testcases.SmokeTests;
import org.mule.modules.tests.ConnectorTestUtils;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class GetDownloadTestCases extends GutHubTestParent
{
    @Before
    public void setUp() throws Exception
    {
        initializeTestRunMessage("downloads");
    }

    /**
     *  Part of downloads API does not work anymore on GitHub. GitHub suggests using releases API instead (see below)
     *  But releases API is not yet stable and is not implemented in Mylyn 2.1.5
     *
     *  POST /repos/mule-tester/github-connector/downloads HTTP/1.1
     *  {
     *  "name": "new_file.jpg",
     *  "size": 114034,
     *  "description": "Latest release",
     *  "content_type": "text/plain"
     *  }
     *
     *  HTTP/1.1 406 Not Acceptable
     *  {
     *  "message": "Cannot create downloads.  Use releases instead.",
     *  "documentation_url": "http://developer.github.com/v3"
     *  }
     */
    @Test
    @Category({SmokeTests.class})
    public void getDownload()
    {
        try
        {
            Download download = runFlowAndGetPayload("getDownload");
            assertNotNull(download);
        } catch (Exception e)
        {
            fail(ConnectorTestUtils.getStackTrace(e));
        }
    }

}
