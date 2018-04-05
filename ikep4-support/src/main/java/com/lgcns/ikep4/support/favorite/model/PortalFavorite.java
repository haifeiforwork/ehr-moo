package com.lgcns.ikep4.support.favorite.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * Favorite
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: PortalFavorite.java 16258 2011-08-18 05:37:22Z giljae $
 */
public class PortalFavorite extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 8814719266235952230L;

	/**
	 * Favorite Id
	 */
	private String favoriteId;
	
	
	private String workspaceName;

	/**
	 * 타입
	 */
	private String type;

	/**
	 * item type code
	 */
	private String itemTypeCode;

	/**
	 * 모듈 코드
	 */
	private String moduleCode;

	/**
	 * 모듈 명
	 */
	private String moduleName;

	/**
	 * 타켓 아이템 ID
	 */
	private String targetId;

	/**
	 * 서브 ID
	 */
	private String subId;

	/**
	 * 타겟 아이템 제목
	 */
	private String targetTitle;

	/**
	 * 타겟 아이템 URL
	 */
	private String targetUrl;

	/**
	 * 등록자
	 */
	private String registerId;

	/**
	 * 등록자명
	 */
	private String registerName;

	/**
	 * 등록자 영문명
	 */
	private String registerEnglishName;

	/**
	 * 등록일
	 */
	private Date registDate;

	/**
	 * 수정자
	 */
	private String updaterId;

	/**
	 * 수정자명
	 */
	private String updaterName;

	/**
	 * 수정일
	 */
	private Date updateDate;

	/**
	 * 모듈
	 */
	private String module;
	
	/**
	 * 모듈
	 */
	private String moduleEn;

	/**
	 * 제목
	 */
	private String title;

	/**
	 * 영문제목
	 */
	private String englishTitle;

	/**
	 * 조회수
	 */
	private int hitCount;

	/**
	 * 추천수
	 */
	private int recommendCount;

	/**
	 * 댓글수
	 */
	private int linereplyCount;

	/**
	 * 팀명
	 */
	private String teamName;

	/**
	 * 팀영문명
	 */
	private String teamEnglishName;

	/**
	 * 핸드폰
	 */
	private String mobile;

	/**
	 * 이메일
	 */
	private String mail;

	/**
	 * 전화번호
	 */
	private String officePhoneNo;

	/**
	 * 직급명
	 */
	private String jobTitleName;

	/**
	 * 직급영문명
	 */
	private String jobTitleEnglishName;

	/**
	 * 사진파일 ID
	 */
	private String pictureId;

	/**
	 * 직번
	 */
	private String empNo;

	/**
	 * Follower ID
	 */
	private String followId;

	/**
	 * 사진파일 경로 (큰이미지)
	 */
	private String picturePath;
	
	/**
	 * 사진파일 경로 (작은이미지)
	 */
	private String profilePicturePath;


	public String getFavoriteId() {
		return favoriteId;
	}

	public void setFavoriteId(String favoriteId) {
		this.favoriteId = favoriteId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public String getTargetTitle() {
		return targetTitle;
	}

	public void setTargetTitle(String targetTitle) {
		this.targetTitle = targetTitle;
	}

	public String getTargetUrl() {
		return targetUrl;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
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

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getHitCount() {
		return hitCount;
	}

	public void setHitCount(int hitCount) {
		this.hitCount = hitCount;
	}

	public int getRecommendCount() {
		return recommendCount;
	}

	public void setRecommendCount(int recommendCount) {
		this.recommendCount = recommendCount;
	}

	public int getLinereplyCount() {
		return linereplyCount;
	}

	public void setLinereplyCount(int linereplyCount) {
		this.linereplyCount = linereplyCount;
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

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}
	
	public void setModuleEn(String moduleEn) {
		this.moduleEn = moduleEn;
	}

	public String getModuleEn() {
		return moduleEn;
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

	public String getOfficePhoneNo() {
		return officePhoneNo;
	}

	public void setOfficePhoneNo(String officePhoneNo) {
		this.officePhoneNo = officePhoneNo;
	}

	public String getFollowId() {
		return followId;
	}

	public void setFollowId(String followId) {
		this.followId = followId;
	}

	public String getSubId() {
		return subId;
	}

	public void setSubId(String subId) {
		this.subId = subId;
	}

	public String getItemTypeCode() {
		return itemTypeCode;
	}

	public void setItemTypeCode(String itemTypeCode) {
		this.itemTypeCode = itemTypeCode;
	}

	public String getPictureId() {
		return pictureId;
	}

	public void setPictureId(String pictureId) {
		this.pictureId = pictureId;
	}

	public String getEmpNo() {
		return empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	public String getEnglishTitle() {
		return englishTitle;
	}

	public void setEnglishTitle(String englishTitle) {
		this.englishTitle = englishTitle;
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

	public String getRegisterEnglishName() {
		return registerEnglishName;
	}

	public void setRegisterEnglishName(String registerEnglishName) {
		this.registerEnglishName = registerEnglishName;
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

	public String getWorkspaceName() {
		return workspaceName;
	}

	public void setWorkspaceName(String workspaceName) {
		this.workspaceName = workspaceName;
	}

}
