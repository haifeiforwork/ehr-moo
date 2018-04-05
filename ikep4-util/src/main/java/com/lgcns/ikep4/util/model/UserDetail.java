package com.lgcns.ikep4.util.model;

import java.util.Date;
import java.util.Properties;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.util.PropertyLoader;

/**
 * 
 * 사용자 정보 model
 *
 * @author 
 * @version 
 */
public class UserDetail extends BaseObject {

	private static final long serialVersionUID = -2116043197179568881L;
	
	private String domain;
	
	public UserDetail() {
		super();		
		Properties prop = PropertyLoader.loadProperties("/configuration/mail-conf.properties");		
		domain = prop.getProperty("ikep4.support.mail.domain");
	}

	/**
	 *  메일 ID
	 */
	private String userId;   
	
	/**
	 *  재직구분
	 */	
	private String userStatus;
	
	/**
	 * 사원 번호(active 한 사번 필요)
	 */
	private String empNo;  
	
	/**
	 * EP 사용자 ID
	 */
	private String mail; 
	
	/**
	 * 사용자 한글 이름
	 */
	private String userName; 
	
	/**
	 * 사용자 영문 이름
	 */
	private String userEnglishName;
	
	/**
	 * 소속부서 코드
	 */ 
	private String teamCode; 
	
	/**
	 * 소속부서 이름
	 */
	private String teamName; 
	
	/**
	 * 휴대전화 번호
	 */
	private String mobile; 
	
	/**
	 * 사무실 전화번호
	 */
	private String officePhoneNo; 
	
	/**
	 * 생년월일 (YYYY-MM-DD)
	 */
	private String birthday; 
	
	/**
	 * 생년월일 음양구분
	 */
	private int lunisolar;
	
	/**
	 * 리더 여부
	 */ 
	private int leader; 
	
	/**
	 * 직책 코드-팀장.이사,본부장
	 */
	private String jobDutyCode; 
	
	/**
	 * 직책명
	 */
	private String jobDutyName; 
	
	/**
	 * 직위 코드-사원,대리,과장
	 */
	private String jobPositionCode; 
	
	/**
	 * 직위명
	 */
	private String jobPositionName; 
	
	/**
	 * 영문 부서 이름
	 */
	private String teamEnglishName; 
	
	/**
	 * 사용자 사진 경로
	 */
	private String profilePicturePath ; 
	
	/**
	 * BW 사용자 ID
	 */
	private String bwId ; 
	
	/**
	 * SAP 사용자 ID
	 */
	private String sapId; 
	
	/**
	 * 직군코드(사원구분코드)
	 */
	private String jobClassCode; 
	
	/**
	 * 직군명(사원구분)-사무직,기능직,별정직등
	 */
	private String jobClassName;
	
	/**
	 * 소속 사업장코드
	 */ 
	private String workPlaceCode; 
	
	/**
	 * 소속 사업장명(본사,울산,진주 등)
	 */	
	private String workPlaceName;
	
	/**
	 * 사업장 이름
	 */
	private String workPlaceEnglishName;
	
	/**
	 * 한문이름
	 */
	private String hanziName; 
	
	/**
	 * 변경년월일시간
	 */
	private String modifiedDate; 
	
	/**
	 * 회사명(무림페이퍼,무림에스피,무림피앤피 등)
	 */
	private String companyName ; 
	
	/**
	 * 회사코드(S100, M100, P100 등)
	 */	
	private String companyCode ; 
	
	/**
	 * 포지션명
	 */
	private String erpPositionName; 
	
	/**
	 * 포지션코드
	 */
	private String erpPositionCode;   
	
	/**
	 * ESS권한코드(예:E1,E2 등)
	 */
	private String essAuthCode;
	
	/**
	 * MSS권한코드(예:M1,M2 등)
	 */
	private String mssAuthCode;
	
	
	private String scheduleManager;
	
	
	private String executiveId;
	
	private String entryDate;
	
	private String leavingDate;
	
