package com.lgcns.ikep4.lightpack.planner.restful.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="body")
public class PlannerBodyPlanCategory {
	@XmlElement(name="PlanCategoryList")
	private List<PlannerDataPlanCategory> planCategoryList;
	
	public PlannerBodyPlanCategory() {
		this.planCategoryList = new ArrayList<PlannerDataPlanCategory>();
	}

	public PlannerBodyPlanCategory(List<PlannerDataPlanCategory> planCategoryList) {
		super();
		this.planCategoryList = planCategoryList;
	}
	
	public void addPlanCategoryList(PlannerDataPlanCategory planCategoryList) {
		this.planCategoryList.add(planCategoryList);
	}
}