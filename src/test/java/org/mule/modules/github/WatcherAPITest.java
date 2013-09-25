package org.mule.modules.github;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.eclipse.egit.github.core.service.WatcherService;
import org.junit.Test;

/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 * 
 * Test methods for 'watch' and 'unwatch' implement logic for 'star' and 'unstar'
 * methods, according to the information noticed in the link below:
 * http://developer.github.com/changes/2012-9-5-watcher-api/ 
 */

public class WatcherAPITest extends BaseAPITest {
		
	public WatcherAPITest() throws IOException {
		github.setServiceFactory(new ServiceFactory(USER, PASS, SCOPE));
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	@Test
	public void testWatcherAPI() throws Exception	       
	{
		List<WatcherService> watchers = runMuleFlow("getWatchers", List.class, null);
		assertNotNull(watchers);
		
		System.out.println("getWatchers size: " + watchers.size());
		
		List<WatcherService> watched = runMuleFlow("getWatched", List.class, null);
		assertNotNull(watched);
		System.out.println("getWatched size: " + watched.size());		
				
		WatcherService watch = runMuleFlow("watch", null, null);
		Boolean isWatching = runMuleFlow("isWatching", Boolean.class, null);
		assertTrue(isWatching);
		
		WatcherService unwatch = runMuleFlow("unwatch", null, null);
		isWatching = runMuleFlow("isWatching", Boolean.class, null);
		assertFalse(isWatching);		
	}	
}
