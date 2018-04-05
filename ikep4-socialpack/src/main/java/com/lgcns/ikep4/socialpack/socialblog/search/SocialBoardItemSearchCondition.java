/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.socialblog.search;

import java.util.Date;
import java.util.List;

import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.FileLink;
import com.lgcns.ikep4.framework.web.SearchCondition;
import com.lgcns.ikep4.socialpack.socialblog.base.Constant;

/**
 * 소셜블로그 게시글 조회시 사용 하기 위한 VO
 *
 * @author 이형운
 * @version $Id: SocialBoardItemSearchCondition.java 16246 2011-08-18 04:48:28Z giljae $
 */
public class SocialBoardItemSearchCondition extends SearchCondition {
	
	
	/**
	 * 블로그 디폴트 페이지 세팅하는 생성자
	 */
	public SocialBoardItemSearchCondition() {
		super();
		super.setPagePerRecord(Constant.SOCIAL_BLOG_DEFAULT_POSTING_ROW_COUNT);
	}

	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = -3775205184368997951L;

	/**
	 * 검색 컬럼 
	 */
	private String searchColumn;
	
	/**
	 * 검색 키워드
	 */
	private String searchWord;
	
	/**
	 * 검색 기준 날자 (YYYYMMDD)
	 */
	private String searchDate;
	
	/**
	 * 소셜블로그 게시글 아이템 ID
	 */
	private String itemId;

	/**
	 * 소셜블로그 게시판 카테고리  ID
	 */
    private String categoryId;
    
    /**
     * 소셜블로그 게시판 카테고리 명
     */
    private String categoryName;

    /**
     * 소셜블로그 게시글 제목
     */
    private String title;

    /**
     * 소셜블로그 게시글 내용
     */
    private String contents;

    /**
     * 소셜블로그 게시글 추천수
     */
    private Integer recommendCount = 0;

    /**
     * 소셜블로그 게시글 댓글수
     */
    private Integer linereplyCount = 0;

    /**
     * 소셜블로그 게시글 첨부파일수
     */
    private Integer attachFileCount = 0;

    /**
     * 소셜블로그 게시글 읽기 권한 
     */
    private String readPermission;
    
    /**
     * 소셜블로그 게시글 등록자 ID
     */
    private String registerId;

    /**
     * 소셜블로그 게시글 등록자 명
     */
    private String registerName;

    /**
     * 소셜블로그 게시글 등록일
     */
    private Date registDate;

    /**
     * 소셜블로그 게시글 수정일
     */
    private Date updateDate; 
 
    /**
     * 첨부파일 Link List
     */
    private List<FileLink> fileLinkList;  
    
    /**
     * 첨부파일 Data List
     */
    private List<FileData> fileDataList;
    
    /**
     * 첨부 파일 Data Jason 타입 리턴 값
     */
    private String fileDataListJson;
    
    /**
     * 등록한 작성자 English Name
     */
    private String registerEnglishName;
    
    /**
     * 등록한 작성자 Team Name
     */
    private String registerTeamName;
    
    /**
     * 등록한 작성자 Team Englisgh Name
     */
    private String registerTeamEngName;
    
    /**
     * 등록한 작성자 JobTitleCode
     */
    private String registerJobTitleCode;
    
    /**
     * 등록한 작성자 JobTitleName
     */
    private String registerJobTitleName;
    
    /**
     * 등록한 작성자 registerJobTitleEnglishName
     */
    private String registerJobTitleEnglishName;
    
    /**
     * 로그인한 세션 사용자의 locale 정보
     */
    private String sessUserLocale;
    
	/**
	 * @return the searchColumn
	 */
	public String getSearchColumn() {
		return searchColumn;
	}
	
	/**
	 * @param searchColumn the searchColumn to set
	 */
	public void setSearchColumn(String searchColumn) {
		this.searchColumn = searchColumn;
	}

	/**
	 * @return the searchWord
	 */
	public String getSearchWord() {
		return searchWord;
	}

