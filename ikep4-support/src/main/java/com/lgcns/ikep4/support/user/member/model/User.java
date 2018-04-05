/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.user.member.model;

import java.util.List;
import java.util.Properties;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.framework.validator.constraints.Tel;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.userTheme.model.UserTheme;
import com.lgcns.ikep4.util.PropertyLoader;


/**
 * 사용자(user) 모델 class
 * 
 * @author 양새로훈(yang.sae@gmail.com)
 * @version $Id: User.java 18234 2012-04-24 05:17:35Z jaesyang $
 */
public class User extends BaseObject {

	public interface ProfileUpdate {
	}

	public interface ProfileCurrentJobUpdate {
	}

	public interface ProfileStatusUpdate {
	}

	public interface ProfileExpertFieldUpdate {
	}

	public interface UserCreate {
	}

	public interface UserUpdate {
	}
	
	public interface MyPsInfoUpdate {
	}

	/**
	 * Generated Serial Version UID
	 */
	private static final long serialVersionUID = 5722962216533271819L;

	/**
	 * 사용자 ID
	 */
	@NotNull(groups = { UserCreate.class, UserUpdate.class })
	@Size(groups = { UserCreate.class, UserUpdate.class }, min = 0, max = 15)
	private String userId;

	/**
	 * 포탈 ID
	 */
	private String portalId;

	/**
	 * 사용자 Password(암호화됨)
	 */
	@NotNull(groups = { UserCreate.class, UserUpdate.class, MyPsInfoUpdate.class })
	@Size(groups = { UserCreate.class, UserUpdate.class, MyPsInfoUpdate.class }, min = 0, max = 50)
	private String userPassword;

	/**
	 * 사용자의 재직구분(C:재직, H:휴직, T:퇴직)
	 */
	private String userStatus;

	/**
	 * 사용자의 사원번호
	 */
	@Size(groups = { UserCreate.class, UserUpdate.class }, min = 0, max = 25)
	private String empNo;

	/**
	 * 사용자의 이메일 주소
	 */
	@Size(groups = { UserCreate.class, UserUpdate.class, ProfileUpdate.class }, min = 0, max = 50)
	@Email(groups = { ProfileUpdate.class, UserCreate.class, UserUpdate.class })
	private String mail;

	/**
	 * 사용자의 메일 패스워드
	 */
	@Size(groups = { UserCreate.class, UserUpdate.class, ProfileUpdate.class }, min = 0, max = 50)
	private String mailPassword;

	/**
	 * 사용자의 이름
	 */
	@NotNull(groups = { UserCreate.class, UserUpdate.class })
	@Size(groups = { UserCreate.class, UserUpdate.class }, min = 0, max = 60)
	private String userName;

	/**
	 * 사용자의 영문 이름
	 */
	@NotNull(groups = { UserCreate.class, UserUpdate.class })
	@Size(groups = { UserCreate.class, UserUpdate.class }, min = 0, max = 100)
	private String userEnglishName;

	/**
	 * 이 사용자가 해당된 부서(그룹) 이름
	 */
	private String teamName;
	
	private String teamCode;

	/**
	 * 이 사용자가 해당된 부서(그룹) 영어 이름
	 */
	private String teamEnglishName;

	/**
	 * 사용자의 그룹 ID
	 */
	private String groupId;
	
	/**
	 * 사용자의 그룹 Level
	 */
	private String groupLevel;

	/**
	 * 사용자에 해당하는 그룹이 대표그룹인지 아닌지 판단하는 플래그
	 */
	private String isRepresentGroup;

	/**
	 * 사용자의 역할 ID(Role-user 매핑 시에 사용됨)
	 */
	private String roleId;

	/**
	 * 사용자의 TIMEZONE ID(등록/업데이트시의 TIMEZONE ID)
	 */
	private String timezoneId;

	/**
	 * 해당 TIMEZONE의 이름
	 */
	private String timezoneName;

	/**
	 * 해당 TIMEZONE의 description
	 */
	private String timezoneDescription;

	/**
	 * 해당 TIMEZONE의 시간차(서울부터 목적지까지)
	 */
	private String timeDifference;
	
	
	private String essAuthCode;
	
	private String mssAuthCode;
	
