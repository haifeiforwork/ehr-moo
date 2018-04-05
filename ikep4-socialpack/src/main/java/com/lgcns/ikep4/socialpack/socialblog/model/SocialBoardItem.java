/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.socialblog.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.FileLink;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 소셜 블로그 게시판 게시글( IKEP4_SB_BD_ITEM)용  VO
 *
 * @author 이형운
 * @version $Id: SocialBoardItem.java 16246 2011-08-18 04:48:28Z giljae $
 */
public class SocialBoardItem extends BaseObject {

	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = 2620283936827745426L;

	/**
	 * 블로그 아이템 타입 
	 */
	public static final String ITEM_TYPE = "SB"; 
	
	/**
	 * 블로그 파일 에디터 가능 유무
	 */
	public static final String EDITOR_FILE = "Y"; 
	
	/**
	 * 블로그 첨부 파일 관련 Flag
	 */
	public static final String ATTECHED_FILE = "N";
	
	/**
	 * 모든 파일 업로드 가능 의 경우 File 값
	 */
	public static final String ALL_FILE = " "; 
	

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
    @NotNull
	@NotEmpty
	@NotBlank
	@Size(min=1, max=150)
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
     * 첨부 파일 Data Jason 타입 리턴 값
     */
    private String fileDataListJson;
    
    /**
     * 첨부파일 Data List
     */
    private List<FileData> fileDataList;
    
    /**
     * Editer 첨부파일 Link List
     */
    private List<FileLink> editorFileLinkList;
    
    /**
     * Editer 첨부파일 Data List
     */
    private List<FileData> editorFileDataList;
    
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
     * 등록한 작성자 registerJobTitleEngName
     */
    private String registerJobTitleEngName;
    
    /**
     * 직접 접속으로 글을 읽은 사용자 수
     */
    private Integer hitCount = 0;
    
    /**
     * 해당 게시물에 속해 있는 테그 리스트
     */
    private List<Tag> tagList;
    
    /**
     * 사용자가 글씨기 및 테그 입력 수정시 직접 입력하는 값
     */
    @NotNull
	@NotEmpty
	@NotBlank
    private String tag;
    
    
    /**
     * 로그인한 세션 사용자의 locale 정보
     */
    private String sessUserLocale;
    
    /** 인터넷 브라우저 (0 : msie 이외 브라우저 , 1 : msie 브라우저 ActiveX editor 위한 체크 값) */
	private Integer msie;
	
	/**
	 * Gets the msie.
	 *
	 * @return the msie
	 */
	public Integer getMsie() {
		return this.msie;
	}
	
	/**
	 * Sets the msie.
	 *
	 * @param msie the new msie
	 */
	public void setMsie(Integer msie) {
		this.msie = msie;
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
	 * @return the editorFileLinkList
	 */
	public List<FileLink> getEditorFileLinkList() {
		return editorFileLinkList;
	}

	/**
	 * @param editorFileLinkList the editorFileLinkList to set
	 */
	public void setEditorFileLinkList(List<FileLink> editorFileLinkList) {
		this.editorFileLinkList = editorFileLinkList;
	}

	/**
	 * @return the editorFileDataList
	 */
	public List<FileData> getEditorFileDataList() {
		return editorFileDataList;
	}

	/**
	 * @param editorFileDataList the editorFileDataList to set
	 */
	public void setEditorFileDataList(List<FileData> editorFileDataList) {
		this.editorFileDataList = editorFileDataList;
	}

	/**
	 * @return the hitCount
	 */
	public Integer getHitCount() {
		return hitCount;
	}

	/**
	 * @param hitCount the hitCount to set
	 */
	public void setHitCount(Integer hitCount) {
		this.hitCount = hitCount;
	}

	/**
	 * @return the tagList
	 */
	public List<Tag> getTagList() {
		return tagList;
	}

	/**
	 * @param tagList the tagList to set
	 */
	public void setTagList(List<Tag> tagList) {
		this.tagList = tagList;
	}

	/**
	 * @return the tag
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * @param tag the tag to set
	 */
	public void setTag(String tag) {
		this.tag = tag;
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
	 * @return the registerJobTitleEngName
	 */
	public String getRegisterJobTitleEngName() {
		return registerJobTitleEngName;
	}

	/**
	 * @param registerJobTitleEngName the registerJobTitleEngName to set
	 */
	public void setRegisterJobTitleEngName(String registerJobTitleEngName) {
		this.registerJobTitleEngName = registerJobTitleEngName;
	}

	
}
