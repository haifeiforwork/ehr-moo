package com.lgcns.ikep4.support.tagging.model;

import java.util.Date;
import java.util.List;

import com.lgcns.ikep4.framework.core.model.BaseObject;

public class Tag extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 6543265168048625085L;

	/**
	 * 동일한 태그의 사용 빈도수에 따라 태그 클라우드의 단계를 표시한다.( 1 : 1단계, 2 : 2단계, 3 : 3단계, 4 :
	 * 4단계, 5 : 5단계)
	 */
	private Integer displayStep;

	/**
	 * 동일 태그의 사용빈도수로 각 단계별로 지정할 수 있다. 1단계 : 3 ( 0개 이상 ~ 3개 이하) 2단계 : 6 ( 4개 이상 ~
	 * 6개 이하) 3단계 : 9 ( 7개 이상 ~ 9개 이하) 4단계 : 12 ( 10개 이상 ~ 12개 이하) 5단계 : 15 (
	 * 13개 이상 ~ 15개 이하)
	 */
	private Integer tagFrequency;

	/**
	 * 포탈 ID
	 */
	private String portalId;

	/**
	 * 태그 ID
	 */
	private String tagId;

	/**
	 * 태그 이름
	 */
	private String tagName;

	/**
	 * 등록자 ID
	 */
	private String registerId;

	/**
	 * 등록자 이름
	 */
	private String registerName;

	/**
	 * 등록일시
	 */
	private Date registDate;

	/**
	 * 태그 아이템 ID(태그가 매핑된 게시물 등의 ID)
	 */
	private String tagItemId;

	/**
	 * 태그가 매핑된 아이템의 타입(BD, WW, QA 등 각 모듈 약칭)
	 */
	private String tagItemType;

	/**
	 * 태그가 매핑된 아이템의 타입(BD, WW, QA 등 각 모듈 약칭)
	 */
	private String tagItemTypeDisplayName;

	/**
	 * 태그 아이템 이름(예 : 게시글 제목)
	 */
	private String tagItemName;

	/**
	 * 태그 아이템 view 화면 URL
	 */
	private String tagItemUrl;

	/**
	 * 게시판 가져올 끝 수
	 */
	private int endRowIndex;

	/**
	 * 게시판 가져올 처음 수
	 */
	private int startRowIndex;

	/**
	 * 정렬 방법
	 */
	private String tagOrder;

	/**
	 * 리스트 형태
	 */
	private String listType;

	/**
	 * 각 단위별 가져올 개수
	 */
	private int unitRowIndex;

	/**
	 * 태그item type 들
	 */
	private String[] tagItemTypes;

	/**
	 * 태그 ID List
	 */
	private List<String> tagIdList;

	/**
	 * 현재 페이지
	 */
	private int pageIndex = 1;

	/**
	 * 현재 페이지 개수
	 */
	private int pagePer = 10;

	/**
	 * 정렬
	 */
	private int sortOrder = 0;

	/**
	 * 태그 아이템 서브 타입[ Coll 인 경우 개별 Coll. ID,QnA 인 경우 질문(Q), 답변(A) ]
	 */
	private String tagItemSubType;

	/**
	 * Item 내용
	 */
	private String tagItemContents;

	/**
	 * 사용자 ID 리스트
	 */
	private List<String> userIdList;

	/**
	 * 제한 날짜
	 */
	private String limitDate;

	/**
	 * 팀 타입ID
	 */
	private String teamTypeId;

	/**
	 * 팀 타입이름
	 */
	private String teamTypeName;

	/**
	 * 태그이름리스트
	 */
	private List<String> tagItemIdList;

	/**
	 * 개수
	 */
	private int count;

	/**
	 * 팀이름
	 */
	private String teamName;

	/**
	 * 사용자 이름
	 */
	private String userName;

	/**
	 * 메일 주소
	 */
	private String mail;

	/**
	 * 모바일 주소
	 */
	private String mobile;

	/**
	 * 직책
	 */
	private String jobDutyName;

	/**
	 * 타임존 시각
	 */
	private Date timeZoneDate;

	/**
	 * 게시물성 자료만 - 프로파일 자료는 제외
	 */
	private String isBbs;

	/**
	 * 그룹지을때 타입
	 */
	private String groupType;

	/**
	 * 조회할 타입과 검색할 모듈타입이 틀릴때 사용
	 */
	private String asTagItemType;

	/**
	 * 서브타입이 있는 Item 만
	 */
	private String isSubType;

	/**
	 * 서브타입이 없는 item 만
	 */
	private String isNotSubType;

	/**
	 * 영문 이름
	 */
	private String userEnglishName;

	/**
	 * 영문 팀명
	 */
	private String teamEnglishName;

	/**
	 * 영문직책
	 */
	private String jobTitleEnglishName;

	/**
	 * 직급
	 */
	private String jobTitleName;

	/**
	 * 자신인지
	 */
	private int isMy;

	/**
	 * 사용자의 프로필 사진 경로 (큰이미지)
	 */
	private String picturePath;
	
	/**
	 * 태그이름리스트
	 */
	private List<Tag> tagList;

	/**
	 * 사용자의 프로필 사진 경로(작은이미지)
	 */
	private String profilePicturePath;

	public Integer getDisplayStep() {
		return displayStep;
	}

	public void setDisplayStep(Integer displayStep) {
		this.displayStep = displayStep;
	}

	public Integer getTagFrequency() {
		return tagFrequency;
	}

	public void setTagFrequency(Integer tagFrequency) {
		this.tagFrequency = tagFrequency;
	}

	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	public String getTagId() {
		return tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
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

	public String getTagItemId() {
		return tagItemId;
	}

	public void setTagItemId(String tagItemId) {
		this.tagItemId = tagItemId;
	}

	public String getTagItemType() {
		return tagItemType;
	}

	public void setTagItemType(String tagItemType) {
		this.tagItemType = tagItemType;
	}

	public String getTagItemName() {
		return tagItemName;
	}

	public void setTagItemName(String tagItemName) {
		this.tagItemName = tagItemName;
	}

	public String getTagItemUrl() {
		return tagItemUrl;
	}

	public void setTagItemUrl(String tagItemUrl) {
		this.tagItemUrl = tagItemUrl;
	}

	public int getEndRowIndex() {
		return endRowIndex;
	}

	public void setEndRowIndex(int endRowIndex) {
		this.endRowIndex = endRowIndex;
	}

	public int getStartRowIndex() {
		return startRowIndex;
	}

	public void setStartRowIndex(int startRowIndex) {
		this.startRowIndex = startRowIndex;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getListType() {
		return listType;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPagePer() {
		return pagePer;
	}

	public void setPagePer(int pagePer) {
		this.pagePer = pagePer;
	}

	public int getUnitRowIndex() {
		return unitRowIndex;
	}

	public void setUnitRowIndex(int unitRowIndex) {
		this.unitRowIndex = unitRowIndex;
	}

	public void setListType(String listType) {
		this.listType = listType;
	}

	public String getTagOrder() {
		return tagOrder;
	}

	public String[] getTagItemTypes() {
		return tagItemTypes;
	}

	public void setTagItemTypes(String[] tagItemTypes) {
		if (tagItemTypes != null) {
			this.tagItemTypes = new String[tagItemTypes.length];
			System.arraycopy(tagItemTypes, 0, this.tagItemTypes, 0,
					tagItemTypes.length);
		}

	}

	public void setTagOrder(String tagOrder) {
		this.tagOrder = tagOrder;
	}

	public List<String> getTagIdList() {
		return tagIdList;
	}

	public void setTagIdList(List<String> tagIdList) {
		this.tagIdList = tagIdList;
	}

	public String getTagItemSubType() {
		return tagItemSubType;
	}

	public void setTagItemSubType(String tagItemSubType) {
		this.tagItemSubType = tagItemSubType;
	}

	public String getTagItemContents() {
		return tagItemContents;
	}

	public void setTagItemContents(String tagItemContents) {
		this.tagItemContents = tagItemContents;
	}

	public List<String> getUserIdList() {
		return userIdList;
	}

	public void setUserIdList(List<String> userIdList) {
		this.userIdList = userIdList;
	}

	public String getLimitDate() {
		return limitDate;
	}

	public void setLimitDate(String limitDate) {
		this.limitDate = limitDate;
	}

	public String getTeamTypeId() {
		return teamTypeId;
	}

	public void setTeamTypeId(String teamTypeId) {
		this.teamTypeId = teamTypeId;
	}

	public String getTeamTypeName() {
		return teamTypeName;
	}

	public void setTeamTypeName(String teamTypeName) {
		this.teamTypeName = teamTypeName;
	}

	public List<String> getTagItemIdList() {
		return tagItemIdList;
	}

	public void setTagItemIdList(List<String> tagItemIdList) {
		this.tagItemIdList = tagItemIdList;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getJobDutyName() {
		return jobDutyName;
	}

	public void setJobDutyName(String jobDutyName) {
		this.jobDutyName = jobDutyName;
	}

	public Date getTimeZoneDate() {
		return timeZoneDate;
	}

	public void setTimeZoneDate(Date timeZoneDate) {
		this.timeZoneDate = timeZoneDate;
	}

	public String getIsBbs() {
		return isBbs;
	}

	public void setIsBbs(String isBbs) {
		this.isBbs = isBbs;
	}

	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	public String getAsTagItemType() {
		return asTagItemType;
	}

	public void setAsTagItemType(String asTagItemType) {
		this.asTagItemType = asTagItemType;
	}

	public String getIsSubType() {
		return isSubType;
	}

	public void setIsSubType(String isSubType) {
		this.isSubType = isSubType;
	}

	public String getIsNotSubType() {
		return isNotSubType;
	}

	public void setIsNotSubType(String isNotSubType) {
		this.isNotSubType = isNotSubType;
	}

	public String getUserEnglishName() {
		return userEnglishName;
	}

	public void setUserEnglishName(String userEnglishName) {
		this.userEnglishName = userEnglishName;
	}

	public String getTeamEnglishName() {
		return teamEnglishName;
	}

	public void setTeamEnglishName(String teamEnglishName) {
		this.teamEnglishName = teamEnglishName;
	}

	public String getJobTitleEnglishName() {
		return jobTitleEnglishName;
	}

	public void setJobTitleEnglishName(String jobTitleEnglishName) {
		this.jobTitleEnglishName = jobTitleEnglishName;
	}

	public String getJobTitleName() {
		return jobTitleName;
	}

	public void setJobTitleName(String jobTitleName) {
		this.jobTitleName = jobTitleName;
	}

	public int getIsMy() {
		return isMy;
	}

	public void setIsMy(int isMy) {
		this.isMy = isMy;
	}

	public String getPicturePath() {
		return picturePath;
	}

	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}

	public String getProfilePicturePath() {
		return profilePicturePath;
	}

	public void setProfilePicturePath(String profilePicturePath) {
		this.profilePicturePath = profilePicturePath;
	}

	public String getTagItemTypeDisplayName() {
		return tagItemTypeDisplayName;
	}

	public void setTagItemTypeDisplayName(String tagItemTypeDisplayName) {
		this.tagItemTypeDisplayName = tagItemTypeDisplayName;
	}

	public List<Tag> getTagList() {
		return tagList;
	}

	public void setTagList(List<Tag> tagList) {
		this.tagList = tagList;
	}

}