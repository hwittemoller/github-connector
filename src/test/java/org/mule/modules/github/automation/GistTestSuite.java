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
import org.mule.modules.github.automation.testcases.CreateGistCommentTestCases;
import org.mule.modules.github.automation.testcases.CreateGistTestCases;
import org.mule.modules.github.automation.testcases.DeleteGistCommentTestCases;
import org.mule.modules.github.automation.testcases.DeleteGistTestCases;
import org.mule.modules.github.automation.testcases.EditGistCommentTestCases;
import org.mule.modules.github.automation.testcases.ForkGistTestCases;
import org.mule.modules.github.automation.testcases.GetGistCommentTestCases;
import org.mule.modules.github.automation.testcases.GetGistCommentsTestCases;
import org.mule.modules.github.automation.testcases.GetGistTestCases;
import org.mule.modules.github.automation.testcases.GetGistsTestCases;
import org.mule.modules.github.automation.testcases.GistTests;
import org.mule.modules.github.automation.testcases.StarGistTestCases;
import org.mule.modules.github.automation.testcases.UnStarGistTestCases;

@RunWith(Categories.class)
@Categories.IncludeCategory(GistTests.class)
@Suite.SuiteClasses({CreateGistCommentTestCases.class, CreateGistTestCases.class, DeleteGistCommentTestCases.class, DeleteGistTestCases.class,
        EditGistCommentTestCases.class, ForkGistTestCases.class, GetGistCommentsTestCases.class, GetGistCommentTestCases.class,
        GetGistsTestCases.class, GetGistTestCases.class, StarGistTestCases.class, UnStarGistTestCases.class})
public class GistTestSuite
{
}
