package com.lgcns.ikep4.support.poll.search;

import org.hibernate.validator.constraints.NotEmpty;

import com.lgcns.ikep4.framework.web.SearchCondition;
import com.lgcns.ikep4.support.poll.constants.PollConstants;

public class PollReceiverSearchCondition extends SearchCondition {
	private static final long serialVersionUID = -6809397882380002043L;

	@Override
	protected Integer getDefaultPagePerRecord() {
		return PollConstants.POLL_PAGE_PER_RECORD;
	}

	/**
	 * 투표진행상태
	 */	
	private String isComplete;
	
	/**
	 * 관리자여부
	 */		
	private String isAdmin;
	
	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	private String searchColumn;
	
	private String searchWord;	
	
	private String isRegister;

	/**
	 * 참여여부
	 */
	private String isResultExists;		

	/**
	 * 답변자 ID
	 */
	@NotEmpty
	private String answerUserId;
	
	/**
	 * 포탈ID
	 */	
	private String portalId;
	
	private String groupId;
	
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getIsComplete() {
		return isComplete;
	}

	public void setIsComplete(String isComplete) {
		this.isComplete = isComplete;
	}
	
	public String getAnswerUserId() {
		return answerUserId;
	}

	public void setAnswerUserId(String answerUserId) {
		this.answerUserId = answerUserId;
	}	
	
	public String getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}	

	public String getIsResultExists() {
		return isResultExists;
	}

	public void setIsResultExists(String isResultExists) {
		this.isResultExists = isResultExists;
	}
	
	public String getSearchColumn() {
		return searchColumn;
	}

	public void setSearchColumn(String searchColumn) {
		this.searchColumn = searchColumn;
	}

	public String getSearchWord() {
		return searchWord;
	}

	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}	
		
	public String getIsRegister() {
		return isRegister;
	}

	public void setIsRegister(String isRegister) {
		this.isRegister = isRegister;
	}	
}
