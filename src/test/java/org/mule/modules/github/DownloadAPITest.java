package org.mule.modules.github;

import org.eclipse.egit.github.core.Download;
import org.eclipse.egit.github.core.DownloadResource;
import org.eclipse.egit.github.core.Repository;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 * <p/>
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */


//Downloads API does not work anymore on GitHub. GitHub suggests use releases instead (see below)
// But releases API does not implemented in Mylyn 2.1.5

/*
POST /repos/mule-tester/github-connector/downloads HTTP/1.1
X-HostCommonName:
api.github.com
Host:
api.github.com
Content-Length:
118
X-Target-URI:
https://api.github.com
Content-Type:
text/plain; charset=UTF-8
Connection:
Keep-Alive

{
  "name": "new_file.jpg",
  "size": 114034,
  "description": "Latest release",
  "content_type": "text/plain"
}


HTTP/1.1 406 Not Acceptable
Access-Control-Expose-Headers:
ETag, Link, X-RateLimit-Limit, X-RateLimit-Remaining, X-RateLimit-Reset, X-OAuth-Scopes, X-Accepted-OAuth-Scopes
X-GitHub-Media-Type:
github.beta; format=json
X-RateLimit-Limit:
60
X-RateLimit-Remaining:
59
Content-Length:
114
X-RateLimit-Reset:
1380205414
Access-Control-Allow-Credentials:
true
Server:
GitHub.com
X-GitHub-Request-Id:
6B174C87:32F8:4398E04:52443556
X-Content-Type-Options:
nosniff
Status:
406 Not Acceptable
Date:
Thu, 26 Sep 2013 13:23:34 GMT
Access-Control-Allow-Origin:
*
Content-Type:
application/json; charset=utf-8

{
  "message": "Cannot create downloads.  Use releases instead.",
  "documentation_url": "http://developer.github.com/v3"
}





*/

public class DownloadAPITest extends BaseAPITest {


/*
    public  void testDownloadAPI() throws Exception {
        Repository repository = runMuleFlow("forkRepository", Repository.class);
        assertNotNull(repository);

        String string = "abcdef";
        InputStream is = new ByteArrayInputStream(string.getBytes());
        DownloadResource resource = github.createResource(USER, REPO, "test-resource", string.getBytes().length, "test description", "text/plain");
        github.uploadResource(resource, is);
        System.err.println(resource.getId());

        List<Download> downloads = github.listDownloadsForRepository(USER, REPO);
        assertTrue(downloads.size()>0);

        Download download = github.getDownload(USER, REPO, resource.getId());
        assertEquals("test-resource", download.getName());

        //github.deleteDownload(USER, REPO, download.getId());
        System.err.println(download.getId());

        github.deleteRepository(USER, REPO); //cleanup
    }

    @Test
    public void test() throws Exception{
        String string = "abcdef";
        InputStream is = new ByteArrayInputStream(string.getBytes());
        DownloadResource resource = github.createResource(USER, REPO, "test-resource", string.getBytes().length, "test description", "text/plain");
        github.uploadResource(resource, is);
        System.err.println(resource.getId());
    }
*/

}
