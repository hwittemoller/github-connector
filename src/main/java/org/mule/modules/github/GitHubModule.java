/**
 * Mule GitHub Cloud Connector
 *
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 * <p/>
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

package org.mule.modules.github;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.eclipse.egit.github.core.Comment;
import org.eclipse.egit.github.core.CommitComment;
import org.eclipse.egit.github.core.CommitFile;
import org.eclipse.egit.github.core.Contributor;
import org.eclipse.egit.github.core.Download;
import org.eclipse.egit.github.core.Gist;
import org.eclipse.egit.github.core.GistFile;
import org.eclipse.egit.github.core.IRepositoryIdProvider;
import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.IssueEvent;
import org.eclipse.egit.github.core.Key;
import org.eclipse.egit.github.core.Label;
import org.eclipse.egit.github.core.MergeStatus;
import org.eclipse.egit.github.core.Milestone;
import org.eclipse.egit.github.core.PullRequest;
import org.eclipse.egit.github.core.PullRequestMarker;
import org.eclipse.egit.github.core.Reference;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryBranch;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.RepositoryContents;
import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.RepositoryTag;
import org.eclipse.egit.github.core.Team;
import org.eclipse.egit.github.core.Tree;
import org.eclipse.egit.github.core.TypedResource;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.PageIterator;
import org.eclipse.egit.github.core.client.PagedRequest;
import org.mule.api.ConnectionException;
import org.mule.api.ConnectionExceptionCode;
import org.mule.api.annotations.Connect;
import org.mule.api.annotations.ConnectionIdentifier;
import org.mule.api.annotations.Connector;
import org.mule.api.annotations.Disconnect;
import org.mule.api.annotations.Processor;
import org.mule.api.annotations.ValidateConnection;
import org.mule.api.annotations.display.Password;
import org.mule.api.annotations.param.ConnectionKey;
import org.mule.api.annotations.param.Default;
import org.mule.api.annotations.param.Optional;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.codec.Base64Decoder;

/**
 * Mule GitHub Cloud Connector
 *
 * @author MuleSoft, Inc.
 */
@Connector(name = "github", schemaVersion = "1.0", friendlyName = "GitHub")
public class GitHubModule
{

    /**
     * A service factory instance
     */
    private ServiceFactory serviceFactory;

    /**
     * Get list of {@link Issue} objects that match the specified filter data
     * <p/>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:getIssues}
     *
     * @param owner       the owner of the repository, leave empty to use {@link ServiceFactory#user}
     * @param repository  the repository name
     * @param filterData  data to filter issues, if non is specified all issues will be returned
     * @return list of {@link Issue}
     * @throws java.io.IOException when the connection to the client failed
     * @api.doc <a href="http://developer.github.com/v3/issues/">List issues for a repository</a>
     */
    @Processor
    public List<Issue> getIssues(@Optional String owner, String repository, @Optional @Default("#[payload]") Map<String, String> filterData) throws IOException
    {
        if (filterData == null)
        {
            filterData = Collections.emptyMap();
        }

        return getServiceFactory().getIssueService().getIssues(getUser(owner), repository, filterData);
    }

