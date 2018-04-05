package com.lgcns.ikep4.lightpack.planner.restful.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="body")
public class PlannerBodyFavoriteUserList {
	@XmlElement(name="FavoriteUserList")
	private List<PlannerDataFavoriteUser> favoriteUserList;
	
	public PlannerBodyFavoriteUserList() {
		this.favoriteUserList = new ArrayList<PlannerDataFavoriteUser>();
	}

	public PlannerBodyFavoriteUserList(List<PlannerDataFavoriteUser> favoriteUserList) {
		super();
		this.favoriteUserList = favoriteUserList;
	}
	
	public void addEntrustUser(PlannerDataFavoriteUser favoriteUserList) {
		this.favoriteUserList.add(favoriteUserList);
	}
}