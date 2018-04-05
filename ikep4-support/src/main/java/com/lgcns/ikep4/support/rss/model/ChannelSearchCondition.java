package com.lgcns.ikep4.support.rss.model;

import com.lgcns.ikep4.framework.web.SearchCondition;


public class ChannelSearchCondition extends SearchCondition {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 채널 Id
	 */
	private String channelId;
	
	/**
	 * 채널 Id
	 */
	private String notInChannelId;
	
	/**
	 * 등록자
	 */
	private String ownerId;
	
	/**
	 * 검색컬럼
	 */
	private String searchColumn;
	
	/**
	 * 검색어
	 */
	private String searchWord;
	
	/**
	 * 뷰모드
	 */
	private String viewMode;
	
	/**
	 * 레이아웃 
	 */
	private String layoutType;
	
	/**
	 * RSS 카테고리 ID
	 */
	private String categoryId;
	
	/**
	 * 초기화여부
	 */
	private boolean init = Boolean.TRUE;

	@Override 
	protected Integer getDefaultPagePerRecord() {
		return 10;
	}
	
	public boolean isInit() {
		return init;
	}

	public void setInit(boolean init) {
		this.init = init;
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

	public String getViewMode() {
		return viewMode;
	}

	public void setViewMode(String viewMode) {
		this.viewMode = viewMode;
	}

	public String getLayoutType() {
		return layoutType;
	}

	public void setLayoutType(String layoutType) {
		this.layoutType = layoutType;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getNotInChannelId() {
		return notInChannelId;
	}

	public void setNotInChannelId(String notInChannelId) {
		this.notInChannelId = notInChannelId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryId() {
		return categoryId;
	}

}