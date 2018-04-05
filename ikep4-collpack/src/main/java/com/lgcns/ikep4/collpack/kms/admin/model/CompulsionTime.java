/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.kms.admin.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 게시판 관리 모델 클래스
 *
 * @author ${user}
 * @version $$Id: Board.java 16236 2011-08-18 02:48:22Z giljae $$
 */
public class CompulsionTime extends BaseObject {

	private String mon;
	private String tue;
	private String wed;
	private String thu;
	private String fri;
	private String startHour;
	private String startMin;
	private String endHour;
	private String endMin;
	
	private String userId;
	private String startClikcTime;
	private String endClickTime;
	private String compulsionTimeClickFlg;
	
	public String getMon() {
		return mon;
	}
	public void setMon(String mon) {
		this.mon = mon;
	}
	public String getTue() {
		return tue;
	}
	public void setTue(String tue) {
		this.tue = tue;
	}
	public String getWed() {
		return wed;
	}
	public void setWed(String wed) {
		this.wed = wed;
	}
	public String getThu() {
		return thu;
	}
	public void setThu(String thu) {
		this.thu = thu;
	}
	public String getFri() {
		return fri;
	}
	public void setFri(String fri) {
		this.fri = fri;
	}
	public String getStartHour() {
		return startHour;
	}
	public void setStartHour(String startHour) {
		this.startHour = startHour;
	}
	public String getStartMin() {
		return startMin;
	}
	public void setStartMin(String startMin) {
		this.startMin = startMin;
	}
	public String getEndHour() {
		return endHour;
	}
	public void setEndHour(String endHour) {
		this.endHour = endHour;
	}
	public String getEndMin() {
		return endMin;
	}
	public void setEndMin(String endMin) {
		this.endMin = endMin;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getStartClikcTime() {
		return startClikcTime;
	}
	public void setStartClikcTime(String startClikcTime) {
		this.startClikcTime = startClikcTime;
	}
	public String getEndClickTime() {
		return endClickTime;
	}
	public void setEndClickTime(String endClickTime) {
		this.endClickTime = endClickTime;
	}
	public String getCompulsionTimeClickFlg() {
		return compulsionTimeClickFlg;
	}
	public void setCompulsionTimeClickFlg(String compulsionTimeClickFlg) {
		this.compulsionTimeClickFlg = compulsionTimeClickFlg;
	}


}