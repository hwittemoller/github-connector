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

package org.mule.modules.github.automation;

import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.mule.modules.github.automation.testcases.AddCollaboratorTestCases;
import org.mule.modules.github.automation.testcases.CollaboratorTests;
import org.mule.modules.github.automation.testcases.GetCollaboratorsTestCases;
import org.mule.modules.github.automation.testcases.IsCollaboratorTestCases;
import org.mule.modules.github.automation.testcases.RemoveCollaboratorTestCases;

@RunWith(Categories.class)
@Categories.IncludeCategory(CollaboratorTests.class)
@Suite.SuiteClasses({AddCollaboratorTestCases.class, GetCollaboratorsTestCases.class, IsCollaboratorTestCases.class, RemoveCollaboratorTestCases.class})
public class CollaboratorTestSuite
{
}
