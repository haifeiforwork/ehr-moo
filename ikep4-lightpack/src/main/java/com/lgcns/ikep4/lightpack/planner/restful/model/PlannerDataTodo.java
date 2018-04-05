package com.lgcns.ikep4.lightpack.planner.restful.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="todo")
public class PlannerDataTodo {

	private String itemId;
	private String isDelayToDo;
	private String itemTitle;
	private String itemEndDate;
	private String regUserId;
	
	public PlannerDataTodo(String itemId, String isDelayToDo, String itemTitle, String itemEndDate, String regUserId) {
		super();
		this.itemId = itemId;
		this.isDelayToDo = isDelayToDo;
		this.itemTitle = itemTitle;
		this.itemEndDate = itemEndDate;
		this.regUserId = regUserId;
	}

	public String getItemId() {
		return itemId;
	}

	public String getIsDelayToDo() {
		return isDelayToDo;
	}

	public String getItemTitle() {
		return itemTitle;
	}

	public String getItemEndDate() {
		return itemEndDate;
	}

	public String getRegUserId() {
		return regUserId;
	}
	
	

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public void setIsDelayToDo(String isDelayToDo) {
		this.isDelayToDo = isDelayToDo;
	}

	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}

	public void setItemEndDate(String itemEndDate) {
		this.itemEndDate = itemEndDate;
	}

	public void setRegUserId(String regUserId) {
		this.regUserId = regUserId;
	}
}