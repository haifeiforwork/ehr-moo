package com.lgcns.ikep4.collpack.dictionary.search;

import org.hibernate.validator.constraints.NotEmpty;

import com.lgcns.ikep4.collpack.dictionary.constants.DictionaryConstants;
import com.lgcns.ikep4.framework.web.SearchCondition;

public class DictionarySearchCondition extends SearchCondition {
	private static final long serialVersionUID = -6809397882380002043L;

	@Override
	protected Integer getDefaultPagePerRecord() {
		return DictionaryConstants.DICTIONARY_PAGE_PER_RECORD;
	}

	/**
	 * 용어사전 ID
	 */
	@NotEmpty
	private String dictionaryId;	

	/**
	 * 용어 이름
	 */
	private String wordName;	
	
	private String startSortChar;
	
	private String endSortChar;
	
	private String dictionarySortIndex;
	
	private String mode;

	/**
	 * 용어 내용
	 */
	private String contents;
	
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
	
	/**
	 * 내가등록한용어 내가조회한용어 더보기 
	 */	
	private String isMore;

	/**
	 * 조회자 ID
	 */
	private String viewId;		
	
	private String portalId;

	public String getWordName() {
		return wordName;
	}

	public void setWordName(String wordName) {
		this.wordName = wordName;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
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
	
	public String getDictionaryId() {
		return dictionaryId;
	}

	public void setDictionaryId(String dictionaryId) {
		this.dictionaryId = dictionaryId;
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

	public String getStartSortChar() {
		return startSortChar;
	}

	public void setStartSortChar(String startSortChar) {
		this.startSortChar = startSortChar;
	}	
	
	public String getDictionarySortIndex() {
		return dictionarySortIndex;
	}

	public void setDictionarySortIndex(String dictionarySortIndex) {
		this.dictionarySortIndex = dictionarySortIndex;
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
