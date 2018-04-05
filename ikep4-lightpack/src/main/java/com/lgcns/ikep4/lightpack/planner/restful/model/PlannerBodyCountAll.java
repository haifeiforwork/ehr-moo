package com.lgcns.ikep4.lightpack.planner.restful.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="body")
public class PlannerBodyCountAll {
	@XmlElement(name="CountAllData")
	private PlannerDataCountAll plannerDataCountAll;
	
	public PlannerBodyCountAll() {
	}

	public PlannerBodyCountAll(PlannerDataCountAll plannerDataCountAll) {
		super();
		this.plannerDataCountAll = plannerDataCountAll;
	}
}