	private String retireFlag;
	
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		
		this.userId =  userId;
	}
	
	public String getUserStatus() {
		
		return userStatus;
	}
	
	public void setUserStatus(String userStatus) {
		
		if("3".equals(userStatus))
		{
			this.userStatus = "C";
		}
		else
		{
			this.userStatus = "T";
		}
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
		this.mail =  mail;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName.replaceAll("\\p{Space}",""); 
	}
	
	public String getUserEnglishName() {
		return userEnglishName;
	}
	
	public void setUserEnglishName(String userEnglishName) {
		this.userEnglishName = userEnglishName;
	}
	
	public String getTeamCode() {
		return teamCode;
	}
	
	public void setTeamCode(String teamCode) {
		this.teamCode = teamCode;
	}
	
	public String getTeamName() {
		return teamName;
	}
	
	public void setTeamName(String teamName) {
		this.teamName = teamName;
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
	
	public String getBirthday() {
		return birthday;
	}
	
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	
	public int getLeader() {
		return leader;
	}
	
	public void setLeader(String leader) {
		if("Y".equalsIgnoreCase(leader))
		{
		    this.leader = 1;
		}
		else
		{
			this.leader = 0;
		}
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
	
	public String getTeamEnglishName() {
		return teamEnglishName;
	}
	
	public void setTeamEnglishName(String teamEnglishName) {
		this.teamEnglishName = teamEnglishName;
	}
	
	public String getProfilePicturePath() {
		return profilePicturePath;
	}
	
	public void setProfilePicturePath(String profilePicturePath) {
		this.profilePicturePath = profilePicturePath;
	}
	
	public String getBwId() {
		return bwId;
	}
	
	public void setBwId(String bwId) {
		this.bwId = bwId;
	}
	
	public String getSapId() {
		return sapId;
	}
	
	public void setSapId(String sapId) {
		this.sapId = sapId;
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
	
	public String getHanziName() {
		return hanziName;
	}
	
	public void setHanziName(String hanziName) {
		if(!hanziName.isEmpty())
		{
			this.hanziName = hanziName.replaceAll("\\p{Space}",""); 
		}
		else
		{
			this.hanziName = hanziName;
		}
	}
	
	public String getModifiedDate() {
		return modifiedDate;
	}
	
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	public String getCompanyName() {
		return companyName;
	}
	
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public String getCompanyCode() {
		return companyCode;
	}
	
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	
	public String getErpPositionName() {
		return erpPositionName;
	}
	
	public void setErpPositionName(String erpPositionName) {
		this.erpPositionName = erpPositionName;
	}
	
	public String getErpPositionCode() {
		return erpPositionCode;
	}
	
	public void setErpPositionCode(String erpPositionCode) {
		this.erpPositionCode = erpPositionCode;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public String getScheduleManager() {
		return scheduleManager;
	}

	public void setScheduleManager(String scheduleManager) {
		this.scheduleManager = scheduleManager;
	}
	
	public String getWorkPlaceEnglishName() {
		return workPlaceEnglishName;
	}

	public void setWorkPlaceEnglishName(String workPlaceEnglishName) {
		this.workPlaceEnglishName = workPlaceEnglishName;
	}

	public void setLunisolar(String lunisolar) {
		
		if("1".endsWith(lunisolar))
		{
			this.lunisolar = 0;
		}
		else
		{
			this.lunisolar = 1;
		}
	}

	public int getLunisolar() {
		return lunisolar;
	}

	public void setExecutiveId(String executiveId) {
		this.executiveId = executiveId;
	}

	public String getExecutiveId() {
		return executiveId;
	}

	public String getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(String entryDate) {
		this.entryDate = entryDate;
	}

	public String getLeavingDate() {
		return leavingDate;
	}

	public void setLeavingDate(String leavingDate) {
		this.leavingDate = leavingDate;
	}

	public String getRetireFlag() {
		return retireFlag;
	}

	public void setRetireFlag(String retireFlag) {
		this.retireFlag = retireFlag;
	}
}
