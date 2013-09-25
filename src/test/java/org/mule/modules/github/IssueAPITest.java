package org.mule.modules.github;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.egit.github.core.Comment;
import org.eclipse.egit.github.core.Issue;
import org.junit.Test;

/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

public class IssueAPITest extends BaseAPITest {

	private Map<String, Object> params = new HashMap<String, Object>();

	public IssueAPITest() throws Exception {
		github.setServiceFactory(new ServiceFactory(USER, PASS, SCOPE));

		params.put("title", "Issue Title");
		params.put("body", "Issue body");
		params.put("assignee", "federecio");
	}
	
	@Test
	public void testIssueAPI()
	       throws Exception
	{           	
	    Issue issue = runMuleFlow("createIssue", Issue.class, params);
	    assertNotNull(issue);
	    int issueNumber = issue.getNumber();
	            
	    params.put("key", "open");
	         
	    @SuppressWarnings("unchecked")
	    List<Issue> issues = runMuleFlow("getIssues", List.class, params);
	    boolean found = false;
	    for (Issue i: issues)
	    	if (i.getNumber()==issueNumber)
	    		found = true;

	    assertTrue(found);
	            
	    params.put("issueId", issueNumber);
	    issue = runMuleFlow("closeIssue", Issue.class, params);
	    assertEquals("closed",issue.getState());
	            
	    params.put("fromIssueNumber", "1");
	            
        @SuppressWarnings("unchecked")
	    List<Issue> certainIssues = runMuleFlow("getIssuesSinceNumber", List.class, params);        
	    found = false;
	    if (certainIssues.size() > 0)
	    	found = true;
	            
	    assertTrue(found);                
	            
	    params.put("minutes", "30000");
	    
	    issue = runMuleFlow("createIssue", Issue.class, params);
	    issueNumber = issue.getNumber();
	    
	    @SuppressWarnings("unchecked")
	    List<Issue> afterIssues = runMuleFlow("getIssuesCretedAfter", List.class, params);
	    found = false;        
	    for (Issue i: afterIssues)
	    	if (i.getNumber()==issueNumber) {
	    		found = true;
	            break;
	    	}

	    assertTrue(found);
	    
	    issue = runMuleFlow("getIssue", Issue.class, params);
	    assertEquals("Issue Title",issue.getTitle());
	    assertEquals("Issue body",issue.getBody());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testCommentAPI()
		       throws Exception
	{
		Issue issue = runMuleFlow("createIssue", Issue.class, params);
		
		int issueNumber = issue.getNumber();
		
		params.put("issueId", String.valueOf(issueNumber));
		params.put("comment", "Comment");
		
		Comment comment = runMuleFlow("createComment", Comment.class, params);
		assertNotNull(comment);
		
		long commentId = comment.getId();
		
		List<Comment> comments = runMuleFlow("getComments", List.class, params);
		boolean found = false;
	    for (Comment c: comments)
	    	if (c.getId()==commentId)
	    		found = true;

	    assertTrue(found);
	    
	    params.put("commentId", commentId);
		params.put("commentBody", "Comment Body");
	    
	    comment = runMuleFlow("editComment", Comment.class, params);
	    
	    assertEquals("Comment Body",comment.getBody());
	    
	    comment = runMuleFlow("deleteComment", null, params);
	    
	    comments = runMuleFlow("getComments", List.class, params);
	    found = false;
	    for (Comment c: comments)
	    	if (c.getId()==commentId)
	    		found = true;
	    
	    assertFalse(found);
	}
}