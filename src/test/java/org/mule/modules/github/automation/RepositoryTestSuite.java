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
import org.mule.modules.github.automation.testcases.CreateRepositoryForOrgTestCases;
import org.mule.modules.github.automation.testcases.CreateRepositoryTestCases;
import org.mule.modules.github.automation.testcases.ForkRepositoryForOrgTestCases;
import org.mule.modules.github.automation.testcases.GetBranchesTestCases;
import org.mule.modules.github.automation.testcases.GetContributorsTestCases;
import org.mule.modules.github.automation.testcases.GetForksTestCases;
import org.mule.modules.github.automation.testcases.GetLanguagesTestCases;
import org.mule.modules.github.automation.testcases.GetRepositoriesForOrgTestCases;
import org.mule.modules.github.automation.testcases.GetRepositoriesForUserTestCases;
import org.mule.modules.github.automation.testcases.GetRepositoriesTestCases;
import org.mule.modules.github.automation.testcases.GetTagsTestCases;
import org.mule.modules.github.automation.testcases.RepositoryTests;

@RunWith(Categories.class)
@Categories.IncludeCategory(RepositoryTests.class)
@Suite.SuiteClasses({GetBranchesTestCases.class,GetContributorsTestCases.class,GetForksTestCases.class,GetLanguagesTestCases.class,
        GetRepositoriesForOrgTestCases.class,GetRepositoriesForUserTestCases.class,GetRepositoriesTestCases.class,GetTagsTestCases.class,
        CreateRepositoryTestCases.class, CreateRepositoryForOrgTestCases.class, ForkRepositoryForOrgTestCases.class})
public class RepositoryTestSuite
{
}
