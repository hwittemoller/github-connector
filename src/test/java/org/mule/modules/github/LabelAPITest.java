package org.mule.modules.github;

import org.eclipse.egit.github.core.Label;
import org.eclipse.egit.github.core.Repository;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 * <p/>
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

public class LabelAPITest extends BaseAPITest {

    @Test
    public void testLabelAPI() throws Exception {
        Repository repository = github.createRepository(REPO, "description", false, true, true, true);
        assertNotNull(repository);

        Label label = runMuleFlow("createLabel", Label.class);
        assertNotNull(label);
        assertEquals("some-label", label.getName());
        assertEquals("f29513", label.getColor());

        List<Label> labels = runMuleFlow("getLabels", List.class);
        assertTrue(labels.size() > 0);

        label = runMuleFlow("getLabel", Label.class);
        assertTrue(labels.contains(label));

        runMuleFlow("deleteLabel", null);

        deleteTestRepository();
    }
}