    /**
     * Get list of {@link Issue} objects that were created during last {@code minutes}
     * <p/>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:getIssuesCreatedAfter}
     *
     * @param owner      the owner of the repository, leave empty to use {@link ServiceFactory#user}
     * @param repository the repository name
     * @param minutes    minutes
     * @return a list of {@link Issue}
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public List<Issue> getIssuesCreatedAfter(@Optional String owner, String repository, int minutes) throws IOException
    {
        List<Issue> issues = getServiceFactory().getIssueService().getIssues(getUser(owner), repository, Collections.<String, String>emptyMap());
        Iterator<Issue> iterator = issues.iterator();
        Date since = new Date(System.currentTimeMillis() - minutes * 60 * 1000);

        while (iterator.hasNext())
        {
            if (since.after(iterator.next().getCreatedAt()))
            {
                iterator.remove();
            }
        }

        return issues;
    }

    /**
     * Get a list of {@link Issue} objects since given number
     * <p/>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:getIssuesSinceNumber}
     *
     * @param owner           the owner of the repository, leave empty to use {@link ServiceFactory#user}
     * @param repository      the repository name
     * @param issueNumber     from issue number
     * @return a list of {@link Issue}
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public List<Issue> getIssuesSinceNumber(@Optional String owner, String repository, int issueNumber) throws IOException
    {
        List<Issue> issues = getServiceFactory().getIssueService().getIssues(getUser(owner), repository, Collections.<String, String>emptyMap());
        Iterator<Issue> iterator = issues.iterator();

        while (iterator.hasNext())
        {
            if (issueNumber >= iterator.next().getNumber())
            {
                iterator.remove();
            }
        }

        return issues;
    }

    /**
     * Creates a GitHub issue
     * <p/>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:createIssue}
     *
     * @param owner      the owner of the repository, leave empty to use {@link ServiceFactory#user}
     * @param repository the repository name
     * @param title      the title for the issue
     * @param body       the body for the issue
     * @param assignee   the assignee for the issue (optional)
     * @return the newly created {@link Issue}
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public Issue createIssue(@Optional String owner, String repository, String title, String body, @Optional String assignee) throws IOException
    {
        Issue issue = new Issue();
        issue.setTitle(title);
        issue.setBody(body);

        if (assignee != null)
        {
            User assigneeUser = new User().setName(assignee);
            issue.setAssignee(assigneeUser);
        }

        return getServiceFactory().getIssueService().createIssue(getUser(owner), repository, issue);
    }

    /**
     * Closes a GitHub issue
     * <p/>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:closeIssue}
     *
     * @param owner       the owner of the repository, leave empty to use {@link ServiceFactory#user}
     * @param repository  the repository name
     * @param issueNumber the issueNumber of the issue
     * @return the newly created {@link Issue}
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public Issue closeIssue(@Optional String owner, String repository, String issueNumber) throws IOException
    {
        Issue issue = getIssue(getUser(owner), repository, issueNumber);
        issue.setState("closed");

        return getServiceFactory().getIssueService().editIssue(getUser(owner), repository, issue);
    }

    /**
     * Get the issue represented by the given issueNumber
     * <p/>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:getIssue}
     *
     * @param owner          the owner of the repository, leave empty to use {@link ServiceFactory#user}
     * @param repository    the repository name
     * @param issueNumber   the id of the issue
     * @return a {@link Issue}
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public Issue getIssue(@Optional String owner, String repository, String issueNumber) throws IOException
    {
        return getServiceFactory().getIssueService().getIssue(getUser(owner), repository, issueNumber);
    }

    /**
     * Get an issue's comments
     * <p/>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:getComments}
     *
     * @param owner         the owner of the repository, leave empty to use {@link ServiceFactory#user}
     * @param repository    the repository name
     * @param issueNumber   the id of the issue
     * @return a list of {@link Comment}
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public List<Comment> getComments(@Optional String owner, String repository, String issueNumber) throws IOException
    {
        return getServiceFactory().getIssueService().getComments(getUser(owner), repository, issueNumber);
    }

    /**
     * Create comment on specified issue number
     * <p/>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:createComment}
     *
     * @param owner         the owner of the repository, leave empty to use {@link ServiceFactory#user}
     * @param repository    the repository name
     * @param issueNumber   the issue id
     * @param comment       the text of the comment
     * @return the created {@link Comment}
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public Comment createComment(@Optional String owner, String repository, String issueNumber, String comment) throws IOException
    {
        return getServiceFactory().getIssueService().createComment(getUser(owner), repository, issueNumber, comment);
    }

    /**
     * Edits the body of the comment represented by the given comment id
     * <p/>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:editComment}
     *
     * @param owner      the owner of the repository, leave empty to use {@link ServiceFactory#user}
     * @param repository the repository name
     * @param commentId  the comment id
     * @param body       the new text of the comment
     * @return the created {@link Comment}
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public Comment editComment(@Optional String owner, String repository, long commentId, String body) throws IOException
    {
        Comment comment = getServiceFactory().getIssueService().getComment(getUser(owner), repository, commentId);
        comment.setBody(body);

        return getServiceFactory().getIssueService().editComment(getUser(owner), repository, comment);
    }

    /**
     * Delete the issue comment with the given id
     * <p/>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:deleteComment}
     *
     * @param owner      the owner of the repository, leave empty to use {@link ServiceFactory#user}
     * @param repository the repository name
     * @param commentId  the id of the comment to delete
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public void deleteComment(@Optional String owner, String repository, String commentId) throws IOException
    {
        getServiceFactory().getIssueService().deleteComment(getUser(owner), repository, commentId);
    }

    /**
     * Get issue event for repository
     * <p/>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:getIssueEvent}
     *
     * @param owner      the owner of the repository, leave empty to use {@link ServiceFactory#user}
     * @param repository the repository name
     * @param eventId    the id of the event
     * @return a {@link IssueEvent}
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public IssueEvent getIssueEvent(@Optional String owner, String repository, long eventId) throws IOException
    {
        return getServiceFactory().getIssueService().getIssueEvent(getUser(owner), repository, eventId);
    }

    /**
     * Get users watching given repository
     * <p/>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:getWatchers}
     *
     * @param owner         the owner of the repository, leave empty to use {@link ServiceFactory#user}
     * @param repository    the name of the repository
     * @return non-null but possibly empty list of {@link User}
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public List<User> getWatchers(@Optional String owner, String repository) throws IOException
    {
        return getServiceFactory().getWatcherService().getWatchers(RepositoryId.create(owner, repository));
    }

    /**
     * Get repositories watched by the given user
     * <p/>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:getWatched}
     *
     * @param user the user for which the get the watched repositories, leave empty to use {@link ServiceFactory#user}
     * @return non-null but possibly empty list of {@link Repository}
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public List<Repository> getWatched(@Optional String user) throws IOException
    {
        return getServiceFactory().getWatcherService().getWatched(getUser(user));
    }

    /**
     * Is currently authenticated user watching given repository?
     * <p/>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:isWatching}
     *
     * @param owner          the name of the user that owns the repository
     * @param repository     the name of the repository
     * @return true if watch, false otherwise
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public boolean isWatching(String owner, String repository) throws IOException
    {
        return getServiceFactory().getWatcherService().isWatching(RepositoryId.create(owner, repository));
    }

    /**
     * Add currently authenticated user as a watcher of the given repository
     * <p/>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:watch}
     *
     * @param owner          the name of the user that owns the repository
     * @param repository     the name of the repository
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public void watch(String owner, String repository) throws IOException
    {
        getServiceFactory().getWatcherService().watch(RepositoryId.create(owner, repository));
    }

    /**
     * Remove currently authenticated user as a watcher of the given repository
     * <p/>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:unwatch}
     *
     * @param owner          the name of the user that owns the repository
     * @param repository     the name of the repository
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public void unwatch(String owner, String repository) throws IOException
    {
        getServiceFactory().getWatcherService().unwatch(RepositoryId.create(owner, repository));
    }

    /**
     * Returns the list of collaborators of the given repository
     * </p>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:getCollaborators}
     *
     * @param owner          the name of the user that owns the repository
     * @param repository     the name of the repository
     * @return non-null but possibly empty list of {@link User}
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public List<User> getCollaborators(String owner, String repository) throws IOException
    {
        return getServiceFactory().getCollaboratorService().getCollaborators(RepositoryId.create(owner, repository));
    }

    /**
     * Returns whether the user is a collaborator of the given repository
     * </p>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:isCollaborator}
     *
     * @param owner          the name of the user that owns the repository
     * @param repository     the name of the repository
     * @param user           the user to consult if it's a collaborator or not, leave empty to use {@link ServiceFactory#user}
     * @return true if the user is a collaborator, false otherwise
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public boolean isCollaborator(String owner, String repository, @Optional String user) throws IOException
    {
        return getServiceFactory().getCollaboratorService().isCollaborator(RepositoryId.create(owner, repository), getUser(user));
    }

    /**
     * Adds a collaborator to the given repository
     * </p>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:addCollaborator}
     *
     * @param owner          the name of the user that owns the repository
     * @param repository the name of the repository
     * @param user           the user that's going to be added as a collaborator to the given repository, leave empty to use {@link ServiceFactory#user}
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public void addCollaborator(String owner, String repository, @Optional String user) throws IOException
    {
        getServiceFactory().getCollaboratorService().addCollaborator(RepositoryId.create(owner, repository), getUser(user));
    }

    /**
     * Removes a collaborator from the given repository
     * </p>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:removeCollaborator}
     *
     * @param owner          the name of the user that owns the repository
     * @param repository     the name of the repository
     * @param user           the user that's going to be removed as a collaborator from the given repository, leave empty to use {@link ServiceFactory#user}
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public void removeCollaborator(String owner, String repository, @Optional String user) throws IOException
    {
        getServiceFactory().getCollaboratorService().removeCollaborator(RepositoryId.create(owner, repository), getUser(user));
    }

    /**
     * Returns a list of the commits for a given repository
     * </p>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:getCommits}
     *
     * @param owner          the name of the user that owns the repository
     * @param repository     the name of the repository
     * @return non-null but possibly empty list of {@link RepositoryCommit}
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public List<RepositoryCommit> getCommits(String owner, String repository) throws IOException
    {
        return getServiceFactory().getCommitService().getCommits(RepositoryId.create(owner, repository));
    }

    /**
     * Returns all commits in given repository beginning at an optional commit SHA-1
     * and affecting an optional path.
     * </p>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:getCommitsBySha}
     *
     * @param owner          the name of the user that owns the repository
     * @param repository     the name of the repository
     * @param sha            an optional Sha or branch to start listing commits from
     * @param path           optional Only commits containing this file path will be returned
     * @return non-null but possibly empty list of {@link RepositoryCommit}
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public List<RepositoryCommit> getCommitsBySha(String owner, String repository, @Optional String sha, @Optional String path) throws IOException
    {
        return getServiceFactory().getCommitService().getCommits(RepositoryId.create(owner, repository), sha, path);
    }

    /**
     * Returns a commit with given SHA-1 from given repository
     * </p>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:getCommit}
     *
     * @param owner          the name of the user that owns the repository
     * @param repository     the name of the repository
     * @param sha            Sha from comment
     * @return a {@link RepositoryCommit} for the given Sha and repository
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public RepositoryCommit getCommit(String owner, String repository, String sha) throws IOException
    {
        return getServiceFactory().getCommitService().getCommit(RepositoryId.create(owner, repository), sha);
    }

    /**
     * Returns all comments on commit with given Sha
     * </p>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:getCommmitComments}
     *
     * @param owner          the name of the user that owns the repository
     * @param repository     the name of the repository
     * @param sha            Sha or branch to start listing commits comments from
     * @return non-null but possibly empty list of {@link CommitComment}
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public List<CommitComment> getCommmitComments(String owner, String repository, String sha) throws IOException
    {
        return getServiceFactory().getCommitService().getComments(RepositoryId.create(owner, repository), sha);
    }

    /**
     * Returns a comment from commit with the given commentId
     * </p>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:getComment}
     *
     * @param owner          the name of the user that owns the repository
     * @param repository     the name of the repository
     * @param commentId      the id from the comment
     * @return a {@link CommitComment} that corresponds to the given id
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public CommitComment getComment(String owner, String repository, long commentId) throws IOException
    {
        return getServiceFactory().getCommitService().getComment(RepositoryId.create(owner, repository), commentId);
    }

    /**
     * Creates a commit comment
     * </p>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:addComment}
     *
     * @param owner          the name of the user that owns the repository
     * @param repository     the name of the repository
     * @param body           body of the commit comment
     * @param commitId       sha of the commit to comment on
     * @param line           line number in the file to comment on
     * @param path           relative path of the file to comment on
     * @param position       line index in the diff to comment on
     * @return created {@link CommitComment}
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public CommitComment addComment(String owner, String repository, String body, String commitId, int line, String path, int position) throws IOException
    {
        CommitComment comment = new CommitComment();
        comment.setCommitId(commitId);
        comment.setLine(line);
        comment.setPath(path);
        comment.setPosition(position);
        comment.setBody(body);

        return getServiceFactory().getCommitService().addComment(RepositoryId.create(owner, repository), commitId, comment);
    }

    /**
     * Edits a given comment
     * </p>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:editCommitComment}
     *
     * @param owner          the name of the user that owns the repository
     * @param repository     the name of the repository
     * @param body           Body of the commit comment
     * @param commentId      the id of commit comment
     * @param line           line number in the file to comment on
     * @param path           relative path of the file to comment on
     * @param position       line index in the diff to comment on
     * @return updated {@link CommitComment}
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public CommitComment editCommitComment(String owner, String repository, String body, long commentId, int line, String path, int position) throws IOException
    {
        CommitComment comment = new CommitComment();
        comment.setId(commentId);
        comment.setLine(line);
        comment.setPath(path);
        comment.setPosition(position);
        comment.setBody(body);

        return getServiceFactory().getCommitService().editComment(RepositoryId.create(owner, repository), comment);
    }

    /**
     * Deletes the given comment
     * </p>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:deleteCommitComment}
     *
     * @param owner          the name of the user that owns the repository
     * @param repository     the name of the repository
     * @param commentId      id from the comment
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public void deleteCommitComment(String owner, String repository, long commentId) throws IOException
    {
        getServiceFactory().getCommitService().deleteComment(RepositoryId.create(owner, repository), commentId);
    }

    /**
     * Returns all deploys keys associated with the given repository
     * </p>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:getDeployKeys}
     *
     * @param owner         the name of the user that owns the repository
     * @param repository    the name of the repository
     * @return non-null but possibly empty list of {@link Key}
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public List<Key> getDeployKeys(String owner, String repository) throws IOException
    {
        return getServiceFactory().getDeployKeyService().getKeys(RepositoryId.create(owner, repository));
    }


    /**
     * Returns the key that corresponds to the given id
     * </p>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:getDeployKey}
     *
     * @param owner          the name of the user that owns the repository
     * @param repository     the name of the repository
     * @param id             the id of the key
     * @return the {@link Key} corresponding to the given id
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public Key getDeployKey(String owner, String repository, int id) throws IOException
    {
        return getServiceFactory().getDeployKeyService().getKey(RepositoryId.create(owner, repository), id);
    }


    /**
     * Returns a new deploy key
     * </p>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:createDeployKey}
     *
     * @param owner          the name of the user that owns the repository
     * @param repository     the name of the repository
     * @param title          the title of the key
     * @param key            ssh key
     * @return a new {@link Key} created based on the given parameters
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public Key createDeployKey(String owner, String repository, String title, String key) throws IOException
    {
        Key newKey = new Key();
        newKey.setTitle(title);
        newKey.setKey(key);

        return getServiceFactory().getDeployKeyService().createKey(RepositoryId.create(owner, repository), newKey);
    }

    /**
     * Edits a given deploy key
     * </p>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:editDeployKey}
     *
     * @param owner          the name of the user that owns the repository
     * @param repository     the name of the repository
     * @param id             the ID of the key
     * @param title          the title of the key
     * @param key            ssh key
     * @return the modified {@link Key}
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public Key editDeployKey(String owner, String repository, int id, @Optional String title, @Optional String key) throws IOException
    {
        Key keyToEdit = getDeployKey(owner, repository, id);

        if (title != null)
        {
            keyToEdit.setTitle(title);
        }

        if (key != null)
        {
            keyToEdit.setKey(key);
        }

        return getServiceFactory().getDeployKeyService().editKey(RepositoryId.create(owner, repository), keyToEdit);
    }


    /**
     * Deletes a deploy key given the given id
     * </p>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:deleteDeployKey}
     *
     * @param owner          the name of the user that owns the repository
     * @param repository     the name of the repository
     * @param id             the id of the deploy key
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public void deleteDeployKey(String owner, String repository, int id) throws IOException
    {
        getServiceFactory().getDeployKeyService().deleteKey(RepositoryId.create(owner, repository), id);
    }

     /**
     * Returns the gist according to the given id
     * </p>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:getGist}
     *
     * @param gistId    the id of the wanted gist
     * @return returns  the {@link Gist} according to the provided id
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public Gist getGist(String gistId) throws IOException
    {
        return getServiceFactory().getGistService().getGist(gistId);
    }

    /**
     * Creates a gist for the given files.
     * </p>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:createGist}
     *
     * @param isPublic    whether or not the gist is public
     * @param description optional description for the gist
     * @param files       optional map of gist files to create
     * @return returns the {@link Gist} created
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public Gist createGist(boolean isPublic, @Optional String description, @Optional @Default("#[payload]") Map<String, String> files) throws IOException
    {
        Gist gist = new Gist();
        gist.setDescription(description);
        gist.setPublic(isPublic);

        for (Map.Entry<String, String> entry : files.entrySet())
        {
            GistFile file = new GistFile().setContent(entry.getValue());
            gist.setFiles(Collections.singletonMap(entry.getKey(), file));
        }

        return getServiceFactory().getGistService().createGist(gist);
    }

    /**
     * Returns the starred gists for the currently authenticated user
     * </p>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:getStarredGists}
     *
     * @return list of starred {@link Gist} for currently authenticated user
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public List<Gist> getStarredGists() throws IOException
    {
        return getServiceFactory().getGistService().getStarredGists();
    }

    /**
     * Returns a list of gists for the specified user
     * </p>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:getGists}
     *
     * @param user user of the gists, leave empty to use {@link ServiceFactory#user}
     * @return a list of gists for the specified user
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public List<Gist> getGists(@Optional String user) throws IOException
    {
        return getServiceFactory().getGistService().getGists(getUser(user));
    }

    /**
     * Creates a comment on the specified gist id
     * </p>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:createGistComment}
     *
     * @param gistId  id of the gist
     * @param comment comment to create in the gist
     * @return a new {@link Comment} for the gist
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public Comment createGistComment(String gistId, String comment) throws IOException
    {
        return getServiceFactory().getGistService().createComment(gistId, comment);
    }

    /**
     * Returns a list of comments for the given gist
     * </p>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:getGistComments}
     *
     * @param gistId    id of the gist
     * @return a list with the comments of the given gist
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public List<Comment> getGistComments(String gistId) throws IOException
    {
        return getServiceFactory().getGistService().getComments(gistId);
    }

    /**
     * Deletes the given gist
     * </p>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:deleteGist}
     *
     * @param gistId    the id of the gist
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public void deleteGist(String gistId) throws IOException
    {
        getServiceFactory().getGistService().deleteGist(gistId);
    }

    /**
     * Returns the comment according to the given id
     * </p>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:getGistComment}
     *
     * @param gistId the id of the Gist
     * @param commentId id of the comment to be retrieved
     * @return a comment corresponding to the given id
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public Comment getGistComment(String gistId, long commentId) throws IOException
    {
        return getServiceFactory().getGistService().getComment(gistId, commentId);
    }

    /**
     * Deletes the given comment
     * </p>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:deleteGistComment}
     *
     * @param gistId the id of the Gist
     * @param commentId id of the comment
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public void deleteGistComment(String gistId, long commentId) throws IOException
    {
        getServiceFactory().getGistService().deleteComment(gistId, commentId);
    }

    /**
     * Updates the given comment
     * </p>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:editGistComment}
     *
     * @param gistId the id of the Gist
     * @param commentId id of the comment
     * @param body      new body of the comment
     * @return returns the updated {@link Comment}
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public Comment editGistComment(String gistId, long commentId, String body) throws IOException
    {
        Comment comment = new Comment();
        comment.setId(commentId);
        comment.setBody(body);

        return getServiceFactory().getGistService().editComment(gistId, comment);
    }

    /**
     * </p>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:starGist}
     * <p/>
     * Star the gist with the given id
     *
     * @param gistId id of the gist to be starred
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public void starGist(String gistId) throws IOException
    {
        getServiceFactory().getGistService().starGist(gistId);
    }

    /**
     * </p>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:unstarGist}
     * <p/>
     * Unstar the gist with the given id
     *
     * @param gistId id of the gist to be unstarred
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public void unstarGist(String gistId) throws IOException
    {
        getServiceFactory().getGistService().unstarGist(gistId);
    }

    /**
     * Returns whether the gist is starred or not
     * </p>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:isStarredGist}
     *
     * @param gistId id of the gist
     * @return returns true if the gist is starred, false otherwise
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public boolean isStarredGist(String gistId) throws IOException
    {
        return getServiceFactory().getGistService().isStarred(gistId);
    }

    /**
     * Forks the given gist
     * </p>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:forkGist}
     *
     * @param gistId id of the gist to be forked
     * @return returns the forked gist
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public Gist forkGist(String gistId) throws IOException
    {
        return getServiceFactory().getGistService().forkGist(gistId);
    }

    /**
     * Returns a list of labels for the given repository and owner
     * </p>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:getLabels}
     *
     * @param owner       the owner of the repository, leave empty to use {@link ServiceFactory#user}
     * @param repository  the name of the repository
     * @return a list of labels for the given repository and owner
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public List<Label> getLabels(@Optional String owner, String repository) throws IOException
    {
        return getServiceFactory().getLabelService().getLabels(getUser(owner), repository);
    }

    /**
     * Returns a single label
     * </p>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:getLabel}
     *
     * @param owner       the owner of the repository, leave empty to use {@link ServiceFactory#user}
     * @param repository the name of the repository
     * @param label      the label name
     * @return the label associated to the given id
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public Label getLabel(@Optional String owner, String repository, String label) throws IOException
    {
        return getServiceFactory().getLabelService().getLabel(getUser(owner), repository, label);
    }

    /**
     * Deletes the given label
     * </p>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:deleteLabel}
     *
     * @param owner      the owner of the repository, leave empty to use {@link ServiceFactory#user}
     * @param repository the name of the repository
     * @param label      the label name
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public void deleteLabel(@Optional String owner, String repository, String label) throws IOException
    {
        getServiceFactory().getLabelService().deleteLabel(getUser(owner), repository, label);
    }

    /**
     * Creates a new label
     * </p>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:createLabel}
     *
     * @param owner         the owner of the repository, leave empty to use {@link ServiceFactory#user}
     * @param repository    the name of the repository
     * @param label         the name of the label
     * @param color         the color of the label, a 6 character hex code, without a leading #
     * @return returns a new {@link Label}
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public Label createLabel(@Optional String owner, String repository, String label, String color) throws IOException
    {
        Label newLabel = new Label();
        newLabel.setName(label);
        newLabel.setColor(color);

        return getServiceFactory().getLabelService().createLabel(getUser(owner), repository, newLabel);
    }

    /**
     * Returns a list of milestones for the given repository
     * </p>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:getMilestones}
     *
     * @param owner       the owner of the repository, leave empty to use {@link ServiceFactory#user}
     * @param repository the name of the repository
     * @param state      state of the milestone, open or closed
     * @return s list of {@link Milestone} for the given repository
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public List<Milestone> getMilestones(@Optional String owner, String repository, String state) throws IOException
    {
        return getServiceFactory().getMilestoneService().getMilestones(getUser(owner), repository, state);
    }


    /**
     * Returns a single milestone
     * </p>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:getMilestone}
     *
     * @param owner      the owner of the repository, leave empty to use {@link ServiceFactory#user}
     * @param repository the name of the repository
     * @param number     milestone number
     * @return returns a single milestone
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public Milestone getMilestone(@Optional String owner, String repository, String number) throws IOException
    {
        return getServiceFactory().getMilestoneService().getMilestone(getUser(owner), repository, number);
    }


    /**
     * Deletes a given milestone
     * </p>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:deleteMilestone}
     *
     * @param owner      the owner of the repository, leave empty to use {@link ServiceFactory#user}
     * @param repository the name of the repository
     * @param number     number of the milestone
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public void deleteMilestone(@Optional String owner, String repository, String number) throws IOException
    {
        getServiceFactory().getMilestoneService().deleteMilestone(getUser(owner), repository, number);
    }

    /**
     * Creates a new milestone
     * </p>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:createMilestone}
     *
     * @param owner        the owner of the repository, leave empty to use {@link ServiceFactory#user}
     * @param repository  the name of the repository
     * @param title       the title of the milestone
     * @param state       the state of the milestone, open or closed,  by default is open
     * @param description the description of the milestone
     * @param dueOn       when the milestone is due
     * @return a new milestone with the given parameters
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public Milestone createMilestone(@Optional String owner, String repository, String title, @Optional String state, @Optional String description, @Optional Date dueOn) throws IOException
    {
        Milestone milestone = new Milestone();
        milestone.setTitle(title);
        milestone.setState(state);
        milestone.setDueOn(dueOn);
        milestone.setDescription(description);

        return getServiceFactory().getMilestoneService().createMilestone(getUser(owner), repository, milestone);
    }

    /**
     * Get user with given login name
     * <p/>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:getUserByLoginName}
     *
     * @param login     the login name of the user to look up
     * @return a {@link User} instance
     * @throws java.io.IOException when the connection to the client failed
     * @api.doc <a href="http://developer.github.com/v3/users/">Get a single user</a>
     */
    @Processor
    public User getUserByLoginName(String login) throws IOException
    {
        return getServiceFactory().getUserService().getUser(login);
    }

