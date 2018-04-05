/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.ideation.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import java.util.Date;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 토론의견댓글 정보
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: IdLinereply.java 14269 2011-06-02 09:35:07Z loverfairy $
 */
public class IdLinereply extends BaseObject {
	
	/**
	 *
	 */
	private static final long serialVersionUID = -7878804729003250165L;

	/**
	 * 토론 의견(댓글) ID
	 */
	private String linereplyId;
	
	/**
	 *  토론글 아이템 ID
	 */
	private String itemId;
	
	/**
	 * 댓글 Thread의 그룹 ID
	 */
	private String linereplyGroupId;
	
	/**
	 * 부모 Id
	 */
	private String linereplyParentId;
	
	/**
	 * 같은 Thread 그룹에 속해있는 댓글들 간의 순서. 같은 Thread에서 단순 정렬 순서
	 */
	private int step = 0;
	
	/**
	 * 댓글 Thread 표시할때 들여쓰기의 숫자(댓글의 경우 최대 3depth로 제한)
	 */
	private int indentation = 0;

	/**
	 * 토론 의견 내용
	 */
	@NotEmpty
	@Size(min=1,max=1500)
	private String contents;

	/**
	 * 등록자 Id
	 */
	private String registerId;

	/**
	 * 등록자 이름
	 */
	private String registerName;

	/**
	 * 등록날짜
	 */
	private Date registDate;
	
	/**
	 *  수정자 Id
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
	 * 토론의견(댓글) 삭제여부
	 */
	private int linereplyDelete;
	
	/**
	 * 베스트 토론의견(댓글) 여부( 0 : 일반토론의견, 1 : 베스트토론의견)
	 */
	private int bestLinereply;
	
	/**
	 * 토론의견(댓글) 추천 수
	 */
	private int recommendCount;
	
	/**
	 * 포털 ID
	 */
	private String portalId;
	
	/**
     * 팀이름
     */
    private String teamName;
    
    /**
     * 직책이름
     */
    private String jobTitleName;
    
    /**
     * 의견개수
     */
    private int count;
    
    /**
     * 베스트 의견 개수
     */
    private int bestCount;
    
    /**
     * 주제 ID
     */
    private String discussionId;
    
    /**
     * 토론글
     */
    private IdIdea idIdea;
    
    /**
     * 채택 댓글 여부( 0 : 채택댓글아님 1 : 채택댓글)
     */
    private int adoptLinereply;
    
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

	public String getLinereplyId() {
		return linereplyId;
	}

	public void setLinereplyId(String linereplyId) {
		this.linereplyId = linereplyId;
	}

	public String getLinereplyParentId() {
		return linereplyParentId;
	}

	public void setLinereplyParentId(String linereplyParentId) {
		this.linereplyParentId = linereplyParentId;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
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

	public String getLinereplyGroupId() {
		return linereplyGroupId;
	}

	public void setLinereplyGroupId(String linereplyGroupId) {
		this.linereplyGroupId = linereplyGroupId;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public int getIndentation() {
		return indentation;
	}

	public void setIndentation(int indentation) {
		this.indentation = indentation;
	}

	public int getLinereplyDelete() {
		return linereplyDelete;
	}

	public void setLinereplyDelete(int linereplyDelete) {
		this.linereplyDelete = linereplyDelete;
	}

	public int getBestLinereply() {
		return bestLinereply;
	}

	public void setBestLinereply(int bestLinereply) {
		this.bestLinereply = bestLinereply;
	}

	public int getRecommendCount() {
		return recommendCount;
	}

	public void setRecommendCount(int recommendCount) {
		this.recommendCount = recommendCount;
	}

	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
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

	public String getDiscussionId() {
		return discussionId;
	}

	public void setDiscussionId(String discussionId) {
		this.discussionId = discussionId;
	}

	public IdIdea getIdIdea() {
		return idIdea;
	}

	public void setIdIdea(IdIdea idIdea) {
		this.idIdea = idIdea;
	}

	public int getAdoptLinereply() {
		return adoptLinereply;
	}

	public void setAdoptLinereply(int adoptLinereply) {
		this.adoptLinereply = adoptLinereply;
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