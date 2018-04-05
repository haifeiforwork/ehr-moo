/**
 * LG CNS IKEP4 
*/
package com.lgcns.ikep4.support.searchpreprocessor.model;

import java.util.ArrayList;
import java.util.List;

import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.support.user.member.model.User;


public class Result extends SpBaseObject {
	/**
	 *
	 */
	private static final long serialVersionUID = -7650149719420561520L;
	Integer totalCount;
	Integer endIndex;
	boolean next;
	String searchOption;
	
	
	List<History> historyList = new ArrayList<History>();
	List<User> userList = new ArrayList<User>();
	List<Tag> tagList = new ArrayList<Tag>();

	public Integer getTotalCount() {
		return totalCount;
	}

	public List<History> getHistoryList() {
		return historyList;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public void setHistoryList(List<History> historyList) {
		this.historyList = historyList;
	}

	public Integer getEndIndex() {
		return endIndex;
	}

	public void setEndIndex(Integer endIndex) {
		this.endIndex = endIndex;
	}

	public boolean getNext() {
		return endIndex < totalCount;
	}

	public String getSearchOption() {
		return searchOption;
	}

	public void setSearchOption(String searchOption) {
		this.searchOption = searchOption;
	}

	public List<User> getUserList() {
		return userList;
	}

	public List<Tag> getTagList() {
		return tagList;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public void setTagList(List<Tag> tagList) {
		this.tagList = tagList;
	}
	
	
}