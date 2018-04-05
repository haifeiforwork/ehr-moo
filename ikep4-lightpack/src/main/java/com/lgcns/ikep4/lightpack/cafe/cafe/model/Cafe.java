/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.cafe.cafe.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.lgcns.ikep4.lightpack.cafe.board.model.BoardItem;
import com.lgcns.ikep4.lightpack.cafe.member.model.Member;
import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.FileLink;


/**
 * Cafe 객체
 * 
 * @author 김종철(happyi1018@nate.com)
 * @version $Id: Cafe.java 16489 2011-09-06 01:41:09Z giljae $
 */
public class Cafe extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 2809810610908372494L;

	public interface CreateCafe {
	} // Create group을 선언

	public interface UpdateCafe {
	} // Update group을 선언

	public interface UpdateCafeIntro {
	} // Update group을 선언

	private List<Member> memberList;

	/**
	 * 워크스페이스 ID
	 */
	private String cafeId;

	/**
	 * 워크스페이스 타입(O:조직, C:Cop, T:TFT, I:Informal)
	 */
	private String typeId;

	/**
	 * 포탈Id
	 */
	private String portalId;

	/**
	 * 카테고리 ID( 팀 Cafe가 아닌 경우)
	 */
	private String categoryId;

	/**
	 * 워크스페이스 명
	 */
	@NotEmpty(groups = { CreateCafe.class, UpdateCafe.class })
	@Size(groups = { CreateCafe.class, UpdateCafe.class }, min = 0, max = 200)
	private String cafeName;

	/**
	 * 워크스페이스 소개
	 */
	@NotEmpty(groups = { CreateCafe.class, UpdateCafe.class })
	@Size(groups = { CreateCafe.class, UpdateCafe.class }, min = 0, max = 500)
	private String description;

	/**
	 * Cafe 소개화면편집
	 */
	@NotEmpty(groups = UpdateCafeIntro.class)
	private String cafeIntroduction;

	/**
	 * 개설일
	 */
	private Date openDate;

	/**
	 * 폐쇄일
	 */
	private Date closeDate;

	/**
	 * 상태(WO:개설대기, O:개설, WC:폐쇄대기, C:폐쇄)
	 */
	private String cafeStatus;

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

	private String layoutId;

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

	private String typeEnglishName;

	/**
	 * 타입 설명
	 */
	private String typeDescription;

	private int isOrganization;

	/**
	 * 카테고리명
	 */
	private String categoryName;

	private String categoryEnglishName;

	/**
	 * 조직코드(조직타입 워크스페이스)
	 */
	private String groupId;

	/**
	 * 조직명
	 */
	private String groupName;

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
	 * 조직 부모 아이디
	 */
	private int childGroupCount;

	/**
	 * 조직 fullPath
	 */
	private String fullPath;

	private String fullEnglishPath;

	/**
	 * 조직장 여부
	 */
	private String leaderFlag;

	/**
	 * 조직장 아이디
	 */
	private String groupLeaderId;

	/**
	 * 조직장 명
	 */
	private String groupLeaderName;

	private String groupLeaderEnglishName;

	/**
	 * 멤버아이디
	 */
	private String memberId;

	private List<String> memberIds;

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
	 * 게시물수
	 */
	private int boardItemCount;

	/**
	 * 방문자수
	 */
	private int visitCount;

	/**
	 * 일자별
	 */
	private String baseDate;

	/**
	 * 시샵아이디
	 */
	private String sysopId;

	/**
	 * 시샵이름
	 */
	private String sysopName;

	private String sysopEnglishName;

	private String jobTitleName;

	private String jobTitleEnglishName;

	/**
	 * 워크스페이스 갯수
	 */
	private int countCafe;

	/**
	 * 그룹 하위 WS 갯수
	 */
	private int countChildCafe;

	private String memberLevel;

	private String memberLevelName;

	private int isDefault;

	private Date joinDate;

	private Date joinApplyDate;

	/**
	 * 태그
	 */
	private String tag;

	/**
	 * 태그
	 */
	private List<Tag> tagList;

	private String fileId;

	private String fileName;

	/** 첨부파일 링크 리스트. */
	private List<FileLink> fileLinkList;

	/** 첨부파일 리스트. */
	private List<FileData> fileDataList;

	/** 최신 게시물 리스트. */
	private List<BoardItem> boardItemList;

	private int cafePvi;

	/** 대표 이미지 */
	private String imageId;

	private String imageIdPre;

	/** 에디터내의 파일링크 리스트. */
	private List<FileLink> editorFileLinkList;

	/** 인터넷 브라우저 (0 : msie 이외 브라우저 , 1 : msie 브라우저 ActiveX editor 위한 체크 값) */
	private Integer msie;

	/**
	 * @return the cafeId
	 */
	public String getCafeId() {
		return cafeId;
	}

	/**
	 * @param cafeId the cafeId to set
	 */
	public void setCafeId(String cafeId) {
		this.cafeId = cafeId;
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
	 * @return the cafeName
	 */
	public String getCafeName() {
		return cafeName;
	}

	/**
	 * @param cafeName the cafeName to set
	 */
	public void setCafeName(String cafeName) {
		this.cafeName = cafeName;
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
	 * @return the cafeStatus
	 */
	public String getCafeStatus() {
		return cafeStatus;
	}

	/**
	 * @param cafeStatus the cafeStatus to set
	 */
	public void setCafeStatus(String cafeStatus) {
		this.cafeStatus = cafeStatus;
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
	 * @return the countCafe
	 */
	public int getCountCafe() {
		return countCafe;
	}

	/**
	 * @param countCafe the countCafe to set
	 */
	public void setCountCafe(int countCafe) {
		this.countCafe = countCafe;
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
	 * @return the countChildCafe
	 */
	public int getCountChildCafe() {
		return countChildCafe;
	}

	/**
	 * @param countChildCafe the countChildCafe to set
	 */
	public void setCountChildCafe(int countChildCafe) {
		this.countChildCafe = countChildCafe;
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
	 * @return the cafePvi
	 */
	public int getCafePvi() {
		return cafePvi;
	}

	/**
	 * @param cafePvi the cafePvi to set
	 */
	public void setCafePvi(int cafePvi) {
		this.cafePvi = cafePvi;
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

	/**
	 * @return the cafeIntroduction
	 */
	public String getCafeIntroduction() {
		return cafeIntroduction;
	}

	/**
	 * @param cafeIntroduction the cafeIntroduction to set
	 */
	public void setCafeIntroduction(String cafeIntroduction) {
		this.cafeIntroduction = cafeIntroduction;
	}

	/**
	 * @return the layoutId
	 */
	public String getLayoutId() {
		return layoutId;
	}

	/**
	 * @param layoutId the layoutId to set
	 */
	public void setLayoutId(String layoutId) {
		this.layoutId = layoutId;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

	public List<BoardItem> getBoardItemList() {
		return boardItemList;
	}

	public void setBoardItemList(List<BoardItem> boardItemList) {
		this.boardItemList = boardItemList;
	}

	public String getMemberLevelName() {
		return memberLevelName;
	}

	public void setMemberLevelName(String memberLevelName) {
		this.memberLevelName = memberLevelName;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public int getBoardItemCount() {
		return boardItemCount;
	}

	public void setBoardItemCount(int boardItemCount) {
		this.boardItemCount = boardItemCount;
	}

	public int getVisitCount() {
		return visitCount;
	}

	public void setVisitCount(int visitCount) {
		this.visitCount = visitCount;
	}

	public String getBaseDate() {
		return baseDate;
	}

	public void setBaseDate(String baseDate) {
		this.baseDate = baseDate;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getImageIdPre() {
		return imageIdPre;
	}

	public void setImageIdPre(String imageIdPre) {
		this.imageIdPre = imageIdPre;
	}

	public Date getJoinApplyDate() {
		return joinApplyDate;
	}

	public void setJoinApplyDate(Date joinApplyDate) {
		this.joinApplyDate = joinApplyDate;
	}

	public String getFullEnglishPath() {
		return fullEnglishPath;
	}

	public void setFullEnglishPath(String fullEnglishPath) {
		this.fullEnglishPath = fullEnglishPath;
	}

	public List<FileLink> getEditorFileLinkList() {
		return editorFileLinkList;
	}

	public void setEditorFileLinkList(List<FileLink> editorFileLinkList) {
		this.editorFileLinkList = editorFileLinkList;
	}

	public Integer getMsie() {
		return msie;
	}

	public void setMsie(Integer msie) {
		this.msie = msie;
	}

}
