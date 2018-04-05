/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.workspace.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.collpack.collaboration.admin.model.WorkspaceType;
import com.lgcns.ikep4.collpack.collaboration.alliance.model.Alliance;
import com.lgcns.ikep4.collpack.collaboration.member.model.Member;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.FileLink;


/**
 * Workspace 객체
 * 
 * @author 김종철(happyi1018@nate.com)
 * @version $Id: Workspace.java 16487 2011-09-06 01:34:40Z giljae $
 */
public class Workspace extends BaseObject {

	public interface CreateWorkspace {
	} // Create group을 선언

	public interface UpdateWorkspace {
	} // Update group을 선언

	public interface UpdateWorkspaceIntro {
	} // Update group을 선언

	private static final long serialVersionUID = 3758009831242236196L;

	// 멤버목록
	private List<Member> memberList;

	// WS 유형 목록
	private List<WorkspaceType> workspaceTypeList;

	// 동맹목록
	private List<Alliance> allianceList;

	/**
	 * 워크스페이스 ID
	 */
	private String workspaceId;

	/**
	 * 워크스페이스 타입(O:조직, C:Cop, T:TFT, I:Informal)
	 */
	private String typeId;

	/**
	 * 포탈Id
	 */
	private String portalId;

	/**
	 * 카테고리 ID( 팀 Workspace가 아닌 경우)
	 */
	private String categoryId;

	/**
	 * 워크스페이스 명
	 */
	@NotEmpty(groups = { CreateWorkspace.class, UpdateWorkspace.class })
	@Size(groups = { CreateWorkspace.class, UpdateWorkspace.class }, min = 0, max = 200)
	private String workspaceName;

	/**
	 * 팀 ID(팀 Workspace인 경우)
	 */
	private String teamId;

	/**
	 * 워크스페이스 소개
	 */
	@NotEmpty(groups = { CreateWorkspace.class, UpdateWorkspace.class })
	@Size(groups = { CreateWorkspace.class, UpdateWorkspace.class }, min = 0, max = 500)
	private String description;

	/**
	 * Workspace 소개화면편집
	 */
	// @NotEmpty(groups = UpdateWorkspaceIntro.class)
	private String introduction;

	/**
	 * 개설 타입( 0 : 사용자 개설, 1 : 관리자 개설)
	 */
	private int openType;

	/**
	 * 개설일
	 */
	private Date openDate;

	/**
	 * 폐쇄일
	 */
	private Date closeDate;

	/**
	 * 개설신청일
	 */
	private Date openApplyDate;

	/**
	 * 폐쇄신청일
	 */
	private Date closeApplyDate;

	/**
	 * 상태(WO:개설대기, O:개설, WC:폐쇄대기, C:폐쇄)
	 */
	private String workspaceStatus;

	/**
	 * 등록자 ID(개설 신청자)
	 */
	private String registerId;

	/**
	 * 등록자명(개설자)
	 */
	private String registerName;

	/**
	 * 등록자 영문명
	 */
	private String registerEnglishName;

	/**
	 * 등록일(개설일)
	 */
	private Date registDate;

	/**
	 * 수정자 ID
	 */
	private String updaterId;

	/**
	 * 수정자 명
	 */
	private String updaterName;

	/**
	 * 수정자 영문명
	 */
	private String updaterEnglishName;

	/**
	 * 수정일
	 */
	private Date updateDate;

	/***********************
	 * 추가 사항
	 ***********************/
	/**
	 * 날짜 String 변환용
	 */
	private String strDate;

	/**
	 * 워크스페이스 타입명
	 */
	private String typeName;

	/**
	 * 타입 영문명
	 */
	private String typeEnglishName;

	/**
	 * 타입 설명
	 */
	private String typeDescription;

	/**
	 * 타입 조직여부 1: 조직 , 0 : 일반
	 */
	private int isOrganization;

	/**
	 * 카테고리명
	 */
	private String categoryName;

	/**
	 * 카테고리 영문명
	 */
	private String categoryEnglishName;

	/**
	 * 조직코드(조직타입 워크스페이스)
	 */
	private String groupId;

	/**
	 * 조직명
	 */
	private String groupName;

	/**
	 * 조직 영문명
	 */
	private String groupEnglishName;

	/**
	 * 조직 타입 아이디
	 */
	private String groupTypeId;

	/**
	 * 조직 부모 아이디
	 */
	private String parentGroupId;

	/**
	 * 하위 조직 수
	 */
	private int childGroupCount;

	/**
	 * 조직 fullPath
	 */
	private String fullPath;

	/**
	 * 조직장 여부
	 */
	private String leaderFlag;

	/**
	 * 리더 아이디
	 */
	private String groupLeaderId;

	/**
	 * 리더 명
	 */
	private String groupLeaderName;

