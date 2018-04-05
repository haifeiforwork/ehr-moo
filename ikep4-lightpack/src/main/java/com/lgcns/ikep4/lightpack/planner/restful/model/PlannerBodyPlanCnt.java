package com.lgcns.ikep4.lightpack.planner.restful.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="body")
public class PlannerBodyPlanCnt {
	@XmlElement(name="PlanCntInfo")
	private int planCntInfo;

	public PlannerBodyPlanCnt(int planCntInfo) {
		super();
		this.planCntInfo = planCntInfo;
	}
}