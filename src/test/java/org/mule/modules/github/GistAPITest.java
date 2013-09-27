package org.mule.modules.github;

import org.eclipse.egit.github.core.Comment;
import org.eclipse.egit.github.core.Gist;
import org.junit.Test;

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
public class GistAPITest extends BaseAPITest {

    @Test
    public void testGistAPI()
            throws Exception {
        Gist gist = runMuleFlow("createGist", Gist.class, "file content");
        System.out.println(gist.getUrl());
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
        //the test should fail

        long commentId = comment.getId();
        comment = github.editGistComment(commentId, "updated test comment");
        assertNotNull(comment);

        comment = github.getGistComment(commentId);
        assertEquals("updated test comment", comment.getBody());

        github.deleteGistComment(commentId);

        Gist forked = github.forkGist(gistId);

        runMuleFlow("deleteGist", null, gistIdParameter);
    }
}
