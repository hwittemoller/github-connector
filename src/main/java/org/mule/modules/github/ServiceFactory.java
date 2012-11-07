/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.modules.github;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.eclipse.egit.github.core.Authorization;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.CollaboratorService;
import org.eclipse.egit.github.core.service.CommitService;
import org.eclipse.egit.github.core.service.DataService;
import org.eclipse.egit.github.core.service.DeployKeyService;
import org.eclipse.egit.github.core.service.DownloadService;
import org.eclipse.egit.github.core.service.GistService;
import org.eclipse.egit.github.core.service.IssueService;
import org.eclipse.egit.github.core.service.LabelService;
import org.eclipse.egit.github.core.service.MilestoneService;
import org.eclipse.egit.github.core.service.OAuthService;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.eclipse.egit.github.core.service.TeamService;
import org.eclipse.egit.github.core.service.UserService;
import org.eclipse.egit.github.core.service.WatcherService;

public class ServiceFactory {

    private static final String BASE_URL = "api.github.com";
    private static IssueService defaultIssueService;
    private static WatcherService defaultWatcherService;
    private static CollaboratorService defaultCollaboratorService;
    private static CommitService defaultCommitService;
    private static DeployKeyService defaultDeployKeyService;
    private static DownloadService defaultDownloadService;
    private static GistService defaultGistService;
    private static LabelService defaultLabelService;
    private static MilestoneService defaultMilestoneService;
    private static UserService defaultUserService;
    private static TeamService defaultTeamService;
    private static RepositoryService defaultRepositoryService;
    private static OAuthService defaultOAuthService;
    private static DataService defaultDataService;
    private final String password;
    private final String user;
    private String token;

    public ServiceFactory(String user, String password, String scope) throws IOException {
        this.user = user;
        this.password = password;
        if (getOAuthService().getAuthorizations().size() > 0) {
            this.token = getOAuthService().getAuthorizations().get(0).getToken();
        } else {
            Authorization auth = new Authorization();
            List<String> scopes = Arrays.asList(scope);
            auth.setScopes(scopes);
            auth.setNote("Mule GitHub connector");
            this.token = getOAuthService().createAuthorization(auth).getToken();
        }
    }
    
    public OAuthService getOAuthService() {
        if (defaultOAuthService == null) {
            GitHubClient client = new GitHubClient(BASE_URL);
            client.setCredentials(user, password);
            setDefaultOAuthService(new OAuthService(client));
        }
        return defaultOAuthService;
    }
    
    public DataService getDataService() {
        if (defaultDataService == null) {
            GitHubClient client = new GitHubClient(BASE_URL);
            client.setCredentials(user, password);
            setDefaultDataService(new DataService(client));
        }
        return defaultDataService;
    }

    public IssueService getIssueService() {
        if (defaultIssueService == null) {
            GitHubClient client = new GitHubClient(BASE_URL);
            client.setOAuth2Token(token);
            setDefaultIssueService(new IssueService(client));
        }
        return defaultIssueService;
    }

    public WatcherService getWatcherService() {
        if (defaultWatcherService == null) {
            GitHubClient client = new GitHubClient(BASE_URL);
            client.setOAuth2Token(token);
            setDefaultWatcherService(new WatcherService(client));
        }
        return defaultWatcherService;
    }

    public CommitService getCommitService() {
        if (defaultCommitService == null) {
            GitHubClient client = new GitHubClient(BASE_URL);
            client.setOAuth2Token(token);
            setDefaultCommitService(new CommitService(client));
        }
        return defaultCommitService;
    }

    public CollaboratorService getCollaboratorService() {
        if (defaultCollaboratorService == null) {
            GitHubClient client = new GitHubClient(BASE_URL);
            client.setOAuth2Token(token);
            setDefaultCollaboratorService(new CollaboratorService(client));
        }
        return defaultCollaboratorService;
    }

    public DeployKeyService getDeployKeyService() {
        if (defaultDeployKeyService == null) {
            GitHubClient client = new GitHubClient(BASE_URL);
            client.setOAuth2Token(token);
            setDefaultDeployKeyService(new DeployKeyService(client));
        }
        return defaultDeployKeyService;
    }

