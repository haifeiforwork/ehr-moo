package com.lgcns.ikep4.support.user.restful.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 
 * 명함 관리 model
 *
 * @version $Id$
 */
public class Vcard extends BaseObject {

	private static final long serialVersionUID = 5374873876670037379L;
	
	/**
	 * 명함정보ID
	 */
	private String visitingcardId;
	
	/**
	 * 명함정보그룹ID
	 */
	private String visitingcardGroupId;
	
	/**
	 * 성
	 */
	private String lastName;
	
	/**
	 * 성(읽기)
	 */
	private String lastNameRead;
	
	/**
	 * 이름
	 */
	private String firstName;
	
	/**
	 * 이름(읽기)
	 */
	private String firstNameRead;
	
	/**
	 * 회사명
	 */
	private String companyName;
	
	/**
	 * 부서명
	 */
	private String teamName;
	
	/**
	 * 직급명
	 */
	private String jobRankName;
	
	/**
	 * 사무실 전화번호
	 */
	private String officePhoneno;
	
	/**
	 * 사무실 FAX
	 */
	private String officeFax;
	
	/**
	 * 휴대폰 번호
	 */
	private String mobile;
	
	/**
	 * 이메일
	 */
	private String mail;
	
	/**
	 * 생년월일
	 */
	private String birthday;
	
	/**
	 * 회사우편번호
	 */
	private String companyPostno;
	
	/**
	 * 회사주소
	 */
	private String companyAddress;
	
	/**
	 * 회사URL
	 */
	private String companyUrl;
	
	/**
	 * 메모
	 */
	private String memo;
	
	/**
	 * 명함 수정 사유
	 */
	private String updateReason;
	
	/**
	 * 버전정보
	 */
	private String version;
	
	/**
	 * 등록자 ID
	 */
	private String registerId;

	/**
	 * 등록자 이름
	 */
	private String registerName;

	/**
	 * 등록일
	 */
	private Date registDate;

	/**
	 * 수정일 ID
	 */
	private String updaterId;

	/**
	 * 수정자 이름
	 */
	private String updaterName;

	/**
	 * 수정일
	 */
	private Date updateDate;
	
	/**
	 * 파일 ID
	 */
	private String fileId;
	
	/**
	 * 파일 수정여부
	 */
	private String fileUpdate = "N";
	
	/**
	 * 체크플래그(일괄저장시 이용)
	 */
	private String checkFlag;
	
	/**
	 * 중복여부(일괄저장시 이용)
	 */
	private String duplicateFlag;
	
	/**
	 * 폴더 ID
	 */
	private String folderId;
	
	/**
	 * 파일 import시 validation 체크 결과
	 */
	private String importFailYn;
	
	public String getFolderId() {
		return folderId;
	}

	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getVisitingcardId() {
		return visitingcardId;
	}

	public void setVisitingcardId(String visitingcardId) {
		this.visitingcardId = visitingcardId;
	}

	public String getVisitingcardGroupId() {
		return visitingcardGroupId;
	}

	public void setVisitingcardGroupId(String visitingcardGroupId) {
		this.visitingcardGroupId = visitingcardGroupId;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLastNameRead() {
		return lastNameRead;
	}

	public void setLastNameRead(String lastNameRead) {
		this.lastNameRead = lastNameRead;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFirstNameRead() {
		return firstNameRead;
	}

	public void setFirstNameRead(String firstNameRead) {
		this.firstNameRead = firstNameRead;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getJobRankName() {
		return jobRankName;
	}

	public void setJobRankName(String jobRankName) {
		this.jobRankName = jobRankName;
	}

	public String getOfficePhoneno() {
		return officePhoneno;
	}

	public void setOfficePhoneno(String officePhoneno) {
		this.officePhoneno = officePhoneno;
	}

	public String getOfficeFax() {
		return officeFax;
	}

	public void setOfficeFax(String officeFax) {
		this.officeFax = officeFax;
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

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getCompanyPostno() {
		return companyPostno;
	}

	public void setCompanyPostno(String companyPostno) {
		this.companyPostno = companyPostno;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getCompanyUrl() {
		return companyUrl;
	}

	public void setCompanyUrl(String companyUrl) {
		this.companyUrl = companyUrl;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getUpdateReason() {
		return updateReason;
	}

	public void setUpdateReason(String updateReason) {
		this.updateReason = updateReason;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
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

	public String getCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}

	public String getDuplicateFlag() {
		return duplicateFlag;
	}

	public void setDuplicateFlag(String duplicateFlag) {
		this.duplicateFlag = duplicateFlag;
	}

	public String getImportFailYn() {
		return importFailYn;
	}

	public void setImportFailYn(String importFailYn) {
		this.importFailYn = importFailYn;
	}

	public String getFileUpdate() {
		return fileUpdate;
	}

	public void setFileUpdate(String fileUpdate) {
		this.fileUpdate = fileUpdate;
	}
	
}