	/**
	 * 리더 영문명
	 */
	private String groupLeaderEnglishName;

	/**
	 * 멤버아이디
	 */
	private String memberId;

	/**
	 * 멤버 IDS
	 */
	private List<String> memberIds;

	/**
	 * 준회원 IDS
	 */
	private List<String> associateIds;

	/**
	 * 멤버수
	 */
	private int memberCount;

	/**
	 * 준회원수
	 */
	private int associateMemberCount;

	/**
	 * 시샵아이디
	 */
	private String sysopId;

	/**
	 * 시샵이름
	 */
	private String sysopName;

	/**
	 * 시샵 영문명
	 */
	private String sysopEnglishName;

	/**
	 * 직급명
	 */
	private String jobTitleName;

	/**
	 * 직급 영문명
	 */
	private String jobTitleEnglishName;

	/**
	 * 워크스페이스 갯수
	 */
	private int countWorkspace;

	/**
	 * 그룹 하위 WS 갯수
	 */
	private int countChildWorkspace;

	/**
	 * 회원레벨
	 */
	private String memberLevel;

	/**
	 * WS 디폴트 여부
	 */
	private int isDefault;

	/**
	 * 회원가입일
	 */
	private Date joinDate;

	/**
	 * 태그
	 */
	private String tag;

	/**
	 * 태그
	 */
	private List<Tag> tagList;

	/**
	 * 소개 이미지
	 */
	private String fileId;

	private String fileName;

	/** 첨부파일 링크 리스트. */
	private List<FileLink> fileLinkList;

	/** 첨부파일 리스트. */
	private List<FileData> fileDataList;

	/** 에디터내의 파일링크 리스트. */
	private List<FileLink> editorFileLinkList;

	/**
	 * WS 통계
	 */
	private int workspacePvi;

	/** 인터넷 브라우저 (0 : msie 이외 브라우저 , 1 : msie 브라우저 ActiveX editor 위한 체크 값) */
	private Integer msie;
	
	private String presentWorkspaceId = "";
	
	/**
	 * @return the workspaceId
	 */
	public String getWorkspaceId() {
		return workspaceId;
	}

	/**
	 * @param workspaceId the workspaceId to set
	 */
	public void setWorkspaceId(String workspaceId) {
		this.workspaceId = workspaceId;
	}

	/**
	 * @return the typeId
	 */
	public String getTypeId() {
		return typeId;
	}

