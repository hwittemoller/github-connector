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
import org.mule.modules.github.automation.testcases.SmokeTests;
import org.mule.modules.github.automation.testcases.collaborator.AddCollaboratorTestCases;
import org.mule.modules.github.automation.testcases.collaborator.GetCollaboratorsTestCases;
import org.mule.modules.github.automation.testcases.collaborator.IsCollaboratorTestCases;
import org.mule.modules.github.automation.testcases.collaborator.RemoveCollaboratorTestCases;
import org.mule.modules.github.automation.testcases.commit.AddCommentTestCases;
import org.mule.modules.github.automation.testcases.commit.DeleteCommentTestCases;
import org.mule.modules.github.automation.testcases.commit.EditCommentTestCases;
import org.mule.modules.github.automation.testcases.commit.GetCommentTestCases;
import org.mule.modules.github.automation.testcases.commit.GetCommitTestCases;
import org.mule.modules.github.automation.testcases.commit.GetCommitsTestCases;
import org.mule.modules.github.automation.testcases.deploykey.CreateDeployKeyTestCases;
import org.mule.modules.github.automation.testcases.deploykey.DeleteDeployKeyTestCases;
import org.mule.modules.github.automation.testcases.deploykey.EditDeployKeyTestCases;
import org.mule.modules.github.automation.testcases.deploykey.GetDeployKeyTestCases;
import org.mule.modules.github.automation.testcases.download.CreateDownloadTestCases;
import org.mule.modules.github.automation.testcases.download.GetDownloadTestCases;
import org.mule.modules.github.automation.testcases.download.ListDownloadsTestCases;
import org.mule.modules.github.automation.testcases.gist.CreateCommentTestCases;
import org.mule.modules.github.automation.testcases.gist.CreateGistTestCases;
import org.mule.modules.github.automation.testcases.gist.DeleteGistTestCases;
import org.mule.modules.github.automation.testcases.gist.ForkGistTestCases;
import org.mule.modules.github.automation.testcases.gist.GetGistTestCases;
import org.mule.modules.github.automation.testcases.gist.StarGistTestCases;
import org.mule.modules.github.automation.testcases.issue.CreateIssueTestCases;
import org.mule.modules.github.automation.testcases.issue.GetIssueTestCases;
import org.mule.modules.github.automation.testcases.label.CreateLabelTestCases;
import org.mule.modules.github.automation.testcases.label.DeleteLabelTestCases;
import org.mule.modules.github.automation.testcases.label.GetLabelTestCases;
import org.mule.modules.github.automation.testcases.milestone.CreateMilestoneTestCases;
import org.mule.modules.github.automation.testcases.milestone.DeleteMilestoneTestCases;
import org.mule.modules.github.automation.testcases.milestone.GetMilestoneTestCases;
import org.mule.modules.github.automation.testcases.pullrequest.CreatePullRequestCommentTestCases;
import org.mule.modules.github.automation.testcases.pullrequest.CreatePullRequestTestCases;
import org.mule.modules.github.automation.testcases.pullrequest.GetPullRequestCommentTestCases;
import org.mule.modules.github.automation.testcases.pullrequest.GetPullRequestTestCases;
import org.mule.modules.github.automation.testcases.repository.CreateRepositoryTestCases;
import org.mule.modules.github.automation.testcases.repository.RepositoryTestCases;
import org.mule.modules.github.automation.testcases.user.AddEmailsTestCases;
import org.mule.modules.github.automation.testcases.user.CreateKeyTestCases;
import org.mule.modules.github.automation.testcases.user.DeleteKeyTestCases;
import org.mule.modules.github.automation.testcases.user.FollowTestCases;
import org.mule.modules.github.automation.testcases.user.GetCurrentUserTestCases;
import org.mule.modules.github.automation.testcases.user.GetEmailsTestCases;
import org.mule.modules.github.automation.testcases.user.GetFollowersTestCases;
import org.mule.modules.github.automation.testcases.user.GetFollowingTestCases;
import org.mule.modules.github.automation.testcases.user.GetKeyTestCases;
import org.mule.modules.github.automation.testcases.user.GetUserByLoginNameTestCases;
import org.mule.modules.github.automation.testcases.user.IsFollowingTestCases;
import org.mule.modules.github.automation.testcases.user.RemoveEmailsTestCases;
import org.mule.modules.github.automation.testcases.user.UpdateCurrentUserTestCases;
import org.mule.modules.github.automation.testcases.watcher.GetWatchedTestCases;
import org.mule.modules.github.automation.testcases.watcher.GetWatchersTestCases;
import org.mule.modules.github.automation.testcases.watcher.IsWatchingTestCases;
import org.mule.modules.github.automation.testcases.watcher.UnwatchTestCases;
import org.mule.modules.github.automation.testcases.watcher.WatchTestCases;

@RunWith(Categories.class)
@Categories.IncludeCategory(SmokeTests.class)
@Suite.SuiteClasses({AddCollaboratorTestCases.class, GetCollaboratorsTestCases.class, IsCollaboratorTestCases.class, RemoveCollaboratorTestCases.class,
        AddCommentTestCases.class, DeleteCommentTestCases.class, EditCommentTestCases.class, GetCommentTestCases.class,
        GetCommitsTestCases.class, GetCommitTestCases.class, CreateDeployKeyTestCases.class,DeleteDeployKeyTestCases.class,
        EditDeployKeyTestCases.class,GetDeployKeyTestCases.class, CreateDownloadTestCases.class, GetDownloadTestCases.class, ListDownloadsTestCases.class,
        CreateCommentTestCases.class,CreateGistTestCases.class,DeleteGistTestCases.class,ForkGistTestCases.class,
        org.mule.modules.github.automation.testcases.gist.GetCommentTestCases.class,GetGistTestCases.class,StarGistTestCases.class,
        CreateCommentTestCases.class, CreateIssueTestCases.class, org.mule.modules.github.automation.testcases.gist.GetCommentTestCases.class, GetIssueTestCases.class,
        CreateLabelTestCases.class, DeleteLabelTestCases.class,GetLabelTestCases.class,
        CreateMilestoneTestCases.class,DeleteMilestoneTestCases.class,GetMilestoneTestCases.class,
        CreatePullRequestCommentTestCases.class,CreatePullRequestTestCases.class,
        GetPullRequestCommentTestCases.class,GetPullRequestTestCases.class,RepositoryTestCases.class, CreateRepositoryTestCases.class,
        GetCurrentUserTestCases.class, GetFollowersTestCases.class, GetFollowingTestCases.class,
        GetUserByLoginNameTestCases.class, IsFollowingTestCases.class, UpdateCurrentUserTestCases.class, FollowTestCases.class,
        CreateKeyTestCases.class, GetKeyTestCases.class, DeleteKeyTestCases.class,
        GetEmailsTestCases.class, AddEmailsTestCases.class, RemoveEmailsTestCases.class,
        GetWatchedTestCases.class, GetWatchersTestCases.class, IsWatchingTestCases.class, WatchTestCases.class, UnwatchTestCases.class

})
public class SmokeTestSuite
{
}
