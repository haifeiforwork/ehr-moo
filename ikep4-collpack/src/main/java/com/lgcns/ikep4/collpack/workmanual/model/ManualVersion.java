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

import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.FileLink;
import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * TODO Javadoc주석작성
 *
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: ManualVersion.java 16236 2011-08-18 02:48:22Z giljae $
 */

public class ManualVersion extends BaseObject {
	private static final long serialVersionUID = -2677569029918618748L;
	
	/**
	 * 버전_ID 
	 */
	private String   versionId;
	
	/**
	 * 메뉴얼ID 
	 */
	private String   manualId;
	
	/**
	 * 버전 
	 */
	private Float    version;
	
	/**
	 * 제목 
	 */
	private String   versionTitle;
	
	/**
	 * 내용 
	 */
	private String   versionContents;
	
	/**
	 * 첨부파일수 
	 */
	private Integer  versionAttachCount;
	
	/**
	 * 공개여부 
	 */
	private Integer  isPublic;
	
	/**
	 * 수정사유 
	 */
	private String   updateReason;
	
	/**
	 * 결재진행 상태 
	 */
	private String   approvalStatus;
	
	/**
	 * 포탈ID 
	 */
	private String   portalId;
	
	/**
	 * 등록자ID 
	 */
	private String   registerId;
	
	/**
	 * 등록자명 
	 */
	private String   registerName;
	
	/**
	 * 등록일 
	 */
	private Date     registDate; 
	
	/**
	 * 폐기여부 
	 */
	private Integer  isAbolition = 0; 
	
	/**
	 * 태그정보 
	 */
	private String   tag;
	
	/**
	 * 메뉴얼 상태 
	 */
	private String   manualType;
	
	/**
	 * 인덱스번호 
	 */
	private Integer  indexRowNum;
	
	/**
	 * 업로드 파일리스트 
	 */
	private List<FileLink> fileLinkList;
	
	/**
	 * 다운로드 파일 리스트 
	 */
	private List<FileData> fileDataList;
	
	/**
	 * 등록자명(영어) 
	 */
	private String   registerEnglishName;
	
	/**
	 * @return the isAbolition
	 */
	public Integer getIsAbolition() {
		return isAbolition;
	}
	/**
	 * @param isAbolition the isAbolition to set
	 */
	public void setIsAbolition(Integer isAbolition) {
		this.isAbolition = isAbolition;
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
	 * @return the versionId
	 */
	public String getVersionId() {
		return versionId;
	}
	/**
	 * @param versionId the versionId to set
	 */
	public void setVersionId(String versionId) {
		this.versionId = versionId;
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
	 * @return the versionTitle
	 */
	public String getVersionTitle() {
		return versionTitle;
	}
	/**
	 * @param versionTitle the versionTitle to set
	 */
	public void setVersionTitle(String versionTitle) {
		this.versionTitle = versionTitle;
	}
	/**
	 * @return the versionContents
	 */
	public String getVersionContents() {
		return versionContents;
	}
	/**
	 * @param versionContents the versionContents to set
	 */
	public void setVersionContents(String versionContents) {
		this.versionContents = versionContents;
	}
	/**
	 * @return the versionAttachCount
	 */
	public Integer getVersionAttachCount() {
		return versionAttachCount;
	}
	/**
	 * @param versionAttachCount the versionAttachCount to set
	 */
	public void setVersionAttachCount(Integer versionAttachCount) {
		this.versionAttachCount = versionAttachCount;
	}
	/**
	 * @return the isPublic
	 */
	public Integer getIsPublic() {
		return isPublic;
	}
	/**
	 * @param isPublic the isPublic to set
	 */
	public void setIsPublic(Integer isPublic) {
		this.isPublic = isPublic;
	}
	/**
	 * @return the updateReason
	 */
	public String getUpdateReason() {
		return updateReason;
	}
	/**
	 * @param updateReason the updateReason to set
	 */
	public void setUpdateReason(String updateReason) {
		this.updateReason = updateReason;
	}
	/**
	 * @return the approvalStatus
	 */
	public String getApprovalStatus() {
		return approvalStatus;
	}
	/**
	 * @param approvalStatus the approvalStatus to set
	 */
	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
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
}