    /**
     * Get the currently authenticated user
     * <p/>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:getCurrentUser}
     *
     * @return a {@link User} instance representing the current user
     * @throws java.io.IOException when the connection to the client failed
     * @api.doc <a href="http://developer.github.com/v3/users/">Get the authenticated user</a>
     */
    @Processor
    public User getCurrentUser() throws IOException
    {
        return getServiceFactory().getUserService().getUser();
    }

    /**
     * Updates the currently authenticated user using the given attributes. Only provide values for the attributes
     * you want to change.
     * <p/>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:updateCurrentUser}
     *
     * @param userName a the name for the current user
     * @param email    a publicly visibile email address for the current user
     * @param blog     a blog for the current user
     * @param company  a company for the current user
     * @param location a location for the current user
     * @param hireable whether the current user is hireable
     * @return a {@link User} instance representing the current user
     * @throws java.io.IOException when the connection to the client failed
     * @api.doc <a href="http://developer.github.com/v3/users/">Update the authenticated user</a>
     */
    @Processor
    public User updateCurrentUser(@Optional String userName, @Optional String email, @Optional String blog, @Optional String company, @Optional String location, @Optional Boolean hireable) throws IOException
    {
        User currentUser = getCurrentUser();

        if (userName != null)
        {
            currentUser.setName(userName);
        }

        if (email != null)
        {
            currentUser.setEmail(email);
        }

        if (blog != null)
        {
            currentUser.setBlog(blog);
        }

        if (company != null)
        {
            currentUser.setCompany(company);
        }

        if (location != null)
        {
            currentUser.setLocation(location);
        }

        if (hireable != null)
        {
            currentUser.setHireable(hireable);
        }

        return getServiceFactory().getUserService().editUser(currentUser);
    }