	/**
	 * @param searchWord the searchWord to set
	 */
	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}

	/**
	 * @return the searchDate
	 */
	public String getSearchDate() {
		return searchDate;
	}

	/**
	 * @param searchDate the searchDate to set
	 */
	public void setSearchDate(String searchDate) {
		this.searchDate = searchDate;
	}

	/**
	 * @return the itemId
	 */
	public String getItemId() {
		return itemId;
	}

	/**
	 * @param itemId the itemId to set
	 */
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	/**
	 * @return the categoryId
	 */
	public String getCategoryId() {
		return categoryId;
	}

	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * @param categoryName the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the contents
	 */
	public String getContents() {
		return contents;
	}

	/**
	 * @param contents the contents to set
	 */
	public void setContents(String contents) {
		this.contents = contents;
	}

	/**
	 * @return the recommendCount
	 */
	public Integer getRecommendCount() {
		return recommendCount;
	}

	/**
	 * @param recommendCount the recommendCount to set
	 */
	public void setRecommendCount(Integer recommendCount) {
		this.recommendCount = recommendCount;
	}

	/**
	 * @return the linereplyCount
	 */
	public Integer getLinereplyCount() {
		return linereplyCount;
	}

	/**
	 * @param linereplyCount the linereplyCount to set
	 */
	public void setLinereplyCount(Integer linereplyCount) {
		this.linereplyCount = linereplyCount;
	}

	/**
	 * @return the attachFileCount
	 */
	public Integer getAttachFileCount() {
		return attachFileCount;
	}

	/**
	 * @param attachFileCount the attachFileCount to set
	 */
	public void setAttachFileCount(Integer attachFileCount) {
		this.attachFileCount = attachFileCount;
	}

	/**
	 * @return the readPermission
	 */
	public String getReadPermission() {
		return readPermission;
	}

	/**
	 * @param readPermission the readPermission to set
	 */
	public void setReadPermission(String readPermission) {
		this.readPermission = readPermission;
	}

	/**
	 * @return the registerId
	 */
	public String getRegisterId() {
		return registerId;
	}

	/**
	 * @param registerId the registerId to set
	 */
	public void setRegisterId(String registerId) {
		this.registerId = registerId;
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

	/**
	 * @return the registDate
	 */
	public Date getRegistDate() {
		return registDate;
	}

	/**
	 * @param registDate the registDate to set
	 */
	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}

	/**
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return the fileLinkList
	 */
	public List<FileLink> getFileLinkList() {
		return fileLinkList;
	}

	/**
	 * @param fileLinkList the fileLinkList to set
	 */
	public void setFileLinkList(List<FileLink> fileLinkList) {
		this.fileLinkList = fileLinkList;
	}

	/**
	 * @return the fileDataList
	 */
	public List<FileData> getFileDataList() {
		return fileDataList;
	}

	/**
	 * @param fileDataList the fileDataList to set
	 */
	public void setFileDataList(List<FileData> fileDataList) {
		this.fileDataList = fileDataList;
	}

	/**
	 * @return the registerEnglishName
	 */
	public String getRegisterEnglishName() {
		return registerEnglishName;
	}

	/**
	 * @param registerEnglishName the registerEnglishName to set
	 */
	public void setRegisterEnglishName(String registerEnglishName) {
		this.registerEnglishName = registerEnglishName;
	}

	/**
	 * @return the registerTeamName
	 */
	public String getRegisterTeamName() {
		return registerTeamName;
	}

	/**
	 * @param registerTeamName the registerTeamName to set
	 */
	public void setRegisterTeamName(String registerTeamName) {
		this.registerTeamName = registerTeamName;
	}
	
	/**
	 * @return the registerTeamEngName
	 */
	public String getRegisterTeamEngName() {
		return registerTeamEngName;
	}

	/**
	 * @param registerTeamEngName the registerTeamEngName to set
	 */
	public void setRegisterTeamEngName(String registerTeamEngName) {
		this.registerTeamEngName = registerTeamEngName;
	}

	/**
	 * @return the registerJobTitleCode
	 */
	public String getRegisterJobTitleCode() {
		return registerJobTitleCode;
	}

	/**
	 * @param registerJobTitleCode the registerJobTitleCode to set
	 */
	public void setRegisterJobTitleCode(String registerJobTitleCode) {
		this.registerJobTitleCode = registerJobTitleCode;
	}

	/**
	 * @return the registerJobTitleName
	 */
	public String getRegisterJobTitleName() {
		return registerJobTitleName;
	}

	/**
	 * @param registerJobTitleName the registerJobTitleName to set
	 */
	public void setRegisterJobTitleName(String registerJobTitleName) {
		this.registerJobTitleName = registerJobTitleName;
	}

	/**
	 * @return the fileDataListJson
	 */
	public String getFileDataListJson() {
		return fileDataListJson;
	}

	/**
	 * @param fileDataListJson the fileDataListJson to set
	 */
	public void setFileDataListJson(String fileDataListJson) {
		this.fileDataListJson = fileDataListJson;
	}
	
	/**
	 * @return the sessUserLocale
	 */
	public String getSessUserLocale() {
		return sessUserLocale;
	}

	/**
	 * @param sessUserLocale the sessUserLocale to set
	 */
	public void setSessUserLocale(String sessUserLocale) {
		this.sessUserLocale = sessUserLocale;
	}

	/**
	 * @return the registerJobTitleEnglishName
	 */
	public String getRegisterJobTitleEnglishName() {
		return registerJobTitleEnglishName;
	}

	/**
	 * @param registerJobTitleEnglishName the registerJobTitleEnglishName to set
	 */
	public void setRegisterJobTitleEnglishName(String registerJobTitleEnglishName) {
		this.registerJobTitleEnglishName = registerJobTitleEnglishName;
	}

}
