/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.dictionary.model;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.lgcns.ikep4.collpack.who.model.Who.Create;
import com.lgcns.ikep4.collpack.who.model.Who.Update;
import com.lgcns.ikep4.support.fileupload.model.FileLink;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * TODO Javadoc주석작성
 *
 * @author 서혜숙(shs0420@nate.com)
 * @version $Id: Dictionary.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class Dictionary extends BaseObject {
	private static final long serialVersionUID = 4182056323460781014L;

    public interface Create {} // Create group을 선언
    public interface Update {} // Update group을 선언
    
	public final static String ITEM_TYPE = "CV";

	/**
	 * 용어사전 ID
	 */
	
	private String dictionaryId;	
	
	/**
	 * 이전 화면에서 클릭한 용어사전 ID
	 */	
	private String viewDictionaryId;	

	/**
	 * 용어 ID
	 */
	
	private String wordId;	

	/**
	 * 용어사전 그룹 ID(동일한 용어의 버전별 수정시 사용)
	 */
	
	private String wordGroupId;	

	/**
	 * 용어사전 이름
	 */
	
	private String dictionaryName;	
	
	/**
	 * 사전별용어수
	 */	
	private String wordCount;	

	/**
	 * 용어사전 정렬순서
	 */
	
	private String sortOrder;	
	
	/**
	 * 용어 이름
	 */
	@NotEmpty(groups=Create.class)
	private String wordName;
	
	/**
	 * 용어 영문 이름
	 */
	private String wordEnglishName;		

	/**
	 * 용어 내용
	 */
	@NotEmpty(groups={Create.class, Update.class})
	private String contents;

	/**
	 * 수정사유
	 */
	@NotEmpty(groups={Update.class})
	private String updateReason;

	/**
	 * 등록자 ID
	 */	
	private String registerId;
	
	/**
	 * 최근 등록자 ID
	 */
	private String recentInputRegisterId;	

	/**
	 * 등록자 이름
	 */	
	private String registerName;	

	/**
	 * 등록자 영어이름
	 */	
	private String registerEnglishName;	

	/**
	 * 등록일시
	 */
	private Date registDate;	

	/**
	 * 용어 버전 정보(예 : 1.0, 1.1, 1.2 ... 2.0, 2.1...)
	 */
	private double version;
	
	/**
	 * 최신 용어 버전 정보(예 : 1.0, 1.1, 1.2 ... 2.0, 2.1...)
	 */	
	private String recentVersion;

	/**
	 *  hit 개수
	 */
	private int hitCount = 0;	

	/**
	 * 포탈 ID(for Multi Portal)
	 */
	
	private String portalId;	
	
	/**
	 *  등록한 날로부터의 기간
	 */
	private int isNew = 0;	

	/**
	 * updater Id
	 */
	private String updaterId;
	
	/**
	 * updater 이름
	 */
	private String updaterName;
	
	/**
	 * update 날짜
	 */
	private Date updateDate;
	
	/**
	 * 조회 ID
	 */
	private String referenceId;	

	/**
	 * reader Id
	 */
	
	private String readerId;
	
	/**
	 * reader name
	 */
	
	private String readerName;	

	/**
	 * 조회일
	 */
		
	private Date hitDate;	

	/**
	 * 직급 이름
	 */
	private String jobRankName;

	/**
	 * 직급 영어이름
	 */
	private String jobTitleEnglishName;

	/**
	 * 팀 이름
	 */
	private String teamName;

	/**
	 * 팀 영어이름
	 */
	private String teamEnglishName;

	/**
	 * 태그
	 */
	private String tag;
	
	/**
	 * 모드
	 */
	private String mode;	
	
	/**
	 * view Id
	 */
	
	private String viewId;	

	private List<Tag> tagList;

	/**
	 * 팝업여부
	 */	
	private boolean docPopup;
	
	public boolean getDocPopup() {
		return docPopup;
	}

	public void setDocPopup(boolean docPopup) {
		this.docPopup = docPopup;
	}
	
	private List<FileLink> editorFileLinkList;

    public List<FileLink> getEditorFileLinkList() {
    	return editorFileLinkList;
    }

    public void setEditorFileLinkList(List<FileLink> editorFileLinkList) {
    	this.editorFileLinkList = editorFileLinkList;
    }

	public String getDictionaryId() {
		return dictionaryId;
	}

	public void setDictionaryId(String dictionaryId) {
		this.dictionaryId = dictionaryId;
	}
	
	public String getWordId() {
		return wordId;
	}

	public void setWordId(String wordId) {
		this.wordId = wordId;
	}

	public String getWordGroupId() {
		return wordGroupId;
	}

	public void setWordGroupId(String wordGroupId) {
		this.wordGroupId = wordGroupId;
	}
	
	public String getDictionaryName() {
		return dictionaryName;
	}

	public void setDictionaryName(String dictionaryName) {
		this.dictionaryName = dictionaryName;
	}		
	
	public String getWordCount() {
		return wordCount;
	}

	public void setWordCount(String wordCount) {
		this.wordCount = wordCount;
	}
	
	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}	
	
	public String getWordName() {
		return wordName;
	}

	public void setWordName(String wordName) {
		this.wordName = wordName;
	}
	
	public String getWordEnglishName() {
		return wordEnglishName;
	}

	public void setWordEnglishName(String wordEnglishName) {
		this.wordEnglishName = wordEnglishName;
	}
		
	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
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
	 * @return the recentInputRegisterId
	 */		
	public String getRecentInputRegisterId() {
		return recentInputRegisterId;
	}
	
	/**
	 * @param registerId the recentInputRegisterId to set
	 */		
	public void setRecentInputRegisterId(String recentInputRegisterId) {
		this.recentInputRegisterId = recentInputRegisterId;
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
	
	public String getRegisterEnglishName() {
		return registerEnglishName;
	}

	public void setRegisterEnglishName(String registerEnglishName) {
		this.registerEnglishName = registerEnglishName;
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
	
	public int getHitCount() {
		return hitCount;
	}

	public void setHitCount(int hitCount) {
		this.hitCount = hitCount;
	}	
	
	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}	
	
	public String getUpdaterId() {
		return updaterId;
	}

	public void setUpdaterId(String updaterId) {
		this.updaterId = updaterId;
	}

	public String getUpdaterName() {
		return updaterName;
	}

	public void setUpdaterName(String updaterName) {
		this.updaterName = updaterName;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}	
	
	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}
	
	public String getReaderId() {
		return readerId;
	}

	public void setReaderId(String readerId) {
		this.readerId = readerId;
	}
	
	public Date getHitDate() {
		return hitDate;
	}

	public void setHitDate(Date hitDate) {
		this.hitDate = hitDate;
	}	

	public double getVersion() {
		return version;
	}

	public void setVersion(double version) {
		this.version = version;
	}
	
	public String getReaderName() {
		return readerName;
	}

	public void setReaderName(String readerName) {
		this.readerName = readerName;
	}	
	
	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}			
	
	public String getTeamEnglishName() {
		return teamEnglishName;
	}

	public void setTeamEnglishName(String teamEnglishName) {
		this.teamEnglishName = teamEnglishName;
	}
	
	public String getJobRankName() {
		return jobRankName;
	}

	public void setJobRankName(String jobRankName) {
		this.jobRankName = jobRankName;
	}		
	
	public String getJobTitleEnglishName() {
		return jobTitleEnglishName;
	}

	public void setJobTitleEnglishName(String jobTitleEnglishName) {
		this.jobTitleEnglishName = jobTitleEnglishName;
	}
	
	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}	
	
    public List<Tag> getTagList() {
		return tagList;
	}

	public void setTagList(List<Tag> tagList) {
		this.tagList = tagList;
	}
	
	public int getIsNew() {
		return isNew;
	}

	public void setIsNew(int isNew) {
		this.isNew = isNew;
	}	

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}	
	
	public String getViewId() {
		return viewId;
	}

	public void setViewId(String viewId) {
		this.viewId = viewId;
	}		

	public String getRecentVersion() {
		return recentVersion;
	}

	public void setRecentVersion(String recentVersion) {
		this.recentVersion = recentVersion;
	}		
	
	public String getUpdateReason() {
		return updateReason;
	}

	public void setUpdateReason(String updateReason) {
		this.updateReason = updateReason;
	}	
	

	public String getViewDictionaryId() {
		return viewDictionaryId;
	}

	public void setViewDictionaryId(String viewDictionaryId) {
		this.viewDictionaryId = viewDictionaryId;
	}	
}
