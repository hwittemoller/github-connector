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
import org.mule.modules.github.automation.testcases.AddCommentTestCases;
import org.mule.modules.github.automation.testcases.CommitTests;
import org.mule.modules.github.automation.testcases.DeleteCommentTestCases;
import org.mule.modules.github.automation.testcases.EditCommentTestCases;
import org.mule.modules.github.automation.testcases.GetCommentTestCases;
import org.mule.modules.github.automation.testcases.GetCommitTestCases;
import org.mule.modules.github.automation.testcases.GetCommitsTestCases;

@RunWith(Categories.class)
@Categories.IncludeCategory(CommitTests.class)
@Suite.SuiteClasses({AddCommentTestCases.class, DeleteCommentTestCases.class, EditCommentTestCases.class, GetCommentTestCases.class,
        GetCommitsTestCases.class, GetCommitTestCases.class})
public class CommitTestSuite
{
}
