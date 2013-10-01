package org.mule.modules.github;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.egit.github.core.Comment;
import org.eclipse.egit.github.core.Gist;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 * <p/>
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

@SuppressWarnings("unchecked")
public class GistAPITest extends BaseAPITest
{

    @Test
    public void testGistAPI() throws Exception
    {
        Gist gist = runMuleFlow("createGist", Gist.class, "file content");
        assertNotNull(gist);
        assertEquals("description for gist", gist.getDescription());

        String gistId = gist.getId();
        Map<String, String> gistIdParameter = Collections.singletonMap("id", gistId);
        gist = runMuleFlow("getGist", Gist.class, gistIdParameter);
        assertNotNull(gist);

        List<Gist> gists = runMuleFlow("getGists", List.class);
        assertTrue(gists.size() > 0);

        runMuleFlow("starGist", null, gistIdParameter);

        gists = runMuleFlow("getStarredGist", List.class);
        assertTrue(gists.size() > 0);

        runMuleFlow("unstarGist", null, gistIdParameter);

        Comment comment = runMuleFlow("createGistComment", Comment.class, gistIdParameter);
        assertNotNull(comment);

        List<Comment> comments = runMuleFlow("getGistComments", List.class, gistIdParameter);
        assertTrue(comments.size() > 0);

        //following 4 methods are broken in mylyn edit.github.core 2.1.5.
        //and re-implemented in ExtendedGistService

        long commentId = comment.getId();
        Map<String, Object> params = new HashMap<String, Object>(2,1);
        params.put("id", gistId);
        params.put("commentId", commentId);

        comment =  runMuleFlow("editGistComment", Comment.class, params);
        assertNotNull(comment);
        assertEquals("updated gist comment", comment.getBody());

        comment = runMuleFlow("getGistComment", Comment.class, params);
        assertNotNull(comment);
        assertEquals("updated gist comment", comment.getBody());

        runMuleFlow("deleteGistComment", null, params);

        Gist forked = runMuleFlow("forkGist", Gist.class);
        //broken till here
        github.deleteGist(forked.getId());

        runMuleFlow("deleteGist", null, gistIdParameter);
    }
}
