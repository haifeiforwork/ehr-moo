/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.admin.model;

import java.util.List;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.Size;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.FileLink;
import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 *
 * @author wonchu
 * @version $Id: FormItem.java 16245 2011-08-18 04:28:59Z giljae $
 */
public class ApprForm extends BaseObject {
	
	
	static final long serialVersionUID = -6000877560192168935L;

	/**
	 * CreateApprForm
	 */
	public interface CreateApprForm {
	}

	/**
	 * UpdateApprInfoForm
	 */
	public interface UpdateApprInfoForm {
	}
	
	/**
	 * UpdateApprContentForm
	 */
	public interface UpdateApprContentForm {
	}
	
	/**
	 * 모드  VO
	 */
	private String mode;
	
	/**
	 * 사용자 아이디  VO
	 */
	private String userId;
	
	/**
	 * 그룹 아이디  VO
	 */
	private String groupId;
	
	/**
	 * 양식 아이디  VO
	 */
	private String formId;

	/**
	 * 양식명  VO
	 */
	@NotEmpty(groups = { CreateApprForm.class, UpdateApprInfoForm.class })
	@Size(groups = { CreateApprForm.class, UpdateApprInfoForm.class }, min = 0, max = 200)
	private String formName;

	/**
	 * 양식 부모 아이디  VO
	 */
	private String formParentId;

	/**
	 * 양식 부모명  VO
	 */
	private String formParentName;

	/**
	 * 양식 설명  VO
	 */
	@NotEmpty(groups = { CreateApprForm.class, UpdateApprInfoForm.class })
	@Size(groups = { CreateApprForm.class, UpdateApprInfoForm.class }, min = 0, max = 200)
	private String formDesc;
	
	/**
	 * 결재문서 유형 (0:내부결재,1:협조공문-수신처가 있는 결재문서) VO
	 */
	private int apprDocType;

	/**
	 * 사용유무 (0:사용안함,1:사용) VO
	 */
	private int usage;

	/**
	 * 디폴트 결제선 아이디  VO
	 */
	private String defLineId;

	/**
	 * 디폴트 결제선 명  VO
	 */
	private String defLineName;
	
	/**
	 * 디폴트 결제선  VO
	 */
	private String defLineSet;
	
	/**
	 * 디폴트 결재선 사용여부 (0:사용안함,1:사용) VO
	 */
	private int defLineUse;

	/**
	 * Default 결재선 기안자 수정가능여부 (0:사용안함,1:사용) VO
	 */
	private int isDefLineUpdate;
	
	/**
	 * 보존연한 타입 (0:사용자선택, 1:관리자선택) VO
	 */
	private int isApprPeriod;

	/**
	 * 보존연한 보존연한 (1년, 2년, 3년, 5년..) VO
	 */
	private String apprPeriodCd;

	/**
	 * 문서종류 타입 (0:사용자선택, 1:관리자선택) VO
	 */
	private int isApprDoc;

	/**
	 * 문서종류 (품의, 보고, 통신..) VO
	 */
	private String apprDocCd;

	/**
	 * 문서타입 (0:사용자선택, 1:관리자선택) VO
	 */
	private int isApprSecurity;

	/**
	 * 문서보안 VO
	 */
	private int apprSecurityType;

	/**
	 * 위임 사용유무 (0:사용안함,1:사용) VO
	 */
	private int isEntrustAppr;

	/**
	 * 문서제목 고정여부 (0:사용안함,1:사용) VO
	 */
	private int isApprTitle;

	/**
	 * 파일첨부 사용여부 (0:사용안함,1:사용) VO
	 */
	private int isApprAttach;

	/**
	 * 처리담당자 사용여부 (0:사용안함,1:사용) VO
	 */
	private int authUse;

	/**
	 * 처리담당자 ID VO
	 */
	private String authUserId;
	
	/**
	 * 처리담당자 명 VO
	 */
	private String authUserName;
	
	/**
	 * 참조자 아이디 VO
	 */
	private String referenceId;
	
	/**
	 * 참조자 이름 VO
	 */
	private String referenceName;
	
	/**
	 * 참조자  VO
	 */
	private String referenceSet;
	
	/**
	 * 양식내용 버젼  VO
	 */
	private int formVersion;
	
	/**
	 * 신규버전   VO
	 */
	private int isNewVersion;
	
	/**
	 * 에디터 사용여부 (0:사용안함,1:사용) VO
	 */
	private int isApprEditor;
	
	/**
	 * 양식가이드내용 VO
	 */
	private String formGuide;
	
	/**
	 * 양식 에디터 작성 내용 데이터(HTML) VO
	 */
	private String formEditorData;
	
	/**
	 * 양식 동적 생성된 데이터 LAYOUT(JSON) VO
	 */
	private String formLayoutData;
	
