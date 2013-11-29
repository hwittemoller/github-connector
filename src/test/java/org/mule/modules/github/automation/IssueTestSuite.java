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
import org.mule.modules.github.automation.testcases.CloseIssueTestCases;
import org.mule.modules.github.automation.testcases.CreateCommentTestCases;
import org.mule.modules.github.automation.testcases.CreateIssueTestCases;
import org.mule.modules.github.automation.testcases.DeleteIssueCommentTestCases;
import org.mule.modules.github.automation.testcases.EditIssueCommentTestCases;
import org.mule.modules.github.automation.testcases.GetIssueCommentsTestCases;
import org.mule.modules.github.automation.testcases.GetIssueTestCases;
import org.mule.modules.github.automation.testcases.GetIssuesTestCases;
import org.mule.modules.github.automation.testcases.IssueTests;

@RunWith(Categories.class)
@Categories.IncludeCategory(IssueTests.class)
@Suite.SuiteClasses({CloseIssueTestCases.class, CreateCommentTestCases.class, CreateIssueTestCases.class, DeleteIssueCommentTestCases.class,
        EditIssueCommentTestCases.class, GetIssueCommentsTestCases.class, GetIssuesTestCases.class,  GetIssueTestCases.class })
public class IssueTestSuite
{
}
