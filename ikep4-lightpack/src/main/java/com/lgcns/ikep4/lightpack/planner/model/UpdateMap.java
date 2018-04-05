package com.lgcns.ikep4.lightpack.planner.model;

import java.util.Date;
import java.util.Map;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.support.user.member.model.User;

public class UpdateMap extends BaseObject {

	private static final long serialVersionUID = 4441413836403378973L;
	
	private Schedule schedule;
	@SuppressWarnings("rawtypes")
	private Map sco;
	private User user;
	
	private Date start;
	private Date end;
	
	private int updateType = 0; 	// 일반일정 변경 - 0, 이번일정 변경 - 1, 이후 일정 변경 - 2
	
	private String portalId;
	
	
	public Schedule getSchedule() {
		return schedule;
	}
	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}
	@SuppressWarnings("rawtypes")
	public Map getSco() {
		return sco;
	}
	@SuppressWarnings("rawtypes")
	public void setSco(Map sco) {
		this.sco = sco;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	public int getUpdateType() {
		return updateType;
	}
	public void setUpdateType(int updateType) {
		this.updateType = updateType;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getPortalId() {
		return portalId;
	}
	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}
	
	
}
