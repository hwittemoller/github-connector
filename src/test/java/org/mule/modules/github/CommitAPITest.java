package org.mule.modules.github;

import org.eclipse.egit.github.core.CommitComment;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

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

public class CommitAPITest extends BaseAPITest {

    @Test
    public void testCommitAPI() throws Exception {
        Repository repository = runMuleFlow("forkRepository", Repository.class);
        assertNotNull(repository);

        List<RepositoryCommit> commits = runMuleFlow("getCommits", List.class);
        assertTrue(commits.size() > 0);

        String filePath = "/README.md";

        commits = runMuleFlow("getCommitsBySha", List.class);
        assertTrue(commits.size() > 0);
        RepositoryCommit aCommit = commits.get(0);
        String commitId = aCommit.getSha();

        Map<String, String> commitIdParameter = Collections.singletonMap("sha", commitId);
        RepositoryCommit c = runMuleFlow("getCommit", RepositoryCommit.class, commitIdParameter);
        assertNotNull(c);

        CommitComment comment = runMuleFlow("addComment", CommitComment.class, commitIdParameter);
        assertNotNull(comment);
        assertEquals("commit comment body", comment.getBody());
        long commentId = comment.getId();
        Map<String, Long> commentIdParameter = Collections.singletonMap("commentId", commentId);

        comment = runMuleFlow("editCommitComment", CommitComment.class, commentIdParameter);
        assertNotNull(comment);
        assertEquals("updated comment body", comment.getBody());

        comment = runMuleFlow("getComment", CommitComment.class, commentIdParameter);
        assertNotNull(comment);
        assertEquals("updated comment body", comment.getBody());

        List<CommitComment> comments = runMuleFlow("getCommmitComments", List.class, commitIdParameter);
        assertNotNull(comments);
        assertTrue(comments.size() > 0);

        boolean found = false;
        for (CommitComment com : comments) {
            if (com.getId() == commentId) {
                found = true;
                break;
            }
        }

        assertTrue(found);

        runMuleFlow("deleteCommitComment", null, commentIdParameter);

        github.deleteRepository(USER, REPO); //cleanup

    }

}
