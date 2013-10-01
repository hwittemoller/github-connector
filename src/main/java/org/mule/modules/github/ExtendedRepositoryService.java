/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */
package org.mule.modules.github;

import java.io.IOException;

import org.eclipse.egit.github.core.IRepositoryIdProvider;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;
import static org.eclipse.egit.github.core.client.IGitHubConstants.SEGMENT_REPOS;

/**
 * Temporary extension of {@link RepositoryService}, in order to support repository deletion
 *
 * @author vgulyakin
 */
public class ExtendedRepositoryService extends RepositoryService
{

    /**
     * Default constructor
     */
    public ExtendedRepositoryService()
    {
        super();
    }

    /**
     * @param client an instance of GitHubClient
     */
    public ExtendedRepositoryService(final GitHubClient client)
    {
        super(client);
    }

    /**
     * Delete a repository
     * This method is not a part of org.eclipse.mylin.github implementation as of version 2.1.5
     * and was added solely for functional connector testing (cleanup data after tests).
     *
     * @param repository the repository to delete
     * @throws IOException in case of connectivity issues or if repository does not exist
     */
    public void deleteRepository(IRepositoryIdProvider repository) throws IOException
    {
        String id = getId(repository);
        StringBuilder uri = new StringBuilder(SEGMENT_REPOS);
        uri.append('/').append(id);
        client.delete(uri.toString());
    }
}