	private String scheduleManager;
	
	private String passwordChangeYn;
	
	private String tempCode;
	
	private String tempCodeDate;
	
	private String updatePasswordDate;
	
	private String expectedUpdatePasswordDate;
	
	
	public String getScheduleManager() {
		return scheduleManager;
	}

	public void setScheduleManager(String scheduleManager) {
		this.scheduleManager = scheduleManager;
	}

	public String getEssAuthCode() {
		return essAuthCode;
	}

	public void setEssAuthCode(String essAuthCode) {
		this.essAuthCode = essAuthCode;
	}

	public String getMssAuthCode() {
		return mssAuthCode;
	}

	public void setMssAuthCode(String mssAuthCode) {
		this.mssAuthCode = mssAuthCode;
	}

	/**
	 * 사용자의 모바일폰 번호
	 */
	@Tel(groups = { ProfileUpdate.class })
	@Size(groups = { UserCreate.class, UserUpdate.class, ProfileUpdate.class }, min = 0, max = 30)
	private String mobile;

	/**
	 * 사용자의 사무실 전화번호
	 */
	@Tel(groups = { ProfileUpdate.class })
	@Size(groups = { UserCreate.class, UserUpdate.class, ProfileUpdate.class }, min = 0, max = 30)
	private String officePhoneNo;

	/**
	 * 사용자의 집 전화번호
	 */
	@Size(groups = { UserCreate.class, UserUpdate.class, MyPsInfoUpdate.class }, min = 0, max = 30)
	private String homePhoneNo;

	/**
	 * 사용자의 사무실 주소(기본)
	 */
	@Size(groups = { UserCreate.class, UserUpdate.class, ProfileUpdate.class }, min = 0, max = 100)
	private String officeBasicAddress;

	/**
	 * 사용자의 사무실 주소(상세)
	 */
	@Size(groups = { UserCreate.class, UserUpdate.class, ProfileUpdate.class }, min = 0, max = 25)
	private String officeDetailAddress;

	/**
	 * 사용자의 사무실 우편번호
	 */
	@Size(groups = { UserCreate.class, UserUpdate.class}, min = 0, max = 10)
	private String officeZipcode;

	/**
	 * 사용자의 집 주소(기본)
	 */
	@Size(groups = { UserCreate.class, UserUpdate.class, MyPsInfoUpdate.class }, min = 0, max = 15)
	private String homeBasicAddress;

	/**
	 * 사용자의 집 주소(상세)
	 */
	@Size(groups = { UserCreate.class, UserUpdate.class, MyPsInfoUpdate.class }, min = 0, max = 25)
	private String homeDetailAddress;

	/**
	 * 사용자의 집 우편번호
	 */
	@Size(groups = { UserCreate.class, UserUpdate.class, MyPsInfoUpdate.class }, min = 0, max = 10)
	private String homeZipcode;

	/**
	 * 사용자의 생일
	 */
	@NotNull(groups = {MyPsInfoUpdate.class })
	@Size(groups = { UserCreate.class, UserUpdate.class, MyPsInfoUpdate.class }, min = 0, max = 10)
	private String birthday;

	/**
	 * 사용자의 결혼 기념일
	 */
	@Size(groups = { UserCreate.class, UserUpdate.class, MyPsInfoUpdate.class }, min = 0, max = 10)
	private String weddingAnniv;

	/**
	 * 사용자의 사진 ID
	 */
	private String pictureId;

	/**
	 * 사용자의 프로필 사진 ID
	 */
	private String profilePictureId;

	/**
	 * 사용자의 프로필 사진 경로 (큰이미지)
	 */
	private String picturePath;
	
	/**
	 * 사용자의 프로필 사진 경로(작은이미지)
	 */
	private String profilePicturePath;

	/**
	 * 사용자의 팀장 여부(0: 팀장아님, 1: 팀장)
	 */
	private String leader;

	/**
	 * 사용자의 직군 코드
	 */
	private String jobClassCode;

	/**
	 * 해당 직군의 이름
	 */
	private String jobClassName;

	/**
	 * 사용자의 직급코드
	 */
	private String jobRankCode;

	/**
	 * 해당 직급코드의 이름
	 */
	private String jobRankName;

