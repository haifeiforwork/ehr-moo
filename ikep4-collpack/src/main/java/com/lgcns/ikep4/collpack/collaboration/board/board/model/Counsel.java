/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.board.board.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.bind.annotation.RequestParam;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.framework.validator.constraints.Url;

import com.lgcns.ikep4.support.security.acl.model.ACLResourcePermission;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.role.model.Role;

/**
 * 게시판 관리 모델 클래스
 *
 * @author ${user}
 * @version $$Id: Board.java 16236 2011-08-18 02:48:22Z giljae $$
 */
public class Counsel extends BaseObject {

	private String appointmentDay;
	
	private String appointmentHour1;
	
	private String appointmentMinute1;
	
	private String appointmentHour2;
	
	private String appointmentMinute2;
	
	private String appointmentHour3;
	
	private String appointmentMinute3;
	
	private String appointmentId;
	
	/** 등록자 ID. */
	private String registerId;

	/** 등록자 이름. */
	private String registerName;

	/** 등록일. */
	private Date registDate;

	public String getAppointmentDay() {
		return appointmentDay;
	}

	public void setAppointmentDay(String appointmentDay) {
		this.appointmentDay = appointmentDay;
	}

	public String getAppointmentHour1() {
		return appointmentHour1;
	}

	public void setAppointmentHour1(String appointmentHour1) {
		this.appointmentHour1 = appointmentHour1;
	}

	public String getAppointmentMinute1() {
		return appointmentMinute1;
	}

	public void setAppointmentMinute1(String appointmentMinute1) {
		this.appointmentMinute1 = appointmentMinute1;
	}

	public String getAppointmentHour2() {
		return appointmentHour2;
	}

	public void setAppointmentHour2(String appointmentHour2) {
		this.appointmentHour2 = appointmentHour2;
	}

	public String getAppointmentMinute2() {
		return appointmentMinute2;
	}

	public void setAppointmentMinute2(String appointmentMinute2) {
		this.appointmentMinute2 = appointmentMinute2;
	}

	public String getAppointmentHour3() {
		return appointmentHour3;
	}

	public void setAppointmentHour3(String appointmentHour3) {
		this.appointmentHour3 = appointmentHour3;
	}

	public String getAppointmentMinute3() {
		return appointmentMinute3;
	}

	public void setAppointmentMinute3(String appointmentMinute3) {
		this.appointmentMinute3 = appointmentMinute3;
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

	public String getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(String appointmentId) {
		this.appointmentId = appointmentId;
	}


}