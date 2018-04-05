/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.workmanual.model;

import java.util.Date;
import java.util.List;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.support.tagging.model.Tag;

/**
 * TODO Javadoc주석작성
 *
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: Manual.java 16236 2011-08-18 02:48:22Z giljae $
 */

public class Manual extends BaseObject {
	private static final long serialVersionUID = -3082693350739183465L;
	
	/**
	 * 메뉴얼ID 
	 */
	private String    manualId;      

	/**
	 * 카테고리 ID 
	 */
	private String    categoryId;    

	/**
	 * 메뉴얼 상태 
	 */
	private String    manualType;    

	/**
	 * 버전정보 
	 */
	private Float     version;       

	/**
	 * 메뉴얼 제목 
	 */
	private String    title;         

	/**
	 * 내용 
	 */
	private String    contents;      

	/**
	 * 첨부 파일 수 
	 */
	private Integer   attachCount = 0;   

	/**
	 * 조회 수 
	 */
	private Integer   hitCount = 0;    

	/**
	 * 한줄 댓글 수 
	 */
	private Integer   linereplyCount = 0;   

	/**
	 * 포탈_ID 
	 */
	private String    portalId;      

	/**
	 * 등록자ID 
	 */
	private String    registerId;    

	/**
	 * 등록자명 
	 */
	private String    registerName;  

	/**
	 * 등록일 
	 */
	private Date      registDate;    

	/**
	 * 수정자ID 
	 */
	private String    updaterId;     

	/**
	 * 수정자 이름 
	 */
	private String    updaterName;   

	/**
	 * 수정일 
	 */
	private Date      updateDate;  

	/**
	 * 인덱스번호 
	 */
	private Integer   indexRowNum = 0;

	/**
	 * 직급명 
	 */
	private String    jobTitleName;   

	/**
	 * 태그리스트 
	 */
	private List<Tag> tagList;

	/**
	 * 수정자 이름(영어) 
	 */
	private String    updaterEnglishName;   

	/**
	 * 직급명(영어) 
	 */
	private String    jobTitleEnglishName;   
	
	/**
	 * @return the jobTitleName
	 */
	public String getJobTitleName() {
		return jobTitleName;
	}
	/**
	 * @param jobTitleName the jobTitleName to set
	 */
	public void setJobTitleName(String jobTitleName) {
		this.jobTitleName = jobTitleName;
	}
	/**
	 * @return the indexRowNum
	 */
	public Integer getIndexRowNum() {
		return indexRowNum;
	}
	/**
	 * @param indexRowNum the indexRowNum to set
	 */
	public void setIndexRowNum(Integer indexRowNum) {
		this.indexRowNum = indexRowNum;
	}
	/**
	 * @return the manualId
	 */
	public String getManualId() {
		return manualId;
	}
	/**
	 * @param manualId the manualId to set
	 */
	public void setManualId(String manualId) {
		this.manualId = manualId;
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
	 * @return the manualType
	 */
	public String getManualType() {
		return manualType;
	}
	/**
	 * @param manualType the manualType to set
	 */
	public void setManualType(String manualType) {
		this.manualType = manualType;
	}
	/**
	 * @return the version
	 */
	public Float getVersion() {
		return version;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(Float version) {
		this.version = version;
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
	 * @return the attachCount
	 */
	public Integer getAttachCount() {
		return attachCount;
	}
	/**
	 * @param attachCount the attachCount to set
	 */
	public void setAttachCount(Integer attachCount) {
		this.attachCount = attachCount;
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
	 * @return the portalId
	 */
	public String getPortalId() {
		return portalId;
	}
	/**
	 * @param portalId the portalId to set
	 */
	public void setPortalId(String portalId) {
		this.portalId = portalId;
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
	 * @return the updaterId
	 */
	public String getUpdaterId() {
		return updaterId;
	}
	/**
	 * @param updaterId the updaterId to set
	 */
	public void setUpdaterId(String updaterId) {
		this.updaterId = updaterId;
	}
	/**
	 * @return the updaterName
	 */
	public String getUpdaterName() {
		return updaterName;
	}
	/**
	 * @param updaterName the updaterName to set
	 */
	public void setUpdaterName(String updaterName) {
		this.updaterName = updaterName;
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
	 * @return the updaterEnglishName
	 */
	public String getUpdaterEnglishName() {
		return updaterEnglishName;
	}
	/**
	 * @param updaterEnglishName the updaterEnglishName to set
	 */
	public void setUpdaterEnglishName(String updaterEnglishName) {
		this.updaterEnglishName = updaterEnglishName;
	}
	/**
	 * @return the jobTitleEnglishName
	 */
	public String getJobTitleEnglishName() {
		return jobTitleEnglishName;
	}
	/**
	 * @param jobTitleEnglishName the jobTitleEnglishName to set
	 */
	public void setJobTitleEnglishName(String jobTitleEnglishName) {
		this.jobTitleEnglishName = jobTitleEnglishName;
	}
}