	/**
	 * 사용자의 직책코드
	 */
	private String jobDutyCode;

	/**
	 * 해당 직책코드의 이름
	 */
	private String jobDutyName;

	/**
	 * 해당 직책코드의 영문이름
	 */
	private String jobDutyEnglishName;
	/**
	 * 사용자의 직위코드
	 */
	private String jobPositionCode;

	/**
	 * 해당 직위코드의 이름
	 */
	private String jobPositionName;

	/**
	 * 사용자의 호칭코드
	 */
	private String jobTitleCode;

	/**
	 * 해당 호칭코드의 이름
	 */
	private String jobTitleName;

	/**
	 * 해당 호칭코드의 영어 이름
	 */
	private String jobTitleEnglishName;

	/**
	 * 사용자의 로케일코드
	 */
	private String localeCode;

	/**
	 * 해당 로케일코드의 이름
	 */
	private String localeName;

	/**
	 * 사용자의 트위터 계정
	 */
	@Size(groups = { UserCreate.class, UserUpdate.class }, min = 0, max = 100)
	private String twitterAccount;

	/**
	 * 트위터 인증 코드
	 */
	@Size(groups = { UserCreate.class, UserUpdate.class }, min = 0, max = 1000)
	private String twitterAuthCode;

	/**
	 * 사용자의 페이스북 계정
	 */
	@Size(groups = { UserCreate.class, UserUpdate.class }, min = 0, max = 100)
	private String facebookAccount;

	/**
	 * 페이스북 인증 코드
	 */
	@Size(groups = { UserCreate.class, UserUpdate.class }, min = 0, max = 1000)
	private String facebookAuthCode;

	/**
	 * 프로필 상태
	 */
	@Size(groups = { UserCreate.class, UserUpdate.class, ProfileStatusUpdate.class }, min = 0, max = 40)
	private String profileStatus;

	/**
	 * 현재 직무
	 */
	@Size(groups = { UserCreate.class, UserUpdate.class, ProfileCurrentJobUpdate.class }, min = 0, max = 200)
	private String currentJob;
	
	private String detailJob;

	/**
	 * 전문분야
	 */
	@Size(groups = { UserCreate.class, UserUpdate.class, ProfileExpertFieldUpdate.class }, min = 0, max = 300)
	private String expertField;

	/**
	 * 이 사용자를 등록한 자의 ID
	 */
	private String registerId;

	/**
	 * 이 사용자를 등록한 자의 이름
	 */
	private String registerName;

	/**
	 * 이 사용자를 등록한 날짜
	 */
	private String registDate;

	/**
	 * 이 사용자의 정보를 업데이트한 자의 ID
	 */
	private String updaterId;

	/**
	 * 이 사용자의 정보를 업데이트한 자의 이름
	 */
	private String updaterName;

	/**
	 * 이 사용자의 정보를 업데이트한 날짜
	 */
	private String updateDate;

	/**
	 * 사용자 ID 중복체크 Flag
	 */
	private String checkIdFlag;

	/**
	 * 엑셀저장시 입력성공여부
	 */
	private String successYn;

	/**
	 * 엑셀저장시 에러메시지
	 */
	private String errMsg;

	/**
	 * 사용자 테마 정보
	 */
	private UserTheme userTheme;

	/**
	 * 로그인 사용자와 following 관계 여부
	 */
	private String isFollowing;

	/**
	 * 사용자의 국가 코드
	 */
	private String nationCode;

	/**
	 * 사용자의 국가 이름
	 */
	private String nationName;

	/**
	 * 사용자가 속한 그룹 목록
	 */
	private List<Group> groupList;

	/**
	 * 사용자가 리더인 경우 리더로 있는 그룹의 ID
	 */
	private String leadingGroupId;

	/**
	 * 결재 비밀번호
	 */
	private String approvalPassword;
	
	/**
	 * 한자 이름
	 */
	private String hanziName;
	
	/**
	 * 사업장 코드
	 */
	private String workPlaceCode;
	
	/**
	 * 사업장 이름
	 */
	private String workPlaceName;
	
	/**
	 * 사업장 이름
	 */
	private String workPlaceEnglishName;
		
	
	/**
	 * fax번호
	 */
	private String fax;
	
