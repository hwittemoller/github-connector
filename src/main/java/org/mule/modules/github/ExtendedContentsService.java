package org.mule.modules.github;

import org.apache.commons.lang.StringUtils;
import org.eclipse.egit.github.core.IRepositoryIdProvider;
import org.eclipse.egit.github.core.RepositoryContents;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.ContentsService;
import org.eclipse.egit.github.core.util.EncodingUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.eclipse.egit.github.core.client.IGitHubConstants.SEGMENT_CONTENTS;
import static org.eclipse.egit.github.core.client.IGitHubConstants.SEGMENT_REPOS;

/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 * <p/>
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

public class ExtendedContentsService extends ContentsService {

    public ExtendedContentsService() {
        super();
    }

    public ExtendedContentsService(final GitHubClient client) {
        super(client);
    }

    /**
     * Commit file to GitHub
     * Github contents API ( http://developer.github.com/v3/repos/contents/ ) was released on May 2013 and
     * MyLyn core 2.1.5 does not implement it yet. Should be added in next Mylyn release.
     * The method is not exposed as a processor and used only for functional testing.
     * @param repository repository
     * @param contents contents to commit
     * @param commitMessage commit message
     * @param ref branch name
     * @throws IOException
     */
    public void putContents(IRepositoryIdProvider repository, RepositoryContents contents, String commitMessage, String ref)
            throws IOException {
        String id = getId(repository);

        StringBuilder uri = new StringBuilder(SEGMENT_REPOS);
        uri.append('/').append(id);
        uri.append(SEGMENT_CONTENTS);
        if (StringUtils.isNotBlank(contents.getPath())) {
            if (!contents.getPath().startsWith("/"))
                uri.append('/');
            uri.append(contents.getPath());
        }

        Map<String, String> params = new HashMap<String, String>(4, 1);
        params.put("message", commitMessage);
        params.put("content", EncodingUtils.toBase64(contents.getContent()));
        params.put("sha", contents.getSha());
        if (ref != null)
            params.put("branch", ref);


        client.put(uri.toString(), params, null); //TODO: ignoring result for now because returning type is not defined in mylyn
    }

}
