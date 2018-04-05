/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.forum.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.lgcns.ikep4.support.fileupload.model.FileLink;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * 토론글 정보
 * 
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: FrItem.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class FrItem extends BaseObject {
	/**
	 *
	 */
	private static final long serialVersionUID = -6887843294016003469L;

	/**
	 * 토론글 아이템 ID
	 */
	private String itemId;

	/**
	 * 토론 주제 ID
	 */
	private String discussionId;

	/**
	 * 토론글 제목
	 */
	@NotEmpty
	@Size(min = 1, max = 500)
	private String title;

	/**
	 * 베스트 토론글 여부( 0 : 일반 토론글, 1 : 베스트 토론글)
	 */
	private int bestItem;

	/**
	 * 토론글 조회수
	 */
	private int hitCount;

	/**
	 * 토론글 하위 토론의견(댓글) 수
	 */
	private int linereplyCount;

	/**
	 * 토론글 찬성수
	 */
	private int agreementCount;

	/**
	 * 토론글 반대수
	 */
	private int oppositionCount;

	/**
	 * 토론글 즐겨찾기수
	 */
	private int favoriteCount;

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
	 * 수정자 ID
	 */
	private String updaterId;

	/**
	 * 수정자 이름
	 */
	private String updaterName;

	/**
	 * 수정일시
	 */
	private Date updateDate;

	/**
	 * 토론글 내용
	 */
	@NotEmpty
	private String contents;

	/**
	 * 팀이름
	 */
	private String teamName;

	/**
	 * 직책이름
	 */
	private String jobTitleName;

	/**
	 * 카테고리 이름
	 */
	private String categoryName;

	/**
	 * 포털 ID
	 */
	private String portalId;

	/**
	 * 개수
	 */
	private int count;

	/**
	 * 베스트 글 개수
	 */
	private int bestCount;

	/**
	 * 태그 url 경로
	 */
	private String pathUrl;

	/**
	 * 주제
	 */
	private FrDiscussion frDiscussion;

	/**
	 * 태그
	 */
	@NotEmpty
	private String tag;

	/**
	 * 참가자 리스트
	 */
	private List<FrParticipant> participantList;

	/**
	 * 태그 리스트
	 */
	private List<Tag> tagList;

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
	 * ckedit 파일 전송
	 */
	private List<FileLink> editorFileLinkList;

	/** 인터넷 브라우저 (0 : msie 이외 브라우저 , 1 : msie 브라우저 ActiveX editor 위한 체크 값) */
	private Integer msie;

	/**
	 * 사진파일 경로 (큰이미지)
	 */
	private String picturePath;

	/**
	 * 사진파일 경로 (작은이미지)
	 */
	private String profilePicturePath;

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getDiscussionId() {
		return discussionId;
	}

	public void setDiscussionId(String discussionId) {
		this.discussionId = discussionId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getBestItem() {
		return bestItem;
	}

	public void setBestItem(int bestItem) {
		this.bestItem = bestItem;
	}

	public int getHitCount() {
		return hitCount;
	}

	public void setHitCount(int hitCount) {
		this.hitCount = hitCount;
	}

	public int getLinereplyCount() {
		return linereplyCount;
	}

	public void setLinereplyCount(int linereplyCount) {
		this.linereplyCount = linereplyCount;
	}

	public int getAgreementCount() {
		return agreementCount;
	}

	public void setAgreementCount(int agreementCount) {
		this.agreementCount = agreementCount;
	}

	public int getOppositionCount() {
		return oppositionCount;
	}

	public void setOppositionCount(int oppositionCount) {
		this.oppositionCount = oppositionCount;
	}

	public int getFavoriteCount() {
		return favoriteCount;
	}

	public void setFavoriteCount(int favoriteCount) {
		this.favoriteCount = favoriteCount;
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

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getJobTitleName() {
		return jobTitleName;
	}

	public void setJobTitleName(String jobTitleName) {
		this.jobTitleName = jobTitleName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getBestCount() {
		return bestCount;
	}

	public void setBestCount(int bestCount) {
		this.bestCount = bestCount;
	}

	public String getPathUrl() {
		return pathUrl;
	}

	public void setPathUrl(String pathUrl) {
		this.pathUrl = pathUrl;
	}

	public FrDiscussion getFrDiscussion() {
		return frDiscussion;
	}

	public void setFrDiscussion(FrDiscussion frDiscussion) {
		this.frDiscussion = frDiscussion;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public List<FrParticipant> getParticipantList() {
		return participantList;
	}

	public void setParticipantList(List<FrParticipant> participantList) {
		this.participantList = participantList;
	}

	public List<Tag> getTagList() {
		return tagList;
	}

	public void setTagList(List<Tag> tagList) {
		this.tagList = tagList;
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

}