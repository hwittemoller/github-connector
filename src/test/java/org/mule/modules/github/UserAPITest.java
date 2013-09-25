package org.mule.modules.github;

/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

import org.eclipse.egit.github.core.Key;
import org.eclipse.egit.github.core.User;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class UserAPITest extends BaseAPITest
{	
    @Test
    public void testUserAPI()
            throws Exception
    {
        github.setServiceFactory(new ServiceFactory(USER, PASS, SCOPE));

        //basic
        User user = runMuleFlow("getCurrentUser", User.class);
        assertNotNull(user);
        assertEquals(USER, user.getLogin());

        user = runMuleFlow("getUserByLoginName", User.class);
        assertNotNull(user);
        assertEquals("orionixNuclear", user.getLogin());

        user = runMuleFlow("updateCurrentUser", User.class); //set company="MuleSoft" location="Buenos Aires" blog="blogs.mulesoft.org"
        assertEquals("blogs.mulesoft.org", user.getBlog());
        assertEquals("Buenos Aires", user.getLocation());

        user = runMuleFlow("cleanupCurrentUser", User.class); //set company="-" location="-" blog="-"
        assertEquals("-", user.getBlog());
        assertEquals("-", user.getLocation());

        //followers, follow

        List<User> followers = runMuleFlow("getFollowers", List.class);
        assertNotNull(followers);

        List<User> following = runMuleFlow("getFollowing", List.class);
        assertNotNull(following);

        boolean isFollow = runMuleFlow("isFollowing", Boolean.class);
        assertFalse(isFollow);

        runMuleFlow("follow", null);
        isFollow = github.isFollowing("federecio");
        assertTrue(isFollow);

        runMuleFlow("unfollow", null);
        isFollow = github.isFollowing("federecio");
        assertFalse(isFollow);

        //emails
        List<String> emails = runMuleFlow("getEmails", List.class);
        assertNotNull(emails);

        runMuleFlow("addEmails", null);
        emails = github.getEmails();
        assertTrue( emails.contains("email1@mulesoft.com") );

        runMuleFlow("removeEmails", null);
        emails = github.getEmails();
        assertFalse(emails.contains("email1@mulesoft.com"));


        //key operations
        Key key = runMuleFlow("createKey", Key.class);
        assertNotNull(key);
        int keyId = key.getId();

        List<Key> keys = runMuleFlow("getKeys", List.class);
        boolean found = false;
        for (Key k: keys)
            if (k.getId()==keyId)
                found = true;

        assertTrue(found);

        key = github.editKey(keyId, "New title", null);
        assertEquals("New title", key.getTitle());

        github.deleteKey(keyId);

        keys = github.getKeys();
        found = false;
        for (Key k: keys)
            if (k.getId()==keyId)
                found = true;

        assertFalse(found);
    }
}
