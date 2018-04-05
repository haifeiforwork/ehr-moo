package com.lgcns.ikep4.collpack.who.search;

import org.hibernate.validator.constraints.NotEmpty;

import com.lgcns.ikep4.collpack.who.constants.WhoConstants;
import com.lgcns.ikep4.framework.web.SearchCondition;

public class WhoSearchCondition extends SearchCondition {
	private static final long serialVersionUID = -6809397882380002043L;

	@Override
	protected Integer getDefaultPagePerRecord() {
		return WhoConstants.WHO_PAGE_PER_RECORD;
	}	
	/**
	 * 태그 ID
	 */
	private String tagId;	

	/**
	 * 이름
	 */
	private String name;	
	
	private String companyName;

	private String teamName;
	
	private String startSortChar;
	
	private String endSortChar;
	
	private String whoSortIndex;

	private String mode;

	/**
	 * 메모
	 */
	private String memo;

	/**
	 * 등록자 ID
	 */
	@NotEmpty
	private String registerId;			

	/**
	 * 등록자 이름
	 */
	@NotEmpty
	private String registerName;
	
	private String searchColumn;
	
	private String searchWord;	

	private String localeCode;
	
	private String portalId;

	/**
	 * 내가등록한용어 내가조회한용어 더보기 
	 */	
	private String isMore;

	/**
	 * 조회자 ID
	 */
	private String viewId;		
	

	public String getTagId() {
		return tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	/**
	 * @return the registerName
	 */		
	public String getRegisterName() {
		return registerName;
	}

	/**
	 * @param registerName the registerName to set
	 */		
	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}	
	
	public String getRegisterId() {
		return registerId;
	}

	public void setRegisterId(String registerId) {
		this.registerId = registerId;
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
	
	public String getEndSortChar() {
		return endSortChar;
	}

	public void setEndSortChar(String endSortChar) {
		this.endSortChar = endSortChar;
	}

	public String getWhoSortIndex() {
		return whoSortIndex;
	}

	public void setWhoSortIndex(String whoSortIndex) {
		this.whoSortIndex = whoSortIndex;
	}
	
	public String getStartSortChar() {
		return startSortChar;
	}

	public void setStartSortChar(String startSortChar) {
		this.startSortChar = startSortChar;
	}	
	
	public String getViewId() {
		return viewId;
	}

	public void setViewId(String viewId) {
		this.viewId = viewId;
	}	
		
	public String getLocaleCode() {
		return localeCode;
	}

	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}	
	
	public String getIsMore() {
		return isMore;
	}

	public void setIsMore(String isMore) {
		this.isMore = isMore;
	}	
		
	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}	
	
	
	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}	
	
}
