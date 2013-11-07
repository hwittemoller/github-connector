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
import org.mule.modules.github.automation.testcases.AddCommentTestCases;
import org.mule.modules.github.automation.testcases.AddEmailsTestCases;
import org.mule.modules.github.automation.testcases.AddTeamMemberTestCases;
import org.mule.modules.github.automation.testcases.AddTeamRepositoryTestCases;
import org.mule.modules.github.automation.testcases.CreateCommentTestCases;
import org.mule.modules.github.automation.testcases.CreateDeployKeyTestCases;
import org.mule.modules.github.automation.testcases.CreateDownloadTestCases;
import org.mule.modules.github.automation.testcases.CreateGistTestCases;
import org.mule.modules.github.automation.testcases.CreateIssueTestCases;
import org.mule.modules.github.automation.testcases.CreateKeyTestCases;
import org.mule.modules.github.automation.testcases.CreateLabelTestCases;
import org.mule.modules.github.automation.testcases.CreateMilestoneTestCases;
import org.mule.modules.github.automation.testcases.CreatePullRequestCommentTestCases;
import org.mule.modules.github.automation.testcases.CreatePullRequestTestCases;
import org.mule.modules.github.automation.testcases.CreateRepositoryTestCases;
import org.mule.modules.github.automation.testcases.CreateTeamTestCases;
import org.mule.modules.github.automation.testcases.DeleteCommentTestCases;
import org.mule.modules.github.automation.testcases.DeleteDeployKeyTestCases;
import org.mule.modules.github.automation.testcases.DeleteGistTestCases;
import org.mule.modules.github.automation.testcases.DeleteKeyTestCases;
import org.mule.modules.github.automation.testcases.DeleteLabelTestCases;
import org.mule.modules.github.automation.testcases.DeleteMilestoneTestCases;
import org.mule.modules.github.automation.testcases.DeleteTeamTestCases;
import org.mule.modules.github.automation.testcases.EditCommentTestCases;
import org.mule.modules.github.automation.testcases.EditDeployKeyTestCases;
import org.mule.modules.github.automation.testcases.EditTeamTestCases;
import org.mule.modules.github.automation.testcases.FollowTestCases;
import org.mule.modules.github.automation.testcases.ForkGistTestCases;
import org.mule.modules.github.automation.testcases.GetCollaboratorsTestCases;
import org.mule.modules.github.automation.testcases.GetCommentTestCases;
import org.mule.modules.github.automation.testcases.GetCommitTestCases;
import org.mule.modules.github.automation.testcases.GetCommitsTestCases;
import org.mule.modules.github.automation.testcases.GetCurrentUserTestCases;
import org.mule.modules.github.automation.testcases.GetDeployKeyTestCases;
import org.mule.modules.github.automation.testcases.GetDownloadTestCases;
import org.mule.modules.github.automation.testcases.GetEmailsTestCases;
import org.mule.modules.github.automation.testcases.GetFollowersTestCases;
import org.mule.modules.github.automation.testcases.GetFollowingTestCases;
import org.mule.modules.github.automation.testcases.GetGistCommentTestCases;
import org.mule.modules.github.automation.testcases.GetGistTestCases;
import org.mule.modules.github.automation.testcases.GetIssueTestCases;
import org.mule.modules.github.automation.testcases.GetKeyTestCases;
import org.mule.modules.github.automation.testcases.GetLabelTestCases;
import org.mule.modules.github.automation.testcases.GetMilestoneTestCases;
import org.mule.modules.github.automation.testcases.GetPullRequestCommentTestCases;
import org.mule.modules.github.automation.testcases.GetPullRequestTestCases;
import org.mule.modules.github.automation.testcases.GetTeamMembersTestCases;
import org.mule.modules.github.automation.testcases.GetTeamRepositoriesTestCases;
import org.mule.modules.github.automation.testcases.GetTeamTestCases;
import org.mule.modules.github.automation.testcases.GetUserByLoginNameTestCases;
import org.mule.modules.github.automation.testcases.GetWatchedTestCases;
import org.mule.modules.github.automation.testcases.GetWatchersTestCases;
import org.mule.modules.github.automation.testcases.IsCollaboratorTestCases;
import org.mule.modules.github.automation.testcases.IsFollowingTestCases;
import org.mule.modules.github.automation.testcases.IsWatchingTestCases;
import org.mule.modules.github.automation.testcases.ListDownloadsTestCases;
import org.mule.modules.github.automation.testcases.RemoveCollaboratorTestCases;
import org.mule.modules.github.automation.testcases.RemoveEmailsTestCases;
import org.mule.modules.github.automation.testcases.RemoveTeamMemberTestCases;
import org.mule.modules.github.automation.testcases.RepositoryTestCases;
import org.mule.modules.github.automation.testcases.SmokeTests;
import org.mule.modules.github.automation.testcases.StarGistTestCases;
import org.mule.modules.github.automation.testcases.UnwatchTestCases;
import org.mule.modules.github.automation.testcases.UpdateCurrentUserTestCases;
import org.mule.modules.github.automation.testcases.WatchTestCases;

@RunWith(Categories.class)
@Categories.IncludeCategory(SmokeTests.class)
@Suite.SuiteClasses({AddCollaboratorTestCases.class, GetCollaboratorsTestCases.class, IsCollaboratorTestCases.class, RemoveCollaboratorTestCases.class,
        AddCommentTestCases.class, DeleteCommentTestCases.class, EditCommentTestCases.class, GetCommentTestCases.class,
        GetCommitsTestCases.class, GetCommitTestCases.class, CreateDeployKeyTestCases.class,DeleteDeployKeyTestCases.class,
        EditDeployKeyTestCases.class,GetDeployKeyTestCases.class, CreateDownloadTestCases.class, GetDownloadTestCases.class, ListDownloadsTestCases.class,
        CreateCommentTestCases.class,CreateGistTestCases.class,DeleteGistTestCases.class,ForkGistTestCases.class,
        GetGistCommentTestCases.class,GetGistTestCases.class,StarGistTestCases.class,
        CreateCommentTestCases.class, CreateIssueTestCases.class, GetGistCommentTestCases.class, GetIssueTestCases.class,
        CreateLabelTestCases.class, DeleteLabelTestCases.class,GetLabelTestCases.class,
        CreateMilestoneTestCases.class,DeleteMilestoneTestCases.class,GetMilestoneTestCases.class,
        CreatePullRequestCommentTestCases.class,CreatePullRequestTestCases.class,
        GetPullRequestCommentTestCases.class,GetPullRequestTestCases.class,RepositoryTestCases.class, CreateRepositoryTestCases.class,
        GetCurrentUserTestCases.class, GetFollowersTestCases.class, GetFollowingTestCases.class,
        GetUserByLoginNameTestCases.class, IsFollowingTestCases.class, UpdateCurrentUserTestCases.class, FollowTestCases.class,
        CreateKeyTestCases.class, GetKeyTestCases.class, DeleteKeyTestCases.class,
        GetEmailsTestCases.class, AddEmailsTestCases.class, RemoveEmailsTestCases.class,
        GetWatchedTestCases.class, GetWatchersTestCases.class, IsWatchingTestCases.class, WatchTestCases.class, UnwatchTestCases.class,
        AddTeamMemberTestCases.class, AddTeamRepositoryTestCases.class, CreateTeamTestCases.class, DeleteTeamTestCases.class,
        EditTeamTestCases.class,GetTeamMembersTestCases.class, GetTeamRepositoriesTestCases.class,GetTeamTestCases.class, RemoveTeamMemberTestCases.class

})
public class SmokeTestSuite
{
}
