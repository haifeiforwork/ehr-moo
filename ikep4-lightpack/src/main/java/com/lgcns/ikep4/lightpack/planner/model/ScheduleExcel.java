/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.planner.model;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.FileLink;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * 일정 model
 * 
 * @author 신용환(combinet@gmail.com)
 * @version $Id: Schedule.java 18496 2012-05-03 07:45:56Z yruyo $
 */
public class ScheduleExcel extends BaseObject {

	
	private static final long serialVersionUID = -3473003393442526646L;



	/**
	 * 엑셀저장시 입력성공여부
	 */
	private String successYn;


	/**
	 * 엑셀저장시 에러메시지
	 */
	private String errMsg;

	/**
	 * 일정 시작일
	 */
	private String startDate;

	/**
	 * 일정 종료일
	 */
	private String endDate;

	

	/**
	 * 일정 시작 시간
	 */
	private String startTime;

	/**
	 * 일정 종료 시간
	 */
	private String endTime;
	
	/**
	 * 종일 일정 여부 ( 0: 시간일정, 1: 종일일정)
	 */
	private String wholeday;

	/**
	 * 일정 제목
	 */
	private String title;

	/**
	 * 일정 장소
	 */
	private String place;

	/**
	 * 일정 내용
	 */
	private String contents;

	
	/*
	 * 전사일정 사업장명
	 */
	private String workAreaName ;	

	
	
	private String meetingRoomId;


	
	/**
	 * portal ID
	 */
	private String portalId;

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
	 * 수정자 ID
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

	
	public ScheduleExcel() {
		
	}
	

	///////////////////////////////////////////////////////////
	
	public static Date toDate(final String str) {
		if(str.length() < 4) {
			throw new IllegalArgumentException();
		}

		String dtStr = StringUtils.rightPad(str, 14, "0");
		
		Calendar c = Calendar.getInstance();
		c.clear();
		
		int year = Integer.valueOf(dtStr.substring(0, 4)); //new Integer(dtStr.substring(0, 4));
		int month = Integer.valueOf(dtStr.substring(4, 6)) - 1; //new Integer(dtStr.substring(4, 6)) - 1;
		int date = Integer.valueOf(dtStr.substring(6, 8)); //new Integer(dtStr.substring(6, 8));
		int hourOfDay = Integer.valueOf(dtStr.substring(8, 10)); //new Integer(dtStr.substring(8, 10));
		int minute = Integer.valueOf(dtStr.substring(10, 12)); //new Integer(dtStr.substring(10, 12));	
		int second = Integer.valueOf(dtStr.substring(12, 14)); //new Integer(dtStr.substring(12, 14));
		
		c.set(year, month, date, hourOfDay, minute, second);
		
		return c.getTime();
	}
	



	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the place
	 */
	public String getPlace() {
		return place;
	}

	/**
	 * @param place the place to set
	 */
	public void setPlace(String place) {
		this.place = place;
	}

	/**
	 * @return the contents
	 */
	public String getContents() {
		return contents;
	}

	/**
	 * @param contents the contents to set
	 */
	public void setContents(String contents) {
		this.contents = contents;
	}




	/**
	 * @return the meetingRoomId
	 */
	public String getMeetingRoomId() {
		return meetingRoomId;
	}

	/**
	 * @param meetingRoomId the meetingRoomId to set
	 */
	public void setMeetingRoomId(String meetingRoomId) {
		this.meetingRoomId = meetingRoomId;
	}



	public String getWorkAreaName() {
		return workAreaName;
	}

	public void setWorkAreaName(String workAreaName) {
		this.workAreaName = workAreaName;
	}


	public String getStartDate() {
		return startDate;
	}


	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}


	public String getEndDate() {
		return endDate;
	}


	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


	public String getWholeday() {
		return wholeday;
	}


	public void setWholeday(String wholeday) {
		this.wholeday = wholeday;
	}


	public String getStartTime() {
		return startTime;
	}


	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}


	public String getEndTime() {
		return endTime;
	}


	public void setEndTime(String endTime) {
		this.endTime = endTime;
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


	public String getPortalId() {
		return portalId;
	}


	public void setPortalId(String portalId) {
		this.portalId = portalId;
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

	
}
