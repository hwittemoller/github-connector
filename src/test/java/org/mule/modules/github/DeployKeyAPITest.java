package org.mule.modules.github;

import org.eclipse.egit.github.core.Key;
import org.eclipse.egit.github.core.Repository;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 * <p/>
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

public class DeployKeyAPITest extends BaseAPITest {

    @Test
    public void testDeployKeyAPI() throws Exception {
        Repository repository = forkTestRepository();
        assertNotNull(repository);

        Key key = runMuleFlow("createDeployKey", Key.class);
        assertNotNull(key);
        int keyId = key.getId();
        Map<String, Integer> keyIdParameter = Collections.singletonMap("id", keyId);
        key = runMuleFlow("getDeployKey", Key.class, keyIdParameter);
        assertNotNull(key);
        assertEquals("key title", key.getTitle());

        List<Key> keys = runMuleFlow("getDeployKeys", List.class);
        assertTrue(keys.size() > 0);

        boolean found = false;
        for (Key k : keys) {
            if (k.getId() == keyId) {
                found = true;
                break;
            }
        }
        assertTrue(found);

        key = runMuleFlow("editDeployKey", Key.class, keyIdParameter);
        assertEquals("updated title", key.getTitle());
        assertTrue(key.getKey().startsWith("ssh-rsa AAAAB3NzaC1y"));

        runMuleFlow("deleteDeployKey", null, keyIdParameter);

        deleteTestRepository();

    }
}
