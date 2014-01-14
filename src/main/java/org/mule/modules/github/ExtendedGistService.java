/**
 * Mule GitHub Cloud Connector
 *
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 * <p/>
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.modules.github;

import java.io.IOException;

import org.eclipse.egit.github.core.Comment;
import org.eclipse.egit.github.core.Gist;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.client.GitHubRequest;
import org.eclipse.egit.github.core.service.GistService;

import static org.eclipse.egit.github.core.client.IGitHubConstants.SEGMENT_COMMENTS;
import static org.eclipse.egit.github.core.client.IGitHubConstants.SEGMENT_FORKS;
import static org.eclipse.egit.github.core.client.IGitHubConstants.SEGMENT_GISTS;

/**
 * 4 methods in org.eclipse.egit.github.core.service.GistService 2.1.5 are broken and are re-implemented here
 */

public class ExtendedGistService  extends GistService
{
    /**
     * Default constructor
     */
    public ExtendedGistService()
    {
    }

    /**
     * @param client an instance of GitHubClient
     */
    public ExtendedGistService(GitHubClient client)
    {
        super(client);
    }

    /**
     * Get Gist comment with specified Id
     * <p/>
     * @param gistId Gist Id
     * @param commentId Comment Id
     * @return Gist comment
     * @throws IOException in case of connectivity issues
     */
    public Comment getComment(String gistId, long commentId) throws IOException
    {
        checkGistId(gistId);
        StringBuilder uri = new StringBuilder(SEGMENT_GISTS);
        uri.append('/').append(gistId);
        uri.append(SEGMENT_COMMENTS);
        uri.append('/').append(commentId);
        GitHubRequest request = createRequest();
        request.setUri(uri);
        request.setType(Comment.class);
        return (Comment) client.get(request).getBody();
    }

    /**
     * Edit Gist comment
     * <p/>
     * @param gistId Gist Id
     * @param comment the Comment
     * @return updated Gist comment
     * @throws IOException in case of connectivity issues
     */
    public Comment editComment(String gistId, Comment comment) throws IOException
    {
        if (comment == null)
        {
            throw new IllegalArgumentException("Comment cannot be null");
        }
        checkGistId(gistId);

        StringBuilder uri = new StringBuilder(SEGMENT_GISTS);
        uri.append('/').append(gistId);
        uri.append(SEGMENT_COMMENTS);
        uri.append('/').append(comment.getId());
        return client.post(uri.toString(), comment, Comment.class);
    }

    /**
     * Delete Gist comment
     * <p/>
     * @param gistId Gist Id
     * @param commentId comment Id
     * @throws IOException in case of connectivity issues
     */
    public void deleteComment(String gistId, long commentId) throws IOException
    {
        checkGistId(gistId);
        StringBuilder uri = new StringBuilder(SEGMENT_GISTS);
        uri.append('/').append(gistId);
        uri.append(SEGMENT_COMMENTS);
        uri.append('/').append(commentId);
        client.delete(uri.toString());
    }

    /**
     * Fork a Gist
     * @param gistId Gist Id
     * @return forked Gist
     * @throws IOException in case of connectivity issues
     */
    @Override
    public Gist forkGist(String gistId) throws IOException
    {
        checkGistId(gistId);
        StringBuilder uri = new StringBuilder(SEGMENT_GISTS);
        uri.append('/').append(gistId);
        uri.append(SEGMENT_FORKS);
        return client.post(uri.toString(), null, Gist.class);
    }
}
