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

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class UserAPITest extends BaseAPITest {

    @Test
    public void testUserAPI() throws Exception {
        //basic
        User user = runMuleFlow("getCurrentUser", User.class);
        assertNotNull(user);
        assertEquals(USER, user.getLogin());

        user = runMuleFlow("getUserByLoginName", User.class);
        assertNotNull(user);
        assertEquals("mule-tester", user.getLogin());

        user = runMuleFlow("updateCurrentUser", User.class);
        assertEquals("blogs.mulesoft.org", user.getBlog());
        assertEquals("Buenos Aires", user.getLocation());
        assertEquals("MuleSoft", user.getCompany());

        //followers, follow
        List<User> followers = runMuleFlow("getFollowers", List.class);
        assertNotNull(followers);

        List<User> following = runMuleFlow("getFollowing", List.class);
        assertNotNull(following);

        boolean isFollow = runMuleFlow("isFollowing", Boolean.class);
        assertFalse(isFollow);

        runMuleFlow("follow", null);
        isFollow = github.isFollowing("vgulyakin");
        assertTrue(isFollow);

        runMuleFlow("unfollow", null);
        isFollow = github.isFollowing("vgulyakin");
        assertFalse(isFollow);

        //emails
        List<String> emails = runMuleFlow("getEmails", List.class);
        assertNotNull(emails);

        runMuleFlow("addEmails", null);
        emails = github.getEmails();
        assertTrue(emails.contains("email1@mulesoft.com"));

        runMuleFlow("removeEmails", null);
        emails = github.getEmails();
        assertFalse(emails.contains("email1@mulesoft.com"));

        //key operations
        Key key = runMuleFlow("createKey", Key.class);
        assertNotNull(key);
        int keyId = key.getId();

        List<Key> keys = runMuleFlow("getKeys", List.class);
        boolean found = false;
        for (Key k : keys)
            if (k.getId() == keyId)
                found = true;

        assertTrue(found);

        Map<String, Integer> keyIdParameter = Collections.singletonMap("keyId", keyId);
        key = runMuleFlow("editKey", Key.class, keyIdParameter);
        assertEquals("New title", key.getTitle());

        key = runMuleFlow("getKey", Key.class, keyIdParameter);
        assertEquals("New title", key.getTitle());

        runMuleFlow("deleteKey", null, keyIdParameter);

        keys = runMuleFlow("getKeys", List.class);
        found = false;
        for (Key k : keys) {
            if (k.getId() == keyId) {
                found = true;
                break;
            }
        }
        assertFalse(found);
    }
}
