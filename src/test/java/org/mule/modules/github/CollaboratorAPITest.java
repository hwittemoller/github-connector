package org.mule.modules.github;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.User;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 * <p/>
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */
public class CollaboratorAPITest extends BaseAPITest {

    @Test
    public void testCollaboratorAPI()
            throws Exception {

        Repository repository = runMuleFlow("forkRepository", Repository.class);
        assertNotNull(repository);

        runMuleFlow("addCollaborator", null);

        boolean isCollaborator = runMuleFlow("isCollaborator", Boolean.class);
        assertTrue(isCollaborator);

        List<User> collaborators = runMuleFlow("getCollaborators", List.class);
        assertNotNull(collaborators);
        assertTrue(collaborators.size() > 0);

        runMuleFlow("removeCollaborator", null);

        github.deleteRepository(USER, REPO); //cleanup
    }
}
