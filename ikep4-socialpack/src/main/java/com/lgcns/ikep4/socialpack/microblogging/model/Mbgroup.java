/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.microblogging.model;

import java.util.Date;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * 
 * Mbgroup VO
 *
 * @author 최성우(csw9737@hanmail.net)
 * @version $Id: Mbgroup.java 16246 2011-08-18 04:48:28Z giljae $
 */

public class Mbgroup extends BaseObject {

	/**
	 * serial Version UID
	 */
	private static final long serialVersionUID = 5364654202370384555L;

	/**
	 * 마이크로블로깅그룹 아이디
	 */
	@NotEmpty
	@Size(min=1,max=9) 
	private String mbgroupId;

	/**
	 * 마이크로블로깅그룹명
	 */
	@NotEmpty
	@Size(min=1,max=60) 
	private String mbgroupName;

	/**
	 * 마이크로블로깅그룹 소개
	 */
	@Size(min=0,max=1300) 
	private String mbgroupIntroduction;

	/**
	 * 이미지파일 아이디
	 */
	private String imagefileId;

	/**
	 * 등록자 아이디
	 */
	private String registerId;

	/**
	 * 등록자명
	 */
	private String registerName;

	/**
	 * 등록자 이름(영문)
	 */
	private String registerEnglishName;
	
	/**
	 * 등록일
	 */
	private Date registDate;

	/**
	 * 수정자 아이디
	 */
	private String updaterId;

	/**
	 * 수정자 명
	 */
	private String updaterName;

	/**
	 * 수정자 이름(영문)
	 */
	private String updaterEnglishName;
	/**
	 * 수정일
	 */
	private Date updateDate;

	/*
	 * 삭제 여부 (0: 정상 , 1:삭제			
	 */
	private int isDelete;
	

	/*
	 * 그룹에 속한 멤버수			
	 */
	private int memberCount;
	
	
	public String getMbgroupId() {
		return mbgroupId;
	}

	public void setMbgroupId(String mbgroupId) {
		this.mbgroupId = mbgroupId;
	}

	public String getMbgroupName() {
		return mbgroupName;
	}

	public void setMbgroupName(String mbgroupName) {
		this.mbgroupName = mbgroupName;
	}

	public String getMbgroupIntroduction() {
		return mbgroupIntroduction;
	}

	public void setMbgroupIntroduction(String mbgroupIntroduction) {
		this.mbgroupIntroduction = mbgroupIntroduction;
	}

	public String getImagefileId() {
		return imagefileId;
	}

	public void setImagefileId(String imagefileId) {
		this.imagefileId = imagefileId;
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

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public int getMemberCount() {
		return memberCount;
	}

	public void setMemberCount(int memberCount) {
		this.memberCount = memberCount;
	}

	public String getRegisterEnglishName() {
		return registerEnglishName;
	}

	public void setRegisterEnglishName(String registerEnglishName) {
		this.registerEnglishName = registerEnglishName;
	}

	public String getUpdaterEnglishName() {
		return updaterEnglishName;
	}

	public void setUpdaterEnglishName(String updaterEnglishName) {
		this.updaterEnglishName = updaterEnglishName;
	}

}