    /**
     * Returns a list of followers for given user, if no one is provided the current authenticated user will be used
     * <p/>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:getFollowers}
     *
     * @param user the user for which to get the followers, leave empty to use current user
     * @return a collection of {@link User} that follow the current user
     * @throws java.io.IOException when the connection to the client failed
     * @api.doc <a href="http://developer.github.com/v3/users/followers/">List followers of a user</a>
     */
    @Processor
    public List<User> getFollowers(@Optional String user) throws IOException
    {
        return getServiceFactory().getUserService().getFollowers(getUser(user));
    }

    /**
     * Returns a list users following the given user, if no one is provided the current authenticated user will be used
     * <p/>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:getFollowing}
     *
     * @param user the user for which to get who is following, leave empty to use current user
     * @return a collection of {@link User} the user is following
     * @throws java.io.IOException when the connection to the client failed
     * @api.doc <a href="http://developer.github.com/v3/users/followers/">List users following another user</a>
     */
    @Processor
    public List<User> getFollowing(@Optional String user) throws IOException
    {
        return getServiceFactory().getUserService().getFollowing(getUser(user));
    }

    /**
     * Returns wether the authenticated user is following the given user
     * <p/>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:isFollowing}
     *
     * @param user the user to check
     * @return whether the authenticated user is following the given user
     * @throws java.io.IOException when the connection to the client failed
     * @api.doc <a href="http://developer.github.com/v3/users/followers/">Check if you are following a user</a>
     */
    @Processor
    public boolean isFollowing(String user) throws IOException
    {
        return getServiceFactory().getUserService().isFollowing(user);
    }

    /**
     * Make the currently authenticated user follow the given user
     * <p/>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:follow}
     *
     * @param user the user to follow
     * @throws java.io.IOException when the connection to the client failed
     * @api.doc <a href="http://developer.github.com/v3/users/followers/">Follow a user</a>
     */
    @Processor
    public void follow(String user) throws IOException
    {
        getServiceFactory().getUserService().follow(user);
    }

    /**
     * Make the currently authenticated user stop following the given user
     * <p/>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:unfollow}
     *
     * @param user the user to unfollow
     * @throws java.io.IOException when the connection to the client failed
     * @api.doc <a href="http://developer.github.com/v3/users/followers/">Unfollow a user</a>
     */
    @Processor
    public void unfollow(String user) throws IOException
    {
        getServiceFactory().getUserService().unfollow(user);
    }

    /**
     * Get all e-mail addresses for the currently authenticated user
     * <p/>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:getEmails}
     *
     * @return list of e-mail address
     * @throws java.io.IOException when the connection to the client failed
     * @api.doc <a href="http://developer.github.com/v3/users/emails/">List email addresses for a user</a>
     */
    @Processor
    public List<String> getEmails() throws IOException
    {
        return getServiceFactory().getUserService().getEmails();
    }

    /**
     * Add one or more e-mail addresses to the currently authenticated user's account
     * <p/>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:addEmails}
     *
     * @param emails the email addresses to add
     * @throws java.io.IOException when the connection to the client failed
     * @api.doc <a href="http://developer.github.com/v3/users/emails/">Add email address(es)</a>
     */
    @Processor
    public void addEmails(@Optional @Default("#[payload]") List<String> emails) throws IOException
    {
        if (CollectionUtils.isNotEmpty(emails))
        {
            getServiceFactory().getUserService().addEmail(emails.toArray(new String[emails.size()]));
        }
    }

    /**
     * Removes one or more e-mail addresses to the currently authenticated user's account
     * <p/>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:removeEmails}
     *
     * @param emails the email addresses to add
     * @throws java.io.IOException when the connection to the client failed
     * @api.doc <a href="http://developer.github.com/v3/users/emails/">Delete email address(es)</a>
     */
    @Processor
    public void removeEmails(@Optional @Default("#[payload]") List<String> emails) throws IOException
    {
        if (CollectionUtils.isNotEmpty(emails))
        {
            getServiceFactory().getUserService().removeEmail(emails.toArray(new String[emails.size()]));
        }
    }

    /**
     * Get all public keys for currently authenticated user
     * <p/>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:getKeys}
     *
     * @return non-null list of {@link Key}
     * @throws java.io.IOException when the connection to the client failed
     * @api.doc <a href="http://developer.github.com/v3/users/keys/">List public keys for a user</a>
     */
    @Processor
    public List<Key> getKeys() throws IOException
    {
        return getServiceFactory().getUserService().getKeys();
    }

