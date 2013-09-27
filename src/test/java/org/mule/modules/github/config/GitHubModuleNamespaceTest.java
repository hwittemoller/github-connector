/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.modules.github.config;

import org.junit.Test;
import org.mule.api.processor.MessageProcessor;
import org.mule.modules.github.BaseAPITest;

public class GitHubModuleNamespaceTest extends BaseAPITest {

    @Test
    public void checkSchemaIsValid() {
        // by loading the mule-config.xml will validate the schema is deployable in Mule
    }
    
    public MessageProcessor lookupFlow(final String flowName)
    {
        return (MessageProcessor) muleContext.getRegistry().lookupFlowConstruct(flowName);
    }
    

}