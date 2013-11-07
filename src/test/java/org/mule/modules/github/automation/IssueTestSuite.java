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
import org.mule.modules.github.automation.testcases.CreateCommentTestCases;
import org.mule.modules.github.automation.testcases.CreateIssueTestCases;
import org.mule.modules.github.automation.testcases.GetGistCommentTestCases;
import org.mule.modules.github.automation.testcases.GetIssueTestCases;
import org.mule.modules.github.automation.testcases.IssueTests;

@RunWith(Categories.class)
@Categories.IncludeCategory(IssueTests.class)
@Suite.SuiteClasses({CreateCommentTestCases.class, CreateIssueTestCases.class, GetGistCommentTestCases.class, GetIssueTestCases.class})
public class IssueTestSuite
{
}