    /**
     * Get key with given id
     * <p/>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:getKey}
     *
     * @param id the id of the key of obtain
     * @return a {@link Key} instance
     * @throws java.io.IOException when the connection to the client failed
     * @api.doc <a href="http://developer.github.com/v3/users/keys/">Get a single public key</a>
     */
    @Processor
    public Key getKey(int id) throws IOException
    {
        return getServiceFactory().getUserService().getKey(id);
    }

    /**
     * Create key for currently authenticated user
     * <p/>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:createKey}
     *
     * @param title the title of the new key
     * @param key   the new key
     * @return the created {@link Key}
     * @throws java.io.IOException when the connection to the client failed
     * @api.doc <a href="http://developer.github.com/v3/users/keys/">Create a public key</a>
     */
    @Processor
    public Key createKey(String title, String key) throws IOException
    {
        Key newKey = new Key();
        newKey.setTitle(title);
        newKey.setKey(key);

        return getServiceFactory().getUserService().createKey(newKey);
    }

    /**
     * Edit key for currently authenticated user
     * <p/>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:editKey}
     *
     * @param id    the id of the key to edit
     * @param title a new title for the key
     * @param key   a new key
     * @return edited {@link Key}
     * @throws java.io.IOException when the connection to the client failed
     * @api.doc <a href="http://developer.github.com/v3/users/keys/">Update a public key</a>
     */
    @Processor
    public Key editKey(int id, @Optional String title, @Optional String key) throws IOException
    {
        Key keyToEdit = getKey(id);

        if (title != null)
        {
            keyToEdit.setTitle(title);
        }

        if (key != null)
        {
            keyToEdit.setKey(key);
        }

        return getServiceFactory().getUserService().editKey(keyToEdit);
    }

    /**
     * Delete key with given id
     * <p/>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:deleteKey}
     *
     * @param id the id of the key to delete
     * @throws java.io.IOException when the connection to the client failed
     * @api.doc <a href="http://developer.github.com/v3/users/keys/">Delete a public key</a>
     */
    @Processor
    public void deleteKey(int id) throws IOException
    {
        getServiceFactory().getUserService().deleteKey(id);
    }

    /**
     * Get team with given id
     * <p/>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:getTeam}
     *
     * @param id    the id of the team
     * @return a {@link Team} instance
     * @throws java.io.IOException when the connection to the client failed
     * @api.doc <a href="http://developer.github.com/v3/orgs/teams/">Get team</a>
     */
    @Processor
    public Team getTeam(int id) throws IOException
    {
        return getServiceFactory().getTeamService().getTeam(id);
    }

    /**
     * Get all teams in the given organization
     * <p/>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:getTeams}
     *
     * @param organization the organization for which te get the teams associated with
     * @return list of {@link Team}
     * @throws java.io.IOException when the connection to the client failed
     * @api.doc <a href="http://developer.github.com/v3/orgs/teams/">List teams</a>
     */
    @Processor
    public List<Team> getTeams(String organization) throws IOException
    {
        return getServiceFactory().getTeamService().getTeams(organization);
    }

    /**
     * Create the given name
     * <p/>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:createTeam}
     *
     * @param organization   the organization name will belong to
     * @param team           the name of the team
     * @param permission     the name's {@link TeamPermission}
     * @param repositories   the associated repository names
     * @return the created {@link Team}
     * @throws java.io.IOException when the connection to the client failed
     * @api.doc <a href="http://developer.github.com/v3/orgs/teams/">Create name</a>
     */
    @Processor
    public Team createTeam(String organization, String team, @Optional @Default("PULL") TeamPermission permission, @Optional @Default("#[payload]") List<String> repositories) throws IOException
    {
        Team newTeam = new Team();
        newTeam.setName(team);
        newTeam.setPermission(permission.toString());

        if (repositories != null)
        {
            return getServiceFactory().getTeamService().createTeam(organization, newTeam, repositories);
        }
        else
        {
            return getServiceFactory().getTeamService().createTeam(organization, newTeam);
        }
    }

    /**
     * Edit the given team. Only provide values for the attributes you want to update.
     * <p/>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:editTeam}
     *
     * @param id             the id of the team to edit
     * @param team       the new name of the team
     * @param permission the new {@link TeamPermission} for the team
     * @return the edited {@link Team}
     * @throws java.io.IOException when the connection to the client failed
     * @api.doc <a href="http://developer.github.com/v3/orgs/teams/">Edit team</a>
     */
    @Processor
    public Team editTeam(int id, @Optional String team, @Optional TeamPermission permission) throws IOException
    {
        Team teamToEdit = getTeam(id);

        if (team != null)
        {
            teamToEdit.setName(team);
        }

        if (permission != null)
        {
            teamToEdit.setPermission(permission.toString());
        }

        return getServiceFactory().getTeamService().editTeam(teamToEdit);
    }

    /**
     * Delete the team with the given id
     * <p/>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:deleteTeam}
     *
     * @param id the id of the team to delete.
     * @throws java.io.IOException when the connection to the client failed
     * @api.doc <a href="http://developer.github.com/v3/orgs/teams/">Delete team</a>
     */
    @Processor
    public void deleteTeam(int id) throws IOException
    {
        getServiceFactory().getTeamService().deleteTeam(id);
    }

    /**
     * Get members of team with given id
     * <p/>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:getTeamMembers}
     *
     * @param id the team id
     * @return team members
     * @throws java.io.IOException when the connection to the client failed
     * @api.doc <a href="http://developer.github.com/v3/orgs/teams/">List members</a>
     */
    @Processor
    public List<User> getTeamMembers(int id) throws IOException
    {
        return getServiceFactory().getTeamService().getMembers(id);
    }

    /**
     * Is the given user a member of the team with the given id
     * <p/>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:isTeamMember}
     *
     * @param id   the team id
     * @param user the user name
     * @return true if member, false if not member
     * @throws java.io.IOException when the connection to the client failed
     * @api.doc <a href="http://developer.github.com/v3/orgs/teams/">Get if a user is a public member</a>
     */
    @Processor
    public boolean isTeamMember(int id, String user) throws IOException
    {
        return getServiceFactory().getTeamService().isMember(id, user);
    }

    /**
     * Add given user to team with given id
     * <p/>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:addTeamMember}
     *
     * @param id   the team id
     * @param user the user name
     * @throws java.io.IOException when the connection to the client failed
     * @api.doc <a href="http://developer.github.com/v3/orgs/teams/">Add a member</a>
     */
    @Processor
    public void addTeamMember(int id, String user) throws IOException
    {
        getServiceFactory().getTeamService().addMember(id, user);
    }

    /**
     * Remove given user from team with given id
     * <p/>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:removeTeamMember}
     *
     * @param id   the team id
     * @param user the user name
     * @throws java.io.IOException when the connection to the client failed
     * @api.doc <a href="http://developer.github.com/v3/orgs/teams/">Remove a member</a>
     */
    @Processor
    public void removeTeamMember(int id, String user) throws IOException
    {
        getServiceFactory().getTeamService().removeMember(id, user);
    }

    /**
     * Get all repositories for given team
     * <p/>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:getTeamRepositories}
     *
     * @param id the team id
     * @return non-null list of repositories
     * @throws java.io.IOException when the connection to the client failed
     * @api.doc <a href="http://developer.github.com/v3/orgs/teams/">List team repos</a>
     */
    @Processor
    public List<Repository> getTeamRepositories(int id) throws IOException
    {
        return getServiceFactory().getTeamService().getRepositories(id);
    }

    /**
     * Add repository to team
     * <p/>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:addTeamRepository}
     *
     * @param id             the team id
     * @param owner          the owner of the repository
     * @param repository     the name of the repository
     * @throws java.io.IOException when the connection to the client failed
     * @api.doc <a href="http://developer.github.com/v3/orgs/teams/">Add team repo</a>
     */
    @Processor
    public void addTeamRepository(int id, String owner, String repository) throws IOException
    {
        getServiceFactory().getTeamService().addRepository(id, RepositoryId.create(owner, repository));
    }

    /**
     * Remove repository from team
     * <p/>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:removeTeamRepository}
     *
     * @param id             the team id
     * @param owner          the owner of the repository
     * @param repository     the name of the repository
     * @throws java.io.IOException when the connection to the client failed
     * @api.doc <a href="http://developer.github.com/v3/orgs/teams/">Remove team repo</a>
     */
    @Processor
    public void removeTeamRepository(int id, String owner, String repository) throws IOException
    {
        getServiceFactory().getTeamService().removeRepository(id, RepositoryId.create(owner, repository));
    }

    /**
     * Get repositories for the currently authenticated user
     * <p/>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:getRepositories}
     *
     * @param filterData data to filter repos, if non is specified all repos will be returned
     * @return list of {@link Repository}
     * @throws java.io.IOException when the connection to the client failed
     * @api.doc <a href="http://developer.github.com/v3/repos"></a>
     */
    @Processor
    public List<Repository> getRepositories(@Optional @Default("#[payload]") Map<String, String> filterData) throws IOException
    {
        return getServiceFactory().getRepositoryService().getRepositories(filterData);
    }

