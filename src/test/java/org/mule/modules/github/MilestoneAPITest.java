package org.mule.modules.github;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.egit.github.core.Milestone;
import org.eclipse.egit.github.core.Repository;
import org.junit.Test;

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

@SuppressWarnings("unchecked")
public class MilestoneAPITest extends BaseAPITest
{

    @Test
    public void testMilestoneAPI() throws Exception
    {
        Repository repository = createTestRepository(false);
        assertNotNull(repository);

        Milestone milestone = runMuleFlow("createMilestone", Milestone.class);
        assertNotNull(milestone);

        String number = String.valueOf(milestone.getNumber());
        List<Milestone> milestones = runMuleFlow("getMilestones", List.class);
        assertNotNull(milestones);
        assertTrue(milestones.size() > 0);

        Map<String, String> milestoneNumberParameter = Collections.singletonMap("number", number);
        milestone = runMuleFlow("getMilestone", Milestone.class, milestoneNumberParameter);
        assertEquals("1.0", milestone.getTitle());
        assertEquals("Milestone 1.0 description", milestone.getDescription());
        assertEquals("open", milestone.getState());

        runMuleFlow("deleteMilestone", null, milestoneNumberParameter);

        deleteTestRepository();
    }
}
