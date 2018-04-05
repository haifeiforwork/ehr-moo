/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.peopleconnection.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 
 * TODO Javadoc주석작성
 *
 * @author 최성우
 * @version $Id: ExternalExpert.java 16246 2011-08-18 04:48:28Z giljae $
 */
public class ExternalExpert extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = -516998219638685787L;

	/**
	 * 인물정보 ID
	 */
	private String profileId;	
	
	/**
	 * 인물이름
	 */
	private String name;
	
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
	 * 사진 이미지
	 */
	private String fileId;	
	
	
	public String getProfileId() {
		return profileId;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

    
}