    /**
     * Get repositories for the given user
     * <p/>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:getRepositoriesForUser}
     *
     * @param user the user for which to get the repositories for
     * @return list of {@link Repository}
     * @throws java.io.IOException when the connection to the client failed
     * @api.doc <a href="http://developer.github.com/v3/repos"></a>
     */
    @Processor
    public List<Repository> getRepositoriesForUser(String user) throws IOException
    {
        return getServiceFactory().getRepositoryService().getRepositories(user);
    }

    /**
     * Get organization repositories for the given organization
     * <p/>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:getRepositoriesForOrg}
     *
     * @param organization the organization for which to get the repositories for
     * @param filterData   data to filter repositories. If none is specified all repositories will be returned
     * @return list of {@link Repository}
     * @throws java.io.IOException when the connection to the client failed
     * @api.doc <a href="http://developer.github.com/v3/repos"></a>
     */
    @Processor
    public List<Repository> getRepositoriesForOrg(String organization, @Optional @Default("#[payload]") Map<String, String> filterData) throws IOException
    {
        return getServiceFactory().getRepositoryService().getOrgRepositories(organization, filterData);
    }

    /**
     * Create a new repository
     * <p/>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:createRepository}
     *
     * @param repository     the name of the new repository
     * @param description    a description for the new repository
     * @param isPrivate      true to create a private repository, false to create a public one. Creating private repositories requires a paid GitHub account. Default is false.
     * @param hasIssues      true to enable issues for this repository, false to disable them. Default is true.
     * @param hasWiki        true to enable the wiki for this repository, false to disable it. Default is true.
     * @param hasDownloads   true to enable downloads for this repository, false to disable them. Default is true.
     * @return the created {@link Repository}
     * @throws java.io.IOException when the connection to the client failed
     * @api.doc <a href="http://developer.github.com/v3/repos">Create</a>
     */
    @Processor
    public Repository createRepository(String repository, @Optional String description, @Optional @Default("false") boolean isPrivate, @Optional @Default("true") boolean hasIssues, @Optional @Default("true") boolean hasWiki, @Optional @Default("true") boolean hasDownloads) throws IOException
    {
        Repository repo = new Repository();
        repo.setName(repository);
        repo.setDescription(description);
        repo.setPrivate(isPrivate);
        repo.setHasIssues(hasIssues);
        repo.setHasWiki(hasWiki);
        repo.setHasDownloads(hasDownloads);

        return getServiceFactory().getRepositoryService().createRepository(repo);
    }

    /**
     * Create a new repository for the given organization
     * <p/>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:createRepositoryForOrg}
     *
     * @param organization   the organization for the new repository
     * @param repository     the name of the new repository
     * @param description    a description for the new repository
     * @param isPrivate      true to create a private repository, false to create a public one. Creating private repositories requires a paid GitHub account. Default is false.
     * @param hasIssues      true to enable issues for this repository, false to disable them. Default is true.
     * @param hasWiki        true to enable the wiki for this repository, false to disable it. Default is true.
     * @param hasDownloads   true to enable downloads for this repository, false to disable them. Default is true.
     * @return the created {@link Repository}
     * @throws java.io.IOException when the connection to the client failed
     * @api.doc <a href="http://developer.github.com/v3/repos">Create</a>
     */
    @Processor
    public Repository createRepositoryForOrg(String organization, String repository, @Optional String description, @Optional @Default("false") boolean isPrivate, @Optional @Default("true") boolean hasIssues, @Optional @Default("true") boolean hasWiki, @Optional @Default("true") boolean hasDownloads) throws IOException
    {
        Repository repo = new Repository();
        repo.setName(repository);
        repo.setDescription(description);
        repo.setPrivate(isPrivate);
        repo.setHasIssues(hasIssues);
        repo.setHasWiki(hasWiki);
        repo.setHasDownloads(hasDownloads);

        return getServiceFactory().getRepositoryService().createRepository(organization, repo);
    }

    /**
     * Get repository
     * <p/>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:getRepository}
     *
     * @param owner          the name of the user that owns the repository
     * @param repository the name of the repository
     * @return repository
     * @throws java.io.IOException when the connection to the client failed
     * @api.doc <a href="http://developer.github.com/v3/repos"></a>
     */
    @Processor
    public Repository getRepository(String owner, String repository) throws IOException
    {
        return getServiceFactory().getRepositoryService().getRepository(owner, repository);
    }

    /**
     * Edit given repository. Only provide values for the attributes you need to change.
     * <p/>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:editRepository}
     *
     * @param owner          the owner of the repository
     * @param repository     the name of the repository
     * @param description    a new description for the repository
     * @param isPrivate      true to turn it into a private repository, false to make it public. Private repositories require a paid GitHub account.
     * @param hasIssues      true to enable issues for this repository, false to disable them.
     * @param hasWiki        true to enable the wiki for this repository, false to disable it.
     * @param hasDownloads   true to enable downloads for this repository, false to disable them.
     * @return the edited {@link Repository}
     * @throws java.io.IOException when the connection to the client failed
     * @api.doc <a href="http://developer.github.com/v3/repos"></a>
     */
    @Processor
    public Repository editRepository(String owner, String repository, @Optional String description, @Optional Boolean isPrivate, @Optional Boolean hasIssues, @Optional Boolean hasWiki, @Optional Boolean hasDownloads) throws IOException
    {
        Repository repositoryToEdit = getRepository(owner, repository);

        if (description != null)
        {
            repositoryToEdit.setDescription(description);
        }

        if (isPrivate != null)
        {
            repositoryToEdit.setPrivate(isPrivate);
        }

        if (hasIssues != null)
        {
            repositoryToEdit.setHasIssues(hasIssues);
        }

        if (hasWiki != null)
        {
            repositoryToEdit.setHasWiki(hasWiki);
        }

        if (hasDownloads != null)
        {
            repositoryToEdit.setHasDownloads(hasDownloads);
        }

        return getServiceFactory().getRepositoryService().editRepository(repositoryToEdit);
    }

    /**
     * Get all the forks of the given repository
     * <p/>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:getForks}
     *
     * @param owner          the owner of the repository
     * @param repository     the name of the repository
     * @return non-null but possibly empty list of repository
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public List<Repository> getForks(String owner, String repository) throws IOException
    {
        return getServiceFactory().getRepositoryService().getForks(RepositoryId.create(owner, repository));
    }

    /**
     * Fork given repository into new repository under the currently
     * authenticated user.
     * <p/>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:forkRepository}
     *
     * @param owner          the owner of the repository
     * @param repository     the name of the repository
     * @return forked repository
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public Repository forkRepository(String owner, String repository) throws IOException
    {
        return getServiceFactory().getRepositoryService().forkRepository(RepositoryId.create(owner, repository));
    }

    /**
     * Fork given repository into new repository.
     * <p/>
     * The new repository will be under the given organization if non-null, else
     * it will be under the currently authenticated user.
     * <p/>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:forkRepositoryForOrg}
     *
     * @param organization   the organization where the new repository will be
     * @param owner          the owner of the repository
     * @param repository     the name of the repository
     * @return forked repository
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public Repository forkRepositoryForOrg(String organization, String owner, String repository) throws IOException
    {
        return getServiceFactory().getRepositoryService().forkRepository(RepositoryId.create(owner, repository), organization);
    }

    /**
     * Get languages used in given repository
     * <p/>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:getLanguages}
     *
     * @param owner          the owner of the repository
     * @param repository     the name of the repository
     * @return map of language names mapped to line counts
     * @throws java.io.IOException when the connection to the client failed\
     * @api.doc <a href="http://developer.github.com/v3/repos"></a>
     */
    @Processor
    public Map<String, Long> getLanguages(String owner, String repository) throws IOException
    {
        return getServiceFactory().getRepositoryService().getLanguages(RepositoryId.create(owner, repository));
    }

    /**
     * Get branches in given repository
     * <p/>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:getBranches}
     *
     * @param owner          the owner of the repository
     * @param repository     the name of the repository
     * @return list of branches
     * @throws java.io.IOException when the connection to the client failed
     * @api.doc <a href="http://developer.github.com/v3/repos"></a>
     */
    @Processor
    public List<RepositoryBranch> getBranches(String owner, String repository) throws IOException
    {
        return getServiceFactory().getRepositoryService().getBranches(RepositoryId.create(owner, repository));
    }

    /**
     * Get tags in given repository
     * <p/>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:getTags}
     *
     * @param owner          the owner of the repository
     * @param repository     the name of the repository
     * @return list of tags
     * @throws java.io.IOException when the connection to the client failed
     * @api.doc <a href="http://developer.github.com/v3/repos"></a>
     */
    @Processor
    public List<RepositoryTag> getTags(String owner, String repository) throws IOException
    {
        return getServiceFactory().getRepositoryService().getTags(RepositoryId.create(owner, repository));
    }