	/**
	 * @param typeId the typeId to set
	 */
	public void setTypeId(String typeId) {
		this.typeId = typeId;
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
	 * @return the workspaceName
	 */
	public String getWorkspaceName() {
		return workspaceName;
	}

	/**
	 * @param workspaceName the workspaceName to set
	 */
	public void setWorkspaceName(String workspaceName) {
		this.workspaceName = workspaceName;
	}

	/**
	 * @return the teamId
	 */
	public String getTeamId() {
		return teamId;
	}

	/**
	 * @param teamId the teamId to set
	 */
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the introduction
	 */
	public String getIntroduction() {
		return introduction;
	}

	/**
	 * @param introduction the introduction to set
	 */
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	/**
	 * @return the openType
	 */
	public int getOpenType() {
		return openType;
	}

	/**
	 * @param openType the openType to set
	 */
	public void setOpenType(int openType) {
		this.openType = openType;
	}

	/**
	 * @return the openDate
	 */
	public Date getOpenDate() {
		return openDate;
	}

	/**
	 * @param openDate the openDate to set
	 */
	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	/**
	 * @return the closeDate
	 */
	public Date getCloseDate() {
		return closeDate;
	}

	/**
	 * @param closeDate the closeDate to set
	 */
	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

	/**
	 * @return the openApplyDate
	 */
	public Date getOpenApplyDate() {
		return openApplyDate;
	}

	/**
	 * @param openApplyDate the openApplyDate to set
	 */
	public void setOpenApplyDate(Date openApplyDate) {
		this.openApplyDate = openApplyDate;
	}

	/**
	 * @return the closeApplyDate
	 */
	public Date getCloseApplyDate() {
		return closeApplyDate;
	}

	/**
	 * @param closeApplyDate the closeApplyDate to set
	 */
	public void setCloseApplyDate(Date closeApplyDate) {
		this.closeApplyDate = closeApplyDate;
	}

	/**
	 * @return the workspaceStatus
	 */
	public String getWorkspaceStatus() {
		return workspaceStatus;
	}

	/**
	 * @param workspaceStatus the workspaceStatus to set
	 */
	public void setWorkspaceStatus(String workspaceStatus) {
		this.workspaceStatus = workspaceStatus;
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
	 * @return the typeName
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * @param typeName the typeName to set
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
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
	 * @return the groupId
	 */
	public String getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * @return the memberId
	 */
	public String getMemberId() {
		return memberId;
	}

	/**
	 * @param memberId the memberId to set
	 */
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	/**
	 * @return the memberCount
	 */
	public int getMemberCount() {
		return memberCount;
	}

	/**
	 * @param memberCount the memberCount to set
	 */
	public void setMemberCount(int memberCount) {
		this.memberCount = memberCount;
	}

	/**
	 * @return the sysopId
	 */
	public String getSysopId() {
		return sysopId;
	}

	/**
	 * @param sysopId the sysopId to set
	 */
	public void setSysopId(String sysopId) {
		this.sysopId = sysopId;
	}

	/**
	 * @return the sysopName
	 */
	public String getSysopName() {
		return sysopName;
	}

	/**
	 * @param sysopName the sysopName to set
	 */
	public void setSysopName(String sysopName) {
		this.sysopName = sysopName;
	}

	/**
	 * @return the countWorkspace
	 */
	public int getCountWorkspace() {
		return countWorkspace;
	}

	/**
	 * @param countWorkspace the countWorkspace to set
	 */
	public void setCountWorkspace(int countWorkspace) {
		this.countWorkspace = countWorkspace;
	}

	/**
	 * @return the memberIds
	 */
	public List<String> getMemberIds() {
		return memberIds;
	}

	/**
	 * @param memberIds the memberIds to set
	 */
	public void setMemberIds(List<String> memberIds) {
		this.memberIds = memberIds;
	}

	/**
	 * @return the isOrganization
	 */
	public int getIsOrganization() {
		return isOrganization;
	}

	/**
	 * @param isOrganization the isOrganization to set
	 */
	public void setIsOrganization(int isOrganization) {
		this.isOrganization = isOrganization;
	}

	/**
	 * @return the memberLevel
	 */
	public String getMemberLevel() {
		return memberLevel;
	}

	/**
	 * @param memberLevel the memberLevel to set
	 */
	public void setMemberLevel(String memberLevel) {
		this.memberLevel = memberLevel;
	}

	/**
	 * @return the joinDate
	 */
	public Date getJoinDate() {
		return joinDate;
	}

	/**
	 * @param joinDate the joinDate to set
	 */
	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	/**
	 * @return the isDefault
	 */
	public int getIsDefault() {
		return isDefault;
	}

	/**
	 * @param isDefault the isDefault to set
	 */
	public void setIsDefault(int isDefault) {
		this.isDefault = isDefault;
	}

	/**
	 * @return the strDate
	 */
	public String getStrDate() {
		return strDate;
	}

	/**
	 * @param strDate the strDate to set
	 */
	public void setStrDate(String strDate) {
		this.strDate = strDate;
	}

	/**
	 * @return the groupLeaderName
	 */
	public String getGroupLeaderName() {
		return groupLeaderName;
	}

	/**
	 * @param groupLeaderName the groupLeaderName to set
	 */
	public void setGroupLeaderName(String groupLeaderName) {
		this.groupLeaderName = groupLeaderName;
	}

	/**
	 * @return the fullPath
	 */
	public String getFullPath() {
		return fullPath;
	}

	/**
	 * @param fullPath the fullPath to set
	 */
	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	/**
	 * @return the leaderFlag
	 */
	public String getLeaderFlag() {
		return leaderFlag;
	}

	/**
	 * @param leaderFlag the leaderFlag to set
	 */
	public void setLeaderFlag(String leaderFlag) {
		this.leaderFlag = leaderFlag;
	}

	/**
	 * @return the groupTypeId
	 */
	public String getGroupTypeId() {
		return groupTypeId;
	}

	/**
	 * @param groupTypeId the groupTypeId to set
	 */
	public void setGroupTypeId(String groupTypeId) {
		this.groupTypeId = groupTypeId;
	}

	/**
	 * @return the parentGroupId
	 */
	public String getParentGroupId() {
		return parentGroupId;
	}

	/**
	 * @param parentGroupId the parentGroupId to set
	 */
	public void setParentGroupId(String parentGroupId) {
		this.parentGroupId = parentGroupId;
	}

	/**
	 * @return the childGroupCount
	 */
	public int getChildGroupCount() {
		return childGroupCount;
	}

	/**
	 * @param childGroupCount the childGroupCount to set
	 */
	public void setChildGroupCount(int childGroupCount) {
		this.childGroupCount = childGroupCount;
	}

	/**
	 * @return the groupLeaderId
	 */
	public String getGroupLeaderId() {
		return groupLeaderId;
	}

	/**
	 * @param groupLeaderId the groupLeaderId to set
	 */
	public void setGroupLeaderId(String groupLeaderId) {
		this.groupLeaderId = groupLeaderId;
	}

	/**
	 * @return the memberList
	 */
	public List<Member> getMemberList() {
		return memberList;
	}

	/**
	 * @param memberList the memberList to set
	 */
	public void setMemberList(List<Member> memberList) {
		this.memberList = memberList;
	}

	/**
	 * @return the workspaceTypeList
	 */
	public List<WorkspaceType> getWorkspaceTypeList() {
		return workspaceTypeList;
	}

	/**
	 * @param workspaceTypeList the workspaceTypeList to set
	 */
	public void setWorkspaceTypeList(List<WorkspaceType> workspaceTypeList) {
		this.workspaceTypeList = workspaceTypeList;
	}

	/**
	 * @return the allianceList
	 */
	public List<Alliance> getAllianceList() {
		return allianceList;
	}

	/**
	 * @param allianceList the allianceList to set
	 */
	public void setAllianceList(List<Alliance> allianceList) {
		this.allianceList = allianceList;
	}

	/**
	 * @return the associateIds
	 */
	public List<String> getAssociateIds() {
		return associateIds;
	}

	/**
	 * @param associateIds the associateIds to set
	 */
	public void setAssociateIds(List<String> associateIds) {
		this.associateIds = associateIds;
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
	 * @return the fileId
	 */
	public String getFileId() {
		return fileId;
	}

	/**
	 * @param fileId the fileId to set
	 */
	public void setFileId(String fileId) {
		this.fileId = fileId;
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
	 * @return the countChildWorkspace
	 */
	public int getCountChildWorkspace() {
		return countChildWorkspace;
	}

	/**
	 * @param countChildWorkspace the countChildWorkspace to set
	 */
	public void setCountChildWorkspace(int countChildWorkspace) {
		this.countChildWorkspace = countChildWorkspace;
	}

	/**
	 * @return the associateMemberCount
	 */
	public int getAssociateMemberCount() {
		return associateMemberCount;
	}

	/**
	 * @param associateMemberCount the associateMemberCount to set
	 */
	public void setAssociateMemberCount(int associateMemberCount) {
		this.associateMemberCount = associateMemberCount;
	}

	/**
	 * @return the typeDescription
	 */
	public String getTypeDescription() {
		return typeDescription;
	}

	/**
	 * @param typeDescription the typeDescription to set
	 */
	public void setTypeDescription(String typeDescription) {
		this.typeDescription = typeDescription;
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
	 * @return the groupEnglishName
	 */
	public String getGroupEnglishName() {
		return groupEnglishName;
	}

	/**
	 * @param groupEnglishName the groupEnglishName to set
	 */
	public void setGroupEnglishName(String groupEnglishName) {
		this.groupEnglishName = groupEnglishName;
	}

	/**
	 * @return the groupLeaderEnglishName
	 */
	public String getGroupLeaderEnglishName() {
		return groupLeaderEnglishName;
	}

	/**
	 * @param groupLeaderEnglishName the groupLeaderEnglishName to set
	 */
	public void setGroupLeaderEnglishName(String groupLeaderEnglishName) {
		this.groupLeaderEnglishName = groupLeaderEnglishName;
	}

	/**
	 * @return the sysopEnglishName
	 */
	public String getSysopEnglishName() {
		return sysopEnglishName;
	}

	/**
	 * @param sysopEnglishName the sysopEnglishName to set
	 */
	public void setSysopEnglishName(String sysopEnglishName) {
		this.sysopEnglishName = sysopEnglishName;
	}

	/**
	 * @return the workspacePvi
	 */
	public int getWorkspacePvi() {
		return workspacePvi;
	}

	/**
	 * @param workspacePvi the workspacePvi to set
	 */
	public void setWorkspacePvi(int workspacePvi) {
		this.workspacePvi = workspacePvi;
	}

	/**
	 * @return the typeEnglishName
	 */
	public String getTypeEnglishName() {
		return typeEnglishName;
	}

	/**
	 * @param typeEnglishName the typeEnglishName to set
	 */
	public void setTypeEnglishName(String typeEnglishName) {
		this.typeEnglishName = typeEnglishName;
	}

	/**
	 * @return the categoryEnglishName
	 */
	public String getCategoryEnglishName() {
		return categoryEnglishName;
	}

	/**
	 * @param categoryEnglishName the categoryEnglishName to set
	 */
	public void setCategoryEnglishName(String categoryEnglishName) {
		this.categoryEnglishName = categoryEnglishName;
	}

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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the msie
	 */
	public Integer getMsie() {
		return msie;
	}

	/**
	 * @param msie the msie to set
	 */
	public void setMsie(Integer msie) {
		this.msie = msie;
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

	public String getPresentWorkspaceId() {
		return presentWorkspaceId;
	}

	public void setPresentWorkspaceId(String presentWorkspaceId) {
		this.presentWorkspaceId = presentWorkspaceId;
	}

}
