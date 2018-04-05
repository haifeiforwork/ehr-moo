/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.forum.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.support.tagging.model.Tag;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 토론 주제 정보
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: FrDiscussion.java 16236 2011-08-18 02:48:22Z giljae $
 */
public class FrDiscussion extends BaseObject {
    /**
	 *
	 */
	private static final long serialVersionUID = -2063411667013016356L;

	/**
	 * 토론 주제 ID
	 */
	private String discussionId;

	/**
	 * 토론 카테고리 ID
	 */
    private String categoryId;

    /**
     * 토론 주제 제목
     */
    @NotEmpty
    @Size(min=1,max=500)
    private String title;

    /**
     * 토론 주제 내용 설명
     */
    @NotEmpty
    @Size(min=1,max=2000)
    private String contents;

    /**
     * 토론 관련 이미지 ID
     */
    @NotEmpty
    private String imageId;

    /**
     * 토론 주제 조회수
     */
    private Integer hitCount;

    /**
     * 토론주제 하위 토론글 갯수
     */
    private Integer itemCount;

    /**
     * 토론주제 하위에 등록된 모든 토론글의 토론의견(댓글)수의 총합
     */
    private Integer linereplyCount;

    /**
     * 토론 참여자수(사용자기준으로 토론주제등록자수 + 토론글등록자수+ 토론의견등록자수 + 토론글찬성자수 + 토론글반대자수 + 토론의견추천자수)
     */
    private Integer participationCount;

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

    private String updaterId;

    private String updaterName;

    private Date updateDate;
    
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
     * 참여자리스트
     */
    private List<FrParticipant> frParticipantList;
    
    /**
	 * 포털 ID
	 */
	private String portalId;
	
	/**
	 * 태그 url 경로
	 */
	private String pathUrl;
	
	
	/**
	 * 리스트 타입
	 */
	private String listType;
	
	/**
     * 메일 수
     */
    private int mailCount;
    
    /**
     * 블로그 수
     */
    private int mblogCount;
    
    /**
     * 태그
     */
    @NotEmpty
    private String tag;
    
    /**
     * 토론글 리스트
     */
    private List<FrItem> itemList;
    

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
     * iframe 모드 인지
     */
    private String docIframe;

	
	public String getDiscussionId() {
		return discussionId;
	}

	public void setDiscussionId(String discussionId) {
		this.discussionId = discussionId;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public Integer getHitCount() {
		return hitCount;
	}

	public void setHitCount(Integer hitCount) {
		this.hitCount = hitCount;
	}

	public Integer getItemCount() {
		return itemCount;
	}

	public void setItemCount(Integer itemCount) {
		this.itemCount = itemCount;
	}

	public Integer getLinereplyCount() {
		return linereplyCount;
	}

	public void setLinereplyCount(Integer linereplyCount) {
		this.linereplyCount = linereplyCount;
	}

	public Integer getParticipationCount() {
		return participationCount;
	}

	public void setParticipationCount(Integer participationCount) {
		this.participationCount = participationCount;
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

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}


	public List<FrParticipant> getFrParticipantList() {
		return frParticipantList;
	}

	public void setFrParticipantList(List<FrParticipant> frParticipantList) {
		this.frParticipantList = frParticipantList;
	}

	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	public String getPathUrl() {
		return pathUrl;
	}

	public void setPathUrl(String pathUrl) {
		this.pathUrl = pathUrl;
	}

	public String getListType() {
		return listType;
	}

	public void setListType(String listType) {
		this.listType = listType;
	}

	public int getMailCount() {
		return mailCount;
	}

	public void setMailCount(int mailCount) {
		this.mailCount = mailCount;
	}

	public int getMblogCount() {
		return mblogCount;
	}

	public void setMblogCount(int mblogCount) {
		this.mblogCount = mblogCount;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public List<FrItem> getItemList() {
		return itemList;
	}

	public void setItemList(List<FrItem> itemList) {
		this.itemList = itemList;
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

	public String getDocIframe() {
		return docIframe;
	}

	public void setDocIframe(String docIframe) {
		this.docIframe = docIframe;
	}

 
}