    /**
     * Get contributors to repository
     * <p/>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:getContributors}
     *
     * @param owner            the owner of the repository
     * @param repository       the name of the repository
     * @param includeAnonymous whether to include anonymus contributors
     * @return list of contributors
     * @throws java.io.IOException when the connection to the client failed
     * @api.doc <a href="http://developer.github.com/v3/repos"></a>
     */
    @Processor
    public List<Contributor> getContributors(String owner, String repository, @Optional @Default("false") boolean includeAnonymous) throws IOException
    {
        return getServiceFactory().getRepositoryService().getContributors(RepositoryId.create(owner, repository), includeAnonymous);
    }

    /**
     * Get list of downloads for a repository
     * <p/>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:getDownloads}
     *
     * @param owner          the owner of the repository
     * @param repository     the name of the repository
     * @return list of downloads
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public List<Download> getDownloads(String owner, String repository) throws IOException
    {
        return getServiceFactory().getDownloadService().getDownloads(RepositoryId.create(owner, repository));
    }

    /**
     * Get Tree Recursively
     * <p/>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:getTreeRecursively}
     *
     * @param owner          the owner of the repository
     * @param repository     the name of the repository
     * @param sha            a sha to start listing the structure
     * @return a tree structure of the repository
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public Tree getTreeRecursively(String owner, String repository, String sha) throws IOException
    {
        return getServiceFactory().getDataService().getTree(RepositoryId.create(owner, repository), sha, true);
    }

    /**
     * Returns a utf-8 String representing the contents of the file
     * </p>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:getFileContent}
     *
     * @param owner          the owner of the repository, leave empty to use {@link ServiceFactory#user}
     * @param repository     the name of the repository
     * @param path           the path of the file to get
     * @param branch         default to master
     * @return s the content of the file
     * @throws java.io.IOException  when the connection to the client failed
     * @throws TransformerException when the file's contents couldn't be transformeds
     */
    @Processor
    public String getFileContent(String owner, String repository, String path, @Optional @Default("master") String branch) throws IOException, TransformerException
    {
        List<RepositoryContents> contents = getServiceFactory().getContentsService().getContents(RepositoryId.create(owner, repository), path, branch);

        if (CollectionUtils.isEmpty(contents))
        {
            throw new IOException("File not found");
        }

        if (contents.size() > 1)
        {
            throw new IllegalStateException("Retrieved RepositoryContent is not a file");
        }

        RepositoryContents fileContent = contents.get(0);
        final String encondedContent = fileContent.getContent();
        final byte[] content = (byte[]) new Base64Decoder().doTransform(encondedContent, fileContent.getEncoding() != null ? fileContent.getEncoding() : "utf-8");
        return new String(content);
    }

    /**
     * Get repository contents for given path and branch
     * </p>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:getContents}
     *
     * @param owner          the owner of the repository
     * @param repository     the name of the repository
     * @param path           file or directory path
     * @param branch         branch name, defaults fo master
     * @return repository contents for given path
     * @throws IOException in case of connectivity issue
     */
    @Processor
    public List<RepositoryContents> getContents(String owner, String repository, String path, @Optional @Default("master") String branch) throws IOException
    {
        return getServiceFactory().getContentsService().getContents(RepositoryId.create(owner, repository), path, branch);
    }

    /**
     * Commit contents to the repository
     * </p>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:putContents}
     *
     * @param owner          the owner of the repository
     * @param repository     the name of the repository
     * @param path           file or directory path
     * @param message        commit message
     * @param content        content
     * @param sha            blob sha of contents that will be replaced
     * @param branch         branch name, defaults fo master
     * @throws IOException in case of connectivity issue
     */
    @Processor
    public void putContents(String owner, String repository, String path, String message, String content, String sha, String branch) throws IOException
    {
        RepositoryContents newContent = new RepositoryContents();
        newContent.setPath(path);
        newContent.setContent(content);
        newContent.setSha(sha);
        getServiceFactory().getContentsService().putContents(RepositoryId.create(owner, repository), newContent, message, branch);
    }

    /**
     * Delete a repository
     * Added solely for functional testing of the connector. Not exposed as processor.
     *
     * @param owner          the owner of the repository
     * @param repository     the name of the repository
     * @throws IOException in case of connectivity issues or if repository does not exists
     */
    public void deleteRepository(String owner, String repository) throws IOException
    {
        getServiceFactory().getRepositoryService().deleteRepository(RepositoryId.create(owner, repository));
    }

    /**
     * Returns pull request by ID
     * </p>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:getPullRequest}
     *
     * @param owner          the owner of the repository
     * @param repository     the name of the repository
     * @param id             the id of pull request
     * @return pull request
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public PullRequest getPullRequest(String owner, String repository, int id) throws IOException
    {
        return getServiceFactory().getPullRequestService().getPullRequest(RepositoryId.create(owner, repository), id);
    }


    /**
     * Get pull requests from repository matching state
     * </p>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:getPullRequests}
     *
     * @param owner          the owner of the repository
     * @param repository     the name of the repository
     * @param state          the state of pull request
     * @return list of pull requests
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public List<PullRequest> getPullRequests(String owner, String repository, String state) throws IOException
    {
        return getServiceFactory().getPullRequestService().getPullRequests(RepositoryId.create(owner, repository), state);
    }

    /**
     * Page pull requests with given state, offset and page size
     * </p>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:pagePullRequests}
     *
     * @param owner          the owner of the repository
     * @param repository     the name of the repository
     * @param state          the state of pull request
     * @param start          the number of first page. Default is <code>PagedRequest.PAGE_FIRST</code>
     * @param size           the page size. Default is <code>PagedRequest.PAGE_SIZE</code>
     * @return list of pull requests
     * @throws java.io.IOException when the connection to the client failed
     */
    @Processor
    public PageIterator<PullRequest> pagePullRequests(String owner, String repository, String state, @Optional Integer start, @Optional Integer size) throws IOException
    {
        if (start == null)
        {
            start = PagedRequest.PAGE_FIRST;
        }

        if (size == null)
        {
            size = PagedRequest.PAGE_SIZE;
        }

        return getServiceFactory().getPullRequestService().pagePullRequests(RepositoryId.create(owner, repository), state, start, size);
    }


    /**
     * Create pull request
     * </p>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:createPullRequest}
     *
     * @param owner          the owner of the repository
     * @param repository     the name of the repository
     * @param body           the body of pull request
     * @param title          the title of pull request
     * @param head           branch name
     * @param base           base name
     * @return created pull request
     * @throws IOException when the connection to the client failed
     */
    @Processor
    public PullRequest createPullRequest(String owner, String repository, @Optional String body, String title, String head, String base) throws IOException
    {
        PullRequest pullRequest = new PullRequest();
        pullRequest.setBody(body);
        pullRequest.setTitle(title);
        pullRequest.setHead(new PullRequestMarker().setLabel(head));
        pullRequest.setBase(new PullRequestMarker().setLabel(base));
        return getServiceFactory().getPullRequestService().createPullRequest(RepositoryId.create(owner, repository), pullRequest);
    }

    /**
     * Create pull request from issue
     * </p>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:createPullRequestFromIssue}
     *
     * @param owner          the owner of the repository
     * @param repository     the name of the repository
     * @param issueNumber    the number of issue
     * @param head           reference to head
     * @param base           reference to base
     * @return created pull request
     * @throws IOException when the connection to the client failed
     */
    @Processor
    public PullRequest createPullRequestFromIssue(String owner, String repository, int issueNumber, String head, String base) throws IOException
    {
        return getServiceFactory().getPullRequestService().createPullRequest(RepositoryId.create(owner, repository), issueNumber, head, base);

    }

    /**
     * Update pull request
     * </p>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:editPullRequest}
     *
     * @param owner          the owner of the repository
     * @param repository     the name of the repository
     * @param id             the id of the pull request
     * @param title          the title of the pull request
     * @param body           the body of the pull request
     * @param state          state of the pull request. Valid values are "open" and "closed"
     * @return updated pull request
     * @throws IOException if pull request was not found by id or if connection to the client failed
     */
    @Processor
    public PullRequest editPullRequest(String owner, String repository, int id, @Optional String title, @Optional String body, @Optional String state) throws IOException
    {
        PullRequest pullRequest = getPullRequest(owner, repository, id);

        if (title != null)
        {
            pullRequest.setTitle(title);
        }

        if (body != null)
        {
            pullRequest.setBody(body);
        }

        if (state != null)
        {
            pullRequest.setState(state);
        }

        return getServiceFactory().getPullRequestService().editPullRequest(RepositoryId.create(owner, repository), pullRequest);
    }

    /**
     * Get all commits associated with given pull request id
     * </p>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:getPullRequestCommits}
     *
     * @param owner          the owner of the repository
     * @param repository     the name of the repository
     * @param id             the id of pull request
     * @return list of commits
     * @throws IOException when the connection to the client failed
     */
    @Processor
    public List<RepositoryCommit> getPullRequestCommits(String owner, String repository, int id) throws IOException
    {
        return getServiceFactory().getPullRequestService().getCommits(RepositoryId.create(owner, repository), id);
    }