	/**
	 *  회사 코드
	 */
	private String companyCode;
	
	/**
	 * 회사 코드 이름
	 */
	private String companyCodeName;
	
	/**
	 * 회사 코드 이름
	 */
	private String companyCodeEnglishName;
	
	
	private String infoGrade;	
	
	/**
	 * 음력 여부(0: 양력, 1: 음력)
	 */
	private String lunisolar;
	
	
	private String sapId;
			
	
	private String bwId;
	
	
	private String itemId;
	
	/**
	 * Favorite Id
	 */
	private String favoriteId;
	
	
	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	
	
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getUserId() {
		return userId;
	}

	public UserTheme getUserTheme() {
		return userTheme;
	}

	public void setUserTheme(UserTheme userTheme) {
		this.userTheme = userTheme;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getEmpNo() {
		return empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {	
		this.mail = mail;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEnglishName() {
		return userEnglishName;
	}

	public void setUserEnglishName(String userEnglishName) {
		this.userEnglishName = userEnglishName;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getTeamEnglishName() {
		return teamEnglishName;
	}

	public void setTeamEnglishName(String teamEnglishName) {
		this.teamEnglishName = teamEnglishName;
	}

	public String getTimezoneId() {
		return timezoneId;
	}

	public void setTimezoneId(String timezoneId) {
		this.timezoneId = timezoneId;
	}

	public String getTimezoneName() {
		return timezoneName;
	}

	public void setTimezoneName(String timezoneName) {
		this.timezoneName = timezoneName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOfficePhoneNo() {
		return officePhoneNo;
	}

	public void setOfficePhoneNo(String officePhoneNo) {
		this.officePhoneNo = officePhoneNo;
	}

	public String getHomePhoneNo() {
		return homePhoneNo;
	}

	public void setHomePhoneNo(String homePhoneNo) {
		this.homePhoneNo = homePhoneNo;
	}

	public String getOfficeBasicAddress() {
		return officeBasicAddress;
	}

	public void setOfficeBasicAddress(String officeBasicAddress) {
		this.officeBasicAddress = officeBasicAddress;
	}

	public String getOfficeDetailAddress() {
		return officeDetailAddress;
	}

	public void setOfficeDetailAddress(String officeDetailAddress) {
		this.officeDetailAddress = officeDetailAddress;
	}

	public String getOfficeZipcode() {
		return officeZipcode;
	}

	public void setOfficeZipcode(String officeZipcode) {
		this.officeZipcode = officeZipcode;
	}

	public String getHomeBasicAddress() {
		return homeBasicAddress;
	}

	public void setHomeBasicAddress(String homeBasicAddress) {
		this.homeBasicAddress = homeBasicAddress;
	}

	public String getHomeDetailAddress() {
		return homeDetailAddress;
	}

	public void setHomeDetailAddress(String homeDetailAddress) {
		this.homeDetailAddress = homeDetailAddress;
	}

	public String getHomeZipcode() {
		return homeZipcode;
	}

	public void setHomeZipcode(String homeZipcode) {
		this.homeZipcode = homeZipcode;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getWeddingAnniv() {
		return weddingAnniv;
	}

	public void setWeddingAnniv(String weddingAnniv) {
		this.weddingAnniv = weddingAnniv;
	}

	public String getPictureId() {
		return pictureId;
	}

	public void setPictureId(String pictureId) {
		this.pictureId = pictureId;
	}

	public String getProfilePictureId() {
		return profilePictureId;
	}

	public void setProfilePictureId(String profilePictureId) {
		this.profilePictureId = profilePictureId;
	}

	public String getLeader() {
		return leader;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}

	public String getJobClassCode() {
		return jobClassCode;
	}

	public void setJobClassCode(String jobClassCode) {
		this.jobClassCode = jobClassCode;
	}

	public String getJobClassName() {
		return jobClassName;
	}

	public void setJobClassName(String jobClassName) {
		this.jobClassName = jobClassName;
	}

	public String getJobRankCode() {
		return jobRankCode;
	}

	public void setJobRankCode(String jobRankCode) {
		this.jobRankCode = jobRankCode;
	}

	public String getJobRankName() {
		return jobRankName;
	}

	public void setJobRankName(String jobRankName) {
		this.jobRankName = jobRankName;
	}

	public String getJobDutyCode() {
		return jobDutyCode;
	}

	public void setJobDutyCode(String jobDutyCode) {
		this.jobDutyCode = jobDutyCode;
	}

	public String getJobDutyName() {
		return jobDutyName;
	}

	public void setJobDutyName(String jobDutyName) {
		this.jobDutyName = jobDutyName;
	}

	public String getJobPositionCode() {
		return jobPositionCode;
	}

	public void setJobPositionCode(String jobPositionCode) {
		this.jobPositionCode = jobPositionCode;
	}

	public String getJobPositionName() {
		return jobPositionName;
	}

	public void setJobPositionName(String jobPositionName) {
		this.jobPositionName = jobPositionName;
	}

	public String getJobTitleCode() {
		return jobTitleCode;
	}

	public void setJobTitleCode(String jobTitleCode) {
		this.jobTitleCode = jobTitleCode;
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

	public String getLocaleCode() {
		return localeCode;
	}

	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}

	public String getLocaleName() {
		return localeName;
	}

	public void setLocaleName(String localeName) {
		this.localeName = localeName;
	}

	public String getTwitterAccount() {
		return twitterAccount;
	}

	public void setTwitterAccount(String twitterAccount) {
		this.twitterAccount = twitterAccount;
	}

	public String getTwitterAuthCode() {
		return twitterAuthCode;
	}

	public void setTwitterAuthCode(String twitterAuthCode) {
		this.twitterAuthCode = twitterAuthCode;
	}

	public String getFacebookAccount() {
		return facebookAccount;
	}

	public void setFacebookAccount(String facebookAccount) {
		this.facebookAccount = facebookAccount;
	}

	public String getFacebookAuthCode() {
		return facebookAuthCode;
	}

	public void setFacebookAuthCode(String facebookAuthCode) {
		this.facebookAuthCode = facebookAuthCode;
	}

	public String getProfileStatus() {
		return profileStatus;
	}

	public void setProfileStatus(String profileStatus) {
		this.profileStatus = profileStatus;
	}

	public String getCurrentJob() {
		return currentJob;
	}

	public void setCurrentJob(String currentJob) {
		this.currentJob = currentJob;
	}

	public String getExpertField() {
		return expertField;
	}

	public void setExpertField(String expertField) {
		this.expertField = expertField;
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

	public String getRegistDate() {
		return registDate;
	}

	public void setRegistDate(String registDate) {
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

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getCheckIdFlag() {
		return checkIdFlag;
	}

	public void setCheckIdFlag(String checkIdFlag) {
		this.checkIdFlag = checkIdFlag;
	}

	public String getSuccessYn() {
		return successYn;
	}

	public void setSuccessYn(String successYn) {
		this.successYn = successYn;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getTimezoneDescription() {
		return timezoneDescription;
	}

	public void setTimezoneDescription(String timezoneDescription) {
		this.timezoneDescription = timezoneDescription;
	}

	public String getTimeDifference() {
		return timeDifference;
	}

	public void setTimeDifference(String timeDifference) {
		this.timeDifference = timeDifference;
	}

	public String getIsFollowing() {
		return isFollowing;
	}

	public void setIsFollowing(String isFollowing) {
		this.isFollowing = isFollowing;
	}

	public String getNationCode() {
		return nationCode;
	}

	public void setNationCode(String nationCode) {
		this.nationCode = nationCode;
	}

	public String getNationName() {
		return nationName;
	}

	public void setNationName(String nationName) {
		this.nationName = nationName;
	}

	public String getMailPassword() {
		return mailPassword;
	}

	public void setMailPassword(String mailPassword) {
		this.mailPassword = mailPassword;
	}

	public String getIsRepresentGroup() {
		return isRepresentGroup;
	}

	public void setIsRepresentGroup(String isRepresentGroup) {
		this.isRepresentGroup = isRepresentGroup;
	}

	public List<Group> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<Group> groupList) {
		this.groupList = groupList;
	}

	public String getLeadingGroupId() {
		return leadingGroupId;
	}

	public void setLeadingGroupId(String leadingGroupId) {
		this.leadingGroupId = leadingGroupId;
	}

	public String getProfilePicturePath() {
		return profilePicturePath;
	}

	public void setProfilePicturePath(String profilePicturePath) {
		this.profilePicturePath = profilePicturePath;
	}

	public String getPicturePath() {
		return picturePath;
	}

	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}

	public String getApprovalPassword() {
		return approvalPassword;
	}

	public void setApprovalPassword(String approvalPassword) {
		this.approvalPassword = approvalPassword;
	}

	public String getJobDutyEnglishName() {
		return jobDutyEnglishName;
	}

	public void setJobDutyEnglishName(String jobDutyEnglishName) {
		this.jobDutyEnglishName = jobDutyEnglishName;
	}
	
	public String getHanziName() {
		return hanziName;
	}

	public void setHanziName(String hanziName) {
		this.hanziName = hanziName;
	}
	
	public String getWorkPlaceCode() {
		return workPlaceCode;
	}

	public void setWorkPlaceCode(String workPlaceCode) {
		this.workPlaceCode = workPlaceCode;
	}
	
	public String getWorkPlaceName() {
		return workPlaceName;
	}

	public void setWorkPlaceName(String workPlaceName) {
		this.workPlaceName = workPlaceName;
	}
	
	public String getWorkPlaceEnglishName() {
		return workPlaceEnglishName;
	}

	public void setWorkPlaceEnglishName(String workPlaceEnglishName) {
		this.workPlaceEnglishName = workPlaceEnglishName;
	}
	
	public String getFax() {
		return fax;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanyCodeName() {
		return companyCodeName;
	}

	public void setCompanyCodeName(String companyCodeName) {
		this.companyCodeName = companyCodeName;
	}

	public String getCompanyCodeEnglishName() {
		return companyCodeEnglishName;
	}

	public void setCompanyCodeEnglishName(String companyCodeEnglishName) {
		this.companyCodeEnglishName = companyCodeEnglishName;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}
	
	public String getInfoGrade() {
		return infoGrade;
	}

	public void setInfoGrade(String infoGrade) {
		this.infoGrade = infoGrade;
	}
	
	public String getLunisolar() {
		return lunisolar;
	}

	public void setLunisolar(String lunisolar) {
		this.lunisolar = lunisolar;
	}
	

	public String getSapId() {
		return sapId;
	}

	public void setSapId(String sapId) {
		this.sapId = sapId;
	}

	public String getBwId() {
		return bwId;
	}

	public void setBwId(String bwId) {
		this.bwId = bwId;
	}

	public String getFavoriteId() {
		return favoriteId;
	}

	public void setFavoriteId(String favoriteId) {
		this.favoriteId = favoriteId;
	}

	public String getGroupLevel() {
		return groupLevel;
	}

	public void setGroupLevel(String groupLevel) {
		this.groupLevel = groupLevel;
	}

	public String getPasswordChangeYn() {
		return passwordChangeYn;
	}

	public void setPasswordChangeYn(String passwordChangeYn) {
		this.passwordChangeYn = passwordChangeYn;
	}

	public String getDetailJob() {
		return detailJob;
	}

	public void setDetailJob(String detailJob) {
		this.detailJob = detailJob;
	}

	public String getTempCode() {
		return tempCode;
	}

	public void setTempCode(String tempCode) {
		this.tempCode = tempCode;
	}

	public String getTempCodeDate() {
		return tempCodeDate;
	}

	public void setTempCodeDate(String tempCodeDate) {
		this.tempCodeDate = tempCodeDate;
	}

	public String getUpdatePasswordDate() {
		return updatePasswordDate;
	}

	public void setUpdatePasswordDate(String updatePasswordDate) {
		this.updatePasswordDate = updatePasswordDate;
	}

	public String getTeamCode() {
		return teamCode;
	}

	public void setTeamCode(String teamCode) {
		this.teamCode = teamCode;
	}

	public String getExpectedUpdatePasswordDate() {
		return expectedUpdatePasswordDate;
	}

	public void setExpectedUpdatePasswordDate(String expectedUpdatePasswordDate) {
		this.expectedUpdatePasswordDate = expectedUpdatePasswordDate;
	}

	
}