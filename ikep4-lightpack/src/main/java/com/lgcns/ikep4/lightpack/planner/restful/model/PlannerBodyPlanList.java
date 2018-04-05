package com.lgcns.ikep4.lightpack.planner.restful.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="body")
public class PlannerBodyPlanList {
	@XmlElement(name="HolidayList")
	private List<PlannerDataHoliday> holidayList;
	
	@XmlElement(name="PlanList")
	private List<PlannerDataPlan> planList;
	
	@XmlElement(name="TodoList")
	private List<PlannerDataTodo> todoList;
	
	public PlannerBodyPlanList() {
		this.holidayList = new ArrayList<PlannerDataHoliday>();
		this.planList = new ArrayList<PlannerDataPlan>();
		this.todoList = new ArrayList<PlannerDataTodo>();
	}

	public PlannerBodyPlanList(List<PlannerDataHoliday> holidayList,
			List<PlannerDataPlan> planList, List<PlannerDataTodo> todoList) {
		super();
		this.holidayList = holidayList;
		this.planList = planList;
		this.todoList = todoList;
	}

	public void addHoliday(PlannerDataHoliday holiday) {
		this.holidayList.add(holiday);
	}
	
	public void addPlan(PlannerDataPlan plan) {
		this.planList.add(plan);
	}
	
	public void addTodo(PlannerDataTodo todo) {
		this.todoList.add(todo);
	}
}