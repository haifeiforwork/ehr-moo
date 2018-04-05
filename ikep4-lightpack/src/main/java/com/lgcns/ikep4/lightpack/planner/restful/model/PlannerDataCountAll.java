package com.lgcns.ikep4.lightpack.planner.restful.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="planCategory")
public class PlannerDataCountAll {

	private String apprCount = "0";
	private String todoCount = "0";
	private String scheduleCount = "0";
	private String messageCount = "0";
	private String phoneMessageCount = "0";
	private String totalCount = "0";
	
	
	public String getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
	public String getTodoCount() {
		return todoCount;
	}
	public void setTodoCount(String todoCount) {
		this.todoCount = todoCount;
	}
	public String getScheduleCount() {
		return scheduleCount;
	}
	public void setScheduleCount(String scheduleCount) {
		this.scheduleCount = scheduleCount;
	}
	public String getMessageCount() {
		return messageCount;
	}
	public void setMessageCount(String messageCount) {
		this.messageCount = messageCount;
	}
	public String getPhoneMessageCount() {
		return phoneMessageCount;
	}
	public void setPhoneMessageCount(String phoneMessageCount) {
		this.phoneMessageCount = phoneMessageCount;
	}
	public String getApprCount() {
		return apprCount;
	}
	public void setApprCount(String apprCount) {
		this.apprCount = apprCount;
	}

	
}