	/**
	 * 기안작성 데이터 (JSON) VO
	 */
	private String formData;
	
	/**
	 * 변경사유 VO
	 */
	private String changeReason;
	
	/**
	 * 등록자 ID VO
	 */
	private String registerId;
	
	/**
	 * 등록자 이름 VO
	 */
	private String registerName;
	
	/**
	 * 등록 일시 VO
	 */
	private Date registDate;
	
	/**
	 * 수정자 ID VO
	 */
	private String updaterId;
	
	/**
	 * 수정자 이름 VO
	 */
	private String updaterName;
	
	/**
	 * 수정일시 VO
	 */
	private Date updateDate;
	
	/**
	 * 연계여부 VO
	 */
	private int isLinkUrl;

	/**
	 * 연계 url
	 */
	private String linkUrl;
	
	/**
	 * 연계 Data Type
	 */
	private int linkDataType;
	
	/*
	 * 첨부파일 링크 리스트. 
	 */
	private List<FileLink> fileLinkList;

	/*
	 * 에디터내의 파일링크 리스트. 
	 */
	private List<FileLink> editorFileLinkList;

	/*
	 * 첨부파일 리스트. 
	 */
	private List<FileData> fileDataList;

	/*
	 * 에디터내의 파일 리스트. 
	 */
	private List<FileData> editorFileDataList;
	
	/*
	 * 포탈 ID
	 */
	private String portalId;
	
	/*
	 * 시스템 ID
	 */
	private String systemId;
	
	/*
	 * 시스템 명
	 */
	private String systemName;
	
	/*
	 * 시스템 타입 (0:문서결재, 1:통합뷰방식연계, 2:결재 Action 연계, 3:결재 Action과 결재선 연계, 4:결재 Action, 결재선, 결재내용)
	 */
	private String systemType;
	
	/*
	 * 양식 사용 여부
	 */
	private int periodUsage;
	
	/*
	 * 양식 사용 시작일
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd hh:mm")
	private Date startDate;
	private String 	startDateStr;
	
	/*
	 * 양식 사용 종료일
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd hh:mm")
	private Date endDate;
	private String 	endDateStr;
	
	/*
	 * 즐겨찾기
	 */
	private String favoriteId;
	
	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getUserId() {
		return userId;
	}
	
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public List<FileLink> getEditorFileLinkList() {
		return editorFileLinkList;
	}

	public void setEditorFileLinkList(List<FileLink> editorFileLinkList) {
		this.editorFileLinkList = editorFileLinkList;
	}

	public List<FileData> getEditorFileDataList() {
		return editorFileDataList;
	}

	public void setEditorFileDataList(List<FileData> editorFileDataList) {
		this.editorFileDataList = editorFileDataList;
	}
	
	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getSystemType() {
		return systemType;
	}

	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public String getFormParentId() {
		return formParentId;
	}

	public void setFormParentId(String formParentId) {
		this.formParentId = formParentId;
	}

	public String getFormParentName() {
		return formParentName;
	}

	public void setFormParentName(String formParentName) {
		this.formParentName = formParentName;
	}

	public String getFormDesc() {
		return formDesc;
	}

	public void setFormDesc(String formDesc) {
		this.formDesc = formDesc;
	}

	public int getApprDocType() {
		return apprDocType;
	}

	public void setApprDocType(int apprDocType) {
		this.apprDocType = apprDocType;
	}

	public int getUsage() {
		return usage;
	}

	public void setUsage(int usage) {
		this.usage = usage;
	}

	public String getDefLineId() {
		return defLineId;
	}

	public void setDefLineId(String defLineId) {
		this.defLineId = defLineId;
	}

	public String getDefLineName() {
		return defLineName;
	}

	public void setDefLineName(String defLineName) {
		this.defLineName = defLineName;
	}
	
	public String getDefLineSet() {
		return defLineSet;
	}

	public void setDefLineSet(String defLineSet) {
		this.defLineSet = defLineSet;
	}

	public int getDefLineUse() {
		return defLineUse;
	}

	public void setDefLineUse(int defLineUse) {
		this.defLineUse = defLineUse;
	}
	
	public int getIsDefLineUpdate() {
		return isDefLineUpdate;
	}

	public void setIsDefLineUpdate(int isDefLineUpdate) {
		this.isDefLineUpdate = isDefLineUpdate;
	}

	public int getIsApprPeriod() {
		return isApprPeriod;
	}

	public void setIsApprPeriod(int isApprPeriod) {
		this.isApprPeriod = isApprPeriod;
	}

	public String getApprPeriodCd() {
		return apprPeriodCd;
	}

