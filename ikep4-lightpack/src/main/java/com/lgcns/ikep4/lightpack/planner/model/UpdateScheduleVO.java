/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.planner.model;

import java.util.Map;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 일정 수정에 사용되는 VO
 *
 * @author  신용환(combinet@gmail.com)
 * @version $Id: UpdateScheduleVO.java 16240 2011-08-18 04:08:15Z giljae $
 */
public class UpdateScheduleVO extends BaseObject {

	private static final long serialVersionUID = -3203309367287569993L;

	private Schedule newSchedule;
	private Map<String, ?> oldSchedule;
	private EventInfoVO eventInfo;
	
	private User user;
	private User trustee;
	
	/**
	 * 수정 유형
	 * updateType == 0 : 일반 일정 수정
	 * updateType > 0  : 반복일정
	 * updateType == 1 : 이번일정만
	 * updateType == 2 : 향후 일정 모두
	 * updateType == 3 : 전체일정
	 */
	private int updateType;	
	
	/**
	 * 삭제 유형
	 * deleteType == 0 : 일반일정 삭제
	 * deleteType == 1 : 반복일정 (이번일정만)
	 * deleteType == 2 : 반복일정 (향후일정 모두)
	 * deleteType == 3 : 반복일정 (전체일정)
	 */
	private int deleteType;
	
	/**
	 * repeat  - 반복 데이터만 변경
	 * content - 반복데이터외 내용만 변경
	 */
	private String updatePart;
	
	private String isDirtyRepeat;
	
	/**
	 * 일정 변경을 참여,참조자에게 메일 발송 여부
	 */
	private boolean sendmail;
	
	public Schedule getNewSchedule() {
		return newSchedule;
	}
	public void setNewSchedule(Schedule newSchedule) {
		this.newSchedule = newSchedule;
	}

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public User getTrustee() {
		return trustee;
	}
	public void setTrustee(User trustee) {
		this.trustee = trustee;
	}
	public Map<String, ?> getOldSchedule() {
		return oldSchedule;
	}
	public void setOldSchedule(Map<String, ?> oldSchedule) {
		this.oldSchedule = oldSchedule;
	}
	public int getUpdateType() {
		return updateType;
	}
	public void setUpdateType(int updateType) {
		this.updateType = updateType;
	}
	public String getUpdatePart() {
		return updatePart;
	}
	public void setUpdatePart(String updatePart) {
		this.updatePart = updatePart;
	}
	public boolean isSendmail() {
		return sendmail;
	}
	public void setSendmail(boolean sendmail) {
		this.sendmail = sendmail;
	}
	public EventInfoVO getEventInfo() {
		return eventInfo;
	}
	public void setEventInfo(EventInfoVO eventInfo) {
		this.eventInfo = eventInfo;
	}
	public String getIsDirtyRepeat() {
		return isDirtyRepeat;
	}
	public void setIsDirtyRepeat(String isDirtyRepeat) {
		this.isDirtyRepeat = isDirtyRepeat;
	}
	public int getDeleteType() {
		return deleteType;
	}
	public void setDeleteType(int deleteType) {
		this.deleteType = deleteType;
	}
	
}
