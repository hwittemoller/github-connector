package org.mule.modules.github;

import org.eclipse.egit.github.core.Download;
import org.eclipse.egit.github.core.DownloadResource;
import org.eclipse.egit.github.core.Repository;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 * <p/>
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */


public class DownloadAPITest extends BaseAPITest {

    @Test
    public void testDownloadAPI() throws Exception {

/*
        Downloads API does not work anymore on GitHub. GitHub suggests using releases API instead (see below)
        But releases API does not implemented in Mylyn 2.1.5

        POST /repos/mule-tester/github-connector/downloads HTTP/1.1
        {
          "name": "new_file.jpg",
          "size": 114034,
          "description": "Latest release",
          "content_type": "text/plain"
        }

        HTTP/1.1 406 Not Acceptable
        {
          "message": "Cannot create downloads.  Use releases instead.",
          "documentation_url": "http://developer.github.com/v3"
        }

*/



        Repository repository = forkTestRepository();
        assertNotNull(repository);

        String string = "abcdef";
        InputStream is = new ByteArrayInputStream(string.getBytes());
        DownloadResource resource = github.createResource(USER, REPO, "test-resource", string.getBytes().length, "test description", "text/plain");
        github.uploadResource(resource, is);

        List<Download> downloads = github.listDownloadsForRepository(USER, REPO);
        assertTrue(downloads.size() > 0);

        Download download = github.getDownload(USER, REPO, resource.getId());
        assertEquals("test-resource", download.getName());

        github.deleteDownload(USER, REPO, download.getId());

        deleteTestRepository();

    }


}