	public void setApprPeriodCd(String apprPeriodCd) {
		this.apprPeriodCd = apprPeriodCd;
	}

	public int getIsApprDoc() {
		return isApprDoc;
	}

	public void setIsApprDoc(int isApprDoc) {
		this.isApprDoc = isApprDoc;
	}

	public String getApprDocCd() {
		return apprDocCd;
	}

	public void setApprDocCd(String apprDocCd) {
		this.apprDocCd = apprDocCd;
	}

	public int getIsApprSecurity() {
		return isApprSecurity;
	}

	public void setIsApprSecurity(int isApprSecurity) {
		this.isApprSecurity = isApprSecurity;
	}
	
	public int getApprSecurityType() {
		return apprSecurityType;
	}

	public void setApprSecurityType(int apprSecurityType) {
		this.apprSecurityType = apprSecurityType;
	}

	public int getIsEntrustAppr() {
		return isEntrustAppr;
	}

	public void setIsEntrustAppr(int isEntrustAppr) {
		this.isEntrustAppr = isEntrustAppr;
	}

	public int getIsApprTitle() {
		return isApprTitle;
	}

	public void setIsApprTitle(int isApprTitle) {
		this.isApprTitle = isApprTitle;
	}

	public int getIsApprAttach() {
		return isApprAttach;
	}

	public void setIsApprAttach(int isApprAttach) {
		this.isApprAttach = isApprAttach;
	}

	public int getAuthUse() {
		return authUse;
	}

	public void setAuthUse(int authUse) {
		this.authUse = authUse;
	}

	public String getAuthUserId() {
		return authUserId;
	}

	public void setAuthUserId(String authUserId) {
		this.authUserId = authUserId;
	}

	public String getAuthUserName() {
		return authUserName;
	}

	public void setAuthUserName(String authUserName) {
		this.authUserName = authUserName;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public String getReferenceName() {
		return referenceName;
	}

	public void setReferenceName(String referenceName) {
		this.referenceName = referenceName;
	}

	public String getReferenceSet() {
		return referenceSet;
	}

	public void setReferenceSet(String referenceSet) {
		this.referenceSet = referenceSet;
	}

	public int getFormVersion() {
		return formVersion;
	}

	public void setFormVersion(int formVersion) {
		this.formVersion = formVersion;
	}
	
	public int getIsNewVersion() {
		return isNewVersion;
	}

	public void setIsNewVersion(int isNewVersion) {
		this.isNewVersion = isNewVersion;
	}

	public int getIsApprEditor() {
		return isApprEditor;
	}

	public void setIsApprEditor(int isApprEditor) {
		this.isApprEditor = isApprEditor;
	}

	public String getFormGuide() {
		return formGuide;
	}

	public void setFormGuide(String formGuide) {
		this.formGuide = formGuide;
	}

	public String getFormEditorData() {
		return formEditorData;
	}

	public void setFormEditorData(String formEditorData) {
		this.formEditorData = formEditorData;
	}

	public String getFormLayoutData() {
		return formLayoutData;
	}

	public void setFormLayoutData(String formLayoutData) {
		this.formLayoutData = formLayoutData;
	}
	
	public String getFormData() {
		return formData;
	}
	
	public String getChangeReason() {
		return changeReason;
	}

	public void setChangeReason(String changeReason) {
		this.changeReason = changeReason;
	}

	public void setFormData(String formData) {
		this.formData = formData;
	}
	
	public String getRegisterId() {
		return registerId;
	}

	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	public String getRegisterName() {
		return registerName;
	}

	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	public Date getRegistDate() {
		return registDate;
	}

	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<FileLink> getFileLinkList() {
		return fileLinkList;
	}

	public void setFileLinkList(List<FileLink> fileLinkList) {
		this.fileLinkList = fileLinkList;
	}

	public List<FileData> getFileDataList() {
		return fileDataList;
	}

	public void setFileDataList(List<FileData> fileDataList) {
		this.fileDataList = fileDataList;
	}

	public String getFavoriteId() {
		return favoriteId;
	}

	public void setFavoriteId(String favoriteId) {
		this.favoriteId = favoriteId;
	}

	public int getPeriodUsage() {
		return periodUsage;
	}

	public void setPeriodUsage(int periodUsage) {
		this.periodUsage = periodUsage;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getStartDateStr() {
		return startDateStr;
	}

	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}

	public String getEndDateStr() {
		return endDateStr;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}

	public int getIsLinkUrl() {
		return isLinkUrl;
	}

	public void setIsLinkUrl(int isLinkUrl) {
		this.isLinkUrl = isLinkUrl;
	}
	
	public int getLinkDataType() {
		return linkDataType;
	}

	public void setLinkDataType(int linkDataType) {
		this.linkDataType = linkDataType;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	
}
