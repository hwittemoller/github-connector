package org.mule.modules.github;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.egit.github.core.Comment;
import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Repository;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
public class IssueAPITest extends BaseAPITest
{

    @Test
    public void testIssueAPI() throws Exception
    {
        Repository repository = github.createRepository(REPO, "Test Repo", false, true, true, true);
        assertNotNull(repository);

        Issue issue = runMuleFlow("createIssue", Issue.class);
        assertNotNull(issue);
        assertEquals("Issue Title", issue.getTitle());
        assertEquals("Issue Body", issue.getBody());
        int issueNumber = issue.getNumber();

        List<Issue> issues = runMuleFlow("getIssues", List.class);
        boolean found = false;
        for (Issue i : issues)
        {
            if (i.getNumber() == issueNumber)
            {
                found = true;
                break;
            }
        }

        assertTrue(found);

        issues = runMuleFlow("getIssuesSinceNumber", List.class);
        assertTrue(issues.size() > 0);

        issues = runMuleFlow("getIssuesCretedAfter", List.class);
        assertTrue(issues.size() > 0);

        Map<String, Integer> issueIdParameter = Collections.singletonMap("issueId", issueNumber);
        issue = runMuleFlow("getIssue", Issue.class, issueIdParameter);
        assertNotNull(issue);
        assertEquals("Issue Title", issue.getTitle());
        assertEquals("Issue Body", issue.getBody());

        Comment comment = runMuleFlow("createComment", Comment.class, issueIdParameter);
        assertNotNull(comment);
        assertEquals("Test comment", comment.getBody());

        long commentId = comment.getId();

        List<Comment> comments = runMuleFlow("getComments", List.class, issueIdParameter);
        found = false;
        for (Comment c : comments)
        {
            if (c.getId() == commentId)
            {
                found = true;
                break;
            }
        }
        assertTrue(found);

        Map<String, Long> commentIdParameter = Collections.singletonMap("commentId", commentId);

        comment = runMuleFlow("editComment", Comment.class, commentIdParameter);
        assertEquals("Updated comment body", comment.getBody());

        runMuleFlow("deleteComment", null, commentIdParameter);

        comments = runMuleFlow("getComments", List.class, issueIdParameter);
        found = false;
        for (Comment c : comments)
        {
            if (c.getId() == commentId)
            {
                found = true;
                break;
            }
        }
        assertFalse(found);

        issue = runMuleFlow("closeIssue", Issue.class, issueIdParameter);
        assertEquals("closed", issue.getState());

        deleteTestRepository();
    }

}