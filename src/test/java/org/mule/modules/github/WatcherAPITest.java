package org.mule.modules.github;

import java.util.List;

import org.eclipse.egit.github.core.service.WatcherService;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 * <p/>
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 * <p/>
 * Test methods for 'watch' and 'unwatch' implement logic for 'star' and 'unstar'
 * methods, according to the information noticed in the link below:
 * http://developer.github.com/changes/2012-9-5-watcher-api/
 */

@SuppressWarnings("unchecked")
public class WatcherAPITest extends BaseAPITest
{

    @Test
    public void testWatcherAPI() throws Exception
    {

        List<WatcherService> watchers = runMuleFlow("getWatchers", List.class);
        assertNotNull(watchers);
        assertTrue(watchers.size() > 0);

        List<WatcherService> watched = runMuleFlow("getWatched", List.class);
        assertNotNull(watched);

        runMuleFlow("watch", null);
        Boolean isWatching = runMuleFlow("isWatching", Boolean.class);
        assertTrue(isWatching);

        runMuleFlow("unwatch", null);
        isWatching = runMuleFlow("isWatching", Boolean.class);
        assertFalse(isWatching);

    }
}