    /**
     * Get all files associated with given pull request id
     * </p>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:getPullRequestFiles}
     *
     * @param owner          the owner of the repository
     * @param repository     the name of the repository
     * @param id             the id of pull request
     * @return list of files
     * @throws IOException when the connection to the client failed
     */
    @Processor
    public List<CommitFile> getPullRequestFiles(String owner, String repository, int id) throws IOException
    {
        return getServiceFactory().getPullRequestService().getFiles(RepositoryId.create(owner, repository), id);
    }


    /**
     * Get if a pull request has been merged
     * </p>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:isPullRequestMerged}
     *
     * @param owner          the owner of the repository
     * @param repository     the name of the repository
     * @param id             the id of pull request
     * @return rue if merged, false otherwise
     * @throws IOException when the connection to the client failed
     */
    @Processor
    public boolean isPullRequestMerged(String owner, String repository, int id) throws IOException
    {
        return getServiceFactory().getPullRequestService().isMerged(RepositoryId.create(owner, repository), id);
    }

    /**
     * Merge given pull request
     * </p>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:mergePullRequest}
     *
     * @param owner          the owner of the repository
     * @param repository     the name of the repository
     * @param id             the id of pull request
     * @param commitMessage  commit message
     * @return status of merge
     * @throws IOException when the connection to the client failed
     */
    @Processor
    public MergeStatus mergePullRequest(String owner, String repository, int id, String commitMessage) throws IOException
    {
        return getServiceFactory().getPullRequestService().merge(RepositoryId.create(owner, repository), id, commitMessage);
    }


    /**
     * Create comment on given pull request
     * </p>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:createPullRequestComment}
     *
     * @param owner          the owner of the repository
     * @param repository     the name of the repository
     * @param id             the id of pull request
     * @param commitId       the id of commit
     * @param body           the body of comment
     * @param path           Relative path of the file to comment on.
     * @param position       Line index in the diff to comment on.
     * @param line           Line number in the file to comment on.
     * @return created commit comment
     * @throws IOException when the connection to the client failed
     */
    @Processor
    public CommitComment createPullRequestComment(String owner, String repository, int id, String commitId, String body, String path, int position, int line) throws IOException
    {

        CommitComment commitComment = new CommitComment();
        commitComment.setBody(body);
        commitComment.setCommitId(commitId);
        commitComment.setPath(path);
        commitComment.setPosition(position);
        commitComment.setLine(line);

        return getServiceFactory().getPullRequestService().createComment(RepositoryId.create(owner, repository), id, commitComment);
    }

    /**
     * Reply to given comment
     * </p>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:replyToPullRequestComment}
     *
     * @param owner          the owner of the repository
     * @param repository     the name of the repository
     * @param id             the id of pull request
     * @param commentId      the id of the comment
     * @param body           the body of response
     * @return created comment
     * @throws IOException when the connection to the client failed
     */
    @Processor
    public CommitComment replyToPullRequestComment(String owner, String repository, int id, int commentId, String body) throws IOException
    {
        return getServiceFactory().getPullRequestService().replyToComment(RepositoryId.create(owner, repository), id, commentId, body);
    }

    /**
     * Edit pull request comment
     * </p>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:editPullRequestComment}
     *
     * @param owner          the owner of the repository
     * @param repository     the name of the repository
     * @param commentId      id of edited comment
     * @param body           new text for comment
     * @return created commit comment
     * @throws IOException when the connection to the client failed
     */
    @Processor
    public CommitComment editPullRequestComment(String owner, String repository, long commentId, String body) throws IOException
    {

        IRepositoryIdProvider repo = RepositoryId.create(owner, repository);
        CommitComment comment = getServiceFactory().getPullRequestService().getComment(repo, commentId);
        comment.setBody(body);

        return getServiceFactory().getPullRequestService().editComment(repo, comment);
    }

    /**
     * Delete pull request commit comment with given id
     * </p>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:deletePullRequestComment}
     *
     * @param owner          the owner of the repository
     * @param repository     the name of the repository
     * @param commentId      id of deleted comment
     * @throws IOException when the connection to the client failed
     */
    @Processor
    public void deletePullRequestComment(String owner, String repository, long commentId) throws IOException
    {
        getServiceFactory().getPullRequestService().deleteComment(RepositoryId.create(owner, repository), commentId);
    }

    /**
     * Get commit comment with given id
     * </p>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:getPullRequestComment}
     *
     * @param owner          the owner of the repository
     * @param repository     the name of the repository
     * @param commentId      id of comment
     * @return created commit comment
     * @throws IOException when the connection to the client failed
     */
    @Processor
    public CommitComment getPullRequestComment(String owner, String repository, long commentId) throws IOException
    {
        return getServiceFactory().getPullRequestService().getComment(RepositoryId.create(owner, repository), commentId);
    }

    /**
     * Get all comments on commits in given pull request
     * </p>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:getPullRequestComments}
     *
     * @param owner          the owner of the repository
     * @param repository     the name of the repository
     * @param id             the id of pull request
     * @return list of comments for given pull request
     * @throws IOException when the connection to the client failed
     */
    @Processor
    public List<CommitComment> getPullRequestComments(String owner, String repository, int id) throws IOException
    {
        return getServiceFactory().getPullRequestService().getComments(RepositoryId.create(owner, repository), id);
    }


    /**
     * Page pull request commit comments with given offset and page size
     * </p>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:pagePullRequestComments}
     *
     * @param owner          the owner of the repository
     * @param repository     the name of the repository
     * @param id             the id of pull request
     * @param start          the number of first page. Default is <code>PagedRequest.PAGE_FIRST</code>
     * @param size           the page size. Default is <code>PagedRequest.PAGE_SIZE</code>
     * @return list of comments for given pull request
     * @throws IOException when the connection to the client failed
     */
    @Processor
    public PageIterator<CommitComment> pagePullRequestComments(String owner, String repository, int id, @Optional Integer start, @Optional Integer size) throws IOException
    {
        if (start == null)
        {
            start = PagedRequest.PAGE_FIRST;
        }

        if (size == null)
        {
            size = PagedRequest.PAGE_SIZE;
        }

        return getServiceFactory().getPullRequestService().pageComments(RepositoryId.create(owner, repository), id, start, size);
    }

    /**
     * Get all references for given repository
     * </p>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:getReferences}
     *
     * @param owner          the owner of the repository
     * @param repository     the name of the repository
     * @return               list of references for given pull request
     * @throws IOException when the connection to the client failed
     */
    @Processor
    public List<Reference> getReferences(String owner, String repository) throws IOException
    {
        return getServiceFactory().getDataService().getReferences(RepositoryId.create(owner, repository));
    }

    /**
     * Create reference with given name in given repository
     * </p>
     * {@sample.xml ../../../doc/GitHub-connector.xml.sample github:createReference}
     *
     * @param owner          the owner of the repository
     * @param repository     the name of the repository
     * @param sha            the sha of reference base
     * @param referenceName  reference name
     * @return               new reference
     * @throws IOException when the connection to the client failed
     */
    @Processor
    public Reference createReference(String owner, String repository, String sha, String referenceName) throws IOException
    {

        Reference reference = new Reference();
        reference.setObject(new TypedResource());
        reference.getObject().setSha(sha);
        reference.setRef(referenceName);
        return getServiceFactory().getDataService().createReference(RepositoryId.create(owner, repository), reference);
    }


    private String getUser(String user)
    {
        return user != null ? user : serviceFactory.getUser();
    }

    public ServiceFactory getServiceFactory() throws IOException
    {
        return serviceFactory;
    }

    public void setServiceFactory(ServiceFactory serviceFactory)
    {
        this.serviceFactory = serviceFactory;
    }

    /**
     * Creates a connection to GitHub by making a login call with the given credentials to the specified address.
     * The login call, if successfull, returns a token which will be used in the subsequent calls to Jira.
     *
     * @param connectionUser     the user login user
     * @param connectionPassword the user login pass
     * @param scope              the repository to connect
     * @throws ConnectionException in case of connectivity issues
     */
    @Connect
    public void connect(@ConnectionKey String connectionUser, @Password String connectionPassword, @Optional @Default("repo") String scope) throws ConnectionException
    {
        try
        {
            setServiceFactory(new ServiceFactory(connectionUser, connectionPassword, scope));
        }
        catch (IOException e)
        {
            throw new ConnectionException(ConnectionExceptionCode.UNKNOWN, null, e.getMessage(), e);
        }
    }

    @Disconnect
    public void disconnect()
    {
        setServiceFactory(null);
    }

    /**
     * Return serviceFactory was set or not
     */
    @ValidateConnection
    public boolean validateConnection()
    {
        return this.serviceFactory != null;
    }

    /**
     * Returns the connection identifier
     */
    @Override
    @ConnectionIdentifier
    public String toString()
    {
        return serviceFactory.getUser();
    }


}
