/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.who.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.FileLink;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * TODO Javadoc주석작성
 *
 * @author 서혜숙(shs0420@nate.com)
 * @version $Id: Who.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class Who extends BaseObject {
	private static final long serialVersionUID = 4182056323460781014L;

    public interface Create {} // Create group을 선언
    public interface Update {} // Update group을 선언
	
	/**
	 * 인물정보 ID
	 */
	private String profileId;	
	
	/**
	 * 프로파일 그룹 ID(동일한 인물정보의 버전별 수정시 사용)
	 */
	private String profileGroupId;	

	/**
	 * 인물이름
	 */
	@NotEmpty(groups=Create.class)
	@Size(min=1,max=100)	
	private String name;
	
	/**
	 * 회사명
	 */
	@NotEmpty(groups={Create.class, Update.class})
	@Size(min=1,max=100)		
	private String companyName;	
	
	/**
	 * 부서명
	 */
	private String teamName;	
	
	/**
	 * 등록자 부서 이름
	 */
	private String registerTeamName;

	/**
	 * 등록자 부서 영어이름
	 */
	private String teamEnglishName;
	
	/**
	 * 직급명
	 */
	private String jobRankName;

	/**
	 * 등록자 직급명
	 */
	private String jobTitleName;

	/**
	 * 등록자 직급영문명
	 */
	private String jobTitleEnglishName;

	/**
	 * 사무실 전화번호
	 */
	private String officePhoneno;

	/**
	 * 휴대폰 번호
	 */
	private String mobile;

	/**
	 * 이메일
	 */
	@NotEmpty(groups={Create.class, Update.class})
	private String mail;

	/**
	 * 회사주소
	 */
	private String companyAddress;	

	/**
	 * 메모
	 */
	private String memo;	

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
	 * 등록일시
	 */
	private Date registDate;	

	/**
	 * 인물 사전 버전 정보(예 : 1.0, 1.1, 1.2 ... 2.0, 2.1...)
	 */
	private double version;	

	/**
	 * 최신 용어 버전 정보(예 : 1.0, 1.1, 1.2 ... 2.0, 2.1...)
	 */	
	private String recentVersion;
	
	/**
	 * 최신 인물 ID
	 */	
	private String recentProfileId;	

	/**
	 * 인물사전 수정 사유
	 */
	private String updateReason;		

	/**
	 *  hit 개수
	 */
	private int hitCount = 0;	

	/**
	 * 포탈 ID(for Multi Portal)
	 */
	private String portalId;		

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
	 * 조회일
	 */
	private Date hitDate;	

	/**
	 * 게시판 가져올 끝 수
	 */
	private String endNo;
	
	/**
	 * 게시판 가져올 처음 수
	 */
	private String baseNo;

	/**
	 *  등록한 날로부터의 기간
	 */
	private int isNew = 0;	
	
	/**
	 * 태그
	 */
	private String tag;
	
	/**
	 * 사진 이미지
	 */
	private String fileId;	
	
	/**
	 * 보기 mode
	 */	
	private String mode;
	
	/**
	 * view Id
	 */
	private String viewId;	

	/**
	 * 등록자 영어이름
	 */	
	private String registerEnglishName;
	
	/**
	 * 사용자의 프로필 사진 ID
	 */
	private String profilePictureId;
	
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

	public String getProfilePictureId() {
		return profilePictureId;
	}

	public void setProfilePictureId(String profilePictureId) {
		this.profilePictureId = profilePictureId;
	}

	public String getRegisterEnglishName() {
		return registerEnglishName;
	}

	public void setRegisterEnglishName(String registerEnglishName) {
		this.registerEnglishName = registerEnglishName;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public int getIsNew() {
		return isNew;
	}

	public void setIsNew(int isNew) {
		this.isNew = isNew;
	}	
	
	private List<Tag> tagList;

	private List<FileLink> editorFileLinkList;
	
    /**
     * 파일데이터리스트
     */
    private List<FileData> fileDataList;
    
	public List<FileData> getFileDataList() {
		return fileDataList;
	}

	public void setFileDataList(List<FileData> fileDataList) {
		this.fileDataList = fileDataList;
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
	
    public List<FileLink> getEditorFileLinkList() {
    	return editorFileLinkList;
    }

    public void setEditorFileLinkList(List<FileLink> editorFileLinkList) {
    	this.editorFileLinkList = editorFileLinkList;
    }
    
	public String getProfileId() {
		return profileId;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}
	
	public String getProfileGroupId() {
		return profileGroupId;
	}

	public void setProfileGroupId(String profileGroupId) {
		this.profileGroupId = profileGroupId;
	}	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	
	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
		
	public String getRegisterTeamName() {
		return registerTeamName;
	}

	public void setRegisterTeamName(String registerTeamName) {
		this.registerTeamName = registerTeamName;
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
		
	public String getJobTitleName() {
		return jobTitleName;
	}

	public void setJobTitleName(String jobTitleName) {
		this.jobTitleName = jobTitleName;
	}
	
	public String getJobTitleEnglishName() {
		return jobTitleEnglishName;
	}

	public void setJobTitleEnglishName(String jobTitleEnglishName) {
		this.jobTitleEnglishName = jobTitleEnglishName;
	}	
	
	public String getOfficePhoneno() {
		return officePhoneno;
	}

	public void setOfficePhoneno(String officePhoneno) {
		this.officePhoneno = officePhoneno;
	}	
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}	
	
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}	
	
	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}	

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
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
	
	public String getRecentInputRegisterId() {
		return recentInputRegisterId;
	}

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
	
	public double getVersion() {
		return version;
	}

	public void setVersion(double version) {
		this.version = version;
	}
	
	public String getUpdateReason() {
		return updateReason;
	}

	public void setUpdateReason(String updateReason) {
		this.updateReason = updateReason;
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
	
	public Date getHitDate() {
		return hitDate;
	}

	public void setHitDate(Date hitDate) {
		this.hitDate = hitDate;
	}	
	
	public String getEndNo() {
		return endNo;
	}

	public void setEndNo(String endNo) {
		this.endNo = endNo;
	}

	public String getBaseNo() {
		return baseNo;
	}

	public void setBaseNo(String baseNo) {
		this.baseNo = baseNo;
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
	
	public String getRecentProfileId() {
		return recentProfileId;
	}

	public void setRecentProfileId(String recentProfileId) {
		this.recentProfileId = recentProfileId;
	}	
}