    public DownloadService getDownloadService() {
        if (defaultDownloadService == null) {
            GitHubClient client = new GitHubClient(BASE_URL);
            client.setOAuth2Token(token);
            setDefaultDownloadService(new DownloadService(client));
        }
        return defaultDownloadService;
    }

    public GistService getGistService() {
        if (defaultGistService == null) {
            GitHubClient client = new GitHubClient(BASE_URL);
            client.setOAuth2Token(token);
            setGistService(new GistService(client));
        }
        return defaultGistService;
    }

    public LabelService getLabelService() {
        if (defaultLabelService == null) {
            GitHubClient client = new GitHubClient(BASE_URL);
            client.setOAuth2Token(token);
            setLabelService(new LabelService(client));
        }
        return defaultLabelService;
    }

    public MilestoneService getMilestoneService() {
        if (defaultMilestoneService == null) {
            GitHubClient client = new GitHubClient(BASE_URL);
            client.setOAuth2Token(token);
            setMilestoneService(new MilestoneService(client));
        }
        return defaultMilestoneService;
    }

    public UserService getUserService() {
        if (defaultUserService == null) {
            GitHubClient client = new GitHubClient(BASE_URL);
            client.setOAuth2Token(token);
            setDefaultUserService(new UserService(client));
        }
        return defaultUserService;
    }

    public TeamService getTeamService() {
        if (defaultTeamService == null) {
            GitHubClient client = new GitHubClient(BASE_URL);
            client.setOAuth2Token(token);
            setDefaultTeamService(new TeamService(client));
        }
        return defaultTeamService;
    }

    public RepositoryService getRepositoryService() {
        if (defaultRepositoryService == null) {
            GitHubClient client = new GitHubClient(BASE_URL);
            client.setOAuth2Token(token);
            setDefaultRepositoryService(new RepositoryService(client));
        }
        return defaultRepositoryService;
    }

    public static void setDefaultIssueService(IssueService defaultIssueService) {
        ServiceFactory.defaultIssueService = defaultIssueService;
    }

    public static void setDefaultDataService(DataService defaultDataService) {
        ServiceFactory.defaultDataService = defaultDataService;
    }
    
    public static void setDefaultWatcherService(WatcherService defaultWatcherService) {
        ServiceFactory.defaultWatcherService = defaultWatcherService;
    }

    public static void setDefaultCollaboratorService(CollaboratorService defaultCollaboratorService) {
        ServiceFactory.defaultCollaboratorService = defaultCollaboratorService;
    }

    public static void setDefaultCommitService(CommitService defaultCommitService) {
        ServiceFactory.defaultCommitService = defaultCommitService;
    }

    public static void setDeployKeyService(DeployKeyService defaultDeployKeyService) {
        ServiceFactory.defaultDeployKeyService = defaultDeployKeyService;
    }

    public static void setDownloadService(DownloadService defaultDownloadService) {
        ServiceFactory.defaultDownloadService = defaultDownloadService;
    }

    public static void setGistService(GistService defaultGistService) {
        ServiceFactory.defaultGistService = defaultGistService;
    }

    public static void setLabelService(LabelService defaultLabelService) {
        ServiceFactory.defaultLabelService = defaultLabelService;
    }

    public static void setMilestoneService(MilestoneService defaultMilestoneService) {
        ServiceFactory.defaultMilestoneService = defaultMilestoneService;
    }

    public static void setDefaultUserService(UserService defaultUserService) {
        ServiceFactory.defaultUserService = defaultUserService;
    }

    public static void setDefaultTeamService(TeamService defaultTeamService) {
        ServiceFactory.defaultTeamService = defaultTeamService;
    }

    public static void setDefaultRepositoryService(RepositoryService defaultRepositoryService) {
        ServiceFactory.defaultRepositoryService = defaultRepositoryService;
    }

    public static void setDefaultOAuthService(OAuthService defaultOAuthService) {
        ServiceFactory.defaultOAuthService = defaultOAuthService;
    }

    public static void setDefaultDeployKeyService(
            DeployKeyService defaultDeployKeyService) {
        ServiceFactory.defaultDeployKeyService = defaultDeployKeyService;
    }

    public static void setDefaultDownloadService(
            DownloadService defaultDownloadService) {
        ServiceFactory.defaultDownloadService = defaultDownloadService;
    }
}