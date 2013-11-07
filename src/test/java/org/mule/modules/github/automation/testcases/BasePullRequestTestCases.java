/**
 *
 * Mule GitHub Cloud Connector
 *
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 * <p/>
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.modules.github.automation.testcases;

import java.util.List;

import org.eclipse.egit.github.core.Reference;
import org.eclipse.egit.github.core.RepositoryContents;

import static org.junit.Assert.assertNotNull;

public class BasePullRequestTestCases extends GitHubTestParent
{

    public void prepareBranch() throws Exception
    {
        //fork test repository
        createTestRepository(true);
        initializeTestRunMessage("reference");

        //find master
        List<Reference> refs = runFlowAndGetPayload("getReferences");
        assertNotNull(refs);
        Reference master = null;
        for (Reference ref : refs)
        {
            if ("refs/heads/master".equals(ref.getRef()))
            {
                master = ref;
            }
        }
        assertNotNull(master);

        //create branch
        upsertOnTestRunMessage("sha", master.getObject().getSha());
        Reference branch = runFlowAndGetPayload("createReference");

        //get content of Readme
        List<RepositoryContents> retrievedContents = runFlowAndGetPayload("getReadme");
        RepositoryContents file = retrievedContents.get(0);
        upsertOnTestRunMessage("fileSha", file.getSha());

        //update Readme in branch
        runFlowAndGetPayload("updateReadme");

        initializeTestRunMessage("pullRequest");
        upsertOnTestRunMessage("head", branch.getRef());
        upsertOnTestRunMessage("base", master.getRef());
    }

}
