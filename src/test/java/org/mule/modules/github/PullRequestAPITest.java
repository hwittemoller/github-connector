package org.mule.modules.github;

import org.eclipse.egit.github.core.*;
import org.eclipse.egit.github.core.client.PageIterator;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
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
public class PullRequestAPITest extends BaseAPITest {

    @Test
    public void testPullRequestAPI()
            throws Exception
    {

        Repository repository = forkTestRepository();
        assertNotNull(repository);

        //create branch
        List<Reference> refs = github.getReferences(USER, REPO);
        assertNotNull(refs);
        Reference master = null;
        for (Reference ref: refs)
        {
            if ("refs/heads/master".equals(ref.getRef()))
                master = ref;
        }
        assertNotNull(master);

        Reference branch = github.createReference(USER, REPO, master.getObject().getSha(), "refs/heads/test-branch");

        //commit file to the branch
        List<RepositoryContents> retrievedContents = runMuleFlow("getReadme", List.class);
        assertNotNull(retrievedContents);
        assertTrue(retrievedContents.size()==1);

        RepositoryContents file = retrievedContents.get(0);

        runMuleFlow("updateReadme",null, Collections.singletonMap("sha",file.getSha()));

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("head", branch.getRef());
        params.put("base", master.getRef());
        PullRequest pullRequest = runMuleFlow("createPullRequest", PullRequest.class, params);
        assertNotNull(pullRequest);
        int pullRequestNumber = pullRequest.getNumber();
        assertTrue(pullRequestNumber > 0);
        assertEquals("pull request title", pullRequest.getTitle());
        assertEquals("pull request body", pullRequest.getBody());

        List<PullRequest> pullRequests = runMuleFlow("getPullRequests", List.class);
        assertNotNull(pullRequests);
        assertTrue(pullRequests.size() > 0);

        Map<String, Integer> pullRequestIdParameter = Collections.singletonMap("id",pullRequestNumber);
        pullRequest = runMuleFlow("getPullRequest", PullRequest.class, pullRequestIdParameter);
        assertNotNull(pullRequest);

        PageIterator<PullRequest> pagedPullRequest = runMuleFlow("pagePullRequests", PageIterator.class);
        assertNotNull(pagedPullRequest);
        assertTrue(pagedPullRequest.hasNext());

        pullRequest = runMuleFlow("editPullRequest", PullRequest.class, pullRequestIdParameter);
        assertEquals("updated test pull request title", pullRequest.getTitle());

        List<RepositoryCommit> commits = runMuleFlow("getPullRequestCommits", List.class, pullRequestIdParameter);
        assertNotNull(commits);
        assertTrue(commits.size()>0);
        RepositoryCommit aCommit = commits.get(0);

        List<CommitFile> files = runMuleFlow("getPullRequestFiles", List.class, pullRequestIdParameter);
        assertNotNull(files);
        assertTrue(files.size()>0);

        boolean isMerged = runMuleFlow("isPullRequestMerged", Boolean.class, pullRequestIdParameter);
        assertFalse(isMerged);

        params = new HashMap<String, Object>();
        params.put("pullRequestId", pullRequestNumber);
        params.put("commitId", aCommit.getSha());
        CommitComment comment = runMuleFlow("createPullRequestComment", CommitComment.class, params );
        assertNotNull(comment);
        assertEquals("test comment body", comment.getBody());
        assertEquals(10, comment.getPosition());
        assertEquals("README.md", comment.getPath());

        long commentId = comment.getId();
        Map<String, Long> commentIdParameter = Collections.singletonMap("commentId",commentId);

        comment = runMuleFlow("editPullRequestComment", CommitComment.class, commentIdParameter );
        assertNotNull(comment);
        assertEquals("updated test comment body", comment.getBody());

        params = new HashMap<String, Object>();
        params.put("pullRequestId", pullRequestNumber);
        params.put("commentId", (int)commentId);
        CommitComment reply = runMuleFlow("replyToPullRequestComment", CommitComment.class, params);
        assertNotNull(reply);
        assertEquals("test reply", reply.getBody());

        comment = runMuleFlow("getPullRequestComment", CommitComment.class, commentIdParameter);
        assertNotNull(comment);
        assertEquals("updated test comment body", comment.getBody());

        List<CommitComment> comments = runMuleFlow("getPullRequestComments", List.class, pullRequestIdParameter);
        boolean found = false;
        for (CommitComment c: comments )
        {
            if (c.getId()==commentId)
                found = true;
        }
        assertTrue(found);

        PageIterator<CommitComment> pagedComments = runMuleFlow("pagePullRequestComments", PageIterator.class, pullRequestIdParameter);
        assertTrue( pagedComments.hasNext() );

        runMuleFlow("deletePullRequestComment", null, commentIdParameter);

        MergeStatus mergeStatus = runMuleFlow("mergePullRequest", MergeStatus.class, pullRequestIdParameter);
        assertTrue (mergeStatus.isMerged());

        github.deleteRepository(USER, REPO); //cleanup

   }

}
