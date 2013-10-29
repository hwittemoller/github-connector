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

package org.mule.modules.github.automation.testcases.deploykey;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.mule.modules.github.automation.testcases.collaborator.AddCollaboratorTestCases;
import org.mule.modules.github.automation.testcases.collaborator.GetCollaboratorsTestCases;
import org.mule.modules.github.automation.testcases.collaborator.IsCollaboratorTestCases;
import org.mule.modules.github.automation.testcases.collaborator.RemoveCollaboratorTestCases;

@RunWith(Suite.class)
@Suite.SuiteClasses({CreateDeployKeyTestCases.class,DeleteDeployKeyTestCases.class,EditDeployKeyTestCases.class,GetDeployKeyTestCases.class})
public class DeployKeyTestSuite
{
}
